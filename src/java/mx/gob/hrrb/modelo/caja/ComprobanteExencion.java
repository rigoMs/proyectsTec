package mx.gob.hrrb.modelo.caja;

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
import mx.gob.hrrb.modelo.core.OtroServicio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Pais;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.modelo.serv.SolicitudOtroServicio;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class ComprobanteExencion extends Recibo implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private String sIncidencia;

	public ComprobanteExencion(){
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
	String sQuery = "";
        int i=0;
        if( this.getFolio()==0){ 
            throw new Exception("ComprobanteExcepcion.buscar: error, faltan datos");
        }
        else{ 
            sQuery = "SELECT * FROM buscaRecibo("+this.getFolio()+"::integer,'"+Parametrizacion.RECIBO_EXENTO+"'::character(3));"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }                
            if (rst != null && rst.size()>0) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);

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
            sQuery = "SELECT * FROM buscaReciboCancelado("+this.getFolio()+"::integer,'"+Parametrizacion.RECIBO_EXENTO+"'::character(3));"; 
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
                bRet = true;
            }
        }
        return bRet; 
    }
        
        @Override
	public ComprobanteExencion[] buscarTodos() throws Exception{
	ComprobanteExencion[] arrRet = null;
        ComprobanteExencion oComprobanteExencion = null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosComprobanteExcepcion();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ComprobanteExencion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        
        @Override
        public ComprobanteExencion[] buscarFoliosEmitidosTurnoActual(int nIdCaja)throws Exception{
        ComprobanteExencion arrRet[]=null, oCE=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaFoliosEmitidosTurnoActual("+nIdCaja+"::smallint,'"+Parametrizacion.RECIBO_EXENTO+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ComprobanteExencion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oCE=new ComprobanteExencion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCE.setFolio(((Double) vRowTemp.get(0)).intValue());
                            arrRet[i]=oCE;
			} 
		} 
		return arrRet;  
        }
        
    @Override
    public ComprobanteExencion[] buscarRecibosCanceladosTurno(Date dFecha,String sCveTurno)throws Exception{
        ComprobanteExencion arrRet[]=null, oCE=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sQuery = "SELECT * FROM buscaComprobantesExencionCanceladosTurno('"+format.format(dFecha)+"'::date,"+this.getCajaCancela().getIdCaja()+"::smallint,'"+sCveTurno+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ComprobanteExencion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oCE=new ComprobanteExencion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCE.setFolio(((Double) vRowTemp.get(0)).intValue());
                            arrRet[i]=oCE;
			} 
		} 
		return arrRet;  
    }
        
    @Override
    public ComprobanteExencion[] buscarRelacionCuentasVales(Date dFecha)throws Exception{
        ComprobanteExencion arrRet[]=null, oCE=null;
        ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sQuery = "SELECT * FROM buscaRelacionCuentasVales('"+format.format(dFecha)+"'::date,'"+Parametrizacion.RECIBO_EXENTO+"'::character(3));"; 
	oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new ComprobanteExencion[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oCE=new ComprobanteExencion();
                oCE.setCuenta(new Cuenta());
                oCE.setCajaEmite(new Caja());
                oCE.getCajaEmite().setTipoCaja(new Parametrizacion());
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                
                oCE.getCuenta().setNumCta(((Double) vRowTemp.get(0)).intValue());
                oCE.setFolio(((Double) vRowTemp.get(1)).intValue());
                oCE.getCajaEmite().getTipoCaja().setTipoParametro((String) vRowTemp.get(2));
                arrRet[i]=oCE;
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
			throw new Exception("ComprobanteExcepcion.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaComprobanteExcepcion('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ComprobanteExcepcion.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaComprobanteExcepcion('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ComprobanteExcepcion.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaComprobanteExcepcion('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ComprobanteExencion.cancelar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM cancelaComprobanteExencion('"+sUsuarioFirmado+"'::character varying,"+this.getFolioInterno()+"::bigint,"+this.getFolio()+"::integer,'"+this.getIncidencia()+"'::character varying,"+this.getCajaCancela().getIdCaja()+"::smallint);"; 
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
        if(this.getFolio()==0||this.getIncidencia()==null||this.getCajaCancela()==null){
            throw new Exception("ComprobanteExencion.cancelar: error, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM cancelaComprobanteExencion('"+sUsuarioFirmado+"'::character varying,"+this.getFolioInterno()+"::bigint,"+this.getFolio()+"::integer,'"+this.getIncidencia()+"'::character varying,"+this.getCajaCancela().getIdCaja()+"::smallint);"; 
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
    public ComprobanteExencion[] buscarCancelados(Date dFechaI,Date dFechaF) throws Exception{
	ComprobanteExencion arrRet[]=null, oCE=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sQuery = "SELECT * FROM buscaRecibosCancelados('"+format.format(dFechaI)+"'::date,'"+format.format(dFechaF)+"'::date,'"+Parametrizacion.RECIBO_EXENTO+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ComprobanteExencion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oCE=new ComprobanteExencion();
                            oCE.setCajaCancela(new Caja());
                            oCE.getCajaCancela().setCorteCaja(new ArrayList());
                            oCE.getCajaCancela().getCorteCaja().add(new CorteCaja());
                            oCE.getCajaCancela().getCorteCaja().get(0).setCajero(new PersonalHospitalario());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCE.setFolio(((Double) vRowTemp.get(0)).intValue());
                            oCE.setIncidencia((String)vRowTemp.get(1));
                            oCE.getCajaCancela().getCorteCaja().get(0).getCajero().setNombres((String)vRowTemp.get(2));
                            oCE.getCajaCancela().getCorteCaja().get(0).getCajero().setApPaterno((String)vRowTemp.get(3));
                            oCE.getCajaCancela().getCorteCaja().get(0).getCajero().setApMaterno((String)vRowTemp.get(4));
                            oCE.setFechaCancelacion((Date) vRowTemp.get(5));
                            arrRet[i]=oCE;
			} 
		} 
		return arrRet; 
    }
        
    @Override
    public ComprobanteExencion[] buscarHistorialTurno() throws Exception{
	ComprobanteExencion arrRet[]=null, oCE=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaHistorialTurno("+this.getCajaEmite().getIdCaja()+"::smallint,'"+Parametrizacion.RECIBO_EXENTO+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ComprobanteExencion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oCE=new ComprobanteExencion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCE.setFolio(((Double) vRowTemp.get(0)).intValue());
                            oCE.setMonto(((BigDecimal) vRowTemp.get(1)));
                            arrRet[i]=oCE;
			} 
		} 
		return arrRet; 
	}

	public String getIncidencia() {
	return sIncidencia;
	}

	public void setIncidencia(String valor) {
	sIncidencia=valor;
	}

} 
