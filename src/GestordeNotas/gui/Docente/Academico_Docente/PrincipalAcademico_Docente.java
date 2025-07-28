package GestordeNotas.gui.Docente.Academico_Docente;

import GestordeNotas.gui.Principal.PrincipalDocente;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalAcademico_Docente extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel DocentRegistroNotas;
    private JTable tablaCalificaciones;
    private JComboBox comboBox1;
    private JButton ingresarCalificacionesButton;
    private JButton modificarCalificacionesButton;
    private JPanel DocentVerEstudiantesXasignatura;
    private JTable tablaVerEstudxAsig;
    private JButton button1;

    // Estudiantes por Asignatura

    public PrincipalAcademico_Docente() {

        setContentPane(panel1);
        setTitle("Academico - Docente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Registro Calificaciones

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


        // Exit


        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalDocente().setVisible(true);
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
