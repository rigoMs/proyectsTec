/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import mx.gob.hrrb.modelo.core.Calendario;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.consultaexterna.Permiso;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oProgVac")
@SessionScoped
public class ProgVacaciones {
    private Medico oMedico;
    private Permiso oFechaIni;
    private Medico oMedico2;
    private Permiso oFechaIni2;
    private String sHoras;
    private Date oFecActual;
    private String index;
    /**
     * Creates a new instance of ProgVacaciones
     */
    public ProgVacaciones() {
        oMedico=new Medico();
        oFechaIni=new Permiso();
        oMedico2=new Medico();
        oFechaIni2=new Permiso();
        oFecActual=new Date();
        sHoras="";
        index="0";
    }
    
       public List<Medico> getListaMedicos(){
       List<Medico> lLista = null;
       try {
           lLista = new ArrayList<Medico>(Arrays.asList(
                   (new Medico()).buscarMedicosCE()));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }

    public Medico getMedico() {
        return oMedico;
    }

    public void setMedico(Medico oMedico) {
        this.oMedico = oMedico;
    }

    public Permiso getFechaIni() {
        return oFechaIni;
    }

    public void setFechaIni(Permiso oFechaIni) {
        this.oFechaIni = oFechaIni;
    }

    public void reseteaHoras() throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        FacesMessage message=null;
        String h1, h2, h3, h4;
        int num=0;
        h1=sHoras.substring(0, 5);
        h2=sHoras.substring(10, 15);
        index="1";
        int repetido=oFechaIni2.buscarPermisoExistente(oMedico2.getNoTarjeta(), oFechaIni2.getFechaIni(), oFechaIni2.getFechaFin());
        if (repetido==0){
        num=oFechaIni2.insertar(oMedico2.getNoTarjeta(), df.format(oFechaIni2.getFechaIni())+" "+h1+":00", df.format(oFechaIni2.getFechaFin())+" "+h2+":00");
        
        if (num==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "Registro fallido :(");
         }else{
               message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "Registro Exitoso!!");
                oFechaIni2.setFechaIni(null);
                oFechaIni2.setFechaFin(null);
                oMedico2.setNoTarjeta(0);
                sHoras="";
         }
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "El Permiso es repetido o las fechas chocan con otro permiso de ese Médico");
        }
         RequestContext.getCurrentInstance().showMessageInDialog(message);
         
    }
    
    public void reseteaDias() throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        FacesMessage message=null;
        int num=0;
        index="0";
        int repetido=oFechaIni2.buscarPermisoExistente(oMedico.getNoTarjeta(), oFechaIni.getFechaIni(), oFechaIni.getFechaFin());
        if (repetido==0){
        num=oFechaIni.insertar(oMedico.getNoTarjeta(), df.format(oFechaIni.getFechaIni()), df.format(oFechaIni.getFechaFin())+" 00:00:00");
        
        if (num==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "Registro fallido :(");
         }else{
               message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "Registro Exitoso!!");
                oFechaIni.setFechaIni(null);
                oFechaIni.setFechaFin(null);
                oMedico.setNoTarjeta(0);
                sHoras="";    
         }
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "El Permiso es repetido o las fechas chocan con otro permiso de ese Médico");
        }
         RequestContext.getCurrentInstance().showMessageInDialog(message);        
    }
    
    public String getHoras() {
        return sHoras;
    }

    public void setHoras(String sHoras) {
        this.sHoras = sHoras;
    }

    public Medico getMedico2() {
        return oMedico2;
    }

    public void setMedico2(Medico oMedico2) {
        this.oMedico2 = oMedico2;
    }

    public Permiso getFechaIni2() {
        return oFechaIni2;
    }

    public void setFechaIni2(Permiso oFechaIni2) {
        this.oFechaIni2 = oFechaIni2;
    }

    public Date getFecActual() {
        return oFecActual;
    }

    public void setFecActual(Date oFecActual) {
        this.oFecActual = oFecActual;
    }
    
    public String getIndex(){
        return index;
    }
    public void setIndex(String index){
        this.index=index;
    }
}
