
package mx.gob.hrrb.jbs.enfermeria;


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
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.enfermeria.DetalleSuperServicios;
import mx.gob.hrrb.modelo.enfermeria.ProcedimientoEnfermeriaPor;
import org.primefaces.context.RequestContext;

        

/**
 *@objetivo: mostrar la infomacion de la hoja de supervision y pacientes que se encuentren el servicio  DISTINCION, GINECO-OBSTETRICA, 
 *           CIRUGIA GENERAL, MAPE, PEDIATRIA, MEDICINA INTERNA,  Y SERVICIOS DE URGENCIAS 
 * @author: Javier
 * @version: 1.0
 */
@ManagedBean(name="oPacSupSer")
@ViewScoped
public class HojaSupervBuscaPacServicios implements Serializable{

    private boolean bDisable;
    private boolean bDisableImp;
    private boolean bRendering;
    private int nArea;
    private String sObservacion;    
    private String sServicio;          
    private Date dFechaActual;    
    private DetalleSuperServicios oDeSupSer, oProcedimiento,oObservacion;     
    private EpisodioMedico[] ArrEpi=null, arrPacHosp=null;   
    private List<ProcedimientoEnfermeriaPor> lListaProcPor = null;      
    private List<DetalleSuperServicios> lListaProc=null; 
    private ArrayList<AreaServicioHRRB> arrServicio;
    private ArrayList<DetalleSuperServicios> lProcAgregado;
    private DetalleSuperServicios[] arrProcedimientosApli=null;
    private static final  String sTablaProc="TAG";
    
    public HojaSupervBuscaPacServicios() {        //DetalleSuperServicios
        dFechaActual = new Date();      
        oDeSupSer= new DetalleSuperServicios(); 
        oProcedimiento = new DetalleSuperServicios();      
        oObservacion= new DetalleSuperServicios();
        bDisable=true;        
         
        try{          
            lListaProc= new ArrayList<DetalleSuperServicios>(Arrays.asList((oProcedimiento.buscaProcedimientosServicios(sTablaProc)))); 
            arrServicio=oProcedimiento.getCabeceraSupervision().buscarServiciosTodosHojaSup();
        }catch(Exception e){
        java.util.logging.Logger.getLogger(DetalleSuperServicios.class.getName()).log(Level.SEVERE,null,e);
        }        
    }
    
    public void buscaPacienteEnServicio(){
        if(this.getAreaServicio()!=0){
            bRendering=false;
            try {
               ArrEpi = oProcedimiento.getEpisodio().buscarPacientesEnServicio(nArea);
                arrPacHosp = oProcedimiento.getEpisodio().buscarPacientesPorServicioHopitalizacion2(this.getAreaServicio()); 
                oObservacion.getCabeceraSupervision().getAreaServicio().setClave(nArea);
                bDisableImp=arrPacHosp==null;
                if(!oObservacion.getCabeceraSupervision().buscarCabeceraSupervisionServicios()){
                    oObservacion= new DetalleSuperServicios(); 
                }               
                if(ArrEpi==null && arrPacHosp ==null){                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se encuentran pacientes en el servicio seleccionado"));
                }else
                    bRendering=true;
                buscaNombreServio(this.getAreaServicio());
            } catch (Exception ex) {
                Logger.getLogger(HojaSupervBuscaPacUCI.class.getName()).log(Level.SEVERE, null, ex);
            }           
        }else{
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Selecciona un servicio"));  
        }
        
    }    
   
   public void datosPaciente(long fp){       
       try{            
           if(buscaPacienteEnMemoria(fp)){              
               lProcAgregado = new ArrayList<DetalleSuperServicios>();      
               this.getProcedimientoAgregado().getProcedimientoEnfermeria().setValor("");
               this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setDescripcion("");
               this.getProcedimientoPac().setObservacion("");
           }else{           
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Paciente no encontrado"));
           }               
       }catch(Exception e){
           java.util.logging.Logger.getLogger(DetalleSuperServicios.class.getName()).log(Level.SEVERE,null,e);           
       }
   }
   
   public void datosPaciente2(long fp){       
       try{          
           if(buscaPacienteEnMemoria2(fp)){              
               lProcAgregado = new ArrayList<DetalleSuperServicios>();      
               this.getProcedimientoAgregado().getProcedimientoEnfermeria().setValor("");
               this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setDescripcion("");
               this.getProcedimientoPac().setObservacion("");
           }else{           
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Paciente no encontrado"));
           }               
       }catch(Exception e){
           java.util.logging.Logger.getLogger(DetalleSuperServicios.class.getName()).log(Level.SEVERE,null,e);           
       }
   }
   
   public boolean buscaPacienteEnMemoria(long outpf){
       boolean bRtn=false;
        try{
            for(EpisodioMedico oEp : ArrEpi ){
                if(oEp.getPaciente().getFolioPaciente()==outpf){               
                    oProcedimiento.getEpisodio().getPaciente().setFolioPaciente(oEp.getPaciente().getFolioPaciente());
                    oProcedimiento.getEpisodio().getPaciente().setClaveEpisodio(oEp.getPaciente().getClaveEpisodio());
                    oProcedimiento.getEpisodio().getPaciente().getExpediente().setNumero(oEp.getPaciente().getExpediente().getNumero());
                    oProcedimiento.getEpisodio().getPaciente().setNombres(oEp.getPaciente().getNombres());
                    oProcedimiento.getEpisodio().getPaciente().setApPaterno(oEp.getPaciente().getApPaterno());
                    oProcedimiento.getEpisodio().getPaciente().setApMaterno(oEp.getPaciente().getApMaterno());
                    oProcedimiento.getEpisodio().getCama().setNumero(oEp.getCama().getNumero());
                    bRtn=true;
                    break;
                }
            }
        }catch(Exception e){e.printStackTrace();}
       return bRtn;
   }
   
   public boolean buscaPacienteEnMemoria2(long outpf){
       boolean bRtn=false;
        try{
            for(EpisodioMedico oEp : arrPacHosp ){
                if(oEp.getPaciente().getFolioPaciente()==outpf){
                    oProcedimiento.getEpisodio().getPaciente().setFolioPaciente(oEp.getPaciente().getFolioPaciente());
                    oProcedimiento.getEpisodio().getPaciente().setClaveEpisodio(oEp.getPaciente().getClaveEpisodio());
                    oProcedimiento.getEpisodio().getPaciente().getExpediente().setNumero(oEp.getPaciente().getExpediente().getNumero());
                    oProcedimiento.getEpisodio().getPaciente().setNombres(oEp.getPaciente().getNombres());
                    oProcedimiento.getEpisodio().getPaciente().setApPaterno(oEp.getPaciente().getApPaterno());
                    oProcedimiento.getEpisodio().getPaciente().setApMaterno(oEp.getPaciente().getApMaterno());
                    oProcedimiento.getEpisodio().getCama().setNumero(oEp.getCama().getNumero());
                    bRtn=true;
                    break;
                }
            }
        }catch(Exception e){e.printStackTrace();}       
       return bRtn;
    }
      
    public void onProceChange(){
        if(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor() !=null && !this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor().equals("")){             
            try{
                lListaProcPor = new ArrayList<ProcedimientoEnfermeriaPor>(Arrays.asList((new ProcedimientoEnfermeriaPor()).buscarProcedimientoEnfermeria(this.oDeSupSer.getProcedimientoEnfermeria().getValor())));                
                if(lListaProcPor.isEmpty()){
                    this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setDescripcion("");
                    bDisable=true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Procedimiento Seleccionado no se puede aplicar por mas "));
                }else{
                    this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setDescripcion("");
                    bDisable=false;
                }
            }catch(Exception e){
             java.util.logging.Logger.getLogger(ProcedimientoEnfermeriaPor.class.getName()).log(Level.SEVERE,null,e);
            }            
        }else{     
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Seleccione un procedimiento"));            
        }
    }
    
    public void agregarProcedimiento(){        
        if(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor()!=null 
                && !this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor().equals("")){
            
            if(this.getProcedimientoAgregado()==null){
                this.setProceAgregado(new ArrayList<DetalleSuperServicios>()); 
                this.getProcedimientoAgregado().getProcedimientoEnfermeria().setClaveParametro(this.getBuscarClaveProcedimiento(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor()));
                this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setClavePor(this.getBuscarClaveProcedimientoPor(this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion()));
                lProcAgregado.add(this.getProcedimientoAgregado());
                oDeSupSer = new DetalleSuperServicios();
                //System.out.println(" paso 1 " + lProcAgregado.size());
            }else{
                if(lProcAgregado.isEmpty()){
                    if(lListaProcPor.isEmpty()){
                        this.getProcedimientoAgregado().getProcedimientoEnfermeria().setClaveParametro(this.getBuscarClaveProcedimiento(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor()));
                        this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setClavePor(this.getBuscarClaveProcedimientoPor(this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion()));
                        lProcAgregado.add(this.getProcedimientoAgregado());
                        oDeSupSer = new DetalleSuperServicios();
                        
                        //System.out.println(" paso 2 " + lProcAgregado.size());
                    }else{
                        if(!lListaProcPor.isEmpty()){
                            if(this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion()!=null 
                                    && !this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion().equals("") ){
                                this.getProcedimientoAgregado().getProcedimientoEnfermeria().setClaveParametro(this.getBuscarClaveProcedimiento(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor()));
                                this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setClavePor(this.getBuscarClaveProcedimientoPor(this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion()));
                                lProcAgregado.add(this.getProcedimientoAgregado());
                                oDeSupSer = new DetalleSuperServicios();
                                
                                //System.out.println(" paso 2.2 " + lProcAgregado.size());
                            }else{
                                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona como se aplica"));
                            }                            
                        }                        
                    }
                    
                }else{
                    if(lListaProcPor.isEmpty()){
                        if (buscarRepetido(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor())) {                            
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se puede agregar mas del mismo tipo"));                           
                        }else{
                            this.getProcedimientoAgregado().getProcedimientoEnfermeria().setClaveParametro(this.getBuscarClaveProcedimiento(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor()));
                            this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setClavePor(this.getBuscarClaveProcedimientoPor(this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion()));
                            lProcAgregado.add(this.getProcedimientoAgregado());
                            oDeSupSer = new DetalleSuperServicios();
                            //System.out.println(" paso 3 " + lProcAgregado.size());
                        }                        
                    }else{
                        if(!lListaProcPor.isEmpty()){
                            if(this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion()!=null 
                                    && !this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion().equals("")){
                               if (buscarRepetido(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor())) {                                   
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se puede agregar mas del mismo tipo"));                           
                               }else{
                                  this.getProcedimientoAgregado().getProcedimientoEnfermeria().setClaveParametro(this.getBuscarClaveProcedimiento(this.getProcedimientoAgregado().getProcedimientoEnfermeria().getValor()));
                                  this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().setClavePor(this.getBuscarClaveProcedimientoPor(this.getProcedimientoAgregado().getProcedimientoEnfermeriaPor().getDescripcion()));
                                  lProcAgregado.add(this.getProcedimientoAgregado());
                                  oDeSupSer = new DetalleSuperServicios();
                                  //System.out.println(" paso 3.3 " + lProcAgregado.size());
                                }    
                            }else{
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona como se aplica"));
                            }                
                        }
                    }
                        
                }
            }  
        }else {            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Seleccione un procedimiento"));
        }
    }
    
    public boolean buscarRepetido( String clave){
        boolean srt=false;
        for (DetalleSuperServicios lRep : lProcAgregado) {
            if (lRep.getProcedimientoEnfermeria().getValor().equals(clave)) {
                srt=true;
                break;
            }
        }
        
        return srt;
    }
    
    public String getBuscarClaveProcedimiento(String valor){
        String sClave="";
        for(DetalleSuperServicios oParam: lListaProc){
            if(oParam.getProcedimientoEnfermeria().getValor().equals(valor)){
                sClave= oParam.getProcedimientoEnfermeria().getClaveParametro();
                break;
            }
        }
        return sClave;
    }
    
    public int getBuscarClaveProcedimientoPor(String por){
        int nCvePor=0;
        if(!por.equals("")){
            for(ProcedimientoEnfermeriaPor oProPor : lListaProcPor){
                if(oProPor.getDescripcion().equals(por)){
                    nCvePor= oProPor.getClavePor();
                    break;
                }
            }
        }
        
        return nCvePor;
    }   
    
    public void eliminarProcedimietoAgregado( DetalleSuperServicios oDetalle){        
        this.lProcAgregado.remove(oDetalle);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Sea Eliminado El Porcedimiento"));       
    }
    
    public void guadarProcedimientoPaciente(){        
        if(!lProcAgregado.isEmpty()){
            try{
                oProcedimiento.getCabeceraSupervision().getAreaServicio().setClave(this.getAreaServicio());
                oProcedimiento.getProcedimientoEnfermeria().setTipoParametro(sTablaProc);
                oProcedimiento.setArrProcAgregado(lProcAgregado);
                if(oProcedimiento.getCabeceraSupervision().getIdCabecera()==0){
                    if(oProcedimiento.getCabeceraSupervision().buscarCabeceraSupervisionServicios()){
                        if(oProcedimiento.buscarLlaveDetalleSupervicionServicios()){
                            if(oProcedimiento.insertarProcedimientoEnfermeria()>0){
                                RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimientos guardados correctamente"));                                
                            }
                        }else{
                            if(oProcedimiento.insertaDetalleSupervicionProcedimientosEnfermeria()>0){
                                RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimientos guardados correctamente"));
                            }                           
                        }
                    }else{
                        if(oProcedimiento.insertaCabeceraDetalleProcedimiento()>0){
                            RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimientos guardados correctamente"));
                        }
                    }

                }else if(oProcedimiento.getCabeceraSupervision().getIdCabecera()!=0){
                    if(oProcedimiento.buscarLlaveDetalleSupervicionServicios()){
                        if(oProcedimiento.insertarProcedimientoEnfermeria()>0){
                            RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimientos guardados correctamente"));
                        }
                    }else{
                        if(oProcedimiento.insertaDetalleSupervicionProcedimientosEnfermeria()>0){
                            RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimientos guardados correctamente"));
                        }
                    }
                }  
            }catch(Exception e){e.printStackTrace();}
        }else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se permite guardar si Procedimientos"));           
        oProcedimiento.setIdSupervision(0);
    }  
    
    public void limpiaCamposObserv(){
        this.getObservacionObj().getCabeceraSupervision().setObservacionTM("");
        this.getObservacionObj().getCabeceraSupervision().setObservacionTN("");
        this.getObservacionObj().getCabeceraSupervision().setObservacionTV("");
        ArrayList<String> update= new ArrayList<String>();
        update.add("observ:obserM");
        update.add("observ:obserV");
        update.add("observ:obserN");
        RequestContext.getCurrentInstance().update(update);        
    }
    
    public void agregarObservacion(ActionEvent ev){            
        if(this.getObservacionObj().getCabeceraSupervision().getObservacionTM()!=null 
                && !this.getObservacionObj().getCabeceraSupervision().getObservacionTM().equals("")
                || this.getObservacionObj().getCabeceraSupervision().getObservacionTV()!=null
                && !this.getObservacionObj().getCabeceraSupervision().getObservacionTV().equals("")
                || this.getObservacionObj().getCabeceraSupervision().getObservacionTN()!=null
                && !this.getObservacionObj().getCabeceraSupervision().getObservacionTN().equals("")){              
            try {             
                
                oObservacion.getCabeceraSupervision().getAreaServicio().setClave(nArea);                       
                if(oObservacion.getCabeceraSupervision().getIdCabecera()==0){
                    if(oObservacion.getCabeceraSupervision().buscarCabeceraSupervisionServicios()){                        
                        if(oObservacion.getCabeceraSupervision().modificarCabeceraSupervision()>0){                            
                            oObservacion.getCabeceraSupervision().buscarCabeceraSupervisionServicios();
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Correctamente"));
                            RequestContext.getCurrentInstance().execute("PF('agregarObservacion').hide()");                        
                        }                   
                    }else{                    
                        if(oObservacion.getCabeceraSupervision().insertarCabeceraSupervisionServicios()>0){                          
                            oObservacion.getCabeceraSupervision().buscarCabeceraSupervisionServicios();
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Correctamente"));
                            RequestContext.getCurrentInstance().execute("PF('agregarObservacion').hide()");
                        }
                    }                    
                }else if(oObservacion.getCabeceraSupervision().getIdCabecera()!=0){             
                    if(oObservacion.getCabeceraSupervision().modificarCabeceraSupervision()>0){             
                        oObservacion.getCabeceraSupervision().buscarCabeceraSupervisionServicios();                        
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Correctamente"));
                        RequestContext.getCurrentInstance().execute("PF('agregarObservacion').hide()");                        
                    }     
                }               
                
            } catch (Exception ex) {
                Logger.getLogger(HojaSupervBuscaPacServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","Para guardar necesitas una observacion"));
        }        
    }    
    public void buscaProcemientoAplicadoServicios(){        
        try {
            DetalleSuperServicios oDe = new DetalleSuperServicios();
            oDe.getCabeceraSupervision().getAreaServicio().setClave(this.getAreaServicio());
            arrProcedimientosApli= oDe.buscarProcedimientosEnfermeriaAplicados();
            if(arrProcedimientosApli!=null && arrProcedimientosApli.length>0){
                RequestContext.getCurrentInstance().execute("PF('verdetalles').show()");                
                RequestContext.getCurrentInstance().update("imp:hojaSer");
            }else{
                RequestContext.getCurrentInstance().update("frmHojaSupGen:msgs");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No hay infomación para mostrar"));
            }
        } catch (Exception ex) {
            Logger.getLogger(HojaSupervBuscaPacServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void buscaNombreServio(int nArea){
        for(AreaServicioHRRB oSer: getArrServicio()){
            if(oSer.getClave()==nArea){
                sServicio=oSer.getDescripcion();
                break;
            }
        }
    }  
     
    public EpisodioMedico[] getArrgloPacientes(){
        return ArrEpi;
    }
      
    public String getServicio(){
        return sServicio;
    }
    
    
    public DetalleSuperServicios getProcedimientoPac(){
        return oProcedimiento;
    }
    
    public DetalleSuperServicios getProcedimientoAgregado(){
       return oDeSupSer;
    }
  
    
    public String getFechaActucal(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");       
        return format.format(dFechaActual);
    }
    
    public List<DetalleSuperServicios> getListaProc(){
        return lListaProc;
    }    
    
    public List<ProcedimientoEnfermeriaPor> getListaProcPor(){
        return lListaProcPor;
    } 
    
    public ArrayList<DetalleSuperServicios> getProceAgregado(){
        return lProcAgregado;
    }
    
    public void setProceAgregado(ArrayList<DetalleSuperServicios> valor){
        this.lProcAgregado=valor;
    }  
    
    public boolean getDisable(){
        return bDisable;
    } 

    public String getObservacion() {
        return sObservacion;
    }

   
    public void setObservacion(String sObservacion) {
        this.sObservacion = sObservacion;
    }
    
    public ArrayList<AreaServicioHRRB> getArrServicio() {
        return arrServicio;
    }
    
    public int getAreaServicio() {
        return nArea;
    }

    public void setAreaServicio(int nArea) {
        this.nArea = nArea;
    }
    
    public EpisodioMedico[] getArrPacHosp() {
        return arrPacHosp;
    }    
    
    public DetalleSuperServicios getObservacionObj() {
        return oObservacion;
    }
  
    public DetalleSuperServicios[] getArrProcedimientosApli() {
        return arrProcedimientosApli;
    }

    public boolean getDisableImp() {
        return bDisableImp;
    }

    public boolean getRendering() {
        return bRendering;
    }
    
}

