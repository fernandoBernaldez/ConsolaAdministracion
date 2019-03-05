
import consolaAdministracion.controlador.ControladorAS;
import consolaAdministracion.modelo.C_Admin;
import consolaAdministracion.modelo.C_Ticket;
import consolaAdministracion.modelo.Modelo;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
public class HiloCliente extends Thread {

    ObjectInputStream fentrada;
    ObjectOutputStream fsalida;
    Socket socket = null;
    Object auxiliar;
    Modelo cm;
    String con;
    ControladorAS cas;

    public HiloCliente(Socket socket, ControladorAS cas) {
        try {
            this.socket = socket;
            fsalida = new ObjectOutputStream(socket.getOutputStream());
            fentrada = new ObjectInputStream(socket.getInputStream());
            cm = new Modelo();
            this.cas = cas;
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Immprimir(String a) {
        con = a;
    }

    @Override
    public void run() {
        try {

            auxiliar = fentrada.readObject();
            Immprimir(auxiliar.getClass().toString());
            if (auxiliar.getClass().equals(C_Admin.class)) {
                C_Admin ad = (C_Admin) auxiliar;
                ArrayList<String> lineas = cm.leerLineaLinea("contenedor\\ar_" + ad.getAdmin() + "\\GestorCasos.txt");
                if (lineas != null) {
                    System.out.println("no es nulo");
                    fsalida.reset();
                    Object aux = new C_Admin(ad.getAdmin());
                    fsalida.writeObject(aux);
                } else {
                    fsalida.reset();
                    fsalida.writeObject(null);
                }
            } else if (auxiliar.getClass().equals(C_Ticket.class)) {
                C_Ticket aux2 = (C_Ticket) auxiliar;
                String nombreA = aux2.getAdminId();
                ArrayList<String> leerLineaLinea1 = cm.leerLineaLinea("contenedor" + "\\" + nombreA + "\\GestorCasos.txt");

                File carpetaC = new File("contenedor" + "\\" + nombreA + "\\caso" + aux2.getCasoId());//se crea un nuevo caso
                if (!carpetaC.exists()) {
                    carpetaC.mkdirs();

                }
                if (!leerLineaLinea1.isEmpty() && leerLineaLinea1.size() > aux2.getCasoId()) {
                    aux2.setId(Integer.valueOf(leerLineaLinea1.get(aux2.getCasoId() - 1)
                            .split(":")[1]) + 1);
                }else if(!leerLineaLinea1.isEmpty()){
                    aux2.setId(Integer.valueOf(leerLineaLinea1.get(leerLineaLinea1.size()-1).split(":")[1])+1);
                }
                if (!(leerLineaLinea1.size() >= aux2.getCasoId())){
                    aux2.setId(1);
                }
                File aux3 = new File("contenedor" + "\\" + nombreA + "\\caso" + aux2.getCasoId() + "\\ticket" + aux2.getId() + ".txt");
                aux3.createNewFile();

                if (!leerLineaLinea1.isEmpty()) {
                    if (leerLineaLinea1.size() >= aux2.getCasoId()) {
                        leerLineaLinea1.set(aux2.getCasoId() - 1, (aux2.getCasoId() + ":" + (Integer.valueOf(leerLineaLinea1.get(aux2.getCasoId() - 1).split(":")[1]) + 1) + ":" + aux2.getEstado()));
                        cm.escribirLineaLinea("contenedor" + "\\" + nombreA + "\\GestorCasos.txt", leerLineaLinea1);
                    } else {
                        cm.escribirLinea("contenedor" + "\\" + nombreA + "\\GestorCasos.txt", (aux2.getCasoId() + ":" + 1 + ":" + aux2.getEstado()));
                    }

                } else {
                    cm.escribirLinea("contenedor" + "\\" + nombreA + "\\GestorCasos.txt", (aux2.getCasoId() + ":" + 1 + ":" + aux2.getEstado()));
                }

                ArrayList<String> leerLineaLinea = cm.leerLineaLinea("contenedor\\GestorTicketsPendientes.txt");
                boolean aux = true;
                for (int i = 0; i < leerLineaLinea.size(); i++) {//para cada fila de GestorTicketsPendientes.txt
                    if (leerLineaLinea.get(i).split(":")[0].equals(aux2.getAdminId().substring(3))) {//si coincide el nombre del administrador se sustitulle la fila
                        int numTickets = Integer.valueOf(leerLineaLinea.get(i).split(":")[1]) + 1;
                        leerLineaLinea.set(i, aux2.getAdminId().substring(3) + ":" + numTickets);
                        aux = false;
                    }

                }
                if (aux) {
                    leerLineaLinea.add(aux2.getAdminId().substring(3) + ":1");//si no se ha sustituido se pone como la primera vez
                }
                cm.escribirLineaLinea("contenedor\\GestorTicketsPendientes.txt", leerLineaLinea);//se escibe las filas

                if (aux2.getEstado().equals("Cerrado")) {//si el ticket esta Cerrado se restan todos los tickets del caso al su fila en GestorTicketsPendientes.txt
                    leerLineaLinea = cm.leerLineaLinea("contenedor\\GestorTicketsPendientes.txt");
                    for (int i = 0; i < leerLineaLinea.size(); i++) {//para cada fila de GestorTicketsPendientes.txt
                        if (leerLineaLinea.get(i).split(":")[0].equals(nombreA)) {//si coincide el nombre del administrador se sustitulle la fila

                            ArrayList<String> get = cm.leerLineaLinea("contenedor\\" + aux2.getAdminId().substring(3) + "\\GestorCasos.txt");
                            int numeroT = (Integer.valueOf(leerLineaLinea.get(i).split(":")[1]) - Integer.valueOf(get.get(i).split(":")[1].toString()));
                            if (numeroT != 0) {//si la resta es distinto de cero se pone el resultado 
                                leerLineaLinea.set(i, nombreA + ":" + numeroT);
                            } else {//si no se elimina lla fila
                                leerLineaLinea.remove(i);
                            }
                        }

                    }
                    cm.escribirLineaLinea("contenedor\\GestorTicketsPendientes.txt", leerLineaLinea);//se ecriben todas las filas
                }

                cm.escribirObjeto("contenedor\\" + aux2.getAdminId() + "\\caso" + aux2.getCasoId() + "\\ticket" + aux2.getId() + ".txt", aux2);
            }
            cas.ListaAdmin();

        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCon() {
        return con;
    }

}
