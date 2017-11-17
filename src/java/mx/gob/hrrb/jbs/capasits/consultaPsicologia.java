package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.CitaPsicologiaCapasits;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.Cita;

/**
 *
 * @author pedro
 */
@ManagedBean (name="psicol")
@SessionScoped
public class consultaPsicologia implements Serializable{
    private CitaPsicologiaCapasits oCPsico;
    private PacienteCapasits oPac;

    
    public consultaPsicologia() {
        oCPsico=new CitaPsicologiaCapasits();
        oPac=new PacienteCapasits();
    }
    
    public PacienteCapasits getCapa(){
        return oPac;
    }
    
    public CitaPsicologiaCapasits getCPsico(){
        return oCPsico;
    }
    
    public String recuperaCita(int valor) throws Exception{
        String link=""; 
         oCPsico=new CitaPsicologiaCapasits();
        oCPsico.setFolioCita(valor);
        if(oCPsico.buscar())
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta Realizada","Para modificar datos dar clic en el boton modificar"));
        else{
        oCPsico.buscarPacienteEncita();
        link="/sesiones/capasits/psicologia.xhtml";}
        return link;
    }
    
    public String recuperaCita2(int valor) throws Exception{
        String link=""; 
        oCPsico=new CitaPsicologiaCapasits();
        oCPsico.setFolioCita(valor);
        if(oCPsico.buscar()) {  
            oCPsico.buscarPacienteEncita();
            link="/faces/sesiones/capasits/modificaPsicologia.xhtml";
        }else
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "No sea realizado la consulta","Por lo tanto no se puede modificar"));
        return link;
    }
    
    public String almacena() throws Exception{
        String sRet="";
        int nAfec =0;
         try{ 
            nAfec=this.oCPsico.insertar(); 
            sRet="/faces/sesiones/capasits/atenderCitasPsico.xhtml";
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al modificar"));
         }
         this.oCPsico=null;
         return sRet;
    }
    
    public String modifica() {
        String link="";
        int nAfec =0;
        try{ 
            this.oCPsico.modificar(); 
            link="/faces/sesiones/capasits/atenderCitasPsico.xhtml";
         } catch(Exception e){
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al modificar"));
         }
        oCPsico=null;
        return link;
    }
    
    public String link(){
        String link="/faces/sesiones/capasits/atenderCitasPsico.xhtml";
        return link;
    }
}
