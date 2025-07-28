package GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante;

import GestordeNotas.gui.Principal.PrincipalEstudiante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalAcademico_Estudiante extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel InicioEstudiante;
    private JTable tablaDatosEstudiante;
    private JButton CARGARButton;
    private JPanel EstudianteCalificaciones;
    private JTable tablaCalificacionesEstudiante;
    private JComboBox comboBox1;
    private JButton verCalificacionesButton;
    private JPanel EstAsignaturasInscritas;
    private JTable tablaAsignaturasEstudiantes;
    private JButton cargarAsignaturasButton;


    public PrincipalAcademico_Estudiante() {

        setContentPane(panel1);
        setTitle("Academico- Estudiante");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // DATOS PERSONALES

        CARGARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // ASIGNATURAS

        cargarAsignaturasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // CALIFICAIONES

        verCalificacionesButton.addActionListener(new ActionListener() {
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
