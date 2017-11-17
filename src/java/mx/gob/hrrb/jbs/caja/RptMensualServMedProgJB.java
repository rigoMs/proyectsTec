package mx.gob.hrrb.jbs.caja;

import java.math.BigDecimal;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.reportes.ReporteMensualServMedProg;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="rptExentosProg")
@RequestScoped
public class RptMensualServMedProgJB {
    private ReporteMensualServMedProg oRpt;
    private ReporteMensualServMedProg arrRpt[];
    private Date dFechaI;
    private Date dFechaF;
    private String sActivaTabla;
    private BigDecimal nTotal;
    private int nTotalPacientes;

    public RptMensualServMedProgJB() {
        oRpt=new ReporteMensualServMedProg();
        sActivaTabla="display: none;";
        nTotal=new BigDecimal(0);
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscaReporte();
        sActivaTabla="";
    }
    
    public void buscaReporte() throws Exception{
        arrRpt=oRpt.buscarReporte(dFechaI, dFechaF);
        if(arrRpt.length>0){
            for(ReporteMensualServMedProg rpt: arrRpt){
                nTotalPacientes+=rpt.getPacientes();
                nTotal=nTotal.add(rpt.getTotal());
            }
        }
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

    public ReporteMensualServMedProg[] getRpt() {
        return arrRpt;
    }

    public BigDecimal getTotal() {
        return nTotal;
    }

    public int getTotalPacientes() {
        return nTotalPacientes;
    }
    
}