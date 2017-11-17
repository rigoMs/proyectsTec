
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.enfermeria.HojaEnfermeriaQuirofano;
import mx.gob.hrrb.modelo.enfermeria.reporte.MustraSignosVitalesHojaQx;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javier
 */
@ManagedBean(name="oBuscaHojaQx")
@ViewScoped
public class BuscarHojaEnfermeriaQuirofano implements Serializable {
    private  HojaEnfermeriaQuirofano oBuscaHoja;    
    private HojaEnfermeriaQuirofano[] arrHojas;
    private boolean sRender;
    private ArrayList<MustraSignosVitalesHojaQx> arrMustraSignos;
    
    public BuscarHojaEnfermeriaQuirofano() {
        oBuscaHoja = new  HojaEnfermeriaQuirofano();        
    }
    
    public void buscarHojas(long pf, long pcv,String sNombre, String sApPaterno, String sApMaterno, int nExp){
        try{
            sRender=false;
            oBuscaHoja.getEpisodioMedico().getPaciente().setFolioPaciente(pf);
            oBuscaHoja.getEpisodioMedico().getPaciente().setClaveEpisodio(pcv);
            oBuscaHoja.getEpisodioMedico().getPaciente().setNombres(sNombre);
            oBuscaHoja.getEpisodioMedico().getPaciente().setApPaterno(sApPaterno);
            oBuscaHoja.getEpisodioMedico().getPaciente().setApMaterno(sApMaterno);
            oBuscaHoja.getEpisodioMedico().getPaciente().getExpediente().setNumero(nExp);
            arrHojas = oBuscaHoja.buscarHojaEnfermeriaQxTodas();
            if(arrHojas.length>0){
                RequestContext.getCurrentInstance().execute("PF('hojasQx').show();");
            }else{
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA",oBuscaHoja.getEpisodioMedico().getPaciente().getNombreCompleto()+" no tiene hojas de enfermeria registradas"));
            }
        }catch(Exception e){
            java.util.logging.Logger.getLogger(HojaEnfermeriaQuirofano.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void buscaHojaEnfermeriaQuirurgica(long nIdHoja){
        try{
            oBuscaHoja.setIdHoja(nIdHoja);
            sRender = oBuscaHoja.buscarHojaQx();
            if(sRender){
                arrMustraSignos= new ArrayList<MustraSignosVitalesHojaQx>();              
                RequestContext.getCurrentInstance().execute("PF('hojasQx').hide()");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","  Hoja Encontrada"));
            }else
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","No se encontro informacion para la hoja"));                
        }catch(Exception e){
            java.util.logging.Logger.getLogger(HojaEnfermeriaQuirofano.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public HojaEnfermeriaQuirofano getBuscaHoja() {
        return oBuscaHoja;
    }

    public HojaEnfermeriaQuirofano[] getArrHojas() {
        return arrHojas;
    }

    public boolean getRender() {
        return sRender;
    }
    
    public void setRender(boolean valor){
        this.sRender=valor;
    }
    
    public ArrayList<MustraSignosVitalesHojaQx> getArrMustraSignos() {
        return arrMustraSignos;
    }

}
