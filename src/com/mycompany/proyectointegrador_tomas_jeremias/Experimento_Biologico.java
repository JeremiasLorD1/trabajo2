/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

import java.util.Date;

/**
 * Experimento_Biologico: (tipo de organismo)
 *
 * @author jeremias
 */
public class Experimento_Biologico extends Experimento {

    private String organismo;

    public Experimento_Biologico(String organismo) {
        this.organismo = organismo;
    }

    public Experimento_Biologico() {
       
    }

     

    public String getOrganismo() {
        return organismo;
    }

    public void setOrganismo(String organismo) {
        this.organismo = organismo;
    }

    public void CargaExperimento() {

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
