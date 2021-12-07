import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Cliente {
	public static final String HOST_PADRAO = "localhost";
	public static final int PORTA_PADRAO = 3000;

	public static void main (String[] args) throws Exception
	{
        if (args.length>2)
        {
            System.err.println ("Uso esperado: java Cliente [HOST [PORTA]]\n");
            return;
        }

        Socket conexao=null;
        try
        {
            String host = Cliente.HOST_PADRAO;
            int    porta= Cliente.PORTA_PADRAO;

            if (args.length>0)
                host = args[0];

            if (args.length==2)
                porta = Integer.parseInt(args[1]);

            conexao = new Socket (host, porta);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectOutputStream transmissor=null;
        try
        {
            transmissor =
            new ObjectOutputStream(
            conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor =
            new ObjectInputStream(
            conexao.getInputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        Parceiro servidor=null;
        try
        {
            servidor =
            new Parceiro (conexao, receptor, transmissor);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
        try
        {
       
		tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento(servidor);
		}
		catch (Exception erro){} // sei que servidor foi instanciado
		
        tratadoraDeComunicadoDeDesligamento.start();

        int opcao=' ';
        do
        {
            System.out.println("Bem-vindo(a) ao jogo forca servidor! - Trabalho final de java");
            System.out.println("Instituição Estudantil: Cotuca/Unicamp");
            System.out.println("Curso: 59 - Técnico em Desenvolvimento de Sistemas Noturno");
            System.out.println("Disciplina: DS201 - Técnicas De Programação II");
            System.out.println("Professor: André de Carvalho");
            System.out.println("Esse jogo foi desenvolvido por:");
            System.out.println("RA 20668 - Elisângela Sanntos, RA 20669 - Eliseu Gilli");
            System.out.println("RA 21101 - Leandro de Freitas, RA 21106 - Lunara Cunha");
            System.out.println("Antes de passarmos ao menu, vamos te passar algumas regras desse jogo");
            System.out.println();
            System.out.println("Regra 1: Você pode escolher digitar uma letra ou palavra quando for a sua vez de jogar na partida");
            System.out.println("Regra 2: Você terá uma palavra a ser advinhada por partida para o grupo de 3 jogadores");
            System.out.println("Regra 3: Sempre terá um ganhador e dois perdedores por partida");
            System.out.println("Regra 4: Para cada palavra terá uma dica para ajudar na advinhação da palavra");
            System.out.println("Regra 5: Você pode escolher uma palavra a qualquer momento da partida");
            System.out.println("Regra 6: Se você digitar a palavra e errar; seu jogo acaba,você termina como perdedor, e os outros dois jogadores continuam na sala até um dos dois advinharem a palavra sorteada");
            System.out.println("Regra 7: Se você digitar a palavra e acertar, seu jogo acaba, você termina como ganhador e os outros dois jogadores ficam como perdedores");
            System.out.println("Regra 8: Se você digitar uma letra e errar, passa a vez para o próximo jogador em sentido horário na sala, até que um dos 3 acerte a palavra sorteada ");
            System.out.println("Regra 9: Se você digitar a última letra para a palavra e acertar, você termina o jogo como ganhador e os demais ficam como perdedores, encerrando a partida");
            System.out.println("Regra 10: Se por algum motivo você sair do jogo pois perdeu e tentar voltar ao jogo, terá que esperar em uma sala para aguardar futuros jogadores que vão estar nessa sala até completar um grupo de 3 jogadores para iniciar uma nova partida");
        	
                    
            servidor.receba(new PedidoParaEntrar());
            Comunicado comunicado = null;
            do
			{
				comunicado = (Comunicado)servidor.espie ();
			}
            
            /**quando o comunicado for uma aceitadora de conexão, o servidor envia uma aceitadora de conexao, e depois esse jogador tem que entrar no grupo a espera 
             * até formar um trio na sala para realmente jogar**/
            
            
			while (!(comunicado instanceof ComunicadoDeInicioDeJogo));
            
            
            ComunicadoDeInicioDeJogo aceitadoradeconexao = (ComunicadoDeInicioDeJogo) servidor.envie();
	            System.out.println("Jogador, a sua conexão com o nosso servidor foi aceita");
	            System.out.println("Bem-vindo ao menu, tecle a sua opcao:" + opcao);
	            System.out.println("Tecle 1 para digitar a letra!");
	            System.out.println("Tecle 2 para digitar a palavra!");
	            
	       /**Capturar um int,se o que o jogador digitar for diferente de 1,2 dispara erro de opção inválida**/     
	           try
	           {
	        	opcao = Teclado.getUmInt();   
	           }
	           
	           catch (Exception erro)
			    {
					System.err.println ("Opcao invalida!\n");
					continue;
				}
	           
            //*Se entrar na opção 1, o servidor recebe um pedido de letra e o comunicado começa nulo, entra no do de espiar o comunicado. *//
            try {           
            if(opcao == 1)
            {
            	System.out.println("Digite uma letra");
            char letra = Teclado.getUmChar();   
             servidor.receba(new PedidoDeLetra(letra));
             
             comunicado =null;
             do
             {
              comunicado = (Comunicado)servidor.espie();	 
             }
           /**Enquanto o comunicado não for uma instância de controlador de letra já digitada, ele fica
            no looping do while**/
             
             // como eu mostro o painel das letras já digitadas?
             
             
             // ao sair do looping, o vetor da letra é instanciado e declarado -- tem que ter na classe ControladorDeLetraJaDigitada!
             // os tracinhos são instanciados e declarados.
             // pede uma letra e posição da letra ao jogador
             // aparecerá os tracinhos com a palavra do jeito que estiver
             // depois de digitada, vê se a letra já foi digitada, registra, revela posição e letra
             // tem alguma variável que seja para todos os jogadores para revelar a letra?
             // depois de respondido, verifica se o jogador falou a última letra da palavra
             // se passou a última letra da palavra e acertou ,ele vence o jogo e os demais perdem
             // senão estiver no if de cima ele passa a vez ao próximo jogador em sentido horário.
             
             
             
             
             /**Quando o comunicado for instância de controlador de letras, eu declaro vários objetos: 
              * O estoque de letras como vetor, tracinhos, palavra. 
              * Peço para digitar uma letra e a posição. 
              * Peço o método tracinhos, verifico se a letra já foi digitada e registro (métodos da controladora de letras). 
              * Crio um vetor todo mundo de grupo para revelar a posição e a letra todos e que o objeto tracinhos também revele. 
              * Verifico se dentro da palavra sorteada tem a posição da letra e mostro os tracinhos faltantes, e depois se acertou ou errou. 
              * Se a posição tiver o mesmo comprimento, acertou for verdadeiro e realmente existir a posição da iezima ocorrencia 
              * (isso seria se acertasse a última letra da palavra), o jogador ativo ganharia a partida e os demais receberiam um comunicado de desligamento. 
              * *Senão, pularia para o próximo jogador a vez de jogar**/
             
             while (!(comunicado instanceof ComunicadoDeLetra));
            }
            }catch(Exception erro) {}
        
                       
            try {
            if(opcao == 2)
            {
            	System.out.println("Digite uma Palavra");
                servidor.receba(new PedidoDePalavra());
                comunicado = null;
                do
                {
                comunicado = (Comunicado)servidor.espie();	 
                }
                
                while (!(comunicado instanceof ComunicadoDePerda )&& !(comunicado instanceof ComunicadoDeVitoria)) ; 
	             
          
	        }
                }catch(Exception erro) {}     
 
        }
        while( "12".indexOf(opcao)!=-1);
 
	}
}
	                
    
          
            
   


