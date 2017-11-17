package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.core.DetalleValeColectivo;
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.almacen.ValeColectivo;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author GIL
 */
@ManagedBean(name = "oQuirofano")
@ViewScoped
public class SurtirQuirofano implements Serializable {
private DetalleValeColectivo oDetVale;
private Material oMat;
private InventarioMateriales oInven = null;
private Date fechaBusca;
private boolean encontrado = false;
private ArrayList<DetalleValeColectivo> lista = new ArrayList<DetalleValeColectivo>();
private ArrayList<DetalleValeColectivo> listaLotesInven = new ArrayList<DetalleValeColectivo>();
private ValeColectivo vaColectivo;
private DetalleValeColectivo[] arrDetaVale = null;
private ArrayList<DetalleValeColectivo> listaDetalle = new ArrayList<DetalleValeColectivo>();
private FacesContext faceContext;
private DetalleValeColectivo selecion;
private boolean bShowButton;
private int total = 0;

    public SurtirQuirofano() {
        oDetVale = new DetalleValeColectivo();
        vaColectivo = new ValeColectivo();
        oInven = new InventarioMateriales();
        oMat = new Material();
    }

    public List<InventarioMateriales> getLotesInventario(String s){
    List<InventarioMateriales> sLot = new ArrayList<InventarioMateriales>();
        try{
            sLot = new ArrayList<InventarioMateriales>(Arrays.asList(
                    new DetalleValeColectivo().buscaLotesQuirofano(s)));
        }catch(Exception e){
            e.printStackTrace();
        }
        return sLot;
    }

    public void buscarClaves(){
        try{
            for (DetalleValeColectivo dv : lista) {
                if (dv.getMaterial().getClaveMaterial().equals(
                        selecion.getMaterial().getClaveMaterial()) && 
                        dv.getVale().getIdHoja() == selecion.getVale().getIdHoja()){
                    this.getDetVale().getMaterial().getClaveMaterial();
                    this.getDetVale().getMaterial().getNombre();
                    this.getDetVale().getCantSolicitada();
                    listaLotes(getDetVale().getMaterial().getClaveMaterial());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void llenaDatosProgramacion(){
    short nCant=0;
        if (selecion == null) {
            FacesMessage msj2 = new FacesMessage("¡Alerta!", "Selecciona Un material");
            bShowButton = true;
            this.getDetVale().getMaterial().setClaveMaterial("");
            this.getDetVale().getMaterial().setNombre("");
            this.getDetVale().setCantSolicitada(nCant);
            this.listaLotesInven.clear();
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
        } else if (selecion != null) {
            bShowButton = false;
            oDetVale = selecion;
            buscarClaves();
        }
    }

    public void editaExistencia(RowEditEvent event){
        short nCant=0;
        for (DetalleValeColectivo m : listaLotesInven) {
            if (((DetalleValeColectivo) event.getObject()).getCantSurtida() > m.getExistencia()
                    && m.getLote().equals(((DetalleValeColectivo) event.getObject()).getLote())) {
                FacesMessage msg = new FacesMessage("Error", "insuficiente en el inventario: " + m.getExistencia());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeColectivo) event.getObject()).setCantSurtida(nCant);
            } else if (this.getTotal() > this.oDetVale.getCantSolicitada()) {
                FacesMessage msg = new FacesMessage("Error", "No puede surtirse mas delo solicitado " + this.oDetVale.getCantSolicitada());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeColectivo) event.getObject()).setCantSurtida(nCant);
            }
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", 
                "Clave Material: " + ((DetalleValeColectivo) event.getObject()).getMaterial().getClaveMaterial());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void listaLotes(String s){
        try{
        this.listaLotesInven = (new ArrayList<DetalleValeColectivo>(
                Arrays.asList(
                new DetalleValeColectivo().buscaLotesDetalleExistencia(s))));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void eventoGuardar(ActionEvent ae) {
        try{
        for (int i = 0; i < listaLotesInven.size(); i++) {
            if (new DetalleValeColectivo().insertarSalidaQuirofano(
                    oDetVale.getVale().getIdHoja(), 
                    oDetVale.getMaterial().getClaveMaterial(), 
                    listaLotesInven.get(i).getLote(), 
                    listaLotesInven.get(i).getCantSurtida(), this.getTotal()) != 1) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                            "Error ", "" + 
                            lista.get(i).getMaterial().getClaveMaterial() + 
                            "No se insertó"));
                break;
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Proceso Completo ", " "));
        this.verifcaFecha();
        selecion = null;
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Error", "Error al almacenar el surtido"));
        }
    }

    public void rebotes(ActionEvent ae){
        verifcaFecha();
    }

    public void verifcaFecha(){
        try{
            lista = new ArrayList<DetalleValeColectivo>(Arrays.asList(
                    new DetalleValeColectivo().buscarQuirofano(
                            vaColectivo.getFechaEmision())));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void calculaTotal() {
    int nTotal = 0;
        for (int i = 0; i < getListaLotesInven().size(); i++) {
            nTotal += getListaLotesInven().get(i).getCantSurtida();
        }
        setTotal(nTotal);
    }

    public int getTotal() {
        calculaTotal();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean getOculta() {
        return lista.size() > 0;
    }

    public void limpiaSalidad() {
        lista.clear();
    }
    
    public DetalleValeColectivo getDetVale() {
        return oDetVale;
    }

    public void setDetVale(DetalleValeColectivo oDetVale) {
        this.oDetVale = oDetVale;
    }

    public Material getMat() {
        return oMat;
    }

    public void setMat(Material oMat) {
        this.oMat = oMat;
    }

    public InventarioMateriales getInven() {
        return oInven;
    }

    public void setInven(InventarioMateriales oInven) {
        this.oInven = oInven;
    }

    public Date getFechaBusca() {
        return fechaBusca;
    }

    public void setFechaBusca(Date fechaBusca) {
        this.fechaBusca = fechaBusca;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public ValeColectivo getVaColectivo() {
        return vaColectivo;
    }

    public void setVaColectivo(ValeColectivo vaColectivo) {
        this.vaColectivo = vaColectivo;
    }

    public DetalleValeColectivo[] getArrDetaVale() {
        return arrDetaVale;
    }

    public void setArrDetaVale(DetalleValeColectivo[] arrDetaVale) {
        this.arrDetaVale = arrDetaVale;
    }

    public ArrayList<DetalleValeColectivo> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(ArrayList<DetalleValeColectivo> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public ArrayList<DetalleValeColectivo> getLista() {
        return lista;
    }

    public void setLista(ArrayList<DetalleValeColectivo> lista) {
        this.lista = lista;
    }

    public DetalleValeColectivo getSelecion() {
        return selecion;
    }

    public void setSelecion(DetalleValeColectivo selecion) {
        this.selecion = selecion;
    }

    public ArrayList<DetalleValeColectivo> getListaLotesInven() {
        return listaLotesInven;
    }

    public void setListaLotesInven(
            ArrayList<DetalleValeColectivo> listaLotesInven) {
        this.listaLotesInven = listaLotesInven;
    }

    public boolean isShowButton() {
        return bShowButton;
    }

    public void setShowButton(boolean bShowButton) {
        this.bShowButton = bShowButton;
    }
}
