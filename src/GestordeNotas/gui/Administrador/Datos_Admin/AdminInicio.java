package GestordeNotas.gui.Administrador.Datos_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.database.CleverDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminInicio extends JFrame {
    private JTable tablaAdminDatosPer;
    private JButton cargarDatosPersonalesButton;
    private JPanel AdminInicio;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;

    public AdminInicio() {
        setTitle("Datos Personales Administrador");
        setContentPane(AdminInicio);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Acción para regresar al panel principal
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana actual
                new PrincipalAdmin().setVisible(true); // Abrir el panel principal
            }
        });

        // Acción para salir del sistema
        salirDelSistemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Cerrar la aplicación
                }
            }
        });

        // Acción para cargar los datos personales en la tabla
        cargarDatosPersonalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosPersonales(); // Llamar al método para cargar los datos
            }
        });
    }
    private void cargarDatosPersonales() {
        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Usuario");
        model.addColumn("Nombre");
        model.addColumn("Correo");
        model.addColumn("Telefono");
        model.addColumn("Direccion");

        try {
            // Establecer conexión a la base de datos
            Connection conexion = CleverDB.getConexion();
            Statement stmt = conexion.createStatement();

            // Consulta SQL para obtener solo los datos del administrador
            String query = "SELECT id_usuario, nombre, correo, telefono, direccion FROM usuarios WHERE rol = 'admin'";

            ResultSet rs = stmt.executeQuery(query);

            // Cargar los datos desde el resultado de la consulta SQL en el modelo de la tabla
            if (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                });
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron datos del administrador.");
            }

            // Establecer el modelo a la tabla para mostrar los datos
            tablaAdminDatosPer.setModel(model);

            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos personales: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
