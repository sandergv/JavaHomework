package datos;


import java.util.ArrayList;

public class Rol {

    private int codigo;
    private String rol;
    private ArrayList<Privilegio> privilegios;

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    private String fechaAsignacion;

    public Rol() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public ArrayList<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(ArrayList<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }
}
