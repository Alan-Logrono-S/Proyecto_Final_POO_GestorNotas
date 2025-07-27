package GestordeNotas.gui.Coordinador.Documentos_Coord;

import javax.swing.*;

public class CoordReportNotas extends JFrame {
    private JPanel CoordReportNotas;
    private JTable tablaCoordRepotes;
    private JButton reportePorEstudianteButton;
    private JButton reportePorMateriaButton;
    private JButton reportePorPeriodoButton;

    public CoordReportNotas() {
        setTitle("Coordinador Reportes");
        setContentPane(CoordReportNotas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
