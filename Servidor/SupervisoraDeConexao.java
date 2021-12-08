
import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread 
{
	private double valor = 0;
	private Parceiro usuario;
	private Socket conexao;
	//private final ArrayList<Parceiro> usuarios;
	private Grupo<Cliente> usuarios;
	private static Palavra palavra;
	private static Tracinhos tracinhos;
	private static ControladoraDePartida controladoradepartida;
	private static ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;

	public SupervisoraDeConexao
    (Socket conexao, ArrayList<Parceiro> usuarios)
    throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.usuarios = usuarios;
    }

	public void run ()
    {

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
            new ObjectOutputStream(
            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }
        
        ObjectInputStream receptor=null;
        try
        {
            receptor=
            new ObjectInputStream(
            this.conexao.getInputStream());
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread
            
            return;
        }

        try
        {
            this.usuario =
            new Parceiro (this.conexao,
                          receptor,
                          transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos

        try
        {
            synchronized (this.usuarios)
            {
                this.usuarios.inserirJogadorNoGrupo(); //parametro deste método é um objeto da classe Cliente
                if(usuarios.isCheio())
                {
                	palavra = BancoDePalavras.getPalavraSorteada();
                	tracinhos = new Tracinhos(palavra.getTamanho());
                    int jogadores = usuarios.length;
           
                    for (Parceiro jogador: this.usuarios) {
                       jogador.receba(new ComunicadoComecouPartida()); 
                    }
                    this.usuarios.get(0).receba(new ComunicadoDeVez());
                }
                System.out.println("Infelizmente servidor estï¿½ cheio");
            }
        } catch(Exception e) {}

            for(;;)
            {
               Comunicado comunicado;
			try {
				comunicado = this.usuario.envie();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
               
               if (comunicado == null)
            	   return;
               else if (comunicado instanceof PedidoParaEntrar)
               {
            	int posJogador = this.usuarios.indexOf(usuario);
            	if (posJogador > 2)
            	{
            	 try {
					usuario.receba(new ComunicadoSalaCheia());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	 
            	}
               }
               else if (comunicado instanceof PedidoDeLetra ) {
                   boolean jaDigitadas = false;
                   boolean palavraTemLetra =false;
                   PedidoDeLetra pedidoDeLetra = (PedidoDeLetra)comunicado;
                   char letra = pedidoDeLetra.getLetra();
                   if (controladorDeLetrasJaDigitadas.isJaDigitada(letra))
                       jaDigitadas = true;
					else
					{
						try {
							controladorDeLetrasJaDigitadas.registrarletra(letra);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						int qtd = palavra.getQuantidade (letra);

						if (qtd==0)
						{
							System.err.println ("A palavra nao tem essa letra!\n");
							try {
								controladoradepartida.errar();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
						{
							for (int i=0; i<qtd; i++)
							{
								int posicao;
								try {
									posicao = palavra.getPosicaoDaIezimaOcorrencia (i,letra);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								try {
									tracinhos.revele (posicao, letra);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							try {
								this.usuario.receba(new ComunicadoDeVitoria());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
                }
                   ComunicadoDeLetra comunicadoDeLetra = new ComunicadoDeLetra();
                   comunicadoDeLetra.setJaDigitada(jaDigitadas);
                   comunicadoDeLetra.setPalavraTemLetra(palavraTemLetra);
                   try {
					this.usuario.receba(comunicadoDeLetra);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               }
        }
    }  
    
}
