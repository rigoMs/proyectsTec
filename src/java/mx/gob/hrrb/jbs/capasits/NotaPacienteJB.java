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
import mx.gob.hrrb.modelo.capasits.NotaMedica;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 *
 * @author sam
 */
@ManagedBean(name = "notaPac")
@SessionScoped
public class NotaPacienteJB implements Serializable {

    private PacienteCapasits oPacienteCapa = null;
    private NotaMedica oNota = null;
    private Usuario oUsua;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private String sNotaAux;
    private boolean bBotonVisible;

    /**
     * Creates a new instance of NotaPacienteJB
     */
    public NotaPacienteJB() {
        oPacienteCapa = new PacienteCapasits();
        oNota = new NotaMedica();
        sNotaAux = "";
        bBotonVisible = false;

        oUsua = new Usuario();
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public void buscarPaciente() throws Exception {
        if (oPacienteCapa.getIdNacional() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "VERIFIQUE ID ", " "));
        } else {
            if (oPacienteCapa.buscarIdNacionalCapa()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "PACIENTE ENCONTRADO ", " "));
                oNota.setFolioPaciente(oPacienteCapa.getFolioPaciente());
                setBotonVisible(true);
                if (oNota.buscar()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "PACIENTE TIENE NOTA "));
                    oPacienteCapa = new PacienteCapasits();
                    oNota = new NotaMedica();
                    setBotonVisible(false);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "PACIENTE NO ENCONTRADO ", " "));
            }

        }
    }

    public void buscarPacienteModEli() throws Exception {
        if (oPacienteCapa.getIdNacional() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "VERIFIQUE ID ", " "));
        } else {
            if (oPacienteCapa.buscarIdNacionalCapa()) {
                oNota.setFolioPaciente(oPacienteCapa.getFolioPaciente());
                if (!oNota.buscar()) {
                    oPacienteCapa = new PacienteCapasits();
                    oNota = new NotaMedica();
                    setNotaAux("");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "PACIENTE SIN NOTA", "NECESITA CREARLA PRIMERO"));
                } else {
                    setBotonVisible(true);
                    setNotaAux(oNota.getObservacion());
                }
            } else {
                setBotonVisible(false);
                oPacienteCapa = new PacienteCapasits();
                oNota = new NotaMedica();
                setNotaAux("");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "PACIENTE NO ENCONTRADO ", " "));
            }
        }
    }

    public String guardar() throws Exception {
        if (oNota.getNoTarjeta() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "NUMERO DE TARJETA INVALIDO "));
            return "notaPaciente";
        }

        if (!oNota.buscarNoTarjeta()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "NUMERO DE TARJETA NO EXISTE "));
            return "notaPaciente";
        }
        if (oPacienteCapa.getIdNacional() <= 0 || oPacienteCapa.getFolioPaciente() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "PACIENTE NO INVALIDO "));
            return "notaPaciente";
        }

        if (oNota.getObservacion().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "LA NOTA ESTA VACIA"));
            return "notaPaciente";
        }

        oNota.setFolioPaciente(oPacienteCapa.getFolioPaciente());
        if (oNota.insertar(sUsuario, oNota.getNoTarjeta()) == 1) {
            oNota = new NotaMedica();
            oPacienteCapa = new PacienteCapasits();
            setNotaAux("");
            setBotonVisible(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "NOTA", "GUARDADA"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "NO GUARDADA"));
        }
        return "notaPaciente";
    }

    public String modificar() throws Exception {
        oNota.setFolioPaciente(oPacienteCapa.getFolioPaciente());
        if (!oNota.buscar()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "PACIENTE SIN NOTA", "NECESITA CREARLA PRIMERO"));
            return "modificaNotaPaciente";
        }
        if (oPacienteCapa.getIdNacional() <= 0 || oPacienteCapa.getFolioPaciente() == 0 || oPacienteCapa == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "PACIENTE NO INVALIDO "));
            return "modificaNotaPaciente";
        }
        if (getNotaAux().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "LA NOTA ESTA VACIA"));
            return "modificaNotaPaciente";
        }
        oNota.setObservacion(getNotaAux());
        oNota.setFolioPaciente(oPacienteCapa.getFolioPaciente());
        if (oNota.modificar(sUsuario) == 1) {
            oNota = new NotaMedica();
            oPacienteCapa = new PacienteCapasits();
            setNotaAux("");
            setBotonVisible(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "NOTA", "MODIFICADA"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "NOTA NO GUARDADA"));
        }
        return "modificaNotaPaciente";
    }

    public String eliminar() throws Exception {
        oNota.setFolioPaciente(oPacienteCapa.getFolioPaciente());
        if (!oNota.buscar()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "PACIENTE SIN NOTA", "NECESITA CREARLA PRIMERO"));
            return "modificaNotaPaciente";
        }
        if (oPacienteCapa.getIdNacional() <= 0 || oPacienteCapa.getFolioPaciente() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "PACIENTE NO INVALIDO "));
            return "modificaNotaPaciente";
        }
        oNota.setFolioPaciente(oPacienteCapa.getFolioPaciente());
        if (oNota.eliminar(sUsuario) == 1) {
            oNota = new NotaMedica();
            oPacienteCapa = new PacienteCapasits();
            setNotaAux("");
            setBotonVisible(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "NOTA", "ELIMINADA"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "NOTA NO ELIMINADA"));
        }
        return "modificaNotaPaciente";
    }

    public PacienteCapasits getPacienteCapa() {
        return oPacienteCapa;
    }

    public void setPacienteCapa(PacienteCapasits oPacienteCapa) {
        this.oPacienteCapa = oPacienteCapa;
    }

    public NotaMedica getNota() {
        return oNota;
    }

    public void setNota(NotaMedica oNota) {
        this.oNota = oNota;
    }

    public String getNotaAux() {
        return sNotaAux;
    }

    public void setNotaAux(String sNotaAux) {
        this.sNotaAux = sNotaAux;
    }

    public boolean getBotonVisible() {
        return bBotonVisible;
    }

    public void setBotonVisible(boolean bBotonVisible) {
        this.bBotonVisible = bBotonVisible;
    }

    public String cancelar() {
        oNota = new NotaMedica();
        oPacienteCapa = new PacienteCapasits();
        setNotaAux("");
        setBotonVisible(false);
        return "modificaNotaPaciente";
    }

}
