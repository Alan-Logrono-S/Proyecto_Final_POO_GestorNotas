package GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante;

import javax.swing.*;

public class EstAsignaturasInscritas extends JFrame{
    JTable tablaCalificacionesEstudiante;;
    JPanel EstAsignaturasInscritas;
    JTable tablaAsignaturasEstudiantes;
    JButton cargarAsignaturasButton;

    public EstAsignaturasInscritas() {
        setTitle("Calificaciones de Estudiante");
        setContentPane(EstAsignaturasInscritas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
