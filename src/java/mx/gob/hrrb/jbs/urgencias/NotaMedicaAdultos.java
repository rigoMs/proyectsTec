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
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oNotaAdulto")
@SessionScoped
//@ViewScoped

public class NotaMedicaAdultos{
    private AdmisionUrgs oAdmisionUrgs;
    private String sOpe="";
    private long nFolioPac=0;
    private boolean habilita;
    
    private boolean bBuscado = false;
        
    public NotaMedicaAdultos(){
        habilita=true;
    }
     
    public AdmisionUrgs getNotaAdulto(){
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                try{              
                    setAdmisionUrgs(new AdmisionUrgs());
                    getAdmisionUrgs().getPaciente().setFolioPaciente(nFolioPac);
                    //getAdmisionUrgs().buscarPacienteNotaAdultos();
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
   
    public List<Parametrizacion> getListaTipoCama(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T28"))));
       } catch (Exception ex) {
           Logger.getLogger(NotaMedicaAdultos.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaTriage(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T31"))));
       } catch (Exception ex) {
           Logger.getLogger(NotaMedicaAdultos.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaTipoUrgencia(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T26"))));
       } catch (Exception ex) {
           Logger.getLogger(NotaMedicaAdultos.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaMotivoAtencion(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T27"))));
       } catch (Exception ex) {
           Logger.getLogger(NotaMedicaAdultos.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaAspecto(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T42"))));
       } catch (Exception ex) {
           Logger.getLogger(NotaMedicaAdultos.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaDestino(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T29"))));
       } catch (Exception ex) {
           Logger.getLogger(NotaMedicaAdultos.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaPlanes(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T50"))));
       } catch (Exception ex) {
           Logger.getLogger(NotaMedicaAdultos.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public boolean habilita(int num){
        boolean habilitado=true;
        if(num==0)
            return habilitado=false;
        else if(getNotaAdulto().getAfePrincipal().getCIE10().getDescripcionDiag()!=null && num==1)
            return habilitado=false;
        else if(getNotaAdulto().getAfePrincipal().getCIE10().getDescripcionDiag()!=null && num==2)
            return habilitado=false;
        else if(num==3)
            return habilitado=false;
        else if(getNotaAdulto().getProceRe1().getCIE9().getDescripcion()!=null && num==4)
            return habilitado=false;
        else if(getNotaAdulto().getProceRe2().getCIE9().getDescripcion()!=null && num==5)
            return habilitado=false;
        else if(num==6)
            return habilitado=false;
        else if(getNotaAdulto().getMedicamento1().getMedicamento().getNombreCve()!=null && num==7)
            return habilitado=false;
        else if(getNotaAdulto().getMedicamento2().getMedicamento().getNombreCve()!=null && num==8)
            return habilitado=false;
        return habilitado; 
    }        
    
    //*********************************HOSPITALIZAR*********************************
     public String insertaNotaMedicaAdultos() throws Exception{
        String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        FacesMessage message=null;
        
        afec=oAdmisionUrgs.insertarNotaAdultos();
            
        if(afec==0 || afec!=6){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota Médica Adultos", "Nota Guardada Exitosamente!!!");
            pag="/faces/sesiones/Inicio.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota Médica Adultos", "Ocurrio un Error al Guardar la Nota!!!");
            //getPaciente()=new Paciente();
        }
       
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

    
}