/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.MysqlConnection;
import datos.Provincia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Controlador de Provincia para la comunicación con la base de datos
 * @author Alexander
 */
public class ProvinciaController {
    
    /**
     * Consultar por provincia con el codigo
     * @param codigo Codigo deprovincia
     * @return Objeto Provincia
     */
    public static Provincia getProvinciaByCodigo(int codigo){
        Provincia provincia = new Provincia();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("provincia", "*", "CODIGOPROVINCIA=" + codigo);
        try{
            if(rs.next()){
                provincia.setCodigo(rs.getInt("CODIGOPROVINCIA"));
                provincia.setNombre(rs.getString("NOMBREPROVINCIA"));
            }
        }
        catch(Exception e){
            System.out.println("ProvinciaControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return provincia;
    }

    /**
     * Consultar por todas las Provincias
     * @return ArrayList de Provincias
     */
    public static ArrayList<Provincia> getProvincias(){
        Provincia provincia;
        ArrayList<Provincia> provincias = new ArrayList<Provincia>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("provincia", "*", "CODIGOPROVINCIA=");
        try{
            while (rs.next()){
                provincia =  new Provincia();
                provincia.setCodigo(rs.getInt("CODIGOPROVINCIA"));
                provincia.setNombre(rs.getString("NOMBREPROVINCIA"));
                provincias.add(provincia);
            }
        }
        catch(Exception e){
            System.out.println("ProvinciaControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return provincias;
    }

    /**
     * Insertar nueva Provincia
     * @param nombre String nombre provincia
     */
    public static void nuevaProvincia(String nombre){
        String[] columnas = {"NOMBREPROVINCIA"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("provincia", columnas);
        try{
            pst.setString(1, nombre);
            pst.execute();
            pst.close();
        }
        catch (Exception e){
            System.out.println("ProvinciaController");
        }
        MysqlConnection.desconectar();

    }

    /**
     * Eliminar Provincia
     * @param codigo 
     */
    public static void borrarProvincia(int codigo){
        MysqlConnection.delete("provincia", "CODIGOPROVINCIA="+codigo);
    }
}
