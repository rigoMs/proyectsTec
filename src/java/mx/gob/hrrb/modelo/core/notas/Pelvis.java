package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.Parametrizacion;
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

public  class Pelvis implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Parametrizacion oValores;
	private String sInferior;
	private String sMedio;
	private String sSuperior;
        
	private ArrayList<SeguimientoTrabajoParto> oSeguimientoTrabajoParto;

	public Pelvis(){
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
			throw new Exception("Pelvis.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePelvis();"; 
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
	public Pelvis[] buscarTodos() throws Exception{
	Pelvis arrRet[]=null, oPelvis=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPelvis();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Pelvis[rst.size()];
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
			throw new Exception("Pelvis.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPelvis('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Pelvis.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPelvis('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Pelvis.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPelvis('"+sUsuarioFirmado+"');"; 
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
	public Parametrizacion getValores() {
	return oValores;
	}

	public void setValores(Parametrizacion valor) {
	oValores=valor;
	}

	public String getInferior() {
	return sInferior;
	}

	public void setInferior(String valor) {
	sInferior=valor;
	}

	public String getMedio() {
	return sMedio;
	}

	public void setMedio(String valor) {
	sMedio=valor;
	}

	public String getSuperior() {
	return sSuperior;
	}

	public void setSuperior(String valor) {
	sSuperior=valor;
	}

	public ArrayList<SeguimientoTrabajoParto> getSeguimientoTrabajoParto() {
	return oSeguimientoTrabajoParto;
	}

	public void setSeguimientoTrabajoParto(ArrayList<SeguimientoTrabajoParto> valor) {
	oSeguimientoTrabajoParto=valor;
	}

} 
