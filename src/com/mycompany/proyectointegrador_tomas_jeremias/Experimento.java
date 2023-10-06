/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

import java.util.ArrayList;
import java.util.Date;

/**
 * (título, descripción, presupuesto, fecha de inicio y fecha de fin)
 *
 * @author jeremias
 */
public abstract class Experimento {

    private String titulo;
    private String descripcion;
    private float presupuesto;
    private Date inicio;
    private Date fin;
    private String tipo;
    private ArrayList<Cientifico> listaCientifico;
    private ArrayList<Equipo> listaEquipo;

    public Experimento() {
        
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<Cientifico> getListaCientifico() {
        return listaCientifico;
    }

    public void setListaCientifico(ArrayList<Cientifico> listaCientifico) {
        this.listaCientifico = listaCientifico;
    }

    public ArrayList<Equipo> getListaEquipo() {
        return listaEquipo;
    }

    public void setListaEquipo(ArrayList<Equipo> listaEquipo) {
        this.listaEquipo = listaEquipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}

   /* public abstract void carga(Experimento e);

    public abstract void modificacion(Experimento e);

    public abstract void eliminar(Experimento e);
}
/**
 * *DENTRO DE EXPERIMENTO** a. Carga. b. Modificación. c. Eliminación. d.
 * Persistencia de datos.(QUE SE GUARDE LA INFO LUEGO DE CERRAR)
   *
 */
