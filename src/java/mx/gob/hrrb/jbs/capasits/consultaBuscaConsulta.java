package mx.gob.hrrb.jbs.capasits;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.Cita;

/**
 *
 * @author pedro
 */
@ManagedBean (name="buscons")
@SessionScoped
public class consultaBuscaConsulta {
private PacienteCapasits oPac=null;
private Cita oCit=null;
private boolean vis=false;
private Firmado oFirm;
private HttpServletRequest httpServletRequest;
private FacesContext faceContext;
private String sUsuario;
private Cita [] arrCitas = null;
  
    public consultaBuscaConsulta() {
        oPac=new PacienteCapasits();
        oCit=new Cita();
        oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
    }
    
     public PacienteCapasits getPaci(){
         return oPac;
    }
     
     public Cita getCit(){
         return oCit;
     }
     
      public Cita[] getListaCitas() throws Exception{
          if(arrCitas==null){
               oCit.buscarUsuarioMedico(sUsuario);
               try{ oCit.setPacienteCapa(oPac);
            arrCitas = (Cita[])oCit.buscarCitaDePaciente();
                   } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
        }
        }
        return arrCitas;
    }
 
      public void bus() throws Exception{
             if(oPac.getIdNacional()<1){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Id invalido"));
        }
         else{ 
         if(oPac.buscarIdNacional()){
             arrCitas=null;}
         else{    
          FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El ID no se encuentra en la base de datos"));
         }
        }
        }
      
      public void cierra(){
        oCit=new Cita();
        oPac=new PacienteCapasits();
        arrCitas=null;
    }
     
}
