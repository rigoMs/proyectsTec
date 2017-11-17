package mx.gob.hrrb.modelo.caja;

import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class Cuenta implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private Date dFechaApertura;
private static int FOLIO_FIN;
private static int FOLIO_INI;
private long nIdInternoCta;
private BigDecimal nMontoCubiertoProgramas;
private BigDecimal nMontoCubiertoSeguroPopular;
private BigDecimal nMontoPendiente;
private BigDecimal nMontoRecuperado;
private BigDecimal nMontoSubsidio;
private BigDecimal nMontoValeAdeudo;
private int nNumCta;
private BigDecimal nTotalPorServicios;
private PersonalHospitalario oAutorizadoPor;
private PersonalHospitalario oCapturadoPor;
private EpisodioMedico oEpisodio;
private Parametrizacion oTipoExento;
private String sRutaArchFact;
private AutorizacionPago arrAutorizacionesPago[];
private Recibo arrRecibos[];
private ServicioRealizado arrServiciosReallzados[];
 /////Reporte
        private String sCobertura;

    public Cuenta(){
    HttpServletRequest req;
        oEpisodio= new EpisodioMedico();
        oAutorizadoPor=new PersonalHospitalario();
        oCapturadoPor=new PersonalHospitalario(); 
        oTipoExento = new Parametrizacion();
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
            sUsuarioFirmado = ((Firmado)req.getSession().getAttribute(
                    "oFirm")).getUsu().getIdUsuario();
        }
    }
    
    public int buscaNumeroCuenta(long clavepac, long claveepi) throws Exception{
    int nCuenta=0;
    ArrayList rst = null;
    String sQuery = "";
        if( this==null){   //completar llave
            throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM buscaNumeroCuentapac("+clavepac+"::bigint,"+claveepi+"::bigint);";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                nCuenta=(((Double)vRowTemp.get( 0 )).intValue());
            }
        } 
        return nCuenta; 
    } 
    
    public int buscaNumeroHospitalizacion() throws Exception{
    int nCuenta=0;
    ArrayList rst = null;
    String sQuery = "";
        if( this==null){   //completar llave
            throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
        }else{ 
            sQuery = "select * from buscanumerosCuentaHospitalizacion(); ";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                nCuenta=(((Double)vRowTemp.get( 0 )).intValue());
            }
        } 
        return nCuenta; 
    } 
      
    public int buscaNumeroUrgencias() throws Exception{
    int nCuenta=0;
    ArrayList rst = null;
    String sQuery = "";
        if( this==null){   //completar llave
            throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
        }else{ 
            sQuery = "select * from buscanumerosCuentaUrgencia();";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                nCuenta=(((Double)vRowTemp.get( 0 )).intValue());
            }
        } 
        return nCuenta; 
    } 
    
    public int aperturarCuenta() throws Exception{
    int afec=0;
    ArrayList rst = null;
    String sQuery = "";
        if(this.oCapturadoPor==null || this.oCapturadoPor.getNoTarjeta()==0 ||
            this.oEpisodio==null || this.oEpisodio.getClaveEpisodio()==0 ||
            this.oEpisodio.getPaciente()==null || 
            this.oEpisodio.getPaciente().getFolioPaciente()==0){   //completar llave
            throw new Exception("Aperturar Cuenta: error de programacion, faltan datos");
        }else{ 
            sQuery = "select * from aperturarCuentaPacit('"+
                    sUsuarioFirmado+"', "+this.nNumCta+", "+
                    this.oCapturadoPor.getNoTarjeta()+", current_date,"+
                    this.oEpisodio.getPaciente().getFolioPaciente()+","+
                    this.oEpisodio.getClaveEpisodio()+");";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				afec=(((Double)vRowTemp.get( 0 )).intValue());
			}
		} 
              
              return afec;
          }
          
    public int cancelarCuenta(long foliopac) throws Exception{
    int afec=0;
    ArrayList rst = null;
    String sQuery = "";
        if( this==null){   //completar llave
            throw new Exception("Aperturar Cuenta: error de programacion, faltan datos");
        }else{ 
            sQuery = "select * from modificaCuentaparaCancelar('"+this.sUsuarioFirmado+"',"+foliopac+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNumCta()+"');";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                afec=(((Double)vRowTemp.get( 0 )).intValue());
            }
        } 
        return afec;
    }
    
    public boolean buscaCuenta() throws Exception{
    ArrayList rst = null;
    String sQuery = ""; 
    SimpleDateFormat df; 

		sQuery = "SELECT * FROM buacaCuentaPac( "+this.getEpisodio().getPaciente().getFolioPaciente()+"::BIGINT,"+this.getEpisodio().getClaveEpisodio()+"::BIGINT);";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0); 
                    this.setIdInternoCta(((Double) vRowTemp.get(0)).intValue());
                    this.setNumCta(((Double) vRowTemp.get(1)).intValue()); 
                    this.getTipoExento().setTipoParametro((String)vRowTemp.get(2));
                    this.getTipoExento().setClaveParametro((String)vRowTemp.get(3)); 
                    this.setMontoSubsidio(BigDecimal.valueOf(((Double) vRowTemp.get(4)).intValue()));
                    this.setMontoCubiertoSeguroPopular(BigDecimal.valueOf(((Double) vRowTemp.get(5)).intValue()));
                    this.setMontoCubiertoProgramas(BigDecimal.valueOf(((Double) vRowTemp.get(6)).intValue()));
                    this.setMontoValeAdeudo(BigDecimal.valueOf(((Double) vRowTemp.get(7)).intValue()));
                    this.getCapturadoPor().setNoTarjeta(((Double) vRowTemp.get(8)).intValue()); 
                    this.setFechaApertura((Date)vRowTemp.get(9));
                    this.getEpisodio().setClaveEpisodio(((Double) vRowTemp.get(11)).intValue());
                    this.getEpisodio().getPaciente().setFolioPaciente(((Double) vRowTemp.get(10)).intValue());
                    return true;
}
                else{
                    return false;
                }
 
    }  
        
    
    public Date getFechaApertura() {
	return dFechaApertura;
    }

    public void setFechaApertura(Date valor) {
	dFechaApertura=valor;
    }

    public long getIdInternoCta() {
    return nIdInternoCta;
    }

    public void setIdInternoCta(long valor) {
    nIdInternoCta=valor;
    }

    public BigDecimal getMontoCubiertoProgramas() {
    return nMontoCubiertoProgramas;
    }

    public void setMontoCubiertoProgramas(BigDecimal valor) {
    nMontoCubiertoProgramas=valor;
    }

    public BigDecimal getMontoCubiertoSeguroPopular() {
    return nMontoCubiertoSeguroPopular;
    }

    public void setMontoCubiertoSeguroPopular(BigDecimal valor) {
    nMontoCubiertoSeguroPopular=valor;
    }

    public BigDecimal getMontoPendiente() {
    return nMontoPendiente;
    }

    public void setMontoPendiente(BigDecimal valor) {
    nMontoPendiente=valor;
    }

    public BigDecimal getMontoRecuperado() {
    return nMontoRecuperado;
    }

    public void setMontoRecuperado(BigDecimal valor) {
    nMontoRecuperado=valor;
    }

    public BigDecimal getMontoSubsidio() {
    return nMontoSubsidio;
    }

    public void setMontoSubsidio(BigDecimal valor) {
    nMontoSubsidio=valor;
    }

    public BigDecimal getMontoValeAdeudo() {
    return nMontoValeAdeudo;
    }

    public void setMontoValeAdeudo(BigDecimal valor) {
    nMontoValeAdeudo=valor;
    }

    public int getNumCta() {
    return nNumCta;
    }

    public void setNumCta(int valor) {
    nNumCta=valor;
    }

    public BigDecimal getTotalPorServicios() {
    return nTotalPorServicios;
    }

    public void setTotalPorServicios(BigDecimal valor) {
    nTotalPorServicios=valor;
    }

    public PersonalHospitalario getAutorizadoPor() {
    return oAutorizadoPor;
    }

    public void setAutorizadoPor(PersonalHospitalario valor) {
    oAutorizadoPor=valor;
    }

    public PersonalHospitalario getCapturadoPor() {
    return oCapturadoPor;
    }

    public void setCapturadoPor(PersonalHospitalario valor) {
    oCapturadoPor=valor;
    }

    public EpisodioMedico getEpisodio() {
    return oEpisodio;
    }

    public void setEpisodio(EpisodioMedico valor) {
    oEpisodio=valor;
    }

    public Parametrizacion getTipoExento() {
    return oTipoExento;
    }

    public void setTipoExento(Parametrizacion valor) {
    oTipoExento=valor;
    }

    public String getRutaArchFact() {
    return sRutaArchFact;
    }

    public void setRutaArchFact(String valor) {
    sRutaArchFact=valor;
    }

    public AutorizacionPago[] getAutorizacionesPago() {
        return arrAutorizacionesPago;
    }

    public void setAutorizacionesPago(AutorizacionPago[] arrAutorizacionesPago) {
        this.arrAutorizacionesPago = arrAutorizacionesPago;
    }

    public Recibo[] getRecibos() {
        return arrRecibos;
    }

    public void setRecibos(Recibo[] arrRecibos) {
        this.arrRecibos = arrRecibos;
    }

    public ServicioRealizado[] getServiciosRealizados() {
        return arrServiciosReallzados;
    }

    public void setServiciosRealizados(ServicioRealizado[] arrServiciosReallzados) {
        this.arrServiciosReallzados = arrServiciosReallzados;
    }
    
    //Por EL
    public int getFolioFin() {
	return FOLIO_FIN;
    }

    public int getFolioIni() {
	return FOLIO_INI;
    }


} 
