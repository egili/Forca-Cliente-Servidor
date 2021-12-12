/*PS: SEGUNDO MONITORIA EM 07-12-2021 (FABRICIO): EM PEDIDO DE LETRA SE PASSA UM CHAR E VIA PEDIDO O TRATAMENTO
 * É FEITO DIRETAMENTE NA SUPERVISORA.*/
package ClassesComuns;

import Clientes.*;
import Servidor.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class PedidoDeLetra extends Comunicado {
	private static ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
	private static Grupo grupo;
	private static Palavra palavra;
    private char letra;
   	private Socket             conexao;
	private ObjectInputStream  receptor;
	private ObjectOutputStream transmissor;
	//String host = Servidor.HOST_PADRAO;
    String porta= Servidor.PORTA_PADRAO;
         
   public PedidoDeLetra(Socket conexao, char letra) throws Exception {
	    
	   
	   // como eu sei que começou a partida????
	   if (grupo.isCheio())
	     {  
		   
		     if (conexao==null)
	            throw new Exception ("Conexao ausente");
		     
		     if  (letra == '\0')
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
		  Parceiro cliente = null;
		  cliente = new Parceiro (conexao, receptor, transmissor);
		  ComunicadoComecouPartida comunicadocomecoupartida =null;
		  Comunicado comunicado = null;		
		   
		    
		   do
		   {
			   comunicado = (Comunicado)cliente.espie ();
		   }
		   
		   while (!(comunicado instanceof ComunicadoComecouPartida));
			   cliente.receba(new ComunicadoComecouPartida());
		       this.letra = '\0';
		    
		 
		   this.conexao  = conexao;
	       this.letra = letra;
	   }
	   
	      
   }
   
      public char getLetra() {
       return letra;
    }

   public void setLetra(char letra) {
        this.letra = letra;
   }
    
}



    	
    	
