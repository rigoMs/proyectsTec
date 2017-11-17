package mx.gob.hrrb.jbs.caja;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.caja.AutorizacionPago;
import mx.gob.hrrb.modelo.caja.Caja;
import mx.gob.hrrb.modelo.caja.ComprobanteExencion;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.caja.FoliosCaja;
import mx.gob.hrrb.modelo.caja.FoliosDisponibles;
import mx.gob.hrrb.modelo.caja.Recibo;
import mx.gob.hrrb.modelo.caja.ReciboSeguroPopular;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Perfil;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oCancelar")
@ViewScoped
public class CancelarReciboJB {
    private Parametrizacion oRec;
    private Parametrizacion arrRecibo[];
    private Recibo arrRecibosTurno[];
    private FoliosCaja arrFolios[];
    private FoliosDisponibles arrFoliosDisp[];
    private FoliosDisponibles oFoliosDisp;
    private FoliosCaja oFoliosCaja;
    private Firmado oFirm;
    private HttpServletRequest req;
    private Recibo oSelectedRecibo;
    private int nFolio;
    private boolean bCajero;
    private boolean bCancelar;
    private boolean bCancelado;
    private boolean bEmitido;
    private boolean bReintegrado;
    private Caja oCaja;
    private String sActRecNoEmitido;
    private String sActRecCancelado;
    private String sActRecReintegro;
    private String sActSub;
    private String sActRecibo;
    private ArrayList<String> vTransac;
    private ArrayList<ServicioRealizado> arrSR;
    private ArrayList<ServicioRealizado> arrSRR;

    public CancelarReciboJB() throws Exception {
        sActRecNoEmitido="display: none;";
        sActRecCancelado="display: none;";
        sActRecReintegro="display: none;";
        sActRecibo="display: none;";
        sActSub="display: none;";
        bCajero=false;
        bCancelar=true;
        bCancelado=false;
        bEmitido=false;
        bReintegrado=false;
        oRec=new Parametrizacion();
        oCaja=new Caja();
        arrRecibo=oRec.buscarTabla(Parametrizacion.TABLA_RECIBO);
        
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                oFirm=(Firmado) req.getSession().getAttribute("oFirm");
            }    
        bCajero=oFirm.getUsu().getPerfil().get(0).getClave().equals(Perfil.CLAVE_CAJERO);
        if(bCajero){
            oCaja=(Caja) req.getSession().getAttribute("oCaja");
        }
        else{
            oCaja.buscarCajaGeneral();
        }
    }
    
    public void preCancelacion(ActionEvent ae)throws Exception{
        cancelacion();
    }
    
    public void cancelacion() throws Exception{
        oSelectedRecibo.setCajaCancela(oCaja);
        if(!bCajero){
            vTransac=new ArrayList<>();
            if(nFolio>oFoliosDisp.getFolioInicial()&&nFolio<oFoliosDisp.getFolioFinal()){
                int nFolioTmp=0;
                nFolioTmp=oFoliosDisp.getFolioFinal();
                vTransac.add(oFoliosDisp.getElimina());
                oFoliosDisp.setFolioFinal(nFolio-1);
                vTransac.add(oFoliosDisp.getInserta());
                oFoliosDisp.setFolioInicial(nFolio+1);
                oFoliosDisp.setFolioFinal(nFolioTmp);
                vTransac.add(oFoliosDisp.getInserta());
            }
            else{
                if(oFoliosDisp.getFolioInicial()==nFolio&&oFoliosDisp.getFolioFinal()==nFolio){
                    vTransac.add(oFoliosDisp.getElimina());
                }
                else{
                    if(oFoliosDisp.getFolioInicial()==nFolio){
                        oFoliosDisp.setFolioInicial(nFolio+1);
                        vTransac.add(oFoliosDisp.getModificaFolioInicial());
                    }
                    else{
                        oFoliosDisp.setFolioFinal(nFolio-1);
                        vTransac.add(oFoliosDisp.getModificaFolioFinal());
                    }                
                }
            }
            oSelectedRecibo.cancelarFolioDisponible(vTransac);
        }
        else{
            oSelectedRecibo.cancelar();
        }
        buscarRecibo();
        RequestContext.getCurrentInstance().execute("PF('dlgIncidencia').hide()");
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscarRecibo();
    }
    
    public void buscarRecibo() throws Exception{
        bCancelar=true;
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
        if(bCajero){
            arrRecibosTurno=oSelectedRecibo.buscarFoliosEmitidosTurnoActual(oCaja.getIdCaja());
            oFoliosCaja=new FoliosCaja();
            oFoliosCaja.setCaja(oCaja);
            oFoliosCaja.setTipoRecibo(new Parametrizacion());
            oFoliosCaja.getTipoRecibo().setTipoParametro(oRec.getTipoParametro());
            arrFolios=oFoliosCaja.buscarExistenciaRecibos();
            for(Recibo recibo:arrRecibosTurno){
                if(recibo.getFolio()==nFolio)
                    bCancelar=false;
            }
            for(FoliosCaja folio:arrFolios){
                if(folio.getFolioInicial()==nFolio||
                   folio.getFolioFinal()==nFolio||
                   (nFolio>folio.getFolioInicial()&&nFolio<folio.getFolioFinal()))
                    bCancelar=false;
            }
        }
        else{
            oFoliosDisp=new FoliosDisponibles();
            oFoliosDisp.setTipoRecibo(new Parametrizacion());
            oFoliosDisp.setTipoCaja(new Parametrizacion());
            oFoliosDisp.getTipoRecibo().setTipoParametro(oRec.getTipoParametro());
            arrFoliosDisp=oFoliosDisp.buscarFoliosDisponiblesCajaRecibo();
            for(FoliosDisponibles ofd:arrFoliosDisp){
                if(nFolio==ofd.getFolioInicial()||
                   nFolio==ofd.getFolioFinal()||
                   (nFolio>ofd.getFolioInicial()&&nFolio<ofd.getFolioFinal())){
                    bCancelar=false;
                    oFoliosDisp=ofd;
                }
            }            
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

    public boolean isCancelar() {
        return bCancelar;
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