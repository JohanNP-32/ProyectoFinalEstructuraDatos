/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CondominioGUI extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final SistemaController controller = new SistemaController();
    private final JLabel topBarTitle = new JLabel("Dashboard", SwingConstants.LEFT);
    private final Map<String, JButton> navButtons = new LinkedHashMap<>();

    public CondominioGUI() {
        setTitle("VITA S.A. - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setMinimumSize(new Dimension(1024, 600));
        setLocationRelativeTo(null);

        // --- Panel de Navegación Lateral ---
        JPanel navPanel = createNavPanel();

        // --- Panel Superior (Header) ---
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.PANEL_DARK);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        topBarTitle.setFont(Theme.FONT_TITLE.deriveFont(22f));
        topBarTitle.setForeground(Theme.TEXT_WHITE);
        topBar.add(topBarTitle, BorderLayout.WEST);

        // --- Panel Central de Contenido ---
        JPanel mainContentWrapper = new JPanel(new BorderLayout());
        mainContentWrapper.setBackground(Theme.CONTENT_AREA_DARK);
        mainContentWrapper.add(topBar, BorderLayout.NORTH);
        mainContentWrapper.add(contentPanel, BorderLayout.CENTER);

        contentPanel.setOpaque(false);
        contentPanel.add(new InicioPanel(controller), "Dashboard");
        contentPanel.add(new ResidentesPanel(controller), "Residentes");
        contentPanel.add(new TareasPanel(controller), "Tareas");
        contentPanel.add(new ReportesPanel(controller), "Reportes");
        contentPanel.add(new PagosPanel(controller), "Pagos");
        contentPanel.add(new HistorialPanel(controller), "Historial");

        // Ensamblaje Final
        add(navPanel, BorderLayout.WEST);
        add(mainContentWrapper, BorderLayout.CENTER);
        
        setActiveNavButton(navButtons.get("Dashboard"));
        topBarTitle.setText("Dashboard");
    }

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(Theme.PANEL_DARK);
        navPanel.setPreferredSize(new Dimension(250, getHeight()));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel appTitle = new JLabel("VITA S.A.");
        appTitle.setForeground(Theme.TEXT_WHITE);
        appTitle.setFont(Theme.FONT_TITLE);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        navPanel.add(appTitle);
        navPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        navButtons.put("Dashboard", addNavButton("Dashboard", "Dashboard", "Dashboard", navPanel));
        navButtons.put("Residentes", addNavButton("Residentes", "Residentes", "Gestión de Residentes", navPanel));
        navButtons.put("Tareas", addNavButton("Tareas", "Tareas", "Gestión de Tareas", navPanel));
        navButtons.put("Reportes", addNavButton("Reportes", "Reportes", "Gestión de Reportes", navPanel));
        navButtons.put("Pagos", addNavButton("Pagos", "Pagos", "Gestión de Pagos y Cuotas", navPanel));
        navButtons.put("Historial", addNavButton("Historial", "Historial", "Historial de Actividades", navPanel));
        navPanel.add(Box.createVerticalGlue());
        addNavButton("Salir", "Salir", "Salir", navPanel);
        return navPanel;
    }

    private JButton addNavButton(String text, String cardName, String title, JPanel panel) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 55));
        button.setPreferredSize(new Dimension(220, 55));
        button.setMinimumSize(new Dimension(220, 55));
        
        button.setForeground(Theme.TEXT_LIGHT);
        button.setBackground(Theme.PANEL_DARK);
        button.setFont(Theme.FONT_SUBTITLE.deriveFont(Font.BOLD));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        button.addActionListener(e -> handleNavAction(cardName, title, button));
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return button;
    }

    private void handleNavAction(String cardName, String title, JButton clickedButton) {
        if ("Salir".equals(cardName)) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        } else {
            cardLayout.show(contentPanel, cardName);
            topBarTitle.setText(title); // --- CORRECCIÓN AQUÍ ---
            setActiveNavButton(clickedButton);
        }
    }
    
    private void setActiveNavButton(JButton activeButton) {
        for (JButton button : navButtons.values()) {
            button.setBackground(Theme.PANEL_DARK);
        }
        activeButton.setBackground(Theme.CONTENT_AREA_DARK);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        UIManager.put("OptionPane.background", Theme.PANEL_DARK);
        UIManager.put("Panel.background", Theme.PANEL_DARK);
        UIManager.put("OptionPane.messageForeground", Theme.TEXT_WHITE);
        SwingUtilities.invokeLater(() -> new CondominioGUI().setVisible(true));
    }
}