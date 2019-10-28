package controller;

import datos.Comentario;
import datos.Etapa;
import database.MysqlConnection;
import datos.TestCase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EtapaController {

    public static Etapa getEtapaByCodigo(int codigo){
        Etapa et = new Etapa();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("etapa", "*", "CODIGOETAPA=" + codigo);
        try{
            if(rs.next()){
                et.setCodigo(rs.getInt("CODIGOETAPA"));
                et.setDescripcion(rs.getString("DESCRIPCIONETAPA"));
                et.setFechaTermino(rs.getString("FECHATERMINOETAPA"));
                et.setFechaInicio(rs.getString("FECHAINICIOETAPA"));

                et.setEstados(getEstados(codigo));
                et.setComentarios(getComentarios(codigo));
            }
        }
        catch(Exception e){
            System.out.println("EtapaControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return et;
    }

    public static ArrayList<Etapa> getEtapas(){
        ArrayList<Etapa> el = new ArrayList<>();
        Etapa et;
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("etapa", "*", "");
        try{
            while (rs.next()){
                et = new Etapa();
                et.setCodigo(rs.getInt("CODIGOETAPA"));
                et.setDescripcion(rs.getString("DESCRIPCIONETAPA"));
                et.setFechaTermino(rs.getString("FECHATERMINOETAPA"));
                et.setFechaInicio(rs.getString("FECHAINICIOETAPA"));

                et.setEstados(getEstados(et.getCodigo()));
                et.setComentarios(getComentarios(et.getCodigo()));

                el.add(et);
            }
        }
        catch(Exception e){
            System.out.println("ProvinciaControler: ");
            e.printStackTrace();
        }
        MysqlConnection.desconectar();
        return el;
    }
    public static void nuevaEtapa(Etapa et){
        String[] columnas = {"DESCRIPCIONETAPA", "FECHATERMINOETAPA", "FECHAINICIOETAPA"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("etapa", columnas);
        try{
            pst.setString(1, et.getDescripcion());
            pst.setString(2, et.getFechaTermino());
            pst.setString(3, et.getFechaInicio());

            pst.execute();
            pst.close();
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
    }
    public static void borrarEtapa(int codigo){
        MysqlConnection.delete("etapa", "CODIGOETAPA="+codigo);
    }

    private static ArrayList<String> getEstados(int codigo){
        MysqlConnection.conectar();
        ArrayList<String> estados = new ArrayList<>();
        ResultSet rs = MysqlConnection.select("etapaestadoetapa", "*", "CODIGOEETAPA="+codigo);
        ArrayList<Integer> c = new ArrayList<>();
        try {
            while(rs.next()){
                c.add(rs.getInt("CODIGOETAPAESTADO"));
            }
            rs.close();
        }
        catch (Exception e) {
        }
        for(int i : c){
            rs = MysqlConnection.select("estadoetapa", "*", "CODIGOETAPAESTADO="+i);
            try{
                estados.add(rs.getString("DESCRIPCIONETAPAESTADO"));
            }
            catch(Exception e){

            }

        }
        MysqlConnection.desconectar();
        return estados;
    }

    public static void addComentario(Comentario c, int codigoEtapa){

        MysqlConnection.conectar();
        ComentarioController.nuevoComentario(c);
        String[] columns = { "CODIGOETAPA", "CODIGOCOMENTARIO"};
        PreparedStatement pst = MysqlConnection.insert("comentarioetapa", columns);

        try{
            pst.setInt(1, codigoEtapa);
            pst.setInt(2, c.getCodigo());

            pst.execute();
            pst.close();
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }

    public static void addEstado(int codigoEstado, int codigoEtapa){
        MysqlConnection.conectar();
        String[] columns = {"CODIGOETAPA", "CODIGOETAPAESTADO"};
        PreparedStatement pst = MysqlConnection.insert("etapaestadoetapa", columns);

        try{
            pst.setInt(1, codigoEtapa);
            pst.setInt(2, codigoEstado);

            pst.execute();
            pst.close();
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }

    public static void addTestCase(int codigoEtapa, int codigoTestCase){
        MysqlConnection.conectar();
        String[] columns = {"CODIGOTESTCASE", "CODIGOETAPA"};
        PreparedStatement pst = MysqlConnection.insert("etapatestcase", columns);

        try{
            pst.setInt(1, codigoTestCase);
            pst.setInt(2, codigoEtapa);

            pst.execute();
            pst.close();
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }

    private static ArrayList<TestCase> getTestcases(int codigo){
        ArrayList<TestCase> cl = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("etapatestcase", "*", "CODIGOETAPA="+codigo);
        ArrayList<Integer> c = new ArrayList<>();
        try {
            while(rs.next()){
                c.add(rs.getInt("CODIGOTESTCASE"));
            }
            rs.close();
        }
        catch (Exception e) {
        }
        for(int i : c){
            try{
                cl.add(null);
            }
            catch(Exception e){
            }
        }
        MysqlConnection.desconectar();
        return cl;
    }

    private static ArrayList<Comentario> getComentarios(int codigo){
        ArrayList<Comentario> cl = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("comentarioetapa", "*", "CODIGOETAPA="+codigo);
        ArrayList<Integer> c = new ArrayList<>();
        try {
            while(rs.next()){
                c.add(rs.getInt("CODIGOCOMENTARIO"));
            }
            rs.close();
        }
        catch (Exception e) {
        }
        for(int i : c){
            try{
                cl.add(ComentarioController.getComentarioByCodigo(i));
            }
            catch(Exception e){
            }

        }
        MysqlConnection.desconectar();
        return cl;
    }
}
