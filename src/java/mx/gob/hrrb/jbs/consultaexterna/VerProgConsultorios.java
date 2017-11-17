/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import mx.gob.hrrb.modelo.core.Perfil;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oVerProgConsul")
@SessionScoped
public class VerProgConsultorios {
    private AsignaConsultorio oAsigcon;
    private  HttpServletRequest httpServletRequest;
    private  FacesContext faceContext;
    private FacesMessage facesMessage;
    private Firmado oFirm;
    private String sUsuario;
    private Perfil oPerfil;
    private boolean bper;
    private String sAux;
    /**
     * Creates a new instance of VerProgConsultorios
     */
    public VerProgConsultorios() {
        oAsigcon=new AsignaConsultorio();
        oPerfil=new Perfil();
        bper=false;
        oFirm=new Firmado();
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
        sAux="  -  ";
    }

    public AsignaConsultorio getAsigcon() {
        return oAsigcon;
    }

    public void setAsigcon(AsignaConsultorio asigcon) {
        this.oAsigcon = asigcon;
    }
    
    public void setAux(String a){
        sAux=a;
    }
    
    public String getAux(){
        return sAux;
    }
    
    public String mostrarColumna(){
        if (bper==true)
            return "";
        else
            return "mostrarbtn";
    }
    
    public String borraProgramacion(){
        int nAfec = 0;
        String pag="VerProgConsultorios.xhtml";
        FacesMessage message=null;
        try{
            nAfec=oAsigcon.eliminar();
        }catch (Exception e){};
        oAsigcon.getAreaServicio().setDescripcion("");
        oAsigcon.getPH().setNoTarjeta(0);
        oAsigcon.getCons().setNoConsultorio(0);
        oAsigcon=new AsignaConsultorio();
        
        if (nAfec>=1)
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "Eliminación Correcta");
        else
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "Eliminacion fallida :(");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }
}
