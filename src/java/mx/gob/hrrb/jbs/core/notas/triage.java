package mx.gob.hrrb.jbs.core.notas;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.NotaEmbarazo;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.MetodoAnticonceptivo;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import mx.gob.hrrb.modelo.core.Paciente;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Alberto
 */
@ManagedBean (name = "triage")
@ViewScoped
public class triage implements Serializable {
    private EpisodioMedico oEpisodioMedico;
    private Medico oMedFirm;    
    private NotaEmbarazo oNotaEmbarazo;
    private boolean bShow;
    private boolean codRojo;
    private boolean codAma;
    private boolean codVer;
    private Date codigoRojo;
    private Date codigoAmarillo;
    private Date codigoVerde;
    private Parametrizacion oParametrizacion;
    private boolean codRojValor0;
    private boolean codRojValor1;
    private boolean codRojValor2;
    private boolean codRojValor3;
    private boolean codRojValor4;
    private boolean codRojValor5;
    private boolean codRojValor6;
    private boolean codRojValor7;
    private boolean codRojValor8;
    private boolean codRojValor9;
    private boolean codAmaValor0;
    private boolean codAmaValor1;
    private boolean codAmaValor2;
    private boolean codAmaValor3;
    private boolean codAmaValor4;
    private boolean codAmaValor5;
    private boolean codAmaValor6;
    private boolean codAmaValor7;
    private boolean codVerValor0;
    private boolean codVerValor1;
    private boolean codVerValor2;
    private boolean codVerValor3;  
    private boolean bEditable1;    
    private boolean bHabilitaOtro;
    private boolean bHabilitaOtro1;
    private boolean bGrupoRh;
    private boolean bShowRh;
    private boolean bDrogas;
    private boolean bDiabetico;
    private boolean bHiperTenso;
    private boolean bHabilitaInput;
    private boolean bRadica0;
    private boolean bRadica1;
    private boolean bOriginario0;
    private boolean bOriginario1;
    private String sTipoSangre;
    private String sTipoFactor; 
    private int fondoUterino;
    private String situacionTemp;
    private String presentacionTemp;
    private String amniosTemp;
    private String liquidoTemp;
    private String pelvisTemp;
    private String trazoTemp;
    private DiagnosticoCIE10 oCie10;
    private ArrayList<DiagnosticoCIE10> arrDiagCie10;
    private ArrayList<DiagnosticoCIE10> arrConsultaCie10;
    private String pronosticoTemp;
    private Date hAtn;
    private Paciente oPaciente;
    private Paciente[] datosPaciente;
    private boolean bBoton;
    /*ATRRIBUTOS PARA LA CONSULTA DE LAS HOJAS DE TRIAGE*/       
    private NotaEmbarazo oDetallePaciente;
    private NotaEmbarazo oDetalleReverso;
    private EpisodioMedico oDetallePacienteEP;
    private boolean banderaConsulta;
    private long fpaciente;
    private long clavepisodio;
    private NotaEmbarazo[] datosPacEmbarazo;
    private NotaEmbarazo tempNotaEmbarazo;    
    private DiagnosticoCIE10 arrRet[];    
    /*TERMINAN ATRIBUTOS PARA LA HOJA DE TRIAGE*/
    private boolean bModifica;
    private boolean bControlaHoraRojo;
    private boolean bControlaHoraAma;
    private boolean bControlaHoraVer;
    private boolean bTab1;
    private boolean bTab2;
    private NotaEmbarazo oPacienteNota;
    private NotaEmbarazo arrPaciente[];
    public triage()throws Exception {
        oNotaEmbarazo = new NotaEmbarazo();        
        codVerValor0 = false;
        oCie10 = new DiagnosticoCIE10();
        arrDiagCie10 = new ArrayList<DiagnosticoCIE10>();
        bShow = false;
        codRojo = true;
        codAma = true;
        codVer = true;
        bShowRh = false;
        oPaciente = new Paciente();
        this.bTab1 = true;
        this.bTab2 = false;
        oPacienteNota = new NotaEmbarazo();
    }
    //*****INICIAN METODOS CONTROL DE DATOS*****    
    public void requerir(TabChangeEvent event){
        if(event.getTab().getId().compareTo("tab1") != 0){
            this.bTab1 = !bTab1;
            this.bTab2 = !bTab2;
        }else{
            this.bTab1 = !bTab1;
            this.bTab2 = !bTab2;
        }
    }
    public void buscarDatosPacienteregistroTriage(int opcion){
        try{
            this.oPacienteNota.getEpiMed().getPaciente().setOpcionUrg(opcion);
            this.arrPaciente = this.oPacienteNota.buscaPacientesValoracionTriage();
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    /*public void buscarDatosPacienteregistroTriage(int opcion){
        try{
            oPaciente.setOpcionUrg(opcion);
            if(oPaciente.getNombres() != null || !oPaciente.getNombres().isEmpty() || oPaciente.getExpediente().getNumero() != 0 || oPaciente.getExpediente().equals(null)){
                datosPaciente = oPaciente.buscarPacienteDatos();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
    public Paciente[] getPacienteDatos(){
        return datosPaciente;
    }
    public void metodoCargaDatosGenearal(NotaEmbarazo opaciente, short opc, NotaEmbarazo oNota){
        try{
            if(opc == 0){
                if(opaciente.getEpiMed().getPaciente().getSexoP().trim().compareTo("F") == 0){
                    this.bShow = false;                    
                    this.oNotaEmbarazo.getEpiMed().getPaciente().setFolioPaciente(opaciente.getEpiMed().getPaciente().getFolioPaciente());
                    this.oNotaEmbarazo.getEpiMed().getPaciente().setClaveEpisodio(opaciente.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oNotaEmbarazo.getEpiMed().getPaciente().setNombres(opaciente.getEpiMed().getPaciente().getNombres());
                    this.oNotaEmbarazo.getEpiMed().getPaciente().setApPaterno(opaciente.getEpiMed().getPaciente().getApPaterno());
                    this.oNotaEmbarazo.getEpiMed().getPaciente().setApMaterno(opaciente.getEpiMed().getPaciente().getApMaterno());
                    this.oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().setNumero(opaciente.getEpiMed().getPaciente().getExpediente().getNumero());
                    datosPacEmbarazo = oNotaEmbarazo.buscaPacienteDatos();
                    if(this.datosPacEmbarazo.length > 0)
                        this.tempNotaEmbarazo = this.datosPacEmbarazo[0];
                    if(this.datosPacEmbarazo.length == 1 && this.datosPacEmbarazo[0].getConsecutivo() == 0 && (this.datosPacEmbarazo[0].getMaxConsecutivo() != 0|| this.datosPacEmbarazo[0].getMaxConsecutivo() == 0))
                        this.bBoton = true;
                    else
                        this.bBoton = false;
                    RequestContext.getCurrentInstance().execute("PF('NotaP').show()");
                }else{
                    bShow = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","EL PACIENTE NO ES DE SEXO FEMENINO"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }
                
            }else if(opc == 1){
                if(this.tempNotaEmbarazo.getEpiMed().getPaciente().getSexoP().compareTo("F") == 0){
                    this.setModifica(true);
                    NotaEmbarazo oConsulta = new NotaEmbarazo();
                    this.oEpisodioMedico = new EpisodioMedico();
                    this.oNotaEmbarazo = new NotaEmbarazo();
                    this.oEpisodioMedico = oConsulta.datosPacienteTriage(oNota.getEpiMed().getPaciente().getFolioPaciente(), oNota.getEpiMed().getPaciente().getClaveEpisodio()); 
                    this.oNotaEmbarazo = oConsulta.datosPaciente(oNota.getEpiMed().getPaciente().getFolioPaciente(), oNota.getEpiMed().getPaciente().getClaveEpisodio(), oNota.getEpiMed().getPaciente().getExpediente().getNumero(), oNota.getConsecutivo()); 
                    this.oNotaEmbarazo.buscaPersonal();
                    this.oNotaEmbarazo.getEpiMed().getPaciente().setFolioPaciente(oNota.getEpiMed().getPaciente().getFolioPaciente());
                    this.oNotaEmbarazo.getEpiMed().setClaveEpisodio(oNota.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().setNumero(oNota.getEpiMed().getPaciente().getExpediente().getNumero());
                    this.oNotaEmbarazo.setConsecutivo(oNota.getConsecutivo());
                    if(this.oNotaEmbarazo.getHoraCodigoRojo() != null){
                        this.setControlaHoraRojo(false);
                        this.setControlaHoraAma(true);
                        this.setControlaHoraVer(true);
                        this.setCodigoRojo(false);
                        this.setCodigoAmarillo(true);
                        this.setCodigoverde(true);
                    }else if(this.oNotaEmbarazo.getHoraCodigoAmarillo() != null){
                        this.setControlaHoraAma(false);
                        this.setControlaHoraRojo(true);
                        this.setControlaHoraVer(true);
                        this.setCodigoAmarillo(false);
                        this.setCodigoRojo(true);
                        this.setCodigoverde(true);
                    }else if(this.oNotaEmbarazo.getHoraCodigoVerde() != null){
                        this.setControlaHoraVer(false);
                        this.setControlaHoraRojo(true);
                        this.setControlaHoraAma(true);
                        this.setCodigoverde(false);
                        this.setCodigoRojo(true);
                        this.setCodigoAmarillo(true);
                    }
                    this.FormatoHora();
                    this.cedula();
                    setFpaciente(oNota.getEpiMed().getPaciente().getFolioPaciente());
                    setClavepisodio(oNota.getEpiMed().getPaciente().getClaveEpisodio()); 
                    this.AsignaFondoUterino();
                    this.AsignaSituacion();
                    this.AsignaPresentacion();
                    this.AsignaAmnios();
                    this.AsignaLiquidoAmniotico();
                    this.AsignaPelvis();
                    this.AsignaTrazo();
                    this.AsignaMetodo();
                    this.AsignaEdoCivil();
                    this.codRojo0();
                    this.codRojo1();
                    this.codRojo2();
                    this.codRojo3();
                    this.codRojo4();
                    this.codRojo5();
                    this.codRojo6();
                    this.codRojo7();
                    this.codRojo8();
                    this.codRojo9();
                    this.codAma0();
                    this.codAma1();
                    this.codAma2();
                    this.codAma3();
                    this.codAma4();
                    this.codAma5();
                    this.codAma6();
                    this.codAma7();
                    this.codVer0();
                    this.codVer1();
                    this.codVer2();
                    this.codVer3();
                    this.bShow = true;
                }else{
                    bShow = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","EL PACIENTE NO ES DE SEXO FEMENINO"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }   
            }else if(opc == 2){
                if(this.tempNotaEmbarazo.getEpiMed().getPaciente().getSexoP().compareTo("F") == 0){
                    this.setModifica(false);                    
                    this.oEpisodioMedico = new EpisodioMedico();
                    this.oNotaEmbarazo = new NotaEmbarazo();
                    NotaEmbarazo oConsulta = new NotaEmbarazo();
                    this.setControlaHoraRojo(false);
                    this.setControlaHoraAma(false);
                    this.setControlaHoraVer(false);
                    this.setCodigoverde(true);
                    this.setCodigoAmarillo(true);
                    this.setCodigoRojo(true);
                    oEpisodioMedico = oConsulta.datosPacienteTriage(this.tempNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente(), this.tempNotaEmbarazo.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oNotaEmbarazo.buscaPersonal();
                    this.oNotaEmbarazo.getEpiMed().getPaciente().setFolioPaciente(this.tempNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente());
                    this.oNotaEmbarazo.getEpiMed().setClaveEpisodio(this.tempNotaEmbarazo.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().setNumero(this.tempNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getNumero());
                    this.oNotaEmbarazo.setConsecutivo(this.tempNotaEmbarazo.getMaxConsecutivo() + 1);
                    this.fondoUterino = 0;
                    this.situacionTemp = "";
                    this.presentacionTemp = "";
                    this.amniosTemp = "";
                    this.liquidoTemp = "";
                    this.pelvisTemp = "";
                    this.trazoTemp = "";
                    this.codRojo0();
                    this.codRojo1();
                    this.codRojo2();
                    this.codRojo3();
                    this.codRojo4();
                    this.codRojo5();
                    this.codRojo6();
                    this.codRojo7();
                    this.codRojo8();
                    this.codRojo9();
                    this.codAma0();
                    this.codAma1();
                    this.codAma2();
                    this.codAma3();
                    this.codAma4();
                    this.codAma5();
                    this.codAma6();
                    this.codAma7();
                    this.codVer0();
                    this.codVer1();
                    this.codVer2();
                    this.codVer3();
                    this.bShow = true;
                }else{
                    bShow = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","EL PACIENTE NO ES DE SEXO FEMENINO"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }   
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    public void guardaInformacion(){
        try{
            if(!this.getModifica()){
                if(oNotaEmbarazo.insertaTodo(oNotaEmbarazo, oEpisodioMedico) > 0)
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","REGISTROS GUARDADOS CON EXITO"));
                else
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,"INFORMACION","TUVIMOS PROBLEMAS AL GUARDAR LA INFORMACION"));
            }else{
                if(this.oNotaEmbarazo.insertaModificacionesAnverso(oNotaEmbarazo, oEpisodioMedico) > 0)
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "MODIFICACIÓN EXITOSA"));                                 
                else
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,"INFORMACION","TUVIMOS PROBLEMAS AL MODIFICAR LA INFORMACION"));}
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void registroDiagnostico(){
        try{
            if(getCie10().getClave().isEmpty()){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","NO PUEDE AGREGAR VACÍO"));
            }else{
                int nTam = arrRet == null ? 0 : arrRet.length;
                if(nTam >= 6 || arrDiagCie10.size() >= 6 || nTam + arrDiagCie10.size() >= 6){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "IMPOSIBLE AGREGAR MAS DIAGNOSTICOS MAXIMO 6 DIAGNOSTICOS POR PACIENTE"));
                    oCie10.setDescripcionDiag("");
                }else{
                    if(arrDiagCie10.isEmpty() && buscaRepetidoDxAnterior(oCie10.getClave()) != true){
                        arrDiagCie10.add(oCie10);
                        oCie10 = new DiagnosticoCIE10();
                        this.getCie10().setDescripcionDiag("");
                    }else{
                        if(buscaRepetido(oCie10.getClave())){
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                            getCie10().setDescripcionDiag("");                        
                        }else{
                            if(buscaRepetidoDxAnterior(oCie10.getClave())){
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                                getCie10().setDescripcionDiag("");
                            }else{
                                arrDiagCie10.add(oCie10);
                                oCie10 = new DiagnosticoCIE10();
                                getCie10().setDescripcionDiag("");
                            }
                        }                        
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }
    public boolean buscaRepetido(String clave){
        boolean bandera = false;
        for(DiagnosticoCIE10 i: arrDiagCie10){
            if(i.getClave().equals(clave)){
                bandera = true;
                break;
            }
        }
        return bandera;
    }
    public boolean buscaRepetidoDxAnterior(String clave){
        if(arrRet == null)
            return false;
        for(int i = 0; i < arrRet.length; i++)
            if(arrRet[i].getClave().compareTo(clave) == 0)
                return true;
        return false;
    }
    public void onDateSelectRojo(SelectEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        codRojo = false;
        codAma = true;
        codVer = true;
        oNotaEmbarazo.setHoraCodigoAmarillo(null);
        oNotaEmbarazo.setHoraCodigoVerde(null);
        codAmaValor0 = false;
        codAmaValor1 = false;
        codAmaValor2 = false;
        codAmaValor3 = false;
        codAmaValor4 = false;
        codAmaValor5 = false;
        codAmaValor6 = false;
        codAmaValor7 = false;
        codVerValor0 = false;
        codVerValor1 = false;
        codVerValor2 = false;
        codVerValor3 = false;                                
    }
    public void onDateSelectAmarillo(SelectEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        codAma = false;
        codRojo = true;
        codVer = true;
        oNotaEmbarazo.setHoraCodigoRojo(null);
        oNotaEmbarazo.setHoraCodigoVerde(null);
        codRojValor0 = false;
        codRojValor1 = false;
        codRojValor2 = false;
        codRojValor3 = false;
        codRojValor4 = false;
        codRojValor5 = false;        
        codRojValor6 = false;
        codRojValor7 = false;
        codRojValor8 = false;
        codRojValor9 = false;
        codRojValor5 = false;
        codVerValor0 = false;
        codVerValor1 = false;
        codVerValor2 = false;
        codVerValor3 = false;               
    }
    public void onDateSelectVerde(SelectEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        codVer = false;
        codRojo= true;
        codAma = true;
        oNotaEmbarazo.setHoraCodigoRojo(null);
        oNotaEmbarazo.setHoraCodigoAmarillo(null);
        codRojValor0 = false;
        codRojValor1 = false;
        codRojValor2 = false;
        codRojValor3 = false;
        codRojValor4 = false;
        codRojValor5 = false;        
        codRojValor6 = false;
        codRojValor7 = false;
        codRojValor8 = false;
        codRojValor9 = false;
        codAmaValor0 = false;
        codAmaValor1 = false;
        codAmaValor2 = false;
        codAmaValor3 = false;
        codAmaValor4 = false;
        codAmaValor5 = false;
        codAmaValor6 = false;
        codAmaValor7 = false;        
    }
    public List<Medico> getListMedicoUrgencias(){
        try{
            List<Medico> listaMedico = null;
            return listaMedico = new ArrayList<Medico>(Arrays.asList((new NotaEmbarazo().buscaMedicos((short) 0))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListEstadoCivil(){
        try{
            List<Parametrizacion> listaEstadoCivil = null;
            return listaEstadoCivil = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarEdosCiviles())));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListEscolaridad(){
        try{
            List<Parametrizacion> listaEscolaridad = null;
            return listaEscolaridad = new ArrayList<Parametrizacion>(Arrays.asList((new NotaEmbarazo().buscaValorParametrizacion((short)0))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListGrupo(){
        try{
            List<Parametrizacion> listaGrupo = null;
            return listaGrupo = new ArrayList<Parametrizacion>(Arrays.asList((new NotaEmbarazo().buscaValorParametrizacion((short)1))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListRh(){
        try{
            List<Parametrizacion> listaRh = null;
            return listaRh = new ArrayList<Parametrizacion>(Arrays.asList((new NotaEmbarazo().buscaValorParametrizacion((short)2))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListSituacion(){
        try{
            List<Parametrizacion> listaSituacion = null;
            return listaSituacion = new ArrayList<Parametrizacion>(Arrays.asList((new NotaEmbarazo().buscaValorParametrizacion((short)3))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListPresentacion(){
        try{
            List<Parametrizacion> listaPresentacion = null;
            return listaPresentacion = new ArrayList<Parametrizacion>(Arrays.asList((new NotaEmbarazo().buscaValorParametrizacion((short)4))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListPelvis(){
        try{
            List<Parametrizacion> listaPelvis = null;
            return listaPelvis = new ArrayList<Parametrizacion>(Arrays.asList((new NotaEmbarazo().buscaValorParametrizacion((short)5))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListTrazo(){
        try{
            List<Parametrizacion> listaTrazo = null;
            return listaTrazo = new ArrayList<Parametrizacion>(Arrays.asList((new NotaEmbarazo().buscaValorParametrizacion((short)6))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Parametrizacion> getListPronostico(){
        try{
            List<Parametrizacion> listaPronostico = null;
            return listaPronostico = new ArrayList<Parametrizacion>(Arrays.asList((new NotaEmbarazo().buscaValorParametrizacion((short)7))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<MetodoAnticonceptivo> getListMetodoAnticonceptivo(){
        try{
            List<MetodoAnticonceptivo> listaMetodo = null;
            return listaMetodo = new ArrayList<MetodoAnticonceptivo>(Arrays.asList((new NotaEmbarazo().buscaMetodoTriage())));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void cedula(){
        try{   
            if(oNotaEmbarazo.getMedicoSupervisor().getNombreCompleto().isEmpty())
                oNotaEmbarazo.getMedicoSupervisor().setCedProf("SELECCIONE UN MEDICO");            
            else{
                String cedulaProfesional = buscarMedico(oNotaEmbarazo.getMedicoSupervisor().getNombres());
                int nTarjeta = buscarMedico(oNotaEmbarazo);
                if(cedulaProfesional.equals(""))
                    oNotaEmbarazo.getMedicoSupervisor().setCedProf("NO DISPONIBLE");
                else
                    oNotaEmbarazo.getMedicoSupervisor().setCedProf(cedulaProfesional);
                if(nTarjeta != 0)
                    oNotaEmbarazo.getMedicoSupervisor().setNoTarjeta(nTarjeta);
                else
                    oNotaEmbarazo.getMedicoSupervisor().setNoTarjeta(0);                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cedulaMedicoSupervisor(){
        try{
            if(this.oNotaEmbarazo.getEpiMed().getMedicoTratante().getNoTarjeta() == 0)
                this.oNotaEmbarazo.getEpiMed().getMedicoTratante().setCedProf("SELECCIONE UN MEDICO");
            else{
                String cedulaProfesional = buscarMedico(this.oNotaEmbarazo.getEpiMed().getMedicoTratante().getNoTarjeta());
                if(cedulaProfesional.compareTo("") == 0)
                    this.oNotaEmbarazo.getEpiMed().getMedicoTratante().setCedProf("NO DISPONIBLE");
                else
                    this.oNotaEmbarazo.getEpiMed().getMedicoTratante().setCedProf(cedulaProfesional);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String buscarMedico(int medico){
        try{
            List<Medico> listaMedico = null;
            listaMedico = new ArrayList<Medico>(Arrays.asList((new NotaEmbarazo().buscaMedicos((short) 0))));
            for(Medico i: listaMedico){
                if(i.getNoTarjeta() == medico)
                    return i.getCedProf();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String buscarMedico(String medico){
        try{            
            List<Medico> listaMedico = null;
            listaMedico = new ArrayList<Medico>(Arrays.asList((new NotaEmbarazo().buscaMedicos((short) 0))));        
            for(Medico i: listaMedico){                
                if(i.getNombreCompleto().compareTo(medico) == 0)                                        
                    return i.getCedProf(); 
            }        
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public int buscarMedico(NotaEmbarazo oNotaEmbarazo){
        try{
            List<Medico> listaMedico = null;
            listaMedico = new ArrayList<Medico>(Arrays.asList((new NotaEmbarazo().buscaMedicos((short) 0))));
            for(Medico i: listaMedico){
                if(i.getNombreCompleto().compareTo(oNotaEmbarazo.getMedicoSupervisor().getNombres()) == 0)
                    return i.getNoTarjeta();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public void asignaCodigoRojoValor0(){
        try{
            String valor = codRojValor0 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setParoCardioDuranteEmb(true);
            else
                oNotaEmbarazo.setParoCardioDuranteEmb(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo0(){
        this.setCodRojValor0(this.oNotaEmbarazo.isParoCardioDuranteEmb());
    }
    public void asignaCodigoRojoValor1(){
        try{
            String valor = codRojValor1 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setAltEdoConciencia(true);
            else
                oNotaEmbarazo.setAltEdoConciencia(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo1(){
        this.setCodRojValor1(this.oNotaEmbarazo.isAltEdoConciencia());
    }
    public void asignaCodigoRojoValor2(){
        try{
            String valor = codRojValor2 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setInsufResp(true);
            else
                oNotaEmbarazo.setInsufResp(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo2(){
        this.setCodRojValor2(this.oNotaEmbarazo.isInsufResp());
    }
    public void asignaCodigoRojoValor3(){
        try{
            String valor = codRojValor3 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setConvulsiones(true);
            else
                oNotaEmbarazo.setConvulsiones(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo3(){
        this.setCodRojValor3(this.oNotaEmbarazo.isConvulsiones());
    }
    public void asignaCodigoRojoValor4(){
        try{
            String valor = codRojValor4 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setCefaleaIntensa(true);
            else
                oNotaEmbarazo.setCefaleaIntensa(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo4(){
        this.setCodRojValor4(this.oNotaEmbarazo.isCefaleaIntensa());
    }
    public void asignaCodigoRojoValor5(){
        try{
            String valor = codRojValor5 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setPreeclampsiaSevera(true);
            else
                oNotaEmbarazo.setPreeclampsiaSevera(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo5(){
        this.setCodRojValor5(this.oNotaEmbarazo.isPreeclampsiaSevera());
    }
    public void asignaCodigoRojoValor6(){
        try{
            String valor = codRojValor6 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setHemorragia(true);
            else
                oNotaEmbarazo.setHemorragia(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo6(){
        this.setCodRojValor6(this.oNotaEmbarazo.isHemorragia());
    }
    public void asignaCodigoRojoValor7(){
        try{
            String valor = codRojValor7 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setChoqueHipovolemico(true);
            else
                oNotaEmbarazo.setChoqueHipovolemico(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo7(){
        this.setCodRojValor7(this.oNotaEmbarazo.isChoqueHipovolemico());
    }
    public void asignaCodigoRojoValor8(){
        try{
            String valor = codRojValor8 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setSepsisEmbarazo(true);
            else
                oNotaEmbarazo.setSepsisEmbarazo(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo8(){
        this.setCodRojValor8(this.oNotaEmbarazo.isSepsisEmbarazo());
    }
    public void asignaCodigoRojoValor9(){
        try{
            String valor = codRojValor9 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setProlapsoCordonUmb(true);
            else
                oNotaEmbarazo.setProlapsoCordonUmb(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codRojo9(){
        this.setCodRojValor9(this.oNotaEmbarazo.isProlapsoCordonUmb());
    }
    public void asignaCodigoAmarilloValor0(){
        try{
            String valor = codAmaValor0 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setTrabajoParto(true);
            else
                oNotaEmbarazo.setTrabajoParto(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codAma0(){
        this.setCodAmaValor0(this.oNotaEmbarazo.isTrabajoParto());
    }
    public void asignaCodigoAmarilloValor1(){
        try{
            String valor = codAmaValor1 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setSalidaLiqSangVag(true);
            else
                oNotaEmbarazo.setSalidaLiqSangVag(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codAma1(){
        this.setCodAmaValor1(this.oNotaEmbarazo.isSalidaLiqSangVag());
    }
    public void asignaCodigoAmarilloValor2(){
        try{
            String valor = codAmaValor2 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setPreeclampsiaLeve(true);
            else
                oNotaEmbarazo.setPreeclampsiaLeve(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codAma2(){
        this.setCodAmaValor2(this.oNotaEmbarazo.isPreeclampsiaLeve());
    }
    public void asignaCodigoAmarilloValor3(){
        try{
            String valor = codAmaValor3 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setHipomotilidadFetal(true);
            else
                oNotaEmbarazo.setHipomotilidadFetal(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codAma3(){
        this.setCodAmaValor3(this.oNotaEmbarazo.isHipomotilidadFetal());
    }
    public void asignaCodigoAmarilloValor4(){
        try{
            String valor = codAmaValor4 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setRupPrematuraMembranas(true);
            else
                oNotaEmbarazo.setRupPrematuraMembranas(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codAma4(){
        this.setCodAmaValor4(this.oNotaEmbarazo.isRupPrematuraMembranas());
    }
    public void asignaCodigoAmarilloValor5(){
        try{
            String valor = codAmaValor5 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setEmbPatolCronica(true);
            else
                oNotaEmbarazo.setEmbPatolCronica(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codAma5(){
        this.setCodAmaValor5(this.oNotaEmbarazo.isEmbPatolCronica());
    }
    public void asignaCodigoAmarilloValor6(){
        try{
            String valor = codAmaValor6 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setAborto(true);
            else
                oNotaEmbarazo.setAborto(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codAma6(){
        this.setCodAmaValor6(this.oNotaEmbarazo.isAborto());
    }
    public void asignaCodigoAmarilloValor7(){
        try{
            String valor = codAmaValor7 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setPartoPretermino(true);
            else
                oNotaEmbarazo.setPartoPretermino(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codAma7(){
        this.setCodAmaValor7(this.oNotaEmbarazo.isPartoPretermino());
    }
    public void asignaCodigoVerdeValor0(){
        try{
            String valor = codVerValor0 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setEmbMenor3SDGsinTrabParto(true);
            else
                oNotaEmbarazo.setEmbMenor3SDGsinTrabParto(false);            
        }catch(Exception e){
                e.printStackTrace();
            }
    }
    public void codVer0(){
        this.setCodVerValor0(this.oNotaEmbarazo.isEmbMenor3SDGsinTrabParto());
    }
    public void asignaCodigoVerdeValor1(){
        try{
            String valor = codVerValor1 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setEmbAsintomatico(true);
            else
                oNotaEmbarazo.setEmbAsintomatico(false);            
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public void codVer1(){
        this.setCodVerValor1(this.oNotaEmbarazo.isEmbAsintomatico());
    }
    public void asignaCodigoVerdeValor2(){
        try{
            String valor = codVerValor2 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setPuerperioFisiologico(true);
            else
                oNotaEmbarazo.setPuerperioFisiologico(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codVer2(){
        this.setCodVerValor2(this.oNotaEmbarazo.isPuerperioFisiologico());
    }
    public void asignaCodigoVerdeValor3(){
        try{
            String valor = codVerValor3 ? "Checked" : "Unchecked";
            if(valor.equals("Checked"))
                oNotaEmbarazo.setPuerperioQxsinCompl(true);
            else
                oNotaEmbarazo.setPuerperioQxsinCompl(false);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void codVer3(){
        this.setCodVerValor3(this.oNotaEmbarazo.isPuerperioQxsinCompl());
    }
    public void habilitaOtros(){
        String valor = bHabilitaOtro ? "true" : "false";        
        if(valor.equals("true"))
            bHabilitaOtro = true;
        else
            bHabilitaOtro = false;
    }
    public void habilitaInputComp(){
        String valor = bHabilitaInput ? "true" : "false";
        if(valor.equals("true"))
            bHabilitaInput = true;
        else
            bHabilitaInput = false;
    }
    public void habilitaOtros1(){
        String valor = bHabilitaOtro1 ? "true" : "false";
        if(valor.compareTo("true") == 0)
            bHabilitaOtro1 = true;
        else
            bHabilitaOtro1 = false;
    }
    public void habilitaLista(){        
        if(getTipoSangre().isEmpty())
            bShowRh = false;
        else
            bShowRh = true;
    }
    public void asignaSangre(){
        try{
            Parametrizacion p = new Parametrizacion();
            oNotaEmbarazo.getEpiMed().getPaciente().setTipoSangre(new Parametrizacion());
            oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().setClaveParametro(getTipoSangre());
            oNotaEmbarazo.getEpiMed().getPaciente().setFactorRH(new Parametrizacion());
            oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().setClaveParametro(getTipoFactor());
            oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().setValor(p.buscaValorParametrizado(getTipoSangre()));
            oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().setValor(p.buscaValorParametrizado(getTipoFactor()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void convierteTipoDrogas(){        
        String valor = bDrogas ? "true" : "false";        
        if(valor.equals("true"))
            oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setDrogas("1");
        else
            oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setDrogas(null);        
    } 
    public void convierteDato(){
        try{
        String valor = bDiabetico ? "true" : "false";        
        if(valor.equals("true"))
            oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setDiabetico("1");
        else
            oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setDiabetico(null);        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void convierteDatoHipertenso(){
        try{
            String valor = bHiperTenso ? "true" : "false";
            if(valor.equals("true"))
                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setHTA("1");
            else
                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setHTA(null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void guardaInformacionReverso(){
        try{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(oNotaEmbarazo.insertaReverso(oNotaEmbarazo, arrDiagCie10, nTam) > 0)
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","REGISTROS GUARDADOS CON EXITO"));
            else
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,"INFORMACION","TUVIMOS PROBLEMAS AL GUARDAR LA INFORMACION :'("));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void fondoUter(){
        String sUterino = Integer.toString(getFondoUterino());
        oNotaEmbarazo.setFondoUterino(sUterino);
    }
    public void almacenaSituacion(){
        oNotaEmbarazo.setSituacionProducto(new Parametrizacion());
        oNotaEmbarazo.getSituacionProducto().setClaveParametro(getSituacionTemporal().substring(0,3));
        oNotaEmbarazo.getSituacionProducto().setTipoParametro(getSituacionTemporal().substring(3,6));
    }
    public void almacenaPresentacion(){
        oNotaEmbarazo.setPresentacion(new Parametrizacion());
        oNotaEmbarazo.getPresentacion().setClaveParametro(getPresentacionTemporal().substring(0,3));
        oNotaEmbarazo.getPresentacion().setTipoParametro(getPresentacionTemporal().substring(3,6));
    }
    public void almacenaAmnios(){
        oNotaEmbarazo.setAmnios(new Parametrizacion());
        oNotaEmbarazo.getAmnios().setClaveParametro(getAmniosTemporal().substring(0,3));
        oNotaEmbarazo.getAmnios().setTipoParametro(getAmniosTemporal().substring(3));
    }
    public void almacenaLiquido(){
        oNotaEmbarazo.setLiqAmniotico(new Parametrizacion());
        oNotaEmbarazo.getLiqAmniotico().setClaveParametro(getLiquidoTemporal().substring(0,3));
        oNotaEmbarazo.getLiqAmniotico().setTipoParametro(getLiquidoTemporal().substring(3));       
    }
    public void almacenaPelvis(){
        oNotaEmbarazo.setPelvis(new Parametrizacion());
        oNotaEmbarazo.getPelvis().setClaveParametro(getPelvisTemporal().substring(0,3));
        oNotaEmbarazo.getPelvis().setTipoParametro(getPelvisTemporal().substring(3));        
    }
    public void almacenaTrazo(){
        oNotaEmbarazo.setTrazoCardio(new Parametrizacion());
        oNotaEmbarazo.getTrazoCardio().setClaveParametro(getTrazoTemporal().substring(0,3));
        oNotaEmbarazo.getTrazoCardio().setTipoParametro(getTrazoTemporal().substring(3));
    }
    
   public NotaEmbarazo[] getDatosPacEmbarazo(){
       return this.datosPacEmbarazo;
   }
    public void cargaDatos(long foliopaciente, long claveepisodio, long expediente, int consecutivo)throws Exception{
        NotaEmbarazo oConsulta = new NotaEmbarazo();
        oDetallePacienteEP = oConsulta.datosPacienteTriage(foliopaciente, claveepisodio);
        oDetallePaciente = oConsulta.datosPaciente(foliopaciente, claveepisodio, expediente, consecutivo);        
        oDetalleReverso = oConsulta.datosPaciente(foliopaciente, claveepisodio, expediente, consecutivo);
        banderaConsulta = (oDetallePaciente == null) ? false : true;         
        setFpaciente(foliopaciente);
        setClavepisodio(claveepisodio);        
    }
    public String formatoCodigoRojo(){
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        if(oDetallePaciente.getHoraCodigoRojo() != null)
            return formatoHora.format(oDetallePaciente.getHoraCodigoRojo());
        return "";
    }
    public String formatoCodigoAmarillo(){
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        if(oDetallePaciente.getHoraCodigoAmarillo() != null)
            return formatoHora.format(oDetallePaciente.getHoraCodigoAmarillo());
        return "";
    }
    public String formatoCoadigoVerde(){
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        if(oDetallePaciente.getHoraCodigoVerde() != null)
            return formatoHora.format(oDetallePaciente.getHoraCodigoVerde());
        return "";
    }
    public DiagnosticoCIE10[] listaDiagnosticos()throws Exception{        
        NotaEmbarazo oConsulta = new NotaEmbarazo();
        return arrRet = oConsulta.datosPaciente(getFpaciente(), getClavepisodio()); 
    }
    public String FormatoHora(){
        if(oNotaEmbarazo.getHoraCodigoRojo()!= null){
            oNotaEmbarazo.setHoraAtnCodigo(oNotaEmbarazo.getHoraCodigoRojo());
            DateFormat horaFormato = new SimpleDateFormat("HH:mm");            
            String hora = horaFormato.format(oNotaEmbarazo.getHoraCodigoRojo());            
            return hora;
        }
        if(oNotaEmbarazo.getHoraCodigoAmarillo() != null){
            Date hAgregada = new Date();
            Calendar hCapturada = new GregorianCalendar();
            DateFormat formato = new  SimpleDateFormat("HH:mm");
            hAgregada.setTime(900000);
            hCapturada.setTime(oNotaEmbarazo.getHoraCodigoAmarillo());                        
            Long tiempo = hAgregada.getTime() + hCapturada.getTimeInMillis();
            hAgregada.setTime(tiempo);
            String convertido = formato.format(hAgregada);
            oNotaEmbarazo.setHoraAtnCodigo(hAgregada);
            return convertido;
        }
        if (oNotaEmbarazo.getHoraCodigoVerde() != null){
            Date hAgregada = new Date();
            Calendar hCapturada = new GregorianCalendar();
            DateFormat formato = new SimpleDateFormat("HH:mm");
            hAgregada.setTime(1800000);
            hCapturada.setTime(oNotaEmbarazo.getHoraCodigoVerde());
            Long tiempo = hAgregada.getTime() + hCapturada.getTimeInMillis();
            hAgregada.setTime(tiempo);
            String convertido = formato.format(hAgregada);
            oNotaEmbarazo.setHoraAtnCodigo(hAgregada);
            return convertido;
        }
        return null;
    }
    
    //*****TERMINAN METODOS CONTROL DE DATOS*****
    //*****INICIAN METODOS SET'S & GET'S*****
    public EpisodioMedico getEpisodioMedico(){
        return oEpisodioMedico;
    }
    public NotaEmbarazo getNotaEmbarazo(){
        return oNotaEmbarazo;
    }
    public void setNotaEmbarazo(NotaEmbarazo oNotaEmbarazo){
        this.oNotaEmbarazo = oNotaEmbarazo;
    }    
    /*public String getFecha(){
        try{
            String fCompleta = oEpisodioMedico.getFIngreso();
            return fCompleta.substring(0,10);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }*/
    public String getHora(){
        try{
            String hCompleta = oEpisodioMedico.getFIngreso();
            return hCompleta.substring(11,19);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getFechaConsulta(){
        try{
            String fCompleta = oDetallePacienteEP.getFIngreso();
            return fCompleta.substring(0,10);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getHoraConsulta(){
        try{
            String hCompleta = oDetallePacienteEP.getFIngreso();
            return hCompleta.substring(11, 19);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean getConfirmaBandera(){
        return bShow;
    }
    public void setConfirmaBandera(boolean bShow){
        this.bShow = bShow;
    }
    public boolean getCodigoRojo(){
        return codRojo;
    }
    public void setCodigoRojo(boolean codRojo){
        this.codRojo = codRojo;
    }
    public boolean getCodigoAmarillo(){
        return codAma;
    }
    public void setCodigoAmarillo(boolean codAma){
        this.codAma = codAma;
    }
    public boolean getCodigoVerde(){
        return codVer;
    }
    public void setCodigoverde(boolean codVer){
        this.codVer = codVer;
    }
    public Date getHoraCodigoRojo(){
        return codigoRojo;
    }
    public void setHoraCodigoRojo(Date codigoRojo){
        this.codigoRojo = codigoRojo;
    }
    public Date getHoraCodigoAmarillo(){
        return codigoAmarillo;
    }
    public void setHoraCodigoAmarillo(Date codigoAmarillo){
        this.codigoAmarillo = codigoAmarillo;
    }
    public Date getHoraCodigoVerde(){
        return codigoVerde;
    }
    public void setHoraCodigoVerde(Date codigoVerde){
        this.codigoVerde = codigoVerde;
    }
    public Medico getMedico(){
        return oMedFirm;
    }
    public void setMedico(Medico oMedico){
        this.oMedFirm = oMedico;
    }
    public Parametrizacion getParametrizacion(){
        return oParametrizacion;
    }
    public void setParametrizacion(Parametrizacion oParametrizacion){
        this.oParametrizacion = oParametrizacion;
    }
    public boolean getCodVerValor0(){
        return codVerValor0;
    }
    public void setCodVerValor0(boolean codVerValor0){
        this.codVerValor0 = codVerValor0;
    }
    public boolean getCodVerValor1(){
        return codVerValor1;
    }
    public void setCodVerValor1(boolean codVerValor1){
        this.codVerValor1 = codVerValor1;
    }
    public boolean getCodVerValor2(){
        return codVerValor2;
    }
    public void setCodVerValor2(boolean codVerValor2){
        this.codVerValor2 = codVerValor2;
    }
    public boolean getCodVerValor3(){
        return codVerValor3;
    }
    public void setCodVerValor3(boolean codVerValor3){
        this.codVerValor3 = codVerValor3;
    }
    public boolean getCodAmaValor0(){
        return codAmaValor0;
    }
    public void setCodAmaValor0(boolean codAmaValor0){
        this.codAmaValor0 = codAmaValor0;
    }
    public boolean getCodAmaValor1(){
        return codAmaValor1;
    }
    public void setCodAmaValor1(boolean codAmaValor1){
        this.codAmaValor1 = codAmaValor1;
    }
    public boolean getCodAmaValor2(){
        return codAmaValor2;
    }
    public void setCodAmaValor2(boolean codAmaValor2){
        this.codAmaValor2 = codAmaValor2;
    }
    public boolean getCodAmaValor3(){
        return codAmaValor3;
    }
    public void setCodAmaValor3(boolean codAmaValor3){
        this.codAmaValor3 = codAmaValor3;
    }
    public boolean getCodAmaValor4(){
        return codAmaValor4;
    }
    public void setCodAmaValor4(boolean codAmaValor4){
        this.codAmaValor4 = codAmaValor4;
    }
    public boolean getCodAmaValor5(){
        return codAmaValor5;
    }
    public void setCodAmaValor5(boolean codAmaValor5){
        this.codAmaValor5 = codAmaValor5;
    }
    public boolean getCodAmaValor6(){
        return codAmaValor6;
    }
    public void setCodAmaValor6(boolean codAmaValor6){
        this.codAmaValor6 = codAmaValor6;
    }
    public boolean getCodAmaValor7(){
        return codAmaValor7;
    }
    public void setCodAmaValor7(boolean codAmaValor7){
        this.codAmaValor7 = codAmaValor7;
    }
    public boolean getCodRojValor0(){
        return codRojValor0;
    }
    public void setCodRojValor0(boolean codRojValor0){
        this.codRojValor0 = codRojValor0;
    }
    public boolean getCodRojValor1(){
        return codRojValor1;
    }
    public void setCodRojValor1(boolean codRojValor1){
        this.codRojValor1 = codRojValor1;
    }
    public boolean getCodRojValor2(){
        return codRojValor2;
    }
    public void setCodRojValor2(boolean codRojValor2){
        this.codRojValor2 = codRojValor2;
    }
    public boolean getCodRojValor3(){
        return codRojValor3;
    }
    public void setCodRojValor3(boolean codRojValor3){
        this.codRojValor3 = codRojValor3;
    }
    public boolean getCodRojValor4(){
        return codRojValor4;
    }
    public void setCodRojValor4(boolean codRojValor4){
        this.codRojValor4 = codRojValor4;
    }
    public boolean getCodRojValor5(){
        return codRojValor5;
    }
    public void setCodRojValor5(boolean codRojValor5){
        this.codRojValor5 = codRojValor5;
    }
    public boolean getCodRojValor6(){
        return codRojValor6;
    }
    public void setCodRojValor6(boolean codRojValor6){
        this.codRojValor6 = codRojValor6;
    }
    public boolean getCodRojValor7(){
        return codRojValor7;
    }
    public void setCodRojValor7(boolean codRojValor7){
        this.codRojValor7 = codRojValor7;
    }
    public boolean getCodRojValor8(){
        return codRojValor8;
    }
    public void setCodRojValor8(boolean codRojValor8){
        this.codRojValor8 = codRojValor8;
    }
    public boolean getCodRojValor9(){
        return codRojValor9;
    }
    public void setCodRojValor9(boolean codRojValor9){
        this.codRojValor9 = codRojValor9;
    }
    public boolean getEditable1(){
        try{            
            if(!oEpisodioMedico.getPaciente().getCiudad().getDescripcionCiu().isEmpty())
                return bEditable1 = true;
            return bEditable1;
        }catch(Exception e){
            e.printStackTrace();
        }
        return bEditable1;
    }        
    public boolean getRadica0(){
        try{
            if(!oEpisodioMedico.getPaciente().getCiudad().getDescripcionCiu().isEmpty())
                return bRadica0 = true;
            return bRadica0 = false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return bRadica0;
    }
    public boolean getRadica1(){
        try{
            if(!oEpisodioMedico.getPaciente().getCiudad().getDescripcionCiu().isEmpty())
                return bRadica1 = false;
            return bRadica1 = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return bRadica1;
    }
    public boolean getOriginaria0(){
        try{
            if(!oEpisodioMedico.getPaciente().getLugarNacimiento().isEmpty())
                return bOriginario0 = true;
            return bOriginario0 = false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return bOriginario0;
    }
    public boolean getOriginaria1(){
        try{
            if(!oEpisodioMedico.getPaciente().getLugarNacimiento().isEmpty())
                return bOriginario1 = false;
            return bOriginario1 = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return bOriginario1;
    }
    public boolean obtieneGrupoRh(){
        try{
            if(!oEpisodioMedico.getPaciente().getFactorRH().getValor().isEmpty()){
                Parametrizacion p = new Parametrizacion();
                oNotaEmbarazo.getEpiMed().getPaciente().setTipoSangre(new Parametrizacion());
                oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().setClaveParametro(getTipoSangre());
                oNotaEmbarazo.getEpiMed().getPaciente().setFactorRH(new Parametrizacion());
                oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().setClaveParametro(getTipoFactor());
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean getGrupoRh(){
        try{
            if(!oEpisodioMedico.getPaciente().getFactorRH().getValor().isEmpty())
                return  bGrupoRh = false;
            return bGrupoRh = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return bGrupoRh;
    }
    public boolean getOtroHabilitado(){       
        return bHabilitaOtro;
    }
    public void setOtroHabilitado(boolean bHabilitaOtro){
        this.bHabilitaOtro = bHabilitaOtro;
    }
    public boolean getOtroHabilitado1(){
        return bHabilitaOtro1;
    }
    public void setOtroHabilitado1(boolean bHabilitaOtro1){
        this.bHabilitaOtro1 = bHabilitaOtro1;
    }
    public boolean getShowRh(){
        return bShowRh;
    }
    public boolean getDrogas(){
        return bDrogas;
    }
    public void setDrogas(boolean bDrogas){
        this.bDrogas = bDrogas;
    }
    public boolean getDiabetico(){
        return bDiabetico;
    }
    public void setDiabetico(boolean bDiabetico){
        this.bDiabetico = bDiabetico;
    }
    public boolean getHipertenso(){
        return bHiperTenso;
    }
    public void setHipertenso(boolean bHiperTenso){
        this.bHiperTenso = bHiperTenso;
    }
    public boolean getHabilitaInput(){
        return bHabilitaInput;
    }
    public void setHabilitaInput(boolean bHabilitaInput){
        this.bHabilitaInput = bHabilitaInput;
    }
    public String getTipoSangre(){
        return sTipoSangre;
    }
    public void setTipoSangre(String sTipoSangre){
            this.sTipoSangre = sTipoSangre;
    }
    public String getTipoFactor(){
        return sTipoFactor;
    }
    public void setTipoFactor(String sTipoFactor){
        this.sTipoFactor = sTipoFactor;
    }
    public int getFondoUterino(){
        return fondoUterino;
    }
    public void setFondoUterino(int fondoUterino){
        this.fondoUterino = fondoUterino;
    }
    public String getSituacionTemporal(){
        return situacionTemp;
    }
    public void setSituacionTemporal(String situacionTemp){
        this.situacionTemp = situacionTemp;
    }
    public String getPresentacionTemporal(){
        return presentacionTemp;
    }
    public void setPresentacionTemporal(String presentacionTemp){
        this.presentacionTemp = presentacionTemp;
    }
    public String getAmniosTemporal(){
        return amniosTemp;
    }
    public void setAmniosTemporal(String amniosTemp){
        this.amniosTemp = amniosTemp;
    }
    public String getLiquidoTemporal(){
        return liquidoTemp;
    }
    public void setLiquidoTemporal(String liquidoTemp){
        this.liquidoTemp = liquidoTemp;
    }
    public String getPelvisTemporal(){
        return pelvisTemp;
    }
    public void setPelvisTemporal(String pelvisTemp){
        this.pelvisTemp = pelvisTemp;
    }
    public String getTrazoTemporal(){
        return trazoTemp;
    }
    public void setTrazoTemporal(String trazoTemp){
        this.trazoTemp = trazoTemp;
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
     public void borrarElementoCie10(DiagnosticoCIE10 obj){
        arrDiagCie10.remove(obj);
    }
    public String getPronosticoTemporal(){
        return pronosticoTemp;
    }
    public void setPronosticoTemporal(String pronosticoTemp){
        this.pronosticoTemp = pronosticoTemp;
    }
    
    public NotaEmbarazo getDetallePaciente(){
        return oDetallePaciente;
    }
    public void setDetallePaciente(NotaEmbarazo oDetallePaciente){
        this.oDetallePaciente = oDetallePaciente;
    }
    public EpisodioMedico getDetallePacienteEP(){
        return oDetallePacienteEP;
    }
    public void setDetallePacienteEP(EpisodioMedico oDetallePacienteEP){
        this.oDetallePacienteEP = oDetallePacienteEP;
    }
    public boolean getMuestraConsulta(){
        return banderaConsulta;
    }
    public void setMuestraConsulta(boolean banderaConsulta){
        this.banderaConsulta = banderaConsulta;
    }
    public ArrayList<DiagnosticoCIE10> getConsultaDiagnostico(){
        return arrConsultaCie10;
    }
    public void setConsultaDiagnostico(ArrayList<DiagnosticoCIE10> arrConsultaCie10){
        this.arrConsultaCie10 = arrConsultaCie10;
    }
    public NotaEmbarazo getDetalleReverso(){
        return oDetalleReverso;
    }
    public void setDetalleReverso(NotaEmbarazo oDetalleReverso){
        this.oDetalleReverso = oDetalleReverso;
    }
    public long getFpaciente(){
        return fpaciente;
    }
    public void setFpaciente(long fpaciente){
        this.fpaciente = fpaciente;
    }
    public long getClavepisodio(){
        return clavepisodio;
    }
    public void setClavepisodio(long clavepisodio){
        this.clavepisodio = clavepisodio;
    }
    public Date getFechaActual(){
        java.util.Date fecha = new Date();
        return fecha;
    }    
    public Date getHoraAtencion(){
        return hAtn;
    }
    public void setHoraAtencion(Date hAtn){
        this.hAtn = hAtn;
    }
    public Paciente getPaciente(){
        return oPaciente;
    }
    public void setPaciente(Paciente oPaciente){
        this.oPaciente = oPaciente;
    }
    public DiagnosticoCIE10[] getDiagnosticos(){
        return arrRet;
    }
    public void setDiagnosticos(DiagnosticoCIE10 arrRet[]){
        this.arrRet = arrRet;
    }
    public boolean getBanderaBoton(){
        return bBoton;
    }
    public void setBanderaBoton(boolean bBoton){
        this.bBoton = bBoton;
    }
    public NotaEmbarazo getTemporalNotaEmbarzo(){
        return tempNotaEmbarazo;
    }
    public void setTemporalNotaEmbarazo(NotaEmbarazo tempNotaEmbarazo){
        this.tempNotaEmbarazo = tempNotaEmbarazo;
    }
    public boolean getModifica(){
        return bModifica;
    }
    public void setModifica(boolean bModifica){
        this.bModifica = bModifica;
    }
    public boolean getControlaHoraRojo(){
        return bControlaHoraRojo;
    }
    public void setControlaHoraRojo(boolean bControlaHoraRojo){
        this.bControlaHoraRojo = bControlaHoraRojo;
    }
    public boolean getControlaHoraAma(){
        return bControlaHoraAma;
    }
    public void setControlaHoraAma(boolean bControlaHoraAma){
        this.bControlaHoraAma = bControlaHoraAma;
    }
    public boolean getControlaHoraVer(){
        return bControlaHoraVer;
    }
    public void setControlaHoraVer(boolean bControlaHoraVer){
        this.bControlaHoraVer = bControlaHoraVer;
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
    public void AsignaFondoUterino(){
        if(this.oNotaEmbarazo.getFondoUterino().compareTo("") != 0)
            this.setFondoUterino(Integer.parseInt(this.oNotaEmbarazo.getFondoUterino()));
    }
    public void AsignaSituacion(){        
        this.setSituacionTemporal(this.oNotaEmbarazo.getSituacionProducto().getClaveParametro() + "" + this.oNotaEmbarazo.getSituacionProducto().getTipoParametro());
    }
    public void AsignaPresentacion(){
        this.setPresentacionTemporal(this.oNotaEmbarazo.getPresentacion().getClaveParametro() + "" + this.oNotaEmbarazo.getPresentacion().getTipoParametro());
    }
    public void AsignaAmnios(){        
        this.setAmniosTemporal(this.oNotaEmbarazo.getAmnios().getClaveParametro().trim() + "" + this.oNotaEmbarazo.getAmnios().getTipoParametro().trim());
    }
    public void AsignaLiquidoAmniotico(){
        this.setLiquidoTemporal(this.oNotaEmbarazo.getLiqAmniotico().getClaveParametro().trim() + "" + this.oNotaEmbarazo.getLiqAmniotico().getTipoParametro().trim());
    }
    public void AsignaPelvis(){        
        this.setPelvisTemporal(this.oNotaEmbarazo.getPelvis().getClaveParametro() + "" + this.oNotaEmbarazo.getPelvis().getTipoParametro());
    }
    public void AsignaTrazo(){
        this.setTrazoTemporal(this.oNotaEmbarazo.getTrazoCardio().getClaveParametro() + "" + this.oNotaEmbarazo.getTrazoCardio().getTipoParametro());
    }
    public void AsignaMetodo(){
        if(this.oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getPF() != null)
            this.oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getMetodoAnticonceptivo().setDescripcion(oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getPF());        
    }
    public void AsignaEdoCivil(){
        if(this.oEpisodioMedico.getPaciente().getEstadoCivil().getClaveParametro() != null)
            this.oNotaEmbarazo.getEpiMed().getPaciente().setEdoCivilStr(this.oEpisodioMedico.getPaciente().getEstadoCivil().getClaveParametro() + " ");        
    }
    public NotaEmbarazo getPacienteNota(){
        return this.oPacienteNota;
    }
    public void setPacienteNota(NotaEmbarazo oPacienteNota){
        this.oPacienteNota = oPacienteNota;
    }
    public NotaEmbarazo[] getArrPacienteNota(){
        return this.arrPaciente;
    }
    public void setArrPacienteNota(NotaEmbarazo[] arrPaciente){
        this.arrPaciente = arrPaciente;
    }
    //*****TERMINAN METODOS SET'S & GET'S*****
}