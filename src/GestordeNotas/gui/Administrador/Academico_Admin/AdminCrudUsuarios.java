package GestordeNotas.gui.Administrador.Academico_Admin;

import javax.swing.*;

public class AdminCrudUsuarios extends JFrame{
    private JTable tablaAdminGestionUsers;
    private JTextField textField1;
    private JButton AGREGARButton;
    private JTextField textField2;
    private JButton MOSTRARButton;
    private JButton LIMPIARButton;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton ELIMINARButton;
    private JPanel AdminCrudUsuarios;
    private JButton ACTUALIZARButton;

    public AdminCrudUsuarios() {
        setTitle("Gestion de Usuarios");
        setContentPane(AdminCrudUsuarios);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
