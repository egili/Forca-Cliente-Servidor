public class BancoDePalavras
{
    private static String[] palavras = 
    {
    		"Axioma",
    		"Azulejo",
    		"Top�zio",
    		"Catarro",
    		"Co�ar",
    		"Crespo",
    		"Cripta",
    		"Duplex",
    		"Icter�cia",
    		"Intrigante",
    		"Vertiginoso",
    		"Xilofone"
    };

    public static Palavra getPalavraSorteada ()
    {
        Palavra palavra = null;

        try
        {
            palavra =
            new Palavra (BancoDePalavras.palavras[
            (int)(Math.random() * BancoDePalavras.palavras.length)]);
        }
        catch (Exception e)
        {}

        return palavra;
    }
}