/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oCita")
@SessionScoped
public class ReservaCita {
    private Hospitalizacion oHosp;
    private String sOpe="";
    private long nFolioPac=0;
    private long nNumHospitalizacion=0;
    private int nNumeroExpediente=0;
    private boolean bBuscado = false;
    private EpisodioMedico oEpiMed;
    private Cita oCita;
    /**
     * Creates a new instance of ReservaCita
     */
    public ReservaCita() {
        oHosp = new Hospitalizacion();
        oEpiMed = new EpisodioMedico();
        oCita = new Cita();
    }

    public Hospitalizacion getDatos(){        
        if (!getBuscado()){
            setBuscado(true);
            if (this.getOpe().equals("a")){
                oHosp = new Hospitalizacion();

            }
            else{
                try{       
                    oHosp = new Hospitalizacion();
                    oCita = new Cita();
                    oEpiMed = new EpisodioMedico();
                    oHosp.getPaciente().setFolioPaciente(getFolioPac());
                    oHosp.setNumIngresoHos(getNumHospitalizacion());
                    oHosp.buscarDatosCita();
                }catch(Exception e){
                    e.printStackTrace();
                    setBuscado(false);
                }
            }
        }
        return oHosp;
    }   
        
    
    public String guardar() throws Exception{
        System.out.println("**********************Probando llegue aqui****************************");
        String url="";
        FacesMessage message=null;
        int nAfec = 0;
            try{
                if (this.sOpe.equals("a"))
                    nAfec = this.oHosp.insertar();
                else if(this.sOpe.equals("m"))
                    oCita.getPaciente().setFolioPaciente(nFolioPac);
                    nAfec = this.oCita.insertarReservacionCita(oEpiMed.getDiagIngreso().getClave(), nNumeroExpediente);
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("Update"+ nAfec);
                if (nAfec>=1){
                    boolean ban=oHosp.buscaperfilHosp();
                    System.out.println("bandera "+ban);
                    if(ban==true)                        
                        url="PacientesHospitalizadosArea";
                    else
                        url="PacientesHospitalizados";
                    //url="/sesiones/Inicio.xhtml";
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ReservaCita", "Datos Guardados Correctamente");
                }else if (nAfec==-1){                    
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ReservaCita", "El Paciente Ya Tiene Una Cita Reservada En Esta Área");
                }else{                    
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ReservaCita", "Datos no Guardados :(");
                }
                RequestContext.getCurrentInstance().showMessageInDialog(message);       
        //sOpe="";
            return url;
    }             
        
         //Retorna lista de Areas Consulta Externa
   public List<AreaServicioHRRB> getListaAreaCE(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasCE()));
       } catch (Exception ex) {
           Logger.getLogger(ReservaCita.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }   

         //Retorna lista de Tiempo Aprox
   public List<Parametrizacion> getListaTiempoAprox(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T45")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
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

    /**
     * @return the oEpiMed
     */
    public EpisodioMedico getEpiMed() {
        return oEpiMed;
    }

    /**
     * @param oEpiMed the oEpiMed to set
     */
    public void setEpiMed(EpisodioMedico oEpiMed) {
        this.oEpiMed = oEpiMed;
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
    
}
