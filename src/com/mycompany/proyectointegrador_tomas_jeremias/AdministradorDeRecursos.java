/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jerem
 */
public class AdministradorDeRecursos {

    ArrayList<Cientifico> listaCientifico;
    ArrayList<Equipo> listaEquipo;
    HashMap<Cientifico, Integer> contadorCientificos;
    HashMap<Equipo, Integer> contadorEquipos;

    public AdministradorDeRecursos() {
        // Carga de Equipo.
        listaEquipo = new ArrayList<>();//esta lista es para llenar la lista de la interfaz

        listaEquipo.add(new Equipo("Martillo", "Martilla", "Todas"));
        listaEquipo.add(new Equipo("Tornillo", "Martisdadsalla", "Todasffsafdsa"));
        listaEquipo.add(new Equipo("Rosca", "de tu ", ""));

        // Carga de Cientiifico.//hacer la lectura de archivo de los equipos
        listaCientifico = new ArrayList<>();//esta lista es para llenar la lista de la interfaz

        //no usar variables si no las uso ej c1 = jeremisa,martinez
        listaCientifico.add(new Cientifico("Jeremias", "Martinez", "Doctor", "111"));
        listaCientifico.add(new Cientifico("Facu", "Joaquin", "inge", "222"));
        listaCientifico.add(new Cientifico("Capo", "Matias", "ijisdajidjsa", "333"));

        for (Cientifico d : listaCientifico) {
            contadorCientificos.put(d,0);
        }

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

}
