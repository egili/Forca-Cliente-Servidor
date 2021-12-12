

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
//import ClassesComuns.*;

public class AceitadoraDeConexao extends Thread {
	private ServerSocket servidor;
	private ArrayList<Parceiro> jogadores;
	private ControladoraDePartida controladoraPartida;
	private Boolean isComecou = false;

	public AceitadoraDeConexao(String porta, ArrayList<Parceiro> usuarios) throws Exception {
		if (porta == null)
			throw new Exception("Insira uma porta valida");

		try {
			servidor = new ServerSocket(Integer.parseInt(porta));
		} catch (Exception e) {
			System.err.println("Porta invalida");
		}

		if (usuarios == null)
			throw new Exception("Usuarios ausentes");

		this.jogadores = usuarios;
		this.controladoraPartida = new ControladoraDePartida(this.jogadores);

	}

	public void run() {
		while (true) {
			Socket conexao;
			try {
				conexao = servidor.accept();
			} catch (Exception e) {
				continue;
			}

			synchronized (jogadores) {
				if (jogadores.size() == 3) {
					try {
						conexao.close();
						continue;
					} catch (Exception e) {
					}
				}
			}

			SupervisoraDeConexao supervisora = null;

			try {
				supervisora = new SupervisoraDeConexao(conexao, jogadores, controladoraPartida);

				synchronized (controladoraPartida.getSupervisoras()) {
					controladoraPartida.adicionarSupervisora(supervisora);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			supervisora.start();

			try {
				Thread.sleep(500);
			} catch (Exception ignored) {
			}

			synchronized (jogadores) {
				if (jogadores.size() == 3 && !isComecou) {
					isComecou = true;
					try {
						for (Parceiro usuario : jogadores)
							usuario.receba(new ComunicadoDeVez(null));
					} catch (Exception e) {
					}
				} else if (isComecou) {
					try {
						if (jogadores.size() == 2)
							jogadores.get(1).receba(new ComunicadoComecouPartida());
						else if (jogadores.size() == 3)
							jogadores.get(2).receba(new ComunicadoComecouPartida());
					} catch (Exception e) {
					}
				}
			}
		}
	}

	@Override
	public String toString() {
			return "Servidor: " + servidor + "\nPartida iniciada: " + isComecou + "\n Controladora De Partida:" + controladoraPartida;	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (this.getClass() != obj.getClass())
			return false;

		AceitadoraDeConexao aceitadora = (AceitadoraDeConexao) obj;

		if (!this.servidor.equals(aceitadora.servidor))
			return false;

		if (!this.controladoraPartida.equals(aceitadora.controladoraPartida))
			return false;

		if (!this.isComecou.equals(aceitadora.isComecou))
			return false;

		if (this.jogadores.size() != aceitadora.jogadores.size())
			return false;

		for (int i = 0; i < this.jogadores.size(); i++)
			if (!this.jogadores.get(i).equals(aceitadora.jogadores.get(i)))
				return false;

		return true;
	}

	@SuppressWarnings("removal")
	@Override
	public int hashCode() {
		int ret = 31;

		ret = ret * 7 + new Boolean(this.isComecou).hashCode();
		ret = ret * 7 + this.controladoraPartida.hashCode();
		ret = ret * 7 + this.servidor.hashCode();

		for (int i = 0; i < jogadores.size(); i++) {
			ret = ret * 7 + jogadores.get(i).hashCode();
		}

		return ret < 0 ? -ret : ret;
	}
}
