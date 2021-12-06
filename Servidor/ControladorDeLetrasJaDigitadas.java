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

/*
 validar se o uso dessa classe eh necessario e
 eventualmente fazer todas as alteracoes
 Classe ControladorDeLetrasJaDigitadas = Lunara;
  Edição dia 04-12-2021 (lembrete): usar vetor como conjunto tal como na classe Conjunto, porém ver a implementação da classe Vector
  do professor no dia 28-10 
 
 Perguntas:
 1. Tenho que realmente usar a classe Conjunto tendo em vista que a cada 3 jogadores terá apenas 1 partida?
 2. Quando usa ArrayList o Vetor é compartilhado, porém, cada partida terá o seu vetor de letrasjaidigitadas de acordo
 com a palavra sorteada. Então não faz sentido compartilhar vetor.
 3. Na aula do dia 28-10 mostra que o Vetor tem sincronismo interno, sendo declarado no cabeçalho do método. Não sendo 
 compartilhado, fazer uma lógica de que quando terminar a partida, isto é, quando o 1 jogador receber o comunicado de vitoria
 e os 2 demais jogadores receberem o comunicado de perda, o vetor deve ser totalmente limpo pelo remova ANTES de começar uma nova
 partida,
 4. Interface Set não funciona, pois usando Character ou String ele barra completamente o Vetor. Por isso se optou por usar
 Vector com validações para elementos repetidos, armazenamento, quando o vetor começar vazio no construtor.
 5. REVISAR CÓDIGO COM NBOUANI EM 06/12/2021. Revisar synchronized para saber se é no cabeçalho ou dentro do método;
 um arraylist ele tem limite para inserir elementos ou cresce a medida em que ele vai sendo acrescentado?
 */

public class ControladorDeLetrasJaDigitadas implements Cloneable
{
	
    
     
     private ArrayList<String> letrasJaDigitadas = new ArrayList<String>();
//     List<ArrayList<String>> listaDeArrayDeletrasjadigitadas = Arrays.asList(letrasJaDigitadas);
//     ArrayList<String> arrayletrasJaDigitadas = letrasJaDigitadas;
     private char letra;
//     private  char  caracter = letra;
     private byte qtd, posicao;
	 //private StringBuilder stringBuilder = new StringBuilder(); 
	
	 
    public ControladorDeLetrasJaDigitadas() throws Exception
    {
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
         
         
         return true;
    	}
    }

    
    public synchronized void adicionarletra (char letra, byte posicao)
	{ 
    	char caracter = letra;
    	Arrays.toString(letrasJaDigitadas.toArray());
        if (this.qtd==this.letrasJaDigitadas.size())
	    this.letrasJaDigitadas.set(posicao, letrasJaDigitadas.get(this.qtd) + letra);
       this.qtd++;
 
	}


    public synchronized void registrarletra (char letra,  byte posicao) throws Exception
    {
    	
        if(isJaDigitada(letra))
            throw new Exception("Letra já foi digitada");
        
        boolean num = false;
        try{
            double numero = Double.parseDouble(letra+"");
            num = true;
        }catch (NumberFormatException erro){
            num = false;
        }
        if (num) {
            throw new Exception(" Letra não pode ser numero");
        }
      
         char  caracter = letra;
     	Arrays.toString(letrasJaDigitadas.toArray());
     	// usar 2 for e um  if para quebrar a palavra em char, e cada char tem um índice.
        boolean contemCaracter = new ArrayList<String>(arrayletrasJaDigitadas).contains(String.valueOf(caracter));
        String palavra; 
        char [] letras = new char[palavra.length()] ;
        
        letras = palavra.toCharArray();
        palavra = palavra.valueOf(letras);
        for (int k = 0; k<palavra.length(); k++)
        {
        	int ondeocorre = k;
        	
        }
        
        for (int i = 0 ; i<letrasJaDigitadas.size(); letra ++)
        {
        	if ( !contemCaracter)
        	{
        		i = this.letrasJaDigitadas.indexOf(palavra.getPosicaoDaIezimaOcorrencia(ondeocorre, letra));
        		
         this.adicionarletra(letra, i);
        		
        	}
        }
         
   }
    
    


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
        catch (Exception ignored) {}        }
        return ret;
    }
}