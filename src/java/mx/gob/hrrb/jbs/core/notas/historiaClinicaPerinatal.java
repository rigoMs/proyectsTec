package mx.gob.hrrb.jbs.core.notas;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.PacienteNeonato;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.notas.EvaluacionEdadGestacionalCapurro;
import mx.gob.hrrb.modelo.core.notas.HistoriaClinicaPerinatal;
import mx.gob.hrrb.modelo.core.notas.ValoracionNeurologica;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;


/**
 *
 * @author Alberto Diiaz
 */
@ManagedBean(name = "hperinatal")
@ViewScoped
public class historiaClinicaPerinatal implements Serializable{
    private PacienteNeonato oPaciente;
    private PacienteNeonato[] arrPaciente; 
    private HistoriaClinicaPerinatal hperinatal;
    private HistoriaClinicaPerinatal[] arrPerinatal;
    private boolean bMuestrapantalla;
    private LineChartModel oPinta;
    private Medico[] arrMedico;
    private Parametrizacion[] arrEdoCivil;
    private Parametrizacion[] arrEducacion;
    private DiagnosticoCIE10 arrRet[];
    private ArrayList<HistoriaClinicaPerinatal> dxAgregados;
    private HistoriaClinicaPerinatal oPerinatal;
    private ValoracionNeurologica oNeurologica;
    private boolean bRequerido;
    private boolean bEvaluacion;
    private boolean bTab2;
    private String npeso;
    private EvaluacionEdadGestacionalCapurro oEvaluacionEdadg;
    
    
    public historiaClinicaPerinatal() {
        oPaciente = new PacienteNeonato();
        dxAgregados = new ArrayList<HistoriaClinicaPerinatal>();
        oPerinatal = new HistoriaClinicaPerinatal();
        bRequerido = true;
        bEvaluacion = true;
        oNeurologica = new ValoracionNeurologica();
        oEvaluacionEdadg = new EvaluacionEdadGestacionalCapurro();
        this.hperinatal = new HistoriaClinicaPerinatal();
    }    
    //*****************INICIAN METODOS DE CONTROL DE DATOS**********************
    public void cargaDatosPaciente(short opcion){
        try{
            this.oPaciente.setOpcionUrg(opcion);
            this.arrPaciente = this.oPaciente.buscaPacientesNeonatos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cargaDetallePaciente(short opcion){
        try{
            this.hperinatal.getEpisodioMedico().getPaciente().setOpcionUrg(opcion);
            this.arrPerinatal = this.hperinatal.buscaPacientesNeonatos();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void cargaDetalleHistoriaClinica(long fpaciente, long clave, int nexp, long foliomama, long clavemama,int consecutivopartograma, long numeropartograma, int nhclinica){
        try{
            this.hperinatal = new HistoriaClinicaPerinatal();
            this.oNeurologica = new ValoracionNeurologica();
            
            this.oEvaluacionEdadg = new EvaluacionEdadGestacionalCapurro();
            this.hperinatal.getPacientenNeonato().setFolioPaciente(fpaciente);
            this.hperinatal.getPacientenNeonato().setClaveEpisodio(clave);
            this.hperinatal.getPacientenNeonato().getExpediente().setNumero(nexp);
            this.hperinatal.getEpisodioMedico().getPaciente().setFolioPaciente(foliomama);
            this.hperinatal.getEpisodioMedico().getPaciente().setClaveEpisodio(clavemama);
            this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivopartograma);
            this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(numeropartograma);
            this.hperinatal.setNumeroHistoriaClinca(nhclinica);
            
            this.oNeurologica.getHistoriaClinicaPerinatal().getPacientenNeonato().setFolioPaciente(fpaciente);
            this.oNeurologica.getHistoriaClinicaPerinatal().getPacientenNeonato().setClaveEpisodio(clave);
            this.oNeurologica.getHistoriaClinicaPerinatal().setNumeroHistoriaClinca(nhclinica);
            
            this.oEvaluacionEdadg.getHistoriaClinicaPerinatal().getPacientenNeonato().setFolioPaciente(fpaciente);
            this.oEvaluacionEdadg.getHistoriaClinicaPerinatal().getPacientenNeonato().setClaveEpisodio(clave);
            this.oEvaluacionEdadg.getHistoriaClinicaPerinatal().setNumeroHistoriaClinca(nhclinica);
            
            this.hperinatal.cargaDatosPaciente();
            this.hperinatal.consulatAnverso1Especifico();
            
            if(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac() != null){
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNacTexto(format.format(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac()));
                this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNacTexto(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().calculaEdad());
            }
            
            this.hperinatal.consultaSignosvitalesNeonatoEsepecifico();
            
            this.hperinatal.consultaReverso001Detalle();
            
            if(this.hperinatal.getDiagnostico().getClave() != null || !this.hperinatal.getDiagnostico().getClave().isEmpty()){
                this.oPerinatal.getDiagnostico().setClave(this.hperinatal.getDiagnostico().getClave());
                this.oPerinatal.getDiagnostico().setDescripcionDiag(this.hperinatal.getDiagnostico().getDescripcionDiag());
            }
            
            this.oNeurologica.consultaEspecificaValoracionNeurologica();
            this.sumatoria();
            
            
            
            this.oEvaluacionEdadg.consultaEspecificaEdadGestacionalCapurro();
            this.operacion();            
            
            createLineModels();
            this.bMuestrapantalla = true;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cargaDatosGenerales(long fpaciente, long clave, int nexp, long foliomama, long clavemama,int consecutivopartograma, long numeropartograma){
        try{            
            this.hperinatal = new HistoriaClinicaPerinatal();
            this.oNeurologica = new ValoracionNeurologica();
            this.dxAgregados = new ArrayList<HistoriaClinicaPerinatal>();
            this.oEvaluacionEdadg = new EvaluacionEdadGestacionalCapurro();
            this.hperinatal.getPacientenNeonato().setFolioPaciente(fpaciente);
            this.hperinatal.getPacientenNeonato().setClaveEpisodio(clave);
            this.hperinatal.getPacientenNeonato().getExpediente().setNumero(nexp);
            this.hperinatal.getEpisodioMedico().getPaciente().setFolioPaciente(foliomama);
            this.hperinatal.getEpisodioMedico().getPaciente().setClaveEpisodio(clavemama);
            this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivopartograma);
            this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(numeropartograma);
            this.oNeurologica.getHistoriaClinicaPerinatal().getPacientenNeonato().setFolioPaciente(fpaciente);
            this.oNeurologica.getHistoriaClinicaPerinatal().getPacientenNeonato().setClaveEpisodio(clave);
            this.oEvaluacionEdadg.getHistoriaClinicaPerinatal().getPacientenNeonato().setFolioPaciente(fpaciente);
            this.oEvaluacionEdadg.getHistoriaClinicaPerinatal().getPacientenNeonato().setClaveEpisodio(clave);
            this.hperinatal.cargaDatosPaciente();
            this.hperinatal.buscaPersonal();
            this.arrMedico = this.hperinatal.buscaMedicos((short) 0);            
            if(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion() != null &&
                this.hperinatal.getPacientenNeonato().getFechaNac() != null){
                calculaEdadGestacional();
            }
            if(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac() != null){
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNacTexto(format.format(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac()));
                this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNacTexto(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().calculaEdad());
            }
            if(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getEscolaridad() != null){
                this.arrEducacion = educacionPrincipal();
                buscaClaveCompletaEducacionPrincipal();
            }
            if(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getEdoCivilStr() != null){
                this.arrEdoCivil = estadoCivil();
                buscaClaveCompletaEstadoCivil();
            }
            this.hperinatal.consultaAnveso001();
            listaDiagnosticos();
            this.hperinatal.consultaSignosvitalesNeonato();            
            this.hperinatal.consultaAnverso001();            
            if(this.hperinatal.getDiagnostico().getClave() == null || this.hperinatal.getDiagnostico().getClave().isEmpty()){
                this.oPerinatal.getDiagnostico().setClave("");
                this.oPerinatal.getDiagnostico().setDescripcionDiag("");                
            }else{
                this.oPerinatal.getDiagnostico().setClave(this.hperinatal.getDiagnostico().getClave());
                this.oPerinatal.getDiagnostico().setDescripcionDiag(this.hperinatal.getDiagnostico().getDescripcionDiag());
            }
            this.oNeurologica.consultaValoracionNeurologica();
            this.sumatoria();
                        
            this.oEvaluacionEdadg.consultaEdadGestacionalCapurro();            
            this.operacion();
            
            createLineModels();
            
            this.setMuestraPantalla(true);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void calculaEdadGestacional(){
        try{
            if(!this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion().equals(null) &&
                !this.hperinatal.getPacientenNeonato().getFechaNac().equals(null)){
                Calendar fechaultimaregla = new GregorianCalendar();
                Calendar fechanacimiento = new GregorianCalendar();        
                fechaultimaregla.setTime(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion());
                fechanacimiento.setTime(this.hperinatal.getPacientenNeonato().getFechaNac());
                long resta = fechanacimiento.getTimeInMillis() - fechaultimaregla.getTimeInMillis();
                long semanas = resta / 604800000;// VALOR EQUIVALENTE AL NUMERO DE MILISEGUNDOS EN UNA SEMANA COMPLETA [7 DIAS]
                this.hperinatal.getAtencion().setEdadgestacionalEGFUR((int)semanas);
            }        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void asignaCedula(){        
        String cedulaProfesional = buscaMedico(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());        
        int ntarjeta = buscaMedico(this.hperinatal);
        String cedula = cedulaProfesional.compareTo("") == 0 ? "NO DISPONIBLE" : cedulaProfesional;
        this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
        int tarjeta = ntarjeta != 0 ? ntarjeta : 0;
        this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
    }
    public String buscaMedico(String medico){
        for(int i = 0; i < arrMedico.length; i++)
            if(arrMedico[i].getNombreCompleto().compareTo(medico) == 0)
                return arrMedico[i].getCedProf();
        return "";
    }
    public int buscaMedico(HistoriaClinicaPerinatal hperinatal){
        for(int i = 0; i < arrMedico.length; i++)
            if(arrMedico[i].getNombreCompleto().compareTo(hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                return arrMedico[i].getNoTarjeta();
        return 0;
    }
    public void edadCalculada(){        
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNacTexto(format.format(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac()));
        this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNacTexto(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().calculaEdad());
    }
    public Parametrizacion[] educacionPrincipal(){
        try{            
            this.arrEducacion = this.hperinatal.getNivelEducacion().buscarTabla("T14");
            return arrEducacion;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void buscaClaveCompletaEducacionPrincipal(){
        for(int i = 0; i < arrEducacion.length; i++)
            if(arrEducacion[i].getValor().compareTo(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getEscolaridad()) == 0){
                this.hperinatal.getNivelEducacion().setTipoParametro(arrEducacion[i].getTipoParametro());
                this.hperinatal.getNivelEducacion().setClaveParametro(arrEducacion[i].getClaveParametro());                
            }
    }
    public Parametrizacion[] estadoCivil(){
        try{            
            this.arrEdoCivil = this.hperinatal.getEstadoCivil().buscarTabla("T35");
            return arrEdoCivil;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void buscaClaveCompletaEstadoCivil(){
        for(int i = 0; i < arrEdoCivil.length; i++)
            if(arrEdoCivil[i].getValor().compareTo(this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getEdoCivilStr()) == 0){
                this.hperinatal.getEstadoCivil().setTipoParametro(arrEdoCivil[i].getTipoParametro());
                this.hperinatal.getEstadoCivil().setClaveParametro(arrEdoCivil[i].getClaveParametro());                
            }
    }
    public void update(){
        RequestContext.getCurrentInstance().update("dialogo");
        RequestContext.getCurrentInstance().update("dialogo:output");
    }   
    public void insertAnverso1(){
        try{
            if(this.hperinatal.insertaAnverso001()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void registroDiagnostico(){
        if(this.oPerinatal.getDiagnostico().getClave().isEmpty())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO ES POSIBLE AGREGAR VACÍO"));
        else{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(nTam >= 6 || dxAgregados.size() >= 6 || nTam + dxAgregados.size() >= 6){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "IMPOSIBLE AGREGAR MAS DIAGNOSTICOS MAXIMO 6 DIAGNOSTICOS POR PACIENTE"));
                try{
                    this.oPerinatal.getDiagnostico().setDescripcionDiag("");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                if(dxAgregados.isEmpty() && buscaRepetidoDxAnterior(oPerinatal.getDiagnostico().getClave()) != true){
                    this.hperinatal.getDiagnostico().setClave(oPerinatal.getDiagnostico().getClave());
                    this.dxAgregados.add(oPerinatal);
                    this.oPerinatal = new HistoriaClinicaPerinatal();
                    //this.oPerinatal.getDiagnostico().setDescripcionDiag("");                    
                }else{
                    if(buscaRepetido(this.oPerinatal.getDiagnostico().getClave())){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                        try{
                        this.oPerinatal.getDiagnostico().setDescripcionDiag("");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        if(buscaRepetidoDxAnterior(this.oPerinatal.getDiagnostico().getClave())){
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                            try{
                                this.oPerinatal.getDiagnostico().setDescripcionDiag("");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            this.dxAgregados.add(oPerinatal);
                            this.oPerinatal = new HistoriaClinicaPerinatal();
                        }
                    }
                }
            }                
        }
    }
    public boolean buscaRepetidoDxAnterior(String clave){
        if(arrRet == null)
            return false;
        for(int i = 0; i < arrRet.length; i++)
            if(arrRet[i].getClave().compareTo(clave) == 0)
                return true;
        return false;
    }    
    public boolean buscaRepetido(String clave){
        boolean bandera = false;
        for(HistoriaClinicaPerinatal i: this.dxAgregados){
            if(i.getDiagnostico().getClave().equals(clave)){
                bandera = true;
                break;
            }
        }
        return bandera;
    }
    public void listaDiagnosticos(){
        try{
            this.arrRet = this.hperinatal.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().datosPaciente(this.hperinatal.getPacientenNeonato().getFolioPaciente(), this.hperinatal.getPacientenNeonato().getClaveEpisodio());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void insertaReverso1(){
        try{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(this.hperinatal.insertaReverso001(this.dxAgregados, nTam)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }                
        }catch(Exception e){            
            e.printStackTrace();
        }
    }
    public void requerir(TabChangeEvent event){
        if(event.getTab().getId().compareTo("hcp00") == 0){
            this.bRequerido = true;
            this.bTab2 = false;
            this.bEvaluacion = false;
        }else if(event.getTab().getId().compareTo("hcp01") == 0){
            this.bTab2 = true;
            this.bRequerido = false;
            this.bEvaluacion = false;
        }else if(event.getTab().getId().compareTo("hcp02") == 0){
            this.bTab2 = false;
            this.bRequerido = false;
            this.bEvaluacion = false;
        }else if(event.getTab().getId().compareTo("hcp03") == 0){
            this.bEvaluacion = true;
            this.bRequerido = false;
            this.bTab2 = false;            
        }
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
    public void insertAnverso2(){
        try{
            if(this.oNeurologica.insertaValoracionNeurologica()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }
        }catch(Exception e){
            e.printStackTrace();
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
    public void insertaReverso2(){
        try{
            if(this.oEvaluacionEdadg.insertaEdadGestacionalCapurro()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                this.oEvaluacionEdadg.consultaEdadGestacionalCapurro();
                this.operacion();
                this.createLineModels();
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
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
        
        punto.set(String.valueOf(this.oEvaluacionEdadg.getTotal()),(this.hperinatal.getPacientenNeonato().getPeso()*1000));
        
        model.addSeries(linea1);
        model.addSeries(linea2);
        model.addSeries(linea3);
        model.addSeries(punto);
        return model;
    }
    public LineChartModel getPinta() {
        return oPinta;
    }
    //*****************TERMINAN METODOS DE CONTROL DE LA GRAFICA*****************
    //*****************TERMINAN METODOS DE CONTROL DE DATOS*********************
    //*****************INICIAN METODOS SET'S Y GET'S****************************
    public PacienteNeonato getPaciente(){
        return oPaciente;
    }
    public void setPacienteNeonato(PacienteNeonato oPaciente){
        this.oPaciente = oPaciente;
    }
    public boolean getMuestraPantalla(){
        return bMuestrapantalla;
    }
    public PacienteNeonato[] getArrPaciente(){
        return arrPaciente;
    }
    public void setMuestraPantalla(boolean bMuestrapantalla){
        this.bMuestrapantalla = bMuestrapantalla;
    }
    public HistoriaClinicaPerinatal getHistoriaClinicaPerinatal(){
        return hperinatal;
    }
    public void setHistoriaClinicaPerinatal(HistoriaClinicaPerinatal hperinatal){
        this.hperinatal = hperinatal;
    }
    public Date getFechaActual(){
        return new Date();
    }
    public Medico[] getArrMedico(){
        return arrMedico;
    }
    public void setArrMedico(Medico[] arrMedico){
        this.arrMedico = arrMedico;
    }
    public DiagnosticoCIE10[] getDiagnosticosAnteriores(){
        return arrRet;
    }
    public void setDiagnosticosAnteriores(DiagnosticoCIE10[] arrRet){
        this.arrRet = arrRet;
    }
    public HistoriaClinicaPerinatal getDiagnosticos(){
        return oPerinatal;
    }
    public void setDiagnosticos(HistoriaClinicaPerinatal oPerinatal){
        this.oPerinatal = oPerinatal;
    }
    public ArrayList<HistoriaClinicaPerinatal> getDiagnosnosticosAgregados(){
        return dxAgregados;
    }
    public void setDiagnosticosAgregados(ArrayList<HistoriaClinicaPerinatal> dxAgregados){
        this.dxAgregados = dxAgregados;
    }
    public void borrarElementoCie10(HistoriaClinicaPerinatal obj){
        if(this.dxAgregados != null || this.dxAgregados.isEmpty()){
            if(this.dxAgregados.get(0).getDiagnostico().getClave().compareTo(obj.getDiagnostico().getClave()) == 0){
                this.oPerinatal.getDiagnostico().setClave("");
                this.dxAgregados.remove(obj);
            }else
                dxAgregados.remove(obj);
        }
    }
    public boolean getRequerido(){
        return bRequerido;
    }
    public void setRequerido(boolean bRequerido){
        this.bRequerido = bRequerido;
    }
    public boolean getRequeridoEvaluacion(){
        return bEvaluacion;
    }
    public void setrequeridoEvaluacion(boolean bEvaluacion){
        this.bEvaluacion = bEvaluacion;
    }
    public String getPeso(){
        
        return npeso;
    }
    public void setPeso(String npeso){
        this.npeso = npeso;
    }
    public ValoracionNeurologica getNeurologica(){
        return oNeurologica;
    }
    public void setNeurologica(ValoracionNeurologica oNeurologica){
        this.oNeurologica = oNeurologica;
    }
    public EvaluacionEdadGestacionalCapurro getEvaluacionEdadGestacionalCapurro(){
        return oEvaluacionEdadg;
    }
    public void setEvaluacionEdadGestacionalCapurro(EvaluacionEdadGestacionalCapurro oEvaluacionEdadg){
        this.oEvaluacionEdadg = oEvaluacionEdadg;
    }
    public HistoriaClinicaPerinatal[] getHistoriaClinicaPacienteNeonato(){
        return arrPerinatal;
    }
    public boolean getTab2(){
        return this.bTab2;
    }
    public void setTab2(boolean bTab2){
        this.bTab2 = bTab2;
    }
    //*****************TERMINAN METODOS SET'S Y GET'S***************************
}
