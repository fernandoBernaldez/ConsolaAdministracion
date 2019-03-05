
import consolaAdministracion.controlador.ControladorAS;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
public class HiloServidor extends Thread {

    ServerSocket servidor;
    boolean parar;
    ControladorAS cas;
    public HiloServidor(ControladorAS cas) throws IOException {
        this.servidor = new ServerSocket(6000);
        parar = false;
        this.cas = cas;
    }

    @Override
    public void run() {

        try {
            while (!parar) {
                Socket cliente = new Socket();
                cliente = servidor.accept();
                System.out.println("cliente conectado");
                HiloCliente hilo = new HiloCliente(cliente,cas);
                hilo.start();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Parar(boolean parar) {
        this.parar = parar;
    }
}
