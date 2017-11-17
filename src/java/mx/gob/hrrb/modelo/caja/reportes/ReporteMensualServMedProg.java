package mx.gob.hrrb.modelo.caja.reportes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Daniel
 */
public class ReporteMensualServMedProg implements Serializable{
    private AccesoDatos oAD;
    private String sClave;
    private String sConcepto;
    private int nPacientes;
    private BigDecimal nTotal;
    
    public ReporteMensualServMedProg[] buscarReporte(Date dFechaI,Date dFechaF) throws Exception{
        ReporteMensualServMedProg arrRet[]=null, oRpt=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        if(dFechaI==null||dFechaF==null){
            throw new Exception("ReporteMensualServMedExentos.buscar: error, faltan datos");
	}
        else{ 
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            sQuery = "SELECT * FROM buscaReporteMensualServMedicosExentosPrograma('"+format.format(dFechaI)+"'::date,'"+format.format(dFechaF)+"'::date);"; 
            oAD=new AccesoDatos(); 
            System.out.println(sQuery);
            if (oAD.conectar()){ 
		rst = oAD.ejecutarConsulta(sQuery); 
		oAD.desconectar(); 
            }
            if (rst != null) {
		arrRet = new ReporteMensualServMedProg[rst.size()];
		for (i = 0; i < rst.size(); i++) {
                    oRpt=new ReporteMensualServMedProg();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oRpt.setClave((String)vRowTemp.get(0));
                    oRpt.setConcepto((String)vRowTemp.get(1));
                    oRpt.setPacientes((int)((Double)vRowTemp.get(2)).intValue());
                    oRpt.setTotal((BigDecimal.valueOf((Double)vRowTemp.get(3))));
                    arrRet[i]=oRpt;
                } 
            } 
        }
        return arrRet; 
    }

    public String getClave() {
        return sClave;
    }

    public void setClave(String sClave) {
        this.sClave = sClave;
    }

    public String getConcepto() {
        return sConcepto;
    }

    public void setConcepto(String sConcepto) {
        this.sConcepto = sConcepto;
    }

    public long getPacientes() {
        return nPacientes;
    }

    public void setPacientes(int nPacientes) {
        this.nPacientes = nPacientes;
    }

    public BigDecimal getTotal() {
        return nTotal;
    }

    public void setTotal(BigDecimal nTotal) {
        this.nTotal = nTotal;
    }
    
}
