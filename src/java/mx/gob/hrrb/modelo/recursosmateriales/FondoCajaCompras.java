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
public class FondoCajaCompras implements Serializable {
    
    private Date dFechaCaja;
    private int nClaveCaja;
    private String sDescripcCaja;
    private double nFondoCaja;

    
    
    
    public Date getdFechaCaja() {
        return dFechaCaja;
    }
    public void setdFechaCaja(Date dFechaCaja) {
        this.dFechaCaja = dFechaCaja;
    } int getnClaveCaja() {
        return nClaveCaja;
    }
    public void setnClaveCaja(int nClaveCaja) {
        this.nClaveCaja = nClaveCaja;
    }
    public String getsDescripcCaja() {
        return sDescripcCaja;
    }
    public void setsDescripcCaja(String sDescripcCaja) {
        this.sDescripcCaja = sDescripcCaja;
    }
    public double getnFondoCaja() {
        return nFondoCaja;
    }
    public void setnFondoCaja(double nFondoCaja) {
        this.nFondoCaja = nFondoCaja;
    }
}
