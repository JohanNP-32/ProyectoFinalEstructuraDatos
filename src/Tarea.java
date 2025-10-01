/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Representa una tarea de trabajo con detalles como descripción, costo, urgencia y dependencias.
public class Tarea implements Comparable<Tarea> {
    private static int nextId = 1;
    private int id;
    private String descripcion;
    private String departamento;
    private Urgencia urgencia;
    private double costo;
    private boolean completada;
    private LocalDate fecha;
    private List<Integer> idPrerrequisitos;

    /**
     * Constructor para crear una nueva Tarea.
     * @param d La descripción de la tarea.
     * @param dep El departamento asignado (ej. "Mantenimiento").
     * @param u El nivel de urgencia (ALTA, MEDIA, BAJA).
     * @param c El costo estimado de la tarea.
     * @param f La fecha límite o de realización.
     */
    public Tarea(String d, String dep, Urgencia u, double c, LocalDate f) {
        this.id = nextId++; this.descripcion = d; this.departamento = dep;
        this.urgencia = u; this.costo = c; this.completada = false;
        this.fecha = f; this.idPrerrequisitos = new ArrayList<>();
    }
    
    /** Devuelve el ID único de la tarea. */
    public int getId() { return id; }
    /** Devuelve la descripción de la tarea. */
    public String getDescripcion() { return descripcion; }
    /** Devuelve el departamento asignado. */
    public String getDepartamento() { return departamento; }
    /** Devuelve el nivel de urgencia. */
    public Urgencia getUrgencia() { return urgencia; }
    /** Devuelve el costo de la tarea. */
    public double getCosto() { return costo; }
    /** Verifica si la tarea ha sido completada. */
    public boolean estaCompletada() { return completada; }
    /** Devuelve la fecha de la tarea. */
    public LocalDate getFecha() { return fecha; }
    /** Devuelve la lista de IDs de tareas prerrequisito. */
    public List<Integer> getPrerrequisitos() { return idPrerrequisitos; }

    /** Marca la tarea como completada. */
    public void completar() { this.completada = true; }

    /**
     * Añade el ID de una tarea como prerrequisito de esta.
     * @param id El ID de la tarea que debe completarse antes.
     */
    public void agregarPrerrequisito(int id) { this.idPrerrequisitos.add(id); }
    
    /**
     * Compara esta tarea con otra para determinar el orden de prioridad.
     * Se ordena primero por urgencia y luego por fecha.
     * @param otra La otra tarea con la que se va to comparar.
     * @return Un valor negativo, cero o positivo si esta tarea es menor, igual o mayor que la otra.
     */
    @Override
    public int compareTo(Tarea otra) {
        // Primero, compara por urgencia (ALTA viene antes que MEDIA).
        int compUrgencia = this.urgencia.compareTo(otra.urgencia);
        if (compUrgencia != 0) return compUrgencia;
        // Si la urgencia es la misma, la fecha más próxima tiene mayor prioridad.
        return this.fecha.compareTo(otra.fecha);
    }
}