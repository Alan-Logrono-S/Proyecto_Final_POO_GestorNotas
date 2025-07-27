package GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante;

import javax.swing.*;

public class EstudianteCalificaciones extends JFrame{
    JTable tablaCalificacionesEstudiante;
    JPanel EstudianteCalificaciones;
    JComboBox comboBox1;
    JButton verCalificacionesButton;

    public EstudianteCalificaciones() {
        setTitle("Calificaciones de Estudiante");
        setContentPane(EstudianteCalificaciones);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
