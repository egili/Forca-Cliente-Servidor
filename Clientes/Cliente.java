/*Classe Cliente = Lunara, acompanhar atualiza��es do servidor para arrumar as op��es 1,2 e 3*/

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Cliente
{
	public static final String HOST_PADRAO  = "localhost";
	public static final int    PORTA_PADRAO = 3000;
		
	public static void main (String[] args)
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
		catch (Exception erro)
		{} // sei que servidor foi instanciado
		
        tratadoraDeComunicadoDeDesligamento.start();

        int opcao=' ';
        do
        {
            System.out.println("Bem-vindo(a) ao jogo forca servidor! - Trabalho final de java");
            System.out.println("Institui��o Estudantil: Cotuca/Unicamp");
            System.out.println("Curso: 59 - T�cnico em Desenvolvimento de Sistemas Noturno");
            System.out.println("Disciplina: DS201 - T�cnicas De Programa��o II");
            System.out.println("Professor: Andr� de Carvalho");
            System.out.println("Esse jogo foi desenvolvido por:");
            System.out.println("RA 20668 - Elis�ngela Sanntos, RA 20669 - Eliseu Gilli");
            System.out.println("RA 21101 - Leandro de Freitas, RA 21106 - Lunara Cunha");
            System.out.println("Antes de passarmos ao menu, vamos te passar algumas regras desse jogo");
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
        	System.out.println("Bem-vindo ao menu, tecle a sua opcao:" + opcao);
            System.out.println("Tecle 1 para entrar no jogo");
            System.out.println("Tecle 2 para digitar a letra!");
            System.out.println("Tecle 3 para digitar a palavra!");
            
       /**Capturar um int,se o que o jogador digitar for diferente de 1,2,3 dispara erro de op��o inv�lida**/     
           try
           {
        	opcao = Teclado.getUmInt();   
           }
           
           catch (Exception erro)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
			}
            if ("123".indexOf(opcao)==-1)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
			}
            /**Se entra na op��o 1, o servidor recebe um pedido para entrar e o comunicado come�a nulo, depois entra num do para espiar o comunicado. 
             * Quando o comunicado for inst�ncia da aceitadora de conex�o, ele vai enviar um objeto de aceitadora para a conex�o e o jogador recebe a mensagem de conex�o aceita*/
            
            if (opcao =='1')
            {
            servidor.receba(new PedidoParaEntrar());
            Comunicado comunicado = null;
            do
			{
				comunicado = (Comunicado)servidor.espie ();
			}
            
            /**quando o comunicado for uma aceitadora de conex�o, o servidor envia uma aceitadora de conexao, e depois esse jogador tem que entrar no grupo a espera 
             * at� formar um trio na sala para realmente jogar**/
            
            
            while (!(comunicado instanceof ComunicadoSalaCheia));
            comunicado = servidor.envie();
            if (comunicado instanceof ComunicadoSalaCheia)
            	System.out.println ("Sala cheia");
             
            
            
            }
            //*Se entrar na op��o 2, o servidor recebe um pedido de letra e o comunicado come�a nulo, entra no do de espiar o comunicado. *//
                       
            if(opcao =='2')
            {
             servidor.receba(new PedidoDeLetra()); //a classe pedido de letra tem um erro
             Comunicado comunicado = null;
             do
             {
              comunicado = (Comunicado)servidor.espie();	 
             }
           /**Enquanto o comunicado n�o for uma inst�ncia de controlador de letra j� digitada, ele fica
            no looping do while**/
             
             // como eu mostro o painel das letras j� digitadas?
             
             
             // ao sair do looping, o vetor da letra � instanciado e declarado -- tem que ter na classe ControladorDeLetraJaDigitada!
             // os tracinhos s�o instanciados e declarados.
             // pede uma letra e posi��o da letra ao jogador
             // aparecer� os tracinhos com a palavra do jeito que estiver
             // depois de digitada, v� se a letra j� foi digitada, registra, revela posi��o e letra
             // tem alguma vari�vel que seja para todos os jogadores para revelar a letra?
             // depois de respondido, verifica se o jogador falou a �ltima letra da palavra
             // se passou a �ltima letra da palavra e acertou ,ele vence o jogo e os demais perdem
             // sen�o estiver no if de cima ele passa a vez ao pr�ximo jogador em sentido hor�rio.
             
             
             
             
             /**Quando o comunicado for inst�ncia de controlador de letras, eu declaro v�rios objetos: 
              * O estoque de letras como vetor, tracinhos, palavra. 
              * Pe�o para digitar uma letra e a posi��o. 
              * Pe�o o m�todo tracinhos, verifico se a letra j� foi digitada e registro (m�todos da controladora de letras). 
              * Crio um vetor todo mundo de grupo para revelar a posi��o e a letra todos e que o objeto tracinhos tamb�m revele. 
              * Verifico se dentro da palavra sorteada tem a posi��o da letra e mostro os tracinhos faltantes, e depois se acertou ou errou. 
              * Se a posi��o tiver o mesmo comprimento, acertou for verdadeiro e realmente existir a posi��o da iezima ocorrencia 
              * (isso seria se acertasse a �ltima letra da palavra), o jogador ativo ganharia a partida e os demais receberiam um comunicado de desligamento. 
              * *Sen�o, pularia para o pr�ximo jogador a vez de jogar**/
             
             while (!(comunicado instanceof ControladorDeLetrasJaDigitadas));
             ControladorDeLetrasJaDigitadas EstoqueDeLetras[] <String> = new EstoqueDeLetras[];
             Tracinhos tracinhos = new Tracinhos();
             Palavra palavra = new palavra();
             
             // fazer m�todo em tracinhos que mostre o vetor das letras j� digitadas ANTES do jogador
             // dizer a letra, exceto se o vetor tiver vazio ele vai ser obrigado a dar uma letra de palpite
             
             System.out.println("Digite uma letra:" + letra);
             System.out.println("Diga uma posi��o que esteja a letra requerida:" + posicao);
             tracinhos.tracinhos();
             EstoqueDeLetras.isJaDigitada();
             EstoqueDeLetras.registre();
             // revelar para todo mundo:
             Grupo TodoMundo [] = new TodoMundo[];
              TodoMundo.revele[posicao, letra]; // revela para todos na sala
              tracinhos.revele(posicao,letra); // revela somente para o jogador ativo
              palavra.getPosicaoDaIezimaOcorrencia(i,letra);
             tracinhos.isAindaComTracinhos();
             AcertoOuErro acertoouerro = new AcertoOuErro();
             
            
             // se acertar a �ltima letra da palavra vence
             if ((posicao == this.texto.length) && (acertou == true) && ((palavra.getPosicaoDaIezimaOcorrencia(i, letra))== (quantidadeDeOcorrencias != 0)))
             {
            	
            	 System.out.println("Jogador" + jogador[i] + "voc� ganhou e saiu do jogo! Parab�ns!");
            	 System.out.println ("A palavra �:" + palavra);
                 servidor.receba(new PedidoParaSair());
                 // � aqui que coloca um comunicado de desligamento ou o servidor faz isso?
                 // encerrar a partida porque o jogador ativo venceu a partida.
                 ComunicadoDeDesligamento comunicadodedesligamento = (ComunicadoDeDesligamento) servidor.envie();
                 TodoMundo.comunicadodedesligamento();
              }
            
                    
             
             else
             {
             jogador[i+1].VezDeJogar(); // depois que o jogador ativo j� jogou e passou o resultado da letra
             // na palavra, obrigatoriamente temos que chamar o outro
             System.out.println ("� a vez do jogador " + jogador[i+1] + "jogar!");
             }
             // independente de acertar ou errar, pelo fato de j� ter jogado, passa a vez para o pr�ximo
             
             
            }
            
            
            /**Na op��o 3 seria a mesma l�gica da op��o 2 depois do while, quando montei estava sem acesso a supervisora e a classe grupo que 
            agora se chama ControladoraDePartida, por isso a escrita das 3 op��es tem que ser revisadas com mais calma
            -->trocando letra por palavra, por�m se o jogador ativo errar a palavra ele sai do jogo e os outros dois ficam at� o primeiro deles ganhar acertando a palavra**/
            
            if(opcao == '3')
            {
                servidor.receba(new PedidoDePalavra());
                Comunicado comunicado = null;
                do
                {
                comunicado = (Comunicado)servidor.espie();	 
                }
                
                while (!(comunicado instanceof ControladorDePalavrasJaDigitadas)) {
                	
	               ControladorDePalavrasJaDigitadas EstoqueDePalavras[] <String> = new EstoqueDePalavras[];
	                Tracinhos tracinhos = new Tracinhos();
	                Palavra palavra = new palavra();
	                System.out.println("Digite uma palavra:" + palavra);
	                tracinhos.tracinhos();
	                EstoqueDePalavras.isJaDigitada();
	                EstoqueDePalavras.registre();
	                Grupo TodoMundo [] = new TodoMundo[];
	                TodoMundo.revele[posicao, letra]; // revela para todos na sala
	                tracinhos.revele(posicao,letra); // revela somente para o jogador ativo
	                palavra.getPosicaoDaIezimaOcorrencia(i,letra);
	               tracinhos.isAindaComTracinhos();
	                AcertoOuErro acertoouerro = new AcertoOuErro();
               
               
                if ((palavra != palavra.getTamanho()) ||(acertou == false) ||((palavra.getPosicaoDaIezimaOcorrencia(i, letra))== (quantidadeDeOcorrencias == 0)))
                {
                	System.out.println ("Jogador" + jogador[i] + "errou! Voc� saiu do jogo!");
                	servidor.receba(new PedidoParaSair());
                 ComunicadoDeDesligamento comunicadodedesligamento = (ComunicadoDeDesligamento) servidor.envie();
                 jogador[i+1].VezDeJogar();
                 System.out.println ("� a vez do jogador ", + jogador[i+1] + "jogar!");
                               	 
                }
               
                                             
               else if((palavra == palavra.getTamanho()) && (acertou ==true) && ((palavra.getPosicaoDaIezimaOcorrencia(i, letra))== (quantidadeDeOcorrencias != 0)))
                	            
                {
                	System.out.println("Jogador" + jogador[i] + "voc� ganhou! Parab�ns!");
                	 System.out.println ("A palavra �:" + palavra);
                     servidor.receba(new PedidoParaSair());
                     // � aqui que coloca um comunicado de desligamento ou o servidor faz isso?
                     // encerrar a partida porque o jogador ativo venceu a partida.
                     ComunicadoDeDesligamento comunicadodedesligamento = (ComunicadoDeDesligamento) servidor.envie();
                     TodoMundo.comunicadodedesligamento();
      
                }
                              
                
            }//end op��o 3
            
        }//end lista das 3 op��es no do, a linha 152 
                                 
           }//end main
           
           }// end Cliente
            