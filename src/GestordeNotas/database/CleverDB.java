package GestordeNotas.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CleverDB {

    private static final String HOST = "b0uv4q9pmjaqbwn1ua40-mysql.services.clever-cloud.com";
    private static final String PUERTO = "3306";
    private static final String DATABASE = "b0uv4q9pmjaqbwn1ua40";
    private static final String USUARIO = "uaetlorx4i55g4k2";
    private static final String PASSWORD = "PNQ3Vgx6KmYN8pfvZ7Ph";
    private static final String URL = "jdbc:mysql://uaetlorx4i55g4k2:PNQ3Vgx6KmYN8pfvZ7Ph@b0uv4q9pmjaqbwn1ua40-mysql.services.clever-cloud.com:3306/b0uv4q9pmjaqbwn1ua40";

    // Constructor privado para que no se pueda instanciar
    private CleverDB() {}

    // Metodo estático para obtener una conexión segura
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    // Prueba de conexión usando try-with-resources (evita conexiones colgadas)
    public static void main(String[] args) {
        try (Connection conexion = CleverDB.getConexion()) {
            System.out.println("✅ Conexión exitosa a Clever Cloud!");
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

