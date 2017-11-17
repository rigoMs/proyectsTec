
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.enfermeria.HojaSupervisionTococirugia;
import mx.gob.hrrb.modelo.enfermeria.reporte.ReporteMensualTocoCirugia;

/**
 *
 * @author Javier
 */
@ManagedBean(name="oBToco")
@ViewScoped
public class BuscaHojaSupervTocoYReporteMensualJB implements Serializable {    
   
    private HojaSupervisionTococirugia oToco;
    private HojaSupervisionTococirugia[] arrHjToco=null;
    
    private ReporteMensualTocoCirugia oReporteMensual;
    private ReporteMensualTocoCirugia[] arrMensualToco;
    private boolean bRendering;
    
    private int nLegrados;
    private int nMadresMenos20Legrado;
    private int nPartoEutocico;
    private int nPartoDisctocico;
    private int nCesarea;
    private int nMadreMenor20Nac;
    private int nMenos2500gr35;
    private int nMayor2500gr35;
    private int nMenor2500gr36;
    private int nMayor2500gr36;
    private int n20a26Semanas;
    private int n26MasSemanas;
    private int nLui;
    private int nDFMaterna;
    private int nDFRn;
    private int nDIUParto;
    private int nDIUCesara;
    private int nDIUAborto;
    private int nOTSParto;
    private int nOTSCesarea;
    private int nTSAborto;
    private int nSolHor;
    private int nAcp20;
    private int nOTB;
    
    
    public BuscaHojaSupervTocoYReporteMensualJB() {
        oToco= new HojaSupervisionTococirugia();
        oReporteMensual= new ReporteMensualTocoCirugia();
    }
  
    public void buscaHojaSupervisioTococirugia(){
        if(this.getHojaToco().getFechaSupervicion()==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","se nesecita una fecha"));
        }else{
            try {
                arrHjToco= oToco.BuscaTodosHojaSupervisionTococirugia();
                if(arrHjToco==null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se encuentra informacion para la hoja con la fecha seleccionada"));
                }
            } catch (Exception ex) {
                Logger.getLogger(BuscaHojaSupervTocoYReporteMensualJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void validaFechaFinal(){
        if(oReporteMensual.getFechaFin().getTime()<=oReporteMensual.getFechaInicio().getTime()){
            bRendering=false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","la fecha final debe ser menor a la fecha inicial"));
        }
    }
    public void buscaReporteMensualToco(){
        if(this.getReporteMensual().getFechaInicio()!=null
                && this.getReporteMensual().getFechaFin()!=null){
            if(oReporteMensual.getFechaFin().getTime()<=oReporteMensual.getFechaInicio().getTime()){
                bRendering=false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","la fecha final debe ser menor a la fecha inicial"));
            }else{
                try {                      
                    arrMensualToco= oReporteMensual.buscaReporteMensualTococirugia();                
                    if(arrMensualToco.length>0){
                        bRendering=true;
                        sumaRow(); 
                    }
                } catch (Exception ex) {
                    Logger.getLogger(BuscaHojaSupervTocoYReporteMensualJB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }    
    
    public void sumaRow(){       
        nLegrados=0; nMadresMenos20Legrado=0;nPartoEutocico=0;
        nPartoDisctocico=0;nCesarea=0;nMadreMenor20Nac=0;nMenos2500gr35=0;
        nMayor2500gr35=0;nMenor2500gr36=0;nMayor2500gr36=0;n20a26Semanas=0;
        n26MasSemanas=0;nLui=0;nDFMaterna=0;nDFRn=0;nDIUParto=0;nDIUCesara=0;
        nDIUAborto=0;nOTSParto=0;nOTSCesarea=0;nTSAborto=0;nSolHor=0;nAcp20=0;nOTB=0;
        for(ReporteMensualTocoCirugia oR:arrMensualToco){
            nLegrados += Integer.parseInt((oR.getTotalAbortos()==null || oR.getTotalAbortos().equals("")?"0":oR.getTotalAbortos()));
            nMadresMenos20Legrado += Integer.parseInt((oR.getMadresMenor20A()==null || oR.getMadresMenor20A().equals("")?"0":oR.getMadresMenor20A()));
            nPartoEutocico += Integer.parseInt(oR.getPartoEutocico()==null || oR.getPartoEutocico().equals("")?"0":oR.getPartoEutocico());
            nPartoDisctocico += Integer.parseInt(oR.getPartoDistocico()==null || oR.getPartoDistocico().equals("")?"0":oR.getPartoDistocico());
            nCesarea += Integer.parseInt(oR.getCesare()==null || oR.getCesare().equals("")?"0":oR.getCesare());
            nMadreMenor20Nac += Integer.parseInt(oR.getMadresMenor20N()==null || oR.getMadresMenor20N().equals("")?"0":oR.getMadresMenor20N());
            nMenos2500gr35 += Integer.parseInt(oR.getS2500grMenorV35()==null || oR.getS2500grMenorV35().equals("")?"0":oR.getS2500grMenorV35());
            nMayor2500gr35 += Integer.parseInt(oR.getS2500grMayorV35()==null || oR.getS2500grMayorV35().equals("")?"0":oR.getS2500grMayorV35());
            nMenor2500gr36 += Integer.parseInt(oR.getS2500grMenorV37()==null || oR.getS2500grMenorV37().equals("")?"0":oR.getS2500grMenorV37());
            nMayor2500gr36 += Integer.parseInt(oR.getS2500grMayorV37()==null || oR.getS2500grMayorV37().equals("")?"0":oR.getS2500grMayorV37());
            n20a26Semanas += Integer.parseInt(oR.getDF20a26()==null || oR.getDF20a26().equals("")?"0":oR.getDF20a26());
            n26MasSemanas += Integer.parseInt(oR.getDF27Mas()==null || oR.getDF27Mas().equals("")?"0":oR.getDF27Mas());
            nLui += Integer.parseInt(oR.getLUI()==null || oR.getLUI().equals("")?"0":oR.getLUI());
            nDFMaterna += Integer.parseInt(oR.getDFMaterna()==null || oR.getDFMaterna().equals("")?"0":oR.getDFMaterna());
            nDFRn += Integer.parseInt(oR.getDFRN()==null || oR.getDFRN().equals("")?"0":oR.getDFRN());
            nDIUParto += Integer.parseInt(oR.getDUIPostParto()==null || oR.getDUIPostParto().equals("")?"0":oR.getDUIPostParto());
            nDIUCesara +=Integer.parseInt(oR.getDIUCesarea()==null || oR.getDIUCesarea().equals("")?"0":oR.getDIUCesarea()) ;
            nDIUAborto += Integer.parseInt(oR.getDIUPostAborto()==null || oR.getDIUPostAborto().equals("")?"0":oR.getDIUPostAborto());
            nOTSParto +=Integer.parseInt(oR.getOTSPostParto()==null || oR.getOTSPostParto().equals("")?"0":oR.getOTSPostParto());
            nOTSCesarea += Integer.parseInt(oR.getOTSCesarea()==null || oR.getOTSCesarea().equals("")?"0":oR.getOTSCesarea());
            nTSAborto += Integer.parseInt(oR.getOTSPostAborto()==null || oR.getOTSPostAborto().equals("")?"0":oR.getOTSPostAborto());
            nSolHor += Integer.parseInt(oR.getSolucionesHormonales()==null || oR.getSolucionesHormonales().equals("")?"0":oR.getSolucionesHormonales());
            nAcp20 += Integer.parseInt(oR.getAcp20Anhos()==null || oR.getAcp20Anhos().equals("")?"0":oR.getAcp20Anhos());
            nOTB +=Integer.parseInt(oR.getOTB()==null || oR.getOTB().equals("")?"0":oR.getOTB());
        }
    }
  
    public HojaSupervisionTococirugia getHojaToco() {
        return oToco;
    }

    public HojaSupervisionTococirugia[] getArrHjToco() {
        return arrHjToco;
    }

    
    public ReporteMensualTocoCirugia getReporteMensual() {
        return oReporteMensual;
    }

    public boolean getRendering() {
        return bRendering;
    }

    public ReporteMensualTocoCirugia[] getArrMensualToco() {
        return arrMensualToco;
    }

    public int getLegrados() {
        return nLegrados;
    }

    public int getMadresMenos20Legrado() {
        return nMadresMenos20Legrado;
    }

    public int getPartoEutocico() {
        return nPartoEutocico;
    }

    public int getPartoDisctocico() {
        return nPartoDisctocico;
    }

    public int getCesarea() {
        return nCesarea;
    }

    public int getMadreMenor20Nac() {
        return nMadreMenor20Nac;
    }

    public int getMenos2500gr35() {
        return nMenos2500gr35;
    }

    public int getMayor2500gr35() {
        return nMayor2500gr35;
    }

    public int getMenor2500gr36() {
        return nMenor2500gr36;
    }

    public int getMayor2500gr36() {
        return nMayor2500gr36;
    }

    public int getN20a26Semanas() {
        return n20a26Semanas;
    }

    public int getN26MasSemanas() {
        return n26MasSemanas;
    }

    public int getLui() {
        return nLui;
    }

    public int getDFMaterna() {
        return nDFMaterna;
    }

    public int getDFRn() {
        return nDFRn;
    }

    public int getDIUParto() {
        return nDIUParto;
    }

    public int getDIUCesara() {
        return nDIUCesara;
    }

    public int getDIUAborto() {
        return nDIUAborto;
    }

    public int getOTSParto() {
        return nOTSParto;
    }

    public int getOTSCesarea() {
        return nOTSCesarea;
    }

    public int getTSAborto() {
        return nTSAborto;
    }

    public int getSolHor() {
        return nSolHor;
    }

    public int getAcp20() {
        return nAcp20;
    }

    public int getOTB() {
        return nOTB;
    }
    
    
}
