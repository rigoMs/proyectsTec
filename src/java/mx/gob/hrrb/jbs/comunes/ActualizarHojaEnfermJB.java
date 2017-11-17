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
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.MedicamentoAplicado;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael
 */
@ManagedBean(name = "oActHojaEnf")
@ViewScoped

public class ActualizarHojaEnfermJB {

    private AccesoDatos oAD;
    private PersonalHospitalario oPerHosp;
    private String sOcultarBusqueda1;
    private String sOcultarBusqueda2;
    private AreaServicioHRRB oAreaServ;
    private Paciente oPaciente;
    private EpisodioMedico oSeleccionado;
    private EpisodioMedico oEpisodioMedico;
    private MedicamentoAplicado oMedAplic;
    private Medicamento oMedicamento;
    private ArrayList<EpisodioMedico> arrEpiMed;
    private ArrayList<MedicamentoAplicado> arrMedicamentoAplicado;
    private ArrayList<MedicamentoAplicado> arrMediAplicado;
    private AreaServicioHRRB arrAreaActual[];
    private Medicamento arrMedicamentoRecetado[];
    private Parametrizacion arrVia[];
    private Turno arrTurno[];
    private String sVisibilidadTabla = "hidden";
    private String sVisibilidadRegistroMedAplic = "hidden";
    private Date dFechaActual;
    private Date dFechaAux;
    private boolean bBuscado = false;

    public ActualizarHojaEnfermJB() throws Exception {
        HttpServletRequest req;
        oPerHosp = new PersonalHospitalario();
        oPerHosp.setUsuar(new Usuario());
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
            oPerHosp.getUsuar().setIdUsuario(((Firmado) req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            oPerHosp.buscaUsuarioFirmado();
            ocultarBusquedaHOSP();
            ocultarBusquedaURG();
        }

        oPaciente = new Paciente();
        oAreaServ = new AreaServicioHRRB();
        oSeleccionado = new EpisodioMedico();
        oEpisodioMedico = new EpisodioMedico();
        dFechaActual = new Date();
        oMedicamento = new Medicamento();
        oMedAplic = new MedicamentoAplicado();
        arrMedicamentoAplicado = new ArrayList<MedicamentoAplicado>();
        arrMediAplicado = new ArrayList<MedicamentoAplicado>();
    }

////////////////////////////////////////////////////////////////////////////////BUSCA PACIENTE
    
    
    private void ocultarBusquedaHOSP(){
        if(oPerHosp.getStatus().compareTo("T4602")==0 || oPerHosp.getStatus().compareTo("T4606")==0 
            || oPerHosp.getStatus().compareTo("T4607")==0 || oPerHosp.getStatus().compareTo("T4609")==0){
            sOcultarBusqueda1="true";
        }else{
            sOcultarBusqueda1="false";
        }
    }
    
    private void ocultarBusquedaURG(){
        if(oPerHosp.getStatus().compareTo("T4602")==0 || oPerHosp.getStatus().compareTo("T4606")==0
            || oPerHosp.getStatus().compareTo("T4607")==0 || oPerHosp.getStatus().compareTo("T4609")==0){
            sOcultarBusqueda2="false";
        }else{
            sOcultarBusqueda2="true";
        }
    }
    
    public ArrayList<EpisodioMedico> getListaEpiMed() {
        return arrEpiMed;
    }

    public AreaServicioHRRB[] getListaAreasHosp() {
        try {
            arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosHospitalizacion();
        } catch (Exception ex) {
            Logger.getLogger(ActualizarHojaEnfermJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrAreaActual;
    }

    public AreaServicioHRRB[] getListaAreasUrg() {
        try {
            arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosUrgencias();
        } catch (Exception ex) {
            Logger.getLogger(ActualizarHojaEnfermJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrAreaActual;
    }

    public EpisodioMedico getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EpisodioMedico oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
        limpiarTodo();
        limpiarTabla();
    }

    public void buscaPacienteHosp() {
        sVisibilidadTabla = "visible";
        try {
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarPacientesColectivosHosp(dFechaActual, oAreaServ.getClave())));
            oSeleccionado = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscaPacienteUrg() {
        sVisibilidadTabla = "visible";
        try {
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarPacientesColectivosURG(dFechaActual, oAreaServ.getClave())));
            oSeleccionado = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void llenadoDatosPersonales() throws Exception {
        if (oSeleccionado == null) {
            FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡Seleccione un paciente!");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bBuscado = false;
        } else {
            oEpisodioMedico = oSeleccionado;
            bBuscado = true;
            sVisibilidadTabla = "hidden";
            ListaMedicamentoAplicar();
        }
    }

    public String getVisible() {
        if (this.bBuscado) {
            return "visible";
        } else {
            return "hidden";
        }
    }

    
////////////////////////////////////////////////////////////////////////////////
    
    
    public void ListaMedicamentoAplicar() {
        try {
            System.out.println("DENTRO DE listaMedicamentoAplicar");
            arrMedicamentoRecetado = (new Medicamento().buscarMedicamentoAplicar(dFechaActual, oEpisodioMedico.getPaciente().getFolioPaciente(), oEpisodioMedico.getClaveEpisodio()));
            
        } catch (Exception ex) {
            Logger.getLogger(ActualizarHojaEnfermJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Parametrizacion[] getListaVia() {
        try {
            arrVia = (new Parametrizacion()).buscarViaAdministracion();
        } catch (Exception ex) {
            Logger.getLogger(ActualizarHojaEnfermJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVia;
    }

    public Turno[] getListaTurno() {
        try {
            arrTurno = (new Turno()).buscarTurnoAplicacionMedicamento();
        } catch (Exception ex) {
            Logger.getLogger(ActualizarHojaEnfermJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrTurno;
    }

    public void registroMedicamentoAplicado() {
        oMedAplic.setMedicamento(oMedicamento);
        if (oMedAplic.getMedicamento().getNombre() != null && oMedAplic.getDosis() != null && oMedAplic.getVia().getValor() != null
                && oMedAplic.getTurno().getDescripcion() != null && oMedAplic.getFechaAplicacion() != null) {
            oMedAplic.setEpisodioMedico(oEpisodioMedico);
            oMedAplic.setAplico(oPerHosp);
            oMedAplic.getVia().equalsViaAdm(arrVia);
            oMedAplic.getTurno().equalsTurnoApliMed(arrTurno);
            oMedAplic.getMedicamento().equalsMedicApli(arrMedicamentoRecetado);
            arrMedicamentoAplicado.add(oMedAplic);
            oMedicamento = new Medicamento();
            oMedAplic = new MedicamentoAplicado();
            oMedicamento.setNombre("");
            oMedAplic.setDosis("");
            oMedAplic.getVia().setValor("");
            oMedAplic.getTurno().setDescripcion("");
            sVisibilidadRegistroMedAplic = "visible";
        } else {
            oMedicamento.setNombre("");
            oMedAplic.setDosis("");
            oMedAplic.getVia().setValor("");
            oMedAplic.getTurno().setDescripcion("");
        }
    }

    public void borrarElemento(MedicamentoAplicado obj) {
        arrMedicamentoAplicado.remove(obj);
        if (arrMedicamentoAplicado.isEmpty()) {
            sVisibilidadRegistroMedAplic = "hidden";
        } else {
            sVisibilidadRegistroMedAplic = "visible";
        }
    }

    public void guardarHojaEnf() throws Exception {
        MedicamentoAplicado MedApl = new MedicamentoAplicado();
        try {
            MedApl.insertaHojaEnfermeriaMedAplic(arrMedicamentoAplicado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Hoja de enfermeria - Administración de medicamento", "Guardada Correctamente"));
            limpiarTabla();
        } catch (Exception e) {
            Logger.getLogger(ActualizarHojaEnfermJB.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Hoja de enfermeria - Administración de medicamento", "No Guardada"));
        }
    }

    public void limpiarTabla() {
        arrMedicamentoAplicado.clear();
        sVisibilidadRegistroMedAplic = "hidden";
    }

    public void buscaMedicamentoAplicadoEnFecha() {
        try {
            arrMediAplicado = new ArrayList<MedicamentoAplicado>(Arrays.asList(oMedAplic.buscarMedicamentoAplicadoPorFecha(dFechaAux, oEpisodioMedico.getPaciente().getFolioPaciente(), oEpisodioMedico.getClaveEpisodio())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiarTodo(){
        oMedicamento.setNombre("");
        oMedAplic.setDosis("");
        oMedAplic.getVia().setValor("");
        oMedAplic.getTurno().setDescripcion("");
        oMedAplic.setFechaAplicacion(null);
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    
    public AccesoDatos getAD() {
        return oAD;
    }

    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }

    public PersonalHospitalario getPerHosp() {
        return oPerHosp;
    }

    public void setPerHosp(PersonalHospitalario oPerHosp) {
        this.oPerHosp = oPerHosp;
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

    public ArrayList<EpisodioMedico> getEpiMed() {
        return arrEpiMed;
    }

    public void setEpiMed(ArrayList<EpisodioMedico> arrEpiMed) {
        this.arrEpiMed = arrEpiMed;
    }

    public String getVisibilidadTabla() {
        return sVisibilidadTabla;
    }

    public void setVisibilidadTabla(String sVisibilidadTabla) {
        this.sVisibilidadTabla = sVisibilidadTabla;
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

    public MedicamentoAplicado getMedAplic() {
        return oMedAplic;
    }

    public void setMedAplic(MedicamentoAplicado oMedAplic) {
        this.oMedAplic = oMedAplic;
    }

    public Parametrizacion[] getVia() {
        return arrVia;
    }

    public void setVia(Parametrizacion[] arrVia) {
        this.arrVia = arrVia;
    }

    public Turno[] getTurno() {
        return arrTurno;
    }

    public void setTurno(Turno[] arrTurno) {
        this.arrTurno = arrTurno;
    }

    public Medicamento getMedicamento() {
        return oMedicamento;
    }

    public void setMedicamento(Medicamento oMedicamento) {
        this.oMedicamento = oMedicamento;
    }

    public ArrayList<MedicamentoAplicado> getMedicamentoAplicado() {
        return arrMedicamentoAplicado;
    }

    public void setMedicamentoAplicado(ArrayList<MedicamentoAplicado> arrMedicamentoAplicado) {
        this.arrMedicamentoAplicado = arrMedicamentoAplicado;
    }

    public String getVisibilidadRegistroMedAplic() {
        return sVisibilidadRegistroMedAplic;
    }

    public void setVisibilidadRegistroMedAplic(String sVisibilidadRegistroMedAplic) {
        this.sVisibilidadRegistroMedAplic = sVisibilidadRegistroMedAplic;
    }

    public ArrayList<MedicamentoAplicado> getMediAplicado() {
        return arrMediAplicado;
    }

    public void setMediAplicado(ArrayList<MedicamentoAplicado> arrMediAplicado) {
        this.arrMediAplicado = arrMediAplicado;
    }

    public Medicamento[] getMedicamentoRecetado() {
        return arrMedicamentoRecetado;
    }

    public void setMedicamentoRecetado(Medicamento[] arrMedicamentoRecetado) {
        this.arrMedicamentoRecetado = arrMedicamentoRecetado;
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
}
