package mx.gob.hrrb.jbs.servicios;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.*;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import mx.gob.hrrb.jbs.core.Firmado;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.serv.EstudioPatologia;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;
/**
 *
 * @author Pablo
 */
@ManagedBean (name = "EstRealPat")
@ViewScoped
public class OtorgaEstudioRealPat extends OtorgarServicio implements Serializable {

    private Firmado oFirmado;
    private AreaServicioHRRB oArea;
    private FacesContext faceContext;
    private HttpServletRequest httpServletRequest;
    private String sUsuario;
    private String sUrl;
    private List<EstudioPatologia> lListaCC;
    private List<EstudioPatologia> lListaCLC;
    private List<EstudioPatologia> lListaBPQ;
    private String sBoton="hidden";
    private String sVisibleTabla="hidden";
    private String sVisibleTabla1="hidden";
    private String sVisibleTabla2="hidden";
    private Date dFecha;
    private Date dFechaDos;
    private Date dFechaTres;
    private HttpSession session;
    private static final String TAB_TIPOS_SOL_EST = "T83";
    private static final String CVE_EST_CIT_CV = "8";
    private static final String CVE_EST_CIT_LIQ_CORP = "9";
    private static final String CVE_EST_BIOP_PZA_QX = "10";
    private EstudioPatologia oSolCitCer;
    private EstudioPatologia oSolCitLC;
    private EstudioPatologia oSolBPQ;
    private EstudioPatologia oEstPat;
    private EstudioPatologia oEstPatologia;
    private EstudioPatologia oRepPat;
    private List<EstudioPatologia> lProcedimientos;
    private List<EstudioPatologia> lTipoCitologia;
    private List<EstudioPatologia> lCatGeneral;
    private List<EstudioPatologia> lSitAnat;
    private List<EstudioPatologia> lPacExt;
    private Date dFecInclusion;
    private Date dFecProcedimiento;
    private String sShowDialog;
    private EstudioPatologia oSeleccionado;
    private EstudioPatologia oSeleReporte;
    private boolean bSolCerv;
    private EstudioRealizado oEstReal;
    private EpisodioMedico oEpisodio;
    private boolean bShowButton=true;
    private List<EstudioPatologia> lReportes;
    private PersonalHospitalario oPersonal;
    private Estudios oEst;
    private DiagnosticoCIE10 oDiag;
    private String sFecNac;
    private String sNombre;
    private String sApPaterno;
    private String sApMaterno;
    private Date dFechaNacimiento;
    
    public OtorgaEstudioRealPat() {
        oEst = new Estudios();
        oEstPat = new EstudioPatologia();
        oEstPat.setTipoProcedimiento(new Parametrizacion());
        oEstPatologia = new EstudioPatologia();
        oEpisodio = new EpisodioMedico();
        oEstReal = new EstudioRealizado();
        oFirmado=new Firmado();
        oDiag = new DiagnosticoCIE10();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("oFirm") != null){
            oFirmado = (Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirmado.getUsu().getIdUsuario();
            sUrl=httpServletRequest.getRequestURL().toString().toLowerCase();
        }
    }
    
    public void inicializar(){ 
    }

    @Override
    public List buscarDetalleSolicitud() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void rebotes(ActionEvent ae) throws Exception{
        listaOrdenes();
        //setFecha(null);
    }
    
    
    public void rebotesDos(ActionEvent ae) throws Exception{
        listaOrdenesDos();
        //setFecha(null);
    }
    
    public void rebotesTres(ActionEvent ae) throws Exception{
        listaOrdenesTres();
        //setFecha(null);
    }

    @Override
    public void listaOrdenes() {
        try{
            lListaCC = new ArrayList<EstudioPatologia>(Arrays.asList(
                    new EstudioPatologia().buscarSolPatologia(
                            TAB_TIPOS_SOL_EST, CVE_EST_CIT_CV, getFecha())));   
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(
                    Level.SEVERE,null, ex);
        }
    }
    
    public void listaOrdenesDos(){
        System.out.println("Dentro de listaOrdenes2");
        try{
            lListaCLC = new ArrayList<EstudioPatologia>(Arrays.asList(
                    new EstudioPatologia().buscarSolPatologia(TAB_TIPOS_SOL_EST, 
                            CVE_EST_CIT_LIQ_CORP, getFecha())));
        }catch(Exception ex){
            System.out.println("Hubo una exception");
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(
                    Level.SEVERE,null, ex);
        }   
    }
    
    public void listaOrdenesTres(){
        try{
            lListaBPQ = new ArrayList<EstudioPatologia>(Arrays.asList(
                    new EstudioPatologia().buscarSolPatologia(
                       TAB_TIPOS_SOL_EST, CVE_EST_BIOP_PZA_QX, getFecha())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(
                    Level.SEVERE,null, ex);
        }
    }
    
    public void listaReportes(int nClave){
        System.out.println("Clave: " + nClave);
        try{
            lReportes = new ArrayList<EstudioPatologia>(Arrays.asList(new EstudioPatologia().buscarReportes(nClave, getFecha(), getFechaDos())));
            setFecha(null);
            setFechaDos(null);
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void listaPacienteExterno(){
        try{
            lPacExt = new ArrayList<EstudioPatologia>(Arrays.asList(new EstudioPatologia().buscarPacientesExterno(getNombre(), getApPaterno(), getApMaterno())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void detallePacExterno(){
        try{
            oEstPat = new EstudioPatologia().buscarDetallePacExt(
                    oSeleccionado.getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public List<EstudioPatologia> getListaProcedimientos(){
        try{
            lProcedimientos = new ArrayList<EstudioPatologia>(Arrays.asList(new EstudioPatologia().buscarProcPat(1)));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
        return lProcedimientos;
    }
    
    public List<EstudioPatologia> getListaProcedimientos2(){
        try{
            lProcedimientos = new ArrayList<EstudioPatologia>(Arrays.asList(new EstudioPatologia().buscarProcPat(2)));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
        return lProcedimientos;
    }
    
    public List<EstudioPatologia> getListaTipoCitologia() throws Exception{
        try{
            lTipoCitologia = new ArrayList<EstudioPatologia>(Arrays.asList(new EstudioPatologia().buscarTipoCitologia()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
        return lTipoCitologia;
    }
    
    public List<EstudioPatologia> getListaCatGeneral() throws Exception{
        try{
            lCatGeneral = new ArrayList<EstudioPatologia>(Arrays.asList(new EstudioPatologia().buscarCatGeneral()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
        return lCatGeneral;
    }
    
    public List<EstudioPatologia> getListaSitiosAnat() throws Exception{
        try{
            lSitAnat = new ArrayList<EstudioPatologia>(Arrays.asList(new EstudioPatologia().buscarSitiosAnatomicos()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
        return lSitAnat;
    }
    
    public PersonalHospitalario datosUsuario(){
        try{
            System.out.println("Usuario actual: " + sUsuario);
            oPersonal = new EstudioPatologia().buscarNombreUsuario(sUsuario);
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
        return oPersonal;
    }
    
    public void detalleCitCer() throws Exception{
        try{
            if(oSeleccionado != null){
                oEstPat = new EstudioPatologia().buscarDetEstCervical(oSeleccionado.getIdentificador());
            }else{
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se seleccionó Paciente"));
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void detalleLiqCor()throws Exception{
        try{
            if(oSeleccionado != null){
                oEstPat = new EstudioPatologia().buscarDetLiqCor(oSeleccionado.getIdentificador());
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se seleccionó Paciente"));
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void detalleBioPQx() throws Exception{
        try{
            if(oSeleccionado != null){
                oEstPat = new EstudioPatologia().buscarDetBiopsia(oSeleccionado.getIdentificador());
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se seleccionó Paciente"));
            } 
       }catch(Exception ex){
           Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
       }
    }
    
    
    public void detalleRepCerv() throws Exception{
        try{
            oRepPat = new EstudioPatologia().buscarDetalleRepCerv(oSeleReporte.getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void detalleRepLiq() throws Exception{
        try{
            oRepPat = new EstudioPatologia().buscarDetalleRepLiq(oSeleReporte.getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void detalleRepBiop() throws Exception{
        try{
            oRepPat = new EstudioPatologia().buscarDetalleRepBiop(oSeleReporte.getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void cambiaEstado(){
        if(oEstPat != null){
            oEstPat=new EstudioPatologia();
        }
    }
    
    public void validaSeleccionPaciente() throws Exception{
        System.out.println("Dentro del método validaSeleccionPaciente");
        if(oSeleccionado == null){
            setShowButton(true);
        }else if(oSeleccionado != null){
            setShowButton(false);
        }
    }
    
    public void modificarDatosSolLiqCor() throws Exception{
        System.out.println("FecInc " + getFecInclusion());
        if(oEstPatologia.modificarDatosReporteLiqCor(oSeleccionado.getIdentificador(), getFecInclusion(), getFecProcedimiento())==1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Reporte guardado exitosamente","")); 
            oEstPatologia.setMaterialEnviado("");
            oEstPatologia.setDescripcionTecnica("");
            oEstPatologia.setDescripMicro("");
            setFecInclusion(null);
            setFecProcedimiento(null);
            //oSeleccionado = null;
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al intentar guardar el reporte",""));
        }
    }
    
    public void modificarDatosSolCitCer() throws Exception{
         if(oEstPatologia.modificarDatosReporteCitCervical(oSeleccionado.getIdentificador()) == 1){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Reporte guardado exitosamente",""));
             oEstPatologia.setTipoCitol(null);
             oEstPatologia.setCitAdecuada(null);
             oEstPatologia.setRazonCitolAdecuada("");
             oEstPatologia.setCatGral(null);
             oEstPatologia.setRazonPosNeg("");
             oEstPatologia.setNotas("");
             //oSeleccionado = null;
         }else{
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al intentar guardar el reporte",""));
             oEstPatologia.setTipoCitol(null);
             oEstPatologia.setCitAdecuada(null);
             oEstPatologia.setRazonCitolAdecuada("");
             oEstPatologia.setCatGral(null);
             oEstPatologia.setRazonPosNeg("");
             oEstPatologia.setNotas("");
             //oSeleccionado = null;
         }
    }
    
    public void modificarDatosSolBiopsia() throws Exception{
        if(oEstPatologia.modificarReporteBiopsia(oSeleccionado.getIdentificador())==1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Reporte guardado exitosamente",""));
            oEstPatologia.setDescripMacro("");
            oEstPatologia.setDescripMicro("");
            oEstPatologia.setImpresionDiagnostica("");
            oEstPatologia.setFechaInclusion(null);
            oEstPatologia.setFechaProcedimiento(null);
            oSeleccionado = null;
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al intentar guardar el reporte",""));
            oEstPatologia.setDescripMacro("");
            oEstPatologia.setDescripMicro("");
            oEstPatologia.setImpresionDiagnostica("");
            oEstPatologia.setFechaInclusion(null);
            oEstPatologia.setFechaProcedimiento(null);
            //oSeleccionado = null;
        }
    }
    
    public void modificarRecepcionPieza() throws Exception{
        if(oEstPatologia.modificarRecepcionPieza(oSeleccionado.getIdentificador())==1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos de la pieza agregados correctamente",""));
            oEstPatologia.setNumeroEstudio("");
            oEstPatologia.setEspecimenMuestraTejido("");
            oEstPatologia.setFechaRecepcion(null);
            //oSeleccionado = null;
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error, la pieza ya fue registrada para esta solicitud",""));
            oEstPatologia.setNumeroEstudio("");
            oEstPatologia.setEspecimenMuestraTejido("");
            oEstPatologia.setFechaRecepcion(null);
            //oSeleccionado = null;
        }
        
    }
    
    public void modificarFechaEntrega() throws Exception{
        Date fecha = new Date();
        System.out.println("Fecha: " + fecha);
        if(oEstPatologia.modificarEntregaArchivo(
                oSeleccionado.getIdentificador(), fecha, 
                oSeleccionado.getEstudio().getClaveInterna(), 
                oSeleccionado.getEpisodio().getPaciente().getFolioPaciente())==1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos entregados correctamente",""));
            listaOrdenes();
            oSeleccionado = null;  
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error, aún no se ha registrado el número de estudio",""));
        }
    }
    
    public void modificarFechaEntrega2() throws Exception{
        Date fecha = new Date();
        System.out.println("Fecha: " + fecha);
        if(getEstPatologia().modificarEntregaArchivo(
                oSeleccionado.getIdentificador(), fecha, 
                oSeleccionado.getEstudio().getClaveInterna(), 
                oSeleccionado.getEpisodio().getPaciente().getFolioPaciente())==1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos entregados correctamente",""));
            listaOrdenesDos();
            oSeleccionado = null;
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error, aún no se ha registrado el número de estudio",""));
        }
    }
    
    public void modificarFechaEntrega3() throws Exception{
        Date fecha = new Date();
        System.out.println("Fecha: " + fecha);
        if(oEstPatologia.modificarEntregaArchivo(
                oSeleccionado.getIdentificador(), fecha, 
                oSeleccionado.getEstudio().getClaveInterna(), 
                oSeleccionado.getEpisodio().getPaciente().getFolioPaciente())==1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos entregados correctamente",""));
            listaOrdenesTres();
            oSeleccionado = null;
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error, aún no se ha registrado el número de estudio",""));
        }
    }
    
    public void insertaPacienteExterno(){
        try{
            System.out.println("Fecha de Nacimiento: " + getFechaNacimiento());
            if(oEstPatologia.insertaPacienteExterno(
                    getEstudios().getClaveInterna(), 
                    getDiag().getClave(), 
                    getPersonal().getNoTarjeta())==1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro Exitoso",""));
                oEstPatologia.getPaciente().setNombres(null);
                oEstPatologia.getPaciente().setApPaterno(null);
                oEstPatologia.getPaciente().setApMaterno(null);
                oEstPatologia.getPaciente().setSexoP(null);
                setFecNac(null);
                oEstPatologia.setFechaRecepcion(null);
                oEstPatologia.setFechaProgramado(null);
                oEstPatologia.setNombreQuienEntregaMuestra(null);
                oEstPatologia.setEspecimenMuestraTejido(null);
                oEstPatologia.getEpisodio().getPaciente().setFechaNac(null);
                getEstudios().setClaveStudio(null);
                getEstudios().setConcepto("");
                getEstudios().setClaveInterna(0);
                getDiag().setClave("");
                getDiag().setDescripcionDiag("");
            }else if(
                    oEstPatologia.insertaPacienteExterno(
                            getEstudios().getClaveInterna(), 
                            getDiag().getClave(), 
                            getPersonal().getNoTarjeta())!=1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se registró al paciente"));
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealLab.class.getName()).log(Level.SEVERE,null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error en la base de datos",""));
        }
    }

    public String fechaActual(){
        Calendar fecha = new GregorianCalendar();
        int anio=fecha.get(Calendar.YEAR);
        int mes=fecha.get(Calendar.MONTH);
        int dia=fecha.get(Calendar.DAY_OF_MONTH);
        
        String a=anio+"";
        String hoy=dia+"/"+(mes+1)+"/"+a.substring(2, 4);

        return hoy;
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

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getsUsuario() {
        return sUsuario;
    }

    public void setsUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }

    public String getsUrl() {
        return sUrl;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public List<EstudioPatologia> getListaCC() {
        return lListaCC;
    }

    public void setListaCC(List<EstudioPatologia> lListaCC) {
        this.lListaCC = lListaCC;
    }

    public List<EstudioPatologia> getListaCLC() {
        return lListaCLC;
    }

    public void selListaCLC(List<EstudioPatologia> lListaCLC) {
        this.lListaCLC = lListaCLC;
    }

    public List<EstudioPatologia> getListaBPQ() {
        return lListaBPQ;
    }

    public void selListaBPQ(List<EstudioPatologia> lListaBPQ) {
        this.lListaBPQ = lListaBPQ;
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
    
    
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }


    public String getVisibleTabla1() {
        return sVisibleTabla1;
    }

    public void setVisibleTabla1(String sVisibleTabla1) {
        this.sVisibleTabla1 = sVisibleTabla1;
    }

    public String getVisibleTabla2() {
        return sVisibleTabla2;
    }

    public void setVisibleTabla2(String sVisibleTabla2) {
        this.sVisibleTabla2 = sVisibleTabla2;
    }

    public Date getFechaDos() {
        return dFechaDos;
    }

    public void setFechaDos(Date dFechaDos) {
        this.dFechaDos = dFechaDos;
    }

    public Date getFechaTres() {
        return dFechaTres;
    }

    public void setFechaTres(Date dFechaTres) {
        this.dFechaTres = dFechaTres;
    }

    public EstudioPatologia getSolCitCer() {
        return oSolCitCer;
    }

    public void setSolCitCer(EstudioPatologia oSolCitCer) {
        this.oSolCitCer = oSolCitCer;
    }

    public EstudioPatologia getSolCitLC() {
        return oSolCitLC;
    }

    public void setSolCitLC(EstudioPatologia oSolCitLC) {
        this.oSolCitLC = oSolCitLC;
    }

    public EstudioPatologia getSolBPQ() {
        return oSolBPQ;
    }

    public void setSolBPQ(EstudioPatologia oSolBPQ) {
        this.oSolBPQ = oSolBPQ;
    }

    public List<EstudioPatologia> getProcedimientos() {
        return lProcedimientos;
    }

    public void setProcedimientos(List<EstudioPatologia> oProcedimientos) {
        this.lProcedimientos = oProcedimientos;
    }

    public EstudioPatologia getEstPat() {
        return oEstPat;
    }

    public void setEstPat(EstudioPatologia oEstPat) {
        this.oEstPat = oEstPat;
    }

    public Date getFecInclusion() {
        return dFecInclusion;
    }

    public void setFecInclusion(Date dFecInclusion) {
        this.dFecInclusion = dFecInclusion;
    }

    public Date getFecProcedimiento() {
        return dFecProcedimiento;
    }

    public void setFecProcedimiento(Date dFecProcedimiento) {
        this.dFecProcedimiento = dFecProcedimiento;
    }

    public String getShowDialog() {
        return sShowDialog;
    }

    public void setShowDialog(String sShowDialog) {
        this.sShowDialog = sShowDialog;
    }

    public EstudioPatologia getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EstudioPatologia oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
    }

    public boolean getSolCerv() {
        return bSolCerv;
    }

    public void setSolCerv(boolean bSolCerv) {
        this.bSolCerv = bSolCerv;
    }

    public EstudioRealizado getEstReal() {
        return oEstReal;
    }

    public void setEstReal(EstudioRealizado oEstReal) {
        this.oEstReal = oEstReal;
    }

    public boolean getShowButton() {
        return bShowButton;
    }

    public void setShowButton(boolean bShowButton) {
        this.bShowButton = bShowButton;
    }

    public List<EstudioPatologia> getlTipoCitologia() {
        return lTipoCitologia;
    }

    public void setlTipoCitologia(List<EstudioPatologia> lTipoCitologia) {
        this.lTipoCitologia = lTipoCitologia;
    }

    public List<EstudioPatologia> getCatGeneral() {
        return lCatGeneral;
    }

    public void setCatGeneral(List<EstudioPatologia> lCatGeneral) {
        this.lCatGeneral = lCatGeneral;
    }

    public List<EstudioPatologia> getReportes() {
        return lReportes;
    }

    public void setReportes(List<EstudioPatologia> lReportes) {
        this.lReportes = lReportes;
    }

    public EstudioPatologia getSeleReporte() {
        return oSeleReporte;
    }

    public void setSeleReporte(EstudioPatologia oSeleReporte) {
        this.oSeleReporte = oSeleReporte;
    }

    public EstudioPatologia getRepPat() {
        return oRepPat;
    }

    public void setRepPat(EstudioPatologia oRetPat) {
        this.oRepPat = oRetPat;
    }

    public PersonalHospitalario getPersonal() {
        return oPersonal;
    }

    public void setPersonal(PersonalHospitalario oPersonal) {
        this.oPersonal = oPersonal;
    }

    public List<EstudioPatologia> getSitAnat() {
        return lSitAnat;
    }

    public void setSitAnat(List<EstudioPatologia> lSitAnat) {
        this.lSitAnat = lSitAnat;
    }

    public Estudios getEstudios() {
        return oEst;
    }

    public void setEstudios(Estudios oEst) {
        this.oEst = oEst;
    }

    public DiagnosticoCIE10 getDiag() {
        return oDiag;
    }

    public void setDiag(DiagnosticoCIE10 oDiag) {
        this.oDiag = oDiag;
    }

    public String getFecNac() {
        return sFecNac;
    }

    public void setFecNac(String sFecNac) {
        this.sFecNac = sFecNac;
    }

    public List<EstudioPatologia> getPacExt() {
        return lPacExt;
    }

    public void setPacExt(List<EstudioPatologia> lPacExt) {
        this.lPacExt = lPacExt;
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
    
    public EstudioPatologia getEstPatologia() {
        return oEstPatologia;
}

    public void setEstPatologia(EstudioPatologia oEstPatologia) {
        this.oEstPatologia = oEstPatologia;
    }

    public Date getFechaNacimiento() {
        return dFechaNacimiento;
    }

    public void setdFechaNacimiento(Date dFechaNacimiento) {
        this.dFechaNacimiento = dFechaNacimiento;
    }
    
}
