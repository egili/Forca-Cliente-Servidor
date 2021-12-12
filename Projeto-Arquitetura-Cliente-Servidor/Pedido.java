
public class Pedido extends Comunicado {

	private Comunicado pedido;

	public Pedido(Comunicado pedido) throws Exception {

		if (pedido == null)
			throw new Exception("pedido nulo");

		if (!(pedido instanceof PedidoDeLetra || pedido instanceof PedidoDePalavra))
			throw new Exception("pedir apenas uma letra ou uma palavra");

		if (pedido instanceof PedidoDeLetra)
			pedido = new PedidoDeLetra();

		if (pedido instanceof PedidoDePalavra)
			pedido = new PedidoDePalavra();

		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return (Pedido) this.pedido;
	}

	public static boolean isPedidoPalavra(Comunicado palavra) {

		if (!(palavra instanceof PedidoDePalavra))
			return false;

		return true;
	}

	public static boolean isPedidoLetra(Comunicado palavra) {

		if (!(palavra instanceof PedidoDeLetra))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Pedido: " + pedido;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		Pedido pedido = (Pedido) obj;

		if (!this.pedido.equals(pedido.pedido))
			return false;

		return true;
	}

	@Override
	public int hashCode() {

		int ret = 13;
		ret = 31 * ret + pedido.hashCode();

		return ret < 0 ? -ret : ret;
	}
}
