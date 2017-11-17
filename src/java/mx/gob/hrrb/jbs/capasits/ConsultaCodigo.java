package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;

/**
 *
 * @author pedro
 */
@ManagedBean (name="codigo")
@SessionScoped
public class ConsultaCodigo implements Serializable{
private PacienteCapasits pac=null;
private boolean vis=false;
  
    public ConsultaCodigo() {
         pac=new PacienteCapasits();
    }
    
    public PacienteCapasits getPaci(){
     return pac;
    }
    
    public void bus() throws Exception{
        FacesMessage msg = null;
         FacesContext context = FacesContext.getCurrentInstance();
         if(pac.getIdNacional()<1){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Id invalido"));
             this.setVisible(false); 
         }
         else{    
         if(pac.buscarIdNacional()){
         try{
             this.setVisible(true);
         }catch(Exception e){
         e.printStackTrace();
         }
         }
         else{
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","no se encuentra El Id en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
             this.pac=new PacienteCapasits();
        this.setVisible(false); 
            }      
        } 
    }
     
    public boolean  getVisible(){
        return vis;
    }
    public void setVisible(boolean valor){
      this.vis=valor;  
    }
    
    public void cerrar(){
        pac=new PacienteCapasits();
        setVisible(false);
    }
}
