package mx.gob.hrrb.jbs.archivo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DetalleReceta;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.NotaEmbarazo;
import mx.gob.hrrb.modelo.core.NotaMedicaHRRB;
import mx.gob.hrrb.modelo.core.Producto;
import mx.gob.hrrb.modelo.core.Receta;
import mx.gob.hrrb.modelo.core.Traslado;
import mx.gob.hrrb.modelo.core.notas.Alumbramiento;
import mx.gob.hrrb.modelo.core.notas.AnalgesiaTparto;
import mx.gob.hrrb.modelo.core.notas.AtencionNeonatalTococirugia;
import mx.gob.hrrb.modelo.core.notas.CalificacionApgar;
import mx.gob.hrrb.modelo.core.notas.CalificacionSilvermann;
import mx.gob.hrrb.modelo.core.notas.Cesarea;
import mx.gob.hrrb.modelo.core.notas.EvaluacionEdadGestacionalCapurro;
import mx.gob.hrrb.modelo.core.notas.EvolucionPartoLegrado;
import mx.gob.hrrb.modelo.core.notas.Forceps;
import mx.gob.hrrb.modelo.core.notas.HistoriaClinica;
import mx.gob.hrrb.modelo.core.notas.HistoriaClinicaPerinatal;
import mx.gob.hrrb.modelo.core.notas.HojaFrontal;
import mx.gob.hrrb.modelo.core.notas.Legrado;
import mx.gob.hrrb.modelo.core.notas.NotaPostOclusionTubariaPostparto;
import mx.gob.hrrb.modelo.core.notas.NotaPostoperatoria;
import mx.gob.hrrb.modelo.core.notas.NotaPreanestesica;
import mx.gob.hrrb.modelo.core.notas.NotaPreoperatoria;
import mx.gob.hrrb.modelo.core.notas.PartoEutocico;
import mx.gob.hrrb.modelo.core.notas.PartoGrama;
import mx.gob.hrrb.modelo.core.notas.Placenta;
import mx.gob.hrrb.modelo.core.notas.PrimeraMitadEmbarazo;
import mx.gob.hrrb.modelo.core.notas.SeguimientoTrabajoParto;
import mx.gob.hrrb.modelo.core.notas.SegundaMitadEmbarazo;
import mx.gob.hrrb.modelo.core.notas.TerminacionEmbarazo;
import mx.gob.hrrb.modelo.core.notas.ValoracionEpidemiologico;
import mx.gob.hrrb.modelo.core.notas.ValoracionNeurologica;
import mx.gob.hrrb.modelo.enfermeria.HojaDeTransfusion;
import mx.gob.hrrb.modelo.enfermeria.HojaEnfermeriaQuirofano;
import mx.gob.hrrb.modelo.enfermeria.reporte.MustraSignosVitalesHojaQx;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;
import mx.gob.hrrb.modelo.trabajosocial.EgresoFamiliarCapturado;
import mx.gob.hrrb.modelo.trabajosocial.EncuestaTrabsocPregBasicas;
import mx.gob.hrrb.modelo.trabajosocial.NivelSocioEconomico;
import mx.gob.hrrb.modelo.trabajosocial.NotaTrabajoSocial;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
import mx.gob.hrrb.modelo.urgencias.HojaLesiones;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Danny
 */
@ManagedBean (name="oExpJB")
@SessionScoped
public class ExpedienteJB {
    private EpisodioMedico oEpi;
    private HojaDeTransfusion oTransfusion;
    private HojaDeTransfusion[] oTranAgregadaBase;
    private HojaEnfermeriaQuirofano oHojaEnfQx;
    private ArrayList<MustraSignosVitalesHojaQx> arrMustraSignos;
    private NotaTrabajoSocial oNotaTS;
    private Traslado oHojaReferencia;
    private HojaFrontal oHojaFrontal;
    private NotaEmbarazo oHojaTriage;
    private NotaEmbarazo oHojaTriageAnverso;
    private PartoGrama oConsultapartograma;
    private PartoGrama oConsultaDatosPartoGrama;
    private PrimeraMitadEmbarazo oDetallePrimeraMitadEmbarazo;
    private SegundaMitadEmbarazo oDetalleSegundaMitadEmbarazo;
    private SeguimientoTrabajoParto oDetalleSeguimiento;
    private Producto oProducto;
    private PartoEutocico oPartoEutocico;
    private Forceps oForceps; 
    private Cesarea oCesarea;
    private Alumbramiento oAlumbramiento;
    private AnalgesiaTparto oAnalgesiaTparto; 
    private AnalgesiaTparto oPartoLegrado;
    private Placenta oPlacenta;
    private Legrado oLegrado;
    private EvolucionPartoLegrado oEvolucion;
    private NotaPreoperatoria oNotaPreoperatoria;
    private NotaPostOclusionTubariaPostparto  npotp;
    private AtencionNeonatalTococirugia oDatosAtencion;
    private DiagnosticoCIE10  oDiagnostico2;
    private CalificacionApgar oCalificacion;
    private CalificacionSilvermann oSilvermann;
    private ValoracionEpidemiologico oValoracion;
    private HistoriaClinicaPerinatal oHperinatal;
    private ValoracionNeurologica oNeurologica;
    private EvaluacionEdadGestacionalCapurro oEvaluacionEdadg;
    private HistoriaClinicaPerinatal oPerinatal;
    private LineChartModel oPinta;
    private HistoriaClinica oHistoriaClinica;
    private DiagnosticoCIE10 oCie10;
    private NotaPreanestesica oNotapreanestesica;
    private NotaPreoperatoria oNotaPreopera;
    private NotaPostoperatoria oNotaPostoperatoria;
    private NotaMedicaHRRB oNotaIngresoEvolucion;
    private DetalleReceta oReceta;
    private NivelSocioEconomico oEstudioSocEco;
    
    
    private HojaDeTransfusion[] arrTransfusiones;
    private HojaEnfermeriaQuirofano[] arrHojaEnfQX;
    private NotaTrabajoSocial[] arrNotaTS;
    private NotaTrabajoSocial[] oArrObservacionesPorNota;
    private Hospitalizacion[] arrCODES;
    private Hospitalizacion[] arrALTAS;
    private HojaFrontal[] arrHojaFrontal;
    private NotaEmbarazo[] arrHojaTriage;
    private PartoGrama[] arrPartogramas;
    private SeguimientoTrabajoParto[] arrDetalleSeguimientoSignosvitales;
    private DiagnosticoCIE10[] arrDiagnosticosTriage;
    private ArrayList<Producto> arrDetalleProducto;
    private ArrayList<EvolucionPartoLegrado>  arrEvolucion;
    private AtencionNeonatalTococirugia[] arrHojaAtnNeonatal;
    private DiagnosticoCIE10[] arrRetAtnNeonatal;
    private HistoriaClinicaPerinatal[] arrHCP;
    private HistoriaClinica[] arrHistoriaClinica;
    private NotaPreanestesica[] arrNotaPreanestesica;
    private NotaPreoperatoria[] arrNotaPreoperatoria;
    private NotaPreoperatoria[] arrNPdiagnosticosAgregados;
    private NotaPostoperatoria[] arrNotaPostoperatoria;
    private EstudioRealizado[]  arrLaboratoriales;
    private AdmisionUrgs[] arrNotaPrimerContacto;
    private HojaLesiones[] arrNotaLesiones;
    private Traslado[] arrHojaReferencia;
    private EstudioRealizado[] arrEstuRealReferencia;
    private EstudioRealizado[] arrEstudiosImagenologia;
    private EstudioRealizado[] arrEstudiosPatologia;
    private EstudioRealizado[] arrEstudiosEndoscopia;
    private EstudioRealizado[] arrEstudiosOtros;
    private NotaMedicaHRRB[] arrNotaMedIngresoEvol;
    private String[]  listaIndicaciones;
    private DetalleReceta[] arrRecetas;
    private NivelSocioEconomico[] arrEstudioSocioEconomico;
    private String[] sDetechoHab;
    private String sSeguroDesglosado[];
    private EgresoFamiliarCapturado[] arrEgresoFamiliarCapturado;
    private EncuestaTrabsocPregBasicas[] arrRespuestasPreguntasBasicasTS;
    private String[] sAsignaRespuesta;
    
    private int nPuntajeIngresoFamMensual=0;
    private int nPuntajeEgresoFamMensual=0;
    private int nPuntajeMensualVivienda=0;
    private int nPuntajeMensualOcupacion=0;
    private int nPuntajeMensualSalud=0;
    
    private long nTotalegre=0;
    private String sArea1;
    private String sArea2;
    private String sArea3;
    private long nFolioPac;
    private int nNumExp;
    private long nClaveEpi;
    private String sTabSeleccionado;
    private String bUrge;
    private boolean bMotivoEnv;
    private boolean bOtroMotivoEnv;
    private String sRutaEstudio;
    private String sUrlParteSelected;
    private String sTitulo;
    
    public ExpedienteJB() throws Exception{
        oEpi=new EpisodioMedico();
    }
    
    public String cargaDatos()throws Exception{
        String url="Expediente.xhtml";
        sTabSeleccionado="tabPortada";
        sUrlParteSelected="";
        sTitulo="";
        sDetechoHab=new String[6];
        sAsignaRespuesta=new String[45];
        bOtroMotivoEnv=false;
        bMotivoEnv=false;
        sRutaEstudio="";
        
        oTransfusion= new HojaDeTransfusion();
        oHojaEnfQx= new HojaEnfermeriaQuirofano();
        oNotaTS= new NotaTrabajoSocial();
        oHojaReferencia= new Traslado();
        oHojaFrontal= new HojaFrontal();
        oHojaTriage= new NotaEmbarazo();
        oHojaTriageAnverso= new NotaEmbarazo();
        oConsultapartograma=new PartoGrama();
        oConsultaDatosPartoGrama=new PartoGrama();
        oDetallePrimeraMitadEmbarazo= new PrimeraMitadEmbarazo();
        oDetalleSegundaMitadEmbarazo= new SegundaMitadEmbarazo();
        oDetalleSeguimiento= new SeguimientoTrabajoParto();
        oProducto= new Producto();
        oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
        oPartoEutocico= new PartoEutocico();
        oForceps =new Forceps();
        oCesarea= new Cesarea();
        oAlumbramiento= new Alumbramiento();
        oAnalgesiaTparto = new AnalgesiaTparto();
        oPartoLegrado= new AnalgesiaTparto();
        oPlacenta= new Placenta();
        oLegrado= new Legrado();
        oEvolucion = new EvolucionPartoLegrado();
        oNotaPreoperatoria= new NotaPreoperatoria();
        npotp=new NotaPostOclusionTubariaPostparto();
        oDatosAtencion=new AtencionNeonatalTococirugia();
        oDiagnostico2 = new DiagnosticoCIE10(); 
        oCalificacion = new CalificacionApgar(); 
        oSilvermann = new CalificacionSilvermann();
        oValoracion = new ValoracionEpidemiologico();
        oHperinatal= new HistoriaClinicaPerinatal();
        oNeurologica = new ValoracionNeurologica(); 
        oEvaluacionEdadg = new EvaluacionEdadGestacionalCapurro();
        oPerinatal= new HistoriaClinicaPerinatal();
        oHistoriaClinica= new HistoriaClinica();
        oCie10= new  DiagnosticoCIE10();
        oNotapreanestesica= new NotaPreanestesica();
        oNotaPreopera= new NotaPreoperatoria();
        oNotaPostoperatoria= new NotaPostoperatoria();
        oNotaIngresoEvolucion = new NotaMedicaHRRB();
        oReceta= new DetalleReceta();
        oEstudioSocEco= new NivelSocioEconomico();
        
        arrTransfusiones= null;
        arrHojaEnfQX=null;
        arrNotaTS=null;
        oArrObservacionesPorNota=null;
        arrCODES=null;
        arrALTAS=null;
        arrHojaFrontal=null;
        arrHojaTriage=null;
        arrDiagnosticosTriage=null;
        arrPartogramas= null;
        arrDetalleSeguimientoSignosvitales= null;
        arrDetalleProducto=null;
        arrEvolucion=null;
        arrHojaAtnNeonatal=null;
        arrRetAtnNeonatal=null;
        arrHCP=null;
        arrHistoriaClinica=null;
        arrNotaPreanestesica=null;
        arrNotaPreoperatoria=null;
        arrNPdiagnosticosAgregados=null;
        arrNotaPostoperatoria=null;
        arrLaboratoriales=null;
        arrNotaPrimerContacto=null;
        arrNotaLesiones=null;
        arrHojaReferencia=null;
        arrEstuRealReferencia=null;
        arrEstudiosImagenologia=null;
        arrEstudiosPatologia=null;
        arrEstudiosEndoscopia=null;
        arrEstudiosOtros=null;
        arrNotaMedIngresoEvol=null;
        listaIndicaciones=null;
        arrRecetas =null;
        arrEstudioSocioEconomico=null;
        arrEgresoFamiliarCapturado=null;
        arrRespuestasPreguntasBasicasTS=null;
        
        oEpi.getPaciente().setFolioPaciente(nFolioPac);
        oEpi.getPaciente().getExpediente().setNumero(nNumExp);
        oEpi.getPaciente().buscarPacARCHIVO();
        oEpi.getPaciente().getExpediente().buscaEstatusExpedienteBD(nFolioPac,nNumExp);
        System.out.println(oEpi.getPaciente().getFolioPaciente());
        System.out.println(oEpi.getPaciente().getExpediente().getNumero());
        return url;
    }
    
    
    public void buscaDetallesReceta(DetalleReceta oR){
        try{
            this.sUrlParteSelected="partes/receta.xhtml"; 
            this.sTitulo="RECETA";
            oReceta=new DetalleReceta();
            oReceta.setEpisodio(new EpisodioMedico());
            oReceta.setReceta(new Receta());
            oReceta.getEpisodio().setClaveEpisodio(oR.getEpisodio().getClaveEpisodio());
            oReceta.getEpisodio().getPaciente().setFolioPaciente(oR.getEpisodio().getPaciente().getFolioPaciente());
            oReceta.getReceta().setConsecReceta(oR.getReceta().getConsecReceta());
            oReceta.buscaInformacionRecetaEXP();
            validaSeleccionArea();
            desglosar();
        }catch(Exception e){
        Logger.getLogger(Receta.class.getName()).log(Level.SEVERE, null, e);}
        
    }
    
    public void buscaHistorialRecetas(){   
        try{ 
            arrRecetas=(new DetalleReceta().buscaHistorialRecetas(nFolioPac)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public DetalleReceta[] getListaHistorialRecetas(){
        return arrRecetas;
    }
    
    public void buscaDetallesNotaMedIngresoEvolucion(NotaMedicaHRRB oNota){
        String cadena="";
        try{
            this.sUrlParteSelected="partes/notaMedIngresoEvolucion.xhtml"; 
            this.sTitulo="NOTA MÉDICA INGRESO/EVOLUCIÓN";
            oNotaIngresoEvolucion=new NotaMedicaHRRB();
            oNotaIngresoEvolucion.getEpiMed().getPaciente().setFolioPaciente(oNota.getEpiMed().getPaciente().getFolioPaciente());
            oNotaIngresoEvolucion.getEpiMed().setClaveEpisodio(oNota.getEpiMed().getClaveEpisodio());
            oNotaIngresoEvolucion.setConsecutivo(oNota.getConsecutivo());
            oNotaIngresoEvolucion.getEpiMed().getSignosVitales().setNgVerbal(oNota.getEpiMed().getSignosVitales().getNgVerbal());
            oNotaIngresoEvolucion.buscaDetallesNotaMedicaIngresoEvolucion();
            if(oNotaIngresoEvolucion.getIndicacionTer()!=null || !oNotaIngresoEvolucion.getIndicacionTer().isEmpty()){
                cadena=oNotaIngresoEvolucion.getIndicacionTer();
                listaIndicaciones=cadena.split(";");//INDICACIONES TERAPEUTICAS DE MEDICAMENTOS
            }
        }catch(Exception e){Logger.getLogger(NotaMedicaHRRB.class.getName()).log(Level.SEVERE, null, e); }
    }
    
    public void buscaHistorialNotaMedIngresoEvolucion(){   
        try{ 
            arrNotaMedIngresoEvol=(new NotaMedicaHRRB()
                    ).buscaHistorialNotaIngresoEvolucionEXP(nFolioPac); 
        }catch(Exception ex){
            ex.printStackTrace();
        }
    } 
    
    public NotaMedicaHRRB[] getListaHistorialNotaMedIngresoEvolucion(){
        return arrNotaMedIngresoEvol;
    }
    
    public void buscaHistorialEstudiosOtros(){   
        try{ 
            //incluye COLPOSCOPÍA, ESTUDIOS OTOACÚSTICOS, 
            //ELECTROCARDIOGRAMA, SOLICITUD DE TRANFUSION AMBULATORIA, 
            //SOLICITUD DE PRODUCTOS DE BANCO DE SANGRE, OTROS
            arrEstudiosOtros=(new EstudioRealizado()
                    ).buscaHistorialEstudiosRealizadosExp(nFolioPac, 5); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public EstudioRealizado[] getListaHistorialEstudiosOtros(){
        return arrEstudiosOtros;  
    }
    
    public void buscaHistorialEstudiosEndoscopia(){   
        try{ 
            //incluye ENDOSCOPIA, SOLICITUD DE PROCEDIMIENTO QUIRÚRGICO
            arrEstudiosEndoscopia=(new EstudioRealizado().buscaHistorialEstudiosRealizadosExp(nFolioPac, 4)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public EstudioRealizado[] getListaHistorialEstudiosEndoscopia(){
        return arrEstudiosEndoscopia;  
    }
    
    public void buscaHistorialEstudiosPatologia(){   
        try{ 
            arrEstudiosPatologia=(new EstudioRealizado().buscaHistorialEstudiosRealizadosExp(nFolioPac, 3)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public EstudioRealizado[] getListaHistorialEstudiosPatologia(){
        return arrEstudiosPatologia;  
    }
    
    public void buscaHistorialEstudiosImagenologia(){   
        try{ 
            arrEstudiosImagenologia=(new EstudioRealizado().buscaHistorialEstudiosRealizadosExp(nFolioPac, 2)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public EstudioRealizado[] getListaHistorialEstudiosImagenologia(){
        return arrEstudiosImagenologia;  
    }
    
    public void buscaHistorialLaboratoriales(){   
        try{ 
            arrLaboratoriales=(new EstudioRealizado().buscaHistorialEstudiosRealizadosExp(nFolioPac, 1)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public EstudioRealizado[] getListaHistorialLaboratoriales(){
        return arrLaboratoriales;  
    }
    
    public void buscaHistorialNotaLesiones(){   
        try{ 
            arrNotaLesiones=(new HojaLesiones()
                    ).buscaHistorialHojaLesion(nFolioPac); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public HojaLesiones[] getListaHistorialNotaLesiones(){
        return arrNotaLesiones;  
    }
    
    public void buscaHistorialNotaPrimerContacto(){   
        try{ 
            arrNotaPrimerContacto=(new AdmisionUrgs()
                    ).buscaHistorialNotaMedicaPrimerContacto(nFolioPac); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public AdmisionUrgs[] getListaHistorialNotaPrimerContacto(){
        return arrNotaPrimerContacto;  
    }
    
    public void buscaDetallesNotaPostoperatoria(NotaPostoperatoria oNotapost){  
        try {
            this.sUrlParteSelected="partes/notaPostOperatoria.xhtml"; 
            this.sTitulo="NOTA POST OPERATORIA";
            oNotaPostoperatoria= new NotaPostoperatoria();
            oNotaPostoperatoria.getEpiMed().getPaciente().setFolioPaciente(oNotapost.getEpiMed().getPaciente().getFolioPaciente());
            oNotaPostoperatoria.getEpiMed().getPaciente().setClaveEpisodio(oNotapost.getEpiMed().getPaciente().getClaveEpisodio());
            oNotaPostoperatoria.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setClave(oNotapost.getProcedimientosRealizados().getCIE9().getClave());
            oNotaPostoperatoria.setConsecutivo(oNotapost.getConsecutivo());
            oNotaPostoperatoria.getNotaPreoperatoria().getEpiMed().getPaciente().setFolioPaciente(oNotapost.getEpiMed().getPaciente().getFolioPaciente());
            oNotaPostoperatoria.getNotaPreoperatoria().getEpiMed().getPaciente().setClaveEpisodio(oNotapost.getEpiMed().getPaciente().getClaveEpisodio());
            oNotaPostoperatoria.buscaDatosCabeceraCPEXP();
            if(oNotaPostoperatoria.getConsecutivo() != 0)
                oNotaPostoperatoria.buscaDetalleAnverso();       
            oNotaPostoperatoria.buscaDetalleReverso();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void buscaHistorialNotaPostoperatoria(){   
        try{
            arrNotaPostoperatoria=(new NotaPostoperatoria()
                    ).buscaHistorialNotaPostoperatoria(nFolioPac); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    }
    
    public NotaPostoperatoria[] getListaHistorialNotaPostoperatoria(){
        return arrNotaPostoperatoria;  
    }
    
    
    public void buscaDetallesNotaPreoperatoria(NotaPreoperatoria oNt){  
        try {
            this.sUrlParteSelected="partes/notaPreOperatoria.xhtml";
            this.sTitulo="NOTA PRE OPERATORIA";
            oNotaPreopera.getEpiMed().getPaciente().setFolioPaciente(oNt.getEpiMed().getPaciente().getFolioPaciente());
            oNotaPreopera.getEpiMed().getPaciente().setClaveEpisodio(oNt.getEpiMed().getPaciente().getClaveEpisodio());
            oNotaPreopera.getProcedimientosRealizados().getCIE9().setClave(oNt.getProcedimientosRealizados().getCIE9().getClave());
            oNotaPreopera.setConsecutivo(oNt.getConsecutivo());
            if(oNotaPreopera.buscarDetalleNotaPreoperatoria()){
                buscaDignosticosAgregados(oNotaPreopera);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void buscaDignosticosAgregados(NotaPreoperatoria oNt){
        try {
            arrNPdiagnosticosAgregados=oNt.buscaDianosticosAgregados();
        } catch (Exception ex) {
            //Logger.getLogger(NotaPreoperatoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaHistorialNotaPreoperatoria(){   
        try{
            arrNotaPreoperatoria=(new NotaPreoperatoria().buscaHistorialNotaPreoperatoria(nFolioPac)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    }
    
    public NotaPreoperatoria[] getListaHistorialNotaPreoperatoria(){
        return arrNotaPreoperatoria;  
    }
    
    public void buscaDetallesNotaPreanestesica(long foliopaciente, long clave, int consecutivo){
        try{
            this.sUrlParteSelected="partes/notaPreAnestesica.xhtml"; 
            this.sTitulo="NOTA PRE ANESTÉSICA";
            oNotapreanestesica.getEpiMed().getPaciente().setFolioPaciente(foliopaciente);
            oNotapreanestesica.getEpiMed().getPaciente().setClaveEpisodio(clave);
            oNotapreanestesica.setConsecutivo(consecutivo);
            oNotapreanestesica.buscaDatosCabecera();
            oNotapreanestesica.buscaDatosNotaEspecifica();            
        }catch(Exception e){
            Logger.getLogger(NotaPreanestesica.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void buscaHistorialNotaPreanestesica(){ 
        try{
            arrNotaPreanestesica=(new NotaPreanestesica().buscaHistorialNotaPreanestesica(nFolioPac)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    }
    
    public NotaPreanestesica[] getListaHistorialNotaPreanestesica(){
        return arrNotaPreanestesica;  
    }
    
    public void buscaDetallesHistoriaClinica(long foliopaciente, long clave, int numHC){
        try{
            this.sUrlParteSelected="partes/historiaClinicaGeneral.xhtml"; 
            this.sTitulo="HISTORIA CLÍNICA";
            oHistoriaClinica.getEpisodioMedico().getPaciente().setFolioPaciente(foliopaciente);
            oHistoriaClinica.getEpisodioMedico().getPaciente().setClaveEpisodio(clave);
            oHistoriaClinica.getEpisodioMedico().getPaciente().getExpediente().setNumero(nNumExp);
            oHistoriaClinica.setNumeroHistoriaClinca(numHC);
            oHistoriaClinica.buscaDatosPacienteHistoriaClinica();
            oHistoriaClinica.buscaDatosAnverso01EXP();
            oHistoriaClinica.buscaDatosReverso01EXP();
            oHistoriaClinica.buscaDatosAnverso02EXP();
            oHistoriaClinica.buscaDatosReverso02EXP();
            oCie10.setDescripcionDiag(oHistoriaClinica.getDiagnostico().getDescripcionDiag() == null ||
                    oHistoriaClinica.getDiagnostico().getDescripcionDiag().isEmpty() ? "" : oHistoriaClinica.getDiagnostico().getDescripcionDiag());
            oCie10.setClave(oHistoriaClinica.getDiagnostico().getClave() == null ||
                    oHistoriaClinica.getDiagnostico().getDescripcionDiag().isEmpty() ? "" : oHistoriaClinica.getDiagnostico().getClave());            
        }catch(Exception e){
            Logger.getLogger(HistoriaClinica.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void buscaHistorialHClinica(){ 
        try{
            arrHistoriaClinica=(new HistoriaClinica().buscaHistorialHClinica(nFolioPac)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    }
    
    public HistoriaClinica[] getListaHistorialHClinica(){
        return arrHistoriaClinica;
    }
    
    public void buscaDetallesHClinicaPerinatal(long fpaciente, long clave, long foliomama, long clavemama,int consecutivopartograma, 
                                               long numeropartograma, int nhclinica, long folvaloracion, int numcapurro){
        try{
            this.sUrlParteSelected="partes/historiaClinicaPerinatal.xhtml"; 
            this.sTitulo="HISTORIAL CLÍNICA PERINATAL";
            oHperinatal.getPacientenNeonato().setFolioPaciente(fpaciente);
            oHperinatal.getPacientenNeonato().setClaveEpisodio(clave);
            oHperinatal.getPacientenNeonato().getExpediente().setNumero(nNumExp);
            oHperinatal.getEpisodioMedico().getPaciente().setFolioPaciente(foliomama);
            oHperinatal.getEpisodioMedico().getPaciente().setClaveEpisodio(clavemama);
            oHperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivopartograma);
            oHperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(numeropartograma);
            oHperinatal.setNumeroHistoriaClinca(nhclinica);
            
            oNeurologica.getHistoriaClinicaPerinatal().getPacientenNeonato().setFolioPaciente(fpaciente);
            oNeurologica.getHistoriaClinicaPerinatal().getPacientenNeonato().setClaveEpisodio(clave);
            oNeurologica.getHistoriaClinicaPerinatal().setNumeroHistoriaClinca(nhclinica);
            
            oEvaluacionEdadg.getHistoriaClinicaPerinatal().getPacientenNeonato().setFolioPaciente(fpaciente);
            oEvaluacionEdadg.getHistoriaClinicaPerinatal().getPacientenNeonato().setClaveEpisodio(clave);
            oEvaluacionEdadg.getHistoriaClinicaPerinatal().setNumeroHistoriaClinca(nhclinica);
            
            oHperinatal.cargaDatosPaciente();
            oHperinatal.consulatAnverso1Especifico();
            
            if(oHperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac() != null){
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                oHperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNacTexto(format.format(oHperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac()));
                oHperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNacTexto(oHperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().calculaEdad());
            }
            
            oHperinatal.consultaSignosvitalesNeonatoEsepecifico();
            oHperinatal.consultaReverso001Detalle();
            
            if(oHperinatal.getDiagnostico().getClave() != null || !oHperinatal.getDiagnostico().getClave().isEmpty()){
                oPerinatal.getDiagnostico().setClave(oHperinatal.getDiagnostico().getClave());
                oPerinatal.getDiagnostico().setDescripcionDiag(oHperinatal.getDiagnostico().getDescripcionDiag());
            }
            
            oNeurologica.consultaEspecificaValoracionNeurologicaEXP(folvaloracion);
            sumatoria();
            
            oEvaluacionEdadg.consultaEspecificaEdadGestacionalCapurroEXP( numcapurro);
            operacion();            
            
            createLineModels();
        }catch(Exception e){
            Logger.getLogger(HistoriaClinicaPerinatal.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void buscaHistorialHClinicaPerinatal(){ 
        try{
            arrHCP=(new HistoriaClinicaPerinatal().buscaHistorialHClinicaPerinatal(nFolioPac)); 
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public HistoriaClinicaPerinatal[] getListaHistorialHClinicaPerinatal(){
        return arrHCP;
    }
    
    public void buscaDetallesHojaAtnNeonatal(long fpaciente, long clave, int consecutivonota, long npartograma, int ningresohosp, int consecutivoproducto, long claveAtnNeoToco){
        try{
            this.sUrlParteSelected="partes/notaAtencionNeonatal.xhtml"; 
            this.sTitulo="NOTA ATENCIÓN NEONATAL";
            NotaEmbarazo oConsulta = new NotaEmbarazo();
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);//foliopac(madre)
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivonota);
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oDatosAtencion.getProducto().setNumeroIngresoHospitalario(ningresohosp);
            this.oDatosAtencion.getProducto().setConsecutivoProducto(consecutivoproducto);
            this.oDatosAtencion.buscaDatosPacienteProductoEpecificoEXP();
            this.arrRetAtnNeonatal = oConsulta.datosPaciente(this.oDatosAtencion.getPacienteNeonato().getFolioPaciente(), this.oDatosAtencion.getPacienteNeonato().getClaveEpisodio());                    
            if(this.oDatosAtencion.getDiagnosticos().getClave() != null){
                oDiagnostico2.setClave(this.oDatosAtencion.getDiagnosticos().getClave());
                oDiagnostico2.setDescripcionDiag(this.oDatosAtencion.getDiagnosticos().getDescripcionDiag());
            }else{
                oDiagnostico2.setClave("");
                oDiagnostico2.setDescripcionDiag("");
            }
            this.oCalificacion.getAtencionNeonatalTococirugia().getProducto().setNumeroIngresoHospitalario(ningresohosp);
            this.oCalificacion.getAtencionNeonatalTococirugia().getProducto().setConsecutivoProducto(consecutivoproducto);
            this.oCalificacion.getAtencionNeonatalTococirugia().setClaveAtNeonatalToco(claveAtnNeoToco);
            this.oCalificacion.buscaDetalleApgarEXP();
            this.oSilvermann.getAtencionNeonatalTococirugia().getProducto().setNumeroIngresoHospitalario(ningresohosp);
            this.oSilvermann.getAtencionNeonatalTococirugia().getProducto().setConsecutivoProducto(consecutivoproducto);
            this.oSilvermann.getAtencionNeonatalTococirugia().setClaveAtNeonatalToco(claveAtnNeoToco);
            this.oSilvermann.buscaDetalleSilvermannEXP();
            this.oValoracion.getAtencionNeonatalTococirugia().getProducto().setNumeroIngresoHospitalario(ningresohosp);
            this.oValoracion.getAtencionNeonatalTococirugia().getProducto().setConsecutivoProducto(consecutivoproducto);
            this.oValoracion.getAtencionNeonatalTococirugia().setClaveAtNeonatalToco(claveAtnNeoToco);
            this.oValoracion.buscaDetalleValoracionEpidemioEXP();
        }catch(Exception e){
            Logger.getLogger(AtencionNeonatalTococirugia.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void buscaHistorialHojaAtnNeonatal(){ 
        try{
            arrHojaAtnNeonatal=(new AtencionNeonatalTococirugia().buscaHistorialHojaAtnNeonatal(nFolioPac));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public AtencionNeonatalTococirugia[] getListaHistorialHojaAtnNeonatal(){
        return arrHojaAtnNeonatal;
    }
    
    public void buscaDetallesPartograma(long fpaciente, long clave, int consecutivo, long npartograma){
        try{
            this.sUrlParteSelected="partes/partograma.xhtml"; 
            this.sTitulo="PARTOGRAMA";
            this.oConsultapartograma = oConsultapartograma.cargarDatosParaConsultaExpediente(fpaciente, clave, consecutivo);
            this.oConsultaDatosPartoGrama = new PartoGrama();
            this.oConsultaDatosPartoGrama.getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oConsultaDatosPartoGrama.getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oConsultaDatosPartoGrama.getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oConsultaDatosPartoGrama.setConsecutivo(consecutivo);
            this.oConsultaDatosPartoGrama.setNpartograma(npartograma);            
            this.oConsultaDatosPartoGrama = oConsultaDatosPartoGrama.CargaDetallePaciente();
            this.oDetallePrimeraMitadEmbarazo = new PrimeraMitadEmbarazo();
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().setConsecutivo(consecutivo);
            this.oDetallePrimeraMitadEmbarazo.getPartoGrama().setNpartograma(npartograma);
            this.oDetallePrimeraMitadEmbarazo = oDetallePrimeraMitadEmbarazo.cargaDetallePaciente();
            this.oDetalleSegundaMitadEmbarazo = new SegundaMitadEmbarazo();  
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().setConsecutivo(consecutivo);
            this.oDetalleSegundaMitadEmbarazo.getPartoGrama().setNpartograma(npartograma);
            this.oDetalleSegundaMitadEmbarazo = oDetalleSegundaMitadEmbarazo.cargaDetallePacienteSegundaMitadEmbarazo();
            this.oDetalleSeguimiento = new SeguimientoTrabajoParto();
            this.oDetalleSeguimiento.getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oDetalleSeguimiento.getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oDetalleSeguimiento.getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oDetalleSeguimiento.getPartoGrama().setConsecutivo(consecutivo);
            this.oDetalleSeguimiento.getPartoGrama().setNpartograma(npartograma);
            this.arrDetalleSeguimientoSignosvitales = oDetalleSeguimiento.cargaSignosVitales();
            this.oDetalleSeguimiento = oDetalleSeguimiento.cargaDetallePacienteSeguimientoParto();
            this.oProducto= new Producto();
            oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oProducto.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.arrDetalleProducto = oProducto.detalleProducto((short) 1);
            this.oPartoEutocico = new PartoEutocico(); 
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oPartoEutocico.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oPartoEutocico.detallePartoEutocico();
            this.oForceps = new Forceps();
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oForceps.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oForceps.buscaDetalleForceps();
            this.oCesarea = new Cesarea(); 
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oCesarea.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oCesarea.detalleCesarea();
            this.oAlumbramiento = new Alumbramiento(); 
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oAlumbramiento.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oAlumbramiento.detalleAlumbramiento();
            this.oAnalgesiaTparto = new AnalgesiaTparto();  
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oAnalgesiaTparto.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oAnalgesiaTparto.detalleAnalgesia();
            this.oPartoLegrado = new AnalgesiaTparto();  
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oPartoLegrado.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oPartoLegrado.detalleAnalgesiaLegrado();
            this.oPlacenta = new Placenta(); 
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oPlacenta.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oPlacenta.detallePaciente((short) 1);
            this.oLegrado = new Legrado();
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oLegrado.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oLegrado.detalleLegrado();
            this.oEvolucion = new EvolucionPartoLegrado(); 
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oEvolucion.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.arrEvolucion = oEvolucion.detalleEvolucionPartoLegrado();                     
            this.oNotaPreoperatoria = new NotaPreoperatoria();  
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.oNotaPreoperatoria.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oNotaPreoperatoria.detalleNotaPreoperatoria();
            this.npotp = new NotaPostOclusionTubariaPostparto();  
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.npotp.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nNumExp);
            this.npotp.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivo);
            this.npotp.getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.npotp.detalleNotaPostOclusionTubariaPostParto((short) 0);
        }catch(Exception e){
            Logger.getLogger(PartoGrama.class.getName()).log(Level.SEVERE,null,e);
        }        
    }
    
    public void buscaHistorialPartograma(){
        try{
            arrPartogramas=(new PartoGrama().buscaHistorialPartograma(nFolioPac));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public PartoGrama[] getListaHistorialPartograma(){
        return arrPartogramas;
    }
    
    public void buscaDetallesHojaTriage(long foliopaciente, long claveepisodio, int consecutivo){
        try{
            this.sUrlParteSelected="partes/triage.xhtml"; 
            this.sTitulo="TRIAGE";
            NotaEmbarazo oConsulta = new NotaEmbarazo();
            oHojaTriageAnverso=oConsulta.buscaDetallesPacienteTriage(foliopaciente, claveepisodio);
            oHojaTriage= oConsulta.datosPaciente(foliopaciente, claveepisodio, nNumExp, consecutivo);
            arrDiagnosticosTriage=oConsulta.datosPaciente(foliopaciente, claveepisodio);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void buscaHistorialHojaTriage(){
        try{
            arrHojaTriage=(new NotaEmbarazo().buscaHistorialTriage(nFolioPac));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public NotaEmbarazo[] getListaHistorialHojaTriage(){
        return arrHojaTriage;
    }
    
    public void buscaDetallesHojaFrontal(long fp, long cveEpi){
        try{
            this.sUrlParteSelected="partes/hojaFrontal.xhtml"; 
            this.sTitulo="HOJA FRONTAL";
            oHojaFrontal.getEpisodioMedico().getPaciente().setFolioPaciente(fp);
            oHojaFrontal.getEpisodioMedico().getPaciente().setClaveEpisodio(cveEpi);
            oHojaFrontal.buscaPacienteDetalles();
            oHojaFrontal.buscaDatosEgreso();
            oHojaFrontal.buscaDatosHojaFrontal();
            RequestContext.getCurrentInstance().update("bodyHF");
            if(oHojaFrontal==null)
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","No se encontró información para la hoja"));
        }catch(Exception e){
            Logger.getLogger(HojaFrontal.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void buscaHistorialHojaFrontal(){
        try{
            arrHojaFrontal=(new HojaFrontal().buscaHistorialHojaFrontal(nFolioPac,nNumExp));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public HojaFrontal[] getListaHistorialHojaFrontal(){
        return arrHojaFrontal;
    }
    
    public void buscaHistorialCODES(){
        try{
            arrCODES=(new Hospitalizacion().buscarHistorialCODESpaciente(oEpi.getPaciente().getFolioPaciente()));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Hospitalizacion[] getListaHistorialCODES(){
        return arrCODES;
    }
    
    public void buscaHistorialALTAS(){
        try{
            arrALTAS=(new Hospitalizacion().buscahistorialHOJAALTApaciente(oEpi.getPaciente().getFolioPaciente()));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Hospitalizacion[] getListaHistorialALTAS(){
        return arrALTAS;
    }
    
    public void buscaDetallesHojaReferencia(Traslado tras){  
        try {
            this.sUrlParteSelected="partes/hojaReferencia.xhtml";
            this.sTitulo="HOJA DE REFERENCIA";
            oHojaReferencia.setEpisodio(new EpisodioMedico());
            oHojaReferencia.setIdentificador(tras.getIdentificador());
            oHojaReferencia.getEpisodio().getPaciente().setFolioPaciente(tras.getEpisodio().getPaciente().getFolioPaciente());
            oHojaReferencia.getEpisodio().setClaveEpisodio(tras.getEpisodio().getClaveEpisodio());
            oHojaReferencia.buscaPacienteReferencia();
            arrEstuRealReferencia=new EstudioRealizado().buscaAuxiliarDeDiag(tras.getEpisodio());
            oHojaReferencia.getEpisodio().getPaciente().getSeg().setNumeroP(oHojaReferencia.getEpisodio().getPaciente().getSeg().buscaSegPopxPac(tras.getEpisodio().getPaciente().getFolioPaciente()));
        } catch (Exception ex) {
            Logger.getLogger(Traslado.class.getName()).log(Level.SEVERE,null,ex);
        }
   }
   
    public void buscaHistorialHojaReferencia(){   
        try{ 
            arrHojaReferencia=(new Traslado().buscaHistorialHojaReferencia(nFolioPac)); 
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    } 
    
    public Traslado[] getListaHistorialHojaReferencia(){
        return arrHojaReferencia;  
    }
    
    
    public void buscaDetallesEstudioSocioEconomico(NivelSocioEconomico oES){
        try{
            this.sUrlParteSelected="partes/estudioSocioEconomico.xhtml"; 
            this.sTitulo="ESTUDIO SOCIOECONÓMICO";
            oEstudioSocEco.getPac().setFolioPaciente(oES.getPac().getFolioPaciente()); 
            oEstudioSocEco.setRegistro(oES.getRegistro());
            oEstudioSocEco.buscaDetallesEstudioSocioEconomicoAnverso1();
            oEstudioSocEco.buscaDocumentacionReclasificacionTS();
            calculaDerechoHabiente();
            determinaPuntajeIngresoFamiliar();
            buscatablaEgresos();
            buscaRespuestasPreguntasBasicasTS();  
            determinaPuntajeEgreso();
        }catch(Exception ex){
        Logger.getLogger(NivelSocioEconomico.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    public void buscaHistorialEstudioSocioEconomico(){
        try{
            arrEstudioSocioEconomico=(new NivelSocioEconomico().buscaHistorialEstudioSocioEconomico(nFolioPac));
        }catch(Exception ex){
            ex.printStackTrace();
        } 
    }
    
    public NivelSocioEconomico[] getListaHistorialEstudioSocioEconomico(){
        return arrEstudioSocioEconomico;
    }
    
    public void buscaDetallesHojaTrabSocial(long folioP, long cveEpi){
        try{
            this.sUrlParteSelected="partes/notaTrabajoSocial.xhtml"; 
            this.sTitulo="NOTA DE TRABAJO SOCIAL";
            oNotaTS.setEpisodioMedico(new EpisodioMedico());
            oNotaTS.getEpisodioMedico().setClaveEpisodio(cveEpi);
            oNotaTS.getEpisodioMedico().getPaciente().setFolioPaciente(folioP);
            oArrObservacionesPorNota=oNotaTS.buscalistdesplAutori();
            oNotaTS=oNotaTS.buscaDatosBasicosHojaTrabajoSocial();
            if(oNotaTS==null)
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","No se encontró información para la hoja"));                
        }catch(Exception ex){
            Logger.getLogger(NotaTrabajoSocial.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void buscaHistorialHojaTrabSocial(){
        try{
            arrNotaTS=(new NotaTrabajoSocial().buscaHistorialNotasTrabajoSocial(nFolioPac));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public NotaTrabajoSocial[] getListaHistorialHojaTrabSocial(){
        return arrNotaTS;
    }
    
    public void buscaDetallesHojaEnfermeriaQx(long folioHoja){
        boolean bBan=false;
        try{
            this.sUrlParteSelected="partes/hojaQuirurgica.xhtml"; 
            this.sTitulo="HOJA QUIRÚRGICA";
            oHojaEnfQx.setIdHoja(folioHoja);
            bBan=oHojaEnfQx.buscarHojaQx();
            if(bBan){
                arrMustraSignos= new ArrayList<MustraSignosVitalesHojaQx>();              
            }else
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","No se encontró información para la hoja"));                
        }catch(Exception ex){Logger.getLogger(HojaEnfermeriaQuirofano.class.getName()).log(Level.SEVERE,null,ex);}
    }
    
    public void buscaHistorialHojaEnfQx(){
        try{
            arrHojaEnfQX=(new HojaEnfermeriaQuirofano().buscaHistorialHojasEnfermeriaQuirurgica(nFolioPac));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public HojaEnfermeriaQuirofano[] getListaHistorialHojaEnfQx(){
        return arrHojaEnfQX;
    }
    
    public void buscaDetallesHojaTransfusion(long fp, long cvEp, long folioHoja){
        try{
            this.sUrlParteSelected="partes/hojaTransfusion.xhtml"; 
            this.sTitulo="HOJA DE ACTOS TRANSFUNCIONALES ";
            oTransfusion.getEpisodio().getPaciente().setFolioPaciente(fp);
            oTransfusion.getEpisodio().getPaciente().setClaveEpisodio(cvEp);
            oTransfusion.setFolio(folioHoja);
            oTranAgregadaBase=oTransfusion.buscaDatosBasicosHojaTransfunsion();
            oTransfusion=oTranAgregadaBase[0];
            if(oTranAgregadaBase==null)
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"INFO","No se cargaron los datos"));
        }catch(Exception ex){Logger.getLogger(HojaDeTransfusion.class.getName()).log(Level.SEVERE,null,ex);}
    }
    
    public void buscaHistorialTransfusiones(){
        try{
            arrTransfusiones=(new HojaDeTransfusion().buscarHistorialTransfunsionesPaciente(nFolioPac));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public HojaDeTransfusion[] getListaHistorialTransfusiones(){
        return arrTransfusiones;
    }
    
    
    
    public void buscar(TabChangeEvent event){
        sTabSeleccionado=event.getTab().getId();
        if(arrCODES==null){  if(event.getTab().getId().equals("tabSAEH")) buscaHistorialCODES(); }
        if(arrTransfusiones==null){  if(event.getTab().getId().equals("tabHojaEnf")) buscaHistorialTransfusiones();  }
        if(arrNotaTS==null){  if(event.getTab().getId().equals("tabTrabSocial")) buscaHistorialHojaTrabSocial();  }
        if(arrHojaFrontal==null){  if(event.getTab().getId().equals("tabHojaFrontal")) buscaHistorialHojaFrontal();  }//solo 1 tab dentro del principal
        if(arrHojaTriage==null){  if(event.getTab().getId().equals("tabAtencionParto")) buscaHistorialHojaTriage();  }
        if(arrHistoriaClinica==null){  if(event.getTab().getId().equals("tabHistoriaClinica")) buscaHistorialHClinica();  }
        if(arrLaboratoriales==null){  if(event.getTab().getId().equals("tabEstGenerados")) buscaHistorialLaboratoriales();  }
        if(arrNotaPrimerContacto==null){  if(event.getTab().getId().equals("tabNotasMedicas")) buscaHistorialNotaPrimerContacto();  }
        if(arrRecetas==null){  if(event.getTab().getId().equals("tabRecetas")) buscaHistorialRecetas();  }//solo 1 tab dentro del principal
    }
    
    public void buscarOtro(TabChangeEvent event){
        if(arrCODES==null){  if(event.getTab().getId().equals("tabCodes")) buscaHistorialCODES();}
        if(arrALTAS==null){  if(event.getTab().getId().equals("tabAltas"))  buscaHistorialALTAS();}
        if(arrTransfusiones==null){  if(event.getTab().getId().equals("tabHojaTransfusion"))  buscaHistorialTransfusiones();}
        if(arrHojaEnfQX==null){  if(event.getTab().getId().equals("tebHojaQuirurgica"))  buscaHistorialHojaEnfQx();}
        if(arrNotaTS==null){  if(event.getTab().getId().equals("tabNotaTS")) buscaHistorialHojaTrabSocial();  }
        if(arrEstudioSocioEconomico==null){  if(event.getTab().getId().equals("tabEstudioSociEcono")) buscaHistorialEstudioSocioEconomico();  }
        if(arrHojaReferencia==null){  if(event.getTab().getId().equals("tabHojaReferencia")) buscaHistorialHojaReferencia();  }
        if(arrHojaTriage==null){  if(event.getTab().getId().equals("tabTriage")) buscaHistorialHojaTriage();  }
        if(arrPartogramas==null){  if(event.getTab().getId().equals("tabPartograma")) buscaHistorialPartograma();  }
        if(arrHojaAtnNeonatal==null){  if(event.getTab().getId().equals("tabAtnNeonatal")) buscaHistorialHojaAtnNeonatal();  }
        if(arrHistoriaClinica==null){  if(event.getTab().getId().equals("tabHisCliGeneral")) buscaHistorialHClinica();  }
        if(arrHCP==null){  if(event.getTab().getId().equals("tabHisCliPerinatal")) buscaHistorialHClinicaPerinatal();  }
        if(arrNotaPreanestesica==null){  if(event.getTab().getId().equals("tabNMedicaPreanestesica")) buscaHistorialNotaPreanestesica();  }
        if(arrNotaPreoperatoria==null){  if(event.getTab().getId().equals("tabNMedicaPreoperatoria")) buscaHistorialNotaPreoperatoria();  }
        if(arrNotaPostoperatoria==null){  if(event.getTab().getId().equals("tabNMedicaPostoperatoria")) buscaHistorialNotaPostoperatoria();  }
        if(arrLaboratoriales==null){  if(event.getTab().getId().equals("tabLab")) buscaHistorialLaboratoriales();  }
        if(arrEstudiosImagenologia==null){  if(event.getTab().getId().equals("tabImagenologia")) buscaHistorialEstudiosImagenologia();  }
        if(arrEstudiosPatologia==null){  if(event.getTab().getId().equals("tabPatologia")) buscaHistorialEstudiosPatologia();  }
        if(arrEstudiosEndoscopia==null){  if(event.getTab().getId().equals("tabEndoscopia")) buscaHistorialEstudiosEndoscopia();  }
        if(arrEstudiosOtros==null){  if(event.getTab().getId().equals("tabOtrosEstudios")) buscaHistorialEstudiosOtros();  }
        if(arrNotaPrimerContacto==null){  if(event.getTab().getId().equals("tabNMedicaPrimerContacto")) buscaHistorialNotaPrimerContacto();  }
        if(arrNotaLesiones==null){  if(event.getTab().getId().equals("tabNMedicaLesiones")) buscaHistorialNotaLesiones();  }
        if(arrNotaMedIngresoEvol==null){  if(event.getTab().getId().equals("tabNMedicaGeneral")) buscaHistorialNotaMedIngresoEvolucion();  }
    } 
    
    public String getTabSelect(){
        return sTabSeleccionado;
    }
    /*----------------------------------------------------------------------------------------------------------*/
    
    
    
   public NivelSocioEconomico getEstudioSocEco(){
           return oEstudioSocEco;
   }
    
    public DetalleReceta getReceta(){
        return oReceta;
    }
    
    public String[] getListaIndicaciones(){
        return listaIndicaciones;
    }
    
    public NotaMedicaHRRB getNotaIngresoEvolucion(){
        return oNotaIngresoEvolucion;
    }
    
    public Traslado getHojaReferencia(){
        return oHojaReferencia;
    }
    
    public EstudioRealizado[] getListAuxDiagHojaReferencia(){
        return arrEstuRealReferencia;
    } 
    
    public NotaPostoperatoria getNotaPostoperatoria(){
        return oNotaPostoperatoria;
    }
        
    public NotaPreoperatoria[] getArrNPAdiagnosticos() {
        return arrNPdiagnosticosAgregados;
    }
    
    public NotaPreoperatoria getNotaPreopera() {
        return oNotaPreopera;
    }
    
    public NotaPreanestesica getNotaPreanestesica(){
        return oNotapreanestesica;
    }
    
    public HistoriaClinica getHistoriaClinica(){
        return oHistoriaClinica;
    }
    
    public DiagnosticoCIE10 getCie10(){
        return oCie10;
    }
    
    public HistoriaClinicaPerinatal getDiagnosticos(){
        return oPerinatal;
    }
    
    public EvaluacionEdadGestacionalCapurro getEvaluacionEdadGestacionalCapurro(){
        return oEvaluacionEdadg;
    }
    
    public ValoracionNeurologica getNeurologica(){
        return oNeurologica;
    }
    
    public HistoriaClinicaPerinatal getHistoriaClinicaPerinatal(){
        return oHperinatal;
    }
    
    public ValoracionEpidemiologico getValoracion(){
        return oValoracion;
    }
    
    public CalificacionSilvermann getCalificacionSilvermann(){
        return oSilvermann;
    }
    
    public CalificacionApgar getCalificacionApgar(){
        return oCalificacion;
    }
    
    public DiagnosticoCIE10 getDiagnostico2(){
        return oDiagnostico2;
    }
    
    public DiagnosticoCIE10[] getArregloDiagnosticosAtnNeonatal(){
        return arrRetAtnNeonatal;
    }
    
    public AtencionNeonatalTococirugia getDatosAtencion(){
        return oDatosAtencion;
    }
    
    public NotaPostOclusionTubariaPostparto getNotaPostOclusionTubariaPostparto(){
        return npotp;
    }
    
    public NotaPreoperatoria getNotaPreoperatoria(){
        return oNotaPreoperatoria;
    }
    
    public EvolucionPartoLegrado getEvolucionPartoLegrado(){
        return oEvolucion;
    }
    
    public ArrayList<EvolucionPartoLegrado> getArregloEvolucion(){
        return arrEvolucion;
    }
    
    public Legrado getLegrado(){
        return oLegrado;
    }
    
    public Placenta getPlacenta(){
        return oPlacenta;
    }
    
    public AnalgesiaTparto getPartoLegrado(){
        return oPartoLegrado;
    }
    
    public AnalgesiaTparto getAnalgesiaTparto(){
        return oAnalgesiaTparto;
    }
    
    public Alumbramiento getAlumbramiento(){
        return oAlumbramiento;
    }
    
    public Cesarea getCesarea(){
        return oCesarea;
    }
    
    public Forceps getForceps(){
        return oForceps;
    }
    
    public PartoEutocico getPartoEutocico(){
        return oPartoEutocico;
    }
    
    public ArrayList<Producto> getArrDetalleProducto(){
            return arrDetalleProducto;
        }
    
    public Producto getProducto(){
        return oProducto;
    }
    
    public SeguimientoTrabajoParto getDetalleSeguimiento(){
        return oDetalleSeguimiento;
    }
    
    public SeguimientoTrabajoParto[] getDetalleSeguimientoSignosVitales(){
        return arrDetalleSeguimientoSignosvitales;
    }
    
    public SegundaMitadEmbarazo getDetalleSegundaMitadEmbarazo(){
        return oDetalleSegundaMitadEmbarazo;
    }
    
    public PrimeraMitadEmbarazo getDetallePrimeraMitadEmbarazo(){
        return oDetallePrimeraMitadEmbarazo;
    }
    
    public PartoGrama getConsultaDatosPartoGrama(){
        return oConsultaDatosPartoGrama;
    }
    
    public PartoGrama getConsultaPartoGrama1(){
        return oConsultapartograma;
    }
    
    public DiagnosticoCIE10[] getArrDiagnosticosTriage(){
        return arrDiagnosticosTriage;
    }
    
    public NotaEmbarazo getHTriageAnverso(){
        return oHojaTriageAnverso;
    }
    
    public NotaEmbarazo getHTRIAGE(){
        return oHojaTriage;
    }
    
    public HojaFrontal getHF(){
        return oHojaFrontal;
    }
    
    public NotaTrabajoSocial getHojaTrabSoc(){
        return oNotaTS;
    }
    
    public NotaTrabajoSocial[] getObservacionesPorNota(){
        return oArrObservacionesPorNota;
    }
    
    public ArrayList<MustraSignosVitalesHojaQx> getArrMustraSignos() {
        return arrMustraSignos;
    }
    
    public HojaEnfermeriaQuirofano getHojaEnfQx(){
        return oHojaEnfQx;
    }
        
    public HojaDeTransfusion getTransfusionDatos() {
        return oTransfusion;
    }

    public HojaDeTransfusion[] getTranAgregadaBase() {
        return oTranAgregadaBase;
    }
    
    public EpisodioMedico getEpi(){
        return oEpi;
    }
    
    public void setEpi(EpisodioMedico oEpi){
        this.oEpi=oEpi;
    }

    public long getFolioPac() {
        return nFolioPac;
    }

    public void setFolioPac(long nFolioPac) {
        this.nFolioPac = nFolioPac;
    }

    public int getNumExpediente() {
        return nNumExp;
    }

    public void setNumExpediente(int nNumExp) {
        this.nNumExp = nNumExp;
    }

    public long getClave() {
        return nClaveEpi;
    }

    public void setClave(int clave) {
        this.nClaveEpi = clave;
    }  
    
    
    //DETALLES DE SEGUIMIENTO PARA EL PARTOGRAMA
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
        
        public String detalleDegarro(short opcion){            
            if(opcion == 0)
                return oPartoEutocico.getDesgarro().get(opcion).getValor()== null || oPartoEutocico.getDesgarro().get(opcion).getValor().isEmpty() ? "PERINÉ ( )" : oPartoEutocico.getDesgarro().get(opcion).getValor() + "(X)";
            if(opcion == 1)
                return oPartoEutocico.getDesgarro().get(opcion).getValor() == null || oPartoEutocico.getDesgarro().get(opcion).getValor().isEmpty() ? "VAGINA ( )" : oPartoEutocico.getDesgarro().get(opcion).getValor() + "(X)";
            if(opcion == 2)
                return oPartoEutocico.getDesgarro().get(opcion).getValor() == null || oPartoEutocico.getDesgarro().get(opcion).getValor().isEmpty() ? "CÉRVIX ( )" : oPartoEutocico.getDesgarro().get(opcion).getValor() + "(X)";
            return "";
        }
        
        public int getDetalleSemanasAmenorrea(){
            if(arrDetalleProducto.isEmpty())
                return 0;
            else
                return arrDetalleProducto.get(0).getTerminacionEmbarazo().getPartoGrama().getSegundaMitadEmbarazo().getSemanasAmerorrea();
        }
        
        public String valorArrDetalleEvolucionMedico(){
        String sRet = null;
            if(!arrEvolucion.isEmpty())
                sRet = arrEvolucion.get(0).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto();
            return sRet;
        }
        
        public String valorArrDetalleCedMedico(){
        String sRet=null;
            if(!arrEvolucion.isEmpty())
                sRet = arrEvolucion.get(0).getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getCedProf();
            return sRet;
        }
        
    public long calculaEdadGestacional(){
        Calendar fechaultimaregla = new GregorianCalendar();
        Calendar fechanacimiento = new GregorianCalendar();
        fechaultimaregla.setTime(this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion());
        fechanacimiento.setTime(this.oDatosAtencion.getPacienteNeonato().getProducto().getFechaNacimiento());
        long resta = fechanacimiento.getTimeInMillis() - fechaultimaregla.getTimeInMillis();
        long semanas = resta / 604800000;// VALOR EQUIVALENTE AL NUMERO DE MILISEGUNDOS EN UNA SEMANA COMPLETA [7 DIAS]
        this.oDatosAtencion.setEdadgestacionalEGFUR((int)semanas); 
        return semanas;
    }
        
    public int sumaApgar1Minuto(){
        this.oCalificacion.setTotalMin(this.oCalificacion.getFcMin() + this.oCalificacion.getEsfuerzoRespiratorioMin() + this.oCalificacion.getTonoMuscularMin() + this.oCalificacion.getIrritabilidadMin() + this.oCalificacion.getColorMin());
        return this.oCalificacion.getTotalMin();
    }
    
    public int sumaApgar5Minutos(){
        this.oCalificacion.setTotal5Min(this.oCalificacion.getFc5Min() + this.oCalificacion.getEsfuerzoRespiratorio5Min() + this.oCalificacion.getTonoMuscular5Min() + this.oCalificacion.getIrritabilidad5Min() + this.oCalificacion.getColor5Min());
        return this.oCalificacion.getTotal5Min();
    }
    
    public int sumaSilvermann(){
        this.oSilvermann.setTotal(this.oSilvermann.getAleteoNasal() + this.oSilvermann.getQResp() + this.oSilvermann.getTInter() + this.oSilvermann.getRetraccion() + this.oSilvermann.getDisociacion());
        return this.oSilvermann.getTotal();
    }
    
    public float puntajeTotal(){
        this.oValoracion.setPuntageTotal(this.oValoracion.getValoracionPesoNacer() + this.oValoracion.getValoracionEdadMaterna() + this.oValoracion.getValoracionEdadGestacional() + this.oValoracion.getValoracionEmParto() + this.oValoracion.getValoracionApgar() + this.oValoracion.getValoracionReanimacion());
        return this.oValoracion.getPuntageTotal();
    }
    
    public String mensaje(){
        if(this.oValoracion.getPuntageTotal() > 30){
            this.oValoracion.setUnidadCorresp("UCIN");
            return "UCIN";
        }
        if(this.oValoracion.getPuntageTotal() >= 15 && this.oValoracion.getPuntageTotal() <= 30 ){
            this.oValoracion.setUnidadCorresp("CUIDADOS INTERMEDIOS");
            return "CUIDADOS INTERMEDIOS";
        }
        if(this.oValoracion.getPuntageTotal() < 15){
            this.oValoracion.setUnidadCorresp("ALOJAMIENTO CONJUNTO");
            return "ALOJAMIENTO CONJUNTO";
        }
        return null;
    }
    
    public void sumatoria(){
        int sumatoria = 0;
        sumatoria = this.oNeurologica.getPostura() + this.oNeurologica.getAnguloMuneca() + this.oNeurologica.getAnguloCodo() +
                this.oNeurologica.getAnguloPopliteo() + this.oNeurologica.getSignoBufanda() + this.oNeurologica.getTalonOreja()+
                this.oNeurologica.getPiel() + this.oNeurologica.getLamugo() + this.oNeurologica.getPlieguesPlantares() +
                this.oNeurologica.getMamas() + this.oNeurologica.getOido() + this.oNeurologica.getGenitalesHombre() +
                this.oNeurologica.getGenitalesMujer();        
        this.oNeurologica.setCalificacion(sumatoria);
        semanas();
    }
    public void semanas(){
        if(this.oNeurologica.getCalificacion() > 0 && this.oNeurologica.getCalificacion() <= 5)
            this.oNeurologica.setSemanas(26);
        else{
            if(this.oNeurologica.getCalificacion()> 5 && this.oNeurologica.getCalificacion() <= 10)
                this.oNeurologica.setSemanas(28);
            else{
                if(this.oNeurologica.getCalificacion() > 10 && this.oNeurologica.getCalificacion() <= 15)
                    this.oNeurologica.setSemanas(30);
                else{
                    if(this.oNeurologica.getCalificacion() > 15 && this.oNeurologica.getCalificacion() <= 20)
                        this.oNeurologica.setSemanas(32);
                    else{
                        if(this.oNeurologica.getCalificacion() > 20 && this.oNeurologica.getCalificacion() <= 25)
                            this.oNeurologica.setSemanas(34);
                        else{
                            if(this.oNeurologica.getCalificacion() > 25 && this.oNeurologica.getCalificacion() <= 30)
                                this.oNeurologica.setSemanas(36);
                            else{
                                if(this.oNeurologica.getCalificacion() > 30 && this.oNeurologica.getCalificacion() <= 35)
                                    this.oNeurologica.setSemanas(38);
                                else{
                                    if(this.oNeurologica.getCalificacion() > 35 && this.oNeurologica.getCalificacion() <= 40)
                                        this.oNeurologica.setSemanas(40);
                                    else{
                                        if(this.oNeurologica.getCalificacion() > 40 && this.oNeurologica.getCalificacion() <= 45)
                                            this.oNeurologica.setSemanas(42);
                                        else{
                                            if(this.oNeurologica.getCalificacion() >45)
                                                this.oNeurologica.setSemanas(44);
                                        }
                                    }
                                }
                            }                              
                        }
                    }
                }
            }
        }
    }
    
    public void operacion(){
        int texturapiel = this.oEvaluacionEdadg.getTexturaPiel().getTipoParametro() == null || this.oEvaluacionEdadg.getTexturaPiel().getTipoParametro().isEmpty() ? 0 : Integer.parseInt(this.oEvaluacionEdadg.getTexturaPiel().getTipoParametro());
        int plieguesplantares = this.oEvaluacionEdadg.getPlieguesPlantares().getTipoParametro() == null || this.oEvaluacionEdadg.getPlieguesPlantares().getTipoParametro().isEmpty() ? 0 : Integer.parseInt(this.oEvaluacionEdadg.getPlieguesPlantares().getTipoParametro());
        int formaoreja = this.oEvaluacionEdadg.getFormaOreja().getTipoParametro() == null || this.oEvaluacionEdadg.getFormaOreja().getTipoParametro().isEmpty() ? 0 : Integer.parseInt(this.oEvaluacionEdadg.getFormaOreja().getTipoParametro());
        int glandula = this.oEvaluacionEdadg.getTglandulaMama().getTipoParametro() == null || this.oEvaluacionEdadg.getTglandulaMama().getTipoParametro().isEmpty() ? 0 : Integer.parseInt(this.oEvaluacionEdadg.getTglandulaMama().getTipoParametro());
        int pezon = this.oEvaluacionEdadg.getFormaPezon().getTipoParametro() == null || this.oEvaluacionEdadg.getFormaPezon().getTipoParametro().isEmpty() ? 0 : Integer.parseInt(this.oEvaluacionEdadg.getFormaPezon().getTipoParametro());        
        this.oEvaluacionEdadg.setTotal((204 + texturapiel + plieguesplantares + formaoreja + glandula + pezon)/7);        
    }
    
    //*****************INICIAN METODOS DE CONTROL DE LA GRAFICA*****************
    private void createLineModels() {
        oPinta = initLinearModel();
        oPinta.setTitle("PESO AL NACER EN RELACION CON LA EDAD GESTACIONAL");
        oPinta.setLegendPosition("nw");
        oPinta.setShowPointLabels(true);
        oPinta.getAxes().put(AxisType.X, new CategoryAxis("SEMANAS"));
        Axis  yAxis = oPinta.getAxis(AxisType.Y);        
        yAxis.setLabel("PESO AL NACER, GRAMOS");
        yAxis.setMin(700);
        yAxis.setMax(4000);
    }
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries linea1 = new ChartSeries();
        linea1.setLabel("Fx1");
        linea1.set("23", null);
        linea1.set("24", 800);
        linea1.set("25", null);        
        linea1.set("26", 900);
        linea1.set("27", null);
        linea1.set("28", 950);
        linea1.set("29", null);
        linea1.set("30", 1100);
        linea1.set("31", null);
        linea1.set("32", 1400);
        linea1.set("33", null);
        linea1.set("34", 1800);
        linea1.set("35", null);
        linea1.set("36", 2350);
        linea1.set("37", null);
        linea1.set("38", 2500);
        linea1.set("39", null);
        linea1.set("40", 2600);
        linea1.set("41", 2650);
        linea1.set("42", 2680);
        linea1.set("43", null);
        linea1.set("44", null);
        linea1.set("45", null);
        linea1.set("46", null);
 
        ChartSeries linea2 = new ChartSeries();
        linea2.setLabel("Fx2");
        linea2.set("23", null);
        linea2.set("24", 900);
        linea2.set("25", null);
        linea2.set("26", 1010);
        linea2.set("27", null);
        linea2.set("28", 1290);
        linea2.set("29", null);
        linea2.set("30", 1500);
        linea2.set("31", null);
        linea2.set("32", 1795);
        linea2.set("33", null);
        linea2.set("34", 2695);
        linea2.set("35", null);
        linea2.set("36", 3000);
        linea2.set("37", null);
        linea2.set("38", 3110);
        linea2.set("39", null);
        linea2.set("40", 3200);
        linea2.set("41", 3220);
        linea2.set("42", 3235);
        linea2.set("43", null);
        linea2.set("44", 3240);
        linea2.set("45", null);
        linea2.set("46", null);
        
        ChartSeries linea3 = new ChartSeries();
        linea3.setLabel("Fx3");
        linea3.set("23", null);
        linea3.set("24", 1200);
        linea3.set("25", null);
        linea3.set("26", 1350);
        linea3.set("27", null);
        linea3.set("28", 1600);
        linea3.set("29", null);
        linea3.set("30", 1800);
        linea3.set("31", null);
        linea3.set("32", 2100);
        linea3.set("33", null);
        linea3.set("34", 3000);
        linea3.set("35", null);
        linea3.set("36", 3500);
        linea3.set("37", null);
        linea3.set("38", 3700);
        linea3.set("39", null);
        linea3.set("40", 3710);
        linea3.set("41", 3720);
        linea3.set("42", 3720);
        linea3.set("43", null);
        linea3.set("44", 3730);
        linea3.set("45", null);
        linea3.set("46", null);
        
        ChartSeries punto = new ChartSeries();
        punto.setLabel("EG");
        //System.out.println("total "+this.oEvaluacionEdadg.getTotal());
        //System.out.println("peso "+oHperinatal.getPacientenNeonato().getPeso()*1000);
        punto.set(String.valueOf(this.oEvaluacionEdadg.getTotal()),(oHperinatal.getPacientenNeonato().getPeso()*1000));
        //System.out.println(punto);
        model.addSeries(linea1);
        model.addSeries(linea2);
        model.addSeries(linea3);
        model.addSeries(punto);
        //System.out.println(model);
        return model;
    }
    
    public LineChartModel getPinta() {
        return oPinta;
    }
    
    
    public String getUrge(){
        if(getHojaReferencia().isUrgente()==true)
            return bUrge="SI";
        else 
            return bUrge="NO";
    }
    
    public boolean renderizaOtroMotivoEnvio(){
        if(getHojaReferencia().getMotivoEnvio().getClaveParametro().charAt(0)=='7')
            bOtroMotivoEnv=true;
        return bOtroMotivoEnv;
    }
    
    public boolean renderizaMotivoEnvio(){
        if(getHojaReferencia().getMotivoEnvio().getClaveParametro().charAt(0)!='7')
            bMotivoEnv=true;
        return bMotivoEnv;
    }

    public void asignaRutaEstudio(String ruta){
        this.sUrlParteSelected="partes/estudios.xhtml"; 
        this.sTitulo="RESULTADO DE ESTUDIO";
        this.sRutaEstudio=ruta;
    }

    public String getRutaEstudio() {
        return sRutaEstudio;
    }
    
    public void validaSeleccionArea(){   
        sArea1=sArea2=sArea3="";
        if(!oReceta.getEpisodio().getArea().getTipo().isEmpty()){
            switch (oReceta.getEpisodio().getArea().getTipo()) {
                case "CE":
                    sArea1 = "X";
                    sArea2 = "";
                    sArea3 = "";
                    break;
                case "HOSP":
                case "HOSP-P":
                case "HOSPSUB":
                    sArea1 = "";
                    sArea2 = "X";
                    sArea3 = "";
                    break;
                case "URG":
                    sArea1 = "";
                    sArea2 = "";
                    sArea3 = "X";
                    break;
            }
        }
    }
    
    public void desglosar(){
        String num="";
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
        if(!oReceta.getEpisodio().getPaciente().getSeg().getNumero().isEmpty()){
            num = oReceta.getEpisodio().getPaciente().getSeg().getNumero();
            char[] arrC = num.toCharArray();
            for(int i=0; i<arrC.length; i++){
                sSeguroDesglosado[i] = ""+arrC[i];
            }
        }
    }
    
    public String[] getSeguroDesglosado() {
        return sSeguroDesglosado;
    }
    
    public String getArea1() {
        return sArea1;
    }

    public String getArea2() {
        return sArea2;
    }

    public String getArea3() {
        return sArea3;
    }
    
    public void calculaDerechoHabiente() {
        if (this.getEstudioSocEco().getPac().getSeg().getDerechohabienteP() == null) {
            this.getDetechoHab()[0] = "";
            this.getDetechoHab()[1] = "";
            this.getDetechoHab()[2] = "";
            this.getDetechoHab()[3] = "";
            this.getDetechoHab()[4] = "";
            this.getDetechoHab()[5] = "";
        } else {
            this.getDetechoHab()[0] = "";
            this.getDetechoHab()[1] = "";
            this.getDetechoHab()[2] = "";
            this.getDetechoHab()[3] = "";
            this.getDetechoHab()[4] = "X";//seguro popular
            this.getDetechoHab()[5] = "";
        }
    }
    
    public String[] getDetechoHab() {
        return sDetechoHab;
    }

    public void setDetechoHab(String[] sDetechoHab) {
        this.sDetechoHab = sDetechoHab;
    }
    
    public void determinaPuntajeIngresoFamiliar(){
        if(this.getEstudioSocEco().getColIngreFamSelecc()==1)
            nPuntajeIngresoFamMensual=this.getEstudioSocEco().getIngreFamSelecc().getPtjDepen1_2();
        if(this.getEstudioSocEco().getColIngreFamSelecc()==2)
            nPuntajeIngresoFamMensual=this.getEstudioSocEco().getIngreFamSelecc().getPtjDepen3_4();
        if(this.getEstudioSocEco().getColIngreFamSelecc()==3)
            nPuntajeIngresoFamMensual=this.getEstudioSocEco().getIngreFamSelecc().getPtjDepen5_6();
        if(this.getEstudioSocEco().getColIngreFamSelecc()==4)
            nPuntajeIngresoFamMensual=this.getEstudioSocEco().getIngreFamSelecc().getPtjDepen7_8();
        if(this.getEstudioSocEco().getColIngreFamSelecc()==5)
            nPuntajeIngresoFamMensual=this.getEstudioSocEco().getIngreFamSelecc().getPtjDepen9();
    }
    
    public int getPuntajeIngresoFamMensual() {
        return nPuntajeIngresoFamMensual;
    }

    public void setPuntajeIngresoFamMensual(int nPuntajeIngresoFamMensual) {
        this.nPuntajeIngresoFamMensual = nPuntajeIngresoFamMensual;
    }
    
    public int getPuntajeEgresoFamMensual() {
        return nPuntajeEgresoFamMensual;
    }

    public void setPuntajeEgresoFamMensual(int nPuntajeEgresoFamMensual) {
        this.nPuntajeEgresoFamMensual = nPuntajeEgresoFamMensual;
    }
    
    public int getPuntajeMensualVivienda() {
        return nPuntajeMensualVivienda;
    }

    public void setPuntajeMensualVivienda(int nPuntajeMensualVivienda) {
        this.nPuntajeMensualVivienda = nPuntajeMensualVivienda;
    }
    
    public int getPuntajeMensualOcupacion() {
        return nPuntajeMensualOcupacion;
    }

    public void setPuntajeMensualOcupacion(int nPuntajeMensualOcupacion) {
        this.nPuntajeMensualOcupacion = nPuntajeMensualOcupacion;
    }
    
    public int getPuntajeMensualSalud() {
        return nPuntajeMensualSalud;
    }

    public void setPuntajeMensualSalud(int nPuntajeMensualSalud) {
        this.nPuntajeMensualSalud = nPuntajeMensualSalud;
    }
    
    
    
    public void buscatablaEgresos(){
       SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd");
       String strDateRegis = SF.format(this.oEstudioSocEco.getRegistro());
       int i;
        try{
           arrEgresoFamiliarCapturado=new EgresoFamiliarCapturado().buscarMontosDeEgresoFamil(this.getFolioPac(),strDateRegis);
           this.nTotalegre=0;
           if (arrEgresoFamiliarCapturado != null) {
                for (i = 0; i < arrEgresoFamiliarCapturado.length; i++) {
                    this.nTotalegre = this.nTotalegre + arrEgresoFamiliarCapturado[i].getMonto().longValue();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public long getTotalEgre() {
        return nTotalegre;
    }
    
    public EgresoFamiliarCapturado[] getArrEgresoFamiliarCapturado(){
        return arrEgresoFamiliarCapturado;
    }
    
    public void buscaRespuestasPreguntasBasicasTS(){ 
       SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd");
       String strDateRegis = SF.format(this.oEstudioSocEco.getRegistro());
       int i,n;
     try{
           arrRespuestasPreguntasBasicasTS= new EncuestaTrabsocPregBasicas().buscaRespuestasBasicasTS(this.getFolioPac(), strDateRegis);
           this.nPuntajeMensualVivienda=0;
           this.nPuntajeMensualOcupacion=0;
           this.nPuntajeMensualSalud=0;
           for (i = 0; i < arrRespuestasPreguntasBasicasTS.length ; i++) {
                if(i >= 0 && i < 7){
                    this.nPuntajeMensualVivienda = this.nPuntajeMensualVivienda + arrRespuestasPreguntasBasicasTS[i].getPuntaje();
                    //System.out.println("posicionvivienda "+i+" "+this.nPuntajeMensualVivienda);
                }else{
                    if(i == 7){
                        this.nPuntajeMensualOcupacion = this.nPuntajeMensualOcupacion + arrRespuestasPreguntasBasicasTS[i].getPuntaje();
                        //System.out.println("posicionocupacion "+i+" "+this.nPuntajeMensualOcupacion);
                    }else{
                        if(i >= 8){
                            this.nPuntajeMensualSalud = this.nPuntajeMensualSalud + arrRespuestasPreguntasBasicasTS[i].getPuntaje();
                            //System.out.println("posicionsalud "+i+" "+this.nPuntajeMensualSalud);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }  
    }
    
    public EncuestaTrabsocPregBasicas[] getArrRespuestasPreguntasBasicasTS(){
        return arrRespuestasPreguntasBasicasTS;
    }
    
    public String[] getAsignaRespuesta(){
        return sAsignaRespuesta;
    }
    
    public void determinaPuntajeEgreso(){
        this.nPuntajeEgresoFamMensual=(this.oEstudioSocEco.getPuntaje() - (this.getPuntajeMensualOcupacion() + this.getPuntajeMensualSalud() + this.getPuntajeMensualVivienda() + this.getPuntajeIngresoFamMensual()));
    }

    public String getUrlParteSelected() {
        return sUrlParteSelected;
    }

    public void setUrlParteSelected(String sUrlParteSelected) {
        this.sUrlParteSelected = sUrlParteSelected;
    }
    public String getTitulo() {
        return sTitulo;
    }

    public void setTitulo(String sTitulo) {
        this.sTitulo = sTitulo;
    }
}