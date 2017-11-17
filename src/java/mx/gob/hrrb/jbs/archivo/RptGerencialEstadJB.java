package mx.gob.hrrb.jbs.archivo;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.archivo.RptGerencialEstadistica;

/**
 *
 * @author JDanny
 */
@ManagedBean(name="oRGerencialEstJB")
@ViewScoped
public class RptGerencialEstadJB {
    private RptGerencialEstadistica oReporte;
    private RptGerencialEstadistica[] lLista;
    private boolean bRenderiza;
    
    public RptGerencialEstadJB(){
        oReporte= new RptGerencialEstadistica();
        bRenderiza=false;
    }
    
    public void buscaDatos(){
        String msg="";
        if(oReporte.getFechaIni()!=null && oReporte.getFechaFin()!=null){
            try{
                oReporte.buscaNacimientosAtendidosPartoCesarea();
                oReporte.buscaNacidosVivosPorSemanaGestacional();
                oReporte.buscaDefuncionesFetales();
                oReporte.buscaProcedimientosdePlanificacionFamiliar();
                oReporte.buscaAceptantesDeMetodosAnticonceptivosPostEventoObstetrico();
                lLista=oReporte.buscaReporteEstadisticaCensoGerencial();
                oReporte.buscaEgresosHospSegunMotivo();
                oReporte.buscaRptDefuncionesHospitalarias();
                oReporte.buscaRptCortaEstancia();
                bRenderiza=true;                
            }catch(Exception e){
                bRenderiza=false;
                FacesContext context= FacesContext.getCurrentInstance();
                msg="Error al buscar información";
                context.addMessage(null, new FacesMessage("Reporte Gerencial Estadístico", msg));
            }
        }
        else{
            bRenderiza=false;
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Reporte Gerencial Estadístico "," No ha especificado fecha"));
            }
    }
    
    public void validaFecha(){
        String mess="";
        if (oReporte.getFechaIni() !=null && oReporte.getFechaFin()!=null)
            if (oReporte.getFechaIni().compareTo(oReporte.getFechaFin())>0)
                mess="La fecha final del periodo debe ser posterior a la fecha de inicio";
        if (!mess.equals("")){
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Aviso",mess));
        }
    }
    
    public RptGerencialEstadistica getReporte(){
        return oReporte;
    } 
    
    public boolean getRender(){
        return bRenderiza;
    }

    public RptGerencialEstadistica[] getLista() {
        return lLista;
    }
    
}
