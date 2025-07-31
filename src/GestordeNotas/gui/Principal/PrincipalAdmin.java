package GestordeNotas.gui.Principal;

// Importación de las clases correspondientes a cada módulo del administrador
import GestordeNotas.gui.Administrador.Academico_Admin.PrincipalAcademico_Admin;
import GestordeNotas.gui.Administrador.Certificados_Admin.PrincipalCertificados_Admin;
import GestordeNotas.gui.Administrador.Datos_Admin.AdminInicio;
import GestordeNotas.gui.Administrador.Documentacion_Admin.PrincipalDocumentacion_Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase principal que representa la ventana del administrador
public class PrincipalAdmin extends JFrame {
    // Declaración de botones para acceder a diferentes módulos
    private JButton GESTIONACADEMICOButton;
    private JButton GESTIONDOCUMENTACIONButton;
    private JButton CERTIFICACIONESButton;
    private JButton DATOSPERSONALESButton;
    private JPanel PrincipalAdmin;

    // Constructor de la clase PrincipalAdmin
    public PrincipalAdmin() {

        // Configuración del panel principal
        setContentPane(PrincipalAdmin);
        setTitle("Principal - Administrador");
        setSize(600, 400); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana

        // Acción al hacer clic en el botón "GESTION ACADEMICO"
        GESTIONACADEMICOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new PrincipalAcademico_Admin().setVisible(true); // Abre la ventana del módulo Académico
            }
        });

        // Acción al hacer clic en el botón "GESTION DOCUMENTACION"
        GESTIONDOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new PrincipalDocumentacion_Admin().setVisible(true); // Abre la ventana del módulo Documentación
            }
        });

        // Acción al hacer clic en el botón "CERTIFICACIONES"
        CERTIFICACIONESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new PrincipalCertificados_Admin().setVisible(true); // Abre la ventana del módulo Certificados
            }
        });

        // Acción al hacer clic en el botón "DATOS PERSONALES"
        DATOSPERSONALESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new AdminInicio().setVisible(true); // Abre la ventana de Datos Personales
            }
        });
    }

    // Método principal que lanza la aplicación mostrando la ventana del administrador
    public static void main(String[] args) {
        new PrincipalAdmin().setVisible(true); // Crea e inicia la ventana principal del administrador
    }
}
