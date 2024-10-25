package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {
    private Conexion conectar;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public HabitacionDAO() {
        this.con = new Conexion().getConexion();
    }

    public int agregarHabitacion(Habitacion h) {
        String sql = "INSERT INTO habitacion (numero, tipo, precio) VALUES (?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, h.getNumero());
            ps.setString(2, h.getTipo());
            ps.setDouble(3, h.getPrecio());
            ps.executeUpdate();
            return 1;
        } catch (Exception e) {
            System.err.println("Error al agregar habitación: " + e.getMessage());
            return 0; 
        }
    }

    public int actualizarHabitacion(Habitacion h) {
        String sql = "UPDATE habitacion SET tipo = ?, precio = ? WHERE numero = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, h.getTipo());
            ps.setDouble(2, h.getPrecio());
            ps.setInt(3, h.getNumero());
            ps.executeUpdate();
            return 1; 
        } catch (Exception e) {
            System.err.println("Error al actualizar habitación: " + e.getMessage());
            return 0; 
        }
    }

    // Método para eliminar una habitación
    public int eliminarHabitacion(int numero) {
        String sql = "DELETE FROM habitacion WHERE numero = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, numero);
            ps.executeUpdate();
            return 1; 
        } catch (Exception e) {
            System.err.println("Error al eliminar habitación: " + e.getMessage());
            return 0; // Retorna 0 si hubo un error
        }
    }

    public List<Habitacion> obtenerHabitaciones() {
        List<Habitacion> listaHabitaciones = new ArrayList<>();
        String sql = "SELECT * FROM habitacion";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Habitacion h = new Habitacion();
                h.setNumero(rs.getInt("numero"));
                h.setTipo(rs.getString("tipo"));
                h.setPrecio(rs.getDouble("precio"));
                listaHabitaciones.add(h);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener habitaciones: " + e.getMessage());
        } 
        return listaHabitaciones; 
    }
}