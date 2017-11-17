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
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oCEdoSalud")
@SessionScoped
public class CambiaEdoSalud {
    private Hospitalizacion oHosp;
    private String sOpe="";
    private long nFolioPac=0;
    private long nNumHospitalizacion=0;
    private long nEpisodio=0;
    private int nNumeroExpediente=0;
    private boolean bBuscado = false;
    /**
     * Creates a new instance of CambiaEdoSalud
     */
    public CambiaEdoSalud() {
        oHosp = new Hospitalizacion();
        //oHosp.getEpisodioMedico().getDiagIngreso().recibeTipo(1);
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
                    //oHosp.getEpisodioMedico().getDiagIngreso().recibeTipo(1);
                    oHosp.getPaciente().setFolioPaciente(getFolioPac());
                    oHosp.setNumIngresoHos(getNumHospitalizacion());
                    oHosp.getEpisodioMedico().setClaveEpisodio(getEpisodio());
                    oHosp.buscarDatosEdoSalud();
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
    FacesMessage message=null;
    String redirect="";
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosp.insertar();
            else if(this.sOpe.equals("m"))
                nAfec = this.oHosp.modificaEdoSalud();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1){
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado De Salud", "Datos Guardados Correctamente");
                    boolean ban=oHosp.buscaperfilHosp();
                    System.out.println("bandera "+ban);
                    if(ban==true)                        
                        redirect="PacientesHospitalizadosArea";
                    else
                        redirect="PacientesHospitalizados";                
            }else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado De Salud", "Datos no Guardados :(");
            RequestContext.getCurrentInstance().showMessageInDialog(message);  
            
        return redirect;
    } 
    
         //Retorna lista Edo Salud
   public List<Parametrizacion> getListaEdoSalud(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T39")));
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
    
}
