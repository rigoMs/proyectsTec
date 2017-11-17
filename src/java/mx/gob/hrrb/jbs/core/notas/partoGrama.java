package mx.gob.hrrb.jbs.core.notas;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.MetodoAnticonceptivo;
import mx.gob.hrrb.modelo.core.NotaEmbarazo;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Producto;
import mx.gob.hrrb.modelo.core.SignosVitales;
import mx.gob.hrrb.modelo.core.notas.Alumbramiento;
import mx.gob.hrrb.modelo.core.notas.AnalgesiaTparto;
import mx.gob.hrrb.modelo.core.notas.Cesarea;
import mx.gob.hrrb.modelo.core.notas.EvolucionPartoLegrado;
import mx.gob.hrrb.modelo.core.notas.Forceps;
import mx.gob.hrrb.modelo.core.notas.Legrado;
import mx.gob.hrrb.modelo.core.notas.NotaPostOclusionTubariaPostparto;
import mx.gob.hrrb.modelo.core.notas.NotaPreoperatoria;
import mx.gob.hrrb.modelo.core.notas.PartoEutocico;
import mx.gob.hrrb.modelo.core.notas.PartoGrama;
import mx.gob.hrrb.modelo.core.notas.Pelvis;
import mx.gob.hrrb.modelo.core.notas.Placenta;
import mx.gob.hrrb.modelo.core.notas.SeguimientoTrabajoParto;
import mx.gob.hrrb.modelo.core.notas.PrimeraMitadEmbarazo;
import mx.gob.hrrb.modelo.core.notas.SegundaMitadEmbarazo;
import mx.gob.hrrb.modelo.core.notas.TerminacionEmbarazo;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Alberto
 */
@ManagedBean (name = "partoGrama")
@ViewScoped
public class partoGrama implements Serializable{
    private PartoGrama oPartoGrama;
    private Date fechaActual;
    private Date horaActual;
    private boolean muestrapantalla;
    private boolean bandera00;
    private boolean bHoraControl0;
    private boolean bHoraControl1;
    private boolean bHoraControl2;
    private boolean bHoraControl3;
    private boolean bHoraControl4;
    private boolean bHoraControl5;
    private boolean bHoraControl6;
    private boolean bHoraControl7;
    private boolean bHoraControl8;
    private boolean bHoraControl9;
    private boolean bHoraControl10;
    private boolean bHoraControl11;
    private boolean bHoraControl12;
    private boolean bHoraControl13;
    private boolean bHoraControl14;
    private boolean consiente;
    private String sDolor;
    private String sDolor1; 
    private String sMotivoConsulta;
    private boolean valorCuello0;
    private boolean valorCuello1;
    private boolean valorCuello2;
    private boolean valorCuello3;
    private boolean longitud0;
    private boolean longitud1;
    private boolean longitud2;
    private boolean longitud3;
    private boolean longitud4;
    private boolean bHabilita;
    private Date fechaTemporal;
    private Date fechaSegundaMitadEmbarazo;
    private Date horaSegundaMitadEmbarazo;
    private SeguimientoTrabajoParto oSeguimientoTrabajoParto;
    private PrimeraMitadEmbarazo oPrimeraMitadEmbarazo;
    private SegundaMitadEmbarazo oSegundaMitadEmbarazo;
    private SignosVitales oRegistro1;
    private SignosVitales oRegistro2;
    private SignosVitales oRegistro3;
    private SignosVitales oRegistro4;
    private SignosVitales oRegistro5;
    private SignosVitales oRegistro6;
    private SignosVitales oRegistro7;
    private String temporal;    
    private Pelvis oPelvis;
    private DiagnosticoCIE10 oCie10;
    private ArrayList<DiagnosticoCIE10> arrDiagCie10;
    private DiagnosticoCIE10 oDx;
    private ArrayList<DiagnosticoCIE10> arrDx;
    private DiagnosticoCIE10 arrRet[];       
    private boolean hnacimiento;
    private Date fechaNacimiento;
    private Date horaNacimiento;
    private Producto oProducto;
    private ArrayList<Producto> arrProducto;
    private PartoEutocico oPartoEutocico;
    private Forceps oForceps;
    private Cesarea oCesarea;
    private Alumbramiento oAlumbramiento;
    private AnalgesiaTparto oAnalgesiaTparto;
    private AnalgesiaTparto oPartoLegrado;
    private Placenta oPlacenta;
    private boolean bReanimacion;
    private Legrado oLegrado;
    private boolean horaLegrado;
    private boolean bdesgarroPerine;
    private boolean bdesgarroVagina;
    private boolean bdesgarroCervix;
    private ArrayList<EvolucionPartoLegrado> arrEvolucion;
    private EvolucionPartoLegrado oEvolucion;
    private NotaPreoperatoria oNotaPreoperatoria;
    private boolean bHoraPreoperatoria;
    private Date dFPreoperatoria;
    private Date dHPreoperatoria;
    private NotaPostOclusionTubariaPostparto npotp;
    private boolean bBandera;
    private Date fecha;
    private Date hora;
    private boolean bHabilitaMedico;    
    private PartoGrama oConsultaPartoGrama;
    private PartoGrama oConsultapartograma;
    private PartoGrama oConsultaDatosPartoGrama;
    private boolean bMuestraConsulta;
    private PrimeraMitadEmbarazo oDetallePrimeraMitadEmbarazo;
    private long fpaciente, clave;
    private SegundaMitadEmbarazo oDetalleSegundaMitadEmbarazo;
    private SeguimientoTrabajoParto oDetalleSeguimiento;
    private SeguimientoTrabajoParto[] arrDetalleSeguimientoSignosvitales;
    private PartoGrama[] datosPartoGrama;
    private Paciente oPaciente;
    private Paciente[] datosPaciente;
    private ArrayList<Producto> arrDetalleProducto;
    private boolean bBoton;
    private Date dFecha1;
    private boolean bTab1;
    private boolean bTab2;
    private boolean bTab3;
    private boolean bTab4;
    private String sPulso1;
    private String sPulso2;
    private String sPulso3;
    private String sPulso4;
    private String sPulso5;
    private String sPulso6;
    private String sPulso7;
    private boolean bHabilitaCompletePostoperatorio;
    private boolean bComplete2;
    private boolean bAlumbramientoMotivo;
    private boolean bHisterectomia;
    private boolean bAnalgesia1;
    private boolean bAnalgesia2;
    private boolean bDescribir1;
    private boolean bDescribir2;
    private boolean bPartoEutocicoCesarea;
    private String sNombreMedEvolucion;
    private String sCedula;
    private int nTarjeta;
    public partoGrama() {
        bandera00 = true;
        bHoraControl0 = true;
        bHoraControl1 = true;
        bHoraControl2 = true;
        bHoraControl3 = true;
        bHoraControl4 = true;
        bHoraControl5 = true;
        bHoraControl6 = true;
        bHoraControl7 = true;
        bHoraControl8 = true;
        bHoraControl9 = true;
        bHoraControl10 = true;
        bHoraControl11 = true;
        bHoraControl12 = true;
        bHoraControl13 = true;
        bHoraControl14 = true;
        bHabilita = true;
        oRegistro1 = new SignosVitales();
        oRegistro2 = new SignosVitales();
        oRegistro3 = new SignosVitales();
        oRegistro4 = new SignosVitales();
        oRegistro5 = new SignosVitales();
        oRegistro6 = new SignosVitales();
        oRegistro7 = new SignosVitales();        
        oCie10 = new DiagnosticoCIE10();
        arrDiagCie10 = new ArrayList<DiagnosticoCIE10>();
        oDx = new DiagnosticoCIE10();
        arrDx = new ArrayList<DiagnosticoCIE10>();
        hnacimiento = true;
        horaLegrado = true;
        arrEvolucion = new ArrayList<EvolucionPartoLegrado>();
        bHoraPreoperatoria = true;
        bBandera = true;
        bHabilitaMedico = true;
        arrProducto = new ArrayList<Producto>();        
        oConsultaPartoGrama = new PartoGrama();
        oConsultapartograma = new PartoGrama();
        oPaciente = new Paciente();
        arrDetalleProducto = null;
        this.dFecha1 = new Date();
        this.oEvolucion = new EvolucionPartoLegrado();
        this.oProducto = new Producto();
        this.oProducto.setSexoProducto(new Parametrizacion());
        this.oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
        this.oSeguimientoTrabajoParto = new SeguimientoTrabajoParto();
        this.bTab1 = true;
        this.oPelvis = new Pelvis();
        this.bAlumbramientoMotivo = true;
        this.bHisterectomia = true;
        this.bAnalgesia1 = true;
        this.bAnalgesia2 = true;
        this.bDescribir1 = true;
        this.bDescribir2 = true;
    }
    /********INICIAN METODOS DE CONTROL DE DATOS****************/
    public void requerir(TabChangeEvent event){
        if(event.getTab().getId().compareTo("tab1") == 0){            
            this.bTab1 = true;
            this.bTab2 = false;
            this.bTab3 = false;
            this.bTab4 = false;
        }else if(event.getTab().getId().compareTo("tab2") == 0){            
            this.bTab2 = true;
            this.bTab1 = false;
            this.bTab3 = false;
            this.bTab4 = false;
        }else if(event.getTab().getId().compareTo("tab3") == 0){            
            this.bTab3 = true;
            this.bTab1 = false;
            this.bTab2 = false;
            this.bTab4 = false;
        }else if(event.getTab().getId().compareTo("tab4") == 0){            
            this.bTab4 = true;
            this.bTab1 = false;
            this.bTab2 = false;
            this.bTab3 = false;
        }        
    }
    public void metodoCargaDatosGeneral(Paciente opaciente, short opc, PartoGrama oparto){
        try{            
            if(opc == 0){
                this.muestrapantalla = false;
                this.oPartoGrama = new PartoGrama();
                this.oPartoGrama.getEpiMed().setPaciente(opaciente);
                this.datosPartoGrama = this.oPartoGrama.buscaPacienteDatos();
                if(this.datosPartoGrama[0].getMaxConsecutivo() == 0){
                    this.bBoton = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","LA PACIENTE NO HA SIDO VALORADA EN TRIAGE, GENERE SU VALORACION DE TRIAGE LO MAS PRONTO POSIBLE"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }else
                    this.bBoton = false;                    
            }else if(opc == 1){
                if(oparto.getConsecutivo() == 0 && oparto.getNpartograma() ==0 && oparto.getMaxConsecutivo() != 0){                                       
                    this.oPartoGrama = new PartoGrama();                    
                    this.setPartoGrama(oparto);
                    this.oPartoGrama = this.oPartoGrama.cargarDatos(oparto.getEpiMed().getPaciente().getFolioPaciente(), oparto.getEpiMed().getPaciente().getClaveEpisodio());                    
                    this.oPartoGrama.getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oPartoGrama.getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oPartoGrama.setConsecutivo(oparto.getConsecutivo());
                    this.oPartoGrama.setNpartograma(oparto.getNpartograma());
                    this.oPartoGrama.setMaxConsecutivo(oparto.getMaxConsecutivo());
                    this.oPrimeraMitadEmbarazo = new PrimeraMitadEmbarazo();
                    this.oPrimeraMitadEmbarazo.setPartoGrama(oparto);
                    this.oSegundaMitadEmbarazo = new SegundaMitadEmbarazo();
                    this.oSegundaMitadEmbarazo.setPartoGrama(oparto);
                    this.oSeguimientoTrabajoParto = new SeguimientoTrabajoParto();                    
                    this.oSeguimientoTrabajoParto.setPartoGrama(oparto);
                    this.oSeguimientoTrabajoParto.setPelvis(new ArrayList<Pelvis>());
                    this.oSeguimientoTrabajoParto.getPelvis().add(oPelvis);
                    this.oPartoEutocico = new PartoEutocico();                    
                    this.oPartoEutocico.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oForceps = new Forceps();
                    this.oForceps.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oCesarea = new Cesarea();
                    this.oCesarea.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oAlumbramiento = new Alumbramiento();
                    this.oAlumbramiento.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oAnalgesiaTparto = new AnalgesiaTparto();
                    this.oAnalgesiaTparto.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oPartoLegrado = new AnalgesiaTparto();
                    this.oPartoLegrado.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oPlacenta = new Placenta();
                    this.oPlacenta.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oLegrado = new Legrado();
                    this.oLegrado.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oProducto = new Producto();
                    this.oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
                    this.oProducto.setSexoProducto(new Parametrizacion());
                    this.oProducto.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oEvolucion = new EvolucionPartoLegrado();
                    this.oEvolucion.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oNotaPreoperatoria = new NotaPreoperatoria();
                    this.oNotaPreoperatoria.getTerminacionEmbarazo().setPartoGrama(oparto);
                    this.oNotaPreoperatoria.getDiagnosticoPreoperatorio().setClave("");
                    this.npotp = new NotaPostOclusionTubariaPostparto();
                    this.npotp.getTerminacionEmbarazo().setPartoGrama(oparto);   
                    this.npotp.getTerminacionEmbarazo().setNotaPreoperatoria(new NotaPreoperatoria());
                    this.npotp.getTerminacionEmbarazo().getNotaPreoperatoria().getDiagnosticoPreoperatorio().setClave("");
                    this.muestrapantalla = true;
                }else{                    
                    this.oPartoGrama = new PartoGrama();
                    this.setPartoGrama(oparto);
                    this.oPartoGrama = this.oPartoGrama.cargarDatos(oparto.getEpiMed().getPaciente().getFolioPaciente(), oparto.getEpiMed().getPaciente().getClaveEpisodio());                
                    this.oPartoGrama.getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oPartoGrama.getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oPartoGrama.setConsecutivo(oparto.getConsecutivo());
                    this.oPartoGrama.setNpartograma(oparto.getNpartograma());
                    this.oPartoGrama.setMaxConsecutivo(oparto.getMaxConsecutivo());
                    this.oPartoGrama.getFechaRegistro();
                    this.setFecha(this.oPartoGrama.getFechaRegistro()==null ? new Date() : this.oPartoGrama.getFechaRegistro());
                    this.setHoraActual(this.oPartoGrama.getFechaRegistro() == null ? new Date() : this.oPartoGrama.getFechaRegistro());
                    this.asignaFecha(null);
                    this.setMotivoConsulta(this.oPartoGrama.getMotivoConsulta().getMotivoAtencion());
                    this.asignaMotivoConsulta();
                    this.oPrimeraMitadEmbarazo = new PrimeraMitadEmbarazo();
                    this.oPrimeraMitadEmbarazo.setPartoGrama(oparto);
                    this.oPrimeraMitadEmbarazo = this.oPrimeraMitadEmbarazo.cargaDetallePaciente();
                    this.asignaDatosPrimeraMitadEmbarazo();                    
                    this.oSegundaMitadEmbarazo = new SegundaMitadEmbarazo();
                    this.oSegundaMitadEmbarazo.setPartoGrama(oparto);
                    this.oSegundaMitadEmbarazo = this.oSegundaMitadEmbarazo.cargaDetallePacienteSegundaMitadEmbarazo();
                    this.setDolor1(this.oSegundaMitadEmbarazo.getDolorIntesidad() == 0 ? "" : this.oSegundaMitadEmbarazo.getDolorIntesidad() + "");                    
                    this.asignaDolor1();
                    this.fechaSegundaMitadBase();                    
                    this.setValorCuello0(this.oSegundaMitadEmbarazo.getCuelloPosterior().getValor().compareTo("POSTERIOR") == 0);
                    this.setValorCuello1(this.oSegundaMitadEmbarazo.getCuelloCentral().getValor().compareTo("CENTRAL") == 0);
                    this.setValorCuello2(this.oSegundaMitadEmbarazo.getCuelloResistente().getValor().compareTo("RESISTENTE") == 0);
                    this.setValorCuello3(this.oSegundaMitadEmbarazo.getCuelloBlando().getValor().compareTo("BLANDO") == 0);
                    this.asignaCuello0();
                    this.asignaCuello1();
                    this.asignaCuello2();
                    this.asignaCuello3();
                    this.setLongitud0(this.oSegundaMitadEmbarazo.getLongitud0().getValor().compareTo("3 CMS") == 0);
                    this.setLongitud1(this.oSegundaMitadEmbarazo.getLongitud1().getValor().compareTo("2 CMS") == 0);
                    this.setLongitud2(this.oSegundaMitadEmbarazo.getLongitud2().getValor().compareTo("1 CM") == 0);
                    this.setLongitud3(this.oSegundaMitadEmbarazo.getLongitud3().getValor().compareTo("0.5 CMS") == 0);
                    this.setLongitud4(this.oSegundaMitadEmbarazo.getLongitud4().getValor().compareTo("CON DESGARROS") == 0);
                    this.asignaLongitud0();
                    this.asignaLongitud1();
                    this.asignaLongitud2();
                    this.asignaLongitud3();
                    this.asignaLongitud4();
                    this.asignaCedula1();
                    this.asignaCedula2();
                    this.oSeguimientoTrabajoParto = new SeguimientoTrabajoParto();                    
                    this.oSeguimientoTrabajoParto.setPartoGrama(oparto);
                    this.arrDetalleSeguimientoSignosvitales = oSeguimientoTrabajoParto.cargaSignosVitales();
                    this.asignaSignosVitales();                    
                    this.oSeguimientoTrabajoParto = oSeguimientoTrabajoParto.cargaDetallePacienteSeguimientoParto();
                    this.asignaPelvisBD();
                    this.habilitaHoras();
                    this.asignaCedula3();
                    this.oPartoEutocico = new PartoEutocico();
                    this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.oPartoEutocico.detallePartoEutocico();
                    this.asignaDesgarro();
                    this.oForceps = new Forceps();
                    this.oForceps.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oForceps.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oForceps.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oForceps.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.oForceps.buscaDetalleForceps();
                    this.oCesarea = new Cesarea();
                    this.oCesarea.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oCesarea.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oCesarea.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oCesarea.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.oCesarea.detalleCesarea();
                    this.habilitaPartoEutocicoCesarea();                    
                    this.oAlumbramiento = new Alumbramiento();
                    this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());                    
                    this.oAlumbramiento.detalleAlumbramiento();                    
                    this.oAnalgesiaTparto = new AnalgesiaTparto();
                    this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.oAnalgesiaTparto.detalleAnalgesia();
                    this.banderaAnalgesiaTipo1();
                    this.oPartoLegrado = new AnalgesiaTparto();
                    this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.oPartoLegrado.detalleAnalgesiaLegrado();
                    this.banderaAnalgesiaTipo2();
                    this.habilitaMedicoAnalgesia();
                    this.asignaCedula5();
                    this.oPlacenta = new Placenta();
                    this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.oPlacenta.detallePaciente((short) 0);
                    this.oLegrado = new Legrado();
                    this.oLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oLegrado.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oLegrado.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.oLegrado.detalleLegrado();
                    this.asignaCedula6();
                    this.asignaFechaLegrado();
                    this.oProducto = new Producto();
                    this.oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
                    this.oProducto.setSexoProducto(new Parametrizacion());
                    this.oProducto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oProducto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oProducto.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oProducto.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.arrProducto = oProducto.detalleProducto((short) 0);
                    this.asignacedula10();
                    this.oEvolucion = new EvolucionPartoLegrado();
                    this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());                    
                    this.arrEvolucion = this.oEvolucion.detalleEvolucionPartoLegrado();                    
                    this.asignaMedicoEvolucion();
                    this.oNotaPreoperatoria = new NotaPreoperatoria();
                    this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.oNotaPreoperatoria.detalleNotaPreoperatoria();
                    this.asignatipocirugia();                    
                    this.asignaCedula8();                                        
                    this.asignaFPreoperatoria();
                    this.habilitaComplete2();
                    this.npotp = new NotaPostOclusionTubariaPostparto();
                    this.npotp.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(oparto.getEpiMed().getPaciente().getFolioPaciente());
                    this.npotp.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(oparto.getEpiMed().getPaciente().getClaveEpisodio());
                    this.npotp.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(oparto.getConsecutivo());
                    this.npotp.getTerminacionEmbarazo().getPartoGrama().setNpartograma(oparto.getNpartograma());
                    this.npotp.detalleNotaPostOclusionTubariaPostParto((short) 1);                                    
                    this.asignaCedula9();                    
                    this.asignaFechaPost();
                    this.habilitaComplete3();
                    this.muestrapantalla = true;
                }                
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cargaDatos(long fpaciente, long clave, int nexpediente, int consecutivo, long npartograma){
        try{
            
            this.fpaciente = fpaciente;
            this.clave = clave;
            this.oConsultapartograma = oConsultapartograma.cargarDatos(fpaciente, clave);
            this.oConsultaDatosPartoGrama = new PartoGrama();
            this.oConsultaDatosPartoGrama.getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oConsultaDatosPartoGrama.getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oConsultaDatosPartoGrama.getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oConsultaDatosPartoGrama.setConsecutivo(consecutivo);
            this.oConsultaDatosPartoGrama.setNpartograma(npartograma);            
            this.oConsultaDatosPartoGrama = oConsultaDatosPartoGrama.CargaDetallePaciente();
            this.oDetallePrimeraMitadEmbarazo = new PrimeraMitadEmbarazo();
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().setConsecutivo(consecutivo);
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().setNpartograma(npartograma);
            this.oDetallePrimeraMitadEmbarazo = oDetallePrimeraMitadEmbarazo.cargaDetallePaciente();
            this.oDetalleSegundaMitadEmbarazo = new SegundaMitadEmbarazo();
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().setConsecutivo(consecutivo);
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().setNpartograma(npartograma);
            this.oDetalleSegundaMitadEmbarazo = oDetalleSegundaMitadEmbarazo.cargaDetallePacienteSegundaMitadEmbarazo();
            this.oDetalleSeguimiento = new SeguimientoTrabajoParto();
            this.oDetalleSeguimiento.getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oDetalleSeguimiento.getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oDetalleSeguimiento.getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oDetalleSeguimiento.getPartoGrama().setConsecutivo(consecutivo);
            this.oDetalleSeguimiento.getPartoGrama().setNpartograma(npartograma);
            this.arrDetalleSeguimientoSignosvitales = oDetalleSeguimiento.cargaSignosVitales();
            this.oDetalleSeguimiento = oDetalleSeguimiento.cargaDetallePacienteSeguimientoParto();            
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.arrDetalleProducto = oProducto.detalleProducto((short) 1);
            this.oPartoEutocico = new PartoEutocico();
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oPartoEutocico.detallePartoEutocico();
            this.oForceps = new Forceps();
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oForceps.buscaDetalleForceps();
            this.oCesarea = new Cesarea();
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oCesarea.detalleCesarea();
            this.oAlumbramiento = new Alumbramiento();
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oAlumbramiento.detalleAlumbramiento();
            this.oAnalgesiaTparto = new AnalgesiaTparto();
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oAnalgesiaTparto.detalleAnalgesia();
            this.oPartoLegrado = new AnalgesiaTparto();
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oPartoLegrado.detalleAnalgesiaLegrado();
            this.oPlacenta = new Placenta();
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oPlacenta.detallePaciente((short) 1);
            this.oLegrado = new Legrado();
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oLegrado.detalleLegrado();
            this.oEvolucion = new EvolucionPartoLegrado();
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.arrEvolucion = oEvolucion.detalleEvolucionPartoLegrado();                        
            this.oNotaPreoperatoria = new NotaPreoperatoria();
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oNotaPreoperatoria.detalleNotaPreoperatoria();
            this.npotp = new NotaPostOclusionTubariaPostparto();
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nexpediente);
            this.npotp.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.npotp.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.npotp.detalleNotaPostOclusionTubariaPostParto((short) 0);
            boolean bandera = this.oConsultaDatosPartoGrama != null;
            if(bandera){                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","DATOS DE LA PACIENTE " + 
                        oConsultapartograma.getEpiMed().getPaciente().getNombreCompleto() + " CARGADOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");                
            }
            setMuestraConsultaPartoGrama(bandera);
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public void DatosPartograma(long fpaciente, long clavepisodio)throws Exception{
        if(fpaciente != 0 && clavepisodio!= 0){
            PartoGrama pGrama = new PartoGrama();
            oPartoGrama = pGrama.cargarDatos(fpaciente, clavepisodio);
            if(oPartoGrama != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","PACIENTE ENCONTRADO :)"));
                setMuestraPantalla(true);
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","LA PACIENTE NO HA SIDO VALORADA EN TRIAGE, GENERE SU VALORACION DE TRIAGE LO MAS PRONTO POSIBLE"));
                setMuestraPantalla(false);
            }
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","LO SIENTO NO ENCUENTRO EL PACIENTE :("));
            setMuestraPantalla(false);
        }
    }
    public List<Parametrizacion> getListMotivoConsulta(){
        try{
            List<Parametrizacion> listaConsulta = null;
            return listaConsulta = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 8))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListSitio(){
        try{
            List<Parametrizacion> listaSitio = null;
            return listaSitio = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 9)))); 
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListPelvis(){
        try{
            List<Parametrizacion> listaPelvis = null;
            return listaPelvis = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 10))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Medico> getListMedicoPartoGrama(){
        try{
            List<Medico> listaMedicoPartoGrama = null;
            return listaMedicoPartoGrama = new ArrayList<Medico>(Arrays.asList((new PartoGrama().buscaMedicos((short) 1))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListVariedadPosicion(){
        try{
            List<Parametrizacion> listaVariedadPosicion = null;
            return listaVariedadPosicion = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 11))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListCesarea(){
        try{
            List<Parametrizacion> listaCesarea = null;
            return listaCesarea = new  ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 12))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListaAlumbramiento(){
        try{
            List<Parametrizacion> listAlumbramiento = null;
            return listAlumbramiento = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 13))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListaRecienNacido(){
        try{
            List<Parametrizacion> listaRecieNacido = null;
            return listaRecieNacido = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 14))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListSexoRecienNacido(){
        try{
            List<Parametrizacion> listaSexoRecienNacido = null;
            return listaSexoRecienNacido = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 15))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListCordon(){
        try{
            List<Parametrizacion> listaCordon = null;
            return listaCordon = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 16))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListCordon1(){
        try{
            List<Parametrizacion> listaCordon1 = null;
            return listaCordon1 = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 17))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListTipoLegrado(){
        try{
            List<Parametrizacion> listaTipoLegrado = null;
            return listaTipoLegrado = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 18))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListTipoCirugia(){
        try{
            List<Parametrizacion> listaCirugia = null;
            return listaCirugia = new ArrayList<Parametrizacion>(Arrays.asList((new PartoGrama().buscaValorParametrizacion((short) 19))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<MetodoAnticonceptivo> getListMetodoAnticonceptivo(){
        try{
            List<MetodoAnticonceptivo> listaMetodo = null;
            return listaMetodo = new ArrayList<MetodoAnticonceptivo>(Arrays.asList((new PartoGrama().buscaMetodoTriage())));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public long CalculaAmenorrea(){
        if(oPrimeraMitadEmbarazo.getFecha() != null){
            long resultado = oPrimeraMitadEmbarazo.getFecha().getTime() - oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion().getTime();
            long semanas = resultado / 604800000;
            oPrimeraMitadEmbarazo.setSemanasAmerorrea((int)semanas);
            return semanas;
        }
        return 0;
    }
    public long CalculaAmenorrea1(){
        if(getFechaSegundaMitadEmbarazo() == null)
            return 0;
        else{
            bHabilita = false;            
            long resultado = this.getFechaSegundaMitadEmbarazo().getTime() - this.oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion().getTime();
            long semanas = resultado / 604800000;
            oSegundaMitadEmbarazo.setSemanasAmerorrea((int) semanas);            
            return semanas;
        }
    }
    public void ControlComponentes(SelectEvent event){
        bandera00 = false;
        bHoraControl13 = true;
        bHoraControl14 = true; 
    }
    public void HControl0(SelectEvent event){
        bHoraControl14 = true;
        bHoraControl0 = false;
        bHoraControl1 = true;
    }
    public void HControl1(SelectEvent event){
        bandera00 = true;
        bHoraControl0 = true;
        bHoraControl1 = false;
        bHoraControl2 = true;
    }
    public void HControl2(SelectEvent event){        
        bHoraControl1 = true;
        bHoraControl2 = false;
        bHoraControl3 = true;
    }
    public void HControl3(SelectEvent event){        
        bHoraControl2 = true;
        bHoraControl3 = false;
        bHoraControl4 = true;
    }
    public void HControl4(SelectEvent event){        
        bHoraControl3 = true;
        bHoraControl4 = false;
        bHoraControl5 = true;
    }
    public void HControl5(SelectEvent event){
        bHoraControl4 = true;
        bHoraControl5 = false;
        bHoraControl6 = true;
    }
    public void HControl6(SelectEvent event){        
        bHoraControl5 = true;
        bHoraControl6 = false;
        bHoraControl7 = true;
    }
    public void HControl7(SelectEvent event){        
        bHoraControl6 = true;
        bHoraControl7 = false;
        bHoraControl8 = true;
    }
    public void HControl8(SelectEvent event){
        bHoraControl7 = true;
        bHoraControl8 = false;
        bHoraControl9 = true;
    }
    public void HControl9(SelectEvent event){
        bHoraControl8 = true;
        bHoraControl9 = false;
        bHoraControl10 = true;
    }
    public void HControl10(SelectEvent event){
        bHoraControl9 = true;
        bHoraControl10 = false;
        bHoraControl11 = true;
    }
    public void HControl11(SelectEvent event){
        bHoraControl10 = true;
        bHoraControl11 = false;
        bHoraControl12 = true;
    }
    public void HControl12(SelectEvent event){
        bHoraControl11 = true;
        bHoraControl12 = false;
        bHoraControl13 = true;
    }
    public void HControl13(SelectEvent event){
        bHoraControl12 = true;
        bHoraControl13 = false;
        bHoraControl14 = true;
    }
    public void HControl14(SelectEvent event){
        bHoraControl13 = true;
        bHoraControl14 = false;
        bHoraControl0 = true;
    }
    public void asignaDolor(){        
        if(this.sDolor.compareTo("") != 0)
            oPrimeraMitadEmbarazo.setDolorIntesidad(Integer.parseInt(sDolor));
        else
            this.oPrimeraMitadEmbarazo.setDolorIntesidad(0);
    }
    public void asignaDolor1(){
        if(this.sDolor1.compareTo("") != 0)
            oSegundaMitadEmbarazo.setDolorIntesidad(Integer.parseInt(sDolor1));
        else
            this.oSegundaMitadEmbarazo.setDolorIntesidad(0);
    }
    public void asignaCuello0(){
        String cuello0 = valorCuello0 ? "TAN00" : "";        
        oSegundaMitadEmbarazo.getCuelloPosterior().setClaveParametro(cuello0);
    }
    public void asignaCuello1(){
        String cuello1 = valorCuello1 ? "TAN01" : "";        
        oSegundaMitadEmbarazo.getCuelloCentral().setClaveParametro(cuello1);
    }
    public void asignaCuello2(){
        String cuello2 = valorCuello2 ? "TAN02" : "";        
        oSegundaMitadEmbarazo.getCuelloResistente().setClaveParametro(cuello2);
    }
    public void asignaCuello3(){
        String cuello3 = valorCuello3 ? "TAN03" : "";        
        oSegundaMitadEmbarazo.getCuelloBlando().setClaveParametro(cuello3);
    }
    public void asignaLongitud0(){
        String Longitud0 =  longitud0 ? "TAO00" : "";
        oSegundaMitadEmbarazo.getLongitud0().setClaveParametro(Longitud0);
    }
    public void asignaLongitud1(){
        String Longitud1 = longitud1 ? "TAO01" : "";
        oSegundaMitadEmbarazo.getLongitud1().setClaveParametro(Longitud1);
    }
    public void asignaLongitud2(){
        String Longitud2 = longitud2 ? "TAO02" : "";
        oSegundaMitadEmbarazo.getLongitud2().setClaveParametro(Longitud2);
    }
    public void asignaLongitud3(){
        String Longitud3 = longitud3 ? "TAO03" : "";
        oSegundaMitadEmbarazo.getLongitud3().setClaveParametro(Longitud3);
    }
    public void asignaLongitud4(){
        String Longitud4 = longitud4 ? "TAO04" : "";
        oSegundaMitadEmbarazo.getLongitud4().setClaveParametro(Longitud4);
    }
    public void asignaFecha(SelectEvent event){        
        DateFormat fecha1 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat hora1 = new SimpleDateFormat("HH:mm");
        String sFecha = fecha1.format(fechaActual);
        String sHora = hora1.format(horaActual);        
        oPartoGrama.setFechaHora(sFecha + " " + sHora);
    }
    public void asignaFecha1(SelectEvent event){                        
        this.oSegundaMitadEmbarazo.setFecha(new Date());
        oSegundaMitadEmbarazo.getFecha().setTime((fechaSegundaMitadEmbarazo.getTime() + horaSegundaMitadEmbarazo.getTime()) - 21600000);        
    }
    public void asignaMotivoConsulta(){        
        oPartoGrama.getMotivoConsulta().setMotivoAtencion(getMotivoConsulta());
    }
    public void guardaDatosAnverso1(){
        try{            
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(this.oSegundaMitadEmbarazo.insertaAnverso1PartoGrama(oPartoGrama, oPrimeraMitadEmbarazo, oSegundaMitadEmbarazo, arrDiagCie10, arrDx, nTam) > 0){
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","REGISTROS GUARDADOS CON EXITO"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("frmPartograma.xhtml");
            }else
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,"i","TUVIMOS PROBLEMAS AL GUARDAR LA INFORMACION REVISA LOS CAMPOS :("));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public int hora0(){
        if(oSeguimientoTrabajoParto.getHora0() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora0());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora1(){
        if(oSeguimientoTrabajoParto.getHora1() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora1());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    
    public int hora2(){
        if(oSeguimientoTrabajoParto.getHora2() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora2());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora3(){
        if(oSeguimientoTrabajoParto.getHora3() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora3());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    
    public int hora4(){
        if(oSeguimientoTrabajoParto.getHora4() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora4());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    
    public int hora5(){
        if(oSeguimientoTrabajoParto.getHora5() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora5());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora6(){
        if(oSeguimientoTrabajoParto.getHora6() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora6());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora7(){
        if(oSeguimientoTrabajoParto.getHora7() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora7());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora8(){
        if(oSeguimientoTrabajoParto.getHora8() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora8());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora9(){
        if(oSeguimientoTrabajoParto.getHora9() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora9());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora10(){
        if(oSeguimientoTrabajoParto.getHora10() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora10());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora11(){
        if(oSeguimientoTrabajoParto.getHora11() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora11());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora12(){
        if(oSeguimientoTrabajoParto.getHora12() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora12());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
    public int hora13(){
        if(oSeguimientoTrabajoParto.getHora13() != null){
            DateFormat format = new SimpleDateFormat("HH");
            String string = format.format(oSeguimientoTrabajoParto.getHora13());
            int retorno = Integer.parseInt(string);
            return retorno;
        }
        return 0;
    }
        
    public void asignaPelviSuperior(){        
        oSeguimientoTrabajoParto.getPelvis().get(0).setSuperior(oPelvis.getSuperior());
    }
    public void asignaPelviMedio(){
        oSeguimientoTrabajoParto.getPelvis().get(0).setMedio(oPelvis.getMedio());
    }
    public void asignaPelvInferior(){
        oSeguimientoTrabajoParto.getPelvis().get(0).setInferior(oPelvis.getInferior());
    }
    public void asignaCedula(){
        if(oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
        else{            
            String cedulaProfesional = buscarMedico(oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().getNombres());            
            nTarjeta = buscarMedico(oPrimeraMitadEmbarazo);
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
            oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
        }
    }
    public void asignaCedula1(){
        if(oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
        else{
            String cedulaProfesional = buscarMedico(oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().getNombres());
            int nTarjeta = buscarMedico(oSegundaMitadEmbarazo);
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            int tarjeta = nTarjeta !=0 ? nTarjeta : 0;
            oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
        }        
    }
    public void asignaCedula2(){
        if(oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().getNombreCompleto().isEmpty())
            oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().setCedProf("SELECCIONE UN MÉDICO");        
        else{
            String cedulaProfesional = buscarMedico(oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().getNombres());
            int nTarjeta = buscarMedico1(oSegundaMitadEmbarazo);
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().setCedProf(cedula);
            int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
            oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().setNoTarjeta(tarjeta);
        }
    }
    public void asignaCedula3(){
        if(this.oSeguimientoTrabajoParto!= null){
            if(oSeguimientoTrabajoParto.getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
                oSeguimientoTrabajoParto.getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
            else{
                String cedulaProfesional = buscarMedico(oSeguimientoTrabajoParto.getPartoGrama().getMedicoSupervisor().getNombres());
                int nTarjeta = buscarMedico(oSeguimientoTrabajoParto);
                String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
                oSeguimientoTrabajoParto.getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
                int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
                oSeguimientoTrabajoParto.getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
            }
        }        
    }
    public void asignaCedula4(){        
        if(oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
        else{
            String cedulaProfesional = buscarMedico(oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());
            int nTarjeta = buscarMedico(oProducto);
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
            oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
        }        
    }
    public void asignacedula10(){
        if(!this.arrProducto.isEmpty()){
            for(int i = 0; i < this.arrProducto.size(); i++){
                if(this.arrProducto.get(i).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
                    this.arrProducto.get(i).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
                else{
                    String cedulaProfesional = buscarMedico(this.arrProducto.get(i).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());
                    int nTarjeta = buscarMedico(this.arrProducto.get(i));
                    String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
                    this.arrProducto.get(i).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
                    int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
                    this.arrProducto.get(i).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
                }
            }
        }
    }
    public void asignaCedula5(){
        if(oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
        else{            
            String cedulaProfesional = buscarMedico(oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());            
            int nTarjeta = buscarMedico(oAnalgesiaTparto);
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
            oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
        }
    }
    public void asignaCedula6(){
        if(oLegrado.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            oLegrado.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
        else{
            String cedulaProfesional = buscarMedico(oLegrado.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());
            int nTarjeta = buscarMedico(oLegrado);
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            oLegrado.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
            oLegrado.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
        }
    }
    public void asignaCedula7(){
        if(oEvolucion.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            oEvolucion.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
        else{
            
            String cedulaProfesional = buscarMedico(this.getNombreMedicoEvolucion());             
            int nTarjeta = buscarMedico1(this.getNombreMedicoEvolucion());            
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            oEvolucion.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            this.setCedula(cedula);
            int tarjeta = nTarjeta != 0 ? nTarjeta : 0;                     
            this.setTarjeta(tarjeta);
        }
    }
    public void asignaCedula8(){
        if(this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MÉDICO");
        else{            
            String cedulaProfesional = buscarMedico(this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());
            int nTarjeta = buscarMedico(this.oNotaPreoperatoria);
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
            
        }
    }
    public void asignaCedula9(){
        if(this.npotp.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("SELECIONE UN MÉDICO");
        else{
            String cedulaProfesional = buscarMedico(this.npotp.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());
            int nTarjeta = buscarMedico(this.npotp);
            String cedula = cedulaProfesional == null || cedulaProfesional.isEmpty() ? "NO DISPONIBLE" : cedulaProfesional;
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            int tarjeta = nTarjeta != 0 ? nTarjeta : 0;
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
        }
    }
    public String buscarMedico(String medico){
        try{            
            List<Medico> listMedico = null;
            listMedico = getListMedicoPartoGrama();
            for(Medico i : listMedico)
                if(i.getNombreCompleto().compareTo(medico) == 0)
                    return i.getCedProf();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public int buscarMedico(PrimeraMitadEmbarazo oEmbarazo){
        try{
            List<Medico> listaMedico = null;
            listaMedico = getListMedicoPartoGrama();
            for(Medico i : listaMedico)
                if(i.getNombreCompleto().compareTo(oEmbarazo.getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                    return i.getNoTarjeta();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int buscarMedico(SegundaMitadEmbarazo oEmbarazo){
        try{
            List<Medico> listaMedico = null;
            listaMedico = getListMedicoPartoGrama();
            for(Medico i : listaMedico)
                if(i.getNombreCompleto().compareTo(oEmbarazo.getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                    return i.getNoTarjeta();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int buscarMedico1(SegundaMitadEmbarazo oEmbarazo){
        try{
            List<Medico> listaMedico = null;
            listaMedico = getListMedicoPartoGrama();
            for(Medico i : listaMedico)
                if(i.getNombreCompleto().compareTo(oEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().getNombres()) == 0)
                    return i.getNoTarjeta();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int buscarMedico(SeguimientoTrabajoParto oSeguimiento){
        try{
            List<Medico> listaMedico = null;
            listaMedico = getListMedicoPartoGrama();
            for(Medico i : listaMedico)
                if(i.getNombreCompleto().compareTo(oSeguimiento.getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                    return i.getNoTarjeta();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int buscarMedico(Producto oProducto){
        try{
            List<Medico> listaMedico = null;
            listaMedico = getListMedicoPartoGrama();
            for(Medico l : listaMedico)
                if(l.getNombreCompleto().compareTo(oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                    return l.getNoTarjeta();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int buscarMedico(AnalgesiaTparto oAnalgesiaTparto){
        try{
            List<Medico> listaMedico = null;
            listaMedico = getListMedicoPartoGrama();
            for(Medico k : listaMedico)
                if(k.getNombreCompleto().compareTo(oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                    return k.getNoTarjeta();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int buscarMedico(Legrado oLegrado){
        try{
            List<Medico> listaMedico = null;
            listaMedico = getListMedicoPartoGrama();
            for(Medico l : listaMedico)
                if(l.getNombreCompleto().compareTo(oLegrado.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                    return l.getNoTarjeta();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public int buscarMedico1(String oEvolucion){
        List<Medico> listaMedico = getListMedicoPartoGrama();
        for(Medico k : listaMedico)
            if(k.getNombreCompleto().compareTo(oEvolucion) == 0)
                return k.getNoTarjeta();
        return 0;
    }
    public int buscarMedico(NotaPreoperatoria oNotaPreoperatoria){
        List<Medico> listaMedico = null;
        listaMedico = getListMedicoPartoGrama();
        for(Medico t : listaMedico)
            if(t.getNombreCompleto().compareTo(oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                return t.getNoTarjeta();
        return 0;
    }
    public int buscarMedico(NotaPostOclusionTubariaPostparto oNota){
        List<Medico> listaMedico = null;
        listaMedico = getListMedicoPartoGrama();
        for(Medico i : listaMedico)
            if(i.getNombreCompleto().compareTo(oNota.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                return i.getNoTarjeta();
        return 0;
    }    
    public void registroDiagnostico1(){
        if(getDx().getClave().isEmpty())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO PUEDE AGREGAR VACÍO"));
        else{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(nTam >= 6 || arrDx.size() >= 6 || nTam + arrDx.size() >= 6){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "IMPOSIBLE AGREGAR MAS DIAGNOSTICOS MAXIMO 6 DIAGNOSTICOS POR PACIENTE"));
                try{
                    getDx().setDescripcionDiag("");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                if(arrDx.isEmpty() && buscaRepetidoDxAnterior(oDx.getClave()) != true){
                    arrDx.add(oDx);
                    oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().setClave(oDx.getClave());
                    oDx = new DiagnosticoCIE10();
                }else{
                    if(buscaRepetido1(oDx.getClave())){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                        try{
                            getDx().setDescripcionDiag("");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        if(buscaRepetidoDxAnterior(oDx.getClave())){
                            FacesContext.getCurrentInstance().addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                            try{
                                getDx().setDescripcionDiag("");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            arrDx.add(oDx);
                            oDx = new DiagnosticoCIE10();
                        }
                    }
                }
            }
        }
    }
    public void registroDiagnostico(){
        if(getCie10().getClave().isEmpty())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO PUEDE AGREGAR VACÍO"));
        else{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(nTam >= 6 || arrDiagCie10.size() >= 6 || nTam + arrDiagCie10.size() >= 6){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "IMPOSIBLE AGREGAR MAS DIAGNOSTICOS MAXIMO 6 DIAGNOSTICOS POR PACIENTE"));
                try{
                    getCie10().setDescripcionDiag("");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                if(arrDiagCie10.isEmpty() && buscaRepetidoDxAnterior(oCie10.getClave())!= true){
                    oPrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().setClave(oCie10.getClave());                    
                    arrDiagCie10.add(oCie10);
                    oCie10 = new DiagnosticoCIE10();
                }else{
                    if(buscaRepetido(oCie10.getClave())){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                        try{
                            getCie10().setDescripcionDiag("");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        if(buscaRepetidoDxAnterior(oCie10.getClave())){
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                            try{
                                getCie10().setDescripcionDiag("");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            arrDiagCie10.add(oCie10);
                            oCie10 = new DiagnosticoCIE10();
                        }
                    }
                }
            }
        }
    }
    public boolean buscaRepetido(String clave){
        boolean bandera = false;
        for(DiagnosticoCIE10 i: arrDiagCie10)
            if(i.getClave().compareTo(clave) == 0)
                return bandera = true;
        return bandera;
    }
    public boolean buscaRepetido1(String clave){        
        for(DiagnosticoCIE10 i : arrDx)
            if(i.getClave().compareTo(clave) == 0)
                return true;
        return false;
    }
    public boolean buscaRepetidoDxAnterior(String clave){
        if(arrRet == null)
            return false;
        for(int l = 0 ; l < arrRet.length; l++)
            if(arrRet[l].getClave().compareTo(clave) == 0)
                return true;
        return false;
    }
    public DiagnosticoCIE10[] listaDiagnosticos(){
        try{
            NotaEmbarazo oConsulta = new NotaEmbarazo();
            return arrRet = oConsulta.datosPaciente(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getClaveEpisodio());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void guardaDatosReverso1(){
        try{            
            if(this.oSeguimientoTrabajoParto.insertaDatosReverso(oPartoGrama, oSeguimientoTrabajoParto, oRegistro1, oRegistro2, oRegistro3, oRegistro4, oRegistro5, oRegistro6, oRegistro7) > 0){
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","REGISTROS GUARDADOS CON EXITO"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("frmPartograma.xhtml");
            }else
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,"INFORMACION","TUVIMOS PROBLEMAS AL GUARDAR LA INFORMACION :'("));
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public void habilitaHoraNacimiento(SelectEvent event){
        hnacimiento = false;
    }    
    public void asignaFechaPreoperatoria(SelectEvent event){
        long fecha = getFechaPreoperatoria().getTime();
        long hora = getHoraPreoperatoria().getTime();
        Date fechaCompleta = new Date();
        fechaCompleta.setTime((fecha + hora) - 21600000);
        this.oNotaPreoperatoria.setFechaRegistro(fechaCompleta);        
    }
    public void asignaFechaCompleta(SelectEvent event){
        long fecha = getFecha1().getTime();
        long hora = getHora01().getTime();
        Date fechaCompleta = new Date();
        fechaCompleta.setTime((fecha + hora) - 21600000);
        npotp.setFecha(fechaCompleta);        
    }
    public void asignaReanimacion(){
        String reanimacion = getReanimacionProducto() ? "SI":"";
        oProducto.setReanimacionNeonatal(reanimacion);        
    }
    public void habilitaHoraLegrado(SelectEvent event){
        horaLegrado = false;
    }
    public int semanasAmenorreaTerminacionEmbarazo(){
        try{
            if(this.oLegrado != null){
                int nSemanas = oLegrado.semanasAmenorrea(oPartoGrama);
                return nSemanas;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public void asignaPerine(){
        String perine = getDesgarroPerine() ? "TAQ01" : "";
        oPartoEutocico.getDesgarro().get(0).setClaveParametro(perine);
    }
    public void asignaVagina(){
        String vagina = getDesgarroVagina() ? "TAQ02" : "";
        oPartoEutocico.getDesgarro().get(1).setClaveParametro(vagina);
    }
    public void asignaCervix(){
        String cervix = getDesgarroCervix() ? "TAQ03" : "";
        oPartoEutocico.getDesgarro().get(2).setClaveParametro(cervix);        
    }
    public void guardaDatosAnverso2(){
        try{                        
            if(this.oLegrado.insertaDatosAnverso2(oPartoGrama, oPartoEutocico, oForceps, oCesarea, oAlumbramiento, arrProducto, oAnalgesiaTparto, oPartoLegrado, oPlacenta, oLegrado) > 0){
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","REGISTROS GUARDADOS CON EXITO"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("frmPartograma.xhtml");               
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"INFORMACION","TUVIMOS PROBLEMAS AL GUARDAR LA INFORMACION"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }                               
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void asignaEvolucion(){
        int ntam = this.arrEvolucion.size();        
        this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(ntam + 1);        
        this.arrEvolucion.add(this.oEvolucion);        
        this.oEvolucion = new EvolucionPartoLegrado();
        this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(this.oPartoGrama.getEpiMed().getPaciente().getFolioPaciente());
        this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(this.oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio());        
        habilitaMedicoEvolucion();
    }
    public void asignaRecienNacido(){
        int nTam = arrProducto.size();
        if(arrProducto == null || arrProducto.isEmpty()){                                           
            arrProducto.add(oProducto);
            oProducto = new Producto();
            oProducto.setSexoProducto(new Parametrizacion());
            oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
        }else{
            for(int i = 0; i < arrProducto.size(); i++)
                if(arrProducto.get(i).equals(oProducto)){                        
                    arrProducto.set(i, oProducto);
                    oProducto = new Producto();
                    oProducto.setSexoProducto(new Parametrizacion());
                    oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
                    return;
                }                
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(nTam + 1);
            arrProducto.add(oProducto);
            oProducto = new Producto();
            oProducto.setSexoProducto(new Parametrizacion());
            oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
        }
    }               
    
    public void reset(){        
        oEvolucion.setFechaHora(null);
        oEvolucion.setEvolucion("");
        oEvolucion.setObservaciones("");
    }
    public void reset1(){
        oProducto = new Producto();
        oProducto.setSexoProducto(new Parametrizacion());
        oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
    }
    public void habilitaHoraPreoperatoria(SelectEvent dateSelect){
        bHoraPreoperatoria = false;
    }
    public void habilitaBanderaHora(SelectEvent event){
        bBandera = false;
    }
    public void guardaDatosReverso2(){        
        this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(this.getTarjeta());        
        ArrayList<String> sQuery = this.oEvolucion.getQueryEvolucion(arrEvolucion);
        this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(this.oPartoGrama.getConsecutivo());
        String sQueryPreoperatorio = this.oNotaPreoperatoria.getQueryPreoperatorio();
        this.npotp.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(this.oPartoGrama.getConsecutivo());
        String sQueryPostoperatorio = this.npotp.getQueryPostoperatorio();                              
        try{
            if(this.npotp.insertaReverso(sQuery, sQueryPreoperatorio, sQueryPostoperatorio) > 0){
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","REGISTROS GUARDADOS CON EXITO"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("frmPartograma.xhtml");               
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"INFORMACION","TUVIMOS PROBLEMAS AL GUARDAR LA INFORMACION :'("));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public void habilitaMedicoEvolucion(){        
        if(this.getNombreMedicoEvolucion() == null || this.getNombreMedicoEvolucion().isEmpty())
            setHabilitaMedico(false);
        else
            setHabilitaMedico(true);        
        
    }
    public void asignatipocirugia(){        
        if(this.getNotaPreoperatoria().getDescripcionCirugia().getClaveParametro().compareTo("") !=0){
            String tipocirugia = getNotaPreoperatoria().getDescripcionCirugia().getClaveParametro().substring(0, 3).compareTo("TAY") != 0 ? "74" : "695";
            getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setClave(tipocirugia);
        }        
    }
    
    public void buscarDatosPaciente(int opcion){      
        try{
            oConsultaPartoGrama.getEpiMed().getPaciente().setOpcionUrg(opcion);
            if(oConsultaPartoGrama.getEpiMed().getPaciente().getNombres() != null || !oConsultaPartoGrama.getEpiMed().getPaciente().getNombres().isEmpty() ||
                    oConsultaPartoGrama.getEpiMed().getPaciente().getExpediente().getNumero() != 0 || !oConsultaPartoGrama.getEpiMed().getPaciente().getExpediente().equals(null)){                
                    datosPartoGrama = oConsultaPartoGrama.buscaPacienteDatos();             
                if(datosPartoGrama != null){
                    oConsultapartograma.getEpiMed().getPaciente().setNombres(datosPartoGrama[0].getEpiMed().getPaciente().getNombres());
                    oConsultapartograma.getEpiMed().getPaciente().setApPaterno(datosPartoGrama[0].getEpiMed().getPaciente().getApPaterno());
                    oConsultapartograma.getEpiMed().getPaciente().setApMaterno(datosPartoGrama[0].getEpiMed().getPaciente().getApMaterno());
                    oConsultapartograma.getEpiMed().getPaciente().setFechaNac(datosPartoGrama[0].getEpiMed().getPaciente().getFechaNac());                    
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","AUN NO HA GENERADO UN PARTO GRAMA PARA LA PACIENTE: " + 
                    oConsultaPartoGrama.getEpiMed().getPaciente().getNombreCompleto()));
                    RequestContext.getCurrentInstance().update("busqueda:msgs"); 
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }       
    }
    public void buscarDatosPacienteRegistroPartograma(int opcion){
        try{
            oPaciente.setOpcionUrg(opcion);
            if(oPaciente.getNombres() != null || 
      !oPaciente.getNombres().isEmpty() || 
                    oPaciente.getExpediente()!=null || 
                    oPaciente.getExpediente().getNumero() != 0 ){
                datosPaciente = oPaciente.buscarPacienteDatos();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public PartoGrama[] getPacientePartoGrama(){
        return datosPartoGrama; 
    }
    public Paciente[] getPacienteDatos(){        
        return datosPaciente;
    }    
    public DiagnosticoCIE10[] listaDx(){
        try{
            NotaEmbarazo oConsulta = new NotaEmbarazo();
            DiagnosticoCIE10 arrRet[];
            return arrRet = oConsulta.datosPaciente(this.fpaciente, this.clave);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /*public String formatoFecha1(){        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        if(oDetalleSegundaMitadEmbarazo.getFecha() != null ){
            String fecha1 = format.format(oDetalleSegundaMitadEmbarazo.getFecha());
            String hora1 = format1.format(oDetalleSegundaMitadEmbarazo.getFecha());
            return fecha1 + " " + hora1;
        }   
        return null;
    }*/
    /********TERMINAN METODOS DE CONTROL DE DATOS****************/
    /********INICIAN METODOS SET'S Y GTE'S DE LAS PROPIEDADES****************/
    public boolean getMuestraConsultaPartoGrama(){
        return bMuestraConsulta;
    }
    public void setMuestraConsultaPartoGrama(boolean bMuestraConsulta){
        this.bMuestraConsulta = bMuestraConsulta;
    }
    public boolean getMuestraPantalla(){
        return muestrapantalla;
    }
    public void setMuestraPantalla(boolean muestrapantalla){
        this.muestrapantalla = muestrapantalla;
    }
    public PartoGrama getPartoGrama(){
        return oPartoGrama;
    }
    public void setPartoGrama(PartoGrama oPartoGrama){
        this.oPartoGrama = oPartoGrama;
    }
    public Date getFechaActual(){
        return dFecha1;
    }
    public void setFechaActual(Date dFecha1){
        this.dFecha1 = dFecha1;
    }
    public Date getFecha(){
        return fechaActual;
    }
    public void setFecha(Date fechaActual){
        this.fechaActual = fechaActual;        
    }
    public Date getHoraActual(){
        return horaActual;
    }
    public void setHoraActual(Date horaActual){
        this.horaActual = horaActual;
    }
    public boolean getBandera00(){
        return bandera00;
    }
    public void setBandera00(boolean bandera00){
        this.bandera00 = bandera00;
    }
    public SeguimientoTrabajoParto getSeguimientoTrabajoParto(){
        return oSeguimientoTrabajoParto;
    }
    public void setSeguimientoTrabajoParto(SeguimientoTrabajoParto oSeguimientoTrabajoParto){
        this.oSeguimientoTrabajoParto = oSeguimientoTrabajoParto;
    }
    public boolean getHoraControl0(){
        return bHoraControl0;
    }
    public void setHoraControl0(boolean bHoraControl0){
        this.bHoraControl0 = bHoraControl0;
    }
    public boolean getHoraControl1(){
        return bHoraControl1;
    }
    public void setHoraControl1(boolean bHoraControl1){
        this.bHoraControl1 = bHoraControl1;
    }
    public boolean getHoraControl2(){
        return bHoraControl2;
    }
    public void setHoraControl2(boolean bHoraControl2){
        this.bHoraControl2 = bHoraControl2;
    }
    public boolean getHoraControl3(){
        return bHoraControl3;
    }
    public void setHoraControl3(boolean bHoraControl3){
        this.bHoraControl3 = bHoraControl3;
    }
    public boolean getHoraControl4(){
        return bHoraControl4;
    }
    public void setHoraControl4(boolean bHoraControl4){
        this.bHoraControl4 = bHoraControl4;
    }
    public boolean getHoraControl5(){
        return bHoraControl5;
    }
    public void setHoraControl5(boolean bHoraControl5){
        this.bHoraControl5 = bHoraControl5;
    }
    public boolean getHoraControl6(){
        return bHoraControl6;
    }
    public void setHoraControl6(boolean bHoraControl6){
        this.bHoraControl6 = bHoraControl6;
    }
    public boolean getHoraControl7(){
        return bHoraControl7;
    }
    public void setHoraControl7(boolean bHoraControl7){
        this.bHoraControl7 = bHoraControl7;
    }
    public boolean getHoraControl8(){
        return  bHoraControl8;
    }
    public void setHoraControl8(boolean bHoraControl8){
        this.bHoraControl8 = bHoraControl8;
    }
    public boolean getHoraControl9(){
        return bHoraControl9;
    }
    public void setHoraControl9(boolean bHoraControl9){
        this.bHoraControl9 = bHoraControl9;
    }
    public boolean getHoraControl10(){
        return bHoraControl10;
    }
    public void setHoraControl10(boolean bHoraControl10){
        this.bHoraControl10 = bHoraControl10;
    }
    public boolean getHoraControl11(){
        return bHoraControl11;
    }
    public void setHoraControl11(boolean bHoraControl11){
        this.bHoraControl11 = bHoraControl11;
    }
    public boolean getHoraControl12(){
        return bHoraControl12;
    }
    public void setHoraControl12(boolean bHoraControl12){
        this.bHoraControl2 = bHoraControl12;
    }
    public boolean getHoraControl13(){
        return bHoraControl13;
    }
    public void setHoraControl13(boolean bHoraControl13){
        this.bHoraControl13 = bHoraControl13;
    }
    public boolean getHoraControl14(){
        return bHoraControl14;
    }
    public void setHoraControl14(boolean bHoraControl14){
        this.bHoraControl14 = bHoraControl14;
    }
    public PrimeraMitadEmbarazo getPrimeraMitadEmbarazo(){
        return oPrimeraMitadEmbarazo;
    }
    public void setPrimeraMitadEmbarazo(PrimeraMitadEmbarazo oPrimeraMitadEmbarazo){
        this.oPrimeraMitadEmbarazo = oPrimeraMitadEmbarazo;
    }
    public boolean getConsiente(){
        return consiente;
    }
    public void setConsiente(boolean consiente){
        this.consiente = consiente;
    }
    public String getDolorTemp(){
        return sDolor;
    }
    public void setDolorTemp(String sDolor){
        this.sDolor = sDolor;
    }
    public SegundaMitadEmbarazo getSegundaMitadEmbarazo(){
        return oSegundaMitadEmbarazo;
    }
    public void setSegundaMitadEmbarazo(SegundaMitadEmbarazo oSegundaMitadEmbarazo){
        this.oSegundaMitadEmbarazo = oSegundaMitadEmbarazo;
    }
    public String getDolor1(){
        return sDolor1;
    }
    public void setDolor1(String sDolor1){
        this.sDolor1 = sDolor1;
    }
    public Date getFechaTemporal(){
        return fechaTemporal;
    }
    public void setFechaTemporal(Date fechaTemporal){
        this.fechaTemporal = fechaTemporal;
    }
    public Date getFechaSegundaMitadEmbarazo(){
        return fechaSegundaMitadEmbarazo;
    }
    public void setFechaSegundaMitadEmbarazo(Date fechaSegundaMitadEmbarazo){
        this.fechaSegundaMitadEmbarazo = fechaSegundaMitadEmbarazo;
    }
    public Date getHoraSegundaMitadEmbarazo(){
        return horaSegundaMitadEmbarazo;
    }
    public void setHoraSegundaMitadEmbarazo(Date horaSegundaMitadEmbarazo){
        this.horaSegundaMitadEmbarazo = horaSegundaMitadEmbarazo;
    }
    public boolean getValorCuello0(){
        return valorCuello0;
    }
    public void setValorCuello0(boolean valorCuello0){
        this.valorCuello0 = valorCuello0;
    }
    public boolean getValorCuello1(){
        return valorCuello1;
    }
    public void setValorCuello1(boolean valorCuello1){
        this.valorCuello1 = valorCuello1;
    }
    public boolean getValorCuello2(){
        return valorCuello2;
    }
    public void setValorCuello2(boolean valorCuello2){
        this.valorCuello2 = valorCuello2;
    }
    public boolean getValorCuello3(){
        return valorCuello3;
    }
    public void setValorCuello3(boolean valorCuello3){
        this.valorCuello3 = valorCuello3;
    }
    public boolean getLongitud0(){
        return longitud0;
    }
    public void setLongitud0(boolean longitud0){
        this.longitud0 = longitud0;
    }
    public boolean getLongitud1(){
        return longitud1;
    }
    public void setLongitud1(boolean longitud1){
        this.longitud1 = longitud1;
    }
    public boolean getLongitud2(){
        return longitud2;
    }
    public void setLongitud2(boolean longitud2){
        this.longitud2 = longitud2;
    }
    public boolean getLongitud3(){
        return longitud3;
    }
    public void setLongitud3(boolean longitud3){
        this.longitud3 = longitud3;
    }
    public boolean getLongitud4(){
        return longitud4;
    }
    public void setLongitud4(boolean longitud4){
        this.longitud4 = longitud4;
    }
    public String  getMotivoConsulta(){
        return sMotivoConsulta;
    }
    public void setMotivoConsulta(String sMotivoConsulta){
        this.sMotivoConsulta = sMotivoConsulta;
    }
    public boolean getHabilita(){
        return bHabilita;
    }
    public void setHabilita(boolean bHabilita){
        this.bHabilita = bHabilita;
    }
    public SignosVitales getRegistro1(){
        return oRegistro1;
    }
    public void setRegistro1(SignosVitales oRegistro1){
        this.oRegistro1 = oRegistro1;
    }
    public SignosVitales getRegistro2(){
        return oRegistro2;
    } 
    public void setRegistro2(SignosVitales oRegistro2){
        this.oRegistro2 = oRegistro2;
    }
    public SignosVitales getRegistro3(){
        return oRegistro3;
    }
    public void setRegistro3(SignosVitales oRegistro3){
        this.oRegistro3 = oRegistro3;
    }
    public SignosVitales getRegistro4(){
        return oRegistro4;
    }
    public void setRegistro4(SignosVitales oRegistro4){
        this.oRegistro4 = oRegistro4;
    }
    public SignosVitales getRegistro5(){
        return oRegistro5;
    }
    public void setRegistro5(SignosVitales oRegistro5){
        this.oRegistro5 = oRegistro5;
    }
    public SignosVitales getRegistro6(){
        return oRegistro6;
    }
    public void setRegistro6(SignosVitales oRegistro6){
        this.oRegistro6 = oRegistro6;
    }
    public SignosVitales getRegistro7(){
        return oRegistro7;
    }
    public void setRegistro7(SignosVitales oRegistro7){
        this.oRegistro7 = oRegistro7;
    }
    public String getTemporal(){
        return temporal;
    }
    public void setTemporal(String temporal){
        this.temporal = temporal;
    }
    public Pelvis getPelvis(){
        return oPelvis;
    }
    public void setPelvis(Pelvis oPelvis){
        this.oPelvis = oPelvis;
    }    
    public DiagnosticoCIE10 getCie10(){
        return oCie10;
    }
    public void setCie10(DiagnosticoCIE10 oCie10){
        this.oCie10 = oCie10;
    }
    public ArrayList<DiagnosticoCIE10> getDiagCie10(){
        return arrDiagCie10;
    }
    public void borrarElementoCie10(DiagnosticoCIE10 oCie10){
        if(arrDiagCie10 != null || arrDiagCie10.isEmpty()){
            if(arrDiagCie10.get(0).getClave().compareTo(oCie10.getClave()) == 0){
                oPrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().setClave("");
                arrDiagCie10.remove(oCie10);                
            }else
                arrDiagCie10.remove(oCie10);
        }
        
    }
    public DiagnosticoCIE10 getDx(){
        return oDx;
    }
    public void setDx(DiagnosticoCIE10 oCie10){
        this.oCie10 = oCie10;
    }
    public ArrayList<DiagnosticoCIE10> getArrdx(){
        return arrDx;
    }
    public void borrarElementoDx(DiagnosticoCIE10 oDx){
        if(arrDx != null || arrDx.isEmpty()){
            if(arrDx.get(0).getClave().compareTo(oDx.getClave()) == 0){
                oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().setClave("");
                arrDx.remove(oDx);
            }else
                arrDx.remove(oDx);
        }
    }    
    public DiagnosticoCIE10[] getDiagnosticoAnterior(){
        return arrRet;
    }
    public void setDiagnosticoAnterior(DiagnosticoCIE10[] arrRet){
        this.arrRet = arrRet;
    }
    public boolean getBanderaHN(){
        return hnacimiento;
    }
    public void setBanderaHN(boolean hnacimiento){
        this.hnacimiento = hnacimiento;
    }
    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
    public Date getHoraNacimiento(){
        return horaNacimiento;
    }
    public void setHoraNacimiento(Date horaNacimiento){
        this.horaNacimiento = horaNacimiento;
    }
    public PartoEutocico getPartoEutocico(){
        return oPartoEutocico;
    }
    public void setPartoEutocico(PartoEutocico oPartoEutocico){
        this.oPartoEutocico = oPartoEutocico;
    }
    public Forceps getForceps(){
        return oForceps;
    }
    public void setForceps(Forceps oForceps){
        this.oForceps = oForceps;
    }
    public Cesarea getCesarea(){
        return oCesarea;
    }
    public void setCesarea(Cesarea oCesarea){
        this.oCesarea = oCesarea;
    }
    public Alumbramiento getAlumbramiento(){
        return oAlumbramiento;
    }
    public void setAlumbramiento(Alumbramiento oAlumbramiento){
        this.oAlumbramiento = oAlumbramiento;
    }
    public Producto getProducto(){
        return oProducto;
    }
    public void setProducto(Producto oProducto){
        this.oProducto = oProducto;        
    }
    public void modificaProducto(Producto oProducto){
        this.oProducto = oProducto;        
    }
    public boolean getReanimacionProducto(){
        return bReanimacion;
    }
    public void setReanimacionProducto(boolean bReanimacion){
        this.bReanimacion = bReanimacion;
    }
    public AnalgesiaTparto getAnalgesiaTparto(){
        return oAnalgesiaTparto;
    }
    public void setAnalgesiaTparto(AnalgesiaTparto oAnalgesiaTparto){
        this.oAnalgesiaTparto = oAnalgesiaTparto;
    }
    public AnalgesiaTparto getPartoLegrado(){
        return oPartoLegrado;
    }
    public void setPartoLegrado(AnalgesiaTparto oPartoLegrado){
        this.oPartoLegrado = oPartoLegrado;
    }
    public Placenta getPlacenta(){
        return oPlacenta;
    }
    public void setPlacenta(Placenta oPlacenta){
        this.oPlacenta = oPlacenta;
    }
    public Legrado getLegrado(){
        return oLegrado;
    }
    public void setLegrado(Legrado oLegrado){
        this.oLegrado = oLegrado;
    }
    public boolean getHoraLegrado(){
        return horaLegrado;
    }
    public void setHoraLegrado(boolean horaLegrado){
        this.horaLegrado = horaLegrado;
    }
    public boolean getDesgarroPerine(){
        return bdesgarroPerine;
    }
    public void setDesgarroPerine(boolean bdesgarroPerine){
        this.bdesgarroPerine = bdesgarroPerine;
    }
    public boolean getDesgarroVagina(){
        return bdesgarroVagina;
    }
    public void setDesgarroVagina(boolean bdesgarroVagina){
        this.bdesgarroVagina = bdesgarroVagina;
    }
    public boolean getDesgarroCervix(){
        return bdesgarroCervix;
    }
    public void setDesgarroCervix(boolean bdesgarroCervix){
        this.bdesgarroCervix = bdesgarroCervix;
    }
    public ArrayList<EvolucionPartoLegrado> getArregloEvolucion(){
        return arrEvolucion;
    }
    public void setArregloEvolucion(ArrayList<EvolucionPartoLegrado> arrEvolucion){
        this.arrEvolucion = arrEvolucion;
    }
    public EvolucionPartoLegrado getEvolucionPartoLegrado(){
        return oEvolucion;
    }
    public void setEvolucionPartoLegrado(EvolucionPartoLegrado oEvolucion){
        this.oEvolucion = oEvolucion;
    }
    public NotaPreoperatoria getNotaPreoperatoria(){
        return oNotaPreoperatoria;
    }
    public void setNotaPreoperatoria(NotaPreoperatoria oNotaPreoperatoria){
        this.oNotaPreoperatoria = oNotaPreoperatoria;
    }
    public boolean getBanderaPreoperatoria(){
        return bHoraPreoperatoria;
    }
    public void setBanderaPreoperatoria(boolean bHoraPreoperatoria){
        this.bHoraPreoperatoria = bHoraPreoperatoria;
    }
    public Date getFechaPreoperatoria(){
        return dFPreoperatoria;
    }
    public void setFechaPreoperatoria(Date dFPreoperatoria){
        this.dFPreoperatoria = dFPreoperatoria;
    }
    public Date getHoraPreoperatoria(){
        return dHPreoperatoria;
    }
    public void setHoraPreoperatoria(Date dHPreoperatoria){
        this.dHPreoperatoria = dHPreoperatoria;
    }
    public NotaPostOclusionTubariaPostparto getNotaPostOclusionTubariaPostparto(){
        return npotp;
    }
    public void setNotaPostOclusionTubariaPostparto(NotaPostOclusionTubariaPostparto npotp){
        this.npotp = npotp;
    }
    public boolean getBandera(){
        return bBandera;
    }
    public void setBandera(boolean bBandera){
        this.bBandera = bBandera;
    }
    public Date getFecha1(){
        return fecha;
    }
    public void setFecha1(Date fecha){
        this.fecha = fecha;
    }
    public Date getHora01(){
        return hora;
    }
    public void setHora01(Date hora){
        this.hora = hora;
    }
    public boolean getHabilitaMedico(){
        return bHabilitaMedico;
    }
    public void setHabilitaMedico(boolean bHabilitaMedico){
        this.bHabilitaMedico = bHabilitaMedico;
    }
    public ArrayList<Producto> getArrProducto(){
        return arrProducto;
    }
    public void setArrProducto(ArrayList<Producto> arrProducto){
        this.arrProducto = arrProducto;
    }    
    public PartoGrama getConsultaPartoGrama(){
        return oConsultaPartoGrama;
    }
    public void setConsultaPartoGrama(PartoGrama oConsultaPartoGrama){
        this.oConsultaPartoGrama = oConsultaPartoGrama;
    }
    public PartoGrama getConsultaPartoGrama1(){
        return oConsultapartograma;
    }
    public void setConsultaPartoGrama1(PartoGrama oConsultapartograma){
        this.oConsultapartograma = oConsultapartograma;
    }
    public PartoGrama getConsultaDatosPartoGrama(){
        return oConsultaDatosPartoGrama;
    }
    public void setConsultaDatosPartoGrama(PartoGrama oConsultaDatosPartoGrama){
        this.oConsultaDatosPartoGrama = oConsultaDatosPartoGrama;
    }
    public PrimeraMitadEmbarazo getDetallePrimeraMitadEmbarazo(){
        return oDetallePrimeraMitadEmbarazo;
    }
    public void setDetallePrimeraMitadEmbarazo(PrimeraMitadEmbarazo oDetallePrimeraMitadEmbarazo){
        this.oDetallePrimeraMitadEmbarazo = oDetallePrimeraMitadEmbarazo;
    }
    public SegundaMitadEmbarazo getDetalleSegundaMitadEmbarazo(){
        return oDetalleSegundaMitadEmbarazo;
    }
    public void setDetalleSegundaMitadEmbarazo(SegundaMitadEmbarazo oDetalleSegundaMitadEmbarazo){
        this.oDetalleSegundaMitadEmbarazo = oDetalleSegundaMitadEmbarazo;
    }
    public Paciente getBuscaPaciente(){
        return oPaciente;
    }
    public void setBuscaPaciente(Paciente oPaciente){
        this.oPaciente = oPaciente;
    }
    public SeguimientoTrabajoParto getDetalleSeguimiento(){
        return oDetalleSeguimiento;
    }
    public void setDetalleSeguimiento(SeguimientoTrabajoParto oDetalleSeguimiento){
        this.oDetalleSeguimiento = oDetalleSeguimiento;
    }
    public SeguimientoTrabajoParto[] getDetalleSeguimientoSignosVitales(){
        return arrDetalleSeguimientoSignosvitales;
    }
    public void setDetalleSeguimientoSignosVitales(SeguimientoTrabajoParto[] arrDetalleSeguimientoSignosvitales){
        this.arrDetalleSeguimientoSignosvitales = arrDetalleSeguimientoSignosvitales;
    }
    public boolean getBanderaBoton(){
        return bBoton;
    }
    public void setBanderaBoton(boolean bBoton){
        this.bBoton = bBoton;
    }
    public boolean getTab1(){
        return bTab1;
    }
    public void setTab1(boolean bTab1){
        this.bTab1 = bTab1;
    }
    public boolean getTab2(){
        return bTab2;
    }
    public void setTab2(boolean bTab2){
        this.bTab2 = bTab2;
    }
    public boolean getTab3(){
        return bTab3;
    }
    public void setTab3(boolean bTab3){
        this.bTab3 = bTab3;
    }
    public boolean getTab4(){
        return bTab4;
    }
    public void setTab4(boolean bTab4){
        this.bTab4 = bTab4;
    }
    public boolean getHabilitaCompletePre(){
        return bComplete2;
    }
    
    public void setHabilitaCompletePre(boolean bComplete2){
        this.bComplete2 = bComplete2;
    }
    public boolean getHabilitaCompletePost(){
        return this.bHabilitaCompletePostoperatorio;
    }
    public void setHabilitaCompletePost(boolean bHabilitaCompletePostoperatorio){
        this.bHabilitaCompletePostoperatorio = bHabilitaCompletePostoperatorio;
    }
    public String getValorFrecuencia00(){
        return oDetalleSeguimiento.getFrec0() == 180 ? "●" : "";
    }
    public String getValorFrecuencia01(){
        return oDetalleSeguimiento.getFrec0() == 160 ? "●" : "";
    }
    public String getValorFrecuencia02(){
        return oDetalleSeguimiento.getFrec0() == 140 ? "●" : "";
    }
    public String getValorFrecuencia03(){
        return oDetalleSeguimiento.getFrec0() == 120 ? "●" : "";
    }
    public String getValorFrecuencia04(){
        return oDetalleSeguimiento.getFrec0() == 100 ? "●" : "";
    }
    public String getValorFrecuencia05(){
        return oDetalleSeguimiento.getFrec0() == 80 ? "●" : "";
    }
    public String getValorFrecuencia10(){
        return oDetalleSeguimiento.getFrec1() == 180 ? "●" : "";
    }
    public String getValorFrecuencia11(){
        return oDetalleSeguimiento.getFrec1() == 160 ? "●" : "";
    }
    public String getValorFrecuencia12(){
        return oDetalleSeguimiento.getFrec1() == 140 ? "●" : "";
    }
    public String getValorFrecuencia13(){
        return oDetalleSeguimiento.getFrec1() == 120 ? "●" : "";
    }
    public String getValorFrecuencia14(){
        return oDetalleSeguimiento.getFrec1() == 100 ? "●" : "";
    }
    public String getValorFrecuencia15(){
        return oDetalleSeguimiento.getFrec1() == 80 ? "●" : "";
    }
    public String getValorFrecuencia20(){
        return oDetalleSeguimiento.getFrec2() == 180 ? "●" : "";
    }
    public String getValorFrecuencia21(){
        return oDetalleSeguimiento.getFrec2() == 160 ? "●" : "";
    }
    public String getValorFrecuencia22(){
        return oDetalleSeguimiento.getFrec2() == 140 ? "●" : "";
    }
    public String getValorFrecuencia23(){
        return oDetalleSeguimiento.getFrec2() == 120 ? "●" : "";
    }
    public String getValorFrecuencia24(){
        return oDetalleSeguimiento.getFrec2() == 100 ? "●" : "";
    }
    public String getValorFrecuencia25(){
        return oDetalleSeguimiento.getFrec2() == 80 ? "●" : "";
    }
    public String getValorFrecuencia30(){
        return oDetalleSeguimiento.getFrec3() == 180 ? "●" : "";
    }
    public String getValorFrecuencia31(){
        return oDetalleSeguimiento.getFrec3() == 160 ? "●" : "";
    }
    public String getValorFrecuencia32(){
        return oDetalleSeguimiento.getFrec3() == 140 ? "●" : "";
    }
    public String getValorFrecuencia33(){
        return oDetalleSeguimiento.getFrec3() == 120 ? "●" : "";
    }
    public String getValorFrecuencia34(){
        return oDetalleSeguimiento.getFrec3() == 100 ? "●" : "";
    }
    public String getValorFrecuencia35(){
        return oDetalleSeguimiento.getFrec3() == 80 ? "●" : "";
    }
    public String getValorFrecuencia40(){
        return oDetalleSeguimiento.getFrec4() == 180 ? "●" : "";
    }
    public String getValorFrecuencia41(){
        return oDetalleSeguimiento.getFrec4() == 160 ? "●" : "";
    }
    public String getValorFrecuencia42(){
        return oDetalleSeguimiento.getFrec4() == 140 ? "●" : "";
    }
    public String getValorFrecuencia43(){
        return oDetalleSeguimiento.getFrec4() == 120 ? "●" : "";
    }
    public String getValorFrecuencia44(){
        return oDetalleSeguimiento.getFrec4() == 100 ? "●" : "";
    }
    public String getValorFrecuencia45(){
        return oDetalleSeguimiento.getFrec4() == 80 ? "●" : "";
    }
    public String getValorFrecuencia50(){
        return oDetalleSeguimiento.getFrec5() == 180 ? "●" : "";
    }
    public String getValorFrecuencia51(){
        return oDetalleSeguimiento.getFrec5() == 160 ? "●" : "";
    }
    public String getValorFrecuencia52(){
        return oDetalleSeguimiento.getFrec5() == 140 ? "●" : "";
    }
    public String getValorFrecuencia53(){
        return oDetalleSeguimiento.getFrec5() == 120 ? "●" : "";
    }
    public String getValorFrecuencia54(){
        return oDetalleSeguimiento.getFrec5() == 100 ? "●" : "";
    }
    public String getValorFrecuencia55(){
        return oDetalleSeguimiento.getFrec5() == 80 ? "●" : "";
    }
    public String getValorFrecuencia60(){
        return oDetalleSeguimiento.getFrec6() == 180 ? "●" : "";
    }
    public String getValorFrecuencia61(){
        return oDetalleSeguimiento.getFrec6() == 160 ? "●" : "";
    }
    public String getValorFrecuencia62(){
        return oDetalleSeguimiento.getFrec6() == 140 ? "●" : "";
    }
    public String getValorFrecuencia63(){
        return oDetalleSeguimiento.getFrec6() == 120 ? "●" : "";
    }
    public String getValorFrecuencia64(){
        return oDetalleSeguimiento.getFrec6() == 100 ? "●" : "";
    }
    public String getValorFrecuencia65(){
        return oDetalleSeguimiento.getFrec6() == 80 ? "●" : "";
    }
    public String getValorFrecuencia70(){
        return oDetalleSeguimiento.getFrec7() == 180 ? "●" : "";
    }
    public String getValorFrecuencia71(){
        return oDetalleSeguimiento.getFrec7() == 160 ? "●" : "";
    }
    public String getValorFrecuencia72(){
        return oDetalleSeguimiento.getFrec7() == 140 ? "●" : "";
    }
    public String getValorFrecuencia73(){
        return oDetalleSeguimiento.getFrec7() == 120 ? "●" : "";
    }
    public String getValorFrecuencia74(){
        return oDetalleSeguimiento.getFrec7() == 100 ? "●" : "";
    }
    public String getValorFrecuencia75(){
        return oDetalleSeguimiento.getFrec7() == 80 ? "●" : "";
    }
    public String getValorFrecuencia80(){
        return oDetalleSeguimiento.getFrec8() == 180 ? "●" : "";
    }
    public String getValorFrecuencia81(){
        return oDetalleSeguimiento.getFrec8() == 160 ? "●" : "";
    }
    public String getValorFrecuencia82(){
        return oDetalleSeguimiento.getFrec8() == 140 ? "●" : "";
    }
    public String getValorFrecuencia83(){
        return oDetalleSeguimiento.getFrec8() == 120 ? "●" : "";
    }
    public String getValorFrecuencia84(){
        return oDetalleSeguimiento.getFrec8() == 100 ? "●" : "";
    }
    public String getValorFrecuencia85(){
        return oDetalleSeguimiento.getFrec8() == 80 ? "●" : "";
    }
    public String getValorFrecuencia90(){
        return oDetalleSeguimiento.getFrec9() == 180 ? "●" : "";
    }
    public String getValorFrecuencia91(){
        return oDetalleSeguimiento.getFrec9() == 160 ? "●" : "";
    }
    public String getValorFrecuencia92(){
        return oDetalleSeguimiento.getFrec9() == 140 ? "●" : "";
    }
    public String getValorFrecuencia93(){
        return oDetalleSeguimiento.getFrec9() == 120 ? "●" : "";
    }
    public String getValorFrecuencia94(){
        return oDetalleSeguimiento.getFrec9() == 100 ? "●" : "";
    }
    public String getValorFrecuencia95(){
        return oDetalleSeguimiento.getFrec9() == 80 ? "●" : "";
    }
    public String getValorFrecuencia100(){
        return oDetalleSeguimiento.getFrec10() == 180 ? "●" : "";
    }
    public String getValorFrecuencia101(){
        return oDetalleSeguimiento.getFrec10() == 160 ? "●" : "";
    }
    public String getValorFrecuencia102(){
        return oDetalleSeguimiento.getFrec10() == 140 ? "●" : "";
    }
    public String getValorFrecuencia103(){
        return oDetalleSeguimiento.getFrec10() == 120 ? "●" : "";
    }
    public String getValorFrecuencia104(){
        return oDetalleSeguimiento.getFrec10() == 100 ? "●" : "";
    }
    public String getValorFrecuencia105(){
        return oDetalleSeguimiento.getFrec10() == 80 ? "●" : "";
    }
    public String getValorFrecuencia110(){
        return oDetalleSeguimiento.getFrec11() == 180 ? "●" : "";
    }
    public String getValorFrecuencia111(){
        return oDetalleSeguimiento.getFrec11() == 160 ? "●" : "";
    }
    public String getValorFrecuencia112(){
        return oDetalleSeguimiento.getFrec11() == 140 ? "●" : "";
    }
    public String getValorFrecuencia113(){
        return oDetalleSeguimiento.getFrec11() == 120 ? "●" : "";
    }
    public String getValorFrecuencia114(){
        return oDetalleSeguimiento.getFrec11() == 100 ? "●" : "";
    }
    public String getValorFrecuencia115(){
        return oDetalleSeguimiento.getFrec11() == 80 ? "●" : "";
    }
    public String getValorFrecuencia120(){
        return oDetalleSeguimiento.getFrec12() == 180 ? "●" : "";
    }
    public String getValorFrecuencia121(){
        return oDetalleSeguimiento.getFrec12() == 160 ? "●" : "";
    }
    public String getValorFrecuencia122(){
        return oDetalleSeguimiento.getFrec12() == 140 ? "●" : "";
    }
    public String getValorFrecuencia123(){
        return oDetalleSeguimiento.getFrec12() == 120 ? "●" : "";
    }
    public String getValorFrecuencia124(){
        return oDetalleSeguimiento.getFrec12() == 100 ? "●" : "";
    }
    public String getValorFrecuencia125(){
        return oDetalleSeguimiento.getFrec12() == 80 ? "●" : "";
    }
    public String getValorFrecuencia130(){
        return oDetalleSeguimiento.getFrec13() == 180 ? "●" : "";
    }
    public String getValorFrecuencia131(){
        return oDetalleSeguimiento.getFrec13() == 160 ? "●" : "";
    }
    public String getValorFrecuencia132(){
        return oDetalleSeguimiento.getFrec13() == 140 ? "●" : "";
    }
    public String getValorFrecuencia133(){
        return oDetalleSeguimiento.getFrec13() == 120 ? "●" : "";
    }
    public String getValorFrecuencia134(){
        return oDetalleSeguimiento.getFrec13() == 100 ? "●" : "";
    }
    public String getValorFrecuencia135(){
        return oDetalleSeguimiento.getFrec13() == 80 ? "●" : "";
    }
    public String getValorFrecuencia140(){
        return oDetalleSeguimiento.getFrec14() == 180 ? "●" : "";
    }
    public String getValorFrecuencia141(){
        return oDetalleSeguimiento.getFrec14() == 160 ? "●" : "";
    }
    public String getValorFrecuencia142(){
        return oDetalleSeguimiento.getFrec14() == 140 ? "●" : "";
    }
    public String getValorFrecuencia143(){
        return oDetalleSeguimiento.getFrec14() == 120 ? "●" : "";
    }
    public String getValorFrecuencia144(){
        return oDetalleSeguimiento.getFrec14() == 100 ? "●" : "";
    }
    public String getValorFrecuencia145(){
        return oDetalleSeguimiento.getFrec14() == 80 ? "●" : "";
    }
    public String getValorDilatacion00(){
        return oDetalleSeguimiento.getDilatacion0() == 10 ? "●" : "";
    }
    public String getValorDilatacion01(){
        return oDetalleSeguimiento.getDilatacion0() == 9 ? "●" : "";
    }
    public String getValorDilatacion02(){
        return oDetalleSeguimiento.getDilatacion0() == 8 ? "●" : "";
    }
    public String getValorDilatacion03(){
        return oDetalleSeguimiento.getDilatacion0() == 7 ? "●" : "";
    }
    public String getValorDilatacion04(){
        return oDetalleSeguimiento.getDilatacion0() == 6 ? "●" : "";
    }
    public String getValorDilatacion05(){
        return oDetalleSeguimiento.getDilatacion0() == 5 ? "●" : "";
    }
    public String getValorDilatacion06(){
        return oDetalleSeguimiento.getDilatacion0() == 4 ? "●" : "";
    }
    public String getValorDilatacion07(){
        return oDetalleSeguimiento.getDilatacion0() == 3 ? "●" : "";
    }
    public String getValorDilatacion08(){
        return oDetalleSeguimiento.getDilatacion0() == 2 ? "●" : "";
    }
    public String getValorDilatacion09(){
        return oDetalleSeguimiento.getDilatacion0() == 1 ? "●" : "";
    }
    public String getValorDilatacion10(){
        return oDetalleSeguimiento.getDilatacion1() == 10 ? "●" : "";
    }
    public String getValorDilatacion11(){
        return oDetalleSeguimiento.getDilatacion1() == 9 ? "●" : "";
    }
    public String getValorDilatacion12(){
        return oDetalleSeguimiento.getDilatacion1() == 8 ? "●" : "";
    }
    public String getValorDilatacion13(){
        return oDetalleSeguimiento.getDilatacion1() == 7 ? "●" : "";
    }
    public String getValorDilatacion14(){
        return oDetalleSeguimiento.getDilatacion1() == 6 ? "●" : "";
    }
    public String getValorDilatacion15(){
        return oDetalleSeguimiento.getDilatacion1() == 5 ? "●" : "";
    }
    public String getValorDilatacion16(){
        return oDetalleSeguimiento.getDilatacion1() == 4 ? "●" : "";
    }
    public String getValorDilatacion17(){
        return oDetalleSeguimiento.getDilatacion1() == 3 ? "●" : "";
    }
    public String getValorDilatacion18(){
        return oDetalleSeguimiento.getDilatacion1() == 2 ? "●" : "";
    }
    public String getValorDilatacion19(){
        return oDetalleSeguimiento.getDilatacion1() == 1 ? "●" : "";
    }
    public String getValorDilatacion20(){
        return oDetalleSeguimiento.getDilatacion2() == 10 ? "●" : "";
    }
    public String getValorDilatacion21(){
        return oDetalleSeguimiento.getDilatacion2() == 9 ? "●" : "";
    }
    public String getValorDilatacion22(){
        return oDetalleSeguimiento.getDilatacion2() == 8 ? "●" : "";
    }
    public String getValorDilatacion23(){
        return oDetalleSeguimiento.getDilatacion2() == 7 ? "●" : "";
    }
    public String getValorDilatacion24(){
        return oDetalleSeguimiento.getDilatacion2() == 6 ? "●" : "";
    }
    public String getValorDilatacion25(){
        return oDetalleSeguimiento.getDilatacion2() == 5 ? "●" : "";
    }
    public String getValorDilatacion26(){
        return oDetalleSeguimiento.getDilatacion2() == 4 ? "●" : "";
    }
    public String getValorDilatacion27(){
        return oDetalleSeguimiento.getDilatacion2() == 3 ? "●" : "";
    }
    public String getValorDilatacion28(){
        return oDetalleSeguimiento.getDilatacion2() == 2 ? "●" : "";
    }
    public String getValorDilatacion29(){
        return oDetalleSeguimiento.getDilatacion2() == 1 ? "●" : "";
    }
    public String getValorDilatacion30(){
        return oDetalleSeguimiento.getDilatacion3() == 10 ? "●" : "";
    }
    public String getValorDilatacion31(){
        return oDetalleSeguimiento.getDilatacion3() == 9 ? "●" : "";
    }
    public String getValorDilatacion32(){
        return oDetalleSeguimiento.getDilatacion3() == 8 ? "●" : "";
    }
    public String getValorDilatacion33(){
        return oDetalleSeguimiento.getDilatacion3() == 7 ? "●" : "";
    }
    public String getValorDilatacion34(){
        return oDetalleSeguimiento.getDilatacion3() == 6 ? "●" : "";
    }
    public String getValorDilatacion35(){
        return oDetalleSeguimiento.getDilatacion3() == 5 ? "●" : "";
    }
    public String getValorDilatacion36(){
        return oDetalleSeguimiento.getDilatacion3() == 4 ? "●" : "";
    }
    public String getValorDilatacion37(){
        return oDetalleSeguimiento.getDilatacion3() == 3 ? "●" : "";
    }
    public String getValorDilatacion38(){
        return oDetalleSeguimiento.getDilatacion3() == 2 ? "●" : "";
    }
    public String getValorDilatacion39(){
        return oDetalleSeguimiento.getDilatacion3() == 1 ? "●" : "";
    }
    public String getValorDilatacion40(){
        return oDetalleSeguimiento.getDilatacion4() == 10 ? "●" : "";
    }
    public String getValorDilatacion41(){
        return oDetalleSeguimiento.getDilatacion4() == 9 ? "●" : "";
    }
    public String getValorDilatacion42(){
        return oDetalleSeguimiento.getDilatacion4() == 8 ? "●" : "";
    }
    public String getValorDilatacion43(){
        return oDetalleSeguimiento.getDilatacion4() == 7 ? "●" : "";
    }
    public String getValorDilatacion44(){
        return oDetalleSeguimiento.getDilatacion4() == 6 ? "●" : "";
    }
    public String getValorDilatacion45(){
        return oDetalleSeguimiento.getDilatacion4() == 5 ? "●" : "";
    }
    public String getValorDilatacion46(){
        return oDetalleSeguimiento.getDilatacion4() == 4 ? "●" : "";
    }
    public String getValorDilatacion47(){
        return oDetalleSeguimiento.getDilatacion4() == 3 ? "●" : "";
    }
    public String getValorDilatacion48(){
        return oDetalleSeguimiento.getDilatacion4() == 2 ? "●" : "";
    }
    public String getValorDilatacion49(){
        return oDetalleSeguimiento.getDilatacion4() == 1 ? "●" : "";
    }
    public String getValorDilatacion50(){
        return oDetalleSeguimiento.getDilatacion5() == 10 ? "●" : "";
    }
    public String getValorDilatacion51(){
        return oDetalleSeguimiento.getDilatacion5() == 9 ? "●" : "";
    }
    public String getValorDilatacion52(){
        return oDetalleSeguimiento.getDilatacion5() == 8 ? "●" : "";
    }
    public String getValorDilatacion53(){
        return oDetalleSeguimiento.getDilatacion5() == 7 ? "●" : "";
    }
    public String getValorDilatacion54(){
        return oDetalleSeguimiento.getDilatacion5() == 6 ? "●" : "";
    }
    public String getValorDilatacion55(){
        return oDetalleSeguimiento.getDilatacion5() == 5 ? "●" : "";
    }
    public String getValorDilatacion56(){
        return oDetalleSeguimiento.getDilatacion5() == 4 ? "●" : "";
    }
    public String getValorDilatacion57(){
        return oDetalleSeguimiento.getDilatacion5() == 3 ? "●" : "";
    }
    public String getValorDilatacion58(){
        return oDetalleSeguimiento.getDilatacion5() == 2 ? "●" : "";
    }
    public String getValorDilatacion59(){
        return oDetalleSeguimiento.getDilatacion5() == 1 ? "●" : "";
    }
    public String getValorDilatacion60(){
        return oDetalleSeguimiento.getDilatacion6() == 10 ? "●" : "";
    }
    public String getValorDilatacion61(){
        return oDetalleSeguimiento.getDilatacion6() == 9 ? "●" : "";
    }
    public String getValorDilatacion62(){
        return oDetalleSeguimiento.getDilatacion6() == 8 ? "●" : "";
    }
    public String getValorDilatacion63(){
        return oDetalleSeguimiento.getDilatacion6() == 7 ? "●" : "";
    }
    public String getValorDilatacion64(){
        return oDetalleSeguimiento.getDilatacion6() == 6 ? "●" : "";
    }
    public String getValorDilatacion65(){
        return oDetalleSeguimiento.getDilatacion6() == 5 ? "●" : "";
    }
    public String getValorDilatacion66(){
        return oDetalleSeguimiento.getDilatacion6() == 4 ? "●" : "";
    }
    public String getValorDilatacion67(){
        return oDetalleSeguimiento.getDilatacion6() == 3 ? "●" : "";
    }
    public String getValorDilatacion68(){
        return oDetalleSeguimiento.getDilatacion6() == 2 ? "●" : "";
    }
    public String getValorDilatacion69(){
        return oDetalleSeguimiento.getDilatacion6() == 1 ? "●" : "";
    }
    public String getValorDilatacion70(){
        return oDetalleSeguimiento.getDilatacion7() == 10 ? "●" : "";
    }
    public String getValorDilatacion71(){
        return oDetalleSeguimiento.getDilatacion7() == 9 ? "●" : "";
    }
    public String getValorDilatacion72(){
        return oDetalleSeguimiento.getDilatacion7() == 8 ? "●" : "";
    }
    public String getValorDilatacion73(){
        return oDetalleSeguimiento.getDilatacion7() == 7 ? "●" : "";
    }
    public String getValorDilatacion74(){
        return oDetalleSeguimiento.getDilatacion7() == 6 ? "●" : "";
    }
    public String getValorDilatacion75(){
        return oDetalleSeguimiento.getDilatacion7() == 5 ? "●" : "";
    }
    public String getValorDilatacion76(){
        return oDetalleSeguimiento.getDilatacion7() == 4 ? "●" : "";
    }
    public String getValorDilatacion77(){
        return oDetalleSeguimiento.getDilatacion7() == 3 ? "●" : "";
    }
    public String getValorDilatacion78(){
        return oDetalleSeguimiento.getDilatacion7() == 2 ? "●" : "";
    }
    public String getValorDilatacion79(){
        return oDetalleSeguimiento.getDilatacion7() == 1 ? "●" : "";
    }
    public String getValorDilatacion80(){
        return oDetalleSeguimiento.getDilatacion8() == 10 ? "●" : "";
    }
    public String getValorDilatacion81(){
        return oDetalleSeguimiento.getDilatacion8() == 9 ? "●" : "";
    }
    public String getValorDilatacion82(){
        return oDetalleSeguimiento.getDilatacion8() == 8 ? "●" : "";
    }
    public String getValorDilatacion83(){
        return oDetalleSeguimiento.getDilatacion8() == 7 ? "●" : "";
    }
    public String getValorDilatacion84(){
        return oDetalleSeguimiento.getDilatacion8() == 6 ? "●" : "";
    }
    public String getValorDilatacion85(){
        return oDetalleSeguimiento.getDilatacion8() == 5 ? "●" : "";
    }
    public String getValorDilatacion86(){
        return oDetalleSeguimiento.getDilatacion8() == 4 ? "●" : "";
    }
    public String getValorDilatacion87(){
        return oDetalleSeguimiento.getDilatacion8() == 3 ? "●" : "";
    }
    public String getValorDilatacion88(){
        return oDetalleSeguimiento.getDilatacion8() == 2 ? "●" : "";
    }
    public String getValorDilatacion89(){
        return oDetalleSeguimiento.getDilatacion8() == 1 ? "●" : "";
    }
    public String getValorDilatacion90(){
        return oDetalleSeguimiento.getDilatacion9() == 10 ? "●" : "";
    }
    public String getValorDilatacion91(){
        return oDetalleSeguimiento.getDilatacion9() == 9 ? "●" : "";
    }
    public String getValorDilatacion92(){
        return oDetalleSeguimiento.getDilatacion9() == 8 ? "●" : "";
    }
    public String getValorDilatacion93(){
        return oDetalleSeguimiento.getDilatacion9() == 7 ? "●" : "";
    }
    public String getValorDilatacion94(){
        return oDetalleSeguimiento.getDilatacion9() == 6 ? "●" : "";
    }
    public String getValorDilatacion95(){
        return oDetalleSeguimiento.getDilatacion9() == 5 ? "●" : "";
    }
    public String getValorDilatacion96(){
        return oDetalleSeguimiento.getDilatacion9() == 4 ? "●" : "";
    }
    public String getValorDilatacion97(){
        return oDetalleSeguimiento.getDilatacion9() == 3 ? "●" : "";
    }
    public String getValorDilatacion98(){
        return oDetalleSeguimiento.getDilatacion9() == 2 ? "●" : "";
    }
    public String getValorDilatacion99(){
        return oDetalleSeguimiento.getDilatacion9() == 1 ? "●" : "";
    }
    public String getValorDilatacion100(){
        return oDetalleSeguimiento.getDilatacion10() == 10 ? "●" : "";
    }
    public String getValorDilatacion101(){
        return oDetalleSeguimiento.getDilatacion10() == 9 ? "●" : "";
    }
    public String getValorDilatacion102(){
        return oDetalleSeguimiento.getDilatacion10() == 8 ? "●" : "";
    }
    public String getValorDilatacion103(){
        return oDetalleSeguimiento.getDilatacion10() == 7 ? "●" : "";
    }
    public String getValorDilatacion104(){
        return oDetalleSeguimiento.getDilatacion10() == 6 ? "●" : "";
    }
    public String getValorDilatacion105(){
        return oDetalleSeguimiento.getDilatacion10() == 5 ? "●" : "";
    }
    public String getValorDilatacion106(){
        return oDetalleSeguimiento.getDilatacion10() == 4 ? "●" : "";
    }
    public String getValorDilatacion107(){
        return oDetalleSeguimiento.getDilatacion10() == 3 ? "●" : "";
    }
    public String getValorDilatacion108(){
        return oDetalleSeguimiento.getDilatacion10() == 2 ? "●" : "";
    }
    public String getValorDilatacion109(){
        return oDetalleSeguimiento.getDilatacion10() == 1 ? "●" : "";
    }
    public String getValorDilatacion110(){
        return oDetalleSeguimiento.getDilatacion11() == 10 ? "●" : "";
    }
    public String getValorDilatacion111(){
        return oDetalleSeguimiento.getDilatacion11() == 9 ? "●" : "";
    }
    public String getValorDilatacion112(){
        return oDetalleSeguimiento.getDilatacion11() == 8 ? "●" : "";
    }
    public String getValorDilatacion113(){
        return oDetalleSeguimiento.getDilatacion11() == 7 ? "●" : "";
    }
    public String getValorDilatacion114(){
        return oDetalleSeguimiento.getDilatacion11() == 6 ? "●" : "";
    }
    public String getValorDilatacion115(){
        return oDetalleSeguimiento.getDilatacion11() == 5 ? "●" : "";
    }
    public String getValorDilatacion116(){
        return oDetalleSeguimiento.getDilatacion11() == 4 ? "●" : "";
    }
    public String getValorDilatacion117(){
        return oDetalleSeguimiento.getDilatacion11() == 3 ? "●" : "";
    }
    public String getValorDilatacion118(){
        return oDetalleSeguimiento.getDilatacion11() == 2 ? "●" : "";
    }
    public String getValorDilatacion119(){
        return oDetalleSeguimiento.getDilatacion11() == 1 ? "●" : "";
    }
    public String getValorDilatacion120(){
        return oDetalleSeguimiento.getDilatacion12() == 10 ? "●" : "";
    }
    public String getValorDilatacion121(){
        return oDetalleSeguimiento.getDilatacion12() == 9 ? "●" : "";
    }
    public String getValorDilatacion122(){
        return oDetalleSeguimiento.getDilatacion12() == 8 ? "●" : "";
    }
    public String getValorDilatacion123(){
        return oDetalleSeguimiento.getDilatacion12() == 7 ? "●" : "";
    }
    public String getValorDilatacion124(){
        return oDetalleSeguimiento.getDilatacion12() == 6 ? "●" : "";
    }
    public String getValorDilatacion125(){
        return oDetalleSeguimiento.getDilatacion12() == 5 ? "●" : "";
    }
    public String getValorDilatacion126(){
        return oDetalleSeguimiento.getDilatacion12() == 4 ? "●" : "";
    }
    public String getValorDilatacion127(){
        return oDetalleSeguimiento.getDilatacion12() == 3 ? "●" : "";
    }
    public String getValorDilatacion128(){
        return oDetalleSeguimiento.getDilatacion12() == 2 ? "●" : "";
    }
    public String getValorDilatacion129(){
        return oDetalleSeguimiento.getDilatacion12() == 1 ? "●" : "";
    }
    public String getValorDilatacion130(){
        return oDetalleSeguimiento.getDilatacion13() == 10 ? "●" : "";
    }
    public String getValorDilatacion131(){
        return oDetalleSeguimiento.getDilatacion13() == 9 ? "●" : "";
    }
    public String getValorDilatacion132(){
        return oDetalleSeguimiento.getDilatacion13() == 8 ? "●" : "";
    }
    public String getValorDilatacion133(){
        return oDetalleSeguimiento.getDilatacion13() == 7 ? "●" : "";
    }
    public String getValorDilatacion134(){
        return oDetalleSeguimiento.getDilatacion13() == 6 ? "●" : "";
    }
    public String getValorDilatacion135(){
        return oDetalleSeguimiento.getDilatacion13() == 5 ? "●" : "";
    }
    public String getValorDilatacion136(){
        return oDetalleSeguimiento.getDilatacion13() == 4 ? "●" : "";
    }
    public String getValorDilatacion137(){
        return oDetalleSeguimiento.getDilatacion13() == 3 ? "●" : "";
    }
    public String getValorDilatacion138(){
        return oDetalleSeguimiento.getDilatacion13() == 2 ? "●" : "";
    }
    public String getValorDilatacion139(){
        return oDetalleSeguimiento.getDilatacion13() == 1 ? "●" : "";
    }
    public String getValorDilatacion140(){
        return oDetalleSeguimiento.getDilatacion14() == 10 ? "●" : "";
    }
    public String getValorDilatacion141(){
        return oDetalleSeguimiento.getDilatacion14() == 9 ? "●" : "";
    }
    public String getValorDilatacion142(){
        return oDetalleSeguimiento.getDilatacion14() == 8 ? "●" : "";
    }
    public String getValorDilatacion143(){
        return oDetalleSeguimiento.getDilatacion14() == 7 ? "●" : "";
    }
    public String getValorDilatacion144(){
        return oDetalleSeguimiento.getDilatacion14() == 6 ? "●" : "";
    }
    public String getValorDilatacion145(){
        return oDetalleSeguimiento.getDilatacion14() == 5 ? "●" : "";
    }
    public String getValorDilatacion146(){
        return oDetalleSeguimiento.getDilatacion14() == 4 ? "●" : "";
    }
    public String getValorDilatacion147(){
        return oDetalleSeguimiento.getDilatacion14() == 3 ? "●" : "";
    }
    public String getValorDilatacion148(){
        return oDetalleSeguimiento.getDilatacion14() == 2 ? "●" : "";
    }
    public String getValorDilatacion149(){
        return oDetalleSeguimiento.getDilatacion14() == 1 ? "●" : "";
    }
    public String getValorHorasParto00(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 1 ? "X" : "";
    }
    public String getValorHorasParto10(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 2 ? "X" : "";
    }
    public String getValorHorasParto20(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 3 ? "X" : "";
    }
    public String getValorHorasParto30(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 4 ? "X" : "";
    }
    public String getValorHorasParto40(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 5 ? "X" : "";
    }
    public String getValorHorasParto50(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 6 ? "X" : "";
    }
    public String getValorHorasParto60(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 7 ? "X" : "";
    }
    public String getValorHorasParto70(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 8 ? "X" : "";
    }
    public String getValorHorasParto80(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 9 ? "X" : "";
    }
    public String getValorHorasParto90(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 10 ? "X" : "";
    }
    public String getValorHorasParto100(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 11 ? "X" : "";
    }
    public String getValorHorasParto110(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 12 ? "X" : "";
    }
    public String getValorHorasParto120(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 13 ? "X" : "";
    }
    public String getValorHorasParto130(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 14 ? "X" : "";
    }
    public String getValorHorasParto140(){
        return oDetalleSeguimiento.getHoraEstanciaParto() == 15 ? "X" : "";
    }
    public String valorTa(short opcion){
        if(arrDetalleSeguimientoSignosvitales != null && arrDetalleSeguimientoSignosvitales.length >= opcion)
            return arrDetalleSeguimientoSignosvitales[opcion].getPartoGrama().getEpiMed().getSignosVitales().getTA() != null || !arrDetalleSeguimientoSignosvitales[opcion].getPartoGrama().getEpiMed().getSignosVitales().getTA().isEmpty() ? arrDetalleSeguimientoSignosvitales[opcion].getPartoGrama().getEpiMed().getSignosVitales().getTA() : "";
        return null;
    }
    public String valorPulso(short opcion){
        if(arrDetalleSeguimientoSignosvitales != null && arrDetalleSeguimientoSignosvitales.length >= opcion)
            return arrDetalleSeguimientoSignosvitales[opcion].getPartoGrama().getEpiMed().getSignosVitales().getPulso() == 0 ? "" : arrDetalleSeguimientoSignosvitales[opcion].getPartoGrama().getEpiMed().getSignosVitales().getPulso() + "";
        return null;
    }
    public String valorTemperatura(short opcion){
        if(arrDetalleSeguimientoSignosvitales != null && arrDetalleSeguimientoSignosvitales.length >= opcion)
            return arrDetalleSeguimientoSignosvitales[opcion].getPartoGrama().getEpiMed().getSignosVitales().getTemp() != null || !arrDetalleSeguimientoSignosvitales[opcion].getPartoGrama().getEpiMed().getSignosVitales().getTemp().isEmpty() ? arrDetalleSeguimientoSignosvitales[opcion].getPartoGrama().getEpiMed().getSignosVitales().getTemp() : "";
        return null;
    }
        public String getValorBloqueo0(){
            return oDetalleSeguimiento.getBloqueo0() ? "(B)" : "( )";
        }
        public String getValorBloqueo1(){
            return oDetalleSeguimiento.getBloqueo1() ? "(B)" : "( )";
        }
        public String getValorBloqueo2(){
            return oDetalleSeguimiento.getBloqueo2() ? "(B)" : "( )";
        }
        public String getValorBloqueo3(){
            return oDetalleSeguimiento.getBloqueo3() ? "(B)" : "( )";
        }
        public String getValorBloqueo4(){
            return oDetalleSeguimiento.getBloqueo4() ? "(B)" : "( )";
        }
        public String getValorBloqueo5(){
            return oDetalleSeguimiento.getBloqueo5() ? "(B)" : "( )";
        }
        public String getValorBloqueo6(){
            return oDetalleSeguimiento.getBloqueo6() ? "(B)" : "( )";
        }
        public String getValorSedacion0(){
            return oDetalleSeguimiento.getSedacion0() ? "(S)" : "( )";
        }
        public String getValorSedacion1(){
            return oDetalleSeguimiento.getSedacion1() ? "(S)" : "( )";
        }
        public String getValorSedacion2(){
            return oDetalleSeguimiento.getSedacion2() ? "(S)" : "( )";
        }
        public String getValorSedacion3(){
            return oDetalleSeguimiento.getSedacion3() ? "(S)" : "( )";
        }
        public String getValorSedacion4(){
            return oDetalleSeguimiento.getSedacion4() ? "(S)" : "( )";
        }
        public String getValorSedacion5(){
            return oDetalleSeguimiento.getSedacion5() ? "(S)" : "( )";
        }
        public String getValorSedacion6(){
            return oDetalleSeguimiento.getSedacion6() ? "(S)" : "( )";
        }
        public ArrayList<Producto> getArrDetalleProducto(){
            return arrDetalleProducto;
        }
        public void setArrDetalleProducto(ArrayList<Producto> arrDetalleProducto){
            this.arrDetalleProducto = arrDetalleProducto;
        }
        public String getPulso1(){
            return sPulso1;
        }
        public void setPulso1(String sPulso1){
            this.sPulso1 = sPulso1;
        }
        public String getPulso2(){
            return sPulso2;
        }
        public void setPulso2(String sPulso2){
            this.sPulso2 = sPulso2;
        }
        public String getPulso3(){
            return sPulso3;
        }
        public void setPulso3(String sPulso3){
            this.sPulso3 = sPulso3;
        }
        public String getPulso4(){
            return sPulso4;
        }
        public void setPulso4(String sPulso4){
            this.sPulso4 = sPulso4;
        }
        public String getPulso5(){
            return sPulso5;
        }
        public void setPulso5(String sPulso5){
            this.sPulso5 = sPulso5;
        }
        public String getPulso6(){
            return sPulso6;
        }
        public void setPulso6(String sPulso6){
            this.sPulso6 = sPulso6;
        }
        public String getPulso7(){
            return sPulso7;
        }
        public void setPulso7(String sPulso7){
            this.sPulso7 = sPulso7;
        }
        public Date getDetalleFechaNacimiento(){
            if(arrDetalleProducto.isEmpty())
                return null;
            else
                return arrDetalleProducto.get(0).getFechaNacimiento();
            
        }   
        public int getDetalleSemanasAmenorrea(){
            if(arrDetalleProducto.isEmpty())
                return 0;
            else
                return arrDetalleProducto.get(0).getTerminacionEmbarazo().getPartoGrama().getSegundaMitadEmbarazo().getSemanasAmerorrea();
        }
        public String detalleDegarro(short opcion){            
            if(opcion == 0)
                return oPartoEutocico.getDesgarro().get(opcion).getValor()== null || oPartoEutocico.getDesgarro().get(opcion).getValor().isEmpty() ? "PERINÉ ( )" : oPartoEutocico.getDesgarro().get(opcion).getValor() + "(X)";
            if(opcion == 1)
                return oPartoEutocico.getDesgarro().get(opcion).getValor() == null || oPartoEutocico.getDesgarro().get(opcion).getValor().isEmpty() ? "VAGINA ( )" : oPartoEutocico.getDesgarro().get(opcion).getValor() + "(X)";
            if(opcion == 2)
                return oPartoEutocico.getDesgarro().get(opcion).getValor() == null || oPartoEutocico.getDesgarro().get(opcion).getValor().isEmpty() ? "CÉRVIX ( )" : oPartoEutocico.getDesgarro().get(opcion).getValor() + "(X)";
            return "";
        }
        public String valorArrDetalleEvolucionMedico(){
            if(!arrEvolucion.isEmpty())
                return arrEvolucion.get(0).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto();
            return null;
        }
        public String valorArrDetalleCedMedico(){
            if(arrEvolucion.size() != 0)
                return arrEvolucion.get(0).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getCedProf();
            return null;
        }
        public void asignaSignosVitales(){
            if(this.arrDetalleSeguimientoSignosvitales != null)                
                for(int i = 0; i < this.arrDetalleSeguimientoSignosvitales.length; i++)
                    if(i == 0){
                        this.setRegistro1(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length >= i ? this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() : new SignosVitales());
                        if(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length > i)
                            this.setPulso1(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() == 0 ? "" : this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() + "");
                    }
                    else if(i == 1){
                        this.setRegistro2(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length >= i ? this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() : new SignosVitales());
                        if(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length > i)
                            this.setPulso2(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() == 0 ? "" : this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() + "");
                    }
                    else if(i == 2){
                        this.setRegistro3(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length >= i ? this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() : new SignosVitales());
                        if(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length > i)
                            this.setPulso3(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() == 0 ? "" : this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() + "");
                    }
                    else if(i == 3){
                        this.setRegistro4(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length >= i ? this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() : new SignosVitales());
                        if(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length > i)
                            this.setPulso4(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() == 0 ? "" : this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() + "");
                    }
                    else if(i == 4){
                        this.setRegistro5(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length >= i ? this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() : new SignosVitales());
                        if(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length > i)
                            this.setPulso5(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() == 0 ? "" : this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() + "");
                    }
                    else if(i == 5){
                        this.setRegistro6(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length >= i ? this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() : new SignosVitales());
                        if(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length > i)
                            this.setPulso6(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() == 0 ? "" : this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() + "");
                    }
                    else if(i == 6){
                        this.setRegistro7(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length >= i ? this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() : new SignosVitales());               
                        if(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales() != null || this.arrDetalleSeguimientoSignosvitales.length > i)
                            this.setPulso7(this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() == 0 ? "" : this.arrDetalleSeguimientoSignosvitales[i].getPartoGrama().getEpiMed().getSignosVitales().getPulso() + "");
                    }
        }
        public void asignaPelvis(){
            if(this.oSeguimientoTrabajoParto != null){
                if(!this.oSeguimientoTrabajoParto.getPelvis().isEmpty() && this.oSeguimientoTrabajoParto.getPelvis().size() > 0)
                    for(int i = 0; i < this.oSeguimientoTrabajoParto.getPelvis().size(); i++)
                        if(i == 1){
                            this.getPelvis().setSuperior(this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro() != null || !this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro().isEmpty() ? this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro().trim() + "" + this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getTipoParametro() : "");
                            this.asignaPelviSuperior();
                        }else{ 
                            if( i == 2){
                                this.getPelvis().setMedio(this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro() != null || !this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro().isEmpty() ? this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro().trim() + "" + this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getTipoParametro() : "");
                                this.asignaPelviMedio();
                            }else{
                                if(i == 3){
                                    this.getPelvis().setInferior(this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro() != null || !this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro().isEmpty() ? this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getClaveParametro().trim() + "" + this.oSeguimientoTrabajoParto.getPelvis().get(i).getValores().getTipoParametro() : "");
                                    this.asignaPelvInferior();
                                }
                            }
                        }
            }   
        }
        public void habilitaHoras(){
            if(this.oSeguimientoTrabajoParto != null){
                if(this.oSeguimientoTrabajoParto.getHora0() != null){
                    this.HControl0(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora1() != null){
                    this.HControl1(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora2() != null){
                    this.HControl2(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora3() != null){
                    this.HControl3(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora4() != null){
                    this.HControl4(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora5() != null){
                    this.HControl5(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora6() != null){
                    this.HControl6(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora7() != null){
                    this.HControl7(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora8() != null){
                    this.HControl8(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora9() != null){
                    this.HControl9(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora10() != null){
                    this.HControl10(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora11() != null){
                    this.HControl11(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora12() != null){
                    this.HControl12(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora13() != null){
                    this.HControl13(null);
                }
                if(this.oSeguimientoTrabajoParto.getHora14() != null){
                    this.HControl14(null);
                }
            }
        }
        public void asignaDesgarro(){
            this.setDesgarroPerine(this.oPartoEutocico.getDesgarro().get(0).getValor() == null || this.oPartoEutocico.getDesgarro().get(0).getValor().compareTo("") == 0 ? false : true);
            this.asignaPerine();
            this.setDesgarroVagina(this.oPartoEutocico.getDesgarro().get(1).getValor() == null || this.oPartoEutocico.getDesgarro().get(1).getValor().compareTo("") == 0 ? false : true);
            this.asignaVagina();
            this.setDesgarroCervix(this.oPartoEutocico.getDesgarro().get(2).getValor() == null || this.oPartoEutocico.getDesgarro().get(2).getValor().compareTo("") == 0 ? false : true);
            this.asignaCervix();
        }
        public void asignaFechaLegrado(){
            if(oLegrado.getFecha() != null){
                this.oLegrado.setHora(this.oLegrado.getFecha());             
            }
        } 
        public void asignaMedicoEvolucion(){            
            if(!this.arrEvolucion.isEmpty()){                
                this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres(arrEvolucion.get(0).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());
                this.setNombreMedicoEvolucion(arrEvolucion.get(0).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());
                this.asignaCedula7();
            }
        }
        public boolean getRequeridoEvolucion(){
            return !this.arrEvolucion.isEmpty();
        }
        public void asignaFPreoperatoria(){
            if(this.oNotaPreoperatoria.getFechaRegistro() != null){
                this.dFPreoperatoria = this.oNotaPreoperatoria.getFechaRegistro();
                this.dHPreoperatoria = this.oNotaPreoperatoria.getFechaRegistro();                
            }else{
                this.dFPreoperatoria = null;
                this.dHPreoperatoria = null;
            }            
            
        }        
        public void asignaFechaPost(){
            if(this.npotp.getFecha() != null){
                this.fecha = this.npotp.getFecha();
                this.hora = this.npotp.getFecha();                
            }else{
                this.fecha = null;
                this.hora = null;
            }
        }        
        public boolean getHabilitaFechaPreoperatoria(){
            return this.dFPreoperatoria != null;
        }        
        public boolean getHabilitaFechaPostoclusion(){
            return this.fecha != null;
        }
        public void fechaSegundaMitadBase(){
            if(this.oSegundaMitadEmbarazo != null && this.oSegundaMitadEmbarazo.getFecha() != null){
                DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat hora = new SimpleDateFormat("HH:mm");                
                try{
                    this.setFechaSegundaMitadEmbarazo(fecha.parse(fecha.format(this.oSegundaMitadEmbarazo.getFecha())));
                    this.setHoraSegundaMitadEmbarazo(hora.parse(hora.format(this.oSegundaMitadEmbarazo.getFecha())));                
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        
        public boolean getRequeridoCondicionesingreso(){
            if(this.oSegundaMitadEmbarazo != null)
                return this.oSegundaMitadEmbarazo.getOtrasCondionesIngreso() == null || this.oSegundaMitadEmbarazo.getOtrasCondionesIngreso().isEmpty() ? !true : !false;                            
            else
                return false;
        }
        public void asignaDatosPrimeraMitadEmbarazo(){
            if(this.oPrimeraMitadEmbarazo != null){
                this.setDolorTemp(this.oPrimeraMitadEmbarazo.getDolorIntesidad() == 0 ? "" : this.oPrimeraMitadEmbarazo.getDolorIntesidad() + "");
                this.asignaDolor();
                this.asignaCedula();
            }else
                this.oPrimeraMitadEmbarazo = new PrimeraMitadEmbarazo();                
        }
        public void asignaPulso1(){            
            this.oRegistro1.setPulso(this.sPulso1 == null || this.sPulso1.isEmpty() ? 0 : Integer.parseInt(this.sPulso1));            
        }
        public void asignaPulso2(){
            this.oRegistro2.setPulso(this.sPulso2 == null || this.sPulso2.isEmpty() ? 0 : Integer.parseInt(this.sPulso2));
        }
        public void asignaPulso3(){
            this.oRegistro3.setPulso(this.sPulso3 == null || this.sPulso3.isEmpty() ? 0 : Integer.parseInt(this.sPulso3));
        }
        public void asignaPulso4(){
            this.oRegistro4.setPulso(this.sPulso4 == null || this.sPulso4.isEmpty() ? 0 : Integer.parseInt(this.sPulso4));
        }
        public void asignaPulso5(){
            this.oRegistro5.setPulso(this.sPulso5 == null || this.sPulso5.isEmpty() ? 0 : Integer.parseInt(this.sPulso5));
        }
        public void asignaPulso6(){
            this.oRegistro6.setPulso(this.sPulso6 == null || this.sPulso6.isEmpty() ? 0 : Integer.parseInt(this.sPulso6));
        }
        public void asignaPulso7(){
            this.oRegistro7.setPulso(this.sPulso7 == null || this.sPulso7.isEmpty() ? 0 : Integer.parseInt(this.sPulso7));
        }
        public void asignaPulsoBD(){
            
        }
        public void asignaPelvisBD(){
            if(this.oSeguimientoTrabajoParto.getPelvis() == null || this.oSeguimientoTrabajoParto.getPelvis().isEmpty()){
                this.oSeguimientoTrabajoParto.setPelvis(new ArrayList<Pelvis>());                
                this.oSeguimientoTrabajoParto.getPelvis().add(this.oPelvis);                
            }else{
                this.asignaPelvis();
            }
        }
        
        public void habilitaComplete3(){
            this.bHabilitaCompletePostoperatorio = this.npotp.getDiagnosticoPostoclusion().getClave().compareTo("") != 0;
        }
        public void habilitaComplete2(){
            this.bComplete2 = this.oNotaPreoperatoria.getDiagnosticoPreoperatorio().getClave().compareTo("") != 0;
        }
        //habilitado = false //deshabilitado = true 
        public boolean getAlumbraminetoMotivo(){
            return this.bAlumbramientoMotivo;
        }
        public void setAlumbramientoMotivo(boolean bAlumbramientoMotivo){
            this.bAlumbramientoMotivo = bAlumbramientoMotivo;
        }
        public boolean getProlongacionHisterectomia(){
            return this.bHisterectomia;
        }
        public void setProlongacionHisterectomia(boolean bHisterectomia){
            this.bHisterectomia = bHisterectomia;
        }
        public boolean getAnalgesia1(){
            return this.bAnalgesia1;
        }
        public void setAnalgesia1(boolean bAnalgesia1){
            this.bAnalgesia1 = bAnalgesia1;
        }
        public boolean getAnalgesia2(){
            return this.bAnalgesia2;
        }
        public void setAnalgesia2(boolean bAnalgesia2){
            this.bAnalgesia2 = bAnalgesia2;
        }
        public boolean getDescribir1(){
            return this.bDescribir1;
        }
        public void setDescribir1(boolean bDescribir1){
            this.bDescribir1 = bDescribir1;
        }
        public boolean getDescribir2(){
            return this.bDescribir2;
        }
        public void setDescribir2(boolean bDescribir2){
            this.bDescribir2 = bDescribir2;
        }
        public boolean getBanderaPartoEutocicoCesarea(){
            return this.bPartoEutocicoCesarea;
        }
        public void setBanderaPartoEutocicoCesarea(boolean bPartoEutocicoCesarea){
            this.bPartoEutocicoCesarea = bPartoEutocicoCesarea;
        }
        public String getNombreMedicoEvolucion(){
            return this.sNombreMedEvolucion;
        }
        public void setNombreMedicoEvolucion(String sNombreMedEvolucion){
            this.sNombreMedEvolucion = sNombreMedEvolucion;
        }
        public String getCedula(){
            return this.sCedula;
        }
        public void setCedula(String sCedula){
            this.sCedula = sCedula;
        }
        public int getTarjeta(){
            return this.nTarjeta;
        }
        public void setTarjeta(int nTarjeta){
            this.nTarjeta = nTarjeta;
        }
        public void banderaAlumbramientoMotivo(){
            this.bAlumbramientoMotivo = this.oAlumbramiento.getRecCavidadUterina() ? !true : !false;
            if(this.bAlumbramientoMotivo)
                this.oAlumbramiento.setMotivo("");
        }
        public void banderaProlongacionHisterectomia(){
            this.bHisterectomia = this.oCesarea.getPHisteroctomia() ? !true : !false;
            if(this.bHisterectomia)
                this.oCesarea.setDescribir("");
        }
        public void banderaAnalgesiaTipo1(){
            this.bAnalgesia1 = this.oAnalgesiaTparto.getAnalgesiaTparto() ? !true : !false;
            if(this.bAnalgesia1){
                this.oAnalgesiaTparto.setTipo("");
                this.oAnalgesiaTparto.setResultado("");
                this.oAnalgesiaTparto.setComplicaciones("");
            }
                
        }
        public void banderaAnalgesiaTipo2(){
            this.bAnalgesia2 = this.oAnalgesiaTparto.getPartoLegrado() ? !true : !false;
            if(this.bAnalgesia2)
                this.oPartoLegrado.setTipo("");
        }
        public boolean habilitaMedicoAnalgesia(){
            return (this.bAnalgesia1 == false || this.bAnalgesia2 == false) ? true : false;
        }
        public void habilitaDescribir1(){
            this.bDescribir1 = this.oPlacenta.getCordon().getClaveParametro() == null || this.oPlacenta.getCordon().getClaveParametro().isEmpty() ? true : false;
            if(this.bDescribir1)
                this.oPlacenta.setDescribir("");
        }
        public void habilitaDescribir2(){
            this.bDescribir2 = this.oPlacenta.getCordon().getTipoParametro() == null || this.oPlacenta.getCordon().getTipoParametro().isEmpty() ? true : false;
            if(this.bDescribir2)
                this.oPlacenta.setDescribir1("");
        }
        public void habilitaPartoEutocicoCesarea(){
            this.bPartoEutocicoCesarea = this.oPartoEutocico.getVariedadPosicion() == null || this.oPartoEutocico.getVariedadPosicion().isEmpty() ? true : false;
            if(this.bPartoEutocicoCesarea){
                this.oPartoEutocico.setVariedadPosicion("");
                this.oPartoEutocico.setEpisiotomia("");
                this.oPartoEutocico.setProlongacion("");
                this.setDesgarroPerine(false);
                this.asignaPerine();
                this.setDesgarroVagina(false);
                this.asignaVagina();
                this.setDesgarroCervix(false);
                this.asignaCervix();
                this.oPartoEutocico.setObservaciones("");
                this.oForceps.getVariedadPosicion().setClaveParametro("");
                this.oForceps.setIndicacionPrincipal("");
                this.oForceps.setInstrumento("");
                this.oForceps.setObservaciones("");
            }else{
                this.oCesarea.getCesarea1().setClaveParametro("");
                this.oCesarea.setOtras("");
                this.oCesarea.setIndicacionPrincipal("");
                this.oCesarea.setPHisteroctomia(false);
                this.oCesarea.setDescribir("");
                this.oCesarea.setExtraccionProducto("");
                this.oCesarea.setComplicaciones("");
                this.oCesarea.setOtrasObservaciones("");
            }
        }
/********TERMINAN METODOS SET'S Y GTE'S DE LAS PROPIEDADES****************/    
}