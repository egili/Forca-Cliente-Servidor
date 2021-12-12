

//import ClassesComuns.*;

public class TratadoraDeComunicados extends Thread {

	private Parceiro servidor;
	
	public TratadoraDeComunicados(Parceiro servidor) throws Exception {
		
		if(servidor == null)
			throw new Exception ("servidor nulo");
		
		this.servidor = servidor;
	}
	
	@Override
	public void run() {

		Comunicado comunicado = null;
		
		for(;;){
			
			try {
				comunicado = servidor.espie();
				
			} catch (Exception erro1) {}
			
			try {
				
				if(comunicado instanceof ComunicadoDeDesligamento) {
					
					System.err.println("\n o servidor sera encerrado agora");
					System.err.println("volte mais tarde \n");
					
					servidor.adeus();
					System.exit(0);
				}
				else if(comunicado instanceof ComunicadoDeVitoria) {
					
					System.out.println("voce venceu a partida");
					servidor.envie();
				}
				else if(comunicado instanceof ComunicadoDePerda) {
					
					System.err.println("\n voce perdeu e sera desconectado do servidor");
					System.err.println("\n obrigado e ate a proxima");
					
					servidor.envie();
				}
			} catch (Exception erro2) {}
		}
	}
	
	@Override
	public String toString() {
	
		return "Servidor: " + servidor;
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if(this == obj)
			return true;
		
		if(obj == null)
			return false;
		
		if(this.getClass() != obj.getClass())
			return false;
		
		TratadoraDeComunicados tratadora = (TratadoraDeComunicados) obj;
		
		return this.servidor.equals(tratadora.servidor);
	}
	
	@Override
	public int hashCode() {
		
		int ret = 31;
		
		ret = ret * 13 + servidor.hashCode();
		
		return ret < 0 ? -ret : ret;
	}
}
