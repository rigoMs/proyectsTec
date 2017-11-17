
package mx.gob.hrrb.jbs.enfermeria;
import java.util.logging.Logger;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.enfermeria.DetalleSupervisionUCI;
import mx.gob.hrrb.modelo.enfermeria.ProcedimientoEnfermeriaPor;
import org.primefaces.context.RequestContext;
 

/**
 * @objetivo: UCI, UCIP, UCIN  servicios o areas en la que se ocupa la hoja de supervision de terapia intensiva
 * @author: Javier
 * @version: 1.0
 */
@ManagedBean(name="oUCIPac")
@ViewScoped
public class HojaSupervBuscaPacUCI implements Serializable {

    private int nArea;    
    private boolean bDisableHosp;
    private boolean bDisable;
    private String sServicio;
    private String sFechaActual;    
    private EpisodioMedico[] oPacSerUCI=null, arrPacHosp=null;         
    private DetalleSupervisionUCI oUCI;
    private DetalleSupervisionUCI oProcUCI;
    private List<ProcedimientoEnfermeriaPor> lListaProcPor = null;    
    private ArrayList<DetalleSupervisionUCI> lProcAgregado;
    private List<AreaServicioHRRB> lListaAreaServicio;
    private DetalleSupervisionUCI[] arrProcedimientosAplic;
    private  List<DetalleSupervisionUCI> lListaProc=null;
    private ArrayList<AreaServicioHRRB> arrServicio;
    private static final  String sTablaProc="TAG";  
    
    public HojaSupervBuscaPacUCI() {
        Date dFechaAc = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        sFechaActual = format.format(dFechaAc);
        oUCI = new DetalleSupervisionUCI();
        oProcUCI = new DetalleSupervisionUCI();
        bDisable=true;            
        Carga();        
    }
    
    private void Carga(){
        try{  
            arrServicio=oProcUCI.getCabeceraSupervisionUCI().buscaAreaServiciosTerapiaIntensiva();
            lListaProc=new ArrayList<DetalleSupervisionUCI>(Arrays.asList((oUCI).buscaProcedimientosTerapiaIntenciva(sTablaProc)));
            //lListaAreaServicio= new ArrayList<AreaServicioHRRB>(Arrays.asList((oUCI).buscarServiciosTerapiaIntensiva()));        
        }catch(Exception e){
            Logger.getLogger(EpisodioMedico.class.getName()).log(Level.SEVERE,null,e);
        } 
    }
    
    public void buscaPacienteEnServicio(){
        if(this.getAreaServicio()!=0){
            try {
                oPacSerUCI = oUCI.getEpisodio().buscarPacientesEnServicio(this.getAreaServicio());
                //arrPacHosp = oUCI.getEpisodio().BuscarPacientesPorServicioHopitalizacion(this.getAreaServicio());   
                bDisableHosp = arrPacHosp != null;
                if(oPacSerUCI==null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se encuentran pacientes en el servicio seleccionado"));
                }
                buscaNombreServio(this.getAreaServicio());
            } catch (Exception ex) {
                Logger.getLogger(HojaSupervBuscaPacUCI.class.getName()).log(Level.SEVERE, null, ex);
            }           
        }else{
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Selecciona un servicio"));  
        }        
    }
   
    public void buscarPaciente(long pf){
        try{
            oUCI.getEpisodio().getPaciente().setFolioPaciente(pf);            
            if(BuscarPacienteArreglo(pf)){                                   
                lProcAgregado = new ArrayList<DetalleSupervisionUCI>();
                RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').show()");
                oProcUCI.getProcedimientoEnfermeria().setValor("");
                oProcUCI.getProcedimientoEnfermeriaPor().setDescripcion("");
                oUCI.setObservacionGeneral("");
                oUCI.setObservacionPaciente("");
            }else{
                oUCI = new DetalleSupervisionUCI();
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Paciente no encontrado")); 
            }
        }catch(Exception e){
            Logger.getLogger(EpisodioMedico.class.getName()).log(Level.SEVERE,null,e);
        }        
    }
    
    public boolean BuscarPacienteArreglo(long outF){
        boolean bRtn=false;
        try{
        for(EpisodioMedico oEp: oPacSerUCI){
            if(oEp.getPaciente().getFolioPaciente()==outF){
                oUCI.getEpisodio().getPaciente().setFolioPaciente(oEp.getPaciente().getFolioPaciente());
                oUCI.getEpisodio().getPaciente().setClaveEpisodio(oEp.getPaciente().getClaveEpisodio());
                oUCI.getEpisodio().getPaciente().getExpediente().setNumero(oEp.getPaciente().getExpediente().getNumero());
                oUCI.getEpisodio().getPaciente().setNombres(oEp.getPaciente().getNombres());
                oUCI.getEpisodio().getPaciente().setApPaterno(oEp.getPaciente().getApPaterno());
                oUCI.getEpisodio().getPaciente().setApMaterno(oEp.getPaciente().getApMaterno());
                oUCI.getEpisodio().getCama().setNumero(oEp.getCama().getNumero());
                bRtn=true;
                break;
            }
        }
        }catch(Exception ex){
            Logger.getLogger(HojaSupervBuscaPacUCI.class.getName()).log(Level.SEVERE,null,ex);
        }
        return bRtn;
    }
    
    public void onProceChange(){
        if(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor() !=null && !this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor().equals("")){             
            try{
                lListaProcPor = new ArrayList<ProcedimientoEnfermeriaPor>(Arrays.asList((new ProcedimientoEnfermeriaPor()).buscarProcedimientoEnfermeria(this.oProcUCI.getProcedimientoEnfermeria().getValor())));                
                if(lListaProcPor.isEmpty()){
                    this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().setDescripcion("");
                    bDisable=true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Procedimiento Seleccionado no se puede aplicar por mas "));
                }else{
                    this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().setDescripcion("");
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
        if(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor()!=null
                && !this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor().equals("")){
            if(this.getProcedimientoUCI()==null){
                this.setProceAgregado(new ArrayList<DetalleSupervisionUCI>()); 
                this.getProcedimientoUCI().getProcedimientoEnfermeria().setTipoParametro(sTablaProc);
                this.getProcedimientoUCI().getProcedimientoEnfermeria().setClaveParametro(getBuscarClaveProcedimiento(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor()));
                this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().setClavePor(getBuscarClaveProcedimientoPor(this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion()));
                lProcAgregado.add(this.getProcedimientoUCI());
                oProcUCI = new DetalleSupervisionUCI();
                //System.out.println(" paso 1 " + lProcAgregado.size());
            }else{
                if(lProcAgregado.isEmpty()){
                    if(lListaProcPor.isEmpty()){
                        this.getProcedimientoUCI().getProcedimientoEnfermeria().setTipoParametro(sTablaProc);
                        this.getProcedimientoUCI().getProcedimientoEnfermeria().setClaveParametro(getBuscarClaveProcedimiento(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor()));
                        this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().setClavePor(getBuscarClaveProcedimientoPor(this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion()));
                        lProcAgregado.add(this.getProcedimientoUCI());
                        oProcUCI = new DetalleSupervisionUCI();
                        //System.out.println(" paso 2 " + lProcAgregado.size());
                    }else{
                        if(!lListaProcPor.isEmpty()){
                            if(this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion()!=null
                                    && !this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion().equals("") ){
                                this.getProcedimientoUCI().getProcedimientoEnfermeria().setTipoParametro(sTablaProc);
                                this.getProcedimientoUCI().getProcedimientoEnfermeria().setClaveParametro(getBuscarClaveProcedimiento(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor()));
                                this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().setClavePor(getBuscarClaveProcedimientoPor(this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion()));
                                lProcAgregado.add(this.getProcedimientoUCI());
                                oProcUCI = new DetalleSupervisionUCI();
                                //System.out.println(" paso 2.2 " + lProcAgregado.size());
                            }else{
                                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona como se aplica"));
                            }                            
                        }                        
                    }
                    
                }else{
                    if(lListaProcPor.isEmpty()){
                        if (buscarRepetido(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor())) {                            
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se puede agregar mas del mismo tipo"));                           
                        }else{
                            this.getProcedimientoUCI().getProcedimientoEnfermeria().setTipoParametro(sTablaProc);
                            this.getProcedimientoUCI().getProcedimientoEnfermeria().setClaveParametro(getBuscarClaveProcedimiento(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor()));
                            this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().setClavePor(getBuscarClaveProcedimientoPor(this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion()));
                            lProcAgregado.add(this.getProcedimientoUCI());                           
                            oProcUCI = new DetalleSupervisionUCI();
                            //System.out.println(" paso 3 " + lProcAgregado.size());
                        }                        
                    }else{
                        if(!lListaProcPor.isEmpty()){
                            if(this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion()!=null 
                                    && !this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion().equals("")){
                               if (buscarRepetido(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor())) {                                   
                                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se puede agregar mas del mismo tipo"));                           
                               }else{
                                  this.getProcedimientoUCI().getProcedimientoEnfermeria().setTipoParametro(sTablaProc);
                                  this.getProcedimientoUCI().getProcedimientoEnfermeria().setClaveParametro(getBuscarClaveProcedimiento(this.getProcedimientoUCI().getProcedimientoEnfermeria().getValor()));
                                  this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().setClavePor(getBuscarClaveProcedimientoPor(this.getProcedimientoUCI().getProcedimientoEnfermeriaPor().getDescripcion()));
                                  lProcAgregado.add(this.getProcedimientoUCI());                                  
                                  oProcUCI = new DetalleSupervisionUCI();
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
        for (DetalleSupervisionUCI lRep : lProcAgregado) {
            if (lRep.getProcedimientoEnfermeria().getValor().equals(clave)) {
                srt=true;
                break;
            }
        }        
        return srt;
    }
    
    public String getBuscarClaveProcedimiento(String valor){
        String sClave="";
        for(DetalleSupervisionUCI oParam: lListaProc){
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
    
    public void eliminarProcedimietoAgregado(DetalleSupervisionUCI oDetalle){
        this.lProcAgregado.remove(oDetalle);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Sea Eliminado El Porcedimiento"));        
    }    
    
    public void guardarProcedimientoUCI(){        
        if(!lProcAgregado.isEmpty()){
            try{
                oUCI.getCabeceraSupervisionUCI().getArea().setClave(getAreaServicio());
                oUCI.getProcedimientoEnfermeria().setTipoParametro(sTablaProc);                
                oUCI.setArrProcedimientos(lProcAgregado);
                if(oUCI.getCabeceraSupervisionUCI().getIdCabeceraUCI()==0){
                    if(oUCI.getCabeceraSupervisionUCI().buscarCabeceraSupervision()){
                        if(oUCI.buscarLlaveDetalleSupervision()){                            
                            if(oUCI.insertarPorcedimientoEnfermeriaUCI()>0){
                                RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimiento guardado correctamente"));                        
                            }                       
                        }else{         
                            if(oUCI.insertaDetalleUCIyProcedimiento()>0){
                                RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimiento guardado correctamente"));                        
                            }                            
                        }
                    }else{
                        if(oUCI.insertarCabeceraDetalleProcedimiento()>0){                            
                            RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimiento guardado correctamente"));                         
                        }                        
                    }
                }else if(oUCI.getCabeceraSupervisionUCI().getIdCabeceraUCI()!=0){
                    if(oUCI.buscarLlaveDetalleSupervision()){
                        if(oUCI.insertarPorcedimientoEnfermeriaUCI()>0){
                            RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimiento guardado correctamente"));         
                        }
                    }else{
                        if(oUCI.insertaDetalleUCIyProcedimiento()>0){
                            RequestContext.getCurrentInstance().execute("PF('registrarProcedimiento').hide()");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Procedimiento guardado correctamente"));       
                        }                       
                    }
                                                 
                }
            }catch(Exception e){
                Logger.getLogger(DetalleSupervisionUCI.class.getName()).log(Level.SEVERE,null, e);
            }           
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se puede guadar sin procedimiento"));
        }
        oUCI.setIdSupervision(0);
    }
    
    public void buscaProcedimientosAplicados(){
        try {
            DetalleSupervisionUCI oDe = new DetalleSupervisionUCI();
            oDe.getCabeceraSupervisionUCI().getArea().setClave(this.getAreaServicio());
            arrProcedimientosAplic = oDe.buscaProcedimientosAplicadosPorFechaYServicio();
            if(arrProcedimientosAplic.length>0){
                RequestContext.getCurrentInstance().execute("PF('verdetalles').show()");                
                RequestContext.getCurrentInstance().update("verDetalles:imp");
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se an registrodo procedimientos no hay detalle"));
            }
        } catch (Exception ex) {
            Logger.getLogger(HojaSupervBuscaPacUCI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EpisodioMedico[] getPacientesUCI(){        
        return oPacSerUCI;
    }
    
    public EpisodioMedico[] getPacienteEnServicioHosp(){
        return arrPacHosp;
    }
    
    public boolean getDisableHosp(){        
        return bDisableHosp;
    }    
    
    public DetalleSupervisionUCI getBuscaPaciente(){
        return oUCI;
    }    
    
    public DetalleSupervisionUCI getProcedimientoUCI(){
        return oProcUCI;
    }
    
    public List<DetalleSupervisionUCI> getListaPorcedimientos(){
        return lListaProc;
    }
    
    public List<ProcedimientoEnfermeriaPor> getListaProcedimientoPor(){
        return lListaProcPor;
    }
    public ArrayList<DetalleSupervisionUCI> getProceAgregado(){
        return lProcAgregado;
    }
    public void setProceAgregado(ArrayList<DetalleSupervisionUCI> valor){
        this.lProcAgregado=valor;
    }
    
    public String getServicioUCI(){
        return sServicio;
    }
    
    public String getFechaActual(){
        return sFechaActual;
    }
    
    public boolean getDisable(){
        return bDisable;
    }

    public int getAreaServicio() {
        return nArea;
    }

    public void setAreaServicio(int nArea) {
        this.nArea = nArea;
    }

    public List<AreaServicioHRRB> getListaAreaServicio() {
        return lListaAreaServicio;
    }
    
    public void buscaNombreServio(int nArea){
        for(AreaServicioHRRB oSer: getArrServicio()){
            if(oSer.getClave()==nArea){
                sServicio=oSer.getDescripcion();
                break;
            }
        }
    }  
    
    public DetalleSupervisionUCI[] getArrProcedimientosAplic() {
        return arrProcedimientosAplic;
    }
    
    public ArrayList<AreaServicioHRRB> getArrServicio() {
        return arrServicio;
    }       
}
