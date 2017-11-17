package mx.gob.hrrb.modelo.enfermeria.reporte;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class MovimientoPacientes implements Serializable{
    private AccesoDatos oAD;
    private String sUsuarioFirmado;
    private Date dFecha;
    private int nAltaVoM;
    private int nAltaVoN;
    private int nAltaVoV;
    private int nCensadoM;
    private int nCensadoN;
    private int nCensadoV;
    private int nDefuncioM;
    private int nDefuncionV;
    private int nFisicosM;
    private int nFisicosN;
    private int nFisicosV;
    private int nGravesM;
    private int nGravesN;
    private int nGravesV;
    private int nPreoperatorioM;
    private int nPreoperatorioN;
    private int nPreoperatorioV;
    private int nReferidosM;
    private int nReferidosN;
    private int nReferidosV;
    private int nTrasladosM;
    private int nTrasladosN;
    private int nTrasladosV;   
    
    private int nExistendiaPac;
    private int nExistencia24h;
    private int nTotalCamas;
    private int nCamasDisponibles;
    
    private String sPaseDe;
    private String sPaseA;
    private Date dHoraVivo;
    private Date dHoraMuerto;
    private AreaServicioHRRB oServicio;
    private EpisodioMedico oEpisodio;
    private PersonalHospitalario oJefaEnfermeria;
    
    public MovimientoPacientes(){
        dFecha = new Date();
        oServicio= new AreaServicioHRRB();
        oEpisodio= new EpisodioMedico();
        oJefaEnfermeria=new PersonalHospitalario();
        HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
    }
    
    public MovimientoPacientes[] buscaMovimientoDiarioDePacientesPorServicioYfecha() throws Exception{
        MovimientoPacientes arrRet[]=null, oMovP;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
        sQuery="SELECT * FROM buscaMovimientoDiarioDePacientes('"
                +this.getFechaMovimientoStr()+"'::date,"
                +this.getServicio().getClave()+"::smallint);";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null && rst.size()>0) {
            arrRet = new MovimientoPacientes[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                ArrayList vRowTemp= (ArrayList)rst.get(i);
                oMovP= new MovimientoPacientes();
                oMovP.getEpisodio().getCama().setNumero((String)vRowTemp.get(2));
                oMovP.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oMovP.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                oMovP.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                oMovP.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                oMovP.getEpisodio().setFechaIngreso((Date)vRowTemp.get(7));
                oMovP.setPaseDe((String)vRowTemp.get(8));
                oMovP.setPaseA((String)vRowTemp.get(9));
                oMovP.getEpisodio().setAltaHospitalaria((Date)vRowTemp.get(10));
                oMovP.getEpisodio().setMotivoEgresoP((String)vRowTemp.get(11));
                oMovP.getEpisodio().setDiasEstancia(((Double)vRowTemp.get(12)).intValue());
                if(oMovP.getEpisodio().getMotivoEgresoP().equals("T3805")){
                    oMovP.setHoraMuerto((Date)vRowTemp.get(10));
                }else{
                    oMovP.setHoraVivo((Date)vRowTemp.get(10));
                }
                arrRet[i]=oMovP;
            } 
        } 
        return arrRet; 
    } 

    public void buscaResumenMovimientoDiarioPacientes()throws Exception{
        ArrayList rst=null;
        String sQuery="";
        if(this.getFechaMovimiento()==null || this.getServicio().getClave()==0){
            throw new  Exception("MovimientoPacientes.buscaResumenMovimientoDiarioPacientes:error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscaResumenMovimientoDiarioPacientes('"
                    +this.getFechaMovimientoStr() +"'::DATE,"
                    +this.getServicio().getClave() +"::SMALLINT);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()==1){
                ArrayList vRowTemp= (ArrayList)rst.get(0);
                this.setExistendiaPac(((Double)vRowTemp.get(0)).intValue());
                this.setTotalCamas(((Double)vRowTemp.get(1)).intValue());
                this.setCamasDisponibles(((Double)vRowTemp.get(2)).intValue());                
            }
        }
    }
    
    public void buscaNombreJefeEnfermeria()throws Exception{       
        ArrayList rst=null;
        String sQuery="";
        sQuery="SELECT * FROM buscaInfomacionJefeEnfermeria();";       
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst= oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }       
        if(rst!=null){            
            ArrayList vRowTemp=(ArrayList)rst.get(0);
            getJefaEnfermeria().setNombres((String)vRowTemp.get(0));
            getJefaEnfermeria().setApPaterno((String)vRowTemp.get(1));
            getJefaEnfermeria().setApMaterno((String)vRowTemp.get(2));
        }        
    }
    
    public int getAltaVoM() {
        return nAltaVoM;
    }

    public void setAltaVoM(int valor) {
        this.nAltaVoM=valor;
    }

    public int getAltaVoN() {
        return nAltaVoN;
    }

    public void setAltaVoN(int valor) {
        this.nAltaVoN=valor;
    }

    public int getAltaVoV() {
        return nAltaVoV;
    }

    public void setAltaVoV(int valor) {
        this.nAltaVoV=valor;
    }

    public int getCensadoM() {
        return nCensadoM;
    }

    public void setCensadoM(int valor) {
        this.nCensadoM=valor;
    }

    public int getCensadoN() {
        return nCensadoN;
    }

    public void setCensadoN(int valor) {
        this.nCensadoN=valor;
    }

    public int getCensadoV() {
        return nCensadoV;
    }

    public void setCensadoV(int valor) {
        this.nCensadoV=valor;
    }

    public int getDefuncioM() {
        return nDefuncioM;
    }

    public void setDefuncioM(int valor) {
        this.nDefuncioM=valor;
    }

    public int getDefuncionV() {
        return nDefuncionV;
    }

    public void setDefuncionV(int valor) {
        this.nDefuncionV=valor;
    }

    public int getFisicosM() {
        return nFisicosM;
    }

    public void setFisicosM(int valor) {
        this.nFisicosM=valor;
    }

    public int getFisicosN() {
        return nFisicosN;
    }

    public void setFisicosN(int valor) {
        this.nFisicosN=valor;
    }

    public int getFisicosV() {
        return nFisicosV;
    }

    public void setFisicosV(int valor) {
        this.nFisicosV=valor;
    }

    public int getGravesM() {
        return nGravesM;
    }

    public void setGravesM(int valor) {
        this.nGravesM=valor;
    }

    public int getGravesN() {
        return nGravesN;
    }

    public void setGravesN(int valor) {
        this.nGravesN=valor;
    }

    public int getGravesV() {
        return nGravesV;
    }

    public void setGravesV(int valor) {
        this.nGravesV=valor;
    }

    public int getPreoperatorioM() {
        return nPreoperatorioM;
    }

    public void setPreoperatorioM(int valor) {
        this.nPreoperatorioM=valor;
    }

    public int getPreoperatorioN() {
        return nPreoperatorioN;
    }

    public void setPreoperatorioN(int valor) {
        this.nPreoperatorioN=valor;
    }

    public int getPreoperatorioV() {
        return nPreoperatorioV;
    }

    public void setPreoperatorioV(int valor) {
        this.nPreoperatorioV=valor;
    }

    public int getReferidosM() {
        return nReferidosM;
    }

    public void setReferidosM(int valor) {
        this.nReferidosM=valor;
    }

    public int getReferidosN() {
        return nReferidosN;
    }

    public void setReferidosN(int valor) {
        this.nReferidosN=valor;
    }

    public int getReferidosV() {
        return nReferidosV;
    }

    public void setReferidosV(int valor) {
        this.nReferidosV=valor;
    }

    public int getTrasladosM() {
        return nTrasladosM;
    }

    public void setTrasladosM(int valor) {
        this.nTrasladosM=valor;
    }

    public int getTrasladosN() {
        return nTrasladosN;
    }

    public void setTrasladosN(int valor) {
        this.nTrasladosN=valor;
    }

    public int getTrasladosV() {
        return nTrasladosV;
    }

    public void setTrasladosV(int valor) {
        this.nTrasladosV=valor;
    }

    public Date getFechaMovimiento() {
        return dFecha;
    }

   
    public void setFechaMovimiento(Date dFecha) {
        this.dFecha = dFecha;
    }

    public String getFechaMovimientoStr(){
        SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dFecha);
    }

    public AreaServicioHRRB getServicio() {
        return oServicio;
    }

    public void setServicio(AreaServicioHRRB oServicio) {
        this.oServicio = oServicio;
    }

    public EpisodioMedico getEpisodio() {
        return oEpisodio;
    }

    public void setEpisodio(EpisodioMedico oEpisodio) {
        this.oEpisodio = oEpisodio;
    }
    
    public String getHoraIngreso(){
        SimpleDateFormat df= new SimpleDateFormat("HH:mm");
        if(this.getEpisodio().getFechaIngreso()!=null){
            return df.format(this.getEpisodio().getFechaIngreso());
        }else{
            return "";
        }
    }

    public String getPaseDe() {
        return sPaseDe;
    }

    public void setPaseDe(String sPaseDe) {
        this.sPaseDe = sPaseDe;
    }

    public String getPaseA() {
        return sPaseA;
    }

    public void setPaseA(String sPaseA) {
        this.sPaseA = sPaseA;
    }

    public Date getHoraVivo() {
        return dHoraVivo;
    }

    public void setHoraVivo(Date dHoraVivo) {
        this.dHoraVivo = dHoraVivo;
    }

    public Date getHoraMuerto() {
        return dHoraMuerto;
    }

    public void setHoraMuerto(Date dHoraMuerto) {
        this.dHoraMuerto = dHoraMuerto;
    }
    
    public String getHoraMuertoStr(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        if(this.getHoraMuerto()!=null){
            return df.format(this.getHoraMuerto());
        }else{
            return "";
        }
        
    }
    
    public String getHoraVivoStr(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        if(this.getHoraVivo()!=null){
            return df.format(this.getHoraVivo());
        }else{
            return "";
        }
    }

    public int getExistendiaPac() {
        return nExistendiaPac;
    }

    public void setExistendiaPac(int nExistendiaPac) {
        this.nExistendiaPac = nExistendiaPac;
    }

    public int getExistencia24h() {
        return nExistencia24h;
    }

    public void setExistencia24h(int nExistencia24h) {
        this.nExistencia24h = nExistencia24h;
    }

    public int getTotalCamas() {
        return nTotalCamas;
    }

    public void setTotalCamas(int nTotalCamas) {
        this.nTotalCamas = nTotalCamas;
    }

    public int getCamasDisponibles() {
        return nCamasDisponibles;
    }

    public void setCamasDisponibles(int nCamasDisponibles) {
        this.nCamasDisponibles = nCamasDisponibles;
    }

    public PersonalHospitalario getJefaEnfermeria() {
        return oJefaEnfermeria;
    }

    public void setJefaEnfermeria(PersonalHospitalario oJefaEnfermeria) {
        this.oJefaEnfermeria = oJefaEnfermeria;
    }
} 
