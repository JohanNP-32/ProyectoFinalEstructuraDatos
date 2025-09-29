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

    public Tarea(String d, String dep, Urgencia u, double c, LocalDate f) {
        this.id = nextId++; this.descripcion = d; this.departamento = dep;
        this.urgencia = u; this.costo = c; this.completada = false;
        this.fecha = f; this.idPrerrequisitos = new ArrayList<>();
    }
    
    public int getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public String getDepartamento() { return departamento; }
    public Urgencia getUrgencia() { return urgencia; }
    public double getCosto() { return costo; }
    public boolean estaCompletada() { return completada; }
    public LocalDate getFecha() { return fecha; }
    public List<Integer> getPrerrequisitos() { return idPrerrequisitos; }
    public void completar() { this.completada = true; }
    public void agregarPrerrequisito(int id) { this.idPrerrequisitos.add(id); }
    
    @Override
    public int compareTo(Tarea otra) {
        int compUrgencia = this.urgencia.compareTo(otra.urgencia);
        if (compUrgencia != 0) return compUrgencia;
        return this.fecha.compareTo(otra.fecha);
    }
}