package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.CitamedicinaGeneralCapasits;
import mx.gob.hrrb.modelo.capasits.NotaMedica;

/**
 *
 * @author pedro
 */
@ManagedBean (name="cExterna")
@SessionScoped
public class ConsultaCExterna implements Serializable{
    public CitamedicinaGeneralCapasits oCitameGe;
    public NotaMedica oNoMe;
    public boolean ban=false;

    public ConsultaCExterna() {
        oCitameGe=new CitamedicinaGeneralCapasits();
        oNoMe= new NotaMedica(); 
    }
    
    public CitamedicinaGeneralCapasits getExterna(){
        return oCitameGe;        
    }
    
    public NotaMedica getNota(){
        return oNoMe;
    }
    
    public String recuperaCita(int valor) throws Exception{
        oCitameGe= new  CitamedicinaGeneralCapasits();  
        String link=""; 
        oCitameGe.setFolioCita(valor);
        if(oCitameGe.buscar()) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta Realizada","Para modificar datos dar clic en el boton modificar"));
        }else{
        oCitameGe.buscarPacienteEncita();
        oNoMe.setFolioPaciente(oCitameGe.getPacienteCapa().getFolioPaciente());
        NotaPac();
        link="/sesiones/capasits/consultaexterna.xhtml";}
        return link;
    }
    
    public String recuperaDato2(int valor) throws Exception{
        String link="";
        oCitameGe= new  CitamedicinaGeneralCapasits();  
        oCitameGe.setFolioCita(valor);
        if(oCitameGe.buscar()) {  
            oCitameGe.buscarPacienteEncita();
            link="/faces/sesiones/capasits/modificaConsultaExterna.xhtml";
        }else
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "No sea realizado la consulta","Por lo tanto no se puede modificar"));
        return link;
    }
    
    public String almacena() throws Exception{
        String sRet="";
        int nAfec =0;
         try{ 
            nAfec=this.oCitameGe.insertar(); 
            sRet="/faces/sesiones/capasits/atenderCitasExterna.xhtml";
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.oCitameGe=null;
         return sRet;
    }
    
    public String modifica() {
        String link="";
        int nAfec =0;
        try{ 
            this.oCitameGe.modificar(); 
            link="/faces/sesiones/capasits/atenderCitasExterna.xhtml";
         } catch(Exception e){
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al modificar"));
         }
        oCitameGe=null;
        return link;
    }
    
    public String link(){
        String link="/faces/sesiones/capasits/atenderCitasExterna.xhtml";
        return link;
    }
    
    public void NotaPac() throws Exception{
        ban=oNoMe.buscar();
    }
    
    public boolean getVisible(){
        return ban;
    }
}
