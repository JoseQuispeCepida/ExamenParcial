package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=GestionHotel;encrypt=false";
    private final String user = "sa";
    private final String password = "josecepida";
    private Connection conexion;

    public Conexion() {
        try {
            conexion = DriverManager.getConnection(url, user, password);
            //System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            //System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }
    
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                //System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            //System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
