package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AntecPerinatales implements Serializable{
	private AccesoDatos oAD;
	private boolean bEmbControlado;
	private boolean bEmbNormal;
	private boolean bNeonatoSano;
	private int nPerinCefal;
	private int nPesoRN;
	private int nTallaRN;
	private DiagnosticoCIE10 oClaveDiag;
	private String sApgar;
	private String sObservaciones;
	private char sPartoNormal;
	private char sTipoParto;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AntecPerinatales.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAntecPerinatales();"; 
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
	public AntecPerinatales[] buscarTodos() throws Exception{
	AntecPerinatales arrRet[]=null, oAntecPerinatales=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAntecPerinatales();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AntecPerinatales[rst.size()];
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
			throw new Exception("AntecPerinatales.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAntecPerinatales();"; 
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
			throw new Exception("AntecPerinatales.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAntecPerinatales();"; 
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
			throw new Exception("AntecPerinatales.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAntecPerinatales();"; 
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
	public boolean getEmbControlado() {
	return bEmbControlado;
	}

	public void setEmbControlado(boolean valor) {
	bEmbControlado=valor;
	}

	public boolean getEmbNormal() {
	return bEmbNormal;
	}

	public void setEmbNormal(boolean valor) {
	bEmbNormal=valor;
	}

	public boolean getNeonatoSano() {
	return bNeonatoSano;
	}

	public void setNeonatoSano(boolean valor) {
	bNeonatoSano=valor;
	}

	public int getPerinCefal() {
	return nPerinCefal;
	}

	public void setPerinCefal(int valor) {
	nPerinCefal=valor;
	}

	public int getPesoRN() {
	return nPesoRN;
	}

	public void setPesoRN(int valor) {
	nPesoRN=valor;
	}

	public int getTallaRN() {
	return nTallaRN;
	}

	public void setTallaRN(int valor) {
	nTallaRN=valor;
	}

	public DiagnosticoCIE10 getClaveDiag() {
	return oClaveDiag;
	}

	public void setClaveDiag(DiagnosticoCIE10 valor) {
	oClaveDiag=valor;
	}

	public String getApgar() {
	return sApgar;
	}

	public void setApgar(String valor) {
	sApgar=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public char getPartoNormal() {
	return sPartoNormal;
	}

	public void setPartoNormal(char valor) {
	sPartoNormal=valor;
	}

	public char getTipoParto() {
	return sTipoParto;
	}

	public void setTipoParto(char valor) {
	sTipoParto=valor;
	}

} 
