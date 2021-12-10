package Servidor;

import ClassesComuns.*;

import java.net.Socket;
import java.util.ArrayList;

public class ControladoraDePartida {
    private final ArrayList<Parceiro> jogadores;
    private final ArrayList<SupervisoraDeConexao> supervisoras = new ArrayList<>();
    private int j = 0;

    public ControladoraDePartida (ArrayList<Parceiro> jogadores) throws Exception {
        if (jogadores == null)
            throw new Exception("Usuario passado pelo parametro eh nulo");

        this.jogadores = jogadores;
    }

    public void proximoJogador() {
        synchronized (jogadores) {
            j++;
            if (j == jogadores.size())
                j = 0;
        }
    }

    public boolean pode(Parceiro cliente) throws Exception {
        try {
            synchronized (jogadores) {

                return cliente == jogadores.get(j);
            }
        } catch (Exception e) {
            throw new Exception("UsuÃ¡rio retirado");
        }
    }

    public ArrayList<Parceiro> getUsuarios(){
        return jogadores;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void addSupervisora(SupervisoraDeConexao sup) throws Exception{
        if(sup == null)
            throw new Exception("Supervisora nula");

        supervisoras.add(sup);
    }


    public ArrayList<SupervisoraDeConexao> getSupervisoras()
    {
        return supervisoras;
    }

    public void fimThreadSup()
    {
        synchronized (supervisoras){
            for (int i = 0; i < supervisoras.size(); i++){
                supervisoras.get(i).fim = false;
            }
        }
    }

    @Override
    public String toString() {
        return "GerenciadoraDeRodada{" +
                "usuarios=" + jogadores +
                ", j=" + j +
                '}';
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if(obj == null)
            return false;

        if(obj.getClass() != this.getClass())
            return false;

        ControladoraDePartida controladora = (ControladoraDePartida) obj;

        if(controladora.j != j)
            return false;
    
        if(controladora.jogadores.size() == jogadores.size()){
            for(int i = 0; i < jogadores.size(); i++){
                if(!jogadores.get(i).equals(controladora.jogadores.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int ret = 31;
     
        ret = ret * 7 + Integer.valueOf(j).hashCode();

        for (int i = 0; i < jogadores.size(); i++){
            ret = ret * 7 + jogadores.get(i).hashCode();
        }

        if(ret < 0 )
        	ret = -ret;
        
        return ret;
    }
}
