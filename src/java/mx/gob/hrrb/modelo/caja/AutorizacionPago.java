package mx.gob.hrrb.modelo.caja;

import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class AutorizacionPago implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaAutorizacion;
	private Date dFechaPago;
	private BigDecimal nMonto;
	private BigDecimal nMontoSubsidio;
	private PersonalHospitalario oQuienAutorizaPago;
	private PersonalHospitalario oQuienCapturaAutPago;
	private PersonalHospitalario oQuienTramitaPago;
	private Parametrizacion oTipoPago;
	private Cuenta oCuenta;
        private ServicioRealizado oServicioRealizado;

	public AutorizacionPago(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AutorizacionPago.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAutorizacionPago();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				bRet = true;
			}
		} 
		return bRet; 
	} 
        
        public int insertar(String sUsuario,float monto, float montosub,String sparamTab , String sparamval, String fechAuto, String fechaPag, int numerTarjetaAut, int numerTarjetacapt, long identifCuentaInterna) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AutorizacionPago.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * from insertaAutorizacionPago("+isNull(sUsuario)+","+floatnull(monto)+","+floatnull(montosub)+","+isNull(sparamTab) +","+ isNull(sparamval)+","+isNull(fechAuto)+","+isNull(fechaPag)+","+numnull(numerTarjetaAut)+","+numnull(numerTarjetacapt)+","+numnull(identifCuentaInterna)+");";  
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
      
        
	public AutorizacionPago[] buscarTodos() throws Exception{
	AutorizacionPago arrRet[]=null, oAutorizacionPago=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAutorizacionPago();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AutorizacionPago[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	
        public AutorizacionPago[] buscarAnticipos(int nTipoAnt,Date dFechaI,Date dFechaF) throws Exception{
	AutorizacionPago arrRet[]=null, oAutorizacionPago=null;
        ServicioRealizado arrServiciosReallzados[] =null;
        Recibo arrRecibos[];
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sQuery = "SELECT * FROM buscaAnticipos("+nTipoAnt+"::smallint,'"+format.format(dFechaI)+"'::date,'"+format.format(dFechaF)+"'::date);"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AutorizacionPago[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oAutorizacionPago=new AutorizacionPago();
                            arrRecibos=new Recibo[1];
                            arrServiciosReallzados=new ServicioRealizado[1];
                            oAutorizacionPago.setCuenta(new Cuenta());
                            oAutorizacionPago.getCuenta().setEpisodio(new EpisodioMedico());
                            oAutorizacionPago.getCuenta().getEpisodio().setPaciente(new Paciente());
                            oAutorizacionPago.getCuenta().setRecibos(new Recibo[1]);
                            oAutorizacionPago.getCuenta().setServiciosRealizados(new ServicioRealizado[1]);
                            arrRecibos[0].setCajaEmite(new Caja());
                            arrRecibos[0].getCajaEmite().setTipoCaja(new Parametrizacion());
                            arrServiciosReallzados[0].setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAutorizacionPago.setFechaPago((Date) vRowTemp.get(0));
                            oAutorizacionPago.getCuenta().getEpisodio().getPaciente().setNombres((String) vRowTemp.get(1));
                            oAutorizacionPago.getCuenta().getEpisodio().getPaciente().setApPaterno((String) vRowTemp.get(2));
                            oAutorizacionPago.getCuenta().getEpisodio().getPaciente().setApMaterno((String) vRowTemp.get(3));
                            arrRecibos[0].setFolio(((Double) vRowTemp.get(4)).intValue());
                            arrServiciosReallzados[0].getServicioCobrable().setDescripcion((String) vRowTemp.get(5));
                            oAutorizacionPago.setMonto((BigDecimal) vRowTemp.get(6));
                            arrRecibos[0].getCajaEmite().getTipoCaja().setValor((String) vRowTemp.get(7));
                            oAutorizacionPago.getCuenta().setRecibos(arrRecibos);
                            oAutorizacionPago.getCuenta().setServiciosRealizados(arrServiciosReallzados);
                            arrRet[i]=oAutorizacionPago;
			} 
		} 
		return arrRet; 
	} 
        
        public int insertaSobreServicio(String sUsuario) throws Exception{
        SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date now = new Date();
        String strDate = SF.format(now);
        ArrayList rst = null;
	int nRet = 0;
	String sQuery = ""; 
            if(sUsuario==null || sUsuario.equals("")){   //completar llave
                throw new Exception("AutorizacionPago.insertar: error de programación, faltan datos");
            }else{ 
                sQuery = "select * from insertaAutorizacionPagoSobreServicio('"+
                        sUsuario+"',"+this.getMonto()+","+
                        this.numnull(this.getMontoSubsidio().longValue())+","+
                        this.isNull(this.getTipoPago().getTipoParametro())+","+
                        this.isNull(this.getTipoPago().getClaveParametro())+",'"+
                        strDate+"',"+getQuienAutorizaPago().getNoTarjeta()+","+
                        this.numnull(this.getQuienCapturaAutPago().getNoTarjeta())+","+
                        this.getServicioRealizado().getIdentificador()+","+floatlong(this.getQuienTramitaPago().getNoTarjeta())+");";  

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
        
        public int modificaServicioTipoPago(String sUsuario, 
                ServicioRealizado oSerRel, String tipopag) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
            if( oSerRel==null){   //completar llave
                    throw new Exception("AutorizacionPago.insertar: error de programación, faltan datos");
            }else{ 
                sQuery = "select * from modificaServicioRealizadTipoPag('"+sUsuario+"', "+ 
                        oSerRel.getIdentificador()+", '"+tipopag+"');";  
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
      
        public int modificarCostoUni(String sUsuario, int idSerReal, 
                String fechAuto, short costouni, int numeroAut) throws Exception{
        int Resul=0; 
        ArrayList rst = null;
        String sQuery = ""; 
        
            if( idSerReal==0){   //completar llave
                throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
            }else{ 
                sQuery = "select * from modificaServicioRealCostUni('"+sUsuario+"', "+
                        idSerReal +", '"+fechAuto+"',"+costouni+", "+numeroAut+");";  
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
                }
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    Resul=(((Double)vRowTemp.get( 0 )).intValue());
                }
            } 
            return Resul; 
        }
           
        public int modificarServicoTipoPagoAntici(String sUsuario, 
                int identiSerVi, String TipPag, String Fecha) throws Exception{
        int Resul=0; 
        ArrayList rst = null;
        String sQuery = "";
            if(identiSerVi==0){   //completar llave
                throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
            }else{ 
                sQuery = "select * from modificaServicioRealizadTipoPag('"+sUsuario+"', "+identiSerVi+", '"+TipPag+"', '"+Fecha+"');";  
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
                }
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    Resul=(((Double)vRowTemp.get( 0 )).intValue());
                }
            } 
            return Resul;
        }  
        
        public int insertAutorizacionesDePagoServ(String idServ) throws Exception{
        SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date now = new Date();
        String strDate = SF.format(now);
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
            if(idServ==null){   //completar llave
                throw new Exception("AutorizacionPago.insertAutorizacionesDePagoServ: error de programación, faltan datos");
            }else{ 
                sQuery = "select * from insertaAutorizacionPagoServicio('"+
                        this.oQuienCapturaAutPago.getUsuar().getIdUsuario()+"',"+
                        this.getMonto()+","+this.getMontoSubsidio()+", '"+
                        this.getTipoPago().getClaveParametro()+"', '"+
                        this.getTipoPago().getTipoParametro()+"', '"+
                        strDate+"', "+this.getQuienAutorizaPago().getNoTarjeta()+
                        ", "+this.getQuienCapturaAutPago().getNoTarjeta()+
                        ", NULL, ARRAY["+idServ+"]::bigint[]);";  
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
        
        
	public Date getFechaAutorizacion() {
	return dFechaAutorizacion;
	}

	public void setFechaAutorizacion(Date valor) {
	dFechaAutorizacion=valor;
	}

	public Date getFechaPago() {
	return dFechaPago;
	}

	public void setFechaPago(Date valor) {
	dFechaPago=valor;
	}

	public BigDecimal getMonto() {
	return nMonto;
	}

	public void setMonto(BigDecimal valor) {
	nMonto=valor;
	}

	public BigDecimal getMontoSubsidio() {
	return nMontoSubsidio;
	}

	public void setMontoSubsidio(BigDecimal valor) {
	nMontoSubsidio=valor;
	}

	public PersonalHospitalario getQuienAutorizaPago() {
	return oQuienAutorizaPago;
	}

	public void setQuienAutorizaPago(PersonalHospitalario valor) {
	oQuienAutorizaPago=valor;
	}

	public PersonalHospitalario getQuienCapturaAutPago() {
	return oQuienCapturaAutPago;
	}

	public void setQuienCapturaAutPago(PersonalHospitalario valor) {
	oQuienCapturaAutPago=valor;
	}

	public PersonalHospitalario getQuienTramitaPago() {
	return oQuienTramitaPago;
	}

	public void setQuienTramitaPago(PersonalHospitalario valor) {
	oQuienTramitaPago=valor;
	}

	public Parametrizacion getTipoPago() {
	return oTipoPago;
	}

	public void setTipoPago(Parametrizacion valor) {
	oTipoPago=valor;
	}
        
    public Cuenta getCuenta() {
        return oCuenta;
    }

    public void setCuenta(Cuenta oCuenta) {
        this.oCuenta = oCuenta;
    }

    public ServicioRealizado getServicioRealizado() {
        return oServicioRealizado;
    }

    public void setServicioRealizado(ServicioRealizado oServicioRealizado) {
        this.oServicioRealizado = oServicioRealizado;
    }
    
    public String isNull(String param){
        if( param==null||param.equals("") || param.isEmpty())
            param=null;
        else
            param="'"+param.trim()+"'";
        return param;
    }
    
    public String numnull(long num){
        if(num==0){
            return null;
        }
        else{
            return ""+num;
        }
    } 

    public String floatnull(float num){
        if(num==0){
            return null;
        }
        else{
            return ""+num;
        }    
    } 
    
    public String floatlong(long num){
        if(num==0){
            return null;
        }
        else{
            return ""+num;
        }    
    }
} 
