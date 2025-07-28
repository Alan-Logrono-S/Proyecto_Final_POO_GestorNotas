package GestordeNotas.gui.Coordinador.Documentos_Coord;

import GestordeNotas.gui.Principal.PrincipalCoordinador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoordReportNotas extends JFrame {
    private JPanel CoordReportNotas;
    private JTable tablaCoordRepotes;
    private JButton reportePorEstudianteButton;
    private JButton reportePorMateriaButton;
    private JButton reportePorPeriodoButton;
    private JButton regresarButton;
    private JButton salirButton;

    public CoordReportNotas() {
        setTitle("Coordinador Reportes");
        setContentPane(CoordReportNotas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalCoordinador().setVisible(true);
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
