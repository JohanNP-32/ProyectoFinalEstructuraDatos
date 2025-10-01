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
    
    // Formateador estático para asegurar que todas las fechas se muestren de la misma manera.
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor para crear una nueva Actividad.
     * Automáticamente captura la fecha y hora actual al ser instanciada.
     *
     * @param descripcion El texto que describe la actividad.
     */
    public Actividad(String descripcion) {
        this.descripcion = descripcion;
        this.timestamp = LocalDateTime.now(); // Captura el momento exacto de la creación
    }

    /**
     * Obtiene la descripción de la actividad.
     * @return El texto de la descripción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la marca de tiempo de la actividad, ya formateada como un String legible.
     * @return La fecha y hora en formato "yyyy-MM-dd HH:mm:ss".
     */
    public String getTimestampFormatted() {
        return timestamp.format(FORMATTER);
    }

    /**
     * Genera una representación en formato de texto de la actividad, lista para ser mostrada en logs o en la interfaz.
     * @return Un String formateado con la fecha, hora y descripción.
     */
    @Override
    public String toString() {
        return "[" + getTimestampFormatted() + "] " + descripcion;
    }
}