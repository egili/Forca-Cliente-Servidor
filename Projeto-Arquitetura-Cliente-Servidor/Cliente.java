

//import ClassesComuns.*;
//import Servidor.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.ArrayList;

public class Cliente {
	public static final String HOST_PADRAO = "localhost";
	public static final int PORTA_PADRAO = 3000;
	public static ArrayList<Parceiro> usuarios;
	public static BancoDePalavras bancoDePalavras;
	public static ControladorDeErros controladorDeErros;
	public static ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
	private static ControladorDePalavrasJaDigitadas controladordePalavrasJaDigitadas;
	public static ControladoraDePartida controladoraDePartida;
	public static Grupo<Cliente> grupo;
   	public static Palavra palavra;
	public static SupervisoraDeConexao supervisora;
	public static Tracinhos tracinhos;
	public static TratadoraDeComunicados comunicadoDeDesligamento;
    
	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {

		System.out.println("Bem-vindo(a) ao jogo forca servidor! - Trabalho final de java");
		System.out.println("Instituicao Estudantil: Cotuca/Unicamp");
		System.out.println("Curso: 59 - Tecnico em Desenvolvimento de Sistemas Noturno");
		System.out.println("Disciplina: DS201 - Tecnicas De Programacaoo II");
		System.out.println("Professor: Andre de Carvalho");
		System.out.println("Esse jogo foi desenvolvido por:");
		System.out.println("RA 20668 - Elisangela Sanntos, RA 20669 - Eliseu Gili");
		System.out.println("RA 21101 - Leandro de Freitas, RA 21106 - Lunara Cunha");
		System.out.println("Antes de passarmos ao menu, vamos te passar algumas regras desse jogo ");
        System.out.println ("\n------------------------------------------------------------------------------\n");	
		System.out.println("Regra 1: Voce pode escolher digitar uma letra ou palavra quando for a sua vez de jogar na partida");
		System.out.println("Regra 2: Voce tera uma palavra a ser advinhada por partida para o grupo de 3 jogadores");
		System.out.println("Regra 3: Sempre tera um ganhador e dois perdedores por partida");
		System.out.println("Regra 4: Voce pode escolher uma palavra a qualquer momento da partida");
		System.out.println("Regra 5: Se voce digitar a palavra e errar; seu jogo acaba,voce termina como perdedor, e os outros dois jogadores continuam na sala ate um dos dois advinharem a palavra sorteada");
		System.out.println("Regra 6: Se voce digitar a palavra e acertar, seu jogo acaba, voce termina como ganhador e os outros dois jogadores ficam como perdedores");
		System.out.println("Regra 7: Se voce digitar uma letra e errar, passa a vez para o proximo jogador em sentido horario na sala, ate que um dos 3 acerte a palavra sorteada ");
		System.out.println("Regra 8: Se voce digitar a ultima letra para a palavra e acertar, voce termina o jogo como ganhador e os demais ficam como perdedores, encerrando a partida");
		System.out.println("Regra 9: Se por algum motivo voce sair do jogo pois perdeu e tentar voltar ao jogo, tera que esperar em uma sala para aguardar futuros jogadores que vao estar nessa sala ate completar um grupo de 3 jogadores para iniciar uma nova partida");
		System.out.println("Jogador, a sua conexao com o nosso servidor foi aceita");
	
		if (args.length > 2) {
			System.err.println("Uso esperado: java Cliente [HOST [PORTA]]\n");
			return;
		}

		// Declarando os objetos que serao instanciados

		Socket conexao = null;
		ObjectOutputStream transmissor = null;
		ObjectInputStream receptor = null;
		Parceiro servidor = null;
		Comunicado comunicado = null;
		ComunicadoComecouPartida iniciadordepartida = null;
		ComunicadoDeAcerto acertou = null;
		ComunicadoDeDados dadosdapartida = null;
		ComunicadoDeDesligamento desligoujogador = null;
		ComunicadoDeErro errou = null;
		ComunicadoDeLetra letradojogador = null;
		ComunicadoDeLetraJaDigitada letraquejadigitou = null;
		ComunicadoDePerda perdeu = null;
		ComunicadoDeVez suavez = null;
		ComunicadoDeVitoria venceu = null;
		ComunicadoSalaCheia salacheia = null;
		ComunicadoTracinhos pediutracinhos = null;
		ComunicadoDeResultadoPalavra resultadodepalavra = null;
		TratadoraDeComunicados tratadora = null;
        		//ArrayList<Parceiro> grupojogador = controladoraDePartida.getJogadores();  
		char letra = Teclado.getUmChar();
		//int quantasletras = palavra.getQuantidade(letra);
		int posicaodaletra = Teclado.getUmInt();
		int qtd = palavra.getPosicaoDaIezimaOcorrencia(posicaodaletra,letra);
		try {
			conexao = Instanciacao.instanciarConexao(args);
			transmissor = Instanciacao.instanciarTransmissor(conexao);
			receptor = Instanciacao.instanciarReceptor(conexao);
			servidor = Instanciacao.instanciarServidor(conexao, receptor, transmissor);
			tratadora = Instanciacao.instanciarTratadoraDeComunicados(servidor);
			AceitadoraDeConexao aceitadora = new AceitadoraDeConexao((Integer.toString(PORTA_PADRAO)), usuarios); 
			if (comunicado instanceof PedidoParaEntrar)
				servidor.receba (new PedidoParaEntrar());
			do
			{
				comunicado = (Comunicado)servidor.espie ();
			}
			
			while(!(comunicado instanceof ComunicadoSalaCheia));
	        comunicado = servidor.envie();
	        if(comunicado instanceof ComunicadoSalaCheia)
		   System.out.println("Sala cheia");
	        else
	        System.out.println("Aguarde os demais jogares entrarem na partida");
        
		
			    
			
		} catch (Exception err) {
			System.err.println(err.getMessage());
			System.err.println("Verifique se o servidor esta ativo.\n"
					+ "Se sim, verifique se o servidor e a porta provido estao corretos!\n");
			System.exit(0);
		}

		// Aguarde os usuarios entrarem
		
	
		int opcao = ' ';

		while (!(comunicado instanceof ComunicadoDeVez)); {
			System.out.println("Jogador, a sua conexao com o nosso servidor foi aceita");
			System.out.println("Bem-vindo ao menu, tecle a sua opcao:" + opcao);
			System.out.println("Tecle 1 para digitar a letra!");
			System.out.println("Tecle 2 para digitar a palavra!");

			/**
			 * Capturar um int,se o que o jogador digitar for diferente de 1,2 dispara erro
			 * de opcao invalida;
			 **/
			do {
				try {
					opcao = Teclado.getUmInt();
				}

				catch (Exception erro) {
					System.err.println("Opcao invalida!\n");
					continue;
					
				}
					
			}
			while (opcao != 1  || opcao !=2 );
			
			// *Se entrar na opcao 1, o servidor recebe um pedido de letra e o comunicado
			// comeca nulo, entra no do de espiar o comunicado. *//
						/** 1 - Pegar a palavra digitada e verificar se já foi digitada antes---Se sim, informa que já foi digitada e solicita nova palavra
		     2 - Confirma se a palavra digitada é a mesma da sorteada --- se sim, preenche os tracinhos e informa que venceu a jogada e encerra o jogo
		     Senão, informa que errou, tira o jogador da partida e segue o jogo com o proximo jogador
		**/
			try {
				if (opcao == 1) {
					System.out.println("Digite uma letra" + letra);
					PedidoDeLetra pedidodeletra = new PedidoDeLetra();
					servidor.receba(pedidodeletra);

					comunicado = null;
					do {
						comunicado = (Comunicado) servidor.espie();
					} while (!(comunicado instanceof PedidoDeLetra));
					if(comunicado instanceof ComunicadoComecouPartida)
					{
						Palavra palavra =BancoDePalavras.getPalavraSorteada();
						Tracinhos tracinhos = null;
						ComunicadoDeDados comunicadodedados = null;
						Palavra copiapalavra = palavra;
						String letrasJaDigitadas = null;
								try
								{
								    tracinhos = new Tracinhos (palavra.getTamanho());
			     				}
								
                             catch(Exception error){}
				         {
				         if((controladorDeLetrasJaDigitadas.isJaDigitada(letra) == true) &&(comunicado instanceof ComunicadoDeLetraJaDigitada))
				        	System.out.println("A letra" + letra + "ja foi digitada");
				         comunicado = (ComunicadoDeLetraJaDigitada)servidor.envie();
				         copiapalavra.getQuantidade(letra);
				         System.out.println(qtd);
				         controladorDeLetrasJaDigitadas.registreletra(letra);
				         
				     	for (int i=0; i<qtd; i++)
						{
							int posicao = copiapalavra.getPosicaoDaIezimaOcorrencia (i,letra);
							tracinhos.revele (posicao, letra);
						}

				         int i = 0 ;

						if((copiapalavra.getQuantidade(letra) > 0) && ((copiapalavra.getPosicaoDaIezimaOcorrencia (i,letra)>0)))
				         {
				           do
				           { 
				        	   comunicado = (Comunicado) servidor.espie();
				           }
				           while (!(comunicado instanceof ComunicadoDeAcerto));
				        	   if ((comunicado instanceof ComunicadoDeDados) && (comunicado instanceof ComunicadoDeVez))
				        		   controladoraDePartida.getJogadores();
				                    
				               comunicado = (ComunicadoDeAcerto) servidor.envie();
				               System.out.println("Jogador" + suavez.getposicaodoJogador() + "acertou a letra!");
				               comunicado = (ComunicadoDeDados) servidor.envie();
				               System.out.println(controladoraDePartida.getJogadores());
         				       System.out.println(comunicadodedados.getTracinhos());
         				       tracinhos.revele(opcao,letra);
				               System.out.println(tracinhos.isAindaComTracinhos());
				               System.out.println("Palavra:" + tracinhos);
				               System.out.println("Digitadas.: "  + letrasJaDigitadas.toString());
				               comunicado = (ComunicadoDeVez) servidor.envie();
       			               System.out.println("Eh a vez do proximo jogador");
       			               controladoraDePartida.toString();
       			               controladoraDePartida.proximoJogador();
				         }
						// Quando o jogador ativo completa a palavra por acertar a letra ele ganha (comunicado de vitoria)
						//e os demais perdem (comunicados de perda);
	                  if ((copiapalavra.getQuantidade(letra)>0) && (copiapalavra.getPosicaoDaIezimaOcorrencia(i, letra) > 0 ) 
	    		        && (tracinhos.isAindaComTracinhos() == false))
	                   {
		                do
                       { 
      	               comunicado = (Comunicado) servidor.espie();
                       }
                       while (comunicado instanceof ComunicadoDeAcerto);
      	               if ((comunicado instanceof ComunicadoDeDados) && (comunicado instanceof ComunicadoDeVitoria))
	                   controladoraDePartida.getJogadores();
		                   
		       			          comunicado = (ComunicadoDeAcerto) servidor.envie();
                      System.out.println("Jogador" + suavez.getposicaodoJogador() + "acertou a letra!");
                      comunicado = (ComunicadoDeDados) servidor.envie();
                      System.out.println(controladoraDePartida.getJogadores());
		              System.out.println(comunicadodedados.getTracinhos());
		              tracinhos.revele(opcao,letra);
                      System.out.println(tracinhos.isAindaComTracinhos());
                      System.out.println("Palavra:" + tracinhos);
                      System.out.println("Digitadas.: "  + letrasJaDigitadas.toString());
                      comunicado = (ComunicadoDeVitoria) servidor.envie();
                      System.out.println("Demais jogadores saem do jogo");
                      comunicado = (ComunicadoDePerda) servidor.envie();
	                  controladoraDePartida.podeJogar(servidor);
	                  grupo.getJogadorDaVez();
	                  controladoraDePartida.fimThreadSupervisora();
	                  comunicado = (ComunicadoDeDesligamento) servidor.envie();
	                  controladoraDePartida.toString();
	                  controladoraDePartida.getJogadores();
	           }     
	         
						else if((copiapalavra.getQuantidade(letra) == 0) && ((copiapalavra.getPosicaoDaIezimaOcorrencia (i,letra) == 0)))
						  
					           do
					           { 
					        	   comunicado = (Comunicado) servidor.espie();
					           }
					           while (comunicado instanceof ComunicadoDeErro);
					        	   if ((comunicado instanceof ComunicadoDeDados) && (comunicado instanceof ComunicadoDeVez))
						                  controladoraDePartida.getJogadores();
					                      
					               comunicado = (ComunicadoDeErro) servidor.envie();
					               System.out.println("Jogador" + suavez.getposicaodoJogador() + "errou a letra!");
					               comunicado = (ComunicadoDeDados) servidor.envie();
					               System.out.println(controladoraDePartida.getJogadores());
					               System.out.println(comunicadodedados.getTracinhos());
	         				       tracinhos.revele(opcao,letra);
					               System.out.println(tracinhos.isAindaComTracinhos());
					               System.out.println("Palavra:" + tracinhos);
					               System.out.println("Digitadas.: "  + letrasJaDigitadas.toString());
					               comunicado = (ComunicadoDeVez) servidor.envie();
	       			               System.out.println("Eh a vez do proximo jogador");
	       			               controladoraDePartida.toString();
	       			               controladoraDePartida.proximoJogador();
	       			               
	       			               
       			         }

						
				         
				}
			}
			}
			
			catch (Exception erro) {
			}

			
			if (opcao == 2)
			{
				
			    String chutepalavra = Teclado.getUmString();
			    
				System.out.println("Digite uma Palavra" + chutepalavra);
				
				chutepalavra = Teclado.getUmString();
				
				servidor.receba(new PedidoDePalavra());
				
				comunicado = null;
				do {
					comunicado = (Comunicado) servidor.espie();
				}

				while (!(comunicado instanceof ComunicadoDeResultadoPalavra));

					if((controladordePalavrasJaDigitadas.isJaDigitada(chutepalavra) == true) &&(comunicado instanceof ComunicadoDePalavraJaDigitada))
						
						
						 //verificar se tem um equals para comparar a palavra digitada com a palavra informada 
						
						System.out.println("A palavra: " + chutepalavra + "ja foi tentada antes, tente novamente, por favor");
					 comunicado = (ComunicadoDePalavraJaDigitada)servidor.envie();
					

				
				if ((comunicado instanceof ComunicadoDePerda) && (comunicado instanceof ComunicadoDeVitoria))
				{
					controladoraDePartida.getJogadores();
				
					
					
					if (chutepalavra.equals(palavra))
					comunicado = (ComunicadoDeAcerto) servidor.envie();
					
					
		               System.out.println("Jogador" + suavez.getposicaodoJogador()  + "acertou a Palavra!");
		               comunicado = (ComunicadoDeDados) servidor.envie();
		               
		               
		               try
						{
							servidor.receba(new PedidoParaSair());
						}
						catch (Exception error) {}

						System.exit(0);
		               
		            
					
				}
				
				else if(!chutepalavra.equals(palavra))
				  {
										
			           do
			           { 
			        	   comunicado = (Comunicado) servidor.espie();
			           }
			           while (!(comunicado instanceof ComunicadoDeErro));
			        	   if ((comunicado instanceof ComunicadoDeDados) && (comunicado instanceof ComunicadoDeVez))
				                  controladoraDePartida.getJogadores();
	                                                  
			               comunicado = (ComunicadoDeErro) servidor.envie();
			               System.out.println("Jogador" + suavez.getposicaodoJogador() + "errou a palavra!");
			               
			               String palavraJaDigitada = chutepalavra; //fazer na classe pedido de palavra para armazenar a palavra ja digitada e depois exibir igual as letras
			               
			               
			               // errou a palavra e saiu do jogo
			               
			               System.out.println("Você errou a palavra e está fora da partida.");
			               comunicado = (ComunicadoDePerda) servidor.envie();
			               
			               comunicado = (ComunicadoDeVez) servidor.envie();
			               comunicado = (ComunicadoDeDados) servidor.envie();
			               controladoraDePartida.proximoJogador();
		                
			               try
							{
								servidor.receba(new PedidoParaSair());
							}
							catch (Exception error) {}
			               
			               

							System.exit(0);
			              
			               
		         }

			}//}catch (Exception erro) {}

		
		
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
				} while (!(comunicado instanceof ComunicadoComecouPartida));

				try {
					suavez = (ComunicadoDeVez) servidor.envie();
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

					while (!(comunicado instanceof ComunicadoComecouPartida));

					// Usuario recebe sua vez de volta
					suavez = (ComunicadoDeVez) servidor.envie();
					System.out.println(suavez);

				} while ("12".indexOf(opcao) != -1);

			}
			System.out.println("Obrigado por usar esse programa !!");
			System.exit(0);
		}

	}// end da main

	}
} //end da classe
		