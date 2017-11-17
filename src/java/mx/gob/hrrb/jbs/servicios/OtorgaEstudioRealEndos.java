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
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.serv.EstudioRealEndos;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Pablo
 */
@ManagedBean (name="EstRealEndos")
@ViewScoped
public class OtorgaEstudioRealEndos extends OtorgarServicio implements Serializable{

    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private String sUrl;
    private EstudioRealEndos oEstRealEnd;
    private EstudioRealizado oEstReal;
    private List<EstudioRealEndos> lOrdenesEndos;
    private List<EstudioRealEndos> lProceQx;
    private Date dFecha;
    private HttpSession session;
    private String sVisibleTabla="hidden";
    private EstudioRealEndos oEstEndos;
    private EstudioRealEndos oSeleccionado;
    private String sNombre;
    private String sApPaterno;
    private String sApMaterno;
    private int nNumExpe;
    private Date dFecRealizacion;
    private Date dFechaQx;
    private String sFecNac;
    private Medicamento oMed;
    private Material oMat;
    private List<EstudioRealEndos> lMaterial;
    private List<EstudioRealEndos> lMedicamento;
    
    public OtorgaEstudioRealEndos() {
    }
    
    public void inicializar(){
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("oFirm")!=null){
            oFirm = (Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            sUrl = httpServletRequest.getRequestURL().toString().toLowerCase();
        }
    }

    @Override
    public List buscarDetalleSolicitud() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void rebotes(ActionEvent ae) throws Exception{
        listaOrdenes();
    }
    
    public void rebotesPacPend(ActionEvent ae) throws Exception{
        listaPacientesPendReporte();
    }
    
    @Override
    public void listaOrdenes() {
        sVisibleTabla ="visible";
        try{
            lOrdenesEndos = new ArrayList<EstudioRealEndos>(Arrays.asList(new EstudioRealEndos().buscarOrdenesServicio(getFecha())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void detalleSolEndos() throws Exception{
        if(oSeleccionado == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un paciente",""));
        }else if (oSeleccionado != null){
            oEstEndos = new EstudioRealEndos().buscarDetOrdServEndos(oSeleccionado.getEstReal().getIdentificador());
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocurrio un error inesperado",""));
        }
    }
    
    public void listaPacientesPendReporte(){
        try{
            lOrdenesEndos = new ArrayList<EstudioRealEndos>(Arrays.asList(new EstudioRealEndos().buscarPacienteEndos(getNombre(), getApPaterno(), getApMaterno(), getNumExpe())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException{
        FacesContext facesContext  = FacesContext.getCurrentInstance();
        String sRuta = facesContext.getExternalContext().getRealPath("")+"" + 
                EstudioRealEndos.RUTA_ARCHIVO;
        OutputStream out = null;
        Date oFec = new Date();
        SimpleDateFormat oFecha = new SimpleDateFormat("yyyy-MM-dd");
        InputStream fileContent = null;
        byte[] bytes = new byte[1024];
        int nRead;
        try{
            
            sNombre = "" + oSeleccionado.getEstReal().getIdentificador() + "" + oSeleccionado.getEpisodio().getPaciente().getNombres().substring(0, 2);
            sNombre = sNombre + "" + oSeleccionado.getEpisodio().getPaciente().getApPaterno().substring(0,2);
            sNombre = sNombre + oFecha.format(oFec) + ".PDF";
            System.out.println(sNombre);
            if(oEstEndos.modificarReporteEndos(oSeleccionado.getEstReal().getIdentificador(), sNombre, oEstEndos.getEstReal().getEstudio().getClaveInterna(), oEstEndos.getEpisodio().getPaciente().getFolioPaciente())==1){
                out = new FileOutputStream(new File(sRuta + File.separator + sNombre));
                fileContent  = event.getFile().getInputstream();
                while((nRead = fileContent.read(bytes))!=-1){
                    out.write(bytes, 0, nRead);
                }
                System.out.println(sRuta);
                FacesMessage msg = new FacesMessage("El archivo", sNombre + " fue almacenado correctamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }else if(oEstEndos.modificarReporteEndos(oSeleccionado.getEstReal().getIdentificador(), sNombre,  oEstEndos.getEstReal().getEstudio().getClaveInterna(), oEstEndos.getEpisodio().getPaciente().getFolioPaciente())!= 1){
                FacesMessage msg = new FacesMessage("ERROR", sNombre + " ya está asignado a un paciente");
                 FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",event.getFile().getFileName() + "no almacenado"));
        }finally{
            if(out != null){
                out.close();
            }
            if(fileContent != null){
                fileContent.close();
            }
        }
         
    }
    
    public List<EstudioRealEndos> getListaMatEst(){
        try{
            lMaterial = new ArrayList<EstudioRealEndos>(Arrays.asList(new EstudioRealEndos().buscarMaterialPorEstEndos()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lMaterial;
    }
    
    public List<EstudioRealEndos> getListaMedEst(){
        try{
            lMedicamento = new ArrayList<EstudioRealEndos>(Arrays.asList(new EstudioRealEndos().buscarMedEstEndos()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lMedicamento;
    }
    
    public void buscarProceQxEndos(){
        try{
            lProceQx = new ArrayList<EstudioRealEndos>(Arrays.asList(new EstudioRealEndos().buscarProcedimientosQuirofano(getFechaQx())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
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
        return sUrl;
    }

    public void setUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public EstudioRealEndos getEstRealEnd() {
        return oEstRealEnd;
    }

    public void setEstRealEnd(EstudioRealEndos oEstRealEnd) {
        this.oEstRealEnd = oEstRealEnd;
    }

    public EstudioRealizado getEstReal() {
        return oEstReal;
    }

    public void setEstReal(EstudioRealizado oEstReal) {
        this.oEstReal = oEstReal;
    }

    public List<EstudioRealEndos> getOrdenesEndos() {
        return lOrdenesEndos;
    }

    public void setOrdenesEndos(List<EstudioRealEndos> lOrdenesEndos) {
        this.lOrdenesEndos = lOrdenesEndos;
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

    public String getVisibleTabla() {
        return sVisibleTabla;
    }

    public void setVisibleTabla(String sVisibleTabla) {
        this.sVisibleTabla = sVisibleTabla;
    }

    public EstudioRealEndos getEstEndos() {
        return oEstEndos;
    }

    public void setEstEndos(EstudioRealEndos oEstEndos) {
        this.oEstEndos = oEstEndos;
    }

    public EstudioRealEndos getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EstudioRealEndos oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
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

    public Date getFecRealizacion() {
        return dFecRealizacion;
    }

    public void setFecRealizacion(Date dFecRealizacion) {
        this.dFecRealizacion = dFecRealizacion;
    }

    public String getFecNac() {
        return sFecNac;
    }

    public void setFecNac(String sFecNac) {
        this.sFecNac = sFecNac;
    }

    public Medicamento getMed() {
        return oMed;
    }

    public void setMed(Medicamento oMed) {
        this.oMed = oMed;
    }

    public Material getMat() {
        return oMat;
    }

    public void setMat(Material oMat) {
        this.oMat = oMat;
    }

    public List<EstudioRealEndos> getMaterial() {
        return lMaterial;
    }

    public void setMaterial(List<EstudioRealEndos> lMaterial) {
        this.lMaterial = lMaterial;
    }

    public List<EstudioRealEndos> getMedicamento() {
        return lMedicamento;
    }

    public void setlMedicamento(List<EstudioRealEndos> lMedicamento) {
        this.lMedicamento = lMedicamento;
    }

    public List<EstudioRealEndos> getProceQx() {
        return lProceQx;
    }

    public void setProceQx(List<EstudioRealEndos> lProceQx) {
        this.lProceQx = lProceQx;
    }

    public Date getFechaQx() {
        return dFechaQx;
    }

    public void setFechaQx(Date dFechaQx) {
        this.dFechaQx = dFechaQx;
    }
    
}
