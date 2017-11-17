/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.core;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.modelo.core.Usuario;
/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oBuscarUsu")
@SessionScoped
public class BuscarUsuarios {

    /**
     * Creates a new instance of BuscarPaciente
     */

    private String sRedireccionar="hospitalizacion/HojaCode";
    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario oUsuario;

    public BuscarUsuarios() {  
        oUsuario = new Usuario();
        oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
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