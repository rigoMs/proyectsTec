package mx.gob.hrrb.modelo.enfermeria.reporte;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Danny
 */
public class RptCensoMensualEnfermeria {
    private AccesoDatos oAD;
    private String sUsuFirm;
    private Date dFechaIni;
    private Date dFechaFin;
    private int ningresos;
    private int negresos;
    private int ndefunciones;
    private int nSumEgreDefun;
    private int nfaltantes;
    private Hospitalizacion oHosp;
    private AreaServicioHRRB oServicio;
    
    public RptCensoMensualEnfermeria(){
        HttpServletRequest r;
        oHosp= new Hospitalizacion();
        oServicio= new AreaServicioHRRB();
        r=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
        if(r.getSession().getAttribute("oFirm")!=null)
            sUsuFirm = ((Firmado)r.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
    }
    
    public RptCensoMensualEnfermeria[] buscaRptMensualEnfermeria() throws Exception{
        RptCensoMensualEnfermeria arrRet[] = null, oRpt=null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptCensoMensualEnfermeria.buscaRptMensualEnfermeria: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaReporteEnfermeriaCensoMensual('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet=new RptCensoMensualEnfermeria[rst.size()];
                for(i=0;i<rst.size();i++){
                    oRpt= new RptCensoMensualEnfermeria();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oRpt.getServicio().setClave(((Double)vRowTemp.get(0)).intValue());
                    oRpt.getServicio().setDescripcion((String)vRowTemp.get(1));
                    oRpt.setIngresos(((Double)vRowTemp.get(2)).intValue());
                    oRpt.setEgresos(((Double)vRowTemp.get(3)).intValue());
                    oRpt.setDefunciones(((Double)vRowTemp.get(4)).intValue());
                    oRpt.setSumaEgreDefun(((Double)vRowTemp.get(5)).intValue());
                    oRpt.setFaltantes(((Double)vRowTemp.get(6)).intValue());
                    arrRet[i]=oRpt;
                }
            }
        }
        return arrRet;
    }
    
    public Hospitalizacion[] buscaDefuncionesPorServicio(int claveservicio) throws Exception{
        Hospitalizacion arrRet[] = null, oHosp = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null && claveservicio==0){
            throw new Exception("Hospitalización.buscaDefuncionesPorServicio: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaDefuncionesPorServicio('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE,"+claveservicio+"::SMALLINT);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet=new Hospitalizacion[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oHosp = new Hospitalizacion();
                    oHosp.getPaciente().setFolioPaciente(((Double) vRowTemp.get(0)).longValue());
                    oHosp.getEpisodioMedico().setClaveEpisodio(((Double) vRowTemp.get(1)).longValue());
                    oHosp.setNumIngresoHos(((Double)vRowTemp.get(2)).intValue());
                    oHosp.getPaciente().setNombres((String) vRowTemp.get(3));
                    oHosp.getPaciente().setApPaterno((String) vRowTemp.get(4));
                    oHosp.getPaciente().setApMaterno((String) vRowTemp.get(5));
                    oHosp.getPaciente().setSexoP((String) vRowTemp.get(6));
                    oHosp.getPaciente().setFechaNac((Date) vRowTemp.get(7));
                    String convierte=edad.format(oHosp.getPaciente().getFechaNac());
                    oHosp.getPaciente().setFechaNacTexto(convierte);
                    oHosp.getPaciente().calculaEdad();
                    oHosp.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(8)).intValue());
                    oHosp.setFechaIngresoHos((Date)vRowTemp.get(9));
                    oHosp.getEpisodioMedico().setMotivoEgresoP((String)vRowTemp.get(11));
                    oHosp.getEpisodioMedico().setAltaHospitalaria((Date)vRowTemp.get(12));
                    oHosp.getEpisodioMedico().setAreaServicioHRRB((String)vRowTemp.get(13));
                    oHosp.getEpisodioMedico().setFechaIngreso((Date)vRowTemp.get(14));
                    oHosp.getEpisodioMedico().getCama().setNumero((String)vRowTemp.get(15));
                    oHosp.getEpisodioMedico().getCama().setAreaServicioHRRB((String)vRowTemp.get(16));
                    arrRet[i]=oHosp;
                }
            }
        }
        return arrRet;
    }
    
    public String obtenFechaAux(){
        String f="";
        SimpleDateFormat fechaHora=new SimpleDateFormat("dd/MM/yyyy HH:mm");      
        f = fechaHora.format(new Date());
        return f;
    }
    
    public String getFechaArch1() {
        String f="";
        SimpleDateFormat fechaHora=new SimpleDateFormat("dd-MM-yyyy");      
        f = fechaHora.format(getFechaIni());
        return f;
    }
    
    public String getFechaArch2() {
        String f="";
        SimpleDateFormat fechaHora=new SimpleDateFormat("dd-MM-yyyy");      
        f = fechaHora.format(getFechaFin());
        return f;
    }
    
    
    public Date getFechaIni() {
        return dFechaIni;
    }

    public void setFechaIni(Date dFechaIni) {
        this.dFechaIni = dFechaIni;
    }

    public Date getFechaFin() {
        return dFechaFin;
    }

    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

    public int getIngresos() {
        return ningresos;
    }

    public void setIngresos(int ningresos) {
        this.ningresos = ningresos;
    }

    public int getEgresos() {
        return negresos;
    }

    public void setEgresos(int negresos) {
        this.negresos = negresos;
    }

    public int getDefunciones() {
        return ndefunciones;
    }

    public void setDefunciones(int ndefunciones) {
        this.ndefunciones = ndefunciones;
    }

    public int getSumaEgreDefun() {
        return nSumEgreDefun;
    }

    public void setSumaEgreDefun(int nSumEgreDefun) {
        this.nSumEgreDefun = nSumEgreDefun;
    }

    public int getFaltantes() {
        return nfaltantes;
    }

    public void setFaltantes(int nfaltantes) {
        this.nfaltantes = nfaltantes;
    }

    public Hospitalizacion getHosp() {
        return oHosp;
    }

    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }

    public AreaServicioHRRB getServicio() {
        return oServicio;
    }

    public void setServicio(AreaServicioHRRB oServicio) {
        this.oServicio = oServicio;
    }
}