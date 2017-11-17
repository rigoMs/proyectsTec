package mx.gob.hrrb.jbs.caja;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.core.Paciente;
import org.primefaces.context.RequestContext;

/**
 *
 * @author BAOZ
 */
@ManagedBean(name = "oAutReintegroJB")
@ViewScoped
public class AutorizarReintegroJB implements Serializable{
private Paciente oPaciente;
private boolean bEncontrado = false;
    public AutorizarReintegroJB() {
        oPaciente = new Paciente();
    }
    
    public void llenar(){
    Paciente oPac=null;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        
        oPac=(Paciente)session.getAttribute("oPacienteSeleccionado");
        if(oPac == null || oPac.getFolioPaciente()==0){
            FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡No ha seleccionado un paciente!");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bEncontrado = false;
        }else{
            System.out.println(oPac.getNombreCompleto());
            oPaciente = oPac;
            bEncontrado = true;
        }
    }
    
    public void limpiar(){
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
        if (this.bEncontrado)
            return "visible";
        else
            return "hidden";
    }
}
