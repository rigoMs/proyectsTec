
package mx.gob.hrrb.jbs.enfermeria;
import java.io.Serializable;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import org.primefaces.context.RequestContext;
/**
 * @objetivo: bean para buscar la informacion de un paciente para agregar una transfusion
 * @author : Javier
 * @version 1.0
 */
@ManagedBean(name="oIden")
@ViewScoped
public class identificacionPaciente implements Serializable{
        
    private EpisodioMedico oPacSer;   
    
    public identificacionPaciente() {       
        oPacSer = new EpisodioMedico();        
    }
        
    public void cargaPaciente(int f){
        try{
            oPacSer.getPaciente().setFolioPaciente(f);
            if(oPacSer.buscaIdentificacionPac()){                
                RequestContext.getCurrentInstance().execute("PF('imprimirIdentificacion').show()");
                RequestContext.getCurrentInstance().update("imp:print");
            }else{
                oPacSer = new EpisodioMedico();
                String erro="NO SE ENCUENTRA";
                String err=" INFORMACION";
                oPacSer.getPaciente().setNombres(erro);
                oPacSer.getPaciente().setApPaterno(err);
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Paciente no encontrado"));
            }
        }catch(Exception e){
            java.util.logging.Logger.getLogger(EpisodioMedico.class.getName()).log(Level.SEVERE,null,e);            
        }   
    }
    
    public void cerraDialog(ActionEvent e){
        RequestContext.getCurrentInstance().execute("PF('imprimirIdentificacion').hide()");
    }    
    
    public EpisodioMedico getEpisodio(){
        return oPacSer;
    }
    
}
