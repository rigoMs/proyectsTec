package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Intencionalidad implements Serializable{
	private AccesoDatos oAD;
	private int nClave;
	private int nClaveCode;
	private int nClaveHojaLesion;
	private String sDescripcion;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Intencionalidad.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveIntencionalidad();"; 
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
	public Intencionalidad[] buscarTodos() throws Exception{
	Intencionalidad arrRet[]=null, oIntencionalidad=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosIntencionalidad();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Intencionalidad[rst.size()];
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
			throw new Exception("Intencionalidad.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaIntencionalidad();"; 
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
			throw new Exception("Intencionalidad.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaIntencionalidad();"; 
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
			throw new Exception("Intencionalidad.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaIntencionalidad();"; 
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
	
	
    public Intencionalidad[] buscarIntencionHojaLesion() throws Exception{
	Intencionalidad arrRet[]=null, oInt=null;
	ArrayList rst = null;
        ArrayList<Intencionalidad> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaIntensionalidadHojaLesion();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Intencionalidad>();
			for (i = 0; i < rst.size(); i++) {
                            oInt = new Intencionalidad();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oInt.setClave(((Double)vRowTemp.get(0)).intValue());
                            oInt.setDescripcion((String)vRowTemp.get(1));
                            oInt.setClaveHojaLesion(((Double)vRowTemp.get(2)).intValue());
                            vObj.add(oInt);
			}
                    nTam = vObj.size();
                    arrRet = new Intencionalidad[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
    
	public int getClave() {
	return nClave;
	}

	public void setClave(int valor) {
	nClave=valor;
	}

	public int getClaveCode() {
	return nClaveCode;
	}

	public void setClaveCode(int valor) {
	nClaveCode=valor;
	}

	public int getClaveHojaLesion() {
	return nClaveHojaLesion;
	}

	public void setClaveHojaLesion(int valor) {
	nClaveHojaLesion=valor;
	}

	public String getDescripcion() {
	return sDescripcion;
	}

	public void setDescripcion(String valor) {
	sDescripcion=valor;
	}

} 
