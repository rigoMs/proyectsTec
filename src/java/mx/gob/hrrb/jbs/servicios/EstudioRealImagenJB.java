package mx.gob.hrrb.jbs.servicios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.bean.ManagedBean;
import mx.gob.hrrb.modelo.core.*;
import mx.gob.hrrb.modelo.serv.EstudioRealImagen;
import mx.gob.hrrb.jbs.core.Firmado;
import javax.faces.context.FacesContext;            
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.serv.EstudioImagen;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;
import org.primefaces.event.FileUploadEvent;


/**
 *
 * @author Pablo
 */
@ManagedBean (name="EstRealImagen")
@ViewScoped
public class EstudioRealImagenJB extends OtorgarServicio implements Serializable{
    
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private String url;
    private AreaServicioHRRB oArea;
    private EstudioRealImagen oEstImagen;
    private Paciente oPaciente;
    private Date fechaIni;
    private Date fechaFin;
    private Date dFechaInicial;
    private Date dFechaFinal;
    private String sFechaI;
    private String sFechaF;
    private String boton="hidden";
    private String sVisibleTabla="hidden";
    private List<EstudioRealImagen> lOrdenes;
    private List<EstudioRealImagen> lOrdenesPend;
    private List<EstudioRealImagen> lOrdenesPendDos;
    private List<EstudioRealImagen> lOrdenesPendTres;
    private List<EstudioRealImagen> lOrdenesPendCuatro;
    private List<EstudioRealImagen> lReportes;
    private List<EstudioImagen> lRepRx;
    private List<EstudioImagen> lRepMasto;
    private List<EstudioImagen> lRepUsg;
    private List<EstudioImagen> lRepTomo;
    private HttpSession session;    
    private int nClaveArea;
    private boolean bFecIni;
    private boolean bFecFin;
    private EstudioRealizado oEstReal;
    private EstudioRealImagen seleccionado;
    private EstudioRealImagen oSelPac;
    private EstudioRealImagen oSelPacRep;
    private EstudioRealImagen oSelPacTomo;
    private boolean bDatosPac;
    private int nClaveIden;
    private String sCallDialog;
    private EstudioRealImagen oSeleccionado;
    private boolean bShowButton;
    private EstudioImagen oEstudioImagen;
    
    public EstudioRealImagenJB() {
        oEstImagen = new EstudioRealImagen();
        oEstReal = new EstudioRealizado();
    }
    
    public void inicializar(){
        oArea = new AreaServicioHRRB();
        oEstImagen = new EstudioRealImagen();
        //oEstReal = new EstudioRealizado();
        //oPaciente = new Paciente();
        //getSession(false);
        fechaIni = null;
        fechaIni = null;
        //session.setAttribute("sEstRealImagen", new EstudioRealImagenJB());
        oFirm=new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("oFirm") != null){
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            url=httpServletRequest.getRequestURL().toString().toLowerCase();
        }
    }
    
    public List<AreaServicioHRRB> getListaAreas(){
        List<AreaServicioHRRB> lLista=null;
        try{
            lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(new AreaServicioHRRB().buscarSubImg()));
        }catch(Exception e){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, e);
        }
               
        return lLista;
    }
    
    @Override
    public void listaOrdenes(){
        boton="visible";
        sVisibleTabla="visible";
        try{
            //System.out.println("Fecha 1 " + getFechaIni());
            //System.out.println("Fecha 2 " + getFechaFin());
            SimpleDateFormat fecha=new SimpleDateFormat("yyyy-MM-dd");
            lOrdenes = new ArrayList<EstudioRealImagen>(Arrays.asList(new EstudioRealImagen().buscarTodosImagen(1,getFechaIni(), getFechaFin())));
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listaOrdenes2(){
        boton="visible";
        sVisibleTabla="visible";
        try{
            //System.out.println("Fecha 1 " + getFechaIni());
            //System.out.println("Fecha 2 " + getFechaFin());
            SimpleDateFormat fecha=new SimpleDateFormat("yyyy-MM-dd");
            lOrdenes = new ArrayList<EstudioRealImagen>(Arrays.asList(new EstudioRealImagen().buscarTodosImagen(2,getFechaIni(), getFechaFin())));
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listaOrdenesPendientes(){
        sVisibleTabla = "visible";
        try{
            lOrdenesPend = new ArrayList<EstudioRealImagen>(Arrays.asList(new EstudioRealImagen().buscarSolPendImagen(1,getFechaIni(), getFechaFin())));
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listaOrdenesPendientes2(){
        sVisibleTabla = "visible";
        try{
            lOrdenesPendDos = new ArrayList<EstudioRealImagen>(Arrays.asList(new EstudioRealImagen().buscarSolPendImagen(2,getFechaIni(), getFechaFin())));
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listaOrdenesPendientes3(){
        sVisibleTabla = "visible";
        try{
            lOrdenesPendTres = new ArrayList<EstudioRealImagen>(Arrays.asList(new EstudioRealImagen().buscarSolPendImagen(3,getFechaIni(), getFechaFin())));
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listaOrdenesPendientes4(){
        sVisibleTabla = "visible";
        try{
            lOrdenesPendCuatro = new ArrayList<EstudioRealImagen>(Arrays.asList(new EstudioRealImagen().buscarSolPendImagen(4,getFechaIni(), getFechaFin())));
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listaReportes(int nClave){
        sVisibleTabla = "visible";
        try{
            lReportes = new ArrayList<EstudioRealImagen>(Arrays.asList(new EstudioRealImagen().buscarRepEstImagen(nClave,getFechaIni(), getFechaFin())));
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscarDatosReporteRegiones(int nOpcion){
        try{
            switch(nOpcion){
                case 1:
                    lRepRx = new ArrayList<EstudioImagen>(Arrays.asList(new EstudioImagen().buscarReporteRegiones(nOpcion, getFechaIni(), getFechaFin())));
                    break;
                case 2:
                    lRepMasto = new ArrayList<EstudioImagen>(Arrays.asList(new EstudioImagen().buscarReporteRegiones(nOpcion, getFechaIni(), getFechaFin())));
                    break;
                case 3:
                    lRepUsg = new ArrayList<EstudioImagen>(Arrays.asList(new EstudioImagen().buscarReporteRegiones(nOpcion, getFechaIni(), getFechaFin())));
                    break;
                case 4:
                    lRepTomo = new ArrayList<EstudioImagen>(Arrays.asList(new EstudioImagen().buscarReporteRegiones(nOpcion, getFechaIni(), getFechaFin())));
                    break;
                default:
                    break;
            }
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getCantidadRegRx(){
        int nCant1=0;
        if(lRepRx == null){
            return nCant1=0;
        }else{
            for(EstudioImagen im:lRepRx){
                nCant1 = (int) (nCant1 + im.getCantidad());
            }
            return nCant1;
        }
        
    }
    
    public int getCantidadRepUsg(){
        int nCant1=0;
        if(lRepUsg == null){
            return nCant1=0;
        }else{
            for(EstudioImagen im:lRepUsg){
                nCant1= (int)(nCant1 + im.getCantidad());
            }
            return nCant1;
        }
        
    }
    
    public int getCantidadRepMasto(){
        int nCant1=0;
        if(lRepMasto == null){
            return nCant1;
        }else{
            for(EstudioImagen im:lRepMasto){
                nCant1= (int)(nCant1 + im.getCantidad());
            }   
            return nCant1;
        }
    }
    
    public int getCantidadRepTomo(){
        int nCant1=0;
        if(lRepTomo == null){
            return nCant1;
        }else{
            for(EstudioImagen im:lRepTomo){
                nCant1= (int)(nCant1 + im.getCantidad());
            }
            return nCant1;
        }
        
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String sRuta = facesContext.getExternalContext().getRealPath("")+"" + 
                EstudioRealImagen.RUTA_ARCHIVO, sNombre="";
        OutputStream out = null;
        Date oFec = new Date();
        SimpleDateFormat oFecha = new SimpleDateFormat("yyyy-MM-dd");
        InputStream fileContent = null;
        byte[] bytes= new byte[1024];
        int nRead;
        try{
            sNombre = "" + oSeleccionado.getIdentificador() + "" + oSeleccionado.getEpisodio().getPaciente().getNombres().substring(0, 2);
            sNombre = sNombre + "" + oSeleccionado.getEpisodio().getPaciente().getApPaterno().substring(0,2);
            sNombre = sNombre + oFecha.format(oFec) + ".PDF";
            System.out.println(sNombre);
            if(oEstImagen.modificarInterpretacion(oSeleccionado.getIdentificador(), sNombre)==1){
                out = new FileOutputStream(new File(sRuta + File.separator + sNombre));
                fileContent = event.getFile().getInputstream();
                while((nRead=fileContent.read(bytes))!=-1){
                    out.write(bytes,0,nRead);
                }
                System.out.println(sRuta);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"El archivo", sNombre + " fue almacenado correctamente"));
            }else if(oEstImagen.modificarInterpretacion(oSeleccionado.getIdentificador(), sNombre)!=1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR", sNombre + " ya tiene un paciente asignado"));
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",event.getFile().getFileName() + "no almacenado"));
        }finally{
            if(out!=null){
                out.close();
            }
            if(fileContent != null){
                fileContent.close();
            }
        }
    }

    public void modificaEstadoSolicitud(int nClave, int nTabla){
        try{
            if(oSeleccionado == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Seleccione un Paciente"));
            }else if(oSeleccionado != null){
                if(oEstImagen.modificaEstadoSolicitud(oSeleccionado.getIdentificador(), nClave, oSeleccionado.getEstudio().getClaveInterna(), oSeleccionado.getEpisodio().getPaciente().getFolioPaciente())==1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO","Se registró el cambio"));
                    oSeleccionado = null;
                    oEstImagen.setImpresionDiagnostica("");
                    switch(nTabla){
                        case 1:
                            listaOrdenesPendientes();
                            break;
                        case 2:
                            listaOrdenesPendientes2();
                            break;
                        case 3:
                            listaOrdenesPendientes3();
                            break;
                        case 4:
                            listaOrdenesPendientes4();
                            break;
                        default:
                            break;
                    }
                }else if (oEstImagen.modificaEstadoSolicitud(oSeleccionado.getIdentificador(), nClave, oSeleccionado.getEstudio().getClaveInterna(), oSeleccionado.getEpisodio().getPaciente().getFolioPaciente())!=1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al intentar cambiar el estado del estudio",""));
                }
                else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error en la base de datos",""));
                }
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void rebotes(ActionEvent ae) throws Exception{
        listaOrdenes();
    }
    
    public void rebotes2(ActionEvent ae) throws Exception{
        listaOrdenes2();
    }
    
    
    public void rebotesSolPend(ActionEvent ae) throws Exception{
        listaOrdenesPendientes();
    }
    
    public void rebotesSolPend2(ActionEvent ae) throws Exception{
        listaOrdenesPendientes2();
    }
    
    public void rebotesSolPend3(ActionEvent ae) throws Exception{
        listaOrdenesPendientes3();
    }
    
    public void rebotesSolPend4(ActionEvent ae) throws Exception{
        listaOrdenesPendientes4();
    }

    public void detallePaciente(){
        try{
            oSelPac = new EstudioRealImagen().buscarDetallePacImg(oSeleccionado.getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void detalleReporte(){
        try{
            oSelPacRep = new EstudioRealImagen().buscarDetalleRepImagen(oSeleccionado.getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cambiarEstadoObjetos(){
        if(oSelPacRep != null || oSelPacTomo != null){
            oSelPacRep = null;
            oSelPacTomo = null;
        }
    }
    
    public void cambiarEstadoSeleccionado(){
        if(oSelPac != null){
            oSelPac = null;
        }
    }
    
    public void detalleReporte2(){
        try{
             oSelPacTomo = new EstudioRealImagen().buscarDetalleRepImagen(oSeleccionado.getIdentificador());
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void modificarInterpretacion(){
        try{
            if(oSeleccionado == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Paciente",""));
                setShowButton(true);
            }else if(oSeleccionado != null){
                setShowButton(false);
                if(oEstImagen.modificarInterpretacion(oSeleccionado.getIdentificador(),"")==1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Interpretación registrada exitosamente",""));
                    listaOrdenesPendientes(); 
                    oEstImagen.setImpresionDiagnostica("");
                }
                else if(oEstImagen.modificarInterpretacion(oSeleccionado.getIdentificador(),"")!=1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se registró la interpretación",""));
                    oEstImagen.setImpresionDiagnostica("");
                }
            }
        }catch(Exception ex){
            Logger.getLogger(EstudioRealImagenJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void rebotesVal(ActionEvent ae) throws Exception{
        validaPaciente();
    }
    
    public void validaPaciente() throws Exception{
       if(oSeleccionado == null){
           setShowButton(true);
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Paciente",""));
       }else if(oSeleccionado != null){
           oSelPac = new EstudioRealImagen().buscarDetallePacImg(oSeleccionado.getIdentificador());
           setShowButton(false);
       }
    }
    
    public String cerrar() throws Exception{
        oEstImagen.setImpresionDiagnostica("");
        return "registrarInterpretacionImg";
    }
    
    public Firmado getFirm() {
        return oFirm;
    }

    public void setFirm(Firmado oFirm) {
        this.oFirm = oFirm;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public FacesContext getFaceContext() {
        return faceContext;
    }

    public void setFaceContext(FacesContext faceContext) {
        this.faceContext = faceContext;
    }

    public String getUsuario() {
        return sUsuario;
    }

    public void setUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EstudioRealImagen getEstImagen() {
        return oEstImagen;
    }

    public void setEstImagen(EstudioRealImagen oEstImagen) {
        this.oEstImagen = oEstImagen;
    }


    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton) {
        this.boton = boton;
    }

    public String getVisibleTabla() {
        return sVisibleTabla;
    }

    public void setVisibleTabla(String sVisibleTabla) {
        this.sVisibleTabla = sVisibleTabla;
    }

    public List<EstudioRealImagen> getOrdenes() {
        return lOrdenes;
    }

    public void setOrdenes(List<EstudioRealImagen> lOrdenes) {
        this.lOrdenes = lOrdenes;
    }

    public String getFechaI() {
        return sFechaI;
    }

    public void setFechaI(String sFechaI) {
        this.sFechaI = sFechaI;
    }

    public String getFechaF() {
        return sFechaF;
    }

    public void setFechaF(String sFechaF) {
        this.sFechaF = sFechaF;
    }
    

    public int getClaveArea() {
        return nClaveArea;
    }

    public void setClaveArea(int nClaveArea) {
        this.nClaveArea = nClaveArea;
    }

    public boolean getFecIni() {
        return bFecIni;
    }

    public void setFecIni(boolean bFecIni) {
        this.bFecIni = bFecIni;
    }

    public boolean getFecFin() {
        return bFecFin;
    }

    public void setFecFin(boolean bFecFin) {
        this.bFecFin = bFecFin;
    }

    @Override
    public List buscarDetalleSolicitud() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public EstudioRealizado getEstReal() {
        return oEstReal;
    }

    public void setEstReal(EstudioRealizado oEstReal) {
        this.oEstReal = oEstReal;
    }

    public EstudioRealImagen getSelPac() {
        return oSelPac;
    }

    public void setSelPac(EstudioRealImagen oSelPac) {
        this.oSelPac = oSelPac;
    }

    public boolean getDatosPac() {
        return bDatosPac;
    }

    public void setDatosPac(boolean bDatosPac) {
        this.bDatosPac = bDatosPac;
    }

    public int getClaveIden() {
        return nClaveIden;
    }

    public void setClaveIden(int nClaveIden) {
        this.nClaveIden = nClaveIden;
    }

    public List<EstudioRealImagen> getOrdenesPend() {
        return lOrdenesPend;
    }

    public void setOrdenesPend(List<EstudioRealImagen> lOrdenesPend) {
        this.lOrdenesPend = lOrdenesPend;
    }

    public String getCallDialog() {
        return sCallDialog;
    }

    public EstudioRealImagen getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EstudioRealImagen oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
    }

    public List<EstudioRealImagen> getReportes() {
        return lReportes;
    }

    public void setReportes(List<EstudioRealImagen> lReportes) {
        this.lReportes = lReportes;
    }

    public boolean getShowButton() {
        return bShowButton;
    }

    public void setShowButton(boolean bShowButton) {
        this.bShowButton = bShowButton;
    }

    public EstudioRealImagen getSelPacTomo() {
        return oSelPacTomo;
    }

    public void setSelPacTomo(EstudioRealImagen oSelPacTomo) {
        this.oSelPacTomo = oSelPacTomo;
    }

    public EstudioRealImagen getSelPacRep() {
        return oSelPacRep;
    }

    public void setSelPacRep(EstudioRealImagen oSelPacRep) {
        this.oSelPacRep = oSelPacRep;
    }

    public List<EstudioRealImagen> getOrdenesPendDos() {
        return lOrdenesPendDos;
    }

    public void setOrdenesPendDos(List<EstudioRealImagen> lOrdenesPendDos) {
        this.lOrdenesPendDos = lOrdenesPendDos;
    }

    public List<EstudioRealImagen> getOrdenesPendTres() {
        return lOrdenesPendTres;
    }

    public void setOrdenesPendTres(List<EstudioRealImagen> lOrdenesPendTres) {
        this.lOrdenesPendTres = lOrdenesPendTres;
    }

    public List<EstudioRealImagen> getOrdenesPendCuatro() {
        return lOrdenesPendCuatro;
    }

    public void setOrdenesPendCuatro(List<EstudioRealImagen> lOrdenesPendCuatro) {
        this.lOrdenesPendCuatro = lOrdenesPendCuatro;
    }

    public List<EstudioImagen> getRepRx() {
        return lRepRx;
    }

    public void setRepRx(List<EstudioImagen> lRepRx) {
        this.lRepRx = lRepRx;
    }

    public List<EstudioImagen> getRepMasto() {
        return lRepMasto;
    }

    public void setRepMasto(List<EstudioImagen> lRepMasto) {
        this.lRepMasto = lRepMasto;
    }

    public List<EstudioImagen> getRepUsg() {
        return lRepUsg;
    }

    public void setRepUsg(List<EstudioImagen> lRepUsg) {
        this.lRepUsg = lRepUsg;
    }

    public List<EstudioImagen> getRepTomo() {
        return lRepTomo;
    }

    public void setRepTomo(List<EstudioImagen> lRepTomo) {
        this.lRepTomo = lRepTomo;
    }

    public Date getFechaInicial() {
        return dFechaInicial;
    }

    public void setFechaInicial(Date dFechaInicial) {
        this.dFechaInicial = dFechaInicial;
    }

    public Date getFechaFinal() {
        return dFechaFinal;
    }

    public void setFechaFinal(Date dFechaFinal) {
        this.dFechaFinal = dFechaFinal;
    }

    public EstudioImagen getEstudioImagen() {
        return oEstudioImagen;
    }

    public void setEstudioImagen(EstudioImagen oEstudioImagen) {
        this.oEstudioImagen = oEstudioImagen;
    }

 
    
}
