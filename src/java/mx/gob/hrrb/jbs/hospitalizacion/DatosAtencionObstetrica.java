/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.AntecGinecoObstetricos;
import mx.gob.hrrb.modelo.core.AtencionObstetrica;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oAtnObs")
@SessionScoped
public class DatosAtencionObstetrica {
    private AntecGinecoObstetricos oAnteGinObs;
    private AtencionObstetrica oAtnObs;
    private Hospitalizacion oHosp;
    private String sOpe="";
    private long nNumHospitalizacion=0;
    private int nNumeroExpediente=0;
    private boolean bBuscado = false;
    private boolean bBloqueado;
    private boolean bActivar;
    /**
     * Creates a new instance of DatosAtencionObstetrica
     */
    public DatosAtencionObstetrica() {
        oAnteGinObs = new AntecGinecoObstetricos();
        oAtnObs = new AtencionObstetrica();    
        oHosp = new Hospitalizacion();
    }
    
    //oAtnObs.code.atencionObstetrica.tipoAtencion
    public int minimo(int tipo){    
        if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P' && tipo==1)
            return 22;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='A' && tipo==1)
            return 1;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P' && tipo==2)
            return 501;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='A' && tipo==2)
            return 1;
        else
            return 0;
    }        
    
    public int maximo(int tipo){
        if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P' && tipo==1)
            return 45;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='A' && tipo==1)
            return 21;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P' && tipo==2)
            return 7000;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='A' && tipo==2)
            return 500;        
        else
            return 0;
    }

    public boolean habilita(){
        if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P'|| oHosp.getAtencionObstetrica().getTipoAtencion()=='A')
            return false;
        else
            return true;
    }
    
    public boolean habilita2(){
        if(oHosp.getAtencionObstetrica().getTipoAtencion()=='A'){
            oHosp.getAtencionObstetrica().setConProducto(' ');
            oHosp.getAtencionObstetrica().setTipoNacimientoP("");
            return true;            
        }
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P' && getBloqueado()==false)
            return false;
        else
            return true;
    }
    
    public boolean habilitaPro(int tipo){
        boolean habilita=true;
        if(oHosp.getAtencionObstetrica().getTipoAtencion()=='A' && tipo==2)
            habilita=true;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='A' && tipo==3)
            habilita=true;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P' && tipo==2 && oHosp.getAtencionObstetrica().getConProducto()=='M' && bBloqueado==false)
            habilita=false;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P' && tipo==2 && oHosp.getAtencionObstetrica().getConProducto()=='U')
            habilita=true;
        else if(oHosp.getAtencionObstetrica().getTipoAtencion()=='P' && tipo==3 && oHosp.getAtencionObstetrica().getConProducto()=='M'
                && oHosp.getAtencionObstetrica().getP2().getPesoAlNacer()!=0 && bBloqueado==false) 
            habilita=false;
        return habilita;
    }
    
    public void guardar(){
        System.out.println("**********************Guardando Datos Atención Obstetrica****************************");
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosp.insertar();
            else if(this.sOpe.equals("m"))
                nAfec = this.oHosp.modificarInsertarAtencionObstetrica();       
            setBuscado(false);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención Obstétrica", "Datos Guardados Correctamente");
            else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estancia", "Datos no Guardados :(");
            RequestContext.getCurrentInstance().showMessageInDialog(message);       
        //sOpe="";
    }      
    
    public Hospitalizacion getCode(){        
        //System.out.println(sOpe);
        if (!getBuscado()){
            setBuscado(true);
            if (this.getOpe().equals("a")){
                oHosp = new Hospitalizacion();

            }
            else{
                try{        
                    oHosp = new Hospitalizacion();
                    oHosp.setNumIngresoHos(nNumHospitalizacion);
                    oHosp.getExpediente().setNumero(nNumeroExpediente);
                    oHosp.buscarCodeAtencionObstetrica();
                    oHosp.buscarCodeAntecedentesGinecoObstetricos();
                    oHosp.buscarCodeProductos();
                }catch(Exception e){
                    e.printStackTrace();
                    setBuscado(false);
                }
            }
        }
        return oHosp;
    }       
    
    /**
     * @return the oAnteGinObs
     */
    public AntecGinecoObstetricos getAnteGinObs() {
        return oAnteGinObs;
    }

    /**
     * @param oAnteGinObs the oAnteGinObs to set
     */
    public void setAnteGinObs(AntecGinecoObstetricos oAnteGinObs) {
        this.oAnteGinObs = oAnteGinObs;
    }

    /**
     * @return the oAtnObs
     */
    public AtencionObstetrica getAtnObs() {
        return oAtnObs;
    }

    /**
     * @param oAtnObs the oAtnObs to set
     */
    public void setAtnObs(AtencionObstetrica oAtnObs) {
        this.oAtnObs = oAtnObs;
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
