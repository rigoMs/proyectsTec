 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.EpisodioMedico;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oHojaLesionLlena")
@SessionScoped
//@ViewScoped

public class HojaLesionLlena{
    private EpisodioMedico oEpisodio;
    private String sOpe="";
    private long nFolioPac=0;
    private long nClaveEpisodio=0;
    private boolean habilita;
    private String sDiscap;
    private String sAtenPre;
    private String sTipoAgresor;
    private String sSexoAgresor;
    private String dFechaAtencion;
    private String dFechaOcurrencia;
    private String sDiaFestivo;
    private String bEsEmbarazada;
    private String bSabeLeer;
    private String dHoraUrgs;
    private String sSiVehiculo;
    private String sAutoinflingido;
    private String sMinisterio;
    private boolean bBuscado = false;
    
        
    public HojaLesionLlena(){
        oEpisodio= new EpisodioMedico();
        habilita=true;
    }
     
    public EpisodioMedico getHojaLlena(){
        //System.out.println("entra a getHojaLlena");
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                try{             
                    //System.out.println("IF de getHojaLlena");
                    setEpisodio(new EpisodioMedico());
                    getEpisodio().getPaciente().setFolioPaciente(nFolioPac);
                    getEpisodio().setClaveEpisodio(nClaveEpisodio);
                    getEpisodio().buscarHojaLesionLlenaAfectado();
                    getEpisodio().buscarHojaLesionLlenaEvento();
                    getEpisodio().buscarHojaLesionLlenaAgresor();
                    getEpisodio().buscarHojaLesionLlenaAtencion();
                    getEpisodio().buscarHojaLesionLlenaAtencionDiag();
                    
                    if(getEpisodio().getPaciente().getDiscapacitado()==true)
                        setDiscap("SI");
                    else
                        setDiscap("NO");
                    
                    if(getEpisodio().getHojaLesiones().getLesion().getAtnPreHospitalaria()==true)
                        setAtenPre("SI");
                    else
                        setAtenPre("NO");
                    
                    if(getEpisodio().getHojaLesiones().getAgresor().getTipoAgresor()=='1')
                        setTipoAgresor("ÚNICO");
                    else if(getEpisodio().getHojaLesiones().getAgresor().getTipoAgresor()=='2')
                        setTipoAgresor("MAS DE UNO");
                    
                    if(getEpisodio().getHojaLesiones().getAgresor().getSexoAgresor()=='M')
                        setSexoAgresor("Masculino");
                    else if(getEpisodio().getHojaLesiones().getAgresor().getSexoAgresor()=='F')
                        setSexoAgresor("Femenino");
                    else
                        setSexoAgresor("");
                    
                    if(getEpisodio().getHojaLesiones().getLesion().getDiaFestivo()=='S')
                        setDiaFestivo("SI");
                    else
                        setDiaFestivo("NO");
                    
                    if(getEpisodio().getHojaLesiones().getEsEmbarazada().compareTo("S")==0)
                        setEsEmbarazada("SI");
                    else if(getEpisodio().getHojaLesiones().getEsEmbarazada().compareTo("N")==0)
                        setEsEmbarazada("NO");
                    else
                        setEsEmbarazada("");
                    
                    if(getEpisodio().getHojaLesiones().getSabeLeerEscribir()=='S')
                        setSabeLeer("SI");
                    else
                        setSabeLeer("NO");
                    
                    if(getEpisodio().getHojaLesiones().getAtencion().getMinisterioPublico().compareTo("S")==0)
                        setMinisterio("SI");
                    else
                        setMinisterio("NO");
                    
                   SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                //System.out.println("fechaIngreso: "+fechaIng.format(getFechaIngreso()));
                   setFechaAtencion(fechaIng.format(getEpisodio().getHojaLesiones().getAtencion().getFechaAtencion()));
                   setFechaOcurrencia(fechaIng.format(getEpisodio().getHojaLesiones().getLesion().getFechaOcurrencia()));
                 
                   
                   SimpleDateFormat horaU = new SimpleDateFormat("HH:mm");
                   
                   if(getEpisodio().getHojaLesiones().getAtencion().getAtnUrgs()!=null)
                   setHoraUrgs(horaU.format(getEpisodio().getHojaLesiones().getAtencion().getAtnUrgs()));
                 
                   if(getEpisodio().getHojaLesiones().getLesion().getCasoEventoAuto()!=null)
                        if(getEpisodio().getHojaLesiones().getLesion().getCasoEventoAuto().compareTo("U")==0)
                                setAutoinflingido("ÚNICA VEZ");
                        else if(getEpisodio().getHojaLesiones().getLesion().getCasoEventoAuto().compareTo("M")==0)
                            setAutoinflingido("Repetido");
                        else 
                            setAutoinflingido("");
                }catch(Exception e){
                    e.printStackTrace();
                    bBuscado = false;
                }
            }
        }
        return oEpisodio;
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

    /**
     * @return the oEpisodio
     */
    public EpisodioMedico getEpisodio() {
        return oEpisodio;
    }

    /**
     * @param oEpisodio the oEpisodio to set
     */
    public void setEpisodio(EpisodioMedico oEpisodio) {
        this.oEpisodio = oEpisodio;
    }

    /**
     * @return the sDiscap
     */
    public String getDiscap() {
        return sDiscap;
    }

    /**
     * @param sDiscap the sDiscap to set
     */
    public void setDiscap(String sDiscap) {
        this.sDiscap = sDiscap;
    }

    /**
     * @return the sAtenPre
     */
    public String getAtenPre() {
        return sAtenPre;
    }

    /**
     * @param sAtenPre the sAtenPre to set
     */
    public void setAtenPre(String sAtenPre) {
        this.sAtenPre = sAtenPre;
    }

    /**
     * @return the sTipoAgresor
     */
    public String getTipoAgresor() {
        return sTipoAgresor;
    }

    /**
     * @param sTipoAgresor the sTipoAgresor to set
     */
    public void setTipoAgresor(String sTipoAgresor) {
        this.sTipoAgresor = sTipoAgresor;
    }

    /**
     * @return the sSexoAgresor
     */
    public String getSexoAgresor() {
        return sSexoAgresor;
    }

    /**
     * @param sSexoAgresor the sSexoAgresor to set
     */
    public void setSexoAgresor(String sSexoAgresor) {
        this.sSexoAgresor = sSexoAgresor;
    }

    /**
     * @return the dFechaAtencion
     */
    public String getFechaAtencion() {
        return dFechaAtencion;
    }

    /**
     * @param dFechaAtencion the dFechaAtencion to set
     */
    public void setFechaAtencion(String dFechaAtencion) {
        this.dFechaAtencion = dFechaAtencion;
    }

    /**
     * @return the dFechaOcurrencia
     */
    public String getFechaOcurrencia() {
        return dFechaOcurrencia;
    }

    /**
     * @param dFechaOcurrencia the dFechaOcurrencia to set
     */
    public void setFechaOcurrencia(String dFechaOcurrencia) {
        this.dFechaOcurrencia = dFechaOcurrencia;
    }

    /**
     * @return the sDiaFestivo
     */
    public String getDiaFestivo() {
        return sDiaFestivo;
    }

    /**
     * @param sDiaFestivo the sDiaFestivo to set
     */
    public void setDiaFestivo(String sDiaFestivo) {
        this.sDiaFestivo = sDiaFestivo;
    }

    /**
     * @return the bEsEmbarazada
     */
    public String getEsEmbarazada() {
        return bEsEmbarazada;
    }

    /**
     * @param bEsEmbarazada the bEsEmbarazada to set
     */
    public void setEsEmbarazada(String bEsEmbarazada) {
        this.bEsEmbarazada = bEsEmbarazada;
    }

    /**
     * @return the bSabeLeer
     */
    public String getSabeLeer() {
        return bSabeLeer;
    }

    /**
     * @param bSabeLeer the bSabeLeer to set
     */
    public void setSabeLeer(String bSabeLeer) {
        this.bSabeLeer = bSabeLeer;
    }

    /**
     * @return the dHoraUrgs
     */
    public String getHoraUrgs() {
        return dHoraUrgs;
    }

    /**
     * @param dHoraUrgs the dHoraUrgs to set
     */
    public void setHoraUrgs(String dHoraUrgs) {
        this.dHoraUrgs = dHoraUrgs;
    }

    /**
     * @return the sSiVehiculo
     */
    public String getSiVehiculo() {
        return sSiVehiculo;
    }

    /**
     * @param sSiVehiculo the sSiVehiculo to set
     */
    public void setSiVehiculo(String sSiVehiculo) {
        this.sSiVehiculo = sSiVehiculo;
    }

    /**
     * @return the sAutoinflingido
     */
    public String getAutoinflingido() {
        return sAutoinflingido;
    }

    /**
     * @param sAutoinflingido the sAutoinflingido to set
     */
    public void setAutoinflingido(String sAutoinflingido) {
        this.sAutoinflingido = sAutoinflingido;
    }

    /**
     * @return the sMinisterio
     */
    public String getMinisterio() {
        return sMinisterio;
    }

    /**
     * @param sMinisterio the sMinisterio to set
     */
    public void setMinisterio(String sMinisterio) {
        this.sMinisterio = sMinisterio;
    }
}