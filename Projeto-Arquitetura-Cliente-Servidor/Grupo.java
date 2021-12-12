
//import Clientes.*;

public class Grupo<X> { // fila

	private Object[] jogadores;
	private byte primeiro, ultimo, total;
	private static final byte TAMANHO_GRUPO = 3;

	public Grupo(Cliente[] jogadores) // sera que o construtor recebe o primeiro como parametro?
	{ 
		this.jogadores = (X[]) new Object[Grupo.TAMANHO_GRUPO];
		this.primeiro = 0;
		this.ultimo = 0;
		this.total = 0;
	}

	public void inserirJogadorNoGrupo(Cliente jogador) throws Exception 
	{
		if (jogador == null)
			throw new Exception("jogador ausente");

		jogadores[ultimo] = jogador; // a ultima posicao do vetor recebe o cliente
		ultimo = (byte) ((ultimo++) % jogadores.length);
		total++;
	}

	public Cliente removerJogadorDoGrupo() throws Exception
	{
		if (this.isVazio())
			throw new Exception("impossivel remover de um grupo vazio");

		Cliente jogadorASerRemovido = (Cliente) jogadores[primeiro];
		primeiro = (byte) ((primeiro++) % jogadores.length);

		return jogadorASerRemovido;
	}

	public boolean isVazio() 
	{
		return jogadores.length == 0 ? true : false;
	}

	public boolean isCheio() 
	{
		return jogadores.length == this.TAMANHO_GRUPO ? true : false;
	}

	// vai armazenar o primeiro jogador para que ele possa ser inserido novamente 
	//apos ser removido
	
	public Cliente getJogadorDaVez() throws Exception 
	{
		if (this.isVazio())
			throw new Exception ("O grupo nao pode ser vazio");
		
		//Nao vai usar !isCheio pois pode acontecer de haver 2 jogadores no grupo, como
		// na situacao de um jogador errar a palavra a ser adivinhada. 
		
       return (Cliente) this.removerJogadorDoGrupo();	
     }
	
	@Override
	public String toString() 
	{
		if(this.isVazio())
			return "nenhum jogador no grupo";
		
		String ret = "{";
		
		ret += this.jogadores[0];
		
		for(int i = 1; i < this.TAMANHO_GRUPO; i++)
			ret += "," + this.jogadores[i];
		
		ret += "}";
		
		return ret;
	}
	
	private Object[] ondeEsta (X x) // necessario apenas para a implementacao do equals
    {
        for (int i=0; i < this.TAMANHO_GRUPO; i++)
            if (x.equals(this.jogadores[i]))
            {
                Object[] ret = {true,i};
                return ret;
            }

        Object[] ret = {false,this.TAMANHO_GRUPO};
        return ret;
    }

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (this.getClass() != obj.getClass())
			return false;

		Grupo<X> grupo = (Grupo<X>) obj;

		if (this.primeiro != grupo.primeiro)
			return false;
		if (this.ultimo != grupo.ultimo)
			return false;
		if (this.ultimo != grupo.ultimo)
			return false;
		
		for(int i = 0; i < this.TAMANHO_GRUPO; i++) 
		{
			boolean achou = (boolean) this.ondeEsta((X)grupo.jogadores[i])[0];
			
			if (!achou)
                return false;
		}
		return true;
	}

	@SuppressWarnings("removal")
	@Override
	public int hashCode() 
	{
		int ret = 31;

		ret = 19 * ret + new Byte(this.primeiro).hashCode();
		ret = 11 * ret + new Byte(this.ultimo).hashCode();
		ret = 7 * ret + new Byte(this.total).hashCode();

		for (int i = 0; i < this.TAMANHO_GRUPO; i++)
			ret = 13 * ret + this.jogadores[i].hashCode();

		return ret < 0 ? -ret : ret;
	}
}
