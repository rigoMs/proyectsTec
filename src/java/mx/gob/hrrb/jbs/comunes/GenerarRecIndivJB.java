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
import mx.gob.hrrb.modelo.core.DetalleRecetaHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Receta;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael
 */
@ManagedBean (name="oGenRecIndiv")
@ViewScoped

public class GenerarRecIndivJB implements Serializable{
    private AccesoDatos oAD;
    private PersonalHospitalario oPerHosp;
    private Paciente oPaciente;
    private EpisodioMedico oSeleccionado;
    private EpisodioMedico oEpisodioMedico;
    private Medicamento oMedicamento;
    private DetalleRecetaHRRB oDetalleRec;
    private Receta oRecetas;
    private ArrayList<DetalleRecetaHRRB> arrReceta;
    private ArrayList<DetalleRecetaHRRB> arrRecetaMedSol;
    private ArrayList<EpisodioMedico> arrEpiMed;
    private String sVisibilidadTabla="hidden";
    private String sVisibilidadRegistroMed="hidden";
    private Date dFechaActual;
    private Date dFechaAux;
    private boolean bBuscado = false;
    
    public GenerarRecIndivJB() throws Exception{
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
        oRecetas = new Receta();
        oDetalleRec = new DetalleRecetaHRRB();
        oMedicamento = new Medicamento();
        dFechaActual = new Date();
        arrReceta = new ArrayList<DetalleRecetaHRRB>();
        arrRecetaMedSol = new ArrayList<DetalleRecetaHRRB>();
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
    
    
    public void registroReceta(){
        oDetalleRec.setMedicamento(oMedicamento);
        if(oDetalleRec.getMedicamento().getNombre().equals("") 
                && oDetalleRec.getCantidad()==0 && oRecetas.getFolioReceta()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Seleccione un medicamento"));
        }else if(oDetalleRec.getMedicamento().getNombre()!=null 
                && oDetalleRec.getCantidad()!=0 && oRecetas.getFolioReceta()!=0){
            
                oDetalleRec.setEpisodio(oEpisodioMedico);
                System.out.println(this.getMedicamento().getGpoLeySalud());
                oDetalleRec.setAutorizadoPor(oPerHosp);
                oDetalleRec.setReceta(oRecetas);
                arrReceta.add(oDetalleRec);
                oMedicamento = new Medicamento();
                
                sVisibilidadRegistroMed="visible";
        }
    }
    
    public void guardarReceta() throws Exception{
        try{
            if(this.oDetalleRec.insertaRecetaInd(arrReceta) == 1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Medicamento", "Solicitud Guardada Correctamente"));
                limpiarTabla();
            }
        }catch(Exception e){
            Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void borrarElemento(DetalleRecetaHRRB obj){
        arrReceta.remove(obj);
        if(arrReceta.isEmpty()){
            sVisibilidadRegistroMed="hidden";
        }else{
            sVisibilidadRegistroMed="visible";
        }
    }
    
    public void buscaClave() throws Exception{
        oMedicamento.buscarPorNombre();
    }
    
    public void limpiarTabla(){
        arrReceta.clear();
        sVisibilidadRegistroMed="hidden";
    }
    
    
////////////////////////////////////////////////////////////////////////////////
    
    
    public void buscaMedicamentoSolSur(){
        sVisibilidadTabla="visible";
        try{
            arrRecetaMedSol = new ArrayList<DetalleRecetaHRRB>(Arrays.asList(this.oDetalleRec.buscarMedicamentosSolicitadosRecetaIndividualPaciente(dFechaAux, oEpisodioMedico.getArea().getClave(), oEpisodioMedico.getPaciente().getFolioPaciente(), oEpisodioMedico.getClaveEpisodio())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
////////////////////////////////////////////////////////////////////////////////

    public AccesoDatos getoAD() {
        return oAD;
    }

    public void setoAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public EpisodioMedico getEpisodioMedico() {
        return oEpisodioMedico;
    }

    public void setEpisodioMedico(EpisodioMedico oEpisodioMedico) {
        this.oEpisodioMedico = oEpisodioMedico;
    }

    public ArrayList<EpisodioMedico> getListaEpiMed(){
        return arrEpiMed;
    }

    public String getVisibilidadTabla() {
        return sVisibilidadTabla;
    }

    public void setVisibilidadTabla(String sVisibilidadTabla) {
        this.sVisibilidadTabla = sVisibilidadTabla;
    }

    public String getVisibilidadRegistroMed() {
        return sVisibilidadRegistroMed;
    }

    public void setVisibilidadRegistroMed(String sVisibilidadRegistroMed) {
        this.sVisibilidadRegistroMed = sVisibilidadRegistroMed;
    }

    public Date getFechaActual() {
        return dFechaActual;
    }

    public void setFechaActual(Date dFechaActual) {
        this.dFechaActual = dFechaActual;
    }

    public Date getFechaAux() {
        return dFechaAux;
    }

    public void setFechaAux(Date dFechaAux) {
        this.dFechaAux = dFechaAux;
    }

    public boolean getBuscado() {
        return bBuscado;
    }

    public void setBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
    }

    public DetalleRecetaHRRB getDetalleRec() {
        return oDetalleRec;
    }

    public void setDetalleRec(DetalleRecetaHRRB oDetalleRec) {
        this.oDetalleRec = oDetalleRec;
    }

    public Medicamento getMedicamento() {
        return oMedicamento;
    }

    public void setMedicamento(Medicamento oMedicamento) {
        this.oMedicamento = oMedicamento;
    }

    public ArrayList<DetalleRecetaHRRB> getReceta() {
        return arrReceta;
    }

    public void setReceta(ArrayList<DetalleRecetaHRRB> arrReceta) {
        this.arrReceta = arrReceta;
    }
    
    public Receta getRecetas() {
        return oRecetas;
    }

    public void setRecetas(Receta oRecetas) {
        this.oRecetas = oRecetas;
    }
    
    public PersonalHospitalario getPerHosp() {
        return oPerHosp;
    }

    public void setPerHosp(PersonalHospitalario oPerHosp) {
        this.oPerHosp = oPerHosp;
    }

    public ArrayList<DetalleRecetaHRRB> getRecetaMedSol() {
        return arrRecetaMedSol;
    }

    public void setRecetaMedSol(ArrayList<DetalleRecetaHRRB> arrRecetaMedSol) {
        this.arrRecetaMedSol = arrRecetaMedSol;
    }
}
