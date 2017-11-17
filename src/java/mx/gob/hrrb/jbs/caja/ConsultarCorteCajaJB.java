package mx.gob.hrrb.jbs.caja;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.caja.CorteCaja;
import mx.gob.hrrb.modelo.caja.FoliosCaja;
import mx.gob.hrrb.modelo.caja.reportes.InformeIngresosCuotasRecup;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConsultarCorte")
@ViewScoped
public class ConsultarCorteCajaJB {
    private Parametrizacion oParam;
    private Parametrizacion arrtCaja[];
    private InformeIngresosCuotasRecup oInformeCuotas;
    
    public ConsultarCorteCajaJB() throws Exception {
        oParam=new Parametrizacion();
        arrtCaja=oParam.buscarCajasAuxiliares();
        oInformeCuotas=new InformeIngresosCuotasRecup();
        oInformeCuotas.setCorteCaja(new CorteCaja());
    }
    
    public void buscaInformeCuotas() throws Exception{
        oInformeCuotas.buscar();
    }

    public Parametrizacion[] getTiposCaja() {
        return arrtCaja;
    }

    public InformeIngresosCuotasRecup getInformeCuotas() {
        return oInformeCuotas;
    }

    public void setInformeCuotas(InformeIngresosCuotasRecup oInformeCuotas) {
        this.oInformeCuotas = oInformeCuotas;
    }

    
}
