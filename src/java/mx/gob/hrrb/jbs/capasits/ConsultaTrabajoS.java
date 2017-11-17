
package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.CitaTrabajoSocialCapasits;

/**
 *
 * @author pedro
 */
@ManagedBean(name="Trabajos")
@SessionScoped
public class ConsultaTrabajoS implements Serializable{
    private CitaTrabajoSocialCapasits oCiTrab;

    public ConsultaTrabajoS() {
        oCiTrab= new CitaTrabajoSocialCapasits();  
    }
    
    public CitaTrabajoSocialCapasits getTrabajoS(){
        return oCiTrab;
    }
    
    public String recuperaCita(int valor) throws Exception{
        oCiTrab= new CitaTrabajoSocialCapasits();  
        String link="";
        oCiTrab.setFolioCita(valor);
        if(oCiTrab.buscar()) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta Realizada","Para modificar datos dar clic en el boton modificar"));
        }else
        {oCiTrab.buscarPacienteEncita();
        link="/sesiones/capasits/trabajosocial.xhtml";}
        return link;
    }
    
    public String almacena() throws Exception{
        String sRet="";
        int nAfec =0;
         try{ 
            nAfec=this.oCiTrab.insertar(); 
            sRet="/faces/sesiones/capasits/atenderCitasTrabajo.xhtml";
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR","Al insertar"));
         }
         this.oCiTrab=null;
         return sRet;
    }
    
    public String recuperaDato2(int valor) throws Exception{
        String link="";
        oCiTrab= new CitaTrabajoSocialCapasits();
        oCiTrab.setFolioCita(valor);
        if(oCiTrab.buscar()) {  
            oCiTrab.buscarPacienteEncita();
            link="/faces/sesiones/capasits/modificaTrabajoSocial.xhtml";
        }else
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "No sea realizado la consulta","Por lo tanto no se puede modificar"));
        return link;
    }
    
    public String modifica() {
        String link="";
        int nAfec =0;
        try{ 
            this.oCiTrab.modificar(); 
            link="/faces/sesiones/capasits/atenderCitasTrabajo.xhtml";
         } catch(Exception e){
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al modificar"));
         }
        oCiTrab=null;
        return link;
    }
    
    public String link(){
        String link="/faces/sesiones/capasits/atenderCitasTrabajo.xhtml";
        return link;
    }
}
