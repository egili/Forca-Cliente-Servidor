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
	
    
     private static final byte TAMANHO_INICIAL = 10;
     private ArrayList<String> letrasjadigitadas = new ArrayList<String>();
     List<ArrayList<String>> listaDeArrayDeletrasjadigitadas = Arrays.asList(letrasjadigitadas);
     ArrayList<String> arrayletrasjadigitadas = letrasjadigitadas;
     private char letra;
     private  char  caracter = letra;
     private int qtd;
	 //private StringBuilder stringBuilder = new StringBuilder(); 
	 private int posicao;
	 
    public ControladorDeLetrasJaDigitadas()
    { 
       this.letrasjadigitadas = new ArrayList<String>(TAMANHO_INICIAL); 
       this.qtd = 0;
       if (letrasjadigitadas == null)
       {
    	System.out.println("Letras ausentes");   
    	
       }   
        
     }
    
    public int size()
    {
      return this.qtd;
    }

    public boolean isJaDigitada (char letra)
    
        {
     	 	
         for (int i = 0 ; i<letrasjadigitadas.size(); letra ++)
    	{
         i = this.letrasjadigitadas.indexOf(letra);
	     
         if (i == -1)
         {
        	return false; 
         }
         
         return true;
    	}
    }

    
    public synchronized void adicionarletra (char letra, int posicao)
	{ 
    	char caracter = letra;
    	Arrays.toString(letrasjadigitadas.toArray());
        if (this.qtd==this.letrasjadigitadas.size())
	    this.letrasjadigitadas.set(posicao, letrasjadigitadas.get(this.qtd) + letra);
       this.qtd++;
 
	}


    public synchronized void registrarletra (char letra,  int posicao) throws Exception
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
     	Arrays.toString(letrasjadigitadas.toArray());
     	// usar 2 for e um  if para quebrar a palavra em char, e cada char tem um índice.
        boolean contemCaracter = new ArrayList<String>(arrayletrasjadigitadas).contains(String.valueOf(caracter));
        String palavra; 
        char [] letras = new char[palavra.length()] ;
        
        letras = palavra.toCharArray();
        palavra = palavra.valueOf(letras);
        for (int k = 0; k<palavra.length(); k++)
        {
        	int ondeocorre = k;
        	
        }
        
        for (int i = 0 ; i<letrasjadigitadas.size(); letra ++)
        {
        	if ( !contemCaracter)
        	{
        		i = this.letrasjadigitadas.indexOf(palavra.getPosicaoDaIezimaOcorrencia(ondeocorre, letra));
        		
         this.adicionarletra(letra, i);
        		
        	}
        }
         
   }
    
    


    @Override
    public String toString ()
    {
    	
        String ret = "";
        for (int letra = 0; letra < this.letrasjadigitadas.size(); letra++){
            ret += letrasjadigitadas.charAt(letra) + ",";
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