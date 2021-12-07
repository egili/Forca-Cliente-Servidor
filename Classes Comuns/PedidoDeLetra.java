import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class PedidoDeLetra extends Comunicado {
	private static ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
	private static Palavra palavra;
    private final ArrayList<String> letrasJaDigitadas;
    //ControladorDeLetrasJaDigitadas letra = new ControladorDeLetrasJaDigitadas();  
    private char umaletra;
    private final byte posicao, quantasvezes;
	private Socket             conexao;
	private ObjectInputStream  receptor;
	private ObjectOutputStream transmissor;
	String host = Servidor.HOST_PADRAO;
    String porta= Servidor.PORTA_PADRAO;
	
   public PedidoDeLetra(char umaletra) throws Exception {
	   Grupo[] jogadores = new Grupo[3]; 
	   
	   // como eu sei que come�ou a partida????
	   if (jogadores.length == 3)
	   {   
		 Socket conexao=null;
		 ObjectInputStream receptor=null;
		  Parceiro servidor = null;
		  conexao = new Socket(host,porta);
		  receptor = new ObjectInputStream(conexao.getInputStream());
		  transmissor = new ObjectOutputStream(conexao.getOutputStream());
		  servidor = new Parceiro (conexao, receptor, transmissor);
		  ComunicadoComecouPartida comunicadocomecoupartida =null;
		  Comunicado comunicado = null;		
		   
		    
		   do
		   {
			   comunicado = (Comunicado)servidor.espie ();
		   }
		   
		   while (!(comunicado instanceof ComunicadoComecouPartida))
			   servidor.receba(new ComunicadoComecouPartida());
		   if  (umaletra == '\0') // char n�o l� nulo, por padr�o ele � zero
			   throw new Exception ("Letra ausente");
		   
		  // valida se letra for um n�mero e se a letra j� foi escrita,
		   //sen�o acrescenta em letrasJaDigitadas
		   controladorDeLetrasJaDigitadas.registrarletra(umaletra, posicao);
		   
		       	            
		   // registra a umaletra dentro da variavel umaletra.      
		   this.umaletra = umaletra;
	   }
	   
	   
    /* Quando o jogo come�ar � porque ter� 3 jogadores em uma sala.
	    O primeiro que come�ar a jogar, a estrutura de letra tem que ser nula,
	    por exemplo: char letras[] = null;
	    Toda vez que o jogador passar uma letra validar se realmente � uma letra,
	    como por exemplo: n�meros, caracteres especiais e espa�os vazios tem
	    que lan�ar exce��es no construtor.
	    Se trabalhar com vetor, fazer com que tenha tamanho m�ximo de 26 espa�os.
	    (O vetor de letra que vai se conectar com as letrasjadigitadas, lembrando que:
	    letrasjadigitadas � o estoque das letras faladas na partida).
	    Num segundo momento, o vetor de letras ter� a posi��o atual instanciada
	    em uma outra estrutura que vai passar essa letra nas posi��es da copia da palavra,
	    fazendo com que essa �ltima estrutura de letra possa ser repetida.
	    Exemplo:
	    letradepainel [] = letra[i]; -- letras que aparecem quando a pessoa advinha a letra
	    de dentro da palavra no roda a roda;
	    for ( i = 0;  i<copiadapalavra.length(); i++)
	     if (letradepainel.equals(copiadapalavra.indexOf[letra])
	      this.copiadepalavra = letradepainel[i]
	      return copiadepalavra;
	      
	      else
	      system.out.println("Essa letra " + letra + "n�o tem na nossa palavra!");
	      
	      PS: Pelo banco de palavras, pode ter de 0 at� 5 posi��es para a letra.
	      Por exemplo, em otorrinolaringologista, tem 5 letras 'o'.
	    * */
      
   }
   
   public String adicioneLetraNaPalavra(byte quantasvezes, char charc) throws Exception
   {
	     String palavramontada= null;
	        int quantidade = palavramontada.length();
			String LetrasAtrasDoPainel[] = new String [quantidade];  
	          char charc1 = umaletra;
	       Arrays.toString(letrasJaDigitadas.toArray());
	       this.letrasJaDigitadas.indexOf(charc1);
	       String palavrasorteada = null;
	   	  palavra = BancoDePalavras.getPalavraSorteada();
	      
	       Palavra p1 = new Palavra(palavrasorteada);
	       
	          String copiapalavra = palavrasorteada;
	       
	          Palavra p2 = new Palavra(palavramontada);
	      
	      boolean contemLetra = LetrasAtrasDoPainel.equals(charc1);
			
	  	
	       
	       if (contemLetra == true) 
	       {
	    	
			while
	    	(palavramontada == copiapalavra)
	    	// verificar quantas vezes aparece a letra dentro do vetor LetrasAtrasDoPainel e armazenar em PalavraMontada;
        for (quantasvezes=0; quantasvezes<palavramontada.length(); quantasvezes ++)
	   	     if (LetrasAtrasDoPainel[quantasvezes].equals(palavramontada.indexOf(charc1)))
	   	       LetrasAtrasDoPainel[quantasvezes].repeat(charc1);	
	        	palavramontada = LetrasAtrasDoPainel[quantasvezes];
	        	this.adicioneLetraNaPalavra(quantasvezes, charc1);
		    // ou para garantir, achar um jeito de usar o m�todo getIezimaPosicaoDaOcorrencia no palavramontada;
	       	       
		    if (contemLetra == false)
		    throw new Exception ("A letra" + charc1 + "nao existe na palavra a ser advinhada");
  
	       }
		        
	       return palavramontada;
			 
   }

   public char getLetra() {
       return umaletra;
    }

   public void setLetra(char umaletra) {
        this.umaletra = umaletra;
   }
    
}

    	
    	
