package mx.gob.hrrb.jbs.comunes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.core.AfeccionTratada;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Receta;
import mx.gob.hrrb.modelo.core.DetalleRecetaHRRB;
import mx.gob.hrrb.modelo.core.DetalleValeMaterial;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.NotaMedicaHRRB;
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.modelo.core.SignosVitales;
import mx.gob.hrrb.modelo.core.Traslado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.serv.DetalleSolicitudHemoderivado;
import mx.gob.hrrb.modelo.serv.EstudioEndoscopia;
import mx.gob.hrrb.modelo.serv.EstudioEspLab;
import mx.gob.hrrb.modelo.serv.EstudioInterconsulta;
import mx.gob.hrrb.modelo.serv.EstudioPatologia;
import mx.gob.hrrb.modelo.serv.EstudioRealEndos;
import mx.gob.hrrb.modelo.serv.EstudioRealImagen;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;
import mx.gob.hrrb.modelo.serv.ProductoHemoderivado;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.modelo.serv.SolicitudOtroServicio;
import mx.gob.hrrb.modelo.serv.SolicitudSangre;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael
 */
@ManagedBean(name = "oRegNotaMedEvol")
@ViewScoped
public class RegistroNotaMedEvolJB implements Serializable {
    private AccesoDatos oAD;
    private Medico oMedFirm;
    private String sOcultarBusqueda1;
    private String sOcultarBusqueda2;
    private String sNotaIngreso;
    private String sNotaEvolucion;
    private Paciente oPaciente;
    private DetalleRecetaHRRB oDetalleRecHRRB;
    private Medicamento oMedicamento;
    private Receta oReceta;
    private Cita oCita;
    private AreaServicioHRRB oAreaServ;
    private NotaMedicaHRRB oNotaMedHrrb;
    private Parametrizacion oParametrizacion;
    private EpisodioMedico oEpisodioMedico;
    private EpisodioMedico oSeleccionado;
    private ProcedimientoCIE9 oCie9;
    private DiagnosticoCIE10 oCie10;
    private ProductoHemoderivado oProductoHemoderivado;
    private SolicitudSangre oSolSangre;
    private ProcedimientosRealizados oProcReal;
    private AfeccionTratada oAfecTrat;
    private SignosVitales oSignVit;
    private DetalleSolicitudHemoderivado oDetalleSolHemo;
    private Estudios oEstudio;
    private EstudioRealizado oEstReal;
    private Traslado oTraslado;
    private DetalleValeMaterial oDetValMat;
    private Material oMaterial;
    private EstudioEspLab oEstEspLab;
    private EstudioRealImagen oEstRealImg;
    private EstudioPatologia oEstPat;
    private EstudioRealEndos oEstEndos;
    private EstudioInterconsulta oEstInter;
    private SolicitudOtroServicio oSolOtroServ;
    private ServicioRealizado oServReal;

    private AreaServicioHRRB arrArea[];
    private AreaServicioHRRB arrAreaCE[];
    private Parametrizacion arrClasificacion[];
    private Parametrizacion arrSitioAnat[];
    private Parametrizacion arrTipoBiopPzaQx[];
    private Parametrizacion arrAntecCitol[];
    private Parametrizacion arrSitioGineco[];
    private Parametrizacion arrTipoProcCitol[];
    private Parametrizacion arrMotivoEnv[];
    private Parametrizacion arrRegImg[];
    private Parametrizacion arrGrpSang[];
    private Parametrizacion arrTipoPacEI[];
    private Parametrizacion arrNivUrgBanco[];
    private Parametrizacion arrRHBancoSangre[];
    private Parametrizacion arrTipoSolBanco[];
    private Parametrizacion arrElectUrgnt[];
    private Parametrizacion arrVia[];
    private ProcedimientosRealizados arrProcRealMatOst[];
    private Estudios arrEstudioTomo[];
    private ProductoHemoderivado arrProHemo[];
    private Parametrizacion arrMedCont[];

    private List<Parametrizacion> tipoEstudio;
    private ArrayList<DetalleRecetaHRRB> arrRec;
    private ArrayList<DiagnosticoCIE10> arrDiagCie10;
    //private ArrayList<ProcedimientoCIE9> arrProcCie9;
    private ArrayList<DetalleSolicitudHemoderivado> arrDetHemo;
    private ArrayList<DetalleValeMaterial> arrDetalleValeMat;
    private ArrayList<Estudios> arrServ;
    private ArrayList<ProcedimientosRealizados> arrProcRealPac;
    private ArrayList<ProcedimientosRealizados> arrProcRealActual;
    private ArrayList<SolicitudOtroServicio> arrSolOto;
    private ArrayList<EstudioRealizado> arrEstuReal;
    private ArrayList<EstudioRealizado> arrEstuOto;
    private ArrayList<AfeccionTratada> arrAfecTratPac;
    private ArrayList<SignosVitales> arrSignVitActual;
    private ArrayList<ProductoHemoderivado> lProHemoAgregado;
    private ArrayList<EstudioEspLab> arrSolLab;
    private ArrayList<EstudioRealImagen> arrSolImg;
    private ArrayList<EpisodioMedico> arrEpiMed;
    private ArrayList<EpisodioMedico> arrConsulta;
    private ArrayList<NotaMedicaHRRB> arrMotivoRef;
    private ArrayList<EstudioRealizado> arrOrdServPrev;
    private ArrayList<EstudioRealizado> arrConsultaResultEst;
    private ArrayList<EstudioRealizado> arrConsultaResultEstPrev;

    private String sVisibilidadReceta = "hidden";
    private String sVisibilidadIndicTerap = "visible";
    private String sVisibilidadDiagnostico = "hidden";
    private String sVisibilidadProcedimientos = "hidden";
    private String sVisibilidadResultEstPrev = "hidden";
    private String sVisibilidadDescargaResult = "hidden";
    private String sVisibilidadOrdPrev = "hidden";
    private String sBotonG = "hidden";
    private String sBotonNota = "visible";
    private String sVisibilidadSolLab = "hidden";
    private String sVisibilidadSolImg = "hidden";
    private String sVisibilidadSolOto = "hidden";
    private String sVisibilidadTabla = "hidden";
    private String sVisibilidadProdHemo = "hidden";
    private String sVisibilidadMatOsteo = "hidden";
    private String sVisibilidadServicios = "hidden";
    private String sBoton;
    private String sObj1;
    private String sObj2;
    private String sObj3;
    private String sObj4;
    private String sArea1;
    private String sArea2;
    private String sArea3;
    private String sSeguroDesglosado[];
    private String bRender="hidden";

    private int sClaveMantener = 0;

    private Date dFechaActual;
    private Date dFechaAux;
    private Date dFechaIniAuxResPrev;
    private Date dFechaFinAuxResPrev;
    private Date dFechaIni;
    private Date dFechaFin;
    private Date dFechaIniOrd;
    private Date dFechaFinOrd;

    private boolean bEstudios1;
    private boolean bEstudios2;
    private boolean bEstudios3;
    private boolean bOtroMotivo;
    private boolean bCantProductoHemo;

    private StreamedContent scCatalogoLab;
    private StreamedContent scResultadoEstudios;
    private boolean bBuscado = false;

    public RegistroNotaMedEvolJB(){
        HttpServletRequest req;
        oMedFirm = new Medico();
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
            oMedFirm.setIdUsuario(((Firmado) req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            try{
                oMedFirm.buscaUsuarioFirmado();
            }catch(Exception e){
                e.printStackTrace(); //PENDIENTE
            }
            ocultarBusquedaCE();
            ocultarBusquedaURGHOSP();
        }

        oPaciente = new Paciente();
        oNotaMedHrrb = new NotaMedicaHRRB();
        oReceta = new Receta();
        oDetalleRecHRRB = new DetalleRecetaHRRB();
        oParametrizacion = new Parametrizacion();
        oMedicamento = new Medicamento();
        oCita = new Cita();
        oAreaServ = new AreaServicioHRRB();
        oEpisodioMedico = new EpisodioMedico();
        oEpisodioMedico.getProceRe1().setCIE9(new ProcedimientoCIE9());
        oEpisodioMedico.setCita(new Cita());
        oCie9 = new ProcedimientoCIE9();
        oCie10 = new DiagnosticoCIE10();
        oEstudio = new Estudios();
        oEstudio.setAreaServHrrb(new AreaServicioHRRB());
        oEstudio.setClasificacion(new Parametrizacion());
        oEstEspLab = new EstudioEspLab();
        oProductoHemoderivado = new ProductoHemoderivado();
        oDetalleSolHemo = new DetalleSolicitudHemoderivado();
        oSolSangre = new SolicitudSangre();
        oSolSangre.setUrgencia(new Parametrizacion());
        oSolSangre.setTipoSolicitud(new Parametrizacion());
        oDetalleSolHemo = new DetalleSolicitudHemoderivado();
        oDetalleSolHemo.setSolicitudS(oSolSangre);
        oDetalleSolHemo.setServicioCobrable(oProductoHemoderivado);
        oDetalleSolHemo.getSolicitudS().setTipoPacSolicita(new Parametrizacion());
        oDetalleSolHemo.getSolicitudS().setTipoSangre(new Parametrizacion());
        oDetalleSolHemo.getSolicitudS().setRH(new Parametrizacion());
        oDetalleSolHemo.getSolicitudS().setUrgencia(new Parametrizacion());
        oDetalleSolHemo.getSolicitudS().setTipoSolicitud(new Parametrizacion());
        oProcReal = new ProcedimientosRealizados();
        oProcReal.setSolSangre(new SolicitudSangre());
        oProcReal.getSolSangre().setTipoSangre(new Parametrizacion());
        oProcReal.getSolSangre().setRH(new Parametrizacion());
        oProcReal.setProdHem(new ProductoHemoderivado());
        oProcReal.setCIE9(new ProcedimientoCIE9());
        oAfecTrat = new AfeccionTratada();
        oSignVit = new SignosVitales();
        oEstRealImg = new EstudioRealImagen();
        oEstRealImg.setImagenRegion(new Parametrizacion());
        oEstRealImg.setRegionSolicitada(new Parametrizacion());
        oEstRealImg.setMedioContraste(new Parametrizacion());
        oEstEndos = new EstudioRealEndos();
        oEstInter = new EstudioInterconsulta();
        oTraslado = new Traslado();
        oTraslado.setSignVit(new SignosVitales());
        oSolOtroServ = new SolicitudOtroServicio();
        oSolOtroServ.setEstudio(new Estudios());
        oEstPat = new EstudioPatologia();
        oEstPat.setAntecCitol(new Parametrizacion());
        oEstPat.setSitGineco(new Parametrizacion());
        oEstPat.setSitAnatomico(new Parametrizacion());
        oEstPat.setTipoCitol(new Parametrizacion());
        oEstPat.setTipoBiopPzaQx(new Parametrizacion());
        oEstPat.setTipoProcedimiento(new Parametrizacion());
        oDetValMat = new DetalleValeMaterial();
        oMaterial = new Material();
        oEstReal = new EstudioRealizado();

        dFechaActual = new Date();

        oEstudio.setAreaServHrrb(new AreaServicioHRRB());
        oEstudio.setClasificacion(new Parametrizacion());
        oEstInter.setAreaServ(new AreaServicioHRRB());
        oDetalleRecHRRB.setMedicamento(new Medicamento());
        oDetalleRecHRRB.setVia(new Parametrizacion());
        oEstPat.setSitAnatomico(new Parametrizacion());
        oEstPat.setTipoBiopPzaQx(new Parametrizacion());
        oEstPat.setAntecCitol(new Parametrizacion());
        oEstPat.setSitGineco(new Parametrizacion());
        oEstPat.setTipoCitol(new Parametrizacion());
        oDetalleSolHemo.setServicioCobrable(new ProductoHemoderivado());
        arrEpiMed = new ArrayList<EpisodioMedico>();
        arrConsulta = new ArrayList<EpisodioMedico>();
        arrConsultaResultEstPrev = new ArrayList<EstudioRealizado>();
        arrConsultaResultEst = new ArrayList<EstudioRealizado>();
        arrDiagCie10 = new ArrayList<DiagnosticoCIE10>();
        arrRec = new ArrayList<DetalleRecetaHRRB>();
        arrProcRealPac = new ArrayList<ProcedimientosRealizados>();
        arrAfecTratPac = new ArrayList<AfeccionTratada>();
        arrDetalleValeMat = new ArrayList<DetalleValeMaterial>();
        arrOrdServPrev = new ArrayList<EstudioRealizado>();
        arrServ = new ArrayList<Estudios>();
        arrSolLab = new ArrayList<EstudioEspLab>();
        arrSolImg = new ArrayList<EstudioRealImagen>();
        arrSolOto = new ArrayList<SolicitudOtroServicio>();
        arrEstuOto = new ArrayList<EstudioRealizado>();
        arrDetHemo = new ArrayList<DetalleSolicitudHemoderivado>();
        lProHemoAgregado = new ArrayList<ProductoHemoderivado>();
        bOtroMotivo = true;
        bCantProductoHemo = true;
        bEstudios1 = false;
        bEstudios2 = false;
        bEstudios3 = false;
        sSeguroDesglosado = new String[13];
        sSeguroDesglosado[0] = "0";
        sSeguroDesglosado[1] = "0";
        sSeguroDesglosado[2] = "0";
        sSeguroDesglosado[3] = "0";
        sSeguroDesglosado[4] = "0";
        sSeguroDesglosado[5] = "0";
        sSeguroDesglosado[6] = "0";
        sSeguroDesglosado[7] = "0";
        sSeguroDesglosado[8] = "0";
        sSeguroDesglosado[9] = "0";
        sSeguroDesglosado[10] = "0";
        sSeguroDesglosado[11] = "0";
        sSeguroDesglosado[12] = "0";
    }

////////////////////////////////////////////////////////////////////////////////////////////////BUSQUEDA PACIENTE
    public Medico getMedFirm() {
        return oMedFirm;
    }

    public void setMedFirm(Medico oMedFirm) {
        this.oMedFirm = oMedFirm;
    }

    private void ocultarBusquedaCE() {
        if (oMedFirm.getUsuar().getCvePerfil().compareTo("CEMED") == 0) {
            sOcultarBusqueda1 = "true";
        } else {
            sOcultarBusqueda1 = "false";
        }
    }

    private void ocultarBusquedaURGHOSP() {
        if (oMedFirm.getUsuar().getCvePerfil().compareTo("CEMED") == 0) {
            sOcultarBusqueda2 = "false";
        } else {
            sOcultarBusqueda2 = "true";
        }
    }

    public void buscaPacienteFechaConsulta() {
        sVisibilidadTabla = "visible";
        try {
            arrConsulta = new ArrayList<EpisodioMedico>(Arrays.asList(
                    oEpisodioMedico.buscarPacientesConsulta(
                            dFechaAux, oMedFirm, oMedFirm, oMedFirm)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<EpisodioMedico> getListaCitado() {
        return arrConsulta;
    }

    public void buscaPacienteAtenMed() {
        sVisibilidadTabla = "visible";
        try {
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(
                    oEpisodioMedico.buscarDatosPacienteAtencionMedica()));
            oSeleccionado = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EpisodioMedico getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EpisodioMedico oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
    }

    public ArrayList<EpisodioMedico> getListaEpiMed() {
        return arrEpiMed;
    }

////////////////////////////////////////////////////////////////////////////////////////////////DATOS DEL PACIENTE
    public void llenadoDatosPersonales() {
        if (oSeleccionado == null) {
            FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡Seleccione un paciente!");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bBuscado = false;
            oEpisodioMedico = null;
        } else {
            oEpisodioMedico = oSeleccionado;
            bBuscado = true;
            arrConsulta.clear();
            arrEpiMed.clear();
            buscaProcedimientosRealizadosPaciente();
            buscaUltimoProcedimientoRealizado();
            buscaDiagnosticosPaciente();
            buscaSignosVitalesActuales();
            notaIngresoEvolucion();
            motivoReferencia();
            buscaAuxDiag();
            validaSeleccionArea();
            desglosar();
            limpiarTodo();
            sVisibilidadTabla = "hidden";
        }
    }

    public String getVisible() {
        if (this.bBuscado) {
            return "visible";
        } else {
            return "hidden";
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////NOTAS MEDICAS
    public void buscaResultadosEstudiosRealizadosPrevios() {
        try {
            arrConsultaResultEstPrev = new ArrayList<EstudioRealizado>(
                    Arrays.asList(
                        oEstReal.buscarEstudiosRealizadosPreviosFechas(
                            oEpisodioMedico.getPaciente().getFolioPaciente(),
                            oEpisodioMedico.getClaveEpisodio(),
                            dFechaIniAuxResPrev, dFechaFinAuxResPrev)));
            sVisibilidadResultEstPrev = "visible";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscaResultadosFechas() {
        try {
            arrConsultaResultEst = new ArrayList<EstudioRealizado>(
                    Arrays.asList(oEstReal.buscarRangoFechas(
                            oEpisodioMedico.getPaciente().getFolioPaciente(), 
                            oEpisodioMedico.getClaveEpisodio(), 
                            dFechaIni, dFechaFin)));
            sVisibilidadDescargaResult = "visible";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepDownload(String date){
        try{
            File f = new File(date);
            InputStream input = new FileInputStream(f);
            ExternalContext externalContext = 
                    FacesContext.getCurrentInstance().getExternalContext();
            setResultadoEstudios(new DefaultStreamedContent(
                    input, externalContext.getMimeType(f.getName()), f.getName()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void notaIngresoEvolucion() {
        if (this.oEpisodioMedico.getCita().getPrimEsp().compareTo("PRIMERA VEZ")
                == 0) {
            sNotaIngreso = "true";
            sNotaEvolucion = "false";
        } else {
            sNotaIngreso = "false";
            sNotaEvolucion = "true";
        }
    }

    public void guardarNotaMedica(){
        try {
            getNotaMedHrrb().setEpiMed(oEpisodioMedico);
            setNotaMedHrrb(oNotaMedHrrb);
            getNotaMedHrrb().setMedFirm(oMedFirm);
            getNotaMedHrrb().setCIE9(oCie9);
            if (oNotaMedHrrb.insertaNotaMedica(arrDiagCie10, arrRec) == 1) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Nota Médica", "Nota Médica Guardada Correctamente"));
                motivoReferencia();
                bRender="visible";
                sBotonNota = "hidden";
            }
        } catch (Exception e) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(
                    Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Nota Médica", "Error al guardar la ´nota médica"));
        }
    }

    //////////////////////////////////////////////////////Diagnosticos
    public void buscaDiagnosticosPaciente() {
        try {
            arrAfecTratPac = new ArrayList<AfeccionTratada>(Arrays.asList(
                    oAfecTrat.buscarAfeccionTratadaPac(
                            oEpisodioMedico.getPaciente().getFolioPaciente(), 
                            oEpisodioMedico.getClaveEpisodio())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registroDiagnostico() {
        try{
            if (getCie10().getDescripcionDiag().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", 
                                "NO SE PUEDE AGREGAR VACÍO"));
            } else {
                if (arrDiagCie10.isEmpty()) {
                    arrDiagCie10.add(oCie10);
                    oCie10 = new DiagnosticoCIE10();
                    sVisibilidadDiagnostico = "visible";
                    getCie10().setDescripcionDiag("");
                } else {
                    if (buscaRepetido(oCie10.getClave())) {
                        FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "ERROR", "EL DIAGNÓSTICO YA FUE PREVIAMENTE AGREGADO"));
                        getCie10().setDescripcionDiag("");
                    } else {
                        arrDiagCie10.add(oCie10);
                        oCie10 = new DiagnosticoCIE10();
                        getCie10().setDescripcionDiag("");
                        sVisibilidadDiagnostico = "visible";
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean buscaRepetido(String clave) {
        boolean bandera = false;
        for (DiagnosticoCIE10 i : arrDiagCie10) {
            if (i.getClave().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void borrarElementoCie10(DiagnosticoCIE10 obj) {
        arrDiagCie10.remove(obj);
        if (arrDiagCie10.isEmpty()) {
            sVisibilidadDiagnostico = "hidden";
        } else {
            sVisibilidadDiagnostico = "visible";
        }
    }

  //////////////////////////////////////////////////////Procedimientos
    public void buscarCveCie9(){
        try{
            oCie9.buscarPorProcedimiento();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void buscaUltimoProcedimientoRealizado() {
        try {
            arrProcRealActual = new ArrayList<ProcedimientosRealizados>(
                    Arrays.asList(oProcReal.buscarUltimoProcedimientoReal(
                            oEpisodioMedico.getPaciente().getFolioPaciente(), 
                            oEpisodioMedico.getClaveEpisodio())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscaProcedimientosRealizadosPaciente() {
        try {
            arrProcRealPac = new ArrayList<ProcedimientosRealizados>(
                    Arrays.asList(oProcReal.buscarProcedimientosRealPac(
                            oEpisodioMedico.getPaciente().getFolioPaciente(), 
                            oEpisodioMedico.getClaveEpisodio())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProcedimientosRealizados[] getListaProcedimientoMaterialOsteosintesis() {
        try {
            arrProcRealMatOst = (new 
            ProcedimientosRealizados()).buscarUltimoProcedimientoRealMatOst(
                    oEpisodioMedico.getPaciente().getFolioPaciente(), 
                    oEpisodioMedico.getClaveEpisodio());
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return arrProcRealMatOst;
    }

//////////////////////////////////////////////////////Signos Vitales
    public void buscaSignosVitalesActuales() {
        try {
            arrSignVitActual = new ArrayList<SignosVitales>(Arrays.asList(
                    oSignVit.buscarSignosVitalesActuales(
                            oEpisodioMedico.getPaciente().getFolioPaciente(), 
                            oEpisodioMedico.getClaveEpisodio())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////REG RECETA CONSULTA EXTERNA
    public Parametrizacion[] getListaVia() {
        try {
            arrVia = (new Parametrizacion()).buscarViaAdministracion();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return arrVia;
    }

    public void registroReceta() {
        oDetalleRecHRRB.setMedicamento(oMedicamento);
        if (oDetalleRecHRRB.getMedicamento().getNombre() == null 
                || oDetalleRecHRRB.getMedicamento().getNombre().equals("")
                || oDetalleRecHRRB.getMedicamento().getClaveMedicamento() == null 
                || oDetalleRecHRRB.getMedicamento().getClaveMedicamento().equals("")
                || oDetalleRecHRRB.getMedicamento().getPresentacion() == null 
                || oDetalleRecHRRB.getMedicamento().getPresentacion().equals("")
                || oDetalleRecHRRB.getDosis() == null || oDetalleRecHRRB.getDosis().equals("")
                || oDetalleRecHRRB.getVia().getValor() == null || oDetalleRecHRRB.getVia().getValor().equals("")
                || oDetalleRecHRRB.getFrecuencia() == null || oDetalleRecHRRB.getFrecuencia().equals("")
                || oDetalleRecHRRB.getDuracion() == null || oDetalleRecHRRB.getDuracion().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "ERROR", "DEBE COMPLETAR LA RECETA"));
        } else {
            if (buscaRepetidoRec(oDetalleRecHRRB.getMedicamento().getClaveMedicamento())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", 
                                "EL MEDICAMENTO YA FUE PREVIAMENTE AGREGADO"));
                oMedicamento = new Medicamento();
                oDetalleRecHRRB.setMedicamento(new Medicamento());
                oDetalleRecHRRB.setVia(new Parametrizacion());
                oDetalleRecHRRB.getMedicamento().setClaveMedicamento("");
                oDetalleRecHRRB.getMedicamento().setNombre("");
                oDetalleRecHRRB.getMedicamento().setPresentacion("");
                oDetalleRecHRRB.setDosis("");
                oDetalleRecHRRB.getVia().setValor("");
                oDetalleRecHRRB.setFrecuencia("");
                oDetalleRecHRRB.setDuracion("");
                oNotaMedHrrb.setIndicacionTer("");
            } else {
                if (!oDetalleRecHRRB.getMedicamento().getNombre().equals("") && 
                        !oDetalleRecHRRB.getMedicamento().getClaveMedicamento().equals("") && 
                        !oDetalleRecHRRB.getMedicamento().getPresentacion().equals("") && 
                        !oDetalleRecHRRB.getDosis().equals("") && 
                        !oDetalleRecHRRB.getVia().getValor().equals("") && 
                        !oDetalleRecHRRB.getFrecuencia().equals("") && 
                        !oDetalleRecHRRB.getDuracion().equals("")) {
                    oDetalleRecHRRB.getVia().equalsViaAdm(arrVia);
                    System.out.println(oDetalleRecHRRB.getMedicamento().getClaveMedicamento());
                    arrRec.add(oDetalleRecHRRB);
                    oMedicamento = new Medicamento();
                    oDetalleRecHRRB = new DetalleRecetaHRRB();
                    oDetalleRecHRRB.setMedicamento(new Medicamento());
                    oDetalleRecHRRB.setVia(new Parametrizacion());
                    sVisibilidadReceta = "visible";
                    sVisibilidadIndicTerap = "hidden";
                    sBotonG = "visible";
                }
            }
        }
    }

    public boolean buscaRepetidoRec(String clave) {
        boolean bandera = false;
        for (DetalleRecetaHRRB i : arrRec) {
            if (i.getMedicamento().getClaveMedicamento().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void borraDatos() {
        arrRec.clear();
    }

    public void borrarElemento(DetalleRecetaHRRB obj) {
        arrRec.remove(obj);
        if (arrRec.isEmpty()) {
            sVisibilidadReceta = "hidden";
            sBotonG = "hidden";
            sVisibilidadIndicTerap = "visible";
        } else {
            sVisibilidadReceta = "visible";
            sBotonG = "visible";
        }
    }

    public void guardarReceta() {
            try {
                oReceta.setEpisodioMedico(oEpisodioMedico);
                oDetalleRecHRRB.setReceta(oReceta);
                oDetalleRecHRRB.setEpisodio(oEpisodioMedico);
                oDetalleRecHRRB.setAutorizadoPor(oMedFirm);
                if (oDetalleRecHRRB.insertaRecetaHrrb(arrRec) == 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receta medica", "Receta Guardada Correctamente"));
                    sBotonG = "hidden";
                }
            } catch (Exception ex) {
                Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public void buscaMedicamento(){
        try{
            oMedicamento.buscarPorNombre();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desglosar(){
        String num;
        num = this.oEpisodioMedico.getPaciente().getSeg().getNumero();
        char[] arrC = num.toCharArray();
        for(int i=0; i<arrC.length; i++){
            this.sSeguroDesglosado[i] = ""+arrC[i];
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////REG ORDEN DE SERVICIO
    public String fecActual() {
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        String a = anio + "";
        String hoy = dia + "/" + (mes + 1) + "/" + a.substring(2, 4);

        return hoy;
    }

    public AreaServicioHRRB[] getListaTiposServicios() {
        try {
            arrArea = (new AreaServicioHRRB()).buscarTodosTipoServicio();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrArea;
    }

    public Parametrizacion[] getListaTipoEstudios() {
        try {
            arrClasificacion = (new Parametrizacion()).buscarTodosTipoEstudio(
                    oEstudio.getAreaServHrrb().getClave());
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return arrClasificacion;
    }

    public void registroServicio() {

        if (oEstudio.getAreaServHrrb().getClave() == 0) {

            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE ELEGIR UN ÁREA DE SERVICIO"));

        } else {
            if (buscaRepetidoServ(getEstudio().getAreaServHrrb().getClave()) && buscaRepetidoServ(getEstudio().getClasificacion().getValor())) {

                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "LA SOLICITUD YA FUE PREVIAMENTE AGREGADA"));
                oEstudio = new Estudios();
                oEstudio.setAreaServHrrb(new AreaServicioHRRB());
                oEstudio.setClasificacion(new Parametrizacion());

            } else {
                if ((getEstudio().getAreaServHrrb().getClave() == 2 || getEstudio().getAreaServHrrb().getClave() == 7)
                        && (getEstudio().getClasificacion().getValor() == null || getEstudio().getClasificacion().getValor().equals(""))
                        || getEstudio().getAreaServHrrb().getClave() != 0 && !getEstudio().getClasificacion().getValor().equals("")) {

                    if (getEstudio().getClasificacion().getValor() == null) {
                        getEstudio().getClasificacion().setValor("");
                    }

                    
                    oEstudio.getAreaServHrrb().equalsArea(arrArea);
                    oEstudio.getClasificacion().equalsEstudios(arrClasificacion);
                    arrServ.add(oEstudio);
                    oEstudio = new Estudios();
                    oEstudio.setAreaServHrrb(new AreaServicioHRRB());
                    oEstudio.setClasificacion(new Parametrizacion());
                    sVisibilidadServicios = "visible";

                }
            }
        }
    }

    public boolean buscaRepetidoServ(int clave) {
        boolean bandera = false;
        for (Estudios i : arrServ) {

            if (i.getAreaServHrrb().getClave() == clave) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public boolean buscaRepetidoServ(String clave) {
        boolean bandera = false;
        for (Estudios i : arrServ) {

            if (i.getClasificacion().getValor().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void borrarServicio(Estudios obj) {
        arrServ.remove(obj);
        if (arrServ.isEmpty()) {
            sVisibilidadServicios = "hidden";
        } else {
            sVisibilidadServicios = "visible";
        }
    }

    public void btnMuestraDialogo(Estudios obj) {


        oEstudio.getAreaServHrrb().setClave(obj.getAreaServHrrb().getClave());
        sClaveMantener = oEstudio.getAreaServHrrb().getClave();

        if (obj.getAreaServHrrb().getClave() == 34 && obj.getClasificacion().getTipoParametro().equals("T83") //Laboratorio//
                && (obj.getClasificacion().getClaveParametro().equals("1") || obj.getClasificacion().getClaveParametro().equals("3")
                || obj.getClasificacion().getClaveParametro().equals("4"))) {
            this.sBoton = "dlgOrdLab";
            if (obj.getClasificacion().getClaveParametro().equals("1")) {
                this.bEstudios1 = true;
                this.bEstudios2 = false;
                this.bEstudios3 = false;
            } else if (obj.getClasificacion().getClaveParametro().equals("3")) {
                this.bEstudios1 = false;
                this.bEstudios2 = true;
                this.bEstudios3 = false;
            } else if (obj.getClasificacion().getClaveParametro().equals("4")) {
                this.bEstudios1 = false;
                this.bEstudios2 = false;
                this.bEstudios3 = true;
            }
        } else if (obj.getAreaServHrrb().getClave() == 86 || obj.getAreaServHrrb().getClave() == 87 //Rayos x + Tomografía + Mastografía + Ultrasonido//
                || obj.getAreaServHrrb().getClave() == 88 || obj.getAreaServHrrb().getClave() == 89
                && obj.getClasificacion().getTipoParametro().equals("T83") && (obj.getClasificacion().getClaveParametro().equals("5")
                || obj.getClasificacion().getClaveParametro().equals("6"))) {
            this.sBoton = "dlgOrdImag";
            if (obj.getClasificacion().getClaveParametro().equals("5")) {
                this.bEstudios1 = true;
                this.bEstudios2 = false;
            } else if (obj.getClasificacion().getClaveParametro().equals("6")) {
                this.bEstudios1 = false;
                this.bEstudios2 = true;
            }
        } else if (obj.getAreaServHrrb().getClave() == 91 && obj.getClasificacion().getTipoParametro().equals("T83") //Electrocardiograma//
                && obj.getClasificacion().getClaveParametro().equals("7")) {
            this.sBoton = "dlgOrdElectro";
        } else if (obj.getAreaServHrrb().getClave() == 7) { //Banco de sangre//
            this.sBoton = "dlgOrdBanco";
        } else if (obj.getAreaServHrrb().getClave() == 84 && obj.getClasificacion().getTipoParametro().equals("T83") //Patología//
                && (obj.getClasificacion().getClaveParametro().equals("8") || obj.getClasificacion().getClaveParametro().equals("9")
                || obj.getClasificacion().getClaveParametro().equals("10"))) {
            this.sBoton = "dlgOrdPato";
            if (obj.getClasificacion().getClaveParametro().equals("8")) {
                this.bEstudios1 = true;
                this.bEstudios2 = false;
                this.bEstudios3 = false;
            } else if (obj.getClasificacion().getClaveParametro().equals("9")) {
                this.bEstudios1 = false;
                this.bEstudios2 = true;
                this.bEstudios3 = false;
            } else if (obj.getClasificacion().getClaveParametro().equals("10")) {
                this.bEstudios1 = false;
                this.bEstudios2 = false;
                this.bEstudios3 = true;
            }
        } else if (obj.getAreaServHrrb().getClave() == 85 && obj.getClasificacion().getTipoParametro().equals("T83") //Endoscopía//
                && obj.getClasificacion().getClaveParametro().equals("2")) {
            this.sBoton = "dlgOrdEndo";
        } else if (obj.getAreaServHrrb().getClave() == 2) { //Anestesiología//
            this.sBoton = "dlgOrdAnes";
        } else if (obj.getAreaServHrrb().getClave() == 90 && obj.getClasificacion().getTipoParametro().equals("T83") //Colposcopía//
                && obj.getClasificacion().getClaveParametro().equals("13")) {
            this.sBoton = "dlgOrdColpos";
        } else if (obj.getAreaServHrrb().getClave() == 27 && obj.getClasificacion().getTipoParametro().equals("T83") //Estudios otoacústicos//
                && obj.getClasificacion().getClaveParametro().equals("11")) {
            this.sBoton = "dlgOrdOto";
        }
        RequestContext.getCurrentInstance().execute("PF('" + sBoton + "').show()");
    }

    public void buscaOrdenesServicioPrevias() {
        try {
            arrOrdServPrev = new ArrayList<EstudioRealizado>(Arrays.asList(
                    oEstReal.buscarOrdenesPrevias(
                            oEpisodioMedico.getPaciente().getFolioPaciente(), 
                            oEpisodioMedico.getClaveEpisodio(), 
                            dFechaIniOrd, dFechaFinOrd)));
            sVisibilidadOrdPrev = "visible";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////ESTUDIOS LABORATORIO
    public void buscaEstudiosLab(){
        try{
            oEstEspLab.buscarClavesEstudiosLab();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void registroSolicitudLab() {
        if (oEstEspLab.getEstudio().getClaveInterna() == 0 && oEstEspLab.getEstudio().getConcepto() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE ELEGIR UN ESTUDIO"));
        } else {
            if (buscaRepetidoLab(oEstEspLab.getEstudio().getConcepto())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "EL ESTUDIO YA FUE PREVIAMENTE AGREGADO"));
                oEstudio = new Estudios();
                oEstudio.setAreaServHrrb(new AreaServicioHRRB());
                oEstudio.setClasificacion(new Parametrizacion());
                oEstEspLab = new EstudioEspLab();
                oEstEspLab.setEstudio(new Estudios());
                oEstEspLab.getEstudio().setClaveInterna(0);
                oEstEspLab.getEstudio().setConcepto("");
                oEstEspLab.setEspecimenMuestraTejido("");
                oEstEspLab.setFecMuestras(null);
            } else {
                if (oEstEspLab.getEstudio().getClaveInterna() != 0 && oEstEspLab.getEstudio().getConcepto() != null) {
                    oEstEspLab.setEpisodio(this.oEpisodioMedico);
                    oEstEspLab.setAutorizadoPor(oMedFirm);
                    sVisibilidadSolLab = "visible";
                    arrSolLab.add(oEstEspLab);

                    oEstudio = new Estudios();
                    oEstudio.setAreaServHrrb(new AreaServicioHRRB());
                    oEstudio.setClasificacion(new Parametrizacion());
                    oEstEspLab = new EstudioEspLab();
                    oEstEspLab.setEstudio(new Estudios());
                }
            }
        }
    }

    public boolean buscaRepetidoLab(String clave) {
        boolean bandera = false;
        for (EstudioEspLab i : arrSolLab) {
            if (i.getEstudio().getConcepto().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void borrarEstSolLab(EstudioEspLab obj) {
        arrSolLab.remove(obj);
        if (arrSolLab.isEmpty()) {
            sVisibilidadSolLab = "hidden";
        } else {
            sVisibilidadSolLab = "visible";
        }
    }

    public void guardarLab1(){
        FacesMessage message = null;
        EstudioEspLab estudio = new EstudioEspLab();
        
        try {
            estudio.insertaEstudiosLab(arrSolLab);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Laboratorio", "Solicitud Guardada Correctamente");
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Laboratorio", "Error: Solicitud No Guardada");
            e.printStackTrace();
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void guardarLab2(){
        FacesMessage message = null;
        
        oEstEspLab.setAutorizadoPor(oMedFirm);
        oEstEspLab.setEpisodio(oEpisodioMedico);
        try {
            if (oEstEspLab.insertaSolBAAR() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Laboratorio BAAR", "Solicitud Guardada Correctamente");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void guardarLab3(){
        FacesMessage message = null;
        
        oEstEspLab.setAutorizadoPor(oMedFirm);
        oEstEspLab.setEpisodio(oEpisodioMedico);
        try {
            if (oEstEspLab.insertaSolEnvio() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Laboratorio de Envío", "Solicitud Guardada Correctamente");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

////////////////////////////////////////////////////////////////////////////////////////////////ESTUDIOS IMAGENOLOGIA   
    public List<EstudioRealImagen> getListaEstudiosImag(String txt, int CveArea) {
        List<EstudioRealImagen> lListaEst = null;
        try {
            lListaEst = new ArrayList<EstudioRealImagen>(Arrays.asList(
                    (new EstudioRealImagen()).buscarEstudiosImag(txt, CveArea)));
        } catch (Exception ex) {
            Logger.getLogger(EstudioRealImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaEst;
    }

    public List<String> completarEstImag(String sTxt){
        List<String> arrRet;
        arrRet = new ArrayList<String>();
        List<EstudioRealImagen> lis = getListaEstudiosImag(sTxt, sClaveMantener);
        
        for (EstudioRealImagen li : lis) {
            if (sp_ascii(li.getEstudio().getConcepto()).contains(sTxt)) {
                arrRet.add(li.getEstudio().getConcepto());
            }
        }
        return arrRet;
    }

    public String sp_ascii(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public void buscaEstudiosIma() {
        try{
            oEstRealImg.buscarClavesEstudiosImag();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Parametrizacion[] getListaRegionImag() {
        try {
            arrRegImg = (new Parametrizacion()).buscarRegionImagen(
                    oEstRealImg.getEstudio().getClaveInterna());
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrRegImg;
    }

    public void registroSolicitudImg() {
        if (oEstRealImg.getEstudio().getClaveInterna() == 0 && 
            oEstRealImg.getEstudio().getConcepto() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "ERROR","DEBE ELEGIR UN ESTUDIO"));
        } else {
            if (buscaRepetidoImg(oEstRealImg.getEstudio().getConcepto())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", 
                                "EL ESTUDIO YA FUE PREVIAMENTE AGREGADO"));
                oEstudio = new Estudios();
                oEstudio.setAreaServHrrb(new AreaServicioHRRB());
                oEstudio.setClasificacion(new Parametrizacion());
                oEstRealImg = new EstudioRealImagen();
                oEstRealImg.setEstudio(new Estudios());
                oEstRealImg.setImagenRegion(new Parametrizacion());
                oEstRealImg.setRegionSolicitada(new Parametrizacion());
                oEstRealImg.setMedioContraste(new Parametrizacion());

                this.getEstRealImg().setPortatil(false);
                this.getEstRealImg().getEstudio().setClaveStudio("");
                this.getEstRealImg().getEstudio().setConcepto("");
                this.getEstRealImg().getImagenRegion().setValor("0");
                this.getEstRealImg().setTransoperatorioProbable("");
            } else {
                if (oEstRealImg.getEstPortatil() != null && 
                    oEstRealImg.getEstudio().getClaveInterna() != 0 && 
                    oEstRealImg.getEstudio().getConcepto() != null && 
                    oEstRealImg.getTransoperatorioProbable() != null) {
                    oEstRealImg.getImagenRegion().equalsImagenRegion(arrRegImg);
                    oEstRealImg.setAutorizadoPor(oMedFirm);
                    oEstRealImg.setEpisodio(oEpisodioMedico);
                    oEstRealImg.setArea(getEstudio().getAreaServHrrb());
                    oEstRealImg.setRegionSolicitada(new Parametrizacion());
                    arrSolImg.add(oEstRealImg);
                    oEstudio = new Estudios();
                    oEstudio.setAreaServHrrb(new AreaServicioHRRB());
                    oEstudio.setClasificacion(new Parametrizacion());
                    oEstRealImg = new EstudioRealImagen();
                    oEstRealImg.setEstudio(new Estudios());
                    oEstRealImg.setImagenRegion(new Parametrizacion());
                    this.getEstRealImg().setPortatil(false);
                    this.getEstRealImg().getEstudio().setClaveStudio("");
                    this.getEstRealImg().getEstudio().setConcepto("");
                    this.getEstRealImg().getImagenRegion().setValor("0");
                    this.getEstRealImg().setTransoperatorioProbable("");
                    this.getEstRealImg().setRegionSolicitada(new Parametrizacion());
                    sVisibilidadSolImg = "visible";
                }
            }
        }
    }

    public boolean buscaRepetidoImg(String clave) {
        boolean bandera = false;
        for (EstudioRealImagen i : arrSolImg) {
            if (i.getEstudio().getConcepto().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void borrarEstSolImg(EstudioRealImagen obj) {
        arrSolImg.remove(obj);
        if (arrSolImg.isEmpty()) {
            sVisibilidadSolImg = "hidden";
        } else {
            sVisibilidadSolImg = "visible";
        }
    }

    public void guardarImg(){
        FacesMessage message = null;
        try {
            oEstRealImg.insertaEstudiosImg(arrSolImg);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Imagenologia", "Solicitud Guardada Correctamente");
            limpiarTablaImg();
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Imagenologia", "Error: Solicitud No Guardada");
            e.printStackTrace();
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void limpiarTablaImg() {
        arrSolImg.clear();
        sVisibilidadSolImg = "hidden";
    }

////////////////////////////////////////////////////////////////////////////////////////////////TOMOGRAFÍA  
    public Parametrizacion[] getListaMedioContrasteTomo() {
        try {
            arrMedCont = (new Parametrizacion()).buscarMedioContraste();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrMedCont;
    }

    public List<EstudioRealImagen> getListaEstudiosTomo(String txt, int CveArea2) {
        List<EstudioRealImagen> lListaEst = null;
        try {
            lListaEst = new ArrayList<EstudioRealImagen>(Arrays.asList(
                    (new EstudioRealImagen()).buscarEstudiosTomo(txt, CveArea2)));
        } catch (Exception ex) {
            Logger.getLogger(EstudioRealImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaEst;
    }

    public List<String> completarEstTomo(String sTxt){
        List<String> arrRet;
        arrRet = new ArrayList<String>();
        List<EstudioRealImagen> lis = getListaEstudiosTomo(sTxt, sClaveMantener);
        for (EstudioRealImagen li : lis) {
            if (sp_ascii(li.getEstudio().getConcepto()).contains(sTxt)) {
                arrRet.add(li.getEstudio().getConcepto());
            }
        }
        return arrRet;
    }

    public void buscaEstudiosTomo(){
        try{
            oEstRealImg.buscarClavesEstudiosTomo();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void guardarImg2(){
        FacesMessage message = null;
        
        oEstRealImg.setAutorizadoPor(oMedFirm);
        oEstRealImg.setEpisodio(oEpisodioMedico);
        oEstRealImg.getRegionSolicitada().equalsImagenRegion(arrRegImg);
        oEstRealImg.getMedioContraste().equalsMedioContraste(arrMedCont);
        try {
            if (oEstRealImg.insertaSolTomografia() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Tomografía", "Solicitud Guardada Correctamente");
                limpiarSolTomo();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Solicitud de Tomografía", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void limpiarSolTomo() {
        oEstRealImg.getEstudio().setClaveStudio("");
        oEstRealImg.getEstudio().setConcepto("");
        oEstRealImg.getRegionSolicitada().setValor("");
        oEstRealImg.setPortatil(false);
    }

////////////////////////////////////////////////////////////////////////////////////////////////OTOACUSTICOS  
    public void buscaEstudiosOto(){
        try{
            oEstReal.buscarClavesEstudiosOto();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void registroSolicitudOto() {
        if (oEstReal.getEstudio().getClaveInterna() == 0 && oEstReal.getEstudio().getConcepto() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE ELEGIR UN ESTUDIO"));
        } else {
            if (buscaRepetidoOto(oEstReal.getEstudio().getConcepto())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "EL ESTUDIO YA FUE PREVIAMENTE AGREGADO"));
                oEstudio = new Estudios();   
                oEstudio.setAreaServHrrb(new AreaServicioHRRB());
                oEstudio.setClasificacion(new Parametrizacion());
                oEstReal = new EstudioRealizado();
                oEstReal.setEstudio(new Estudios());

                oEstReal.getEstudio().setClaveInterna(0);
                oEstReal.getEstudio().setConcepto("");
            } else {
                if (oEstReal.getEstudio().getClaveInterna() != 0 && oEstReal.getEstudio().getConcepto() != null) {
                    oEstReal.setEpisodio(oEpisodioMedico);
                    oEstReal.setAutorizadoPor(oMedFirm);
                    sVisibilidadSolOto = "visible";
                    arrEstuOto.add(oEstReal);

                    oEstudio = new Estudios();
                    oEstudio.setAreaServHrrb(new AreaServicioHRRB());
                    oEstudio.setClasificacion(new Parametrizacion());
                    oEstReal = new EstudioRealizado();
                    oEstReal.setEstudio(new Estudios());
                }
            }
        }
    }

    public boolean buscaRepetidoOto(String clave) {
        boolean bandera = false;
        if (arrEstuOto != null)
            for (EstudioRealizado i : arrEstuOto) {
                if (i.getEstudio().getConcepto().equals(clave)) {
                    bandera = true;
                    break;
                }
            }
        return bandera;
    }

    public void borrarEstSolOto(EstudioRealizado obj) {
        arrEstuOto.remove(obj);
        if (arrEstuOto.isEmpty()) {
            sVisibilidadSolOto = "hidden";
        } else {
            sVisibilidadSolOto = "visible";
        }
    }

    public void guardarOto(){
        FacesMessage message = null;
        EstudioRealizado estudio = new EstudioRealizado();
        try {
            estudio.insertaEstudiosOto(arrEstuOto);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Otoacústicos", "Solicitud Guardada Correctamente");
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Otoacústicos", "Error: Solicitud No Guardada");
            e.printStackTrace();
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

////////////////////////////////////////////////////////////////////////////////////////////////BANCO DE SANGRE
    public ProductoHemoderivado[] getListaProductoHem() {
        try {
            arrProHemo = (new ProductoHemoderivado()).buscarProductoHem();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrProHemo;
    }

    public Parametrizacion[] getListaGrpSang() {
        try {
            arrGrpSang = (new Parametrizacion()).buscarGrupoSanguineo();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrGrpSang;
    }

    public Parametrizacion[] getListaTipoPacienteExtInt() {
        try {
            arrTipoPacEI = (new Parametrizacion()).buscarTipoPacExtInt();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrTipoPacEI;
    }

    public Parametrizacion[] getListaNivelUrgenciaBancoSangre() {
        try {
            arrNivUrgBanco = (new Parametrizacion()).buscarNivUrgBanco();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrNivUrgBanco;
    }

    public Parametrizacion[] getListaRHBS() {
        try {
            arrRHBancoSangre = (new Parametrizacion()).buscarRH();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrRHBancoSangre;
    }

    public Parametrizacion[] getListaTipoSolBanco() {
        try {
            arrTipoSolBanco = (new Parametrizacion()).buscarTipoSolBanco();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrTipoSolBanco;
    }

    public void registroProductoHemoSol() {
        if (oDetalleSolHemo.getProductoH().getDescripcion().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "SELECCIONE UN PRODUCTO"));
        } else {
            if (buscaRepetidoHemoderivado(oDetalleSolHemo.getProductoH().getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "EL PRODUCTO YA FUE PREVIAMENTE AGREGADO"));
                oDetalleSolHemo.setServicioCobrable(new ProductoHemoderivado());
                oDetalleSolHemo.getProductoH().setDescripcion("");
                oDetalleSolHemo.getProductoH().setUnidades(0);
                oDetalleSolHemo.getSolicitudS().setTipoPacSolicita(new Parametrizacion());
            } else {
                if (oDetalleSolHemo.getProductoH().getDescripcion() != null && 
                        oDetalleSolHemo.getProductoH().getUnidades() != 0) {
                    oDetalleSolHemo.getProductoH().equalsProductoHemo(arrProHemo);
                    lProHemoAgregado.add(this.getDetalleSolHemo().getProductoH());
                    oDetalleSolHemo.setServicioCobrable(new ProductoHemoderivado());
                    sVisibilidadProdHemo = "visible";
                }
            }
        }
    }

    public boolean buscaRepetidoHemoderivado(String clave) {
        boolean bandera = false;
        for (ProductoHemoderivado i : lProHemoAgregado) {
            if (i.getDescripcion().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void borrarElementoProductoHemo(ProductoHemoderivado obj) {
        lProHemoAgregado.remove(obj);
        if (lProHemoAgregado.isEmpty()) {
            sVisibilidadProdHemo = "hidden";
        } else {
            sVisibilidadProdHemo = "visible";
        }
    }

    public void guardarBancoSangre() {
        oDetalleSolHemo.setEpisodio(oEpisodioMedico);
        oDetalleSolHemo.setAutorizadoPor(oMedFirm);
        System.out.println(oDetalleSolHemo.getSolicitudS().getFechaUltTrans());
        try {
            if (oDetalleSolHemo.insertaSolBancoSangre(lProHemoAgregado) >= 1) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Solicitud de Banco de Sangre", 
                                "Solicitud Guardada Correctamente"));
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(
                    Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                "Solicitud de Banco de Sangre", 
                                "Error al almacenar la solicitud"));
        }
    }

    public void cantidadSolHemo() {
        oDetalleSolHemo.getProductoH().equalsProductoHemo(arrProHemo);
        
        if (oDetalleSolHemo.getProductoH().getClave().equals("410-13") || 
                oDetalleSolHemo.getProductoH().getClave().equals("410-08")) {
            this.bCantProductoHemo = false;
        } else {
            this.bCantProductoHemo = true;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////ANESTESIOLOGÍA
    public Parametrizacion[] getListaElectUrgnt() {
        try {
            arrElectUrgnt = (new Parametrizacion()).buscarElectivaUrgente();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrElectUrgnt;
    }

    public void guardarProcedimientoQuirurgico(){
        FacesMessage message = null;
        oProcReal.setAutorizadoPor(oMedFirm);
        oProcReal.setEpisodioMedico(oEpisodioMedico);
        
        if(oProcReal.getSolSangre().getTipoSangre().getValor() == null || 
                oProcReal.getSolSangre().getTipoSangre().getValor().equals("") ||
                oProcReal.getSolSangre().getRH().getValor() == null || 
                oProcReal.getSolSangre().getRH().getValor().equals("") || 
                oProcReal.getProdHem().getDescripcion() == null || 
                oProcReal.getProdHem().getDescripcion().equals("")){
            try {
                if (oProcReal.insertaSolProcedimientoQuirurgico() == 1) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Solicitud de Procedimiento Quirúrgico", 
                            "Solicitud Guardada Correctamente");
                    getListaProcedimientoMaterialOsteosintesis();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                        "Error en la base de datos", 
                        "Error: Solicitud No Guardada");
            }
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            
        } else {
            
            oProcReal.getSolSangre().getTipoSangre().equalsGrupoSangre(arrGrpSang);
            oProcReal.getSolSangre().getRH().equalsRHBanSang(arrRHBancoSangre);
            oProcReal.getProdHem().equalsProductoHemo(arrProHemo);
            try {
                if (oProcReal.insertaSolProcedimientoQuirurgico() == 1) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Procedimiento Quirúrgico", "Solicitud Guardada Correctamente");
                    getListaProcedimientoMaterialOsteosintesis();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
            }
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void registroMatOsteo() {
        oDetValMat.setServicioCobrable(oMaterial);
        if (getMaterial().getNombre().equals("") && 
            oDetValMat.getCantSolicitada() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR", 
                    "DEBE COMPLETAR LA SOLICITUD DE MATERIAL"));
        } else {
            if (buscaRepetidoMatOst(getMaterial().getClaveMaterial())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", 
                                "EL MATERIAL YA FUE PREVIAMENTE AGREGADO"));
                oMaterial = new Material();
            } else {
                if (getMaterial().getNombre() != null 
                    && oDetValMat.getCantSolicitada() != 0) {
                    oDetValMat.setEpisodio(oEpisodioMedico);
                    oDetValMat.getValeMat().setProcReal(oProcReal);
                    oDetValMat.setAutorizadoPor(oMedFirm);
                    arrDetalleValeMat.add(oDetValMat);
                    oMaterial = new Material();

                    sVisibilidadMatOsteo = "visible";
                }
            }
        }
    }

    public boolean buscaRepetidoMatOst(String clave) {
        boolean bandera = false;
        for (DetalleValeMaterial i : arrDetalleValeMat) {
            if (i.getMaterial().getClaveMaterial().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void borrarElementoMatOsteo(DetalleValeMaterial obj) {
        arrDetalleValeMat.remove(obj);
        if (arrDetalleValeMat.isEmpty()) {
            sVisibilidadMatOsteo = "hidden";
        } else {
            sVisibilidadMatOsteo = "visible";
        }
    }

    public void buscaClave(){
        try{
            oMaterial.buscarPorNombre();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void guardarMatOst(){
        try {
            if (this.oDetValMat.insertaSolMatOst(arrDetalleValeMat) == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Material de Osteosintesis", "Solicitud Guardada Correctamente"));
                limpiarTablaOst();
            }
        } catch (Exception e) {
            Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void limpiarTablaOst() {
        arrDetalleValeMat.clear();
        sVisibilidadMatOsteo = "hidden";
    }
    
    public void validaSeleccionArea(){
        if (getEpisodioMedico().getArea().getTipo().equals("CE")) {
            sArea1 = "X";
            sArea2 = "";
            sArea3 = "";
        } else if (getEpisodioMedico().getArea().getTipo().equals("HOSP") || getEpisodioMedico().getArea().getTipo().equals("HOSP-P") || getEpisodioMedico().getArea().getTipo().equals("HOSPSUB")) {
            sArea1 = "";
            sArea2 = "X";
            sArea3 = "";
        } else if (getEpisodioMedico().getArea().getTipo().equals("URG")) {
            sArea1 = "";
            sArea2 = "";
            sArea3 = "X";
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////PATOLOGíA
    public Parametrizacion[] getListaSitioAnat() {
        try {
            arrSitioAnat = (new Parametrizacion()).buscarSitiosAnat();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrSitioAnat;
    }

    public Parametrizacion[] getListaTipoBiopPzaQx() {
        try {
            arrTipoBiopPzaQx = (new Parametrizacion()).buscarTipoBiopPzaQx();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrTipoBiopPzaQx;
    }

    public Parametrizacion[] getListaAntecCitol() {
        try {
            arrAntecCitol = (new Parametrizacion()).buscarAntecCitologia();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrAntecCitol;
    }

    public Parametrizacion[] getListaSitGineco() {
        try {
            arrSitioGineco = (new Parametrizacion()).buscarSitGineco();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrSitioGineco;
    }

    public Parametrizacion[] getListaTipoProcCitol() {
        try {
            arrTipoProcCitol = (new Parametrizacion()).buscarTipoProcCitol();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrTipoProcCitol;
    }

    public Parametrizacion[] buscarTipoProcPatoLiq() {
        try {
            arrTipoProcCitol = (new Parametrizacion()).buscarTipoProcCitol();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrTipoProcCitol;
    }

    public void buscaEstudiosPatoBiop(){
        try{
            oEstPat.buscarClavesEstudiosPatoBiop();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void validaSeleccion(){
        if (getProcReal().getTipoCir().equals("8")) {
            sObj1 = "X";
            sObj2 = "";
        } else if (getProcReal().getTipoCir().equals("9")) {
            sObj1 = "";
            sObj2 = "X";
        }
    }

    public void validaSeleccionTransOpe(){
        if (getProcReal().getEstTransProb().equals("1")) {
            sObj3 = "X";
            sObj4 = "";
        } else if (getProcReal().getEstTransProb().equals("0")) {
            sObj3 = "";
            sObj4 = "X";
        }
    }

    public void guardarPat1(){
        FacesMessage message = null;
        
        oEstPat.setAutorizadoPor(oMedFirm);
        oEstPat.setEpisodio(oEpisodioMedico);
        try {
            oEstPat.getSitAnatomico().equalsPatologiaSitiosAnat(arrSitioAnat);
            oEstPat.getTipoBiopPzaQx().equalsPatologiaBiopsia(arrTipoBiopPzaQx);
            if (oEstPat.insertaSolPatBiopPzaQx() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Patología Biopsia Pieza Quirúrgica", "Solicitud Guardada Correctamente");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void guardarPat2(){
        FacesMessage message = null;
        
        oEstPat.setAutorizadoPor(oMedFirm);
        oEstPat.setEpisodio(oEpisodioMedico);
        try {
            oEstPat.getAntecCitol().equalsPatologiaCitologia(arrAntecCitol);
            oEstPat.getSitGineco().equalsPatologiaSituacionGineco(arrSitioGineco);
            if (oEstPat.insertaSolPatCitCerv() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Patología Citología Cervico Vaginal", "Solicitud Guardada Correctamente");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void guardarPat3(){
        FacesMessage message = null;
        
        oEstPat.setAutorizadoPor(oMedFirm);
        oEstPat.setEpisodio(oEpisodioMedico);
        try {
            oEstPat.getTipoCitol().equalsPatologiaTipoProcCitol(arrTipoProcCitol);
            oEstPat.getSitAnatomico().equalsPatologiaSitiosAnat(arrSitioAnat);
            if (oEstPat.insertaSolPatLiqCorp() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Patología Citología Cervico Vaginal", "Solicitud Guardada Correctamente");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

////////////////////////////////////////////////////////////////////////////////////////////////ELECTROCARDIOGRAMA
    public void guardarSolElectro(){
        FacesMessage message = null;
        
        oEstReal.setAutorizadoPor(oMedFirm);
        oEstReal.setEpisodio(oEpisodioMedico);
        try {
            if (oEstReal.insertarElectro() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de electrocardiograma", "Solicitud Guardada Correctamente");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

////////////////////////////////////////////////////////////////////////////////////////////////INTERCONSULTA ENDOSCOPIA
    public void buscaEstudiosEnd(){
        try{
            (new EstudioEndoscopia()).buscarClavesEstudiosEnd();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void guardarSolEndos(){
        FacesMessage message = null;
        
        this.oEstEndos.setAutorizadoPor(oMedFirm);
        oEstEndos.setEpisodio(oEpisodioMedico);
        try {
            if (oEstEndos.insertarInterEndos() >= 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Interconsulta de endoscopía", "Interconsulta Guardada Correctamente");
                oEstEndos.setMotivo(null);
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Interconsulta de endoscopía", 
                        "Error al almacenar la interconsulta");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

////////////////////////////////////////////////////////////////////////////////////////////////INTERCONSULTA COLPOSCOPÍA
    public void guardarSolColpos() {
        //int afec=0;
        FacesMessage message = null;
        
        this.oEstInter.setAutorizadoPor(oMedFirm);
        oEstInter.setEpisodio(oEpisodioMedico);
        try {
            if (oEstInter.insertarInterColpos() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Interconsulta de colposcopía", "Interconsulta Guardada Correctamente");
                oEstInter.setMotivo(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Solicitud No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

////////////////////////////////////////////////////////////////////////////////////////////////INTERCONSULTA
    public void guardarSolInter() {
        FacesMessage message = null;
        
        oEstInter.setAutorizadoPor(oMedFirm);
        oEstInter.setEpisodio(oEpisodioMedico);
        oEstInter.setAreaServ(oAreaServ);
        
        try {
            //oEstInter.getAreaServ().equalsAreaConsulta(arrAreaCE);
            if (oEstInter.insertarInterconsulta() >= 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Interconsulta", "Interconsulta Guardada Correctamente");
                oEstInter.setMotivo("");
                oEstInter.getAreaServ().setClave(0);
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Interconsulta", "Error al almacenar la interconsulta");
            }
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error en la base de datos", "Error: Solicitud No Guardada");
            ex.printStackTrace();
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public AreaServicioHRRB[] getListaAreaCE() {
        try {
            arrAreaCE = (new AreaServicioHRRB()).buscarConsultoriosCE();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrAreaCE;
    }

////////////////////////////////////////////////////////////////////////////////////////////////NOTA DE REFERENCIA
    public void guardarNotaReferencia() {
        FacesMessage message = null;
        
        oTraslado.setAutorizadoPor(oMedFirm);
        oTraslado.setEpisodio(oEpisodioMedico);
        try {
            oTraslado.getMotivoEnvio().equalsMotivoEnv(arrMotivoEnv);
            if (oTraslado.insertaNotaRefMedico() == 1) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota Referencia", "Nota Referencia Guardada Correctamente");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en la base de datos", "Error: Nota De Referencia No Guardada");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public Parametrizacion[] getListaMotivoEnv() {
        try {
            arrMotivoEnv = (new Parametrizacion()).buscarMotivoEnvio();
        } catch (Exception ex) {
            Logger.getLogger(RegistroNotaMedEvolJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrMotivoEnv;
    }

    public void otroMotivoRef() {
        oTraslado.getMotivoEnvio().equalsMotivoEnv(arrMotivoEnv);
        if (oTraslado.getMotivoEnvio().getClaveParametro().equals("7")) {
            this.bOtroMotivo = false;
        } else {
            this.bOtroMotivo = true;
        }
    }

    public List<EstudioRealizado> buscaAuxDiag() {
        try {
            arrEstuReal = new ArrayList<EstudioRealizado>(Arrays.asList(oEstReal.buscaAuxiliarDeDiag(oEpisodioMedico)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrEstuReal;
    }

    public ArrayList<EstudioRealizado> getListAuxDiag() {
        return arrEstuReal;
    }

    public void motivoReferencia() {
        try {
            arrMotivoRef = new ArrayList<NotaMedicaHRRB>(Arrays.asList(oNotaMedHrrb.buscarMotivoReferencia(oEpisodioMedico.getPaciente().getFolioPaciente(), oEpisodioMedico.getClaveEpisodio())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void limpiarTodo(){
        ////////////////////////////////////////////////////////////////////////NOTA MEDICA
        this.oEpisodioMedico.getSignosVitales().setTA("");
        this.oEpisodioMedico.getSignosVitales().setFC("");
        this.oEpisodioMedico.getSignosVitales().setFR("");
        this.oEpisodioMedico.getSignosVitales().setTemp("");
        this.oEpisodioMedico.getPaciente().setPeso(0);
        this.oEpisodioMedico.getPaciente().setTalla(0);
        this.oNotaMedHrrb.setResumenInter("");
        this.oNotaMedHrrb.setExploracionFisica("");
        this.oNotaMedHrrb.setResultadosServAuxDiag("");
        this.setFechaIniAuxResPrev(null);
        this.setFechaFinAuxResPrev(null);
        this.setFechaIni(null);
        this.setFechaFin(null);
        try{
            this.oCie10.setDescripcionDiag("");
        }catch(Exception e){
            e.printStackTrace();
        }
        this.oCie9.setDescripcion("");
        this.oCie9.setClave("");
        this.oNotaMedHrrb.setPlanDeTratamiento("");
        this.oNotaMedHrrb.setPronostico("");
        this.setVisibilidadResultEstPrev("hidden");
        this.setVisibilidadDescargaResult("hidden");
        this.setVisibilidadDiagnostico("hidden");
        ////////////////////////////////////////////////////////////////////////RECETA
        this.oReceta.setFolioReceta(0);
        this.oMedicamento.setClaveMedicamento("");
        this.oMedicamento.setNombre("");
        this.oMedicamento.setPresentacion("");
        this.oDetalleRecHRRB.setDosis("");
        this.oDetalleRecHRRB.getVia().setValor("");
        this.oDetalleRecHRRB.setFrecuencia("");
        this.oDetalleRecHRRB.setDuracion("");
        this.oNotaMedHrrb.setIndicacionTer("");
        this.setVisibilidadReceta("hidden");
        ////////////////////////////////////////////////////////////////////////SERVICIOS
        this.oEstudio.getAreaServHrrb().setClave(0);
        this.oEstudio.getClasificacion().setValor("");
        this.setVisibilidadServicios("hidden");
        this.setFechaIniOrd(null);
        this.setFechaFinOrd(null);
        this.setVisibilidadOrdPrev("hidden");
        ////////////////////////////////////////////////////////////////////////INTERCONSULTA
        this.oAreaServ.setClave(0);
        this.oEstInter.setMotivo("");
        ////////////////////////////////////////////////////////////////////////NOTA REFERENCIA
        this.oTraslado.setHospital("");
        this.oTraslado.setServicioDestino("");
        this.oTraslado.setFechaCita(null);
        this.oTraslado.getSignVit().setPulso(0);
        this.oTraslado.setDiagEnvio("");
        this.oTraslado.getMotivoEnvio().setValor("");
        this.oTraslado.setOtroMotiv("");
        ////////////////////////////////////////////////////////////////////////LABORATORIO
        this.oEstEspLab.getEstudio().setClaveStudio("");
        this.oEstEspLab.getEstudio().setConcepto("");
        this.oEstEspLab.setEspecimenMuestraTejido("");
        this.oEstEspLab.setFecMuestras(null);
        //this.oEstEspLab.setTempCent((short) 0);
        this.oEstEspLab.setFiO2(0);
        this.oEstEspLab.setHmg(0);
        this.oEstEspLab.setFechaDiagnostico(null);
        this.oEstEspLab.setTratAntifimico(false);
        this.oEstEspLab.setObs("");
        this.oEstEspLab.setFecMuestras(null);
        this.oEstEspLab.setTemp(0);
        this.oEstEspLab.setLabDestino("");
        this.setVisibilidadSolLab("hidden");
        ////////////////////////////////////////////////////////////////////////IMAGENOLOGIA
        this.oEstRealImg.setEstPortatil("");
        this.oEstRealImg.getEstudio().setClaveStudio("");
        this.oEstRealImg.getEstudio().setConcepto("");
        this.oEstRealImg.getImagenRegion().setValor("");
        this.oEstRealImg.setTransoperatorioProbable("");
        this.oEstRealImg.getRegionSolicitada().setValor("");
        this.oEstRealImg.getMedioContraste().setValor("");
        //this.oEstRealImg.setCantMedio((short) 0);
        this.setVisibilidadSolImg("hidden");
        ////////////////////////////////////////////////////////////////////////BANCO DE SANGRE
        this.oDetalleSolHemo.getSolicitudS().setFechaUltTrans(null);
        this.oDetalleSolHemo.getSolicitudS().setEmbPrevios("");
        this.oDetalleSolHemo.getSolicitudS().getTipoPacSolicita().setClaveParametro("");
        this.oDetalleSolHemo.getSolicitudS().setHb(0);
        this.oDetalleSolHemo.getSolicitudS().setHo(0);
        this.oDetalleSolHemo.getSolicitudS().getTipoSangre().setClaveParametro("");
        this.oDetalleSolHemo.getSolicitudS().getRH().setClaveParametro("");
        this.oDetalleSolHemo.getSolicitudS().setTp(0);
        this.oDetalleSolHemo.getSolicitudS().setTTp(0);
        this.oDetalleSolHemo.getSolicitudS().setNumPlaquetas(0);
        this.oDetalleSolHemo.getSolicitudS().getUrgencia().setClaveParametro("");
        this.oDetalleSolHemo.getSolicitudS().getTipoSolicitud().setClaveParametro("");
        this.oDetalleSolHemo.getProductoH().setDescripcion("");
        this.oDetalleSolHemo.getProductoH().setUnidades(0);
        this.setVisibilidadProdHemo("hidden");
        ////////////////////////////////////////////////////////////////////////PATOLOGIA
        this.oEstPat.getAntecCitol().setValor("");
        this.oEstPat.getSitGineco().setValor("");
        this.oEstPat.getTipoCitol().setValor("");
        this.oEstPat.getSitAnatomico().setValor("");
        this.oEstPat.setEspecimenMuestraTejido("");
        this.oEstPat.getTipoBiopPzaQx().setValor("");
        this.oEstPat.setFechaAproxSgteConsul(null);
        this.oEstPat.getEstudio().setConcepto("");
        this.oEstPat.getEstudio().setClaveStudio("");
        ////////////////////////////////////////////////////////////////////////OTOACÚSTICOS
        this.oSolOtroServ.getEstudio().setClaveStudio("");
        this.oSolOtroServ.getEstudio().setConcepto("");
        this.setVisibilidadSolOto("hidden");
        ////////////////////////////////////////////////////////////////////////ENDOSCOPÍA
        this.oEstEndos.getEstudio().setConcepto("");
        this.oEstEndos.getEstudio().setClaveStudio("");
        //this.oEstEndos.setMotivoInt(""); PENDIENTE
        ////////////////////////////////////////////////////////////////////////PROCEDIMIENTO QUIRÚRGICO
        this.oEpisodioMedico.getProceRe1().getCIE9().setDescripcion("");
        this.oProcReal.setNota("");
        this.oProcReal.setDuracionP("");
        this.oProcReal.setTipoCir("");
        this.oProcReal.setEstTransProb("");
        this.oProcReal.getSolSangre().getTipoSangre().setValor("");
        this.oProcReal.getSolSangre().getRH().setValor("");
        this.oProcReal.getProdHem().setDescripcion("");
        this.oProcReal.setCantSangreRes(0);
        ////////////////////////////////////////////////////////////////////////MATERIAL OSTEOSINTESIS
        this.oMaterial.setClaveMaterial("");
        this.oMaterial.setNombre("");
        this.oMaterial.getPresentacion().setValor("");
        //this.oMaterial.setCantSolicitada((short) 0);
        this.oDetValMat.setObservaciones("");
        this.arrDetalleValeMat.clear();
        this.setVisibilidadMatOsteo("hidden");
        ////////////////////////////////////////////////////////////////////////COLPOSCOPIA
        this.oEstInter.setMotivo("");
        ////////////////////////////////////////////////////////////////////////ARREGLOS
        if(!arrConsultaResultEstPrev.isEmpty()){
            this.arrConsultaResultEstPrev.clear();
        }
        if(!arrConsultaResultEst.isEmpty()){
            this.arrConsultaResultEst.clear();
        }
        if(!arrDiagCie10.isEmpty()){
            this.arrDiagCie10.clear();
        }
        if(!arrRec.isEmpty()){
            this.arrRec.clear();
        }
        if(!arrServ.isEmpty()){
            this.arrServ.clear();
        }
        if(!arrOrdServPrev.isEmpty()){
            this.arrOrdServPrev.clear();
        }
        if(!arrSolLab.isEmpty()){
            this.arrSolLab.clear();
        }
        if(!arrSolImg.isEmpty()){
            this.arrSolImg.clear();
        }
        if(!lProHemoAgregado.isEmpty()){
            this.lProHemoAgregado.clear();
        }
        if(!arrSolOto.isEmpty()){
            this.arrSolOto.clear();
        }
    }
    
////////////////////////////////////////////////////////////////////////////////////////////////
    
    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public DetalleRecetaHRRB getDetalleRecHRRB() {
        return oDetalleRecHRRB;
    }

    public void setDetalleRecHRRB(DetalleRecetaHRRB oDetalleRecHRRB) {
        this.oDetalleRecHRRB = oDetalleRecHRRB;
    }

    public ArrayList<DetalleRecetaHRRB> getRec() {
        return arrRec;
    }

    public void setRec(ArrayList<DetalleRecetaHRRB> arrRec) {
        this.arrRec = arrRec;
    }

    public boolean getBuscado() {
        return this.bBuscado;
    }

    public String getVisibilidadTabla() {
        return this.sVisibilidadTabla;
    }

    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
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

    public ProcedimientoCIE9 getCie9() {
        return oCie9;
    }

    public void setCie9(ProcedimientoCIE9 oCie9) {
        this.oCie9 = oCie9;
    }

    public Estudios getEstudio() {
        return oEstudio;
    }

    public void setEstudio(Estudios oEstudio) {
        this.oEstudio = oEstudio;
    }

    public Parametrizacion getParametrizacion() {
        return oParametrizacion;
    }

    public void setParametrizacion(Parametrizacion oParametrizacion) {
        this.oParametrizacion = oParametrizacion;
    }

    public String getVisibilidadReceta() {
        return sVisibilidadReceta;
    }

    public void setVisibilidadReceta(String sVisibilidadReceta) {
        this.sVisibilidadReceta = sVisibilidadReceta;
    }

    public String getBotonG() {
        return sBotonG;
    }

    public void setBotonG(String sBotonG) {
        this.sBotonG = sBotonG;
    }

    public Date getFechaActual() {
        return dFechaActual;
    }

    public void setFechaActual(Date dFechaActual) {
        this.dFechaActual = dFechaActual;
    }

    public List<Parametrizacion> getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(List<Parametrizacion> tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    public ArrayList<Estudios> getServ() {
        return arrServ;
    }

    public void setServ(ArrayList<Estudios> arrServ) {
        this.arrServ = arrServ;
    }

    public boolean getEstudios1() {
        return bEstudios1;
    }

    public void setEstudios1(boolean bEstudios1) {
        this.bEstudios1 = bEstudios1;
    }

    public boolean getEstudios2() {
        return bEstudios2;
    }

    public void setEstudios2(boolean bEstudios2) {
        this.bEstudios2 = bEstudios2;
    }

    public boolean getEstudios3() {
        return bEstudios3;
    }

    public void setEstudios3(boolean bEstudios3) {
        this.bEstudios3 = bEstudios3;
    }

    public EstudioEspLab getEstEspLab() {
        return oEstEspLab;
    }

    public void setEstEspLab(EstudioEspLab oEstEspLab) {
        this.oEstEspLab = oEstEspLab;
    }

    public String getBoton() {
        return sBoton;
    }

    public void setBoton(String sBoton) {
        this.sBoton = sBoton;
    }

    public AreaServicioHRRB[] getArea() {
        return arrArea;
    }

    public void setArea(AreaServicioHRRB[] arrArea) {
        this.arrArea = arrArea;
    }

    public Parametrizacion[] getClasificacion() {
        return arrClasificacion;
    }

    public void setClasificacion(Parametrizacion[] arrClasificacion) {
        this.arrClasificacion = arrClasificacion;
    }

    public EstudioRealizado getEstReal() {
        return oEstReal;
    }

    public void setEstReal(EstudioRealizado oEstReal) {
        this.oEstReal = oEstReal;
    }

    public ArrayList<EstudioEspLab> getSolLab() {
        return arrSolLab;
    }

    public void setSolLab(ArrayList<EstudioEspLab> arrSolLab) {
        this.arrSolLab = arrSolLab;
    }

    public String getVisibilidadSolLab() {
        return sVisibilidadSolLab;
    }

    public void setVisibilidadSolLab(String sVisibilidadSolLab) {
        this.sVisibilidadSolLab = sVisibilidadSolLab;
    }

    public String getVisibilidadSolImg() {
        return sVisibilidadSolImg;
    }

    public void setVisibilidadSolImg(String sVisibilidadSolImg) {
        this.sVisibilidadSolImg = sVisibilidadSolImg;
    }

    public EstudioRealImagen getEstRealImg() {
        return oEstRealImg;
    }

    public void setEstRealImg(EstudioRealImagen oEstRealImg) {
        this.oEstRealImg = oEstRealImg;
    }

    public ArrayList<EstudioRealImagen> getSolImg() {
        return arrSolImg;
    }

    public void setSolImg(ArrayList<EstudioRealImagen> arrSolImg) {
        this.arrSolImg = arrSolImg;
    }

    public AccesoDatos getAD() {
        return oAD;
    }

    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }

    public ArrayList<EpisodioMedico> getConsulta() {
        return arrConsulta;
    }

    public void setConsulta(ArrayList<EpisodioMedico> arrConsulta) {
        this.arrConsulta = arrConsulta;
    }

    public Date getFechaAux() {
        return dFechaAux;
    }

    public void setFechaAux(Date dFechaAux) {
        this.dFechaAux = dFechaAux;
    }

    public EstudioRealEndos getEstEndos() {
        return oEstEndos;
    }

    public void setEstEndos(EstudioRealEndos oEstEndos) {
        this.oEstEndos = oEstEndos;
    }

    public EstudioInterconsulta getEstInter() {
        return oEstInter;
    }

    public void setEstInter(EstudioInterconsulta oEstInter) {
        this.oEstInter = oEstInter;
    }

    public SolicitudOtroServicio getSolOtroServ() {
        return oSolOtroServ;
    }

    public void setSolOtroServ(SolicitudOtroServicio oSolOtroServ) {
        this.oSolOtroServ = oSolOtroServ;
    }

    public Parametrizacion[] getSitioAnat() {
        return arrSitioAnat;
    }

    public void setSitioAnat(Parametrizacion[] arrSitioAnat) {
        this.arrSitioAnat = arrSitioAnat;
    }

    public EstudioPatologia getEstPat() {
        return oEstPat;
    }

    public void setEstPat(EstudioPatologia oEstPat) {
        this.oEstPat = oEstPat;
    }

    public Parametrizacion[] getTipoBiopPzaQx() {
        return arrTipoBiopPzaQx;
    }

    public void setTipoBiopPzaQx(Parametrizacion[] arrTipoBiopPzaQx) {
        this.arrTipoBiopPzaQx = arrTipoBiopPzaQx;
    }

    public NotaMedicaHRRB getNotaMedHrrb() {
        return oNotaMedHrrb;
    }

    public void setNotaMedHrrb(NotaMedicaHRRB oNotaMedHrrb) {
        this.oNotaMedHrrb = oNotaMedHrrb;
    }

    public Parametrizacion[] getAntecCitol() {
        return arrAntecCitol;
    }

    public void setTipoCitol(Parametrizacion[] arrAntecCitol) {
        this.arrAntecCitol = arrAntecCitol;
    }

    public Parametrizacion[] getSitioGineco() {
        return arrSitioGineco;
    }

    public void setSitioGineco(Parametrizacion[] arrSitioGineco) {
        this.arrSitioGineco = arrSitioGineco;
    }

    public Parametrizacion[] getTipoProcCitol() {
        return arrTipoProcCitol;
    }

    public void setTipoProcCitol(Parametrizacion[] arrTipoProcCitol) {
        this.arrTipoProcCitol = arrTipoProcCitol;
    }

    public AreaServicioHRRB[] getAreaCE() {
        return arrAreaCE;
    }

    public void setAreaCE(AreaServicioHRRB[] arrAreaCE) {
        this.arrAreaCE = arrAreaCE;
    }

    public AreaServicioHRRB getAreaServ() {
        return oAreaServ;
    }

    public void setAreaServ(AreaServicioHRRB oAreaServ) {
        this.oAreaServ = oAreaServ;
    }

    public Traslado getTraslado() {
        return oTraslado;
    }

    public void setTraslado(Traslado oTraslado) {
        this.oTraslado = oTraslado;
    }

    public Parametrizacion[] getMotivoEnv() {
        return arrMotivoEnv;
    }

    public void setMotivoEnv(Parametrizacion[] arrMotivoEnv) {
        this.arrMotivoEnv = arrMotivoEnv;
    }

    public Estudios[] getEstudioTomo() {
        return arrEstudioTomo;
    }

    public void setEstudioTomo(Estudios[] arrEstudioTomo) {
        this.arrEstudioTomo = arrEstudioTomo;
    }

    public Parametrizacion[] getRegImg() {
        return arrRegImg;
    }

    public void setRegImg(Parametrizacion[] arrRegImg) {
        this.arrRegImg = arrRegImg;
    }

    public boolean getOtroMotivo() {
        return bOtroMotivo;
    }

    public void setOtroMotivo(boolean bOtroMotivo) {
        this.bOtroMotivo = bOtroMotivo;
    }

    public ProductoHemoderivado[] getProHemo() {
        return arrProHemo;
    }

    public void setProHemo(ProductoHemoderivado[] arrProHemo) {
        this.arrProHemo = arrProHemo;
    }

    public ProductoHemoderivado getProductoHemoderivado() {
        return oProductoHemoderivado;
    }

    public void setProductoHemoderivado(ProductoHemoderivado oProductoHemoderivado) {
        this.oProductoHemoderivado = oProductoHemoderivado;
    }

    public SolicitudSangre getSolSangre() {
        return oSolSangre;
    }

    public void setSolSangre(SolicitudSangre oSolSangre) {
        this.oSolSangre = oSolSangre;
    }

    public ArrayList<DetalleSolicitudHemoderivado> getDetHemo() {
        return arrDetHemo;
    }

    public void setDetHemo(ArrayList<DetalleSolicitudHemoderivado> arrDetHemo) {
        this.arrDetHemo = arrDetHemo;
    }

    public DetalleSolicitudHemoderivado getDetalleSolHemo() {
        return oDetalleSolHemo;
    }

    public void setDetalleSolHemo(DetalleSolicitudHemoderivado oDetalleSolHemo) {
        this.oDetalleSolHemo = oDetalleSolHemo;
    }

    public String getVisibilidadProdHemo() {
        return sVisibilidadProdHemo;
    }

    public void setVisibilidadProdHemo(String sVisibilidadProdHemo) {
        this.sVisibilidadProdHemo = sVisibilidadProdHemo;
    }

    public Parametrizacion[] getGrpSang() {
        return arrGrpSang;
    }

    public void setGrpSang(Parametrizacion[] arrGrpSang) {
        this.arrGrpSang = arrGrpSang;
    }

    public Parametrizacion[] getTipoPacEI() {
        return arrTipoPacEI;
    }

    public void setTipoPacEI(Parametrizacion[] arrTipoPacEI) {
        this.arrTipoPacEI = arrTipoPacEI;
    }

    public Parametrizacion[] getNivUrgBanco() {
        return arrNivUrgBanco;
    }

    public void setNivUrgBanco(Parametrizacion[] arrNivUrgBanco) {
        this.arrNivUrgBanco = arrNivUrgBanco;
    }

    public Parametrizacion[] getRHBancoSangre() {
        return arrRHBancoSangre;
    }

    public void setRHBancoSangre(Parametrizacion[] arrRHBancoSangre) {
        this.arrRHBancoSangre = arrRHBancoSangre;
    }

    public Parametrizacion[] getTipoSolBanco() {
        return arrTipoSolBanco;
    }

    public void setTipoSolBanco(Parametrizacion[] arrTipoSolBanco) {
        this.arrTipoSolBanco = arrTipoSolBanco;
    }

    public Parametrizacion[] getElectUrgnt() {
        return arrElectUrgnt;
    }

    public void setElectUrgnt(Parametrizacion[] arrElectUrgnt) {
        this.arrElectUrgnt = arrElectUrgnt;
    }

    public ProcedimientosRealizados getProcReal() {
        return oProcReal;
    }

    public void setProcReal(ProcedimientosRealizados oProcReal) {
        this.oProcReal = oProcReal;
    }

    public DetalleValeMaterial getDetValMat() {
        return oDetValMat;
    }

    public void setDetValMat(DetalleValeMaterial oDetValMat) {
        this.oDetValMat = oDetValMat;
    }

    public ArrayList<DetalleValeMaterial> getDetalleValeMat() {
        return arrDetalleValeMat;
    }

    public void setDetalleValeMat(ArrayList<DetalleValeMaterial> arrDetalleValeMat) {
        this.arrDetalleValeMat = arrDetalleValeMat;
    }

    public String getVisibilidadMatOsteo() {
        return sVisibilidadMatOsteo;
    }

    public void setVisibilidadMatOsteo(String sVisibilidadMatOsteo) {
        this.sVisibilidadMatOsteo = sVisibilidadMatOsteo;
    }

    public Material getMaterial() {
        return oMaterial;
    }

    public void setMaterial(Material oMaterial) {
        this.oMaterial = oMaterial;
    }

    public boolean getCantProductoHemo() {
        return bCantProductoHemo;
    }

    public void setCantProductoHemo(boolean bCantProductoHemo) {
        this.bCantProductoHemo = bCantProductoHemo;
    }

    public Parametrizacion[] getVia() {
        return arrVia;
    }

    public void setVia(Parametrizacion[] arrVia) {
        this.arrVia = arrVia;
    }

    public Receta getReceta() {
        return oReceta;
    }

    public void setReceta(Receta oReceta) {
        this.oReceta = oReceta;
    }

    public ArrayList<ProductoHemoderivado> getProHemoAgregado() {
        return lProHemoAgregado;
    }

    public void setProHemoAgregado(ArrayList<ProductoHemoderivado> lProHemoAgregado) {
        this.lProHemoAgregado = lProHemoAgregado;
    }

    public String getVisibilidadSolOto() {
        return sVisibilidadSolOto;
    }

    public void setVisibilidadSolOto(String sVisibilidadSolOto) {
        this.sVisibilidadSolOto = sVisibilidadSolOto;
    }

    public ArrayList<SolicitudOtroServicio> getSolOto() {
        return arrSolOto;
    }

    public void setSolOto(ArrayList<SolicitudOtroServicio> arrSolOto) {
        this.arrSolOto = arrSolOto;
    }

    public DiagnosticoCIE10 getCie10() {
        return oCie10;
    }

    public void setCie10(DiagnosticoCIE10 oCie10) {
        this.oCie10 = oCie10;
    }

    public ArrayList<DiagnosticoCIE10> getDiagCie10() {
        return arrDiagCie10;
    }

    public void setDiagCie10(ArrayList<DiagnosticoCIE10> arrDiagCie10) {
        this.arrDiagCie10 = arrDiagCie10;
    }

    public String getVisibilidadServicios() {
        return sVisibilidadServicios;
    }

    public void setVisibilidadServicios(String sVisibilidadServicios) {
        this.sVisibilidadServicios = sVisibilidadServicios;
    }

    public String getOcultarBusqueda2() {
        return sOcultarBusqueda2;
    }

    public void setOcultarBusqueda2(String sOcultarBusqueda2) {
        this.sOcultarBusqueda2 = sOcultarBusqueda2;
    }

    public String getOcultarBusqueda1() {
        return sOcultarBusqueda1;
    }

    public void setOcultarBusqueda1(String sOcultarBusqueda1) {
        this.sOcultarBusqueda1 = sOcultarBusqueda1;
    }

    public ArrayList<ProcedimientosRealizados> getProcRealPac() {
        return arrProcRealPac;
    }

    public void setProcRealPac(ArrayList<ProcedimientosRealizados> arrProcRealPac) {
        this.arrProcRealPac = arrProcRealPac;
    }

    public String getNotaIngreso() {
        return sNotaIngreso;
    }

    public void setNotaIngreso(String sNotaIngreso) {
        this.sNotaIngreso = sNotaIngreso;
    }

    public String getNotaEvolucion() {
        return sNotaEvolucion;
    }

    public void setNotaEvolucion(String sNotaEvolucion) {
        this.sNotaEvolucion = sNotaEvolucion;
    }

    public ProcedimientosRealizados[] getProcRealMatOst() {
        return arrProcRealMatOst;
    }

    public void setProcRealMatOst(ProcedimientosRealizados[] arrProcRealMatOst) {
        this.arrProcRealMatOst = arrProcRealMatOst;
    }

    public ArrayList<ProcedimientosRealizados> getProcRealActual() {
        return arrProcRealActual;
    }

    public void setProcRealActual(ArrayList<ProcedimientosRealizados> arrProcRealActual) {
        this.arrProcRealActual = arrProcRealActual;
    }

    public ArrayList<AfeccionTratada> getAfecTratPac() {
        return arrAfecTratPac;
    }

    public void setAfecTratPac(ArrayList<AfeccionTratada> arrAfecTratPac) {
        this.arrAfecTratPac = arrAfecTratPac;
    }

    public AfeccionTratada getAfecTrat() {
        return oAfecTrat;
    }

    public void setAfecTrat(AfeccionTratada oAfecTrat) {
        this.oAfecTrat = oAfecTrat;
    }

    public ArrayList<SignosVitales> getSignVitActual() {
        return arrSignVitActual;
    }

    public void setSignVitActual(ArrayList<SignosVitales> arrSignVitActual) {
        this.arrSignVitActual = arrSignVitActual;
    }

    public SignosVitales getSignVit() {
        return oSignVit;
    }

    public void setSignVit(SignosVitales oSignVit) {
        this.oSignVit = oSignVit;
    }

    public int getClaveMantener() {
        return sClaveMantener;
    }

    public void setClaveMantener(int sClaveMantener) {
        this.sClaveMantener = sClaveMantener;
    }

    public ArrayList<NotaMedicaHRRB> getMotivoRef() {
        return arrMotivoRef;
    }

    public void setMotivoRef(ArrayList<NotaMedicaHRRB> arrMotivoRef) {
        this.arrMotivoRef = arrMotivoRef;
    }

    public StreamedContent getCatalogoLab() {

        
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                .getContext()).getResourceAsStream("/resources/estudios/laboratorio/CatalogodeLaboratorioHRRB.pdf");
        scCatalogoLab = new DefaultStreamedContent(stream, "application/pdf", "CatalogodeLaboratorioHRRB.pdf");
        return scCatalogoLab;
    }

    public void setCatalogoLab(StreamedContent scCatalogoLab) {
        this.scCatalogoLab = scCatalogoLab;
    }

    public ArrayList<EstudioRealizado> getOrdServPrev() {
        return arrOrdServPrev;
    }

    public void setOrdServPrev(ArrayList<EstudioRealizado> arrOrdServPrev) {
        this.arrOrdServPrev = arrOrdServPrev;
    }

    public ServicioRealizado getServReal() {
        return oServReal;
    }

    public void setServReal(ServicioRealizado oServReal) {
        this.oServReal = oServReal;
    }

    public String getObj1() {
        return sObj1;
    }

    public void setObj1(String sObj1) {
        this.sObj1 = sObj1;
    }

    public String getObj2() {
        return sObj2;
    }

    public void setObj2(String sObj2) {
        this.sObj2 = sObj2;
    }

    public String getObj3() {
        return sObj3;
    }

    public void setObj3(String sObj3) {
        this.sObj3 = sObj3;
    }

    public String getObj4() {
        return sObj4;
    }

    public void setObj4(String sObj4) {
        this.sObj4 = sObj4;
    }

    public String getBotonNota() {
        return sBotonNota;
    }

    public void setBotonNota(String sBotonNota) {
        this.sBotonNota = sBotonNota;
    }
    
    public String getRender() {
        return bRender;
    }

    public void setRender(String bRender) {
        this.bRender = bRender;
    }

    public StreamedContent getResultadoEstudios() {
        return scResultadoEstudios;
    }

    public void setResultadoEstudios(StreamedContent scResultadoEstudios) {
        this.scResultadoEstudios = scResultadoEstudios;
    }

    public Date getFechaIni() {
        return dFechaIni;
    }

    public void setFechaIni(Date dFechaIni) {
        this.dFechaIni = dFechaIni;
    }

    public Date getFechaFin() {
        return dFechaFin;
    }

    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

    public ArrayList<EstudioRealizado> getConsultaResultEst() {
        return arrConsultaResultEst;
    }

    public void setConsultaResultEst(ArrayList<EstudioRealizado> arrConsultaResultEst) {
        this.arrConsultaResultEst = arrConsultaResultEst;
    }

    public Parametrizacion[] getMedCont() {
        return arrMedCont;
    }

    public void setMedCont(Parametrizacion[] arrMedCont) {
        this.arrMedCont = arrMedCont;
    }

    public String getVisibilidadDiagnostico() {
        return sVisibilidadDiagnostico;
    }

    public void setVisibilidadDiagnostico(String sVisibilidadDiagnostico) {
        this.sVisibilidadDiagnostico = sVisibilidadDiagnostico;
    }

    public String getVisibilidadDescargaResult() {
        return sVisibilidadDescargaResult;
    }

    public void setVisibilidadDescargaResult(String sVisibilidadDescargaResult) {
        this.sVisibilidadDescargaResult = sVisibilidadDescargaResult;
    }

    public Date getFechaIniOrd() {
        return dFechaIniOrd;
    }

    public void setFechaIniOrd(Date dFechaIniOrd) {
        this.dFechaIniOrd = dFechaIniOrd;
    }

    public Date getFechaFinOrd() {
        return dFechaFinOrd;
    }

    public void setFechaFinOrd(Date dFechaFinOrd) {
        this.dFechaFinOrd = dFechaFinOrd;
    }

    public String getVisibilidadOrdPrev() {
        return sVisibilidadOrdPrev;
    }

    public void setVisibilidadOrdPrev(String sVisibilidadOrdPrev) {
        this.sVisibilidadOrdPrev = sVisibilidadOrdPrev;
    }

    public Date getFechaIniAuxResPrev() {
        return dFechaIniAuxResPrev;
    }

    public void setFechaIniAuxResPrev(Date dFechaIniAuxResPrev) {
        this.dFechaIniAuxResPrev = dFechaIniAuxResPrev;
    }

    public Date getFechaFinAuxResPrev() {
        return dFechaFinAuxResPrev;
    }

    public void setFechaFinAuxResPrev(Date dFechaFinAuxResPrev) {
        this.dFechaFinAuxResPrev = dFechaFinAuxResPrev;
    }

    public ArrayList<EstudioRealizado> getConsultaResultEstPrev() {
        return arrConsultaResultEstPrev;
    }

    public void setConsultaResultEstPrev(ArrayList<EstudioRealizado> arrConsultaResultEstPrev) {
        this.arrConsultaResultEstPrev = arrConsultaResultEstPrev;
    }

    public String getVisibilidadResultEstPrev() {
        return sVisibilidadResultEstPrev;
    }

    public void setVisibilidadResultEstPrev(String sVisibilidadResultEstPrev) {
        this.sVisibilidadResultEstPrev = sVisibilidadResultEstPrev;
    }

    public String getVisibilidadIndicTerap() {
        return sVisibilidadIndicTerap;
    }

    public void setVisibilidadIndicTerap(String sVisibilidadIndicTerap) {
        this.sVisibilidadIndicTerap = sVisibilidadIndicTerap;
    }

    public String getArea1() {
        return sArea1;
    }

    public void setArea1(String sArea1) {
        this.sArea1 = sArea1;
    }

    public String getArea2() {
        return sArea2;
    }

    public void setArea2(String sArea2) {
        this.sArea2 = sArea2;
    }

    public String getArea3() {
        return sArea3;
    }

    public void setArea3(String sArea3) {
        this.sArea3 = sArea3;
    }

    public String[] getSeguroDesglosado() {
        return sSeguroDesglosado;
    }

    public void setSeguroDesglosado(String[] sSeguroDesglosado) {
        this.sSeguroDesglosado = sSeguroDesglosado;
    }

    public String getVisibilidadProcedimientos() {
        return sVisibilidadProcedimientos;
    }

    public void setVisibilidadProcedimientos(String sVisibilidadProcedimientos) {
        this.sVisibilidadProcedimientos = sVisibilidadProcedimientos;
    }
    
    public ArrayList<EstudioRealizado> getEstuOto() {
        return arrEstuOto;
    }

    public void setEstuOto(ArrayList<EstudioRealizado> arrEstuOto) {
        this.arrEstuOto = arrEstuOto;
    }
}
