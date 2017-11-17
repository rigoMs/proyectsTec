package mx.gob.hrrb.jbs.caja;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.ComprobanteExencion;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.caja.Recibo;
import mx.gob.hrrb.modelo.caja.ReciboSeguroPopular;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConsRecCan")
@RequestScoped
public class ConsultarRecCanceladosJB {
    private String sActivaTabla;
    private Date dFechaI;
    private Date dFechaF;
    private Parametrizacion oRec;
    private Parametrizacion arrRecibo[];
    private Recibo arrRecCancelados[];
    private Recibo oRecibo;

    
    public ConsultarRecCanceladosJB() throws Exception {
        oRec=new Parametrizacion();
        sActivaTabla="display: none;";
        arrRecibo=oRec.buscarTabla(Parametrizacion.TABLA_RECIBO);
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscarRecibos();
        oRec.equalsParam(arrRecibo);
        sActivaTabla="";
    }
    
    public void buscarRecibos() throws Exception{
        if(oRec.getTipoParametro().equals(Parametrizacion.RECIBO_CUOTAS)){
            oRecibo=new CuotaRecuperacion();
            arrRecCancelados=oRecibo.buscarCancelados(dFechaI, dFechaF);
        }
        else{
            if(oRec.getTipoParametro().equals(Parametrizacion.RECIBO_SEGPOP)){
                oRecibo=new ReciboSeguroPopular();
                arrRecCancelados=oRecibo.buscarCancelados(dFechaI, dFechaF);
            }
            else{
                oRecibo=new ComprobanteExencion();
                arrRecCancelados=oRecibo.buscarCancelados(dFechaI, dFechaF);
            }
        }
    }

    public Recibo[] getRecCancelados() {
        return arrRecCancelados;
    }

    public void setRecCancelados(Recibo[] arrRecCancelados) {
        this.arrRecCancelados = arrRecCancelados;
    }
    
    public Parametrizacion[] getTiposRecibo() {
        return arrRecibo;
    }

    public Parametrizacion getRec() {
        return oRec;
    }

    public void setRec(Parametrizacion oRec) {
        this.oRec = oRec;
    }

    public Date getFechaI() {
        return dFechaI;
    }

    public void setFechaI(Date dFechaI) {
        this.dFechaI = dFechaI;
    }

    public Date getFechaF() {
        return dFechaF;
    }

    public void setFechaF(Date dFechaF) {
        this.dFechaF = dFechaF;
    }

    public String getActivaTabla() {
        return sActivaTabla;
    }

    public void setActivaTabla(String sActivaTabla) {
        this.sActivaTabla = sActivaTabla;
    }

}
