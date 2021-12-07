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
    private char letra = umaletra;
    private final byte posicao, quantasvezes;
	private Socket             conexao;
	private ObjectInputStream  receptor;
	private ObjectOutputStream transmissor;
	String host = Servidor.HOST_PADRAO;
    String porta= Servidor.PORTA_PADRAO;
	
   public PedidoDeLetra(Socket conexao, char umaletra) throws Exception {
	   Grupo[] jogadores = new Grupo[3]; 
	   
	   // como eu sei que começou a partida????
	   if (jogadores.length == 3)
	   {
		     
		   
		     if (conexao==null)
	            throw new Exception ("Conexao ausente");
		     
		     if  (umaletra == '\0') // char não lê nulo, por padrão ele é zero
				   throw new Exception ("Letra ausente");
			
		     ObjectOutputStream transmissor;
		        try
		        {
		            transmissor =
		            new ObjectOutputStream(
		            this.conexao.getOutputStream());
		        }
		        catch (Exception erro)
		        {
		            return;
		        }
		        
		        ObjectInputStream receptor=null;
		        try
		        {
		            receptor=
		            new ObjectInputStream(
		            this.conexao.getInputStream());
		        }
		        catch (Exception erro) 
		        {
		        	return;
		        }
		  Parceiro servidor = null;
		  servidor = new Parceiro (conexao, receptor, transmissor);
		  ComunicadoComecouPartida comunicadocomecoupartida =null;
		  Comunicado comunicado = null;		
		   
		    
		   do
		   {
			   comunicado = (Comunicado)servidor.espie ();
		   }
		   
		   while (!(comunicado instanceof ComunicadoComecouPartida))
			   servidor.receba(new ComunicadoComecouPartida());
		       this.umaletra = '\0';
		    
		  // valida se letra for um número e se a letra já foi escrita,
		   //senão acrescenta em letrasJaDigitadas
		   controladorDeLetrasJaDigitadas.registrarletra(umaletra, posicao);
		   
		       	            
		   // registra a umaletra dentro da variavel umaletra.
		   this.conexao  = conexao;
	       this.umaletra = umaletra;
	   }
	   
	      
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
		    // ou para garantir, achar um jeito de usar o método getIezimaPosicaoDaOcorrencia no palavramontada;
	       	       
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

    	
    	
