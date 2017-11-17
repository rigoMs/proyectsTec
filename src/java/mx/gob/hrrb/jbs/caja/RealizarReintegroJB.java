package mx.gob.hrrb.jbs.caja;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oRealizarRtgr")
@RequestScoped
public class RealizarReintegroJB {
    private CuotaRecuperacion arrRecibos[];
    private CuotaRecuperacion oSelectedRecibo;

    public RealizarReintegroJB() {
    }

    public CuotaRecuperacion[] getRecibos() {
        return arrRecibos;
    }

    public void setRecibos(CuotaRecuperacion[] arrRecibos) {
        this.arrRecibos = arrRecibos;
    }

    public CuotaRecuperacion getSelectedRecibo() {
        return oSelectedRecibo;
    }

    public void setSelectedRecibo(CuotaRecuperacion oSelectedRecibo) {
        this.oSelectedRecibo = oSelectedRecibo;
    }
    
}
