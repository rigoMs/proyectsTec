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
public class CuadroComparativo implements Serializable {
    
    
    private Date dFechaCuad;
    private char sTipoSolic;
    private int nNoDeReq;
    private RequisicionInterna oRequisicionInterna;
    private String oDescripcionPartida;
    private PartidaPresupuestal oPartidaPres;
    private String sDescripcionReq;
    private String sPresentacion;
    private double nPu1;
    private double nSubt1;
    private int nIva1;
    private double nTot1;
    private double nPu2;
    private double nSubt2;
    private int nIva2;
    private double nTot2;
    private double nPu3;
    private double nSubt3;
    private int nIva3;
    private double nTot3;
    private PersonalHospitalario oJefeAdquisiciones;
    private PersonalHospitalario oCoordRecMat;
    private PersonalHospitalario oDirectorHosp;

    
    
    
    
    
    
    public Date getdFechaCuad() {
        return dFechaCuad;
    }
    public void setdFechaCuad(Date dFechaCuad) {
        this.dFechaCuad = dFechaCuad;
    }
    public char getsTipoSolic() {
        return sTipoSolic;
    }
    public void setsTipoSolic(char sTipoSolic) {
        this.sTipoSolic = sTipoSolic;
    }
    public int getnNoDeReq() {
        return nNoDeReq;
    }
    public void setnNoDeReq(int nNoDeReq) {
        this.nNoDeReq = nNoDeReq;
    }
    public RequisicionInterna getoRequisicionInterna() {
        return oRequisicionInterna;
    }
    public void setoRequisicionInterna(RequisicionInterna oRequisicionInterna) {
        this.oRequisicionInterna = oRequisicionInterna;
    }
    public String getoDescripcionPartida() {
        return oDescripcionPartida;
    }
    public void setoDescripcionPartida(String oDescripcionPartida) {
        this.oDescripcionPartida = oDescripcionPartida;
    }
    public PartidaPresupuestal getoPartidaPres() {
        return oPartidaPres;
    }
    public void setoPartidaPres(PartidaPresupuestal oPartidaPres) {
        this.oPartidaPres = oPartidaPres;
    }
    public String getsDescripcionReq() {
        return sDescripcionReq;
    }
    public void setsDescripcionReq(String sDescripcionReq) {
        this.sDescripcionReq = sDescripcionReq;
    }
    public String getsPresentacion() {
        return sPresentacion;
    }
    public void setsPresentacion(String sPresentacion) {
        this.sPresentacion = sPresentacion;
    }
    public double getnPu1() {
        return nPu1;
    }
    public void setnPu1(double nPu1) {
        this.nPu1 = nPu1;
    }
    public double getnSubt1() {
        return nSubt1;
    }
    public void setnSubt1(double nSubt1) {
        this.nSubt1 = nSubt1;
    }
    public int getnIva1() {
        return nIva1;
    }
    public void setnIva1(int nIva1) {
        this.nIva1 = nIva1;
    }
    public double getnTot1() {
        return nTot1;
    }
    public void setnTot1(double nTot1) {
        this.nTot1 = nTot1;
    }
    public double getnPu2() {
        return nPu2;
    }
    public void setnPu2(double nPu2) {
        this.nPu2 = nPu2;
    }
    public double getnSubt2() {
        return nSubt2;
    }
    public void setnSubt2(double nSubt2) {
        this.nSubt2 = nSubt2;
    }
    public int getnIva2() {
        return nIva2;
    }
    public void setnIva2(int nIva2) {
        this.nIva2 = nIva2;
    }
    public double getnTot2() {
        return nTot2;
    }
    public void setnTot2(double nTot2) {
        this.nTot2 = nTot2;
    }
    public double getnPu3() {
        return nPu3;
    }
    public void setnPu3(double nPu3) {
        this.nPu3 = nPu3;
    }
    public double getnSubt3() {
        return nSubt3;
    }
    public void setnSubt3(double nSubt3) {
        this.nSubt3 = nSubt3;
    }
    public int getnIva3() {
        return nIva3;
    }
    public void setnIva3(int nIva3) {
        this.nIva3 = nIva3;
    }
    public double getnTot3() {
        return nTot3;
    }
    public void setnTot3(double nTot3) {
        this.nTot3 = nTot3;
    }
    public PersonalHospitalario getoJefeAdquisiciones() {
        return oJefeAdquisiciones;
    }
    public void setoJefeAdquisiciones(PersonalHospitalario oJefeAdquisiciones) {
        this.oJefeAdquisiciones = oJefeAdquisiciones;
    }
    public PersonalHospitalario getoCoordRecMat() {
        return oCoordRecMat;
    }
    public void setoCoordRecMat(PersonalHospitalario oCoordRecMat) {
        this.oCoordRecMat = oCoordRecMat;
    }
    public PersonalHospitalario getoDirectorHosp() {
        return oDirectorHosp;
    }
    public void setoDirectorHosp(PersonalHospitalario oDirectorHosp) {
        this.oDirectorHosp = oDirectorHosp;
    }
    
}
