package GestordeNotas.gui.Administrador.Documentacion_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.database.CleverDB;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PrincipalDocumentacion_Admin extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel AdminPeriodosAcad;
    private JTable tablaAdminPeridoAcad;
    private JButton INGRESARButton;
    private JButton BUSCARButton;
    private JButton ELIMINARButton;
    private JPanel AdminMatricula;
    private JTable tablaAdminMatriculas;
    private JComboBox comboBox1Materias;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;
    private JButton consultarButton;

    public PrincipalDocumentacion_Admin() {
        setContentPane(panel1);
        setTitle("Documentación - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // PERIODO ACADEMICO
        BUSCARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPeriodoAcademico();  // Buscar periodos académicos
            }
        });

        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarPeriodoAcademico();  // Ingresar un nuevo periodo académico
            }
        });

        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPeriodoAcademico();  // Eliminar periodo académico
            }
        });

        // MATRÍCULA
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarMatricula();  // Agregar matrícula
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarMatricula();  // Cancelar matrícula
            }
        });

        // EXIT
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalAdmin().setVisible(true);
            }
        });

        salirDelSistemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        comboBox1Materias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar lo que desees hacer cuando se seleccione una materia
            }
        });

        // Acción para el botón Consultar
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarMatriculaPorMateria();
            }
        });
    }

    // Método para buscar un periodo académico
    private void buscarPeriodoAcademico() {
        String descripcionPeriodo = JOptionPane.showInputDialog(this, "Ingrese la descripción del periodo:");
        if (descripcionPeriodo != null && !descripcionPeriodo.trim().isEmpty()) {
            try {
                Connection conexion = CleverDB.getConexion();
                String query = "SELECT * FROM periodos WHERE descripcion LIKE ?";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, "%" + descripcionPeriodo + "%");
                ResultSet rs = stmt.executeQuery();

                // Mostrar los resultados en la tabla
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("ID Periodo");
                model.addColumn("Descripción");
                model.addColumn("Fecha Inicio");
                model.addColumn("Fecha Fin");

                while (rs.next()) {
                    model.addRow(new Object[] {
                            rs.getInt("id_periodo"),
                            rs.getString("descripcion"),
                            rs.getDate("fecha_inicio"),
                            rs.getDate("fecha_fin")
                    });
                }

                tablaAdminPeridoAcad.setModel(model);
                rs.close();
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al buscar el periodo académico: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una descripción.");
        }
    }

    // Método para ingresar un nuevo periodo académico
    private void ingresarPeriodoAcademico() {
        JTextField descripcionField = new JTextField(20);
        JTextField fechaInicioField = new JTextField(20);
        JTextField fechaFinField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);
        panel.add(new JLabel("Fecha Inicio:"));
        panel.add(fechaInicioField);
        panel.add(new JLabel("Fecha Fin:"));
        panel.add(fechaFinField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Ingresar Periodo Académico", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String descripcion = descripcionField.getText();
            String fechaInicio = fechaInicioField.getText();
            String fechaFin = fechaFinField.getText();

            try {
                Connection conexion = CleverDB.getConexion();
                String query = "INSERT INTO periodos (descripcion, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, descripcion);
                stmt.setDate(2, Date.valueOf(fechaInicio));
                stmt.setDate(3, Date.valueOf(fechaFin));
                stmt.executeUpdate();
                stmt.close();
                conexion.close();

                JOptionPane.showMessageDialog(this, "Periodo académico ingresado exitosamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al ingresar el periodo académico: " + e.getMessage());
            }
        }
    }

    // Método para eliminar un periodo académico
    private void eliminarPeriodoAcademico() {
        int selectedRow = tablaAdminPeridoAcad.getSelectedRow();
        if (selectedRow != -1) {
            int idPeriodo = (int) tablaAdminPeridoAcad.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar el periodo?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Connection conexion = CleverDB.getConexion();
                    String query = "DELETE FROM periodos WHERE id_periodo = ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setInt(1, idPeriodo);
                    stmt.executeUpdate();
                    stmt.close();
                    conexion.close();

                    JOptionPane.showMessageDialog(this, "Periodo académico eliminado.");
                    // Actualizar la tabla
                    buscarPeriodoAcademico();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el periodo académico: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un periodo para eliminar.");
        }
    }

    // Método para agregar matrícula
    private void agregarMatricula() {
        try {
            // Obtener datos de la matrícula
            String estudiante = (String) comboBox1Materias.getSelectedItem();
            int idEstudiante = getIdEstudiante(estudiante); // Obtener ID del estudiante
            int idAsignatura = 1; // ID de la asignatura, ejemplo: obtener de un JComboBox
            int idPeriodo = 1; // ID del periodo, ejemplo: obtener de un JComboBox

            // Verificar que los valores no sean nulos
            if (idEstudiante == -1 || idAsignatura == -1 || idPeriodo == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione todos los campos.");
                return;
            }

            // Agregar matrícula
            Connection conexion = CleverDB.getConexion();
            String query = "INSERT INTO matriculas (id_estudiante, id_asignatura, id_periodo) VALUES (?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idAsignatura);
            stmt.setInt(3, idPeriodo);
            stmt.executeUpdate();
            stmt.close();
            conexion.close();

            JOptionPane.showMessageDialog(this, "Matrícula agregada exitosamente.");
            actualizarTablaMatriculas(); // Actualizar tabla de matrículas
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar la matrícula: " + e.getMessage());
        }
    }

    // Método para cancelar la operación de matrícula
    private void cancelarMatricula() {
        int selectedRow = tablaAdminMatriculas.getSelectedRow();
        if (selectedRow != -1) {
            int idMatricula = (int) tablaAdminMatriculas.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de cancelar la matrícula?", "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Eliminar matrícula
                    Connection conexion = CleverDB.getConexion();
                    String query = "DELETE FROM matriculas WHERE id_matricula = ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setInt(1, idMatricula);
                    stmt.executeUpdate();
                    stmt.close();
                    conexion.close();

                    JOptionPane.showMessageDialog(this, "Matrícula cancelada.");
                    actualizarTablaMatriculas(); // Actualizar tabla de matriculas
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al cancelar la matrícula: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una matrícula para cancelar.");
        }
    }

    // Método para actualizar la tabla de matrículas
    private void actualizarTablaMatriculas() {
        try {
            // Actualiza los datos de la tabla con las matrículas
            Connection conexion = CleverDB.getConexion();
            String query = "SELECT * FROM matriculas"; // Obtener todas las matrículas
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Mostrar los resultados en la tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Matrícula");
            model.addColumn("ID Estudiante");
            model.addColumn("ID Asignatura");
            model.addColumn("ID Periodo");

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id_matricula"),
                        rs.getInt("id_estudiante"),
                        rs.getInt("id_asignatura"),
                        rs.getInt("id_periodo")
                });
            }

            tablaAdminMatriculas.setModel(model);
            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar las matrículas: " + e.getMessage());
        }

        // En el método de actualización de la tabla de matrículas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Matrícula");
        model.addColumn("ID Estudiante");
        model.addColumn("ID Asignatura");
        model.addColumn("ID Periodo");

        tablaAdminMatriculas.setModel(model);


    }

    // Método para obtener el ID del estudiante desde el nombre
    private int getIdEstudiante(String nombreEstudiante) {
        // Esta función debería buscar el ID del estudiante en la base de datos
        // por el nombre o identificador único.
        return 1;  // Devuelve un ID de ejemplo
    }

    // Método para consultar matriculas por materia
    private void consultarMatriculaPorMateria() {
        // Aquí debería ir el código para realizar la consulta sobre las matrículas de acuerdo con la materia seleccionada
        String materiaSeleccionada = (String) comboBox1Materias.getSelectedItem();

        if (materiaSeleccionada != null && !materiaSeleccionada.isEmpty()) {
            try {
                Connection conexion = CleverDB.getConexion();
                String query = "SELECT * FROM matriculas WHERE id_asignatura IN (SELECT id_asignatura FROM asignaturas WHERE nombre = ?)";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, materiaSeleccionada);
                ResultSet rs = stmt.executeQuery();

                // Mostrar los resultados en la tabla
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("ID Matrícula");
                model.addColumn("ID Estudiante");
                model.addColumn("ID Asignatura");
                model.addColumn("ID Periodo");

                while (rs.next()) {
                    model.addRow(new Object[] {
                            rs.getInt("id_matricula"),
                            rs.getInt("id_estudiante"),
                            rs.getInt("id_asignatura"),
                            rs.getInt("id_periodo")
                    });
                }

                tablaAdminMatriculas.setModel(model);
                rs.close();
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al consultar las matrículas: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una materia.");
        }
    }
}
