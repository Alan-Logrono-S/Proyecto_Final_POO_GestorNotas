package GestordeNotas.gui.Docente.Academico_Docente;

import javax.swing.*;

public class DocentVerEstudiantesXasignatura extends JFrame{
    private JTable tablaVerEstudxAsig;
    private JComboBox comboBox1;
    private JButton button1;
    private JPanel DocentVerEstudiantesXasignatura;

    public DocentVerEstudiantesXasignatura () {
        setTitle("Ver Estudiantes por Asignatura");
        setContentPane(DocentVerEstudiantesXasignatura );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
