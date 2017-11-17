package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Medico;


/**
 *
 * @author pedro
 */
@ManagedBean (name="busMed")
@SessionScoped
public class ConsultaBuscaMedico implements Serializable{
    private Medico oMed;
    
    private boolean ban=false;


    public ConsultaBuscaMedico() {
        oMed=new Medico();
    }
    
    public Medico getMed() {
        return oMed;
    }
    
    public void busca() throws Exception{
        if(oMed.getNoTarjeta()<1){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Número de tarjeta invalido"));
        }
        else{
            if(oMed.buscarMedicoCapasits()){
                ban=true;
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","No se encuentra el número de tarjeta en la base de datos"));
            }
        }
    }
    
    public boolean getVisible(){
        return ban;
    }
    
    public String borrar(){
        ban=false; 
        String link ="/faces/sesiones/capasits/modificaMedico.xhtml";
        oMed=new Medico();
        return link;
    }
}
