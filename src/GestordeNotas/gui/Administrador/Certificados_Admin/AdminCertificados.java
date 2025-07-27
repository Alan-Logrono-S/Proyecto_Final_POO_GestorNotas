package GestordeNotas.gui.Administrador.Certificados_Admin;

import javax.swing.*;

public class AdminCertificados extends JFrame{
    private JTable tablaAdminCertificados;
    private JButton VISUALIZARButton;
    private JButton DESCARGARButton;
    private JPanel AdminCertificados;

    public AdminCertificados() {
        setTitle("Certificados");
        setContentPane(AdminCertificados);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
