package GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private JTabbedPane tabbedPane;

    public VentanaPrincipal() {
        setTitle("Ventana Principal");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el JTabbedPane
        tabbedPane = new JTabbedPane();

        // Crear los formularios y agregarlos como pestañas
        EstAsignaturasInscritas asignaturasForm = new EstAsignaturasInscritas();
        EstudianteCalificaciones calificacionesForm = new EstudianteCalificaciones();
        InicioEstudiante inicioEstudianteForm = new InicioEstudiante();

        // Agregar las pestañas al JTabbedPane
        tabbedPane.addTab("Asignaturas Inscritas", asignaturasForm);
        tabbedPane.addTab("Calificaciones", calificacionesForm);
        tabbedPane.addTab("Inicio Estudiante", inicioEstudianteForm);

        // Establecer el JTabbedPane como el contenido de la ventana
        setContentPane(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
