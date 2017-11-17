package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DetalleReceta;
import mx.gob.hrrb.modelo.core.Receta;
import java.util.Calendar;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.ControlMed;

/**
 *
 * @author sam
 */
@ManagedBean(name = "recSurDet")
@SessionScoped
public class RecetasDetalladasJB implements Serializable {

    private Date fechaDia1 = null;
    private PacienteCapasits[] listaXDia = null;
    private ControlMed[] listaControl = null;
    private int totalRecetas = 0;
    private int totalMedicSurtidos = 0;

    /**
     * Creates a new instance of RecetasDetalladasJB
     */
    public RecetasDetalladasJB() throws Exception {
        buscar();
        listaSalidaDetalle();
    }

    public void buscar() throws Exception {
        if (getFechaDia1() == null) {
            setFechaDia1(new Date());
        }
        if (getFechaDia1().after(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La fecha no puede ser futura", " "));
        } 
    }

    public void cargaListaXDia() throws Exception {
        DetalleReceta oDetMet = new DetalleReceta();
        listaXDia = (PacienteCapasits[]) oDetMet.buscaRecetasConDetalle(getFechaDia1());
        if (listaXDia == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se Encontraron Datos", " "));
        }
    }

    public void listaSalidaDetalle() throws Exception {
        ControlMed oCont = new ControlMed();
        listaControl = (ControlMed[]) oCont.buscarXdia(fechaDia1);
        if (listaControl == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se Encontraron Datos", " "));
        }
    }

    public Date getFechaDia1() {
        return fechaDia1;
    }

    public void setFechaDia1(Date fechaDia1) {
        this.fechaDia1 = fechaDia1;
    }

    public PacienteCapasits[] getListaXDia() throws Exception {
        listaXDia = null;
        cargaListaXDia();
        return listaXDia;
    }

    public void setListaXDia(PacienteCapasits[] listaXDia) {
        this.listaXDia = listaXDia;
    }

    public int getDianumero() {
        Calendar dia = Calendar.getInstance();
        dia.setTime(fechaDia1);
        return dia.get(Calendar.DAY_OF_MONTH);
    }

    public String getMes() {
        String mesNombre = "";
        Calendar mes = Calendar.getInstance();
        mes.setTime(fechaDia1);
        int m = mes.get(Calendar.MONTH);
        switch (m) {
            case 0:
                mesNombre = "Enero";
                break;
            case 1:
                mesNombre = "Febrero";
                break;
            case 2:
                mesNombre = "Marzo";
                break;
            case 3:
                mesNombre = "Abril";
                break;
            case 4:
                mesNombre = "Mayo";
                break;
            case 5:
                mesNombre = "Junio";
                break;
            case 6:
                mesNombre = "Julio";
                break;
            case 7:
                mesNombre = "Agosto";
                break;
            case 8:
                mesNombre = "Septiembre";
                break;
            case 9:
                mesNombre = "Octubre";
                break;
            case 10:
                mesNombre = "Noviembre";
                break;
            case 11:
                mesNombre = "Diciembre";
                break;
        }
        return mesNombre;
    }

    public int getAnio() {
        Calendar dia = Calendar.getInstance();
        dia.setTime(fechaDia1);
        return dia.get(Calendar.YEAR);
    }

    public int getTotalRecetas() throws Exception {
        totalRecetas = new Receta().buscaRecetasSurtidasXdia(getFechaDia1());
        return totalRecetas;
    }

    public void setTotalRecetas(int totalRecetas) {
        this.totalRecetas = totalRecetas;
    }

    public int getTotalMedicSurtidos() {
        totalMedicSurtidos = 0;
        for (PacienteCapasits listaXDia1 : listaXDia) {
            totalMedicSurtidos += listaXDia1.getCodigoBarras();
        }
        return totalMedicSurtidos;
    }

    public void setTotalMedicSurtidos(int totalMedicSurtidos) {
        this.totalMedicSurtidos = totalMedicSurtidos;
    }

    public ControlMed[] getListaControl() throws Exception {
        listaSalidaDetalle();
        return listaControl;
    }

    public void setListaControl(ControlMed[] listaControl) {
        this.listaControl = listaControl;
    }
    

}
