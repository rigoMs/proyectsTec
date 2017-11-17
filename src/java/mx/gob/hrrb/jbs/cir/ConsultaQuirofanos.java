/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.cir.AnestesiaEspecifica;
import mx.gob.hrrb.modelo.cir.Quirofanos;
import mx.gob.hrrb.modelo.cir.Residente;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import org.primefaces.context.RequestContext;
/**
 *
 * @author Quintero
 */

@ManagedBean (name="oConsultaQx")
@ViewScoped
public class ConsultaQuirofanos implements Serializable{
    private ProcedimientosRealizados oProcedimientos;
    private ArrayList<ProcedimientosRealizados> arrSolicitudes;
    private String sVisibilidadTabla = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false; 
    private Date dFecha;
    private String esAmbulatoria="N";
    
    
    public ConsultaQuirofanos(){
        oProcedimientos = new ProcedimientosRealizados();
    }    
    
    public void inicializar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        session.setAttribute("oPacienteSeleccionado", new ProcedimientosRealizados());
        arrSolicitudes = null;
    }
    /*Pintado de ComboBox*/
    public List<Quirofanos> getListaQx() throws Exception{
        List<Quirofanos> lista=null;
        lista=new ArrayList<Quirofanos>(Arrays.asList((new Quirofanos().buscarTodos())));
        return lista;
    }
    
    public List<Medico> getLisCirujanos() throws Exception{
        List<Medico> listaCir = null;
        listaCir = new ArrayList<Medico>(Arrays.asList((new Medico().buscarMedicoCir())));
        return listaCir;
    }
    
    public List<Medico> getListaAnestesiologos() throws Exception{
        List<Medico> listAnes = null;
        listAnes = new ArrayList<Medico>(Arrays.asList((new Medico().buscarMedicoAnestesio())));
        return listAnes;
    }
    
    public List<AnestesiaEspecifica> getListaAnestesias() throws Exception{
        List<AnestesiaEspecifica> listaAnestesia = null;
        listaAnestesia = new ArrayList<AnestesiaEspecifica>(Arrays.asList((new AnestesiaEspecifica().buscarTodos())));
        return listaAnestesia;
    }
    
    public List<Residente> getListaResidentes() throws Exception{
        List<Residente> listaResidente = null;
        listaResidente = new ArrayList<Residente>(Arrays.asList((new Residente().buscarResidentes())));
        return listaResidente;
        
    }
    /* Termina Pintado de ComboBox*/
    
   
    /*Pintado de Tabla*/
    public void buscaSolicitudes(ActionEvent ae){
        sVisibilidadTabla = "visible";
        try{
            arrSolicitudes = new ArrayList<ProcedimientosRealizados>(Arrays.asList(new ProcedimientosRealizados().buscarSolicitudesFecha(getFecha())));
        }catch(Exception ex){
            Logger.getLogger(ConsultaQuirofanos.class.getName()).log(Level.SEVERE, null, ex);
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
    /* Termina Pintado de Tabla*/
    
    /* Llenado de Datos */
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
            limpia();
        }
     }
      public void limpia(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec",null);
    }
    
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; } 
    public String getVisibilidadTabla() { return this.sVisibilidadTabla; } 
    public Date getFecha() { return dFecha; }
    public void setFecha(Date dFecha) { this.dFecha = dFecha; }
    
    public String getEsAmbulatoria(){
        return this.esAmbulatoria;
    }
    public void setEsAmbulatoria(String valor){
        this.esAmbulatoria = valor;
    }
}
