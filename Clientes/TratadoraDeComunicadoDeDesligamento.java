

public class TratadoraDeComunicadoDeDesligamento extends Thread {
/*Conforme o arquivo orginal do projeto Forca (servidor de Continhas), a TratadoraDeComunicadoDeDesligamento vai controlar
 * o ComunicadoDeDesligamento que o servidor manda e que o cliente precisa devolver uma resposta. A tratadora é a ponte entre
 * cliente e servidor.
 * Class TratadoraDeComunicadoDeDesligamento = Elys; */
	private Parceiro servidor;

    public TratadoraDeComunicadoDeDesligamento (Parceiro servidor) throws Exception
    {
        if (servidor==null)
            throw new Exception ("Porta invalida");

        this.servidor = servidor;
    }

    public void run ()
    {
        for(;;)
        {
			try
			{
				if (this.servidor.espie() instanceof ComunicadoDeDesligamento)
				{
					System.out.println ("\nO servidor será desligado agora;");
				    System.err.println ("volte mais tarde!\n");
				    System.exit(0);
				}
			}
			catch (Exception erro)
			{}
        }
    }
}


