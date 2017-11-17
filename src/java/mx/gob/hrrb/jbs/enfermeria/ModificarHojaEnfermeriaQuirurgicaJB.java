
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.MedicamentoAplicado;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.enfermeria.ActividadesRecuperacion;
import mx.gob.hrrb.modelo.enfermeria.ControlLiquidos;
import mx.gob.hrrb.modelo.enfermeria.Hemoderivados;
import mx.gob.hrrb.modelo.enfermeria.HojaEnfermeriaQuirofano;
import org.primefaces.context.RequestContext;

/**
 *
 * @author J2GOV
 */
@ManagedBean(name="oMQx")
@SessionScoped
public class ModificarHojaEnfermeriaQuirurgicaJB implements Serializable{
 
    private boolean bRender;
    private MedicamentoAplicado oSolucion; 
    private Hemoderivados oHemoderivadoAplicado;
    private ControlLiquidos oControlLiquido;
    private ActividadesRecuperacion oActRecuperacion;     
    private MedicamentoAplicado oMedicamentoAplicado; 
    private HojaEnfermeriaQuirofano oHQx;
    private MedicamentoAplicado arrMedicamentoRecetado[];
    private MedicamentoAplicado arrMedicamentoSoluciones[];
    private Parametrizacion arrVia[];
    private Hemoderivados[] arreHemosAp;
    private Turno[] arrTurno=null; 
    private Turno oTurno;
    private Date dFechaActual;
    
    public ModificarHojaEnfermeriaQuirurgicaJB() {
        oHQx= new HojaEnfermeriaQuirofano();
        oHemoderivadoAplicado= new Hemoderivados();
        oMedicamentoAplicado = new MedicamentoAplicado();
        oSolucion = new MedicamentoAplicado();
        oControlLiquido= new ControlLiquidos();
        oActRecuperacion= new ActividadesRecuperacion();
        oTurno= new Turno();
        dFechaActual= new Date();
        RequestContext.getCurrentInstance().execute("PF('hojasQx').hide()"); 
    }
    
    public String buscaHojaEnfermeriaQuirurgica(long nIdHoja){
        String pagina="";
        try{            
            oHQx.setIdHoja(nIdHoja);   
            bRender=oHQx.buscarHojaQx();
            if(bRender){       
                arrTurno = oTurno.buscarTurnosRequeridos();
                Medicamento oMe = new Medicamento(); 
                MedicamentoAplicado oMedApl = new MedicamentoAplicado();
                arrMedicamentoRecetado = (oMedApl.buscarMedicamentoDiferentesDeSoluciones(dFechaActual,oHQx.getEpisodioMedico().getPaciente().getFolioPaciente(),oHQx.getEpisodioMedico().getPaciente().getClaveEpisodio()));          
                arrMedicamentoSoluciones=(oMedApl.buscarMedicamentoConDeSoluciones(dFechaActual,oHQx.getEpisodioMedico().getPaciente().getFolioPaciente(),oHQx.getEpisodioMedico().getPaciente().getClaveEpisodio()));                 
                pagina="ModificaHojaQx.xhtml";
                RequestContext.getCurrentInstance().update("frmHojaQx");
                RequestContext.getCurrentInstance().update("frmHojaQx:tabQx:tbl2");                
                RequestContext.getCurrentInstance().execute("PF('hojasQx').hide()"); 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","  Hoja Encontrada"));
            }else
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","No se encontro informacion para la hoja"));                
        }catch(Exception e){
            java.util.logging.Logger.getLogger(HojaEnfermeriaQuirofano.class.getName()).log(Level.SEVERE,null,e);
        }
        return pagina;
    }
    
    public void buscarHemoderivados(){
        try {
            arreHemosAp= oHemoderivadoAplicado.buscaSolicitudHemoderivadoPaciente(oHQx.getEpisodioMedico().getPaciente().getFolioPaciente(),
                    oHQx.getEpisodioMedico().getPaciente().getClaveEpisodio());
        } catch (Exception ex) {
            Logger.getLogger(HojaEnfermeriaQx.class.getName()).log(Level.SEVERE, null, ex);
        }
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
           this.oSolucion.setEpisodioMedico(oHQx.getEpisodioMedico());
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
                    this.getHemoderivadoAplicado().setHemoderivado(oh.getDescripcionHem());
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
        }else{
            if(this.getMedicamentoAplicado().getTurno().getClave().equals("MAT")){
                this.getMedicamentoAplicado().getTurno().setDescripcion("MATUTINO");
            }else if(this.getMedicamentoAplicado().getTurno().getClave().equals("VES")){
                this.getMedicamentoAplicado().getTurno().setDescripcion("VESPERTINO");
            }else if(this.getMedicamentoAplicado().getTurno().getClave().equals("NOC")){
                this.getMedicamentoAplicado().getTurno().setDescripcion("NOCTURNO");
            }
        }    
        if(bBan==false){
            this.buscarClavesMedicamento(this.getMedicamentoAplicado().getMedicamento().getClaveMedicamento());
            this.BuscaClaveViaAplicacion(this.getMedicamentoAplicado().getVia().getClaveParametro());            
            this.oMedicamentoAplicado.setEpisodioMedico(oHQx.getEpisodioMedico());
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
            RequestContext.getCurrentInstance().execute("PF('agregarRecuperacion').hide()");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Actividad Agregada"));
        }
               
    }
    
     public void eliminarActividadesRecuperacion(ActividadesRecuperacion oAct){
        this.getHojaQx().getArrActividadesRecuperacion().remove(oAct);        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Actividad ELiminada"));
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
                oControlLiquido = new ControlLiquidos();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Agregado"));
                RequestContext.getCurrentInstance().execute("PF('controlLiquidos').hide()");
            }else if(!this.getHojaQx().getArrControlLiquidos().isEmpty()){
                if(!buscaRepetidoControlLiquidos(this.getControlLiquido().getTipoControl().getValor())){ 
                      if(!buscaNoAgregarDosServiciosSalasQx(this.getControlLiquido().getTipoControl().getValor())){
                          this.getControlLiquido().getBuscaClave(oControlLiquido.getTipoControl().getValor());
                          this.getHojaQx().getArrControlLiquidos().add(oControlLiquido);                   
                          oControlLiquido = new ControlLiquidos();
                          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Agregado")); 
                          RequestContext.getCurrentInstance().execute("PF('controlLiquidos').hide()");
                      }else{
                         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR"," No se Puede agregar mas de dos servicios")); 
                      }        
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR", this.getControlLiquido().getTipoControl().getValor()+" Ya esta Agregado"));
                }
            } 
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
        }else{
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
    
    public Parametrizacion[] getListaVia() {
        try {
            arrVia = (new Parametrizacion()).buscarViaAdministracion();
        } catch (Exception ex) {
           Logger.getLogger(HojaEnfermeriaQx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVia;
    }
    
    public void modificar(){
        try {
            if(this.getHojaQx().getArrActividadesRecuperacion()!=null && 
                    !this.getHojaQx().getArrActividadesRecuperacion().isEmpty()){
                if(oHQx.ActualizaHojaDeEnfermeriaQx()>0){
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Modificacion Correcta"));
                    FacesContext.getCurrentInstance().getExternalContext().redirect("HojaEnfermeriaQuirurgica.xhtml"); 
                }
            }else{
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","Antes de modifcar necesitas agregar actividades de recuperación")); 
            }
                        
        } catch (Exception ex) {
            Logger.getLogger(ModificarHojaEnfermeriaQuirurgicaJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getRegresar(){
        return "HojaEnfermeriaQuirurgica.xhtml";
    }
    
    public boolean getRender(){
        return bRender;
    }
    
    public HojaEnfermeriaQuirofano getHojaQx(){
        return oHQx;
    }

    public MedicamentoAplicado[] getMedicamentoRecetado() {
        return arrMedicamentoRecetado;
    }

    public MedicamentoAplicado[] getArrMedicamentoSoluciones() {
        return arrMedicamentoSoluciones;
    }

    public Hemoderivados[] getArreHemosAp() {
        return arreHemosAp;
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
    public ActividadesRecuperacion getActRecuperacion() {
        return oActRecuperacion;
    }

    public MedicamentoAplicado getMedicamentoAplicado() {
        return oMedicamentoAplicado;
    }

    public Turno[] getArrTurno() {
        return arrTurno;
    }
}
               
//FacesContext.getCurrentInstance().getExternalContext().redirect("ModificaHojaQx.xhtml");