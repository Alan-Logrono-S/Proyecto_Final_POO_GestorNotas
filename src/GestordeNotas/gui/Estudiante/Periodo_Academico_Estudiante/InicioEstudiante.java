package GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante;

import javax.swing.*;

public class InicioEstudiante extends JFrame{
    JTable tablaDatosEstudiante;
    JButton CARGARButton;
    JPanel InicioEstudiante;

    public InicioEstudiante() {
        setTitle("Datos Personales Estudiante");
        setContentPane(InicioEstudiante);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
