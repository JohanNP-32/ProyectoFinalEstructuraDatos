/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.time.LocalDate;

// Define los posibles estados de un reporte.
enum ReporteStatus {
    ACTIVO,
    CANCELADO
}

// Representa un reporte financiero con ingresos, egresos y un balance.
public class Reporte {
    private static int nextId = 1;
    public int id;
    String titulo;
    LocalDate fecha;
    double totalIngresos;
    double totalEgresos;
    double balance;
    private ReporteStatus status;

    /**
     * Constructor para un nuevo reporte financiero.
     * @param titulo El título descriptivo del reporte.
     * @param totalIngresos La suma total de ingresos para este reporte.
     * @param totalEgresos La suma total de egresos para este reporte.
     */
    public Reporte(String titulo, double totalIngresos, double totalEgresos) {
        this.id = nextId++;
        this.titulo = titulo;
        this.fecha = LocalDate.now();
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
        this.balance = totalIngresos - totalEgresos;
        this.status = ReporteStatus.ACTIVO; // Por defecto, un reporte nuevo está activo.
    }
    
    /**
     * Devuelve el estado actual del reporte.
     * @return El estado del reporte (ACTIVO o CANCELADO).
     */
    public ReporteStatus getStatus() {
        return status;
    }

    /**
     * Actualiza el estado del reporte.
     * @param status El nuevo estado a establecer (ACTIVO o CANCELADO).
     */
    public void setStatus(ReporteStatus status) {
        this.status = status;
    }

    /**
     * Devuelve una representación simple del reporte en texto.
     * @return Un String con el ID y el título del reporte.
     */
    @Override
    public String toString() {
        return "ID: " + id + " | Titulo: '" + titulo + "'";
    }
}