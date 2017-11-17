/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author DavidH
 */
public class CotizacionDNAFarmacia implements Serializable {
    
    private String sControlMed;
    private double nPrecioUnitario;
    private int nCantSurtir;
    private double nPrecioTotal;
    private DetalleMedicamentos oDetalleMedicamentos;
    private AccesoDatos oAD;

    
    
    
    
    
    public String getsControlMed() {
        return sControlMed;
    }
    public void setsControlMed(String sControlMed) {
        this.sControlMed = sControlMed;
    }
    public double getnPrecioUnitario() {
        return nPrecioUnitario;
    }
    public void setnPrecioUnitario(double nPrecioUnitario) {
        this.nPrecioUnitario = nPrecioUnitario;
    }
    public int getnCantSurtir() {
        return nCantSurtir;
    }
    public void setnCantSurtir(int nCantSurtir) {
        this.nCantSurtir = nCantSurtir;
    }
    public double getnPrecioTotal() {
        return nPrecioTotal;
    }
    public void setnPrecioTotal(double nPrecioTotal) {
        this.nPrecioTotal = nPrecioTotal;
    }
    public DetalleMedicamentos getoDetalleMedicamentos() {
        return oDetalleMedicamentos;
    }
    public void setoDetalleMedicamentos(DetalleMedicamentos oDetalleMedicamentos) {
        this.oDetalleMedicamentos = oDetalleMedicamentos;
    }

    public AccesoDatos getoAD() {
        return oAD;
    }

    public void setoAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }
    
    
}
