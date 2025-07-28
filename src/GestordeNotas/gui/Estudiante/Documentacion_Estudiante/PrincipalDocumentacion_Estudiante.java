package GestordeNotas.gui.Estudiante.Documentacion_Estudiante;

import GestordeNotas.gui.Principal.PrincipalEstudiante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalDocumentacion_Estudiante extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel EstudianteCertificados;
    private JTable tablaCertificadosEstudiantes;
    private JButton CARGARCERTIFICACIONESButton;
    private JPanel EstudiantesNotificaciones;
    private JTable tablaCorreoEstudiantes;
    private JButton CARGARCORREOButton;

    public PrincipalDocumentacion_Estudiante() {

        setContentPane(panel1);
        setTitle("Documentacion - Estudiante");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // CERTIFICACIONES
        CARGARCERTIFICACIONESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // CORREO


        CARGARCORREOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // EXIT


        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalEstudiante().setVisible(true);
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
