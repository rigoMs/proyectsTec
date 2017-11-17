 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Ciudad;
//*****************************************CAMBIO 2017*************************************
import mx.gob.hrrb.modelo.core.CiudadCP;
//******************************************************************************
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Referencia;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
import mx.gob.hrrb.modelo.urgencias.HojaLesiones;
import org.primefaces.context.RequestContext;
//******************************CAMBIO 2017************************************************
import mx.gob.hrrb.modelo.core.TipoVialidad;
import mx.gob.hrrb.modelo.core.TipoAsentamiento;
//******************************************************************************
/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oHojaLesion")
@SessionScoped
//@ViewScoped

public class LlenarHojaLesion{
    private AdmisionUrgs oAdmisionUrgs;
    private long nFolioPac=0;
    private int numExp;
    private boolean habilita;
    private Paciente oPaciente;
    private HojaLesiones oHojaLesiones;
    private boolean bBuscado = false;
    private String fechaHoy;
     
    //***************************CAMBIO 2017***********************************************
    private List<TipoVialidad>lTipoVialidad;
    private List<TipoAsentamiento>lTipoAsentamiento;
    //**************************************************************************
    
    public LlenarHojaLesion(){
    //******************************CAMBIO 2017********************************************
        crearListaTipoVialidad();
        crearListaTipoAsentamiento();
        
    //**************************************************************************
        habilita=true;
        oPaciente=new Paciente();
        oHojaLesiones=new HojaLesiones();
        oAdmisionUrgs=new AdmisionUrgs();
        setFechaHoy(oAdmisionUrgs.fechaActual());
    }
    
    /**
     * @return the oAdmisionUrgs
     */
    public AdmisionUrgs getAdmisionUrgs() {
        return oAdmisionUrgs;
    }

    /**
     * @param oAdmisionUrgs the oAdmisionUrgs to set
     */
    public void setAdmisionUrgs(AdmisionUrgs oAdmisionUrgs) {
        this.oAdmisionUrgs = oAdmisionUrgs;
    }

       
    public long getFolioPac() {return nFolioPac; }

    public void setFolioPac(long nFolioPac) throws Exception { this.nFolioPac = nFolioPac;
    }

    public boolean getBuscado() { return bBuscado;}

    public void setBuscado(boolean bBuscado) {this.bBuscado = bBuscado;System.out.println(bBuscado);}
    
    
    
//**********************************CAMBIO 2017****************************************************************
    public void crearListaTipoVialidad(){
    TipoVialidad[] arrTipoVialidad= null;
    String sDescripcion ="";
    try{
    arrTipoVialidad = (new TipoVialidad()).buscarTodos();
    if(arrTipoVialidad !=null){
    for(TipoVialidad oTipoVial : arrTipoVialidad){
    sDescripcion="";
    sDescripcion = sp_ascii(oTipoVial.getDescripcion());
    oTipoVial.setDescripcion(sDescripcion);
    }
    lTipoVialidad = new ArrayList<>(Arrays.asList(arrTipoVialidad));
    }
    }
    catch(Exception ex){
    ex.printStackTrace();
    }
    
    }
    public void crearListaTipoAsentamiento(){
    TipoAsentamiento[] arrTipoAsentamiento = null;
    String sDescripcion="";
    try{
        arrTipoAsentamiento = (new TipoAsentamiento()).buscarTodos();
        if(arrTipoAsentamiento !=null){
        for(TipoAsentamiento oTipoAsenta : arrTipoAsentamiento){
        sDescripcion="";
        sDescripcion=sp_ascii(oTipoAsenta.getDescripcion());
        oTipoAsenta.setDescripcion(sDescripcion);
        }
        lTipoAsentamiento= new ArrayList<>(Arrays.asList(arrTipoAsentamiento));
        }
    
    
    }
    
    catch(Exception ex){
    ex.printStackTrace();
    
    }
    
    }
    public String sp_ascii( String input ) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for( int i = 0; i < original.length(); i++ ) {
            // Reemplazamos los caracteres especiales.
            output = output.replace( original.charAt( i ), ascii.charAt( i ) );
        }//for i
        return output;
    }

    
    public List<TipoVialidad> getListaTipoVialidad(){
    return lTipoVialidad;
    }
    public List<TipoAsentamiento> getListaTipoAsentamiento(){
    return lTipoAsentamiento;
    }
//************************************************************************************
   
    public List<Parametrizacion> getListaTipoCama(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T28"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaTriage(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T31"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaTipoUrgencia(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T26"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaMotivoAtencion(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T27"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    
    public List<Parametrizacion> getListaDestino(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T29"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
   
    
    
    
    
    
    
    public List<Parametrizacion> getListaEscolaridad(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T14"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaTipoViolencia(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T15"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaPacBajoEfectos(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T16"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getCausaAccidente(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T17"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getUsoEquipo(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T18"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getEquipoNombre(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T19"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getParentescoAgresor(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T20"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getBajoEfectos(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T16"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getServAtencion(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T21"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getTipoAtencion(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T22"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getUsuarioReferido(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T23"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getDestinoDespuesAtn(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T24"))));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    //********************************CAMBIO 2017***********************************************************************
         public List<Parametrizacion> getListaPertGpoIndigena(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarPertGpoInd()));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
   //*********************************************************************************************************    
         
   //************************************CAMBIO 2017*********************************************************************      
         public boolean habilitaEstado(){
         boolean b=false;
         System.out.println(oHojaLesiones.getLesion().getEquipoSeguridad().getClaveParametro().trim());
         if(oHojaLesiones.getAtencion().getReferidoPor().getClaveParametro().trim().compareTo("T2301")==0)
             b=false;
         else 
             b=true;
         return b;
             
         }
         public boolean habilitaUmedic(){
         boolean b=false;
        System.out.println(oHojaLesiones.getLesion().getEquipoSeguridad().getClaveParametro());
        if(oHojaLesiones.getAtencion().getReferidoPor().getClaveParametro().trim().compareTo("T2301")==0)
            b=false;
        else
            b=true;
        return b;
         }
//***********************************************************************************************************+      
    public boolean habilitaTipoViolencia(){
        boolean b=false;
        if(oHojaLesiones.getLesion().getIntencionalidad().getClave()==2 || oHojaLesiones.getLesion().getIntencionalidad().getClave()==3)
            b=false;
        else
            b=true;
        return b;
    }

    
//********************************CAMBIO 2017*****************************************************************************
    public boolean habilitaOtroLugO(){
    boolean b=false;
    System.out.println(oHojaLesiones.getLesion().getSitioOcurrencia().getClave());
    if(oHojaLesiones.getLesion().getSitioOcurrencia().getClave()==17)
        b=false;
    else
        b=true;
    return b;
    
    }
//*************************************************************************************************************   



    public boolean requiereTipoViolencia(){
        boolean b=false;
        if(oHojaLesiones.getLesion().getIntencionalidad().getClave()==2 || oHojaLesiones.getLesion().getIntencionalidad().getClave()==3)
            b=true;
        else
            b=false;
        return b;
    }
    
    public boolean habilitaEventoauto(){
        boolean b=false;
        if(oHojaLesiones.getLesion().getIntencionalidad().getClave()==2 || oHojaLesiones.getLesion().getIntencionalidad().getClave()==3
                || oHojaLesiones.getLesion().getIntencionalidad().getClave()==4)
            b=false;
        else
            b=true;
        return b;
    }
    
    public boolean requiereEventoAuto(){
        boolean b=false;
        if(oHojaLesiones.getLesion().getIntencionalidad().getClave()==2 || oHojaLesiones.getLesion().getIntencionalidad().getClave()==3
                || oHojaLesiones.getLesion().getIntencionalidad().getClave()==4)
            b=true;
        else
            b=false;
        return b;
    }
    
    public boolean habilitaSiVehiculo(){
        boolean b=false;
        
        if(oHojaLesiones.getLesion().getAgenteLesion().getClaveParametro().compareTo("20")==0)
            b=false;
        else
            b=true;
        return b;
    }
    
    public boolean requiereSiVehiculo(){
        boolean b=false;
        if(oHojaLesiones.getLesion().getAgenteLesion().getClaveParametro().compareTo("20")==0)
            b=true;
        else
            b=false;
        return b;
    }
    
    public boolean habilitaNombreEquipoSeguridad(){
        boolean b=false;
        System.out.println(oHojaLesiones.getLesion().getEquipoSeguridad().getClaveParametro());
        if(oHojaLesiones.getLesion().getEquipoSeguridad().getClaveParametro().trim().compareTo("T1801")==0)
            b=false;
        else
            b=true;
        return b;
    }
    
    public boolean requiereNombreEquipoSeguridad(){
        boolean b=false;
        System.out.println(oHojaLesiones.getLesion().getEquipoSeguridad().getClaveParametro()+"@");
        if(oHojaLesiones.getLesion().getEquipoSeguridad().getClaveParametro().trim().compareTo("T1801")==0)
            b=true;
        else
            b=false;
        return b;
    }
    
    
    public boolean habilitaDatosAgresor(){
        boolean b=false;
        if(oHojaLesiones.getAgresor().getTipoAgresor()=='1')
            b=false;
        else
            b=true;
        return b;
    }
//***********************************************CAMBIO 2017*********************************************************************************** 
    public boolean habilitaLengua(){
        boolean b=false;
        //System.out.println("habilita lengua: "+oPaciente.getEtnicidad().getHablaLenguaIndStr());
        if(oAdmisionUrgs.getPaciente().getEtnicidad().getHablaLenguaIndStr().trim().compareToIgnoreCase("T0202")==1)
            b=true;
        else
            b=false;
        return b;
    }
  //***********************************************************************************************************************************  
    
 
    
    public boolean requiereDatosAgresor(){
        boolean b=false;
        if(oHojaLesiones.getAgresor().getTipoAgresor()=='1')
            b=true;
        else
            b=false;
        return b;
    }
    
    
    public boolean habilita(int num){
        //System.out.println("num---> "+num);
        boolean habilitado=true;
        if(num==0)
            return habilitado=false;
        else if(oAdmisionUrgs.getAfePrincipal().getCIE10().getDescripcionDiag()!=null && num==1 || oAdmisionUrgs.getAfePrincipal().getCIE10().getDescripcionDiag()!=null && num==3)
            return habilitado=false;
        else if(oAdmisionUrgs.getAfeSegunda().getCIE10().getDescripcionDiag()!=null && num==2)
            return habilitado=false;
        return habilitado; 
    }        

    /**
     * @return the numExp
     */
    public int getNumExp() {
        return numExp;
    }
    
    //Retorna lista de opciones de derechohabiente
     public List<Parametrizacion> getListaDerechobiente(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarDerechohabiencia()));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
    
    

    /**
     * @param numExp the numExp to set
     */
    public void setNumExp(int numExp) {
        this.numExp = numExp;
    }

    /**
     * @return the oPaciente
     */
    public Paciente getPaciente() {
        return oPaciente;
    }

    /**
     * @param oPaciente the oPaciente to set
     */
    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    
//***********************************************CAMBIO 2017************************************************************
    public List<Estado> getListaEstadoNac(){
    
    List<Estado>lLista=null;
    try{
        lLista= new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
    }
    catch(Exception ex){
    Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lLista;
    }
    
    
    public List<Estado>getListaEstadoUni(){
       List<Estado>lLista=null;
       try{
       lLista=new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       }catch(Exception ex){
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       
       }
return lLista;
    }
    
     public List<Referencia>getListaReferencia(){
     List<Referencia>lList=null;
     try{
     lList= new ArrayList<Referencia>(Arrays.asList((new Referencia()).buscarReferencias()));
     
     }
     catch(Exception ex){
     Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
     }
     return lList;
     }
     
     
 //********************************************************************************************************************   
    
    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
        //System.out.println("Entra a lista municipios: "+oEstado.getClaveEdo());
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList(
                   (new Municipio()).buscarMunicipio(oHojaLesiones.getLesion().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    
    public List<Ciudad> getListaCiudades(){
         //System.out.println("Entra a lista Ciudades: "+oMunicipio.getEstado().getClaveEdo()+" "+ oMunicipio.getClaveMun());
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList(
                   (new Ciudad()).buscarCiudad(oHojaLesiones.getLesion().getEstado().getClaveEdo(), oHojaLesiones.getLesion().getMunicipio().getClaveMun())));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
 
 
//*******************************************CAMBIO 2017********************************************************************************************************************************************   
     public List<CiudadCP> getListaCP(){
       List<CiudadCP> lListaCP = null;
       try {
           lListaCP = new ArrayList<CiudadCP>(Arrays.asList(
                   (new CiudadCP()).buscarCP(oHojaLesiones.getLesion().getCiudad().getClaveCiu(),oHojaLesiones.getLesion().getMunicipio().getClaveMun(),oHojaLesiones.getLesion().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(LlenarHojaLesion.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        return lListaCP;
    }
 //**************************************************************************************************************   
    public String disEmbarazada(){
        if(oPaciente.getSexoP().compareTo("M")==0)
            return "true";
        else 
            return "false";
    }
    
    public String reqEmbarazada(){
        if(oPaciente.getSexoP().compareTo("M")==0)
            return "false";
        else 
            return "true";
    }
    //*********************************HOSPITALIZAR*********************************
     public String insertaHojaLesion() throws Exception{
        String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        FacesMessage message=null;
        
        afec=oHojaLesiones.insertarHojaLesionNuevo(oPaciente, oAdmisionUrgs);
           
        if(afec==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hoja de Lesión", "Ocurrio un Error al la hoja de lesiones!!!");
            pag="/faces/sesiones/Inicio.xhtml";
            oPaciente=new Paciente();
            oHojaLesiones=new HojaLesiones();
            oAdmisionUrgs=new AdmisionUrgs();
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hoja de Lesión", "Hoja de Lesiones guardada Exitosamente!!!");
            oPaciente=new Paciente();
            oHojaLesiones=new HojaLesiones();
            oAdmisionUrgs=new AdmisionUrgs();
        }
       
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

    
    /**
     * @return the oHojaLesiones
     */
    public HojaLesiones getHojaLesiones() {
        return oHojaLesiones;
    }

    /**
     * @param oHojaLesiones the oHojaLesiones to set
     */
    public void setHojaLesiones(HojaLesiones oHojaLesiones) {
        this.oHojaLesiones = oHojaLesiones;
    }

    /**
     * @return the fechaHoy
     */
    public String getFechaHoy() {
        System.out.println("Fecha Hoy - "+fechaHoy);
        return fechaHoy;
    }

    /**
     * @param fechaHoy the fechaHoy to set
     */
    public void setFechaHoy(String fechaHoy) {
        this.fechaHoy = fechaHoy;
    }
}