package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.MedicamentoAplicado;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.enfermeria.ActividadesRealizadasQx;
import mx.gob.hrrb.modelo.enfermeria.ActividadesRecuperacion;
import mx.gob.hrrb.modelo.enfermeria.ControlLiquidos;
import mx.gob.hrrb.modelo.enfermeria.Hemoderivados;
import mx.gob.hrrb.modelo.enfermeria.HojaEnfermeriaQuirofano;
import mx.gob.hrrb.modelo.enfermeria.reporte.MustraSignosVitalesHojaQx;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Javier
 */
@ManagedBean(name="oHojaEnfQx")
@ViewScoped
public class HojaEnfermeriaQx implements Serializable {

    
    private boolean bRender ;   
    private boolean bRenderTblControl;
    private String sFechaActual;
    private String sServicio; 
    private Date dFechaActual;
    private HojaEnfermeriaQuirofano oHojaQx;    
    private Turno oTurno;
    private ActividadesRealizadasQx oActividades;
    private MedicamentoAplicado oSolucion;
    private Hemoderivados oHemoderivadoAplicado; 
    private ControlLiquidos oControlLiquido;
    private ActividadesRecuperacion oActRecuperacion;     
    private MedicamentoAplicado oMedicamentoAplicado;   
    private ArrayList<MustraSignosVitalesHojaQx> arrMustraSignos;
    private MedicamentoAplicado arrMedicamentoRecetado[];
    private MedicamentoAplicado arrMedicamentoSoluciones[];
    private Parametrizacion arrVia[];
    private Turno[] arrTurno=null; 
    private Hemoderivados[] arreHemosAp;
    
    private ArrayList<HojaEnfermeriaQuirofano> arrMedicoAyudante;
    private ArrayList<HojaEnfermeriaQuirofano> arrInstrumentista;
    private ArrayList<HojaEnfermeriaQuirofano> arrCirculantes;
    
    public HojaEnfermeriaQx() {
        dFechaActual = new Date();
        oHojaQx= new HojaEnfermeriaQuirofano();
        oTurno= new Turno();
        oActividades= new ActividadesRealizadasQx();                            
        oSolucion = new MedicamentoAplicado();               
        oHemoderivadoAplicado = new Hemoderivados();               
        oControlLiquido= new ControlLiquidos();                
        oActRecuperacion= new ActividadesRecuperacion();                
        arrMustraSignos= new ArrayList<MustraSignosVitalesHojaQx>();
        oMedicamentoAplicado = new MedicamentoAplicado();              
        signosCarga();
    } 
    
    public void cargarPaciente(Paciente oP){
        try{
            oHojaQx.getEpisodioMedico().setPaciente(oP);
            bRender = oHojaQx.buscaPacienteRegistrarHojaQx();            
            if(bRender!=true){               
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","AL Paciente "+oHojaQx.getEpisodioMedico().getPaciente().getNombreCompleto()+" no se le pueda registrar Hoja Enfermeria Quirúrgica")); 
            }else{
                arrMedicoAyudante= oHojaQx.buscaAyudanteInstrumentistaCirculante((short)1);
                arrInstrumentista=oHojaQx.buscaAyudanteInstrumentistaCirculante((short)2);
                arrCirculantes=oHojaQx.buscaAyudanteInstrumentistaCirculante((short)3);
                arrTurno = oTurno.buscarTurnosRequeridos();
                Medicamento oMe = new Medicamento();  
                MedicamentoAplicado oMedAplic = new MedicamentoAplicado();
                arrMedicamentoRecetado = (oMedAplic.buscarMedicamentoDiferentesDeSoluciones(dFechaActual,oHojaQx.getEpisodioMedico().getPaciente().getFolioPaciente(),oHojaQx.getEpisodioMedico().getPaciente().getClaveEpisodio()));          
                arrMedicamentoSoluciones=(oMedAplic.buscarMedicamentoConDeSoluciones(dFechaActual,oHojaQx.getEpisodioMedico().getPaciente().getFolioPaciente(),oHojaQx.getEpisodioMedico().getPaciente().getClaveEpisodio()));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Paciente para registrar la Hoja"));
            }
           
        }catch (Exception e){
         java.util.logging.Logger.getLogger(HojaEnfermeriaQuirofano.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void agregarActividadesEnQx(){        
        if(this.getActividades().getHoraActividad()!=null){
            this.getHojaQx().getArrActividadesRealizadasQx().add(oActividades);
            
            oActividades= new ActividadesRealizadasQx();
            RequestContext.getCurrentInstance().update("ActAgre");
            RequestContext.getCurrentInstance().execute("PF('agregarActividad').hide()");
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info","Faltan datos"));
        }     
    }
    
    public void eliminarActividad(ActividadesRealizadasQx Ac){
        this.getHojaQx().getArrActividadesRealizadasQx().remove(Ac);        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Actividad ELiminada"));
    }
    
    public void agregarSoluciones(){
        boolean bRt=false;
        if(this.getSolucion().getMedicamento().getClaveMedicamento()==null){
            bRt=true;
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta la Solucion"));
        }
        if(this.getSolucion().getCantAplicada()==0){
            bRt=true;
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta la Cantidad"));
        }
        if(this.getSolucion().getFechaAplicacion()==null){
            bRt=true;
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta la hora de Aplicacion"));
        }
        
        if(bRt==false){
           buscarClavesMedicamentoSoluciones(this.getSolucion().getMedicamento().getClaveMedicamento());
           this.getHojaQx().getArrSoluciones().add(oSolucion);          
           oSolucion= new MedicamentoAplicado();
           RequestContext.getCurrentInstance().update("frm_solucion");
           RequestContext.getCurrentInstance().execute("PF('agregarSoluc').hide()"); 
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","Solucion Agregada "));            
        }
       
    }
    
    public void eliminaSolucion(MedicamentoAplicado obj){
        this.getHojaQx().getArrSoluciones().remove(obj);        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Solucion ELiminada"));
    }
    
    public void buscarHemoderivados(){
        try {            
            arreHemosAp= oHemoderivadoAplicado.buscaSolicitudHemoderivadoPaciente(oHojaQx.getEpisodioMedico().getPaciente().getFolioPaciente(),
                    oHojaQx.getEpisodioMedico().getPaciente().getClaveEpisodio());
        } catch (Exception ex) {
            Logger.getLogger(HojaEnfermeriaQx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void agregarHemoderivados(){
        boolean bRtn=false;
        if(this.getHemoderivadoAplicado().getIdDetalleSol()==0){
            bRtn=true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta el producto hemoderivado"));
        }
        if(this.getHemoderivadoAplicado().getCantidad()==0){
            bRtn=true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta la Cantidad"));
        }
        if(this.getHemoderivadoAplicado().getHoraAplicacion()==null){
            bRtn=true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta la hora de Aplicacion"));
        }
        
        if(bRtn==false){
            buscaNombreHemoderivado();        
            this.getHojaQx().getArrHemoderivados().add(oHemoderivadoAplicado);           
            oHemoderivadoAplicado= new Hemoderivados();
            RequestContext.getCurrentInstance().update("frm_hemoderivado");
            RequestContext.getCurrentInstance().execute("PF('agregarHermod').hide()");  
        }
      
    }
    
    public void buscaNombreHemoderivado(){
         for(Hemoderivados oh: this.arreHemosAp){
                if(oh.getIdDetalleSol()==this.getHemoderivadoAplicado().getIdDetalleSol()){
                    this.getHemoderivadoAplicado().setClaveHemoderivado(oh.getClaveHemoderivado());
                    this.getHemoderivadoAplicado().setDescripcionHem(oh.getDescripcionHem());
                    this.getHemoderivadoAplicado().getTipoSangre().setValor(oh.getTipoSangre().getValor());
                    this.getHemoderivadoAplicado().getTipoRH().setValor(oh.getTipoRH().getValor());   
                    break;
                }
                
            } 
    }    
    
    public void eliminarHemoderivado(Hemoderivados oElmHem){
        this.getHojaQx().getArrHemoderivados().remove(oElmHem);       
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Hemoderivado ELiminado"));
    }
    
    public void agregarControlLiquido(){
        String sBan="";
        if(this.getControlLiquido().getTipoControl().getValor().equals("")){
            sBan="01";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Faltan el Servicio"));          
        }
        if(this.getControlLiquido().getTotalEgresos()==0){
            sBan="02";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Faltan el Total de Egresos")); 
        }
        if(this.getControlLiquido().getSangrado()==0){
            sBan="03";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Faltan Sangrado")); 
        }
         if(this.getControlLiquido().getDiuresis()==0){
            sBan="04";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Faltan Deuresis")); 
        }
        
        if(sBan.equals("")){
            if(this.getHojaQx().getArrControlLiquidos().isEmpty()){
                this.getControlLiquido().getBuscaClave(oControlLiquido.getTipoControl().getValor());
                this.getHojaQx().getArrControlLiquidos().add(oControlLiquido);                
                bRenderTblControl=true;
                oControlLiquido = new ControlLiquidos();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Agregado"));
            }else if(!this.getHojaQx().getArrControlLiquidos().isEmpty()){
                if(!buscaRepetidoControlLiquidos(this.getControlLiquido().getTipoControl().getValor())){ 
                      if(!buscaNoAgregarDosServiciosSalasQx(this.getControlLiquido().getTipoControl().getValor())){
                          this.getControlLiquido().getBuscaClave(oControlLiquido.getTipoControl().getValor());
                          this.getHojaQx().getArrControlLiquidos().add(oControlLiquido);  
                          bRenderTblControl=true;
                          oControlLiquido = new ControlLiquidos();
                          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Agregado")); 
                      }else{
                         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR"," No se Puede agregar mas de dos servicios")); 
                      }        
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR", this.getControlLiquido().getTipoControl().getValor()+" Ya esta Agregado"));
                }
            }           
            RequestContext.getCurrentInstance().update("frm-hojaEnferiaQuirurgica:tabHojaQx:sltCtr");
            RequestContext.getCurrentInstance().update("frm-hojaEnferiaQuirurgica:tabHojaQx:tlSangre");
            RequestContext.getCurrentInstance().update("frm-hojaEnferiaQuirurgica:tabHojaQx:sangrado");
            RequestContext.getCurrentInstance().update("frm-hojaEnferiaQuirurgica:tabHojaQx:deuresis");
            RequestContext.getCurrentInstance().update("frm-hojaEnferiaQuirurgica:tabHojaQx:otros");   
        }
    }
    
    public boolean buscaRepetidoControlLiquidos(String sBusca){
        boolean bRtn=false;
        for(ControlLiquidos oCt: this.getHojaQx().getArrControlLiquidos()){
            if(oCt.getTipoControl().getValor().equals(sBusca)){
                bRtn= true;
                break;
            }
        }
        return bRtn;
    }
    
    public boolean buscaNoAgregarDosServiciosSalasQx(String sB){
        boolean bRt= false;
        if(sB.equals("QUIRÓFANO")){
            for(ControlLiquidos oB: this.getHojaQx().getArrControlLiquidos()){
                if(oB.getTipoControl().getValor().equals("TOCOCIRUGIA")){
                   bRt=true;
                   break;  
                }            
            }            
        }else {      
            if(sB.equals("TOCOCIRUGIA")){
                for(ControlLiquidos oB: this.getHojaQx().getArrControlLiquidos()){
                    if(oB.getTipoControl().getValor().equals("QUIRÓFANO")){                
                         bRt=true;
                         break;      
                    }
                }
            }
            
        }            
        return bRt;
    }
    
    public void eliminarControlLiquido(ControlLiquidos oC){
       this.getHojaQx().getArrControlLiquidos().remove(oC);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","ELiminado"));
    }
    
    
    public void agregarActividadesRecuperacion(){
        String sBan="";
        if(this.getActRecuperacion().getHoraRecuperacion()==null){
            sBan="01";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Faltan Hora Actividad"));          
        }
        if(this.getActRecuperacion().getRecuperacion()==null || this.getActRecuperacion().getRecuperacion().equals("")){
            sBan+="02";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Faltan la Actividad"));          
        }
        if(sBan.equals("")){
            this.getHojaQx().getArrActividadesRecuperacion().add(oActRecuperacion);
            //arrActRecuperacion.add(oActRecuperacion);
            oActRecuperacion= new ActividadesRecuperacion();
            RequestContext.getCurrentInstance().update("frm-recu");
            RequestContext.getCurrentInstance().execute("PF('agregarRecuperacion').hide()");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Actividad Agregada"));
        }
               
    }
    
    public void eliminarActividadesRecuperacion(ActividadesRecuperacion oAct){
        this.getHojaQx().getArrActividadesRecuperacion().remove(oAct);        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Actividad ELiminada"));
    }
            
    public void agregarMedicamentoParaAplicar(){
       boolean bBan=false;
        if(this.getMedicamentoAplicado().getMedicamento().getClaveMedicamento()==null ||
                this.getMedicamentoAplicado().getMedicamento().getClaveMedicamento().equals("")){
            bBan=true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta el Medicamento"));
        }
        if(this.getMedicamentoAplicado().getDosis()==null || this.getMedicamentoAplicado().getDosis().equals("")){
            bBan=true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Faltan Dosis"));
        }
        if(this.getMedicamentoAplicado().getVia().getClaveParametro()==null ||
                this.getMedicamentoAplicado().getVia().getClaveParametro().equals("")){
            bBan=true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta La via de Administracion"));
        }
        if(this.getMedicamentoAplicado().getFechaAplicacion()==null){
            bBan=true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta la hora de Aplicacion"));
        }
        if(this.getMedicamentoAplicado().getTurno().getClave()==null || 
                this.getMedicamentoAplicado().getTurno().getClave().equals("")){
            bBan=true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","Falta el Turno"));
        }
        
        if(bBan==false){
            this.buscarClavesMedicamento(this.getMedicamentoAplicado().getMedicamento().getClaveMedicamento());
            this.BuscaClaveViaAplicacion(this.getMedicamentoAplicado().getVia().getClaveParametro());
            this.getHojaQx().getArrTerapeuticaEmpleada().add(oMedicamentoAplicado);
            oMedicamentoAplicado = new MedicamentoAplicado();
            RequestContext.getCurrentInstance().execute("PF('agregarMedi').hide()");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Se agrego un medicamento"));
            RequestContext.getCurrentInstance().update("frmMedica");  
        /*if(this.getHojaQx().getArrTerapeuticaEmpleada().isEmpty()){
             this.getHojaQx().getArrTerapeuticaEmpleada().add(oMedicamentoAplicado);
             oMedicamentoAplicado = new MedicamentoAplicado();
             RequestContext.getCurrentInstance().execute("PF('agregarMedi').hide()");
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Se agrego un medicamento"));
        }else if(!this.getHojaQx().getArrTerapeuticaEmpleada().isEmpty()){
            if(!buscaMeicamentoRepetido(this.getMedicamentoAplicado().getMedicamento().getNombre())){
                this.getHojaQx().getArrTerapeuticaEmpleada().add(oMedicamentoAplicado);                
                oMedicamentoAplicado = new MedicamentoAplicado();
                RequestContext.getCurrentInstance().execute("PF('agregarMedi').hide()");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Se agrego un medicamento"));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se pueden agregar medicamentos repetidos")); 
            }
        }     
        RequestContext.getCurrentInstance().update("frmMedica"); */      
        }
             
    }
    
    public boolean buscaMeicamentoRepetido(String sMedicamento){
        boolean bRt=false;
        for(MedicamentoAplicado oMe: this.getHojaQx().getArrTerapeuticaEmpleada()){
            if(oMe.getMedicamento().getNombre().equals(sMedicamento)){
                bRt=true;
                break;
            }
        }
        return bRt;
    }
    
    public void eliminarMedicamentoAplicado(MedicamentoAplicado oMed){        
        this.getHojaQx().getArrTerapeuticaEmpleada().remove(oMed);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Se ha Elimindado el medicamento"));
    }
    
    public void guargarHojaEnfermeriaQx(){
        String sBn="";
        if(oHojaQx.getEpisodioMedico().getPaciente().getFolioPaciente()==0){
            sBn="02";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA  ","Falta paciente"));
        }
        if(oHojaQx.getConteoGasas()==0){
            sBn="03";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA  ","Falta el conteo de gasas"));
        }
        if(oHojaQx.getConteoCompresas()==0){
            sBn="03";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA  ","Falta el conteo de compresas"));
        }
        if(oHojaQx.getInstrumental()==0){
            sBn="03";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA  ","Falta el conteo de instrumental"));
        }
        if(oHojaQx.getMaterialQx()==0){
            sBn="03";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA  ","Falta el conteo de Material quirurgico"));
        }       
        if(this.getHojaQx().getArrActividadesRealizadasQx().isEmpty()){
           sBn="03";
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA  ","Te Faltan las Actividades en Quirofano")); 
        }
        if(this.getHojaQx().getArrControlLiquidos().isEmpty()){
            sBn="03";
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA  ","Falta el control de liquidos")); 
        }
        if(sBn.equals("")){
            if(this.getHojaQx().getEpisodioMedico().getPaciente().getFolioPaciente()!=0){
                try {
                    this.getHojaQx().setArrMustraSignos(this.getArrMustraSignos()); 
                    if(this.getHojaQx().insertarHojaEnfermeriaQxArreglos()>0){
                        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","SE GUARDO CORRECTAMENTE"));
                        FacesContext.getCurrentInstance().getExternalContext().redirect("HojaEnfermeriaQuirurgica.xhtml");
                    }                    
                } catch (Exception ex) {
                    Logger.getLogger(HojaEnfermeriaQx.class.getName()).log(Level.SEVERE, null, ex);
                }                 
            }          
        }
        
    }   
    
    public void buscarClavesMedicamento(String valor){
        for(MedicamentoAplicado oMe : arrMedicamentoRecetado){
            if(oMe.getMedicamento().getClaveMedicamento().equals(valor)){
                this.getMedicamentoAplicado().getMedicamento().setNombre(oMe.getMedicamento().getNombre());
                this.getMedicamentoAplicado().getMedicamento().setClaveMedicamento(oMe.getMedicamento().getClaveMedicamento());
                this.getMedicamentoAplicado().getMedicamento().setPresentacion(oMe.getMedicamento().getPresentacion());
                this.getMedicamentoAplicado().getMedicamento().setDetalle(new DetalleMedicamentos());
                this.getMedicamentoAplicado().getMedicamento().setCodBarras(oMe.getMedicamento().getCodBarras());
                this.getMedicamentoAplicado().getServReal().setIdentificador(oMe.getServReal().getIdentificador());
                break;
            }
        }
    }

    public void buscarClavesMedicamentoSoluciones(String valor){
        for(MedicamentoAplicado oMe : arrMedicamentoSoluciones){
            if(oMe.getMedicamento().getClaveMedicamento().equals(valor)){
                this.getSolucion().getMedicamento().setClaveMedicamento(oMe.getMedicamento().getClaveMedicamento());
                this.getSolucion().getMedicamento().setNombre(oMe.getMedicamento().getNombre());
                this.getSolucion().getMedicamento().setPresentacion(oMe.getMedicamento().getPresentacion());
                this.getSolucion().getMedicamento().setDetalle(new DetalleMedicamentos());
                this.getSolucion().getMedicamento().setCodBarras(oMe.getMedicamento().getCodBarras());
                this.getSolucion().getServReal().setIdentificador(oMe.getServReal().getIdentificador());
                break;
            }
        }
    }
     
    public void BuscaClaveViaAplicacion(String valor){
        for(Parametrizacion oPa: this.getListaVia()){
            if(oPa.getClaveParametro().equals(valor)){
                this.getMedicamentoAplicado().getVia().setClaveParametro(oPa.getClaveParametro());
                this.getMedicamentoAplicado().getVia().setValor(oPa.getValor());
                break;
            }
        }
    }
             
    private void signosCarga(){
        MustraSignosVitalesHojaQx oMustra=null;
        oMustra = new MustraSignosVitalesHojaQx();
        oMustra.setID("TA1");
        oMustra.getSignoValor().setSignoValor("TENSIÓN ARTERIAL");
        arrMustraSignos.add( oMustra);
        oMustra = new MustraSignosVitalesHojaQx();
        oMustra.setID("RES1");
        oMustra.getSignoValor().setSignoValor("RESPIRACIÓN");
        arrMustraSignos.add( oMustra);
        oMustra = new MustraSignosVitalesHojaQx();
        oMustra.setID("PULSO1");
        oMustra.getSignoValor().setSignoValor("PULSO");
        arrMustraSignos.add( oMustra);
        oMustra = new MustraSignosVitalesHojaQx();
        oMustra.setID("TEMP1");
        oMustra.getSignoValor().setSignoValor("TEMPERATURA");
        arrMustraSignos.add( oMustra);
        oMustra = new MustraSignosVitalesHojaQx();
        oMustra.setID("SO1");
        oMustra.getSignoValor().setSignoValor("SATURACIÓN OXIGENO");
        arrMustraSignos.add( oMustra); 
    }    
     
    public void onRowEditSignos(RowEditEvent event) {         
        MustraSignosVitalesHojaQx oEvent = ((MustraSignosVitalesHojaQx) event.getObject());
        if(oEvent.getSigHora7().getValor()!=null && !oEvent.getSigHora7().getValor().equals("")){
            FacesMessage msg = new FacesMessage("Agregado Correcta", oEvent.getSignoValor().getSignoValor());
            FacesContext.getCurrentInstance().addMessage(null, msg); 
        }         
    }
     
    public void onRowCancelSignos(RowEditEvent event) {        
        FacesMessage msg = new FacesMessage("Cancelada", ((MustraSignosVitalesHojaQx) event.getObject()).getSignoValor().getSignoValor());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }    
    
    public Parametrizacion[] getListaVia() {
        try {
            arrVia = (new Parametrizacion()).buscarViaAdministracion();
        } catch (Exception ex) {
           Logger.getLogger(HojaEnfermeriaQx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVia;
    }
    
    public String getDeterminaTurno(){ 
        Date dFechaActual2= new Date();
        SimpleDateFormat hf= new SimpleDateFormat("HH.mm");
         String sTurno="";
         String sT=hf.format(dFechaActual2);
         Double horaActual=Double.parseDouble(sT);
         sTurno= (horaActual>=07.00 && horaActual<15.00)?"MAT": (horaActual >= 15.00 && horaActual <= 21.00)?"VES":"NOC";        
         return sTurno;
    }
    
    public void buscaHojaEnfermeriaQuirurgica(long nIdHoja){
        String pagina="";
        try{            
            oHojaQx.setIdHoja(nIdHoja);            
            if(oHojaQx.buscarHojaQx()){                         
                RequestContext.getCurrentInstance().execute("PF('hojasQx').hide()");
                FacesContext.getCurrentInstance().getExternalContext().redirect("enfermeria/HojaEnfermeriaQuirurgica.xhtml");                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","  Hoja Encontrada"));
            }else
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","No se encontro informacion para la hoja"));                
        }catch(Exception e){
            java.util.logging.Logger.getLogger(HojaEnfermeriaQuirofano.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public HojaEnfermeriaQuirofano getHojaQx() {
        return oHojaQx;
    }
     
    public boolean getRender() {
        return bRender;
    }
    
    public void setRender(boolean valor){
        this.bRender=valor;
    }

    public ActividadesRealizadasQx getActividades() {
        return oActividades;
    }      

    public MedicamentoAplicado getSolucion() {
        return oSolucion;
    }
    
    public Hemoderivados getHemoderivadoAplicado() {
        return oHemoderivadoAplicado;
    }   

    public ControlLiquidos getControlLiquido() {
        return oControlLiquido;
    }   

    public boolean getRenderTblControl() {
        return bRenderTblControl;
    }

    public ActividadesRecuperacion getActRecuperacion() {
        return oActRecuperacion;
    }
    
    public ArrayList<MustraSignosVitalesHojaQx> getArrMustraSignos() {         
        return arrMustraSignos;
    }   
    
    public MedicamentoAplicado getMedicamentoAplicado() {
        return oMedicamentoAplicado;
    }
    
    public MedicamentoAplicado[] getMedicamentoRecetado() {
        return arrMedicamentoRecetado;
    }
    
    public Turno[] getTurnos(){
        return arrTurno;
    }    

    public Hemoderivados[] getArreHemosAp() {
        return arreHemosAp;
    }
    
    public MedicamentoAplicado[] getArrMedicamentoSoluciones() {
        return arrMedicamentoSoluciones;
    }
    
    public String getFechaString(){
        SimpleDateFormat format= new SimpleDateFormat();
        return format.format(dFechaActual);
    }
    
    public void validarTencionArterial7(MustraSignosVitalesHojaQx oM) throws ValidatorException{
        System.out.println("valor entra ");
         boolean error=false;
        if(oM.getSigHora7().getValor()!=null && !oM.getSigHora7().getValor().equals("")){
            System.out.println("valor a validar paso1 "+oM.getSigHora7().getValor());
            if(oM.getSignoValor().getSignoValor().compareTo("TENSIÓN ARTERIAL")==0){
                System.out.println("valor a validar paso2 "+oM.getSigHora7().getValor());
                String sTA = (String)oM.getSigHora7().getValor();
                Pattern p = Pattern.compile("([1-9]{1})([0-9]{1,2})/([1-9]{1})([0-9]{1,2})$");
                Matcher m = p.matcher(sTA);
                error = !m.matches();
                if(error){            
                    FacesMessage message = new FacesMessage("Tension Arterial Formato de entrada Incorrecto 000/000");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    //throw new ValidatorException(message);
                }   
            }
        }
    }

 
    public ArrayList<HojaEnfermeriaQuirofano> getArrMedicoAyudante() {
        return arrMedicoAyudante;
    }

    public ArrayList<HojaEnfermeriaQuirofano> getArrInstrumentista() {
        return arrInstrumentista;
    }
    
    public ArrayList<HojaEnfermeriaQuirofano> getArrCirculantes() {
        return arrCirculantes;
    }
    
    
    
}
// FacesContext.getCurrentInstance().getExternalContext().redirect("");