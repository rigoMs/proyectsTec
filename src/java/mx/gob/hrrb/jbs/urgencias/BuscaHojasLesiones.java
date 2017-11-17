 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
import mx.gob.hrrb.modelo.urgencias.HojaLesiones;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oBuscaHL")
@SessionScoped
//@ViewScoped

public class BuscaHojasLesiones{
    private AdmisionUrgs oAdmision;
    private EpisodioMedico oEpisodioMedico;
    private Date dFechaInicial;
    private Date dFechaFinal;
    private List<EpisodioMedico> lEpisodio;
    private String sFechaI;
    private String sFechaF;
    private long nFolioPaciente;
    private long nClaveEpisodio;
    private String activaTabla="display: none;";
    int a,m,d;
    
    public BuscaHojasLesiones(){
        activaTabla="display: none;";
        dFechaInicial=null;
        dFechaFinal=null;
        lEpisodio=null;
        oAdmision=new AdmisionUrgs();
        oEpisodioMedico= new EpisodioMedico();
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
     * @return the dFechaFinal
     */
    public Date getFechaFinal() {
        return dFechaFinal;
    }

    /**
     * @param dFechaFinal the dFechaFinal to set
     */
    public void setFechaFinal(Date dFechaFinal) {
        this.dFechaFinal = dFechaFinal;
    }
    
    public void generar(){
        setActivaTabla("");
        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fF=new SimpleDateFormat("yyyy-MM-dd");
        
        setFechaI(fI.format(getFechaInicial()));
        setFechaF(fF.format(getFechaFinal()));
        
        try {
            setEpisodio(getEpisodioMedico().buscaHojasLesionesLlenas(getFechaI(), getFechaF()));
        } catch (Exception ex) {
            Logger.getLogger(BuscaHojasLesiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        dFechaInicial=null;
        dFechaFinal=null;
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
     * @return the nFolioPaciente
     */
    public long getFolioPaciente() {
        return nFolioPaciente;
    }

    /**
     * @param nFolioPaciente the nFolioPaciente to set
     */
    public void setFolioPaciente(long nFolioPaciente) {
        this.nFolioPaciente = nFolioPaciente;
    }

    /**
     * @return the nClaveEpisodio
     */
    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    /**
     * @param nClaveEpisodio the nClaveEpisodio to set
     */
    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    /**
     * @return the activaTabla
     */
    public String getActivaTabla() {
        return activaTabla;
    }

    /**
     * @param activaTabla the activaTabla to set
     */
    public void setActivaTabla(String activaTabla) {
        this.activaTabla = activaTabla;
    }

    /**
     * @return the oEpisodioMedico
     */
    public EpisodioMedico getEpisodioMedico() {
        return oEpisodioMedico;
    }

    /**
     * @param oEpisodioMedico the oEpisodioMedico to set
     */
    public void setEpisodioMedico(EpisodioMedico oEpisodioMedico) {
        this.oEpisodioMedico = oEpisodioMedico;
    }

    /**
     * @return the lEpisodio
     */
    public List<EpisodioMedico> getEpisodio() {
        return lEpisodio;
    }

    /**
     * @param lEpisodio the lEpisodio to set
     */
    public void setEpisodio(List<EpisodioMedico> lEpisodio) {
        this.lEpisodio = lEpisodio;
    }

    /**
     * @return the oAdmision
     */
    public AdmisionUrgs getAdmision() {
        return oAdmision;
    }

    /**
     * @param oAdmision the oAdmision to set
     */
    public void setAdmision(AdmisionUrgs oAdmision) {
        this.oAdmision = oAdmision;
    }

}