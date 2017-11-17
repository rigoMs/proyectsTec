/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "ohojaAlta")
@SessionScoped
public class HojaAltaPaciente {
    private Hospitalizacion oHosp;
    private Cita oCita;
    private String sOpe="";
    private long nFolioPac=0;
    private long nNumHospitalizacion=0;
    private int nNumeroExpediente=0;
    private long nEpisodio=0;
    private boolean bBuscado = false;
    private List<Cita> lCitaha;
    private boolean bActivar;
    private String sEstadoCODE;
    /**
     * Creates a new instance of HojaAltaPaciente
     */
    public HojaAltaPaciente() throws Exception {
        oHosp = new Hospitalizacion();
        oCita = new Cita();                    
        //oCita.getPaciente().setFolioPaciente(getFolioPac());     
        //oCita.setNumIngresoHosp(getNumHospitalizacion());
        //oCita.setClaveEpisodio(getEpisodio());       
    }

    public Hospitalizacion getAlta(){        
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                oHosp = new Hospitalizacion();
            }
            else{
                try{            
                    System.out.println("FolioPac "+nFolioPac+" nNumeroExpediente "+nNumeroExpediente+" nNumHospitalizacion "+nNumHospitalizacion+" episodio "+nEpisodio);
                    oCita.buscarCitasHojaAlta();
                    oHosp = new Hospitalizacion();
                    oHosp.getPaciente().setFolioPaciente(nFolioPac);
                    oHosp.getPaciente().getExpediente().setNumero(nNumeroExpediente);
                    oHosp.setNumIngresoHos(nNumHospitalizacion);
                    oHosp.getEpisodioMedico().setClaveEpisodio(nEpisodio);   
                    oHosp.buscarDatosEdoSalud();
                    oHosp.buscarDatosPacienteCODE();    
                    oHosp.buscarCodeEstancia();
                    oHosp.buscarCamaServicio();
                    oHosp.buscarHojaAlta();
                    oHosp.buscarSignosVitalesHojaAlta();
                    oHosp.buscarCodeAfecciones();
                    oHosp.buscarCodeProcedimientos();   
                    oHosp.buscarDatosEdoSalud();
                }catch(Exception e){
                    e.printStackTrace();
                    bBuscado = false;
                }
            }
        }
        return oHosp;
    } 
    
    public Cita getCitas(){        
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                oCita = new Cita();
            }
            else{
                try{      
                    lCitaha = new ArrayList<Cita>();
                    Date fechaActual=new Date();
                    SimpleDateFormat fecha=new SimpleDateFormat("yyyy-MM-dd");
                    String FI=fecha.format(oHosp.getFechaIngresoHos());
                    String FF=fecha.format(fechaActual);
                    oCita = new Cita();
                    oCita.getPaciente().setFolioPaciente(nFolioPac);                  
                    //lCitaha=oCita.buscarCitasHojaAlta(FI, FF);
                }catch(Exception e){
                    e.printStackTrace();
                    bBuscado = false;
                }
            }
        }
        return oCita;
    }     
   
    public void guardar(){
        System.out.println("**********************Probando llegue aqui****************************");
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosp.insertar();
            else if(this.sOpe.equals("m"))
                nAfec = this.oHosp.InsertarHojaAlta();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos Guardados Correctamente");
            else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos no Guardados :(");
            RequestContext.getCurrentInstance().showMessageInDialog(message);       
        //sOpe="";
    }      

    public String cerrarHojaAltaLlena() throws Exception{
        System.out.println("************");
        String redirect="";
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosp.insertar();
            else if(this.sOpe.equals("m")){
                guardar();
                nAfec = this.oHosp.modificaEstadoHojaAlta();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1){
                guardar();
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos Guardados Correctamente");
                //redirect="PacientesHospitalizadosArea";              
                    boolean ban=oHosp.buscaperfilHosp();
                    //System.out.println("bandera "+ban);
                    if(ban==true)                        
                        redirect="PacientesHospitalizadosArea";
                    else
                        redirect="PacientesHospitalizados";                 
            }else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos no Guardados :(");            
            
            FacesContext.getCurrentInstance().addMessage(null, message);  
            
            return redirect;
    }     
    
    public long diasEstancia() throws ParseException{
        SimpleDateFormat fecha= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        Date fechaIng=oHosp.getEpisodioMedico().getAltaHospitalaria();
        
        
        long milis1, milis2, diff=0;
        long milxdia = 24 * 60 * 60 * 1000;
        
        if(oHosp.getEpisodioMedico().getAltaHospitalaria()!=null){
            String fechaI=""+oHosp.getFechaIngresoHos();
            String fechaE=fI.format(fechaIng);
            Date fechaIngreso=null, fechaEgreso=null;
            System.out.println(fechaI);
            System.out.println(fechaE);
            fechaIngreso=fecha.parse(fechaI);
            fechaEgreso=fecha.parse(fechaE);       
            Calendar Finicio = Calendar.getInstance();
            Calendar Ffinal = Calendar.getInstance();
            Finicio.setTime(fechaIngreso);
            Ffinal.setTime(fechaEgreso);
            milis1 = Finicio.getTimeInMillis();
            milis2 = Ffinal.getTimeInMillis();
            diff = (milis2-milis1)/milxdia;
        }
         return diff;
        
    }
    
    public boolean altaHosp(){
        boolean bloqueado=false;
        if(oHosp.getEpisodioMedico().getAltaHospitalaria()!=null)
            bloqueado=true;
        return bloqueado;
    }    
    
    public boolean habilita(int num){
        boolean habilitado=true;
        if(oHosp.getEpisodioMedico().getMotivoEgresoP()!=null && (oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3804") || oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3803")) && num==1)
            habilitado=false;
        return habilitado;
    }
    
    public boolean requiere(int num){
        boolean requerido=false;
        if(oHosp.getEpisodioMedico().getMotivoEgresoP()!=null && (oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3804") || oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3803")) && num==1)
            requerido=true;
        return requerido;
    }    
    
    public boolean activarBotones() throws Exception{
        boolean deshabilitado=true;
        if(oHosp.getEpisodioMedico().getAltaHospitalaria()!=null && oHosp.getEpisodioMedico().getMotivoEgresoP()!=null && !oHosp.getEpisodioMedico().getMotivoEgresoP().equals("     ") && !oHosp.getEpisodioMedico().getMotivoEgresoP().equals("")
                && oHosp.getPronosticoP()!=null && !oHosp.getPronosticoP().equals("     ") && !oHosp.getPronosticoP().equals("") && oHosp.getEpisodioMedico().getDestinoSTR()!=null && !oHosp.getEpisodioMedico().getDestinoSTR().equals("     ")
                && !oHosp.getEpisodioMedico().getDestinoSTR().equals("") && oHosp.getReingreso()!=null && (oHosp.getReingreso().equals("S") || oHosp.getReingreso().equals("N"))){
            sEstadoCODE = oHosp.buscaEstadoCODE();
            if(sEstadoCODE.equals("T4300") || sEstadoCODE.equals("T4303")){
                deshabilitado=false;
            }
        }
        return deshabilitado;
    }
    
    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }

    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }

    /**
     * @return the sOpe
     */
    public String getOpe() {
        return sOpe;
    }

    /**
     * @param sOpe the sOpe to set
     */
    public void setOpe(String sOpe) {
        this.sOpe = sOpe;
    }

    /**
     * @return the nFolioPac
     */
    public long getFolioPac() {
        return nFolioPac;
    }

    /**
     * @param nFolioPac the nFolioPac to set
     */
    public void setFolioPac(long nFolioPac) {
        this.nFolioPac = nFolioPac;
    }

    /**
     * @return the nNumHospitalizacion
     */
    public long getNumHospitalizacion() {
        return nNumHospitalizacion;
    }

    /**
     * @param nNumHospitalizacion the nNumHospitalizacion to set
     */
    public void setNumHospitalizacion(long nNumHospitalizacion) {
        this.nNumHospitalizacion = nNumHospitalizacion;
    }

    /**
     * @return the nNumeroExpediente
     */
    public int getNumeroExpediente() {
        return nNumeroExpediente;
    }

    /**
     * @param nNumeroExpediente the nNumeroExpediente to set
     */
    public void setNumeroExpediente(int nNumeroExpediente) {
        this.nNumeroExpediente = nNumeroExpediente;
    }

    /**
     * @return the nEpisodio
     */
    public long getEpisodio() {
        return nEpisodio;
    }

    /**
     * @param nEpisodio the nEpisodio to set
     */
    public void setEpisodio(long nEpisodio) {
        this.nEpisodio = nEpisodio;
    }

    /**
     * @return the bBuscado
     */
    public boolean getBuscado() {
        return bBuscado;
    }

    /**
     * @param bBuscado the bBuscado to set
     */
    public void setBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
    }

         //Retorna lista de Motivos Egresos
   public List<Parametrizacion> getListaMotivosEgreso(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T38")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }    
   
         //Retorna lista de Motivos Egresos
   public List<Parametrizacion> getListaPronostico(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T47")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }     
   
         //Retorna lista de Motivos Egresos
   public List<Parametrizacion> getListaDestino(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T37")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }     

    /**
     * @return the oCita
     */
    public Cita getCita() {
        return oCita;
    }

    /**
     * @param oCita the oCita to set
     */
    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }

    /**
     * @return the lCitaha
     */
    public List<Cita> getCitaha() {
        return lCitaha;
    }

    /**
     * @param lCitaha the lCitaha to set
     */
    public void setCitaha(List<Cita> lCitaha) {
        this.lCitaha = lCitaha;
    }
   
    /**
     * @return the bActivar
     */
    public boolean getActivar() {
        return bActivar;
    }

    /**
     * @param bActivar the bActivar to set
     */
    public void setActivar(boolean bActivar) {
        if(bActivar ==true){
            guardar();
            bActivar=false;
        }
        this.bActivar = bActivar;
    }    

    /**
     * @return the sEstadoCODE
     */
    public String getEstadoCODE() {
        return sEstadoCODE;
    }

    /**
     * @param sEstadoCODE the sEstadoCODE to set
     */
    public void setEstadoCODE(String sEstadoCODE) {
        this.sEstadoCODE = sEstadoCODE;
    }
    
}
