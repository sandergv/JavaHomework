/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.MysqlConnection;
import datos.Comuna;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Alexander
 */
public class ComunaController {
    
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
