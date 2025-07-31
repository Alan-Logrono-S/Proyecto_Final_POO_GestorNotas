package GestordeNotas.gui.Principal;

// Importación de las clases correspondientes a cada módulo del coordinador
import GestordeNotas.gui.Coordinador.Academico_Coord.Principal_Academico_Coord;
import GestordeNotas.gui.Coordinador.Datos_Coord.CoordInicio;
import GestordeNotas.gui.Coordinador.Documentos_Coord.CoordReportNotas;
import GestordeNotas.gui.Login.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase principal que representa la ventana del coordinador
public class PrincipalCoordinador extends JFrame {
    // Declaración del panel principal y botones de navegación
    private JPanel PrincipalCoordinador;
    private JButton GESTIONACADEMICAButton;
    private JButton GESTIONDOCUMENTACIONButton;
    private JButton DATOSPERSONALESButton;

    // Constructor de la clase PrincipalCoordinador
    public PrincipalCoordinador() {

        // Configuración de la ventana principal
        setContentPane(PrincipalCoordinador); // Establece el panel principal como contenido de la ventana
        setTitle("Principal - Coordinador"); // Título de la ventana
        setSize(600, 400); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana

        // Acción al hacer clic en el botón "GESTIÓN ACADÉMICA"
        GESTIONACADEMICAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new Principal_Academico_Coord().setVisible(true); // Abre la ventana del módulo académico del coordinador
            }
        });

        // Acción al hacer clic en el botón "GESTIÓN DOCUMENTACIÓN"
        GESTIONDOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new CoordReportNotas().setVisible(true); // Abre la ventana de reportes/documentación del coordinador
            }
        });

        // Acción al hacer clic en el botón "DATOS PERSONALES"
        DATOSPERSONALESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                CoordInicio ventana = new CoordInicio(2);  // Crea la ventana de datos personales con ID de usuario 2
                ventana.setVisible(true); // Muestra la ventana
            }
        });
    }
}
