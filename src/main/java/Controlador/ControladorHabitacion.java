package Controlador;

import Modelo.Habitacion;
import Modelo.HabitacionDAO;
import Vista.VistaHabitacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorHabitacion implements ActionListener{
    
    private VistaHabitacion vistaHabitacion; 
    private HabitacionDAO habitacionDAO;
    Habitacion h = new Habitacion();

    public ControladorHabitacion(VistaHabitacion v){
        this.vistaHabitacion = v;
        this.habitacionDAO = new HabitacionDAO();
        this.vistaHabitacion.btnListar.addActionListener(this);
        this.vistaHabitacion.btnAgregar.addActionListener(this);
        this.vistaHabitacion.btnActualizar.addActionListener(this);
        this.vistaHabitacion.btnEliminar.addActionListener(this);
        listarHabitaciones(vistaHabitacion.tablaHabitacion);  
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaHabitacion.btnListar) {
            listarHabitaciones(vistaHabitacion.tablaHabitacion);
        } else if (e.getSource() == vistaHabitacion.btnAgregar) {
            agregarHabitacion();
        } else if (e.getSource() == vistaHabitacion.btnActualizar) {
            actualizarHabitacion();
        } else if (e.getSource() == vistaHabitacion.btnEliminar) {
            eliminarHabitacion();
        }
    }

    private void agregarHabitacion() {
        h = new Habitacion();

        // Validación del número: solo números
        String numeroStr = vistaHabitacion.txtNumero.getText();
        if (!numeroStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(vistaHabitacion, "El número de habitación debe ser un valor numérico.");
            return;
        }
        h.setNumero(Integer.parseInt(numeroStr));

        // Validación del tipo: solo letras
        String tipo = vistaHabitacion.txtTipo.getText();
        if (!tipo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(vistaHabitacion, "El tipo de habitación solo debe contener letras.");
            return;
        }
        h.setTipo(tipo);

        // Validación del precio: solo números
        String precioStr = vistaHabitacion.txtPrecio.getText();
        if (!precioStr.matches("\\d+(\\.\\d{1,2})?")) { // Permite números enteros y decimales con hasta 2 decimales
            JOptionPane.showMessageDialog(vistaHabitacion, "El precio debe ser un valor numérico de hasta 2 decimales.");
            return;
        }
        h.setPrecio(Double.parseDouble(precioStr));

        // Si todas las validaciones son exitosas, se procede a agregar la habitación
        int r = habitacionDAO.agregarHabitacion(h);
        if (r == 1) {
            JOptionPane.showMessageDialog(vistaHabitacion, "Habitación agregada con éxito");
            listarHabitaciones(vistaHabitacion.tablaHabitacion);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaHabitacion, "Error al agregar habitación");
        }
    }   

    private void actualizarHabitacion() {
        // Validación del número: solo números
        String numeroStr = vistaHabitacion.txtNumero.getText();
        if (!numeroStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(vistaHabitacion, "El número de habitación debe ser un valor numérico.");
            return;
        }
        h.setNumero(Integer.parseInt(numeroStr));

        // Validación del tipo: solo letras
        String tipo = vistaHabitacion.txtTipo.getText();
        if (!tipo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(vistaHabitacion, "El tipo de habitación solo debe contener letras.");
            return;
        }
        h.setTipo(tipo);

        // Validación del precio: solo números
        String precioStr = vistaHabitacion.txtPrecio.getText();
        if (!precioStr.matches("\\d+(\\.\\d{1,2})?")) { // Permite números enteros y decimales con hasta 2 decimales
            JOptionPane.showMessageDialog(vistaHabitacion, "El precio debe ser un valor numérico válido con hasta 2 decimales.");
            return;
        }
        h.setPrecio(Double.parseDouble(precioStr));

        // Si todas las validaciones son exitosas, se procede a actualizar la habitación
        int r = habitacionDAO.actualizarHabitacion(h);
        if (r == 1) {
            JOptionPane.showMessageDialog(vistaHabitacion, "Habitación actualizada con éxito");
            listarHabitaciones(vistaHabitacion.tablaHabitacion);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaHabitacion, "Error al actualizar habitación");
        }
    }

    public void eliminarHabitacion() {
        int fila = vistaHabitacion.tablaHabitacion.getSelectedRow();
    
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaHabitacion, "Debe seleccionar una habitación");
        } else {
            int numero = Integer.parseInt(vistaHabitacion.tablaHabitacion.getValueAt(fila, 0).toString());
            habitacionDAO.eliminarHabitacion(numero);
            JOptionPane.showMessageDialog(vistaHabitacion, "Habitación eliminada con éxito");
            listarHabitaciones(vistaHabitacion.tablaHabitacion);
        }
    }

    private void listarHabitaciones(JTable tablaHabitacion) {
        List<Habitacion> habitaciones = habitacionDAO.obtenerHabitaciones();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Número", "Tipo", "Precio"}, 0);
        
        for (Habitacion habitacion : habitaciones) {
            model.addRow(new Object[]{habitacion.getNumero(), habitacion.getTipo(), habitacion.getPrecio()});
        }
        vistaHabitacion.tablaHabitacion.setModel(model);
    }
    
    private void limpiarCampos() {
        vistaHabitacion.txtNumero.setText("");
        vistaHabitacion.txtTipo.setText("");
        vistaHabitacion.txtPrecio.setText("");
    }
}
