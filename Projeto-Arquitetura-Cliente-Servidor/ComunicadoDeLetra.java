

public class ComunicadoDeLetra extends Comunicado {
	public boolean isJaDigitada() {
        return jaDigitada;
    }

    public void setJaDigitada(boolean jaDigitada) {
        this.jaDigitada = jaDigitada;
    }

    boolean jaDigitada;

    public boolean isPalavraTemLetra() {
        return palavraTemLetra;
    }

    public void setPalavraTemLetra(boolean palavraTemLetra) {
        this.palavraTemLetra = palavraTemLetra;
    }

    boolean palavraTemLetra;

}
