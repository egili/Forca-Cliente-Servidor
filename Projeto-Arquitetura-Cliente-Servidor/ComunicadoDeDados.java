
//import Servidor.*;

public class ComunicadoDeDados extends Comunicado {
	private Palavra palavra;
	private Tracinhos tracinhos;
	private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
	private ControladorDeErros controladorDeErros;
	private ComunicadoDeDados comunicadoDeDados;

	public ComunicadoDeDados() {
		try {
			// Inicia os dados do Jogo da forca
			this.palavra = BancoDePalavras.getPalavraSorteada();
			this.tracinhos = new Tracinhos(palavra.getTamanho());
			this.controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();
			this.controladorDeErros = new ControladorDeErros((int) (palavra.getTamanho() * 0.6));

		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public Palavra getPalavra() {
		return palavra;
	}

	public void setPalavra(Palavra palavra) {
		this.palavra = palavra;
	}
	public boolean isTracinhos() {
		return tracinhos.isAindaComTracinhos();
	}

	public Tracinhos getTracinhos() {
		return tracinhos;
	}

	public void setTracinhos(Tracinhos tracinhos) {
		this.tracinhos = tracinhos;
	}

	public ControladorDeLetrasJaDigitadas getControladorDeLetrasJaDigitadas() {
		return controladorDeLetrasJaDigitadas;
	}

	public void setControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas) {
		this.controladorDeLetrasJaDigitadas = controladorDeLetrasJaDigitadas;
	}

	public ControladorDeErros getControladorDeErros() {
		return controladorDeErros;
	}

	public void setControladorDeErros(ControladorDeErros controladorDeErros) {
		this.controladorDeErros = controladorDeErros;
	}

	public ComunicadoDeDados getComunicadoDeDados() {
		return comunicadoDeDados;
	}
}
