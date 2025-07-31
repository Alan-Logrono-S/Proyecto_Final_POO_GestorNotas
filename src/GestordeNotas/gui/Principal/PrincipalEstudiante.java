package GestordeNotas.gui.Principal;

// Importación de las interfaces relacionadas con el estudiante
import GestordeNotas.gui.Estudiante.Documentacion_Estudiante.PrincipalDocumentacion_Estudiante;
import GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante.PrincipalAcademico_Estudiante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase principal que representa la interfaz gráfica del estudiante
public class PrincipalEstudiante extends JFrame {
    // Panel principal de la interfaz del estudiante
    private JPanel PrincipalEstudiante;

    // Botones de navegación para los módulos de periodo académico y documentación
    private JButton PERIODOACADEMICOButton;
    private JButton DOCUMENTACIONButton;

    // Variable para almacenar el ID del estudiante, que debería ser recibido desde el módulo de login
    private int idEstudiante;

    // Constructor que recibe el ID del estudiante
    public PrincipalEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;

        // Configuración básica de la ventana
        setContentPane(PrincipalEstudiante); // Establece el panel principal como contenido de la ventana
        setTitle("Principal - Estudiante"); // Título de la ventana
        setSize(600, 400); // Dimensiones de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana

        // Acción al hacer clic en el botón "PERIODO ACADÉMICO"
        PERIODOACADEMICOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                // Abre la ventana del módulo académico, pasando el ID del estudiante
                new PrincipalAcademico_Estudiante(idEstudiante).setVisible(true);
            }
        });

        // Acción al hacer clic en el botón "DOCUMENTACIÓN"
        DOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                // Abre la ventana del módulo de documentación, pasando el ID del estudiante
                new PrincipalDocumentacion_Estudiante(idEstudiante).setVisible(true);
            }
        });
    }
}
