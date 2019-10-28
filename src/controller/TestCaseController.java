package controller;

import database.MysqlConnection;
import datos.Defecto;
import datos.TestCase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TestCaseController {

    public static TestCase getTestCaseByCodigo(int codigo){
        TestCase tc = new TestCase();
        MysqlConnection.conectar();

        ResultSet rs = MysqlConnection.select("testcase", "*", "CODIGOTESTCASE="+codigo);
        try{
            if (rs.next()){
                tc.setCodigo(codigo);
                tc.setDescripcion(rs.getString("DESCRIPTIONTESTCASE"));

                tc.setDefectos(getDefectos(codigo));
            }
        }
        catch(Exception e){
        }
        MysqlConnection.desconectar();
        return tc;
    }

    public static ArrayList<TestCase> getTestCases(){
        ArrayList<TestCase> tcl = new ArrayList<>();
        TestCase tc;
        MysqlConnection.conectar();

        ResultSet rs = MysqlConnection.select("testcase", "*", "");
        try{
            while (rs.next()){
                tc = new TestCase();
                tc.setCodigo(rs.getInt("CODIGOTESTCASE"));
                tc.setDescripcion(rs.getString("DESCRIPCIONTESTCASE"));

                tc.setDefectos(getDefectos(tc.getCodigo()));
                tcl.add(tc);
            }
        }
        catch(Exception e){
        }
        MysqlConnection.desconectar();
        return tcl;
    }

    public static void nuevoTestCase(TestCase tc){
        String[] columnas = {"DESCRIPCIONTESTCASE"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("testcase", columnas);
        try{
            pst.setString(1, tc.getDescripcion());

            pst.execute();
            pst.close();
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
    }

    public static void borrarTestCase(){

    }

    private static void addDefecto(int codigoTestCase, int codigoDefecto){
        MysqlConnection.conectar();
        String[] columns = {"CODIGOTESTCASE", "CODIGODEFECTO"};
        PreparedStatement pst = MysqlConnection.insert("testcasedefecto", columns);
        try{
            pst.setInt(1, codigoTestCase);
            pst.setInt(2, codigoDefecto);

            pst.execute();
            pst.close();
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
    }

    private static ArrayList<Defecto> getDefectos(int codigo){
        ArrayList<Defecto> dl = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("testcasedefecto", "*", "CODIGOTESTCASE="+codigo);
        ArrayList<Integer> c = new ArrayList<>();
        try {
            while(rs.next()){
                c.add(rs.getInt("CODIGODEFECTO"));
            }
            rs.close();
        }
        catch (Exception e) {
        }
        for(int i : c){
            try{
                dl.add(DefectoController.getDefectoByCodigo(i));
            }
            catch(Exception e){
            }

        }
        MysqlConnection.desconectar();
        return dl;
    }

}
