package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.CitaOdontologiaCapasits;
/**
 *
 * @author pedro
 */
@ManagedBean (name="Odonto")
@SessionScoped
public class ConsultaOdontodologia implements Serializable{
     private CitaOdontologiaCapasits oCiOdo;

    public ConsultaOdontodologia() {
        oCiOdo= new CitaOdontologiaCapasits();
    }
        
    public CitaOdontologiaCapasits getOdontologia(){
        return oCiOdo;
    }
    
    public String recuperaCita(int valor) throws Exception{ System.out.println("recupera");
        oCiOdo= new CitaOdontologiaCapasits();
        String link=""; 
        oCiOdo.setFolioCita(valor);
         if(oCiOdo.buscar())
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta Realizada","Para modificar datos dar clic en el boton modificar"));
         else{
         oCiOdo.buscarPacienteEncita();
        link="/sesiones/capasits/odontologia.xhtml";}
        return link;
    }
    
    public String recuperaCita2(int valor) throws Exception{
        String link=""; 
        oCiOdo= new CitaOdontologiaCapasits();
        oCiOdo.setFolioCita(valor);
        if(oCiOdo.buscar()) {  
            oCiOdo.buscarPacienteEncita();
            link="/faces/sesiones/capasits/modificaOdontologia.xhtml";
        }else
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "No sea realizado la consulta","Por lo tanto no se puede modificar"));
        return link;
    }
    
    public String almacena(){ System.out.println("pasa");
        String sRet="";
        int nAfec =0;
         try{ 
            nAfec=this.oCiOdo.insertar(); 
            sRet="/faces/sesiones/capasits/atenderCitasOdonto.xhtml";
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR","Al insertar"));
         }
         this.oCiOdo=null;
         return sRet;
    }
    
    public String modifica() {
        String link="";
        int nAfec =0;
        try{ 
            this.oCiOdo.modificar(); 
            link="/faces/sesiones/capasits/atenderCitasOdonto.xhtml";
         } catch(Exception e){
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al modificar"));
         }
        oCiOdo=null;
        return link;
    }
    
    public String link(){
        String link="/faces/sesiones/capasits/atenderCitasOdonto.xhtml";
        return link;
    }
    
}