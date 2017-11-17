/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.consultaexterna.Permiso;
import mx.gob.hrrb.modelo.core.Perfil;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Javi
 */
@ManagedBean(name="oPer")
@SessionScoped
public class VerPermisos {
    private Permiso oPer;
    private  HttpServletRequest httpServletRequest;
    private  FacesContext faceContext;
    private FacesMessage facesMessage;
    private Firmado oFirm;
    private String sUsuario;
    private String colspan;
    private Perfil oPerfil;
    private boolean bper;
    
    /**
     * Creates a new instance of VerPermisos
     */
    public VerPermisos(){
        oPer=new Permiso();
        oPerfil=new Perfil();
        bper=false;
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
        try{
        bper=oPerfil.buscaPerfilPorUsuario();
        }catch(Exception e){}
        if (bper==true){
            colspan="2";
        }
        else{
            colspan="1";
        }      
    }

    public Permiso getPer() {
        return oPer;
    }

    public void setPer(Permiso oPer) {
        this.oPer = oPer;
    }
    
        public String mostrarColumna(){
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
        if (bper==true){
            colspan="2";
            return "";
        }
        else{
            colspan="1";
            return "mostrarbtn";
        }
    }
        
    public String habilitaCalendarios(){
        if (bper==true)
            return "mostrarbtn";
        else
            return "";
    }
    
    public String borraPermiso(){
        int nAfec = 0;
        String pag="/faces/sesiones/consultaexterna/VerPermisos.xhtml";
        FacesMessage message=null;
        try{
            nAfec=oPer.eliminar();
        }catch (Exception e){};
        oPer.getPersonalHospitalario().setNoTarjeta(0);
        oPer.setFechaIni(null);
        oPer.setHoras("");
        oPer=new Permiso();
        
        if (nAfec>=1)
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "Eliminación Correcta");
        else
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "Eliminacion fallida :(");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

        public String actualizar() throws Exception{
        int nAfec = 0;
        String pag="/faces/sesiones/consultaexterna/VerPermisos.xhtml";
        FacesMessage message=null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fechaIni="";
        String horas="";
        String horas2="";
        String tipo="";
        if (oPer.getHoras2()==null)
            oPer.setHoras2("");
        if (oPer.getHoras2().compareTo("")==0){
            horas=" 00:00:00";
            horas2=" 00:00:00";
            if (oPer.getNewFechaIni()==oPer.getNewFechaFin())
                tipo="2";
            else
                tipo="1";
        }else{
            horas=" "+oPer.getHoras2().substring(0, 5)+":00";
            horas2=" "+oPer.getHoras2().substring(10, 15)+":00";
            tipo="3";
        }
        int repetido=oPer.PermisoExistenteMod(oPer.getPersonalHospitalario().getNoTarjeta(), oPer.getNewFechaIni(), oPer.getNewFechaFin(), oPer.getHini());
        if(repetido==0){
        try{
           nAfec=oPer.modificar(df.format(oPer.getNewFechaIni())+horas, df.format(oPer.getNewFechaFin())+horas2);
        }catch (Exception e){};
        
        oPer.getPersonalHospitalario().setNoTarjeta(0);
        oPer.setFechaIni(null);
        oPer.setFechaFin(null);
        oPer=new Permiso();
        
        if (nAfec>=1)
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "Modificación Correcta");
        else
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "Modificación fallida :(");
        }else{
            pag="/faces/sesiones/consultaexterna/ModificaPermiso.xhtml";
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Permisos", "El Permiso es repetido o las fechas chocan con otro permiso de ese Médico");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

    
    public String getColspan(){
        return colspan;
    }
    
    public boolean habilitaHorasMod(){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if (getPer().getNewFechaIni()!=null && getPer().getNewFechaFin()!=null){
        String inicial=df.format(getPer().getNewFechaIni());
        String finali=df.format(getPer().getNewFechaFin());
        if (inicial.compareTo(finali)!=0)
            return true;
        else
            return false;
        }else
            return false;
    }
}
