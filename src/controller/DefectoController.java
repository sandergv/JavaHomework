package controller;

import database.MysqlConnection;
import datos.Defecto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DefectoController {

    public static Defecto getDefectoByCodigo(int codigo){
        Defecto def = new Defecto();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("defecto", "*", "CODIGODEFECTO="+codigo);

        try {
            if(rs.next()){
                def.setCodigo(codigo);
                def.setObservacion(rs.getString("OBSERVACIONDEFECTO"));
            }
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
        return def;
    }
    public static ArrayList<Defecto> getDefectos(){
        ArrayList<Defecto> d = new ArrayList<>();
        Defecto def;
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("defecto", "*", "");

        try {
            while (rs.next()){
                def = new Defecto();
                def.setCodigo(rs.getInt("CODIGODEFECTO"));
                def.setObservacion(rs.getString("OBSERVACIONDEFECTO"));
            }
        }
        catch (Exception e){
        }
        MysqlConnection.desconectar();
        return d;
    }
    public static void nuevoDefecto(Defecto d){
        MysqlConnection.conectar();
        String[] columns = {"OBSERVACIONDEFECTO"};
        PreparedStatement pst = MysqlConnection.insert("defecto", columns);

        try{
            pst.setString(1, d.getObservacion());
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }
    public static void borrarDefecto(int codigo){
        MysqlConnection.delete("defecto", "CODIGODEFECTO="+codigo);
    }
}
