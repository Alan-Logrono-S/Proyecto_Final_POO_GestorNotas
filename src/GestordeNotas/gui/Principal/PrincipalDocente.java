package GestordeNotas.gui.Principal;

// Importación de los módulos correspondientes al docente
import GestordeNotas.gui.Docente.Academico_Docente.PrincipalAcademico_Docente;
import GestordeNotas.gui.Docente.Datos_Docente.InicioDocente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase principal que representa la ventana del docente
public class PrincipalDocente extends JFrame {
    // Declaración de botones para navegar a módulos académicos y de datos personales
    private JButton ACADEMICOButton;
    private JButton DATOSPERSONALESButton;
    private JPanel PrincipalDocente;

    // Variable para almacenar el ID del docente
    private int idDocente;

    // Constructor principal que recibe el ID del docente como parámetro
    public PrincipalDocente(int idDocente) {
        this.idDocente = idDocente;

        // Configuración de la ventana principal
        setContentPane(PrincipalDocente); // Se establece el panel como contenido principal
        setTitle("Principal - Docente"); // Título de la ventana
        setSize(600, 400); // Dimensiones de la ventana
        setLocationRelativeTo(null); // Centrar en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana

        // Acción al hacer clic en el botón "ACADÉMICO"
        ACADEMICOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra esta ventana
                new PrincipalAcademico_Docente(idDocente).setVisible(true); // Abre la ventana del módulo académico del docente
            }
        });

        // Acción al hacer clic en el botón "DATOS PERSONALES"
        DATOSPERSONALESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra esta ventana
                new InicioDocente(idDocente).setVisible(true); // Abre la ventana de datos personales del docente
            }
        });
    }

    // Constructor sin parámetros, útil para pruebas o cuando no se tiene un ID específico
    // Se asigna un ID de docente por defecto (3 en este caso)
    public PrincipalDocente() {
        this(3);
    }
}
