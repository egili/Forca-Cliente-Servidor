

public class ComunicadoDeVez extends Comunicado {
	private int posicaoJogador;
	
	public ComunicadoDeVez (ComunicadoDeDados dados) {
		this.posicaoJogador = posicaoJogador;
		this.dados = dados;
	}
	public int getposicaodoJogador() {
		return this.posicaoJogador;
	}
	
	public ComunicadoDeDados dados; 
	
}
