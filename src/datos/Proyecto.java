/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class Proyecto {
    
    private int codigo;
    private int codigoTipo;
    private String tipoProyecto;
    private String Nombre;
    private int horaEstimada;
    private String fechaInicio;
    private String fechaTerminoPlanificada;
    private ArrayList<String> estados;

    private ArrayList<Cliente> clientes; // clientes asociados al proyecto
    private ArrayList<Recurso> recursos; // recursos asociados al proyecto
    private ArrayList<Empleado> empleados; // empleados asociados al proyecto
    private ArrayList<Etapa> etapas;

    public Proyecto() {
    }

    public int getCodigo() {
        return codigo;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(int codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getHoraEstimada() {
        return horaEstimada;
    }

    public void setHoraEstimada(int horaEstimada) {
        this.horaEstimada = horaEstimada;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTerminoPlanificada() {
        return fechaTerminoPlanificada;
    }

    public void setFechaTerminoPlanificada(String fechaTerminoPlanificada) {
        this.fechaTerminoPlanificada = fechaTerminoPlanificada;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(ArrayList<Recurso> recursos) {
        this.recursos = recursos;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public ArrayList<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(ArrayList<Etapa> etapas) {
        this.etapas = etapas;
    }

    public ArrayList<String> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<String> estados) {
        this.estados = estados;
    }
}
