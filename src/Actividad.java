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

public class Actividad {
    private String descripcion;
    private LocalDateTime timestamp;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Actividad(String descripcion) {
        this.descripcion = descripcion;
        this.timestamp = LocalDateTime.now();
    }

    public String getDescripcion() { return descripcion; }
    public String getTimestampFormatted() { return timestamp.format(FORMATTER); }

    @Override
    public String toString() {
        return "[" + getTimestampFormatted() + "] " + descripcion;
    }
}
