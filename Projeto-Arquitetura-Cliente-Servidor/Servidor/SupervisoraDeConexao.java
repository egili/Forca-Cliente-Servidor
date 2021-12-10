package Servidor;

import ClassesComuns.*;
import Clientes.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread {
	private static final String Comunicado = null;
	private double valor = 0;
	private Parceiro usuario;
	private Socket conexao;
	private final ArrayList<Parceiro> usuarios;
	private static Palavra palavra;
	private static Tracinhos tracinhos;
	private static ControladorDeErros controladorDeErros;
	private static ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;

	public SupervisoraDeConexao(Socket conexao, ArrayList<Parceiro> usuarios) throws Exception {
		if (conexao == null)
			throw new Exception("Conexao ausente");

		if (usuarios == null)
			throw new Exception("Usuarios ausentes");

		this.conexao = conexao;
		this.usuarios = usuarios;
	}

	public void run() {

		ObjectOutputStream transmissor;
		try {
			transmissor = new ObjectOutputStream(this.conexao.getOutputStream());
		} catch (Exception erro) {
			return;
		}

		ObjectInputStream receptor = null;
		try {
			receptor = new ObjectInputStream(this.conexao.getInputStream());
		} catch (Exception err0) {
			try {
				transmissor.close();
			} catch (Exception falha) {
			} // so tentando fechar antes de acabar a thread

			return;
		}

		try {
			this.usuario = new Parceiro(this.conexao, receptor, transmissor);
		} catch (Exception erro) {
		} // sei que passei os parametros corretos
		ComunicadoDeDados dadosDaForca = null;
		try {
			synchronized (this.usuarios) {
				// formar grupos sortear a palavra
				this.usuarios.add(this.usuario);
				if (usuarios.size() % 3 == 0) {
					palavra = BancoDePalavras.getPalavraSorteada();
					tracinhos = new Tracinhos(palavra.getTamanho());
					int jogadores = usuarios.size();

					for (Parceiro jogador : this.usuarios) {
						jogador.receba(new ComunicadoComecouPartida());
					}
					dadosDaForca = new ComunicadoDeDados();
					// para chamar comunicado de vez ai vai da a vez para primeiro jogador
					this.usuarios.get(0).receba(new ComunicadoDeVez(dadosDaForca));
				}
				Comunicado comunicado;
				if ( comunicado == null)
	            	   return;
	                if (comunicado instanceof PedidoParaEntrar)
	               {
	            	int posJogador = this.usuarios.indexOf(usuario);
	            	if (posJogador > 2)
	            	{
	            	 try {
						usuario.receba(new ComunicadoSalaCheia());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	 
	            	}
				System.out.println("Infelizmente servidor esta cheio");
			}

			for (;;) {
				Comunicado comunicado2 = this.usuario.envie();

				// Pede a letra para cliente aqui esta jogo todo, verifica se ela j� foi
				// digitada, verifica erros se j� digitou ou n�o
				// pega a posi��o das letras os tracinhos dela
				if (comunicado2 == null)
					return;
				else if (comunicado2 instanceof ComunicadoDeDados) {
					dadosDaForca = (ComunicadoDeDados) comunicado2;
				} else if (comunicado2 instanceof PedidoDePalavra) {
					PedidoDePalavra pedidoPalavra = (PedidoDePalavra) comunicado2;
					String palavra = pedidoPalavra.palavra;

					// Verifica se o jogador acertou ou n�o a palavra na FORCA
					if (palavra.equals(dadosDaForca.getPalavra().toString())) {
						// Ele eh avisado da vitoria no jogo
						usuario.receba(new ComunicadoDeResultadoPalavra(true));

					} else {

						usuario.receba(new ComunicadoDeResultadoPalavra(false));

					}
				} else if (comunicado instanceof PedidoDeLetra) {
					boolean jaDigitadas = false;
					boolean palavraTemLetra = false;
					PedidoDeLetra pedidoDeLetra = (PedidoDeLetra) comunicado;
					char letra = pedidoDeLetra.getLetra();
					if (controladorDeLetrasJaDigitadas.isJaDigitada(letra))
						jaDigitadas = true;
					else {
						dadosDaForca.getControladorDeLetrasJaDigitadas().registrarletra(letra);
						int qtd = palavra.getQuantidade(letra);

						if (qtd == 0) {
							System.err.println("A palavra nao tem essa letra!\n");
							dadosDaForca.getControladorDeErros().registreUmErro();
						} else {
							for (int i = 0; i < qtd; i++) {
								int posicao = palavra.getPosicaoDaIezimaOcorrencia(i, letra);
								dadosDaForca.getTracinhos().revele(posicao, letra);
							}
							this.usuario.receba(new ComunicadoDeVitoria());
						}
					}
					ComunicadoDeLetra comunicadoDeLetra = new ComunicadoDeLetra();
					comunicadoDeLetra.setJaDigitada(jaDigitadas);
					comunicadoDeLetra.setPalavraTemLetra(palavraTemLetra);
					this.usuario.receba(comunicadoDeLetra);
				}
				// para percorrer a vez do jogador
				else if (comunicado instanceof ComunicadoDeVez) {
					int percorrerVez = this.usuarios.indexOf(this.usuario) + 1;

					if (percorrerVez == 3) {
						percorrerVez = 0;
					}

					this.usuarios.get(percorrerVez).receba(new ComunicadoDeVez(dadosDaForca));
				} else if (comunicado instanceof ComunicadoDeVitoria) {
					int percorrerVez = this.usuarios.indexOf(this.usuario) + 1;

					if (percorrerVez == 3) {
						percorrerVez = 0;
					}

					this.usuarios.get(percorrerVez).receba(new ComunicadoDeVez(dadosDaForca));
				}
			}
		} catch (Exception err) {

		}
	}
}