/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.cir.AnestesiaEspecifica;
import mx.gob.hrrb.modelo.cir.Quirofanos;
import mx.gob.hrrb.modelo.cir.Residente;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Quintero
 */
@ManagedBean (name="oReprogramaQx")
@ViewScoped
public class ReprogramaProcedimientos implements Serializable{
    private ProcedimientosRealizados oProcedimientos = new ProcedimientosRealizados();
    private ArrayList<ProcedimientosRealizados> arrProcedimientos = null;
    private Medico oCirujano = new Medico();
    private String sVisibilidad = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false;
    private int pTipoCir = 0;
    private Date dFechaIntern;
    private String sHoraIntern;
    
    public void ReprogramaProcedimientos(){}
    
    public void inicializar()throws Exception{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("oPacienteSeleccionado", new ProcedimientosRealizados());
        oProcedimientos = new ProcedimientosRealizados();
        arrProcedimientos = null;
    }
    
    public List<Medico> getLisCirujanos() throws Exception{
        List<Medico> listaCir = null;
        listaCir = new ArrayList<Medico>(Arrays.asList((new Medico().buscarMedicoCir())));
        return listaCir;
    }
     /*Pintado de ComboBox*/
    public List<Quirofanos> getListaQx() throws Exception{
        List<Quirofanos> lista=null;
        lista=new ArrayList<Quirofanos>(Arrays.asList((new Quirofanos().buscarTodos())));
        return lista;
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
    
    public List<Parametrizacion> getListaHorarios()throws Exception{
        List<Parametrizacion> listaHorarios = null;
        listaHorarios = new ArrayList<Parametrizacion>(Arrays.asList(new Parametrizacion().buscaHorariosQx()));
        return listaHorarios;
    }
    
    public List<Parametrizacion> getListaTiposQx()throws Exception{
        List<Parametrizacion> listaTiposQx = null;
        listaTiposQx = new ArrayList<Parametrizacion>(Arrays.asList(new Parametrizacion().buscaTiposProceQx()));
        return listaTiposQx;
    }
    
    public List<Parametrizacion> getListaMotCancelacion() throws Exception{
        List<Parametrizacion> listaMotivosCancel = null;
        listaMotivosCancel = new ArrayList<Parametrizacion>(Arrays.asList(new Parametrizacion().buscaMotivosCancelacionQx()));
        return listaMotivosCancel;
    }
    /* Termina Pintado de ComboBox*/
    
    public void buscaProcedimientos(){
        sVisibilidad = "visible";
        try{
            arrProcedimientos = new ArrayList<ProcedimientosRealizados>(Arrays.asList(oProcedimientos.buscaProcedimientosReprogramacion(oCirujano)));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void setSeleccionado(ProcedimientosRealizados valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec", valor);
    }
    public ProcedimientosRealizados getSeleccionado(){ return new ProcedimientosRealizados(); }
    
    public void llenaDatosProcedimiento(){
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
            System.out.println("Trae la fecha "+oProce.getFechaPropInternacion());
            oProcedimientos = oProce;
            bBuscado = true;
        }
     }
    public void limpia(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec",null);
    }
    
    public void reprogramaProcedimientoQx(){
        FacesMessage message = null;
        if(getTipoCir() == 1)
            oProcedimientos.getValorTipoQx().setClaveParametro("4");
        else
            oProcedimientos.getValorTipoQx().setClaveParametro("3");
        try{
            SimpleDateFormat fecCom = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
            String fechaCompletaIntern = "" + fd.format(getFechaIntern()) + " " + (getHoraIntern())+ ":00";
            Date dateCom = fecCom.parse(fechaCompletaIntern);
            oProcedimientos.setFechaPropInternacion(dateCom);
            if(oProcedimientos.registraProgramacionDeProcedimiento() == 1){
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Procedimiento Programado","Guardado Exitosamente  :) !!");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Procedimiento Programado","Ocurrio un Error al Guardar :( !!");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public ArrayList<ProcedimientosRealizados> getListaProcedimientos(){ return arrProcedimientos; }
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; }
    public String getVisibilidad() { return sVisibilidad; }
    public String getVisible(){
        if(this.bBuscado)
            Visible = "visible";
        else
            Visible = "hidden";
        return Visible;
    }
    public Medico getCirujano() { return oCirujano; }
    public void setCirujano(Medico oCirujano) { this.oCirujano = oCirujano; }
    public int getTipoCir() { return pTipoCir; }
    public void setTipoCir(int pTipoCir) { this.pTipoCir = pTipoCir; }
    public Date getFechaIntern() { return dFechaIntern; }
    public void setFechaIntern(Date dFechaIntern) { this.dFechaIntern = dFechaIntern; }
    public String getHoraIntern() { return sHoraIntern; }
    public void setHoraIntern(String sHoraIntern) { this.sHoraIntern = sHoraIntern; }
}
