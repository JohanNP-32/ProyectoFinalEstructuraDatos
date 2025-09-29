/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class HistorialPanel extends JPanel {
    private final SistemaController controller;
    private final JTable tablaHistorial;
    private final DefaultTableModel tableModel;

    public HistorialPanel(SistemaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 20));
        setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
        setOpaque(false);

        String[] cols = {"Fecha y Hora", "Descripción"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaHistorial = new JTable(tableModel);
        styleTable(tablaHistorial);
        
        actualizarTabla();

        RoundedPanel tableContainer = new RoundedPanel(15, null);
        tableContainer.setBackground(Theme.PANEL_DARK);
        tableContainer.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        
        add(tableContainer, BorderLayout.CENTER);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                actualizarTabla();
            }
        });
    }
    
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for(Actividad act : controller.getHistorialActividades()) {
            tableModel.addRow(new Object[]{ act.getTimestampFormatted(), act.getDescripcion() });
        }
    }
    
    private void styleTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setRowHeight(40);
        table.setFont(Theme.FONT_NORMAL);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowGrid(false);
        table.setOpaque(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(Theme.FONT_BOLD.deriveFont(16f));
        header.setBackground(Theme.PANEL_DARK);
        header.setForeground(Theme.TEXT_WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.CONTENT_AREA_DARK));
        header.setPreferredSize(new Dimension(100, 40));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private final Color evenColor = Theme.PANEL_DARK;
            private final Color oddColor = new Color(0x323842);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? evenColor : oddColor);
                c.setForeground(isSelected ? Color.CYAN : Theme.TEXT_LIGHT);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }
}