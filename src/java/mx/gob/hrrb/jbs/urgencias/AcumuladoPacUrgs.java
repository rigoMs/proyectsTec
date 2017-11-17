 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oAcumPac")
@SessionScoped
//@ViewScoped

public class AcumuladoPacUrgs{
    private AdmisionUrgs oAdmisionUrgs;
    
    private List<AdmisionUrgs> lAdmision;
    private String activaTabla="display: none;";
    private int año;
    
    public AcumuladoPacUrgs(){
        activaTabla="display: none;";
        lAdmision=null;
        oAdmisionUrgs= new AdmisionUrgs();
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

    public void generar(){
        setActivaTabla("");
        try {
            setAdmision(oAdmisionUrgs.BuscaAcumuladoPac(""+getAño()));
        } catch (Exception ex) {
            Logger.getLogger(BuscaNotasMedicas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the lAdmision
     */
    public List<AdmisionUrgs> getAdmision() {
        return lAdmision;
    }

    /**
     * @param lAdmision the lAdmision to set
     */
    public void setAdmision(List<AdmisionUrgs> lAdmision) {
        this.lAdmision = lAdmision;
    }
    
    /**
     * @return the activaTabla
     */
    public String getActivaTabla() {
        return activaTabla;
    }

    /**
     * @param activaTabla the activaTabla to set
     */
    public void setActivaTabla(String activaTabla) {
        this.activaTabla = activaTabla;
    }

    public int[] getAños(){
        setActivaTabla("display: none;");
       int lLista[] = null, cont=-1;
       String x,y;
       
       try {
           x=oAdmisionUrgs.getFechaSistema();
           y=x.substring(0, x.length()-6);
           
           lLista=new int[(Integer.parseInt(y)-2015)+1];
           for(int i=2015;i<=Integer.parseInt(y);i++){
               cont++;
               lLista[cont]=i;
           }
       } catch (Exception ex) {
           Logger.getLogger(CambioCamas.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        return lLista;
    }

    
    /**
     * @return the año
     */
    public int getAño() {
        return año;
    }

    /**
     * @param año the año to set
     */
    public void setAño(int año) {
        this.año = año;
    }
}