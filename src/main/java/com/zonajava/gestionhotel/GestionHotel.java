package com.zonajava.gestionhotel;

import Modelo.Conexion;
import Vista.VistaCliente;

public class GestionHotel {

    public static void main(String[] args) {
        Conexion conexion = new Conexion();
       
        VistaCliente vistaCliente = new VistaCliente();
        vistaCliente.setVisible(true);        
        conexion.cerrarConexion();
        
    }
}
