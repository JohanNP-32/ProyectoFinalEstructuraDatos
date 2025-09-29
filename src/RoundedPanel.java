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

public class RoundedPanel extends JPanel {
    private final int cornerRadius;
    private final Color borderColor;

    public RoundedPanel(int radius, Color border) {
        super();
        this.cornerRadius = radius;
        this.borderColor = border;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        
        if (borderColor != null) {
            g2d.setColor(borderColor);
            g2d.fillRoundRect(0, 0, getWidth(), 5, cornerRadius, cornerRadius);
        }
        
        g2d.dispose();
    }
}