package GestordeNotas.gui.Coordinador.Academico_Coord;

import javax.swing.*;

public class CoordEstudiantes extends JFrame {
    private JPanel CoordEstudiantes;
    private JTable tablaEstudiantesxAsig;
    private JComboBox comboBox1;
    private JButton verEstudiantesButton;

    public CoordEstudiantes() {
        setTitle("Estudiantes Incritos por Materias");
        setContentPane(CoordEstudiantes);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
