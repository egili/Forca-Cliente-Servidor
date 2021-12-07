
public class PedidoDeLetra extends Comunicado {
	private char letra;

   public PedidoDeLetra(char letra) throws Exception {
	   /* Quando o jogo começar é porque terá 3 jogadores em uma sala.
	    O primeiro que começar a jogar, a estrutura de letra tem que ser nula,
	    por exemplo: char letras[] = null;
	    Toda vez que o jogador passar uma letra validar se realmente é uma letra,
	    como por exemplo: números, caracteres especiais e espaços vazios tem
	    que lançar exceções no construtor.
	    Se trabalhar com vetor, fazer com que tenha tamanho máximo de 26 espaços.
	    (O vetor de letra que vai se conectar com as letrasjadigitadas, lembrando que:
	    letrasjadigitadas é o estoque das letras faladas na partida).
	    Num segundo momento, o vetor de letras terá a posição atual instanciada
	    em uma outra estrutura que vai passar essa letra nas posições da copia da palavra,
	    fazendo com que essa última estrutura de letra possa ser repetida.
	    Exemplo:
	    letradepainel [] = letra[i]; -- letras que aparecem quando a pessoa advinha a letra
	    de dentro da palavra no roda a roda;
	    for ( i = 0;  i<copiadapalavra.length(); i++)
	     if (letradepainel.equals(copiadapalavra.indexOf[letra])
	      this.copiadepalavra = letradepainel[i]
	      return copiadepalavra;
	      
	      else
	      system.out.println("Essa letra " + letra + "não tem na nossa palavra!");
	      
	      PS: Pelo banco de palavras, pode ter de 0 até 5 posições para a letra.
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
   
  /**Pensei se deveriamos lançar execção
   
verifica se a letra recebido é nula ou então vazio,
ou seja, sem nenhum caractere, lançando exceção.

	
	if(letra == null || letra == "")
		throw new Exception ("Digite uma letra");
	
	//armazena o texto recebido em this.letra.
	
	this.letra = letra;
**/
    	
    	
