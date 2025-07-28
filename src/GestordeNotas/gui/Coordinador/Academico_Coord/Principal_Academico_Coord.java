package GestordeNotas.gui.Coordinador.Academico_Coord;

import GestordeNotas.gui.Principal.PrincipalCoordinador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal_Academico_Coord extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel CoordEditarNotas;
    private JTable tablaCoordCalificaciones;
    private JButton ingresarCalificacionesButton;
    private JButton modificarCalificacionesButton;
    private JComboBox comboBox1;
    private JPanel CoordEstudiantes;
    private JTable tablaEstudiantesxAsig;
    private JButton verEstudiantesButton;


    public Principal_Academico_Coord() {

        setContentPane(panel1);
        setTitle("Academico - Coordinador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // CALIFICACIONES

        ingresarCalificacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        modificarCalificacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // INSCRIPCIONES

        verEstudiantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // EXIT


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
