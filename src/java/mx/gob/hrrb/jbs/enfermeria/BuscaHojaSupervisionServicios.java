
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.enfermeria.reporte.ImprimeHojaSupervisionServciosHosp;

/**
 * @Objetivo : 
 * @author : Javier
 * @version : 1.0
 */
@ManagedBean(name="oConSer")
@ViewScoped
public class BuscaHojaSupervisionServicios implements Serializable  {
    
    private boolean bRendering;
    private String sServicio;
    private ImprimeHojaSupervisionServciosHosp arrProcesAgregados[], oDeSup;
    private ArrayList<AreaServicioHRRB> arrServicio;
    
    public BuscaHojaSupervisionServicios() {
        oDeSup= new ImprimeHojaSupervisionServciosHosp();
        arrServicio= oDeSup.getDetalleSer().getCabeceraSupervision().buscarServiciosTodosHojaSup();
    }

    public void buscaProcedimientosHojaSupervisionServicios() throws Exception{
        if(this.getBuscaDetalleSupervision().getDetalleSer().getCabeceraSupervision().getAreaServicio().getClave()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Seleccione un Servicio"));
        }else if(this.getBuscaDetalleSupervision().getDetalleSer().getCabeceraSupervision().getFechaSupervicion()==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona una fecha"));
        }else{
            try {
                arrProcesAgregados = oDeSup.buscarProcedimientosEnfermeriaAplicadosServicios();
                if(arrProcesAgregados!=null && arrProcesAgregados.length>0){
                    oDeSup.getDetalleSer().getCabeceraSupervision().buscarCabeceraSupervisionServicios();
                    buscaNombreServio(this.getBuscaDetalleSupervision().getDetalleSer().getCabeceraSupervision().getAreaServicio().getClave());  
                    bRendering=true;
                }else{
                    bRendering=false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","No se encuentra informacion para la hoja de supervision"));
                }               
            } catch (Exception ex) {
                Logger.getLogger(BuscaHojaSupervisionServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }           
    
    public void busca()throws Exception{
        this.buscaNombreServio(this.getBuscaDetalleSupervision().getDetalleSer().getCabeceraSupervision().getAreaServicio().getClave());
    }    
    
    public void buscaNombreServio(int nArea){
        for(AreaServicioHRRB oSer: getArrServicio()){
            if(oSer.getClave()==nArea){
                sServicio=oSer.getDescripcion();
                break;
            }
        }
    } 
    
    public ArrayList<AreaServicioHRRB> getArrServicio() {
        return arrServicio;
    }
    
    public ImprimeHojaSupervisionServciosHosp[] getArrProcesAgregados() {
        return arrProcesAgregados;
    }

    public ImprimeHojaSupervisionServciosHosp getBuscaDetalleSupervision() {
        return oDeSup;
    }

    public String getServicio() {
        return sServicio;
    }

    public boolean getRendering() {
        return bRendering;
    }      
    
}
