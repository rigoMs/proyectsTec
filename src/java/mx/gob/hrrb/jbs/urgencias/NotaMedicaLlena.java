 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oNotaLlena")
@SessionScoped
//@ViewScoped

public class NotaMedicaLlena{
    private AdmisionUrgs oAdmisionUrgs;
    private String sOpe="";
    private long nFolioPac=0;
    private long nClaveEpisodio=0;
    private boolean habilita;
    
    private boolean bBuscado = false;
    private boolean bConsultaExpediente=false;
        
    public NotaMedicaLlena(){
        oAdmisionUrgs= new AdmisionUrgs();
        habilita=true;
    }
     
    public AdmisionUrgs getNotaLlena(){
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                try{              
                    setAdmisionUrgs(new AdmisionUrgs());
                    System.out.println(nFolioPac+" "+nClaveEpisodio+" "+sOpe);
                    getAdmisionUrgs().getPaciente().setFolioPaciente(nFolioPac);
                    getAdmisionUrgs().setClaveEpisodio(nClaveEpisodio);
                    getAdmisionUrgs().buscarNotaMedicaLlena();
                    getAdmisionUrgs().buscarNotaMedicaLlenaDiag();
                    getAdmisionUrgs().buscarNotaMedicaLlenaProc();
                    getAdmisionUrgs().buscarNotaMedicaLlenaMed();
                    if(bConsultaExpediente!=true)
                        getAdmisionUrgs().buscaDatosMedicoUrg();
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
    
    public boolean getConsultaExpediente(){
        return bConsultaExpediente;
    }
    
    public void setConsultaExpediente(boolean bConsultaExpediente){
        this.bConsultaExpediente=bConsultaExpediente;
    }
}