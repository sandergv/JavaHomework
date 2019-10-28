/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.MysqlConnection;
import datos.Proyecto;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class ProyectoController {
    
    public static Proyecto getProyectoByCodigo(int codigo){
        MysqlConnection.conectar();
        Proyecto p = new Proyecto();
        
        ResultSet rs = MysqlConnection.select("proyecto", "*", "CODIGOPROYECTO=" + codigo);
        
        try {
            if(rs.next()){

                p.setCodigo(rs.getInt("CODIGOPROYECTO"));
                p.setCodigoTipo(rs.getInt("CODIGOTIPOPROYECTO"));
                p.setNombre(rs.getString("NOMBREPROYECTO"));
                p.setHoraEstimada(rs.getInt("HORASESTIMADAS"));
                p.setFechaInicio(rs.getString("FECHAINICIO"));                 
                p.setFechaTerminoPlanificada(rs.getString("FECHATERMINOPLANIFICADA"));
                
            }   
            

            p.setTipoProyecto(getTipoProyecto(p.getCodigoTipo()));
            
        } catch (Exception e) {
        }
        MysqlConnection.desconectar();

        return p;
    }
    
    public static ArrayList<Proyecto> getProyectos(){
        ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
        Proyecto p;
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("proyecto", "*", "");
        
        try {
            while(rs.next()){
                p = new Proyecto();
                
                p.setCodigo(rs.getInt("CODIGOPROYECTO"));
                p.setCodigoTipo(rs.getInt("CODIGOTIPOPROYECTO"));
                p.setNombre(rs.getString("NOMBREPROYECTO"));
                p.setHoraEstimada(rs.getInt("HORASESTIMADAS"));
                p.setFechaInicio(rs.getString("FECHAINICIO"));                 
                p.setFechaTerminoPlanificada(rs.getString("FECHATERMINOPLANIFICADA"));
                
                proyectos.add(p);
            }   
            

        } catch (Exception e) {
        }
        MysqlConnection.desconectar();
        return proyectos;
    }
    
    public static String getTipoProyecto(int codigo){
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("tipo_proyecto", "*", "CODIGOTIPOPROYECTO=" + codigo);
        String tp = "";
        try {
            if(rs.next())
                tp = rs.getString("TIPOPROYECTO");
            
        } catch (Exception e) {
        }
        MysqlConnection.desconectar();
        return tp;
    }
    
}
