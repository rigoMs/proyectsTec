package mx.gob.hrrb.jbs.comunes;

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
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.DetalleValeColectivo;
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
@ManagedBean (name="oGenSolValeMatCol")
@ViewScoped

public class GenSolValeMatColecJB {
    private AccesoDatos oAD;
    private PersonalHospitalario oPerHosp;
    private String sOcultarBusqueda1;
    private String sOcultarBusqueda2;
    private String sOcultarBusqueda3;
    private AreaServicioHRRB oAreaServ;
    private AreaServicioHRRB arrAreaActual[];
    private Paciente oPaciente;
    private EpisodioMedico oSeleccionado;
    private EpisodioMedico oEpisodioMedico;
    private Material oMaterial;
    private DetalleValeColectivo oDetValColect;
    private ArrayList<DetalleValeColectivo> arrVale;
    private ArrayList<DetalleValeColectivo> arrValeMatSol;
    private ArrayList<EpisodioMedico> arrEpiMed;
    private ArrayList<DetalleValeColectivo> lMaterialLista;
    private String sVisibilidadTabla="hidden";
    private String sVisibilidadRegistroMat="hidden";
    private Date dFechaActual;
    private Date dFechaAux;
    private Date dFechaAux2;
    private boolean bBuscado = false;
    
    public GenSolValeMatColecJB() throws Exception{
        HttpServletRequest req;
        oPerHosp=new PersonalHospitalario();
        oPerHosp.setUsuar(new Usuario());
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm")!=null){
            oPerHosp.getUsuar().setIdUsuario(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            oPerHosp.buscaUsuarioFirmado();
            ocultarBusquedaCE();
            ocultarBusquedaHOSP();
            ocultarBusquedaURG();
        }
        
        oPaciente = new Paciente();
        oAreaServ = new AreaServicioHRRB();
        oSeleccionado = new EpisodioMedico();
        oEpisodioMedico = new EpisodioMedico();
        oDetValColect = new DetalleValeColectivo();
        oMaterial = new Material();
        dFechaActual = new Date();
        arrVale = new ArrayList<DetalleValeColectivo>();
        arrValeMatSol = new ArrayList<DetalleValeColectivo>();
    }
    
////////////////////////////////////////////////////////////////////////////////BUSCA PACIENTE
    
    
    private void ocultarBusquedaCE(){
        if (oPerHosp.getUsuar().getCvePerfil().compareTo("CEENF")==0) {
            sOcultarBusqueda1="true";
            System.out.println("Dentro de IF Mostrar Busqueda CE "+this.getOcultarBusqueda1());
        }else{
            sOcultarBusqueda1="false";
            System.out.println("Dentro de ELSE No Mostrar Busqueda CE "+this.getOcultarBusqueda1());
        }
    }
    
    private void ocultarBusquedaHOSP(){
        if (oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("HOSPENF")==0 || 
                oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("ONCOENF")==0) {
            sOcultarBusqueda2="true";
        }else{
            sOcultarBusqueda2="false";
        }
    }
    
    private void ocultarBusquedaURG(){
        if (oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("URGENF")==0) {
            sOcultarBusqueda3="true";
        }else{
            sOcultarBusqueda3="false";
        }
    }
    
    public AreaServicioHRRB[] getListaAreasCE(){
        try {
            arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosConsultaExterna();
        }   catch (Exception ex) {
                Logger.getLogger(GenSolValeMatColecJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        return arrAreaActual;
    }
    
    public AreaServicioHRRB[] getListaAreasHosp(){
        try {
            arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosHospitalizacion();
        }   catch (Exception ex) {
                Logger.getLogger(GenSolValeMatColecJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        return arrAreaActual;
    }
    
    public AreaServicioHRRB[] getListaAreasUrg() {
        try {
            arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosUrgencias();
        } catch (Exception ex) {
            Logger.getLogger(GenSolValeMatColecJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrAreaActual;
    }
    
    public EpisodioMedico getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EpisodioMedico oSeleccionado) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteselecc", oSeleccionado);
    }
    
    public void buscaPacienteCE(){
        sVisibilidadTabla="visible";
        try{
            System.out.println("FECHA AUX: "+this.dFechaAux);
            System.out.println("AREA SERVICIO ACTUAL: "+this.oAreaServ.getClave());
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarPacientesColectivosCE(dFechaAux, oAreaServ.getClave())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void buscaPacienteHosp(){
        sVisibilidadTabla="visible";
        try{
            System.out.println("FECHA AUX: "+this.dFechaActual);
            System.out.println("AREA SERVICIO ACTUAL: "+this.oAreaServ.getClave());
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarPacientesColectivosHosp(dFechaActual, oAreaServ.getClave())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void buscaPacienteUrg(){
        sVisibilidadTabla="visible";
        try{
            System.out.println("FECHA AUX: "+this.dFechaAux);
            System.out.println("AREA SERVICIO ACTUAL: "+this.oAreaServ.getClave());
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarPacientesColectivosURG(dFechaAux, oAreaServ.getClave())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void llenadoDatosPersonales() throws Exception{
        EpisodioMedico oEpiMed;
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
        oDetValColect.setServicioCobrable(oMaterial);
        if(oMaterial.getNombre().equals("") && 
                oDetValColect.getCantSolicitada()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Seleccione un material"));
        }else if(oMaterial.getNombre()!=null && 
                oDetValColect.getCantSolicitada()!=0)
        {
                oDetValColect.setEpisodio(oEpisodioMedico);
                oDetValColect.setAutorizadoPor(oPerHosp);
                oDetValColect.getVale().setAreaAtiende(oAreaServ);
                arrVale.add(oDetValColect);
                oMaterial = new Material();
                
                sVisibilidadRegistroMat="visible";
        }
    }
    
    public void borrarElemento(DetalleValeColectivo obj){
        this.arrVale.remove(obj);
        if(arrVale.isEmpty()){
            sVisibilidadRegistroMat="hidden";
        }else{
            sVisibilidadRegistroMat="visible";
        }
    }
    
    public void guardarMatCol() throws Exception{
        try{
            if(this.oDetValColect.insertaSolMatCol(arrVale) == 1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Material Colectivo", "Receta Guardada Correctamente"));
                limpiarTabla();
            }
        }catch(Exception e){
            Logger.getLogger(GenSolValeMatColecJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void guardarMatArea() throws Exception {
        try {
            if(oDetValColect.insertaSolMatCEYE(lMaterialLista) == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Material por Área", "Solicitud Guardada Correctamente"));
                limpiarTabla();
            }
        } catch (Exception ex) {
            Logger.getLogger(GenSolValeMatColecJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaClave() throws Exception{
        oMaterial.buscarPorNombre();
    }
    
    public void limpiarTabla(){
        arrVale.clear();
        sVisibilidadRegistroMat="hidden";
    }
////////////////////////////////////////////////////////////////////////////////
    
    
    public void buscaMaterialSolSur(){
        sVisibilidadTabla="visible";
        try{
            arrValeMatSol = new ArrayList<DetalleValeColectivo>(
                    Arrays.asList(
                            oDetValColect.buscarMaterialesSolicitadosValeColectivoPaciente(dFechaAux2, oAreaServ.getClave(), oEpisodioMedico.getPaciente().getFolioPaciente(), oEpisodioMedico.getClaveEpisodio())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public AreaServicioHRRB[] getListaAreas() {
        if (oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("HOSPENF")==0 || 
                oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("ONCOENF")==0) {
            try {
                arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosHospitalizacion();
            } catch (Exception ex) {
                Logger.getLogger(GenSolValeMatColecJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("URGENF")==0) {
            try {
                arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosUrgencias();
            } catch (Exception ex) {
                Logger.getLogger(GenSolValeMatColecJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("LABJEFE")==0 || 
                oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("ENDOENF")==0 || 
                oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("PATJEFE")==0 || 
                oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("IMGJEFE")==0){
            try{
                arrAreaActual = (new AreaServicioHRRB()).buscarTodosTipoServicio();
            } catch (Exception ex) {
                Logger.getLogger(GenSolValeMatColecJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        return arrAreaActual;
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

    public Material getMaterial() {
        return oMaterial;
    }

    public void setMaterial(Material oMaterial) {
        this.oMaterial = oMaterial;
    }

    public String getVisibilidadRegistroMat() {
        return sVisibilidadRegistroMat;
    }

    public void setVisibilidadRegistroMat(String sVisibilidadRegistroMat) {
        this.sVisibilidadRegistroMat = sVisibilidadRegistroMat;
    }

    public AreaServicioHRRB getAreaServ() {
        return oAreaServ;
    }

    public void setAreaServ(AreaServicioHRRB oAreaServ) {
        this.oAreaServ = oAreaServ;
    }

    public DetalleValeColectivo getDetValColect() {
        return oDetValColect;
    }

    public void setDetValColect(DetalleValeColectivo oDetValColect) {
        this.oDetValColect = oDetValColect;
    }

    public ArrayList<DetalleValeColectivo> getVale() {
        return arrVale;
    }

    public void setVale(ArrayList<DetalleValeColectivo> arrVale) {
        this.arrVale = arrVale;
    }

    public PersonalHospitalario getPerHosp() {
        return oPerHosp;
    }

    public void setPerHosp(PersonalHospitalario oPerHosp) {
        this.oPerHosp = oPerHosp;
    }

    public Date getFechaAux2() {
        return dFechaAux2;
    }

    public void setFechaAux2(Date dFechaAux2) {
        this.dFechaAux2 = dFechaAux2;
    }

    public ArrayList<DetalleValeColectivo> getValeMatSol() {
        return arrValeMatSol;
    }

    public void setValeMatSol(ArrayList<DetalleValeColectivo> arrValeMatSol) {
        this.arrValeMatSol = arrValeMatSol;
    }
    
    public String getOcultarBusqueda1() {
        return sOcultarBusqueda1;
    }

    public void setOcultarBusqueda1(String sOcultarBusqueda1) {
        this.sOcultarBusqueda1 = sOcultarBusqueda1;
    }

    public String getOcultarBusqueda2() {
        return sOcultarBusqueda2;
    }

    public void setOcultarBusqueda2(String sOcultarBusqueda2) {
        this.sOcultarBusqueda2 = sOcultarBusqueda2;
    }

    public String getOcultarBusqueda3() {
        return sOcultarBusqueda3;
    }

    public void setOcultarBusqueda3(String sOcultarBusqueda3) {
        this.sOcultarBusqueda3 = sOcultarBusqueda3;
    }
    
    public ArrayList<DetalleValeColectivo> getMaterialLista() {
        return lMaterialLista;
    }

    public void setMaterialLista(ArrayList<DetalleValeColectivo> lMaterialLista) {
        this.lMaterialLista = lMaterialLista;
    }
}
