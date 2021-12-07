import java.lang.String;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.lang.StringBuilder;
import java.net.Socket;

 

public class ControladorDeLetrasJaDigitadas implements Cloneable
{ 
     private ArrayList<String> letrasJaDigitadas = new ArrayList<String>();
//     List<ArrayList<String>> listaDeArrayDeletrasjadigitadas = Arrays.asList(letrasJaDigitadas);
     ArrayList<String> arrayletrasJaDigitadas = letrasJaDigitadas;
     private char letra;
//     private  char  caracter = letra;
     private byte qtd, posicao;
	 //private StringBuilder stringBuilder = new StringBuilder(); 
	
    public ControladorDeLetrasJaDigitadas() throws Exception{

      	  if (letrasJaDigitadas == null)
                throw new Exception ("Letras ausentes");   
              	
       this.letrasJaDigitadas = new ArrayList<String>(qtd); 
       this.qtd = 0;
         
     }
    
    public byte size()
    {
      return this.qtd;
    }

    public boolean isJaDigitada (char letra)
    
        {
     	 	
         for (int i = 0 ; i<letrasJaDigitadas.size(); letra ++)
    	{
         i = this.letrasJaDigitadas.indexOf(letra);
	     
         if (i == -1)
            return false; 
          
    	}
         return true;
    }

    
    public synchronized void adicionarletra (char letra, byte posicao)
	{ 
    	 Arrays.toString(letrasJaDigitadas.toArray());
        //if (this.qtd==this.letrasJaDigitadas.size())
	    this.letrasJaDigitadas.set(posicao, letrasJaDigitadas.get(this.qtd) + letra);
       this.qtd++;
 
	}


    public synchronized void registrarletra (char letra,  byte posicao) throws Exception
    {
    	// verifica se a letra já foi digitada e se o jogador passa um numero no lugar de letra;
    	// senão tiver erros, adiciona a letra na posição do letrasjadigitadas, tal como um estoque
    	// das letras já digitadas.
        if(isJaDigitada(letra))
            throw new Exception("Letra já foi digitada");
        
        boolean num = false;
        try{
            double numero = Double.parseDouble(letra+"");
            num = true;
        }catch (NumberFormatException erro){
            num = false;
        }
        if (num) 
            throw new Exception(" Letra não pode ser numero");
        
         this.adicionarletra(letra, posicao);
    
               
      }

    /*
    @Override
    public String toString ()
    {
    	
        String ret = "";
        for (int letra = 0; letra < this.letrasJaDigitadas.size(); letra++){
            ret += letrasJaDigitadas.charAt(letra) + ",";
        }

        return  ret;
    }
    
    @Override
    public boolean equals (Object obj) {

        if (this == obj) return true;

        if (obj == null) return false;

        if (obj.getClass() != ControladorDeLetrasJaDigitadas.class)
            return false;

        if(this.letrasJaDigitadas != ((ControladorDeLetrasJaDigitadas) obj) .letrasJaDigitadas)
            return false;

        return  true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 17;
        ret = ret * 17 + new String(letrasJaDigitadas).hashCode() ;
        if (ret < 0)
            ret = - ret;
        return  ret;
    }
    
    public ControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas c) throws Exception // construtor de copia
    {
        if(c == null)
            throw new Exception("c era null");
        this.letrasJaDigitadas = c.letrasJaDigitadas;
    }

    public Object clone ()
    {
        ControladorDeLetrasJaDigitadas ret = null;
        try{
            ret = new ControladorDeLetrasJaDigitadas(this);
        }
        catch (Exception ignored) {}        
        return ret;
    } */
} 