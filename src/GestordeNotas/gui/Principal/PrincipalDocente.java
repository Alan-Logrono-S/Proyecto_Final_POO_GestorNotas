package GestordeNotas.gui.Principal;

import GestordeNotas.gui.Docente.Academico_Docente.PrincipalAcademico_Docente;
import GestordeNotas.gui.Docente.Datos_Docente.InicioDocente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalDocente extends JFrame {
    private JTextField textField1;
    private JButton ACADEMICOButton;
    private JButton DATOSPERSONALESButton;
    private JPanel PrincipalDocente;

    private int idDocente;

    public PrincipalDocente(int idDocente) {
        this.idDocente = idDocente;

        setContentPane(PrincipalDocente);
        setTitle("Principal - Docente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ACADEMICOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalAcademico_Docente(idDocente).setVisible(true);
            }
        });
        DATOSPERSONALESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InicioDocente(idDocente).setVisible(true);
            }
        });
    }

    // Constructor sin parámetros para pruebas, asigna un idDocente por defecto
    public PrincipalDocente() {
        this(3);
    }
}
