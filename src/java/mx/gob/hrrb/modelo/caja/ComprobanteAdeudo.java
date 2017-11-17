package mx.gob.hrrb.modelo.caja;

import mx.gob.hrrb.modelo.core.PersonalHospitalario;
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

public  class ComprobanteAdeudo extends Recibo implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private static int nFolioFinRepartir;
	private static int nFolioIniRepartir;
	private PersonalHospitalario oAutorizo;
	private PersonalHospitalario oElaboro;
	private String sDomicilioAval;
	private String sNombreAval;
	private CuotaRecuperacion oCuotaRecuperacion;

	public ComprobanteAdeudo(){
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
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("ComprobanteAdeudo.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveComprobanteAdeudo();"; 
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
        @Override
	public ComprobanteAdeudo[] buscarTodos() throws Exception{
	ComprobanteAdeudo arrRet[]=null, oComprobanteAdeudo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosComprobanteAdeudo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ComprobanteAdeudo[rst.size()];
			for (i = 0; i < rst.size(); i++) {
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
			throw new Exception("ComprobanteAdeudo.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaComprobanteAdeudo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ComprobanteAdeudo.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaComprobanteAdeudo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ComprobanteAdeudo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaComprobanteAdeudo('"+sUsuarioFirmado+"');"; 
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
	public int getFolioFinRepartir() {
	return nFolioFinRepartir;
	}

	public void setFolioFinRepartir(int valor) {
	nFolioFinRepartir=valor;
	}

	public int getFolioIniRepartir() {
	return nFolioIniRepartir;
	}

	public void setFolioIniRepartir(int valor) {
	nFolioIniRepartir=valor;
	}

	public PersonalHospitalario getAutorizo() {
	return oAutorizo;
	}

	public void setAutorizo(PersonalHospitalario valor) {
	oAutorizo=valor;
	}

	public PersonalHospitalario getElaboro() {
	return oElaboro;
	}

	public void setElaboro(PersonalHospitalario valor) {
	oElaboro=valor;
	}

	public String getDomicilioAval() {
	return sDomicilioAval;
	}

	public void setDomicilioAval(String valor) {
	sDomicilioAval=valor;
	}

	public String getNombreAval() {
	return sNombreAval;
	}

	public void setNombreAval(String valor) {
	sNombreAval=valor;
	}

} 
