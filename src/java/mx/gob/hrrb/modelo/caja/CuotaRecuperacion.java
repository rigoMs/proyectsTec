package mx.gob.hrrb.modelo.caja;

import java.io.File;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.DetalleRecetarioColectivo;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Pais;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class CuotaRecuperacion extends Recibo implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private Date dFechaAutReintegro;
private PersonalHospitalario oQuienAutorizaReintegro;
private PersonalHospitalario oQuienCapturaAutReintegro;
private PersonalHospitalario oQuienTramitaReintegro;
private String sIncidencia;
private BigDecimal nMontoReintegro;
private String sRutaArchFactXML;
private String sRutaArchFactPDF;
public static final String RUTA_XML_UP=File.separator+"resources"+File.separator+"facturas"+File.separator+"xml"+File.separator;
public static final String RUTA_PDF_UP=File.separator+"resources"+File.separator+"facturas"+File.separator+"pdf"+File.separator;
public static final String RUTA_XML_DOWN="/resources/facturas/xml/";
public static final String RUTA_PDF_DOWN="/resources/facturas/pdf/";
//////////////////Reportes
private String sDescripcion;

    public CuotaRecuperacion(){
    HttpServletRequest req;
            req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                    sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
            }
    }
    @Override
    public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
        ServicioRealizado oSR=null;
        ServicioRealizado arrSR[]=null;
        int i=0;
	String sQuery = "";
        if( this.getFolio()==0){
            throw new Exception("CuotaRecuperacion.buscar: error, faltan datos");
	}
        else{ 
            sQuery = "SELECT * FROM buscaRecibo("+this.getFolio()+"::integer,'"+Parametrizacion.RECIBO_CUOTAS+"'::character(3));"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            } 
            if (rst != null && rst.size()>0) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                AutorizacionPago arrAP[]=null;
                AutorizacionPago oAp=null;
                arrAP=new AutorizacionPago[1];
                oAp=new AutorizacionPago();
                this.setCuenta(new Cuenta());
                this.getCuenta().setEpisodio(new EpisodioMedico());
                this.getCuenta().getEpisodio().setPaciente(new Paciente());
                this.getCuenta().getEpisodio().getPaciente().setCiudad(new Ciudad());
                this.getCuenta().getEpisodio().getPaciente().setMunicipio(new Municipio());
                this.getCuenta().getEpisodio().getPaciente().setEstado(new Estado());
                this.getCuenta().getEpisodio().getPaciente().getEstado().setPais(new Pais());
                this.getCuenta().getEpisodio().getPaciente().setExpediente(new Expediente());
                this.getCuenta().getEpisodio().getPaciente().setNivelSocioEco(new Parametrizacion());
                
                this.setFolioInterno((long)((Double)vRowTemp.get(0)).intValue());
                this.setFolio(((Double)vRowTemp.get(1)).intValue());
                this.setFechaEmision((Date)vRowTemp.get(2));
                this.getCuenta().getEpisodio().getPaciente().setNombres((String)vRowTemp.get(3));
                this.getCuenta().getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(4));
                this.getCuenta().getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(5));
                this.getCuenta().getEpisodio().getPaciente().setCalleNum((String)vRowTemp.get(6));
                this.getCuenta().getEpisodio().getPaciente().setColonia((String)vRowTemp.get(7));
                this.getCuenta().getEpisodio().getPaciente().setCodigoPos((String)vRowTemp.get(8));
                this.getCuenta().getEpisodio().getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(9));
                this.getCuenta().getEpisodio().getPaciente().getMunicipio().setDescripcionMun((String)vRowTemp.get(10));
                this.getCuenta().getEpisodio().getPaciente().getEstado().setDescripcionEdo((String)vRowTemp.get(11));
                this.getCuenta().getEpisodio().getPaciente().getEstado().getPais().setDescripcionPais((String)vRowTemp.get(12));
                this.getCuenta().getEpisodio().getPaciente().setOtroPais((String)vRowTemp.get(13));
                this.getCuenta().getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(14)).intValue());
                oAp.setMontoSubsidio((BigDecimal.valueOf((Double)vRowTemp.get(18))));
                arrAP[0]=oAp;
                this.getCuenta().setAutorizacionesPago(arrAP);
                this.getCuenta().getEpisodio().getPaciente().getNivelSocioEco().setTipoParametro((String)vRowTemp.get(19));
                this.setMonto((BigDecimal.valueOf((Double)vRowTemp.get(20))));
                arrSR=new ServicioRealizado[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    vRowTemp = (ArrayList)rst.get(i);
                    oSR=new DetalleRecetarioColectivo();
                    oSR.setServicioCobrable(new Medicamento());
                    oSR.getServicioCobrable().setClave((String)vRowTemp.get(15));
                    oSR.getServicioCobrable().setDescripcion((String)vRowTemp.get(16));
                    oSR.setCostoUnitACobrar((BigDecimal.valueOf((Double)vRowTemp.get(17))));
                    arrSR[i]=oSR;
                }
                this.setServiciosCubiertos(arrSR);
                bRet = true;
		}
        }
        return bRet; 
    }
    
    public boolean buscarReintegro() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
        ServicioRealizado oSR=null;
        ServicioRealizado arrSR[]=null;
        int i=0;
	String sQuery = "";
        if( this.getFolio()==0){
            throw new Exception("CuotaRecuperacion.buscar: error, faltan datos");
	}
        else{ 
            sQuery = "SELECT * FROM buscaReciboReintegrado("+this.getFolio()+"::integer);"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            } 
            if (rst != null && rst.size()>0) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                AutorizacionPago arrAP[]=null;
                AutorizacionPago oAp=null;
                arrAP=new AutorizacionPago[1];
                oAp=new AutorizacionPago();
                this.setCuenta(new Cuenta());
                this.getCuenta().setEpisodio(new EpisodioMedico());
                this.getCuenta().getEpisodio().setPaciente(new Paciente());
                this.getCuenta().getEpisodio().getPaciente().setCiudad(new Ciudad());
                this.getCuenta().getEpisodio().getPaciente().setMunicipio(new Municipio());
                this.getCuenta().getEpisodio().getPaciente().setEstado(new Estado());
                this.getCuenta().getEpisodio().getPaciente().getEstado().setPais(new Pais());
                this.getCuenta().getEpisodio().getPaciente().setExpediente(new Expediente());
                this.getCuenta().getEpisodio().getPaciente().setNivelSocioEco(new Parametrizacion());
                
                this.setFolioInterno((long)((Double)vRowTemp.get(0)).intValue());
                this.setFolio(((Double)vRowTemp.get(1)).intValue());
                this.setFechaEmision((Date)vRowTemp.get(2));
                this.getCuenta().getEpisodio().getPaciente().setNombres((String)vRowTemp.get(3));
                this.getCuenta().getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(4));
                this.getCuenta().getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(5));
                this.getCuenta().getEpisodio().getPaciente().setCalleNum((String)vRowTemp.get(6));
                this.getCuenta().getEpisodio().getPaciente().setColonia((String)vRowTemp.get(7));
                this.getCuenta().getEpisodio().getPaciente().setCodigoPos((String)vRowTemp.get(8));
                this.getCuenta().getEpisodio().getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(9));
                this.getCuenta().getEpisodio().getPaciente().getMunicipio().setDescripcionMun((String)vRowTemp.get(10));
                this.getCuenta().getEpisodio().getPaciente().getEstado().setDescripcionEdo((String)vRowTemp.get(11));
                this.getCuenta().getEpisodio().getPaciente().getEstado().getPais().setDescripcionPais((String)vRowTemp.get(12));
                this.getCuenta().getEpisodio().getPaciente().setOtroPais((String)vRowTemp.get(13));
                this.getCuenta().getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(14)).intValue());
                oAp.setMontoSubsidio((BigDecimal.valueOf((Double)vRowTemp.get(18))));
                arrAP[0]=oAp;
                this.getCuenta().setAutorizacionesPago(arrAP);
                this.getCuenta().getEpisodio().getPaciente().getNivelSocioEco().setTipoParametro((String)vRowTemp.get(19));
                this.setMonto((BigDecimal.valueOf((Double)vRowTemp.get(20))));
                arrSR=new ServicioRealizado[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    vRowTemp = (ArrayList)rst.get(i);
                    oSR=new DetalleRecetarioColectivo();
                    oSR.setSitPago(new Parametrizacion());
                    oSR.setServicioCobrable(new Medicamento());
                    oSR.getServicioCobrable().setClave((String)vRowTemp.get(15));
                    oSR.getServicioCobrable().setDescripcion((String)vRowTemp.get(16));
                    oSR.setCostoUnitACobrar((BigDecimal.valueOf((Double)vRowTemp.get(17))));
                    oSR.getSitPago().setTipoParametro(((String)vRowTemp.get(21)).trim());
                    if(rst.size()==1){
                       oSR.setCostoUnitACobrar((BigDecimal.valueOf((Double)vRowTemp.get(20)))); 
                    }
                    arrSR[i]=oSR;
                }
                this.setServiciosCubiertos(arrSR);
                bRet = true;
		}
        }
        return bRet; 
    }
        
    @Override
    public boolean buscarReciboCancelado() throws Exception{
        boolean bRet = false;
        ArrayList rst = null;
	String sQuery = "";
        int i=0;
        if(this.getFolio()==0){
            throw new Exception("ComprobanteExcepcion.buscar: error, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM buscaReciboCancelado("+this.getFolio()+"::integer,'"+Parametrizacion.RECIBO_CUOTAS+"'::character(3));"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
		rst = oAD.ejecutarConsulta(sQuery); 
		oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
		ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setIncidencia((String) vRowTemp.get(0));
                this.setFechaCancelacion((Date) vRowTemp.get(1));
                this.setFechaAutReintegro((Date) vRowTemp.get(2));
                bRet = true;
            }
        }
            return bRet; 
    }
        
        @Override
	public CuotaRecuperacion[] buscarTodos() throws Exception{
	CuotaRecuperacion arrRet[]=null, oCuotaRecuperacion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCuotaRecuperacion();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CuotaRecuperacion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        
        @Override
        public CuotaRecuperacion[] buscarFoliosEmitidosTurnoActual(int nIdCaja)throws Exception{
            CuotaRecuperacion arrRet[]=null, oCR=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
		sQuery = "SELECT * FROM buscaFoliosEmitidosTurnoActual("+nIdCaja+"::smallint,'"+Parametrizacion.RECIBO_CUOTAS+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CuotaRecuperacion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oCR=new CuotaRecuperacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCR.setFolio(((Double) vRowTemp.get(0)).intValue());
                            arrRet[i]=oCR;
			} 
		} 
		return arrRet;   
        }
        
    @Override
    public CuotaRecuperacion[] buscarRecibosCanceladosTurno(Date dFecha,String sCveTurno)throws Exception{
            CuotaRecuperacion arrRet[]=null, oCR=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sQuery = "SELECT * FROM buscaRecibosCuotaRecuperacionCanceladosTurno('"+format.format(dFecha)+"'::date,"+this.getCajaCancela().getIdCaja()+"::smallint,'"+sCveTurno+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CuotaRecuperacion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oCR=new CuotaRecuperacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCR.setFolio(((Double) vRowTemp.get(0)).intValue());
                            arrRet[i]=oCR;
			} 
		} 
		return arrRet;   
    }
        
    public CuotaRecuperacion[] buscarRecibosPaciente(long nFolioPaciente)throws Exception{
            CuotaRecuperacion arrRet[]=null, oCR=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
		sQuery = "SELECT * FROM buscaRecibosPaciente("+nFolioPaciente+"::bigint);"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CuotaRecuperacion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oCR=new CuotaRecuperacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCR.setFolioInterno((long)((Double)vRowTemp.get(0)).intValue());
                            oCR.setFolio(((Double) vRowTemp.get(1)).intValue());
                            oCR.setFechaEmision((Date)vRowTemp.get(2));
                            oCR.setMonto((BigDecimal.valueOf((Double)vRowTemp.get(3))));
                            arrRet[i]=oCR;
			} 
		} 
	return arrRet;   
    }
        
    @Override
    public CuotaRecuperacion[] buscarRelacionCuentasVales(Date dFecha)throws Exception{
        CuotaRecuperacion arrRet[]=null, oCR=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sQuery = "SELECT * FROM buscaRelacionCuentasVales('"+format.format(dFecha)+"'::date,'"+Parametrizacion.RECIBO_CUOTAS+"'::character(3));"; 
	oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new CuotaRecuperacion[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oCR=new CuotaRecuperacion();
                oCR.setCuenta(new Cuenta());
                oCR.setCajaEmite(new Caja());
                oCR.getCajaEmite().setTipoCaja(new Parametrizacion());
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                
                oCR.getCuenta().setNumCta(((Double) vRowTemp.get(0)).intValue());
                oCR.setFolio(((Double) vRowTemp.get(1)).intValue());
                oCR.getCajaEmite().getTipoCaja().setTipoParametro((String) vRowTemp.get(2));
                arrRet[i]=oCR;
            } 
        } 
	return arrRet;   
    }
        
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CuotaRecuperacion.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCuotaRecuperacion('"+sUsuarioFirmado+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)rst.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	} 
        @Override
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CuotaRecuperacion.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCuotaRecuperacion('"+sUsuarioFirmado+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)rst.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	} 
        @Override
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CuotaRecuperacion.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCuotaRecuperacion('"+sUsuarioFirmado+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)rst.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	}
        
        @Override
        public int cancelar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if(this.getFolio()==0||this.getIncidencia()==null||this.getCajaCancela().getIdCaja()==0){
			throw new Exception("CuotaRecuperacion.cancelar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM cancelaCuotaRecuperacion('"+sUsuarioFirmado+"'::character varying,"+this.getFolioInterno()+"::bigint,"+this.getFolio()+"::integer,'"+this.getIncidencia()+"'::character varying,"+this.getCajaCancela().getIdCaja()+"::smallint);"; 
			System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	}
        
        @Override
        public boolean cancelarFolioDisponible(ArrayList<String> vTransac) throws Exception{
        boolean bRet=false;
        String sQuery = "";
        int nAfectados=0;
        if(this.getFolio()==0||this.getIncidencia()==null||this.getCajaCancela().getIdCaja()==0||this.getCajaCancela()==null){
            throw new Exception("CuotaRecuperacion.cancelar: error, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM cancelaCuotaRecuperacion('"+sUsuarioFirmado+"'::character varying,"+this.getFolioInterno()+"::bigint,"+this.getFolio()+"::integer,'"+this.getIncidencia()+"'::character varying,"+this.getCajaCancela().getIdCaja()+"::smallint);"; 
            vTransac.add(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                nAfectados = oAD.ejecutarConsultaComando(vTransac);
                oAD.desconectar(); 
            }
            if(vTransac.size()>=nAfectados){
                bRet=true;
            }
        }
        return bRet;
    }
        
    @Override    
    public CuotaRecuperacion[] buscarCancelados(Date dFechaI,Date dFechaF) throws Exception{
	CuotaRecuperacion arrRet[]=null, oCuotaRecuperacion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sQuery = "SELECT * FROM buscaRecibosCancelados('"+format.format(dFechaI)+"'::date,'"+format.format(dFechaF)+"'::date,'"+Parametrizacion.RECIBO_CUOTAS+"'::character(3));"; 
	oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new CuotaRecuperacion[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oCuotaRecuperacion=new CuotaRecuperacion();
                oCuotaRecuperacion.setCajaCancela(new Caja());
                oCuotaRecuperacion.getCajaCancela().setCorteCaja(new ArrayList());
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().add(new CorteCaja());
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().get(0).setCajero(new PersonalHospitalario());
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oCuotaRecuperacion.setFolio(((Double) vRowTemp.get(0)).intValue());
                oCuotaRecuperacion.setIncidencia((String)vRowTemp.get(1));
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().get(0).getCajero().setNombres((String)vRowTemp.get(2));
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().get(0).getCajero().setApPaterno((String)vRowTemp.get(3));
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().get(0).getCajero().setApMaterno((String)vRowTemp.get(4));
                oCuotaRecuperacion.setFechaCancelacion((Date) vRowTemp.get(5));
                arrRet[i]=oCuotaRecuperacion;
                } 
        } 
	return arrRet; 
    }
        
        
    @Override
    public CuotaRecuperacion[] buscarHistorialTurno() throws Exception{
	CuotaRecuperacion arrRet[]=null, oCuotaRecuperacion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
	sQuery = "SELECT * FROM buscaHistorialTurno("+this.getCajaEmite().getIdCaja()+"::smallint,'"+Parametrizacion.RECIBO_CUOTAS+"'::character(3));"; 
	oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new CuotaRecuperacion[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oCuotaRecuperacion=new CuotaRecuperacion();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oCuotaRecuperacion.setFolio(((Double) vRowTemp.get(0)).intValue());
                oCuotaRecuperacion.setMonto((BigDecimal.valueOf((Double)vRowTemp.get(1))));
                arrRet[i]=oCuotaRecuperacion;
            } 
	} 
        return arrRet; 
    }
        
    public CuotaRecuperacion[] buscarReintegrosRealizados(Date dFechaI,Date dFechaF) throws Exception{
	CuotaRecuperacion arrRet[]=null, oCuotaRecuperacion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sQuery = "SELECT * FROM buscaReintegrosRealizados('"+format.format(dFechaI)+"'::date,'"+format.format(dFechaF)+"'::date);"; 
	oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new CuotaRecuperacion[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oCuotaRecuperacion=new CuotaRecuperacion();
                oCuotaRecuperacion.setCajaCancela(new Caja());
                oCuotaRecuperacion.getCajaCancela().setCorteCaja(new ArrayList());
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().add(new CorteCaja());
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().get(0).setCajero(new PersonalHospitalario());
                oCuotaRecuperacion.setCuenta(new Cuenta());
                oCuotaRecuperacion.getCuenta().setEpisodio(new EpisodioMedico());
                oCuotaRecuperacion.getCuenta().getEpisodio().setPaciente(new Paciente());
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oCuotaRecuperacion.setFechaCancelacion((Date) vRowTemp.get(0));
                oCuotaRecuperacion.setFolio(((Double) vRowTemp.get(1)).intValue());
                oCuotaRecuperacion.setMontoReintegro((BigDecimal) vRowTemp.get(2));
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().get(0).getCajero().setNombres((String) vRowTemp.get(3));
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().get(0).getCajero().setApPaterno((String) vRowTemp.get(4));
                oCuotaRecuperacion.getCajaCancela().getCorteCaja().get(0).getCajero().setApMaterno((String) vRowTemp.get(5));
                oCuotaRecuperacion.getCuenta().getEpisodio().getPaciente().setNombres((String) vRowTemp.get(6));
                oCuotaRecuperacion.getCuenta().getEpisodio().getPaciente().setApPaterno((String) vRowTemp.get(7));
                oCuotaRecuperacion.getCuenta().getEpisodio().getPaciente().setApMaterno((String) vRowTemp.get(8));
                arrRet[i]=oCuotaRecuperacion;
            } 
        } 
        return arrRet; 
    }
       
    public int asociarFacturaCuotaRecuperacion(ArrayList<CuotaRecuperacion> arrCR,String sNombreArch) throws Exception{
        int nRegAfectados=0;
        ArrayList<String> vTransac=new ArrayList<>();
        if(arrCR.isEmpty()){
            throw new Exception("CuotaRecuperacion.AsociarFacturaCuotaRecuperacion: error, faltan datos");
        }
        else{
            for(CuotaRecuperacion cr:arrCR){
                cr.setRutaArchFactXML(sNombreArch+".xml");
                cr.setRutaArchFactPDF(sNombreArch+".pdf");
                vTransac.add(cr.getAsociarFacturaCuotaRecuperacion());
                System.out.println(cr.getAsociarFacturaCuotaRecuperacion());
            }
            oAD=new AccesoDatos();
            if (oAD.conectar()){ 
                nRegAfectados=oAD.ejecutarConsultaComando(vTransac);
                oAD.desconectar(); 
            }
        }
        return nRegAfectados;
    }
    
    public String getAsociarFacturaCuotaRecuperacion() throws Exception{
        String sQuery="";
        if(this.getFolioInterno()==0){
            throw new Exception("CuotaRecuperacion.AsociarFacturaCuotaRecuperacion: error, faltan datos");
        }
        else{
            sQuery="SELECT * FROM asociaFacturaCuotaRecuperacion('"+this.sUsuarioFirmado+"'::character varying,"+this.getFolioInterno()+"::bigint,'"+this.getRutaArchFactXML()+"'::character varying,'"+this.getRutaArchFactPDF()+"'::character varying);";
        }
        return sQuery;
    }
    
    public CuotaRecuperacion[] buscarReintegrosRealizadosTrabSocil(
            Date fechInici,Date fechFinal) throws Exception{
    CuotaRecuperacion arrRet[]=null, oReint=null;
    SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
    String fechainicail =df.format(fechInici);
    String fechaFinal=df.format(fechFinal); 
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
        sQuery = "select * from buscaTodosReintegRealizDep("+
                this.isNull(this.getCuenta().getEpisodio().getPaciente().getNombres().toUpperCase())+","+
                this.isNull(this.getCuenta().getEpisodio().getPaciente().getApPaterno().toUpperCase())+","+
                this.isNull(this.getCuenta().getEpisodio().getPaciente().getApMaterno().toUpperCase())+","+
                this.numnull(this.getCuenta().getEpisodio().getPaciente().getExpediente().getNumero())+",'"+
                fechainicail+"','"+fechaFinal+"');";
                 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null) {
            arrRet = new CuotaRecuperacion[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oReint=new CuotaRecuperacion();
                oReint.setQuienAutorizaReintegro(new PersonalHospitalario());
                oReint.setQuienCapturaAutReintegro(new PersonalHospitalario());
                oReint.setQuienTramitaReintegro(new PersonalHospitalario());
                ArrayList vRowTemp = (ArrayList)rst.get(i); 
                oReint.getCuenta().getEpisodio().getPaciente().setNombres((String)vRowTemp.get(0));
                oReint.getCuenta().getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(1));
                oReint.getCuenta().getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(2));
                oReint.getCuenta().getEpisodio().getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                oReint.setFechaAutReintegro((Date)vRowTemp.get(4));
                oReint.setFolio(((Double) vRowTemp.get(5)).intValue());
                oReint.setDescripcion((String)vRowTemp.get(6));
                oReint.setMontoReintegro(BigDecimal.valueOf(((Double) vRowTemp.get(7)).intValue()));
                oReint.getQuienAutorizaReintegro().setNoTarjeta(((Double) vRowTemp.get(8)).intValue());
                oReint.getQuienCapturaAutReintegro().setNoTarjeta(((Double) vRowTemp.get(9)).intValue());
                oReint.getQuienTramitaReintegro().setNoTarjeta(((Double) vRowTemp.get(10)).intValue()); 
                oReint.getQuienAutorizaReintegro().buscarCapa();
                oReint.getQuienCapturaAutReintegro().buscarCapa();
                oReint.getQuienTramitaReintegro().buscarCapa();
                arrRet[i]=oReint;
            } 
        } 
        return arrRet; 
    }
    
    public CuotaRecuperacion[] buscarReintegrosAutorizados(
            Date fechInici,Date fechFinal) throws Exception{
    CuotaRecuperacion arrRet[]=null, oReint=null;
    SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
    String fechainicail =df.format(fechInici);
    String fechaFinal=df.format(fechFinal); 
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
        sQuery = "select * from buscaTodosReintegAutoriDep("+
                this.isNull(this.getCuenta().getEpisodio().getPaciente().getNombres().toUpperCase())+","+
                this.isNull(this.getCuenta().getEpisodio().getPaciente().getApPaterno().toUpperCase())+","+
                this.isNull(this.getCuenta().getEpisodio().getPaciente().getApMaterno().toUpperCase())+","+
                this.numnull(this.getCuenta().getEpisodio().getPaciente().getExpediente().getNumero())+",'"+
                fechainicail+"','"+fechaFinal+"');";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null) {
            arrRet = new CuotaRecuperacion[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oReint=new CuotaRecuperacion();
                oReint.setQuienAutorizaReintegro(new PersonalHospitalario());
                oReint.setQuienCapturaAutReintegro(new PersonalHospitalario());
                oReint.setQuienTramitaReintegro(new PersonalHospitalario());
                ArrayList vRowTemp = (ArrayList)rst.get(i); 
                oReint.getCuenta().getEpisodio().getPaciente().setNombres((String)vRowTemp.get(0));
                oReint.getCuenta().getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(1));
                oReint.getCuenta().getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(2));
                oReint.getCuenta().getEpisodio().getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                oReint.setFechaAutReintegro((Date)vRowTemp.get(4));
                oReint.setFolio(((Double) vRowTemp.get(5)).intValue());
                oReint.setDescripcion((String)vRowTemp.get(6));
                oReint.setMontoReintegro(BigDecimal.valueOf(((Double) vRowTemp.get(7)).intValue()));
                oReint.getQuienAutorizaReintegro().setNoTarjeta(((Double) vRowTemp.get(8)).intValue());
                oReint.getQuienCapturaAutReintegro().setNoTarjeta(((Double) vRowTemp.get(9)).intValue());
                oReint.getQuienTramitaReintegro().setNoTarjeta(((Double) vRowTemp.get(10)).intValue()); 
                oReint.getQuienAutorizaReintegro().buscarCapa();
                oReint.getQuienCapturaAutReintegro().buscarCapa();
                oReint.getQuienTramitaReintegro().buscarCapa();
                arrRet[i]=oReint;
            } 
        } 
        return arrRet; 
    }
       
       
    public String numnull(int num){
        if(num==0){
            return null;
        }
        else{
            return ""+num;
        }
    }
    
    public String isNull(String param){
        if( param==null||param.equals("") || param.isEmpty())
            param=null;
        else
            param="'"+param.trim()+"'";
        return param;
    }
         
	public Date getFechaAutReintegro() {
	return dFechaAutReintegro;
	}

	public void setFechaAutReintegro(Date valor) {
	dFechaAutReintegro=valor;
	}

	public PersonalHospitalario getQuienAutorizaReintegro() {
	return oQuienAutorizaReintegro;
	}

	public void setQuienAutorizaReintegro(PersonalHospitalario valor) {
	oQuienAutorizaReintegro=valor;
	}

	public PersonalHospitalario getQuienCapturaAutReintegro() {
	return oQuienCapturaAutReintegro;
	}

	public void setQuienCapturaAutReintegro(PersonalHospitalario valor) {
	oQuienCapturaAutReintegro=valor;
	}

	public PersonalHospitalario getQuienTramitaReintegro() {
	return oQuienTramitaReintegro;
	}

	public void setQuienTramitaReintegro(PersonalHospitalario valor) {
	oQuienTramitaReintegro=valor;
	}

	public String getIncidencia() {
	return sIncidencia;
	}

	public void setIncidencia(String valor) {
	sIncidencia=valor;
	}

    public BigDecimal getMontoReintegro() {
        return nMontoReintegro;
    }

    public void setMontoReintegro(BigDecimal nMontoReintegro) {
        this.nMontoReintegro = nMontoReintegro;
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
    
    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion(String sdescripcion) {
        this.sDescripcion = sdescripcion;
    }
} 
