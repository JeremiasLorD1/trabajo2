/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

/**
 *Científicos: (conoce su nombre, especialidad y fecha de contratación) x experimento hasta 4.

 * @author jeremias
 */
public class Cientificos {
    private String nombre,apellido,especialidad;
    private Fecha contratacion;
    private int cantidadExperimentos;

    public Cientificos(String nombre,String apellido, String especialidad, Fecha contratacion, int cantidadExperimentos) {
        this.nombre = nombre;
                this.apellido = apellido;

        this.especialidad = especialidad;
        this.contratacion = contratacion;
        this.cantidadExperimentos = cantidadExperimentos;
        
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

    public Fecha getContratacion() {
        return contratacion;
    }

    public void setContratacion(Fecha contratacion) {
        this.contratacion = contratacion;
    }
    
    public int getCantidadExperimentos(){
    return cantidadExperimentos;
    }
    
    public void setCantidadExperimentos(int cantidadExperimentos){
        this.cantidadExperimentos = cantidadExperimentos;}
                            
    
    
}
