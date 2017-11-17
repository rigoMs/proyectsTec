package mx.gob.hrrb.modelo.cxc;

import mx.gob.hrrb.modelo.core.OtroServicio;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class TabuladorTraslado implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private BigDecimal nMonto;
	private String sHospital;
	private OtroServicio oOtroServicio;

	public TabuladorTraslado(){
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
			throw new Exception("TabuladorTraslado.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveTabuladorTraslado();"; 
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
	public TabuladorTraslado[] buscarTodos() throws Exception{
	TabuladorTraslado arrRet[]=null, oTabuladorTraslado=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosTabuladorTraslado();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new TabuladorTraslado[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("TabuladorTraslado.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaTabuladorTraslado('"+sUsuarioFirmado+"');"; 
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
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("TabuladorTraslado.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaTabuladorTraslado('"+sUsuarioFirmado+"');"; 
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
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("TabuladorTraslado.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaTabuladorTraslado('"+sUsuarioFirmado+"');"; 
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
	public BigDecimal getMonto() {
	return nMonto;
	}

	public void setMonto(BigDecimal valor) {
	nMonto=valor;
	}

	public String getHospital() {
	return sHospital;
	}

	public void setHospital(String valor) {
	sHospital=valor;
	}

	public OtroServicio getOtroServicio() {
	return oOtroServicio;
	}

	public void setOtroServicio(OtroServicio valor) {
	oOtroServicio=valor;
	}

} 
