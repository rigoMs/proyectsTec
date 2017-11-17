package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.enfermeria.CabeceraSolicitudDietas;
import mx.gob.hrrb.modelo.enfermeria.DetalleSolicitudDietas;
import mx.gob.hrrb.modelo.enfermeria.Dietas;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javier
 */

@ManagedBean(name="oStdDietas")
@ViewScoped
public class SolicitudDietas implements Serializable {

    private int nArea;
    private String sServicio;
    private String sUsuarioFirmado;
    private String sFechaActual;   
    private String sPersonal;
    private String sFechaSolicitud;
    private Date dFechaActual;
    private DetalleSolicitudDietas oSolicitudDietas, oPac;    
    private DetalleSolicitudDietas oModificar;
    private Usuario oUsuario;    
    private PersonalHospitalario oPersonal;   
    private DetalleSolicitudDietas[] arrSolDietas =null;
    private DetalleSolicitudDietas [] arrPacStd=null;
    private EpisodioMedico[] arrPacientes = null;
    private List<Dietas> lListaDietas=null;  
    private List<CabeceraSolicitudDietas> arrServicios;
    
    public SolicitudDietas() {
        dFechaActual= new Date();        
        oSolicitudDietas = new DetalleSolicitudDietas();
        oPac = new DetalleSolicitudDietas();         
        oPersonal = new PersonalHospitalario();
        oModificar = new DetalleSolicitudDietas();
        oUsuario= new Usuario(); 
        cargaDatosNecesarios();        
        HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();                        
                oUsuario.setIdUsuario(sUsuarioFirmado);
                oUsuario.setCvePerfil(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getCvePerfil());        
        }
        try {
            oPersonal.setUsuar(new Usuario());
            oPersonal.getUsuar().setIdUsuario(sUsuarioFirmado);
            oPersonal.buscaPersonalHospitalarioDatos();                   
        } catch (Exception ex) {
            Logger.getLogger(SolicitudDietas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void cargaDatosNecesarios(){
        try {
            lListaDietas = new ArrayList<Dietas>(Arrays.asList((new Dietas()).buscarTodosLasDietas()));
            arrServicios= new ArrayList<CabeceraSolicitudDietas>(Arrays.asList((new CabeceraSolicitudDietas()).buscaServiciosTodos()));
        } catch (Exception ex) {
            Logger.getLogger(SolicitudDietas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaPacientesEnServicioSeleccionado(){
        try {     
            arrPacientes = oPac.getEpisodio().buscarPacientesEnServicio(nArea);
            oPac.getCabeceraSolicitudDietas().getServicio().setClave(nArea);
            arrPacStd= oPac.buscaPacientesParaSolictudDietasYPacientesConDietas();
            buscaNonbreServicio();
            if(arrPacStd==null || arrPacStd.length==0){
               FacesContext.getCurrentInstance().addMessage( null,new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","NO SE ENCIENTRAN PACIENTES EN EL SERVICIO"));          
            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitudDietas.class.getName()).log(Level.SEVERE, null, ex);
        } 
        oSolicitudDietas.getCabeceraSolicitudDietas().setFolioCabecera(0);
    }
    
    public void buscaNonbreServicio(){
        for (CabeceraSolicitudDietas arrServicio : arrServicios) {
            if (arrServicio.getServicio().getClave() == nArea) {
                sServicio = arrServicio.getServicio().getDescripcion();
                break;
            }                
        }
    }
    
    public void buscarPaciente(long fp, long cve){
        if(buscaPaciente(fp, cve)){            
            oSolicitudDietas.getDietas().setFolioDieta(0);
            oSolicitudDietas.setObservacion("");
        }else{            
            FacesContext.getCurrentInstance().addMessage( null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Paciente No Encontrado"));         
        }
    }
    
    public boolean buscaPaciente(long pfp, long pcve){
        boolean bRtn=false;
        for(DetalleSolicitudDietas pac: arrPacStd){
            if(pac.getEpisodio().getPaciente().getFolioPaciente()==pfp && pac.getEpisodio().getPaciente().getClaveEpisodio()==pcve){                
                try {
                    this.oSolicitudDietas.getEpisodio().getPaciente().setFolioPaciente(pac.getEpisodio().getPaciente().getFolioPaciente());
                    this.oSolicitudDietas.getEpisodio().getPaciente().setClaveEpisodio(pac.getEpisodio().getPaciente().getClaveEpisodio());
                    this.oSolicitudDietas.getEpisodio().getPaciente().setNombres(pac.getEpisodio().getPaciente().getNombres());
                    this.oSolicitudDietas.getEpisodio().getPaciente().setApPaterno(pac.getEpisodio().getPaciente().getApPaterno());
                    this.oSolicitudDietas.getEpisodio().getPaciente().setApMaterno(pac.getEpisodio().getPaciente().getApMaterno());
                    this.oSolicitudDietas.getEpisodio().getPaciente().getExpediente().setNumero(pac.getEpisodio().getPaciente().getExpediente().getNumero());
                } catch (Exception ex) {
                    Logger.getLogger(SolicitudDietas.class.getName()).log(Level.SEVERE, null, ex);
                }
                bRtn=true;
                break;
            }
        }
        return bRtn;
    }
    
    public void buscarPacienteDatos( long fp, long cve){
        oSolicitudDietas.getEpisodio().getPaciente().setFolioPaciente(fp);
        oSolicitudDietas.getEpisodio().getPaciente().setClaveEpisodio(cve);
        try {
            if(oSolicitudDietas.getEpisodio().buscarDatosPaciente()){                 
                oSolicitudDietas.getDietas().setFolioDieta(0);
                oSolicitudDietas.setObservacion("");
                RequestContext.getCurrentInstance().execute("PF('registrarPreS').show()");                
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Paciente No Encontrado"));
            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitudDietas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
 
    public void guardarSolicitudDietas() {        
        if(oSolicitudDietas.getDietas().getFolioDieta()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona una dieta"));
        }else{
            try{
                oSolicitudDietas.getCabeceraSolicitudDietas().getServicio().setClave(nArea);
                //oSolicitudDietas.getCabeceraSolicitudDietas().getTurno().setClave(determinaTurno());                
                if(oSolicitudDietas.getCabeceraSolicitudDietas().getFolioCabecera()==0){
                    if(oSolicitudDietas.getCabeceraSolicitudDietas().buscarLlaveCabeceraSolictudDietas()){
                        //System.out.println("paso 1");
                        if(oSolicitudDietas.insertarDetalleSolicitudDietas()>0){
                            oSolicitudDietas.getDietas().setFolioDieta(0);
                            oSolicitudDietas.setObservacion("");
                            oPac.getCabeceraSolicitudDietas().getServicio().setClave(nArea);
                            arrPacStd= oPac.buscaPacientesParaSolictudDietasYPacientesConDietas();   
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Dieta Guardada"));
                            RequestContext.getCurrentInstance().execute("PF('registrarPreS').hide()");
                        }
                    }else{
                        if(oSolicitudDietas.getCabeceraSolicitudDietas().insertarCabeceraSiolicitudDietas()>0){
                            //System.out.println("paso 2");
                            if(oSolicitudDietas.insertarDetalleSolicitudDietas()>0){
                                oSolicitudDietas.getDietas().setFolioDieta(0);
                                oSolicitudDietas.setObservacion("");
                                oPac.getCabeceraSolicitudDietas().getServicio().setClave(nArea);
                                arrPacStd= oPac.buscaPacientesParaSolictudDietasYPacientesConDietas();   
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Dieta Guardada"));
                                RequestContext.getCurrentInstance().execute("PF('registrarPreS').hide()");
                            }
                        }
                    }
                }else if(oSolicitudDietas.getCabeceraSolicitudDietas().getFolioCabecera()!=0){
                    //System.out.println("paso 3");
                    if(oSolicitudDietas.insertarDetalleSolicitudDietas()>0){
                        oSolicitudDietas.getDietas().setFolioDieta(0);
                        oSolicitudDietas.setObservacion("");
                        oPac.getCabeceraSolicitudDietas().getServicio().setClave(nArea);
                        arrPacStd= oPac.buscaPacientesParaSolictudDietasYPacientesConDietas();   
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Dieta Guardada"));
                        RequestContext.getCurrentInstance().execute("PF('registrarPreS').hide()");
                    }
                }
            
            }catch(Exception e){
                Logger.getLogger(DetalleSolicitudDietas.class.getName()).log(Level.SEVERE,null, e);
            }
        }
        
    }
    
    public void buscaSolicitudDietas(){
        try {
            oPac.getCabeceraSolicitudDietas().getServicio().setClave(nArea);
            arrSolDietas = oPac.buscaSolicitudDeDietasPorFechaYServicio();
            if(this.getArrSolDietas().length>0){
                this.setServicio(arrSolDietas[0].getCabeceraSolicitudDietas().getServicio().getDescripcion());
                this.setPersonalString(arrSolDietas[0].getCabeceraSolicitudDietas().getPersonalHospitalario().getNombreCompleto());
                this.setFechaSolicitud(arrSolDietas[0].getCabeceraSolicitudDietas().getFechaSolicitudString2());
                RequestContext.getCurrentInstance().execute("PF('presPrint').show()");
            }else{
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No hay una solicitud llena"));  
            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitudDietas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaSolictudDietaPaciente(long pnf, long pncve, long pnIdD, int pnIdCb){
        try {            
            oModificar.getEpisodio().getPaciente().setFolioPaciente(pnf);
            oModificar.getEpisodio().getPaciente().setClaveEpisodio(pncve);
            oModificar.setIdDetalleDietas(pnIdD);
            oModificar.getDietas().setFolioDieta(pnIdCb);
            if(oModificar.buscaDietaPaciente()){
                RequestContext.getCurrentInstance().execute("PF('modificarDieta').show()");
            }           
        } catch (Exception ex) {
            Logger.getLogger(SolicitudDietas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modificarDietaPaciente(){      
       if(oModificar.getDietas().getFolioDieta()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona una dieta"));
       }else{
           try {
               if(oModificar.modificarDietaPaciente()==1){
                   oModificar.getDietas().setFolioDieta(0);
                   oModificar.setObservacion("");
                   oPac.getCabeceraSolicitudDietas().getServicio().setClave(nArea);
                   arrPacStd= oPac.buscaPacientesParaSolictudDietasYPacientesConDietas(); 
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Dieta Modificada "));
                   RequestContext.getCurrentInstance().execute("PF('modificarDieta').hide()");
               }
               
           } catch (Exception ex) {
               Logger.getLogger(SolicitudDietas.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    
    public String determinaTurno(){ 
         Date dFechaActual2= new Date();
         SimpleDateFormat hf= new SimpleDateFormat("HH.mm");
         String sTurno="";
         String sT=hf.format(dFechaActual2);
         Double horaActual=Double.parseDouble(sT);
         sTurno= (horaActual>=07.00 && horaActual<15.00)?"MAT": (horaActual >= 15.00 && horaActual < 21.00)?"VES":"NOC";         
         return sTurno;
    }   
    
    public int getAreaServicio(){
        return nArea;
    }
    
    public void setAreaServicio(int value){
        this.nArea=value;
    }
    
    public EpisodioMedico[] getArrPaciente(){
        return arrPacientes;
    }
    
    public DetalleSolicitudDietas getSolicitdDieta(){
        return oSolicitudDietas;
    }
    
    public List<Dietas> getListaDietas(){
        return lListaDietas;
    }
    
    public String getFechaActual(){
        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");
        sFechaActual = format.format(this.dFechaActual);
        return sFechaActual;
    }
    
    public String getServicio(){
        return sServicio;
    }
    
    public void setServicio(String valor){
        this.sServicio = valor;
    }
    public PersonalHospitalario getPersonal() {
        return oPersonal;
    }

    public DetalleSolicitudDietas[] getArrSolDietas() {
        return arrSolDietas;
    }

    public String getPersonalString() {
        return sPersonal;
    }

    public void setPersonalString(String sPersonal) {
        this.sPersonal = sPersonal;
    }

    public String getFechaSolicitud() {
        return sFechaSolicitud;
    }

    public void setFechaSolicitud(String sFechaSolicitud) {
        this.sFechaSolicitud = sFechaSolicitud;
    }

    public DetalleSolicitudDietas[] getArrrgloPacienteStd() {
        return arrPacStd;
    }

    public DetalleSolicitudDietas getModificarDieta() {
        return oModificar;
    }

    /**
     * @return the arrServicios
     */
    public List<CabeceraSolicitudDietas> getArrServicios() {
        return arrServicios;
    }
    
    
}
