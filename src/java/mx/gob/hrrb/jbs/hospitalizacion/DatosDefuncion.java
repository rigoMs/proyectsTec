/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.AfeccionTratada;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oDefuncion")
@SessionScoped
public class DatosDefuncion {
    private AfeccionTratada oAfeA; 
    private AfeccionTratada oAfeB; 
    private AfeccionTratada oAfeC; 
    private AfeccionTratada oAfeD; 
    private AfeccionTratada oAfeE;
    private AfeccionTratada oAfeF;
    private Hospitalizacion oHosp;
    private String sOpe="";
    private long nFolioPac=0;
    private long nNumHospitalizacion=0;
    private int nNumeroExpediente=0;
    private long nEpisodio=0;
    private boolean bBuscado = false;
    private boolean bBloqueado;
    private boolean bActivar;
    /**
     * Creates a new instance of DatosDefuncion
     */
    public DatosDefuncion() {
        oAfeA = new AfeccionTratada();
        oAfeB = new AfeccionTratada();
        oAfeC = new AfeccionTratada();
        oAfeD = new AfeccionTratada();
        oAfeE = new AfeccionTratada();
        oAfeF = new AfeccionTratada();
        oHosp = new Hospitalizacion();
    }

    public boolean habilita(int num){
        boolean habilitado=true;
        if(num==0)
            return habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().equals("") && num==1 && bBloqueado==false)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().equals("") && num==2 && bBloqueado==false)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag().equals("") && num==3 && bBloqueado==false)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag().equals("") && num==4 && bBloqueado==false)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag().equals("") && num==5 && bBloqueado==false)
             habilitado=false;

            return habilitado;
  
    }    
    
    public boolean motivoE(){
        boolean deshabilitado=false;
        if(bBloqueado==false){
            if(oHosp.getPaciente().getFolioDefuncion()!=null && !oHosp.getPaciente().getFolioDefuncion().equals("")){
                System.out.println("Deshabilitado: Entre 1");
                deshabilitado=true;
            }else if(oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().equals("")){
                System.out.println("Deshabilitado: Entre 2");
                deshabilitado=true;
            }else{ 
                System.out.println("Deshabilitado: Entre 3");
                deshabilitado=false;}
        }else if(bBloqueado==true)
            deshabilitado=true;
        
        return deshabilitado;
    }
    
    public void guardar(){
        System.out.println("**********************Guardando Datos Defunción****************************");
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosp.insertar();
            else if(this.sOpe.equals("m"))
                nAfec = this.oHosp.modificarInsertarDefuncion();    
                setBuscado(false);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Defunción", "Datos Guardados Correctamente");
            else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Defunción", "Datos no Guardados :(");
            RequestContext.getCurrentInstance().showMessageInDialog(message);       
        //sOpe="";
    }    
    
    public Hospitalizacion getCode(){    
        if (!getBuscado()){
            setBuscado(true);
            if (this.getOpe().equals("a")){
                oHosp = new Hospitalizacion();

            }
            else{
                try{       
                    oHosp = new Hospitalizacion();
                    oHosp.getPaciente().setFolioPaciente(nFolioPac);
                    oHosp.getEpisodioMedico().setClaveEpisodio(nEpisodio);
                    oHosp.setNumIngresoHos(nNumHospitalizacion);
                    oHosp.buscarCodeDatosDefuncion();
                    oHosp.buscarCodeAfeccionesDefuncion();
                }catch(Exception e){
                    e.printStackTrace();
                    setBuscado(false);
                }
            }
        }
        return oHosp;
    }     
    
    /**
     * @return the oAfeA
     */
    public AfeccionTratada getAfeA() {
        return oAfeA;
    }

    /**
     * @param oAfeA the oAfeA to set
     */
    public void setAfeA(AfeccionTratada oAfeA) {
        this.oAfeA = oAfeA;
    }

    /**
     * @return the oAfeB
     */
    public AfeccionTratada getAfeB() {
        return oAfeB;
    }

    /**
     * @param oAfeB the oAfeB to set
     */
    public void setAfeB(AfeccionTratada oAfeB) {
        this.oAfeB = oAfeB;
    }

    /**
     * @return the oAfeC
     */
    public AfeccionTratada getAfeC() {
        return oAfeC;
    }

    /**
     * @param oAfeC the oAfeC to set
     */
    public void setAfeC(AfeccionTratada oAfeC) {
        this.oAfeC = oAfeC;
    }

    /**
     * @return the oAfeD
     */
    public AfeccionTratada getAfeD() {
        return oAfeD;
    }

    /**
     * @param oAfeD the oAfeD to set
     */
    public void setAfeD(AfeccionTratada oAfeD) {
        this.oAfeD = oAfeD;
    }

    /**
     * @return the oAfeE
     */
    public AfeccionTratada getAfeE() {
        return oAfeE;
    }

    /**
     * @param oAfeE the oAfeE to set
     */
    public void setAfeE(AfeccionTratada oAfeE) {
        this.oAfeE = oAfeE;
    }
    
    /**
     * @return the oAfeF
     */
    public AfeccionTratada getAfeF() {
        return oAfeF;
    }

    /**
     * @param oAfeF the oAfeF to set
     */
    public void setAfeF(AfeccionTratada oAfeF) {
        this.oAfeF = oAfeF;
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
    }

    /**
     * @return the nFolioPac
     */
    public long getFolioPac() {
        return nFolioPac;
    }

    /**
     * @param nFolioPac the nFolioPac to set
     */
    public void setFolioPac(long nFolioPac) {
        this.nFolioPac = nFolioPac;
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
    }

    /**
     * @return the nNumeroExpediente
     */
    public int getNumeroExpediente() {
        return nNumeroExpediente;
    }

    /**
     * @param nNumeroExpediente the nNumeroExpediente to set
     */
    public void setNumeroExpediente(int nNumeroExpediente) {
        this.nNumeroExpediente = nNumeroExpediente;
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
     * @return the bActivar
     */
    public boolean getActivar() {
        return bActivar;
    }

    /**
     * @param bActivar the bActivar to set
     */
    public void setActivar(boolean bActivar) {
        if(bActivar ==true){
            guardar();
            bActivar=false;
        }
        this.bActivar = bActivar;
    }    
}
