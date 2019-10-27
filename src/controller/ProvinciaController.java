/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.MysqlConnection;
import datos.Provincia;
import java.sql.ResultSet;

/**
 *
 * @author Alexander
 */
public class ProvinciaController {
    
    public static Provincia getProvinciaByCodigo(int codigo){
        Provincia provincia = new Provincia();
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
}
