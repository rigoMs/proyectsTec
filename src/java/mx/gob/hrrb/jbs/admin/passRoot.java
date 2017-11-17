/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.admin;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 *
 * @author juan
 */
@ManagedBean(name="edicPassRoot")
@ViewScoped
public class passRoot {

    private Usuario oUsuario;
    
    private Firmado oFirm;
    private String sUsuarioFirm;
    
    /**
     * Creates a new instance of passRoot
     */
    public passRoot() {
        oUsuario= new Usuario();
        
        
        oFirm = new Firmado();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuarioFirm = oFirm.getUsu().getIdUsuario();
        }
    }
    
    public void cambiarPassword(){
        boolean exito=false;
        
        try {
            exito=oUsuario.modificarUsuarioRoot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(exito){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Exito", "La contraseña se ha modificado satisfactoriamente"));
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error",
                    "No se ha actualizado la contraseña, verifique que la contraseña anterior sea correcta"));
        }
    }

    /**
     * @return the oUsuario
     */
    public Usuario getUsuario() {
        return oUsuario;
    }

    /**
     * @param oUsuario the oUsuario to set
     */
    public void setUsuario(Usuario oUsuario) {
        this.oUsuario = oUsuario;
    }
    
    
    
}
