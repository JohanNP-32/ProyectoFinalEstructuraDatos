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
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

// Panel de la GUI para la gestión completa de los residentes.
public class ResidentesPanel extends JPanel {
    private final SistemaController controller;
    private final JTable tablaResidentes;
    private final DefaultTableModel tableModel;

    /**
     * Constructor del panel de gestión de residentes.
     * @param controller La instancia del controlador principal de la aplicación.
     */
    public ResidentesPanel(SistemaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 20));
        setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
        setOpaque(false);

        String[] columnNames = {"ID", "Nombre", "Departamento", "Teléfono", "Deuda"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaResidentes = new JTable(tableModel);
        styleTable(tablaResidentes);
        
        // --- Renderizador específico para la columna "Deuda" ---
        // Se obtiene primero el renderizador base para heredar sus propiedades (fondo, etc.)
        TableCellRenderer baseRenderer = tablaResidentes.getDefaultRenderer(Object.class);
        tablaResidentes.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Se obtiene el componente con el estilo base (colores de fila, etc.)
                Component c = baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Se aplica el cambio de color de texto específico para esta columna
                String saldoStr = value.toString();
                if (saldoStr.contains("$")) {
                    c.setForeground(Theme.ACCENT_ORANGE);
                } else {
                    c.setForeground(Theme.ACCENT_GREEN);
                }
                // Se aplica la alineación y se devuelve el componente final
                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.RIGHT);
                }
                return c;
            }
        });

        actualizarTabla();
        
        RoundedPanel tableContainer = new RoundedPanel(15, null);
        tableContainer.setBackground(Theme.PANEL_DARK);
        tableContainer.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tablaResidentes);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(createStyledButton("Agregar", e -> agregarResidente()));
        actionPanel.add(createStyledButton("Eliminar", e -> eliminarResidente()));
        actionPanel.add(createStyledButton("Buscar por ID", e -> buscarResidentePorId()));
        actionPanel.add(createStyledButton("Buscar por Nombre", e -> buscarResidentePorNombre()));
        actionPanel.add(createStyledButton("Enviar Avisos", e -> enviarAvisos()));
        add(actionPanel, BorderLayout.SOUTH);
    }

    /**
     * Aplica el estilo visual profesional y unificado a la tabla.
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
     * @param text El texto del botón.
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
     * Recarga los datos de la tabla de residentes desde el controlador.
     */
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (Residente r : controller.getResidentes()) {
            String deudaFmt = "Al corriente";
            if (r.tieneDeuda()) {
                deudaFmt = String.format("$%,.2f", Math.abs(r.getSaldo()));
            }
            tableModel.addRow(new Object[]{r.getId(), r.getNombre(), r.getDepartamento(), r.getTelefono(), deudaFmt});
        }
    }

    /**
     * Muestra una ventana con los avisos de pago para todos los deudores.
     */
    private void enviarAvisos() {
        String avisosHtml = controller.generarAvisosDePagoFormateados();
        JEditorPane editorPane = new JEditorPane("text/html", avisosHtml);
        editorPane.setEditable(false);
        editorPane.setBackground(Theme.PANEL_DARK);
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setPreferredSize(new Dimension(400, 500));
        JOptionPane.showMessageDialog(this, scrollPane, "Avisos de Pago", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Muestra un diálogo para agregar un nuevo residente al sistema.
     */
    private void agregarResidente() {
        JTextField nF = new JTextField(), dF = new JTextField(), tF = new JTextField(), sF = new JTextField("0.0");
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre Completo:")); panel.add(nF);
        panel.add(new JLabel("Departamento (ej. A-101):")); panel.add(dF);
        panel.add(new JLabel("Teléfono:")); panel.add(tF);
        panel.add(new JLabel("Deuda Inicial (ej. 800.0):")); panel.add(sF);
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Nuevo Residente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double deuda = Double.parseDouble(sF.getText());
                controller.agregarResidente(nF.getText(), dF.getText(), tF.getText(), -deuda);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Residente agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Elimina el residente seleccionado en la tabla, previa confirmación.
     */
    private void eliminarResidente() {
        int selectedRow = tablaResidentes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un residente para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar a " + nombre + " (ID: " + id + ")?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.eliminarResidente(id);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Residente eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Muestra un diálogo para buscar un residente por su ID.
     */
    private void buscarResidentePorId() {
        String idStr = JOptionPane.showInputDialog(this, "Ingrese ID del residente:");
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            Residente r = controller.buscarResidentePorId(id);
            if (r != null) JOptionPane.showMessageDialog(this, r.toString(), "Residente Encontrado", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(this, "No se encontró residente con ese ID.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID numérico válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Muestra un diálogo para buscar un residente por su nombre completo.
     */
    private void buscarResidentePorNombre() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese Nombre del residente:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        Residente r = controller.buscarResidentePorNombre(nombre);
        if (r != null) JOptionPane.showMessageDialog(this, r.toString(), "Residente Encontrado", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(this, "No se encontró residente con ese nombre.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
    }
}