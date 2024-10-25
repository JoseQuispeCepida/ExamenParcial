package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private Connection con;
    private Conexion conectar = new Conexion();
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ReservaDAO() {
        this.con = new Conexion().getConexion();
    }
    
    public int agregarReserva(Reserva reserva) {
        String sql = "INSERT INTO reserva (idCliente, numeroHabitacion, fechaEntrada, fechaSalida) VALUES (?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, reserva.getIdCliente());
            ps.setInt(2, reserva.getNumeroHabitacion());
            ps.setDate(3, new java.sql.Date(reserva.getFechaEntrada().getTime()));
            ps.setDate(4, new java.sql.Date(reserva.getFechaSalida().getTime()));
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int actualizarReserva(Reserva reserva) {
        String sql = "UPDATE reserva SET idCliente = ?, numeroHabitacion = ?, fechaEntrada = ?, fechaSalida = ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, reserva.getIdCliente());
            ps.setInt(2, reserva.getNumeroHabitacion());
            ps.setDate(3, new java.sql.Date(reserva.getFechaEntrada().getTime()));
            ps.setDate(4, new java.sql.Date(reserva.getFechaSalida().getTime()));
            ps.setInt(5, reserva.getId());
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int eliminarReserva(int id) {
        String sql = "DELETE FROM reserva WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<Reserva> obtenerReservas() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setIdCliente(rs.getInt("idCliente"));
                reserva.setNumeroHabitacion(rs.getInt("numeroHabitacion"));
                reserva.setFechaEntrada(rs.getDate("fechaEntrada"));
                reserva.setFechaSalida(rs.getDate("fechaSalida"));
                lista.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public Reserva obtenerReservaPorId(int id) {
        Reserva reserva = null;
        String sql = "SELECT * FROM reserva WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setIdCliente(rs.getInt("idCliente"));
                reserva.setNumeroHabitacion(rs.getInt("numeroHabitacion"));
                reserva.setFechaEntrada(rs.getDate("fechaEntrada"));
                reserva.setFechaSalida(rs.getDate("fechaSalida"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reserva;
    }
}
