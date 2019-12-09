/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.MysqlConnection;
import datos.Comuna;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Controlador de Comuna para la interacciónconla base de dato
 * @author Alexander
 */
public class ComunaController {
    
    /**
     * Consultar por Comuna con el codigo
     * @param codigo Codigo de Comuna
     * @return Objeto Comuna
     */
    public static Comuna getComunaByCodigo(int codigo){
       Comuna comuna = new Comuna();
       MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("comuna", "*", "CODIGOCOMUNA=" + codigo);
        try{
            if(rs.next())
                comuna.setCodigo(rs.getInt("CODIGOCOMUNA"));
                comuna.setCodigoProvincia(rs.getInt("CODIGOPROVINCIA"));
                comuna.setNombre(rs.getString("NOMBRECOMUNA"));
        }
        catch(Exception e){
            System.out.println("ComunaControler: ");
            e.printStackTrace();
        }
        comuna.setProvincia(ProvinciaController.getProvinciaByCodigo(comuna.getCodigoProvincia()));
        MysqlConnection.desconectar();
        return comuna;
    }
    
    /**
     * Consultar por Comuna por su nombre
     * @param name Nombre de Comuna
     * @return Objeto Comuna
     */
    public static Comuna getComunaByName(String name){
        Comuna comuna = new Comuna();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("comuna", "*", String.format("NOMBRECOMUNA='%s'", name));
        try{
            if(rs.next()){
                comuna.setCodigo(rs.getInt("CODIGOCOMUNA"));
                comuna.setCodigoProvincia(rs.getInt("CODIGOPROVINCIA"));
                comuna.setNombre(rs.getString("NOMBRECOMUNA"));
            }
        }
        catch(Exception e){
            System.out.println("ComunaControler: ");
            e.printStackTrace();
        }
        comuna.setProvincia(ProvinciaController.getProvinciaByCodigo(comuna.getCodigoProvincia()));
        MysqlConnection.desconectar();
        return comuna;
    }
    
    /**
     * Consultar por todas las Comunas
     * @return ArrayList de Comunas
     */
    public static ArrayList<Comuna> getComunas(){
        ArrayList<Comuna> comunas = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("comuna", "*", "");
        
        try {
            while(rs.next()){
                Comuna c = new Comuna();
                c.setCodigo(rs.getInt("CODIGOCOMUNA"));
                c.setCodigoProvincia(rs.getInt("CODIGOPROVINCIA"));
                c.setNombre(rs.getString("NOMBRECOMUNA"));
                
                comunas.add(c);
            }
        } catch (Exception e) {
        }
        MysqlConnection.desconectar();
        return comunas;
    }

    /**
     * Insertar nueva Comuna
     * @param comuna 
     */
    public static void nuevaComuna(Comuna comuna){
        String[] columnas = {"CODIGOPROVINCIA", "NOMBRECOMUNA"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("comuna", columnas);
        try{
            pst.setInt(1, comuna.getCodigoProvincia());
            pst.setString(2, comuna.getNombre());
            pst.execute();
            pst.close();
        }
        catch (Exception e){
            System.out.println("ComunaController");
        }
        MysqlConnection.desconectar();
    }
}
