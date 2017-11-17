package mx.gob.hrrb.jbs.capasits;

import java.text.ParseException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author pedro
 */
@ManagedBean (name="vigseg")
@SessionScoped
public class ConsultaVigenciaTodos {
private PacienteCapasits pac=null;
private PacienteCapasits[] arrPacs = null;
   
    public ConsultaVigenciaTodos() {
         pac=new PacienteCapasits();
    }
    
    public PacienteCapasits getPacic(){
     return pac;
    }
    
    public PacienteCapasits[] getListaVigencia(){
        try{
            arrPacs = (PacienteCapasits[])pac.buscarTodosVigenciaSPaciente();
                   } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));

        }
        return arrPacs;
    }
    
     public void onEdit(RowEditEvent event){
        PacienteCapasits paci= (PacienteCapasits) event.getObject();
        String sMsg = ""; 
        try{ 
            sMsg =paci.getNombreCompleto(); 
            paci.modificarVigenciaPaciente(); 
        }catch(Exception e){
            e.printStackTrace();
            sMsg = "Error al actualizar bd";
        }
        FacesMessage msg = new FacesMessage("Editado", sMsg);
        FacesContext.getCurrentInstance().addMessage(null, msg);        
    }
    
    public void onCancel(RowEditEvent event) throws ParseException {
        FacesMessage msg;msg = new FacesMessage("Cancelado", ((PacienteCapasits) event.getObject()).getNombreCompleto().toString());

        FacesContext.getCurrentInstance().addMessage(null, msg); 
    }
    
}
