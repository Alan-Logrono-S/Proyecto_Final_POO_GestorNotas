package GestordeNotas.gui.Administrador.Certificados_Admin;

import javax.swing.*;

public class AdminReportesSistem extends JFrame{
    private JTable tablaReportesAdmin;
    private JButton reportePorMateriaButton;
    private JButton reportePorEstudianteButton;
    private JButton reportePorPeriodoButton;
    private JPanel AdminReportesSistem;

    public AdminReportesSistem() {
        setTitle("Reportes");
        setContentPane(AdminReportesSistem);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
