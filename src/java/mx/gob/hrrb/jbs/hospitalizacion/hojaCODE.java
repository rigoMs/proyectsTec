/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Lesion;
import mx.gob.hrrb.modelo.core.MetodoAnticonceptivo;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Perfil;
import mx.gob.hrrb.modelo.core.Referencia;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oCode")
@SessionScoped
public class hojaCODE {
    private Hospitalizacion oHosp;
    private String sOpe="";
    private long nFolioPac=0;
    private long nNumHospitalizacion=0;
    private int nNumeroExpediente=0;
    private long nEpisodio=0;
    private boolean bBuscado = false;
    private EpisodioMedico oEpiMed;   
    private Referencia oReferencia;
    private boolean bBloqueado;
    private int nTipo;
    private Perfil oPerfil;
    private boolean bPerfil;
    private boolean bPerfil2;
    /**
     * Creates a new instance of hojaCODE
     */
    public hojaCODE() {
        oHosp = new Hospitalizacion();
        oEpiMed = new EpisodioMedico();       
        oReferencia = new Referencia();
        oPerfil = new Perfil();
        try {
            bPerfil = oPerfil.buscaPerfilHosp();
            bPerfil2 = oPerfil.buscaPerfilHospEst();
        } catch (Exception ex) {
            Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardar(){
        System.out.println("**********************Probando llegue aqui****************************");
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosp.insertar();
            else if(this.sOpe.equals("m"))
                nAfec = this.oHosp.modificarInsertarFolioCODE();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos Guardados Correctamente");
            else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos no Guardados :(");
            RequestContext.getCurrentInstance().showMessageInDialog(message);       
        //sOpe="";
    }     
    
    public Hospitalizacion getCode(){        
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                oHosp = new Hospitalizacion();
            }
            else{
                try{            
                    oHosp = new Hospitalizacion();
                    oHosp.getPaciente().setFolioPaciente(nFolioPac);
                    oHosp.getPaciente().getExpediente().setNumero(nNumeroExpediente);
                    oHosp.setNumIngresoHos(nNumHospitalizacion);
                    oHosp.getEpisodioMedico().setClaveEpisodio(nEpisodio);
                    System.out.println("FolioPac "+nFolioPac+" nNumeroExpediente "+nNumeroExpediente+" nNumHospitalizacion "+nNumHospitalizacion+" episodio "+nEpisodio);
                    oHosp.buscarDatosPacienteCODE();                            
                    oHosp.buscarCodeFolio();
                }catch(Exception e){
                    e.printStackTrace();
                    bBuscado = false;
                }
            }
        }
        return oHosp;
    }       
    
    public String mostrarBotonCerrarCODE(){
        if (getTipo()==1)
            return "";
        else if (getTipo()==3)
            return "display:none";
        else
            return "display:none";
    }    
    
    public String mostrarBotonDescargarCODE(){
        if (getTipo()==1)
            return "";
        else if (getTipo()==3)
            return "";
        else
            return "display:none";
    }        
    
    public String mostrarBotonAutorizarCODE(){
        if (getTipo()==2){
            if (bPerfil==false)
                return "display:none";
            else 
                return "";
        }     
        else if (getTipo()==3)
            return "display:none";        
        else
            return "display:none";
    }   
    
    public String mostrarBotonGuardar(){
        System.out.println("boton guardar: "+getTipo());
        if(getTipo()==3)
            return "display:none";
        else{
            System.out.println("Entre para bPerfil2: "+bPerfil2);
            if(bPerfil2==false)
                return "display:none";
            else 
                return "";
        }        
    }
    
    public boolean activarBtnCODE(){
        boolean bandera;        
        if(oHosp.getFolioCode()!=null && !oHosp.getFolioCode().equals(""))
            bandera=false;
        else 
            bandera=true;
        return bandera;
    }    
    
    public String cerrarCODE(){
        System.out.println("**********");
        String redirect="";
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosp.insertar();
            else if(this.sOpe.equals("m"))
                nAfec = this.oHosp.modificaEstadoCODE("T4300");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1){               
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos Guardados Correctamente");  
                redirect="PacientesHospitalizadosArea";
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos no Guardados :(");
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        return redirect;
    }      

    public String cerrarCODELLena(){
        System.out.println("************");
        String redirect="";
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosp.insertar();
            else if(this.sOpe.equals("m")){
                guardar();
                nAfec = this.oHosp.modificaEstadoCODE("T4303");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1){
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos Guardados Correctamente");
                redirect="CodesSinFolio";
            }else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital", "Datos no Guardados :(");            
            
            FacesContext.getCurrentInstance().addMessage(null, message);  
            
            return redirect;
    }    
    
    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }
    
    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }

    /**
     * @return the sOpe
     */
    public String getOpe() {
        return sOpe;
    }

    /**
     * @param sOpe the sOpe to set
     */
    public void setOpe(String sOpe) {
        this.sOpe = sOpe;
        //System.out.println(sOpe);
    }

    /**
     * @return the nFolioPac
     */
    public long getFolioPac() {
        //System.out.println(nFolioPac);
        return nFolioPac;
    }

    /**
     * @param nFolioPac the nFolioPac to set
     */
    public void setFolioPac(long nFolioPac) {
        this.nFolioPac = nFolioPac;
        //System.out.println(nFolioPac);
    }

    /**
     * @return the bBuscado
     */
    public boolean getBuscado() {
        return bBuscado;
    }

    /**
     * @param bBuscado the bBuscado to set
     */
    public void setBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
        //System.out.println(bBuscado);
    }
    
         //Retorna lista de tipos de anestesias
   public List<Parametrizacion> getListaAnestesias(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T07")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }  
   
         //Retorna lista de tipos de procedencias
   public List<Parametrizacion> getListaProcedencias(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T04")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }    
   
         //Retorna lista de Motivos Egresos
   public List<Parametrizacion> getListaMotivosEgreso(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T38")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }    
   
         //Retorna lista de Motivos Egresos
   public List<Parametrizacion> getListaTipoNacimiento(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T08")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }    
   
         //Retorna lista de Unidades Psiquiatricas
   public List<Parametrizacion> getListaHospitalPsiquiatrico(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T11")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }   
   
         //Retorna lista de Servicios de Hospital continuo
   public List<Parametrizacion> getListaServiciosHC(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T12")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }     

         //Retorna lista de Servicios de Hospital continuo
   public List<Parametrizacion> getListaServiciosHP(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T13")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }      
   
         //Retorna lista de Sexos para recien nacido
   public List<Parametrizacion> getListaSexo(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T09")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }    
   
         //Retorna lista de condición al egreso
   public List<Parametrizacion> getListaCondicionEgreso(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T10")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }      
   
         //Retorna lista de Tiempos Enfermedad Muerte
   public List<Parametrizacion> getListaTiempoEnfermedadMuerte(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T55")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }     
   
         //Retorna lista de intencionalidad CODE
   public List<Lesion> getListaIntencionalidadCODE(){
        List<Lesion> lLista = null;
       try {
           lLista = new ArrayList<Lesion>(Arrays.asList(
                   (new Lesion()).buscarLesionCODE("INTENCIONALIDAD")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }    
   
         //Retorna lista de sitio ocurrencia CODE
   public List<Lesion> getListaSitioOcurrenciaCODE(){
        List<Lesion> lLista = null;
       try {
           lLista = new ArrayList<Lesion>(Arrays.asList(
                   (new Lesion()).buscarLesionCODE("SITIO OCURRENCIA")));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }    

   public List<MetodoAnticonceptivo> getListaMetodosAnticonceptivos(){
        List<MetodoAnticonceptivo> lLista = null;
       try {
           lLista = new ArrayList<MetodoAnticonceptivo>(Arrays.asList(
                   (new MetodoAnticonceptivo()).buscarMetodoAnticonceptivoCODE()));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }   
   
            //Retorna Lista de Referencias
     public List<Referencia> getListaReferencias(){
        List<Referencia> lLista = null;
       try {
           lLista = new ArrayList<Referencia>(Arrays.asList(
                   (new Referencia()).buscarReferencias()));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
   
    /**
     * @return the oEpiMed
     */
    public EpisodioMedico getEpiMed() {
        return oEpiMed;
    }

    /**
     * @param oEpiMed the oEpiMed to set
     */
    public void setEpiMed(EpisodioMedico oEpiMed) {
        this.oEpiMed = oEpiMed;
    }

    /**
     * @return the nNumeroExpediente
     */
    public int getNumeroExpediente() {
        //System.out.println(nNumeroExpediente);
        return nNumeroExpediente;
    }

    /**
     * @param nNumeroExpediente the nNumeroExpediente to set
     */
    public void setNumeroExpediente(int nNumeroExpediente) {
        this.nNumeroExpediente = nNumeroExpediente;
        //System.out.println(nNumeroExpediente);
    }

    /**
     * @return the nNumHospitalizacion
     */
    public long getNumHospitalizacion() {
        return nNumHospitalizacion;
    }

    /**
     * @param nNumHospitalizacion the nNumHospitalizacion to set
     */
    public void setNumHospitalizacion(long nNumHospitalizacion) {
        this.nNumHospitalizacion = nNumHospitalizacion;
    }

    /**
     * @return the nEpisodio
     */
    public long getEpisodio() {
        return nEpisodio;
    }

    /**
     * @param nEpisodio the nEpisodio to set
     */
    public void setEpisodio(long nEpisodio) {
        this.nEpisodio = nEpisodio;
    }

    /**
     * @return the oReferencia
     */
    public Referencia getReferencia() {
        return oReferencia;
    }

    /**
     * @param oReferencia the oReferencia to set
     */
    public void setReferencia(Referencia oReferencia) {
        this.oReferencia = oReferencia;
    }

    /**
     * @return the bBloqueado
     */
    public boolean getBloqueado() {
        return bBloqueado;
    }

    /**
     * @param bBloqueado the bBloqueado to set
     */
    public void setBloqueado(boolean bBloqueado) {
        this.bBloqueado = bBloqueado;
    }

    /**
     * @return the nTipo
     */
    public int getTipo() {
        return nTipo;
    }

    /**
     * @param nTipo the nTipo to set
     */
    public void setTipo(int nTipo) {
        this.nTipo = nTipo;
    }

    /**
     * @return the bPerfil2
     */
    public boolean getPerfil2() {
        return bPerfil2;
    }

    /**
     * @param bPerfil2 the bPerfil2 to set
     */
    public void setPerfil2(boolean bPerfil2) {
        this.bPerfil2 = bPerfil2;
    }
}

     
    

