package GestordeNotas.gui.Docente.Academico_Docente;

import GestordeNotas.database.CleverDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class PrincipalAcademico_Docente extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel DocentRegistroNotas;
    private JTable tablaCalificaciones;
    private JComboBox<String> comboBox1;
    private JButton ingresarCalificacionesButton;
    private JButton modificarCalificacionesButton;
    private JPanel DocentVerEstudiantesXasignatura;
    private JTable tablaVerEstudxAsig;
    private JButton button1;
    private JButton verCalificacionesButton;

    private int idDocente; // ID del docente que inicia sesión

    public PrincipalAcademico_Docente(int idDocente) {
        this.idDocente = idDocente;

        setContentPane(panel1);
        setTitle("Académico - Docente");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cargarAsignaturasDocente();

        button1.addActionListener(e -> verEstudiantesPorAsignatura());
        ingresarCalificacionesButton.addActionListener(e -> ingresarCalificacion());
        modificarCalificacionesButton.addActionListener(e -> modificarCalificacion());

        regresarButton.addActionListener(e -> {
            dispose();
            new GestordeNotas.gui.Principal.PrincipalDocente(idDocente).setVisible(true);
        });

        salirButton.addActionListener(e -> dispose());

        verCalificacionesButton.addActionListener(e -> cargarCalificaciones());
    }

    public PrincipalAcademico_Docente() {
        this(3);
    }

    private void cargarAsignaturasDocente() {
        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT a.nombre FROM asignaturas a " +
                    "JOIN docentes_asignaturas da ON a.id_asignatura = da.id_asignatura " +
                    "WHERE da.id_docente = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idDocente);
            ResultSet rs = stmt.executeQuery();

            comboBox1.removeAllItems();
            while (rs.next()) {
                comboBox1.addItem(rs.getString("nombre"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + ex.getMessage());
        }
    }

    private void verEstudiantesPorAsignatura() {
        String asignatura = (String) comboBox1.getSelectedItem();
        if (asignatura == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una asignatura primero.");
            return;
        }

        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT u.id, u.nombre FROM usuarios u " +
                    "JOIN matriculas m ON u.id = m.id_estudiante " +
                    "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                    "WHERE a.nombre = ? AND u.rol = 'estudiante'";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, asignatura);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id"),
                        rs.getString("nombre")
                });
            }

            tablaVerEstudxAsig.setModel(model);

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar estudiantes: " + ex.getMessage());
        }
    }

    private void cargarCalificaciones() {
        String asignatura = (String) comboBox1.getSelectedItem();
        if (asignatura == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una asignatura para cargar calificaciones.");
            return;
        }

        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT u.id, u.nombre, c.calificacion FROM usuarios u " +
                    "JOIN matriculas m ON u.id = m.id_estudiante " +
                    "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                    "LEFT JOIN calificaciones c ON m.id_matricula = c.id_matricula " +
                    "WHERE a.nombre = ? AND u.rol = 'estudiante'";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, asignatura);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nombre", "Calificación"}, 0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getObject("calificacion") != null ? rs.getDouble("calificacion") : ""
                });
            }

            tablaCalificaciones.setModel(model);

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar calificaciones: " + ex.getMessage());
        }
    }

    private void ingresarCalificacion() {
        int filaSeleccionada = tablaVerEstudxAsig.getSelectedRow();
        String asignatura = (String) comboBox1.getSelectedItem();

        // Verifica si se ha seleccionado una fila
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante para ingresar la calificación.");
            return;
        }
        if (asignatura == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una asignatura.");
            return;
        }

        // Obtener ID del estudiante de la tabla seleccionada (tablaVerEstudxAsig)
        int idEstudiante = (int) tablaVerEstudxAsig.getValueAt(filaSeleccionada, 0);

        String input = JOptionPane.showInputDialog(this, "Ingrese la calificación para el estudiante:");
        if (input == null) return; // Cancelado
        double calificacion;
        try {
            calificacion = Double.parseDouble(input);
            if (calificacion < 0 || calificacion > 10) {
                JOptionPane.showMessageDialog(this, "La calificación debe estar entre 0 y 10.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para la calificación.");
            return;
        }

        try (Connection con = CleverDB.getConexion()) {
            // Obtener id_matricula
            String queryMatricula = "SELECT m.id_matricula FROM matriculas m " +
                    "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                    "WHERE m.id_estudiante = ? AND a.nombre = ?";
            PreparedStatement psMatricula = con.prepareStatement(queryMatricula);
            psMatricula.setInt(1, idEstudiante);
            psMatricula.setString(2, asignatura);
            ResultSet rsMatricula = psMatricula.executeQuery();

            if (!rsMatricula.next()) {
                JOptionPane.showMessageDialog(this, "Matrícula no encontrada para el estudiante en esta asignatura.");
                return;
            }
            int idMatricula = rsMatricula.getInt("id_matricula");
            rsMatricula.close();
            psMatricula.close();

            // Verificar si ya existe calificación
            String queryCalif = "SELECT id_calificacion FROM calificaciones WHERE id_matricula = ?";
            PreparedStatement psCheck = con.prepareStatement(queryCalif);
            psCheck.setInt(1, idMatricula);
            ResultSet rsCheck = psCheck.executeQuery();

            rsCheck.close();
            psCheck.close();

            // Insertar calificación
            String insertSQL = "INSERT INTO calificaciones (id_matricula, calificacion, observaciones) VALUES (?, ?, ?)";
            PreparedStatement psInsert = con.prepareStatement(insertSQL);
            psInsert.setInt(1, idMatricula);
            psInsert.setDouble(2, calificacion);
            psInsert.setString(3, "Calificación ingresada por docente");
            int filas = psInsert.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "Calificación ingresada correctamente.");
                cargarCalificaciones(); // Actualizar tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al ingresar la calificación.");
            }
            psInsert.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al ingresar calificación: " + ex.getMessage());
        }
    }

    private void modificarCalificacion() {
        int filaSeleccionada = tablaCalificaciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una calificación para modificar.");
            return;
        }

        String asignatura = (String) comboBox1.getSelectedItem();
        if (asignatura == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una asignatura.");
            return;
        }

        int idEstudiante = (int) tablaCalificaciones.getValueAt(filaSeleccionada, 0);

        String input = JOptionPane.showInputDialog(this, "Ingrese la nueva calificación:");
        if (input == null) return; // Cancelado
        double calificacion;
        try {
            calificacion = Double.parseDouble(input);
            if (calificacion < 0 || calificacion > 10) {
                JOptionPane.showMessageDialog(this, "La calificación debe estar entre 0 y 10.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para la calificación.");
            return;
        }

        try (Connection con = CleverDB.getConexion()) {
            // Obtener id_matricula
            String queryMatricula = "SELECT m.id_matricula FROM matriculas m " +
                    "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                    "WHERE m.id_estudiante = ? AND a.nombre = ?";
            PreparedStatement psMatricula = con.prepareStatement(queryMatricula);
            psMatricula.setInt(1, idEstudiante);
            psMatricula.setString(2, asignatura);
            ResultSet rsMatricula = psMatricula.executeQuery();

            if (!rsMatricula.next()) {
                JOptionPane.showMessageDialog(this, "Matrícula no encontrada para el estudiante en esta asignatura.");
                return;
            }
            int idMatricula = rsMatricula.getInt("id_matricula");
            rsMatricula.close();
            psMatricula.close();

            // Verificar si existe calificación para modificar
            String queryCalif = "SELECT id_calificacion FROM calificaciones WHERE id_matricula = ?";
            PreparedStatement psCheck = con.prepareStatement(queryCalif);
            psCheck.setInt(1, idMatricula);
            ResultSet rsCheck = psCheck.executeQuery();

            if (!rsCheck.next()) {
                JOptionPane.showMessageDialog(this, "No existe calificación para este estudiante. Use Ingresar.");
                rsCheck.close();
                psCheck.close();
                return;
            }
            int idCalificacion = rsCheck.getInt("id_calificacion");
            rsCheck.close();
            psCheck.close();

            // Actualizar calificación
            String updateSQL = "UPDATE calificaciones SET calificacion = ? WHERE id_calificacion = ?";
            PreparedStatement psUpdate = con.prepareStatement(updateSQL);
            psUpdate.setDouble(1, calificacion);
            psUpdate.setInt(2, idCalificacion);

            int filas = psUpdate.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "Calificación modificada correctamente.");
                cargarCalificaciones(); // actualizar tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar la calificación.");
            }
            psUpdate.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar calificación: " + ex.getMessage());
        }
    }
}
