package mx.gob.hrrb.modelo.enfermeria.reporte;



import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.modelo.enfermeria.HojaEnfermeriaQuirofano;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class HojaSuperYActividadesQx implements Serializable{
    private AccesoDatos oAD;
    private String sUsuarioFirmado;
    private Date dFecha;
    private PersonalHospitalario oEnfRealiza;
    private HojaEnfermeriaQuirofano oQx;
    private ProcedimientosRealizados oIntervencion; 
    private int nNumero;
    private String sCirugiaGeneral;
    private String sGinecoObstetricia;
    private String sMedicinaInterma;
    private String sPediatria;
    private String sOtros;
    private String sServiciosUrg;
    private String sCirugiaProgramada;
    private String sCirugiaUrgente;
    private String sTiempoQuirurgico;
    private String sCirugiaSuspendida;
    private String sMotivoSuspension;
    
    private String sSala;
    private String sUCI;
    private String sUCIP;
    private String sUCIN;
    private String sOncoPedriatria;
    
    private String sClaveAgrupacion;
    private String sDescripcionAgrupacion; 
    
    private Parametrizacion oTipoCirugia;

    public HojaSuperYActividadesQx(){
        dFecha= new Date();
        oIntervencion = new ProcedimientosRealizados();
        oIntervencion.inicializar();
        oQx= new HojaEnfermeriaQuirofano();
        oTipoCirugia = new Parametrizacion();
        HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
    }
    
    public HojaSuperYActividadesQx[] buscarHojaSupervisionQuirofano() throws Exception{
        HojaSuperYActividadesQx oRet[] = null,oHj;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
        if( this.getFecha()==null){
                throw new Exception("HojaSuperYActividadesQx.buscarHojaSupervisionQuirofano: error, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM buscaHojaSupervisionQuirofano('"+this.getFechaStr()+"'::date);"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null && rst.size()>0) {
                oRet= new HojaSuperYActividadesQx[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oHj= new HojaSuperYActividadesQx();
                    oHj.getHojaQx().getEpisodioMedico().getCama().setNumero((String)vRowTemp.get(0));
                    oHj.getHojaQx().getEpisodioMedico().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(1)).intValue());
                    oHj.getHojaQx().getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(2));
                    oHj.getHojaQx().getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(3));
                    oHj.getHojaQx().getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(4));
                    oHj.getHojaQx().getProcedimiento().setQuirofano((String)vRowTemp.get(5));
                    oHj.getHojaQx().getProcedimiento().getCIE9().setDescripcion((String)vRowTemp.get(6));
                    oHj.getHojaQx().getProcedimiento().getCirujano().setNombres((String)vRowTemp.get(7));
                    oHj.getHojaQx().getProcedimiento().getCirujano().setApPaterno((String)vRowTemp.get(8));
                    oHj.getHojaQx().getProcedimiento().getCirujano().setApMaterno((String)vRowTemp.get(9));
                    oHj.getHojaQx().setAnesteciologo(new PersonalHospitalario());
                    oHj.getHojaQx().getAnesteciologo().setNombres((String)vRowTemp.get(10));
                    oHj.getHojaQx().getAnesteciologo().setApPaterno((String)vRowTemp.get(11));
                    oHj.getHojaQx().getAnesteciologo().setApMaterno((String)vRowTemp.get(12));
                    oHj.getHojaQx().setInstrumentista(new PersonalHospitalario());
                    oHj.getHojaQx().getInstrumentista().setNombres((String)vRowTemp.get(13));
                    oHj.getHojaQx().getInstrumentista().setApPaterno((String)vRowTemp.get(14));
                    oHj.getHojaQx().getInstrumentista().setApMaterno((String)vRowTemp.get(15));
                    oHj.getHojaQx().setCirculante(new PersonalHospitalario());
                    oHj.getHojaQx().getCirculante().setNombres((String)vRowTemp.get(16));
                    oHj.getHojaQx().getCirculante().setApPaterno((String)vRowTemp.get(17));
                    oHj.getHojaQx().getCirculante().setApMaterno((String)vRowTemp.get(18));
                    oHj.getHojaQx().getProcedimiento().setResultado((String)vRowTemp.get(19));
                    oRet[i]=oHj;
                }
            }
        } 
        return oRet; 
    } 
    
    public HojaSuperYActividadesQx[] buscaActividadesDiariasQuirofano()throws Exception{
        HojaSuperYActividadesQx arrRet[]=null, oA;
        ArrayList rst=null;
        String sQuery="",sFecha="2016-05-10";
        int i=0;
        if(this.getFecha()==null){
            throw new Exception("HojaSuperYActividadesQx.buscaActividadesDiariasQuirofano: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscaActividadesDiariasQuirofano('"+this.getFechaStr()+"'::date);";
            //sQuery="SELECT * FROM buscaActividadesDiariasQuirofano('"+sFecha+"'::date);";
            
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                arrRet= new HojaSuperYActividadesQx[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp= (ArrayList)rst.get(i);                    
                    oA= new HojaSuperYActividadesQx();                   
                    oA.getProcedimiento().setEpisodioMedico(new EpisodioMedico());
                    oA.getProcedimiento().getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oA.getProcedimiento().getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oA.getProcedimiento().getEpisodioMedico().getCama().setNumero((String)vRowTemp.get(2));
                    oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oA.getProcedimiento().getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(4));
                    oA.getProcedimiento().getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(5));
                    oA.getProcedimiento().getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(6));
                    oA.getProcedimiento().getCIE9().setDescripcion((String)vRowTemp.get(7));
                    oA.getTipoCirugia().setClaveParametro((String)vRowTemp.get(8));
                    oA.getTipoCirugia().setValor((String)vRowTemp.get(9));
                    oA.setTiempoQuirurgico((String)vRowTemp.get(10));
                    oA.setMotivoSuspension((String)vRowTemp.get(11));
                    oA.getProcedimiento().getCirujano().setNombres((String)vRowTemp.get(12));
                    oA.getProcedimiento().getCirujano().setApPaterno((String)vRowTemp.get(13));
                    oA.getProcedimiento().getCirujano().setApMaterno((String)vRowTemp.get(14));
                    oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().setClave(((Double)vRowTemp.get(15)).intValue());
                    oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().setDescripcion((String)vRowTemp.get(16));
                    oA.setClaveAgrupacion((String)vRowTemp.get(17));
                    oA.setDescripcionAgrupacion((String)vRowTemp.get(18));
                    oA.setSala((String)vRowTemp.get(19));
                    oA.setNumero(i+1);                    
                    if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==74){
                        oA.setServiciosUrg("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==75){
                        oA.setServiciosUrg("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==76){
                       oA.setServiciosUrg("1"); 
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==13){
                        oA.setCirugiaGeneral("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==29
                            || oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==69 ){
                        oA.setGinecoObstetricia("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==37){
                        oA.setMedicinaInterma("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==56){
                        oA.setPediatria("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==72){
                        oA.setUCI("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==73){
                        oA.setUCIP("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==79
                            || oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==39){
                       oA.setUCIN("1");
                    }else if(oA.getProcedimiento().getEpisodioMedico().getPaciente().getExpediente().getServicioIngreso().getClave()==48){
                        oA.setOncoPedriatria("1");
                    }            
                    int nTipoCirugia=Integer.parseInt(((String)vRowTemp.get(8)).substring(0, 1));
                    if(nTipoCirugia==3){
                        oA.setCirugiaProgramada("1");
                    }else if(nTipoCirugia==1){
                        oA.setCirugiaUrgente("1");
                    }else if(nTipoCirugia==5){
                        oA.setCirugiaSuspendida("1");
                    }
                    arrRet[i]=oA;
                }
            }
        }      
        return arrRet;
    }
            
    
    public HojaSuperYActividadesQx[] buscarTodos() throws Exception{
        HojaSuperYActividadesQx[] arrRet = null;
        HojaSuperYActividadesQx oRegistroActividadesQuirofano = null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
        sQuery = "SELECT * FROM buscaTodosRegistroActividadesQuirofano();"; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null) {
                arrRet = new HojaSuperYActividadesQx[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                } 
        } 
        return arrRet; 
    }     
    
    public Date getFecha() {
        return dFecha;
    }

    public void setFecha(Date valor) {
        this.dFecha=valor;
    }

    public PersonalHospitalario getEnfRealiza() {
        return oEnfRealiza;
    }

    public void setEnfRealiza(PersonalHospitalario valor) {
        this.oEnfRealiza=valor;
    }

    public String getMotivoSuspension() {
        return sMotivoSuspension;
    }

    public void setMotivoSuspension(String valor) {
        this.sMotivoSuspension=valor;
    }

    
    public ProcedimientosRealizados getProcedimiento() {
        return oIntervencion;
    }

    public void setProcedimiento(ProcedimientosRealizados oIntervencion) {
        this.oIntervencion = oIntervencion;
    }

    
    public HojaEnfermeriaQuirofano getHojaQx() {
        return oQx;
    }

    
    public void setHojaQx(HojaEnfermeriaQuirofano oQx) {
        this.oQx = oQx;
    }

    public String getFechaStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dFecha);
    }

    public int getNumero() {
        return nNumero;
    }

    public void setNumero(int nNumero) {
        this.nNumero = nNumero;
    }

    public String getCirugiaGeneral() {
        return sCirugiaGeneral;
    }

    public void setCirugiaGeneral(String sCirugiaGeneral) {
        this.sCirugiaGeneral = sCirugiaGeneral;
    }

    public String getGinecoObstetricia() {
        return sGinecoObstetricia;
    }

    public void setGinecoObstetricia(String sGinecoObstetricia) {
        this.sGinecoObstetricia = sGinecoObstetricia;
    }

    public String getMedicinaInterma() {
        return sMedicinaInterma;
    }

    public void setMedicinaInterma(String sMedicinaInterma) {
        this.sMedicinaInterma = sMedicinaInterma;
    }

    public String getPediatria() {
        return sPediatria;
    }

    public void setPediatria(String sPediatria) {
        this.sPediatria = sPediatria;
    }

    public String getOtros() {
        return sOtros;
    }

    public void setOtros(String sOtros) {
        this.sOtros = sOtros;
    }

    public String getServiciosUrg() {
        return sServiciosUrg;
    }

    public void setServiciosUrg(String sServiciosUrg) {
        this.sServiciosUrg = sServiciosUrg;
    }

    public String getCirugiaProgramada() {
        return sCirugiaProgramada;
    }

    public void setCirugiaProgramada(String sCirugiaProgramada) {
        this.sCirugiaProgramada = sCirugiaProgramada;
    }

    public String getCirugiaUrgente() {
        return sCirugiaUrgente;
    }
    
    public void setCirugiaUrgente(String sCirugiaUrgente) {
        this.sCirugiaUrgente = sCirugiaUrgente;
    }

    public String getTiempoQuirurgico() {
        return sTiempoQuirurgico;
    }

    public void setTiempoQuirurgico(String sTiempoQuirurgico) {
        this.sTiempoQuirurgico = sTiempoQuirurgico;
    }
    
    public String getCirugiaSuspendida() {
        return sCirugiaSuspendida;
    }
    
    public void setCirugiaSuspendida(String sCirugiaSuspendida) {
        this.sCirugiaSuspendida = sCirugiaSuspendida;
    }

    public Parametrizacion getTipoCirugia() {
        return oTipoCirugia;
    }

    public void setTipoCirugia(Parametrizacion oTipoCirugia) {
        this.oTipoCirugia = oTipoCirugia;
    }

    public String getClaveAgrupacion() {
        return sClaveAgrupacion;
    }

    public void setClaveAgrupacion(String sClaveAgrupacion) {
        this.sClaveAgrupacion = sClaveAgrupacion;
    }
    public String getDescripcionAgrupacion() {
        return sDescripcionAgrupacion;
    }

    public void setDescripcionAgrupacion(String sDescripcionAgrupacion) {
        this.sDescripcionAgrupacion = sDescripcionAgrupacion;
    }

    public String getSala() {
        return sSala;
    }

    public void setSala(String sSala) {
        this.sSala = sSala;
    }

    public String getUCI() {
        return sUCI;
    }

    public void setUCI(String sUCI) {
        this.sUCI = sUCI;
    }

    public String getUCIP() {
        return sUCIP;
    }

    public void setUCIP(String sUCIP) {
        this.sUCIP = sUCIP;
    }

    public String getUCIN() {
        return sUCIN;
    }

    public void setUCIN(String sUCIN) {
        this.sUCIN = sUCIN;
    }

    public String getOncoPedriatria() {
        return sOncoPedriatria;
    }

    public void setOncoPedriatria(String sOncoPedriatria) {
        this.sOncoPedriatria = sOncoPedriatria;
    }
} 
