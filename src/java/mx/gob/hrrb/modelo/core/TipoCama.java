package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class TipoCama implements Serializable{
	private AccesoDatos oAD;
	private boolean bSensable;
	private float nCosto;
	private int nTipo;
	private String sDescripcion;
	private char sTipoUrgs;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("TipoCama.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveTipoCama();"; 
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
	public TipoCama[] buscarTodos() throws Exception{
	TipoCama arrRet[]=null, oTipoCama=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosTipoCama();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new TipoCama[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("TipoCama.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaTipoCama();"; 
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
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("TipoCama.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaTipoCama();"; 
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
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("TipoCama.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaTipoCama();"; 
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
	public boolean getSensable() {
	return bSensable;
	}

	public void setSensable(boolean valor) {
	bSensable=valor;
	}

	public float getCosto() {
	return nCosto;
	}

	public void setCosto(float valor) {
	nCosto=valor;
	}

	public int getTipo() {
	return nTipo;
	}

	public void setTipo(int valor) {
	nTipo=valor;
	}

	public String getDescripcion() {
	return sDescripcion;
	}

	public void setDescripcion(String valor) {
	sDescripcion=valor;
	}

	public char getTipoUrgs() {
	return sTipoUrgs;
	}

	public void setTipoUrgs(char valor) {
	sTipoUrgs=valor;
	}

} 
