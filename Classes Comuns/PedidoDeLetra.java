
public class PedidoDeLetra extends Comunicado {
	private char letra;

   public PedidoDeLetra(char letra) throws Exception {
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
       this.letra = letra;
   }

   public char getLetra() {
       return letra;
    }

   public void setLetra(char letra) {
        this.letra = letra;
   }
    
}
   
  /**Pensei se deveriamos lan�ar exec��o
   
verifica se a letra recebido � nula ou ent�o vazio,
ou seja, sem nenhum caractere, lan�ando exce��o.

	
	if(letra == null || letra == "")
		throw new Exception ("Digite uma letra");
	
	//armazena o texto recebido em this.letra.
	
	this.letra = letra;
**/
    	
    	
