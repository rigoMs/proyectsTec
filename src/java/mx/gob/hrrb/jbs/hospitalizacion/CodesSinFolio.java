/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Hospitalizacion;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oSinFolio")
@SessionScoped
public class CodesSinFolio {
    private Hospitalizacion oHosp;
    private List<Hospitalizacion> lHospitalizacion;
    private List<Hospitalizacion> lFiltroHospitalizacion;
    /**
     * Creates a new instance of CodesSinFolio
     */
    public CodesSinFolio() {
        oHosp = new Hospitalizacion();       
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
    
}
