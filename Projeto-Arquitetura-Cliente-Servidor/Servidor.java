
import java.util.ArrayList;
//import ClassesComuns.*;

public class Servidor {

	public static String PORTA_PADRAO = "3000";

	public static void main(String[] args) {

		if (args.length > 1) {
			System.err.println("Uso esperado: java Servidor [PORTA]\n");
			return;
		}

		String porta = Servidor.PORTA_PADRAO;

		if (args.length == 1)
			porta = args[0];

		String comando = " ";
		AceitadoraDeConexao aceitadora;
		ArrayList<Parceiro> jogadores;

		jogadores = new ArrayList<>();

		try {
			aceitadora = new AceitadoraDeConexao(porta, jogadores);
			aceitadora.start();
		} catch (Exception e) {
			System.err.print("escolha uma porta apropriada e liberada para uso");
			return;
		}

		while (true) {
			System.out.println("O servidor esta ativo! Para desativa-lo,");
			System.out.println("use o comando \"desativar\"\n");
			System.out.print("> ");

			try {
				comando = (Teclado.getUmString().toLowerCase());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			if (comando.equals("desativar")) {
				synchronized (jogadores) {
					ComunicadoDeDesligamento comunicadoDeDesligamento = new ComunicadoDeDesligamento();

					for (Parceiro cliente : jogadores) {
						try {
							cliente.receba(comunicadoDeDesligamento);
							cliente.adeus();
						} catch (Exception erro) {
						}
					}

					System.out.println("O servidor foi desativado!\n");
					System.exit(0);
				}
			} else
				System.err.println("Comando invalido");
		}
	}
}
