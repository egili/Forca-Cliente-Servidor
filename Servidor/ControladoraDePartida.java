import java.net.Socket;
import java.util.Random;


/*
 essa classe representa uma estrutra de dados que, a cada vez que um jogador tenta se conectar, 
 eh armazenado num objeto dela e, será direcionado para a partida quando o grupo possuir 
 3 jogadores (que sao objetos da futura classe Cliente)
 uma unica thread a cada grupo de 3 clientes!
 
 passo a passo:
	 1.cliente tenta se conectar 
	 2. aparece a mensagem "aguardando outros jogadores"
	 3. starta a aceitadora de conexao (lembrando que uma aceitadora para varias supervisoras (1 - N))
 
 como deve funcionar a classe:
 	1.cada vez que a aceitadora de conexao for startada, o ciente que tentou se conectar será armazenado no grupo (estrutura de dados)
 	2.o primeiro jogador a entrar no grupo eh o primeiro a jogar (principio de FILA)
 	3.aceitadora de conexao instancia um objeto da classe Grupo -> chama um método que adiciona(push?) o cliente naquele grupo
 	4. método pop(?) acontece quando o cliente perde a partida -> excluido do grupo, logo, excluido da partida
 	5.cada vez que uma aceitadora startar uma supervisora, ela passará a estrutura como parametro para o construtor
 
 obs: cliente = jogador
 */

public class ControladoraDePartida {

	private static Grupo<Cliente> grupo;
    private static char letra;
    private static Socket conexao;
    private static BancoDePalavras bancodepalavras;
    private static Palavra palavra;
    private static ControladoraDePartida controladoradepartida;
    
	public ControladoraDePartida(Cliente[] jogadores) throws Exception {
		
		if (jogadores == null)
			throw new Exception ("Jogador nao pode ser nulo!");
		if (!grupo.isCheio())
			throw new Exception ("Grupo precisa ter 3 jogadores!");
		
		this.grupo = new Grupo<Cliente>(jogadores);
	}
	
	// Em que momento será colocado no cliente que a partida começou porque temos 3 jogadores?

	public static void vezDeJogar() throws Exception {
		
		Cliente jogadorDaVez = grupo.getJogadorDaVez();
		
		grupo.removerJogadorDoGrupo();
		Comunicado comunicado = null;
		int numJogador = 0;
		do
		{
		grupo.removerJogadorDoGrupo(); 
		numJogador ++;
		grupo.inserirJogadorNoGrupo(jogadorDaVez);
		
			try
			{
				comunicado = new Comunicado();
			}
			catch (Exception erro) {}
		}
		while ((!(comunicado instanceof ComunicadoDeVitoria));)
		
		/* a classe Grupo ja possibilita que ordem de jogada seja como numa fila,
		   a logica aqui vai ser remover o jogador do Grupo e depois inseri-lo novamente,
		   isso pois, o jogador removido eh sempre o primeiro do Grupo e sempre sera inserido 
		   como  ultimo do grupo
		   
		   TODO: a ideia deste metodo eh fazer com que o jogador saiba que eh sua vez de jogar e que ele possa ser unico a jogar */
	}

	public static ComunicadoDeAcerto acertar() throws Exception
	{
		return new ComunicadoDeAcerto();
	}
	
	// metodo para quando o cliente acertar a letra retorna que acertou a letra;
	
	public static String acertarletra() throws Exception
	{
	    PedidoDeLetra pedidodeletra = new PedidoDeLetra (conexao, letra);
	    Palavra palavrasorteada = null;
	    try
	    {
	    palavrasorteada = bancodepalavras.getPalavraSorteada();
	    }
	    catch (Exception erro) {}
        Palavra copiapalavra;
        copiapalavra = palavrasorteada;
        int quantidade = copiapalavra.getTamanho();
	    String letrasAtrasDoPainel[] = new String [quantidade];
	    
	    int contemLetra = copiapalavra.getQuantidade(letra);
	    
	    int posicaoDaLetra = copiapalavra.getPosicaoDaIezimaOcorrencia(contemLetra, letra);
	    
	   
	    if (contemLetra>0)
	    {
	    	for (posicaoDaLetra = 0; posicaoDaLetra<letrasAtrasDoPainel.length ; posicaoDaLetra++)
	    	letrasAtrasDoPainel[contemLetra] = letrasAtrasDoPainel[posicaoDaLetra]; 	
	    	letrasAtrasDoPainel[posicaoDaLetra] = letrasAtrasDoPainel[letra];
	    	
	    }
		
	    return "Voce acertou a letra!";
	}
    
	// metodo para quando o cliente acertar o �ltimo tipo de letra que complete a palavra 
	// a ser advinhada
	public static String completarpalavra () throws Exception
	{
	 String palavra1 = "Axioma";
	 String palavra2 = "Azulejo";
	 String palavra3 = "Caleidoscopio";
	 String palavra4 = "Catarro";
	 String palavra5 = "Cocar";
	 String palavra6 = "Crespo";
	 String palavra7 = "Cripta";
	 String palavra8 = "Duplex";
	 String palavra9 = "Ictericia";
	 String palavra10 = "Inconstitucionalissimamente";
	 String palavra11 = "Intrigante";
	 String palavra12 = "Ornitorrinco";
	 String palavra13 = "Otorrinolaringologista";
	 String palavra14 = "Paralelepipedo";
	 String palavra15 = "Proparoxitona";
	 String palavra16 = "Topazio";
	 String palavra17 = "Vertiginoso";
	 String palavra18 = "Xilofone";
			 
	 String[] palavras = {palavra1, palavra2, palavra3, palavra4, palavra5, palavra6, palavra7, palavra8, palavra9, palavra10, palavra11, palavra12, palavra13, palavra14, palavra15, palavra16, palavra17, palavra18}
      	
	  String[] tipodeletrap1 = {"a", "x", "i", "o", "m"};
	  String[] tipodeletrap2 = {"a", "z", "u", "l", "e", "j", "o"};
	  String[] tipodeletrap3 = {"c", "a", "l", "e", "i", "d", "o", "s", "p"};
	  String[] tipodeletrap4 = {"c", "a", "t", "r", "o"};
	  String[] tipodeletrap5 = {"c", "o", "a", "r"};
	  String[] tipodeletrap6 = {"c", "r", "e", "s", "p", "o"};
	  String[] tipodeletrap7 = {"c", "r", "i", "p", "t", "a"};
	  String[] tipodeletrap8 = {"d", "u", "p", "l", "e", "x"};
	  String[] tipodeletrap9 = {"i", "c", "t", "e", "r", "a"};
	  String[] tipodeletrap10 = {"i", "n", "c", "o", "s", "t","u", "a", "l", "m", "e"};
	  String[] tipodeletrap11 = {"i", "n", "t", "r", "g", "a", "e"};
	  String[] tipodeletrap12 = {"o", "r", "n", "i", "t", "c"};
	  String[] tipodeletrap13 = {"o", "t", "r", "i", "n", "l", "a", "g", "s"};
	  String[] tipodeletrap14 = {"p", "a", "r", "l", "e", "i", "d", "o" };
	  String[] tipodeletrap15 = {"p", "r", "o", "a", "r", "x", "i", "t", "n"};
	  String[] tipodeletrap16 = {"t", "o", "p","a", "z","i"};
	  String[] tipodeletrap17 = {"v", "e", "r", "t", "i", "g", "n", "o", "s"};
	  String[] tipodeletrap18 = {"x", "i", "l", "o", "f", "n", "e"};
	  
	  String[] tipodeletraN = new String[18];
	  tipodeletraN = {tipodeletrap1, tipodeletrap2, tipodeletrap3, tipodeletrap4, tipodeletrap5, tipodeletrap6, tipodeletrap7, tipodeletrap8, tipodeletrap9, tipodeletrap10, tipodeletrap11, tipodeletrap12, tipodeletrap13, tipodeletrap14, tipodeletrap15, tipodeletrap16, tipodeletrap17, tipodeletrap18};
	  
	  PedidoDeLetra pedidodeletra = new PedidoDeLetra (conexao, letra);
	   
	    try
	    {}
	    catch (Exception erro) {}
	      Random aleatorio = new Random();
	      int sorteio = aleatorio.nextInt(18) + 1;
	      String palavradesorteio = palavras[sorteio];
	      String tipopalavrasorteio = tipodeletraN[sorteio];
	      Palavra palavra = new Palavra (palavradesorteio);
	      Palavra palavracont = new Palavra (tipopalavrasorteio);
	      Palavra copiapalavracont = palavracont;
	      Palavra copiapalavra = palavra;
         int quantidade = copiapalavra.getTamanho();
	      String letrasAtrasDoPainel[] = new String [quantidade];
	    
	    int contemLetra = copiapalavra.getQuantidade(letra);
	    
	    int posicaoDaLetra = copiapalavra.getPosicaoDaIezimaOcorrencia(contemLetra, letra);
	     
	    int letrafaltante = (contemLetra) - (palavracont.getTamanho()); 
	   
	    if ((contemLetra>0) && (letrafaltante == 1)) 
	    {
	    	for (posicaoDaLetra = 0; posicaoDaLetra<letrasAtrasDoPainel.length ; posicaoDaLetra++)
	    	letrasAtrasDoPainel[contemLetra] = letrasAtrasDoPainel[posicaoDaLetra]; 	
	    	letrasAtrasDoPainel[posicaoDaLetra] = letrasAtrasDoPainel[letra];
	    	
	    
	  	}
          return "Voc� completou a palavra!";
 	 
	}
	
	public static Palavra acertarpalavra() throws Exception
	{
	    PedidoDeLetra pedidodeletra = new PedidoDeLetra (conexao, letra);
	    Palavra palavrasorteada = null;
	    try
	    {
	    palavrasorteada = bancodepalavras.getPalavraSorteada();
	    }
	    catch (Exception erro) {}
        Palavra copiapalavra;
        copiapalavra = palavrasorteada;
        Palavra chutepalavra = null;
        
        if (chutepalavra == copiapalavra)
        
        	System.out.print("Voc� acertou a palavra + \n"); ;
        
		return copiapalavra;
         
        
	}
	
	public static String errar() 
	{
		 
		 return "Voce errou"; 
		 
		/*Se a pessoa errou a letra ou a palavra, retornar comunicadoDeErro */
	}
	
	public static String errarletra() throws Exception
	{
	    PedidoDeLetra pedidodeletra = new PedidoDeLetra (conexao, letra);
	    Palavra palavrasorteada = null;
	    try
	    {
	    palavrasorteada = bancodepalavras.getPalavraSorteada();
	    }
	    catch (Exception erro) {}
        Palavra copiapalavra;
        copiapalavra = palavrasorteada;
        int quantidade = copiapalavra.getTamanho();
	    String letrasAtrasDoPainel[] = new String [quantidade];
	    
	    int contemLetra = copiapalavra.getQuantidade(letra);
	    
	    int posicaoDaLetra = copiapalavra.getPosicaoDaIezimaOcorrencia(contemLetra, letra);
	    
	    if (contemLetra == 0)
	   throw new Exception ("Letra nao encontrada");
	    
		return "Voc� errou a letra";			    	

	}
	
	public static String errarpalavra() throws Exception
	{
		 PedidoDeLetra pedidodeletra = new PedidoDeLetra (conexao, letra);
		    Palavra palavrasorteada = null;
		    try
		    {
		    palavrasorteada = bancodepalavras.getPalavraSorteada();
		    }
		    catch (Exception erro) {}
	        Palavra copiapalavra;
	        copiapalavra = palavrasorteada;
	        Palavra chutepalavra = null;
	        
	        if (chutepalavra != copiapalavra)
	        
	        	System.out.print("Palavra nao encontrada + \n"); ;
				return "Voce errou a palavra" ;
	       		
	}
	
	public static String vencer() throws Exception {
		
		Palavra clienteacertouapalavra = controladoradepartida.acertarpalavra();
		String clientecompletoupalavra = controladoradepartida.completarpalavra();
			
		
		if ( clienteacertouapalavra != null)
				return "Voce venceu!";
		
		
		 if (clientecompletoupalavra != null)
             return "Voce venceu!";
		 
		return clientecompletoupalavra;
		 
			
		  /*METODO VENCER
          else (chute palavra == copia palavra)
          System.out.println ("Jogador [falar a posicao do jogador ativo] você ganhou"!);
          dar comunicado de vitoria
          chamar método perder aos demais da sala para dar comunicado de desligamento
          encerrar partida para todos via objeto todo mundo (grupo ou controladora de partida);
          
          
            SITUAÇÃO 2 DO MÉTODO VENCER
            Como eu sei que o jogador passou uma letra que realmente era a última faltante para completar a copia da palavra?
          */
		
	}

	public static String perder() throws Exception {
	
		String clienterroupalavra = controladoradepartida.errarpalavra();
		
		if(clienterroupalavra  != null)
		return "Voce perdeu e sera removido da partida";
		return clienterroupalavra;
		
		
		/*  A partir desses resultados, incrementar na lógica para quando:
        chute palavra = o que o jogador digitar
        
        METODO PERDER
        if (chute palavra != copia palavra)
        System.out.println ("Jogador ´[falar a posicao do jogador ativo] você perdeu");
        dar comunicados de perda;
       chamar método VezDeJogar e comunicados aos demais da sala sobre a vez de jogar.
       */
	}
	
	
	
}
