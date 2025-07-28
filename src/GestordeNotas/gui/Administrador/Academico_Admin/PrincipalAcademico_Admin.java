package GestordeNotas.gui.Administrador.Academico_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalAcademico_Admin extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel AdminCrudAsig;
    private JTable tablaAdminGestiondeAsig;
    private JButton INGRESARButton;
    private JTextField textField1;
    private JButton MOSTRARButton;
    private JButton ELIMINARButton;
    private JButton LIMPIARButton;
    private JTextField textField2;
    private JButton ACTUALIZARButton1;
    private JPanel AdminCrudUsuarios;
    private JTable tablaAdminGestionUsers;
    private JButton AGREGARButton;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton ACTUALIZARButton;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;
    private JButton MOSTRARButton1;
    private JButton ELIMINARButton1;
    private JButton SALIRButton;

    public PrincipalAcademico_Admin() {

        setContentPane(panel1);
        setTitle("Academico - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // USUARIOS

        AGREGARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        MOSTRARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ACTUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ELIMINARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        // ASIGNATURAS


        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        MOSTRARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ACTUALIZARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        LIMPIARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //EXIT


        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalAdmin().setVisible(true);
            }
        });
        salirDelSistemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
