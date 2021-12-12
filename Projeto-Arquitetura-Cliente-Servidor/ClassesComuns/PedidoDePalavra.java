/*PS: SEGUNDO MONITORIA EM 07-12-2021 (FABRICIO): EM PEDIDO DE PALAVRA SE PASSA UMA STRING
 *  E VIA PEDIDO O TRATAMENTO  � FEITO DIRETAMENTE NA SUPERVISORA.*/
package ClassesComuns;

import Servidor.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PedidoDePalavra extends Comunicado {
	private static BancoDePalavras bancoDePalavras;
	private static Grupo grupo;
	private static Palavra palavra;
	private static Teclado teclado;
	private Socket conexao;
	private ObjectInputStream  receptor;
	private ObjectOutputStream transmissor;
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
   		    /*    
   		  Parceiro cliente = null;
   		  cliente = new Parceiro (conexao, receptor, transmissor);
   		  ComunicadoComecouPartida comunicadocomecoupartida =null;
		  Comunicado comunicado = null;		
		  */  
		    
		  	         
	
}
    
}
