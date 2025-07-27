package GestordeNotas.gui.Coordinador.Academico_Coord;

import javax.swing.*;

public class CoordEditarNotas extends JFrame{
    private JTable tablaCoordCalificaciones;
    private JButton ingresarCalificacionesButton;
    private JButton modificarCalificacionesButton;
    private JComboBox comboBox1;
    private JPanel CoordEditarNotas;

    public CoordEditarNotas() {
        setTitle("Calificaciones Registradas");
        setContentPane(CoordEditarNotas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
