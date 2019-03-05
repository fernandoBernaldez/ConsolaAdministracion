/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaAdministracion.modelo;

/**
 *
 * @author Fernando
 */
public class C_Caso {

    private int id;
    private String adminId;
    private String estado;

    public C_Caso(int id, String adminId, String estado) {
        this.id = id;
        this.adminId = adminId;
        this.estado = estado;
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
