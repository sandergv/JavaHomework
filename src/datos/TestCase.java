package datos;

import java.util.ArrayList;

public class TestCase {

    private int codigo;
    private String descripcion;

    private ArrayList<Defecto> defectos;

    public TestCase() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Defecto> getDefectos() {
        return defectos;
    }

    public void setDefectos(ArrayList<Defecto> defectos) {
        this.defectos = defectos;
    }
}
