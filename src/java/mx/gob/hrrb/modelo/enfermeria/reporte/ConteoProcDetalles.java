package mx.gob.hrrb.modelo.enfermeria.reporte;

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

public  class ConteoProcDetalles implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nAParentalM;
	private int nAParentalN;
	private int nAParentalV;
	private int nAVentilatorioM;
	private int nAVentilatorioN;
	private int nAVentilatorioV;
	private int nDialisisV;
	private int nDialsisN;
	private int nDilalisM;
	private int nPleurovacM;
	private int nPleurovacN;
	private int nPleurovacV;
	private int nQuimioM;
	private int nQuimioN;
	private int nQuimioV;
	private int nSEndopleuralM;
	private int nSEndopleuralN;
	private int nSEndopleuralV;
	private int nTransfusionM;
	private int nTransfusionN;
	private int nTransfusionV;

	public ConteoProcDetalles(){
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
			throw new Exception("ConteoProdecimientoDetalles.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveConteoProdecimientoDetalles();"; 
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
	public ConteoProcDetalles[] buscarTodos() throws Exception{
	ConteoProcDetalles[] arrRet = null;
        ConteoProcDetalles oContProcDet = null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosConteoProdecimientoDetalles();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ConteoProcDetalles[rst.size()];
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
			throw new Exception("ConteoProdecimientoDetalles.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaConteoProdecimientoDetalles('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ConteoProdecimientoDetalles.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaConteoProdecimientoDetalles('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ConteoProdecimientoDetalles.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaConteoProdecimientoDetalles('"+sUsuarioFirmado+"');"; 
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
	public int getAParentalM() {
	return nAParentalM;
	}

	public void setAParentalM(int valor) {
	nAParentalM=valor;
	}

	public int getAParentalN() {
	return nAParentalN;
	}

	public void setAParentalN(int valor) {
	nAParentalN=valor;
	}

	public int getAParentalV() {
	return nAParentalV;
	}

	public void setAParentalV(int valor) {
	nAParentalV=valor;
	}

	public int getAVentilatorioM() {
	return nAVentilatorioM;
	}

	public void setAVentilatorioM(int valor) {
	nAVentilatorioM=valor;
	}

	public int getAVentilatorioN() {
	return nAVentilatorioN;
	}

	public void setAVentilatorioN(int valor) {
	nAVentilatorioN=valor;
	}

	public int getAVentilatorioV() {
	return nAVentilatorioV;
	}

	public void setAVentilatorioV(int valor) {
	nAVentilatorioV=valor;
	}

	public int getDialisisV() {
	return nDialisisV;
	}

	public void setDialisisV(int valor) {
	nDialisisV=valor;
	}

	public int getDialsisN() {
	return nDialsisN;
	}

	public void setDialsisN(int valor) {
	nDialsisN=valor;
	}

	public int getDilalisM() {
	return nDilalisM;
	}

	public void setDilalisM(int valor) {
	nDilalisM=valor;
	}

	public int getPleurovacM() {
	return nPleurovacM;
	}

	public void setPleurovacM(int valor) {
	nPleurovacM=valor;
	}

	public int getPleurovacN() {
	return nPleurovacN;
	}

	public void setPleurovacN(int valor) {
	nPleurovacN=valor;
	}

	public int getPleurovacV() {
	return nPleurovacV;
	}

	public void setPleurovacV(int valor) {
	nPleurovacV=valor;
	}

	public int getQuimioM() {
	return nQuimioM;
	}

	public void setQuimioM(int valor) {
	nQuimioM=valor;
	}

	public int getQuimioN() {
	return nQuimioN;
	}

	public void setQuimioN(int valor) {
	nQuimioN=valor;
	}

	public int getQuimioV() {
	return nQuimioV;
	}

	public void setQuimioV(int valor) {
	nQuimioV=valor;
	}

	public int getSEndopleuralM() {
	return nSEndopleuralM;
	}

	public void setSEndopleuralM(int valor) {
	nSEndopleuralM=valor;
	}

	public int getSEndopleuralN() {
	return nSEndopleuralN;
	}

	public void setSEndopleuralN(int valor) {
	nSEndopleuralN=valor;
	}

	public int getSEndopleuralV() {
	return nSEndopleuralV;
	}

	public void setSEndopleuralV(int valor) {
	nSEndopleuralV=valor;
	}

	public int getTransfusionM() {
	return nTransfusionM;
	}

	public void setTransfusionM(int valor) {
	nTransfusionM=valor;
	}

	public int getTransfusionN() {
	return nTransfusionN;
	}

	public void setTransfusionN(int valor) {
	nTransfusionN=valor;
	}

	public int getTransfusionV() {
	return nTransfusionV;
	}

	public void setTransfusionV(int valor) {
	nTransfusionV=valor;
	}

} 
