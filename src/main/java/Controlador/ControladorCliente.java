package Controlador;

import Modelo.Cliente;
import Modelo.ClienteDAO;
import Vista.VistaCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorCliente implements ActionListener{
    
    private VistaCliente vistaCliente; 
    private ClienteDAO clienteDAO;
    Cliente c = new Cliente();
    
    public ControladorCliente(VistaCliente v){
        this.vistaCliente = v;
        this.clienteDAO = new ClienteDAO();
        this.vistaCliente.btnListar.addActionListener(this);
        this.vistaCliente.btnAgregar.addActionListener(this);
        this.vistaCliente.btnActualizar.addActionListener(this);
        this.vistaCliente.btnEliminar.addActionListener(this);
        listar(vistaCliente.tablaCliente);  
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaCliente.btnListar) {
            listar(vistaCliente.tablaCliente);
        } else if (e.getSource() == vistaCliente.btnAgregar) {
            agregarCliente();
        } else if (e.getSource() == vistaCliente.btnActualizar) {
            actualizarCliente();
        } else if (e.getSource() == vistaCliente.btnEliminar) {
            eliminarCliente();
        }
    }
    
    private void listar(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo");
        modelo.addColumn("Teléfono");

        try {
            List<Cliente> clientes = clienteDAO.obtenerClientes();
            for (Cliente cliente : clientes) {
                Object[] fila = new Object[4];
                fila[0] = cliente.getId();
                fila[1] = cliente.getNombre();
                fila[2] = cliente.getCorreo();
                fila[3] = cliente.getTelefono();
                modelo.addRow(fila);
            }
            tabla.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vistaCliente, "Error al listar clientes: " + ex.getMessage());
        }
    }
    
    public void agregarCliente() {
        String nom = vistaCliente.txtNombre.getText();
        String correo = vistaCliente.txtCorreo.getText();
        String tel = vistaCliente.txtTelefono.getText();
        
        if (!nom.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(vistaCliente, "El nombre solo debe contener letras");
            return;
        }

        if (!correo.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
        JOptionPane.showMessageDialog(vistaCliente, "El correo no debe contener números");
        return;
        }
    
        if (!tel.matches("\\d{9}")) {
            JOptionPane.showMessageDialog(vistaCliente, "El teléfono debe contener 9 dígitos numéricos");
            return;
        }
        
        c.setNombre(nom);
        c.setCorreo(correo);
        c.setTelefono(tel);       
        int r = clienteDAO.agregarCliente(c); 
        if (r == 1) {
            JOptionPane.showMessageDialog(vistaCliente, "Cliente agregado con éxito");
            listar(vistaCliente.tablaCliente);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaCliente, "Error al agregar cliente");
        }
    }
    
    public void actualizarCliente() {
        int fila = vistaCliente.tablaCliente.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaCliente, "Debe seleccionar un cliente");
        } else {
            int id = Integer.parseInt(vistaCliente.tablaCliente.getValueAt(fila, 0).toString());

            String nom = vistaCliente.txtNombre.getText();
            String correo = vistaCliente.txtCorreo.getText();
            String tel = vistaCliente.txtTelefono.getText();

            // Validación del nombre: solo letras
            if (!nom.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(vistaCliente, "El nombre solo debe contener letras.");
                return;
            }
            
            // Validación del correo: permite caracteres especiales pero no números
            if (!correo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ._%+-]+@[a-zA-ZáéíóúÁÉÍÓÚñÑ.-]+\\.[a-zA-Z]{2,}")) {
                JOptionPane.showMessageDialog(vistaCliente, "El correo solo debe contener letras y caracteres especiales. No se permiten números.");
                return;
            }

                // Validación del teléfono: solo números y exactamente 9 dígitos
            if (!tel.matches("\\d{9}")) {
                JOptionPane.showMessageDialog(vistaCliente, "El teléfono debe contener 9 dígitos y solo números.");
                return;
            }

            // Si todas las validaciones son exitosas, se procede a actualizar
            c.setId(id);
            c.setNombre(nom);
            c.setCorreo(correo);
            c.setTelefono(tel);

            int r = clienteDAO.actualizarCliente(c);
            if (r == 1) {
                JOptionPane.showMessageDialog(vistaCliente, "Cliente actualizado con éxito");
                listar(vistaCliente.tablaCliente);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vistaCliente, "Error al actualizar el cliente");
            }
        }
    }
    
    public void eliminarCliente() {
        int fila = vistaCliente.tablaCliente.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaCliente, "Debe seleccionar un cliente");
        } else {
            int id = Integer.parseInt(vistaCliente.tablaCliente.getValueAt(fila, 0).toString()); 
            clienteDAO.eliminar(id);
            JOptionPane.showMessageDialog(vistaCliente, "Cliente eliminado con éxito");
            listar(vistaCliente.tablaCliente);
        }
    }
    
    private void limpiarCampos() {
        vistaCliente.txtId.setText("");
        vistaCliente.txtNombre.setText("");
        vistaCliente.txtCorreo.setText("");
        vistaCliente.txtTelefono.setText("");
    }    
}
