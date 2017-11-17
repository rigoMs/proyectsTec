package mx.gob.hrrb.jbs.servicios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.serv.DetalleSolicitudProtoQuimio;
import mx.gob.hrrb.modelo.serv.ProtocoloQuimio;
import mx.gob.hrrb.modelo.serv.SolicitudProtocoloQuimio;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael
 */
@ManagedBean(name = "oProtocolo")
@ViewScoped
public class OtorgaProtocoloQuimio extends OtorgarServicio 
                                   implements Serializable {
private Medico oMedFirm;
private EpisodioMedico oSeleccionado;
private EpisodioMedico oEpisodioMedico;
private SolicitudProtocoloQuimio oSolicitud;
private DetalleSolicitudProtoQuimio oDetalle;
private ProtocoloQuimio oProtocolo;
private ProtocoloQuimio arrProtoQuim[];
private Medicamento oMedicamento;
private Medicamento arrMed[];
private ArrayList<EpisodioMedico> arrEpiMed;
private Parametrizacion arrVia[];
private ArrayList<DetalleSolicitudProtoQuimio> arrRegProtocolo;
private String sVisibilidadTabla = "hidden";
private String sVisibilidadProtocolo = "hidden";
private boolean bBuscado = false;
private ArrayList<SolicitudProtocoloQuimio> lProtocolos;
private Date dFecha;
private boolean bShowButton;
private String sNombre;
private String sApPaterno;
private String sApMaterno;
private int nNumExpe;

    public OtorgaProtocoloQuimio() throws Exception{
        oSolicitud = new SolicitudProtocoloQuimio();
        oDetalle  = new DetalleSolicitudProtoQuimio();
    HttpServletRequest req;
        oMedFirm=new Medico();
        oMedFirm.setUsuar(new Usuario());
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm")!=null){
            oMedFirm.getUsuar().setIdUsuario(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            oMedFirm.buscaUsuarioFirmado();
        }

        oEpisodioMedico = new EpisodioMedico();
        oMedicamento = new Medicamento();
        arrRegProtocolo = new ArrayList<DetalleSolicitudProtoQuimio>();
        oDetalle = new DetalleSolicitudProtoQuimio();
        oProtocolo = new ProtocoloQuimio();
    }
    
    public void buscaPacienteAtenMed() {
        sVisibilidadTabla = "visible";
        try {
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarDatosPacienteAtencionMedica()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EpisodioMedico getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EpisodioMedico oSeleccionado) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteselecc", oSeleccionado);
    }

    public ArrayList<EpisodioMedico> getListaEpiMed() {
        return arrEpiMed;
    }

    public void llenadoDatosPersonales() throws Exception {
        EpisodioMedico oEpiMed = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        oEpiMed = (EpisodioMedico) session.getAttribute("pacienteselecc");
        if (oEpiMed == null) {
            FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡Seleccione un paciente!");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bBuscado = false;
        } else {
            oEpisodioMedico = oEpiMed;
            bBuscado = true;
            limpiar();
        }
    }

    public void limpiar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) 
                facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteselecc", null);
        sVisibilidadTabla = "hidden";
    }

    public String getVisible() {
        if (this.bBuscado) {
            return "visible";
        } else {
            return "hidden";
        }
    }

    
//////////////////////////////////////////////////////////////////////////////// 
    
    
    public void buscaMedicamentoOnco() throws Exception {
        oMedicamento.equalsMedOnco(arrMed);
        System.out.println("NOMBRE " + this.oMedicamento.getNombre());
        System.out.println("CLAVE " + this.oMedicamento.getClaveMedicamento());
        oMedicamento.buscarPorNombreOnco();
    }

    public Medicamento[] getListaMedicamentoOncologia() {
        try {
            arrMed = (new Medicamento()).buscarMedicamentoOnco();
        } catch (Exception ex) {
            Logger.getLogger(OtorgaProtocoloQuimio.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return arrMed;
    }

    public Parametrizacion[] getListaVia() {
        try {
            arrVia = (new Parametrizacion()).buscarViaAdministracion();
        } catch (Exception ex) {
            Logger.getLogger(OtorgaProtocoloQuimio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVia;
    }
    
    public ProtocoloQuimio[] getListaProtocoloQuimio() {
        try {
            arrProtoQuim = (new ProtocoloQuimio()).buscarProtocoloQuimioIntExt();
        } catch (Exception ex) {
            Logger.getLogger(OtorgaProtocoloQuimio.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return arrProtoQuim;
    }
    
    public void buscaDuracionProto() throws Exception {
        oProtocolo.buscarPorNombreProtocolo();
    }

    public void registrarQuimiOnco() {
        oDetalle.setServicioCobrable(oMedicamento);
        oDetalle.setSolicitudProtocolo(oSolicitud);
        if (getMedicamento().getClaveMedicamento().equals("") && 
            oDetalle.getDosis().equals("") && 
            oDetalle.getTipoVia().getValor().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", 
                            "Seleccione un medicamento"));
        }else if (getMedicamento().getClaveMedicamento() != null && 
                oDetalle.getDosis() != null && 
                oDetalle.getTipoVia().getValor() != null) {
            oDetalle.getTipoVia().equalsViaAdm(arrVia);
            oDetalle.setEpisodio(oEpisodioMedico);
            oDetalle.setAutorizadoPor(oMedFirm);
            oDetalle.getSolicitudProtocolo().setServicioCobrable(oProtocolo);
            arrRegProtocolo.add(oDetalle);
            oMedicamento = new Medicamento();
            
            oMedicamento.setClaveMedicamento("");
            oMedicamento.setPresentacion("");
            oDetalle.setDosis("");
            oDetalle.getTipoVia().setValor("");
            sVisibilidadProtocolo = "visible";
        } else {
            oMedicamento.setClaveMedicamento("");
            oMedicamento.setPresentacion("");
            oDetalle.setDosis("");
            oDetalle.getTipoVia().setValor("");
        }
    }

    public void borrarElemento(DetalleSolicitudProtoQuimio obj) {
        arrRegProtocolo.remove(obj);
        if (arrRegProtocolo.isEmpty()) {
            sVisibilidadProtocolo = "hidden";
        } else {
            sVisibilidadProtocolo = "visible";
        }
    }

    public void guardarProtocolo() throws Exception {
        try {
            if(this.oDetalle.insertaProtocoloQuimio(arrRegProtocolo) == 1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Protocolo de Quimioterapia", "Protocolo Guardado Correctamente"));
            limpiarTabla();
        } catch (Exception e) {
            Logger.getLogger(OtorgaProtocoloQuimio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void limpiarTabla(){
        arrRegProtocolo.clear();
        sVisibilidadProtocolo="hidden";
    }

    
////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////
    
     public void buscarProtocolos(){
        try{
            setProtocolos(new ArrayList<SolicitudProtocoloQuimio>(Arrays.asList(new SolicitudProtocoloQuimio().buscarProtocolosPorPaciente(getNombre(), getApPaterno(), getApMaterno(), getNumExpe(), getFecha()))));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void buscarCantidades(){
        if(oSeleccionado == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Seleccione un medicamento"));
            setbShowButton(true);
        }else if(oSeleccionado != null){
            setbShowButton(false);
            for(SolicitudProtocoloQuimio sp: getProtocolos()){
               //if(sp.getDetalle().getIdentificador() == oSeleccionado.getDetalle().getIdentificador()){
                 //   oDetalle.setCantSolicitada(sp.getDetalle().getCantSolicitada());
                //}
            }
        }
    }
    
    public void modificarEstadoProtocolo(int nCve){
        String sMensaje;
        int nCant = 0;
        try{
            /*if(oSolicitud.modificarEstadoProtocolo(oSeleccionado.getDetalle().getIdentificador(), nCve)==1){
                sMensaje = nCve == 1 ? "Registro guardado":"Aplicación cancelada";   
                this.oDetalle.setCantSurtida((short)nCant);
                buscarProtocolos();
                setbShowButton(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO",sMensaje));
            }else if(oSolicitud.modificarEstadoProtocolo(oSeleccionado.getDetalle().getIdentificador(), nCve)!=1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se pudo realizar la acción"));
            }*/
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
        
    }
    
    
    //////////////////////////////////////////////////////////////////////////////
    
    
    @Override
    public List buscarDetalleSolicitud() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void listaOrdenes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SolicitudProtocoloQuimio getSolicitud() {
        return oSolicitud;
    }

    public void setSolicitud(SolicitudProtocoloQuimio oSolicitud) {
        this.oSolicitud = oSolicitud;
    }

    public DetalleSolicitudProtoQuimio getDetalle() {
        return oDetalle;
    }

    public void setDetalle(DetalleSolicitudProtoQuimio oDetalle) {
        this.oDetalle = oDetalle;
    }

    public ProtocoloQuimio getProtocolo() {
        return oProtocolo;
    }

    public void setProtocolo(ProtocoloQuimio oProtocolo) {
        this.oProtocolo = oProtocolo;
    }

    public Medico getMedFirm() {
        return oMedFirm;
    }

    public void setMedFirm(Medico oMedFirm) {
        this.oMedFirm = oMedFirm;
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

    public boolean getBuscado() {
        return bBuscado;
    }

    public void setBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
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

    public Parametrizacion[] getVia() {
        return arrVia;
    }

    public void setVia(Parametrizacion[] arrVia) {
        this.arrVia = arrVia;
    }

    public ArrayList<DetalleSolicitudProtoQuimio> getRegProtocolo() {
        return arrRegProtocolo;
    }

    public void setRegProtocolo(
            ArrayList<DetalleSolicitudProtoQuimio> arrRegProtocolo) {
        this.arrRegProtocolo = arrRegProtocolo;
    }

    public String getVisibilidadProtocolo() {
        return sVisibilidadProtocolo;
    }

    public void setVisibilidadProtocolo(String sVisibilidadProtocolo) {
        this.sVisibilidadProtocolo = sVisibilidadProtocolo;
    }

    public Medicamento[] getMed() {
        return arrMed;
    }

    public void setMed(Medicamento[] arrMed) {
        this.arrMed = arrMed;
    }

    public ProtocoloQuimio[] getProtoQuim() {
        return arrProtoQuim;
    }

    public void setProtoQuim(ProtocoloQuimio[] arrProtoQuim) {
        this.arrProtoQuim = arrProtoQuim;
    }

    public Date getFecha() {
        return dFecha;
    }

    public void setFecha(Date dFecha) {
        this.dFecha = dFecha;
    }

    public boolean getShowButton() {
        return bShowButton;
    }

    public void setbShowButton(boolean bShowButton) {
        this.bShowButton = bShowButton;
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

    public ArrayList<SolicitudProtocoloQuimio> getProtocolos() {
        return lProtocolos;
    }

    public void setProtocolos(ArrayList<SolicitudProtocoloQuimio> lProtocolos) {
        this.lProtocolos = lProtocolos;
    }
}
