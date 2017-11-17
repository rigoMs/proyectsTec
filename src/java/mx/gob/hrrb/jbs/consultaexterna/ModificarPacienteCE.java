/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Seguro;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oModPac")
@SessionScoped
public class ModificarPacienteCE {
    private Paciente oPac;
    private Seguro oSeg;
    /**
     * Creates a new instance of ModificarPacienteCE
     */
    public ModificarPacienteCE() {
        oPac=new Paciente();
        oSeg=new Seguro();
    }

    public Paciente getPac() {
        return oPac;
    }

    public void setPac(Paciente oPac) {
        this.oPac = oPac;
    }
    
    public boolean habilitaListas(){
        if (oPac.getOtroPais().compareToIgnoreCase("MÉXICO")==0 || oPac.getOtroPais().compareToIgnoreCase("MEXICO")==0)
            return false;
        else
            return true;
    }
    
    public List<Ciudad> getListaCiudades(){
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList((new Ciudad().buscaCiudadCP(oPac.getEstado().getClaveEdo(), oPac.getMunicipio().getClaveMun(), oPac.getCodigoPos()))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
     public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList((new Municipio().buscarMunicipio(oPac.getEstado().getClaveEdo()))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public String modificaPac() throws Exception{
        String pag="/faces/sesiones/BuscarPaciente.xhtml";
        int afec=0;
        FacesMessage message=null;
        boolean encontrado=oSeg.buscarSeguroPop(oPac.getSeg().getNumero(), oPac.getFolioPaciente());
        if (encontrado==false){
        afec=oPac.modificarPacCE();
        
        if(afec==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificacion de Paciente", "Modificación fallida :(");
            pag="/faces/sesiones/ModificarPacienteCE.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificacion de Paciente", "Modificación exitosa :)");
            oPac=new Paciente();
        }
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificacion de Paciente", "El número de seguro popular ya existe para otro paciente");
            pag="/faces/sesiones/ModificarPacienteCE.xhtml";
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

    public Seguro getSeg() {
        return oSeg;
    }

    public void setSeg(Seguro oSeg) {
        this.oSeg = oSeg;
    }
}
