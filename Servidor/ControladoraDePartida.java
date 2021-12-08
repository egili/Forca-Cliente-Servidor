/*
 essa classe representa uma estrutra de dados que, a cada vez que um jogador tenta se conectar, 
 eh armazenado num objeto dela e, ser� direcionado para a partida quando o grupo possuir 
 3 jogadores (que sao objetos da futura classe Cliente)
 uma unica thread a cada grupo de 3 clientes!
 
 passo a passo:
	 1.cliente tenta se conectar 
	 2. aparece a mensagem "aguardando outros jogadores"
	 3. starta a aceitadora de conexao (lembrando que uma aceitadora para varias supervisoras (1 - N))
 
 como deve funcionar a classe:
 	1.cada vez que a aceitadora de conexao for startada, o ciente que tentou se conectar ser� armazenado no grupo (estrutura de dados)
 	2.o primeiro jogador a entrar no grupo eh o primeiro a jogar (principio de FILA)
 	3.aceitadora de conexao instancia um objeto da classe Grupo -> chama um m�todo que adiciona(push?) o cliente naquele grupo
 	4. m�todo pop(?) acontece quando o cliente perde a partida -> excluido do grupo, logo, excluido da partida
 	5.cada vez que uma aceitadora startar uma supervisora, ela passar� a estrutura como parametro para o construtor
 
 obs: cliente = jogador
 */

public class ControladoraDePartida {

	private Grupo<Cliente> grupo;
	
	public ControladoraDePartida(Cliente[] jogadores) throws Exception {
		
		if (jogadores == null)
			throw new Exception ("Jogador nao pode ser nulo!");
		if (!grupo.isCheio())
			throw new Exception ("Grupo precisa ter 3 jogadores!");
		
		this.grupo = new Grupo<Cliente>(jogadores);
	}
	
	// Em que momento ser� colocado no cliente que a partida come�ou porque temos 3 jogadores?

	public static void vezDeJogar() throws Exception {
		// se o jogador estiver na partida h� mais tempo -> primeiro a jogar
		// vai remover o jogador do Grupo e depois inseri-lo novamente
	}

	public static ComunicadoDeAcerto acertar() throws Exception
	{
		return new ComunicadoDeAcerto();
	}
	
	public static String errar() 
	{
		 
		 return "Voce errou"; 
		 
		/*Se a pessoa errou a letra ou a palavra, retornar comunicadoDeErro */
	}
	
	public static String vencer() throws Exception {
		return "Voce venceu!";
		
		  /*METODO VENCER
          else (chute palavra == copia palavra)
          System.out.println ("Jogador [falar a posicao do jogador ativo] voc� ganhou"!);
          dar comunicado de vitoria
          chamar m�todo perder aos demais da sala para dar comunicado de desligamento
          encerrar partida para todos via objeto todo mundo (grupo ou controladora de partida);
          
          
            SITUA��O 2 DO M�TODO VENCER
            Como eu sei que o jogador passou uma letra que realmente era a �ltima faltante para completar a copia da palavra?
          */
		
	}

	public static String perder() throws Exception {
		
		return "Voce perdeu e sera removido da partida";
		
		
		/*  A partir desses resultados, incrementar na l�gica para quando:
        chute palavra = o que o jogador digitar
        
        METODO PERDER
        if (chute palavra != copia palavra)
        System.out.println ("Jogador �[falar a posicao do jogador ativo] voc� perdeu");
        dar comunicados de perda;
       chamar m�todo VezDeJogar e comunicados aos demais da sala sobre a vez de jogar.
       */
	}
	
	
	
}
