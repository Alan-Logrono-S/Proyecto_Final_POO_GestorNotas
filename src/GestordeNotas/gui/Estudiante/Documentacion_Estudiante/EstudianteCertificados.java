package GestordeNotas.gui.Estudiante.Documentacion_Estudiante;

import javax.swing.*;

public class EstudianteCertificados extends JFrame{
    private JTable tablaCertificadosEstudiantes;
    private JButton CARGARCERTIFICACIONESButton;
    private JPanel EstudianteCertificados;

    public EstudianteCertificados() {
        setTitle("Certificaciones de Estudiantes");
        setContentPane(EstudianteCertificados);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
