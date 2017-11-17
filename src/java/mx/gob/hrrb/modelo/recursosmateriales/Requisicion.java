/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import java.util.Date;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 *
 * @author DavidH
 */
public class Requisicion implements Serializable{
    private int nNoDeReq;
    private Date dFechaSolic;
    private Parametrizacion oTipoRequisicion;
    private Date dFechaAcep;
    private AreaServicioHRRB oAreaServicio;
    private PartidaPresupuestal oPartidaPres;
    private CatProductosRec oProducto;
    private String sCaracteristicas;
    private int nSolicitado;
    private PersonalHospitalario oSolicMesa;
    private FondoCajaCompras oFondoCaja;

    
    public int getnNoDeReq() {
        return nNoDeReq;
    }
    public void setnNoDeReq(int nNoDeReq) {
        this.nNoDeReq = nNoDeReq;
    }
    public Date getdFechaSolic() {
        return dFechaSolic;
    }
    public void setdFechaSolic(Date dFechaSolic) {
        this.dFechaSolic = dFechaSolic;
    }
    public Parametrizacion getoTipoRequisicion() {
        return oTipoRequisicion;
    }
    public void setoTipoRequisicion(Parametrizacion oTipoRequisicion) {
        this.oTipoRequisicion = oTipoRequisicion;
    }
    public Date getdFechaAcep() {
        return dFechaAcep;
    }
    public void setdFechaAcep(Date dFechaAcep) {
        this.dFechaAcep = dFechaAcep;
    }
    public AreaServicioHRRB getoAreaServicio() {
        return oAreaServicio;
    }
    public void setoAreaServicio(AreaServicioHRRB oAreaServicio) {
        this.oAreaServicio = oAreaServicio;
    }
    public PartidaPresupuestal getoPartidaPres() {
        return oPartidaPres;
    }
    public void setoPartidaPres(PartidaPresupuestal oPartidaPres) {
        this.oPartidaPres = oPartidaPres;
    }
    public CatProductosRec getoProducto() {
        return oProducto;
    }
    public void setoProducto(CatProductosRec oProducto) {
        this.oProducto = oProducto;
    }
    public String getsCaracteristicas() {
        return sCaracteristicas;
    }
    public void setsCaracteristicas(String sCaracteristicas) {
        this.sCaracteristicas = sCaracteristicas;
    }
    public int getnSolicitado() {
        return nSolicitado;
    }
    public void setnSolicitado(int nSolicitado) {
        this.nSolicitado = nSolicitado;
    }
    public PersonalHospitalario getoSolicMesa() {
        return oSolicMesa;
    }
    public void setoSolicMesa(PersonalHospitalario oSolicMesa) {
        this.oSolicMesa = oSolicMesa;
    }
    public FondoCajaCompras getoFondoCaja() {
        return oFondoCaja;
    }
    public void setoFondoCaja(FondoCajaCompras oFondoCaja) {
        this.oFondoCaja = oFondoCaja;
    }
}
