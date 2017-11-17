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
import mx.gob.hrrb.modelo.core.Lesion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oLesion")
@SessionScoped
public class DatosLesion {
    private AfeccionTratada oAfeLesion;
    private Lesion oLesion;
    private String sOpe="";
    private boolean bBuscado = false;
    private long nClaveEpisodio=0;
    private long nFolioPac=0; 
    private long nNumIngresoHosp=0;
    private boolean bBloqueado;
    private boolean bActivar;
    /**
     * Creates a new instance of DatosLesion
     */
    public DatosLesion() {
        oAfeLesion = new AfeccionTratada();
        oLesion = new Lesion();
    }
    
    public boolean habilita(int num){
        boolean habilitado=true;
        if(oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag()!=null && !oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag().equals("")  && num==1 && bBloqueado==false)
            habilitado=false;            

            return habilitado;
    }    

    public boolean requiere(int num){
        boolean habilitado=false;
        System.out.println("Lesion++"+oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag()+"++");
        if(oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag()!=null && !oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag().isEmpty()
                && !oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag().equals("") && num==1)
             habilitado=true;
        else if(oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag()!=null && !oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag().isEmpty()
                && !oLesion.getAfeccionTratada().getCIE10().getDescripcionDiag().equals("") && num==2)
             habilitado=true; 
        
            return habilitado;
    } 

    public void guardar(){
        System.out.println("**********************Guardando Datos Lesión****************************");
    FacesMessage message=null;
    int nAfec = 0;
        try{
            if (this.sOpe.equals("a"))
                nAfec = this.oLesion.insertar();
            else if(this.sOpe.equals("m"))
                nAfec = this.oLesion.modificarInsertar();    
            setBuscado(false);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lesión", "Datos Guardados Correctamente");
            else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lesión", "Datos no Guardados :(");
            RequestContext.getCurrentInstance().showMessageInDialog(message);       
        //sOpe="";
    } 

    public Lesion getCode(){        
        if (!getBuscado()){
            setBuscado(true);
            if (this.getOpe().equals("a")){
                oLesion = new Lesion();

            }
            else{
                try{        
                    oLesion = new Lesion();
                    oLesion.setFolioPaciente(nFolioPac);
                    oLesion.setClaveEpisodio(nClaveEpisodio);
                    oLesion.setNumIngresoHosp(nNumIngresoHosp);
                    oLesion.buscarCode();
                }catch(Exception e){
                    e.printStackTrace();
                    setBuscado(false);
                }
            }
        }
        return oLesion;
    }       
    
    /**
     * @return the oAfeLesion
     */
    public AfeccionTratada getAfeLesion() {
        return oAfeLesion;
    }

    /**
     * @param oAfeLesion the oAfeLesion to set
     */
    public void setAfeLesion(AfeccionTratada oAfeLesion) {
        this.oAfeLesion = oAfeLesion;
    }

    /**
     * @return the oLesion
     */
    public Lesion getLesion() {
        return oLesion;
    }

    /**
     * @param oLesion the oLesion to set
     */
    public void setLesion(Lesion oLesion) {
        this.oLesion = oLesion;
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
     * @return the nClaveEpisodio
     */
    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    /**
     * @param nClaveEpisodio the nClaveEpisodio to set
     */
    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
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
     * @return the nNumIngresoHosp
     */
    public long getNumIngresoHosp() {
        return nNumIngresoHosp;
    }

    /**
     * @param nNumIngresoHosp the nNumIngresoHosp to set
     */
    public void setNumIngresoHosp(long nNumIngresoHosp) {
        this.nNumIngresoHosp = nNumIngresoHosp;
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
