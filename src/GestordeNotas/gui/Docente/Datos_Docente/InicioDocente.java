package GestordeNotas.gui.Docente.Datos_Docente;

import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalDocente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class InicioDocente extends JFrame {
    // Tabla para mostrar los datos personales del docente
    private JTable tablaDatosPerDocente;
    // Botón para cargar los datos personales del docente
    private JButton CARGARDATOSPERSONALESButton;
    // Panel principal del JFrame
    private JPanel InicioDocente;
    // Botón para regresar al menú principal del docente
    private JButton regresarButton;
    // Botón para salir de la aplicación o ventana
    private JButton salirButton;

    private int idDocente;  // ID del docente para identificar qué datos cargar

    // Constructor que recibe el ID del docente y configura la ventana
    public InicioDocente(int idDocente) {
        this.idDocente = idDocente;

        setTitle("Datos Personales Docente");  // Título de la ventana
        setContentPane(InicioDocente);         // Establece el panel principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);                     // Tamaño de la ventana
        setLocationRelativeTo(null);           // Centra la ventana en pantalla

        // Evento para cargar los datos personales al hacer clic en el botón
        CARGARDATOSPERSONALESButton.addActionListener(e -> cargarDatosPersonales());

        // Evento para regresar al menú principal del docente
        regresarButton.addActionListener(e -> {
            dispose();  // Cierra esta ventana
            new PrincipalDocente(idDocente).setVisible(true); // Abre la ventana principal docente
        });

        // Evento para salir de la ventana
        salirButton.addActionListener(e -> dispose());
    }

    // Metodo para cargar los datos personales del docente desde la base de datos
    private void cargarDatosPersonales() {
        try (Connection con = CleverDB.getConexion()) {
            // Consulta SQL para obtener los datos del docente por su ID
            String query = "SELECT nombre, correo, telefono, direccion FROM usuarios WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idDocente);  // Establece el parámetro del ID

            ResultSet rs = stmt.executeQuery();  // Ejecuta la consulta

            // Modelo para la tabla, con dos columnas: Campo y Valor
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Campo");
            model.addColumn("Valor");

            if (rs.next()) {
                // Agrega fila por cada campo con su respectivo valor
                model.addRow(new Object[]{"Nombre", rs.getString("nombre")});
                model.addRow(new Object[]{"Correo", rs.getString("correo")});
                model.addRow(new Object[]{"Teléfono", rs.getString("telefono")});
                model.addRow(new Object[]{"Dirección", rs.getString("direccion")});
            } else {
                // Si no encuentra datos, muestra un mensaje informativo
                JOptionPane.showMessageDialog(this, "No se encontraron datos para el docente con ID: " + idDocente);
            }

            tablaDatosPerDocente.setModel(model);  // Asigna el modelo a la tabla para mostrar datos

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            // En caso de error, muestra un mensaje con la descripción del problema
            JOptionPane.showMessageDialog(this, "Error al cargar datos personales: " + ex.getMessage());
        }
    }
}
