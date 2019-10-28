package controller;

import database.MysqlConnection;
import datos.Empleado;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EmpleadoController {

    public static ArrayList<Empleado> getEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();

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
            MysqlConnection.desconectar();

            for(Empleado emp: empleados){
                emp.setEmpleado(getEmpleadoByCodigo(emp.getCodigo()));
                emp.setComuna(ComunaController.getComunaByCodigo(emp.getCodigoComuna()));
                emp.setRol(RolController.getRolByCodigo(emp.getCodigoRol()));
            }

        }
        catch (Exception e){

        }

        return empleados;
    }

    public static Empleado getEmpleadoByCodigo(int codigo){
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
            MysqlConnection.desconectar();
            em.setComuna(ComunaController.getComunaByCodigo(em.getCodigoComuna()));
            em.setRol(RolController.getRolByCodigo(em.getCodigoRol()));
        }
        catch (Exception e){

        }

        return em;
    }
}
