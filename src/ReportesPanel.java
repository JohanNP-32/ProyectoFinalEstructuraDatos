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
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

// Panel de la GUI para visualizar y gestionar los reportes financieros.
public class ReportesPanel extends JPanel {
    private final SistemaController controller;
    private final JTable tablaReportes;
    private final DefaultTableModel tableModel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    /**
     * Constructor del panel de reportes.
     * @param controller La instancia del controlador principal de la aplicación.
     */
    public ReportesPanel(SistemaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 20));
        setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
        setOpaque(false);

        // Configuración de la tabla, permitiendo editar solo la columna de "Status".
        String[] cols = {"ID", "Título", "Fecha", "Ingresos", "Egresos", "Balance", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override 
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        tablaReportes = new JTable(tableModel);
        styleTable(tablaReportes);
        
        // Asigna un JComboBox como editor para la columna de estado.
        JComboBox<ReporteStatus> statusComboBox = new JComboBox<>(ReporteStatus.values());
        tablaReportes.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(statusComboBox));
        
        // Listener que detecta cambios en la tabla para actualizar el estado de un reporte.
        tableModel.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && e.getColumn() == 6) {
                int row = e.getFirstRow();
                int id = (int) tableModel.getValueAt(row, 0);
                ReporteStatus nuevoStatus = (ReporteStatus) tableModel.getValueAt(row, 6);
                controller.cambiarEstadoReporte(id, nuevoStatus);
            }
        });
        
        actualizarTabla();

        // Contenedor redondeado para la tabla.
        RoundedPanel tableContainer = new RoundedPanel(15, null);
        tableContainer.setBackground(Theme.PANEL_DARK);
        tableContainer.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);

        // Panel con los botones de acción.
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(createStyledButton("Generar Nuevo Reporte", e -> generarReporte()));
        actionPanel.add(createStyledButton("Eliminar Seleccionado", e -> eliminarReporte()));
        add(actionPanel, BorderLayout.SOUTH);
        
        // Actualiza la tabla cada vez que el panel se hace visible.
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                actualizarTabla();
            }
        });
    }
    
    /**
     * Aplica el estilo visual profesional a una tabla.
     * @param table La JTable a la que se le aplicará el estilo.
     */
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

    /**
     * Método de ayuda para crear un botón con el estilo unificado de la aplicación.
     * @param text El texto que mostrará el botón.
     * @param listener La acción que se ejecutará al hacer clic.
     * @return Un JButton estilizado.
     */
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

    /**
     * Muestra un diálogo para que el usuario ingrese el título y genera un nuevo reporte.
     */
    private void generarReporte() {
        String titulo = JOptionPane.showInputDialog(this, "Título para el nuevo reporte:", "Generar Reporte", JOptionPane.PLAIN_MESSAGE);
        if (titulo != null && !titulo.trim().isEmpty()) {
            controller.generarReporteFinanciero(titulo);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Reporte '" + titulo + "' generado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Elimina el reporte seleccionado en la tabla, previa confirmación.
     */
    private void eliminarReporte() {
        int selectedRow = tablaReportes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un reporte para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String titulo = (String) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar el reporte '" + titulo + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.eliminarReporte(id);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Reporte eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Recarga los datos de la tabla de reportes desde el controlador.
     */
    private void actualizarTabla() {
        // Detiene la edición de celdas si está activa para evitar errores.
        if (tablaReportes.isEditing()) {
            tablaReportes.getCellEditor().stopCellEditing();
        }
        tableModel.setRowCount(0);
        for (Reporte r : controller.getReportes()) {
            Object[] row = { 
                r.id, 
                r.titulo, 
                r.fecha.format(dateFormatter), 
                currencyFormatter.format(r.totalIngresos), 
                currencyFormatter.format(r.totalEgresos), 
                currencyFormatter.format(r.balance),
                r.getStatus() 
            };
            tableModel.addRow(row);
        }
    }
}