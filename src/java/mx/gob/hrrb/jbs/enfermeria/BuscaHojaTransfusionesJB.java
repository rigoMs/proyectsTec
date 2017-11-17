
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.enfermeria.HojaDeTransfusion;

/**
 *
 * @author J2GOV
 */
@ManagedBean(name="oBTras")
@ViewScoped
public class BuscaHojaTransfusionesJB implements Serializable{

    private boolean bRender;
    private HojaDeTransfusion oTransfusion;    
    private HojaDeTransfusion[] oTranAgregadaBase;
    
    public BuscaHojaTransfusionesJB() {
        oTransfusion= new HojaDeTransfusion();
    }
    
    public void CargaPacienteEvent( long fp, long cvEp){
        try{
            oTransfusion.getEpisodio().getPaciente().setFolioPaciente(fp);
            oTransfusion.getEpisodio().getPaciente().setClaveEpisodio(cvEp);
            bRender = oTransfusion.getEpisodio().buscarDatosPaciente();            
            if(bRender==true){
                oTranAgregadaBase=oTransfusion.buscarTransfunsionesPaciente();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO"," paciente encontrado"));
            }else{
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Paciente no encontrado")); 
            }
            
        }catch(Exception e){            
            java.util.logging.Logger.getLogger(HojaDeTransfusion.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public boolean getRender() {
        return bRender;
    }

    public HojaDeTransfusion getTransfusionDatos() {
        return oTransfusion;
    }

    public HojaDeTransfusion[] getTranAgregadaBase() {
        return oTranAgregadaBase;
    }
    
}
