/*Classe Cliente = Lunara, acompanhar atualizações do servidor para arrumar as opções 1,2 e 3*/

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
            System.out.println("Instituição Estudantil: Cotuca/Unicamp");
            System.out.println("Curso: 59 - Técnico em Desenvolvimento de Sistemas Noturno");
            System.out.println("Disciplina: DS201 - Técnicas De Programação II");
            System.out.println("Professor: André de Carvalho");
            System.out.println("Esse jogo foi desenvolvido por:");
            System.out.println("RA 20668 - Elisângela Sanntos, RA 20669 - Eliseu Gilli");
            System.out.println("RA 21101 - Leandro de Freitas, RA 21106 - Lunara Cunha");
            System.out.println("Antes de passarmos ao menu, vamos te passar algumas regras desse jogo");
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
        	System.out.println("Bem-vindo ao menu, tecle a sua opcao:" + opcao);
            System.out.println("Tecle 1 para entrar no jogo");
            System.out.println("Tecle 2 para digitar a letra!");
            System.out.println("Tecle 3 para digitar a palavra!");
            
            
           try
           {
        	opcao = Teclado.getUmInt();   
           }
           
           catch (Exception erro)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
			}
            if ("1234".indexOf(opcao)==-1)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
			}
            //quando o jogador tecla 1, ele pede para entrar no jogo. O comunicado começa nulo 
            //e o servidor espia enquando o comunicado não for uma instância da aceitadora de conexão
            
            if (opcao =='1')
            {
            servidor.receba(new PedidoParaEntrar());
            Comunicado comunicado = null;
            do
			{
				comunicado = (Comunicado)servidor.espie ();
			}
            
            // quando o comunicado for uma aceitadora de conexão, o servidor envia uma aceitadora de
            // conexao, e depois esse jogador tem que entrar no grupo a espera de formar um trio na sala
            // para realmente jogar
			while (!(comunicado instanceof AceitadoraDeConexao));
             AceitadoraDeConexao aceitadoradeconexao = (AceitadoraDeConexao) servidor.envie();
            System.out.println("Jogador, a sua conexão com o nosso servidor foi aceita");
            
            }
            
                       
            if(opcao =='2')
            {
             servidor.receba(new PedidoDeLetra());
             Comunicado comunicado = null;
             do
             {
              comunicado = (Comunicado)servidor.espie();	 
             }
             // enquanto o comunicado não for uma instância de controlador de letra já digitada, ele fica
             // no looping do do:
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
             
             while (!(comunicado instanceof ControladorDeLetrasJaDgitadas));
             ControladorDeLetrasJaDigitadas EstoqueDeLetras[] <String> = new EstoqueDeLetras[];
             Tracinhos tracinhos = new Tracinhos;
             Palavra palavra = new palavra;
             // fazer método em tracinhos que mostre o vetor das letras já digitadas ANTES do jogador
             // dizer a letra, exceto se o vetor tiver vazio ele vai ser obrigado a dar uma letra de palpite
             System.out.println("Digite uma letra:" + letra);
             System.out.println("Diga uma posição que esteja a letra requerida:" + posicao);
             tracinhos.tracinhos();
             EstoqueDeLetras.isJaDigitada;
             EstoqueDeLetras.registre;
             // revelar para todo mundo:
             Grupo TodoMundo [] = new TodoMundo[];
              TodoMundo.revele[posicao, letra]; // revela para todos na sala
              tracinhos.revele(posicao,letra); // revela somente para o jogador ativo
              palavra.getPosicaoDaIezimaOcorrencia(i,letra);
             tracinhos.isAindaComTracinhos();
             AcertoOuErro acertoouerro = new AcertoOuErro;
             
            
             // se acertar a última letra da palavra vence
             if ((posicao == this.texto.length) && (acertou == true) && ((palavra.getPosicaoDaIezimaOcorrencia(i, letra))== (quantidadeDeOcorrencias != 0)))
             {
            	
            	 System.out.println("Jogador" + jogador[i] + "você ganhou e saiu do jogo! Parabéns!");
            	 System.out.println ("A palavra é:" + palavra);
                 servidor.receba(new PedidoParaSair());
                 // é aqui que coloca um comunicado de desligamento ou o servidor faz isso?
                 // encerrar a partida porque o jogador ativo venceu a partida.
                 ComunicadoDeDesligamento comunicadodedesligamento = (ComunicadoDeDesligamento) servidor.envie();
                 TodoMundo.comunicadodedesligamento();
              }
            
                    
             
             else if
             {
             jogador[i+1].VezDeJogar(); // depois que o jogador ativo já jogou e passou o resultado da letra
             // na palavra, obrigatoriamente temos que chamar o outro
             System.out.println ("É a vez do jogador ": + jogador[i+1] + "jogar!");
             }
             // independente de acertar ou errar, pelo fato de já ter jogado, passa a vez para o próximo
             
             
            }
            
            if(opcao == '3')
            {
            	// segue a mesma lógica da opção 3, trocando letra por palavra, porém se o jogador ativo errar a palavra
            	// ele sai do jogo e os outros dois ficam até o primeiro deles ganhar acertando a palavra
            
                servidor.receba(new PedidoDePalavra());
                Comunicado comunicado = null;
                do
                {
                comunicado = (Comunicado)servidor.espie();	 
                }
                
                while (!(comunicado instanceof ControladorDePalavrasJaDigitadas))
               ControladorDePalavrasJaDigitadas EstoqueDePalavras[] <String> = new EstoqueDePalavras[];
                Tracinhos tracinhos = new Tracinhos;
                Palavra palavra = new palavra;
                System.out.println("Digite uma palavra:" + palavra);
                tracinhos.tracinhos();
                EstoqueDePalavras.isJaDigitada;
                EstoqueDePalavras.registre;
                Grupo TodoMundo [] = new TodoMundo[];
                TodoMundo.revele[posicao, letra]; // revela para todos na sala
                tracinhos.revele(posicao,letra); // revela somente para o jogador ativo
                palavra.getPosicaoDaIezimaOcorrencia(i,letra);
               tracinhos.isAindaComTracinhos();
                AcertoOuErro acertoouerro = new AcertoOuErro;
               
               
                if ((palavra != palavra.getTamanho()) ||(acertou == false) ||((palavra.getPosicaoDaIezimaOcorrencia(i, letra))== (quantidadeDeOcorrencias == 0)))
                {
                	System.out.println ("Jogador" + jogador[i] + "errou! Você saiu do jogo!");
                	servidor.receba(new PedidoParaSair());
                 ComunicadoDeDesligamento comunicadodedesligamento = (ComunicadoDeDesligamento) servidor.envie();
                 jogador[i+1].VezDeJogar();
                 System.out.println ("É a vez do jogador ": + jogador[i+1] + "jogar!");
                               	 
                }
               
                                             
               else if((palavra == palavra.getTamanho()) && (acertou ==true) && ((palavra.getPosicaoDaIezimaOcorrencia(i, letra))== (quantidadeDeOcorrencias != 0)))
                	            
                {
                	System.out.println("Jogador" + jogador[i] + "você ganhou! Parabéns!");
                	 System.out.println ("A palavra é:" + palavra);
                     servidor.receba(new PedidoParaSair());
                     // é aqui que coloca um comunicado de desligamento ou o servidor faz isso?
                     // encerrar a partida porque o jogador ativo venceu a partida.
                     ComunicadoDeDesligamento comunicadodedesligamento = (ComunicadoDeDesligamento) servidor.envie();
                     TodoMundo.comunicadodedesligamento();
      
                }
                              
                
            }// fecha a opção 3
            
        }// fecha a lista das 3 opções no do, a linha 152 
                                 
           }// fecha a main
           
           }// fecha a classe Cliente
            