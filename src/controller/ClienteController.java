/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.MysqlConnection;
import datos.Cliente;
import datos.Proyecto;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class ClienteController {
    
    
    
    public static ArrayList<Cliente> getClientes(){
        
        ArrayList<Cliente> arrClientes = new ArrayList<Cliente>();
        
        try{

            ResultSet rs;
            rs = MysqlConnection.select("cliente", "*", ""); // st.executeQuery("SELECT * FROM cliente");

            while(rs.next()){
                Cliente c = new Cliente();
                c.setRut(rs.getString("RUTCLIENTE"));
                c.setCodigoComuna(rs.getInt("CODIGOCOMUNA"));
                c.setNombre(rs.getString("NOMBRECLIENTE"));
                c.setDireccion(rs.getString("DIRECCIONCLIENTE"));
                c.setRazonSocial(rs.getString("RAZONSOCIAL"));
                c.setGirocliente(rs.getString("GIROCLIENTE"));
                c.setCorreo(rs.getString("CORREO"));

                arrClientes.add(c);
            }
            rs.close();
            MysqlConnection.desconectar();
            for(Cliente c : arrClientes){
                c.setComuna(ComunaController.getComunaByCodigo(c.getCodigoComuna()));
                c.setTelefonos(getTelefonosCliente(c.getRut()));
                c.setProyectos(getProyectosCliente(c.getRut()));
            }
        }
        catch(Exception e){
            System.out.println("ClienteController");
        }
        return arrClientes;
    }
    
    public static ArrayList<Integer> getTelefonosCliente(String rut){
        ArrayList<Integer> telefonos = new ArrayList<Integer>();
        ResultSet rs = MysqlConnection.select("clientetelefono", "*", String.format("RUTCLIENTE='%s'", rut));
        ArrayList<Integer> c = new ArrayList<Integer>();

        try {
            while(rs.next()){
                c.add(rs.getInt("CODIGOTELEFONO"));
            }
            rs.close();
            MysqlConnection.desconectar();
        }
        catch (Exception e) {
        }
        
        for(int i : c){
            rs = MysqlConnection.select("telefono", "*", "CODIGOTELEFONO=" + i);
            try {
                if(rs.next())
                    telefonos.add(rs.getInt("TELEFONO"));
                
                rs.close();
                MysqlConnection.desconectar();
            } catch (Exception e) {
            }
        }

        return telefonos;
    }
    
    public static ArrayList<Proyecto> getProyectosCliente(String rut){
        ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
        ResultSet rs = MysqlConnection.select("proyectocliente", "*", String.format("RUTCLIENTE='%s'", rut));
        ArrayList<Integer> c = new ArrayList<Integer>();
        Proyecto p;
        try {
            while(rs.next()){
                c.add(rs.getInt("CODIGOPROYECTO"));
            }
            rs.close();
            MysqlConnection.desconectar();
        }
        catch (Exception e) {
        }
        for(int i : c){
            proyectos.add(ProyectoController.getProyectoByCodigo(i));
        }
        return proyectos;
    }
}
