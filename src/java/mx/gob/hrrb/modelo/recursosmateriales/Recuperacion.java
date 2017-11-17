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
public class Recuperacion implements Serializable {
    
    private Date dFechaCreac;
    private int nClave;
    private String sDescripc;
    private double nTotalRecup;
    private FondoCajaCompras oFondoCaja;

    
    
    
    
    public Date getdFechaCreac() {
        return dFechaCreac;
    }
    public void setdFechaCreac(Date dFechaCreac) {
        this.dFechaCreac = dFechaCreac;
    }
    public int getnClave() {
        return nClave;
    }
    public void setnClave(int nClave) {
        this.nClave = nClave;
    }
    public String getsDescripc() {
        return sDescripc;
    }
    public void setsDescripc(String sDescripc) {
        this.sDescripc = sDescripc;
    }
    public double getnTotalRecup() {
        return nTotalRecup;
    }
    public void setnTotalRecup(double nTotalRecup) {
        this.nTotalRecup = nTotalRecup;
    }
    public FondoCajaCompras getoFondoCaja() {
        return oFondoCaja;
    }
    public void setoFondoCaja(FondoCajaCompras oFondoCaja) {
        this.oFondoCaja = oFondoCaja;
    }
}
