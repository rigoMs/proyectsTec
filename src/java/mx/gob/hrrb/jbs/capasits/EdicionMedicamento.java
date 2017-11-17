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
import mx.gob.hrrb.modelo.core.Medicamento;

/**
 *
 * @author sam
 */
@ManagedBean(name = "oEdicMed")
@SessionScoped
public class EdicionMedicamento implements Serializable {

    private int nCodBarras = 0;
    private boolean encontrado = false;
    private Medicamento oMed = null;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;

    public EdicionMedicamento() {
        oMed = new Medicamento();
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public void buscaMedicamento() throws Exception {
        oMed = new Medicamento();
        if (getCodBarras() > 0) {
            oMed.setCodBarras(getCodBarras());
            if (oMed.buscar()) {
                encontrado = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Encontrado, Clave: ", "" + oMed.getClaveMedicamento()));
            } else {
                encontrado = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, " No encontrado ", ""));
            }
        }
    }

    public String actualiza() throws Exception {
        if (oMed.modificar(sUsuario) == 1) {
            nCodBarras = 0;
            encontrado = false;
            oMed = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Medicamento Actualizado ", " "));
            return "edicionMedicamento";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error ", " Verificar Datos "));
            return "edicionMedicamento";
        }
    }

    public int getCodBarras() {
        return nCodBarras;
    }

    public void setCodBarras(int nCodBarras) {
        this.nCodBarras = nCodBarras;
    }

    public Medicamento getMed() {
        return oMed;
    }

    public void setMed(Medicamento oMed) {
        this.oMed = oMed;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

}
