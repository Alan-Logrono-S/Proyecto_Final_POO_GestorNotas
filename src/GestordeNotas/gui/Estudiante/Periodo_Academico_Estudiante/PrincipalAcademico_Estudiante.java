package GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante;

import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalEstudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrincipalAcademico_Estudiante extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel InicioEstudiante;
    private JTable tablaDatosEstudiante;
    private JButton CARGARButton;
    private JPanel EstudianteCalificaciones;
    private JTable tablaCalificacionesEstudiante;
    private JComboBox<String> comboBox1;
    private JButton verCalificacionesButton;
    private JPanel EstAsignaturasInscritas;
    private JTable tablaAsignaturasEstudiantes;
    private JButton cargarAsignaturasButton;

    private int idEstudiante;

    public PrincipalAcademico_Estudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;

        setContentPane(panel1);
        setTitle("Académico - Estudiante");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cargar datos personales
        CARGARButton.addActionListener(e -> cargarDatosPersonales());

        // Cargar asignaturas inscritas
        cargarAsignaturasButton.addActionListener(e -> cargarAsignaturasInscritas());

        // Ver calificaciones de asignatura seleccionada en comboBox1
        verCalificacionesButton.addActionListener(e -> cargarCalificaciones());

        // Volver al menú principal
        regresarButton.addActionListener(e -> {
            dispose();
            new PrincipalEstudiante().setVisible(true);
        });

        salirButton.addActionListener(e -> dispose());
    }

    private void cargarDatosPersonales() {
        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT nombre, correo, telefono, direccion FROM usuarios WHERE id_usuario = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre");
            model.addColumn("Correo");
            model.addColumn("Teléfono");
            model.addColumn("Dirección");

            if (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                });
            }
            tablaDatosEstudiante.setModel(model);

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos personales: " + ex.getMessage());
        }
    }

    private void cargarAsignaturasInscritas() {
        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT a.nombre FROM asignaturas a " +
                    "JOIN matriculas m ON a.id_asignatura = m.id_asignatura " +
                    "WHERE m.id_estudiante = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Asignatura");

            comboBox1.removeAllItems();

            while (rs.next()) {
                String asignatura = rs.getString("nombre");
                model.addRow(new Object[]{asignatura});
                comboBox1.addItem(asignatura);
            }

            tablaAsignaturasEstudiantes.setModel(model);

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + ex.getMessage());
        }
    }

    private void cargarCalificaciones() {
        String asignatura = (String) comboBox1.getSelectedItem();
        if (asignatura == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una asignatura primero.");
            return;
        }

        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT c.calificacion, c.observaciones FROM calificaciones c " +
                    "JOIN matriculas m ON c.id_matricula = m.id_matricula " +
                    "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                    "WHERE m.id_estudiante = ? AND a.nombre = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            stmt.setString(2, asignatura);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Calificación");
            model.addColumn("Observaciones");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getDouble("calificacion"),
                        rs.getString("observaciones")
                });
            }

            tablaCalificacionesEstudiante.setModel(model);

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar calificaciones: " + ex.getMessage());
        }
    }
}
