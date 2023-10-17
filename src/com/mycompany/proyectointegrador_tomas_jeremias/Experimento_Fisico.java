/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author jeremias
 */
public class Experimento_Fisico extends Experimento {

    private String fenomeno;

    public Experimento_Fisico(String fenomeno) {
        this.fenomeno = fenomeno;
    }

    public Experimento_Fisico() {

    }

    public Experimento_Fisico(String titulo, String descripcion, float presupuesto,String tipo ,Date inicio, Date fin, ArrayList<Cientifico> listaCientifico, ArrayList<Equipo> listaEquipo, String fenomeno) {
        super(titulo, descripcion, presupuesto,tipo, inicio, fin, listaCientifico, listaEquipo);
        this.fenomeno = fenomeno;
    }

    public String getFenomeno() {
        return fenomeno;
    }

    public void setFenomeno(String fenomeno) {
        this.fenomeno = fenomeno;
    }


    /*  @Override
    public void carga(Experimento e) {
        
    }

    @Override
    public void modificacion(Experimento e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Experimento e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     */
}
