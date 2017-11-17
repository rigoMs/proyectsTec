/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author DavidH
 */
public class CotizacionDNAAlmacen implements Serializable {
    
    private double nPrecioUnitario;
    private int nCantSurtir;
    private int nIva;
    private double nTotal;
    private InventarioMateriales oInventMat;
    private AccesoDatos oAD;

    
    
    
    
    public double getPrecioUnitario() {
        return nPrecioUnitario;
    }
    public void setPrecioUnitario(double nPrecioUnitario) {
        this.nPrecioUnitario = nPrecioUnitario;
    }
    public int getCantSurtir() {
        return nCantSurtir;
    }
    public void setCantSurtir(int nCantSurtir) {
        this.nCantSurtir = nCantSurtir;
    }
    public int getIva() {
        return nIva;
    }
    public void setIva(int nIva) {
        this.nIva = nIva;
    }
    public double getTotal() {
        return nTotal;
    }
    public void setTotal(double nTotal) {
        this.nTotal = nTotal;
    }
    public InventarioMateriales getInventMat() {
        return oInventMat;
    }
    public void setInventMat(InventarioMateriales oInventMat) {
        this.oInventMat = oInventMat;
    }
    public AccesoDatos getAD() {
        return oAD;
    }
    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }
}
