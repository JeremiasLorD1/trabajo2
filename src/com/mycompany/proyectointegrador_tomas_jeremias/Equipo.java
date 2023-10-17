/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

/**
 *Equipos:(el nombre, descripción y área de especialidad.) x experimento mas de 1.

 * @author jeremias
 */
public class Equipo {
    private String nombre,descripcion,areaEspecialidad;

    public Equipo(String nombre, String descripcion, String areaEspecialidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.areaEspecialidad = areaEspecialidad;
    }
    public Equipo(String nombre) {
        this.nombre = nombre;
        
    }
    public Equipo() {
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAreaEspecialidad() {
        return areaEspecialidad;
    }

    public void setAreaEspecialidad(String areaEspecialidad) {
        this.areaEspecialidad = areaEspecialidad;
    }
     @Override
    public String toString() {
        return nombre; // El método toString muestra solo el nombre
    }
    
}
