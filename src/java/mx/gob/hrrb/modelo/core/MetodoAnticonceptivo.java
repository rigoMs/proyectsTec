package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class MetodoAnticonceptivo implements Serializable{
	private AccesoDatos oAD;
	private String sClave;
	private String sDescripcion;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("MetodoAnticonceptivo.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveMetodoAnticonceptivo();"; 
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
	public MetodoAnticonceptivo[] buscarTodos() throws Exception{
	MetodoAnticonceptivo arrRet[]=null, oMetodoAnticonceptivo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosMetodoAnticonceptivo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new MetodoAnticonceptivo[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 

	//********************************************************************************************************
        public MetodoAnticonceptivo[] buscarMetodoAnticonceptivoCODE() throws Exception{
	MetodoAnticonceptivo arrRet[]=null, oMetodoAnticonceptivo=null;
	ArrayList rst = null;
        ArrayList<MetodoAnticonceptivo> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaMetodoAnticonceptivoCODE();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<MetodoAnticonceptivo>();
			for (i = 0; i < rst.size(); i++) {
                            oMetodoAnticonceptivo = new MetodoAnticonceptivo();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);

                                oMetodoAnticonceptivo.setClave((String)vRowTemp.get(0));
                                oMetodoAnticonceptivo.setDescripcion((String)vRowTemp.get(1));
                            
                            vObj.add(oMetodoAnticonceptivo);
			}
                    nTam = vObj.size();
                    arrRet = new MetodoAnticonceptivo[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);                        
                    }
		} 
		return arrRet; 
	}          
//************************************************************************
	
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("MetodoAnticonceptivo.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaMetodoAnticonceptivo();"; 
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
			throw new Exception("MetodoAnticonceptivo.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaMetodoAnticonceptivo();"; 
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
			throw new Exception("MetodoAnticonceptivo.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaMetodoAnticonceptivo();"; 
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
	public String getClave() {
	return sClave;
	}

	public void setClave(String valor) {
	sClave=valor;
	}

	public String getDescripcion() {
	return sDescripcion;
	}

	public void setDescripcion(String valor) {
	sDescripcion=valor;
	}

} 
