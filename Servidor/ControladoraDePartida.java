	
	//	TODO: completar os metodos existentes, nenhum deles sera removidos
	
public class ControladoraDePartida {

	public ControladoraDePartida() throws Exception {
		// construtor
	}

	public static void vezDeJogar() throws Exception {
		/* a classe Grupo ja possibilita que ordem de jogada seja como numa fila,
		   a logica aqui vai ser remover o jogador do Grupo e depois inseri-lo novamente,
		   isso pois, o jogador removido eh sempre o primeiro do Grupo e sempre sera inserido 
		   como  ultimo do grupo
		   
		   TODO: a ideia deste metodo eh fazer com que o jogador saiba que eh sua vez de jogar e que ele possa ser unico a jogar */
	}

	public static String vencer() throws Exception { // a logica deste metodo talvez possa ser aplicada na main do servidor
		// TODO: implementar uma logica que envie o comunicadoDeVitoria ((e faca com que os outros recebam um de Derrota, todos serao removidos),o retorno possivelmente vai mudar
		return "Voce venceu!";
	}

	public static String perder() throws Exception { // a logica deste metodo talvez possa ser aplicada na main do servidor
		// TODO: implementar uma logica que envie o comunicadoDeDerrota (e faca com que ele seja removido), o retorno possivelmente vai mudar
		return "Voce perdeu e sera removido da partida";
	}
}
