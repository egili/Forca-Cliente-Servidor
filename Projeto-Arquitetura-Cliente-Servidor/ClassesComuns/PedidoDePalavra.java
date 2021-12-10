/*PS: SEGUNDO MONITORIA EM 07-12-2021 (FABRICIO): EM PEDIDO DE PALAVRA SE PASSA UMA STRING
 *  E VIA PEDIDO O TRATAMENTO  � FEITO DIRETAMENTE NA SUPERVISORA.*/
package ClassesComuns;

import Clientes.*;
import Servidor.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class PedidoDePalavra extends Comunicado {
	private static Grupo grupo;
	private static Palavra palavra;
	private static Teclado teclado;
	private char letra;
	private Socket conexao;
	private ObjectInputStream  receptor;
	private ObjectOutputStream transmissor;
	String host = Servidor.HOST_PADRAO;
    String porta= Servidor.PORTA_PADRAO;	
	private String chutepalavra;
    
    public PedidoDePalavra (Socket conexao, String chutepalavra) throws Exception
    {

    	   if (grupo.isCheio())
    	   {  
    		   
    		   if (conexao==null)
   	            throw new Exception ("Conexao ausente");
   		     
   		     if  (chutepalavra == "null" || chutepalavra == "" ) // char nao eh nulo, por padrão ele é zero
   				   throw new Exception ("Palavra nao pode ser nula!");
   		     
   		    
   		     int numero = teclado.getUmInt();
   			
   		      if (chutepalavra == chutepalavra.valueOf(numero))
   		       throw new Exception ("Palavra nao pode ser numero!");
   		      }
    	   
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
   		  Parceiro cliente = null;
   		  cliente = new Parceiro (conexao, receptor, transmissor);
   		  ComunicadoComecouPartida comunicadocomecoupartida =null;
		  Comunicado comunicado = null;		
		   
		    
		   do
		   {
			   comunicado = (Comunicado)cliente.espie ();
		   }
		   
		   while (!(comunicado instanceof ComunicadoComecouPartida))
			   cliente.receba(new ComunicadoComecouPartida());
		       this.chutepalavra = "null";
		    
		        //String palavramontada= null;
		        
		        String palavrasorteada = null;
			   	 palavra = BancoDePalavras.getPalavraSorteada();
			   	 Palavra p1 = new Palavra(palavrasorteada);
			    String copiapalavra = palavrasorteada;
			   	int quantidade = copiapalavra.length();
			     //Palavra p2 = new Palavra(palavramontada);
		    	 Palavra p2 = new Palavra (chutepalavra);
			     if (chutepalavra == copiapalavra)
			     {
			    	 System.out.println(chutepalavra);
			     }	
		    	this.chutepalavra = chutepalavra;
		    	
		       
    	   }
    
    
	
}
