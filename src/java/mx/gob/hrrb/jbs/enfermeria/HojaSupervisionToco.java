
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.enfermeria.HojaSupervisionTococirugia;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;

/**
 * @objetivo : informacion de pacientes en tococirugia para agregar procedimientos de enfermeria
 * @author : Javier
 * @version : 1.0
 */
@ManagedBean(name="oTocoPac")
@ViewScoped
public class HojaSupervisionToco implements Serializable {
    
    private int nArea;    
    private HojaSupervisionTococirugia arrPacToco[]=null, oToco;   
    
    public HojaSupervisionToco() {
        oToco = new HojaSupervisionTococirugia();
        nArea=69;        
        try{               
            arrPacToco = oToco.BuscarPacientesToco(nArea);
        }catch(Exception ex){                
            Logger.getLogger(HojaSupervisionToco.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void buscarPaciente( long pfp, long pcve){
        try {
            if(BuscaPacienteCargado(pfp,pcve)){
                RequestContext.getCurrentInstance().execute("PF('tocoProcAgre').show()");
            }else{
                RequestContext.getCurrentInstance().update(""); 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","Paciente no encontrado"));
            }
        } catch (Exception ex) {
            Logger.getLogger(HojaSupervisionToco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean BuscaPacienteCargado(long fp, long cve){
        boolean bRet=false;
        try{
            for(HojaSupervisionTococirugia oTocoo: arrPacToco ){
                if(oTocoo.getEpisodio().getPaciente().getFolioPaciente()==fp 
                        && oTocoo.getEpisodio().getPaciente().getClaveEpisodio()==cve){
                    oToco.getEpisodio().getPaciente().setFolioPaciente(oTocoo.getEpisodio().getPaciente().getFolioPaciente());
                    oToco.getEpisodio().getPaciente().setClaveEpisodio(oTocoo.getEpisodio().getPaciente().getClaveEpisodio());
                    oToco.getEpisodio().getPaciente().setNombres(oTocoo.getEpisodio().getPaciente().getNombres());
                    oToco.getEpisodio().getPaciente().setApPaterno(oTocoo.getEpisodio().getPaciente().getApPaterno());
                    oToco.getEpisodio().getPaciente().setApMaterno(oTocoo.getEpisodio().getPaciente().getApMaterno());
                    oToco.getEpisodio().getCama().setNumero(oTocoo.getEpisodio().getCama().getNumero());
                    oToco.getEpisodio().getPaciente().getExpediente().setNumero(oTocoo.getEpisodio().getPaciente().getExpediente().getNumero());
                    oToco.setInduccion(oTocoo.isInduccion());
                    oToco.setUs(oTocoo.isUs());
                    oToco.setRct(oTocoo.isRct());
                    oToco.setObservacion(oTocoo.getObservacion());
                    oToco.getProcedimiento().getCIE9().setDescripcion(oTocoo.getProcedimiento().getCIE9().getDescripcion());
                    oToco.getProcedimiento().getCIE9().setClave(oTocoo.getProcedimiento().getCIE9().getClave());
                    oToco.setIdHojaSuper(oTocoo.getIdHojaSuper());
                    bRet=true;
                    break;
                }
            }
        }catch(Exception e){
            Logger.getLogger(HojaSupervisionToco.class.getName()).log(Level.SEVERE, null, e);
        }
        return bRet;
    }
   
    public void guardarProcedimientoToco(){
        if(this.getTococirugia().isInduccion()==false
                && this.getTococirugia().isRct()==false
                && this.getTococirugia().isUs()==false){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","Dedes de seleccionar al menos un proceimiento"));
        }else{
            try{
                if(oToco.getIdHojaSuper()==0){
                   if(oToco.insertarHojaSupervisionTococirugia()==1){                
                        arrPacToco = oToco.BuscarPacientesToco(nArea);
                        RequestContext.getCurrentInstance().execute("PF('tocoProcAgre').hide()");
                    }   
                }else if(oToco.getIdHojaSuper()!=0){
                    if(oToco.modificarHojaSupervisionTococirugia()==1){
                        arrPacToco = oToco.BuscarPacientesToco(nArea);
                        RequestContext.getCurrentInstance().execute("PF('tocoProcAgre').hide()");
                    }
                }  
            }catch(Exception ex){
                Logger.getLogger(HojaSupervisionToco.class.getName()).log(Level.SEVERE, null, ex);
            }
        }            
    }
    
    public HojaSupervisionTococirugia[] getPacienteToco(){
        return arrPacToco;
    }
    
    public HojaSupervisionTococirugia getTococirugia(){
        return oToco;
    }
}
