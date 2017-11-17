package mx.gob.hrrb.modelo.caja.reportes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Daniel
 */
public class ReporteFacturas {
    private AccesoDatos oAD;
    private Paciente oPaciente;
    private Date dFechaFacturacion;
    private ArrayList<CuotaRecuperacion> arrRecibos;
    private String sRutaArchFactXML;
    private String sRutaArchFactPDF;
    
    public ArrayList<ReporteFacturas> buscaFacturas(Date dFechaI,Date dFechaF) throws Exception{
        ArrayList<ReporteFacturas> arrRet=null; 
        ReporteFacturas oRpt=null;
        CuotaRecuperacion oCR=null;
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat format;
        int i=0;
        if(dFechaI==null||dFechaF==null){
            throw new Exception("ReporteFacturas.buscar: error, faltan datos");
	}
        else{
            oAD=new AccesoDatos(); 
            format = new SimpleDateFormat("yyyy-MM-dd");
            sQuery="SELECT * FROM buscaFacturas('"+format.format(dFechaI)+"'::date,'"+format.format(dFechaF)+"'::date);";
            System.out.println(sQuery);
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new ArrayList();
                for (i = 0; i < rst.size(); i++) {
                    oRpt=new ReporteFacturas();
                    arrRecibos=new ArrayList();
                    oCR=new CuotaRecuperacion();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oRpt.setPaciente(new Paciente());
                    oRpt.setFechaFacturacion((Date)vRowTemp.get(0));
                    oRpt.getPaciente().setNombres((String)vRowTemp.get(1));
                    oRpt.getPaciente().setApPaterno((String)vRowTemp.get(2));
                    oRpt.getPaciente().setApMaterno((String)vRowTemp.get(3));
                    oRpt.setRutaArchFactXML((String)vRowTemp.get(6));
                    oRpt.setRutaArchFactPDF((String)vRowTemp.get(7));
                    for(i = 0; i < rst.size(); i++){
                        ArrayList vRowTemp1 = (ArrayList)rst.get(i);
                        oCR.setFolio(((Double)vRowTemp1.get(4)).intValue());
                        oCR.setMonto((BigDecimal.valueOf((Double)vRowTemp1.get(5))));
                        oCR.setRutaArchFactXML((String)vRowTemp.get(6));
                        if(oRpt.getRutaArchFactXML().equals(oCR.getRutaArchFactXML())){
                            arrRecibos.add(oCR);
                        }
                    }
                    oRpt.setRecibos(arrRecibos);
                    arrRet.add(oRpt);
                } 
            }
        }
        return arrRet;
    }

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public Date getFechaFacturacion() {
        return dFechaFacturacion;
    }

    public void setFechaFacturacion(Date dFechaFacturacion) {
        this.dFechaFacturacion = dFechaFacturacion;
    }

    public String getRutaArchFactXML() {
        return sRutaArchFactXML;
    }

    public void setRutaArchFactXML(String sRutaArchFactXML) {
        this.sRutaArchFactXML = sRutaArchFactXML;
    }

    public String getRutaArchFactPDF() {
        return sRutaArchFactPDF;
    }

    public void setRutaArchFactPDF(String sRutaArchFactPDF) {
        this.sRutaArchFactPDF = sRutaArchFactPDF;
    }

    public ArrayList<CuotaRecuperacion> getRecibos() {
        return arrRecibos;
    }

    public void setRecibos(ArrayList<CuotaRecuperacion> arrRecibos) {
        this.arrRecibos = arrRecibos;
    }
    
}
