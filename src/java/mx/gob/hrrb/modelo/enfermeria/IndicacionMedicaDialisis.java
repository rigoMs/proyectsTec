package mx.gob.hrrb.modelo.enfermeria;

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

public  class IndicacionMedicaDialisis implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nIdIndicacion;
	private String sIndicacion;
	private CabeceraDialisis oCabeceraDialisis;

	public IndicacionMedicaDialisis(){
            
            oCabeceraDialisis = new CabeceraDialisis();
            
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
			throw new Exception("IndicacionMedicaDialisis.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveIndicacionMedicaDialisis();"; 
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
	public IndicacionMedicaDialisis[] buscarTodos() throws Exception{
	IndicacionMedicaDialisis arrRet[]=null, oIndicacionMedicaDialisis=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosIndicacionMedicaDialisis();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new IndicacionMedicaDialisis[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
	public int insertarIndicacionMedica() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if(this.oCabeceraDialisis.getIdCabeceraDialisis()==0 &&  this.getIndicacion().equals("")){  
			throw new Exception("IndicacionMedicaDialisis.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaIndicacionMedicaDialisis('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("IndicacionMedicaDialisis.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaIndicacionMedicaDialisis('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("IndicacionMedicaDialisis.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaIndicacionMedicaDialisis('"+sUsuarioFirmado+"');"; 
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
        
	public int getIdIndicacion() {
            return nIdIndicacion;
	}

	public void setIdIndicacion(int valor) {
            this.nIdIndicacion=valor;
	}

	public String getIndicacion() {
            return sIndicacion;
	}

	public void setIndicacion(String valor) {
            this.sIndicacion=valor;
	}

	public CabeceraDialisis getCabeceraDialisis() {
            return oCabeceraDialisis;
	}

	public void setCabeceraDialisis(CabeceraDialisis valor) {
            this.oCabeceraDialisis=valor;
	}

} 
