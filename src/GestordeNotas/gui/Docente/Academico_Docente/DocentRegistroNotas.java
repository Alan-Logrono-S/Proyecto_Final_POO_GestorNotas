package GestordeNotas.gui.Docente.Academico_Docente;

import javax.swing.*;

public class DocentRegistroNotas extends JFrame {
    private JTable tablaCalificaciones;
    private JComboBox comboBox1;
    private JButton ingresarCalificacionesButton;
    private JButton modificarCalificacionesButton;
    private JPanel DocentRegistroNotas;

    public DocentRegistroNotas () {
        setTitle("Docente Registro de Notas");
        setContentPane(DocentRegistroNotas );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
