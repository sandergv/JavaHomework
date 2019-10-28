/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;

/**
 *
 * @author Alexander
 */
public class MysqlConnection {
    
    private static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";

    private static final String url = "jdbc:mysql://192.168.0.10:3306/appit";

    private static int callCount = 0; // conteo de llamadas a conectar, la conexión no es cerrada hasta que la primera llamada se desconecte

    public static void conectar(){

        try {
            if (conn == null) {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Conexion ready");
            }
        } catch (Exception e) {
            System.out.println("No conexion :c");
            e.printStackTrace();
        }
        callCount++;
    }
    
    public static void desconectar(){
        try {
            if (isConn() && callCount == 1) {
                conn.close();
                conn = null;
                System.out.println("Conexion cerrada");
            }
        } catch (Exception e) {
            System.out.println("Conexion no cerrada");
        }
        try{

        }catch (Exception e){}
        callCount--;
    }

    public static boolean isConn(){
        return conn != null;
    }

    public static ResultSet select(String table,String data , String condition){
        ResultSet rs = null;
        try{
            Statement st = conn.createStatement();
            if(condition.isEmpty())
                rs = st.executeQuery(
                        String.format("SELECT %s FROM %s", data, table ));
            else
                rs = st.executeQuery(
                        String.format("SELECT %s FROM %s WHERE %s", data, table, condition));
        }
        catch(Exception e){
            System.out.println("HERE");
            e.printStackTrace();
        }
        return rs;
    }

    public static PreparedStatement insert(String table, String[] columns) {
        StringBuilder strc = new StringBuilder();
        String v = "";
        PreparedStatement pst = null;
        for (int i = 0; i < columns.length; i++) {
            if (i != columns.length - 1) {
                strc.append(columns[i].concat(", "));
                v = v.concat("?, ");
            } else {
                strc.append(columns[i]);
                v = v.concat("?");
            }
        }

        String query = " insert into "+table+" (" + strc.toString() + ")"
                + " values (" + v + ")";
        try {
            pst = conn.prepareStatement(query);
        }
        catch(Exception e){

        }
        return pst;
    }

    public static void delete(String table, String condition){
        conectar();
        try {

            String query = "delete from "+table+" where "+ condition;
            Statement st = conn.createStatement();
            st.execute(query);
            st.close();
        }
        catch(Exception e){

        }
        desconectar();
    }

    public static void update(String table, String column, String value, String condition){
        conectar();
        String query = "update "+table+" set "+column+" = ? where first_name = ?";
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, value);
            pst.execute();
            pst.close();
        }
        catch(Exception e){

        }
        desconectar();
    }

    public static void update(String table, String column, int value, String condition){
        conectar();
        String query = "update "+table+" set "+column+" = ? where first_name = ?";
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, value);
            pst.execute();
            pst.close();
        }
        catch(Exception e){
        }
        desconectar();
    }
}
