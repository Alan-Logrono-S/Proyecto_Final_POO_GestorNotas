package GestordeNotas.gui.Docente.Datos_Docente;

import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalDocente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class InicioDocente extends JFrame {
    private JTable tablaDatosPerDocente;
    private JButton CARGARDATOSPERSONALESButton;
    private JPanel InicioDocente;
    private JButton regresarButton;
    private JButton salirButton;

    private int idDocente;  // ID del docente

    public InicioDocente(int idDocente) {
        this.idDocente = idDocente;

        setTitle("Datos Personales Docente");
        setContentPane(InicioDocente);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        CARGARDATOSPERSONALESButton.addActionListener(e -> cargarDatosPersonales());

        regresarButton.addActionListener(e -> {
            dispose();
            new PrincipalDocente(idDocente).setVisible(true);
        });

        salirButton.addActionListener(e -> dispose());
    }

    private void cargarDatosPersonales() {
        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT nombre, correo, telefono, direccion FROM usuarios WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idDocente);

            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Campo");
            model.addColumn("Valor");

            if (rs.next()) {
                model.addRow(new Object[]{"Nombre", rs.getString("nombre")});
                model.addRow(new Object[]{"Correo", rs.getString("correo")});
                model.addRow(new Object[]{"Teléfono", rs.getString("telefono")});
                model.addRow(new Object[]{"Dirección", rs.getString("direccion")});
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron datos para el docente con ID: " + idDocente);
            }

            tablaDatosPerDocente.setModel(model);

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos personales: " + ex.getMessage());
        }
    }
}
