
public class BancoDePalavras
{
    public static String[] palavras = 
    {
    		"Axioma",
    		"Azulejo",
    		"Caleidoscopio",
    		"Catarro",
    		"Cocar",
    		"Crespo",
    		"Cripta",
    		"Duplex",
    		"Ictericia",
    		"Inconstitucionalissimamente",
    		"Intrigante",
    		"Ornitorrinco",
    		"Otorrinolaringologista",
    		"Paralelepipedo",
    		"Proparoxitona",
    		"Topazio",
    		"Vertiginoso",
    		"Xilofone"
    };

    public static Palavra getPalavraSorteada ()
    {
        Palavra palavra = null;

        try
        {
            palavra = new Palavra (BancoDePalavras.palavras[(int)(Math.random() * BancoDePalavras.palavras.length)]);
        }
        catch (Exception e)
        {}

        return palavra;
    }
}
