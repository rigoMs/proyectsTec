/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.hospitalizacion.HospitalPsiquiatrico;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oHospPsi")
@SessionScoped
public class DatosHospitalPsiquiatrico {
    private HospitalPsiquiatrico oHosPsi;
    private String sOpe="";
    private boolean bBuscado = false;
    private long nNumHospitalizacion=0;
    private long nFolioPac=0;
    private boolean bBloqueado;
    private boolean bActivar;
    /**
     * Creates a new instance of DatosHospitalPsiquiatrico
     */
    public DatosHospitalPsiquiatrico() {
        oHosPsi = new HospitalPsiquiatrico();
    }
    
    public boolean habilita(int num){
        boolean habilitado=true;
        if(oHosPsi.getTipoUnidadP()!=null && oHosPsi.getTipoUnidadP().equals("T1101") && num==2 && bBloqueado==false)
            return habilitado=false;
        else if(oHosPsi.getTipoUnidadP()!=null && oHosPsi.getTipoUnidadP().equals("T1102") && num==3  && bBloqueado==false)
            return habilitado=false; 
        else
            return habilitado;
    }    

    public boolean requiere(int num){
        boolean habilitado=false;
        if(oHosPsi.getTipoUnidadP()!=null && oHosPsi.getTipoUnidadP().equals("T1101") && num==2)
            return habilitado=true;
        else if(oHosPsi.getTipoUnidadP()!=null && oHosPsi.getTipoUnidadP().equals("T1102") && num==3)
            return habilitado=true; 
        else
            return habilitado;
    }      

    public void guardar(){
        System.out.println("**********************Guardando Datos Hospital Psiquiatrico****************************");
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oHosPsi.insertar();
            else if(this.sOpe.equals("m"))
                nAfec = this.oHosPsi.modificarInsertar();   
            setBuscado(false);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital Psiquiátrico", "Datos Guardados Correctamente");
            else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital Psiquiátrico", "Datos no Guardados :(");
            RequestContext.getCurrentInstance().showMessageInDialog(message);       
        //sOpe="";
    }     
    
    public HospitalPsiquiatrico getCode(){        
        if (!getBuscado()){
            setBuscado(true);
            if (this.getOpe().equals("a")){
                oHosPsi = new HospitalPsiquiatrico();

            }
            else{
                try{           
                    oHosPsi = new HospitalPsiquiatrico();
                    oHosPsi.getHospitalizacion().setNumIngresoHos(nNumHospitalizacion);                    
                    oHosPsi.getPaciente().setFolioPaciente(nFolioPac);
                    oHosPsi.buscarCode();                    
                }catch(Exception e){
                    e.printStackTrace();
                    setBuscado(false);
                }
            }
        }
        return oHosPsi;
    }    
    
    /**
     * @return the oHosPsi
     */
    public HospitalPsiquiatrico getHosPsi() {
        return oHosPsi;
    }

    /**
     * @param oHosPsi the oHosPsi to set
     */
    public void setHosPsi(HospitalPsiquiatrico oHosPsi) {
        this.oHosPsi = oHosPsi;
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
