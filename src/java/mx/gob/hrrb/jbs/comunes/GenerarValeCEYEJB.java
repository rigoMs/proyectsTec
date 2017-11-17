package mx.gob.hrrb.jbs.comunes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.DetalleValeColectivo;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Rafael
 */
@ManagedBean(name = "oGenValeCEYE")
@ViewScoped

public class GenerarValeCEYEJB implements Serializable {

    private AccesoDatos oAD;
    private PersonalHospitalario oPerHosp;
    private AreaServicioHRRB oAreaServ;
    private AreaServicioHRRB arrAreaActual[];
    private DetalleValeColectivo oDetValColect;
    private Material oMaterial;
    private ArrayList<DetalleValeColectivo> lMaterialLista;
    private ArrayList<DetalleValeColectivo> arrValeMatSol;
    private String sVisibilidadRegistroMat = "hidden";
    private Date dFechaActual;
    private Date dFechaAux;
    private boolean bBuscado = false;
    private String sVisibilidadTabla = "hidden";

    public GenerarValeCEYEJB() throws Exception {
        HttpServletRequest req;
        oPerHosp = new PersonalHospitalario();
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
            oPerHosp.getUsuar().setIdUsuario(((Firmado) req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            oPerHosp.buscaUsuarioFirmado();
        }

        oAreaServ = new AreaServicioHRRB();
        oDetValColect = new DetalleValeColectivo();
        oMaterial = new Material();
        dFechaActual = new Date();
        lMaterialLista = new ArrayList<DetalleValeColectivo>();
        arrValeMatSol = new ArrayList<DetalleValeColectivo>();
    }

    public AreaServicioHRRB[] getListaAreas() {
        if (oPerHosp.getUsuar().getCvePerfil().compareTo("HOSPENF")==0 || 
                oPerHosp.getUsuar().getCvePerfil().compareTo("ONCOENF")==0) {
            try {
                arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosHospitalizacion();
            } catch (Exception ex) {
                Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (oPerHosp.getUsuar().getCvePerfil().compareTo("URGENF")==0) {
            try {
                arrAreaActual = (new AreaServicioHRRB()).buscarTodosServiciosUrgencias();
            } catch (Exception ex) {
                Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return arrAreaActual;
    }

    public String getVisible() {
        if (this.bBuscado) {
            return "visible";
        } else {
            return "hidden";
        }
    }
    
    
////////////////////////////////////////////////////////////////////////////////
    
    public void registroMaterial() {
        oDetValColect.setServicioCobrable(oMaterial);
        if (oMaterial.getNombre().equals("") && 
                oDetValColect.getCantSolArea() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Seleccione un material"));
        }else if(oMaterial.getNombre()!=null && 
                oDetValColect.getCantSolArea() != 0){
            
            System.out.println("Area Servicio Bean Admin: "+this.oAreaServ.getClave());
            
            oDetValColect.setAutorizadoPor(oPerHosp);
            oDetValColect.getVale().setAreaAtiende(oAreaServ);
            lMaterialLista.add(this.oDetValColect);
            oMaterial = new Material();
            
            sVisibilidadRegistroMat = "visible";
            System.out.println("Tamaño del arreglo: " + lMaterialLista.size());
        }
    }

    public void borrarElemento(DetalleValeColectivo obj) {
        this.lMaterialLista.remove(obj);
        if (lMaterialLista.isEmpty()) {
            sVisibilidadRegistroMat = "hidden";
        } else {
            sVisibilidadRegistroMat = "visible";
        }
    }

    public void guardarMatCeye() throws Exception {
        try {
            if(oDetValColect.insertaSolMatCEYE(lMaterialLista) == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de CEYE", "Solicitud Guardada Correctamente"));
                limpiarTabla();
            }
        } catch (Exception ex) {
            Logger.getLogger(GenerarValeCEYEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscaClave() throws Exception {
        oMaterial.buscarPorNombre();
    }

    public void limpiarTabla() {
        lMaterialLista.clear();
        sVisibilidadRegistroMat = "hidden";
    }

////////////////////////////////////////////////////////////////////////////////
    public void buscaMaterialSolSur() {
        sVisibilidadTabla = "visible";
        try {
            arrValeMatSol = new ArrayList<DetalleValeColectivo>(
                    Arrays.asList(
                            oDetValColect.buscarMaterialesSolicitadosCEYE(
                                    dFechaAux, oAreaServ.getClave())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////
    public AccesoDatos getAD() {
        return oAD;
    }

    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }

    public AreaServicioHRRB getAreaServ() {
        return oAreaServ;
    }

    public void setAreaServ(AreaServicioHRRB oAreaServ) {
        this.oAreaServ = oAreaServ;
    }

    public Date getFechaActual() {
        return dFechaActual;
    }

    public void setFechaActual(Date dFechaActual) {
        this.dFechaActual = dFechaActual;
    }

    public boolean getBuscado() {
        return bBuscado;
    }

    public void setBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
    }

    public Date getFechaAux() {
        return dFechaAux;
    }

    public void setFechaAux(Date dFechaAux) {
        this.dFechaAux = dFechaAux;
    }

    public Material getMaterial() {
        return oMaterial;
    }

    public void setMaterial(Material oMaterial) {
        this.oMaterial = oMaterial;
    }

    public String getVisibilidadRegistroMat() {
        return sVisibilidadRegistroMat;
    }

    public void setVisibilidadRegistroMat(String sVisibilidadRegistroMat) {
        this.sVisibilidadRegistroMat = sVisibilidadRegistroMat;
    }

    public AreaServicioHRRB[] getAreaActual() {
        return arrAreaActual;
    }

    public void setAreaActual(AreaServicioHRRB[] arrAreaActual) {
        this.arrAreaActual = arrAreaActual;
    }

    public PersonalHospitalario getPerHosp() {
        return oPerHosp;
    }

    public void setPerHosp(PersonalHospitalario oPerHosp) {
        this.oPerHosp = oPerHosp;
    }

    public DetalleValeColectivo getDetValColect() {
        return oDetValColect;
    }

    public void setDetValColect(DetalleValeColectivo oDetValColect) {
        this.oDetValColect = oDetValColect;
    }

    public String getVisibilidadTabla() {
        return sVisibilidadTabla;
    }

    public void setVisibilidadTabla(String sVisibilidadTabla) {
        this.sVisibilidadTabla = sVisibilidadTabla;
    }

    public ArrayList<DetalleValeColectivo> getValeMatSol() {
        return arrValeMatSol;
    }

    public void setValeMatSol(ArrayList<DetalleValeColectivo> arrValeMatSol) {
        this.arrValeMatSol = arrValeMatSol;
    }

    public ArrayList<DetalleValeColectivo> getMaterialLista() {
        return lMaterialLista;
    }

    public void setMaterialLista(ArrayList<DetalleValeColectivo> lMaterialLista) {
        this.lMaterialLista = lMaterialLista;
    }
}
