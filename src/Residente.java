/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
public class Residente {
    private static int nextId = 1;
    private int id;
    private String nombre;
    private String departamento;
    private String telefono;
    private double saldo; 

    public Residente(String nombre, String departamento, String telefono, double saldo) {
        this.id = nextId++;
        this.nombre = nombre;
        this.departamento = departamento;
        this.telefono = telefono;
        this.saldo = saldo;
    }
    
    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDepartamento() { return departamento; }
    public String getTelefono() { return telefono; }
    public double getSaldo() { return saldo; }
    public boolean tieneDeuda() { return this.saldo < 0; }

    // Métodos para modificar el saldo
    public void sumarSaldo(double monto) { this.saldo += monto; } 
    public void restarSaldo(double monto) { this.saldo -= monto; } 

    @Override
    public String toString() {
        String estado;
        if (tieneDeuda()) estado = "DEUDA DE " + String.format("($%,.2f)", Math.abs(saldo));
        else estado = "SALDO A FAVOR de " + String.format("$%,.2f", saldo);
        return "ID: " + id + " | " + nombre + " | Depto: " + departamento + " | Estado: " + estado;
    }
}