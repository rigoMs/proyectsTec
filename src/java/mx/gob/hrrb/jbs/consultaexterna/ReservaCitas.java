/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.EpisodioMedico;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oResCita")
@SessionScoped
public class ReservaCitas {
private Cita oCita;
private EpisodioMedico oEP;
    /**
     * Creates a new instance of ReservaCitas
     */
    public ReservaCitas() {
        oCita=new Cita();
        oEP=new EpisodioMedico();
    }

    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }

    public EpisodioMedico getEP() {
        return oEP;
    }

    public void setEP(EpisodioMedico oEP) {
        this.oEP = oEP;
    }
    
    public boolean direccion(int area, long folio) throws Exception{
        int n=oEP.buscaTipoEPis(area, folio, 2);

        if (n==5){
            oEP.setPrimeraVezHRRB(false);
            oEP.setPrimeraVezEsp(false);
        }
        else{
            oEP.setPrimeraVezHRRB(true);
            oEP.setPrimeraVezEsp(true);
        }
        return oEP.getPrimeraVezEsp();
    }
}
