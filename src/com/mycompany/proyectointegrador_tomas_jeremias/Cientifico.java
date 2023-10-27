/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Científicos: (conoce su nombre, especialidad y fecha de contratación) x
 * experimento hasta 4.
 *
 * @author jeremias
 */
public class Cientifico {

    private String nombre, apellido, especialidad, dni;
    private String contratacion;    
    
    public Cientifico(String nombre, String apellido, String especialidad, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.dni = dni;

    }

    public Cientifico(String nombre, String apellido, String especialidad, String contratacion, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.contratacion = contratacion;

    }

    public Cientifico(String nombre, String contratacion) {
        this.nombre = nombre;
        this.contratacion = contratacion;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getContratacion() {
        return contratacion;
    }

    public void setContratacion(String contratacion) {
        this.contratacion = contratacion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return nombre; // El método toString muestra solo el nombre
    }
}
