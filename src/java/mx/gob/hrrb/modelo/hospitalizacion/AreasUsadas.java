package mx.gob.hrrb.modelo.hospitalizacion;

import mx.gob.hrrb.modelo.hospitalizacion.AreasApoyo;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.core.Hospitalizacion;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AreasUsadas implements Serializable{
	private AccesoDatos oAD;
	private int nTiempoUso;
	private Hospitalizacion oHospitalizacion;
	private AreasApoyo oAreasApoyo;

	public AreasUsadas(){
            oAreasApoyo = new AreasApoyo();
        }
        
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AreasUsadas.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAreasUsadas();"; 
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
	public AreasUsadas[] buscarTodos() throws Exception{
	AreasUsadas arrRet[]=null, oAreasUsadas=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAreasUsadas();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AreasUsadas[rst.size()];
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
			throw new Exception("AreasUsadas.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAreasUsadas();"; 
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
			throw new Exception("AreasUsadas.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAreasUsadas();"; 
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
			throw new Exception("AreasUsadas.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAreasUsadas();"; 
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
	public int getTiempoUso() {
	return nTiempoUso;
	}

	public void setTiempoUso(int valor) {
	nTiempoUso=valor;
	}

	public Hospitalizacion getHospitalizacion() {
	return oHospitalizacion;
	}

	public void setHospitalizacion(Hospitalizacion valor) {
	oHospitalizacion=valor;
	}

	public AreasApoyo getAreaApoyo() {
            return oAreasApoyo;
	}

	public void setAreaApoyo(AreasApoyo valor) {
            oAreasApoyo=valor;
	}

} 
