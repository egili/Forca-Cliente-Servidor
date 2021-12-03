import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

/*
 validar se o uso dessa classe eh necessario e
 eventualmente fazer todas as alteracoes
 Classe ControladorDeLetrasJaDigitadas = Lunara;
 */

public class ControladorDeLetrasJaDigitadas implements Cloneable
{
    private String letrasJaDigitadas;
    public 

    public ControladorDeLetrasJaDigitadas ()
    {
        this.letrasJaDigitadas = "";
    }

    public boolean isJaDigitada (char letra)
    {
        int i = this.letrasJaDigitadas.indexOf(letra);
        if (i == -1)

            return false;

        return true;
    }

    public void registre (char letra) throws Exception
    {
        if(isJaDigitada(letra))
            throw new Exception("Letra ja digitada");
        
        boolean num = false;
        try{
            double numero = Double.parseDouble(letra+"");
            num = true;
        }catch (NumberFormatException erro){
            num = false;
        }
        if (num) {
            throw new Exception("n�o pode ser numero");
        }
        
        this.letrasJaDigitadas = this.letrasJaDigitadas + letra;
 
    }
    @Override
    public String toString ()
    {
        String ret = "";
        for (int letra = 0; letra < this.letrasJaDigitadas.length(); letra++){
            ret += letrasJaDigitadas.charAt(letra) + ",";
        }

        return  ret;
    }
    
    @Override
    public boolean equals (Object obj) {

        if (this == obj) return true;

        if (obj == null) return false;

        if (obj.getClass() != ControladorDeLetrasJaDigitadas.class)
            return false;

        if(this.letrasJaDigitadas != ((ControladorDeLetrasJaDigitadas) obj) .letrasJaDigitadas)
            return false;

        return  true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 17;
        ret = ret * 17 + new String(letrasJaDigitadas).hashCode() ;
        if (ret < 0)
            ret = - ret;
        return  ret;
    }

    public ControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas c) throws Exception // construtor de copia
    {
        if(c == null)
            throw new Exception("c era null");
        this.letrasJaDigitadas = c.letrasJaDigitadas;
    }

    public Object clone ()
    {
        ControladorDeLetrasJaDigitadas ret = null;
        try{
            ret = new ControladorDeLetrasJaDigitadas(this);
        }
        catch (Exception ignored) {}        }
        return ret;
    }
}