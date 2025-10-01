/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
public class Condominio {
    String nombre;
    String direccion;

    /**
     * Constructor para crear un nuevo objeto Condominio.
     *
     * @param n El nombre oficial del condominio.
     * @param d La dirección física donde se ubica el condominio.
     */
    public Condominio(String n, String d) {
        this.nombre = n;
        this.direccion = d;
    }

    /**
     * Devuelve una representación en formato de texto del objeto Condominio.
     *
     * @return Un String que incluye el nombre y la dirección para una fácil visualización.
     */
    @Override
    public String toString() {
        return "Condominio: " + nombre + " (" + direccion + ")";
    }
}
