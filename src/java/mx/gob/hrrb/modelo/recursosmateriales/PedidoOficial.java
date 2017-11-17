/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import java.util.Date;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 *
 * @author DavidH
 */
public class PedidoOficial implements Serializable {
   
    
    private Date dFechaPedido;
    private String sTransporteEntrega;
    private int nNoDePedido;
    private String sComprador;
    private String sCondEnt;
    private String sCondPago;
    private String sReferenciaPedido;
    private int nNoDeRequisicion;
    private int nNoDeSemFarm;
    private int nNoDeSemAlm;
    private String sObservacionPedido;
    private String sCargoSupervisa;
    private PersonalHospitalario oCoordRecMat;
    private PersonalHospitalario oSupervisoPed;
    private PersonalHospitalario oDirector;

    
    
    
    
    public Date getdFechaPedido() {
        return dFechaPedido;
    }
    public void setdFechaPedido(Date dFechaPedido) {
        this.dFechaPedido = dFechaPedido;
    }
    public String getsTransporteEntrega() {
        return sTransporteEntrega;
    }
    public void setsTransporteEntrega(String sTransporteEntrega) {
        this.sTransporteEntrega = sTransporteEntrega;
    }
    public int getnNoDePedido() {
        return nNoDePedido;
    }
    public void setnNoDePedido(int nNoDePedido) {
        this.nNoDePedido = nNoDePedido;
    }
    public String getsComprador() {
        return sComprador;
    }
    public void setsComprador(String sComprador) {
        this.sComprador = sComprador;
    }
    public String getsCondEnt() {
        return sCondEnt;
    }
    public void setsCondEnt(String sCondEnt) {
        this.sCondEnt = sCondEnt;
    }
    public String getsCondPago() {
        return sCondPago;
    }
    public void setsCondPago(String sCondPago) {
        this.sCondPago = sCondPago;
    }
    public String getsReferenciaPedido() {
        return sReferenciaPedido;
    }
    public void setsReferenciaPedido(String sReferenciaPedido) {
        this.sReferenciaPedido = sReferenciaPedido;
    }
    public int getnNoDeRequisicion() {
        return nNoDeRequisicion;
    }
    public void setnNoDeRequisicion(int nNoDeRequisicion) {
        this.nNoDeRequisicion = nNoDeRequisicion;
    }
    public int getnNoDeSemFarm() {
        return nNoDeSemFarm;
    }
    public void setnNoDeSemFarm(int nNoDeSemFarm) {
        this.nNoDeSemFarm = nNoDeSemFarm;
    }
    public int getnNoDeSemAlm() {
        return nNoDeSemAlm;
    }
    public void setnNoDeSemAlm(int nNoDeSemAlm) {
        this.nNoDeSemAlm = nNoDeSemAlm;
    }
    public String getsObservacionPedido() {
        return sObservacionPedido;
    }
    public void setsObservacionPedido(String sObservacionPedido) {
        this.sObservacionPedido = sObservacionPedido;
    }
    public String getsCargoSupervisa() {
        return sCargoSupervisa;
    }
    public void setsCargoSupervisa(String sCargoSupervisa) {
        this.sCargoSupervisa = sCargoSupervisa;
    }
    public PersonalHospitalario getoCoordRecMat() {
        return oCoordRecMat;
    }
    public void setoCoordRecMat(PersonalHospitalario oCoordRecMat) {
        this.oCoordRecMat = oCoordRecMat;
    }
    public PersonalHospitalario getoSupervisoPed() {
        return oSupervisoPed;
    }
    public void setoSupervisoPed(PersonalHospitalario oSupervisoPed) {
        this.oSupervisoPed = oSupervisoPed;
    }
    public PersonalHospitalario getoDirector() {
        return oDirector;
    }
    public void setoDirector(PersonalHospitalario oDirector) {
        this.oDirector = oDirector;
    }
    
}
