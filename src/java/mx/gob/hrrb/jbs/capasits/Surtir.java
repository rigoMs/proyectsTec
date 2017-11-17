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
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.ControlMed;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.DetalleReceta;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Receta;

/**
 *
 * @author sam
 */
@ManagedBean(name = "surtir")
@SessionScoped
public class Surtir implements Serializable {
    private String codigoBarras = "";
    private DetalleMedicamentos oDetMed = null;
    private ArrayList<DetalleMedicamentos> listaDetalleMed = new ArrayList<DetalleMedicamentos>();
    private boolean masDeUnLote = false;
    private DetalleMedicamentos[] listaLotes, medicamentos, medBusNom = null;
    private String nombreBusqueda = "";
    private boolean medEncontrado = false;
    private String tipoMoviemiento = "";
    private PacienteCapasits oPacienteCapa = null;
    private long idNacional = 0;
    private Receta oReceta = null;
    private boolean nombreEncontrado = false;
    private Date dFecha = null;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;

    public Surtir() {
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
        oPacienteCapa = new PacienteCapasits();
        oReceta = new Receta();
        dFecha = new Date();
    }

    public void buscarMedicamento() throws Exception {
        setMedEncontrado(false);
        medicamentos = null;
        if (!getCodigoBarras().equals("")) {
            if (esEntero(getCodigoBarras())) {
                DetalleMedicamentos DetMedAux = new DetalleMedicamentos();
                DetMedAux.getMedicamento().setCodBarras(Integer.parseInt(getCodigoBarras()));
                medicamentos = DetMedAux.buscaCodigoBarras();
                if (medicamentos.length != 0) {
                    setMasDeUnLote(true);
                    if (medicamentos.length == 1) {//solo un lote
                        anexaLista(medicamentos[0]);
                        medicamentos = null;
                        setMasDeUnLote(false);
                    } else {//mas de un lote
                        setMasDeUnLote(true);
                        listaLotes = medicamentos;
                    }
                } else {//no hay ninguno
                    // System.out.println("no hay ninguno");
                }
            } else {
                setNombreBusqueda(getCodigoBarras());
                muestraCoincidencias();
            }
        } else {
            setCodigoBarras("");
        }
    }

    public void buscarMedicamentoNombre() throws Exception {
        medBusNom = null;
        if (!nombreBusqueda.equals("")) {
            medBusNom = (new DetalleMedicamentos()).buscaXnombre(nombreBusqueda);
            if (medBusNom.length != 0) {
                setCodigoBarras("");
            } else {//no hay ninguno
            }
        }
        nombreBusqueda = "";
    }

    public void anexaLista(DetalleMedicamentos med) throws Exception {
        med.setExistencia(1);
        if (getAlmenosUnoEnLista()) {
            for (DetalleMedicamentos listaDetalleMed1 : listaDetalleMed) {
                if (listaDetalleMed1.getMedicamento().getClaveMedicamento().equals(med.getMedicamento().getClaveMedicamento())) {
                    if (listaDetalleMed1.getLote().equals(med.getLote()) && listaDetalleMed1.getMedicamento().getPresentacion().equals(med.getMedicamento().getPresentacion())) {
                        int exisTem = listaDetalleMed1.getExistencia();
                        listaDetalleMed1.setExistencia(exisTem + 1);
                        return;
                    }
                }
            }
            listaDetalleMed.add(0, med);
        } else {
            listaDetalleMed.add(0, med);
        }
        nombreBusqueda = "";
        codigoBarras = "";
    }

    public String guardarSurtido() throws Exception {
        if (oReceta.getFolioReceta() <= 0 || oReceta == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingrese Folio Receta ", " "));
        }
        if (oPacienteCapa.getIdNacional() == 0 || oPacienteCapa == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingrese Còdigo ", " "));
        } else {
            long serialReceta = 0;
            EpisodioMedico episodioMed;
            long folioPac = oPacienteCapa.getFolioPaciente();

            episodioMed = new EpisodioMedico();
            episodioMed.setClaveEpisodio(episodioMed.buscaEpisodioPacienteCapa(folioPac));

            oReceta.getEpisodioMedico().getPaciente().setFolioPaciente(folioPac);
            oReceta.setEpisodioMedico(episodioMed);
            serialReceta = oReceta.insertar(sUsuario);

            DetalleReceta oDetRec = new DetalleReceta();
            oDetRec.setReceta(oReceta);
            oDetRec.setListaDetalleMed(listaDetalleMed);
            oDetRec.setFechaSurtido(getFecha());

            if (oDetRec.insertar(sUsuario)) {
                setCodigoBarras("");
                listaDetalleMed.clear();
                setIdNacional(0);
                oReceta = new Receta();
                oPacienteCapa = new PacienteCapasits();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receta Surtida ", " "));
                return "surtirReceta.xhtml";
            } else {
                setCodigoBarras("");
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error ", " Verifique Datos "));
        return "";
    }

    public String guardarSalida() throws Exception { //salida sin receta
        for (DetalleMedicamentos listaDetalleMed1 : listaDetalleMed) {
            if (new ControlMed().insertar(sUsuario, listaDetalleMed1.getMedicamento().getClaveMedicamento(), listaDetalleMed1.getMedicamento().getPresentacion(), listaDetalleMed1.getLote(), getTipoMoviemiento(), listaDetalleMed1.getExistencia(), listaDetalleMed1.getMedicamento().getCodBarras()) != 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error ", "" + listaDetalleMed1.getMedicamento().getClaveMedicamento() + " No se inserto"));
                break;
            }
        }
        setCodigoBarras("");
        listaDetalleMed.clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Proceso Completo ", " "));
        return "salidaMedicamento";
    }

    public void medicamentoSeleccionado(DetalleMedicamentos seleccion) throws Exception {
        anexaLista(seleccion);
        setMasDeUnLote(false);
        setNombreBusqueda("");
        listaLotes = null;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        setNombreBusqueda(codigoBarras);
        this.codigoBarras = codigoBarras;
    }

    public DetalleMedicamentos getDetMed() {
        return oDetMed;
    }

    public void setDetMed(DetalleMedicamentos oDetMed) {
        this.oDetMed = oDetMed;
    }

    public boolean isMasDeUnLote() {
        return masDeUnLote;
    }

    public void setMasDeUnLote(boolean masDeUnLote) {
        this.masDeUnLote = masDeUnLote;
    }

    public ArrayList<DetalleMedicamentos> getListaDetalleMed() {
        return listaDetalleMed;
    }

    public void setListaDetalleMed(ArrayList<DetalleMedicamentos> listaDetalleMed) {
        this.listaDetalleMed = listaDetalleMed;
    }

    public DetalleMedicamentos[] getListaLotes() {
        return listaLotes;
    }

    public void setListaLotes(DetalleMedicamentos[] listaLotes) {
        this.listaLotes = listaLotes;
    }

    public void cambiaEstado() {
        setNombreBusqueda("");
        setMasDeUnLote(false);
    }

    public void limpia() {
        listaDetalleMed.clear();
    }

    public boolean getAlmenosUnoEnLista() {
        return listaDetalleMed.size() > 0;
    }

    public String getNombreBusqueda() {
        return nombreBusqueda;
    }

    public void setNombreBusqueda(String nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }

    public void borraNom() throws Exception {
        nombreEncontrado = false;
        this.nombreBusqueda = "&";
        medBusNom = (new DetalleMedicamentos()).buscaXnombre(nombreBusqueda);
        this.nombreBusqueda = "";
    }

    public boolean getMedEncontrado() {
        return medEncontrado;
    }

    public void setMedEncontrado(boolean medEncontrado) {
        this.medEncontrado = medEncontrado;
    }

    public DetalleMedicamentos[] getMedBusNom() throws Exception {
        buscarMedicamentoNombre();
        setMedEncontrado(false);
        codigoBarras = "";
        return medBusNom;
    }

    public void setMedBusNom(DetalleMedicamentos[] medBusNom) {
        this.medBusNom = medBusNom;
    }

    public Date getFecha() {
        return this.dFecha;
    }

    public void setFecha(Date fecha) {
        this.dFecha = fecha;
    }

    public String getTipoMoviemiento() {
        return tipoMoviemiento;
    }

    public void setTipoMoviemiento(String tipoMoviemiento) {
        this.tipoMoviemiento = tipoMoviemiento;
    }

    public List<SelectItem> getParametrizaciones() throws Exception {
        List<SelectItem> sMot = new ArrayList<SelectItem>();
        Parametrizacion parametrizaciones[] = null;

        parametrizaciones = (new Parametrizacion()).buscarTipoMovimientoMedicamento();
        if (parametrizaciones != null) {
            for (Parametrizacion n : parametrizaciones) {
                sMot.add(new SelectItem(n.getClaveParametro(), n.getValor()));
            }
        } else {
            parametrizaciones = (new Parametrizacion()).buscarTodos();
            for (Parametrizacion n : parametrizaciones) {
                sMot.add(new SelectItem(n.getClaveParametro(), n.getValor()));
            }
        }
        return sMot;
    }

    public void buscarPaciente() throws Exception {
        if (getFecha().after(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La fecha no puede ser futura", " "));
        } else {
            if (idNacional != 0) {
                if (getReceta().getFolioReceta() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Insertar el Folio de Receta ", " "));
                } else {
                    oPacienteCapa = new PacienteCapasits();
                    oPacienteCapa.setIdNacional(idNacional);
                    if (!oPacienteCapa.buscarIdNacionalCapa()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Paciente no encontrado ", " "));
                        oPacienteCapa = null;
                    }
                }
            }
        }
    }

    public long getIdNacional() {
        return idNacional;
    }

    public void setIdNacional(long idNacional) {
        this.idNacional = idNacional;
    }

    public PacienteCapasits getPacienteCapa() {
        return oPacienteCapa;
    }

    public void setPacienteCapa(PacienteCapasits oPacienteCapa) {
        this.oPacienteCapa = oPacienteCapa;
    }

    public Receta getReceta() {
        return oReceta;
    }

    public void setReceta(Receta oReceta) {
        this.oReceta = oReceta;
    }

    public boolean getNombreEncontrado() {
        return nombreEncontrado;
    }

    public void setNombreEncontrado(boolean nombreEncontrado) {
        this.nombreEncontrado = nombreEncontrado;
    }

    public void muestraCoincidencias() {
        nombreEncontrado = true;
    }

    public boolean esEntero(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
