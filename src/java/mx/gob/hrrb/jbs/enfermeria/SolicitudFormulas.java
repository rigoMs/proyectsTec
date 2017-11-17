
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.enfermeria.DetalleSolicitudFormula;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.enfermeria.CabeceraSolicitudFormula;
import org.primefaces.context.RequestContext;
/**
 *
 * @author Javier
 */
@ManagedBean(name="oSolicitudFormula")
@ViewScoped
public class SolicitudFormulas implements Serializable {
    
    private int nArea;
    private String sUsuario;
    private String sServicio;
    private String sServicio2;
    private String sPersonalHospitalario;
    private String sFechaSolicitud;
    protected HttpServletRequest httpServletRequest;
    protected FacesContext faceContext;
    private Usuario oUsuario; 
    private Firmado oFirm;
    private DetalleSolicitudFormula oSolicitud, oPac;
    private Parametrizacion  oFormulas;
    private PersonalHospitalario oPersonal;
    private EpisodioMedico[] arrPacientes=null;    
    private DetalleSolicitudFormula[] arrFormulaPac=null;    
    private DetalleSolicitudFormula[] arrPacientesSolicitud=null;
    private DetalleSolicitudFormula oModificar;
    private List<Parametrizacion> lListaFormulas; 
    private static final String sTabla="TAH";   
    private List<CabeceraSolicitudFormula> arrServicios;
    
    public SolicitudFormulas(){       
                             
        oSolicitud = new DetalleSolicitudFormula();
        oModificar = new DetalleSolicitudFormula();
        oPac= new DetalleSolicitudFormula();
        oPersonal = new PersonalHospitalario();       
        cargaDatosNecesarios();        
        
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
        {
        oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
        sUsuario=oFirm.getUsu().getIdUsuario();            
        } 
        try{      
            oPersonal.setUsuar(new Usuario());
            oPersonal.getUsuar().setIdUsuario(sUsuario);
            oPersonal.buscaPersonalHospitalarioDatos();    
        }catch(Exception e){ Logger.getLogger(EpisodioMedico.class.getName()).log(Level.SEVERE,null,e);}
    }    
    
    private void cargaDatosNecesarios(){
        try {
            lListaFormulas = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion()).buscarTabla(sTabla)));
            arrServicios= new ArrayList<CabeceraSolicitudFormula>(Arrays.asList((new CabeceraSolicitudFormula()).buscaServiciosTodos()));
        } catch (Exception ex) {
            Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaPacientesEnServicioSeleccionado(){
        try {
            arrPacientes= oPac.getEpisodio().buscarPacientesEnServicio(nArea);
            oPac.getCabeceraSolicitudFormula().getServicio().setClave(nArea);
            arrPacientesSolicitud = oPac.buscaPacientesParaSolictudFormula();
            buscaNonbreServicio();
        } catch (Exception ex) {
            Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
        }
        oSolicitud.getCabeceraSolicitudFormula().setIdSolicitudFormula(0);
    }
    
    public void buscaNonbreServicio(){
        for (CabeceraSolicitudFormula arrServicio : arrServicios) {
            if (arrServicio.getServicio().getClave() == nArea) {
                sServicio = arrServicio.getServicio().getDescripcion();
                break;
            }                
        }
    }
    
    
    public void buscarPaciente(long fp, long cve){              
        try{
            if(buscaPaciente(fp,cve)){                
                this.getSolicitudFormula().getTipoFormula().setClaveParametro("");
                this.getSolicitudFormula().setNoTomas(0);
                this.getSolicitudFormula().setCantidad(0);
                RequestContext.getCurrentInstance().execute("PF('registrarSolic').show()");
            }            
        }catch(Exception e){
            Logger.getLogger(Parametrizacion.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void buscarPacienteDatos(long fp, long pcve){        
        oSolicitud.getEpisodio().getPaciente().setClaveEpisodio(pcve);
        oSolicitud.getEpisodio().getPaciente().setFolioPaciente(fp);        
        try {
            if(oSolicitud.getEpisodio().buscarDatosPaciente()){                
                this.getSolicitudFormula().getTipoFormula().setClaveParametro("");
                this.getSolicitudFormula().setNoTomas(0);
                this.getSolicitudFormula().setCantidad(0);
                RequestContext.getCurrentInstance().execute("PF('registrarSolic').show()");
            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean buscaPaciente( long fp, long cve){
        boolean bRtn=false;
        for(EpisodioMedico ep :arrPacientes){
            if(ep.getPaciente().getFolioPaciente()==fp && ep.getPaciente().getClaveEpisodio()==cve){                
                try {
                    oSolicitud.getEpisodio().getPaciente().setFolioPaciente(ep.getPaciente().getFolioPaciente());
                    oSolicitud.getEpisodio().getPaciente().setClaveEpisodio(ep.getPaciente().getClaveEpisodio());
                    oSolicitud.getEpisodio().getPaciente().getExpediente().setNumero(ep.getPaciente().getExpediente().getNumero());
                    oSolicitud.getEpisodio().getPaciente().setNombres(ep.getPaciente().getNombres());
                    oSolicitud.getEpisodio().getPaciente().setApPaterno(ep.getPaciente().getApPaterno());
                    oSolicitud.getEpisodio().getPaciente().setApMaterno(ep.getPaciente().getApMaterno());                    
                } catch (Exception ex) {
                    Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
                }
                bRtn= true;
                break;                
            }
        }
        return bRtn;
    }
    
    public void guardaSolicitudFormulaEvento(ActionEvent ae) {
        try {
            guardarSolicitudFormula();
        } catch (Exception ex) {
            Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardarSolicitudFormula(){
        String sVal="";
        if(oSolicitud.getTipoFormula().getClaveParametro()==null || oSolicitud.getTipoFormula().getClaveParametro().equals("")){
            sVal+="01";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona una Formula"));
        }       
        if(oSolicitud.getCantidad()==0){
            sVal+="02";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","ingresa una cantidad"));
        }
        if(oSolicitud.getNoTomas()==0){
            sVal+="03";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","ingresa el numero de tomas"));
        }
        
        if(sVal.equals("")){  
            oSolicitud.getTipoFormula().setTipoParametro(sTabla);
            oSolicitud.getCabeceraSolicitudFormula().getServicio().setClave(nArea);
            oSolicitud.getCabeceraSolicitudFormula().getTurno().setClave(getDeterminaTurno());            
            try {
                if(oSolicitud.getCabeceraSolicitudFormula().getIdSolicitudFormula()==0){  
                    if(oSolicitud.getCabeceraSolicitudFormula().buscarCabeceraSolicitudFormulas()){
                       if(oSolicitud.insertarDetalleSolicitudFormula()==1){
                            oPac.getCabeceraSolicitudFormula().getServicio().setClave(nArea);
                            arrPacientesSolicitud = oPac.buscaPacientesParaSolictudFormula();
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Formula Guardada"));
                            RequestContext.getCurrentInstance().execute("PF('registrarSolic').hide()");                            
                            this.getSolicitudFormula().getTipoFormula().setClaveParametro("");
                       } 
                    }else{
                        if(oSolicitud.getCabeceraSolicitudFormula().insertarCabeceraSolicitudFormula()==1){
                         if(oSolicitud.insertarDetalleSolicitudFormula()==1){
                             oPac.getCabeceraSolicitudFormula().getServicio().setClave(nArea);
                             arrPacientesSolicitud = oPac.buscaPacientesParaSolictudFormula();
                             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Formula Guardada"));
                             RequestContext.getCurrentInstance().execute("PF('registrarSolic').hide()");
                             this.getSolicitudFormula().getTipoFormula().setClaveParametro("");
                         }else{
                             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal","No se puedo Guardar"));
                         }
                      }                        
                    }                     
                }else if (oSolicitud.getCabeceraSolicitudFormula().getIdSolicitudFormula()!=0){                    
                    if(oSolicitud.insertarDetalleSolicitudFormula()==1){
                        oPac.getCabeceraSolicitudFormula().getServicio().setClave(nArea);
                        arrPacientesSolicitud = oPac.buscaPacientesParaSolictudFormula();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Formula Guardada"));
                        RequestContext.getCurrentInstance().execute("PF('registrarSolic').hide()");
                        this.getSolicitudFormula().getTipoFormula().setClaveParametro("");
                  }   
                }                
            } catch (Exception ex) {
                Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
            }           
        }        
    } 
    
    public void buscarSolicitudFormulaDelDia(){
        try {
            oPac.getCabeceraSolicitudFormula().getServicio().setClave(nArea);
            arrFormulaPac= oPac.buscarDetalleSolicitudFormulasPorFechaYServicio();            
            if(this.getArrFormulaPac().length>0){ 
                sServicio2 = arrFormulaPac[0].getCabeceraSolicitudFormula().getServicio().getDescripcion();
                sPersonalHospitalario=arrFormulaPac[0].getCabeceraSolicitudFormula().getPersonal().getNombreCompleto();
                sFechaSolicitud=arrFormulaPac[0].getCabeceraSolicitudFormula().getFechaString2();
                RequestContext.getCurrentInstance().execute("PF('FormulaPrint2').show()");  
                RequestContext.getCurrentInstance().update("imprime2");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","Solicitud de Formula vacia"));
            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaPacienteModificarFormula(long nfolio,long nCve,long nIdd, long nIdCb){
        try {
            oModificar.getCabeceraSolicitudFormula().setIdSolicitudFormula(nIdCb);
            oModificar.setIdDetalleFormula(nIdd);
            oModificar.getEpisodio().getPaciente().setFolioPaciente(nfolio);
            oModificar.getEpisodio().getPaciente().setClaveEpisodio(nCve);
            if(oModificar.buscarFormulaPaciente()){
                RequestContext.getCurrentInstance().execute("PF('ModificarSolicitud').show()");
            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modificarSolicitudFormulaPaciente(){
       boolean bBan=true;
       if(this.getModificarSolicitud().getTipoFormula().getClaveParametro().equals("")){
           bBan=false;
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona una Formula"));
       }
       if(this.getModificarSolicitud().getCantidad()==0){
           bBan=false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","ingresa una cantidad"));
       }
       if(this.getModificarSolicitud().getNoTomas()==0){
           bBan=false;
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","ingresa el numero de tomas"));
       }       
       if(bBan){
           try {
               if(this.getModificarSolicitud().modificarFormulaPaciente()==1){
                  oPac.getCabeceraSolicitudFormula().getServicio().setClave(nArea);
                  arrPacientesSolicitud = oPac.buscaPacientesParaSolictudFormula();
                  RequestContext.getCurrentInstance().execute("PF('ModificarSolicitud').hide()");
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Se Modifica La Formula Correctamente"));
               }
           } catch (Exception ex) {
               Logger.getLogger(SolicitudFormulas.class.getName()).log(Level.SEVERE, null, ex);
           }
       }      
    }
    
    public String getDeterminaTurno(){ 
        Date dFechaActual2= new Date();
        SimpleDateFormat hf= new SimpleDateFormat("HH.mm");
         String sTurno="";
         String sT=hf.format(dFechaActual2);
         Double horaActual=Double.parseDouble(sT);
         sTurno= (horaActual>=07.00 && horaActual<15.00)?"MAT": (horaActual >=15.00 && horaActual < 21.00)?"VES":"NOC";        
         return sTurno;
     }   
    
    public int getAreaServicio(){
        return nArea;
    }
    
    public void setAreaServicio(int value){
        this.nArea=value;
    }
    public EpisodioMedico[] getArrayPacientes(){
        return arrPacientes;
    }
    
    public DetalleSolicitudFormula getSolicitudFormula(){
        return oSolicitud;
    }
    
    public String getServicio(){
        return sServicio;
    }
    
    public Parametrizacion getFormulas(){
        return oFormulas;
    }
    
    public List<Parametrizacion> getListaFormulas(){
        return lListaFormulas;
    }
    
    public String getFechaActual(){
         Date dFecha = new Date(); 
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");        
        return format.format(dFecha);
    }

    public PersonalHospitalario getPersonalFirmado() {
        return oPersonal;
    }

    public DetalleSolicitudFormula[] getArrFormulaPac() {
        return arrFormulaPac;
    }

    public String getServicio2() {
        return sServicio2;
    }
  
    public String getPersonalHospitalario() {
        return sPersonalHospitalario;
    }

    public String getFechaSolicitud() {
        return sFechaSolicitud;
    }

    public DetalleSolicitudFormula[] getArrPacientesSolicitud() {
        return arrPacientesSolicitud;
    }

    public DetalleSolicitudFormula getModificarSolicitud() {
        return oModificar;
    }
    
    public List<CabeceraSolicitudFormula> getArrServicios() {
        return arrServicios;
    }
}

