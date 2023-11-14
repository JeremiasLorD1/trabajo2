/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author jerem
 */
public class AdministradorDeRecursos {

    ArrayList<Cientifico> listaCientifico;
    ArrayList<Equipo> listaEquipo;
    HashMap<Cientifico, Integer> contadorCientificos;
    HashMap<Equipo, Integer> contadorEquipos;
    String nombreArchivo = "experimentos.txt";
    String nombreArchivoEquipos = "equipos.txt";

    public AdministradorDeRecursos() {
        // Carga de Equipo.
        listaEquipo = new ArrayList<>();//esta lista es para llenar la lista de la interfaz
        try {
            BufferedReader lector = new BufferedReader(new FileReader("equipos.txt"));
            String linea;
            int contador = 0;
            String nombre = "";
            String descripcion = "";
            String areaEspecialidad = "";
            int contadorEquipo = 0;
            while ((linea = lector.readLine()) != null) {
                switch (contador) {
                    case 0:
                        nombre = linea;
                        break;
                    case 1:
                        descripcion = linea;
                        break;
                    case 2:
                        areaEspecialidad = linea;
                        break;
                    case 3:
                        contadorEquipo = Integer.parseInt(linea);
                        listaEquipo.add(new Equipo(nombre, descripcion, areaEspecialidad, 0));
                        break;
                }
                contador = (contador + 1) % 4;
            }
            lector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        
        // Carga de Cientiifico.//hacer la lectura de archivo de los equipos                    
    listaCientifico = new ArrayList<>();// esta lista es para llenar la lista de la interfaz

try {
    BufferedReader lector = new BufferedReader(new FileReader("cientificos.txt"));
    String linea;
    ArrayList<String> datosCientifico = new ArrayList<>(); // Almacena temporalmente los datos de un científico

    while ((linea = lector.readLine()) != null) {
        datosCientifico.add(linea);

        // Cuando se han leído todos los datos del científico, crear la instancia de Cientifico y agregar a la lista
        if (datosCientifico.size() == 5) {
            listaCientifico.add(new Cientifico(
                    datosCientifico.get(0),
                    datosCientifico.get(1),
                    datosCientifico.get(2),
                    datosCientifico.get(4), // Ajuste en el orden de las variables
                    datosCientifico.get(3)  // Ajuste en el orden de las variables
            ));

            // Limpiar la lista para el próximo científico
            datosCientifico.clear();
        }
    }

    lector.close();
} catch (Exception e) {
    // Manejo de excepciones
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

    public ArrayList<Experimento> cargarExperimentos() {

        ArrayList<Experimento> listaExperimentosBioFis = new ArrayList<>();
        try {
            BufferedReader lector = new BufferedReader(new FileReader("C:\\Users\\ttoom\\OneDrive\\Escritorio\\UNIVERSIDADD\\Programacion II\\TP de Maquina\\trabajo2\\experimentos.txt"));
            String linea;

            // PONER TRY POR SI NO HAY ARCHIVO
            while ((linea = lector.readLine()) != null) {
                // Titulo
                String expTitulo = linea;

                // Descripcion
                String expDescr = lector.readLine();

                // Presupuesto
                Float expPresupuesto = Float.valueOf(lector.readLine());

                // Fecha Inicio
                String expFechaIn = lector.readLine();

                // Fecha Fin
                String expFechaFin = lector.readLine();    

                // Cientifico
                ArrayList<Cientifico> expListaCientifico = new ArrayList<>();
                String[] arregloCientifico = lector.readLine().split(" ");
                for (String dniFecha : arregloCientifico) {
                    String[] arregloDniFecha = dniFecha.split(",");
                    for (Cientifico c : listaCientifico) {
                        if (arregloDniFecha[0].equals(c.getDni())) {
                            c.setContratacion(arregloDniFecha[0]);
                            expListaCientifico.add(c);
                        }
                    }
                }
 

                // Equipoj
                ArrayList<Equipo> expListaEquipos = new ArrayList<>();
                String[] arregloEquipos = lector.readLine().split(",");
                for (String nombreEquipo : arregloEquipos) {
                    for (Equipo e : listaEquipo) {
                        if (nombreEquipo.equals(e.getNombre())) {
                            e.setContador(e.getContador() + 1);
                            expListaEquipos.add(e);

                        }
                    }
                }
                // Tipo de Experimento
                String expTipo = lector.readLine();

                if (expTipo.equals("Fisico")) {
                    // Fenomeno
                    String expFenomeno = lector.readLine();
                    listaExperimentosBioFis.add(
                            new Experimento_Fisico(
                                    expTitulo,
                                    expDescr,
                                    expPresupuesto,
                                    expTipo,
                                    expFechaIn,
                                    expFechaFin,
                                    expListaCientifico,//Tengo que clonar la lista porque si lo hago de manera directa lo que pasa en realidad es que el puntero va a la direccion de memoria de la lista y esa esta permamentemente cambiando.
                                    expListaEquipos,
                                    expFenomeno));
                } else {
                    //Organismo
                    String expOrganismo = lector.readLine();
                    listaExperimentosBioFis.add(
                            new Experimento_Biologico(
                                    expTitulo,
                                    expDescr,
                                    expPresupuesto,
                                    expTipo,
                                    expFechaIn,
                                    expFechaFin,
                                    expListaCientifico,//Tengo que clonar la lista porque si lo hago de manera directa lo que pasa en realidad es que el puntero va a la direccion de memoria de la lista y esa esta permamentemente cambiando.
                                    expListaEquipos,
                                    expOrganismo));
                }
                
            }
            lector.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return listaExperimentosBioFis;
    }

    public void guardarExperimentos(ArrayList<Experimento> listaExperimentos) {

        try {
            File myObj = new File("C:\\Users\\ttoom\\OneDrive\\Escritorio\\UNIVERSIDADD\\Programacion II\\TP de Maquina\\trabajo2\\experimentos.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter escritorArchivo = new FileWriter("C:\\Users\\ttoom\\OneDrive\\Escritorio\\UNIVERSIDADD\\Programacion II\\TP de Maquina\\trabajo2\\experimentos.txt");
            for (Experimento e : listaExperimentos) {

                // Titulo
                escritorArchivo.write(e.getTitulo() + "\n");

                // Descripcion
                escritorArchivo.write(e.getDescripcion() + "\n");

                // Presupuesto
                escritorArchivo.write(Float.toString(e.getPresupuesto()) + "\n");

                // Fecha Inicio
                escritorArchivo.write(e.getInicio() + "\n");

                // Fecha Fin
                escritorArchivo.write(e.getFin() + "\n");

                // Cientifico
                boolean primerElemento = true;

                for (Cientifico c : e.getListaCientifico()) {
                    // Si no es el primer elemento, agrega una coma antes del nombre del equipo
                    if (!primerElemento) {
                        escritorArchivo.write(" ");
                    } else {
                        // Si es el primer elemento, actualiza la variable a "false"
                        primerElemento = false;
                    }
                    escritorArchivo.write(c.getDni()+",");
                }
                escritorArchivo.write("\n");

                // Equipo
                boolean primerElementoEquipo = true;

                for (Equipo equipo : e.getListaEquipo()) {
                    // Si no es el primer elemento, agrega una coma antes del nombre del equipo
                    if (!primerElementoEquipo) {
                        escritorArchivo.write(",");
                    } else {
                        // Si es el primer elemento, actualiza la variable a "false"
                        primerElementoEquipo = false;
                    }
                    escritorArchivo.write(equipo.getNombre());
                }
                escritorArchivo.write("\n");

                // Tipo de Experimento
                escritorArchivo.write(e.getTipo() + "\n");

                if (e.getTipo().equals("Fisico")) {
                    // Fenomeno
                    escritorArchivo.write(((Experimento_Fisico) e).getFenomeno() + "\n");
                } else {
                    //Organismo
                    escritorArchivo.write(((Experimento_Biologico) e).getOrganismo() + "\n");
                }

            }
            escritorArchivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarEquipos() {
        try {
            FileWriter escritorArchivo = new FileWriter(nombreArchivoEquipos);
            for (Equipo e1 : listaEquipo) {
                // Titulo
                escritorArchivo.write(e1.getNombre() + "\n");

                // Descripcion
                escritorArchivo.write(e1.getDescripcion() + "\n");

                // Presupuesto
                escritorArchivo.write((e1.getAreaEspecialidad()) + "\n");

                // Contador
                escritorArchivo.write((e1.getContador()) + "\n");
            }
            escritorArchivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
