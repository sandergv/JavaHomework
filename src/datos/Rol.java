package datos;

import java.util.ArrayList;

public class Rol {

    private String rol;
    private ArrayList<Privilegio> privilegios;

    public Rol() {
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
