package Servidor;

import java.util.ArrayList;

import ClassesComuns.*;

public class Servidor {
	public static final String PORTA_PADRAO = "3000";
	public static final String HOST_PADRAO = "localhost";

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
		ArrayList<Parceiro> usuarios;
		
		usuarios = new ArrayList<>();
		
		try 
		{
			aceitadora = new AceitadoraDeConexao(porta, usuarios);
			aceitadora.start();
		}catch(Exception e)
		{
			System.err.print(e.getMessage());			
		}
		
		while (true)
		{
			System.out.println("O servidor esta ativo! Para desativa-lo,");
			System.out.println("use o comando \"desativar\"\n");
			System.out.print("> ");

			try {
				comando = (Teclado.getUmString().toLowerCase());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			if (comando.equals("desativar")) {
				synchronized (usuarios) {
					ComunicadoDeDesligamento comunicadoDeDesligamento =
							new ComunicadoDeDesligamento();

					for (Parceiro usuario : usuarios) {
						try {
							usuario.receba(comunicadoDeDesligamento);
							usuario.adeus();
						} catch (Exception erro) {
						}
					}
				}

				System.out.println("O servidor foi desativado!\n");
				System.exit(0);
			} else
				System.err.println("Comando invalido");


		}
	}
}
