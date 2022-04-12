
//import ClassesComuns.*;
import java.util.ArrayList;

public class ControladoraDePartida {
	private final ArrayList<Parceiro> jogadores;
	private final ArrayList<SupervisoraDeConexao> supervisoras = new ArrayList<>();
	private int posicaoJogador = 0;

	public ControladoraDePartida(ArrayList<Parceiro> jogadores) throws Exception {

		if (jogadores == null)
			throw new Exception("Nenhum jogador no grupo");

		this.jogadores = jogadores;
	}

	public void proximoJogador() // reimplementando a ideia de fila
	{
		synchronized (jogadores) {
			posicaoJogador++;

			if (posicaoJogador == jogadores.size())
				posicaoJogador = 0;
		}
	}

	public boolean podeJogar(Parceiro jogador) throws Exception {
		
		try {
			synchronized (jogadores) {
				return jogador == jogadores.get(posicaoJogador);
			}
		} catch (Exception e) {
			throw new Exception("jogador removido");
		}
	}

	public ArrayList<Parceiro> getJogadores() {
		return jogadores;
	}

	public int getPosicaoJogador() {
		return posicaoJogador;
	}

	public void setPosicaoJogador(int posicao) {
		this.posicaoJogador = posicao;
	}

	public void adicionarSupervisora(SupervisoraDeConexao supervisora) throws Exception {
		
		if (supervisora == null)
			throw new Exception("Supervisora nula");

		supervisoras.add(supervisora);
	}

	public ArrayList<SupervisoraDeConexao> getSupervisoras() {
		return supervisoras;
	}

	public void fimThreadSupervisora() {
		
		synchronized (supervisoras) {
			for (int i = 0; i < supervisoras.size(); i++) {
				supervisoras.get(i).fim = false;
			}
		}
	}

	@Override
	public String toString() {
		return "[ Jogadores = " + jogadores + ", posicao =" + posicaoJogador + ']';
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (obj.getClass() != this.getClass())
			return false;

		ControladoraDePartida controladora = (ControladoraDePartida) obj;

		if (controladora.posicaoJogador != posicaoJogador)
			return false;

		if (controladora.jogadores.size() == jogadores.size()) {
			for (int i = 0; i < jogadores.size(); i++) {
				if (!jogadores.get(i).equals(controladora.jogadores.get(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int ret = 31;

		ret = ret * 7 + Integer.valueOf(posicaoJogador).hashCode();

		for (int i = 0; i < jogadores.size(); i++) {
			ret = ret * 7 + jogadores.get(i).hashCode();
		}

		return ret < 0 ? -ret : ret;
	}
}