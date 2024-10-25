package Controlador;

import Modelo.Reserva;
import Modelo.ReservaDAO;
import Vista.VistaReserva;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorReserva implements ActionListener{
    
    private VistaReserva vistaReserva; 
    private ReservaDAO reservaDAO;
    Reserva r = new Reserva();
    
    public ControladorReserva(VistaReserva vista) {
        this.vistaReserva = vista;
        this.reservaDAO = new ReservaDAO();
        this.vistaReserva.btnListar.addActionListener(this);
        this.vistaReserva.btnAgregar.addActionListener(this);
        this.vistaReserva.btnActualizar.addActionListener(this);
        this.vistaReserva.btnEliminar.addActionListener(this);
        listarReservas(vistaReserva.tablaReserva);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaReserva.btnListar) {
            listarReservas(vistaReserva.tablaReserva);
        } else if (e.getSource() == vistaReserva.btnAgregar) {
            agregarReserva();
        } else if (e.getSource() == vistaReserva.btnActualizar) {
            actualizarReserva();
        } else if (e.getSource() == vistaReserva.btnEliminar) {
            eliminarReserva();
        }
    }
    
    private void agregarReserva() {

        // Validación del ID del Cliente: solo números
        String idClienteStr = vistaReserva.txtIdCliente.getText();
        if (!idClienteStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(vistaReserva, "El ID del cliente debe ser un valor numérico.");
            return;
        }
        r.setIdCliente(Integer.parseInt(idClienteStr));

        // Validación del Número de Habitación: solo números
        String numeroHabitacionStr = vistaReserva.txtNumeroHabitacion.getText();
        if (!numeroHabitacionStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(vistaReserva, "El número de habitación debe ser un valor numérico.");
            return;
        }
        r.setNumeroHabitacion(Integer.parseInt(numeroHabitacionStr));

        // Validación de las fechas: comprobar que no sean nulas
        if (vistaReserva.fechaEntrada.getDate() == null) {
            JOptionPane.showMessageDialog(vistaReserva, "La fecha de entrada es obligatoria.");
            return;
        }
        r.setFechaEntrada(vistaReserva.fechaEntrada.getDate());

        if (vistaReserva.fechaSalida.getDate() == null) {
            JOptionPane.showMessageDialog(vistaReserva, "La fecha de salida es obligatoria.");
            return;
        }
        r.setFechaSalida(vistaReserva.fechaSalida.getDate());

        // Asegurarse de que la fecha de salida sea posterior a la fecha de entrada
        if (r.getFechaSalida().before(r.getFechaEntrada())) {
            JOptionPane.showMessageDialog(vistaReserva, "La fecha de salida debe ser posterior a la fecha de entrada.");
            return;
        }

        // Si todas las validaciones son exitosas, se procede a agregar la reserva
        int resultado = reservaDAO.agregarReserva(r);
        if (resultado == 1) {
            JOptionPane.showMessageDialog(vistaReserva, "Reserva agregada con éxito");
            listarReservas(vistaReserva.tablaReserva);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaReserva, "Error al agregar reserva");
        }
    }
    
    private void actualizarReserva() {
        int fila = vistaReserva.tablaReserva.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaReserva, "Debe seleccionar una reserva");
            return;
        }

        r = new Reserva();
        r.setId(Integer.parseInt(vistaReserva.tablaReserva.getValueAt(fila, 0).toString()));

        // Validación del ID del Cliente: solo números
        String idClienteStr = vistaReserva.txtIdCliente.getText();
        if (!idClienteStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(vistaReserva, "El ID del cliente debe ser un valor numérico.");
            return;
        }
        r.setIdCliente(Integer.parseInt(idClienteStr));

        // Validación del Número de Habitación: solo números
        String numeroHabitacionStr = vistaReserva.txtNumeroHabitacion.getText();
        if (!numeroHabitacionStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(vistaReserva, "El número de habitación debe ser un valor numérico.");
            return;
        }
        r.setNumeroHabitacion(Integer.parseInt(numeroHabitacionStr));

        // Validación de las fechas: comprobar que no sean nulas
        if (vistaReserva.fechaEntrada.getDate() == null) {
            JOptionPane.showMessageDialog(vistaReserva, "La fecha de entrada es obligatoria.");
            return;
        }
        r.setFechaEntrada(vistaReserva.fechaEntrada.getDate());

        if (vistaReserva.fechaSalida.getDate() == null) {
            JOptionPane.showMessageDialog(vistaReserva, "La fecha de salida es obligatoria.");
            return;
        }
        r.setFechaSalida(vistaReserva.fechaSalida.getDate());

        // Asegurarse de que la fecha de salida sea posterior a la fecha de entrada
        if (r.getFechaSalida().before(r.getFechaEntrada())) {
            JOptionPane.showMessageDialog(vistaReserva, "La fecha de salida debe ser posterior a la fecha de entrada.");
            return;
        }

        // Si todas las validaciones son exitosas, se procede a actualizar la reserva
        int resultado = reservaDAO.actualizarReserva(r);
        if (resultado == 1) {
            JOptionPane.showMessageDialog(vistaReserva, "Reserva actualizada con éxito");
            listarReservas(vistaReserva.tablaReserva);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaReserva, "Error al actualizar reserva");
        }
    }

    private void eliminarReserva() {
        int fila = vistaReserva.tablaReserva.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaReserva, "Debe seleccionar una reserva");
            return;
        }
        
        int id = Integer.parseInt(vistaReserva.tablaReserva.getValueAt(fila, 0).toString());
        int resultado = reservaDAO.eliminarReserva(id);
        if (resultado == 1) {
            JOptionPane.showMessageDialog(vistaReserva, "Reserva eliminada con éxito");
            listarReservas(vistaReserva.tablaReserva);
        } else {
            JOptionPane.showMessageDialog(vistaReserva, "Error al eliminar reserva");
        }
    }
        
    private void listarReservas(JTable tablaReserva) {
        DefaultTableModel modelo = (DefaultTableModel) tablaReserva.getModel();
        modelo.setRowCount(0);
        List<Reserva> listaReservas = reservaDAO.obtenerReservas();
        for (Reserva reserva : listaReservas) {
            Object[] fila = new Object[5];
            fila[0] = reserva.getId();
            fila[1] = reserva.getIdCliente();
            fila[2] = reserva.getNumeroHabitacion();
            fila[3] = reserva.getFechaEntrada();
            fila[4] = reserva.getFechaSalida();
            modelo.addRow(fila);
        }
    }
    
    private void limpiarCampos() {
        vistaReserva.txtIdCliente.setText("");
        vistaReserva.txtNumeroHabitacion.setText("");
        vistaReserva.fechaEntrada.setDate(null);
        vistaReserva.fechaSalida.setDate(null);
    }
}
