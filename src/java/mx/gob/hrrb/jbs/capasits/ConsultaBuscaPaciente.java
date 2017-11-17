package mx.gob.hrrb.jbs.capasits;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;

/**
 *
 * @author pedro
 */
@ManagedBean (name="buscap")
@RequestScoped
public class ConsultaBuscaPaciente {
private PacienteCapasits pac=null;
private boolean vis=false;

    public ConsultaBuscaPaciente() {
        pac=new PacienteCapasits();
    }
    
    public PacienteCapasits getPaci(){
     return pac;
    }
    
    public void bus() throws Exception{
        FacesMessage msg = null;
         FacesContext context = FacesContext.getCurrentInstance();
         if(pac.getIdNacional()<1){System.out.println(pac.getIdNacional());
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Id invalido"));
         }
         else{    
         if(pac.buscarIdNacional()){
         try{ this.setVisible(true);
         }catch(Exception e){
         e.printStackTrace();
         FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
         }
         }
         else{
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","no se encuentra El Id en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
             this.pac=new PacienteCapasits();
            }      
        } 
    }
    
    public boolean  getVisible(){
        return vis;
    }
    public void setVisible(boolean valor){
      this.vis=valor;  
    }
    
    public String borrar(){
        String link ="/faces/sesiones/capasits/buscaPacienteCapasits.xhtml";
        vis=false;
        this.pac=new PacienteCapasits();
     return link;   
    }
    
    public String borrar2(){
        String link ="/faces/sesiones/capasits/modificaPaciente.xhtml";
        vis=false;
        this.pac=new PacienteCapasits();
     return link;   
    }
}
