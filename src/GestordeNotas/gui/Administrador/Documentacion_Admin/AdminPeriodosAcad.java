package GestordeNotas.gui.Administrador.Documentacion_Admin;

import javax.swing.*;

public class AdminPeriodosAcad extends JFrame{
    private JTable tablaAdminPeridoAcad;
    private JButton INGRESARButton;
    private JButton BUSCARButton;
    private JButton ELIMINARButton;
    private JPanel AdminPeriodosAcad;

    public AdminPeriodosAcad() {
        setTitle("Periodo Academico");
        setContentPane(AdminPeriodosAcad);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
