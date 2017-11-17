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
@ManagedBean(name = "oCEYE")
@ViewScoped
public class SurtirCEYE implements Serializable {
private DetalleValeColectivo oDetVale = null;
private DetalleValeColectivo selectClave;
private String sVisibilidadTabla = "hidden";
private ArrayList<DetalleValeColectivo> temporal = new ArrayList<DetalleValeColectivo>();
private Material oMat;
private InventarioMateriales oInven = null;
private Date fechaBusca;
private boolean encontrado = false;
private ValeColectivo vaColectivo;
private String cadena;
private DetalleValeColectivo[] arrDetaVale = null;
private InventarioMateriales[] arrInven = null;
private ArrayList<DetalleValeColectivo> existencia = new ArrayList<DetalleValeColectivo>();
private FacesContext faceContext;
private ArrayList<DetalleValeColectivo> arrDetalles;
private ArrayList<DetalleValeColectivo> lista = new ArrayList<DetalleValeColectivo>();
private ArrayList<DetalleValeColectivo> listaLotesInven = new ArrayList<DetalleValeColectivo>();
private List<DetalleValeColectivo> listaCe;
private int total = 0;
private boolean bBuscado = false;
private String Visible = "hidden";
private boolean ocultaRadio = true;
private DetalleValeColectivo sele;
private boolean bShowButton;

    public SurtirCEYE() {
        oDetVale = new DetalleValeColectivo();
        vaColectivo = new ValeColectivo();
        oInven = new InventarioMateriales();
        oMat = new Material();
    }

    public void buscarClaves(){
        for (DetalleValeColectivo dv : lista) {
            if (dv.getMaterial().getClaveMaterial().equals(
                    sele.getMaterial().getClaveMaterial())&& 
                dv.getVale().getIdHoja()==sele.getVale().getIdHoja()) {
                this.getDetVale().getMaterial().getClaveMaterial();
                this.getDetVale().getMaterial().getNombre();
                this.getDetVale().getCantSolicitada();
                listaLotes(getDetVale().getMaterial().getClaveMaterial());
            }
        }
    }
    
    public void validaSeleccionPaciente(){
        if(sele == null){
            setOcultaRadio(true);
        }else if(sele != null){
            setOcultaRadio(false);
        }
    }
    
    public boolean getValidaSeleccion() {
        if (sele== null) {
            ocultaRadio=true; 
        } else 
            if (sele != null) {
                ocultaRadio=false; 
            }
        return ocultaRadio;
    }

    public void llenaDatosProgramacion(){
    short nVal=0;
        if (sele == null) {
            FacesMessage msj2 = new FacesMessage("¡Alerta!", "Selecciona Un material");
            bShowButton = true;
            this.getDetVale().getMaterial().setClaveMaterial("");
            this.getDetVale().getMaterial().setNombre("");
            this.getDetVale().setCantSolicitada(nVal);
            this.listaLotesInven.clear();
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            setbBuscado(false);
        } else if(sele!=null){ 
            bShowButton= false;
            setbBuscado(true);
            oDetVale = sele;
            buscarClaves();  
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
    
    public void eventoGuardar(ActionEvent ae) {
    int nVal=0;
        for (int i = 0; i < listaLotesInven.size(); i++) {
            try{
                nVal =oDetVale.insertarSalidaCEYE(
                    oDetVale.getVale().getIdHoja(), 
                    oDetVale.getMaterial().getClaveMaterial(), 
                    listaLotesInven.get(i).getLote(), 
                    listaLotesInven.get(i).getCantSurtida(), this.getTotal());
                if (nVal != 1) {
                    FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_WARN, 
                            "Error ", "" + 
                            lista.get(i).getMaterial().getClaveMaterial() + 
                            " No se insertó"));
                    break;
                }
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Proceso Completo ", " "));     
                this.verifcaFecha();
                sele=null;
            }catch(Exception e){
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_WARN, 
                            "Error ", "Error al procesar el surtido"));
            }
        }
    }

    public void rebotes(ActionEvent ae){
        verifcaFecha();
    }
    
    public void verifcaFecha(){
        try{
            lista = new ArrayList<DetalleValeColectivo>(Arrays.asList(
                    new DetalleValeColectivo().buscarCeye(
                            vaColectivo.getFechaEmision())));
            //va al metodo para obtener la lista de los materiales
        }catch(Exception e){
            e.printStackTrace();
        }
    }

   
    public List<DetalleValeColectivo> getLotesInventario(String s){
    List<DetalleValeColectivo> oLot = null;
    DetalleValeColectivo oDet = new DetalleValeColectivo();
        try{
            oLot = new ArrayList<DetalleValeColectivo>(Arrays.asList(
                    oDet.buscaLotesDetalle(s)));
        }catch(Exception e){
            e.printStackTrace();
        }
        return oLot;
    }

    public void listaCeye(){
        try{
            setListaCe(Arrays.asList(
                    new DetalleValeColectivo().buscarCeye(
                            vaColectivo.getFechaEmision())));
            for (int i = 0; i < getListaCe().size(); i++) {
                getListaCe().get(i).setListaLotes(
                        getLotesInventario(
                        getListaCe().get(i).getMaterial().getClaveMaterial()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void listaDetalleColectivoConARRA(){
        try{
            lista = new ArrayList<DetalleValeColectivo>(Arrays.asList(
                    new DetalleValeColectivo().buscarCeye(
                            vaColectivo.getFechaEmision())));
            existencia = new ArrayList<DetalleValeColectivo>(Arrays.asList(
                    new DetalleValeColectivo().arrayExistencias(
                            vaColectivo.getFechaEmision())));
            for (DetalleValeColectivo lista1 : lista) {
                lista1.setListaLotes(getLotesInventario(
                        lista1.getMaterial().getClaveMaterial()));
                this.vaColectivo.setFechaEmision(null);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void onRowCancel(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Edicion Cancelada", 
                "Clave Material: " + 
                ((DetalleValeColectivo) 
                        event.getObject()).getMaterial().getClaveMaterial());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void editaExistencia(RowEditEvent event)  {
    short nCant=0;
        for (DetalleValeColectivo m : listaLotesInven) {
            if (((DetalleValeColectivo) event.getObject()).getCantSurtida() > 
                    m.getExistencia()&& 
                    m.getLote().equals(((DetalleValeColectivo) 
                            event.getObject()).getLote())) {
                FacesMessage msg = new FacesMessage("Error", 
                        "Insuficiente en el inventario: " + m.getExistencia());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeColectivo) event.getObject()).setCantSurtida(nCant);
            } else if (this.getTotal() > this.oDetVale.getCantSolicitada()) {
                FacesMessage msg = new FacesMessage("Error", 
                        "No puede surtirse más de lo solicitado: " + 
                                this.oDetVale.getCantSolicitada());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeColectivo) event.getObject()).setCantSurtida(nCant);
            }
        }
    }

    public void onRowEdit(RowEditEvent event){
        for (DetalleValeColectivo m : existencia) {
            if (((DetalleValeColectivo) event.getObject()).getCantSurtida() <= 
                    m.getExistencia()
                    && m.getMaterial().getClaveMaterial().equals(
                            ((DetalleValeColectivo) 
                            event.getObject()).getMaterial().getClaveMaterial())
                    && m.getLote().equals(((DetalleValeColectivo) 
                            event.getObject()).getTiposLotes())) {
                int a1 = m.getExistencia();
                short b1 = ((DetalleValeColectivo) 
                        event.getObject()).getCantSurtida();
                int c1 = ((DetalleValeColectivo) 
                        event.getObject()).getCantAnterior();
                int su1 = c1 + a1;
                m.setExistencia(su1);
                int res1 = su1 - b1;
                m.setExistencia(res1);
                ((DetalleValeColectivo) event.getObject()).setCantAnterior(b1);
            } else if (((DetalleValeColectivo) 
                    event.getObject()).getCantAnterior() <= 
                    (((DetalleValeColectivo)event.getObject()).getCantSurtida())
                    && m.getMaterial().getClaveMaterial().equals((
                        (DetalleValeColectivo) 
                        event.getObject()).getMaterial().getClaveMaterial())
                    && m.getLote().equals(((DetalleValeColectivo) 
                            event.getObject()).getTiposLotes())) {
                int exist = m.getExistencia();
                short cantAnterior = ((DetalleValeColectivo) 
                        event.getObject()).getCantAnterior();
                short cantNueva = ((DetalleValeColectivo) 
                        event.getObject()).getCantSurtida();
                int suma = exist + cantAnterior;
                m.setExistencia(suma);
                int resta1 = m.getExistencia() - cantNueva;
                if (resta1 < 0) {
                    FacesMessage msg = new FacesMessage("Error", 
                            "Insuficiente en el inventario: " + exist);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    ((DetalleValeColectivo) event.getObject()
                            ).setCantSurtida(cantAnterior);
                    m.setExistencia(exist);
                } else {
                    m.setExistencia(resta1);
                    ((DetalleValeColectivo) 
                            event.getObject()).setCantAnterior(cantNueva);
                }
            } else if (((DetalleValeColectivo) 
                    event.getObject()).getCantAnterior() >= 
                    (((DetalleValeColectivo)event.getObject()).getCantSurtida())
                    && m.getMaterial().getClaveMaterial().equals((
                            (DetalleValeColectivo) 
                            event.getObject()).getMaterial().getClaveMaterial())
                    && m.getLote().equals(((DetalleValeColectivo) 
                            event.getObject()).getTiposLotes())) {
                int ex = m.getExistencia();
                int cantAn = ((DetalleValeColectivo) 
                        event.getObject()).getCantAnterior();
                short cantN = ((DetalleValeColectivo) 
                        event.getObject()).getCantSurtida();
                int sum = ex + cantAn;
                m.setExistencia(sum);
                int resta2 = m.getExistencia() - cantN;
                m.setExistencia(resta2);
                ((DetalleValeColectivo) 
                        event.getObject()).setCantAnterior(cantN);
            }
        }
    }

    public boolean getRadio(){
        for (DetalleValeColectivo lista1 : lista) {
            ocultaRadio = lista1.getVale().getIdHoja() == 
                    oDetVale.getVale().getIdHoja();
        }
        return ocultaRadio;
    }

    public boolean getOcultas() {
        return lista.size() > 0;
    }

    public boolean getOculta() {
        return listaCe.size() > 0;
    }

    public void limpiaSalida(ActionEvent ae) {
        lista.clear();
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

    public DetalleValeColectivo getDetVale() {
        return oDetVale;
    }

    public void setDetVale(DetalleValeColectivo oDetVale) {
        this.oDetVale = oDetVale;
    }

    public DetalleValeColectivo[] getArrDetaVale() {
        return arrDetaVale;
    }

    public void setArrDetaVale(DetalleValeColectivo[] arrDetaVale) {
        this.arrDetaVale = arrDetaVale;
    }

    public ValeColectivo getVaColectivo() {
        return vaColectivo;
    }

    public void setVaMaterial(ValeColectivo vaColectivo) {
        this.vaColectivo = vaColectivo;
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

    public InventarioMateriales[] getArrInven() {
        return arrInven;
    }

    public void setArrInven(InventarioMateriales[] arrInven) {
        this.arrInven = arrInven;
    }

    public ArrayList<DetalleValeColectivo> getArrDetalles() {
        return arrDetalles;
    }

    public void setArrDetalles(ArrayList<DetalleValeColectivo> arrDetalles) {
        this.arrDetalles = arrDetalles;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public DetalleValeColectivo getSelectClave() {
        return selectClave;
    }

    public void setSelectClave(DetalleValeColectivo selectClave) {
        this.selectClave = selectClave;
    }

    public ArrayList<DetalleValeColectivo> getLista() {
        return lista;
    }

    public void setLista(ArrayList<DetalleValeColectivo> lista) {
        this.lista = lista;
    }

    public List<DetalleValeColectivo> getListaCe() {
        return listaCe;
    }

    public void setListaCe(List<DetalleValeColectivo> listaCe) {
        this.listaCe = listaCe;
    }

    public void setExistencia(ArrayList<DetalleValeColectivo> existencia) {
        this.existencia = existencia;
    }

    public ArrayList<DetalleValeColectivo> getExistencia() {
        return existencia;
    }

    public boolean isbBuscado() {
        return bBuscado;
    }

    public void setbBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
    }

    public String getVisibilidadTabla() {
        return this.sVisibilidadTabla;
    }

    public ArrayList<DetalleValeColectivo> getListaLotesInven() {
        return listaLotesInven;
    }

    public void setListaLotesInven(ArrayList<DetalleValeColectivo> listaLotesInven) {
        this.listaLotesInven = listaLotesInven;
    }

    public boolean isOcultaRadio() {
        return ocultaRadio;
    }

    public void setOcultaRadio(boolean ocultaRadio) {
        this.ocultaRadio = ocultaRadio;
    }

    public ArrayList<DetalleValeColectivo> getTemporal() {
        return temporal;
    }

    public void setTemporal(ArrayList<DetalleValeColectivo> temporal) {
        this.temporal = temporal;
    }

    public DetalleValeColectivo getSele() {
        return sele;
    }

    public void setSele(DetalleValeColectivo sele) {
        this.sele = sele;
    }

    public boolean getShowButton() {
        return bShowButton;
    }

    public void setShowButton(boolean bShowButton) {
        this.bShowButton = bShowButton;
    }

}
