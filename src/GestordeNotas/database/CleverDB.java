package GestordeNotas.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CleverDB {

    private static final String HOST = "bff6ewsjuz7mbtynro1r-mysql.services.clever-cloud.com";
    private static final String PUERTO = "3306";
    private static final String DATABASE = "bff6ewsjuz7mbtynro1r";
    private static final String USUARIO = "uaqr1wnk37o44s3o";
    private static final String PASSWORD = "VULRz16CXAjY3Q9ZpmHA";
    private static final String URL = "jdbc:mysql://uaqr1wnk37o44s3o:VULRz16CXAjY3Q9ZpmHA@bff6ewsjuz7mbtynro1r-mysql.services.clever-cloud.com:3306/bff6ewsjuz7mbtynro1r";

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

