package GestordeNotas.gui.Estudiante.Periodo_Academico_Estudiante;

import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalEstudiante;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

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
    private JButton correoButton;
    private JComboBox comboBoxAsignaturaMatricula;
    private JButton agregarMatriculaButton;
    private JTable tableMatricula;
    private JButton cancelarButton;
    private JPanel matriculasPanel;

    private int idEstudiante;

    private void cargarTablaMatriculas() {
        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT a.nombre FROM asignaturas a " +
                    "JOIN matriculas m ON a.id_asignatura = m.id_asignatura " +
                    "WHERE m.id_estudiante = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Asignaturas Matriculadas");

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("nombre")});
            }

            tableMatricula.setModel(model);

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar tabla de matrícula: " + ex.getMessage());
        }
    }

    private void agregarMatriculaEstudiante() {
        String nombreAsignatura = (String) comboBoxAsignaturaMatricula.getSelectedItem();
        if (nombreAsignatura == null || nombreAsignatura.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona una asignatura.");
            return;
        }

        try (Connection con = CleverDB.getConexion()) {
            // Buscar el ID de la asignatura por su nombre
            String idQuery = "SELECT id_asignatura FROM asignaturas WHERE nombre = ?";
            PreparedStatement idStmt = con.prepareStatement(idQuery);
            idStmt.setString(1, nombreAsignatura);
            ResultSet rsId = idStmt.executeQuery();

            if (!rsId.next()) {
                JOptionPane.showMessageDialog(this, "No se encontró la asignatura en la base de datos.");
                return;
            }

            int idAsignatura = rsId.getInt("id_asignatura");

            // Verificar si ya está matriculado
            String checkQuery = "SELECT COUNT(*) FROM matriculas WHERE id_estudiante = ? AND id_asignatura = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setInt(1, idEstudiante);
            checkStmt.setInt(2, idAsignatura);
            ResultSet rsCheck = checkStmt.executeQuery();

            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Ya estás matriculado en esta asignatura.");
                return;
            }

            int idPeriodo = 1; // o el valor real del período académico actual

            String insertQuery = "INSERT INTO matriculas (id_estudiante, id_asignatura, id_periodo) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(insertQuery);
            insertStmt.setInt(1, idEstudiante);
            insertStmt.setInt(2, idAsignatura);
            insertStmt.setInt(3, idPeriodo);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "¡Matrícula exitosa en " + nombreAsignatura + "!");
            cargarAsignaturasInscritas(); // actualizar lista
            cargarTablaMatriculas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar matrícula: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public PrincipalAcademico_Estudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;

        setContentPane(panel1);
        setTitle("Académico - Estudiante");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cargarTablaMatriculas();

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
        correoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarCalificacionesPorCorreo();

            }
        });
        agregarMatriculaButton.addActionListener(e -> agregarMatriculaEstudiante());
    }

    private void cargarDatosPersonales() {
        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT nombre, correo, telefono, direccion FROM usuarios WHERE id = ?";
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
            String query = "SELECT a.nombre AS asignatura, c.calificacion, c.observaciones " +
                    "FROM calificaciones c " +
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



    private void enviarCalificacionesPorCorreo(){
        String asignatura = (String) comboBox1.getSelectedItem();
        try (Connection con = CleverDB.getConexion()){
            String correo = "";
            PreparedStatement ps = con.prepareStatement("SELECT correo FROM usuarios WHERE id = ?");
            ps.setInt(1, idEstudiante);
            ResultSet rsCorreo = ps.executeQuery();
            if (rsCorreo.next()){
                correo = rsCorreo.getString("correo");
            }else {
                JOptionPane.showMessageDialog(null,"no se encontro el correo del estudiante");
                return;
            }
            StringBuilder cuerpo = new StringBuilder("Tus calificaciones \n\n");
            String query = "SELECT a.nombre AS asignatura, c.calificacion, c.observaciones " +
                    "FROM calificaciones c " +
                    "JOIN matriculas m ON c.id_matricula = m.id_matricula " +
                    "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                    "WHERE m.id_estudiante = ? AND a.nombre = ?";
            PreparedStatement stmt= con.prepareStatement(query);
            stmt.setInt(1,idEstudiante);
            stmt.setString(2, asignatura);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cuerpo.append("Asignatura: ").append(rs.getString("asignatura")).append("\n");
                cuerpo.append("Calificación: ").append(rs.getDouble("calificacion")).append("\n");
                cuerpo.append("Observaciones: ").append(rs.getString("observaciones")).append("\n\n");
            }
            rs.close();
            stmt.close();
            rsCorreo.close();
            ps.close();

            enviarCorreo(correo, "Resumen de Calificaciones ",cuerpo.toString());

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error al enviar correo: "+ ex.getMessage());
        }
    }
    private void enviarCorreo(String destinatario, String asunto, String cuerpo){
        final String remitente = "anabelayo2017@gmail.com";
        final String clave = "pyfz hxru towk wssk";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            Transport.send(message);
            JOptionPane.showMessageDialog(null,"Las notas fueron enviados al correo correctamente :)");

        }catch (MessagingException e){
            JOptionPane.showMessageDialog(null,"Error enviando correo: "+ e.getMessage());
        }


    }

}