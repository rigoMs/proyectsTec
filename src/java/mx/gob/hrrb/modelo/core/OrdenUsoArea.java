package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.hospitalizacion.AreaServicioSAEH;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class OrdenUsoArea implements Serializable{
	private AccesoDatos oAD;
	private int nOrden;
	private Hospitalizacion oHospitalizacion;
	private AreaServicioSAEH oAreaServicioSAEH;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("OrdenUsoArea.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveOrdenUsoArea();"; 
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
	public OrdenUsoArea[] buscarTodos() throws Exception{
	OrdenUsoArea arrRet[]=null, oOrdenUsoArea=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosOrdenUsoArea();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new OrdenUsoArea[rst.size()];
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
			throw new Exception("OrdenUsoArea.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaOrdenUsoArea();"; 
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
			throw new Exception("OrdenUsoArea.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaOrdenUsoArea();"; 
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
			throw new Exception("OrdenUsoArea.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaOrdenUsoArea();"; 
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
	public int getOrden() {
	return nOrden;
	}

	public void setOrden(int valor) {
	nOrden=valor;
	}

	public Hospitalizacion getHospitalizacion() {
	return oHospitalizacion;
	}

	public void setHospitalizacion(Hospitalizacion valor) {
	oHospitalizacion=valor;
	}

	public AreaServicioSAEH getAreaServicioSAEH() {
	return oAreaServicioSAEH;
	}

	public void setAreaServicioSAEH(AreaServicioSAEH valor) {
	oAreaServicioSAEH=valor;
	}

} 
