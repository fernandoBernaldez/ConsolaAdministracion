/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaAdministracion.controlador;

import consolaAdministracion.modelo.C_Caso;
import consolaAdministracion.modelo.C_Ticket;
import consolaAdministracion.modelo.Modelo;
import consolaAdministracion.vista.ConsolaASistema;
import consolaAdministracion.vista.ConsolaCreacionAReservas;
import consolaAdministracion.vista.ConsolaTicketsAS;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class ControladorAS implements ActionListener, ItemListener {

    public ConsolaASistema cas;
    public ConsolaTicketsAS ct;
    public ConsolaCreacionAReservas ciar;
    public Modelo cm;

    String nombreA = "";//identificador del administrador
    String Admins = "";//lista de admins
    String Casos = "";//lista de casos del administrador
    String Tickets = "";//lista de tickets de administrador
    C_Caso caso = null;//objeto C_Caso auxiliar
    C_Ticket ticket = null;//objeto C_Ticket auxiliar

    public ControladorAS(Modelo cm) {
        this.cm = cm;
    }

    public void EstablecerVentanas(ConsolaTicketsAS ct) {
        this.ct = ct;
    }

    public void EstablecerVentanas(ConsolaASistema cas) {
        this.cas = cas;

        if (cm.leerLinea("contenedor\\GestorTicketsPendientes.txt") == null) {//si no existe el GestorCasos no existe el usuario
            cas.setJComboBox1(new String[]{"No hay administrador"});//se indica que no hay casos
            cas.setJComboBox2(new String[]{"No hay casos"});//se indica que no hay casos
            cas.setJComboBox3(new String[]{"No hay Tickets"});//se indica que no hay tickets
            cas.setjTextArea1("No hay descripcion");//se indica que no hay descripcion disponible
            cas.setjButton1(false);//se bloque la opcion de continuar el caso
            cas.setjButton2(false);//se bloquea la opcion de lectura
            Admins = "No hay admins";
            Casos = "No hay casos";
            Tickets = "No hay Tickets";
            try {
                this.cm = cm;
                String carpeta = "contenedor";
                File contenedor = new File(carpeta);
                File carpetaA = new File(carpeta + "\\ar_No hay administrador\\caso1");
                File GestorTicketsPendientes = new File(carpeta + "\\GestorTicketsPendientes.txt");
                File GestorCasos = new File(carpeta + "\\ar_No hay administrador\\GestorCasos.txt");
                File ticketaux = new File(carpeta + "\\ar_No hay administrador\\caso1\\ticket1.txt");
                carpetaA.mkdirs();
                GestorTicketsPendientes.createNewFile();
                GestorCasos.createNewFile();
                ticketaux.createNewFile();
                cm.escribirLinea("contenedor\\GestorTicketsPendientes.txt", "No hay administrador:1");
                cm.escribirLinea("contenedor\\ar_No hay administrador\\GestorCasos.txt", "1:1:Pendiente");
                cm.escribirObjeto("contenedor\\ar_No hay administrador\\caso1\\ticket1.txt", new C_Ticket(1, 1, "No hay administrador", "no hay administrador", "No hay asunto"));
            } catch (IOException ex) {
                Logger.getLogger(ControladorAS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (cm.leerLineaLinea("contenedor\\GestorTicketsPendientes.txt").size() > 1) {
            ListaAdmin();
            cas.setJComboBox1(Admins.split(";"));//se indica que no hay casos
            ListaCasos(Admins.split(";")[0]);

            cas.setJComboBox2(Casos.split(";"));//se indica que no hay casos
            ListaTickets(Integer.valueOf(Casos.split(";")[0]));
            cas.setJComboBox3(Tickets.split(";"));

            System.out.println("contenedor\\ar_" + cas.getJComboBox1Selected() + "\\" + "caso" + cas.getJComboBox2Selected() + "\\ticket1.txt");
            C_Ticket ticket1 = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + cas.getJComboBox1Selected() + "\\" + "caso" + cas.getJComboBox2Selected() + "\\ticket1.txt");//guardo un objeto ticket
            cas.setjTextArea1(ticket1.getDescripcion());//muestro la descripcion de ese ticket}

        } else {
            cas.setJComboBox1(new String[]{"No hay administrador"});//se indica que no hay casos
            cas.setJComboBox2(new String[]{"No hay casos"});//se indica que no hay casos
            cas.setJComboBox3(new String[]{"No hay Tickets"});//se indica que no hay tickets
            cas.setjTextArea1("No hay descripcion");//se indica que no hay descripcion disponible
            cas.setjButton1(false);//se bloque la opcion de continuar el caso
            cas.setjButton2(false);//se bloquea la opcion de lectura
            Admins = "No hay admins";
            Casos = "No hay casos";
            Tickets = "No hay Tickets";
        }
    }

    public void EstablecerVentanas(ConsolaCreacionAReservas ciar) {
        this.ciar = ciar;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String nombre = ae.getActionCommand();//se guarda el nombre del boton de se ha accionado
        if (nombre.equals("Aceptar")) {//si es el boton llamado Aceptar
            nombreA = ciar.getJtextfield1();//se guarda el nombre del nuevo administrador de reservas
            /*se preparan las carpetas que pueda necesitar el nuevo administrador*/
            String carpeta = "contenedor";
            File contenedor = new File(carpeta);
            File carpetaA = new File(carpeta + "\\ar_" + nombreA);
            File GestorTicketsPendientes = new File(carpeta + "\\GestorTicketsPendientes.txt");
            File GestorCasos = new File(carpeta + "\\ar_" + nombreA + "\\GestorCasos.txt");
            /*--------------------------------------------------------------------------------*/
            if (GestorCasos.exists()) {//si ya existe este fichero quiere decir que ya existe el administrador
                ciar.setJtextfield1("Ya existe este administrador");//lo notifico
            } else if (nombreA.equals("No hay administrador") || nombreA.equals("Ya existe este administrador") || nombreA.equals("Por favor ponga otro nombre") || nombreA.equals("Error al crear el administrador") || nombreA.equals("No existe este administrador") || nombreA.equals("") || nombreA == null) {//si pone este texto por defecto
                ciar.setJtextfield1("Por favor ponga otro nombre");//lo notifico
            } else {//si no existe el administrador hago lo siguiente
                try {
                    if (GestorTicketsPendientes.exists()) {//si existe la carpeta contenedor
                        carpetaA.mkdirs();//creo la carpeta del administrador
                        GestorCasos.createNewFile();// y creo el archivo GestorCasos
                    } else {//si no existe
                        contenedor.mkdirs();//creo la carpeta contenedor
                        GestorTicketsPendientes.createNewFile();//creo el archivo GestorTicketsPendientes
                        carpetaA.mkdirs();//creo la carpeta del administrador
                        GestorCasos.createNewFile();// y creo el archivo GestorCasos
                    }

                    cas.setVisible(true);//la abro
                    ciar.dispose();//cierro la vista de inicio de sesion
                } catch (IOException e) {
                    ciar.setJtextfield1("Error al crear el administrador");
                }
            }
        }
        if (nombre.equals("Responder")) {//si es el boton llamado Responder

            caso = new C_Caso((Integer.valueOf((String) cas.getJComboBox2Selected())), cas.getJComboBox1Selected().toString(), "Abierto");//creo instancio una variable caso con una id igual a 1

            /*perparo una variable ticket*/
            ct = new ConsolaTicketsAS(this);
            ct.setjTextField1(LocalDate.now().toString());
            ct.setJComboBox2V(false);
            ct.setVisible(true);
            /*--------------------------------*/
        }

        if (nombre.equals("Admin nuevo")) {//si es el boton llamado Continuar Caso
            ciar = new ConsolaCreacionAReservas(this);
            ciar.setVisible(true);
        }
        if (nombre.equals("Leer")) {//si es el boton llamado Leer Ticke
            C_Ticket ticket1 = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + cas.getJComboBox1Selected() + "\\" + "caso" + cas.getJComboBox2Selected() + "\\ticket" + cas.getJComboBox3Selected() + ".txt");//guardo un objeto ticket
            cas.setjTextArea1(ticket1.getDescripcion());//muestro la descripcion de ese ticket
        }
        if (nombre.equals("OK")) {//si es el boton llamado OK
            System.out.println("contenedor\\ar_" + caso.getAdminId() + "\\caso" + caso.getId() + "\\ticket" + (Integer.valueOf((String) cas.getJComboBox3Selected()) + 1) + ".txt");
            try {//intentar crear el archivo
                File aux2 = new File("contenedor\\ar_" + caso.getAdminId() + "\\caso" + caso.getId() + "\\ticket" + (Integer.valueOf((String) cas.getJComboBox3Selected()) + 1) + ".txt");
                aux2.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ControladorAR2.class.getName()).log(Level.SEVERE, null, ex);
            }
            int a = Integer.valueOf(cas.getJComboBox3Selected().toString());
            ticket = new C_Ticket((a + 1), Integer.valueOf(cas.getJComboBox2Selected().toString()), (String) cas.getJComboBox1Selected());
            ticket.setAsunto(ct.getjTextField2());
            ticket.setDescripcion(ct.getjTextArea1());
            ticket.setPrioridad((String) ct.getJComboBox1());
            ticket.setEstado("Abierto");

            cm.escribirObjeto("contenedor\\ar_" + caso.getAdminId() + "\\caso" + ticket.getCasoId() + "\\ticket" + ticket.getId() + ".txt", ticket);

            ArrayList<String> lineas = cm.leerLineaLinea("contenedor" + "\\ar_" + cas.getJComboBox1Selected() + "\\GestorCasos.txt");
            lineas.set(caso.getId() - 1, (caso.getId() + ":" + (Integer.valueOf(cas.getJComboBox3Selected().toString()) + 1) + ":Abierto"));
            cm.escribirLineaLinea("contenedor" + "\\ar_" + cas.getJComboBox1Selected() + "\\GestorCasos.txt", lineas);

            ArrayList<String> leerLineaLinea = cm.leerLineaLinea("contenedor\\GestorTicketsPendientes.txt");
            for (int i = 0; i < leerLineaLinea.size(); i++) {//para cada fila de GestorTicketsPendientes.txt
                if (leerLineaLinea.get(i).split(":")[0].equals(caso.getAdminId())) {//si coincide el nombre del administrador se sustitulle la fila
                    ListaTickets(caso.getId());
                    int numeroT = (Integer.valueOf(leerLineaLinea.get(i).split(":")[1]) - (Tickets.split(";").length));
                    if (numeroT != 0) {//si la resta es distinto de cero se pone el resultado 
                        leerLineaLinea.set(i, nombreA + ":" + numeroT);
                    } else {//si no se elimina lla fila
                        leerLineaLinea.remove(i);
                    }
                }

            }
            cm.escribirLineaLinea("contenedor\\GestorTicketsPendientes.txt", leerLineaLinea);

            ListaAdmin();
            cas.setJComboBox1(Admins.split(";"));
            ListaCasos(Admins.split(";")[0]);
            ListaTickets(Integer.valueOf(Casos.split(";")[0]));
            cas.setJComboBox2(Casos.split(";"));
            cas.setJComboBox3(Tickets.split(";"));
            /*---------
            cas.setJ----------------------------------------------------*/
 /*se carga el ticket y/o caso*/

            C_Ticket ticket1 = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + cas.getJComboBox1Selected() + "\\" + "caso" + cas.getJComboBox2Selected() + "\\ticket" + cas.getJComboBox3Selected() + ".txt");//guardo un objeto ticket
            cas.setjTextArea1(ticket1.getDescripcion());//muestro la descripcion de ese ticket

            ct.dispose();//se cierra la ventana de creacion de tickets
            ct = new ConsolaTicketsAS(this);//se crea otra pero no se muestra
        }
        if (nombre.equals("Cancelar")) {//si es el boton llamado Cancelar
            ct.dispose();//se cierra la ventana de creacion de tickets
            ct = new ConsolaTicketsAS(this);//se crea otra pero no se muestra
        }
        if (nombre.equals("Refrescar")) {
            if (cm.leerLinea("contenedor\\GestorTicketsPendientes.txt") == null) {//si no existe el GestorCasos no existe el usuario
                cas.setJComboBox1(new String[]{"No hay administrador"});//se indica que no hay casos
                cas.setJComboBox2(new String[]{"No hay casos"});//se indica que no hay casos
                cas.setJComboBox3(new String[]{"No hay Tickets"});//se indica que no hay tickets
                cas.setjTextArea1("No hay descripcion");//se indica que no hay descripcion disponible
                cas.setjButton1(false);//se bloque la opcion de continuar el caso
                cas.setjButton2(false);//se bloquea la opcion de lectura
                Admins = "No hay admins";
                Casos = "No hay casos";
                Tickets = "No hay Tickets";
                try {
                    this.cm = cm;
                    String carpeta = "contenedor";
                    File contenedor = new File(carpeta);
                    File carpetaA = new File(carpeta + "\\ar_No hay administrador\\caso1");
                    File GestorTicketsPendientes = new File(carpeta + "\\GestorTicketsPendientes.txt");
                    File GestorCasos = new File(carpeta + "\\ar_No hay administrador\\GestorCasos.txt");
                    File ticketaux = new File(carpeta + "\\ar_No hay administrador\\caso1\\ticket1.txt");
                    carpetaA.mkdirs();
                    GestorTicketsPendientes.createNewFile();
                    GestorCasos.createNewFile();
                    ticketaux.createNewFile();
                    cm.escribirLinea("contenedor\\GestorTicketsPendientes.txt", "No hay administrador:1");
                    cm.escribirLinea("contenedor\\ar_No hay administrador\\GestorCasos.txt", "1:1:Pendiente");
                    cm.escribirObjeto("contenedor\\ar_No hay administrador\\caso1\\ticket1.txt", new C_Ticket(1, 1, "No hay administrador", "no hay administrador", "No hay asunto"));
                } catch (IOException ex) {
                    Logger.getLogger(ControladorAS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cm.leerLineaLinea("contenedor\\GestorTicketsPendientes.txt").size() > 1) {
                ListaAdmin();
                cas.setJComboBox1(Admins.split(";"));//se indica que no hay casos
                ListaCasos(Admins.split(";")[0]);

                cas.setJComboBox2(Casos.split(";"));//se indica que no hay casos
                ListaTickets(Integer.valueOf(Casos.split(";")[0]));
                cas.setJComboBox3(Tickets.split(";"));

                System.out.println("contenedor\\ar_" + cas.getJComboBox1Selected() + "\\" + "caso" + cas.getJComboBox2Selected() + "\\ticket1.txt");
                C_Ticket ticket1 = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + cas.getJComboBox1Selected() + "\\" + "caso" + cas.getJComboBox2Selected() + "\\ticket1.txt");//guardo un objeto ticket
                cas.setjTextArea1(ticket1.getDescripcion());//muestro la descripcion de ese ticket}
                
                cas.setjButton1(true);//se abre la opcion de continuar el caso
                cas.setjButton2(true);//se abre la opcion de lectura

            } else {
                cas.setJComboBox1(new String[]{"No hay administrador"});//se indica que no hay casos
                cas.setJComboBox2(new String[]{"No hay casos"});//se indica que no hay casos
                cas.setJComboBox3(new String[]{"No hay Tickets"});//se indica que no hay tickets
                cas.setjTextArea1("No hay descripcion");//se indica que no hay descripcion disponible
                cas.setjButton1(false);//se bloque la opcion de continuar el caso
                cas.setjButton2(false);//se bloquea la opcion de lectura
                Admins = "No hay admins";
                Casos = "No hay casos";
                Tickets = "No hay Tickets";
            }
        }

    }

    /**
     * <h1>Esta funcion capta el item seleccionado del jCombobox1 de la vista
     * principal</h1>
     *
     * @param ie captura la accion de seleccion de un item
     */
    @Override
    public void itemStateChanged(ItemEvent ie) {
        for (int i = 0; i < Admins.split(";").length; i++) {
            if (Admins.split(";")[i].equals(ie.getItem())) {
                ListaCasos(Admins.split(";")[i]);
                ListaTickets(Integer.valueOf(Casos.split(";")[0]));
                cas.setJComboBox2(Casos.split(";"));
                ListaAdmin();
                if (Admins.split(";")[0].equals("No hay administrador") || Admins.split(";")[0].equals("")) {
                    cas.setjTextArea1("No hay descripcion");//se indica que no hay descripcion disponible
                    cas.setjButton1(false);//se bloque la opcion de continuar el caso
                    cas.setjButton2(false);//se bloquea la opcion de lectura
                }
            }

        }
        for (int i = 0; i < Casos.split(";").length; i++) {
            if (Casos.split(";")[i].equals(ie.getItem())) {
                ListaTickets(i);
                cas.setJComboBox3(Tickets.split(";"));
            }

        }
    }

    public void ListaAdmin() {
        if (cm.leerLineaLinea("contenedor\\GestorTicketsPendientes.txt") != null && !cm.leerLineaLinea("contenedor\\GestorTicketsPendientes.txt").equals("")) {
            ArrayList<String> lineas = cm.leerLineaLinea("contenedor\\GestorTicketsPendientes.txt");
            for (int i = 0; i < lineas.size(); i++) {
                if (i == 0) {
                    Admins = lineas.get(i).split(":")[0];
                } else if (i == 1) {
                    Admins = lineas.get(i).split(":")[0];
                } else {

                    Admins = Admins + ";" + lineas.get(i).split(":")[0];
                }
            }
        } else {
            Admins = "No hay administrador";
        }
    }

    public void ListaCasos(String nombreA) {
        if (cm.leerLineaLinea("contenedor\\ar_" + nombreA + "\\GestorCasos.txt") != null) {
            ArrayList<String> lineas = cm.leerLineaLinea("contenedor\\ar_" + nombreA + "\\GestorCasos.txt");
            int aux = 0;
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).split(":")[2].equals("Pendiente")) {
                    if (aux == 0) {
                        Casos = "" + (i + 1);
                    } else {
                        Casos = Casos + ";" + (i + 1);
                    }
                    aux++;

                }
            }
        } else {
            Casos = "No hay casos";
        }
    }

    public void ListaTickets(int caso) {
        if (cm.leerLineaLinea("contenedor\\ar_" + cas.getJComboBox1Selected() + "\\GestorCasos.txt") != null) {
            ArrayList<String> lineas = cm.leerLineaLinea("contenedor\\ar_" + cas.getJComboBox1Selected() + "\\GestorCasos.txt");
            for (int i = 0; i < lineas.size(); i++) {
                if (i == caso - 1) {
                    for (int j = 0; j < lineas.get(i).split(":")[1].length(); j++) {
                        if (j == 0) {
                            Tickets = "" + (j + 1);
                        } else {
                            Tickets = Tickets + ";" + (j + 1);
                        }
                        System.out.println("consolaAdministracion.controlador.ControladorAS.ListaTickets()");
                    }
                }
            }
        } else {
            Tickets = "No hay Tickets;\"\"";
        }
    }

}
