package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    private Connection con;
    private PreparedStatement ps;

    public ClienteDAO() {
        this.con = new Conexion().getConexion();
    }

    public int agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nombre, correo, telefono) VALUES (?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre()); 
            ps.setString(2, cliente.getCorreo()); 
            ps.setString(3, cliente.getTelefono()); 
            ps.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace(); 
            return 0; 
        }
        return 1; 
    }

    public List<Cliente> obtenerClientes() throws SQLException {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("Id"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setCorreo(rs.getString("Correo"));
                cliente.setTelefono(rs.getString("Telefono"));
                listaClientes.add(cliente);
            }
        }
        return listaClientes;
    }

    public int actualizarCliente(Cliente c) {
    String sql = "UPDATE cliente SET nombre = ?, correo = ?, telefono = ? WHERE id = ?"; 
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, c.getNombre()); 
        ps.setString(2, c.getCorreo());
        ps.setString(3, c.getTelefono()); 
        ps.setInt(4, c.getId());
        ps.executeUpdate();
        return 1; 
    } catch (Exception e) {
        System.err.println("Error al actualizar: " + e.getMessage());
        return 0;
    }
}

    public int eliminar(int id) {
    String sql = "DELETE FROM cliente WHERE id = ?";
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        return 1;
    } catch (Exception e) {
        System.err.println("Error al eliminar: " + e.getMessage());
        return 0; 
    }
    }
}
