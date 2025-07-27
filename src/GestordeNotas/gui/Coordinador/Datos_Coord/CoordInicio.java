package GestordeNotas.gui.Coordinador.Datos_Coord;

import javax.swing.*;

public class CoordInicio extends JFrame{
    private JTable tablaDatosCoord;
    private JButton cargarDatosButton;
    private JPanel CoordInicio;

    public CoordInicio() {
        setTitle("Datos Personales Coordinador");
        setContentPane(CoordInicio);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
