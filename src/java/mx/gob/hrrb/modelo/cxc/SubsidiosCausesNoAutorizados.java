package mx.gob.hrrb.modelo.cxc;

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

public  class SubsidiosCausesNoAutorizados implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private float nPctCubreHRRB;
	private ServicioCobrable oServicioCobrable;

	public SubsidiosCausesNoAutorizados(){
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
			throw new Exception("SubsidiosCausesNoAutorizados.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSubsidiosCausesNoAutorizados();"; 
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
	public SubsidiosCausesNoAutorizados[] buscarTodos() throws Exception{
	SubsidiosCausesNoAutorizados arrRet[]=null, oSubsidiosCausesNoAutorizados=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSubsidiosCausesNoAutorizados();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new SubsidiosCausesNoAutorizados[rst.size()];
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
			throw new Exception("SubsidiosCausesNoAutorizados.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSubsidiosCausesNoAutorizados('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("SubsidiosCausesNoAutorizados.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSubsidiosCausesNoAutorizados('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("SubsidiosCausesNoAutorizados.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSubsidiosCausesNoAutorizados('"+sUsuarioFirmado+"');"; 
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
	public float getPctCubreHRRB() {
	return nPctCubreHRRB;
	}

	public void setPctCubreHRRB(float valor) {
	nPctCubreHRRB=valor;
	}

	public ServicioCobrable getServicioCobrable() {
	return oServicioCobrable;
	}

	public void setServicioCobrable(ServicioCobrable valor) {
	oServicioCobrable=valor;
	}

} 
