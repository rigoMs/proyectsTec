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
import mx.gob.hrrb.modelo.cir.Residente;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Quintero
 */
@ManagedBean(name = "oValoraPre")
@ViewScoped
public class ValoracionPreanesJB implements Serializable{
    private Medico oMedico = new Medico();
    private Residente oResid = new Residente();
    private String sVisibilidadTabla="hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false;    
    private ArrayList<ProcedimientosRealizados> arrPacientes = null;
    private String sNotaPre = "";
    private ProcedimientosRealizados oProcedimientos = null;
    private ProcedimientosRealizados oSeleccionado;
    private String sNomPac = "";
    private String sAppPac = "";
    private String sApmPac = "";
    private int nNumExpPac = 0;
    private String sFechaHoy = "";
    
    public ValoracionPreanesJB(){
        oProcedimientos = new ProcedimientosRealizados();
    }
    
     public void inicializar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        session.setAttribute("oPacienteSeleccionado", new ProcedimientosRealizados());
        arrPacientes = null;
    }
     
     public void buscaPacienteValoracion(){
         sVisibilidadTabla = "visible";
         try{
             arrPacientes = new ArrayList<ProcedimientosRealizados>(
                     Arrays.asList(
                             oProcedimientos.buscaPacientaValoracionPre(
                                     sNomPac,sAppPac,sApmPac,nNumExpPac)));
         }catch(Exception e){
             e.printStackTrace();
         }
     }
     
     public ProcedimientosRealizados getSeleccionado(){ return oSeleccionado; }
     public ArrayList<ProcedimientosRealizados> getListaDatosPaciente() { return arrPacientes; }
     
     public void setSeleccionado(ProcedimientosRealizados oSeleccionado){
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
         session.setAttribute("pacienteSelec", oSeleccionado);
     }
     
     public void llenaDatosPaciente(){
         ProcedimientosRealizados oProce = null;
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
         oProce = (ProcedimientosRealizados)session.getAttribute("pacienteSelec");
         if(oProce == null){
             FacesMessage msj2 = new FacesMessage("¡Alerta!","Selecciona Un Paciente");
             RequestContext.getCurrentInstance().showMessageInDialog(msj2);
             bBuscado = false;
         }else{
             oProcedimientos = oProce;
             bBuscado = true;
         }
     } 
    
     public void limpia(){
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
         session.setAttribute("pacienteSelec",null);
     }
     
     public String getVisible(){
        if(this.bBuscado)
            Visible = "visible";
        else
            Visible = "hidden";
        return Visible;
    }

    public String getVisibilidadTabla(){
        return this.sVisibilidadTabla;
    }
    
    public void registraValoracion(){
        FacesMessage message= null;
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Fecha a imprimir ---->" + sFechaHoy);
        try{
            Date fecAct = fecha.parse(sFechaHoy);
            oProcedimientos.setNotaPreanestesica(sNotaPre);
            oProcedimientos.setFechaValAnestesica(fecAct);
            if(oProcedimientos.registraValoracionPreanestesica() == 1){
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Valoracion Preanestesica","Guardado Exitosamente  :) !!");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Valoracion Preanestesica","Ocurrio un Error al Guardar :( !!!");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; }
    public String getNotaPre() { return sNotaPre; }
    public void setNotaPre(String sNotaPre) { this.sNotaPre = sNotaPre; }
    public String getNomPac() { return sNomPac; }
    public void setNomPac(String sNomPac) { this.sNomPac = sNomPac; }
    public String getAppPac() { return sAppPac; }
    public void setAppPac(String sAppPac) { this.sAppPac = sAppPac; }
    public String getApmPac() { return sApmPac; }
    public void setApmPac(String sApmPac) { this.sApmPac = sApmPac; }
    public int getNumExpPac() { return nNumExpPac; }
    public void setNumExpPac(int nNumExpPac) { this.nNumExpPac = nNumExpPac; }
    public String getFechaHoy() { 
        SimpleDateFormat hAc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date actual = new Date();
        sFechaHoy = hAc.format(actual);
        return sFechaHoy; 
    }
    public void setFechaHoy(String sFechaHoy) {
        this.sFechaHoy = sFechaHoy;
    }
}     