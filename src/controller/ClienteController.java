/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.MysqlConnection;
import datos.Cliente;
import datos.Proyecto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Controlador Cliente para la interacción con la base de datos
 * @author Alexander
 * @version %I% %G%
 */
public class ClienteController {

    /**
     * Consulta por Cliente con su rut 
     * @param rut Rut Cliente
     * @return Objeto Cliente
     */
    public static Cliente getClienteByRut(String rut){
        MysqlConnection.conectar();
        Cliente c = null;
        try{

            ResultSet rs;
            rs = MysqlConnection.select("cliente", "*", String.format("RUTCLIENTE='%s'", rut)); // st.executeQuery("SELECT * FROM cliente");
            c = new Cliente();
            if(rs.next()){

                c.setRut(rs.getString("RUTCLIENTE"));
                c.setCodigoComuna(rs.getInt("CODIGOCOMUNA"));
                c.setNombre(rs.getString("NOMBRECLIENTE"));
                c.setDireccion(rs.getString("DIRECCIONCLIENTE"));
                c.setRazonSocial(rs.getString("RAZONSOCIAL"));
                c.setGirocliente(rs.getString("GIROCLIENTE"));
                c.setCorreo(rs.getString("CORREO"));
            }
            rs.close();

            c.setComuna(ComunaController.getComunaByCodigo(c.getCodigoComuna()));
            c.setTelefonos(getTelefonosCliente(c.getRut()));
        }
        catch(Exception e){
            System.out.println("ClienteController");
        }
        MysqlConnection.desconectar();
        return c;

    }
    
    /**
     * Consultar por Cliente con su nombre
     * @param nombre Nombre de Cliente
     * @return Objeto Cliente
     */
    public static Cliente getClienteByNombre(String nombre){
        MysqlConnection.conectar();
        
        ResultSet rs = MysqlConnection.select("cliente", "RUTCLIENTE", String.format("NOMBRECLIENTE='%s'", nombre));
        String rut = "";
        try {
            if (rs.next()) {
                rut = rs.getString("RUTCLIENTE");
            }
            
        } catch (Exception e) {
        }
        Cliente c = getClienteByRut(rut);
        MysqlConnection.desconectar();
        
        return c;
    }
    
    /**
     * Consulta por la lista de todos los Clientes
     * @return ArrayList de Clientes
     */
    public static ArrayList<Cliente> getClientes(){
        MysqlConnection.conectar();
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
            for(Cliente c : arrClientes){
                c.setComuna(ComunaController.getComunaByCodigo(c.getCodigoComuna()));
                c.setTelefonos(getTelefonosCliente(c.getRut()));
            }
        }
        catch(Exception e){
            System.out.println("ClienteController");
        }
        MysqlConnection.desconectar();
        return arrClientes;
    }
    
    /**
     * Consultar por Telefonos asociados al Cliente 
     * @param rut Rut Cliente
     * @return ArrayList de Enteros
     */
    public static ArrayList<Integer> getTelefonosCliente(String rut){
        MysqlConnection.conectar();
        ArrayList<Integer> telefonos = new ArrayList<Integer>();
        ResultSet rs = MysqlConnection.select("clientetelefono", "*", String.format("RUTCLIENTE='%s'", rut));
        ArrayList<Integer> c = new ArrayList<Integer>();

        try {
            while(rs.next()){
                c.add(rs.getInt("CODIGOTELEFONO"));
            }
            rs.close();
        }
        catch (Exception e) {
        }
        
        for(int i : c){
            rs = MysqlConnection.select("telefono", "*", "CODIGOTELEFONO=" + i);
            try {
                if(rs.next())
                    telefonos.add(rs.getInt("TELEFONO"));
                
                rs.close();
            } catch (Exception e) {
            }
        }
        MysqlConnection.desconectar();
        return telefonos;
    }
    
    /**
     * Consultar por Proyectos asociados al Cliente
     * @param rut Rut Cliente
     * @return Arraylist de Proyectos
     */
    private static ArrayList<Proyecto> getProyectosCliente(String rut){
        MysqlConnection.conectar();
        ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
        ResultSet rs = MysqlConnection.select("proyectocliente", "*", String.format("RUTCLIENTE='%s'", rut));
        ArrayList<Integer> c = new ArrayList<Integer>();
        Proyecto p;
        try {
            while(rs.next()){
                c.add(rs.getInt("CODIGOPROYECTO"));
            }
            rs.close();
        }
        catch (Exception e) {
        }
        for(int i : c){
            proyectos.add(ProyectoController.getProyectoByCodigo(i));
        }
        MysqlConnection.desconectar();
        return proyectos;
    }

    /**
     * Insertar nuevo Cliente a la base de datos
     * @param c Objeto Cliente
     */
    public static void nuevoCliente(Cliente c){
        MysqlConnection.conectar();
        String[] columns = { "RUTCLIENTE", "CODIGOCOMUNA", "NOMBRECLIENTE", "DIRECCIONCLIENTE", "RAZONSOCIAL", "GIROCLIENTE", "CORREO"};
        PreparedStatement pst = MysqlConnection.insert("cliente", columns);
        try{
            pst.setString(1, c.getRut());
            pst.setInt(2, c.getComuna().getCodigo());
            pst.setString(3, c.getNombre());
            pst.setString(4, c.getDireccion());
            pst.setString(5, c.getRazonSocial());
            pst.setString(6, c.getGirocliente());
            pst.setString(7, c.getCorreo());

            pst.execute();
            pst.close();

            addTelefonosCliente(c.getRut(), c.getTelefonos());
        }
        catch (Exception e){
            System.out.println("hi");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
    }
    
    /**
     * Añadir Telefonos y asociarlos al cliente
     * @param rut Rut Cliente
     * @param tel ArrayList de Telefonos (Enteros)
     */
    private static void addTelefonosCliente(String rut, ArrayList<Integer> tel){
        MysqlConnection.conectar();
        try{
            String[] tc = {"TELEFONO"};
            PreparedStatement pst = null;
            for (int i = 0; i < tel.size(); i++) {

                pst = MysqlConnection.insert("TELEFONO", tc);
                pst.setInt(1, tel.get(i));
                pst.execute();
                pst.close();
            }
            ArrayList<Integer> cdT = new ArrayList<Integer>();
            ResultSet rs;
            for (int i = 0; i < tel.size(); i++) {
                rs = MysqlConnection.select("telefono", "*", "TELEFONO="+ tel.get(i));
                if(rs.next())
                    cdT.add(rs.getInt("CODIGOTELEFONO"));
                rs.close();
            }

            String[] columns = {"CODIGOTELEFONO", "RUTCLIENTE"};
            for (int c:cdT) {
                pst = MysqlConnection.insert("clientetelefono", columns);
                pst.setInt(1, c);
                pst.setString(2, rut);
                pst.execute();
                pst.close();
            }

        }
        catch(Exception e){
        }
        MysqlConnection.desconectar();
    }
    
    public static void modificarCliente(Cliente c){
        
        String condition = String.format("RUTCLIENTE='%s'", c.getRut());
        
        MysqlConnection.conectar();
        
        MysqlConnection.update("cliente", "NOMBRECLIENTE", c.getNombre(), condition);
        MysqlConnection.update("cliente", "DIRECCIONCLIENTE", c.getDireccion(), condition);
        MysqlConnection.update("cliente", "RAZONSOCIAL", c.getRazonSocial(), condition);
        MysqlConnection.update("cliente", "GIROCLIENTE", c.getGirocliente(), condition);
        MysqlConnection.update("cliente", "CORREO", c.getCorreo(), condition);

        MysqlConnection.desconectar();
    }

    public static void borrarClienteByRut(String rut){
        MysqlConnection.delete("cliente", "RUTCLIENTE="+rut);
    }
}
