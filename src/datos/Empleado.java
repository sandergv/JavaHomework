/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 *
 * @author Alexander
 */
public class Empleado {
    
    private int codigo;
    private int codigoRol;
    private int codigoComuna; 
    private int codigoEmpleado; // empleadod el cual depende
    private String nombre;
    private String apellido;
    private float sueldo;
    private String incorporación;
    private String usuario;
    private String contraseña;
    private String Direccion;

    public Empleado() {
    }

    public Empleado(int codigo, String usuario, String contraseña) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(int codigoRol) {
        this.codigoRol = codigoRol;
    }

    public int getCodigoComuna() {
        return codigoComuna;
    }

    public void setCodigoComuna(int codigoComuna) {
        this.codigoComuna = codigoComuna;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

    public String getIncorporación() {
        return incorporación;
    }

    public void setIncorporación(String incorporación) {
        this.incorporación = incorporación;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    
    
}
