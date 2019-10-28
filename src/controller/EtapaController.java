package controller;

import datos.Etapa;
import database.MysqlConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EtapaController {

    public static Etapa getEtapaByCodigo(int codigo){
        Etapa et = new Etapa();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("etapa", "*", "CODIGOETAPA=" + codigo);
        try{
            if(rs.next()){
                et.setCodigo(rs.getInt("CODIGOETAPA"));
                et.setDescripcion(rs.getString("DESCRIPCIONETAPA"));
                et.setFechaTermino(rs.getString("FECHATERMINOETAPA"));
                et.setFechaInicio(rs.getString("FECHAINICIOETAPA"));
            }
        }
        catch(Exception e){
            System.out.println("EtapaControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return et;
    }

    public static ArrayList<Etapa> getEtapas(){
        ArrayList<Etapa> el = new ArrayList<>();
        Etapa et;
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("etapa", "*", "");
        try{
            while (rs.next()){
                et = new Etapa();
                et.setCodigo(rs.getInt("CODIGOETAPA"));
                et.setDescripcion(rs.getString("DESCRIPCIONETAPA"));
                et.setFechaTermino(rs.getString("FECHATERMINOETAPA"));
                et.setFechaInicio(rs.getString("FECHAINICIOETAPA"));
            }
        }
        catch(Exception e){
            System.out.println("ProvinciaControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return el;
    }
    public static void nuevaEtapa(Etapa et){
        String[] columnas = {"DESCRIPCIONETAPA", "FECHATERMINOETAPA", "FECHAINICIOETAPA"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("etapa", columnas);
        try{
            pst.setString(1, et.getDescripcion());
            pst.setString(2, et.getFechaTermino());
            pst.setString(3, et.getFechaInicio());

            pst.execute();
            pst.close();
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
    }
    public static void borrarEtapa(int codigo){
        MysqlConnection.delete("etapa", "CODIGOETAPA="+codigo);
    }
}
