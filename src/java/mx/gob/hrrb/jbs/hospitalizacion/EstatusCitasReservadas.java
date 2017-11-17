/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Hospitalizacion;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oEstCitasRes")
@RequestScoped
public class EstatusCitasReservadas {
    private Date dFechaIni;
    private Date dFechaFin;
    private String sFechaI;
    private String sFechaF;
    private Cita oCita;
    private List<Cita> lEstatusCita;
    private Hospitalizacion oHosp;
    /**
     * Creates a new instance of EstatusCitasReservadas
     */
    public EstatusCitasReservadas() {
        oCita = new Cita();
        dFechaIni = null;
        dFechaFin = null;
        oHosp = null;
    }

    public void generar(){
        //Date fAltaHosp=(oEpisodioMedico.getAltaHospitalaria());

        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fF=new SimpleDateFormat("yyyy-MM-dd");
        
        setFechaI(fI.format(getFechaIni()));
        setFechaF(fF.format(getFechaFin()));
        
        System.out.println("Fecha Inicio: "+getFechaI());
        System.out.println("Fecha Fin: "+getFechaF());
        try {
            setEstatusCita(oCita.buscarEstatusCitasReservadas(getFechaI(), getFechaF()));
                    //buscarEstatusCitasReservadas(getFechaI(), getFechaF());
        } catch (Exception ex) {
            Logger.getLogger(ReporteHospitalizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /**
     * @return the dFechaIni
     */
    public Date getFechaIni() {
        return dFechaIni;
    }

    /**
     * @param dFechaIni the dFechaIni to set
     */
    public void setFechaIni(Date dFechaIni) {
        this.dFechaIni = dFechaIni;
    }

    /**
     * @return the dFechaFin
     */
    public Date getFechaFin() {
        return dFechaFin;
    }

    /**
     * @param dFechaFin the dFechaFin to set
     */
    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
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
     * @return the sFechaF
     */
    public String getFechaF() {
        return sFechaF;
    }

    /**
     * @param sFechaF the sFechaF to set
     */
    public void setFechaF(String sFechaF) {
        this.sFechaF = sFechaF;
    }

    /**
     * @return the oCita
     */
    public Cita getCita() {
        return oCita;
    }

    /**
     * @param oCita the oCita to set
     */
    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }

    /**
     * @return the lEstatusCita
     */
    public List<Cita> getEstatusCita() {
        return lEstatusCita;
    }

    /**
     * @param lEstatusCita the lEstatusCita to set
     */
    public void setEstatusCita(List<Cita> lEstatusCita) {
        this.lEstatusCita = lEstatusCita;
    }

    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }

    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }
    
}
