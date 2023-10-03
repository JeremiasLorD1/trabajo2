/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectointegrador_tomas_jeremias;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clases Experimento: (título, descripción, presupuesto, fecha de inicio y
 * fecha de fin) <--- tiene que ser abstracta no se usa para nada>
 * Experimento_Biologico: (tipo de organismo) Experimento_Fisico: (fenómeno
 * estudiado) Científicos: (conoce su nombre, especialidad y fecha de
 * contratación) x experimento hasta 4. Equipos:(el nombre, descripción y área
 * de especialidad.) x experimento mas de 1. Area: CLASE APARTE O PARA MI ES SI
 * ES BIOLOGICO/FISICO
 *
 *
 **PUEDE SER QUE NECESITEMOS MAS ATRIBUTOS DE ALGUNA CLASE PARA QUE FUNCIONE!**
 *
 * Metodos *DENTRO DE EXPERIMENTO** a. Carga. b. Modificación. c. Eliminación.
 * d. Persistencia de datos.(QUE SE GUARDE LA INFO LUEGO DE CERRAR)
 *
 * a. Tipo de equipos más utilizados. b. Cantidad de experimento Físicos y
 * Biológicos. *DENTRO DE SISTEMA** c. El experimento de mayor y el de menor
 * duración. d. Presupuesto total de todos los experimento.
 *
 *
 *
 *
 * @author jerem
 */


public class ProyectoIntegrador_Tomas_Jeremias {

    public static void main(String[] args) {
        //Experimento_Biologico Planta = new Experimento_Biologico ("Planta","Plantas verdes","Las plantas verdes son re piolas amigo",a,1/2/2003,1/3/2003));

        ArrayList<Experimento> listaExperimentoBiolYFis = new ArrayList<>();
        System.out.println("Ingrese una cantidad de experimento para cargar de tipo biologico");
        int i;
        Scanner teclado = new Scanner(System.in);
        int cantidad = teclado.nextInt();

        for (i = 0; i < cantidad; i++) {
            
        }

        System.out.println("Ingrese una cantidad de experimento para cargar de tipo fisico");

        System.out.println("Ingrese una cantidad de cientficos para cargar");
        System.out.println("Ingrese un cantidadad de equipos para cargar");

    }

    /*La carga depende del experimento ya que sabiendo este podemos hablar de cuantos equipos usa y cuantos cientificos
por lo tanto cargamos el experimento y vemos que onda el resto, si voy a tener  */
}
