/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaAdministracion.modelo;

import java.io.Serializable;

/**
 *
 * @author Fernando
 */
public class C_Admin implements Serializable{
    String Admin;

    public C_Admin(String Admin) {
        this.Admin = Admin;
    }

    public String getAdmin() {
        return Admin;
    }

    public void setAdmin(String Admin) {
        this.Admin = Admin;
    }
    
    
}
