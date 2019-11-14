package controller;

import database.MysqlConnection;
import datos.Recurso;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RecursoController {

    public static Recurso getRecursoByCodigo(int codigo){
        Recurso recurso = new Recurso();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("recurso", "*", "CODIGORECURSO=" + codigo);
        try{
            if(rs.next()){
                recurso.setCodigo(rs.getInt("CODIGORECURSO"));
                recurso.setNombre(rs.getString("NOMBRERECURSO"));
                recurso.setDescripcion(rs.getString("DESCRIPCIONRECURSO"));
                recurso.setValor(rs.getInt("VALORRECURSO"));
            }
        }
        catch(Exception e){
            System.out.println("RECURSOControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return recurso;
    }
    
    public static Recurso getRecursoByNombre(String nombre){
               Recurso recurso = new Recurso();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("recurso", "*", String.format("NOMBRERECURSO='%s'", nombre));
        try{
            if(rs.next()){
                recurso.setCodigo(rs.getInt("CODIGORECURSO"));
                recurso.setNombre(rs.getString("NOMBRERECURSO"));
                recurso.setDescripcion(rs.getString("DESCRIPCIONRECURSO"));
                recurso.setValor(rs.getInt("VALORRECURSO"));
            }
        }
        catch(Exception e){
            System.out.println("RECURSOControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return recurso; 
    }

    public static ArrayList<Recurso> getRecursos(){
        ArrayList<Recurso> recursos = new ArrayList<>();
        Recurso recurso;
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("recurso", "*", "");
        try{
            while (rs.next()){
                recurso = new Recurso();
                recurso.setCodigo(rs.getInt("CODIGORECURSO"));
                recurso.setNombre(rs.getString("NOMBRERECURSO"));
                recurso.setDescripcion(rs.getString("DESCRIPCIONRECURSO"));
                recurso.setValor(rs.getInt("VALORRECURSO"));
                
                recursos.add(recurso);
            }
        }
        catch(Exception e){
            System.out.println("RECURSOControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return recursos;
    }

    public static void nuevoRecurso(Recurso r){
        String[] columnas = {"NOMBRERECURSO", "DESCRIPCIONRECURSO", "VALORRECURSO"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("recurso", columnas);
        try{
            pst.setString(1, r.getNombre());
            pst.setString(2, r.getDescripcion());
            pst.setInt(3, r.getValor());

            pst.execute();
            pst.close();
        }
        catch (Exception e){
            System.out.println("ProvinciaController");
        }
        MysqlConnection.desconectar();

    }

    public static void borrarRecurso(int codigo){
        MysqlConnection.delete("recurso", "CODIGORECURSO="+codigo);
    }
}
