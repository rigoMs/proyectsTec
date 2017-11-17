package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.NotaMedicaHRRB;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class IndicacionesMedicas extends NotaMedicaHRRB implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private String sCuidadosGeneralesDesc;
	private String sCuidadosGeneralesDura;
	private String sCuidadosGeneralesPerio;

	public IndicacionesMedicas(){
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
			throw new Exception("IndicacionesMedicas.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveIndicacionesMedicas();"; 
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
	public IndicacionesMedicas[] buscarTodos() throws Exception{
	IndicacionesMedicas arrRet[]=null, oIndicacionesMedicas=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosIndicacionesMedicas();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new IndicacionesMedicas[rst.size()];
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
			throw new Exception("IndicacionesMedicas.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaIndicacionesMedicas('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("IndicacionesMedicas.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaIndicacionesMedicas('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("IndicacionesMedicas.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaIndicacionesMedicas('"+sUsuarioFirmado+"');"; 
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
	public String getCuidadosGeneralesDesc() {
	return sCuidadosGeneralesDesc;
	}

	public void setCuidadosGeneralesDesc(String valor) {
	sCuidadosGeneralesDesc=valor;
	}

	public String getCuidadosGeneralesDura() {
	return sCuidadosGeneralesDura;
	}

	public void setCuidadosGeneralesDura(String valor) {
	sCuidadosGeneralesDura=valor;
	}

	public String getCuidadosGeneralesPerio() {
	return sCuidadosGeneralesPerio;
	}

	public void setCuidadosGeneralesPerio(String valor) {
	sCuidadosGeneralesPerio=valor;
	}

} 
