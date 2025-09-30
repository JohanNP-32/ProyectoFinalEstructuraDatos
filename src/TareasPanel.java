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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TareasPanel extends JPanel {
    private final SistemaController controller;
    private final JTable tablaTareas;
    private final DefaultTableModel tableModel;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy", new Locale("es", "ES"));

    public TareasPanel(SistemaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 20));
        setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
        setOpaque(false);

        String[] col = {"ID", "Descripción", "Depto.", "Urgencia", "Fecha", "Costo", "Estado", "Prerreq."};
        tableModel = new DefaultTableModel(col, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaTareas = new JTable(tableModel);
        styleTable(tablaTareas); 

       
        TableCellRenderer baseRenderer = tablaTareas.getDefaultRenderer(Object.class);

       
        tablaTareas.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
              
                Component c = baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (value.toString().equals("Completada")) {
                    c.setForeground(Theme.ACCENT_GREEN);
                } else {
                    c.setForeground(Theme.ACCENT_YELLOW);
                }
                return c;
            }
        });
        
      
        tablaTareas.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
              
                Component c = baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
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
        JScrollPane scrollPane = new JScrollPane(tablaTareas);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(createStyledButton("Agregar Tarea", e -> agregarTarea()));
        actionPanel.add(createStyledButton("Marcar Completada", e -> marcarTareaCompletada()));
        actionPanel.add(createStyledButton("Buscar por Costo", e -> buscarPorCosto()));
        add(actionPanel, BorderLayout.SOUTH);
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
        int selectedRow = tablaTareas.getSelectedRow();
        tableModel.setRowCount(0);
        for (Tarea t : controller.getTareasOrdenadas()) {
            Object[] row = { t.getId(), t.getDescripcion(), t.getDepartamento(), t.getUrgencia(), t.getFecha().format(formatter), String.format("$%,.2f", t.getCosto()), t.estaCompletada() ? "Completada" : "Pendiente", t.getPrerrequisitos().toString() };
            tableModel.addRow(row);
        }
        if (selectedRow != -1 && selectedRow < tablaTareas.getRowCount()) {
            tablaTareas.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }

    private void agregarTarea() {
        JTextField descField = new JTextField();
        JComboBox<String> deptoCombo = new JComboBox<>(new String[]{"Mantenimiento", "Seguridad", "Administracion"});
        JComboBox<Urgencia> urgenciaCombo = new JComboBox<>(Urgencia.values());
        JTextField costoField = new JTextField("0.0");
        JTextField fechaField = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        JTextField prereqField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Descripción:")); panel.add(descField);
        panel.add(new JLabel("Departamento:")); panel.add(deptoCombo);
        panel.add(new JLabel("Urgencia:")); panel.add(urgenciaCombo);
        panel.add(new JLabel("Costo $:")); panel.add(costoField);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):")); panel.add(fechaField);
        panel.add(new JLabel("Prerrequisitos (IDs separados por coma):")); panel.add(prereqField);
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Nueva Tarea", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                List<Integer> prerrequisitos = new ArrayList<>();
                String prereqInput = prereqField.getText().trim();
                if (!prereqInput.isEmpty()) {
                    String[] ids = prereqInput.split(",");
                    for (String idStr : ids) prerrequisitos.add(Integer.parseInt(idStr.trim()));
                }
                controller.agregarTarea(descField.getText(), (String)deptoCombo.getSelectedItem(), (Urgencia)urgenciaCombo.getSelectedItem(), Double.parseDouble(costoField.getText()), LocalDate.parse(fechaField.getText()), prerrequisitos);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Tarea agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + ex.getMessage(), "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void marcarTareaCompletada() {
        int selectedRow = tablaTareas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una tarea para marcarla como completada.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String resultado = controller.marcarTareaComoCompletada(id);
        JOptionPane.showMessageDialog(this, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        actualizarTabla();
    }
    
    private void buscarPorCosto() {
        String costoStr = JOptionPane.showInputDialog(this, "Ingrese el costo exacto a buscar:", "Búsqueda por Costo", JOptionPane.PLAIN_MESSAGE);
        if (costoStr == null || costoStr.trim().isEmpty()) return;
        try {
            double costo = Double.parseDouble(costoStr);
            Tarea t = controller.busquedaBinariaTareas(controller.getTareasOrdenadas(), costo);
            if (t != null) JOptionPane.showMessageDialog(this, "Tarea encontrada:\n" + t.getDescripcion(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(this, "No se encontró tarea con ese costo exacto.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}