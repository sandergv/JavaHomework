package controller;

import database.MysqlConnection;
import datos.Comentario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ComentarioController {

    public static Comentario getComentarioByCodigo(int codigo){
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("comentario", "*", "CODIGOCOMENTARIO="+codigo);
        Comentario c = new Comentario();
        try {
            if(rs.next()){
                c.setCodigo(rs.getInt("CODIGOCOMENTARIO"));
                c.setCodigoEmpleado(rs.getInt("CODIGOEMPLEADO"));
                c.setComentario(rs.getString("DESCRIPCIONCOMENTARIO"));
                c.setFecha(rs.getString("FECHACOMENTARIO"));
            }

            c.setEmpleado(EmpleadoController.getEmpleadoByCodigo(c.getCodigoEmpleado()));
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
        return c;
    }

    public static ArrayList<Comentario> getComentarios(){
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("comentario", "*", "");
        Comentario c;
        ArrayList<Comentario> com = new ArrayList<>();
        try {
            while (rs.next()){
                c = new Comentario();
                c.setCodigo(rs.getInt("CODIGOCOMENTARIO"));
                c.setCodigoEmpleado(rs.getInt("CODIGOEMPLEADO"));
                c.setComentario(rs.getString("DESCRIPCIONCOMENTARIO"));
                c.setFecha(rs.getString("FECHACOMENTARIO"));

                c.setEmpleado(EmpleadoController.getEmpleadoByCodigo(c.getCodigoEmpleado()));

                com.add(c);
            }
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
        return com;
    }

    public static void nuevoComentario(Comentario c){
        MysqlConnection.conectar();
        String[] columns = {"CODIGOEMPLEADO", "DESCRIPCIOMCOMENTARIO"};
        PreparedStatement pst = MysqlConnection.insert("comentario", columns);
        try{
            pst.setInt(1, c.getCodigoEmpleado());
            pst.setString(2, c.getComentario());
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
    }
    public static void borrarComentario(int codigo){
        MysqlConnection.delete("comentario", "CODIGOCOMENTARIO="+codigo);
    }
    public static void updateComentario(int codigo, String value){
        MysqlConnection.update("comentario", "DESCRIPCIONCOMENTARIO", value, "CODIGOCOMENTARIO="+codigo);
    }
}
