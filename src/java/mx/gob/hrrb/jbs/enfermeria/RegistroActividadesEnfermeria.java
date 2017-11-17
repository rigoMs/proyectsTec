
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.enfermeria.CabeceraActividadesEnfermeria;
import mx.gob.hrrb.modelo.enfermeria.DetalleActividadesEnfermeria;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javier
 */
@ManagedBean(name="oRAct")
@ViewScoped
public class RegistroActividadesEnfermeria implements Serializable {
    
    private boolean bRender;
    private boolean bDisable;    
    private String sClaveBusca;
    private DetalleActividadesEnfermeria oDeAct,oActividad;    
    private List<CabeceraActividadesEnfermeria> arrServicios;
    private List<DetalleActividadesEnfermeria> arrAct;
    private Map<String,String> mTipoActividad;
    private ArrayList<Parametrizacion> arrTipoActividad;  
    private DetalleActividadesEnfermeria oTAZ,oTBA,oTBB;
    private DetalleActividadesEnfermeria oBusca,oModifica;
    
    public RegistroActividadesEnfermeria() {
        bRender=false;
        bDisable=true;
        oDeAct= new DetalleActividadesEnfermeria();
        oActividad = new DetalleActividadesEnfermeria();
        mTipoActividad = new HashMap<String, String>();         
        oBusca= new DetalleActividadesEnfermeria();
        oModifica = new DetalleActividadesEnfermeria();
        cargaDatos();        
    }
    
    private void cargaDatos(){
        try{
            arrServicios= new ArrayList<CabeceraActividadesEnfermeria>(Arrays.asList((new CabeceraActividadesEnfermeria()).buscaServiciosTodos()));
            arrAct = new ArrayList<DetalleActividadesEnfermeria>(Arrays.asList((oDeAct).buscaActividades()));
            mTipoActividad.put("ACTIVIDADES ADMINISTRATIVAS ", "TAZ");
            mTipoActividad.put("TÉCNICAS Y PROCEDIMIENTO", "TBA");
            mTipoActividad.put("ACTIVIDADES DOCENTES", "TBB");   
        }catch(Exception e){
            Logger.getLogger(RegistroActividadesEnfermeria.class.getName()).log(Level.SEVERE, null, e);                    
        }
    }
    
    public void buscaNombreServicio(){
        bRender=false;
        if(this.getDetalleAct().getCabeceraAct().getAreaServicio().getClave()!=0){
            for(CabeceraActividadesEnfermeria oCb: arrServicios){
                if(oCb.getAreaServicio().getClave()==this.getDetalleAct().getCabeceraAct().getAreaServicio().getClave()){
                    this.getDetalleAct().getCabeceraAct().getAreaServicio().setDescripcion(oCb.getAreaServicio().getDescripcion());
                    break;
                }
            }
        }     
    }
    
    public void buscaActividadesDelServicioSeleccionado(){        
        boolean bBan=true;
        if(this.getDetalleAct().getCabeceraAct().getAreaServicio().getClave()==0){            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","Selecciona un servicio"));
            bBan=false;        
        }
        if(this.getDetalleAct().getCabeceraAct().getFechaRegistroo()==null){            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","Selecciona una fecha"));
            bBan=false;        
        }
        if(bBan){            
            bRender=true;
            ArrayList<String> update= new ArrayList<String>();
            update.add("reActEnf");
            update.add("reActEnf:pan1");
            update.add("reActEnf:opan2");
            update.add("reActEnf:fec");
            RequestContext.getCurrentInstance().update(update);            
            try{           
                oBusca.getCabeceraAct().getAreaServicio().setClave(this.getDetalleAct().getCabeceraAct().getAreaServicio().getClave());
                oBusca.getCabeceraAct().setFechaRegistroo(this.getDetalleAct().getCabeceraAct().getFechaRegistroo());
                oBusca.BuscaActividadesEnfermeriaRegistradas();               
            }catch(Exception e){
                Logger.getLogger(RegistroActividadesEnfermeria.class.getName()).log(Level.SEVERE, null, e);  
            }
            oTAZ = new DetalleActividadesEnfermeria();
            oTBA = new DetalleActividadesEnfermeria();
            oTBB = new DetalleActividadesEnfermeria();
            this.getDetalleAct().setArrActividades(new ArrayList<DetalleActividadesEnfermeria>());
        }      
    }
    
    public void onActividadChange(){
       if(!this.getClaveBusca().equals("")){
           bDisable=false;
           arrTipoActividad = new ArrayList<Parametrizacion>();
           Parametrizacion oParam=null;           
          for(DetalleActividadesEnfermeria oD:arrAct){
              if(oD.getTipoActividad().getTipoParametro().equals(this.getClaveBusca())){
                  oParam= new Parametrizacion();
                  oParam.setTipoParametro(oD.getTipoActividad().getTipoParametro());
                  oParam.setClaveParametro(oD.getTipoActividad().getClaveParametro());
                  oParam.setValor(oD.getTipoActividad().getValor());                  
                  this.getArrTipoActividad().add(oParam);                  
              }
          }          
       }else{
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","Selecciona una actividad"));
       }
    }
    
    public void buscaNombreActividades(){
        if(!this.getActividad().getTipoActividad().getClaveParametro().equals("")){
            for(Parametrizacion oParam : this.getArrTipoActividad()){
                if(oParam.getClaveParametro().equals(this.getActividad().getTipoActividad().getClaveParametro())){
                    this.getActividad().getTipoActividad().setClaveParametro(oParam.getClaveParametro());
                    this.getActividad().getTipoActividad().setTipoParametro(oParam.getTipoParametro());
                    this.getActividad().getTipoActividad().setValor(oParam.getValor());
                    break;
                }
            }
        }
    }   
    
    public void agregarActividad(){
        if(this.getActividad().getTipoActividad().getClaveParametro().equals("")){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","Selecciona la actividad"));
        }else if(this.getActividad().getCantidadMAT()==0 && this.getActividad().getCantidadVES()==0 && this.getActividad().getCantidadNOC()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","Ingresa una Cantidad"));
        }else{
            if(buscaActividadAGregadasBD()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","La Actividad ya esta agregada ver detalles"));
            }else{
                try{
                    if(this.getClaveBusca().equals("TAZ")){                
                        if(this.getDetalleAct().getArrActividades().isEmpty()){                   
                            oTAZ.setNombreTipoActividad("ADMINISTRATIVAS");
                            buscaNombreActividades();                    
                            oTAZ.getArrActividades().add(this.getActividad());  
                            this.getDetalleAct().getArrActividades().add(oTAZ);
                        }else{
                            if(buscaTipoActividad()){
                                if(buscaRepetidoAdministrativas()){
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se puede Agregar dos veces la misma Actividad Administrativa"));
                                }else{
                                    this.getDetalleAct().getArrActividades().remove(oTAZ);
                                    buscaNombreActividades();                       
                                    oTAZ.getArrActividades().add(this.getActividad());
                                    this.getDetalleAct().getArrActividades().add(oTAZ);
                                }
                            }else{
                                oTAZ.setNombreTipoActividad("ADMINISTRATIVAS");
                                buscaNombreActividades();                        
                                oTAZ.getArrActividades().add(this.getActividad());
                                this.getDetalleAct().getArrActividades().add(oTAZ);
                            }
                        }                                      
                        oActividad= new DetalleActividadesEnfermeria();
                    }else if(this.getClaveBusca().equals("TBA")){                
                        if(this.getDetalleAct().getArrActividades().isEmpty()){                   
                            oTBA.setNombreTipoActividad("TÉCNICAS Y PROCEDIMIENTOS");
                            buscaNombreActividades();                    
                            oTBA.getArrActividades().add(this.getActividad());  
                            this.getDetalleAct().getArrActividades().add(oTBA);
                        }else{
                            if(buscaTipoActividad()){
                                if(buscaRepetidoTecnicasYproce()){
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se puede Agregar dos veces la misma Tecnica o Procedimiento"));
                                }else{
                                    this.getDetalleAct().getArrActividades().remove(oTBA);
                                    buscaNombreActividades();                       
                                    oTBA.getArrActividades().add(this.getActividad());
                                    this.getDetalleAct().getArrActividades().add(oTBA);
                                }
                            }else{
                                oTBA.setNombreTipoActividad("TÉCNICAS Y PROCEDIMIENTOS");
                                buscaNombreActividades();                        
                                oTBA.getArrActividades().add(this.getActividad());
                                this.getDetalleAct().getArrActividades().add(oTBA);
                            }
                        }                                          
                        oActividad= new DetalleActividadesEnfermeria();
                    }else if(this.getClaveBusca().equals("TBB")){                
                        if(this.getDetalleAct().getArrActividades().isEmpty()){                   
                            oTBB.setNombreTipoActividad("ACTIVIDADES DOCENTES");
                            buscaNombreActividades();                    
                            oTBB.getArrActividades().add(this.getActividad());  
                            this.getDetalleAct().getArrActividades().add(oTBB);
                        }else{
                            if(buscaTipoActividad()){
                                if(buscaRepetidoActividadesDocentes()){
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se puede Agregar dos veces la misma Actividad Docente"));
                                }else{
                                    this.getDetalleAct().getArrActividades().remove(oTBB);
                                    buscaNombreActividades();                       
                                    oTBB.getArrActividades().add(this.getActividad());
                                    this.getDetalleAct().getArrActividades().add(oTBB);
                                }
                            }else{
                                oTBB.setNombreTipoActividad("ACTIVIDADES DOCENTES");
                                buscaNombreActividades();                        
                                oTBB.getArrActividades().add(this.getActividad());
                                this.getDetalleAct().getArrActividades().add(oTBB);
                            }
                        }                                
                        oActividad= new DetalleActividadesEnfermeria();                
                    }else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Tipo de Actividad no encontrada"));
                    }
                }catch(Exception e){
                    Logger.getLogger(RegistroActividadesEnfermeria.class.getName()).log(Level.SEVERE, null, e);
                }
            }              
            
        }
    }
    
    public boolean buscaActividadAGregadasBD(){
        boolean bRet=false;
        if(!this.getBusca().getArrActividades().isEmpty()){
            for(DetalleActividadesEnfermeria oBus:this.getBusca().getArrActividades()){
                for(DetalleActividadesEnfermeria oB: oBus.getArrActividades()){
                    if(oB.getTipoActividad().getTipoParametro().equals(this.getClaveBusca())
                            && oB.getTipoActividad().getClaveParametro().equals(this.getActividad().getTipoActividad().getClaveParametro())){
                        bRet=true;
                        break;
                    }
                }
            }
        }
        return bRet;
    }
    
    public boolean buscaTipoActividad(){
        boolean bRet=false;
        for(DetalleActividadesEnfermeria oDeEn: this.getDetalleAct().getArrActividades()){
            for(DetalleActividadesEnfermeria oSu : oDeEn.getArrActividades()){
                if(oSu.getTipoActividad().getTipoParametro().equals(this.getClaveBusca())){
                    bRet=true;
                    break;
                }
            }
            
        }
        return bRet;
    }
    
    public boolean buscaRepetidoAdministrativas(){
        boolean bRet=false;
        for(DetalleActividadesEnfermeria oTaz: oTAZ.getArrActividades()){
            if(oTaz.getTipoActividad().getClaveParametro().equals(this.getActividad().getTipoActividad().getClaveParametro())){
                bRet=true;
                break;
            }
        }
        return bRet;
    }
    
    public boolean buscaRepetidoTecnicasYproce(){
        boolean bRet=false;
        for(DetalleActividadesEnfermeria oTba: oTBA.getArrActividades()){
            if(oTba.getTipoActividad().getClaveParametro().equals(this.getActividad().getTipoActividad().getClaveParametro())){
                bRet=true;
                break;
            }
        }
        return bRet;
    }
    
    public boolean buscaRepetidoActividadesDocentes(){
        boolean bRet=false;
        for(DetalleActividadesEnfermeria oTbb: oTBB.getArrActividades()){
            if(oTbb.getTipoActividad().getClaveParametro().equals(this.getActividad().getTipoActividad().getClaveParametro())){
                bRet=true;
                break;
            }
        }
        return bRet;
    }
    
    public void eliminarActividad(DetalleActividadesEnfermeria oAc){
        for(DetalleActividadesEnfermeria oEl:this.getDetalleAct().getArrActividades()){            
            oEl.getArrActividades().remove(oAc);
            if(oEl.getArrActividades().isEmpty()){ 
                this.getDetalleAct().getArrActividades().remove(oEl);
                break;
            }
                      
        }
        RequestContext.getCurrentInstance().update("tbStActiv:opan2");
    }
    
    public void buscaModificarActividadEnArregloTemporal(String sTip, String sCve){
        for(DetalleActividadesEnfermeria oD:this.getDetalleAct().getArrActividades()){
            for(DetalleActividadesEnfermeria oS: oD.getArrActividades()){
                if(oS.getTipoActividad().getTipoParametro().equals(sTip)
                        && oS.getTipoActividad().getClaveParametro().equals(sCve)){
                    this.getBusca().getTipoActividad().setTipoParametro(oS.getTipoActividad().getTipoParametro());
                    this.getBusca().getTipoActividad().setClaveParametro(oS.getTipoActividad().getClaveParametro());
                    this.getBusca().getTipoActividad().setValor(oS.getTipoActividad().getValor());
                    this.getBusca().setCantidadMAT(oS.getCantidadMAT());
                    this.getBusca().setCantidadVES(oS.getCantidadVES());
                    this.getBusca().setCantidadNOC(oS.getCantidadNOC());
                    break;
                }
            }
        }
        RequestContext.getCurrentInstance().update("reActEnf:msgs");        
    }
    
    public void modificarActividadArregloTemporal(){
        if(this.getBusca().getTipoActividad().getClaveParametro().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","NO hay Actividad"));
        }else if(this.getBusca().getCantidadMAT()==0
                && this.getBusca().getCantidadVES()==0
                && this.getBusca().getCantidadNOC()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No puede termer cantidad en ceros"));
        }else{
            for(DetalleActividadesEnfermeria oDee:this.getDetalleAct().getArrActividades()){
                for(DetalleActividadesEnfermeria oSu:oDee.getArrActividades()){
                    if(oSu.getTipoActividad().getTipoParametro().equals(this.getBusca().getTipoActividad().getTipoParametro())
                            && oSu.getTipoActividad().getClaveParametro().equals(this.getBusca().getTipoActividad().getClaveParametro())){
                        oSu.setCantidadMAT(this.getBusca().getCantidadMAT());
                        oSu.setCantidadVES(this.getBusca().getCantidadVES());
                        oSu.setCantidadNOC(this.getBusca().getCantidadNOC());
                        break;
                    }
                }
            }
            RequestContext.getCurrentInstance().execute("PF('ModAct').hide()");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","Se modifico correctamente"));
        }
        this.getBusca().getTipoActividad().setTipoParametro("");
        this.getBusca().getTipoActividad().setClaveParametro("");
        this.getBusca().getTipoActividad().setValor("");
        this.getBusca().setCantidadMAT(0);
        this.getBusca().setCantidadVES(0);
        this.getBusca().setCantidadNOC(0);
        RequestContext.getCurrentInstance().update("reActEnf:tbStActiv");
        RequestContext.getCurrentInstance().update("reActEnf:msgs");        
    }
    
    public boolean buscaIdCabeceraActividadEnfermeria(){
        boolean bRet=false;
        if(!this.getBusca().getArrActividades().isEmpty()){ 
            for(DetalleActividadesEnfermeria oB: this.getBusca().getArrActividades()){
                if(oB.getArrActividades().size()>0){
                    this.getDetalleAct().getCabeceraAct().setnIdCebeceraActEnf(oB.getArrActividades().get(0).getCabeceraAct().getnIdCebeceraActEnf());
                    System.out.println("se encontro en el arreglo");
                    bRet=true;
                    break;
                }
            } 
            if(!bRet){
                try {
                    bRet= this.getDetalleAct().getCabeceraAct().buscaLLaveCabeceraActividadEnfermeria();
                    System.out.println("busba en base ");
                } catch (Exception ex) {
                    Logger.getLogger(RegistroActividadesEnfermeria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
           this.getDetalleAct().getCabeceraAct().setnIdCebeceraActEnf(0); 
        }
        return bRet;
    }
    
    public void guardarActividades() {
        ArrayList<String> update= new ArrayList<String>();
        if(this.getDetalleAct().getArrActividades().isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Faltan agregar actividades"));
        }else{
            try {
                if(buscaIdCabeceraActividadEnfermeria()){
                    if(this.getDetalleAct().insertarDetalleActividadesEnfermeria()>0){
                        this.getDetalleAct().setArrActividades(new ArrayList<DetalleActividadesEnfermeria>());
                        oTAZ = new DetalleActividadesEnfermeria();
                        oTBA = new DetalleActividadesEnfermeria();
                        oTBB = new DetalleActividadesEnfermeria();                        
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Se guardo correctamente"));                                            
                    }
                }else{
                    if(this.getDetalleAct().insertaCabeceraDetalleActividadesEnfermeria()>0){
                        this.getDetalleAct().setArrActividades(new ArrayList<DetalleActividadesEnfermeria>());
                        oTAZ = new DetalleActividadesEnfermeria();
                        oTBA = new DetalleActividadesEnfermeria();
                        oTBB = new DetalleActividadesEnfermeria();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Se guardo correctamente"));                        
                    }                     
                }
            } catch (Exception ex) {
                Logger.getLogger(RegistroActividadesEnfermeria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        RecargarActiviadesAgregadas();        
        update.add("reActEnf");
        update.add("reActEnf:msgs");
        update.add("reActEnf:opan2");
        update.add("reActEnf:tabla2");
        update.add("reActEnf:tbStActiv2");
        RequestContext.getCurrentInstance().update(update);
    }
  
    public void RecargarActiviadesAgregadas(){
        try {
            oBusca.getCabeceraAct().getAreaServicio().setClave(this.getDetalleAct().getCabeceraAct().getAreaServicio().getClave());
            oBusca.getCabeceraAct().setFechaRegistroo(this.getDetalleAct().getCabeceraAct().getFechaRegistroo());
            oBusca.BuscaActividadesEnfermeriaRegistradas();
        } catch (Exception ex) {
            Logger.getLogger(RegistroActividadesEnfermeria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaActividadModificarAgregadas(String sTipo, String sCve){
        for(DetalleActividadesEnfermeria oD: this.getBusca().getArrActividades()){
            for(DetalleActividadesEnfermeria oS : oD.getArrActividades()){
                if(oS.getTipoActividad().getTipoParametro().equals(sTipo) 
                        && oS.getTipoActividad().getClaveParametro().equals(sCve)){
                    this.getModifica().getTipoActividad().setTipoParametro(oS.getTipoActividad().getTipoParametro());
                    this.getModifica().getTipoActividad().setClaveParametro(oS.getTipoActividad().getClaveParametro());
                    this.getModifica().getTipoActividad().setValor(oS.getTipoActividad().getValor());
                    this.getModifica().setCantidadMAT(oS.getCantidadMAT());
                    this.getModifica().setCantidadVES(oS.getCantidadVES());
                    this.getModifica().setCantidadNOC(oS.getCantidadNOC());
                    break;
                }
            }
        }
    }
    
    public void modificarActividadesAgregadas(){
        if(this.getModifica().getTipoActividad().getClaveParametro().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","NO hay Actividad"));
        }else if(this.getModifica().getCantidadMAT()==0
                && this.getModifica().getCantidadVES()==0
                && this.getModifica().getCantidadNOC()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","NO  se puede moodificar con cantidad en ceros"));
        }else{
            for(DetalleActividadesEnfermeria oD: this.getBusca().getArrActividades()){
                for(DetalleActividadesEnfermeria oS : oD.getArrActividades()){
                    if(oS.getTipoActividad().getTipoParametro().equals(this.getModifica().getTipoActividad().getTipoParametro()) 
                            && oS.getTipoActividad().getClaveParametro().equals(this.getModifica().getTipoActividad().getClaveParametro())){
                        oS.setCantidadMAT(this.getModifica().getCantidadMAT());
                        oS.setCantidadVES(this.getModifica().getCantidadVES());
                        oS.setCantidadNOC(this.getModifica().getCantidadNOC());
                        break;
                    }
                }
        }
            RequestContext.getCurrentInstance().execute("PF('ModActAgregada').hide()");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","Modificacion temporal correctamente"));
        }
        RequestContext.getCurrentInstance().update("reActEnf:tabla2");
        RequestContext.getCurrentInstance().update("reActEnf:tbStActiv2");   
        RequestContext.getCurrentInstance().update("reActEnf:msgs");   
    }
    
    public void guardaModificacionActividadesEnfermeria(){
        try { 
            boolean bBan=false;
            for(DetalleActividadesEnfermeria oD:this.getBusca().getArrActividades()){
                if(oD.getArrActividades().size()>0){
                    bBan=true;
                    break;
                }
            }
            if(bBan){
                buscaIds();
                if(this.getBusca().modificarActividadesEnfermeriaCabeceraDetalle()>0){
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","Modificacion  correctamente"));
                }               
            }else{
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR","No se puede mofificar")); 
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistroActividadesEnfermeria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaIds(){
        for(DetalleActividadesEnfermeria oB: this.getBusca().getArrActividades()){
            if(oB.getArrActividades().size()>0){                
                this.getBusca().getCabeceraAct().setnIdCebeceraActEnf(oB.getArrActividades().get(0).getCabeceraAct().getnIdCebeceraActEnf());
             break;   
            }
        }
    }
    
    public DetalleActividadesEnfermeria getDetalleAct() {
        return oDeAct;
    }

    public List<CabeceraActividadesEnfermeria> getArrServicios() {
        return arrServicios;
    } 

    public List<DetalleActividadesEnfermeria> getArrActividades() {
        return arrAct;
    }

    public String getClaveBusca() {
        return sClaveBusca;
    }

    public void setClaveBusca(String sClaveBusca) {
        this.sClaveBusca = sClaveBusca;
    }

    public Map<String,String> getTipoActividad() {
        return mTipoActividad;
    }

    public ArrayList<Parametrizacion> getArrTipoActividad() {
        return arrTipoActividad;
    }

    public boolean isDisable() {
        return bDisable;
    } 

    public DetalleActividadesEnfermeria getActividad() {
        return oActividad;
    }

    public boolean isRender() {
        return bRender;
    }

    public DetalleActividadesEnfermeria getBusca() {
        return oBusca;
    }
    
    public DetalleActividadesEnfermeria getModifica() {
        return oModifica;
    }
}
