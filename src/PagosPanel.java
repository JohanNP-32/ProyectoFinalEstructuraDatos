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
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PagosPanel extends JPanel {
    private final SistemaController controller;
    private final JTable tablaPagos;
    private final DefaultTableModel tableModel;

    public PagosPanel(SistemaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 20));
        setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
        setOpaque(false);

        String[] cols = {"ID", "Nombre", "Departamento", "Deuda"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPagos = new JTable(tableModel);
        styleTable(tablaPagos);
        
        tablaPagos.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String saldoStr = value.toString();
                if (saldoStr.contains("(")) {
                    c.setForeground(Theme.ACCENT_ORANGE);
                } else {
                    c.setForeground(Theme.ACCENT_GREEN);
                }
                setHorizontalAlignment(JLabel.RIGHT);
                return c;
            }
        });
        
        actualizarTabla();

        RoundedPanel tableContainer = new RoundedPanel(15, null);
        tableContainer.setBackground(Theme.PANEL_DARK);
        tableContainer.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tablaPagos);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(createStyledButton("Registrar Pago", e -> registrarPago()));
        actionPanel.add(createStyledButton("Aplicar Cuota General", e -> aplicarCuota()));
        actionPanel.add(createStyledButton("Ver Historial de Pagos", e -> verHistorialPagos()));
        add(actionPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                actualizarTabla();
            }
        });
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

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(Theme.FONT_BOLD);
        button.setBackground(Theme.ACCENT_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.addActionListener(listener);
        return button;
    }
    
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (Residente r : controller.getResidentes()) {
            String deudaFmt;
            if (r.tieneDeuda()) {
                deudaFmt = String.format("($%,.2f)", Math.abs(r.getSaldo()));
            } else {
                deudaFmt = "Al corriente";
            }
            tableModel.addRow(new Object[]{ r.getId(), r.getNombre(), r.getDepartamento(), deudaFmt });
        }
    }

    private void registrarPago() {
        String idStr = JOptionPane.showInputDialog(this, "ID del Residente:");
        if (idStr == null || idStr.trim().isEmpty()) return;
        String montoStr = JOptionPane.showInputDialog(this, "Monto a pagar:");
        if (montoStr == null || montoStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr);
            double monto = Double.parseDouble(montoStr);
            String resultado = controller.registrarPago(id, monto);
            JOptionPane.showMessageDialog(this, resultado, "Registro de Pago", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aplicarCuota() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Aplicar cuota de $800.00 a todos los residentes?", "Confirmar Cuota General", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String resultado = controller.aplicarCuotaGeneral(800.0);
            JOptionPane.showMessageDialog(this, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        }
    }

    private void verHistorialPagos() {
        JDialog historialDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Historial de Pagos", true);
        historialDialog.setSize(700, 500);
        historialDialog.setLocationRelativeTo(this);
        String[] cols = {"Fecha y Hora", "ID Residente", "Nombre", "Monto"};
        DefaultTableModel historialModel = new DefaultTableModel(cols, 0);
        JTable historialTable = new JTable(historialModel);
        styleTable(historialTable); 
        for (Pago p : controller.getHistorialPagos()) {
            historialModel.addRow(new Object[]{ p.getTimestampFormatted(), p.getResidente().getId(), p.getResidente().getNombre(), NumberFormat.getCurrencyInstance(Locale.US).format(p.getMonto()) });
        }
        historialDialog.add(new JScrollPane(historialTable));
        historialDialog.setVisible(true);
    }
}