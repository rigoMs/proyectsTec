package mx.gob.hrrb.jbs.capasits;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.Seguro;
import org.primefaces.event.RowEditEvent;


@ManagedBean (name="vigencia")
@SessionScoped
public class ConsultaVigencia {
private PacienteCapasits pac=null;
private Seguro segu=null;
private PacienteCapasits[] arrPac = null;
private PacienteCapasits[] arrPacs = null;

    public ConsultaVigencia() {
        pac=new PacienteCapasits();
        segu= new Seguro();
    }
    
    public PacienteCapasits getPacic(){
     return pac;
    }
    
     public Seguro getSeg(){
        return segu;
    }
    
    public PacienteCapasits[] getLista(){
        if (arrPac==null){
        try{
            arrPac = (PacienteCapasits[])pac.buscarVigenciaSPaciente();
                   } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));

        }
         FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","No se encuentra el ID o el paciente no tiene seguro pupular"));
         }
        return arrPac;
    }
    
    public void inicializa(){
     arrPac=null;   
    }
    
     public void onEdit(RowEditEvent event){
        PacienteCapasits paci= (PacienteCapasits) event.getObject();
        String sMsg = ""; 
        Date fecha= new Date();
            SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String f=ff.format(fecha);
            if(fecha.after(paci.getSeg().getVigencia()))
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA DE VIGENCIA INVALIDA"));
            else{
        try{ 
            sMsg =paci.getNombreCompleto(); 
            paci.modificarVigenciaPaciente(); 
        }catch(Exception e){
            e.printStackTrace();
            sMsg = "Error al actualizar bd";
        }
        FacesMessage msg = new FacesMessage("Editado", sMsg);
        FacesContext.getCurrentInstance().addMessage(null, msg);  arrPac=null;  
            }
    }
    
    public void onCancel(RowEditEvent event) throws ParseException {
        FacesMessage msg;msg = new FacesMessage("Cancelado", ((PacienteCapasits) event.getObject()).getNombreCompleto().toString());

        FacesContext.getCurrentInstance().addMessage(null, msg); 
    }
        
}
