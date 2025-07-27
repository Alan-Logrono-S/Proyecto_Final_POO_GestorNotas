package GestordeNotas.gui.Administrador.Academico_Admin;

import javax.swing.*;

public class AdminCrudAsig extends JFrame{
    private JTable tablaAdminGestiondeAsig;
    private JTextField textField1;
    private JButton INGRESARButton;
    private JButton MOSTRARButton;
    private JButton ELIMINARButton;
    private JButton LIMPIARButton;
    private JTextField textField2;
    private JPanel AdminCrudAsig;
    private JButton actualizarButton;

    public AdminCrudAsig() {
        setTitle("Gestion de Asignaturas");
        setContentPane(AdminCrudAsig);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
