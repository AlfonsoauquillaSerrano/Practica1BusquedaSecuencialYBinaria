package ec.edu.ups.practica1busquedasecuencialybinaria;

import ec.edu.ups.practica1busquedasecuencialybinaria.Modelo.MPalabra;

/**
 *
 * @author ASUS
 */
public class Practica1BusquedaSecuencialYBinaria {

    public static void main(String[] args) {
        Buscador buscador = new Buscador();

        System.out.println("========== 1. DICCIONARIO BASE DE 8 PALABRAS ==========");

        MPalabra[] diccionario = {
            new MPalabra("arbol", "Planta leniosa perenne."),
            new MPalabra("barco", "Vehiculo flotante para navegar."),
            new MPalabra("cielo", "Espacio en el que se mueven los astros."),
            new MPalabra("delta", "Desembocadura de un rio en varios brazos."),
            new MPalabra("fuego", "Fenomeno calorico y luminoso."),
            new MPalabra("grano", "Semilla pequena de los cereales."),
            new MPalabra("nieve", "Agua helada que cae de las nubes."),
            new MPalabra("zorro", "Mamifero carnivoro de hocico alargado.")
        };

        String[] terminos = new String[diccionario.length];
        for (int i = 0; i < diccionario.length; i++) {
            terminos[i] = diccionario[i].getTermino();
        }

        String[] consultas8 = {
            "arbol", // primer término (mejor caso secuencial)
            "delta", // término intermedio (caso promedio)
            "zorro", // último término (peor caso secuencial)
            "delfin" // inexistente entre delta y fuego
        };

        for (String q : consultas8) {
            probarConsulta(terminos, diccionario, q, buscador);
        }

        System.out.println("\n--- Caso extremo: Arreglo vacio ---");
        probarConsulta(new String[0], new MPalabra[0], "palabra", buscador);

        System.out.println("\n--- Caso extremo: Arreglo de 1 termino ---");
        MPalabra[] unitarioP = {new MPalabra("luz", "Radiacion visible.")};
        String[] unitarioT = {"luz"};
        probarConsulta(unitarioT, unitarioP, "luz", buscador);     // Presente
        probarConsulta(unitarioT, unitarioP, "sombra", buscador);  // Ausente

        Experimentos experimentos = new Experimentos(buscador);
        experimentos.ejecutarPruebasRendimiento();
    }

    private static void probarConsulta(String[] arr, MPalabra[] dic, String query, Buscador b) {
        System.out.println("\nConsulta: '" + query + "'");

        // 1. Búsqueda Secuencial
        int iSec = b.secuencial(arr, query, true);
        int cSec = b.getComparaciones();
        String signSec = (iSec >= 0) ? dic[iSec].getSignificado() : "AUSENTE";

        // 2. Búsqueda Binaria Propia
        int iBin = b.binariaPropia(arr, query, true);
        int cBin = b.getComparaciones();
        String signBin = (iBin >= 0) ? dic[iBin].getSignificado() : "AUSENTE";
        //Significado    condicion      valor si es verdadero       valor si es falso

        // 3. Búsqueda Binaria Biblioteca
        int iLib = b.binariaJava(arr, query);
        String signLib = (iLib >= 0) ? dic[iLib].getSignificado() : "AUSENTE";

        // Salida normal con System.out.println y concatenacion simple (+)
        System.out.println("  [Secuencial]     Indice: " + iSec + " | Comp: " + cSec + " | Significado: " + signSec);
        System.out.println("  [Binaria Propia] Indice: " + iBin + " | Comp: " + cBin + " | Significado: " + signBin);
        System.out.println("  [Binaria Java]   Indice: " + iLib + " | Comp: No disponible | Significado: " + signLib);
    }

}