/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.StringUtils;

import java.sql.*;

/**
 *
 * @author Alexander
 */
public class MysqlConnection {
    
    private static Connection conn;
    private static Statement st;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";

    private static final String url = "jdbc:mysql://192.168.0.10:3306/appit";


    public static Connection getConn() {
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Conexion Ready.");
            }
        } catch (Exception e) {
            System.out.println("No conexion :c");
            e.printStackTrace();
        }
        return conn;
    }
    
    public static void desconectar(){

        try {
            conn.close();
            
            System.out.println("Conexion cerrada");
        } catch (Exception e) {
            System.out.println("Conexion no cerrada");
        }
        try{

        }catch (Exception e){}
        try{
            st.close();
        }catch (Exception e){}
    }
    
    public static ResultSet select(String table,String data , String condition){
        Connection conn = getConn();
        ResultSet rs = null;

        try{
            st = conn.createStatement();
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
        Connection conn = getConn();
        try {
            pst = conn.prepareStatement(query);
        }
        catch(Exception e){

        }
        return pst;
    }
}
