/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import mx.gob.hrrb.modelo.consultaexterna.Permiso;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.DiaFestivo;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Referencia;
import mx.gob.hrrb.modelo.core.Seguro;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oRegCita")
@SessionScoped
public class RegistroCita {
//private Paciente oPac;
private Cita oCita;
private Referencia oRef;
private Medico oMed;
private EpisodioMedico oEP;
List<String> arrRet;
private Seguro oSeg;
private DiaFestivo oDiaFes;
private Permiso oPermiso;
private AsignaConsultorio oAsigCon;
private FacesMessage message;
private int totalPrimVez;
private int totalSubs;
private int maximoCitasPS;
private int nExtra;
private int nTotalCitados;
private String sEstado;
private DiagnosticoCIE10 diagcie10;
private int nPestana;

    /**
     * Creates a new instance of RegistroCita
     */
    public RegistroCita() {
        //oPac=new Paciente();
        oCita=new Cita();
        oRef=new Referencia();
        oMed=new Medico();
        oEP=new EpisodioMedico();
        arrRet=null;
        oSeg=new Seguro();
        oDiaFes=new DiaFestivo();
        oPermiso=new Permiso();
        oAsigCon=new AsignaConsultorio();
        message=null;
        totalPrimVez=0;
        totalSubs=0;
        maximoCitasPS=0;
        nExtra=0;
        nTotalCitados=0;
        sEstado="mostrarbtn"; 
        diagcie10=new DiagnosticoCIE10();
        nPestana=0;
    }

  /*  public Paciente getPac() {
        return oPac;
    }

    public void setPac(Paciente oPac) {
        this.oPac = oPac;
    }*/
    
    public boolean habilitaListas(){
        return !(getCita().getPaciente().getOtroPais().compareToIgnoreCase("MÉXICO")==0 || getCita().getPaciente().getOtroPais().compareToIgnoreCase("MEXICO")==0);
    }
    
    public List<Ciudad> getListaCiudades(){
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList((new Ciudad().buscaCiudadCP(getCita().getPaciente().getEstado().getClaveEdo(), getCita().getPaciente().getMunicipio().getClaveMun(), getCita().getPaciente().getCodigoPos()))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
     public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList((new Municipio().buscarMunicipio(getCita().getPaciente().getEstado().getClaveEdo()))));
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
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
           
    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }

    public Referencia getRef() {
        return oRef;
    }

    public void setRef(Referencia oRef) {
        this.oRef = oRef;
    }

    public Medico getMed() {
        return oMed;
    }

    public void setMed(Medico oMed) {
        this.oMed = oMed;
    }
    
    public EpisodioMedico getEP() {
        return oEP;
    }

    public void setEP(EpisodioMedico oEP) {
        this.oEP = oEP;
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

    public String registraCita(){
    String pag="RegistrarCita";
    int mod=0, ins=0, cita=0, ep=0;
    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita", "Registro Fallido :(");
    String max[]=oMed.getNombres().split("-");
    try{
        if (getDiagcie10().getClave1CE()!=null && 
                getDiagcie10().getClave1CE().compareTo("")!=0){
            int maximo=oMed.buscarMaximoPorDia(max[0], max[4], 
                    oCita.getFechaCita(), max[2]); //Máximo de citas programado
            int total=0;
            boolean tipocitas=false;
            
            total=oCita.buscarTotalCitasMedico(max[0], max[1], max[4]); //Total de citados para el médico
            nTotalCitados=total;
            if ((maximo+nExtra)>total){
                tipocitas=validaTiposCitas();
                if (tipocitas==false){
                    if (oDiaFes.buscar(oCita.getFechaCita())==false){
                        if (oPermiso.buscar(oMed.getNombres().split("-"), 
                                oCita.getFechaCita())==false){
                            if (oCita.buscarCitaCE(oMed.getNombres().split("-"), 
                                    getCita().getPaciente().getFolioPaciente()
                            )==false){
                                boolean encontrado=getoSeg().buscarSeguroPop(getCita().getPaciente().getSeg().getNumero(), getCita().getPaciente().getFolioPaciente());
                                if (encontrado==false){
                                    if (getCita().getPaciente().getFolioPaciente()==0){
                                        ins=getCita().getPaciente().insertarPacCE();
                                        getCita().getPaciente().setFolioPaciente(ins);
                                        if (ins>0){
                                            cita=oCita.insertar(oMed.getNombres(), 
                                                    getCita().getPaciente().getFolioPaciente(), 
                                                    maximoCitasPS, totalPrimVez, 
                                                    totalSubs);
                                            if (cita>0){
                                                ep=oEP.insertarEpisodioCE(oCita.getDatosFechaFinal(), 
                                                    oMed.getNombres().split("-"), 
                                                    getDiagcie10().getClave1CE(), 
                                                    getCita().getPaciente().getExpediente().getNumero(), 
                                                    getCita().getPaciente().getFolioPaciente());
                                                pag="BuscarCitas";
                                                reseteaDatosCita();
                                                message = new FacesMessage(
                                                        FacesMessage.SEVERITY_INFO, 
                                                        "Registro de Cita", 
                                                        "Registro exitoso! :) "+
                                                    oCita.getDatosFechaFinal());
                                            }
                                        }
                                    }else{
                                        mod=getCita().getPaciente().modificarPacCE();
                                        if (mod>0){/*Insertar Cita*/
                                            if (oCita.isOpcion()==false)
                                                cita=oCita.insertar(
                                                        oMed.getNombres(), 
                                                        getCita().getPaciente().getFolioPaciente(), 
                                                        maximoCitasPS, totalPrimVez, totalSubs);
                                            else{
                                                cita=oCita.modificaCitaReservada(
                                                        oMed.getNombres(), 
                                                        getCita().getPaciente().getFolioPaciente(), 
                                                        maximoCitasPS, totalPrimVez, totalSubs);
                                            }
                                        }
                                        if (cita>0 && (ins!=23503 && mod!=23503)){
                                            if (oCita.isOpcion()==false)
                                                ep=oEP.insertarEpisodioCE(oCita.getDatosFechaFinal(), 
                                                    oMed.getNombres().split("-"), 
                                                    getDiagcie10().getClave1CE(), 
                                                    getCita().getPaciente().getExpediente().getNumero(), 
                                                    getCita().getPaciente().getFolioPaciente());
                                            else{
                                                ep=oEP.modificarEPReserva(oCita.getDatosFechaFinal(), 
                                                    oMed.getNombres().split("-"), 
                                                    getDiagcie10().getClave1CE(), 
                                                    getCita().getPaciente().getExpediente().getNumero(), 
                                                    getCita().getPaciente().getFolioPaciente(), oCita.getEP());
                                            }
                                            pag="BuscarCitas";
                                            reseteaDatosCita();
                                            message = new FacesMessage(
                                                    FacesMessage.SEVERITY_INFO, 
                                                    "Registro de Cita", "Registro exitoso! :)"+
                                                    oCita.getDatosFechaFinal());
                                            oAsigCon.setCitasSubs(0);
                                            oAsigCon.setCitas1eraVez(0);
                                        }
                                    }
                                }else{
                                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita", "El número de seguro popular ya existe para otro paciente");
                                    setPestana(0);
                                }
                            }else{
                                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita", "Paciente ya Citado");
                                setPestana(0);
                            }
                        }else{
                            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita", "El médico tiene un permiso en esa fecha");
                            setPestana(1);
                        }
                    }else{
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita", "La fecha seleccionada es un Día Festivo");
                        setPestana(1);
                    }
                }
            }else{
                sEstado="";
                tipocitas=validaTiposCitas();
                if (tipocitas==false)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Registro de Cita", 
                        "No hay espacio en la fecha seleccionada");
                setPestana(1);
            }
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Registro de Cita", 
                    "Debe seleccionarse un diagnóstico existente");
            getDiagcie10().setDescripcionDiag("");
        }
    }catch(Exception e){
        e.printStackTrace();
    }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }
    //*********************************************************************

    public Seguro getoSeg() {
        return oSeg;
    }

    public void setoSeg(Seguro oSeg) {
        this.oSeg = oSeg;
    }
    
    public boolean validaTiposCitas() throws Exception{
        boolean error=false;
        //Total de citados de primvez y subs
        totalPrimVez=oEP.buscarTipoEpisodio(oMed.getNombres().split("-"), oCita.getFechaCita(), "S");
        totalSubs=oEP.buscarTipoEpisodio(oMed.getNombres().split("-"), oCita.getFechaCita(), "N");
        SimpleDateFormat df=new SimpleDateFormat("EEEE");
        Calendar Cal=Calendar.getInstance();
        Cal.setTime(oCita.getFechaCita());
        String diaTexto="";
        int numDia=Cal.get(Calendar.DAY_OF_WEEK);
        switch(numDia){
            case 1: diaTexto="DOMINGO"; break;
            case 2: diaTexto="LUNES"; break;
            case 3: diaTexto="MARTES"; break;
            case 4: diaTexto="MIÉRCOLES"; break;
            case 5: diaTexto="JUEVES"; break;
            case 6: diaTexto="VIERNES"; break;
            case 7: diaTexto="SÁBADO"; break;
        }
        String dia=diaTexto;
        //MAXIMO DE CITAS PRIM Y SUBS
        boolean max=oAsigCon.buscaMaxPrimSubs(oMed.getNombres().split("-"), dia);
        //si citados < maximo si se puede citar

            if (totalPrimVez>=(oAsigCon.getCitas1eraVez()+nExtra) && oEP.getPrimeraVezEsp()==true){
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita", "No hay cupo para citas de primera vez");
                setPestana(1);
                sEstado="";
                error=true;
            }
            else{
                if (totalSubs>=(oAsigCon.getCitasSubs()+nExtra) && oEP.getPrimeraVezEsp()==false){
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita", "No hay cupo para citas subsecuentes");
                setPestana(1);
                error=true;
                sEstado="";
                }
            }
           if (oAsigCon.getCitas1eraVez()==0 && oAsigCon.getCitasSubs()==0){
               message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita", "El médico no atiende citas ese día de la semana");
               setPestana(1);
               sEstado="mostrarbtn";
               error=true;
           }
           maximoCitasPS=oAsigCon.getCitas1eraVez()+oAsigCon.getCitasSubs();
        return error;
    }
    
    public void reseteaDatosCita(){
        getCita().getPaciente().getReferencia().setClave("0");
        getCita().getPaciente().getTipoSol().setTipoParametro("T3201");
        oEP.setPrimeraVezHRRB(true);
        oEP.setPrimeraVezEsp(true);
        oCita.setFechaCita(null);
        nExtra=0;
    }
    
    public int getMaximoCitasPS(){
        return maximoCitasPS;
    }
    
    public int getTotalCitados(){
        return nTotalCitados;
    }
    
    public String añadirExtra(){
        String pag="";
        //aumentar una variable en 1
        if (nTotalCitados>=maximoCitasPS){
        nExtra=nTotalCitados-maximoCitasPS+1;
        }else{
            if (oEP.getPrimeraVezEsp()==true){
                nExtra=nTotalCitados-oAsigCon.getCitas1eraVez()+1;
            }else{
                nExtra=nTotalCitados-oAsigCon.getCitasSubs()+1;
            }
        }
        //invocar al método de registro
        pag=registraCita();
        sEstado="mostrarbtn";
        return pag;
    }

    public String muestraExtra(){
        return sEstado;
    }
    
    public void setEstado(String sEstado){
        this.sEstado=sEstado;
    }
    
    public String getEstado(){
        return sEstado;
    }

    public DiagnosticoCIE10 getDiagcie10() {
        return diagcie10;
    }

    public void setDiagcie10(DiagnosticoCIE10 diagcie10) {
        this.diagcie10 = diagcie10;
    }
    
    public boolean habilitaRef(){
        if (getCita().getPaciente().getTipoSol().getTipoParametro() != null &&
                (getCita().getPaciente().getTipoSol().getTipoParametro().compareTo(
                "01")==0 || 
                getCita().getPaciente().getTipoSol().getTipoParametro().compareTo("")==0)){
            return false;
        }else{
            getCita().getPaciente().getReferencia().setClave("0");
            return true;
        }    
    }
    
    public void setPestana(int nPestana){
        this.nPestana=nPestana;
    }
    
    public int getPestana(){
        return nPestana;
    }
}
