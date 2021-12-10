package Servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import ClassesComuns.*;

public class AceitadoraDeConexao extends Thread{
	private ServerSocket servidor;
	private ArrayList<Parceiro> usuarios;
	private ControladoraDePartida controladoraPartida;
	private Boolean isComecou = false;

	public AceitadoraDeConexao(String porta, ArrayList<Parceiro> usuarios) throws Exception {
		if(porta == null)
			throw new Exception("Insira uma porta valida");
		
		try
		{
			servidor = new ServerSocket(Integer.parseInt(porta));
		}
		catch(Exception e)
		{
			System.err.println("Porta invalida");
		}
		
		if(usuarios == null)
			throw new Exception("Usuarios ausentes");
		
		this.usuarios = usuarios;
		this.controladoraPartida = new ControladoraDePartida(this.usuarios);

	}
	
	public void run() {

		while(true) {
			Socket conexao;

			try
			{
				conexao = servidor.accept();
			}
			catch (Exception e)
			{
				continue;
			}

			synchronized (usuarios){
				if(usuarios.size() == 3)
				{
					try {
						conexao.close();
						continue;
					}catch (Exception e){

					}
				}
			}

			SupervisoraDeConexao supervisora = null;

			try {
				supervisora = new SupervisoraDeConexao(conexao, usuarios, controladoraPartida);

				synchronized (controladoraPartida.getSupervisoras())
				{
					controladoraPartida.addSupervisora(supervisora);
				}
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
			}

			supervisora.start();

			try
			{
				Thread.sleep(500);
			}
			catch(Exception ignored){}


			synchronized (usuarios)
			{
				if (usuarios.size() == 3 && !isComecou)
				{
					isComecou = true;
					try
					{
						for (Parceiro usuario : usuarios)
							usuario.receba(new ComunicadoDeVez(null));

					} catch (Exception e) {}

				}
				else if (isComecou)
				{
					try
					{
						if (usuarios.size() ==2)
						usuarios.get(1).receba(new ComunicadoDeComecoPartida());
						else
							if (usuarios.size() ==3)
								usuarios.get(2).receba(new ComunicadoDeComecoPartida());
					}
					catch (Exception e)
					{}
				}
			}

		}
	}

	@Override
	public String toString ()
	{
		String ret ="";

		ret = "Servidor: " + servidor + "\nPartida iniciada: " + isComecou + "\n Controladora De Partida:" + controladoraPartida;

		return ret;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) 
			return true;

		if (o == null)
			return false;

		if (this.getClass() != o.getClass())
			return false;

		AceitadoraDeConexao act = (AceitadoraDeConexao) o;

		if (!this.servidor.equals(act.servidor))
			return false;

		if (!this.controladoraPartida.equals(act.controladoraPartida)) 
			return false;

		if(!this.isComecou.equals(act.isComecou)) 
			return false;


		if (this.usuarios.size() != act.usuarios.size())
			return false;

		for (int i = 0; i < this.usuarios.size() ; i++)
			if (!this.usuarios.get(i).equals(act.usuarios.get(i)))
				return false;

			return true;
	}

	@Override
	public int hashCode()
	{
		int ret = 31;

		ret = ret * 7 + new Boolean(this.isComecou).hashCode();
		ret = ret * 7 + this.controladoraPartida.hashCode();
		ret = ret * 7 + this.servidor.hashCode();

		for (int i = 0; i < usuarios.size(); i++){
			ret = ret * 7 + usuarios.get(i).hashCode();
		}

		if(ret < 0)
			ret = -ret;
		
		return ret;
	}
}
