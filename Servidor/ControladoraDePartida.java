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


/* Edi�ao dia 02-12-2021 por Lunara(como resultado da reuni�o de mesmo dia):
 *  Colocar m�todo para VezDeJogar para controlar quando cada jogador estar� ativo pra jogar; 
 * classe Grupo controla as a��es do jogador e devolve pro cliente. 
 * A classe Supervisora recebe os pedidos do cliente 
 * e devolve os comunicados ao cliente. 
 * NOVO NOME DA CLASSE GRUPO = CONTROLADORADEPARTIDA.java
 * Classe ControladoraDePartida = Eliseu
 * Classe SupervidoraDeConexao = Leandro*/


public class ControladoraDePartida {

}