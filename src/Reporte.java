/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.time.LocalDate;

public class Reporte {
    private static int nextId = 1;
    public int id;
    String titulo;
    LocalDate fecha;
    double totalIngresos;
    double totalEgresos;
    double balance;

    public Reporte(String titulo, double totalIngresos, double totalEgresos) {
        this.id = nextId++; this.titulo = titulo; this.fecha = LocalDate.now();
        this.totalIngresos = totalIngresos; this.totalEgresos = totalEgresos;
        this.balance = totalIngresos - totalEgresos;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + " | Titulo: '" + titulo + "'";
    }
}