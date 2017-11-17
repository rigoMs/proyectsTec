/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mx.gob.hrrb.modelo.core.Cita;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oCitasAu")
@RequestScoped
public class CitaAutorizada {
private Cita oCita;
    /**
     * Creates a new instance of CitaAutorizada
     */
    public CitaAutorizada() {
        oCita=new Cita();
    }
    
    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }
}
