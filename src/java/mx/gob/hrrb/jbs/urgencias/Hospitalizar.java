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
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oHospitalizar")
@SessionScoped
//@ViewScoped

public class Hospitalizar{
    private AdmisionUrgs oAdmisionUrgs;
    
    private String sOpe="";
    
    private long nFolioPac=0;
    private boolean bBuscado = false;
    //private int nNumeroExpediente=0;
    int a,m,d;
    
    public Hospitalizar(){
        
        oAdmisionUrgs= new AdmisionUrgs();
    }
     
    public String getOpe() {return sOpe;}

    public void setOpe(String sOpe) {this.sOpe = sOpe; System.out.println(sOpe);}
    
    public long getFolioPac() { System.out.println(nFolioPac); return nFolioPac; }

    public void setFolioPac(long nFolioPac) { this.nFolioPac = nFolioPac; System.out.println(nFolioPac);}

    public boolean getBuscado() { return bBuscado;}

    public void setBuscado(boolean bBuscado) {this.bBuscado = bBuscado;System.out.println(bBuscado);}
    /*
    public int getNumeroExpediente() {
        return nNumeroExpediente;
    }

    public void setNumeroExpediente(int nNumeroExpediente) {
        this.nNumeroExpediente = nNumeroExpediente;
    }*/
    
    public AdmisionUrgs getHospiPac(){
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                try{              
                    setAdmisionUrgs(new AdmisionUrgs());
                    oAdmisionUrgs.getPaciente().setFolioPaciente(nFolioPac);
                    //oAdmisionUrgs.getPaciente().getExpediente().setNumero(nNumeroExpediente);
                    //getAdmisionUrgs().buscarBasicosHospitalizar();
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

    
    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(Hospitalizar.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
        //System.out.println("Entra a lista municipios: "+oEstado.getClaveEdo());
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList(
                   (new Municipio()).buscarMunicipio(oAdmisionUrgs.getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(Hospitalizar.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    
    public List<Ciudad> getListaCiudades(){
         //System.out.println("Entra a lista Ciudades: "+oMunicipio.getEstado().getClaveEdo()+" "+ oMunicipio.getClaveMun());
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList(
                   (new Ciudad()).buscarCiudad(oAdmisionUrgs.getPaciente().getEstado().getClaveEdo(), oAdmisionUrgs.getPaciente().getMunicipio().getClaveMun())));
       } catch (Exception ex) {
           Logger.getLogger(Hospitalizar.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<CiudadCP> getListaCP(){
       List<CiudadCP> lListaCP = null;
       try {
           lListaCP = new ArrayList<CiudadCP>(Arrays.asList(
                   (new CiudadCP()).buscarCP(oAdmisionUrgs.getPaciente().getCiudad().getClaveCiu(),oAdmisionUrgs.getPaciente().getMunicipio().getClaveMun(),oAdmisionUrgs.getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(Hospitalizar.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        return lListaCP;
    }
    
        //*********************************HOSPITALIZAR*********************************
     public String insertaHospitalizacion() throws Exception{
        String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        FacesMessage message=null;
        
        afec=oAdmisionUrgs.insertarPacHospitalizacion();
            
        if(afec==0 || afec<7){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospitalización de Paciente", "Hospitalización Fallida!!!");
            pag="/faces/sesiones/Inicio.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospitalización de Paciente", "Hospitalización Exitosa!!!");
            //getPaciente()=new Paciente();
        }
       
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

}