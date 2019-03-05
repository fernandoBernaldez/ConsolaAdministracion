
import consolaAdministracion.controlador.ControladorAR2;
import consolaAdministracion.modelo.C_Ticket;
import consolaAdministracion.modelo.Modelo;
import consolaAdministracion.vista.ConsolaIdentificacionAReservas;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fernando
 */
public class AdminReserva {

    public static void main(String[] args) {

        ControladorAR2 car = new ControladorAR2(new Modelo());
        ConsolaIdentificacionAReservas ciar = new ConsolaIdentificacionAReservas(car);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ciar.setVisible(true);
            }
        });
    }
}
