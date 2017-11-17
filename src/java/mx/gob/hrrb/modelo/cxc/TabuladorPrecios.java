package mx.gob.hrrb.modelo.cxc;

import mx.gob.hrrb.modelo.core.Parametrizacion;
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

public  class TabuladorPrecios implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private BigDecimal nMontoACobrar;
	private Parametrizacion oNivelSocioeconomico;
	private ServicioCobrable oServicioCobrable;

	public TabuladorPrecios(){
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
			throw new Exception("TabuladorPrecios.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveTabuladorPrecios();"; 
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
	public TabuladorPrecios[] buscarTodos() throws Exception{
	TabuladorPrecios arrRet[]=null, oTabuladorPrecios=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosTabuladorPrecios();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new TabuladorPrecios[rst.size()];
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
			throw new Exception("TabuladorPrecios.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaTabuladorPrecios('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("TabuladorPrecios.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaTabuladorPrecios('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("TabuladorPrecios.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaTabuladorPrecios('"+sUsuarioFirmado+"');"; 
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
	public BigDecimal getMontoACobrar() {
	return nMontoACobrar;
	}

	public void setMontoACobrar(BigDecimal valor) {
	nMontoACobrar=valor;
	}

	public Parametrizacion getNivelSocioeconomico() {
	return oNivelSocioeconomico;
	}

	public void setNivelSocioeconomico(Parametrizacion valor) {
	oNivelSocioeconomico=valor;
	}

	public ServicioCobrable getServicioCobrable() {
	return oServicioCobrable;
	}

	public void setServicioCobrable(ServicioCobrable valor) {
	oServicioCobrable=valor;
	}

} 
