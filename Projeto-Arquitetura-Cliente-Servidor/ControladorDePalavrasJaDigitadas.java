
public class ControladorDePalavrasJaDigitadas implements Cloneable {
	private String palavrasJaDigitadas;

	public ControladorDePalavrasJaDigitadas() {
		this.palavrasJaDigitadas = "";
	}

	public boolean isJaDigitada(String palavra) {
		int i = this.palavrasJaDigitadas.indexOf(palavra);
		if (i == -1)

			return false;

		return true;
	}

	public void registrepalavra(String palavra) throws Exception {
		if (isJaDigitada(palavra))
			throw new Exception(" Palavra ja digitada");

		boolean num = false;
		try {
			double numero = Double.parseDouble(palavra + "");
			num = true;
		} catch (NumberFormatException erro) {
			num = false;
		}
		if (num) {
			throw new Exception(" " + " Palavra nao pode ser numero");
		}

		this.palavrasJaDigitadas = this.palavrasJaDigitadas + palavra;

	}

	@Override
	public String toString() {
		String ret = "";
		for (int letra = 0; letra < this.palavrasJaDigitadas.length(); letra++) {
			ret += palavrasJaDigitadas.charAt(letra) + ",";
		}

		return ret;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (obj.getClass() != ControladorDePalavrasJaDigitadas.class)
			return false;

		if (this.palavrasJaDigitadas != ((ControladorDePalavrasJaDigitadas) obj).palavrasJaDigitadas)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int ret = 17;
		ret = ret * 17 + new String(palavrasJaDigitadas).hashCode();
		if (ret < 0)
			ret = -ret;
		return ret;
	}

	public ControladorDePalavrasJaDigitadas(ControladorDePalavrasJaDigitadas c) throws Exception // construtor de copia
	{
		if (c == null)
			throw new Exception("c era null");
		this.palavrasJaDigitadas = c.palavrasJaDigitadas;
	}

	public Object clone() {
		ControladorDePalavrasJaDigitadas ret = null;
		try {
			ret = new ControladorDePalavrasJaDigitadas(this);
		} catch (Exception ignored) {

		}
		return ret;
	}
}
