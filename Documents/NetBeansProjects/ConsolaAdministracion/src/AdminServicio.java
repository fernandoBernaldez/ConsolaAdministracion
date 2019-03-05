
import consolaAdministracion.controlador.ControladorAS;
import consolaAdministracion.modelo.Modelo;
import consolaAdministracion.vista.ConsolaASistema;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fernando
 */
public class AdminServicio {

    public static void main(String[] args) {

        try {
            
            ControladorAS cas = new ControladorAS(new Modelo());
            HiloServidor h1 = new HiloServidor(cas);
            h1.start();
            ConsolaASistema car = new ConsolaASistema(cas);
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    car.setVisible(true);
                }
            });
        } catch (IOException ex) {
            System.err.println(ex.getMessage() + " por: " + ex.getCause() + " en " + ex.getLocalizedMessage());
        }
    }
}
