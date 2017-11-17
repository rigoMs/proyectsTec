package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Pais implements Serializable{
	private AccesoDatos oAD;
	private String sClavePais;
	private String sDescripcionPais;

	/*public Pais(){}*/
	public Pais() //EDITADO: (JMHG)
	{
	    sClavePais = "MX";
	}
	
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Pais.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePais();"; 
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
	
	public Pais[] buscarTodos() throws Exception{ //EDITADO: (JMHG)
	Pais arrRet[]=null, oPais=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPais();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Pais[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			    ArrayList vRowTemp = (ArrayList)rst.get( i );
			    oPais = new Pais();
			    oPais.setClavePais( (String)vRowTemp.get( 0 ) );
			    oPais.setDescripcionPais( (String)vRowTemp.get( 1 ) );
			    arrRet[ i ] = oPais;
			} 
		} 
		return arrRet; 
	}
	
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Pais.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPais();"; 
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
			throw new Exception("Pais.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPais();"; 
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
			throw new Exception("Pais.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPais();"; 
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
	public String getClavePais() {
	return sClavePais;
	}

	public void setClavePais(String valor) {
	sClavePais=valor;
	}

	public String getDescripcionPais() {
	return sDescripcionPais;
	}

	public void setDescripcionPais(String valor) {
	sDescripcionPais=valor;
	}

} 
