/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaAdministracion.controlador;

import consolaAdministracion.vista.*;
import consolaAdministracion.modelo.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class ControladorAR2 implements ActionListener, Serializable, ItemListener {

    /**
     * <p>
     * vista principal del administrador de reservas</p>
     */
    public ConsolaAReservas car;

    /**
     * <p>
     * vista de inicio de sesion</p>
     */
    public ConsolaIdentificacionAReservas ciar;

    /**
     * <p>
     * vista de creacion de tickets</p>
     */
    public ConsolaTicketsAR ct;

    /**
     * <p>
     * modelo para manejar archivos</p>
     */
    public Modelo cm;

    String nombreA = "";//identificador del administrador
    String Casos = "";//lista de casos del administrado
    String Tickets = "";//lista de tickets de administrador
    C_Caso caso = null;//objeto C_Caso auxiliar
    C_Ticket ticket = null;//objeto C_Ticket auxiliar
    boolean existe = false;

    /**
     * <h1>Aqui se instancia el modelo</h1>
     *
     * @param cm es el modelo del controlador
     */
    public ControladorAR2(Modelo cm) {
        this.cm = cm;
    }

    /**
     * <h1>Aqui se instancia la vista principal del administrador de reserv</h1>
     *
     * @param car es la vista principal del administrador de reserva
     */
    public void EstablecerVentanas(ConsolaAReservas car) {
        this.car = car;

        if (cm.leerLinea("contenedor\\ar_" + nombreA + "\\GestorCasos.txt") == null) {//si no existe el GestorCasos no existe el usuario
            car.setJComboBox1(new String[]{"No hay casos"});//se indica que no hay casos
            car.setJComboBox2(new String[]{"No hay Tickets"});//se indica que no hay tickets
            car.setjTextArea1("No hay descripcion");//se indica que no hay descripcion disponible
            car.setjButtonNC(false);//se bloque la opcion de continuar el caso
            car.setjButtonL(false);//se bloquea la opcion de lectura
            Casos = "No hay casos";
            Tickets = "No hay Tickets";
        } else {
            ArrayList<String> a = CuentaCT(1);//se lee los casos y los tickets del primer caso
            Casos = a.get(0);//se almacenan los casos
            Tickets = a.get(1);// se almacenan los tickets
            car.setJComboBox1(Casos.split(";"));//se enseñan los casos
            car.setJComboBox2(Tickets.split(";"));//se enseñan los tickets

            ticket = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + nombreA + "\\caso1\\ticket1.txt");//se lee y almacena un ticket
            car.setjTextArea1(ticket.getDescripcion());// se enseña la descripcion del ticket 
            caso = new C_Caso(1, nombreA, cm.leerLineaLinea("contenedor\\ar_" + nombreA + "\\GestorCasos.txt").get(0).split(":")[2]);//se lee y almacena un objeto caso
            if (caso.getEstado().equals("Cerrado")) {//si el objeto caso esta cerrado 
                car.setjButtonNC(false);//se bloquea la opcion de continuar caso
            } else {//si no
                car.setjButtonNC(true);//se habilita
            }

        }

    }

    /**
     * <h1>Aqui se instancia la ventana de creacion de tickets</h1>
     *
     * @param ct es la vista de creacion de tickets
     */
    public void EstablecerVentanas(ConsolaTicketsAR ct) {
        this.ct = ct;
    }

    /**
     * <h1>Aqui se instancia la ventana de inicio de sesion</h1>
     *
     * @param ciar es la vista de inicio de sesion
     */
    public void EstablecerVentanas(ConsolaIdentificacionAReservas ciar) {
        this.ciar = ciar;
    }

    /**
     * <h1>Aqui se controlan los botones de todas las vistas del administrador
     * de reserva</h1>
     *
     * @param ae es el parametro que captura la accion
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        String nombre = ae.getActionCommand();//se guarda el nombre del boton de se ha accionado
        if (nombre.equals("Aceptar")) {
            try {
                //si es el boton llamado Aceptar
                nombreA = ciar.getJtextfield1();//guardo el nombre del administrador que ha iniciado sesion
                /*cargo las direcciones del administrados*/
                HiloUsuario h2 = new HiloUsuario(this, new C_Admin(nombreA));
                h2.start();
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(100);
                }
                h2.interrupt();
                /*---------------------------------------------------*/
                if (existe) {//si existe
                    car = new ConsolaAReservas(this);//creo la consola del administrador de reservas
                    car.setVisible(true);//la abro
                    ciar.dispose();//cierro la ventana de inicio de sesion
                } else {//si no
                    ciar.setJtextfield1("No existe este administrador");//se lo notifico
                }
            } catch (IOException ex) {
                Logger.getLogger(ControladorAR2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControladorAR2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (nombre.equals("Caso Nuevo")) {//si es el boton llamado Caso Nuevo

            if (!Casos.split(";")[0].equals("No hay casos")) {//si existen casos
                caso = new C_Caso((Casos.split(";").length + 1), "ar_" + nombreA, "Pendiente");//creo una instancio una variable caso con su id correspondiente
            } else {//si no
                caso = new C_Caso(1, "ar_" + nombreA, "Pendiente");//creo instancio una variable caso con una id igual a 1
            }
            ticket = new C_Ticket(1, caso.getId(), "ar_" + nombreA);//se instancia un nuevo ticket
            /*perparo una variable ticket*/
            ct = new ConsolaTicketsAR(this);
            ct.setjTextField1(LocalDate.now().toString());
            ct.setJComboBox2V(false);
            car.setjButtonNC(true);
            car.setjButtonL(true);
            ct.setVisible(true);
            /*--------------------------------*/
        }
        if (nombre.equals("Continuar Caso")) {//si es el boton llamado Continuar Caso
            ct = new ConsolaTicketsAR(this);//instancio una vista de cracion de tickets
            ct.setjTextField1(LocalDate.now().toString());//le pongo la fecha actual
            String[] split = cm.leerLineaLinea("contenedor" + "\\ar_" + nombreA + "\\GestorCasos.txt").get(Integer.valueOf(car.getJComboBox1()) - 1).split(":");//variable que guarda la id del ultimo ticket
            int idTicket;//creo una variable auxiliar para guardar la id del ticket
            idTicket = 1 + Integer.valueOf(split[1]);//instancio la variable
            ticket = new C_Ticket(idTicket, Integer.valueOf(car.getJComboBox1()), "ar_" + nombreA);//instanci la variablr ticket
            ct.setVisible(true);//hago visible la ventana de creacion de tickets
        }
        if (nombre.equals("Leer Ticket")) {
            //si es el boton llamado Leer Ticke
            C_Ticket ticket1 = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + nombreA + "\\" + "caso" + car.getJComboBox1Selected() + "\\ticket" + car.getJComboBox2Selected() + ".txt");//guardo un objeto ticket
            car.setjTextArea1(ticket1.getDescripcion());//muestro la descripcion de ese ticket
        }
        if (nombre.equals("OK")) {
            try {
                ticket.setAsunto(ct.getjTextField2());
                ticket.setDescripcion(ct.getjTextArea1());
                ticket.setPrioridad((String) ct.getJComboBox1());
                ticket.setEstado((String) ct.getJComboBox2());

                HiloUsuario h2 = new HiloUsuario(this, ticket);
                h2.start();
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(400);
                }
                h2.interrupt();
                if (existe) {
                    ArrayList<String> a = CuentaCT(ticket.getCasoId());
                    Casos = a.get(0);
                    Tickets = a.get(1);
                    car.setJComboBox1(Casos.split(";"));
                    car.setJComboBox2(Tickets.split(";"));
                    /*-------------------------------------------------------------*/
 /*se carga el ticket y/o caso*/

                    C_Ticket tc = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + nombreA + "\\caso1\\ticket1.txt");//se pone la descripcion del ticket 1 del caso 1
                    car.setjTextArea1(tc.getDescripcion());

                    ct.dispose();//se cierra la ventana de creacion de tickets
                    ct = new ConsolaTicketsAR(this);//se crea otra pero no se muestra
                }
            } catch (IOException ex) {
                Logger.getLogger(ControladorAR2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControladorAR2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (nombre.equals("Cancelar")) {//si es el boton llamado Cancelar
            ct.dispose();//se cierra la ventana de creacion de tickets
            ct = new ConsolaTicketsAR(this);//se crea otra pero no se muestra
        }
        if (nombre.equals("Refrescar")) {
            if (cm.leerLinea("contenedor\\ar_" + nombreA + "\\GestorCasos.txt") == null) {//si no existe el GestorCasos no existe el usuario
                car.setJComboBox1(new String[]{"No hay casos"});//se indica que no hay casos
                car.setJComboBox2(new String[]{"No hay Tickets"});//se indica que no hay tickets
                car.setjTextArea1("No hay descripcion");//se indica que no hay descripcion disponible
                car.setjButtonNC(false);//se bloque la opcion de continuar el caso
                car.setjButtonL(false);//se bloquea la opcion de lectura
                Casos = "No hay casos";
                Tickets = "No hay Tickets";
            } else {
                ArrayList<String> a = CuentaCT(1);//se lee los casos y los tickets del primer caso
                Casos = a.get(0);//se almacenan los casos
                Tickets = a.get(1);// se almacenan los tickets
                car.setJComboBox1(Casos.split(";"));//se enseñan los casos
                car.setJComboBox2(Tickets.split(";"));//se enseñan los tickets

                ticket = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + nombreA + "\\caso1\\ticket1.txt");//se lee y almacena un ticket
                car.setjTextArea1(ticket.getDescripcion());// se enseña la descripcion del ticket 
                caso = new C_Caso(1, nombreA, cm.leerLineaLinea("contenedor\\ar_" + nombreA + "\\GestorCasos.txt").get(0).split(":")[2]);//se lee y almacena un objeto caso
                if (caso.getEstado().equals("Cerrado")) {//si el objeto caso esta cerrado 
                    car.setjButtonNC(false);//se bloquea la opcion de continuar caso
                } else {//si no
                    car.setjButtonNC(true);//se habilita
                }

            }
        }

    }

    /**
     * <h1>Esta funcion te devuelve todos los tickets del cao seleccionado
     * ademas de todos los casos actuales</h1>
     *
     * @param caso es la id del caso del que se dese buscar sus tickets
     * @return devuelve una lista en la primera posicion estan todos los casos y
     * en la segundo todos los tickets del caso seleccionado
     */
    public ArrayList CuentaCT(int caso) {
        Casos = "";
        Tickets = "";
        ArrayList<String> lineas2 = cm.leerLineaLinea("contenedor\\ar_" + nombreA + "\\GestorCasos.txt");
        if (lineas2 != null || lineas2.size() != 0) {//se hayan los casos mediante las lineas que hay en el archivo GestorCasos.txt
            Casos = "1";
            for (int i = 0; i < lineas2.size(); i++) {//aqui se crea la lista de casos
                if (i > 0) {
                    Casos = Casos + ";" + (i + 1);
                }
            }
            for (int j = 0; j < Integer.valueOf(lineas2.get(caso - 1).split(":")[1]); j++) {//se hayan los el numero de ctickets asociado al caso
                if (j == 0) {
                    Tickets = "1";
                }
                if (j > 0) {
                    Tickets = Tickets + ";" + (j + 1);
                }
            }
            /*se devuelve la lista de casos y la de tickets*/
            ArrayList<String> a = new ArrayList<>();
            a.add(Casos);
            a.add(Tickets);
            return a;
        }
        ArrayList<String> a = new ArrayList<>();
        a.add("");
        a.add("");
        return a;
    }

    /**
     * <h1>Esta funcion capta el item seleccionado del jCombobox1 de la vista
     * principal</h1>
     *
     * @param ie captura la accion de seleccion de un item
     */
    @Override
    public void itemStateChanged(ItemEvent ie) {
        for (int i = 0; i < Casos.split(";").length; i++) {
            if (Casos.split(";")[i].equals(ie.getItem())) {
                String get = (String) CuentaCT(Integer.valueOf(Casos.split(";")[i])).get(1);
                car.setJComboBox2(get.split(";"));
                ticket = (C_Ticket) cm.leerObjeto("contenedor\\ar_" + nombreA + "\\caso" + (i + 1) + "\\ticket" + get.split(";").length + ".txt");
                if (ticket.getEstado().equals("Cerrado")) {//si el ultimo ticket del caso esta Cerrado desabilita la opcion de continuar el caso
                    car.setjButtonNC(false);
                } else {//si no la habilita
                    car.setjButtonNC(true);
                }
            }

        }
    }

    public void devolver(boolean b) {
        existe = b;
    }

}
