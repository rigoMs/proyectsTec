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
public class ValeNormal implements Serializable {
    
    private int nNoDeFolio;
    private String sDescripcArt;
    private String sCaracteristicasProd;
    private int nCantidad;
    private String sUnidadMed;
    private double nPrecioUnit;
    private ValeNormal arrValeNormal[];

    
    
    
    
    
    
    public int getnNoDeFolio() {
        return nNoDeFolio;
    }
    public void setnNoDeFolio(int nNoDeFolio) {
        this.nNoDeFolio = nNoDeFolio;
    }
    public String getsDescripcArt() {
        return sDescripcArt;
    }
    public void setsDescripcArt(String sDescripcArt) {
        this.sDescripcArt = sDescripcArt;
    }
    public String getsCaracteristicasProd() {
        return sCaracteristicasProd;
    }
    public void setsCaracteristicasProd(String sCaracteristicasProd) {
        this.sCaracteristicasProd = sCaracteristicasProd;
    }
    public int getnCantidad() {
        return nCantidad;
    }
    public void setnCantidad(int nCantidad) {
        this.nCantidad = nCantidad;
    }
    public String getsUnidadMed() {
        return sUnidadMed;
    }
    public void setsUnidadMed(String sUnidadMed) {
        this.sUnidadMed = sUnidadMed;
    }
    public double getnPrecioUnit() {
        return nPrecioUnit;
    }
    public void setnPrecioUnit(double nPrecioUnit) {
        this.nPrecioUnit = nPrecioUnit;
    }
    public ValeNormal[] getArrValeNormal() {
        return arrValeNormal;
    }
    public void setArrValeNormal(ValeNormal[] arrValeNormal) {
        this.arrValeNormal = arrValeNormal;
    }
}
