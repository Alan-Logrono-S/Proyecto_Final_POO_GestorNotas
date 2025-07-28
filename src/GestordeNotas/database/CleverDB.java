package GestordeNotas.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CleverDB {

    private static final String HOST = "bqajf1zbxvelgzzikbmb-mysql.services.clever-cloud.com";
    private static final String PUERTO = "3306";
    private static final String DATABASE = "bqajf1zbxvelgzzikbmb";
    private static final String USUARIO = "uckxb462tr4fsxeg";
    private static final String PASSWORD = "4iP5maW2S0NrDXd4WlHR";
    private static final String URL = "jdbc:mysql://uckxb462tr4fsxeg:4iP5maW2S0NrDXd4WlHR@bqajf1zbxvelgzzikbmb-mysql.services.clever-cloud.com:3306/bqajf1zbxvelgzzikbmb";

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

