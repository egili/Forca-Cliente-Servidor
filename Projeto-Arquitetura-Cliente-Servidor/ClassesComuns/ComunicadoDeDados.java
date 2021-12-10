package ClassesComuns;

import Clientes.*;
import Servidor.*;

public class ComunicadoDeDados extends Comunicado{
	 private Palavra palavra;
	    private Tracinhos tracinhos;
	    private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
	    private ControladorDeErro controladorDeErros;

	    public ComunicadoDeDados () {
	        try
	        {

	            // Inicia os dados do Jogo da forca
	            this.palavra = BancoDePalavras.getPalavraSorteada();
	            this.tracinhos = new Tracinhos (palavra.getTamanho());
	            this.controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();
	            this.controladorDeErros = new ControladorDeErro ((int)(palavra.getTamanho()*0.6));

	        }
	        catch (Exception err)
	        {
	            err.printStackTrace();
	        }
	    }


	    public Palavra getPalavra() {
	        return palavra;
	    }

	    public void setPalavra(Palavra palavra) {
	        this.palavra = palavra;
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

	    public ControladorDeErro getControladorDeErros() {
	        return controladorDeErros;
	    }

	    public void setControladorDeErros(ControladorDeErro controladorDeErros) {
	        this.controladorDeErros = controladorDeErros;
	    }

}
