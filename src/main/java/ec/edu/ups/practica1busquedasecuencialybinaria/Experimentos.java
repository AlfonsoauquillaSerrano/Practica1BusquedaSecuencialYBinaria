/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.practica1busquedasecuencialybinaria;

import ec.edu.ups.practica1busquedasecuencialybinaria.Modelo.MPalabra;
import java.util.Arrays;

public class Experimentos {

    private final Buscador buscador;

    public Experimentos(Buscador buscador) {
        this.buscador = buscador;
    }

    // Generador de datos sintéticos (de 'aaa' a 'zzz')
    public MPalabra[] generarPalabrasSinteticas(int cantidad) {
        MPalabra[] lista = new MPalabra[cantidad];
        int totalCombinaciones = 26 * 26 * 26; // 17576 combinaciones sin la lentra enie
        int paso = totalCombinaciones / cantidad;//los saltos que se toma para no usar todas las letras

        // operacion de sacar los numeros inviduales para que nunca se repita una palabra y salga ordena por i
        for (int i = 0; i < cantidad; i++) {
            int valor = i * paso;// valor de la letra en codigo sski 
            char c1 = (char) ('a' + (valor / (26 * 26)));//saca la centena
            char c2 = (char) ('a' + ((valor / 26) % 26));//saca la decena
            char c3 = (char) ('a' + (valor % 26));//saca la unidad

            String termino = "" + c1 + c2 + c3;
            lista[i] = new MPalabra(termino, "Definicion sintetica de " + termino);
        }
        return lista;
    }

    // Extraer arreglo String[] conservando posiciones
    public String[] extraerTerminos(MPalabra[] palabras) {
        String[] terminos = new String[palabras.length];
        for (int i = 0; i < palabras.length; i++) {
            terminos[i] = palabras[i].getTermino();
        }
        return terminos;
    }

    // Medición de las 5 rondas y mediana
    public double medirMedianaTiempo(String[] arreglo, String termino, int tipoMetodo) {
        // Calentamiento
        for (int i = 0; i < 1000; i++) {
            ejecutarBusqueda(arreglo, termino, tipoMetodo);
        }

        double[] rondas = new double[5];//rondas de medicion de tiempo

        for (int r = 0; r < 5; r++) {
            long inicio = System.nanoTime();

            // Bloque de 10.000 repeticiones
            for (int i = 0; i < 10000; i++) {
                ejecutarBusqueda(arreglo, termino, tipoMetodo);
            }

            long fin = System.nanoTime();
            rondas[r] = (double) (fin - inicio) / 10000.0;
        }

        Arrays.sort(rondas);//ordena el arreglo de menor a mayor para poder sacar la media
        return rondas[2]; // Mediana
    }

    private int ejecutarBusqueda(String[] arreglo, String termino, int tipoMetodo) {
        if (tipoMetodo == 1) {
            return buscador.secuencial(arreglo, termino, false);
        } else if (tipoMetodo == 2) {
            return buscador.binariaPropia(arreglo, termino, false);
        } else {
            return buscador.binariaJava(arreglo, termino);
        }
    }

    // Ejecución de pruebas
    public void ejecutarPruebasRendimiento() {
        System.out.println();
        System.out.println("==========================================================================");
        System.out.println("============== 2. EXPERIMENTOS Y MEDICION DE RENDIMIENTO =================");
        System.out.println("==========================================================================");

        int[] tamanos = {100, 1000, 10000};

        for (int n : tamanos) {
            MPalabra[] datos = generarPalabrasSinteticas(n);
            String[] arr = extraerTerminos(datos);

            String qPrimero = arr[0]; // 1. El primer elemento (índice 0)
            String qMedio = arr[n / 2]; // 2. El elemento del centro exacto
            String qUltimo = arr[n - 1]; // 3. El último elemento
            String qAntes = "000";// 4. Ausente anterior: en la tabla ASCII '0' va antes que 'a'
            String qEntre = arr[n / 2] + "x";// 5. Ausente intermedio: le pega una 'x' a la palabra del medio
            String qDespues = "~~~"; // 6. Ausente posterior: en la tabla ASCII '~' va después de 'z'

            String[] nombresCasos = {
                "Primero", "Medio", "Ultimo",
                "Ausente Antes", "Ausente Entre", "Ausente Despues"
            };
            String[] consultas = {qPrimero, qMedio, qUltimo, qAntes, qEntre, qDespues};//CASOS

            System.out.println();
            System.out.println("=== RESULTADOS PARA N = " + n + " ===");
            System.out.println("Caso | Metodo | Indice | Comparaciones | Mediana Tiempo (ns)");
            System.out.println("------------------------------------------------------------------");

            for (int c = 0; c < consultas.length; c++) {
                String caso = nombresCasos[c];//inidica con los casos
                String query = consultas[c];//dato del caso 

                // Conteo de comparaciones
                buscador.secuencial(arr, query, true);
                int compSec = buscador.getComparaciones();

                buscador.binariaPropia(arr, query, true);
                int compBin = buscador.getComparaciones();

                // Tiempos
                double tSec = medirMedianaTiempo(arr, query, 1);
                double tBin = medirMedianaTiempo(arr, query, 2);
                double tJava = medirMedianaTiempo(arr, query, 3);

                // Índices encontrados
                int idxSec = buscador.secuencial(arr, query, false);
                int idxBin = buscador.binariaPropia(arr, query, false);
                int idxJava = buscador.binariaJava(arr, query);

                // Salidas con System.out.println y concatenación (+)
                System.out.println(caso + " | Secuencial | Indice: " + idxSec + " | Comp: " + compSec + " | " + tSec + " ns");
                System.out.println(caso + " | Binaria Propia | Indice: " + idxBin + " | Comp: " + compBin + " | " + tBin + " ns");
                System.out.println(caso + " | Arrays.binary | Indice: " + idxJava + " | Comp: No disponible | " + tJava + " ns");
            }
        }
    }
}
