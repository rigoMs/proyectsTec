 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
import org.primefaces.context.RequestContext;
import mx.gob.hrrb.modelo.core.TipoVialidad;
import mx.gob.hrrb.modelo.core.TipoAsentamiento;
/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oAlta")
@SessionScoped
//@ViewScoped

public class AltaHospitalaria{
    private AdmisionUrgs oAdmisionUrgs;
    private String sOpe="";
    private long nFolioPac=0;
    private int nNumeroExpediente=0;
    private String sDestino;
    private String sMotivoEgreso;
    private String sRazonVolunTrasl;
    
    private boolean bBuscado = false;
    int a,m,d;
    //****INICIAN VARIABLES AGREGADAS POR ALBERTO****
    private Date dFecha;
    private Date dHora;
    private String sPiso;
    private Parametrizacion lista;
    private String sRecomendaciones;
    private String sNombrefam;
    private String sTelefono;
    private String sTextigo1;
    private String sTextigo2;
    private String sParentesco;
    //**** TERMINAN VARIABLES AGREGADAS POR ALBERTO****
   
     
    public AdmisionUrgs getAltaHosp(){
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                try{              
                    setAdmisionUrgs(new AdmisionUrgs());
                    getAdmisionUrgs().getPaciente().setFolioPaciente(nFolioPac);
                    getAdmisionUrgs().getPaciente().getExpediente().setNumero(nNumeroExpediente);
                }catch(Exception e){
                    e.printStackTrace();
                    bBuscado = false;
                }
            }
        }
        return oAdmisionUrgs;
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

    
    public String getOpe() {return sOpe;}

    public void setOpe(String sOpe) {this.sOpe = sOpe; System.out.println(sOpe);}
    
    public long getFolioPac() {return nFolioPac; }

    public void setFolioPac(long nFolioPac) throws Exception { this.nFolioPac = nFolioPac;
    }

    public boolean getBuscado() { return bBuscado;}

    public void setBuscado(boolean bBuscado) {this.bBuscado = bBuscado;System.out.println(bBuscado);}
   
    
    
    public List<Estado> getListaEstados(){
                    recuperaDatosHosp();
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(AltaHospitalaria.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
        //System.out.println("Entra a lista municipios: "+oEstado.getClaveEdo());
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList((new Municipio()).buscarMunicipio(oAdmisionUrgs.getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(AltaHospitalaria.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    //*************************************************************************************************
    public List<TipoVialidad> getListaTipoVialidad(){
    List<TipoVialidad> lList=null;
    try{
    lList = new ArrayList<TipoVialidad>(Arrays.asList(new TipoVialidad().buscarTodos()));
    }catch(Exception ex){
    Logger.getLogger(AltaHospitalaria.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lList;
    }
    //*********************************************************************************************
    public List<TipoAsentamiento>getListaTipoAsentamiento(){
    List<TipoAsentamiento>lList=null;
    try{
    lList=new ArrayList<TipoAsentamiento>(Arrays.asList(new TipoAsentamiento().buscarTodos()));
    }catch(Exception ex){
    Logger.getLogger(AltaHospitalaria.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return lList;
    }
    
    
    
    
    
    //***********************************************************************************************
    public List<Ciudad> getListaCiudades(){
         //System.out.println("Entra a lista Ciudades: "+oMunicipio.getEstado().getClaveEdo()+" "+ oMunicipio.getClaveMun());
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList(
                   (new Ciudad()).buscarCiudad(oAdmisionUrgs.getPaciente().getEstado().getClaveEdo(), oAdmisionUrgs.getPaciente().getMunicipio().getClaveMun())));
       } catch (Exception ex) {
           Logger.getLogger(AltaHospitalaria.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<CiudadCP> getListaCP(){
       List<CiudadCP> lListaCP = null;
       try {
           lListaCP = new ArrayList<CiudadCP>(Arrays.asList(
                   (new CiudadCP()).buscarCP(oAdmisionUrgs.getPaciente().getCiudad().getClaveCiu(),oAdmisionUrgs.getPaciente().getMunicipio().getClaveMun(),oAdmisionUrgs.getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(AltaHospitalaria.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        return lListaCP;
    }
    
    
    //*********************************DAR DE ALTA*********************************
     public void insertaAltaHospitalaria() throws Exception{
        //String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        FacesMessage message=null;
        
        afec=oAdmisionUrgs.insertarAltaHospi();
        System.out.println("Cadena vacia osea no hay seguro repetido");
            
        if(afec!=0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Hospitalaria", "Alta Hospitalaria Exitosa!!!");
            oAdmisionUrgs.buscarPacienteAlta();
            
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Paciente", "Alta Hospitalaria Fallida!!!");
            //getPaciente()=new Paciente();
        }
       
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        //return pag;
    }

     
     //*********************************DAR DE ALTA FISICA*********************************
     public String insertaAltaFisica() throws Exception{
        String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        FacesMessage message=null;
        System.out.println("entra a insetaaltafisica*****************************************");
        
        afec=oAdmisionUrgs.insertarAltaFisica();
        //message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Paciente", "Ninguna Derechohabiencia");
            
        if(afec!=0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Física", "Alta Física Exitosa!!!");
            pag="/faces/sesiones/Inicio.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Física", "Alta Física Fallida!!!");
            //getPaciente()=new Paciente();
        }
       
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }
     
     public void recuperaDatosHosp(){
         System.out.println("Entra a recupera "+oAdmisionUrgs.getDestinoSTR());
         if(oAdmisionUrgs.getDestinoSTR()!=null){
             System.out.println("Entra a if destino");
         if(oAdmisionUrgs.getDestinoSTR().compareTo("")==0 && oAdmisionUrgs.getAltaHospitalaria()==null)
             setDestino("false");
         else if(oAdmisionUrgs.getDestinoSTR().compareTo("")==0 && oAdmisionUrgs.getAltaHospitalaria()!=null)
             setDestino("false");
         else if(oAdmisionUrgs.getDestinoSTR().compareTo("")!=0 && oAdmisionUrgs.getAltaHospitalaria()==null)
             setDestino("false");
         else
             setDestino("true");
         
         if(oAdmisionUrgs.getRazonAltaVolunTrasl().compareTo("")==0)
             setRazonVolunTrasl("false");
         else
             setRazonVolunTrasl("true");
         
         if(oAdmisionUrgs.getMotivoEgresoP().compareTo("")==0  && oAdmisionUrgs.getAltaHospitalaria()==null)
             setMotivoEgreso("false");
         else if(oAdmisionUrgs.getMotivoEgresoP().compareTo("")==0  && oAdmisionUrgs.getAltaHospitalaria()!=null)
             setMotivoEgreso("false");
         else if(oAdmisionUrgs.getMotivoEgresoP().compareTo("")!=0 && oAdmisionUrgs.getAltaHospitalaria()==null)
             setMotivoEgreso("false");
         else 
             setMotivoEgreso("true");
     }
     }
     
     
        public boolean habilitaRazonDestino(){
            if(oAdmisionUrgs.getDestinoSTR()!=null && oAdmisionUrgs.getAltaHospitalaria()==null){
                return oAdmisionUrgs.getDestinoSTR().compareTo("T3705") != 0;
            }
           
            else{return true;}
            
        }
        
    public boolean habilitaRazonAltaVolun(){
        if(oAdmisionUrgs.getAltaHospitalaria()!=null && oAdmisionUrgs.getRazonAltaVolunTrasl().compareTo("")!=0 && (oAdmisionUrgs.getMotivoEgresoP().compareTo("T3803")==0 || oAdmisionUrgs.getMotivoEgresoP().compareTo("T3804")==0))
        {
        return true;
        }
        else if(oAdmisionUrgs.getMotivoEgresoP()!=null){
            return !(oAdmisionUrgs.getMotivoEgresoP().compareTo("T3803")==0 || oAdmisionUrgs.getMotivoEgresoP().compareTo("T3804")==0);
            }
            
            return true;
            
        }
    
        public boolean requiereDestino(){
        return oAdmisionUrgs.getDestinoSTR().compareTo("T3705") == 0;
        }
        
        public boolean requiereRazonAlta(){
        return oAdmisionUrgs.getMotivoEgresoP().compareTo("T3803")==0 || oAdmisionUrgs.getMotivoEgresoP().compareTo("T3804")==0;
        }
    
    
//**************INICIAN METODOS CREADOS POR ALBERTO ****************************        
        public void lanzaDialogo(){            
            if(this.oAdmisionUrgs.getMotivoEgresoP().compareTo("T3803 ") == 0)
                RequestContext.getCurrentInstance().execute("PF('dialogo_datos').show();");            
        }
        
        public Parametrizacion[] getListaParentesco(){
            
            lista = new Parametrizacion();
            try{
                return lista.buscarTabla("T40");
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        
        public Date getFechaActual(){
            return new Date();
        }
        public void lanzaDialogo2(){
            RequestContext.getCurrentInstance().execute("PF('dialogo_impresion').show();");
        }
        public void reset(){            
            this.setFecha(null);
            this.setHora(null);
            this.setPiso("");
            this.setRecomendaciones("");
            this.setNombreFamiliar("");
            this.setTelefono("");
            this.setTestigo1("");
            this.setTestigo2("");
            this.oAdmisionUrgs.setRazonAltaVolunTrasl("");            
        }
//**************TERMINAN METODOS CREADOS POR ALBERTO ****************************
        
        
    /**
     * @return the sDestino
     */
    public String getDestino() {
        return sDestino;
    }

    /**
     * @param sDestino the sDestino to set
     */
    public void setDestino(String sDestino) {
        this.sDestino = sDestino;
    }

    /**
     * @return the sMotivoEgreso
     */
    public String getMotivoEgreso() {
        return sMotivoEgreso;
    }

    /**
     * @param sMotivoEgreso the sMotivoEgreso to set
     */
    public void setMotivoEgreso(String sMotivoEgreso) {
        this.sMotivoEgreso = sMotivoEgreso;
    }

    /**
     * @return the sRazonVolunTrasl
     */
    public String getRazonVolunTrasl() {
        return sRazonVolunTrasl;
    }

    /**
     * @param sRazonVolunTrasl the sRazonVolunTrasl to set
     */
    public void setRazonVolunTrasl(String sRazonVolunTrasl) {
        this.sRazonVolunTrasl = sRazonVolunTrasl;
    }
    
    public Date getFecha(){
        return this.dFecha;
    }
    public void setFecha(Date dFecha){
        this.dFecha = dFecha;
    }
    public Date getHora(){
        return this.dHora;
    }
    public void setHora(Date dHora){
        this.dHora = dHora;
    }
    public String getPiso(){
        return this.sPiso;
    }
    public void setPiso(String sPiso){
        this.sPiso = sPiso.toUpperCase();
    }
    public String getRecomendaciones(){
        return this.sRecomendaciones;
    }
    public void setRecomendaciones(String sRecomendaciones){
        this.sRecomendaciones = sRecomendaciones.toUpperCase();
    }
    public String getNombreFamiliar(){
        return this.sNombrefam;
    }
    public void setNombreFamiliar(String sNombrefam){
        this.sNombrefam = sNombrefam.toUpperCase();
    }
    public String getTelefono(){
        return this.sTelefono;
    }
    public void setTelefono(String sTelefono){
        this.sTelefono = sTelefono;
    }
    public String getTestigo1(){
        return this.sTextigo1;
    }
    public void setTestigo1(String sTestigo1){
        this.sTextigo1 = sTestigo1.toUpperCase();
    }
    public String getTestigo2(){
        return this.sTextigo2;
    }
    public void setTestigo2(String sTestigo2){
        this.sTextigo2 = sTestigo2.toUpperCase();
    }
    public String getParentesco(){
        return this.sParentesco;
    }
    public void setParentesco(String sParentesco){
        this.sParentesco = sParentesco;
    }
}