package mx.gob.hrrb.jbs.consultaexterna;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.jbs.urgencias.registrarPaciente;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Referencia;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oConfirmaCita")
@SessionScoped

public class ConfCita {
private Cita oCita;
List<DiagnosticoCIE10>lListaDiagcve;
private String btnConfirma;
    /**
     * Creates a new instance of ConfCita
     */
    public ConfCita() {
        oCita=new Cita();
        lListaDiagcve=null;
        btnConfirma="";
    }
    
    public String getBtnConfirma(){
    Date fechaActual=new Date();
    String fAux="";
    String fAct="";
    String sRet="false";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        fAux=df.format(getCita().getFechaAux());
        fAct=df.format(fechaActual);
        sRet = (fAux.compareToIgnoreCase(fAct)==0?"false":"true");
        return sRet;
    }
    
    public void setBtnConfirma(String btnConfirma){
        this.btnConfirma=btnConfirma;
    }
    
    public void setCita(Cita oCita){
        this.oCita=oCita;
    }
    
    public Cita getCita(){
        return oCita;
    }
    
     public List<AreaServicioHRRB> getListaAreasCE(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasCE()));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
   //*******************************************************************      
        public String sp_ascii(String input) {
    // Cadena de caracteres original a sustituir.
    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    // Cadena de caracteres ASCII que reemplazarán los originales.
    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    String output = input;
    for (int i=0; i<original.length(); i++) {
        // Reemplazamos los caracteres especiales.
        output = output.replace(original.charAt(i), ascii.charAt(i));
    }//for i
    return output;
}
   //*******************************************************************     
   public List<DiagnosticoCIE10> getClavesCIE10Causes(String txt){
         lListaDiagcve = null;
       try {
           lListaDiagcve= new ArrayList<DiagnosticoCIE10>(Arrays.asList(
                   (new DiagnosticoCIE10()).buscarClavesCIE10CAUSES(txt)));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lListaDiagcve;
    }
   //*******************************************************************
    public List<DiagnosticoCIE10> getListaDiagnostico(String txt){
       List<DiagnosticoCIE10>lListaDiag = null;
       
       try {
           lListaDiag= new ArrayList<DiagnosticoCIE10>(Arrays.asList(
                   (new DiagnosticoCIE10()).buscarDiagnostico(txt)));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lListaDiag;
    }
    //****************************************************************************
        public String clave2() {
        String y="";
        if(lListaDiagcve==null)
            y="";
        else{
            y=lListaDiagcve.get(0).getCauses().getClave();
        }
        return y;
    }
    //********************************************************************+
    public String clave1() {
        String x="";
        if(lListaDiagcve==null)
            x="";
        else{
            x=lListaDiagcve.get(0).getClave();}
        return x;
    }
    
    public boolean habilitaListas(){
    return !(oCita.getPaciente().getOtroPais().compareToIgnoreCase("MÉXICO")==0||
            oCita.getPaciente().getOtroPais().compareToIgnoreCase("MEXICO")==0);
    }
    
    public List<Ciudad> getListaCiudades(){
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList((new Ciudad().buscaCiudadCP(oCita.getPaciente().getEstado().getClaveEdo(), oCita.getPaciente().getMunicipio().getClaveMun(), oCita.getPaciente().getCodigoPos()))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
     public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList((new Municipio().buscarMunicipio(oCita.getPaciente().getEstado().getClaveEdo()))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }

    public List<Parametrizacion> getListaSolicitudes(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Paciente().getTipoSol().buscarSolicitudes())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Referencia> getListaRefs(){
        List<Referencia> lLista = null;
       try {
           lLista = new ArrayList<Referencia>(Arrays.asList((new Paciente().getReferencia().buscarReferencias())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
     public List<Medico> getListaMedicos(){
       List<Medico> lLista = null;
       try {
           lLista = new ArrayList<Medico>(Arrays.asList(
                   (new Medico()).buscarMedicosParaCambios()));
       } catch (Exception ex) {
           Logger.getLogger(ConfCita.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
    public String confirmaCita(){
    String pag="ConfirmarCita";
    int upPac=0, upCita=0;
    FacesMessage message;
    String datos="";
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            if (oCita.getDiag().getClave1CE()!=null && 
                oCita.getDiag().getClave1CE().compareTo("")!=0){
                upPac=oCita.getPaciente().modificarPacCE();
                upCita=oCita.modificarCitaCE();
                if (upCita>0){
                    datos="Fecha: "+df.format(oCita.getFechaAux())+" "+oCita.getFechaCitaTexto(0)+"           No. Ficha: "+upCita;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmación de Cita", datos);
                    oCita.setFolioPago("");
                    oCita.getPaciente().getReferencia().setClave("");
                    oCita.getPaciente().getReferencia().setDescripcion("");
                }else{
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmación de Cita", "Error :(");
                }
                oCita.setNoFicha(0);
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmación de Cita", "Debe seleccionarse un diagnóstico existente");
                oCita.getDiag().setDescripcionDiag("");
                pag="DetalleCita.xhtml";
            }
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }catch(Exception e){
             Logger.getLogger(ConfCita.class.getName()).log(Level.SEVERE, null, e);
        }
        return pag;
    }
     
    public String confirmarCitaCE(){
    String pag="DetalleCita";
        try{
            //Busca información del paciente y la cita
            //antes de cambiar de página
            this.getCita().getPaciente().buscarPacCE(0);
            this.getCita().buscaDatosCita();
            this.getCita().buscaCitaEP();
            this.getCita().getDiag().buscar();
            this.getCita().getDiag().getCauses().buscar(
                        this.getCita().getDiag().getClave());
        }catch(Exception e){
            Logger.getLogger(ConfCita.class.getName()).log(Level.SEVERE, null, e);
        }
        return pag;
    }
}
