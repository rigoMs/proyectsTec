package mx.gob.hrrb.jbs.caja;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.AutorizacionPago;
import mx.gob.hrrb.modelo.caja.ComprobanteExencion;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.caja.Recibo;
import mx.gob.hrrb.modelo.caja.ReciboSeguroPopular;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConsultarRec")
@ViewScoped
public class ConsultarReciboJB {
    private Parametrizacion oRec;
    private Parametrizacion arrRecibo[];
    private Recibo oSelectedRecibo;
    private int nFolio;
    private boolean bCancelado;
    private boolean bEmitido;
    private boolean bReintegrado;
    private String sActRecNoEmitido;
    private String sActRecCancelado;
    private String sActRecReintegro;
    private String sActSub;
    private String sActRecibo;
    private ArrayList<ServicioRealizado> arrSR;
    private ArrayList<ServicioRealizado> arrSRR;

    public ConsultarReciboJB() throws Exception {
        sActRecNoEmitido="display: none;";
        sActRecCancelado="display: none;";
        sActRecReintegro="display: none;";
        sActRecibo="display: none;";
        sActSub="display: none;";
        bCancelado=false;
        bEmitido=false;
        bReintegrado=false;
        oRec=new Parametrizacion();
        arrRecibo=oRec.buscarTabla(Parametrizacion.TABLA_RECIBO);
        
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscarRecibo();
    }
    
    public void buscarRecibo() throws Exception{
        bCancelado=false;
        bEmitido=false;
        bReintegrado=false;
        sActSub="display: none;";
        if(oRec.getTipoParametro().equals(Parametrizacion.RECIBO_CUOTAS)){
            oSelectedRecibo=new CuotaRecuperacion();
            sActSub="";
        }
        if(oRec.getTipoParametro().equals(Parametrizacion.RECIBO_SEGPOP)){
            oSelectedRecibo=new ReciboSeguroPopular();
        }
        if(oRec.getTipoParametro().equals(Parametrizacion.RECIBO_EXENTO)){
            oSelectedRecibo=new ComprobanteExencion();
        }

        oSelectedRecibo.setFolio(nFolio);
        bCancelado=oSelectedRecibo.buscarReciboCancelado();
            if(bCancelado&&oRec.getTipoParametro().equals(Parametrizacion.RECIBO_CUOTAS)){
                if(((CuotaRecuperacion)oSelectedRecibo).getFechaAutReintegro()!=null){    
                    bReintegrado=((CuotaRecuperacion)oSelectedRecibo).buscarReintegro();
                }
        }
        if(bReintegrado){
            reciboReintegrado();
        }    
            
        if(!bCancelado){
            bEmitido=oSelectedRecibo.buscar();
        }
        if(bEmitido){
            sActRecNoEmitido="display: none;";
            sActRecCancelado="display: none;";
            sActRecReintegro="display: none;";
            sActRecibo="";
        }
        else{
            if(bCancelado&&!bReintegrado){
                sActRecNoEmitido="display: none;";
                sActRecCancelado="";
                sActRecReintegro="display: none;";
                sActRecibo="display: none;";
            }
            else{
                if(bReintegrado){
                    sActRecNoEmitido="display: none;";
                    sActRecCancelado="display: none;";
                    sActRecReintegro="";
                    sActRecibo="display: none;";
                }
                else{
                    sActRecNoEmitido="";
                    sActRecCancelado="display: none;";
                    sActRecReintegro="display: none;";
                    sActRecibo="display: none;";
                }
            }
        }
        oRec.equalsParam(arrRecibo);
    }
      
    public void reciboReintegrado(){
        arrSR=new ArrayList();
        arrSRR=new ArrayList();
        for(ServicioRealizado sr:oSelectedRecibo.getServiciosCubiertos()){
            if(sr.getSitPago().getTipoParametro().equals(Parametrizacion.SIT_PAGO_PAGADO)){
                arrSR.add(sr);
            }
        }
        for(ServicioRealizado sr:oSelectedRecibo.getServiciosCubiertos()){
            if(sr.getSitPago().getTipoParametro().equals(Parametrizacion.SIT_PAGO_REINTEGRADO)){
                arrSRR.add(sr);
            }
        }
    }
    
    public BigDecimal getSubtotal(){
        BigDecimal nTotal = null; 
        nTotal=new BigDecimal(0);
        if(bEmitido||bReintegrado){
            for(ServicioRealizado sr: oSelectedRecibo.getServiciosCubiertos()){
                nTotal=nTotal.add(sr.getCostoUnitACobrar());
            }
        }
        return nTotal;
    }
    
    public BigDecimal getTotalRecReintegro(){
        BigDecimal nTotal = null; 
        nTotal=new BigDecimal(0);
        if(bReintegrado){
            for(ServicioRealizado sr:arrSRR){
                nTotal=nTotal.add(sr.getCostoUnitACobrar());
            }
        }
        return nTotal;
    }    
    
    public BigDecimal getSubsidio(AutorizacionPago arrAP[]){
        if(arrAP!=null){
            return arrAP[0].getMontoSubsidio();
        }
        return null;
    }

    public Parametrizacion[] getTiposRecibo() {
        return arrRecibo;
    }

    public Parametrizacion getRec() {
        return oRec;
    }
    
    public Recibo getSelectedRecibo() {
        return oSelectedRecibo;
    }

    public int getFolio() {
        return nFolio;
    }

    public void setFolio(int nFolio) {
        this.nFolio = nFolio;
    }

    public String getActRecNoEmitido() {
        return sActRecNoEmitido;
    }

    public String getActRecCancelado() {
        return sActRecCancelado;
    }

    public String getActRecReintegro() {
        return sActRecReintegro;
    }

    public String getActRecibo() {
        return sActRecibo;
    }

    public ArrayList<ServicioRealizado> getSR() {
        return arrSR;
    }

    public ArrayList<ServicioRealizado> getSRR() {
        return arrSRR;
    }

    public String getActSub() {
        return sActSub;
    }
}