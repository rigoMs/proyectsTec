package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.core.DetalleValeColectivo;
import mx.gob.hrrb.modelo.almacen.ValeColectivo;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Quintero
 */
@ManagedBean (name = "oSolicitudesCEYE")
@ViewScoped
public class ConsultaSolicitudesCEYE implements Serializable{
    private ValeColectivo oVale = new ValeColectivo();
    private DetalleValeColectivo oDetalleVale = new DetalleValeColectivo();
    private ArrayList<ValeColectivo> arrVales = null;
    private String sVisibilidad = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false; 
    private DetalleValeColectivo oSeleccionadoVale;
    private ValeColectivo oSeleccionado;
    private String sFechaHoy = "";
    private Date dFechaSol;
    
    
    public ConsultaSolicitudesCEYE(){
        oVale = new ValeColectivo();
        oDetalleVale = new DetalleValeColectivo();
    }
    
    public void inicializar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("oSolicitudes", new ValeColectivo());
    }
    
    public void buscaSolicitudes(){
        sVisibilidad = "visible";
        try{
            arrVales = new ArrayList<ValeColectivo>(Arrays.asList(oVale.buscarSolicitudesMaterialCEYE())) ;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public ArrayList<ValeColectivo> getListaSolicitudesCEYE (){
        return getArrVales();
    }

    public String getVisible(){
        if(this.bBuscado)
            Visible = "visible";
        else 
            Visible = "hidden";
        return Visible;
    }
    
    public void setSeleccionado(ValeColectivo valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("valeSelec", valor);
    }
   
    
    public DetalleValeColectivo getSeleccionadoDetalle(){
        return new DetalleValeColectivo();
    }
    
    
    public void llenaDatos(){
         ValeColectivo oVal = null;
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
         oVal = (ValeColectivo)session.getAttribute("valeSelec");
         if(oVal == null){
            FacesMessage msj2 = new FacesMessage("¡Alerta!","Selecciona Un Área");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bBuscado = false;
        }else{
            oVale = oVal;
            bBuscado = true;
        }
    }
    
    public void editaCantidad(CellEditEvent event){
        String sMsg = "";
        sMsg = "Cantidad Surtida = "+ event.getNewValue();
        FacesMessage msg = new FacesMessage("Editado", sMsg);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
   
    public void almacena(){
        int i = 0;
        FacesMessage msg;
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date fecAct = fecha.parse(sFechaHoy);
            oVale.setFechaSurtido(fecAct);
            i = (oVale.modificarConjunto(arrVales.iterator().next().getArrDetalle()));
            if(i>0)
                msg = new FacesMessage("Almacenamiento","Se actualizo con éxito");
            else 
                msg = new FacesMessage("Almacenamiento","No se registraron los datos");
        }catch(Exception e){
            msg = new FacesMessage("Error", "Hubo un Error en la base de Datos");
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public ValeColectivo getVale() { return oVale; }
    public void setVale(ValeColectivo oVale) { this.oVale = oVale; }
    public String getVisibilidad() { return sVisibilidad; }
    public void setVisibilidad(String sVisibilidad) { this.sVisibilidad = sVisibilidad; }
    public DetalleValeColectivo getDetalleVale() { return oDetalleVale; }
    public void setDetalleVale(DetalleValeColectivo oDetalleVale) { this.oDetalleVale = oDetalleVale; }

    public ArrayList<ValeColectivo> getArrVales() {
        return arrVales;
    }

    public void setArrVales(ArrayList<ValeColectivo> arrVales) {
        this.arrVales = arrVales;
    }

    public DetalleValeColectivo getSeleccionadoVale() {
        return oSeleccionadoVale;
    }

    public void setSeleccionadoVale(DetalleValeColectivo oSeleccionadoVale) {
        this.oSeleccionadoVale = oSeleccionadoVale;
    }

    public ValeColectivo getSeleccionado() {
        return oSeleccionado;
    }
    
    public String getFechaHoy() { 
        SimpleDateFormat hAc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date actual = new Date();
        sFechaHoy = hAc.format(actual);
        return sFechaHoy; 
    }
    
    public void setFechaHoy(String sFechaHoy) {
        this.sFechaHoy = sFechaHoy;
    }

    public Date getFechaSol() {
        return dFechaSol;
    }

    public void setFechaSol(Date dFechaSol) {
        this.dFechaSol = dFechaSol;
    }
    
}
