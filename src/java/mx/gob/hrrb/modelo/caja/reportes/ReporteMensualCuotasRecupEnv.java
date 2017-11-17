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
public class ReporteMensualCuotasRecupEnv implements Serializable{
    private AccesoDatos oAD;
    private BigDecimal nTotal;
    private Date dFechaI;
    private Date dFechaF;

    public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		if( this.getFechaI()==null||this.getFechaF()==null){
			throw new Exception("ReporteMensualCuotasRecupEnv.buscar: error, faltan datos");
		}else{ 
                     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			sQuery = "SELECT * FROM buscaReporteMensualCuotasRecupEnviadas('"+format.format(this.getFechaI())+"'::date,'"+format.format(this.getFechaF())+"'::date);"; 
			System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setTotal((BigDecimal.valueOf((Double)vRowTemp.get(0))));
				bRet = true;
			}
		} 
		return bRet; 
	} 
    
    public BigDecimal getTotal() {
        return nTotal;
    }

    public void setTotal(BigDecimal nTotal) {
        this.nTotal = nTotal;
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
    
    public BigDecimal getEntero10() {
        if(nTotal==null){
            return null;
        }
        else{
            return nTotal.multiply((BigDecimal.valueOf(0.10)));
        }
    }
    public BigDecimal getEntero5() {
        if(nTotal==null){
            return null;
        }
        else{
            return nTotal.multiply((BigDecimal.valueOf(0.05)));
        }
    }
    
    public BigDecimal getPor85() {
        if(nTotal==null){
            return null;
        }
        else{
            return nTotal.multiply((BigDecimal.valueOf(0.85)));
        }
    }
    
    public BigDecimal getPor15() {
        if(nTotal==null){
            return null;
        }
        else{
            return nTotal.multiply((BigDecimal.valueOf(0.15)));
        }
    }
    
    public BigDecimal getDebe() {
        if(nTotal==null){
            return null;
        }
        else{
            return nTotal.add(nTotal);
        }
    }
    
    public BigDecimal getHaber() {
        if(nTotal==null){
            return null;
        }
        else{
            return nTotal.add(nTotal);
        }
    }
}
