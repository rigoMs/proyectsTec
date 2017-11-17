
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
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.enfermeria.CabeceraDialisis;
import mx.gob.hrrb.modelo.enfermeria.DetalleDialisis;
import mx.gob.hrrb.modelo.enfermeria.IndicacionMedicaDialisis;
import org.primefaces.context.RequestContext;

/**
 *
 * @author : Javier
 * @version : 1.0
 */
@ManagedBean(name="oDialisisPac")
@ViewScoped
public class HojaDialisisJB implements Serializable {
    
    private int nClaveServicio;
    private int nNumHoja;
    private String sServicio;    
    private Usuario oUsuario;
    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private CabeceraDialisis oCbDialisis;
    private Date dFechaActual;
    private String sFechaActual;    
    private boolean isRender;
    private boolean isRendered;
    private boolean isDisable=true;
    private DetalleDialisis oDetalleDialisis;
    private ArrayList<DetalleDialisis> lListaDialisis;
    private DetalleDialisis oAgregarDialisis;
    private DetalleDialisis oEliminar;
    private DetalleDialisis oModificar;
    private DetalleDialisis[] arrDialisisPaciente;
    private IndicacionMedicaDialisis oIndicacionM;
    private Turno[] ArrTurnos;
    private SimpleDateFormat horaFormat;
   // agregar arreglo de medicamentos dl paciente para el dia 
    
    public HojaDialisisJB() {        
        dFechaActual = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        horaFormat = new SimpleDateFormat("HH.mm");
        nNumHoja=1;
        sFechaActual = format.format(dFechaActual);
        oCbDialisis = new CabeceraDialisis();        
        oDetalleDialisis = new DetalleDialisis();
        oAgregarDialisis = new DetalleDialisis();  
        oIndicacionM = new IndicacionMedicaDialisis();
        oModificar= new DetalleDialisis(); 
        //lListaDialisis = new ArrayList<DetalleDialisis>();
        
        oUsuario= new Usuario();
        oFirm=new Firmado();
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
        {
        oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
        sUsuario=oFirm.getUsu().getIdUsuario();            
        }
        
    }
  
    public void  cargaPaciente(long fp, long cve){
        try{
          oDetalleDialisis.getCabeceraDialisis().getEpisodioMedico().getPaciente().setFolioPaciente(fp);
          oDetalleDialisis.getCabeceraDialisis().getEpisodioMedico().getPaciente().setClaveEpisodio(cve);
          isRender=oDetalleDialisis.getCabeceraDialisis().getEpisodioMedico().buscarDatosPaciente();
          if(isRender==true){              
              /*DetalleDialisis oDialisis= new DetalleDialisis();              
              oDialisis.getCabeceraDialisis().getEpisodioMedico().getPaciente().setFolioPaciente(oDetalleDialisis.getCabeceraDialisis().getEpisodioMedico().getPaciente().getFolioPaciente());
              oDialisis.getCabeceraDialisis().getEpisodioMedico().getPaciente().setClaveEpisodio(oDetalleDialisis.getCabeceraDialisis().getEpisodioMedico().getPaciente().getClaveEpisodio());
              
              arrDialisisPaciente = oDialisis.buscarDialisisAgregadosPaciente();
              if(arrDialisisPaciente.length>0){
                 isRendered=true;   
                 isDisable=false; 
              }*/              
              //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Paciente Encontrado"));
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Paciente Encontrado"));
          }else
              RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Paciente no Encontrado"));
        }catch(Exception ex){
             Logger.getLogger(CabeceraDialisis.class.getName()).log(Level.SEVERE,null,ex);
        }  
    }
    //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","Paciente Encontrado"));
    public void AgregarDialisisArray(){
        String sBan="";
        if(this.getAgregarDialisis().getNoRecam()==0){
            sBan="01";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Falta en numero de recambio"));
        }
        if(this.getAgregarDialisis().getVolumenIngresa()==0){
             sBan+="02";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Falta el volumen que ingresa"));
        }
        if(this.getAgregarDialisis().getHoraIniciaIngreso()==null){
             sBan+="03";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Falta la hora de ingreso"));
        }
        if(sBan.equals("")){
            if(lListaDialisis.isEmpty()){                
                lListaDialisis.add(this.getAgregarDialisis());
                oAgregarDialisis = new DetalleDialisis();
            }else if(!lListaDialisis.isEmpty()){
                if(buscarRepetido(this.getAgregarDialisis().getNoRecam())){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","El numero de recambio ya esta agregado"));
                }else{
                    lListaDialisis.add(this.getAgregarDialisis());
                    oAgregarDialisis = new DetalleDialisis();
                }
            }
        }
        
    }    
    
    public boolean buscarRepetido(int nR){
        boolean bRtn=false;
        for(DetalleDialisis oD : lListaDialisis){
            if(oD.getNoRecam()==nR){
                bRtn=true;
                break;
            }
        }
        return bRtn;
    }
    
    public void eliminarRecambio(ActionEvent event) throws Exception{
        if(this.getEliminar()==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona para eliminar  de la tabla"));
        }else{
            if(eliminarDeLista()){
                FacesContext.getCurrentInstance().addMessage( null,new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Eliminado Correctamente"));
            }
        }
    }
    
    public boolean eliminarDeLista(){
        boolean bRtn=false;
        for (int i=0; i< lListaDialisis.size();i++) {
            if(lListaDialisis.get(i)==this.getEliminar()){
                lListaDialisis.remove(i);
                bRtn=true;
                break;
            }
        }                
        return bRtn;
    }
   
    // metodo para guardar las dialisis aplicadas, pero primero busca si ya existe la cabecera 
    public void guardarDialisis(ActionEvent event) throws Exception{
        
        if(this.lListaDialisis.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Agrega una dialisis"));
        }else{
           // String sTurnoActual= determinaTurno();
            //System.out.println(" turno  actual "+ sTurnoActual);
            //this.getDetalleDialisis().getTurno().setClave(sTurnoActual);
            /*try {
                if(this.getDetalleDialisis().getCabeceraDialisis().buscarIdentificadorCabeceraDialisis()){
                     for(DetalleDialisis oD : lListaDialisis){
                        this.getDetalleDialisis().setNoRecam(oD.getNoRecam());
                        this.getDetalleDialisis().setVolumenIngresa(oD.getVolumenIngresa());
                        this.getDetalleDialisis().setHoraIniciaIngreso(oD.getHoraIniciaIngreso());
                        this.getDetalleDialisis().setHoraFinIngreso(oD.getHoraFinIngreso());
                        this.getDetalleDialisis().setTiempoCavidad(oD.getTiempoCavidad());
                        this.getDetalleDialisis().setHoraIniciaEgreso(oD.getHoraIniciaEgreso());
                        this.getDetalleDialisis().setHoraFinEgreso(oD.getHoraFinEgreso());
                        this.getDetalleDialisis().setVolumenEgresa(oD.getVolumenEgresa());
                        //this.getDetalleDialisis().getMedicamentos().setClaveMedicamento(oD.getMedicamentos().getClaveMedicamento());
                        this.getDetalleDialisis().getTurno().setClave(determinaTurno());
                        this.getDetalleDialisis().insertarDetalleDialisis();
                        }
                }else{
                    //this.getDetalleDialisis().getCabeceraDialisis().setArea(new AreaServicioHRRB());
                    this.getDetalleDialisis().getCabeceraDialisis().setNoHoja(nNumHoja);
                    this.getDetalleDialisis().getCabeceraDialisis().getArea().setClave(nClaveServicio);
                    if(this.getDetalleDialisis().getCabeceraDialisis().insertarCabeceraDialisis()==1){
                        for(DetalleDialisis oD : lListaDialisis){
                            this.getDetalleDialisis().setNoRecam(oD.getNoRecam());
                            this.getDetalleDialisis().setVolumenIngresa(oD.getVolumenIngresa());
                            this.getDetalleDialisis().setHoraIniciaIngreso(oD.getHoraIniciaIngreso());
                            this.getDetalleDialisis().setHoraFinIngreso(oD.getHoraFinIngreso());
                            this.getDetalleDialisis().setTiempoCavidad(oD.getTiempoCavidad());
                            this.getDetalleDialisis().setHoraIniciaEgreso(oD.getHoraIniciaEgreso());
                            this.getDetalleDialisis().setHoraFinEgreso(oD.getHoraFinEgreso());
                            this.getDetalleDialisis().setVolumenEgresa(oD.getVolumenEgresa());
                            //this.getDetalleDialisis().getMedicamentos().setClaveMedicamento(oD.getMedicamentos().getClaveMedicamento());
                            this.getDetalleDialisis().getTurno().setClave(determinaTurno());
                            this.getDetalleDialisis().insertarDetalleDialisis();
                        }
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Dialisis Guardadad correctamente"));                       
                        RequestContext.getCurrentInstance().execute("PF('dialisis').hide()"); 
                        RequestContext.getCurrentInstance().update("hojaDialisis:plIndcacion");
                        RequestContext.getCurrentInstance().update("hojaDialisis:plObse");
                        
                    }else{
                        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"",""));
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(HojaDialisisJB.class.getName()).log(Level.SEVERE, null, ex);
            }*/
             isRendered=true;   
             isDisable=false;
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO","Dialisis Guardadad correctamente"));
             RequestContext.getCurrentInstance().execute("PF('dialisis').hide()"); 
             RequestContext.getCurrentInstance().update("hojaDialisis:plIndcacion");
             RequestContext.getCurrentInstance().update("hojaDialisis:plObse");
        }         
    }
    
    //crea la instancia del arraylist para agregar las dialisis
     public void instanciarArrayDialisis(ActionEvent event) throws Exception{
        lListaDialisis = new ArrayList<DetalleDialisis>();
        //String s=determinaTurno();
        //System.out.println(" es tuno "+ s);
        //RequestContext.getCurrentInstance().execute("PF('dialisis').show()");        
    }     
     
     public void agregarIndicacionMedica(ActionEvent event){
         int nAfec=-1;         
         if(this.getIndicacionMedica().getIndicacion().equals("")){
             //RequestContext.getCurrentInstance().update("hojaDialisis:msgs");
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Escribe la Indicacion Medica"));
         }else{
             /*try {
                 oIndicacionM.getCabeceraDialisis().getEpisodioMedico().getPaciente().setFolioPaciente(oDetalleDialisis.getCabeceraDialisis().getEpisodioMedico().getPaciente().getFolioPaciente());
                 oIndicacionM.getCabeceraDialisis().getEpisodioMedico().getPaciente().setClaveEpisodio(oDetalleDialisis.getCabeceraDialisis().getEpisodioMedico().getPaciente().getClaveEpisodio());
                 System.out.println(" folio pac recuperado de indicacion medica "+ oIndicacionM.getCabeceraDialisis().getEpisodioMedico().getPaciente().getFolioPaciente());
                  if(oIndicacionM.getCabeceraDialisis().buscarIdentificadorCabeceraDialisis()){
                      if(oIndicacionM.insertarIndicacionMedica()>0){
                          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","Indicacion Guardada"));
                          RequestContext.getCurrentInstance().execute("PF('indicacion').hide()");
                          //RequestContext.getCurrentInstance().update("hojaDialisis:tbIndicaciones"); 
                      }
                  }else{
                      FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"FATAL","NO se puede guardar Indicacion Medica"));
                  }      
             } catch (Exception ex) {
                 Logger.getLogger(IndicacionMedicaDialisis.class.getName()).log(Level.SEVERE, null, ex);                 
             }*/
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Indicación Guardada"));
             RequestContext.getCurrentInstance().execute("PF('indicacion').hide()");
         }
     }
    
     //cargar los turnos para agregar ala observacion
     public void CargarTurnos(ActionEvent event) throws Exception{
         try{
             oDetalleDialisis.getCabeceraDialisis().setTurno(new Turno());
             ArrTurnos= new Turno().buscarTurnosRequeridos();
         }catch(Exception e){
             Logger.getLogger(Turno.class.getName()).log(Level.SEVERE,null,e);
         } 
         
         
     } 
     
     public void guardarObservacion(ActionEvent event) throws Exception{
         int nAfec=-1;
         if(this.getDetalleDialisis().getCabeceraDialisis().getTurno().getClave().equals("") && this.getDetalleDialisis().getCabeceraDialisis().getObservacionT1().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Faltan Datos"));
         }else{
            /* try {
                 //buscamos el identificador de la cabecera
                 if(this.getDetalleDialisis().getCabeceraDialisis().buscarIdentificadorCabeceraDialisis()){
                     
                     //si la encuenta entra manda a insertar la observacion
                     if(this.getDetalleDialisis().getCabeceraDialisis().insertarObservacion()==1){
                       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Observación guardada"));
                       RequestContext.getCurrentInstance().update("hojaDialisis:observa");
                       RequestContext.getCurrentInstance().execute("PF('AgreObser').hide()");  
                     }
                 }else{
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"FATAL","No se puede guardar Observación"));
                 }
             } catch (Exception ex) {
                 Logger.getLogger(HojaDialisisJB.class.getName()).log(Level.SEVERE, null, ex);
             }*/  
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Observación guardada"));              
              RequestContext.getCurrentInstance().execute("PF('AgreObser').hide()"); 
         }
     }
     
     public String determinaTurno(){         
         String sTurno="";
         String sT=this.horaFormat.format(dFechaActual);
         Double horaActual=Double.parseDouble(sT);
         if(horaActual> 08.00 && horaActual <14.00 ){
             sTurno="MAT";
         }else if(horaActual > 14.00 && horaActual < 20.00){
             sTurno="VES";
         }else{
             sTurno="NOC";
         }
         
         return sTurno;
     }
     
     public void modificarDialisis(ActionEvent event) throws Exception{
         if(this.getModificar()==null){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Selecciona Para Modificar "));
         }else{             
             RequestContext.getCurrentInstance().execute("PF('dialisisModificar').show()");
             RequestContext.getCurrentInstance().update("mofDialisis");
         }
     }
     
     public void guardarModoficarDialisis(ActionEvent event){
         if(this.getModificar().getNoRecam()==0){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","Faltan Datos"));
         }else{
             /*try {
                 if(this.getModificar().modificarDetalleDialisis()>0){
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Modificación Correcta"));
                     RequestContext.getCurrentInstance().execute("PF('dialisisModificar').hide()");
                     RequestContext.getCurrentInstance().update(":hojaDialisis:tablaDialisis"); 
                 }
             } catch (Exception ex) {
                 Logger.getLogger(HojaDialisisJB.class.getName()).log(Level.SEVERE, null, ex);
             }*/
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACIÓN","Modificación Correcta"));
             RequestContext.getCurrentInstance().execute("PF('dialisisModificar').hide()");
             RequestContext.getCurrentInstance().update(":hojaDialisis:tablaDialisis"); 
         }
     }
     
    //asta este momento no se ocupa
    public CabeceraDialisis getCabeceraDialisis(){
        return oCbDialisis;
    } 
    // objeto para cargar los datos del paciente y agregar indicaciones
    public DetalleDialisis getDetalleDialisis(){
        return this.oDetalleDialisis;
    }
    
    //ocupado para agregar al array 
    public DetalleDialisis getAgregarDialisis(){
        return oAgregarDialisis;
    }
    
    public String getFechaActual(){
        return sFechaActual;
    }
    
    public boolean getRender(){
        return this.isRender;
    }
    public boolean getRendered(){
        return this.isRendered;
    }
    
    public boolean getDisable(){
        return isDisable;
    }
    public ArrayList<DetalleDialisis> getListaDialisis(){
        return lListaDialisis;
    }  
    
    public ArrayList<DetalleDialisis> getListaDialisisPrueba(){
        return lListaDialisis;
    }  

    public DetalleDialisis getEliminar() {
        return oEliminar;
    }

    public void setEliminar(DetalleDialisis oEliminar) {
        this.oEliminar = oEliminar;
    }
    
    public DetalleDialisis getModificar(){
        return oModificar;
    }
    
    public void setModificar( DetalleDialisis oValor){
        this.oModificar=oValor;
    }
    
    public IndicacionMedicaDialisis getIndicacionMedica(){
        return oIndicacionM;
    }
    
    public Turno[] getTurnos(){
        return ArrTurnos;
    }
    
    public DetalleDialisis[] getArrDialisisPaciente(){
        return arrDialisisPaciente;
    }
    
    
}
/*FacesMessage message=null;
message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Abriendo", "Agregar Dialisis");
RequestContext.getCurrentInstance().showMessageInDialog(message);*/
