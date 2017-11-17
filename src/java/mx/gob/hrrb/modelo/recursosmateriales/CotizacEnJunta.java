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
public class CotizacEnJunta implements Serializable {
    
    private FondoCajaCompras oFondoCaja;
    private RequisicionBasica oBasica;
    private int nAprobadoBasic;
    private int nIvaBasic;
    private double nTotalBasic;
    private String sClaveCajaBasic;
    private Recuperacion oRecuperacion;
    private double nTotalRecup;
    private String sClaveCajaRecup;
    private RequisicionInterna oRequisicion;
    private int nAprobadoReqInt;
    private int nIvaReqInt;
    private double nTotalReqInt;
    private String sClaveCajaReqInt;
    private AccesoDatos oAD;

   
    
    
    
    public FondoCajaCompras getFondoCaja() {
        return oFondoCaja;
    }
    public void setFondoCaja(FondoCajaCompras oFondoCaja) {
        this.oFondoCaja = oFondoCaja;
    }
    public RequisicionBasica getBasica() {
        return oBasica;
    }
    public void setBasica(RequisicionBasica oBasica) {
        this.oBasica = oBasica;
    }
    public int getAprobadoBasic() {
        return nAprobadoBasic;
    }
    public void setAprobadoBasic(int nAprobadoBasic) {
        this.nAprobadoBasic = nAprobadoBasic;
    }
    public int getIvaBasic() {
        return nIvaBasic;
    }
    public void setIvaBasic(int nIvaBasic) {
        this.nIvaBasic = nIvaBasic;
    }
    public double getTotalBasic() {
        return nTotalBasic;
    }
    public void setTotalBasic(double nTotalBasic) {
        this.nTotalBasic = nTotalBasic;
    }
    public String getClaveCajaBasic() {
        return sClaveCajaBasic;
    }
    public void setClaveCajaBasic(String sClaveCajaBasic) {
        this.sClaveCajaBasic = sClaveCajaBasic;
    }
    public Recuperacion getRecuperacion() {
        return oRecuperacion;
    }
    public void setRecuperacion(Recuperacion oRecuperacion) {
        this.oRecuperacion = oRecuperacion;
    }
    public double getTotalRecup() {
        return nTotalRecup;
    }
    public void setTotalRecup(double nTotalRecup) {
        this.nTotalRecup = nTotalRecup;
    }
    public String getClaveCajaRecup() {
        return sClaveCajaRecup;
    }
    public void setClaveCajaRecup(String sClaveCajaRecup) {
        this.sClaveCajaRecup = sClaveCajaRecup;
    }
    public RequisicionInterna getRequisicion() {
        return oRequisicion;
    }
    public void setRequisicion(RequisicionInterna oRequisicion) {
        this.oRequisicion = oRequisicion;
    }
    public int getAprobadoReqInt() {
        return nAprobadoReqInt;
    }
    public void setAprobadoReqInt(int nAprobadoReqInt) {
        this.nAprobadoReqInt = nAprobadoReqInt;
    }
    public int getIvaReqInt() {
        return nIvaReqInt;
    }
    public void setIvaReqInt(int nIvaReqInt) {
        this.nIvaReqInt = nIvaReqInt;
    }
    public double getTotalReqInt() {
        return nTotalReqInt;
    }
    public void setTotalReqInt(double nTotalReqInt) {
        this.nTotalReqInt = nTotalReqInt;
    }
    public String getClaveCajaReqInt() {
        return sClaveCajaReqInt;
    }
    public void setClaveCajaReqInt(String sClaveCajaReqInt) {
        this.sClaveCajaReqInt = sClaveCajaReqInt;
    }
    public AccesoDatos getAD() {
        return oAD;
    }
    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }
}
