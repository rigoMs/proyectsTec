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
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.DetalleReceta;
import mx.gob.hrrb.modelo.core.Receta;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author sam
 */
@ManagedBean(name = "ediRece")
@SessionScoped
public class edicionRecetaJB implements Serializable {

    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private Receta oReceta;
    private DetalleReceta oDetReceta;
    private DetalleMedicamentos[] detalleMeds;

    public edicionRecetaJB() {
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
        oReceta = new Receta();
        oDetReceta = new DetalleReceta();
    }

    public String buscar() throws Exception {
        if (oReceta.getEpisodioMedico().getPaciente().getFolioPaciente() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Folio Paciente", "No Valido"));
            return "editarReceta";
        }
        if (oReceta.getFolioReceta() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Folio Receta", "No Valido"));
            return "editarReceta";
        }
        if (oReceta.buscar()) {
            oDetReceta.setReceta(oReceta);
            detalleMeds = oDetReceta.buscarPorReceta();
            if (detalleMeds == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receta", "Sin Medicamentos"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receta", "No Encontrada"));
        }
        return "editarReceta";
    }

    public void onEdit(RowEditEvent event) throws Exception {
        DetalleMedicamentos oMedEdic = (DetalleMedicamentos) event.getObject();
        if (new DetalleReceta().modificarMedicamento(sUsuario, oReceta.getConsecReceta(), oMedEdic) == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicamento", "Modificado"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No Modificado"));
        }
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((DetalleMedicamentos) event.getObject()).getMedicamento().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Receta getReceta() {
        return oReceta;
    }

    public void limpiar() {
        oReceta = new Receta();
        oDetReceta = new DetalleReceta();
        detalleMeds = new DetalleMedicamentos[0];
    }

    public void setReceta(Receta oReceta) {
        this.oReceta = oReceta;
    }

    public DetalleMedicamentos[] getDetalleMeds() {
        return detalleMeds;
    }

    public void setDetalleMeds(DetalleMedicamentos[] detalleMeds) {
        this.detalleMeds = detalleMeds;
    }

}
