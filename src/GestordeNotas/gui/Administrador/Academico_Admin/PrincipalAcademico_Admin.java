package GestordeNotas.gui.Administrador.Academico_Admin;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.database.CleverDB;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalAcademico_Admin extends JFrame {
    // Panel principal y componentes visuales
    private JPanel panel1;
    private JPanel PrincipalAdmin;
    private JTabbedPane tabbedPane1;
    private JPanel AdminCrudAsig; // Panel para CRUD de asignaturas
    private JTable s; // Tabla para mostrar asignaturas
    private JButton INGRESARasignaturasButton; // Botón para ingresar nueva asignatura
    private JTextField textField1; // Campo para nombre de asignatura
    private JButton MOSTRARasginaturasButton; // Botón para mostrar asignaturas
    private JButton ELIMINARasignaturaButton; // Botón para eliminar asignatura
    private JButton LIMPIARButton; // Botón para limpiar campos asignaturas
    private JTextField textField2; // Campo para descripción de asignatura
    private JButton ACTUALIZARasignaturasButton2; // Botón para actualizar asignatura
    private JPanel AdminCrudUsuarios; // Panel para CRUD de usuarios
    private JTable tablaAdminGestionUsers; // Tabla para mostrar usuarios
    private JButton AGREGARButton; // Botón para agregar usuario
    private JTextField textField3; // Campo nombre usuario
    private JTextField textField4; // Campo correo usuario
    private JTextField textField5; // Campo rol usuario
    private JButton ACTUALIZARButton; // Botón para actualizar usuario
    private JButton regresarButton; // Botón para regresar al panel principal
    private JButton salirDelSistemaButton; // Botón para salir del sistema
    private JButton MOSTRARButton1; // Botón para mostrar usuarios
    private JButton ELIMINARButton1; // Botón para eliminar usuario
    private JButton LIMPIARButton1; // Botón para limpiar campos usuarios
    private JTextField textField6; // Campo ID usuario (si es necesario)
    private JTextField textFieldContraseña; // Campo para contraseña usuario
    private JTextField textField7;  // Campo para descripción asignatura (extra)
    private JTextField textFieldCreditos;  // Campo para créditos de la asignatura

    public PrincipalAcademico_Admin() {
        setContentPane(panel1);
        setTitle("Academico - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null); // Centrar ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Acción para ingresar una asignatura
        INGRESARasignaturasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField1.getText();
                String descripcion = textField2.getText();
                String creditosStr = textFieldCreditos.getText();

                // Validar campos no vacíos
                if (nombre.isEmpty() || descripcion.isEmpty() || creditosStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    int creditos = Integer.parseInt(creditosStr); // Convertir créditos a entero
                    Connection conexion = CleverDB.getConexion();
                    String query = "INSERT INTO asignaturas (nombre, descripcion, creditos) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nombre);
                    stmt.setString(2, descripcion);
                    stmt.setInt(3, creditos);
                    stmt.executeUpdate(); // Ejecutar inserción en DB
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(null, "Asignatura agregada correctamente.");
                    cargarAsignaturas(); // Actualizar tabla asignaturas
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar asignatura: " + ex.getMessage());
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los créditos deben ser un número.");
                }
            }
        });

        // Acción para actualizar una asignatura existente
        ACTUALIZARasignaturasButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = s.getSelectedRow();
                if (selectedRow == -1) { // Validar que se haya seleccionado una fila
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una asignatura de la tabla para actualizar.");
                    return;
                }

                String nombre = textField1.getText();
                String descripcion = textField2.getText();
                String creditosStr = textFieldCreditos.getText();

                // Validar campos no vacíos
                if (nombre.isEmpty() || descripcion.isEmpty() || creditosStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    int id = (Integer) s.getValueAt(selectedRow, 0); // Obtener ID asignatura de la tabla
                    int creditos = Integer.parseInt(creditosStr);

                    Connection conexion = CleverDB.getConexion();
                    String query = "UPDATE asignaturas SET nombre = ?, descripcion = ?, creditos = ? WHERE id_asignatura = ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nombre);
                    stmt.setString(2, descripcion);
                    stmt.setInt(3, creditos);
                    stmt.setInt(4, id);
                    stmt.executeUpdate(); // Ejecutar actualización en DB
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(null, "Asignatura actualizada correctamente.");
                    cargarAsignaturas(); // Recargar tabla
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar asignatura: " + ex.getMessage());
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los créditos deben ser un número.");
                }
            }
        });

        // Acción para eliminar una asignatura seleccionada
        ELIMINARasignaturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = s.getSelectedRow();
                if (selectedRow == -1) { // Validar selección
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una asignatura de la tabla para eliminar.");
                    return;
                }

                try {
                    int id = (Integer) s.getValueAt(selectedRow, 0); // Obtener ID asignatura
                    // Confirmar eliminación con el usuario
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta asignatura?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Connection conexion = CleverDB.getConexion();
                        String query = "DELETE FROM asignaturas WHERE id_asignatura = ?";
                        PreparedStatement stmt = conexion.prepareStatement(query);
                        stmt.setInt(1, id);
                        stmt.executeUpdate(); // Ejecutar eliminación en DB
                        stmt.close();
                        conexion.close();
                        JOptionPane.showMessageDialog(null, "Asignatura eliminada correctamente.");
                        cargarAsignaturas(); // Actualizar tabla
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar asignatura: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Botón para limpiar los campos de texto relacionados con asignaturas
        LIMPIARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(""); // Limpiar nombre
                textField2.setText(""); // Limpiar descripción
                textField7.setText(""); // Limpiar descripción extra
                textFieldCreditos.setText(""); // Limpiar créditos
            }
        });

        // Botón para limpiar los campos de texto relacionados con usuarios
        LIMPIARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField3.setText(""); // Limpiar nombre usuario
                textField4.setText(""); // Limpiar correo
                textField5.setText(""); // Limpiar rol
                textField6.setText(""); // Limpiar ID
                textFieldContraseña.setText(""); // Limpiar contraseña
            }
        });

        // Botón para regresar al panel principal del administrador
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar ventana actual
                new PrincipalAdmin().setVisible(true); // Abrir ventana principal administrador
            }
        });

        // Botón para salir completamente del sistema con confirmación
        salirDelSistemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Salir de la aplicación
                }
            }
        });

        // Botón para mostrar las asignaturas en la tabla
        MOSTRARasginaturasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarAsignaturas(); // Cargar y mostrar asignaturas
            }
        });

        // Botón para mostrar los usuarios en la tabla
        MOSTRARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarUsuarios(); // Cargar y mostrar usuarios
            }
        });

        // Acción para actualizar datos de un usuario seleccionado
        ACTUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaAdminGestionUsers.getSelectedRow();
                if (selectedRow == -1) { // Validar selección
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la tabla para actualizar.");
                    return;
                }

                String nombre = textField3.getText();
                String correo = textField4.getText();
                String rol = textField5.getText();

                // Validar campos no vacíos
                if (nombre.isEmpty() || correo.isEmpty() || rol.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    int id = (Integer) tablaAdminGestionUsers.getValueAt(selectedRow, 0); // Obtener ID usuario
                    Connection conexion = CleverDB.getConexion();
                    String query = "UPDATE usuarios SET nombre = ?, correo = ?, rol = ? WHERE id= ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nombre);
                    stmt.setString(2, correo);
                    stmt.setString(3, rol);
                    stmt.setInt(4, id);
                    stmt.executeUpdate(); // Ejecutar actualización en DB
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
                    cargarUsuarios(); // Recargar tabla usuarios
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Acción para agregar un nuevo usuario a la base de datos
        AGREGARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField3.getText();
                String correo = textField4.getText();
                String rol = textField5.getText();
                String contraseña = textFieldContraseña.getText(); // Obtener contraseña

                // Validar campos no vacíos
                if (nombre.isEmpty() || correo.isEmpty() || rol.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    Connection conexion = CleverDB.getConexion();
                    String query = "INSERT INTO usuarios (nombre, correo, rol, contraseña) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nombre);
                    stmt.setString(2, correo);
                    stmt.setString(3, rol);
                    stmt.setString(4, contraseña);  // Insertar contraseña
                    stmt.executeUpdate(); // Ejecutar inserción
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.");
                    cargarUsuarios(); // Recargar tabla usuarios
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Acción para eliminar un usuario seleccionado
        ELIMINARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaAdminGestionUsers.getSelectedRow();
                if (selectedRow == -1) { // Validar selección
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la tabla para eliminar.");
                    return;
                }

                try {
                    int id = (Integer) tablaAdminGestionUsers.getValueAt(selectedRow, 0); // Obtener ID usuario
                    // Confirmar eliminación
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Connection conexion = CleverDB.getConexion();
                        String query = "DELETE FROM usuarios WHERE id = ?";
                        PreparedStatement stmt = conexion.prepareStatement(query);
                        stmt.setInt(1, id);
                        stmt.executeUpdate(); // Ejecutar eliminación
                        stmt.close();
                        conexion.close();
                        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
                        cargarUsuarios(); // Recargar tabla usuarios
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

    }

    // Metodo para cargar los usuarios desde la base de datos y mostrar en la tabla
    private void cargarUsuarios() {
        try {
            Connection conexion = CleverDB.getConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, correo, rol FROM usuarios");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Correo");
            model.addColumn("Rol");

            // Recorrer resultados y agregar filas a la tabla
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("rol")
                });
            }

            tablaAdminGestionUsers.setModel(model); // Asignar modelo a la tabla usuarios
            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo para cargar las asignaturas desde la base de datos y mostrar en la tabla
    private void cargarAsignaturas() {
        try {
            Connection conexion = CleverDB.getConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_asignatura, nombre, descripcion FROM asignaturas");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Descripción");

            // Recorrer resultados y agregar filas a la tabla
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_asignatura"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                });
            }

            s.setModel(model); // Asignar modelo a la tabla asignaturas
            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
