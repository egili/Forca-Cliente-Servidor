import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.ArrayList;

public class Cliente {
	public static final String HOST_PADRAO = "localhost";
	public static final int PORTA_PADRAO = 3000;

	public static void main(String[] args) throws Exception {

		System.out.println("Bem-vindo(a) ao jogo forca servidor! - Trabalho final de java");
		System.out.println("Institui��o Estudantil: Cotuca/Unicamp");
		System.out.println("Curso: 59 - T�cnico em Desenvolvimento de Sistemas Noturno");
		System.out.println("Disciplina: DS201 - T�cnicas De Programa��o II");
		System.out.println("Professor: Andr� de Carvalho");
		System.out.println("Esse jogo foi desenvolvido por:");
		System.out.println("RA 20668 - Elis�ngela Sanntos, RA 20669 - Eliseu Gili");
		System.out.println("RA 21101 - Leandro de Freitas, RA 21106 - Lunara Cunha");
		System.out.println("Antes de passarmos ao menu, vamos te passar algumas regras desse jogo \n");
	
		System.out.println("Regra 1: Voc� pode escolher digitar uma letra ou palavra quando for a sua vez de jogar na partida");
		System.out.println("Regra 2: Voc� ter� uma palavra a ser advinhada por partida para o grupo de 3 jogadores");
		System.out.println("Regra 3: Sempre ter� um ganhador e dois perdedores por partida");
		System.out.println("Regra 4: Para cada palavra ter� uma dica para ajudar na advinha��o da palavra");
		System.out.println("Regra 5: Voc� pode escolher uma palavra a qualquer momento da partida");
		System.out.println("Regra 6: Se voc� digitar a palavra e errar; seu jogo acaba,voc� termina como perdedor, e os outros dois jogadores continuam na sala at� um dos dois advinharem a palavra sorteada");
		System.out.println("Regra 7: Se voc� digitar a palavra e acertar, seu jogo acaba, voc� termina como ganhador e os outros dois jogadores ficam como perdedores");
		System.out.println("Regra 8: Se voc� digitar uma letra e errar, passa a vez para o pr�ximo jogador em sentido hor�rio na sala, at� que um dos 3 acerte a palavra sorteada ");
		System.out.println("Regra 9: Se voc� digitar a �ltima letra para a palavra e acertar, voc� termina o jogo como ganhador e os demais ficam como perdedores, encerrando a partida");
		System.out.println("Regra 10: Se por algum motivo voc� sair do jogo pois perdeu e tentar voltar ao jogo, ter� que esperar em uma sala para aguardar futuros jogadores que v�o estar nessa sala at� completar um grupo de 3 jogadores para iniciar uma nova partida");

		if (args.length > 2) {
			System.err.println("Uso esperado: java Cliente [HOST [PORTA]]\n");
			return;
		}

		// Criando os objetos que ser�o instanciados

		Socket conexao = null;
		ObjectOutputStream transmissor = null;
		ObjectInputStream receptor = null;
		Parceiro servidor = null;
		Comunicado comunicado = null;
		ControladorDePartida suavez = null;

		try {
			conexao = Instanciacao.instanciarConexao(args);
			transmissor = Instanciacao.instanciarTransmissor(conexao);
			receptor = Instanciacao.instanciarReceptor(conexao);
			servidor = Instanciacao.instanciarServidor(conexao, receptor, transmissor);
			comunicado = Instanciacao.instanciarTratadora(servidor);
		} catch (Exception err) {
			System.err.println(err.getMessage());
			System.err.println("Verefique se o servidor est� ativo.\n"
					+ "Se sim, verefique se o servidor e a porta provido est�o corretos!\n");
			System.exit(0);
		}

		// Aguarde os usuarios entrarem
		System.out.println("Aguarde os demais jogares entrarem na partida");

		int opcao = ' ';

		while (!(comunicado instanceof ComunicadoDeInicioDeJogo)) {
			System.out.println("Jogador, a sua conex�o com o nosso servidor foi aceita");
			System.out.println("Bem-vindo ao menu, tecle a sua opcao:" + opcao);
			System.out.println("Tecle 1 para digitar a letra!");
			System.out.println("Tecle 2 para digitar a palavra!");

			/**
			 * Capturar um int,se o que o jogador digitar for diferente de 1,2 dispara erro
			 * de op��o inv�lida
			 **/
			try {
				opcao = Teclado.getUmInt();
			}

			catch (Exception erro) {
				System.err.println("Opcao invalida!\n");
				continue;
			}

			// *Se entrar na op��o 1, o servidor recebe um pedido de letra e o comunicado
			// come�a nulo, entra no do de espiar o comunicado. *//
			try {
				if (opcao == 1) {
					System.out.println("Digite uma letra");
					char letra = Teclado.getUmChar();
					servidor.receba(new PedidoDeLetra(letra));

					comunicado = null;
					do {
						comunicado = (Comunicado) servidor.espie();
					} while (!(comunicado instanceof ComunicadoDeLetra));

				}
			}

			catch (Exception erro) {
			}

			try {
				if (opcao == 2) {
					System.out.println("Digite uma Palavra");
					servidor.receba(new PedidoDePalavra());
					comunicado = null;
					do {
						comunicado = (Comunicado) servidor.espie();
					}

					while (!(comunicado instanceof ComunicadoDePerda) && !(comunicado instanceof ComunicadoDeVitoria));

				}
			} catch (Exception erro) {
			}

			try {
				comunicado = (Comunicado) servidor.espie();
			} catch (Exception err) {
				System.err.print(err.getMessage());
			}

			try {
				servidor.receba(comunicado);
				comunicado = servidor.envie();
			} catch (Exception e) {
				System.err.print(e.getMessage());
			}

			System.out.println("O jogo comecou!");

			while (true) {
				Comunicado rodada = null;

				try {

					do {
						rodada = servidor.espie();
					} while (!(rodada instanceof PedidoVezDeJogar));
					rodada = servidor.envie();
				} catch (Exception e) {
				}

				System.out.println("\n-----------Sua vez comecou----------");

				if (rodada instanceof PedidoVezDeJogar) {
					comunicado = null;
					do {
						try {
							comunicado = servidor.espie();
						} catch (Exception err) {
							System.err.println(err.getMessage() + " Erro ao espiar");
						}
					} while (!(comunicado instanceof ControladorDePartida));

					try {
						suavez = (ControladorDePartida) servidor.envie();
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}

					do {
						try {
							comunicado = servidor.espie();
						} catch (Exception e) {
							System.err.println(e.getMessage() + " Erro ao espiar");
						}

						do {

							// O usuario espera o servidor enviar a sua vez de volta
							comunicado = (Comunicado) servidor.espie();
						}

						while (!(comunicado instanceof ControladorDePartida));

						// Usuario recebe sua vez de volta
						suavez = (ControladorDePartida) servidor.envie();
						System.out.println(suavez);

					} while ("12".indexOf(opcao) != -1);

				}
			}
		}

		System.out.println("Obrigado por usar esse programa !!");
		System.exit(0);
	}// end da main

}// end da classe
