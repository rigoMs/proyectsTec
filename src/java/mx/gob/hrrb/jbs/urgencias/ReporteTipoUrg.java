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
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oRepTipUrg")
@SessionScoped
//@ViewScoped

public class ReporteTipoUrg{
    private AdmisionUrgs oAdmisionUrgs;
    
    private List<AdmisionUrgs> lAdmision;
    private String activaTabla;
    private Date dFechaInicial;
    private Date dFechaFinal;
    private String sFechaI;
    private String sFechaF;
    private String fechaHoy;
    
    public ReporteTipoUrg(){
        activaTabla="display: none;";
        lAdmision=null;
        dFechaInicial=null;
        dFechaFinal=null;
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

    public void generar(){
        setActivaTabla("");
        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fF=new SimpleDateFormat("yyyy-MM-dd");
        
        setFechaI(fI.format(getFechaInicial()));
        setFechaF(fF.format(getFechaFinal()));
        
        try {
            setAdmision(oAdmisionUrgs.BuscaTipoUrgs(getFechaI(), getFechaF()));
        } catch (Exception ex) {
            Logger.getLogger(BuscaNotasMedicas.class.getName()).log(Level.SEVERE, null, ex);
        }
        dFechaInicial=null;
        dFechaFinal=null;
    }

    /**
     * @return the lAdmision
     */
    public List<AdmisionUrgs> getAdmision() {
        return lAdmision;
    }

    /**
     * @param lAdmision the lAdmision to set
     */
    public void setAdmision(List<AdmisionUrgs> lAdmision) {
        this.lAdmision = lAdmision;
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
     * @return the fechaHoy
     */
    public String getFechaHoy() {
        setActivaTabla("display: none;");
        return fechaHoy;
    }

    /**
     * @param fechaHoy the fechaHoy to set
     */
    public void setFechaHoy(String fechaHoy) {
        this.fechaHoy = fechaHoy;
    }
}