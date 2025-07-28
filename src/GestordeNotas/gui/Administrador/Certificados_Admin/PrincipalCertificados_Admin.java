package GestordeNotas.gui.Administrador.Certificados_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalCertificados_Admin extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel AdminCertificados;
    private JTable tablaAdminCertificados;
    private JButton VISUALIZARButton;
    private JButton DESCARGARButton;
    private JPanel AdminReportesSistem;
    private JTable tablaReportesAdmin;
    private JButton reportePorMateriaButton;
    private JButton reportePorEstudianteButton;
    private JButton reportePorPeriodoButton;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;

    // CERTIFICADOS

    public PrincipalCertificados_Admin() {

        setContentPane(panel1);
        setTitle("Certificados - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        VISUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        DESCARGARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        //REPORTES
        reportePorMateriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        reportePorPeriodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        reportePorEstudianteButton.addActionListener(new ActionListener() {
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
