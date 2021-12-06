
public class PedidoDeLetra extends Comunicado {
	private char letra;

   public PedidoDeLetra(char letra) {
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
    	
    	
