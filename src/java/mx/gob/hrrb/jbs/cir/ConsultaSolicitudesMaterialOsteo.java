package mx.gob.hrrb.jbs.cir;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.modelo.almacen.ValeMaterial;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Quintero
 */
@ManagedBean (name="oSolicitudesOsteo")
@ViewScoped

public class ConsultaSolicitudesMaterialOsteo {
    private ProcedimientosRealizados oProcedimientos;
    private ArrayList<ProcedimientosRealizados> arrSolicitudes;
    private String sVisibilidadTabla = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false;
    private Date dFecha;
    private int nSurtidoMat = 0;
    private ValeMaterial oVale;
    private String sFechaHoy = "";
            
    public ConsultaSolicitudesMaterialOsteo(){
        oProcedimientos = new ProcedimientosRealizados();
        oVale = new ValeMaterial();
    }   
    
    public void buscaSolicitudes(int numClv){
        sVisibilidadTabla = "visible";
        try{
            arrSolicitudes = new ArrayList<ProcedimientosRealizados>(
                    Arrays.asList(
                        new ProcedimientosRealizados()
                                .buscaSolicitudesConMaterialDeOsteosintesis(
                                        getFecha(),numClv)));
        }catch(Exception ex){
            Logger.getLogger(ConsultaSolicitudesQuirofanos.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public String getVisible(){
         if(this.bBuscado)
             Visible = "visible";
         else
             Visible = "hidden";
         return Visible;
     }
    
    
    public void setSeleccionado(ProcedimientosRealizados valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec", valor);
    }
    
    public ProcedimientosRealizados getSeleccionado(){
         return new ProcedimientosRealizados();
     }
    
    public ArrayList<ProcedimientosRealizados> getListaSolicitudes(){
        return arrSolicitudes;
    }
    
    /* Llenado de Datos */
    public void llenaDatos(){
        ProcedimientosRealizados oProce = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        oProce = (ProcedimientosRealizados)session.getAttribute("pacienteSelec");
        if(oProce == null){
            FacesMessage msj2 = new FacesMessage("¡Alerta!","Selecciona Un Paciente");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bBuscado = false;
        }else{
            System.out.println("Estamos en la impresion de Datos "+oProce.getEpisodioMedico().getPaciente().getNombreCompleto());
            oProcedimientos = oProce;
            bBuscado = true;
        }
    }
    
    public void cancelaValeOsteosinteis(){
        oVale = new ValeMaterial();
        oVale.setFolioVale(oProcedimientos.getValeMat().getFolioVale());
        oVale.setArrDetalle(oProcedimientos.getValeMat().getArrDetalle());
        FacesMessage msg;
        try{
            if(oVale != null){
                System.out.println("Estamos enviando el arreglo");
                if(oVale.cancelarValeOsteosintesis(oVale.getArrDetalle()) == 1){
                    msg = new FacesMessage("Almacenamiento", "Se Cancelo el Vale con Éxito :D ");
                }else{
                    msg = new FacesMessage("Almacenamiento", "No Se Pudo Cancelar el Vale :( ");
                }
            }else{
                 msg = new FacesMessage("Almacenamiento", "No hay Materiales para este paciente :( ");
                System.out.println("Que tiene Vale" + oVale.getFolioVale());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error al cancelar");
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
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date fecAct = fecha.parse(sFechaHoy);
            oVale.setFechaSurtido(fecAct);
            i = (oVale.modificarConjunto(arrSolicitudes.iterator().next().getValeMat().getArrDetalle()));
            if(i>0)
                msg = new FacesMessage("Almacenamiento",
                        "Se actualizo con éxito :)");
            else 
                msg = new FacesMessage("Almacenamiento",
                        "No se registraron los datos :( ");
        }catch(Exception e){
            msg = new FacesMessage("Catch",
            "Error al afectar la base de datos");
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; } 
    public String getVisibilidadTabla() { return this.sVisibilidadTabla; }
    public Date getFecha() { return dFecha; }
    public void setFecha(Date dFecha) { this.dFecha = dFecha; }

    public int getSurtidoMat() {
        return nSurtidoMat;
    }

    public void setSurtidoMat(int nSurtidoMat) {
        this.nSurtidoMat = nSurtidoMat;
    }

    public ValeMaterial getVale() {
        return oVale;
    }

    public void setVale(ValeMaterial oVale) {
        this.oVale = oVale;
    }
    
    public String getFechaHoy() { 
        SimpleDateFormat hAc = new SimpleDateFormat("yyyy-MM-dd");
        Date actual = new Date();
        sFechaHoy = hAc.format(actual);
        return sFechaHoy; 
    }
    
    
    
    
}
