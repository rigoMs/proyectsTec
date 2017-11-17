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
@ManagedBean(name = "regMed")
@SessionScoped
public class RegistraMedicamento implements Serializable {

    private Medicamento oMed = null;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;

    public RegistraMedicamento() {
        oMed = new Medicamento();
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public String almacena() throws Exception {
        Medicamento medAux = new Medicamento();
        medAux.setCodBarras(oMed.getCodBarras());
        if (! medAux.buscar()) {
            if (oMed.insertar(sUsuario) == 1) {
                this.oMed = new Medicamento();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "MEDICAMENTO ", "INSERTADO"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "MEDICAMENTO ", "NO INSERTADO "));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "CODIGO DE BARRAS YA EXISTE"));
        }
        medAux = new Medicamento();
        return "nuevoMedicamento";
    }

    public Medicamento getMed() {
        return oMed;
    }
}
