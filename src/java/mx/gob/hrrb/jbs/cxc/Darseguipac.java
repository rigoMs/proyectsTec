 
package mx.gob.hrrb.jbs.cxc;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.Hospitalizacion;

@ManagedBean(name="ReptSegiPac")
@ViewScoped 
public class Darseguipac implements Serializable {
    private Date dFechaIni;
    private Date dFechaFin; 
    private Hospitalizacion oHosp;

public Darseguipac(){
        oHosp = new Hospitalizacion();
        dFechaIni = null;
        dFechaFin = null;
}
 
    public Date getFechaIni() {
        return dFechaIni;
    }
 
    public void setFechaIni(Date dFechaIni) {
        this.dFechaIni = dFechaIni;
    }
 
    public Date getFechaFin() {
        return dFechaFin;
    }
 
    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }
 
    public Hospitalizacion getHosp() {
        return oHosp;
    }
 
    public void setoHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }
}
