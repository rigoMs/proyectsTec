
package mx.gob.hrrb.modelo.enfermeria.reporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.enfermeria.DetalleSuperServicios;
import mx.gob.hrrb.modelo.enfermeria.DetalleSupervisionUCI;
/**
 * Objetivo: 
 * @author : Javier
 * @version: 1.0
*/

public class ImprimeHojaSupervisionServciosHosp implements Serializable{
    
    private AccesoDatos oAD;
    private String sVenoclisis;
    private String sCateter;
    private String sTransfusion;
    private String sNpt;
    private String sPvc;
    private String sSondas;
    private String sGlicemias;
    private String sCuraciones;
    private String sMonitoreo;
    private String sVentilador;
    private String sOxigeno;
    private String sFisioPulmonar;
    private String sEjercResp;
    private String sAspiracionSecre;
    private String sLavBronq;
    private String sNebulizacion;
    private String sEjercVesical;
    private String sDialisis;
    private String sAseosOCulares;
    private String sColutorios;
    private String sControlLiquidos;
    private String sDrenajesCerrados;
    private String sAlta;
    
    private DetalleSupervisionUCI oDetalleUCI;
    private DetalleSuperServicios oDetalleSer;
    
    public ImprimeHojaSupervisionServciosHosp(){
        oDetalleUCI= new DetalleSupervisionUCI();
        oDetalleSer= new DetalleSuperServicios();
    }
    
    public ImprimeHojaSupervisionServciosHosp[] buscaProcedimientosAplicadosPorFechaYServicioTerapiaIntensiva() throws Exception{
            ImprimeHojaSupervisionServciosHosp arrRet[]=null, oDeUCI=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
                if(this.getDetalleUCI().getCabeceraSupervisionUCI().getFechaString().equals("")
                        || this.getDetalleUCI().getCabeceraSupervisionUCI().getArea().getClave()==0){
                    throw new Exception("DetalleSupervisionUCI.buscaProcedimientosAplicadosPorFechaYServicio: error, faltan datos");
                }else{
                    sQuery = "SELECT * FROM buscaProcedimientosAplicadosEnfermeriaTerapiaIntenciva('"
                            +this.getDetalleUCI().getCabeceraSupervisionUCI().getFechaString()+"'::date,"
                            +this.getDetalleUCI().getCabeceraSupervisionUCI().getArea().getClave()+"::smallint);"; 
                    System.out.println(sQuery);
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                    }
                    if (rst != null) {
                        arrRet = new ImprimeHojaSupervisionServciosHosp[rst.size()];
                        for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDeUCI= new ImprimeHojaSupervisionServciosHosp();
                            oDeUCI.getDetalleUCI().getCabeceraSupervisionUCI().setFechaSupervision((Date)vRowTemp.get(0));
                            oDeUCI.getDetalleUCI().getCabeceraSupervisionUCI().getArea().setClave(((Double)vRowTemp.get(1)).intValue());
                            oDeUCI.getDetalleUCI().setIdSupervision(((Double)vRowTemp.get(2)).longValue());
                            oDeUCI.getDetalleUCI().getCabeceraSupervisionUCI().setIdCabeceraUCI(((Double)vRowTemp.get(3)).longValue());
                            oDeUCI.getDetalleUCI().getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                            oDeUCI.getDetalleUCI().getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                            oDeUCI.getDetalleUCI().getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                            oDeUCI.getDetalleUCI().setObservacionPaciente((String)vRowTemp.get(7));
                            oDeUCI.getDetalleUCI().setObservacionGeneral((String)vRowTemp.get(8));
                            oDeUCI.getDetalleUCI().getEpisodio().getCama().setNumero((String)vRowTemp.get(9));
                            oDeUCI.getDetalleUCI().setOxigeno(!((String)vRowTemp.get(10)).equals(""));
                            oDeUCI.getDetalleUCI().setCateter(!((String)vRowTemp.get(11)).equals(""));
                            oDeUCI.getDetalleUCI().setSondas(!((String)vRowTemp.get(12)).equals(""));
                            oDeUCI.getDetalleUCI().setVenoclisis(!((String)vRowTemp.get(13)).equals(""));
                            oDeUCI.getDetalleUCI().setTransfusion(!((String)vRowTemp.get(14)).equals(""));
                            oDeUCI.getDetalleUCI().setVentilador(!((String)vRowTemp.get(15)).equals(""));
                            oDeUCI.getDetalleUCI().setNPT(!((String)vRowTemp.get(16)).equals(""));
                            oDeUCI.getDetalleUCI().setGlicemias(!((String)vRowTemp.get(17)).equals(""));
                            oDeUCI.getDetalleUCI().setCuraciones(!((String)vRowTemp.get(18)).equals(""));
                            oDeUCI.getDetalleUCI().setColutorios(!((String)vRowTemp.get(19)).equals(""));
                            oDeUCI.getDetalleUCI().setAspiracionSecre(!((String)vRowTemp.get(20)).equals(""));
                            oDeUCI.getDetalleUCI().setLavadoBronq(!((String)vRowTemp.get(21)).equals(""));
                            oDeUCI.getDetalleUCI().setNebulizaciones(!((String)vRowTemp.get(22)).equals(""));
                            oDeUCI.getDetalleUCI().setFisioPulmonar(!((String)vRowTemp.get(23)).equals(""));
                            oDeUCI.getDetalleUCI().setDialisisPeritonial(!((String)vRowTemp.get(24)).equals(""));
                            oDeUCI.getDetalleUCI().setMonitoreo(!((String)vRowTemp.get(25)).equals(""));
                            oDeUCI.getDetalleUCI().setEjercicioVesical(!((String)vRowTemp.get(26)).equals(""));
                            oDeUCI.getDetalleUCI().setEjercicioResp(!((String)vRowTemp.get(27)).equals(""));
                            oDeUCI.getDetalleUCI().setPVC(!((String)vRowTemp.get(28)).equals(""));
                            oDeUCI.getDetalleUCI().setAseosOculares(!((String)vRowTemp.get(29)).equals(""));
                            oDeUCI.setOxigeno(!((String)vRowTemp.get(10)).equals("")?"●":"");
                            oDeUCI.setCateter(!((String)vRowTemp.get(11)).equals("")?"●":"");
                            oDeUCI.setSondas(!((String)vRowTemp.get(12)).equals("")?"●":"");
                            oDeUCI.setVenoclisis(!((String)vRowTemp.get(13)).equals("")?"●":"");
                            oDeUCI.setTransfusion(!((String)vRowTemp.get(14)).equals("")?"●":"");
                            oDeUCI.setVentilador(!((String)vRowTemp.get(15)).equals("")?"●":"");
                            oDeUCI.setNpt(!((String)vRowTemp.get(16)).equals("")?"●":"");
                            oDeUCI.setGlicemias(!((String)vRowTemp.get(17)).equals("")?"●":"");
                            oDeUCI.setCuraciones(!((String)vRowTemp.get(18)).equals("")?"●":"");
                            oDeUCI.setColutorios(!((String)vRowTemp.get(19)).equals("")?"●":"");
                            oDeUCI.setAspiracionSecre(!((String)vRowTemp.get(20)).equals("")?"●":"");
                            oDeUCI.setLavBronq(!((String)vRowTemp.get(21)).equals("")?"●":"");
                            oDeUCI.setNebulizacion(!((String)vRowTemp.get(22)).equals("")?"●":"");
                            oDeUCI.setFisioPulmonar(!((String)vRowTemp.get(23)).equals("")?"●":"");
                            oDeUCI.setDialisis(!((String)vRowTemp.get(24)).equals("")?"●":"");
                            oDeUCI.setMonitoreo(!((String)vRowTemp.get(25)).equals("")?"●":"");
                            oDeUCI.setEjercVesical(!((String)vRowTemp.get(26)).equals("")?"●":"");
                            oDeUCI.setEjercResp(!((String)vRowTemp.get(27)).equals("")?"●":"");
                            oDeUCI.setPvc(!((String)vRowTemp.get(28)).equals("")?"●":"");
                            oDeUCI.setAseosOCulares(!((String)vRowTemp.get(29)).equals("")?"●":"");
                            oDeUCI.getDetalleUCI().getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(30));
                            oDeUCI.getDetalleUCI().getEpisodio().setFechaIngreso((Date)vRowTemp.get(31));
                            oDeUCI.getDetalleUCI().getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(32)).intValue());
                            arrRet[i]=oDeUCI;
                        } 
                    }                     
                }		
		return arrRet; 
	} 
    
        public ImprimeHojaSupervisionServciosHosp[] buscarProcedimientosEnfermeriaAplicadosServicios() throws Exception{
            ImprimeHojaSupervisionServciosHosp arrRet[] = null,oDeSup = null;         
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
            if(this.getDetalleSer().getCabeceraSupervision().getFechaFormat().equals("")
                    || this.getDetalleSer().getCabeceraSupervision().getAreaServicio().getClave()==0){
                throw new Exception("DetalleSuperServicios.buscarProcedimientosEnfermeriaAplicados: error, faltan datos");
            }else{                
                sQuery = "SELECT * FROM buscaProcedimientosAplicadosEnfermeriaServicios('"
                        +this.getDetalleSer().getCabeceraSupervision().getFechaFormat()+"'::date,"
                        +this.getDetalleSer().getCabeceraSupervision().getAreaServicio().getClave()+"::smallint);";                 
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                }
                if (rst != null && rst.size()>0) {
                        arrRet = new ImprimeHojaSupervisionServciosHosp[rst.size()];
                        for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp= (ArrayList)rst.get(i);
                            oDeSup= new ImprimeHojaSupervisionServciosHosp();
                            oDeSup.getDetalleSer().getCabeceraSupervision().setFechaSupervicion((Date)vRowTemp.get(0));
                            oDeSup.getDetalleSer().getCabeceraSupervision().getAreaServicio().setClave(((Double)vRowTemp.get(1)).intValue());
                            oDeSup.getDetalleSer().setIdSupervision(((Double)vRowTemp.get(2)).longValue());
                            oDeSup.getDetalleSer().getCabeceraSupervision().setIdCabecera(((Double)vRowTemp.get(3)).longValue());
                            oDeSup.getDetalleSer().getEpisodio().getCama().setNumero((String)vRowTemp.get(4));
                            oDeSup.getDetalleSer().getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(5)).intValue());
                            oDeSup.getDetalleSer().getEpisodio().getPaciente().setNombres((String)vRowTemp.get(6));
                            oDeSup.getDetalleSer().getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(7));
                            oDeSup.getDetalleSer().getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(8));
                            oDeSup.getDetalleSer().getEpisodio().setFechaIngreso((Date)vRowTemp.get(9));
                            oDeSup.getDetalleSer().setObservacion((String)vRowTemp.get(10));
                            oDeSup.getDetalleSer().getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(11));
                            oDeSup.getDetalleSer().setOxigeno(!((String)vRowTemp.get(12)).equals(""));
                            oDeSup.getDetalleSer().setCateter(!((String)vRowTemp.get(13)).equals(""));
                            oDeSup.getDetalleSer().setSondas(!((String)vRowTemp.get(14)).equals(""));
                            oDeSup.getDetalleSer().setVenoclisis(!((String)vRowTemp.get(15)).equals(""));
                            oDeSup.getDetalleSer().setTransfusion(!((String)vRowTemp.get(16)).equals(""));
                            oDeSup.getDetalleSer().setVentilador(!((String)vRowTemp.get(17)).equals(""));                            
                            oDeSup.getDetalleSer().setNPT(!((String)vRowTemp.get(18)).equals(""));
                            oDeSup.getDetalleSer().setControlLiquidos(!((String)vRowTemp.get(19)).equals(""));
                            oDeSup.getDetalleSer().setGlicemias(!((String)vRowTemp.get(20)).equals(""));
                            oDeSup.getDetalleSer().setCuraciones(!((String)vRowTemp.get(21)).equals(""));
                            oDeSup.getDetalleSer().setColutorios(!((String)vRowTemp.get(22)).equals(""));
                            oDeSup.getDetalleSer().setAspiracionSecre(!((String)vRowTemp.get(23)).equals(""));
                            oDeSup.getDetalleSer().setLavadoBronq(!((String)vRowTemp.get(24)).equals(""));
                            oDeSup.getDetalleSer().setNebulizaciones(!((String)vRowTemp.get(25)).equals(""));
                            oDeSup.getDetalleSer().setFisioPulmonar(!((String)vRowTemp.get(26)).equals(""));
                            oDeSup.getDetalleSer().setDialisisPeritonial(!((String)vRowTemp.get(27)).equals(""));
                            oDeSup.getDetalleSer().setDrenajesCerrados(!((String)vRowTemp.get(28)).equals(""));
                            oDeSup.getDetalleSer().setMonitoreo(!((String)vRowTemp.get(29)).equals(""));
                            oDeSup.getDetalleSer().setEjercicioVesical(!((String)vRowTemp.get(30)).equals(""));
                            oDeSup.getDetalleSer().setEjercicioResp(!((String)vRowTemp.get(31)).equals(""));
                            oDeSup.getDetalleSer().setAlta(!((String)vRowTemp.get(32)).equals(""));
                            oDeSup.setOxigeno(!((String)vRowTemp.get(12)).equals("")?"●":"");
                            oDeSup.setCateter(!((String)vRowTemp.get(13)).equals("")?"●":"");
                            oDeSup.setSondas(!((String)vRowTemp.get(14)).equals("")?"●":"");
                            oDeSup.setVenoclisis(!((String)vRowTemp.get(15)).equals("")?"●":"");
                            oDeSup.setTransfusion(!((String)vRowTemp.get(16)).equals("")?"●":"");
                            oDeSup.setVentilador(!((String)vRowTemp.get(17)).equals("")?"●":"");
                            oDeSup.setNpt(!((String)vRowTemp.get(18)).equals("")?"●":"");
                            oDeSup.setControlLiquidos(!((String)vRowTemp.get(19)).equals("")?"●":"");
                            oDeSup.setGlicemias(!((String)vRowTemp.get(20)).equals("")?"●":"");
                            oDeSup.setCuraciones(!((String)vRowTemp.get(21)).equals("")?"●":"");
                            oDeSup.setColutorios(!((String)vRowTemp.get(22)).equals("")?"●":"");
                            oDeSup.setAspiracionSecre(!((String)vRowTemp.get(23)).equals("")?"●":"");
                            oDeSup.setLavBronq(!((String)vRowTemp.get(24)).equals("")?"●":"");
                            oDeSup.setNebulizacion(!((String)vRowTemp.get(25)).equals("")?"●":"");
                            oDeSup.setFisioPulmonar(!((String)vRowTemp.get(26)).equals("")?"●":"");
                            oDeSup.setDialisis(!((String)vRowTemp.get(27)).equals("")?"●":"");
                            oDeSup.setDrenajesCerrados(!((String)vRowTemp.get(28)).equals("")?"●":"");
                            oDeSup.setMonitoreo(!((String)vRowTemp.get(29)).equals("")?"●":"");
                            oDeSup.setEjercVesical(!((String)vRowTemp.get(30)).equals("")?"●":"");
                            oDeSup.setEjercResp(!((String)vRowTemp.get(31)).equals("")?"●":"");
                            oDeSup.setAlta(!((String)vRowTemp.get(32)).equals("")?"●":"");
                            arrRet[i]= oDeSup;
                        } 
                } 
            }
            return arrRet; 
	} 
        

    public String getVenoclisis() {
        return sVenoclisis;
    }

    public void setVenoclisis(String sVenoclisis) {
        this.sVenoclisis = sVenoclisis;
    }

    public String getCateter() {
        return sCateter;
    }

    public void setCateter(String sCateter) {
        this.sCateter = sCateter;
    }

    public String getTransfusion() {
        return sTransfusion;
    }

    public void setTransfusion(String sTransfusion) {
        this.sTransfusion = sTransfusion;
    }

    public String getNpt() {
        return sNpt;
    }

    public void setNpt(String sNpt) {
        this.sNpt = sNpt;
    }

    public String getPvc() {
        return sPvc;
    }

    public void setPvc(String sPvc) {
        this.sPvc = sPvc;
    }

    public String getSondas() {
        return sSondas;
    }

    public void setSondas(String sSondas) {
        this.sSondas = sSondas;
    }

    public String getGlicemias() {
        return sGlicemias;
    }

    public void setGlicemias(String sGlicemias) {
        this.sGlicemias = sGlicemias;
    }

    public String getCuraciones() {
        return sCuraciones;
    }

    public void setCuraciones(String sCuraciones) {
        this.sCuraciones = sCuraciones;
    }

    public String getMonitoreo() {
        return sMonitoreo;
    }

    public void setMonitoreo(String sMonitoreo) {
        this.sMonitoreo = sMonitoreo;
    }

    public String getVentilador() {
        return sVentilador;
    }

    public void setVentilador(String sVentilador) {
        this.sVentilador = sVentilador;
    }
  
    public String getOxigeno() {
        return sOxigeno;
    }

    public void setOxigeno(String sOxigeno) {
        this.sOxigeno = sOxigeno;
    }

    public String getFisioPulmonar() {
        return sFisioPulmonar;
    }

    public void setFisioPulmonar(String sFisioPulmonar) {
        this.sFisioPulmonar = sFisioPulmonar;
    }

    public String getEjercResp() {
        return sEjercResp;
    }

    public void setEjercResp(String sEjercResp) {
        this.sEjercResp = sEjercResp;
    }

    public String getAspiracionSecre() {
        return sAspiracionSecre;
    }

    public void setAspiracionSecre(String sAspiracionSecre) {
        this.sAspiracionSecre = sAspiracionSecre;
    }

    public String getLavBronq() {
        return sLavBronq;
    }

    public void setLavBronq(String sLavBronq) {
        this.sLavBronq = sLavBronq;
    }

    public String getNebulizacion() {
        return sNebulizacion;
    }

    public void setNebulizacion(String sNebulizacion) {
        this.sNebulizacion = sNebulizacion;
    }

    public String getEjercVesical() {
        return sEjercVesical;
    }

    public void setEjercVesical(String sEjercVesical) {
        this.sEjercVesical = sEjercVesical;
    }

    public String getDialisis() {
        return sDialisis;
    }

    public void setDialisis(String sDialisis) {
        this.sDialisis = sDialisis;
    }

    public String getAseosOCulares() {
        return sAseosOCulares;
    }

    public void setAseosOCulares(String sAseosOCulares) {
        this.sAseosOCulares = sAseosOCulares;
    }

    public String getColutorios() {
        return sColutorios;
    }

  
    public void setColutorios(String sColutorios) {
        this.sColutorios = sColutorios;
    }

    public String getControlLiquidos() {
        return sControlLiquidos;
    }

    public void setControlLiquidos(String sControlLiquidos) {
        this.sControlLiquidos = sControlLiquidos;
    }
   
    public String getDrenajesCerrados() {
        return sDrenajesCerrados;
    }

    public void setDrenajesCerrados(String sDrenajesCerrados) {
        this.sDrenajesCerrados = sDrenajesCerrados;
    }

    public DetalleSupervisionUCI getDetalleUCI() {
        return oDetalleUCI;
    }

    public void setDetalleUCI(DetalleSupervisionUCI oDetalleUCI) {
        this.oDetalleUCI = oDetalleUCI;
    }   

    public DetalleSuperServicios getDetalleSer() {
        return oDetalleSer;
    }

    public void setDetalleSer(DetalleSuperServicios oDetalleSer) {
        this.oDetalleSer = oDetalleSer;
    }

    public String getAlta() {
        return sAlta;
    }

    public void setAlta(String sAlta) {
        this.sAlta = sAlta;
    }
    
}
