package controller;

import database.MysqlConnection;
import datos.Privilegio;
import datos.Rol;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RolController {

    public static Rol getRolByCodigo(int codigo){
        MysqlConnection.conectar();
        Rol rol = new Rol();
        ResultSet rs = MysqlConnection.select("rol", "*", "CODIGOROL="+codigo);
        try {
            if(rs.next()){
                rol.setCodigo(rs.getInt("CODIGOROL"));
                rol.setRol(rs.getString("DESCRIPCIONROL"));
            }
            rs.close();

            rol.setPrivilegios(getPrivilegios(codigo));
        }
        catch(Exception e){}
        MysqlConnection.desconectar();
        return rol;
    }

    public static ArrayList<Privilegio> getPrivilegios(int codigoRol){
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("rolprivilegio", "*", "CODIGOROL="+codigoRol);
        ArrayList<Privilegio> cp = new ArrayList<Privilegio>();
        Privilegio p;
        try {
            while (rs.next()) {
                p = new Privilegio();
                p.setCodigo(rs.getInt("CODIGOPRIVILEGIO"));
                p.setFechaAsignacion(rs.getString("FECHAASIGNACION"));
                cp.add(p);
            }
            rs.close();
            for (Privilegio c : cp) {
                rs = MysqlConnection.select("privilegio", "*", "CODIGOPRIVILEGIO=" + c);
                if (rs.next()) {
                    c.setNombre(rs.getString("NOMBREPRIVILEGIO"));
                }
            }
            rs.close();
        }
        catch(Exception e){

        }
        MysqlConnection.desconectar();
        return cp;
    }

    public static ArrayList<Rol> getRoles(){
        MysqlConnection.conectar();
        Rol rol = null;
        ResultSet rs = MysqlConnection.select("rol", "*", "");
        ArrayList<Rol> ar = new ArrayList<Rol>();
        try {
            while(rs.next()){
                rol = new Rol();
                rol.setCodigo(rs.getInt("CODIGOROL"));
                rol.setRol(rs.getString("DESCRIPCIONROL"));
                ar.add(rol);
            }
            rs.close();

            for(Rol r: ar){
                r.setPrivilegios(getPrivilegios(r.getCodigo()));
            }
        }
        catch(Exception e){}
        MysqlConnection.desconectar();
        return ar;
    }

    public static void borrarRol(int codigo){
        MysqlConnection.delete("rol", "CODIGOROL="+codigo);
    }
}
