package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.EpisodioMedico;
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

public  class ConsentimientoInformado implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bIngresoHospitalario;
	private boolean bIntervencionQuirurgica;
	private boolean bPlanificacionFamiliar;
	private boolean bProcesoAnestecico;
	private Date dFechaElaboracion;
	private EpisodioMedico oEpisodioMedico;

	public ConsentimientoInformado(){
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
			throw new Exception("ConsentimientoInformado.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveConsentimientoInformado();"; 
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
	public ConsentimientoInformado[] buscarTodos() throws Exception{
	ConsentimientoInformado arrRet[]=null, oConsentimientoInformado=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosConsentimientoInformado();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ConsentimientoInformado[rst.size()];
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
			throw new Exception("ConsentimientoInformado.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaConsentimientoInformado('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ConsentimientoInformado.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaConsentimientoInformado('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ConsentimientoInformado.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaConsentimientoInformado('"+sUsuarioFirmado+"');"; 
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
	public boolean getIngresoHospitalario() {
	return bIngresoHospitalario;
	}

	public void setIngresoHospitalario(boolean valor) {
	bIngresoHospitalario=valor;
	}

	public boolean getIntervencionQuirurgica() {
	return bIntervencionQuirurgica;
	}

	public void setIntervencionQuirurgica(boolean valor) {
	bIntervencionQuirurgica=valor;
	}

	public boolean getPlanificacionFamiliar() {
	return bPlanificacionFamiliar;
	}

	public void setPlanificacionFamiliar(boolean valor) {
	bPlanificacionFamiliar=valor;
	}

	public boolean getProcesoAnestecico() {
	return bProcesoAnestecico;
	}

	public void setProcesoAnestecico(boolean valor) {
	bProcesoAnestecico=valor;
	}

	public Date getFechaElaboracion() {
	return dFechaElaboracion;
	}

	public void setFechaElaboracion(Date valor) {
	dFechaElaboracion=valor;
	}

	public EpisodioMedico getEpisodioMedico() {
	return oEpisodioMedico;
	}

	public void setEpisodioMedico(EpisodioMedico valor) {
	oEpisodioMedico=valor;
	}

} 
