/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.MysqlConnection;
import datos.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

                p.setCodigo(codigo);
                p.setCodigoTipo(rs.getInt("CODIGOTIPOPROYECTO"));
                p.setNombre(rs.getString("NOMBREPROYECTO"));
                p.setHoraEstimada(rs.getInt("HORASESTIMADAS"));
                p.setFechaInicio(rs.getString("FECHAINICIO"));                 
                p.setFechaTerminoPlanificada(rs.getString("FECHATERMINOPLANIFICADA"));
                
            }   
            p.setTipoProyecto(getTipoProyecto(codigo));
            p.setClientes(getClientes(codigo));
            p.setEmpleados(getEmpleados(codigo));
            p.setEtapas(getEtapas(codigo));
            p.setEstados(getEstados(codigo));
            p.setRecursos(getRecursos(codigo));

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

                int codigo = p.getCodigo();
                p.setTipoProyecto(getTipoProyecto(p.getCodigoTipo()));
                p.setEtapas(getEtapas(codigo));
                p.setEstados(getEstados(codigo));

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

    public static void nuevoProyecto(Proyecto p){
        String[] columns = {"CODIGOTIPOPROYECTO", "NOMBREPROYECTO", "HORASESTIMADAS", "FECHAINICIO", "FECHATERMINOPLANIFICADA"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("proyecto", columns);

        try{
            pst.setInt(1, p.getCodigoTipo());
            pst.setString(2, p.getNombre());
            pst.setInt(3, p.getHoraEstimada());
            pst.setString(4, p.getFechaInicio());
            pst.setString(5, p.getFechaTerminoPlanificada());

            pst.execute();
            pst.close();

            p.getClientes().forEach(c -> {
                addClienteProyecto(p.getCodigo(), c.getRut());
            });
            p.getRecursos().forEach(r -> {
                addRecursoProyecto(p.getCodigo(), r.getCodigo());
            });
            p.getEmpleados().forEach(em -> {
                addEmpleadoProyecto(p.getCodigo(), em.getCodigo());
            });
            p.getEtapas().forEach(et -> {
                addEtapaProyecto(p.getCodigo(), et.getCodigo());
            });
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
    }

    public static void addClienteProyecto(int codigoProyecto, String RutCliente){
        String[] columns = {"RUTCLIENTE", "CODIGOPROYECTO"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("proyectocliente", columns);
        try {
            pst.setString(1, RutCliente);
            pst.setInt(2, codigoProyecto);
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }
    public static void addEmpleadoProyecto(int codigoProyecto, int codigoEmpleado){
        String[] columns = {"CODIGOEMPLEADO", "CODIGOPROYECTO"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("empleadoproyecto", columns);
        try {
            pst.setInt(1, codigoEmpleado);
            pst.setInt(2, codigoProyecto);
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }

    public static void addRecursoProyecto(int codigoProyecto,int codigoRecurso){
        String[] columns = {"CODIGORECURSO", "CODIGOPROYECTO"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("proyectorecurso", columns);
        try {
            pst.setInt(1, codigoRecurso);
            pst.setInt(2, codigoProyecto);
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }
    public static void addEtapaProyecto(int codigoProyecto,int codigoEtapa){
        String[] columns = {"CODIGOETAPA", "CODIGOPROYECTO"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("etapaproyecto", columns);
        try {
            pst.setInt(1, codigoEtapa);
            pst.setInt(2, codigoProyecto);
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }
    public static ArrayList<Cliente> getClientes(int codigo){
        ArrayList<Cliente> c = new ArrayList<>();
        ArrayList<String> rc = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("proyectocliente", "*", "CODIGOPROYECTO="+codigo);
        try {
            while (rs.next()) {
                rc.add(rs.getString("RUTCLIENTE"));
            }
            rs.close();
            for(String r: rc){
                c.add(ClienteController.getClienteByRut(r));
            }

        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
        return c;
    }

    public static  ArrayList<Empleado> getEmpleados(int codigo){
        ArrayList<Empleado> c = new ArrayList<>();
        ArrayList<Integer> rc = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("empleadoproyecto", "*", "CODIGOPROYECTO="+codigo);
        try {
            while (rs.next()) {
                rc.add(rs.getInt("CODIGOEMPLEADO"));
            }
            rs.close();
            for(int r: rc){
                c.add(EmpleadoController.getEmpleadoByCodigo(r));
            }
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
        return c;
    }

    private static ArrayList<Etapa> getEtapas(int codigo){
        ArrayList<Etapa> c = new ArrayList<>();
        ArrayList<Integer> rc = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("etapaproyecto", "*", "CODIGOPROYECTO="+codigo);
        try {
            while (rs.next()) {
                rc.add(rs.getInt("CODIGOETAPA"));
            }
            rs.close();
            for(int r: rc){
                c.add(EtapaController.getEtapaByCodigo(r));
            }
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
        return c;
    }

    public static ArrayList<String> getEstados(int codigo){
        ArrayList<String> c = new ArrayList<>();
        ArrayList<Integer> rc = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("proyectoestadoproyecto", "*", "CODIGOPROYECTO="+codigo);
        try {
            while (rs.next()) {
                rc.add(rs.getInt("CODIGOESTADO"));
            }
            rs.close();
            for(int r: rc){
                rs = MysqlConnection.select("estadoproyecto", "*", "CODIGOESTADOPROYECTO"+r);
                while(rs.next()){
                    c.add(rs.getString("DESCRIPCIONESTADOPROYECTO")); // estado controller
                }
            }
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
        return c;
    }

    private static ArrayList<Recurso> getRecursos(int codigo){
        ArrayList<Recurso> c = new ArrayList<>();
        ArrayList<Integer> rc = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("proyectorecurso", "*", "CODIGOPROYECTO="+codigo);
        try {
            while (rs.next()) {
                rc.add(rs.getInt("CODIGORECURSO"));
            }
            rs.close();
            for(int r: rc){
                    c.add(RecursoController.getRecursoByCodigo(r)); // estado controller
            }
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
        return c;
    }
}
