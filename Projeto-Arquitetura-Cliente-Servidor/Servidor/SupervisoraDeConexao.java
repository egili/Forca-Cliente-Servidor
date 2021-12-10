package Servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import ClassesComuns.*;

public class SupervisoraDeConexao extends Thread {
	private Socket conexao;
	private ControladoraDePartida controladora;
	private Parceiro jogador;
	private ArrayList<Parceiro> usuarios;
	private ObjectOutputStream transmissor;
	private ObjectInputStream receptor;
	public boolean fim = true;

	public SupervisoraDeConexao(Socket conexao, ArrayList<Parceiro> usuarios, ControladoraDePartida controladora)
			throws Exception {
		if (conexao == null)
			throw new Exception("Conexao ausente");

		if (usuarios == null)
			throw new Exception("Usuarios ausentes");

		if (controladora == null)
			throw new Exception("Controladora invalida");

		this.conexao = conexao;
		this.usuarios = usuarios;
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

			synchronized (usuarios) {
				this.usuarios.add(this.jogador);
			}

		} catch (Exception erro) {
			try {
				transmissor.close();
				receptor.close();
			} catch (Exception falha) {
			} // so tentando fechar antes de acabar a thread

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
				if (controladora.pode(jogador)) {
					vezDoUsuario();
				}
			} catch (Exception e) {
				return;
			}
		}

	}

	private void vezDoUsuario() {
		try {

		} catch (Exception e) {

		}
	}

	@Override
	public String toString() {
		String ret = "Jogador: " + jogador;
		return ret;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		SupervisoraDeConexao sup = (SupervisoraDeConexao) o;

		if (!this.jogador.equals(sup.jogador))
			return false;

		if (!this.conexao.equals(sup.conexao))
			return false;

		if (!this.controladora.equals(sup.controladora))
			return false;

		if (!this.receptor.equals(sup.receptor))
			return false;

		if (!this.transmissor.equals(sup.transmissor))
			return false;

		if (this.usuarios.size() != sup.usuarios.size())
			return false;

		for (int i = 0; i < this.usuarios.size(); i++)
			if (!this.usuarios.get(i).equals(sup.usuarios.get(i)))
				return false;

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

		for (Parceiro parc : usuarios)
			ret = ret * 11 + parc.hashCode();

		if(ret < 0)
			ret = -ret;
		return ret;
	}
}
