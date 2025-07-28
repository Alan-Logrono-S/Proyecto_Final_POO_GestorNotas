package GestordeNotas.gui.Administrador.Datos_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminInicio extends JFrame{
    private JTable tablaAdminDatosPer;
    private JButton cargarDatosPersonalesButton;
    private JPanel AdminInicio;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;

    public AdminInicio() {
        setTitle("Datos Personales Administrador");
        setContentPane(AdminInicio);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
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
