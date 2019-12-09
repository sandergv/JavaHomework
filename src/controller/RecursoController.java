package controller;

import database.MysqlConnection;
import datos.Recurso;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Controlador de Recursos para lainteracción con base de datos
 * @author Alexander
 */
public class RecursoController {

    /**
     * Consultar por Recurso con elcodigo
     * @param codigo Codigo de recurso
     * @return Objeto Recurso
     */
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
    
    /**
     * Consultar por Recurso con su nombre
     * @param nombre Nombre de Recurso
     * @return Objeto Recurso
     */
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

    /**
     * Consulta por todos los Recursos
     * @return ArrayList de Recursos
     */
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

    /**
     * Insertar nuevo Recurso
     * @param r Objeto Recurso
     */
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

    /**
     * Borrar recurso
     * @param codigo codigo Recurso
     */
    public static void borrarRecurso(int codigo){
        MysqlConnection.delete("recurso", "CODIGORECURSO="+codigo);
    }
}
