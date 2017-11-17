/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 *
 * @author sam
 */
@ManagedBean(name = "regUsuAdm")
@SessionScoped
public class RegistrarUsuAdm implements Serializable {

    private Usuario oUsua, oUsuAdm, oUsuAdmAux;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private boolean bEstadoBoton;
    private boolean bEstadoDlg;

    /**
     * Creates a new instance of RegistrarUsuAdm
     */
    public RegistrarUsuAdm() {
        bEstadoBoton = false;
        bEstadoDlg = false;
        oUsua = new Usuario();
        oUsuAdm = new Usuario();

        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public String guardar() throws Exception {
        if (oUsuAdm.getIdUsuario().equals("") || oUsuAdm.getPassword().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "INGRESE TODOS LOS DATOS"));
        } else {
            if (oUsuAdm.buscarNombreUsuario()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO PUEDE USAR ESE NOMBRE"));
            } else {
                if (oUsuAdm.insertarCapa(sUsuario) == 1) {
                    oUsuAdm.setDesPerfil("ADMCAPASIT");
                    if (oUsuAdm.insertarUsuPerf(sUsuario) == 1) {
                        oUsuAdm = new Usuario();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ADMINISTRADOR", "GUARDADO"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "ADMINISTRATIVO NO GUARDADO"));
                }
            }
        }
        return "altaUsuario";
    }

    public String buscar() throws Exception {
        if (oUsuAdm.getIdUsuario().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "INGRESE NOMBRE DE USUARIO"));
        } else {
            if (oUsuAdm.buscarNombreUsuario()) {//falta validar que sea administrador
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "USUARIO", "ENCONTRADO"));
                oUsuAdm.setIdUsuario2(oUsuAdm.getIdUsuario());
                if (oUsuAdm.buscarUsuarioPerfilCapa()) {
                    if (oUsuAdm.getCvePerfil().equals("ADMCAPASIT")) {
                        bEstadoBoton = true;
                        oUsuAdmAux = oUsuAdm;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "EL USUARIO", " ES ADMINISTRADOR"));
                    } else {
                        oUsuAdm = new Usuario();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "EL USUARIO", " NO ES ADMINISTRADOR"));
                    }
                }
            } else {
                bEstadoBoton = false;
                oUsuAdm = new Usuario();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "ADMINISTRADOR NO ENCONTRADO"));
            }
        }
        return "modificaUsuario";
    }

    public String buscarElinacion() throws Exception {
        if (oUsuAdm.getIdUsuario().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "INGRESE NOMBRE DE USUARIO"));
        } else {
            if (oUsuAdm.buscarNombreUsuario()) {
                setEstadoDlg(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "USUARIO", "ENCONTRADO"));
            } else {
                oUsuAdm = new Usuario();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "USUARIO NO ENCONTRADO"));
            }
        }
        return "modificaUsuario";
    }

    public String modificar() throws Exception {
        if (oUsuAdm.getIdUsuario().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "BUSQUE USUARIO"));
            return "modificaUsuario";
        }
        if (oUsuAdm.getIdUsuario2().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "INGRESE EL NUEVO NOMBRE"));
            return "modificaUsuario";
        }
        if (oUsuAdm.getPassword().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "INGRESE CONTRESEÑA"));
            return "modificaUsuario";
        }
        if (oUsuAdm.getIdUsuario2().equals(oUsuAdm.getIdUsuario())) {   
            if (oUsuAdm.modificarUsuarioCapa(sUsuario) == 1) {
                oUsuAdm = new Usuario();
                setEstadoBoton(false);
                oUsuAdmAux = new Usuario();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ADMINISTRADOR", "MODIFICADO"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ADMINISTRADOR", "NO MODIFICADO"));
            }
        } else {         
            String old = oUsuAdm.getIdUsuario();             
            oUsuAdm.setIdUsuario(oUsuAdm.getIdUsuario2());
            if (oUsuAdm.buscarNombreUsuario()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL NOMBRE DE USUARIO YA EXISTE"));
            } else {                 
                oUsuAdm.setIdUsuario(old);
                if (oUsuAdm.modificarUsuarioCapa(sUsuario) == 1) {
                    oUsuAdm = new Usuario();
                    setEstadoBoton(false);
                    oUsuAdmAux = new Usuario();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ADMINISTRADOR", "MODIFICADO"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ADMINISTRADOR", "NO MODIFICADO"));
                }
            }
        }
        return "modificaUsuario";
    }

    public String eliminar() throws Exception {
        if (oUsuAdm.eliminarUsuCapa(sUsuario) == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "USUARIO", "ELIMINADO"));
            oUsuAdm = new Usuario();
            setEstadoDlg(false);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "USUARIO", " NO MODIFICADO"));
            oUsuAdm = new Usuario();
            setEstadoDlg(false);
        }
        return "modificaUsuario";
    }

    public String cambiaEstadoDlg() {
        setEstadoDlg(false);
        oUsuAdm = new Usuario();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ELIMINACION", "CANCELADA"));

        return "modificaUsuario";
    }

    public Usuario getUsuAdm() {
        return oUsuAdm;
    }

    public void setUsuAdm(Usuario oUsuAdm) {
        this.oUsuAdm = oUsuAdm;
    }

    public boolean getEstadoBoton() {
        return bEstadoBoton;
    }

    public void setEstadoBoton(boolean bEstadoBoton) {
        this.bEstadoBoton = bEstadoBoton;
    }

    public boolean getEstadoDlg() {
        return bEstadoDlg;
    }

    public void setEstadoDlg(boolean bEstadoDlg) {
        this.bEstadoDlg = bEstadoDlg;
    }

}
