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

public  class NotaEgreso extends NotaMedicaHRRB implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bAtencionFactoresRiesgo;
	private boolean bReingresoMismaAfeccion;
	private boolean bSolicitoNecroscopia;

	public NotaEgreso(){
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
			throw new Exception("NotaEgreso.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveNotaEgreso();"; 
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
	public NotaEgreso[] buscarTodos() throws Exception{
	NotaEgreso arrRet[]=null, oNotaEgreso=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosNotaEgreso();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new NotaEgreso[rst.size()];
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
			throw new Exception("NotaEgreso.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaNotaEgreso('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaEgreso.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaNotaEgreso('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaEgreso.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaNotaEgreso('"+sUsuarioFirmado+"');"; 
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
	public boolean getAtencionFactoresRiesgo() {
	return bAtencionFactoresRiesgo;
	}

	public void setAtencionFactoresRiesgo(boolean valor) {
	bAtencionFactoresRiesgo=valor;
	}

	public boolean getReingresoMismaAfeccion() {
	return bReingresoMismaAfeccion;
	}

	public void setReingresoMismaAfeccion(boolean valor) {
	bReingresoMismaAfeccion=valor;
	}

	public boolean getSolicitoNecroscopia() {
	return bSolicitoNecroscopia;
	}

	public void setSolicitoNecroscopia(boolean valor) {
	bSolicitoNecroscopia=valor;
	}

} 
