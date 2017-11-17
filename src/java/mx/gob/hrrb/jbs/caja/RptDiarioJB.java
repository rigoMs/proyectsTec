package mx.gob.hrrb.jbs.caja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.ComprobanteExencion;
import mx.gob.hrrb.modelo.caja.CorteCaja;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.caja.Recibo;
import mx.gob.hrrb.modelo.caja.ReciboSeguroPopular;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Turno;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oRptDiario")
@RequestScoped
public class RptDiarioJB {
    private Date dFecha;
    private CorteCaja oCorteCaja;
    private ArrayList<Recibo> arrRecibosUrg;
    private ArrayList<Recibo> arrRecibosCE;
    private ArrayList<Recibo> arrRecibos;
    private CorteCaja arrCorte[];
    private String sActivaTabView;
    private ComprobanteExencion oRecExento;
    private CuotaRecuperacion oRecCuota;
    private ReciboSeguroPopular oRecSegPop;
    private CorteCaja oCorteMatUrg;
    private CorteCaja oCorteVesUrg;
    private CorteCaja oCorteMatCE;
    private CorteCaja oCorteVesCE;
    private CorteCaja oCorteNoc;
    private CorteCaja oCorteJac;
    
    public RptDiarioJB() {
        sActivaTabView="display: none;";
        arrRecibosUrg=new ArrayList();
        arrRecibosCE=new ArrayList();
        arrRecibos=new ArrayList();
        oCorteCaja=new CorteCaja();
        oRecExento=new ComprobanteExencion();
        oRecCuota=new CuotaRecuperacion();
        oRecSegPop=new ReciboSeguroPopular();
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscarReporte();
        sActivaTabView="";
    }
    
    public void buscarReporte() throws Exception{
        arrRecibos.addAll(Arrays.asList(oRecExento.buscarRelacionCuentasVales(dFecha)));
        arrRecibos.addAll(Arrays.asList(oRecCuota.buscarRelacionCuentasVales(dFecha)));
        arrRecibos.addAll(Arrays.asList(oRecSegPop.buscarRelacionCuentasVales(dFecha)));
        
        arrCorte=oCorteCaja.buscarInformeDiario(dFecha);
        
        for(Recibo oRec:arrRecibos){
            if(oRec.getCajaEmite().getTipoCaja().getTipoParametro().equals(Parametrizacion.CAJA_AUX_CE)){
                arrRecibosCE.add(oRec);
            }
            else{
                arrRecibosUrg.add(oRec);
            }
        }
        for(CorteCaja cc: arrCorte){
            if(cc.getCaja().getTipoCaja().getClaveParametro().equals(Parametrizacion.CAJA_AUX_URG)){
                if(cc.getTurno().getClave().equals(Turno.CVE_MATUTINO)){
                    oCorteMatUrg=cc;
                }
                else{
                    if(cc.getTurno().getClave().equals(Turno.CVE_VESPERTINO)){
                        oCorteVesUrg=cc;
                    }
                    else{
                        if(cc.getTurno().getClave().equals(Turno.CVE_NOCTURNO)){
                            oCorteNoc=cc;
                        }
                        else{
                            oCorteJac=cc;
                        }
                    }
                }
            }
            else{
                if(cc.getCaja().getTipoCaja().getClaveParametro().equals(Parametrizacion.CAJA_AUX_CE)){
                    if(cc.getTurno().getClave().equals(Turno.CVE_MATUTINO)){
                        oCorteMatCE=cc;
                    }
                    else{
                        oCorteVesCE=cc;
                    }
                }
            }
        }
        
    }
    
    public String getActivaTabView() {
        return sActivaTabView;
    }

    public ArrayList<Recibo> getRecibosUrg() {
        return arrRecibosUrg;
    }

    public ArrayList<Recibo> getRecibosCE() {
        return arrRecibosCE;
    }

    public CorteCaja getCorteMatUrg() {
        return oCorteMatUrg;
    }

    public CorteCaja getCorteVesUrg() {
        return oCorteVesUrg;
    }

    public CorteCaja getCorteMatCE() {
        return oCorteMatCE;
    }

    public CorteCaja getCorteVesCE() {
        return oCorteVesCE;
    }

    public CorteCaja getCorteNoc() {
        return oCorteNoc;
    }

    public CorteCaja getCorteJac() {
        return oCorteJac;
    }

    public Date getFecha() {
        return dFecha;
    }

    public void setFecha(Date dFecha) {
        this.dFecha = dFecha;
    }
    
}
