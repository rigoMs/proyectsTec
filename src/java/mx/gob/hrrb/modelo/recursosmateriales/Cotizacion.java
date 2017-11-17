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
public class Cotizacion implements Serializable {
    
    private RequisicionInterna oRequisicion;
    private int nClavePartida;
    private String sTipoProducto;
    private String sDescProducto;
    private String sCaracteristicas;
    private String sUnidadPresentacion;
    private double nPrecioUnitario;
    private int nIva;
    private AccesoDatos oAD;

    
    
    
    
    public RequisicionInterna getRequisicion() {
        return oRequisicion;
    }
    public void setRequisicion(RequisicionInterna oRequisicion) {
        this.oRequisicion = oRequisicion;
    }
    public int getClavePartida() {
        return nClavePartida;
    }
    public void setClavePartida(int nClavePartida) {
        this.nClavePartida = nClavePartida;
    }
    public String getTipoProducto() {
        return sTipoProducto;
    }
    public void setTipoProducto(String sTipoProducto) {
        this.sTipoProducto = sTipoProducto;
    }
    public String getDescProducto() {
        return sDescProducto;
    }
    public void setDescProducto(String sDescProducto) {
        this.sDescProducto = sDescProducto;
    }
    public String getCaracteristicas() {
        return sCaracteristicas;
    }
    public void setCaracteristicas(String sCaracteristicas) {
        this.sCaracteristicas = sCaracteristicas;
    }
    public String getUnidadPresentacion() {
        return sUnidadPresentacion;
    }
    public void setUnidadPresentacion(String sUnidadPresentacion) {
        this.sUnidadPresentacion = sUnidadPresentacion;
    }
    public double getPrecioUnitario() {
        return nPrecioUnitario;
    }
    public void setPrecioUnitario(double nPrecioUnitario) {
        this.nPrecioUnitario = nPrecioUnitario;
    }
    public int getIva() {
        return nIva;
    }
    public void setIva(int nIva) {
        this.nIva = nIva;
    }
    public AccesoDatos getAD() {
        return oAD;
    }
    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }
}
