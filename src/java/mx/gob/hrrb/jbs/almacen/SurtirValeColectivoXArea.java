package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.core.DetalleValeColectivo;
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;
import mx.gob.hrrb.modelo.almacen.ValeColectivo;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "ValeColectivoArea")
@ViewScoped
public class SurtirValeColectivoXArea implements Serializable {
private DetalleValeColectivo oDetalleValeColectivo;
private ArrayList<DetalleValeColectivo> listaValeArea = new ArrayList<DetalleValeColectivo>();
private AreaServicioHRRB area;
private InventarioMateriales inventario = null;
private DetalleValeColectivo selecionado;
private boolean showButton;
private ArrayList<DetalleValeColectivo> listaLotesInven = new ArrayList<DetalleValeColectivo>();
private int total = 0;
private ValeColectivo oValeColectivo;

    public SurtirValeColectivoXArea() {
        oValeColectivo =new ValeColectivo();
        oDetalleValeColectivo = new DetalleValeColectivo();
        area = new AreaServicioHRRB();
        inventario = new InventarioMateriales();
        selecionado=new DetalleValeColectivo();
    }

    public void EventoFecha(ActionEvent ae){
        buscaFechaSolicitudes();
    }

    public void buscaFechaSolicitudes(){
        try{
            listaValeArea=new ArrayList<DetalleValeColectivo>(Arrays.asList(
                    new DetalleValeColectivo().buscarValePorArea(
                            this.getArea().getClave(), 
                            this.getValeColectivo().getFechaEmision())));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void actualizaPrincipal(){
        try{
            listaValeArea=new ArrayList<DetalleValeColectivo>(Arrays.asList(
                    new DetalleValeColectivo().buscarValePorArea(
                            this.getArea().getClave(), 
                            this.getValeColectivo().getFechaEmision())));
            selecionado=null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<AreaServicioHRRB> getAreaServicios() throws Exception {
        List<AreaServicioHRRB> sArea = new ArrayList<AreaServicioHRRB>();
        try {
            sArea = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                    new AreaServicioHRRB().buscarAreaServicioHRRB()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sArea;
    }

    public void llenaDatosProgramacion(){
    short nCant=0;
        if (selecionado == null) {
            FacesMessage msj2 = new FacesMessage("¡Alerta!", 
                    "Selecciona Un material");
            setShowButton(true);
            this.getDetalleValeColectivo().getMaterial().setClaveMaterial("");
            this.getDetalleValeColectivo().getMaterial().setNombre("");
            this.getDetalleValeColectivo().setCantSolArea(nCant);
            listaLotesInven.clear();
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
        } else if (selecionado != null) {
            setShowButton(false);
            oDetalleValeColectivo = selecionado;
            buscarClaves();
        }
    }

    public void buscarClaves(){
        for (DetalleValeColectivo dv : listaValeArea) {
            if (dv.getMaterial().getClaveMaterial().equals(
                    selecionado.getMaterial().getClaveMaterial()) && 
                dv.getVale().getIdHoja()== selecionado.getVale().getIdHoja()) {
                this.getDetalleValeColectivo().getMaterial().getClaveMaterial();
                this.getDetalleValeColectivo().getMaterial().getNombre();
                this.getDetalleValeColectivo().getCantSolArea();
                listaLotes(
                    getDetalleValeColectivo().getMaterial().getClaveMaterial());
            }
        }
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

    public void editaExistencia(RowEditEvent event){
    short nCant=0;
        for (DetalleValeColectivo m : listaLotesInven) {
            if (((DetalleValeColectivo) event.getObject()).getCantSurArea()> 
                    m.getExistencia()
                    && m.getLote().equals(((DetalleValeColectivo) 
                            event.getObject()).getLote())) {
                FacesMessage msg = new FacesMessage("Error", 
                        "Insuficiente en el inventario: " + m.getExistencia());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeColectivo)event.getObject()).setCantSurArea(nCant);
            } else if (this.getTotal() > 
                    this.oDetalleValeColectivo.getCantSolicitada()) {
                FacesMessage msg = new FacesMessage("Error", 
                        "No puede surtirse más de lo solicitado " + 
                                this.oDetalleValeColectivo.getCantSolArea());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeColectivo) event.getObject()).setCantSurArea(nCant);
            }
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", 
                "Clave Material: " + 
                ((DetalleValeColectivo) 
                event.getObject()).getMaterial().getClaveMaterial());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    public void eventoGuardar(ActionEvent ae) {
        try{
            if(listaLotesInven.size()<=0){
                DetalleValeColectivo od=new DetalleValeColectivo(); 
                od.surtirValeColectivoPorArea(
                        oDetalleValeColectivo.getVale().getIdHoja(),
                        this.getArea().getClave(),
                        oDetalleValeColectivo.getMaterial().getClaveMaterial(),
                        oDetalleValeColectivo.getLote(),
                        this.getTotal(),
                        this.getTotal()); 
                this.buscaFechaSolicitudes();
                selecionado=null;
            }else{
                for (DetalleValeColectivo listaLotesInven1 : listaLotesInven) {
                    if (new DetalleValeColectivo().surtirValeColectivoPorArea(
                            oDetalleValeColectivo.getVale().getIdHoja(), 
                            this.getArea().getClave(), 
                            oDetalleValeColectivo.getMaterial().getClaveMaterial(), 
                            listaLotesInven1.getLote(), 
                            listaLotesInven1.getCantSurArea(), 
                            this.getTotal()) != 1) {
                        FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_WARN, 
                            "Error ", 
                            oDetalleValeColectivo.getMaterial().getClaveMaterial() + 
                            "No se surtió"));
                        break;
                    }
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Proceso Completo ", " "));     
            this.buscaFechaSolicitudes();
            selecionado=null;
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Error ", "Error durante el surtido"));  
        }
    }


    public void calculaTotal() {
    int nTotal = 0;
        for (int i = 0; i < getListaLotesInven().size(); i++) {
            nTotal += getListaLotesInven().get(i).getCantSurArea();
        }
        setTotal(nTotal);
    }

    public DetalleValeColectivo getDetalleValeColectivo() {
        return oDetalleValeColectivo;
    }

    public void setoDetalleValeColectivo(DetalleValeColectivo oDetalleValeColectivo) {
        this.oDetalleValeColectivo = oDetalleValeColectivo;
    }

    public ArrayList<DetalleValeColectivo> getListaValeArea() {
        return listaValeArea;
    }

    public void setListaValeArea(ArrayList<DetalleValeColectivo> listaValeArea) {
        this.listaValeArea = listaValeArea;
    }

    public AreaServicioHRRB getArea() {
        return area;
    }

    public void setArea(AreaServicioHRRB area) {
        this.area = area;
    }

    public InventarioMateriales getInventario() {
        return inventario;
    }

    public void setInventario(InventarioMateriales inventario) {
        this.inventario = inventario;
    }

    public DetalleValeColectivo getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(DetalleValeColectivo selecionado) {
        this.selecionado = selecionado;
    }

    public boolean isShowButton() {
        return showButton;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }

    public ArrayList<DetalleValeColectivo> getListaLotesInven() {
        return listaLotesInven;
    }

    public void setListaLotesInven(ArrayList<DetalleValeColectivo> listaLotesInven) {
        this.listaLotesInven = listaLotesInven;
    }

    public int getTotal() {
        calculaTotal();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ValeColectivo getValeColectivo() {
        return oValeColectivo;
    }

    public void setValeColectivo(ValeColectivo oValeColectivo) {
        this.oValeColectivo = oValeColectivo;
    }
    public void limpiaSalida() {
        listaValeArea.clear();
    }
}
