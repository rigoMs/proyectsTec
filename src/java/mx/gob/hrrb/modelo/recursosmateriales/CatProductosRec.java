/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author DavidH
 */
public class CatProductosRec implements Serializable {
    
    private int nClave;
    private String sDescripcProd;
    private String sPresentacion;
    private AccesoDatos oAD;

    
    
    
    public int getClave() {
        return nClave;
    }
    public void setClave(int nClave) {
        this.nClave = nClave;
    }
    public String getDescripcProd() {
        return sDescripcProd;
    }
    public void setDescripcProd(String sDescripcProd) {
        this.sDescripcProd = sDescripcProd;
    }
    public String getPresentacion() {
        return sPresentacion;
    }
    public void setPresentacion(String sPresentacion) {
        this.sPresentacion = sPresentacion;
    }
    public AccesoDatos getAD() {
        return oAD;
    }
    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }
}
