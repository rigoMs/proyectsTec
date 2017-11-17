
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.enfermeria.reporte.ActividadesTerapiaIntensiva;

/**
 *
 * @author J2GOV
 */
@ManagedBean(name="oRUCI")
@ViewScoped
public class InformeMensualTerapiaIntensiva implements Serializable {

    private boolean bRender;
    private ActividadesTerapiaIntensiva oReporte;
    private ActividadesTerapiaIntensiva[] arrActividades=null;
    
    public InformeMensualTerapiaIntensiva() {
        oReporte = new ActividadesTerapiaIntensiva();
    }
    
    public void validaFechaFinal(){
        if(oReporte.getFechaFin().getTime()<=oReporte.getFechaInicio().getTime()){
            bRender=false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","la fecha final debe ser menor a la fecha inicial"));
        }
    }
    
    public void buscaReporte(){
        if(oReporte.getFechaInicio()!=null
                || oReporte.getFechaFin()!=null){
           if(oReporte.getFechaFin().getTime()<=oReporte.getFechaInicio().getTime()){
               bRender=false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","la fecha final debe ser menor a la fecha inicial"));
            }else{
                try {
                    bRender=true;
                    arrActividades= oReporte.buscaActividadesTerapiaIntensiva();
                } catch (Exception ex) {
                    Logger.getLogger(InformeMensualTerapiaIntensiva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ActividadesTerapiaIntensiva getReporte() {
        return oReporte;
    }

    public ActividadesTerapiaIntensiva[] getArrActividades() {
        return arrActividades;
    }

    public boolean getRender() {
        return bRender;
    }
    
    
    
}
