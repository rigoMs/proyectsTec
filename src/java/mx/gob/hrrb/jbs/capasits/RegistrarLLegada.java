package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Proveedor;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author sam
 */
@ManagedBean(name = "oLLegada")
@SessionScoped
public class RegistrarLLegada implements Serializable {

    private String nCveMed = "";
    private int nCodBarras = 0;
    private Medicamento oMed;
    private boolean encontrado, alMenosUnProveedor = false;
    private ArrayList<DetalleMedicamentos> lisMed = new ArrayList<DetalleMedicamentos>();
    private DetalleMedicamentos oDetMed, medAux;
    private String sClaveProveedor;
    private String sPresentacion;
    private DetalleMedicamentos[] arrDetMed = null;
    private String sNomProveedor = null;
    private float total = 0;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;

    public RegistrarLLegada() {
        oDetMed = new DetalleMedicamentos();
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public String getNombre() throws Exception {
        alMenosUnProveedor = false;
        oMed = new Medicamento();
        String sNom = "";
        if (getCodBarras() > 0) {
            oMed.setCodBarras(getCodBarras());
            if (oMed.buscar()) {
                sNom += oMed.getClaveMedicamento() + " - ";
                sNom += oMed.getNombre();
                setCveMed(oMed.getClaveMedicamento());
                encontrado = true;
            } else {
                encontrado = false;
                setCodBarras(0);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Código", "No existe"));
            }
        }
        return sNom;
    }

    public List<SelectItem> getProveedores() throws Exception {
        List<SelectItem> sProv = new ArrayList<SelectItem>();
        Proveedor proveedores[] = null;
        proveedores = (new Proveedor()).buscarProvedoresDeMedicamento(nCveMed);
        if (proveedores != null) {
            alMenosUnProveedor = true;
            setClaveProveedor(proveedores[0].getId());
            for (Proveedor n : proveedores) {
                sProv.add(new SelectItem(n.getId(), n.getNombre()));
            }
        } else {
            proveedores = (new Proveedor()).buscarTodos();
            for (Proveedor n : proveedores) {
                sProv.add(new SelectItem(n.getId(), n.getNombre()));
            }
        }
        return sProv;
    }

    public List<SelectItem> getPresentaciones() throws Exception {
        List<SelectItem> sPresentaciones = new ArrayList<SelectItem>();
        Medicamento medicamentos[] = null;
        medicamentos = (new Medicamento()).buscarPresentaciones(getClaveProveedor(), getCveMed());
        for (Medicamento n : medicamentos) {
            sPresentaciones.add(new SelectItem(n.getPresentacion(), n.getPresentacion()));
        }
        return sPresentaciones;
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

    public boolean getEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public String setLista() throws Exception {
        if ( oDetMed.getPrecio() < 0) {           
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El precio no puede ser menor a 0 ", " "));
        return "recibirMedicamento";
        }
        if (oDetMed.getExistencia() == 0 || oDetMed.getExistencia() < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cantidad no puede ser 0 o menor", " "));
         return "recibirMedicamento";
        }
        if (oDetMed.getCaducidad().before(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La caducidad no puede ser atrazada", " "));
         return "recibirMedicamento";
        } else {
            Medicamento m = new Medicamento(getPresentacion());
            oDetMed.getMedicamento().setClaveMedicamento(oMed.getClaveMedicamento());
            oDetMed.getMedicamento().setNombre(oMed.getNombre());
            oDetMed.getMedicamento().setCodBarras(oMed.getCodBarras());
            oDetMed.getMedicamento().setDescripcion(oMed.getDescripcion());
            if (alMenosUnProveedor) {
                oDetMed.getMedicamento().setPresentacion(m.getPresentacion());
            } else {
                oDetMed.getMedicamento().setPresentacion(oMed.getPresentacion());
            }
            Proveedor aux = new Proveedor();
            aux.setId(getClaveProveedor());
            aux.buscar();
            oDetMed.setProveedor(aux);
            lisMed.add(0, oDetMed);

            oMed = new Medicamento();
            oDetMed = new DetalleMedicamentos();
            encontrado = false;
            nCveMed = "";
            nCodBarras = 0;
            sClaveProveedor = "";
            forzarEncontrado();
        }
        return null;
    }

    public boolean getAlmenosUno() {
        return lisMed.size() > 0;
    }

    public void forzarEncontrado() {
        oMed = new Medicamento();
        oDetMed = new DetalleMedicamentos();
        nCveMed = "";
        nCodBarras = 0;
        sClaveProveedor = "";
        encontrado = false;
    }

    public void medicamentoSeleccionado(DetalleMedicamentos m) {
        medAux = m;
    }

    public String remueveMedicamento(int i) {
        if (i == 1) {
            lisMed.remove(medAux);
        } else {
            medAux = null;
        }
        return "";
    }

    public String guarda() throws Exception {
        for (DetalleMedicamentos lisMed1 : lisMed) {
            if (almacena(lisMed1) != 1) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                "No se insertó", "" + 
                               lisMed1.getMedicamento().getCodBarras() + ""));
            }
        }
        oMed = null;
        encontrado = false;
        nCveMed = "";
        lisMed = new ArrayList<DetalleMedicamentos>();
        nCodBarras = 0;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lista insertada", " "));
        return "Inventario";
    }

    public int almacena(DetalleMedicamentos med) throws Exception {
        int nAfec = 0;
        nAfec = med.insertar(sUsuario);
        med = null;
        return nAfec;
    }

    public ArrayList<DetalleMedicamentos> getLista() {
        return lisMed;
    }

    public String getCveMed() {
        return nCveMed;
    }

    public void setCveMed(String nCveMed) {
        this.nCveMed = nCveMed;
    }

    public DetalleMedicamentos getDetMed() {
        return oDetMed;
    }

    public void setDetMed(DetalleMedicamentos oDetMed) {
        this.oDetMed = oDetMed;
    }

    public void limpia() {
        lisMed.clear();
    }

    public void calculaTotal() {
    float tot = 0;
        for (DetalleMedicamentos lisMed1 : lisMed) {
            tot += lisMed1.getSubTotal();
        }
        setTotal(tot);
    }

    public float getTotal() {
        calculaTotal();
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void onRowEdit(RowEditEvent event) {
    FacesMessage msg = new FacesMessage("Midicamento Editado", "Clave : " + 
       ((DetalleMedicamentos) event.getObject()).getMedicamento().getClaveMedicamento());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
    FacesMessage msg = new FacesMessage("Edicion Cancelada", "Clave : " + 
            ((DetalleMedicamentos) event.getObject()).getMedicamento().getClaveMedicamento());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getClaveProveedor() {
        return sClaveProveedor;
    }

    public void setClaveProveedor(String sNombreProveedor) {
        this.sClaveProveedor = sNombreProveedor;
    }

    public String getPresentacion() {
        return sPresentacion;
    }

    public void setPresentacion(String sPresentacion) {
        this.sPresentacion = sPresentacion;
    }

    public boolean getAlmenorUnProveedor() {
        return alMenosUnProveedor;
    }

    public String getNomProveedor() {
        return sNomProveedor;
    }

    public void setNomProveedor(String sNomProveedor) {
        this.sNomProveedor = sNomProveedor;
    }
}