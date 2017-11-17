package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class RecetarioColectivo implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaEmision;
	private int nIdHoja;
	private AreaServicioHRRB oAreaServ;
	private Parametrizacion oTipoRecetario;
	private ArrayList<Medicamento> oMedicamento;

	public RecetarioColectivo(){
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
			throw new Exception("RecetarioColectivo.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveRecetarioColectivo();"; 
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
	public RecetarioColectivo[] buscarTodos() throws Exception{
	RecetarioColectivo arrRet[]=null, oRecetarioColectivo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosRecetarioColectivo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new RecetarioColectivo[rst.size()];
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
			throw new Exception("RecetarioColectivo.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaRecetarioColectivo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("RecetarioColectivo.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaRecetarioColectivo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("RecetarioColectivo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaRecetarioColectivo('"+sUsuarioFirmado+"');"; 
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
	public Date getFechaEmision() {
	return dFechaEmision;
	}

	public void setFechaEmision(Date valor) {
	dFechaEmision=valor;
	}

	public int getIdHoja() {
	return nIdHoja;
	}

	public void setIdHoja(int valor) {
	nIdHoja=valor;
	}

	public AreaServicioHRRB getAreaServ() {
	return oAreaServ;
	}

	public void setAreaServ(AreaServicioHRRB valor) {
	oAreaServ=valor;
	}

	public Parametrizacion getTipoRecetario() {
	return oTipoRecetario;
	}

	public void setTipoRecetario(Parametrizacion valor) {
	oTipoRecetario=valor;
	}

	public ArrayList<Medicamento> getMedicamento() {
	return oMedicamento;
	}

	public void setMedicamento(ArrayList<Medicamento> valor) {
	oMedicamento=valor;
	}

} 
