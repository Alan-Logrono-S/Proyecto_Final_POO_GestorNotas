package GestordeNotas.gui.Coordinador.Academico_Coord;

import GestordeNotas.gui.Principal.PrincipalCoordinador;
import GestordeNotas.database.CleverDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Principal_Academico_Coord extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel CoordEditarNotas;
    private JTable tablaCoordCalificaciones;
    private JButton ingresarCalificacionesButton;
    private JButton modificarCalificacionesButton;
    private JComboBox<String> comboBox1;  // Aquí estamos asegurándonos de que el tipo de datos sea String
    private JPanel CoordEstudiantes;
    private JTable tablaEstudiantesxAsig;
    private JButton verEstudiantesButton;
    private JButton verCalificacionesButton;

    public Principal_Academico_Coord() {

        setContentPane(panel1);
        setTitle("Academico - Coordinador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializar el JComboBox con las asignaturas (asume que ya tienes un método para obtener las asignaturas)
        cargarAsignaturas();

        // CALIFICACIONES
        ingresarCalificacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarCalificaciones();  // Método para ingresar calificaciones
            }
        });

        modificarCalificacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarCalificaciones();  // Método para modificar calificaciones
            }
        });

        // INSCRIPCIONES
        verEstudiantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verEstudiantes();  // Método para ver estudiantes por asignatura
            }
        });

        // EXIT
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalCoordinador().setVisible(true);
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        verCalificacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    // Método para cargar las asignaturas en el JComboBox
    private void cargarAsignaturas() {
        try {
            Connection conexion = CleverDB.getConexion();
            String query = "SELECT nombre FROM asignaturas";  // Usamos "nombre" que es el nombre correcto de la columna
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                comboBox1.addItem(rs.getString("nombre"));  // "nombre" es la columna correcta
            }

            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las asignaturas: " + e.getMessage());
        }
    }

    // Método para ingresar calificaciones
    private void ingresarCalificaciones() {
        String asignatura = (String) comboBox1.getSelectedItem();

        if (asignatura != null && !asignatura.isEmpty()) {
            try {
                Connection conexion = CleverDB.getConexion();

                // Paso 1: Obtener estudiantes inscritos en la asignatura
                String queryEstudiantes = "SELECT u.id, u.nombre FROM usuarios u " +
                        "JOIN matriculas m ON u.id = m.id_estudiante " +
                        "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                        "WHERE a.nombre = ? AND u.rol = 'estudiante'";
                PreparedStatement stmtEst = conexion.prepareStatement(queryEstudiantes);
                stmtEst.setString(1, asignatura);
                ResultSet rsEst = stmtEst.executeQuery();

                // Mostrar en combo para selección
                DefaultComboBoxModel<String> modeloEst = new DefaultComboBoxModel<>();
                java.util.Map<String, Integer> mapaEstudiantes = new java.util.HashMap<>();

                while (rsEst.next()) {
                    String nombre = rsEst.getString("nombre");
                    int id = rsEst.getInt("id");
                    modeloEst.addElement(nombre);
                    mapaEstudiantes.put(nombre, id);
                }

                JComboBox<String> comboEstudiantes = new JComboBox<>(modeloEst);
                JTextField calificacionField = new JTextField(5);
                JPanel panel = new JPanel();
                panel.add(new JLabel("Estudiante:"));
                panel.add(comboEstudiantes);
                panel.add(new JLabel("Calificación:"));
                panel.add(calificacionField);

                int option = JOptionPane.showConfirmDialog(this, panel, "Ingresar Calificación", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String nombreEst = (String) comboEstudiantes.getSelectedItem();
                    double calificacion = Double.parseDouble(calificacionField.getText());
                    int idEstudiante = mapaEstudiantes.get(nombreEst);

                    // Paso 2: obtener id_matricula
                    String queryMatricula = "SELECT m.id_matricula FROM matriculas m " +
                            "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                            "WHERE m.id_estudiante = ? AND a.nombre = ?";
                    PreparedStatement stmtMat = conexion.prepareStatement(queryMatricula);
                    stmtMat.setInt(1, idEstudiante);
                    stmtMat.setString(2, asignatura);
                    ResultSet rsMat = stmtMat.executeQuery();

                    if (rsMat.next()) {
                        int idMatricula = rsMat.getInt("id_matricula");

                        // Paso 3: Insertar calificación
                        String insertQuery = "INSERT INTO calificaciones (id_matricula, calificacion) VALUES (?, ?)";
                        PreparedStatement stmtInsert = conexion.prepareStatement(insertQuery);
                        stmtInsert.setInt(1, idMatricula);
                        stmtInsert.setDouble(2, calificacion);
                        stmtInsert.executeUpdate();
                        stmtInsert.close();

                        JOptionPane.showMessageDialog(this, "Calificación ingresada correctamente.");
                        actualizarTablaCalificaciones();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró matrícula para este estudiante y asignatura.");
                    }

                    rsMat.close();
                    stmtMat.close();
                }

                rsEst.close();
                stmtEst.close();
                conexion.close();

            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una asignatura.");
        }
    }


    // Método para modificar calificaciones
    private void modificarCalificaciones() {
        int selectedRow = tablaCoordCalificaciones.getSelectedRow();
        if (selectedRow != -1) {
            int idCalificacion = (int) tablaCoordCalificaciones.getValueAt(selectedRow, 0);
            String nuevaCalificacion = JOptionPane.showInputDialog(this, "Ingrese la nueva calificación:");

            if (nuevaCalificacion != null && !nuevaCalificacion.isEmpty()) {
                try {
                    Connection conexion = CleverDB.getConexion();
                    String query = "UPDATE calificaciones SET calificacion = ? WHERE id_calificacion = ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nuevaCalificacion);
                    stmt.setInt(2, idCalificacion);
                    stmt.executeUpdate();
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(this, "Calificación modificada correctamente.");
                    actualizarTablaCalificaciones();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al modificar la calificación: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor ingrese una nueva calificación.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una calificación para modificar.");
        }
    }

    // Método para ver estudiantes por asignatura
    private void verEstudiantes() {
        String asignaturaSeleccionada = (String) comboBox1.getSelectedItem();

        if (asignaturaSeleccionada != null && !asignaturaSeleccionada.isEmpty()) {
            try {
                // Consulta SQL modificada
                String query = "SELECT u.id, u.nombre, a.nombre AS asignatura " +
                        "FROM usuarios u " +
                        "JOIN matriculas m ON u.id = m.id_estudiante " +
                        "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                        "WHERE a.nombre = ? AND u.rol = 'estudiante'";  // Filtrar solo los estudiantes

                Connection conexion = CleverDB.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, asignaturaSeleccionada);  // Pasar la asignatura seleccionada en el comboBox
                ResultSet rs = stmt.executeQuery();

                // Mostrar los resultados en la tabla
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("ID Estudiante");
                model.addColumn("Nombre");
                model.addColumn("Asignatura");

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("asignatura")
                    });
                }

                tablaEstudiantesxAsig.setModel(model);
                rs.close();
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al consultar los estudiantes: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una asignatura.");
        }
    }


    // Método para actualizar la tabla de calificaciones
    private void actualizarTablaCalificaciones() {
        String asignaturaSeleccionada = (String) comboBox1.getSelectedItem();

        if (asignaturaSeleccionada != null && !asignaturaSeleccionada.isEmpty()) {
            try {
                Connection conexion = CleverDB.getConexion();
                String query = "SELECT c.id_calificacion, u.nombre AS estudiante, a.nombre AS asignatura, c.calificacion " +
                        "FROM calificaciones c " +
                        "JOIN matriculas m ON c.id_matricula = m.id_matricula " +
                        "JOIN usuarios u ON m.id_estudiante = u.id" +
                        "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                        "WHERE a.nombre = ?";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, asignaturaSeleccionada);
                ResultSet rs = stmt.executeQuery();

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("ID Calificación");
                model.addColumn("Estudiante");
                model.addColumn("Asignatura");
                model.addColumn("Calificación");

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id_calificacion"),
                            rs.getString("estudiante"),
                            rs.getString("asignatura"),
                            rs.getDouble("calificacion")
                    });
                }

                tablaCoordCalificaciones.setModel(model);
                rs.close();
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar calificaciones: " + e.getMessage());
            }
        }
    }

}
