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
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.DetalleRecetarioColectivo;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael
 */
@ManagedBean (name="oGenRecCol")
@ViewScoped

public class GenerarRecColecJB {
    private AccesoDatos oAD;
    private PersonalHospitalario oPerHosp;
    private String sOcultarBusqueda1;
    private String sOcultarBusqueda2;
    private AreaServicioHRRB oAreaServ;
    private AreaServicioHRRB arrAreaActual[];
    private Paciente oPaciente;
    private EpisodioMedico oSeleccionado;
    private EpisodioMedico oEpisodioMedico;
    private Medicamento oMedicamento;
    private DetalleRecetarioColectivo oDetalleRecColec;
    private ArrayList<DetalleRecetarioColectivo> arrReceta;
    private ArrayList<DetalleRecetarioColectivo> arrRecetaMedSol;
    private ArrayList<DetalleRecetarioColectivo> arrRecetaMedSolTot;
    private ArrayList<DetalleRecetarioColectivo> arrRecetaMedRes;
    private ArrayList<EpisodioMedico> arrEpiMed;
    private String sVisibilidadTabla="hidden";
    private String sVisibilidadRegistroMed="hidden";
    private Date dFechaActual;
    private Date dFechaAux;
    private boolean bBuscado = false;
    
    public GenerarRecColecJB() throws Exception{
        HttpServletRequest req;
        oPerHosp=new PersonalHospitalario();
        oPerHosp.setUsuar(new Usuario());
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm")!=null){
            oPerHosp.getUsuar().setIdUsuario(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            oPerHosp.buscaUsuarioFirmado();
            ocultarBusquedaCE();
            ocultarBusquedaURGHOSP();
        }
        
        oPaciente = new Paciente();
        oAreaServ = new AreaServicioHRRB();
        oEpisodioMedico = new EpisodioMedico();
        oDetalleRecColec = new DetalleRecetarioColectivo();
        oMedicamento = new Medicamento();
        dFechaActual = new Date();
        arrReceta = new ArrayList<DetalleRecetarioColectivo>();
        arrRecetaMedSol = new ArrayList<DetalleRecetarioColectivo>();
        arrRecetaMedSolTot = new ArrayList<DetalleRecetarioColectivo>();
    }
    
    
////////////////////////////////////////////////////////////////////////////////BUSCA PACIENTE
    private void ocultarBusquedaCE(){
        if(oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("CEENF")==0){
            sOcultarBusqueda1="true";
        }else{
            sOcultarBusqueda1="false";
        }
    }
    
    private void ocultarBusquedaURGHOSP(){
        if(oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("HOSPENF")==0 || 
                oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("ONCOENF")==0){
            sOcultarBusqueda2="true";
        }else{
            sOcultarBusqueda2="false";
        }
    }
    
    public ArrayList<EpisodioMedico> getListaEpiMed(){
        return arrEpiMed;
    }
    
    public AreaServicioHRRB[] getListaAreasHosp(){
        try {
            arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosHospitalizacion();
            //oSeleccionado = null;
        }   catch (Exception ex) {
                Logger.getLogger(GenerarRecColecJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        return arrAreaActual;
    }
    
    public AreaServicioHRRB[] getListaAreasCE(){
        try {
            arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosConsultaExterna();
            //oSeleccionado = null;
        }   catch (Exception ex) {
                Logger.getLogger(GenerarRecColecJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        return arrAreaActual;
    }
    
    public AreaServicioHRRB[] getListaAreas() {
        if (oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("HOSPENF")==0) {
            try {
                arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosHospitalizacion();
            } catch (Exception ex) {
                Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (oPerHosp.getUsuar().getPerfil().get(0).getClave().compareTo("CEENF")==0) {
            try {
                arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosConsultaExterna();
            } catch (Exception ex) {
                Logger.getLogger(GenerarRecColecJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return arrAreaActual;
    }
    
    public EpisodioMedico getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EpisodioMedico oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
        limpiarMedicamentoSolSur();
        limpiarTabla();
        borrarTodo();
    }
    
    public void buscaPacienteHOSP(){
        sVisibilidadTabla="visible";
        try{
            System.out.println("FECHA AUX: "+this.dFechaActual);
            System.out.println("AREA SERVICIO ACTUAL: "+this.oAreaServ.getClave());
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarPacientesColectivosHosp(dFechaActual, oAreaServ.getClave())));
        }catch (Exception e){
            e.printStackTrace();
        }
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
    
    public void llenadoDatosPersonales() throws Exception{
            if(oSeleccionado == null){
                FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡Seleccione un paciente!");
                RequestContext.getCurrentInstance().showMessageInDialog(msj2);
                bBuscado = false;
                oEpisodioMedico = null;
            }else{
                oEpisodioMedico = oSeleccionado;
                bBuscado=true;
                sVisibilidadTabla="hidden";
            }
    } 
    
    public String getVisible(){
        if(this.bBuscado)
            return "visible";
        else
            return "hidden";                
    }
    
    
////////////////////////////////////////////////////////////////////////////////
    
    
    public void registroMedicamento() {
        oDetalleRecColec.setServicioCobrable(oMedicamento);
        if (getMedicamento().getNombre() == null || 
            getMedicamento().getNombre().equals("") || 
            oDetalleRecColec.getCantidadSol() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR", 
                    "SELECCIONE UN MEDICAMENTO"));
        } else {
            if (buscaRepetidoMedic(oMedicamento.getClaveMedicamento())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", 
                                "EL MEDICAMENTO YA FUE PREVIAMENTE AGREGADO"));
                oMedicamento = new Medicamento();
                oMedicamento.setNombre("");
                oMedicamento.setClaveMedicamento("");
                oMedicamento.setPresentacion("");
                oDetalleRecColec.setCantidadSol(0);
            } else {
                if (!getMedicamento().getNombre().equals("") && 
                    oDetalleRecColec.getCantidadSol() != 0) {
                    oDetalleRecColec.setEpisodio(oEpisodioMedico);
                    oDetalleRecColec.setAutorizadoPor(oPerHosp);
                    arrReceta.add(oDetalleRecColec);
                    oMedicamento = new Medicamento();
                    oMedicamento.setNombre("");
                    oMedicamento.setClaveMedicamento("");
                    oMedicamento.setPresentacion("");
                    sVisibilidadRegistroMed = "visible";
                } else {
                    oMedicamento.setNombre("");
                    oMedicamento.setClaveMedicamento("");
                    oMedicamento.setPresentacion("");
        }
    }
        }
    }
    
    public boolean buscaRepetidoMedic(String clave) {
        boolean bandera = false;
        for (DetalleRecetarioColectivo i : arrReceta) {
            if (i.getMedicamento().getClaveMedicamento().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }
    
    public void borrarElemento(DetalleRecetarioColectivo obj){
        arrReceta.remove(obj);
        if(arrReceta.isEmpty()){
            sVisibilidadRegistroMed="hidden";
        }else{
            sVisibilidadRegistroMed="visible";
        }
    }
    
    public void guardarRecCol() throws Exception{
        try{
            if(oDetalleRecColec.insertaRecetarioColectivo(arrReceta) == 1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de medicamento colectivo", "Receta Guardada Correctamente"));
                limpiarTabla();
            }
        }catch(Exception e){
            Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void buscaClave() throws Exception{
        oMedicamento.buscarPorNombre();
    }
    
    public void limpiarTabla(){
        arrReceta.clear();
        sVisibilidadRegistroMed="hidden";
    }
    
    public void borrarTodo(){
        oMedicamento.setClaveMedicamento("");
        oMedicamento.setNombre("");
        oMedicamento.setPresentacion("");
        oDetalleRecColec.setCantidadSol(0);
    }
    
    
////////////////////////////////////////////////////////////////////////////////
    
    
    public void buscaMedicamentoSolSur(){
        sVisibilidadTabla="visible";
        try{
            arrRecetaMedSol = new ArrayList<DetalleRecetarioColectivo>(Arrays.asList(oDetalleRecColec.buscarMedicamentoSolicitadosPaciente(dFechaAux, oEpisodioMedico.getArea().getClave(), oEpisodioMedico.getPaciente().getFolioPaciente(), oEpisodioMedico.getClaveEpisodio())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void buscaMedicamentoSolSurTot(){
        sVisibilidadTabla="visible";
        try{
            arrRecetaMedSolTot = new ArrayList<DetalleRecetarioColectivo>(
                    Arrays.asList(
                            oDetalleRecColec.buscarTotalMedicamentoSolSur(
                                    dFechaAux, oAreaServ.getClave())));
            this.oAreaServ.equalsArea(arrAreaActual);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void buscaMedicamentoResurtidoArea(){
        sVisibilidadTabla="visible";
        try{
            arrRecetaMedRes = new ArrayList<DetalleRecetarioColectivo>(Arrays.asList(oDetalleRecColec.buscarMedicamentoControladoResurtidoArea(dFechaAux, oAreaServ.getClave())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void limpiarMedicamentoSolSur(){
        arrRecetaMedSol.clear();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public AccesoDatos getAD() {
        return oAD;
    }

    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }

    public AreaServicioHRRB getAreaServ() {
        return oAreaServ;
    }

    public void setAreaServ(AreaServicioHRRB oAreaServ) {
        this.oAreaServ = oAreaServ;
    }

    public AreaServicioHRRB[] getAreaActual() {
        return arrAreaActual;
    }

    public void setAreaActual(AreaServicioHRRB[] arrAreaActual) {
        this.arrAreaActual = arrAreaActual;
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

    public Medicamento getMedicamento() {
        return oMedicamento;
    }

    public void setMedicamento(Medicamento oMedicamento) {
        this.oMedicamento = oMedicamento;
    }

    public DetalleRecetarioColectivo getDetalleRecColec() {
        return oDetalleRecColec;
    }

    public void setDetalleRecColec(DetalleRecetarioColectivo oDetalleRecColec) {
        this.oDetalleRecColec = oDetalleRecColec;
    }

    public ArrayList<DetalleRecetarioColectivo> getReceta() {
        return arrReceta;
    }

    public void setReceta(ArrayList<DetalleRecetarioColectivo> arrReceta) {
        this.arrReceta = arrReceta;
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
    
    public PersonalHospitalario getPerHosp() {
        return oPerHosp;
    }

    public void setPerHosp(PersonalHospitalario oPerHosp) {
        this.oPerHosp = oPerHosp;
    }

    public ArrayList<DetalleRecetarioColectivo> getRecetaMedSol() {
        return arrRecetaMedSol;
    }

    public void setRecetaMedSol(ArrayList<DetalleRecetarioColectivo> arrRecetaMedSol) {
        this.arrRecetaMedSol = arrRecetaMedSol;
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

    public ArrayList<DetalleRecetarioColectivo> getRecetaMedRes() {
        return arrRecetaMedRes;
}

    public void setRecetaMedRes(ArrayList<DetalleRecetarioColectivo> arrRecetaMedRes) {
        this.arrRecetaMedRes = arrRecetaMedRes;
    }

    public ArrayList<DetalleRecetarioColectivo> getRecetaMedSolTot() {
        return arrRecetaMedSolTot;
    }

    public void setRecetaMedSolTot(ArrayList<DetalleRecetarioColectivo> arrRecetaMedSolTot) {
        this.arrRecetaMedSolTot = arrRecetaMedSolTot;
    }
}
