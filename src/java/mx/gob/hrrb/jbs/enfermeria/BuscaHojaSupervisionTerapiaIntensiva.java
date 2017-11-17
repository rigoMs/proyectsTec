
package mx.gob.hrrb.jbs.enfermeria;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.enfermeria.reporte.ImprimeHojaSupervisionServciosHosp;


@ManagedBean(name="oConcUCI")
@ViewScoped
public class BuscaHojaSupervisionTerapiaIntensiva {
    
    private boolean bRender;
    private String sServicio;
    private ImprimeHojaSupervisionServciosHosp oImprimeHojaUCI;
    private ArrayList<AreaServicioHRRB> arrServicio;   
    private ImprimeHojaSupervisionServciosHosp[] arrImprime=null;
    
    public BuscaHojaSupervisionTerapiaIntensiva() {
        oImprimeHojaUCI= new ImprimeHojaSupervisionServciosHosp();        
        bRender=false;        
        arrServicio=oImprimeHojaUCI.getDetalleUCI().getCabeceraSupervisionUCI().buscaAreaServiciosTerapiaIntensiva();
    }
    
    public void buscaProcedimientosHojaTerapiaIntensiva(){
        if(this.getImprimeHojaUCI().getDetalleUCI().getCabeceraSupervisionUCI().getArea().getClave()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona un Servicio"));
        }else if(this.getImprimeHojaUCI().getDetalleUCI().getCabeceraSupervisionUCI().getFechaSupervision()==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona una fecha"));
        }else{
            try {
                arrImprime= this.getImprimeHojaUCI().buscaProcedimientosAplicadosPorFechaYServicioTerapiaIntensiva();
                if(arrImprime.length>0){
                    sServicio=getBuscaServicio(this.getImprimeHojaUCI().getDetalleUCI().getCabeceraSupervisionUCI().getArea().getClave());
                    bRender=true;                   
                }else{
                    bRender=false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se encuentra informacion para la hoja")); 
                }
            } catch (Exception ex) {
                Logger.getLogger(BuscaHojaSupervisionTerapiaIntensiva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ImprimeHojaSupervisionServciosHosp getImprimeHojaUCI() {
        return oImprimeHojaUCI;
    }

    public ArrayList<AreaServicioHRRB> getArrServicio() {
        return arrServicio;
    }
    
    public boolean getRender() {
        return bRender;
    }

    public ImprimeHojaSupervisionServciosHosp[] getArrImprime() {
        return arrImprime;
    }
     
    public String getServicio() {
        return sServicio;
    }
    
    public String getBuscaServicio(int cve){
        String sRt="";
        for(AreaServicioHRRB oA:this.getArrServicio()){
            if(oA.getClave()==cve){
                sRt= oA.getDescripcion();
                break;
            }
        }
        return sRt;
    }    
    
}
