/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolaAdministracion.modelo;

import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class Modelo {

    public void escribirObjeto(String ruta, Object objeto) {
        try {
            File GestorCasos = new File(ruta);
            FileOutputStream fos = new FileOutputStream(GestorCasos);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(objeto);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object leerObjeto(String ruta) {
        try {
            File GestorCasos = new File(ruta);
            FileInputStream fis = new FileInputStream(GestorCasos);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object ob = ois.readObject();
            ois.close();
            fis.close();
            return ob;
        } catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String leerLinea(String ruta) {

        try {
            String linea = "";
            File GestorCasos = new File(ruta);
            FileReader fr = new FileReader(GestorCasos);
            BufferedReader br = new BufferedReader(fr);
            linea = br.readLine();
            br.close();
            fr.close();
            return linea;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void escribirLinea(String ruta, String linea) {

        try {
            File GestorCasos = new File(ruta);
            FileWriter fr = new FileWriter(GestorCasos,true);
            BufferedWriter br = new BufferedWriter(fr);
            br.append(linea);
            br.newLine();
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> leerLineaLinea(String ruta){
        try {
            String linea = "";
            	
            ArrayList<String> texto = new ArrayList<String>();
            File GestorCasos = new File(ruta);
            FileReader fr = new FileReader(GestorCasos);
            BufferedReader br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                texto.add(linea);
            }
            br.close();
            fr.close();
            return texto;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
    public void escribirLineaLinea(String ruta, ArrayList<String> linea) {

        try {
            File GestorCasos = new File(ruta);
            FileWriter fr = new FileWriter(GestorCasos);
            BufferedWriter br = new BufferedWriter(fr);
            for (int i = 0; i < linea.size(); i++) {
                br.write(linea.get(i));
                br.newLine();
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
