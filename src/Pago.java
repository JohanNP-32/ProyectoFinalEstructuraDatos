/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pago {
    private double monto;
    private LocalDateTime timestamp;
    private Residente residenteAsignado;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Pago(double monto, Residente residenteAsignado) {
        this.monto = monto;
        this.residenteAsignado = residenteAsignado;
        this.timestamp = LocalDateTime.now();
    }
    
    public double getMonto() { return monto; }
    public String getTimestampFormatted() { return timestamp.format(FORMATTER); }
    public Residente getResidente() { return residenteAsignado; }
}