package GestordeNotas.gui.Coordinador.Datos_Coord;

import GestordeNotas.gui.Principal.PrincipalCoordinador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoordInicio extends JFrame{
    private JTable tablaDatosCoord;
    private JButton cargarDatosButton;
    private JPanel CoordInicio;
    private JButton regresarButton;
    private JButton salirButton;

    public CoordInicio() {
        setTitle("Datos Personales Coordinador");
        setContentPane(CoordInicio);
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
