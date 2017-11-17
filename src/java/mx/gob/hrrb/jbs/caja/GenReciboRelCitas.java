/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.caja;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mx.gob.hrrb.modelo.core.Cita;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="genRecRelCitas")
@RequestScoped
public class GenReciboRelCitas {
    private Cita arrCitas[];
    /**
     * Creates a new instance of GenReciboRelCitas
     */
    public GenReciboRelCitas() {
    }

    public Cita[] getCitas() {
        return arrCitas;
    }

    public void setCitas(Cita[] arrCitas) {
        this.arrCitas = arrCitas;
    }
    
}
