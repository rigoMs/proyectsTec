/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;

/**
 *
 * @author DavidH
 */
public class TipoProd implements Serializable {
    
    private int nCveTipoProd;
    private String sDescripcion;

    
    
    
    public int getnCveTipoProd() {
        return nCveTipoProd;
    }
    public void setnCveTipoProd(int nCveTipoProd) {
        this.nCveTipoProd = nCveTipoProd;
    }
    public String getsDescripcion() {
        return sDescripcion;
    }
    public void setsDescripcion(String sDescripcion) {
        this.sDescripcion = sDescripcion;
    }
}
