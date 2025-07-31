package GestordeNotas.gui.Principal;

import GestordeNotas.gui.Estudiante.Documentacion_Estudiante.PrincipalDocumentacion_Estudiante;
import GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante.PrincipalAcademico_Estudiante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalEstudiante extends JFrame {
    private JPanel PrincipalEstudiante;
    private JButton PERIODOACADEMICOButton;
    private JButton DOCUMENTACIONButton;
    private int idEstudiante;  // 👈 Este debe venir desde el login

    public PrincipalEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;

        setContentPane(PrincipalEstudiante);
        setTitle("Principal - Estudiante");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PERIODOACADEMICOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalAcademico_Estudiante(idEstudiante).setVisible(true);
            }
        });

        DOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalDocumentacion_Estudiante(idEstudiante).setVisible(true);
            }
        });
    }
}

