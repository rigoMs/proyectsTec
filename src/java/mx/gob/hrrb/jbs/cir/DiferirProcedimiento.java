package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Quintero
 */

@ManagedBean (name="oDiferirQx")
@ViewScoped
public class DiferirProcedimiento implements Serializable{
    private ProcedimientosRealizados oProcedimientos = new ProcedimientosRealizados();
    private ArrayList<ProcedimientosRealizados> arrProgramadas = null;
    private String sVisibilidad = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false;
    
    public DiferirProcedimiento(){
        oProcedimientos = new ProcedimientosRealizados();
    }
    
    public void inicializar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("oPacienteSeleccionado", new ProcedimientosRealizados());
        arrProgramadas = null;
    }
    
    public List<Parametrizacion> getListaCausasDiferir() throws Exception{
        List<Parametrizacion> lista=null;
        lista=new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscaMotivosDiferirQx())));
        return lista;
    }
    
    public void buscaProgramacionQx(){
        sVisibilidad = "visible";
        try{
            System.out.println("Estamos mandando la fecha -- >"+oProcedimientos.getFechaProgramada());
            arrProgramadas = new ArrayList<ProcedimientosRealizados>(Arrays.asList(oProcedimientos.buscaProgramacion()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setSeleccionado(ProcedimientosRealizados valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec", valor);
    }
    
    public ProcedimientosRealizados getSeleccionado(){
        return new ProcedimientosRealizados();
    }
    
    public void llenaDatosProgramacion(){
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
    
    public void limpia(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec",null);
    }

    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; }
    public ArrayList<ProcedimientosRealizados> getListaProgramadas(){ return arrProgramadas; }
    public String getVisibilidad() { return this.sVisibilidad; }
    public String getVisible(){
        if(this.bBuscado)
            Visible = "visible";
        else
            Visible = "hidden";
        return Visible;
    }
    
    public void registraCausa(){
        FacesMessage message=null; 
        try{
            if(oProcedimientos.registraDiferirQx() == 1){
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Diferir Procedimiento Qx", "Guardada Exitosamente!!!");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Diferir Procedimiento Qx", "Ocurrio un Error al Guardar :( !!!");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
      
      
}
