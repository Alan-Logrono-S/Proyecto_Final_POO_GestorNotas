package GestordeNotas.gui.Administrador.Documentacion_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalDocumentacion_Admin extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel AdminPeriodosAcad;
    private JTable tablaAdminPeridoAcad;
    private JButton INGRESARButton;
    private JButton BUSCARButton;
    private JButton ELIMINARButton;
    private JPanel AdminMatricula;
    private JTable tablaAdminMatriculas;
    private JComboBox comboBox1;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;

    public PrincipalDocumentacion_Admin() {
        setContentPane(panel1);
        setTitle("Documentacion - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // PERIODO ACADEMICO

        BUSCARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //MATRICULA


        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // EXIT

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
