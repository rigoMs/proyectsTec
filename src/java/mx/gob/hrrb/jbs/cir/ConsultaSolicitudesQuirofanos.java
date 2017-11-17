package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@ManagedBean (name="oConsultaQx")
@ViewScoped

public class ConsultaSolicitudesQuirofanos implements Serializable{
    private ProcedimientosRealizados oProcedimientos;
    private ArrayList<ProcedimientosRealizados> arrSolicitudes;
    private String sVisibilidadTabla = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false; 
    private Date dFecha;
    private Date dFechaIntern;
    private String sHoraIntern;
    private int pTipoCir = 0;
    private String sClaveCancel;
    private Parametrizacion arrHorarios[];
    private int sClv = 0;
    
    public ConsultaSolicitudesQuirofanos(){
        oProcedimientos = new ProcedimientosRealizados();
    }    
    
    public void inicializar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
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
    
   
    /*Pintado de Tabla*/
    public void buscaSolicitudes(int numClv){
        sVisibilidadTabla = "visible";
        try{
            arrSolicitudes = new ArrayList<ProcedimientosRealizados>(
                    Arrays.asList(
                            new ProcedimientosRealizados()
                                    .buscaServicioRealizadoSolicitudxFechas(
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
        }
     }
    
    public void limpia(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec",null);
    }
    
    public Parametrizacion[] getListaHorariosDisponibles(){
        try{
            if(oProcedimientos.getFechaProgramada() != null && 
               !oProcedimientos.getTipoTurno().equals("") && 
               !oProcedimientos.getQuirofanos().getClave().equals("")){
                arrHorarios = (new Parametrizacion().buscaHorariosDisponiblesQx( oProcedimientos.getFechaProgramada(), oProcedimientos.getTipoTurno(), oProcedimientos.getQuirofanos().getClave()));
                
            }
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("");
        }
        return arrHorarios;
    }
    
    public void registraTHFQ(){
         try{
            if(oProcedimientos.registraFechaDeProcedimiento()== 1){
                getListaHorariosDisponibles();
            }
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("No se guardaron che :( ");
        }
    }
    
    public void registraProcedimientoQx(){
        FacesMessage message = null;
        if(getTipoCir() == 1)
            oProcedimientos.getValorTipoQx().setClaveParametro("4");
        else
            oProcedimientos.getValorTipoQx().setClaveParametro("3");
        try{
            SimpleDateFormat fecCom = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
            String fechaCompletaIntern = "" + fd.format(dFechaIntern) + " " + (sHoraIntern)+ ":00";
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
    
    public void registraProcedimientHospQx(){
        FacesMessage message = null;
        if(getTipoCir() == 1)
            oProcedimientos.getValorTipoQx().setClaveParametro("4");
        else
            oProcedimientos.getValorTipoQx().setClaveParametro("3");
        try{
            if(oProcedimientos.registraProgramacionDeProcedimientoHospUrg() == 1){
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Procedimiento Programado","Guardado Exitosamente  :) !!");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Procedimiento Programado","Ocurrio un Error al Guardar :( !!");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
      
    public void registraCancelacion(){
        System.out.println("Clave de Servicio Realizado  ---> "+ oProcedimientos.getSituacion().getClaveParametro()); 
        FacesMessage message = null;
        try{
            if(oProcedimientos.registraCancelacion(getClaveCancel()) == 1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Procedimiento Programado","Guardado Exitosamente  :) !!");
        }catch(Exception ex){
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Procedimiento Programado","Ocurrio un Error al Guardar :( !!");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
      
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; } 
    public String getVisibilidadTabla() { return this.sVisibilidadTabla; } 
    public Date getFecha() { return dFecha; }
    public void setFecha(Date dFecha) { this.dFecha = dFecha; }
    public Date getFechaIntern() { return dFechaIntern; }
    public void setFechaIntern(Date dFechaIntern) { this.dFechaIntern = dFechaIntern; }
    public String getHoraIntern() { return sHoraIntern; }
    public void setHoraIntern(String sHoraIntern) { this.sHoraIntern = sHoraIntern; }
    public int getTipoCir() { return pTipoCir; }
    public void setTipoCir(int pTipoCir) { this.pTipoCir = pTipoCir; }    
    public String getClaveCancel() { return sClaveCancel; }
    public void setClaveCancel(String sClaveCancel) { this.sClaveCancel = sClaveCancel; }
    public int getClv() { return sClv; }
    public void setClv(int sClv) { this.sClv = sClv; }
    
}
