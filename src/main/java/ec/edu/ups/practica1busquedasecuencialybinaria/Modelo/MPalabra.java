/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.practica1busquedasecuencialybinaria.Modelo;

/**
 *
 * @author ASUS
 */
public class MPalabra 
{
    private String termino;
    private String significado;

    public MPalabra(String termino, String significado) {
        this.termino = termino;
        this.significado = significado;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getSignificado() {
        return significado;
    }

    public void setSignificado(String significado) {
        this.significado = significado;
    }
    
    
}
