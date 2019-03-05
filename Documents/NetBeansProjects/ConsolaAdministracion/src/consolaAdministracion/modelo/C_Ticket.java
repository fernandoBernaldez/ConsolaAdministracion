/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaAdministracion.modelo;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author Fernando
 */
public class C_Ticket implements Serializable{

    private int id;
    private int casoId;
    private String adminId;
    private String fecha;
    private String asunto;
    private String prioridad;
    private String descripcion;
    private String estado;

    public C_Ticket(int id, int casoId, String adminId, String fecha, String asunto, String prioridad, String descripcion, String estado) {
        super();
        this.id = id;
        this.casoId = casoId;
        this.adminId = adminId;
        this.fecha = fecha;
        this.asunto = asunto;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public C_Ticket(int id, int casoId, String adminId, String asunto, String prioridad, String descripcion, String estado) {
        super();
        this.id = id;
        this.casoId = casoId;
        this.adminId = adminId;
        this.fecha = (Integer.toString(Calendar.DATE) + "/" + (Integer.toString(Calendar.MONTH) + 1) + "/" + Integer.toString(Calendar.YEAR));
        this.asunto = asunto;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public C_Ticket(int id, int casoId, String adminId, String asunto, String prioridad, String descripcion) {
        super();
        this.id = id;
        this.casoId = casoId;
        this.adminId = adminId;
        this.fecha = (Integer.toString(Calendar.DATE) + "/" + (Integer.toString(Calendar.MONTH) + 1) + "/" + Integer.toString(Calendar.YEAR));
        this.asunto = asunto;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
        this.estado = "abierto";
    }

    public C_Ticket(int id, int casoId, String adminId, String asunto, String descripcion) {
        super();
        this.id = id;
        this.casoId = casoId;
        this.adminId = adminId;
        this.fecha = (Integer.toString(Calendar.DATE) + "/" + (Integer.toString(Calendar.MONTH) + 1) + "/" + Integer.toString(Calendar.YEAR));
        this.asunto = asunto;
        this.prioridad = "media";
        this.descripcion = descripcion;
        this.estado = "abierto";
    }
    public C_Ticket(int id, int casoId, String adminId) {
        super();
        this.id = id;
        this.casoId = casoId;
        this.adminId = adminId;
        this.fecha = (Integer.toString(Calendar.DATE) + "/" + (Integer.toString(Calendar.MONTH) + 1) + "/" + Integer.toString(Calendar.YEAR));
        this.asunto = "";
        this.prioridad = "media";
        this.descripcion = "";
        this.estado = "abierto";
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the casoId
     */
    public int getCasoId() {
        return casoId;
    }

    /**
     * @param casoId the casoId to set
     */
    public void setCasoId(int casoId) {
        this.casoId = casoId;
    }

    /**
     * @return the adminId
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * @param adminId the adminId to set
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the prioridad
     */
    public String getPrioridad() {
        return prioridad;
    }

    /**
     * @param prioridad the prioridad to set
     */
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
