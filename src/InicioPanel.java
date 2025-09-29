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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class InicioPanel extends JPanel {
    private final SistemaController controller;
    private JLabel residentesValueLabel;
    private JLabel pendientesValueLabel;
    private JLabel completadasValueLabel;
    private JLabel ingresosValueLabel;

    public InicioPanel(SistemaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(20, 30));
        setBorder(new EmptyBorder(0, 25, 25, 25));
        setOpaque(false);

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 25, 0));
        statsPanel.setOpaque(false);
        
        residentesValueLabel = createValueLabel();
        pendientesValueLabel = createValueLabel();
        completadasValueLabel = createValueLabel();
        ingresosValueLabel = createValueLabel();
        
        statsPanel.add(createStatCard("Total de Residentes", residentesValueLabel, Theme.ACCENT_BLUE, "👤", "Residentes"));
        statsPanel.add(createStatCard("Tareas Pendientes", pendientesValueLabel, Theme.ACCENT_ORANGE, "📋", "Tareas Pendientes"));
        statsPanel.add(createStatCard("Tareas Completadas", completadasValueLabel, Theme.ACCENT_GREEN, "✔️", "Tareas Completadas"));
        statsPanel.add(createStatCard("Ingresos Totales", ingresosValueLabel, new Color(0xAF87FF), "💰", "Ingresos"));
        
        RoundedPanel bottomPanel = new RoundedPanel(15, null);
        bottomPanel.setBackground(Theme.PANEL_DARK);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel bottomLabel = new JLabel("Métricas Adicionales & Reportes Rápidos");
        bottomLabel.setFont(Theme.FONT_SUBTITLE.deriveFont(Font.BOLD));
        bottomLabel.setForeground(Theme.TEXT_LIGHT);
        bottomPanel.add(bottomLabel, BorderLayout.NORTH);

        add(statsPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                actualizarEstadisticas();
            }
        });
    }

    private JLabel createValueLabel() {
        JLabel label = new JLabel("0", SwingConstants.LEFT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 48));
        label.setForeground(Theme.TEXT_WHITE);
        return label;
    }

    public void actualizarEstadisticas() {
        residentesValueLabel.setText(String.valueOf(controller.getResidentes().size()));
        long pendientes = controller.getTareasOrdenadas().stream().filter(t -> !t.estaCompletada()).count();
        pendientesValueLabel.setText(String.valueOf(pendientes));
        completadasValueLabel.setText(String.valueOf(controller.getTareasOrdenadas().size() - pendientes));
        double ingresos = controller.getHistorialPagos().stream().mapToDouble(Pago::getMonto).sum();
        ingresosValueLabel.setText(NumberFormat.getCurrencyInstance(Locale.US).format(ingresos));
    }

    private RoundedPanel createStatCard(String title, JLabel valueLabel, Color borderColor, String icon, String buttonText) {
        RoundedPanel card = new RoundedPanel(15, borderColor);
        card.setBackground(Theme.PANEL_DARK);
        card.setLayout(new BorderLayout(15, 10)); 
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.FONT_SUBTITLE);
        titleLabel.setForeground(Theme.TEXT_LIGHT);

        textPanel.add(valueLabel);
        textPanel.add(Box.createVerticalStrut(5)); 
        textPanel.add(titleLabel);
        
        CircularIconPanel iconPanel = new CircularIconPanel(icon, borderColor);

        
        card.add(textPanel, BorderLayout.CENTER);
        card.add(iconPanel, BorderLayout.EAST);

        return card;
    }

    
    private static class CircularIconPanel extends JPanel {
        private final String icon;
        private final Color borderColor;

        public CircularIconPanel(String icon, Color borderColor) {
            this.icon = icon;
            this.borderColor = borderColor;
            setOpaque(false);
            setPreferredSize(new Dimension(45, 45)); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.setColor(borderColor.brighter().brighter().brighter());
            g2d.fillOval(0, 0, 45, 45);
            
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(1, 1, 43, 43);

            g2d.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
            FontMetrics fm = g2d.getFontMetrics();
            int x = (45 - fm.stringWidth(icon)) / 2;
            int y = (fm.getAscent() + (45 - (fm.getAscent() + fm.getDescent())) / 2);
            g2d.drawString(icon, x, y);
            
            g2d.dispose();
        }
    }
}