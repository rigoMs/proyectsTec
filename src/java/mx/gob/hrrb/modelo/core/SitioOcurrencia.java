package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class SitioOcurrencia implements Serializable{
	private AccesoDatos oAD;
	private int nClave;
	private int nClaveCode=10;
	private int nClaveHojaLesion;
	private String sDescripcion;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SitioOcurrencia.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSitioOcurrencia();"; 
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
	public SitioOcurrencia[] buscarTodos() throws Exception{
	SitioOcurrencia arrRet[]=null, oSitioOcurrencia=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSitioOcurrencia();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new SitioOcurrencia[rst.size()];
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
			throw new Exception("SitioOcurrencia.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSitioOcurrencia();"; 
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
			throw new Exception("SitioOcurrencia.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSitioOcurrencia();"; 
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
			throw new Exception("SitioOcurrencia.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSitioOcurrencia();"; 
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

           
        public SitioOcurrencia[] buscarSitioOcurrenciaHojaLesion() throws Exception{
	SitioOcurrencia arrRet[]=null, oSitio=null;
	ArrayList rst = null;
        ArrayList<SitioOcurrencia> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaSitioOcurrenciaHojaLesion();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<SitioOcurrencia>();
			for (i = 0; i < rst.size(); i++) {
                            oSitio = new SitioOcurrencia();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSitio.setClave(((Double)vRowTemp.get(0)).intValue());
                            oSitio.setDescripcion((String)vRowTemp.get(1));
                            oSitio.setClaveHojaLesion(((Double)vRowTemp.get(2)).intValue());
                            vObj.add(oSitio);
			}
                    nTam = vObj.size();
                    arrRet = new SitioOcurrencia[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
    
} 
