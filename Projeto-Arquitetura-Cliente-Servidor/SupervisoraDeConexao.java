
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
//import ClassesComuns.*;

public class SupervisoraDeConexao extends Thread {
	private Socket conexao;
	private ControladoraDePartida controladora;
	private Parceiro jogador;
	private ArrayList<Parceiro> jogadores;
	private ObjectOutputStream transmissor;
	private ObjectInputStream receptor;
	private ComunicadoDeDados comunicadoDeDados;
	public boolean fim = true;

	public SupervisoraDeConexao(Socket conexao, ArrayList<Parceiro> usuarios, ControladoraDePartida controladora)
			throws Exception {

		if (conexao == null)
			throw new Exception("Conexao ausente");

		if (usuarios == null)
			throw new Exception("Usuarios ausentes");

		if (controladora == null)
			throw new Exception("Controladora nula");

		this.conexao = conexao;
		this.jogadores = usuarios;
		this.controladora = controladora;
	}

	public void run() {
		try {
			transmissor = new ObjectOutputStream(this.conexao.getOutputStream());
		} catch (Exception erro) {
			return;
		}

		try {
			receptor = new ObjectInputStream(this.conexao.getInputStream());
		} catch (Exception erro) {
			try {
				transmissor.close();
			} catch (Exception falha) {
			}
			return;
		}

		try {
			this.jogador = new Parceiro(this.conexao, receptor, transmissor);
		} catch (Exception erro) {
		}

		try {
			synchronized (jogadores) {
				this.jogadores.add(this.jogador);
			}
		} catch (Exception erro) {
			try {
				transmissor.close();
				receptor.close();
			} catch (Exception falha) {
			} // tentativa de fechar antes que a thread termine
			return;
		}

		try {
			do {
			} while (!(jogador.espie() instanceof ComunicadoDeVez));

			jogador.envie();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		while (fim) {
			try {
				if (controladora.podeJogar(jogador)) {
					vezDeJogar();
				}
			} catch (Exception e) {
				return;
			}
		}

	}

	private void vezDeJogar() {
		try {
			jogador.receba(new VezDeJogar());

			jogador.receba(this.comunicadoDeDados);

			Comunicado comunicado = null;

			comunicado = this.jogador.envie();

			if (comunicado == null)
				return;

			else if (comunicado instanceof Pedido) {
				Pedido pedido = (Pedido) comunicado;

				Pedido oQueFoiPedido = pedido.getPedido();
//				switch (oQueFoiPedido) {
//
//				case '1':
//						//TODO: acoes que acontecerao qnd o cliente escolher digitar uma letra
//					break;
//
//				case '2':
//					    //TODO: acoes que acontecerao qnd o cliente escolher digitar uma palavra
//					break;
//				}

			}

			else if (comunicado instanceof PedidoParaSair) {
				synchronized (this.jogadores) {
					if (this.jogador == jogadores.get(jogadores.size() - 1))
						controladora.proximoJogador();

					this.jogadores.remove(this.jogador);
				}
				this.jogador.adeus();
				fim = false;
				return;
			}

			else if (comunicado instanceof ComunicadoDePerda) {
				// TODO: o que acontece qnd um usuario perde
			}

			if (!comunicadoDeDados.isTracinhos()) {
				comunicado = new ComunicadoDeVitoria();
					//TODO: o que acontece qnd um usuario ganha
			}

		} catch (Exception e) {
			
			System.err.println(e.getMessage()); 
		}
	}

	@Override
	public String toString() {
		String ret = "Jogador: " + jogador;
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (this.getClass() != obj.getClass())
			return false;

		SupervisoraDeConexao supervisora = (SupervisoraDeConexao) obj;

		if (!this.jogador.equals(supervisora.jogador))
			return false;

		if (!this.conexao.equals(supervisora.conexao))
			return false;

		if (!this.controladora.equals(supervisora.controladora))
			return false;

		if (!this.receptor.equals(supervisora.receptor))
			return false;

		if (!this.transmissor.equals(supervisora.transmissor))
			return false;

		if (this.jogadores.size() != supervisora.jogadores.size())
			return false;

		for (int i = 0; i < this.jogadores.size(); i++) {
			if (!this.jogadores.get(i).equals(supervisora.jogadores.get(i)))
				return false;
		}

		return true;
	}

	@Override
	public int hashCode() {

		int ret = 31;

		ret = ret * 11 + this.controladora.hashCode();
		ret = ret * 11 + this.jogador.hashCode();
		ret = ret * 11 + this.conexao.hashCode();
		ret = ret * 11 + this.receptor.hashCode();
		ret = ret * 11 + this.transmissor.hashCode();

		for (Parceiro cliente : jogadores)
			ret = ret * 11 + cliente.hashCode();

		return ret < 0 ? -ret : ret;
	}
}
