package GestordeNotas.gui.Coordinador.Datos_Coord;

import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalCoordinador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CoordInicio extends JFrame {
    private JTable tablaDatosCoord;
    private JButton cargarDatosButton;
    private JPanel CoordInicio;
    private JButton regresarButton;
    private JButton salirButton;

    private int idUsuario; // ID del coordinador logueado

    // Constructor con el ID del coordinador
    public CoordInicio(int idUsuario) {
        this.idUsuario = idUsuario;

        setTitle("Datos Personales Coordinador");
        setContentPane(CoordInicio);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Acción para cargar datos personales
        cargarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosCoordinador();
            }
        });

        // Acción para regresar
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalCoordinador().setVisible(true);
            }
        });

        // Acción para salir
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Método para cargar los datos del coordinador
    private void cargarDatosCoordinador() {
        try {
            Connection conexion = CleverDB.getConexion();
            String query = "SELECT id, nombre, correo, telefono, direccion FROM usuarios " +
                    "WHERE id = ? AND rol = 'coordinador'";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            // Crear modelo de tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Correo");
            model.addColumn("Teléfono");
            model.addColumn("Dirección");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                });
            }

            tablaDatosCoord.setModel(model);

            rs.close();
            stmt.close();
            conexion.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del coordinador: " + ex.getMessage());
        }
    }
}
