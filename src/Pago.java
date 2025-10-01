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

    /**
     * Constructor para un nuevo registro de pago.
     * @param monto La cantidad de dinero pagada.
     * @param residenteAsignado El residente que realizó el pago.
     */
    public Pago(double monto, Residente residenteAsignado) {
        this.monto = monto;
        this.residenteAsignado = residenteAsignado;
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * Devuelve el monto del pago.
     * @return La cantidad pagada.
     */
    public double getMonto() {
        return monto;
    }

    /**
     * Devuelve la fecha y hora del pago en un formato de texto.
     * @return La fecha y hora formateadas.
     */
    public String getTimestampFormatted() {
        return timestamp.format(FORMATTER);
    }

    /**
     * Devuelve el objeto Residente asociado a este pago.
     * @return El residente que pagó.
     */
    public Residente getResidente() {
        return residenteAsignado;
    }
}