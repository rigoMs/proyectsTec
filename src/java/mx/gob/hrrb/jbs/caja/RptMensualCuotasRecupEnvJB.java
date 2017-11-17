package mx.gob.hrrb.jbs.caja;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.reportes.ReporteMensualCuotasRecupEnv;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="rptCuotasEnv")
@RequestScoped
public class RptMensualCuotasRecupEnvJB {
    private ReporteMensualCuotasRecupEnv oRpt;
    private String sActivaTabla;
    
    public RptMensualCuotasRecupEnvJB() {
        oRpt=new ReporteMensualCuotasRecupEnv();
        sActivaTabla="display: none;";
    }

    public void preBusqueda(ActionEvent ae)throws Exception{
        buscaReporte();
        sActivaTabla="";
    }
    
    public void buscaReporte() throws Exception{
        oRpt.buscar();
    }

    public ReporteMensualCuotasRecupEnv getRpt() {
        return oRpt;
    }

    public void setRpt(ReporteMensualCuotasRecupEnv oRpt) {
        this.oRpt = oRpt;
    }

    public String getActivaTabla() {
        return sActivaTabla;
    }
}
