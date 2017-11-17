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
public class RecibirDNAFarmacia implements Serializable {
    
    
    private Date dFechaInicPer;
    private Date dFechaFinPer;
    private Date dFechaEmision;
    private String sControlMed;

    
    
    
    
    
    public Date getdFechaInicPer() {
        return dFechaInicPer;
    }
    public void setdFechaInicPer(Date dFechaInicPer) {
        this.dFechaInicPer = dFechaInicPer;
    }
    public Date getdFechaFinPer() {
        return dFechaFinPer;
    }
    public void setdFechaFinPer(Date dFechaFinPer) {
        this.dFechaFinPer = dFechaFinPer;
    }
    public Date getdFechaEmision() {
        return dFechaEmision;
    }
    public void setdFechaEmision(Date dFechaEmision) {
        this.dFechaEmision = dFechaEmision;
    }
    public String getsControlMed() {
        return sControlMed;
    }
    public void setsControlMed(String sControlMed) {
        this.sControlMed = sControlMed;
    }
}
