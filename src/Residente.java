/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
// Representa a un residente del condominio, con su información personal y estado de cuenta.
public class Residente {
    private static int nextId = 1;
    private int id;
    private String nombre;
    private String departamento;
    private String telefono;
    private double saldo; // Un valor negativo indica una deuda.

    /**
     * Constructor para crear un nuevo Residente.
     * @param nombre El nombre completo del residente.
     * @param departamento El identificador de su departamento (ej. "A-101").
     * @param telefono El número de contacto del residente.
     * @param saldo El saldo inicial (negativo si empieza con deuda).
     */
    public Residente(String nombre, String departamento, String telefono, double saldo) {
        this.id = nextId++;
        this.nombre = nombre;
        this.departamento = departamento;
        this.telefono = telefono;
        this.saldo = saldo;
    }
    
    /**
     * Devuelve el ID único del residente.
     * @return El ID del residente.
     */
    public int getId() { return id; }

    /**
     * Devuelve el nombre completo del residente.
     * @return El nombre del residente.
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve el departamento del residente.
     * @return El departamento del residente.
     */
    public String getDepartamento() { return departamento; }

    /**
     * Devuelve el teléfono del residente.
     * @return El teléfono del residente.
     */
    public String getTelefono() { return telefono; }

    /**
     * Devuelve el saldo actual del residente.
     * @return El saldo (positivo o negativo).
     */
    public double getSaldo() { return saldo; }
    
    /**
     * Verifica si el residente tiene una deuda (saldo negativo).
     * @return `true` si el saldo es menor a cero, `false` en caso contrario.
     */
    public boolean tieneDeuda() { return this.saldo < 0; }

    /**
     * Suma un monto al saldo actual (ej. al registrar un pago).
     * @param monto La cantidad a sumar.
     */
    public void sumarSaldo(double monto) { this.saldo += monto; }

    /**
     * Resta un monto del saldo actual (ej. al aplicar una cuota).
     * @param monto La cantidad a restar.
     */
    public void restarSaldo(double monto) { this.saldo -= monto; }

    /**
     * Genera una representación en texto del estado de cuenta del residente.
     * @return Un String formateado con la información principal del residente.
     */
    @Override
    public String toString() {
        String estado;
        if (tieneDeuda()) estado = "DEUDA DE " + String.format("($%,.2f)", Math.abs(saldo));
        else estado = "SALDO A FAVOR de " + String.format("$%,.2f", saldo);
        return "ID: " + id + " | " + nombre + " | Depto: " + departamento + " | Estado: " + estado;
    }
}