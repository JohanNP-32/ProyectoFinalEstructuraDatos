/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Un panel personalizado que se dibuja con bordes redondeados y un borde de color opcional en la parte superior.
public class RoundedPanel extends JPanel {
    private final int cornerRadius;
    private final Color borderColor;

    /**
     * Constructor para el panel con bordes redondeados.
     * @param radius El radio de las esquinas redondeadas.
     * @param border El color del borde superior (puede ser null si no se desea un borde).
     */
    public RoundedPanel(int radius, Color border) {
        super();
        this.cornerRadius = radius;
        this.borderColor = border;
        setOpaque(false); // Se hace transparente para permitir el dibujado personalizado.
    }

    /**
     * Sobrescribe el método de dibujado para crear la apariencia redondeada.
     * @param g El contexto gráfico proporcionado por Swing para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Activa el antialiasing para que los bordes se vean suaves.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuja el rectángulo de fondo con las esquinas redondeadas.
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Si se especificó un color de borde, dibuja una franja superior.
        if (borderColor != null) {
            g2d.setColor(borderColor);
            g2d.fillRoundRect(0, 0, getWidth(), 5, cornerRadius, cornerRadius);
        }
        
        g2d.dispose(); 
    }
}