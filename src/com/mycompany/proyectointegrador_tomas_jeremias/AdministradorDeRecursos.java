/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
         try{
            BufferedReader lector = new BufferedReader(new FileReader("equipos.txt"));
            String linea;
            int contador = 0;
            String nombre = "";
            String descripcion = "";
            String areaEspecialidad = "";
            while((linea=lector.readLine())!=null){
                switch(contador){
                    case 0:
                        nombre=linea;
                        break;
                    case 1:
                        descripcion=linea;
                        break;
                    case 2:
                        areaEspecialidad=linea;
                        listaEquipo.add(new Equipo(nombre,descripcion,areaEspecialidad));
                }
                contador=(contador+1)%3;
            }
            lector.close();
         } catch(IOException e) {
             e.printStackTrace();
         }       
        // Carga de Cientiifico.//hacer la lectura de archivo de los equipos
        listaCientifico = new ArrayList<>();//esta lista es para llenar la lista de la interfaz
        try {
            BufferedReader lector= new BufferedReader(new FileReader("cientificos.txt"));
            String linea;
            int contador=0;
            String nombre="";
            String apellido="";
            String especialidad="";
            String dni="";
            while((linea=lector.readLine())!=null){
                switch(contador){
                    case 0:
                        nombre=linea;
                        break;
                    case 1:
                        apellido=linea;
                        break;
                    case 2:
                        especialidad=linea;                       
                        break;
                    case 3:
                        dni=linea;
                         listaCientifico.add(new Cientifico(nombre,apellido,especialidad,dni));
                        break;                        
                }
                contador=(contador+1)%4;
            }
            lector.close();
        } catch (Exception e) {
        }
        //no usar variables si no las uso ej c1 = jeremisa,martinez
        /*listaCientifico.add(new Cientifico("Jeremias", "Martinez", "Doctor", "111"));
        listaCientifico.add(new Cientifico("Facu", "Joaquin", "inge", "222"));
        listaCientifico.add(new Cientifico("Capo", "Matias", "ijisdajidjsa", "333"));*/

       // for (Cientifico d : listaCientifico) {
         //   contadorCientificos.put(d,0);
        //}

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
