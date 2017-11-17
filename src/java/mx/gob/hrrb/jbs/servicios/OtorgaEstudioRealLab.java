/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.servicios;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.*;
import mx.gob.hrrb.modelo.serv.EstudioEspLab;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import mx.gob.hrrb.jbs.core.Firmado;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;

/**
 *
 * @author Pablo
 */
@ManagedBean (name="EstRealLab")
@ViewScoped
public class OtorgaEstudioRealLab extends OtorgarServicio implements Serializable {

    private Firmado oFirmado;
    private AreaServicioHRRB oArea;
    private FacesContext faceContext;
    private EstudioEspLab oEstLab;
    private List<EstudioEspLab> lOrdenes;
    private List<EstudioEspLab> lOrdenesBaar;
    private List<EstudioEspLab> lOrdenesEnv;
    private List<EstudioEspLab> lOrdenesEnvio;
    private List<EstudioEspLab> lEstPacientesLab;
    private List<EstudioEspLab> lPacCitados;
    private String sBoton="hidden";
    private String sVisibleTabla="hidden";
    private Date dFecha;
    private HttpServletRequest httpServletRequest;
    private String sUsuario;
    private String sUrl;
    private Date dFechaSol;
    private HttpSession session;
    private int nExpediente;
    private Date dFechaSolBaar;
    private boolean dFecSol;
    private String sNombre;
    private String sApPaterno;
    private String sApMaterno;
    private int nNumExpe;
    private EstudioRealizado oEstReal;
    private EstudioEspLab oEstGab;
    private EstudioEspLab oEstBaar;
    private EstudioEspLab oSolEnv;
    private boolean bSituacion;
    private boolean bDisable;
    private boolean dFechaIni;
    private boolean dFechaFin;
    private Date dFecIni;
    private Date dFecFin;
    private EstudioEspLab oSeleccionado;
    
    public OtorgaEstudioRealLab() {
        oEstLab = new EstudioEspLab();
    }
    
    public void inicializar(){
        setArea(new AreaServicioHRRB());
        oFirmado=new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("oFirm") != null){
            oFirmado = (Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirmado.getUsu().getIdUsuario();
            sUrl=httpServletRequest.getRequestURL().toString().toLowerCase();
        }
        dFecha = new Date();
    }

    @Override
    public List buscarDetalleSolicitud() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void rebotes(ActionEvent ae) throws Exception{
        listaOrdenes();
    }
    
    public void rebotesBaar(ActionEvent ae) throws Exception{
        listaEstudiosBaar();
    }
    
    public void rebotesEnvio(ActionEvent ae) throws Exception{
        listaCasosEnvio();
    }
    
    public void rebotesEnv(ActionEvent ae) throws Exception{
        listaCasosEnv();
    }
    
    public void buscarPacCitados(){
        try{
            lPacCitados = new ArrayList<EstudioEspLab>(Arrays.asList(new EstudioEspLab().buscarPacCitadosLab(getFecha())));
        }catch(Exception  ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    @Override
    public void listaOrdenes() {
        sBoton="visible";
        sVisibleTabla="visible";
        if (getFecha() == null)
            setFecha(new Date());
        try{
            lOrdenes = new ArrayList<EstudioEspLab>(Arrays.asList(new EstudioEspLab().buscarEstGab(getFecha())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void listaEstudiosBaar(){
        sBoton="visible";
        sVisibleTabla="visible";
        try{
            lOrdenesBaar = new ArrayList<EstudioEspLab>(Arrays.asList(new EstudioEspLab().buscarEstBaar(getNombre(), getApPaterno(), getApMaterno(), getNumExpe())));
            this.nExpediente=0;
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void listaCasosEnvio(){
        sBoton="visible";
        sVisibleTabla="visible";
        try{
            lOrdenesEnv = new ArrayList<EstudioEspLab>(Arrays.asList(new EstudioEspLab().buscarSolEnvio(getNombre(),getApPaterno(), getApMaterno(), getNumExpe())));
            this.nNumExpe=0;
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void listaCasosEnv(){
        try{
            lOrdenesEnvio = new ArrayList<EstudioEspLab>(Arrays.asList(new EstudioEspLab().buscarSolEnv(getNombre(),getApPaterno(), getApMaterno(), getNumExpe())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void listaEstPacientes(){
        try{
             lEstPacientesLab = new ArrayList<EstudioEspLab>(Arrays.asList(new EstudioEspLab().buscarPacientesLaboratorio(getNombre(),getApPaterno(), getApMaterno(), getNumExpe(), getFecIni(), getFecFin())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void detalleGabinete(){
        try{
            oEstGab = new EstudioEspLab().buscarDetEstGab(oSeleccionado.getEstRealizado().getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void rebBaar(ActionEvent ae) throws Exception{
        detalleBaar();
    }
    
    public void detalleBaar(){
        try{
            oEstBaar = new EstudioEspLab().buscarDetPacBaar(oSeleccionado.getEstRealizado().getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void detalleEnvio(){
        try{
            oSolEnv = new EstudioEspLab().buscarDetSolEnvio(oSeleccionado.getEstRealizado().getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void modificarEstadoSolEnv(){
        
        if(oSeleccionado == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Paciente",""));
        }else if(oSeleccionado != null){
            try{
                if(oEstLab.modificarEstadoSolicitud(oSeleccionado.getEstRealizado().getIdentificador()) == 1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud aprobada",""));
                    listaCasosEnv();
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error",""));
                }
            }catch(Exception ex){
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error en bd",""));
            }
        }
    }
    
    public void modificarEstadoSolGabReal() {
        
        if(oSeleccionado ==null){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Paciente",""));
             this.bDisable=false;
        }else if(oSeleccionado!=null){
            try{
                if(oEstLab.modificarSolicitudRealizadaGabinete(oSeleccionado.getEstRealizado().getIdentificador(), oSeleccionado.getEstRealizado().getEstudio().getClaveInterna(), oSeleccionado.getEpisodio().getPaciente().getFolioPaciente()) ==1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud Realizada",""));
                    //this.bDisable=true;
                    listaOrdenes();
                    oSeleccionado = null;            
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error",""));
                }
            }catch(Exception ex){
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error en bd",""));
            }
        }
    }
    
    public void modificarEstadoCitaPaciente(){
        if(oSeleccionado ==null){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Paciente",""));
             this.bDisable=false;
        }else if(oSeleccionado!=null){
            try{
                if(oEstLab.modificarSolicitudRealizadaGabinete(oSeleccionado.getEstRealizado().getIdentificador(), oSeleccionado.getEstRealizado().getEstudio().getClaveInterna(), oSeleccionado.getEpisodio().getPaciente().getFolioPaciente()) ==1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Asistencia confirmada",""));
                    //this.bDisable=true;
                    buscarPacCitados();
                    oSeleccionado = null;            
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"El Paciente no asistió",""));
                }
            }catch(Exception ex){
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error en bd",""));
            }
        }
    }
    
    public void modificarEstadoSolGabCancel()throws Exception{
        if(oSeleccionado ==null){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Paciente",""));
             this.bDisable=false;
        }else if(oSeleccionado !=null){
            if(oEstLab.modificarSolicitudCanceladaGabinete(oSeleccionado.getEstRealizado().getIdentificador()) ==1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud Cancelada",""));
                //this.bDisable=true;
                listaOrdenes();
                oSeleccionado = null;
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error",""));
        }
    }
    
    public void modificarEstadoSolBaarReal() throws Exception{
        if(oSeleccionado == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Paciente",""));
        }else if(oSeleccionado != null){
            if(oEstLab.modificarSolicitudEstBaarRealizado(oSeleccionado.getEstRealizado().getIdentificador(), oSeleccionado.getEstRealizado().getEstudio().getClaveInterna(), oSeleccionado.getEpisodio().getPaciente().getFolioPaciente())==1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Estudio realizado",""));
                listaEstudiosBaar();
                oSeleccionado = null;
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error",""));
        }
    }
    
    public void modificarEstadoSolBaarCancel(){
        if(oSeleccionado == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Paciente",""));
        }else if(oSeleccionado !=null){
            try{
                if(oEstLab.modificarSolEstBaarCancel(oSeleccionado.getEstRealizado().getIdentificador())==1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Estudio Cancelado",""));
                    listaEstudiosBaar();
                    oSeleccionado = null;
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error",""));
                }
            }catch(Exception ex){
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error en bd",""));
            }
        }       
    }
    
    public Firmado getFirmado() {
        return oFirmado;
    }

    public void setFirmado(Firmado oFirmado) {
        this.oFirmado = oFirmado;
    }

    public FacesContext getFaceContext() {
        return faceContext;
    }

    public void setFaceContext(FacesContext faceContext) {
        this.faceContext = faceContext;
    }

    public EstudioEspLab getEstLab() {
        return oEstLab;
    }

    public void setEstLab(EstudioEspLab oEstLab) {
        this.oEstLab = oEstLab;
    }

    public List<EstudioEspLab> getOrdenes() {
        return lOrdenes;
    }

    public void setOrdenes(List<EstudioEspLab> lOrdenes) {
        this.lOrdenes = lOrdenes;
    }

    public String getBoton() {
        return sBoton;
    }

    public void setBoton(String sBoton) {
        this.sBoton = sBoton;
    }

    public String getVisibleTabla() {
        return sVisibleTabla;
    }

    public void setVisibleTabla(String sVisibleTabla) {
        this.sVisibleTabla = sVisibleTabla;
    }

    public Date getFecha() {
        return dFecha;
    }

    public void setFecha(Date dFecha) {
        this.dFecha = dFecha;
    }

    public String getUsuario() {
        return sUsuario;
    }

    public void setUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }

    public String getUrl() {
        return sUrl;
    }

    public void setUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public Date getFechaSol() {
        return dFechaSol;
    }

    public void setFechaSol(Date dFechaSol) {
        this.dFechaSol = dFechaSol;
    }
    
    public List<EstudioEspLab> getOrdenesBaar() {
        return lOrdenesBaar;
    }

    public void setOrdenesBaar(List<EstudioEspLab> lOrdenesBaar) {
        this.lOrdenesBaar = lOrdenesBaar;
    }

    public int getExpediente() {
        return nExpediente;
    }

    public void setExpediente(int nExpediente) {
        this.nExpediente = nExpediente;
    }

    public Date getFechaSolBaar() {
        return dFechaSolBaar;
    }

    public void setFechaSolBaar(Date dFechaSolBaar) {
        this.dFechaSolBaar = dFechaSolBaar;
    }

    public boolean getFecSol() {
        return dFecSol;
    }

    public void setFecSol(boolean dFecSol) {
        this.dFecSol = dFecSol;
    }

    public String getNombre() {
        return sNombre;
    }

    public void setNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public String getApPaterno() {
        return sApPaterno;
    }

    public void setApPaterno(String sApPaterno) {
        this.sApPaterno = sApPaterno;
    }

    public String getApMaterno() {
        return sApMaterno;
    }

    public void setApMaterno(String sApMaterno) {
        this.sApMaterno = sApMaterno;
    }

    public int getNumExpe() {
        return nNumExpe;
    }

    public void setNumExpe(int nNumExpe) {
        this.nNumExpe = nNumExpe;
    }

    public EstudioRealizado getEstReal() {
        return oEstReal;
    }

    public void setEstReal(EstudioRealizado oEstReal) {
        this.oEstReal = oEstReal;
    }

    public List<EstudioEspLab> getOrdenesEnv() {
        return lOrdenesEnv;
    }

    public void setOrdenesEnv(List<EstudioEspLab> lOrdenesEnv) {
        this.lOrdenesEnv = lOrdenesEnv;
    }

    public EstudioEspLab getEstGab() {
        return oEstGab;
    }

    public void setEstGab(EstudioEspLab oEstGab) {
        this.oEstGab = oEstGab;
    }

    public EstudioEspLab getEstBaar() {
        return oEstBaar;
    }

    public void setEstBaar(EstudioEspLab oEstBaar) {
        this.oEstBaar = oEstBaar;
    }

    public EstudioEspLab getSolEnv() {
        return oSolEnv;
    }

    public void setSolEnv(EstudioEspLab oSolEnv) {
        this.oSolEnv = oSolEnv;
    }

    public boolean isSituacion() {
        return bSituacion;
    }

    public void setSituacion(boolean bSituacion) {
        this.bSituacion = bSituacion;
    }

    public List<EstudioEspLab> getOrdenesEnvio() {
        return lOrdenesEnvio;
    }

    public void setOrdenesEnvio(List<EstudioEspLab> lOrdenesEnvio) {
        this.lOrdenesEnvio = lOrdenesEnvio;
    }


    public boolean getDisable() {
        return bDisable;
    }

    public void setDisable(boolean bDisable) {
        this.bDisable = bDisable;
    }

    public boolean getFechaIni() {
        return dFechaIni;
    }

    public void setFechaIni(boolean dFechaIni) {
        this.dFechaIni = dFechaIni;
    }

    public boolean getFechaFin() {
        return dFechaFin;
    }

    public void setFechaFin(boolean dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

    public Date getFecIni() {
        return dFecIni;
    }

    public void setFecIni(Date dFecIni) {
        this.dFecIni = dFecIni;
    }

    public Date getFecFin() {
        return dFecFin;
    }

    public void setFecFin(Date dFecFin) {
        this.dFecFin = dFecFin;
    }

    public List<EstudioEspLab> getEstPacientesLab() {
        return lEstPacientesLab;
    }

    public void setEstPacientesLab(List<EstudioEspLab> lEstPacientesLab) {
        this.lEstPacientesLab = lEstPacientesLab;
    }

    public void setSeleccionado(EstudioEspLab oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
    }

    public EstudioEspLab getSeleccionado() {
        return oSeleccionado;
    }

    public List<EstudioEspLab> getPacCitados() {
        return lPacCitados;
    }

    public void setPacCitados(List<EstudioEspLab> lPacCitados) {
        this.lPacCitados = lPacCitados;
    }
    
}
