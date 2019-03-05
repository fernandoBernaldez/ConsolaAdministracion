package consolaAdministracion.modelo;


import consolaAdministracion.controlador.ControladorAR2;
import consolaAdministracion.modelo.C_Admin;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class HiloUsuario extends Thread{
    Socket usuario;
    ObjectOutputStream fsalida;
    ObjectInputStream fentrada;
    Object auxiliar;
    ControladorAR2 car2;
    String con;

    public HiloUsuario() throws IOException {
        this.usuario = new Socket("localhost",6000);
        fsalida = new ObjectOutputStream(usuario.getOutputStream());
        fentrada = new ObjectInputStream(usuario.getInputStream());
    }

    public HiloUsuario(ControladorAR2 car2,Object c_Admin) throws IOException {
        this.usuario = new Socket("localhost",6000);
        fsalida = new ObjectOutputStream(usuario.getOutputStream());
        fentrada = new ObjectInputStream(usuario.getInputStream());
        auxiliar = c_Admin;
        this.car2 = car2;
    }
    public void Immprimir(String a){
        con = a;
    }
    @Override
    public void run() {
        try {
            fsalida.reset();
            fsalida.writeObject(auxiliar);
            Thread.sleep(100);
            Object auxiliar = fentrada.readObject();
            if(auxiliar != null){
                car2.devolver(true);
            }else{
                car2.devolver(false);
            }
            car2.CuentaCT(1);
        } catch (IOException ex) {
            Logger.getLogger(HiloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HiloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(HiloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String getCon() {
        return con;
    }
    
}
