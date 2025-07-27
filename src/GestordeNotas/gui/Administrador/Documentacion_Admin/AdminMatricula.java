package GestordeNotas.gui.Administrador.Documentacion_Admin;

import javax.swing.*;

public class AdminMatricula extends JFrame{
    private JTable tablaAdminMatriculas;
    private JComboBox comboBox1;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JPanel AdminMatricula;

    public AdminMatricula() {
        setTitle("Matriculas");
        setContentPane(AdminMatricula);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
