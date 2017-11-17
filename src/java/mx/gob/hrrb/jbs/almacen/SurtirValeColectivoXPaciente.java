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
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author GIL
 */
@ManagedBean(name = "oColectivo")
@ViewScoped
public class SurtirValeColectivoXPaciente implements Serializable {
private ArrayList<InventarioMateriales> listaDetalleInv = new ArrayList<InventarioMateriales>();
private InventarioMateriales oInventario = null;
private DetalleValeColectivo oDetVale = null;
private DetalleValeColectivo oDetLote=null;
private ValeColectivo vaColectivo;
private DetalleValeColectivo selecionado;
private DetalleValeColectivo selecionaClave;
private boolean showButton;
private boolean btnLote;    
private Material oMat;
private int total = 0;     
private String tipoArea;
private AreaServicioHRRB area;
private ArrayList<DetalleValeColectivo> listaValePaciente = new ArrayList<DetalleValeColectivo>();
private ArrayList<ValeColectivo> listaValeColectivoPaciente = new ArrayList<ValeColectivo>();
private List<DetalleValeColectivo> listaLotes= new ArrayList<DetalleValeColectivo>();
private List<DetalleValeColectivo> listaMateriales= new ArrayList<DetalleValeColectivo>();

    public SurtirValeColectivoXPaciente() {
        oDetVale = new DetalleValeColectivo();
        oDetLote=new DetalleValeColectivo();
        area = new AreaServicioHRRB();
        vaColectivo = new ValeColectivo();
        oInventario = new InventarioMateriales();
        oMat = new Material();
    }

    public void rebotes(ActionEvent ae){
        listaDetalleColectivoAreasLista();
    }

    public List<AreaServicioHRRB> getAreaServicios(){
    List<AreaServicioHRRB> sArea = new ArrayList<AreaServicioHRRB>();
        try {
            sArea = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                    new AreaServicioHRRB().buscarAreaServicioHRRB()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sArea;
    }
    
    public void actualizaPrincipal(){
        try{
            listaValePaciente=new ArrayList<DetalleValeColectivo>(Arrays.asList(
                    new DetalleValeColectivo().buscarValePorPaciente(
                            this.getArea().getClave(), 
                            this.getVaColectivo().getFechaEmision())));
            selecionado=null;
            selecionaClave=null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void listaDetalleColectivoAreasLista(){
        try{
            listaValePaciente=new ArrayList<DetalleValeColectivo>
            (Arrays.asList(new DetalleValeColectivo().buscarValePorPaciente(
            this.getArea().getClave(), this.getVaColectivo().getFechaEmision())));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void llenaDatosValeColectivo(){
        if (selecionado== null) {
            FacesMessage msj2 = new FacesMessage("¡Alerta!", "Selecciona Un Paciente");
            showButton = true;
            this.getDetVale().getEpisodio().getPaciente().setFolioPaciente(0);
            this.getDetVale().getEpisodio().getPaciente().setNombres("");
            this.getDetVale().getEpisodio().getPaciente().setApPaterno("");
            this.getDetVale().getEpisodio().getPaciente().setApMaterno("");
            this.getDetVale().getEpisodio().getPaciente().getSeg().setNumero("");
            try{
                this.getDetVale().getEpisodio().getPaciente().getExpediente().setNumero(0);
            }catch(Exception e){
                e.printStackTrace();
            }
            this.listaMateriales.clear();
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
        } else if(selecionado!=null){ 
            showButton= false;
            oDetVale = selecionado;
            buscarClavesMateriales();   
        }
    }
    
    public void buscarClavesMateriales(){
        for (DetalleValeColectivo dv : listaValePaciente) {
            if (dv.getEpisodio().getPaciente().getFolioPaciente()==
                selecionado.getEpisodio().getPaciente().getFolioPaciente()) {
                    this.getDetVale().getEpisodio().getPaciente().getFolioPaciente();
                    this.getDetVale().getEpisodio().getPaciente().getNombres();
                    this.getDetVale().getEpisodio().getPaciente().getApPaterno();
                    this.getDetVale().getEpisodio().getPaciente().getApMaterno();
                    this.getDetVale().getEpisodio().getPaciente().getSeg().getNumero();
                    this.getDetVale().getEpisodio().getPaciente().getExpediente().getNumero();
                    listaClavesMateriales(this.getVaColectivo().getFechaEmision(),
                         this.getArea().getClave(),
                         this.getDetVale().getEpisodio().getPaciente().getFolioPaciente());
            }
        }
    }

    public void listaClavesMateriales(Date fecha,int clave,long folio){
        try{
            this.listaMateriales = (new ArrayList<DetalleValeColectivo>(
                    Arrays.asList(
                    new DetalleValeColectivo().buscaMaterialesValePaciente(
                            fecha,clave,folio))));
        }catch(Exception e){
            e.printStackTrace();
        }    
    }
     
    public void llenaDatosMateriales(){
    short nCant = 0;
        if (selecionaClave== null) {
            FacesMessage msj2 = new FacesMessage("¡Alerta!", "Selecciona Un Material");
            btnLote = true;
            this.getDetLote().getMaterial().setClaveMaterial("");
            this.getDetLote().getMaterial().setNombre("");
            this.getDetLote().setCantSolicitada(nCant);             
            this.listaLotes.clear();
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
        } else if(selecionaClave!=null){ 
            btnLote= false; 
            oDetLote = selecionaClave;
            buscarClaveLotes();  
        }
    }
    
    public void buscarClaveLotes(){       
        for (DetalleValeColectivo cv : listaMateriales) {
            if (cv.getMaterial().getClaveMaterial().equals(
                    selecionaClave.getMaterial().getClaveMaterial())) {
                this.getDetLote().getMaterial().getClaveMaterial();
                this.getDetLote().getMaterial().getNombre();
                this.getDetLote().getCantSolicitada();                
                listaLotesMateriales(cv.getMaterial().getClaveMaterial());
            }
        }
    }
       
    public void listaLotesMateriales(String clave){
        try{
            this.listaLotes = (new ArrayList<DetalleValeColectivo>(
                Arrays.asList(new 
                DetalleValeColectivo().buscaLotesDetalleExistencia(clave))));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void editaExistencia(RowEditEvent event){
        for (DetalleValeColectivo m : listaLotes) {
            if (((DetalleValeColectivo) event.getObject()).getCantSurtida() > 
                    m.getExistencia()
                    && m.getLote().equals(((DetalleValeColectivo) 
                            event.getObject()).getLote())) {
                FacesMessage msg = new FacesMessage("Error", 
                        "Insuficiente en el inventario: " + m.getExistencia());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeColectivo) 
                        event.getObject()).setCantSurtida((short)0);
            } else if (this.getTotal() > this.oDetLote.getCantSolicitada()) {
                FacesMessage msg = new FacesMessage("Error", 
                        "No puede surtirse más de lo solicitado " + 
                                this.oDetLote.getCantSolicitada());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeColectivo) 
                        event.getObject()).setCantSurtida((short)0);
            }
        }
    }
    
    public void onRowCancel(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Edicion Cancelada", 
            "Clave Material: " + ((DetalleValeColectivo) 
                    event.getObject()).getMaterial().getClaveMaterial());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
        
    public void eventoGuardar(ActionEvent ae) {
    DetalleValeColectivo od=new DetalleValeColectivo(); 
        try{
            if(listaLotes.size()<=0){
                od.insertarCantidaSurtidaPaciente(   
                    this.getArea().getClave(),                                      
                    this.getDetVale().getEpisodio().getPaciente().getFolioPaciente(),
                    oDetLote.getMaterial().getClaveMaterial(),
                    oDetLote.getLote(),
                    this.getTotal(),                   
                    this.getTotal()); 
                    listaClavesMateriales(this.getVaColectivo().getFechaEmision(),
                    this.getArea().getClave(),
                    this.getDetVale().getEpisodio().getPaciente().getFolioPaciente());         
            }
            if(listaMateriales.size()<=0){     
                this.listaDetalleColectivoAreasLista();
                selecionado=null;             
            }else{
                for (DetalleValeColectivo listaLote : listaLotes) {
                    if (od.insertarCantidaSurtidaPaciente(
                            this.getArea().getClave(), 
                            this.getDetVale().getEpisodio().getPaciente().getFolioPaciente(), 
                            oDetLote.getMaterial().getClaveMaterial(), 
                            listaLote.getLote(), listaLote.getCantSurtida(), 
                            this.getTotal()) != 1) {
                        FacesContext.getCurrentInstance().addMessage(null, 
                                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        "Error ", " No se surtió"));
                        break;
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Proceso Completo ", " "));            
                listaClavesMateriales(this.getVaColectivo().getFechaEmision(),
                        this.getArea().getClave(),
                        this.getDetVale().getEpisodio().getPaciente().getFolioPaciente());
                if(listaMateriales.size()<=0){
                    this.listaDetalleColectivoAreasLista();
                    selecionado=null; 
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                   "Error", "Error en el surtido"));                    
        }
    }
    
    public void calculaTotal() {
    int nTotal = 0;
        for (DetalleValeColectivo listaLote : listaLotes) {
            nTotal += listaLote.getCantSurtida();
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
    
    public int getTotalEntradas() {  
    int sum = 0;  
        for (DetalleValeColectivo d : listaValePaciente) {  
            sum += d.getCantSolicitada();  
        }  
        return sum;  
    }  
    
    public ArrayList<InventarioMateriales> getListaDetalleInv() {
        return listaDetalleInv;
    }

    public void setListaDetalleInv(ArrayList<InventarioMateriales> listaDetalleInv) {
        this.listaDetalleInv = listaDetalleInv;
    }

    public InventarioMateriales getInventario() {
        return oInventario;
    }

    public void setInventario(InventarioMateriales oInventario) {
        this.oInventario = oInventario;
    }

    
    public ValeColectivo getVaColectivo() {
        return vaColectivo;
    }

    public void setVaColectivo(ValeColectivo vaColectivo) {
        this.vaColectivo = vaColectivo;
    }

    public Material getMat() {
        return oMat;
    }

    public void setMat(Material oMat) {
        this.oMat = oMat;
    }

    public String getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(String tipoArea) {
        this.tipoArea = tipoArea;
    }

    public AreaServicioHRRB getArea() {
        return area;
    }

    public void setArea(AreaServicioHRRB area) {
        this.area = area;
    }
    
    public List<DetalleValeColectivo> getListaLotes() {
        return listaLotes;
    }

    public void setListaLotesInven(List<DetalleValeColectivo> listaLotes) {
        this.listaLotes = listaLotes;
    }

    public DetalleValeColectivo getDetVale() {
        return oDetVale;
    }

    public void setDetVale(DetalleValeColectivo oDetVale) {
        this.oDetVale = oDetVale;
    }

    public ArrayList<DetalleValeColectivo> getListaValePaciente() {
        return listaValePaciente;
    }

    public void setListaValePaciente(ArrayList<DetalleValeColectivo> listaValePaciente) {
        this.listaValePaciente = listaValePaciente;
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

    public ArrayList<ValeColectivo> getListaValeColectivoPaciente() {
        return listaValeColectivoPaciente;
    }

    public void setListaValeColectivoPaciente(ArrayList<ValeColectivo> listaValeColectivoPaciente) {
        this.listaValeColectivoPaciente = listaValeColectivoPaciente;
    }

    public DetalleValeColectivo getSelecionaClave() {
        return selecionaClave;
    }

    public void setSelecionaClave(DetalleValeColectivo selecionaClave) {
        this.selecionaClave = selecionaClave;
    }

    public boolean isBtnLote() {
        return btnLote;
    }

    public void setBtnLote(boolean btnLote) {
        this.btnLote = btnLote;
    }

    public DetalleValeColectivo getDetLote() {
        return oDetLote;
    }

    public void setDetLote(DetalleValeColectivo oDetLote) {
        this.oDetLote = oDetLote;
    }

    public List<DetalleValeColectivo> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<DetalleValeColectivo> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }
}
