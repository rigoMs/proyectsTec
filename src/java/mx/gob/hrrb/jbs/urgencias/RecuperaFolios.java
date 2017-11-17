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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.FamiliarCercano;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oRecupera")
@SessionScoped
//@ViewScoped

public class RecuperaFolios{
    private AdmisionUrgs oAdmisionUrgs;
    
    private String sOpe="";
    
    private boolean bBuscado = false;
    int a,m,d;
    
    public RecuperaFolios(){
        
        oAdmisionUrgs= new AdmisionUrgs();
    }
     
    public AdmisionUrgs getRecuperaFolio(){
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("c")){
                try{              
                    setAdmisionUrgs(new AdmisionUrgs());
                    getAdmisionUrgs().buscarFolioAdmision();
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
           Logger.getLogger(consultaModificaPac.class.getName()).log(Level.SEVERE, null, ex);
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
           Logger.getLogger(consultaModificaPac.class.getName()).log(Level.SEVERE, null, ex);
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
           Logger.getLogger(consultaModificaPac.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<CiudadCP> getListaCP(){
       List<CiudadCP> lListaCP = null;
       try {
           lListaCP = new ArrayList<CiudadCP>(Arrays.asList(
                   (new CiudadCP()).buscarCP(oAdmisionUrgs.getPaciente().getCiudad().getClaveCiu(),oAdmisionUrgs.getPaciente().getMunicipio().getClaveMun(),oAdmisionUrgs.getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(CambioCamas.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        return lListaCP;
    }
    
    public boolean requerida(){
        if(getAdmisionUrgs().getCama().getNumero().compareTo("")==0)
            return false;
        else
            return true;
        
    }
    
    public boolean activaCama(){
        if(getAdmisionUrgs().getCama().getNumero().compareTo("")==0)
            return true;
        else
            return false;
        
    }
    
}