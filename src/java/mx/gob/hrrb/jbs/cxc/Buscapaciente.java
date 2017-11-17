package mx.gob.hrrb.jbs.cxc;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import org.primefaces.context.RequestContext; 

@ManagedBean(name = "oBuscaPaci")
@ViewScoped
 
public class Buscapaciente implements Serializable {
    private Paciente oPaciente;
    private boolean bEncontrado = false;
    private PersonalHospitalario oPersonalFirmado;
    private String sUsuario; 
    
     public Buscapaciente() throws Exception{
        oPaciente = new Paciente();
            HttpServletRequest req;
       oPersonalFirmado=new PersonalHospitalario();
	req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                 oPersonalFirmado.getUsuar().setIdUsuario(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());                
            }
    }
      public void llenar() throws Exception{
    Paciente oPac=null;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        oPac=(Paciente)session.getAttribute("pacienteselecc");
        if(oPac == null){
            FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡No ha seleccionado un paciente!");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bEncontrado = false; 
        }else{ 
            oPaciente = oPac; 
             
            bEncontrado = true;
            limpiar();
        }
    }
      
      
    
    
    public void limpiar(){
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        session.setAttribute("oPacienteSeleccionado", null); 
    }
    
     public void limpiaboton(){
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        session.setAttribute("oPacienteSeleccionado", null);
        bEncontrado = false;
    }
    
    public Paciente getPaciente(){
        return this.oPaciente;
    }
    
    public boolean getEncontrado(){
        return this.bEncontrado;
    }
    
    public String getVisible(){
        if (this.bEncontrado){
            return "visible";}
        else{ 
            return "hidden";
            
        }
    }
    
 
}
