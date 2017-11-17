package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.core.DetalleValeMaterial;
import mx.gob.hrrb.modelo.almacen.ValeMaterial;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "oIndividual")
@ViewScoped
public class SurtirValeIndividual implements Serializable{
private DetalleValeMaterial oDetalleValeMaterial=null;
private ValeMaterial oValeMaterial;
private DetalleValeMaterial oDetalleLote=null;
private DetalleValeMaterial seleccionado;
private DetalleValeMaterial seleccionaClave;
private ArrayList<DetalleValeMaterial> listaValePaciente=new ArrayList<DetalleValeMaterial>();
private ArrayList<DetalleValeMaterial> listaMateriales=new ArrayList<DetalleValeMaterial>();
private ArrayList<DetalleValeMaterial> listaLotes=new ArrayList<DetalleValeMaterial>();
private boolean showButton;
private boolean btnLote;
private int total=0;
    
    public SurtirValeIndividual(){
        oDetalleValeMaterial=new DetalleValeMaterial();
        seleccionado=new DetalleValeMaterial();
        seleccionaClave=new DetalleValeMaterial();
        oDetalleLote=new DetalleValeMaterial();
        oValeMaterial=new ValeMaterial();        
    }
    
    public void eventoBuscaFechaPacienteIndividual(ActionEvent ae)throws Exception{
        muestraPacientesIndividual();
    }
    
    public void muestraPacientesIndividual(){
        try{
        listaValePaciente=new ArrayList<DetalleValeMaterial>(Arrays.asList(
                new DetalleValeMaterial().buscarValeIndividual(            
             this.getValeMaterial().getFechaEmision())));   
        }catch(Exception e){
            e.printStackTrace();
        }
    }
      
    public void actualizaPrincipal(){
        try{
            listaValePaciente=new ArrayList<DetalleValeMaterial>(Arrays.asList(new DetalleValeMaterial().buscarValeIndividual(            
                 this.getValeMaterial().getFechaEmision())));
            seleccionado=null;
            seleccionaClave=null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void cancelar(){
        try{
            listaClavesMateriales();   
            seleccionaClave=null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
      
    public void llenaDatosValePacienteIndividual(){
 
        if (seleccionado == null) {
            FacesMessage msj2 = new FacesMessage("¡Alerta!", "Selecciona Un Paciente");
            showButton = true;
            this.getDetalleValeMaterial().getEpisodio().getPaciente().setFolioPaciente(0);
            this.getDetalleValeMaterial().getEpisodio().getPaciente().setNombres("");
            this.getDetalleValeMaterial().getEpisodio().getPaciente().setApPaterno("");
            this.getDetalleValeMaterial().getEpisodio().getPaciente().setApMaterno("");
            this.getDetalleValeMaterial().getEpisodio().getPaciente().getSeg().setNumero("");
            try{
                this.getDetalleValeMaterial().getEpisodio().getPaciente().getExpediente().setNumero(0);
            }catch(Exception e){
                e.printStackTrace();
            }
            this.listaMateriales.clear();
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
        } else if (seleccionado != null) {
            showButton = false;
            oDetalleValeMaterial = seleccionado;
            buscarClavesMateriales();
        }
    }
    
    public void buscarClavesMateriales(){
        for (DetalleValeMaterial dv : listaValePaciente) {
            if (dv.getEpisodio().getPaciente().getFolioPaciente()==seleccionado.getEpisodio().getPaciente().getFolioPaciente()) {
                this.getDetalleValeMaterial().getEpisodio().getPaciente().getFolioPaciente();
                this.getDetalleValeMaterial().getEpisodio().getPaciente().getNombres();
                this.getDetalleValeMaterial().getEpisodio().getPaciente().getApPaterno();
                this.getDetalleValeMaterial().getEpisodio().getPaciente().getApMaterno();
                this.getDetalleValeMaterial().getEpisodio().getPaciente().getSeg().getNumero();
                this.getDetalleValeMaterial().getEpisodio().getPaciente().getExpediente().getNumero();
                listaClavesMateriales();            
            }
        }
    }  
         
    public void listaClavesMateriales(){
        try{
            listaMateriales = (new ArrayList<DetalleValeMaterial>(Arrays.asList
            (new DetalleValeMaterial().buscaMaterialesValeIndividual(
                getValeMaterial().getFechaEmision(), 
                getDetalleValeMaterial().getEpisodio().getPaciente().getFolioPaciente()))));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void llenaDatosMateriales(){
        if (seleccionaClave== null) {
            FacesMessage msj2 = new FacesMessage("¡Alerta!", "Selecciona Un Material");
            btnLote = true;
            this.getDetalleLote().getMaterial().setClaveMaterial("");
            this.getDetalleLote().getMaterial().setNombre("");
            this.getDetalleLote().setCantSolicitada((short)0);             
            this.listaLotes.clear();           
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
        } else if(seleccionaClave!=null){ 
            btnLote= false; 
            oDetalleLote = seleccionaClave;
            buscarClaveLotes(); 
        }
    }
    
    public void buscarClaveLotes(){       
        for (DetalleValeMaterial cv : listaMateriales) {
            if (cv.getMaterial().getClaveMaterial().equals(
                    seleccionaClave.getMaterial().getClaveMaterial())) {
                this.getDetalleLote().getMaterial().getClaveMaterial();
                this.getDetalleLote().getMaterial().getNombre();
                this.getDetalleLote().getCantSolicitada();                
                listaLotesMateriales(cv.getMaterial().getClaveMaterial());
            }
        }
    }
    
    public void listaLotesMateriales(String clave){
        try{
            this.listaLotes = (new ArrayList<DetalleValeMaterial>(Arrays.asList(
                new DetalleValeMaterial().buscaLotesDetalleExistencia(clave))));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void editaExistencia(RowEditEvent event){
        for (DetalleValeMaterial m : listaLotes) {
            if (((DetalleValeMaterial) event.getObject()).getCantSurtida() > 
                    m.getInv().getExistencia()
                    && m.getInv().getLote().equals(((DetalleValeMaterial) 
                    event.getObject()).getInv().getLote())) {
                FacesMessage msg = new FacesMessage("Error", 
                    "Insuficiente en el inventario: " + m.getInv().getExistencia());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeMaterial) event.getObject()).setCantSurtida((short)0);
            } else if (this.getTotal() > this.oDetalleLote.getCantSolicitada()) {
                FacesMessage msg = new FacesMessage("Error", 
                    "No puede surtirse más de lo solicitado " + 
                            this.oDetalleLote.getCantSolicitada());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                ((DetalleValeMaterial)event.getObject()).setCantSurtida((short)0);
            }
        }
    }
    
    public void onRowCancel(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Edicion Cancelada", 
                "Material  " +this.oDetalleLote.getMaterial().getNombre()+
                " Lote:  "+((DetalleValeMaterial) event.getObject()).getInv().getLote());
        ((DetalleValeMaterial) event.getObject()).setCantSurtida((short)0); 
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void eventoGuardar(ActionEvent ae) {
    DetalleValeMaterial od=new DetalleValeMaterial(); 
        try{
            if(listaLotes.size()<=0){
                od.insertarCantidadSurtidaPacienteIndividual( 
                    this.getDetalleValeMaterial().getEpisodio().getPaciente().getFolioPaciente(),
                    this.oDetalleLote.getMaterial().getClaveMaterial(),
                    this.oDetalleLote.getInv().getLote(),
                    this.getTotal(),this.getTotal());       
                    listaClavesMateriales();
           
            }
            if(listaMateriales.size()<=0){   
                this.muestraPacientesIndividual();
                seleccionado=null;            
            } else{
                for (DetalleValeMaterial listaLote : listaLotes) {
                    if (od.insertarCantidadSurtidaPacienteIndividual(
                            this.getDetalleValeMaterial().getEpisodio().getPaciente().getFolioPaciente(), 
                            this.getDetalleLote().getMaterial().getClaveMaterial(), 
                            listaLote.getInv().getLote(), 
                            listaLote.getCantSurtida(), this.getTotal()) != 1) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error ", ""  + " No se inserto"));
                        break;
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Proceso Completo ", " "));            
                listaClavesMateriales();
                if(listaMateriales.size()<=0){            
                    this.muestraPacientesIndividual();
                    seleccionado=null;            
                }        
            } 
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "Error en surtido"));            
        }
    }
    
    public void calculaTotal() {
    int nTotal = 0;
        for (DetalleValeMaterial listaLote : listaLotes) {
            nTotal += listaLote.getCantSurtida();
        }
        setTotal(nTotal);
    }
    
    public String getFechaActual() {
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String a = anio + "";
        String hoy = dia + "/" + (mes + 1) + "/" + a.substring(2, 4); 
        return hoy;
    }
    
    public DetalleValeMaterial getDetalleValeMaterial() {
        return oDetalleValeMaterial;
    }

    public void setDetalleValeMaterial(DetalleValeMaterial oDetalleValeMaterial) {
        this.oDetalleValeMaterial = oDetalleValeMaterial;
    }

    public DetalleValeMaterial getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(DetalleValeMaterial seleccionado) {
        this.seleccionado = seleccionado;
    }

    public DetalleValeMaterial getSeleccionaClave() {
        return seleccionaClave;
    }

    public void setSeleccionaClave(DetalleValeMaterial seleccionaClave) {
        this.seleccionaClave = seleccionaClave;
    }

    public ArrayList<DetalleValeMaterial> getListaValePaciente() {
        return listaValePaciente;
    }

    public void setListaValePaciente(ArrayList<DetalleValeMaterial> listaValePaciente) {
        this.listaValePaciente = listaValePaciente;
    }

    public ArrayList<DetalleValeMaterial> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(ArrayList<DetalleValeMaterial> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public ArrayList<DetalleValeMaterial> getListaLotes() {
        return listaLotes;
    }

    public void setListaLotes(ArrayList<DetalleValeMaterial> listaLotes) {
        this.listaLotes = listaLotes;
    }

    public boolean isShowButton() {
        return showButton;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }

    public boolean isBtnLote() {
        return btnLote;
    }

    public void setBtnLote(boolean btnLote) {
        this.btnLote = btnLote;
    }

    public int getTotal() {
        calculaTotal();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public DetalleValeMaterial getDetalleLote() {
        return oDetalleLote;
    }

    public void setDetalleLote(DetalleValeMaterial oDetalleLote) {
        this.oDetalleLote = oDetalleLote;
    }

    public ValeMaterial getValeMaterial() {
        return oValeMaterial;
    }

    public void setValeMaterial(ValeMaterial oValeMaterial) {
        this.oValeMaterial = oValeMaterial;
    }
}
