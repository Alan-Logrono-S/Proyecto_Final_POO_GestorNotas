package GestordeNotas.gui.Administrador.Datos_Admin;

import javax.swing.*;

public class AdminInicio extends JFrame{
    private JTable tablaAdminDatosPer;
    private JButton cargarDatosPersonalesButton;
    private JPanel AdminInicio;

    public AdminInicio() {
        setTitle("Datos Personales Administrador");
        setContentPane(AdminInicio);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
