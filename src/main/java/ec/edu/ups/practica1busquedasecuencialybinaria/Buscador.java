/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.practica1busquedasecuencialybinaria;

import java.util.Arrays;

/**
 *
 * @author ASUS
 */
public class Buscador 
{
    private int comparaciones;

    public int getComparaciones() {
        return comparaciones;
    }

    public void reiniciarContador() {
        this.comparaciones = 0;
    }


    public int secuencial(String[] arreglo, String terminoBuscado, boolean contar) {
        if (contar) reiniciarContador();
        for (int i = 0; i < arreglo.length; i++) {
            if (contar) comparaciones++;
            if (arreglo[i].equals(terminoBuscado)) {
                return i;
            }
        }
        return -1;
    }


    public int binariaPropia(String[] arreglo, String terminoBuscado, boolean contar) {
        if (contar) reiniciarContador();
        int izq = 0;
        int der = arreglo.length - 1;

        while (izq <= der) {
            int medio = izq + (der - izq) / 2;
            if (contar) comparaciones++;
            
            int comp = arreglo[medio].compareTo(terminoBuscado);
            if (comp == 0) {
                return medio;
            } else if (comp < 0) {
                izq = medio + 1; 
            } else {
                der = medio - 1;
            }
        }
        return -1;
    }


    public int binariaJava(String[] arreglo, String terminoBuscado) {
        int indice = Arrays.binarySearch(arreglo, terminoBuscado);

        return (indice >= 0) ? indice : -1;
    }
    
}
