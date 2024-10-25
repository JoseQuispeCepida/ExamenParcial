package Modelo;

import java.util.Date;

public class Reserva {
    private int id;
    private int idCliente;
    private int numeroHabitacion;
    private Date fechaEntrada;
    private Date fechaSalida;

    public Reserva() {
    }

    public Reserva(int id, int idCliente, int numeroHabitacion, Date fechaEntrada, Date fechaSalida) {
        this.id = id;
        this.idCliente = idCliente;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    @Override
    public String toString() {
        return "Reserva{" + "id=" + id + ", idCliente=" + idCliente + ", numeroHabitacion=" + numeroHabitacion + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida + '}';
    }
}