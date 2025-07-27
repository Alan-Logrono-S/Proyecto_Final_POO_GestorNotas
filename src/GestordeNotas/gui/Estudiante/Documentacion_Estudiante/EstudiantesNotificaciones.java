package GestordeNotas.gui.Estudiante.Documentacion_Estudiante;

import javax.swing.*;

public class EstudiantesNotificaciones extends JFrame {
    private JTable tablaCorreoEstudiantes;
    private JButton CARGARCORREOButton;
    private JPanel EstudiantesNotificaciones;

    public EstudiantesNotificaciones() {
        setTitle("Correo de Estudiantes");
        setContentPane(EstudiantesNotificaciones);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
