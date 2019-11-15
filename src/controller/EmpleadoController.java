package controller;

import database.MysqlConnection;
import datos.Empleado;
import datos.Proyecto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmpleadoController {

    public static ArrayList<Empleado> getEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("empleado", "*", "");
        Empleado em;
        try {
            while (rs.next()){
                em = new Empleado();
                em.setCodigo(rs.getInt("CODIGOEMPLEADO"));
                em.setCodigoComuna(rs.getInt("CODIGOCOMUNA"));
                em.setCodigoRol(rs.getInt("CODIGOROL"));
                em.setCodigoEmpleado(rs.getInt("EMP_CODIGOEMPLEADO"));
                em.setNombre(rs.getString("NOMBREEMPLEADO"));
                em.setApellido(rs.getString("APELLIDOEMPLEADO"));
                em.setSueldo(rs.getInt("SUELDO"));
                em.setIncorporacion(rs.getString("INCORPORACION"));
                em.setUsuario(rs.getString("USUARIO"));
                em.setContrasena(rs.getString("CONTRASENA"));
                em.setDireccion(rs.getString("DIRECCIONEMPLEADO"));

                empleados.add(em);
            }

            for(Empleado emp: empleados){
                emp.setComuna(ComunaController.getComunaByCodigo(emp.getCodigoComuna()));
                emp.setRol(RolController.getRolByCodigo(emp.getCodigoRol()));
            }
            MysqlConnection.desconectar();
        }
        catch (Exception e){

        }

        return empleados;
    }

    public static Empleado getEmpleadoByCodigo(int codigo){
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select(
                "empleado", "*", "CODIGOEMPLEADO="+codigo);
        Empleado em = new Empleado();
        try {
            if(rs.next()){
                em = new Empleado();
                em.setCodigo(rs.getInt("CODIGOEMPLEADO"));
                em.setCodigoComuna(rs.getInt("CODIGOCOMUNA"));
                em.setCodigoRol(rs.getInt("CODIGOROL"));
                em.setCodigoEmpleado(rs.getInt("EMP_CODIGOEMPLEADO"));
                em.setNombre(rs.getString("NOMBREEMPLEADO"));
                em.setApellido(rs.getString("APELLIDOEMPLEADO"));
                em.setSueldo(rs.getInt("SUELDO"));
                em.setIncorporacion(rs.getString("INCORPORACION"));
                em.setUsuario(rs.getString("USUARIO"));
                em.setContrasena(rs.getString("CONTRASENA"));
                em.setDireccion(rs.getString("DIRECCIONEMPLEADO"));
            }
            em.setComuna(ComunaController.getComunaByCodigo(em.getCodigoComuna()));
            em.setRol(RolController.getRolByCodigo(em.getCodigoRol()));
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
        return em;
    }

    public static void nuevoEmpleado(Empleado e){
        String[] columnas = {
                "CODIGOROL", "CODIGOCOMUNA", "EMP_CODIGOEMPLEADO",
                "NOMBREEMPLEADO", "APELLIDOEMPLEADO", "SUELDO",
                "INCORPORACION", "USUARIO", "CONTRASENA", "DIRECCIONEMPLEADO"};
        MysqlConnection.conectar();
        PreparedStatement pst = MysqlConnection.insert("empleado", columnas);
        try{
            pst.setInt(1, e.getRol().getCodigo());
            pst.setInt(2, e.getComuna().getCodigo());
            try{
                pst.setInt(3, e.getEmpleado().getCodigo());
            }
            catch(Exception ex){
                
            }
            
            pst.setString(4, e.getNombre());
            pst.setString(5, e.getApellido());
            pst.setFloat(6, e.getSueldo());
            pst.setString(7, e.getIncorporacion());
            pst.setString(8, e.getUsuario());
            pst.setString(9, e.getContrasena());
            pst.setString(10, e.getDireccion());

            pst.execute();
            pst.close();
        }
        catch (Exception er){
            System.out.println("EmpleadoController");
        }
        MysqlConnection.desconectar();
    }
    public  static void borrarEmpleado(int codigo){
        MysqlConnection.delete("empleado", "CODIGOEMPLEADO="+codigo);
    }

    public static void addProyecto(Proyecto p, int codigoEmpleado){
        MysqlConnection.conectar();
        String[] columns = {"CODIGOEMPLEADO", "CODIGOPROYECTO"};
        PreparedStatement pst = MysqlConnection.insert("empleadoproyecto", columns);

        try{
            pst.setInt(1, codigoEmpleado);
            pst.setInt(2, p.getCodigo());

            pst.execute();
            pst.close();
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
    }

    public static ArrayList<Proyecto> getProyectos(int codigo){
        ArrayList<Proyecto> c = new ArrayList<>();
        ArrayList<Integer> rc = new ArrayList<>();
        MysqlConnection.conectar();
        ResultSet rs = MysqlConnection.select("empleadoproyecto", "*", "CODIGOEMPLEADO="+codigo);
        try {
            while (rs.next()) {
                rc.add(rs.getInt("CODIGOPROYECTO"));
            }
            rs.close();
            for(int r: rc){
                c.add(ProyectoController.getProyectoByCodigo(r));
            }
        }
        catch (Exception e){

        }
        MysqlConnection.desconectar();
        return c;
    }
}
