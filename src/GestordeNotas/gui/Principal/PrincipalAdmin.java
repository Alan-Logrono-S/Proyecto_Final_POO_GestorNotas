package GestordeNotas.gui.Principal;

import GestordeNotas.gui.Administrador.Academico_Admin.PrincipalAcademico_Admin;
import GestordeNotas.gui.Administrador.Certificados_Admin.PrincipalCertificados_Admin;
import GestordeNotas.gui.Administrador.Datos_Admin.AdminInicio;
import GestordeNotas.gui.Administrador.Documentacion_Admin.PrincipalDocumentacion_Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalAdmin extends JFrame {
    private JButton GESTIONACADEMICOButton;
    private JButton GESTIONDOCUMENTACIONButton;
    private JButton CERTIFICACIONESButton;
    private JButton DATOSPERSONALESButton;
    private JPanel PrincipalAdmin;

    public PrincipalAdmin() {

        setContentPane(PrincipalAdmin);
        setTitle("Principal - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GESTIONACADEMICOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalAcademico_Admin().setVisible(true);
            }
        });
        GESTIONDOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalDocumentacion_Admin().setVisible(true);
            }
        });
        CERTIFICACIONESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalCertificados_Admin().setVisible(true);
            }
        });
        DATOSPERSONALESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminInicio().setVisible(true);
            }
        });
    }
}