/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.time.LocalDate;

// Se crea un enum para manejar los estados de forma segura
enum ReporteStatus {
    ACTIVO,
    CANCELADO
}

public class Reporte {
    private static int nextId = 1;
    public int id;
    String titulo;
    LocalDate fecha;
    double totalIngresos;
    double totalEgresos;
    double balance;
    private ReporteStatus status; // Nuevo campo para el estado

    public Reporte(String titulo, double totalIngresos, double totalEgresos) {
        this.id = nextId++;
        this.titulo = titulo;
        this.fecha = LocalDate.now();
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
        this.balance = totalIngresos - totalEgresos;
        this.status = ReporteStatus.ACTIVO; // Por defecto, un reporte nuevo está activo
    }
    
    // Getter y Setter para el nuevo estado
    public ReporteStatus getStatus() {
        return status;
    }

    public void setStatus(ReporteStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Titulo: '" + titulo + "'";
    }
}