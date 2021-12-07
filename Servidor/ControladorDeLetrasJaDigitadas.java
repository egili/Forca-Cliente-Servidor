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
        if (this.qtd==this.letrasJaDigitadas.size())
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
        
        // segunda parte: verificar se a letra passada realmente faz parte da palavrasorteada e 
         // acrescentar a letra na(s) posicao(oes) designadas.
         /*
          A ideia é que a estrutura de dados letrasjadigitadas funcione como um estoque das letras que vão sendo digitadas dentro de uma partida.
          A partir dessa estrutura de dados, fazer uma variável que seja uma cópia estável da palavra sorteada, pois para cada partida
          haverá apenas uma palavra sorteada. A partir dessa cópia, quando o jogador passar uma letra ela será armazenada no letrasjadigitadas.
          A partir dessa letrasjadigitadas, pegar a posição que foi passada essa letra (lembrando que: sempre será acrescentada na ultima posicao 
          do letrasjadigitadas, portanto para arraylist usar lastIndexOf ou se for char c = letra, usar toCharArray(posicao letra);).
          Depois de ver a letra, descobrir na copia da palavra qual a posição que a letra deve ser colocada. Por exemplo se passar a letra A em casa,
          seriam as posições 1 e 3. Para ver letra e posição, seria bom usar na main (cliente ou servidor?) os métodos  
          copia palavra = bancoDePalavras.getPalavraSorteada e palavra.getPosicaoDaIezimaOcorrencia
          
          A partir desses resultados, incrementar na lógica para quando:
           chute palavra = o que o jogador digitar
           
           METODO PERDER
           if (chute palavra != copia palavra)
           System.out.println ("Jogador ´[falar a posicao do jogador ativo] você perdeu");
           dar comunicados de perda;
          chamar método VezDeJogar e comunicados aos demais da sala sobre a vez de jogar.
           
           METODO VENCER
           else (chute palavra == copia palavra)
           System.out.println ("Jogador [falar a posicao do jogador ativo] você ganhou"!);
           dar comunicado de vitoria
           chamar método perder aos demais da sala para dar comunicado de desligamento
           encerrar partida para todos via objeto todo mundo (grupo ou controladora de partida);
           
            SITUAÇÃO 2 DO MÉTODO VENCER
            Como eu sei que o jogador passou uma letra que realmente era a última faltante para completar a copia da palavra?
          * */
      // COMO EU PEGO A PALAVRA SORTEADA DA CLASSE BANCO DE PALAVRAS, MÉTODO GETPALAVRASORTEADA?
         
        String palavramontada;
        int quantidade = palavramontada.length();
		String LetrasAtrasDoPainel[] = new String [quantidade];  
          char charc = letra;
       
          

       Arrays.toString(letrasJaDigitadas.toArray());
       this.letrasJaDigitadas.indexOf(charc);
       String palavrasorteada = null;
      /* COMO EU PEGO A PALAVRA SORTEADA DO BANCO DE PALAVRAS E COPIO ESSA PALAVRA NA COPIA PALAVRA?*/       
       Palavra p1 = new Palavra(palavrasorteada);
       
          String copiapalavra = palavrasorteada;
       
          Palavra p2 = new Palavra(palavramontada);
      
      boolean contemLetra = LetrasAtrasDoPainel.equals(charc);
		

       
       if (contemLetra == true) 
       {
    	byte quantasvezes = y;
		while
    	(palavramontada == copiapalavra)
    	// verificar quantas vezes aparece a letra dentro do vetor LetrasAtrasDoPainel e armazenar em PalavraMontada;
        for (quantasvezes=0; quantasvezes<palavramontada.length(); quantasvezes ++)
        	LetrasAtrasDoPainel[quantasvezes].charAt(quantasvezes); // intuito de pegar a posicao;
        	LetrasAtrasDoPainel[quantasvezes].repeat(charc); // pegar as ocorrencias da letra;
	    this.adicionarletra(charc, quantasvezes);
	    // ou para garantir, achar um jeito de usar o método getIezimaPosicaoDaOcorrencia no palavramontada;
	    
       }
		
		 
    	    
    	
    	    
    	   
    	    
    	    
    	  }
    	}
       }
       
        
      
       
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