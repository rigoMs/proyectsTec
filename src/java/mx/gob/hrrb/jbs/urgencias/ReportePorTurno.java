 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oRepTurno") 
@SessionScoped
//@ViewScoped

public class ReportePorTurno{
    private AdmisionUrgs oAdmisionUrgs;
    
    private List<AdmisionUrgs> lAdmision1;
    private List<AdmisionUrgs> lAdmision2;
    private List<AdmisionUrgs> lAdmision3;
    private List<AdmisionUrgs> lAdmision4;
    private List<AdmisionUrgs> lAdmisionUsu;
    
    private Date dFechaInicial;
    private String sFechaI;
    private String fechaHoy;
    private int nArea;
    private int turno;
    private String sAreaEmision;
    private String sUsuario;
    private int nNumeroTarjeta;
    
    public ReportePorTurno() throws Exception{
        nArea=nNumeroTarjeta=0;
        lAdmision1=null;
        lAdmision2=null;
        lAdmision3=null;
        lAdmision4=null;
        lAdmisionUsu=null;
        dFechaInicial=null;
        oAdmisionUrgs= new AdmisionUrgs();
        setFechaHoy(oAdmisionUrgs.fechaActual());
    }
     
    /**
     * @return the oAdmisionUrgs
     */
    public AdmisionUrgs getAdmisionUrgs() {
        return oAdmisionUrgs;
    }

    /**
     * @param oAdmisionUrgs the oAdmisionUrgs to set
     */
    public void setAdmisionUrgs(AdmisionUrgs oAdmisionUrgs) {
        this.oAdmisionUrgs = oAdmisionUrgs;
    }

    public String generar(){
        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        
        setFechaI(fI.format(getFechaInicial()));
        
        try {
            setAdmision1(oAdmisionUrgs.buscareportePorTurno1(getTurno(),getArea(),getFechaI()));
            setAdmision2(oAdmisionUrgs.buscareportePorTurno2(getTurno(),getArea(),getFechaI()));
            setAdmision3(oAdmisionUrgs.buscareportePorTurno3(getTurno(),getArea(),getFechaI()));
            setAdmision4(oAdmisionUrgs.buscareportePorTurno4(getTurno(),getArea(),getFechaI()));
            setAdmisionUsu(oAdmisionUrgs.buscaUsuarioUrgencias());
            if(!lAdmisionUsu.isEmpty()){
            setUsuario(oAdmisionUrgs.buscaUsuarioUrgencias().get(0).getPaciente().getNombres()+
                    " "+oAdmisionUrgs.buscaUsuarioUrgencias().get(0).getPaciente().getApPaterno()
                    +" "+oAdmisionUrgs.buscaUsuarioUrgencias().get(0).getPaciente().getApMaterno());
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ReportePorTurno.class.getName()).log(Level.SEVERE, null, ex);
        }
        dFechaInicial=null;
        return "ReporteTurno";
    }
    
    /**
     * @return the dFechaInicial
     */
    public Date getFechaInicial() {
        return dFechaInicial;
    }

    /**
     * @param dFechaInicial the dFechaInicial to set
     */
    public void setFechaInicial(Date dFechaInicial) {
        this.dFechaInicial = dFechaInicial;
    }
    /**
     * @return the sFechaI
     */
    public String getFechaI() {
        return sFechaI;
    }

    /**
     * @param sFechaI the sFechaI to set
     */
    public void setFechaI(String sFechaI) {
        this.sFechaI = sFechaI;
    }


    /**
     * @return the fechaHoy
     */
    public String getFechaHoy() {
        return fechaHoy;
    }

    /**
     * @param fechaHoy the fechaHoy to set
     */
    public void setFechaHoy(String fechaHoy) {
        this.fechaHoy = fechaHoy;
    }
    
    //Retorna lista de areas para el reporte de emision de estados de salud
     public List<AreaServicioHRRB> getListaAreasHospi(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasRepHospDia()));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }

    /**
     * @return the nArea
     */
    public int getArea() {
        return nArea;
    }

    /**
     * @param nArea the nArea to set
     */
    public void setArea(int nArea) {
        this.nArea = nArea;
        if (nArea==1)
        setAreaEmision("Admisión Urgencias");
        else
            setAreaEmision("Urgencias Ginecológicas");
    }

    /**
     * @return the lAdmision1
     */
    public List<AdmisionUrgs> getAdmision1() {
        return lAdmision1;
    }

    /**
     * @param lAdmision1 the lAdmision1 to set
     */
    public void setAdmision1(List<AdmisionUrgs> lAdmision1) {
        this.lAdmision1 = lAdmision1;
    }


    /**
     * @return the lAdmision2
     */
    public List<AdmisionUrgs> getAdmision2() {
        return lAdmision2;
    }

    /**
     * @param lAdmision2 the lAdmision2 to set
     */
    public void setAdmision2(List<AdmisionUrgs> lAdmision2) {
        this.lAdmision2 = lAdmision2;
    }

    /**
     * @return the lAdmision3
     */
    public List<AdmisionUrgs> getAdmision3() {
        return lAdmision3;
    }

    /**
     * @param lAdmision3 the lAdmision3 to set
     */
    public void setAdmision3(List<AdmisionUrgs> lAdmision3) {
        this.lAdmision3 = lAdmision3;
    }

    /**
     * @return the turno
     */
    public int getTurno() {
        return turno;
    }

    /**
     * @param turno the turno to set
     */
    public void setTurno(int turno) {
        this.turno = turno;
    }

    /**
     * @return the sAreaEmision
     */
    public String getAreaEmision() {
        return sAreaEmision;
    }

    /**
     * @param sAreaEmision the sAreaEmision to set
     */
    public void setAreaEmision(String sAreaEmision) {
        this.sAreaEmision = sAreaEmision;
    }

    /**
     * @return the lAdmision4
     */
    public List<AdmisionUrgs> getAdmision4() {
        return lAdmision4;
    }

    /**
     * @param lAdmision4 the lAdmision4 to set
     */
    public void setAdmision4(List<AdmisionUrgs> lAdmision4) {
        this.lAdmision4 = lAdmision4;
    }

    /**
     * @return the sUsuario
     */
    public String getUsuario() {
        return sUsuario;
    }

    /**
     * @param sUsuario the sUsuario to set
     */
    public void setUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }

    /**
     * @return the nNumeroTarjeta
     */
    public int getNumeroTarjeta() {
        return nNumeroTarjeta;
    }

    /**
     * @param nNumeroTarjeta the nNumeroTarjeta to set
     */
    public void setNumeroTarjeta(int nNumeroTarjeta) {
        this.nNumeroTarjeta = nNumeroTarjeta;
    }

    /**
     * @return the lAdmisionUsu
     */
    public List<AdmisionUrgs> getAdmisionUsu() {
        return lAdmisionUsu;
    }

    /**
     * @param lAdmisionUsu the lAdmisionUsu to set
     */
    public void setAdmisionUsu(List<AdmisionUrgs> lAdmisionUsu) {
        this.lAdmisionUsu = lAdmisionUsu;
    }
}