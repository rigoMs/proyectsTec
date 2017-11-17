/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

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
import mx.gob.hrrb.modelo.core.Hospitalizacion;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oConCodes")
@SessionScoped
public class ConsultaCodes {
    private Hospitalizacion oHosp;
    private Date dFechaIni;
    private Date dFechaFin;
    private List<Hospitalizacion> lHospitalizacion;
    private List<Hospitalizacion> lFiltroHospitalizacion;
    private String sFechaI;
    private String sFechaF;
    private AreaServicioHRRB oASerHRRB;
    /**
     * Creates a new instance of ConsultaCodes
     */
    public ConsultaCodes() {
        oHosp = new Hospitalizacion();
        oASerHRRB = new AreaServicioHRRB();
        dFechaIni = null;
        dFechaFin = null;        
    }
   public List<AreaServicioHRRB> getListaAreasServicio(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasServicioUsuario()));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }        
    
    public void generar(int tipo){
        //Date fAltaHosp=(oEpisodioMedico.getAltaHospitalaria());

        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fF=new SimpleDateFormat("yyyy-MM-dd");
        
        setFechaI(fI.format(getFechaIni()));
        setFechaF(fF.format(getFechaFin()));
        
        System.out.println("Fecha Inicio: "+getFechaI());
        System.out.println("Fecha Fin: "+getFechaF());
        try {
            lHospitalizacion=oHosp.consultaCODES(getFechaI(), getFechaF(), oASerHRRB.getClave(), tipo);
        } catch (Exception ex) {
            Logger.getLogger(ReporteHospitalizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @return the lHospitalizacion
     */
    public List<Hospitalizacion> getHospitalizacion() {
        return lHospitalizacion;
    }

    /**
     * @param lHospitalizacion the lHospitalizacion to set
     */
    public void setHospitalizacion(List<Hospitalizacion> lHospitalizacion) {
        this.lHospitalizacion = lHospitalizacion;
    }

    /**
     * @return the lFiltroHospitalizacion
     */
    public List<Hospitalizacion> getFiltroHospitalizacion() {
        return lFiltroHospitalizacion;
    }

    /**
     * @param lFiltroHospitalizacion the lFiltroHospitalizacion to set
     */
    public void setFiltroHospitalizacion(List<Hospitalizacion> lFiltroHospitalizacion) {
        this.lFiltroHospitalizacion = lFiltroHospitalizacion;
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
     * @return the oASerHRRB
     */
    public AreaServicioHRRB getASerHRRB() {
        return oASerHRRB;
    }

    /**
     * @param oASerHRRB the oASerHRRB to set
     */
    public void setASerHRRB(AreaServicioHRRB oASerHRRB) {
        this.oASerHRRB = oASerHRRB;
    }    
}
