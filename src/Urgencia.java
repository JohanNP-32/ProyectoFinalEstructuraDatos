/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
/**
 * Define un conjunto de niveles para representarl la urgencia de una tarea.
 * El orden de declaración (de ALTA a BAJA) es importante, ya que se usa
 * para la ordenación automática en la cola de prioridad.
 */
public enum Urgencia {
    /** Máxima prioridad. */
    ALTA,
    
    /** Prioridad intermedia. */
    MEDIA,
    
    /** Baja prioridad. */
    BAJA 
}