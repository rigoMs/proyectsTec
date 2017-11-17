package mx.gob.hrrb.jbs.comunes;

import java.io.Serializable;
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
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.core.DetalleValeMaterial;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael
 */
@ManagedBean (name="oGenSolValeMatPac")
@ViewScoped

public class GenerarSolicitudValeMatPacJB implements Serializable{
    private AccesoDatos oAD;
    private PersonalHospitalario oPerHosp;
    private Paciente oPaciente;
    private EpisodioMedico oSeleccionado;
    private EpisodioMedico oEpisodioMedico;
    private DetalleValeMaterial oDetalleValMat;
    private Material oMaterial;
    private ArrayList<DetalleValeMaterial> arrVale;
    private ArrayList<DetalleValeMaterial> arrValeMatSol;
    private ArrayList<EpisodioMedico> arrEpiMed;
    private String sVisibilidadTabla="hidden";
    private String sVisibilidadRegistroMat="hidden";
    private Date dFechaActual;
    private Date dFechaAux;
    private boolean bBuscado = false;
    
    public GenerarSolicitudValeMatPacJB() throws Exception{
        HttpServletRequest req;
        oPerHosp=new PersonalHospitalario();
        oPerHosp.setUsuar(new Usuario());
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm")!=null){
            oPerHosp.getUsuar().setIdUsuario(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            oPerHosp.buscaUsuarioFirmado();
        }
        
        oPaciente = new Paciente();
        oSeleccionado = new EpisodioMedico();
        oEpisodioMedico = new EpisodioMedico();
        oDetalleValMat = new DetalleValeMaterial();
        oMaterial = new Material();
        dFechaActual = new Date();
        arrVale = new ArrayList<DetalleValeMaterial>();
        arrValeMatSol = new ArrayList<DetalleValeMaterial>();
    }
    
    
////////////////////////////////////////////////////////////////////////////////BUSCA PACIENTE
    
    
    public EpisodioMedico getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EpisodioMedico oSeleccionado) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteselecc", oSeleccionado);
    }
    
    public void buscaPacienteAtenMed(){
        sVisibilidadTabla="visible";
        try{
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarDatosPacienteAtencionMedica()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void llenadoDatosPersonales() throws Exception{
        EpisodioMedico oEpiMed=null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
            oEpiMed=(EpisodioMedico)session.getAttribute("pacienteselecc");
            if(oEpiMed==null){
                FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡Seleccione un paciente!");
                RequestContext.getCurrentInstance().showMessageInDialog(msj2);
                bBuscado = false;
            }else{
                oEpisodioMedico = oEpiMed;
                bBuscado=true;
                sVisibilidadTabla="hidden";
            }
    } 
    
    public void limpiar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteselecc", null);
        sVisibilidadTabla="hidden";
    }
    
    public String getVisible(){
        if(this.bBuscado)
            return "visible";
        else
            return "hidden";                
    }
    
    
////////////////////////////////////////////////////////////////////////////////
    
    public void registroMaterial(){
        oDetalleValMat.setServicioCobrable(oMaterial);
        if(oMaterial.getNombre().equals("") &&
                oDetalleValMat.getCantSolicitada()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Seleccione un material"));
        }else if(oMaterial.getNombre()!=null && 
                oDetalleValMat.getCantSolicitada()!=0){
            
                oDetalleValMat.setEpisodio(oEpisodioMedico);
                oDetalleValMat.setAutorizadoPor(oPerHosp);
                arrVale.add(oDetalleValMat);
                oMaterial = new Material();
                
                sVisibilidadRegistroMat="visible";
        }
    }
    
    public void borrarElemento(DetalleValeMaterial obj){
        this.arrVale.remove(obj);
        if(arrVale.isEmpty()){
            sVisibilidadRegistroMat="hidden";
        }else{
            sVisibilidadRegistroMat="visible";
        }
    }
    
    public void buscaClave() throws Exception{
        oMaterial.buscarPorNombre();
    }
    
    public void limpiarTabla(){
        arrVale.clear();
        sVisibilidadRegistroMat="hidden";
    }
            
    public void guardarMatInd() throws Exception{
        try{
            if(this.oDetalleValMat.insertaSolMatInd(arrVale) == 1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Material", "Solicitud Guardada Correctamente"));
                limpiarTabla();
            }
        }catch(Exception e){
            Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
////////////////////////////////////////////////////////////////////////////////
    
    
    public void buscaMaterialSolSur(){
        sVisibilidadTabla="visible";
        try{
            arrValeMatSol = new ArrayList<DetalleValeMaterial>(Arrays.asList(oDetalleValMat.buscarMaterialesSolicitadosPaciente(dFechaAux, oEpisodioMedico.getArea().getClave(), oEpisodioMedico.getPaciente().getFolioPaciente(), oEpisodioMedico.getClaveEpisodio())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    
    public ArrayList<EpisodioMedico> getListaEpiMed(){
        return arrEpiMed;
    }

    public String getVisibilidadTabla() {
        return sVisibilidadTabla;
    }

    public void setVisibilidadTabla(String sVisibilidadTabla) {
        this.sVisibilidadTabla = sVisibilidadTabla;
    }

    public AccesoDatos getAD() {
        return oAD;
    }

    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }

    public Paciente getoPaciente() {
        return oPaciente;
    }

    public void setoPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public ArrayList<EpisodioMedico> getEpiMed() {
        return arrEpiMed;
    }

    public void setEpiMed(ArrayList<EpisodioMedico> arrEpiMed) {
        this.arrEpiMed = arrEpiMed;
    }    

    public Date getFechaActual() {
        return dFechaActual;
    }

    public void setFechaActual(Date dFechaActual) {
        this.dFechaActual = dFechaActual;
    }

    public boolean getBuscado() {
        return bBuscado;
    }

    public void setBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
    }

    public Date getFechaAux() {
        return dFechaAux;
    }

    public void setFechaAux(Date dFechaAux) {
        this.dFechaAux = dFechaAux;
    }

    public EpisodioMedico getEpisodioMedico() {
        return oEpisodioMedico;
    }

    public void setEpisodioMedico(EpisodioMedico oEpisodioMedico) {
        this.oEpisodioMedico = oEpisodioMedico;
    }

    public DetalleValeMaterial getDetalleValMat() {
        return oDetalleValMat;
    }

    public void setDetalleValMat(DetalleValeMaterial oDetalleValMat) {
        this.oDetalleValMat = oDetalleValMat;
    }

    public Material getMaterial() {
        return oMaterial;
    }

    public void setMaterial(Material oMaterial) {
        this.oMaterial = oMaterial;
    }

    public ArrayList<DetalleValeMaterial> getVale() {
        return arrVale;
    }

    public void setVale(ArrayList<DetalleValeMaterial> arrVale) {
        this.arrVale = arrVale;
    }

    public String getVisibilidadRegistroMat() {
        return sVisibilidadRegistroMat;
    }

    public void setVisibilidadRegistroMat(String sVisibilidadRegistroMat) {
        this.sVisibilidadRegistroMat = sVisibilidadRegistroMat;
    }

    public PersonalHospitalario getPerHosp() {
        return oPerHosp;
    }

    public void setPerHosp(PersonalHospitalario oPerHosp) {
        this.oPerHosp = oPerHosp;
    }

    public ArrayList<DetalleValeMaterial> getValeMatSol() {
        return arrValeMatSol;
    }

    public void setValeMatSol(ArrayList<DetalleValeMaterial> arrValeMatSol) {
        this.arrValeMatSol = arrValeMatSol;
    }
}