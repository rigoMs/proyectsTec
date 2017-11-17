
package mx.gob.hrrb.jbs.enfermeria;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.enfermeria.HojaDeTransfusion;
import mx.gob.hrrb.modelo.enfermeria.SignosVitalesEnTransfusion;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 * @objetivo : agregar transfusion a paciente 
 * @author Javier
 * @version 1.0
 */
@ManagedBean(name="oDatosTrans")
@ViewScoped
public class HojaDeTransfusionAgregar implements Serializable {
    
    private boolean bVisible=false;
    private boolean bRender; 
    private String sServicio;
    private String sOpe;   
    private String sFechaActual;
    private EpisodioMedico oEpisodio;            
    private HojaDeTransfusion oTransfusion; // contiene a paciente 
    private ArrayList<SignosVitalesEnTransfusion> lListaSignosAgregados; // array donde se agregan los signos
    private SignosVitalesEnTransfusion  oSignosTrans;   //para agregar al array    
    //private static final String sTablaRevisado="TAI";    
    private HojaDeTransfusion oModificar;// instancia para modificar 
    private HojaDeTransfusion oStdHem;
    private ArrayList<SignosVitalesEnTransfusion> lListaSignos;//buscada desde la base
    private HojaDeTransfusion[] arrStdHem;    
    private HojaDeTransfusion[] oTranAgregadaBase;
    
    public HojaDeTransfusionAgregar() {
        Date dFechaActual = new Date();        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        sFechaActual = format.format(dFechaActual);
        oSignosTrans = new SignosVitalesEnTransfusion();        
        oTransfusion= new HojaDeTransfusion(); 
        oStdHem= new HojaDeTransfusion();
        oModificar = new HojaDeTransfusion();       
    }   
    
    public void buscarSolicitudHemoderivado(){
        try{
            oStdHem.getEpisodio().getPaciente().setFolioPaciente(oTransfusion.getEpisodio().getPaciente().getFolioPaciente());
            oStdHem.getEpisodio().getPaciente().setClaveEpisodio(oTransfusion.getEpisodio().getPaciente().getClaveEpisodio());
            arrStdHem= oStdHem.buscaSolicitudHemoderivadoPaciente();
            if(arrStdHem.length>0){
                bVisible=true;
                RequestContext.getCurrentInstance().execute("PF('ConSolicitudHemoderiv').show()");
            }else{
                bVisible=false;
                String sMens="El Paciente "+oTransfusion.getEpisodio().getPaciente().getNombreCompleto()+" no tiene una solicitud de sangre";
                RequestContext.getCurrentInstance().showMessageInDialog( new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA",sMens));                
            }
        }catch(Exception e){
            e.printStackTrace();            
        }
    } 
    
    public void CargaPacienteEvent( long fp, long cvEp){
        try{
            oTransfusion.getEpisodio().getPaciente().setFolioPaciente(fp);
            oTransfusion.getEpisodio().getPaciente().setClaveEpisodio(cvEp);
            bRender = oTransfusion.getEpisodio().buscarDatosPaciente();            
            if(bRender==true){
                oStdHem.getEpisodio().getPaciente().setFolioPaciente(fp);
                oStdHem.getEpisodio().getPaciente().setClaveEpisodio(cvEp);
                oTranAgregadaBase = oStdHem.buscarTransfunsionesPaciente();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO"," paciente encontrado"));
            }else{
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Paciente no encontrado")); 
            }
            
        }catch(Exception e){            
            java.util.logging.Logger.getLogger(HojaDeTransfusion.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
 
    public void AgregarSignosEvento(ActionEvent e) throws Exception{
        agregarSignosVitales();
    }
    
    public void agregarSignosVitales(){
       
        String sRtn="";
        if(this.getSignosVitales().getTipoRevisado().getValor().equals("")){
              sRtn+="01";
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona Tipo Revisado"));
        }
        if(this.getSignosVitales().getTa().equals("")){
            sRtn+=" 02";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Captura la tension arterial"));
        }
        if(this.getSignosVitales().getFc().equals("")){
             sRtn+="03";
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Captura la frecuencia cardiaca"));
        }
        if(this.getSignosVitales().getTemp().equals("")){
            sRtn+="04";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Captura la Temperatura"));
        }
        if(this.getSignosVitales().getFr().equals("")){
            sRtn+="05";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Captura la Frecuencia Respiratoria"));
        }
        
        if(sRtn.equals("")){
             if(this.sOpe.equals("a")){           
                if(!this.getSignosVitales().getTipoRevisado().getValor().equals("")
                        && !this.getSignosVitales().getTa().equals("") 
                        && !this.getSignosVitales().getFc().equals("")
                        && !this.getSignosVitales().getTemp().equals("")
                        && !this.getSignosVitales().getFr().equals("")){

                    if(lListaSignosAgregados.isEmpty()){
                        lListaSignosAgregados.add(this.getSignosVitales());                        
                        oSignosTrans= new SignosVitalesEnTransfusion(); 
                        RequestContext.getCurrentInstance().update("agregaTras:select");
                        RequestContext.getCurrentInstance().update("agregaTras:TencionArterial1");
                        RequestContext.getCurrentInstance().update("agregaTras:frecCard1"); 
                        RequestContext.getCurrentInstance().update("agregaTras:temp1"); 
                        RequestContext.getCurrentInstance().update("agregaTras:frecResp1"); 
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO"," Revision de Signos Agregado"));
                    }else if(!lListaSignosAgregados.isEmpty()){
                          if(buscarRepetidosTipoRevisado(this.getSignosVitales().getTipoRevisado().getValor())){
                              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","El tipo de Revision ya se encuentra "+this.getSignosVitales().getTipoRevisado().getValor()));
                          }else{                              
                              lListaSignosAgregados.add(this.getSignosVitales());                             
                              oSignosTrans= new SignosVitalesEnTransfusion();
                              RequestContext.getCurrentInstance().update("agregaTras:select");
                              RequestContext.getCurrentInstance().update("agregaTras:TencionArterial1");
                              RequestContext.getCurrentInstance().update("agregaTras:frecCard1"); 
                              RequestContext.getCurrentInstance().update("agregaTras:temp1"); 
                              RequestContext.getCurrentInstance().update("agregaTras:frecResp1"); 
                              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO"," Revision de Signos Agregado"));
                          }
                    }
                }        
             }else if(this.sOpe.equals("m")){
                 if(this.getModificar().getFolio()!=0){
                     if(!this.getSignosVitales().getTipoRevisado().getValor().equals("")
                        && !this.getSignosVitales().getTa().equals("") 
                        && !this.getSignosVitales().getFc().equals("")
                        && !this.getSignosVitales().getTemp().equals("")
                        && !this.getSignosVitales().getFr().equals("")){
                         if(buscarRepetidoArregloBase(this.getSignosVitales().getTipoRevisado().getValor())){
                             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR", this.getSignosVitales().getTipoRevisado().getValor()+", No se puede agregar ya se encuenta agregado"));
                         }else{
                             ArrayList<SignosVitalesEnTransfusion> vTemp= new ArrayList<SignosVitalesEnTransfusion>();
                             vTemp.add(this.getSignosVitales());
                             String sTipoRev= this.getSignosVitales().getTipoRevisado().getValor();
                             oSignosTrans = new SignosVitalesEnTransfusion();                           
                             SignosVitalesEnTransfusion oSigAgreBase = new SignosVitalesEnTransfusion();
                             oSigAgreBase.setHojaDeTransfusion(new HojaDeTransfusion());                            
                             oSigAgreBase.getHojaDeTransfusion().setFolio(this.getModificar().getFolio());
                             try {
                                 if(oSigAgreBase.insertarSignosTransfusion(vTemp)>0){
                                     lListaSignos = oModificar.buscaSignosVitalesEnTransfusion(this.getModificar().getFolio());
                                     oStdHem.getEpisodio().getPaciente().setFolioPaciente(oTransfusion.getEpisodio().getPaciente().getFolioPaciente());
                                     oStdHem.getEpisodio().getPaciente().setClaveEpisodio(oTransfusion.getEpisodio().getPaciente().getClaveEpisodio());
                                     oTranAgregadaBase = oStdHem.buscarTransfunsionesPaciente();
                                     RequestContext.getCurrentInstance().update("frmTras:tablaTransfusiones");
                                     RequestContext.getCurrentInstance().update("modTras:select2");
                                     RequestContext.getCurrentInstance().update("modTras:TencionArterial");
                                     RequestContext.getCurrentInstance().update("modTras:fc1");
                                     RequestContext.getCurrentInstance().update("modTras:temp1");
                                     RequestContext.getCurrentInstance().update("modTras:fr1");
                                     RequestContext.getCurrentInstance().update("modTras:tblSignosAgre2");
                                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION", sTipoRev+", Agregado correctamente"));
                                 }
                             } catch (Exception ex) {
                                 Logger.getLogger(HojaDeTransfusionAgregar.class.getName()).log(Level.SEVERE, null, ex);
                             }
                             
                         }                        
                     }                
                 }                
             }       
        }        
    }
    
    public boolean buscarRepetidosTipoRevisado(String valor){
        boolean bRtn= false;
        for(SignosVitalesEnTransfusion oSig : lListaSignosAgregados){
            if(oSig.getTipoRevisado().getValor().equals(valor)){
                bRtn=true;
                break;
            }
        }
        return bRtn;
    }
    
    public boolean buscarRepetidoArregloBase(String sValor){
        boolean bRt=false;
        for(SignosVitalesEnTransfusion oSt: lListaSignos){
            if(oSt.getTipoRevisado().getValor().equals(sValor)){
                bRt=true;
                break;                 
            }
        }        
        return bRt;
    }
    
    public void EliminarSignos(SignosVitalesEnTransfusion obj){
        this.lListaSignosAgregados.remove(obj);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Se Elimino la Revision"));
    }       
    
    public void instanciarArrayList(HojaDeTransfusion oHoja){
       lListaSignosAgregados = new ArrayList<SignosVitalesEnTransfusion>();
       try {
               oTransfusion.getTipoUnidad().setValor(oHoja.getTipoUnidad().getValor());
               oTransfusion.getTipoUnidad().setClaveParametro(oHoja.getTipoUnidad().getClaveParametro());
               oTransfusion.getTipoUnidad().setTipoParametro(oHoja.getTipoUnidad().getTipoParametro());
               oTransfusion.getTipoFactor().setValor(oHoja.getTipoFactor().getValor());
               oTransfusion.getTipoFactor().setClaveParametro(oHoja.getTipoFactor().getClaveParametro());
               oTransfusion.getTipoFactor().setTipoParametro(oHoja.getTipoFactor().getTipoParametro());
               oTransfusion.setIdServicioRealizado(oHoja.getIdServicioRealizado());               
               RequestContext.getCurrentInstance().execute("PF('ConSolicitudHemoderiv').hide()");               
           } catch (Exception ex) {
               Logger.getLogger(HojaDeTransfusionAgregar.class.getName()).log(Level.SEVERE, null, ex);
           }
       
        oTransfusion.setNoUnidad(0);      
        oTransfusion.setHoraIT(null);
        oTransfusion.setVolumenTras(0);
        oTransfusion.setHoraFT(null);
        oTransfusion.setObsGenerales("");
        oSignosTrans = new SignosVitalesEnTransfusion();
        ArrayList<String>update = new ArrayList<String>();
        update.add("agregaTras:tipoUnidad1");
        update.add("agregaTras:tipoParametro1");
        update.add("agregaTras:horaIT");
        update.add("agregaTras:TencionArterial1");
        update.add("agregaTras:frecCard1");
        update.add("agregaTras:temp1");
        update.add("agregaTras:frecResp1");
        update.add("agregaTras:horaFT");
        update.add("agregaTras:volumenTrans");
        update.add("agregaTras:observ");
        RequestContext.getCurrentInstance().execute("PF('agregar').show()");    
        RequestContext.getCurrentInstance().update(update);
        

    }
    
    public void guargarTransfusionPorEvento(ActionEvent event){
        try {
            guardarTransfusion();
        } catch (Exception ex) {
            Logger.getLogger(HojaDeTransfusionAgregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardarTransfusion(){
        if(!lListaSignosAgregados.isEmpty() ){
            if(this.getTransfusionDatos().getNoUnidad()>0){            
            try {                               
                oTransfusion.getTurno().setClave(getDeterminaTurno());
                SignosVitalesEnTransfusion oSig= new SignosVitalesEnTransfusion();                   
                oSig.setHojaDeTransfusion(oTransfusion);                   
                if(oSig.insertarTransfusionYSignosVistales(lListaSignosAgregados)>0){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Se guardo correctamente"));
                    oTransfusion.setNoUnidad(0);
                    oTransfusion.getTipoUnidad().setValor("");
                    oTransfusion.getTipoFactor().setValor("");
                    oTransfusion.setHoraIT(null);
                    oTransfusion.setVolumenTras(0);
                    oTransfusion.setHoraFT(null);
                    oTransfusion.setObsGenerales("");
                    oSignosTrans = new SignosVitalesEnTransfusion();
                    oStdHem.getEpisodio().getPaciente().setFolioPaciente(oTransfusion.getEpisodio().getPaciente().getFolioPaciente());
                    oStdHem.getEpisodio().getPaciente().setClaveEpisodio(oTransfusion.getEpisodio().getPaciente().getClaveEpisodio());
                    oTranAgregadaBase = oStdHem.buscarTransfunsionesPaciente();
                    RequestContext.getCurrentInstance().execute("PF('agregar').hide()");

                }else{
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","no se pudo guaradar la transfusion")); 
                }
            } catch (Exception ex) {
                Logger.getLogger(HojaDeTransfusionAgregar.class.getName()).log(Level.SEVERE, null, ex);
            }  
            }else{
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","El Numero de unidad Debe ser mayor a 0"));   
            }
                        
        }else{                        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No tienes Revision de signos agregados"));            
        }
    }
    
    public void buscandoDatosTransfusion(long nIdTransfusion){
        try{
            ArrayList<String> update= new ArrayList<String>();
            update.add("modTras");
            update.add("modTras:fechaini");
            update.add("modTras:fechafin");
            oModificar.setFolio(nIdTransfusion);
            if(oModificar.buscaUnaTransfusion()){
                lListaSignos = oModificar.buscaSignosVitalesEnTransfusion(nIdTransfusion);
                oSignosTrans = new SignosVitalesEnTransfusion();                
                RequestContext.getCurrentInstance().update(update);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Abrindo para Modificar"));
                RequestContext.getCurrentInstance().execute("PF('modificar').show()"); 
            }
        }catch(Exception e){
            e.printStackTrace();
        }         
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modificacion Correcta", ((SignosVitalesEnTransfusion) event.getObject()).getTipoRevisado().getValor());
        FacesContext.getCurrentInstance().addMessage(null, msg);     
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modificacion Cancelada", ((SignosVitalesEnTransfusion) event.getObject()).getTipoRevisado().getValor());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void modificarTransfusionEvento(ActionEvent event) {
        modificarTransfusion();
    }
    
    public void modificarTransfusion(){      
        if(this.getModificar().getNoUnidad()!=0){
            if(this.getModificar().getHoraFT()!=null){
                if(this.getModificar().getVolumenTras()!=0){
                    try {
                         SignosVitalesEnTransfusion oSg= new SignosVitalesEnTransfusion();
                         oSg.setHojaDeTransfusion(oModificar);
                         if(oSg.modificarTransfucionYSignosVitales(lListaSignos)>0){
                             oModificar.setHoraFT(null);
                             oModificar.setHoraIT(null);
                             oModificar.setVolumenTras(0);
                             oModificar.setObsGenerales("");
                             oModificar.setFolio(0);
                             oStdHem.getEpisodio().getPaciente().setFolioPaciente(oTransfusion.getEpisodio().getPaciente().getFolioPaciente());
                             oStdHem.getEpisodio().getPaciente().setClaveEpisodio(oTransfusion.getEpisodio().getPaciente().getClaveEpisodio());
                             oTranAgregadaBase = oStdHem.buscarTransfunsionesPaciente();
                             RequestContext.getCurrentInstance().execute("PF('modificar').hide()");
                             RequestContext.getCurrentInstance().update("frmTras:tablaTransfusiones");
                             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Modificacion exitosa "));
                         }    
                     } catch (Exception ex) {
                         Logger.getLogger(HojaDeTransfusionAgregar.class.getName()).log(Level.SEVERE, null, ex);
                     }  
                }else
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","EL volumen Transfundido debe ser mayor a cero"));
                }else
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","falta la hora de termino de la transfusión"));
            
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR"," El numero de unidad debe ser mayor a cero"));
        }
    }
    
    public String getDeterminaTurno(){ 
        Date dFechaActual2= new Date();
        SimpleDateFormat hf= new SimpleDateFormat("HH.mm");
         String sTurno="";
         String sT=hf.format(dFechaActual2);
         Double horaActual=Double.parseDouble(sT);
         sTurno= (horaActual>=07.00 && horaActual<15.00)?"MAT": (horaActual >= 15.00 && horaActual < 21.00)?"VES":"NOC";        
         return sTurno;
    }     
  
    public HojaDeTransfusion getTransfusionDatos(){
        return oTransfusion;
    }
    
    public SignosVitalesEnTransfusion getSignosVitales(){
        return oSignosTrans;
    }
    
    public String getFechaActual(){
        return sFechaActual;
    }
    
    public ArrayList<SignosVitalesEnTransfusion> getListaSignos(){
        return lListaSignosAgregados;
    }      
   
    public HojaDeTransfusion getModificar(){
        return oModificar;
    }
    
    public boolean getRenderizar(){
        return bRender;
    }
    
    public void setRenderizar(boolean valor){
        this.bRender=valor;
    }
    
    public ArrayList<SignosVitalesEnTransfusion> getListaSignosBase(){
        return lListaSignos;
    }
    
    public String getOperacion(){
        return sOpe;
    }
    
    public void setOperacion(String v){
        this.sOpe=v;
    }   
    public boolean getVisible() {
        return bVisible;
    }

    public HojaDeTransfusion[] getSolicitudHemPac() {
        return arrStdHem;
    }

    public HojaDeTransfusion[] getTranAgregadaBase() {
        return oTranAgregadaBase;
    }

    public void setTranAgregadaBase(HojaDeTransfusion[] oTranAgregadaBase) {
        this.oTranAgregadaBase = oTranAgregadaBase;
    }
    
     
   
}
