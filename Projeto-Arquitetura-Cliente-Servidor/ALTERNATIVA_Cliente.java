
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

	/**FIXME: LER ESTA COMENTARIO COM ATENCAO 
	 * esta classe eh uma ALTERNATIVA a main do Cliente ja implementada por outros membros do grupo
	 * 	o motivo pelo qual eu fiz ela eh que estava muito trabalhoso refatorar a main ja existente
	 * ate o momento, nao ha motivo para substituir o uso da outra main por essa**/

public class ALTERNATIVA_Cliente {

	public static final String HOST_PADRAO = "localhost";
	public static final int PORTA_PADRAO = 3000;

	public static void main(String[] args) {
		if (args.length > 2) {
			System.err.println("Uso esperado: java app [HOST[PORTA]]");
			return;
		}

		Socket conexao = null;
		ObjectOutputStream transmissor = null;
		ObjectInputStream receptor = null;
		Parceiro servidor = null;
		TratadoraDeComunicados tratadoraDeComunicados = null;
		Pedido pedido = null;

		try {
			conexao = Instanciacao.instanciarConexao(args);
			transmissor = Instanciacao.instanciarTransmissor(conexao);
			receptor = Instanciacao.instanciarReceptor(conexao);
			servidor = Instanciacao.instanciarServidor(conexao, receptor, transmissor);
			tratadoraDeComunicados = Instanciacao.instanciarTratadoraDeComunicados(servidor);

		} catch (Exception err) {
			System.err.println(err.getMessage());
			System.err.println("Verefique se o servidor esta ativo.\n"
					+ "Se sim, verifique se o servidor e a porta providos estao corretos!\n");
			System.exit(0);
		}

		tratadoraDeComunicados.start();

		System.out.println("Aguarde os jogadores entrarem na partida");

		Comunicado comunicado = null;
		
		while (!(comunicado instanceof ComunicadoComecouPartida)) {

			try {
				comunicado = (Comunicado) servidor.espie();
			} catch (Exception err) {
				System.err.print(err.getMessage());
			}
		}

		try {
			servidor.receba(comunicado);
			comunicado = servidor.envie();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}

		System.out.println("O jogo comecou!");

		while (true) {
			Comunicado partida = null;

			try {

				do {
					partida = servidor.espie();
				} while (!(partida instanceof ComunicadoDeVez));
				partida = servidor.envie();
			} catch (Exception e) {
			}

			System.out.println("\n sua vez de jogar");

			if (partida instanceof ComunicadoDeVez) {
				comunicado = null;
				do {
					try {
						comunicado = servidor.espie();
					} catch (Exception err) {
						System.err.println(err.getMessage() + " Erro ao espiar");
					}
				} while (!(comunicado instanceof Pedido));

				try {
					pedido = (Pedido) servidor.envie();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

				String opcao = "";
				for (;;) {
					try {
						opcao = Teclado.getUmString().toUpperCase();
						System.out.println("Jogador, a sua conexao com o nosso servidor foi aceita");
						System.out.println("Bem-vindo ao menu, tecle a sua opcao:" + opcao);
						System.out.println("Tecle 1 para digitar a letra!");
						System.out.println("Tecle 2 para digitar a palavra!");

						if (!(opcao.equals("1") || opcao.equals("2")))
							throw new Exception("Opcao Invalida");
						break;
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
				
				try {

					if (opcao.equals("1")) {
						servidor.receba(new PedidoDeLetra());

						do {
							comunicado = (Comunicado) servidor.espie();
						} while (!(comunicado instanceof Pedido));

						pedido = (Pedido) servidor.envie();
						System.out.println(pedido);

					} else if (opcao.equals("2")) {
						servidor.receba(new Pedido(pedido));

						do {
							if (comunicado instanceof PedidoDePalavra) {
								pedido = (Pedido) servidor.envie();
								System.out.println(pedido.getPedido());
							}

							comunicado = (Comunicado) servidor.espie();
						} while (!(comunicado instanceof Pedido));

						pedido = (Pedido) servidor.envie();

						System.out.println(pedido);

						do {
							opcao = Teclado.getUmString().toUpperCase();
							System.out.print("digite sua palavra: " + opcao);

						} while (!Pedido.isPedidoPalavra(pedido));

						servidor.receba(new PedidoDePalavra());

						do {
							comunicado = (Comunicado) servidor.espie();
						} while (!(comunicado instanceof Pedido));

						pedido = (Pedido) servidor.envie();
						System.out.println(pedido);
					}

				} catch (Exception erro) {
					System.err.println("\n opcao invalida");
				}
			}

			try {
				servidor.receba(new PedidoParaSair());
			} catch (Exception erro) {
			}

			System.out.println("Obrigado por usar esse programa !!");
			System.exit(0);
		}
	}
}
