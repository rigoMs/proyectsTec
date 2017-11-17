package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.consultaexterna.Horario;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Consultorio;

/**
 *
 * @author pedro
 */
@ManagedBean (name="cHC")
@SessionScoped
public class ConsultaHistorialConsultas implements Serializable{
    private PacienteCapasits oPac=null;
    private Cita oCit=null;
    private Horario oHor=null;
    private Consultorio oConsul=null;
    private Cita [] arrCitas = null;

    public ConsultaHistorialConsultas() {
        oPac=new PacienteCapasits();
        oCit=new Cita();
        oHor=new Horario();
        oConsul=new Consultorio();
    }
    
    public PacienteCapasits getPac(){
         return oPac;
    }
     
    public Cita getCit(){
         return oCit;
    }
    
    public Horario getHor(){
        return oHor;
    }
    
    public Consultorio getConsul(){
        return oConsul;
    }
    
    public Cita[] getListaCitas() throws Exception{
          if(arrCitas==null){
               try{ oCit.setPacienteCapa(oPac);
            arrCitas = (Cita[])oCit.buscarHistorialDeCitasDePaciente();
                   } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
        }
        }
        return arrCitas;
    }
 
      public void bus() throws Exception{
          Date fecha= new Date();
             if(oPac.getIdNacional()<1){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Id invalido"));
        }
         else{
         if(fecha.before(oCit.getFechaIni())){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Fecha de inicio invalida"));
         }   
         else{
         if(oCit.getFechaFin().after(oCit.getFechaIni())){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Fecha final mayor que Fecha de inicio"));
         }
         else{
         if(oPac.buscarIdNacional()){
             arrCitas=null;}
         else{    
          FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El ID no se encuentra en la base de datos"));
         }
         }
         }
        }
        }
    
    public void cierra(){
        oCit=new Cita();
        oPac=new PacienteCapasits();
        oHor=new Horario();
        oConsul=new Consultorio();
        arrCitas=null;
    }
    
}
