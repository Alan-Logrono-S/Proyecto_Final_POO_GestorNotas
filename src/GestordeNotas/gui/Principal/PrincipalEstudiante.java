package GestordeNotas.gui.Principal;

import GestordeNotas.gui.Estudiante.Documentacion_Estudiante.PrincipalDocumentacion_Estudiante;
import GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante.PrincipalAcademico_Estudiante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalEstudiante extends JFrame {
    private JPanel PrincipalEstudiante; // Asegúrate de que este panel esté inicializado correctamente
    private JTextField textField1;
    private JButton PERIODOACADEMICOButton;
    private JButton DOCUMENTACIONButton;

    public PrincipalEstudiante() {
        // Inicializar el panel si no se hace en otro lugar
        setContentPane(PrincipalEstudiante);
        setTitle("Principal - Estudiante");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Acción para el botón PERIODOACADEMICOButton
        PERIODOACADEMICOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                int idEstudiante = 4; // ejemplo
                new PrincipalAcademico_Estudiante(idEstudiante).setVisible(true);

            }
        });

        // Acción para el botón DOCUMENTACIONButton (añadir funcionalidad o dejar comentario si no se usa)
        DOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                int idEstudiante = 4; // ejemplo
                new PrincipalAcademico_Estudiante(idEstudiante).setVisible(true);
            }
        });
    }
}
