
package mx.gob.hrrb.jbs.enfermeria;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import mx.gob.hrrb.modelo.enfermeria.reporte.HojaSuperYActividadesQx;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
/**
 *
 * @author Javier
 */
@ManagedBean(name="oHojaQx")
@ViewScoped
public class HojaSuperYActividadesQuirofano implements Serializable{
    
    private boolean isRender;
    private HojaSuperYActividadesQx oReporte;
    private HojaSuperYActividadesQx[] arrHojaSupQx=null;
    private HojaSuperYActividadesQx[] arrActividesQx=null;
    
    private int nSumCirugiaGeneral;
    private int nSumGinecoObstetricia;
    private int nSumMedicinaInterna;
    private int nSumPediatria;
    private int nSumOtros;
    private int nSumServiciosUrg;
    private int nSumCirugiaProgramada;
    private int nSumCirugiaUrgente;
    private int nSumTiempoQx;
    private int nSumCirugiaSuspendida;    
    private int nSumUCI;
    private int nSumUCIP;
    private int nSumUCIN;
    private int nSumOncoPed;
    
    private boolean sRender;
    
    public HojaSuperYActividadesQuirofano() {
        oReporte = new HojaSuperYActividadesQx();   
        sRender=false;
        //buscaActividadesDiariasQuirofano();
    }
    
    public void buscaDatosHojaSupervisionQuirofano(){
        if(oReporte.getFecha()!=null){
            try {
                arrHojaSupQx= oReporte.buscarHojaSupervisionQuirofano();
                if(arrHojaSupQx!=null){
                   isRender=true;
                }else{
                    isRender=false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No hay informacion para la hoja"));
                }
                
            } catch (Exception ex) {
                Logger.getLogger(HojaSuperYActividadesQuirofano.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void buscaActividadesDiariasQuirofano(){
        try {
            arrActividesQx= oReporte.buscaActividadesDiariasQuirofano();
            if(arrActividesQx!=null && arrActividesQx.length>0){
                sRender=true;
               sumaRow(); 
            } else{
                sRender=false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se encuentra infomacion"));
            }          
        } catch (Exception ex) {
            Logger.getLogger(HojaSuperYActividadesQuirofano.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sumaRow(){
        for( HojaSuperYActividadesQx oSuma:arrActividesQx){
            nSumCirugiaGeneral +=Integer.parseInt(oSuma.getCirugiaGeneral()==null || oSuma.getCirugiaGeneral().equals("")?"0":oSuma.getCirugiaGeneral());
            nSumGinecoObstetricia += Integer.parseInt(oSuma.getGinecoObstetricia()==null || oSuma.getGinecoObstetricia().equals("")?"0":oSuma.getGinecoObstetricia());
            nSumMedicinaInterna += Integer.parseInt(oSuma.getMedicinaInterma()==null || oSuma.getMedicinaInterma().equals("")?"0":oSuma.getMedicinaInterma());
            nSumPediatria += Integer.parseInt(oSuma.getPediatria()==null || oSuma.getPediatria().equals("")?"0":oSuma.getPediatria());            
            nSumOtros +=Integer.parseInt(oSuma.getOtros()==null||(oSuma.getOtros()).equals("")?"0":oSuma.getOtros());
            nSumServiciosUrg +=Integer.parseInt(oSuma.getServiciosUrg()==null|| oSuma.getServiciosUrg().equals("")?"0":oSuma.getServiciosUrg());
            nSumCirugiaProgramada +=Integer.parseInt(oSuma.getCirugiaProgramada()==null || oSuma.getCirugiaProgramada().equals("")?"0":oSuma.getCirugiaProgramada());
            nSumCirugiaUrgente += Integer.parseInt(oSuma.getCirugiaUrgente()==null || oSuma.getCirugiaUrgente().equals("")?"0":oSuma.getCirugiaUrgente());           
            nSumCirugiaSuspendida +=Integer.parseInt(oSuma.getCirugiaSuspendida()==null || oSuma.getCirugiaSuspendida().equals("")?"0":oSuma.getCirugiaSuspendida());
            nSumTiempoQx +=Integer.parseInt(oSuma.getTiempoQuirurgico()==null || oSuma.getTiempoQuirurgico().equals("")?"0":oSuma.getTiempoQuirurgico());
            nSumUCI +=Integer.parseInt(oSuma.getUCI()==null || oSuma.getUCI().equals("")?"0":oSuma.getUCI());
            nSumUCIP +=Integer.parseInt(oSuma.getUCIP()==null || oSuma.getUCIP().equals("")?"0":oSuma.getUCIP());
            nSumUCIN +=Integer.parseInt(oSuma.getUCIN()==null || oSuma.getUCIN().equals("")?"0":oSuma.getUCIN());
            nSumOncoPed +=Integer.parseInt(oSuma.getOncoPedriatria()==null || oSuma.getOncoPedriatria().equals("")?"0":oSuma.getOncoPedriatria());
        }
    }
    
    public HojaSuperYActividadesQx getReporteHojaQx(){
        return oReporte;
    }
    
    public boolean getRender(){
        return isRender;
    }
    
    public HojaSuperYActividadesQx[] getArrHojaSupQx() {
        return arrHojaSupQx;
    }

     public HojaSuperYActividadesQx[] getArrActividesQx() {
        return arrActividesQx;
    }     
    
    public String getCirugiaGenetal(){
        if(nSumCirugiaGeneral==0){
            return "";
        }else{
            return String.valueOf(nSumCirugiaGeneral);
        }
    }
    
    public String getGinecoObstetricia(){
        if(this.nSumGinecoObstetricia==0){
            return "";
        }else{
            return String.valueOf(nSumGinecoObstetricia);
        }
    }
    
    public String getMedicinaInterna(){
        if(this.nSumMedicinaInterna==0){
            return "";
        }else{
            return String.valueOf(nSumMedicinaInterna);
        }
    }
    
    public String getPediatria(){
        if(this.nSumPediatria==0){
            return "";
        }else{
            return String.valueOf(nSumPediatria);
        }
    }
    
    public String getOtros(){
        if(this.nSumOtros==0){
            return "";
        }else{
            return String.valueOf(nSumOtros);
        }
    }
    
    public String getServicioUrg(){
        if(this.nSumServiciosUrg==0){
            return "";
        }else{
            return String.valueOf(nSumServiciosUrg);
        }
    }
    
    public String getCirugiaProgramada(){
        if(this.nSumCirugiaProgramada==0){
            return "";
        }else{
            return String.valueOf(nSumCirugiaProgramada);
        }
    }
    
    public String getCirugiaUrgente(){
        if(this.nSumCirugiaUrgente==0){
            return "";
        }else{
            return String.valueOf(nSumCirugiaUrgente);
        }
    }
    
    public String getTiempoQx(){
        if(this.nSumTiempoQx==0){
            return "";
        }else{
            return String.valueOf(nSumTiempoQx);
        }
    }
    
    public String getCirugiaSupendida(){
        if(this.nSumCirugiaSuspendida==0){
            return "";
        }else{
            return String.valueOf(nSumCirugiaSuspendida);
        }
    }
    
    public String getFechaConsulta(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String sFechaCons="";
        if(oReporte.getFecha()!=null){
            sFechaCons= format.format(oReporte.getFecha());
        }
        return sFechaCons;
    }

    public String getUCI() {
        if(this.nSumUCI==0){
            return "";
        }else{
         return String.valueOf(nSumUCI);
        }
    }
    
    public String getUCIP() {
        if(this.nSumUCIP==0){
            return "";
        }else{
          return String.valueOf(nSumUCIP);
        }
    }

    public String getUCIN() {
        if(this.nSumUCIN==0){
            return "";
        }else{
            return String.valueOf(nSumUCIN);
        }
    }

    public String getOncoPed() {
        if(this.nSumOncoPed==0){
            return "";
        }else{
            return String.valueOf(nSumOncoPed);
        }
    }
    
    public boolean getRendering(){
        return sRender;
    }
}
