import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    private double              valor=0;
    private Parceiro            usuario;
    private Socket              conexao;
    private ArrayList<Parceiro> usuarios;
    private static Palavra   palavra;
    private static Tracinhos tracinhos;
    private static ControladorDeLetrasJaDigitadas  controladorDeLetrasJaDigitadas;

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
        catch (Exception err0)
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
                this.usuarios.add  (this.usuario);
                if(usuarios.size()%3==0)
                {
                	
                	palavra = BancoDePalavras.getPalavraSorteada();
                	tracinhos = new Tracinhos(palavra.getTamanho());
                    int jogadores = usuarios.size();

                    for (Parceiro jogador: this.usuarios) {
                       jogador.receba(new ControladorDePartida());
                    }

                }
                System.out.println("Infelizmente servidor está cheio");
            }
           

            for(;;)
            {
               Comunicado comunicado = this.usuario.envie();
               
               if (comunicado == null)
            	   return;
               else if (comunicado instanceof PedidoDeLetra ) {
            	   if (controladorDeLetrasJaDigitadas.isJaDigitada (letra))
						System.err.println ("Essa letra ja foi digitada!\n");
					else
					{
						controladorDeLetrasJaDigitadas.registre (letra);

						int qtd = palavra.getQuantidade (letra);

						if (qtd==0)
						{
							System.err.println ("A palavra nao tem essa letra!\n");
							controladorDeErros.registreUmErro ();
						}
						else
						{
							for (int i=0; i<qtd; i++)
							{
								int posicao = palavra.getPosicaoDaIezimaOcorrencia (i,letra);
								tracinhos.revele (posicao, letra);
							}
							this.usuario.receba(new ComunicadoDeVitoria());
						}
                }
               }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}
