/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DavidH
 */
public class RecibirDNAAlmacen implements Serializable {
    
    private Date dFechaInic;
    private Date dFechaFin;

    
    
    
    
    public Date getdFechaInic() {
        return dFechaInic;
    }
    public void setdFechaInic(Date dFechaInic) {
        this.dFechaInic = dFechaInic;
    }
    public Date getdFechaFin() {
        return dFechaFin;
    }
    public void setdFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }
}
