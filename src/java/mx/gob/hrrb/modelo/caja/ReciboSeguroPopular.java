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

public  class ReciboSeguroPopular extends Recibo implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private String sIncidencia;


	public ReciboSeguroPopular(){
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
                sQuery = "SELECT * FROM buscaRecibo("+this.getFolio()+"::integer,'"+Parametrizacion.RECIBO_SEGPOP+"'::character(3));"; 
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
            throw new Exception("ReciboSeguroPopular.buscar: error, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM buscaReciboCancelado("+this.getFolio()+"::integer,'"+Parametrizacion.RECIBO_SEGPOP+"'::character(3));"; 
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
	public ReciboSeguroPopular[] buscarTodos() throws Exception{
	ReciboSeguroPopular arrRet[]=null, oReciboSeguroPopular=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosReciboSeguroPopular();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ReciboSeguroPopular[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        
        @Override
        public ReciboSeguroPopular[] buscarFoliosEmitidosTurnoActual(int nIdCaja)throws Exception{
            ReciboSeguroPopular arrRet[]=null, oRSP=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
		sQuery = "SELECT * FROM buscaFoliosEmitidosTurnoActual("+nIdCaja+"::smallint,'"+Parametrizacion.RECIBO_SEGPOP+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ReciboSeguroPopular[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oRSP=new ReciboSeguroPopular();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oRSP.setFolio(((Double) vRowTemp.get(0)).intValue());
                            arrRet[i]=oRSP;
			}
		}
		return arrRet; 
        }
        
        @Override
        public ReciboSeguroPopular[] buscarRecibosCanceladosTurno(Date dFecha,String sCveTurno)throws Exception{
            ReciboSeguroPopular arrRet[]=null, oRSP=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sQuery = "SELECT * FROM buscaRecibosSegPopCanceladosTurno('"+format.format(dFecha)+"'::date,"+this.getCajaCancela().getIdCaja()+"::smallint,'"+sCveTurno+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ReciboSeguroPopular[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oRSP=new ReciboSeguroPopular();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oRSP.setFolio(((Double) vRowTemp.get(0)).intValue());
                            arrRet[i]=oRSP;
			}
		}
		return arrRet; 
        }
        
        @Override
    public ReciboSeguroPopular[] buscarRelacionCuentasVales(Date dFecha)throws Exception{
        ReciboSeguroPopular arrRet[]=null, oRSP=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	sQuery = "SELECT * FROM buscaRelacionCuentasVales('"+format.format(dFecha)+"'::date,'"+Parametrizacion.RECIBO_SEGPOP+"'::character(3));"; 
        oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new ReciboSeguroPopular[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oRSP=new ReciboSeguroPopular();
                oRSP.setCuenta(new Cuenta());
                oRSP.setCajaEmite(new Caja());
                oRSP.getCajaEmite().setTipoCaja(new Parametrizacion());
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                
                oRSP.getCuenta().setNumCta(((Double) vRowTemp.get(0)).intValue());
                oRSP.setFolio(((Double) vRowTemp.get(1)).intValue());
                oRSP.getCajaEmite().getTipoCaja().setTipoParametro((String) vRowTemp.get(2));
                arrRet[i]=oRSP;
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
			throw new Exception("ReciboSeguroPopular.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaReciboSeguroPopular('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ReciboSeguroPopular.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaReciboSeguroPopular('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ReciboSeguroPopular.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaReciboSeguroPopular('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ReciboSeguroPopular.cancelar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM cancelaReciboSeguroPopular('"+sUsuarioFirmado+"'::character varying,"+this.getFolioInterno()+"::bigint,"+this.getFolio()+"::integer,'"+this.getIncidencia()+"'::character varying,"+this.getCajaCancela().getIdCaja()+"::smallint);"; 
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
            throw new Exception("ReciboSeguroPopular.cancelar: error, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM cancelaReciboSeguroPopular('"+sUsuarioFirmado+"'::character varying,"+this.getFolioInterno()+"::bigint,"+this.getFolio()+"::integer,'"+this.getIncidencia()+"'::character varying,"+this.getCajaCancela().getIdCaja()+"::smallint);"; 
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
    public ReciboSeguroPopular[] buscarCancelados(Date dFechaI,Date dFechaF) throws Exception{
        ReciboSeguroPopular arrRet[]=null, oRSP=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sQuery = "SELECT * FROM buscaRecibosCancelados('"+format.format(dFechaI)+"'::date,'"+format.format(dFechaF)+"'::date,'"+Parametrizacion.RECIBO_SEGPOP+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ReciboSeguroPopular[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oRSP=new ReciboSeguroPopular();
                            oRSP.setCajaCancela(new Caja());
                            oRSP.getCajaCancela().setCorteCaja(new ArrayList());
                            oRSP.getCajaCancela().getCorteCaja().add(new CorteCaja());
                            oRSP.getCajaCancela().getCorteCaja().get(0).setCajero(new PersonalHospitalario());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oRSP.setFolio(((Double) vRowTemp.get(0)).intValue());
                            oRSP.setIncidencia((String)vRowTemp.get(1));
                            oRSP.getCajaCancela().getCorteCaja().get(0).getCajero().setNombres((String)vRowTemp.get(2));
                            oRSP.getCajaCancela().getCorteCaja().get(0).getCajero().setApPaterno((String)vRowTemp.get(3));
                            oRSP.getCajaCancela().getCorteCaja().get(0).getCajero().setApMaterno((String)vRowTemp.get(4));
                            oRSP.setFechaCancelacion((Date) vRowTemp.get(5));
                            arrRet[i]=oRSP;
			} 
		} 
		return arrRet; 
	}
        
        @Override
        public ReciboSeguroPopular[] buscarHistorialTurno() throws Exception{
	ReciboSeguroPopular arrRet[]=null, oRSP=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaHistorialTurno("+this.getCajaEmite().getIdCaja()+"::smallint,'"+Parametrizacion.RECIBO_SEGPOP+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ReciboSeguroPopular[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oRSP=new ReciboSeguroPopular();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oRSP.setFolio(((Double) vRowTemp.get(0)).intValue());
                            oRSP.setMonto(((BigDecimal) vRowTemp.get(1)));
                            arrRet[i]=oRSP;
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
