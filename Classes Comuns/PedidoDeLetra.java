
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
   
  /**Pensei se deveriamos lan�ar exec��o
   
verifica se a letra recebido � nula ou ent�o vazio,
ou seja, sem nenhum caractere, lan�ando exce��o.

	
	if(letra == null || letra == "")
		throw new Exception ("Digite uma letra");
	
	//armazena o texto recebido em this.letra.
	
	this.letra = letra;
**/
    	
    	
