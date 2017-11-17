/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.capasits;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;

/**
 *
 * @author sam
 */
@ManagedBean(name="consulMed")
@SessionScoped
public class ConsultaMedicamentos {
    private String sNombre = "";
    private DetalleMedicamentos[]  medBusNom = null;
    private boolean nombreEncontrado = false;

    /**
     * Creates a new instance of ConsultaMedicamentos
     */
    public ConsultaMedicamentos() {
    }
    
    
    public void buscaMedicamento(){        
        if (!getNombre().equals("")) {
            try {
                medBusNom = (new DetalleMedicamentos()).buscaXnombre(getNombre());
                if (medBusNom.length != 0) {
                     setNombreEncontrado(true);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron Coincidencias ", " "));
                }
            } catch (Exception ex) {
                Logger.getLogger(Surtir.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }

    public String getNombre() {
        return sNombre;
    }

    public void setNombre(String sNombre) {
        this.sNombre = sNombre;
    }
    
      public boolean getNombreEncontrado() {
        return nombreEncontrado;
    }

    public void setNombreEncontrado(boolean nombreEncontrado) {
        this.nombreEncontrado = nombreEncontrado;
    }
    
    public DetalleMedicamentos[] getMedBusNom() {
        buscaMedicamento();
        return medBusNom;
    }
    
    public void borraNom(){
    setNombre("");
    setNombreEncontrado(false);
    }
    
}
