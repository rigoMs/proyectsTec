package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Municipio implements Serializable{
	private AccesoDatos oAD;
	private Estado oEstado;
	private String sClaveMun;
	private String sDescripcionMun;

	public Municipio(){
            oEstado=new Estado();
        }
        
	public boolean buscar(String edo) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Municipio.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveMunicipio('"+edo+"', '"+sClaveMun+"');";
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setDescripcionMun((String)vRowTemp.get(2));
				bRet = true;
			}
		} 
		return bRet; 
	} 
	public Municipio[] buscarTodos() throws Exception{
	Municipio arrRet[]=null, oMunicipio=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosMunicipio();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Municipio[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        //***************************************************************
       public Municipio[] buscarMunicipio(String cveEdo) throws Exception{
	Municipio arrRet[]=null, oMun=null;
	ArrayList rst = null;
        ArrayList<Municipio> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaMunicipios('"+cveEdo+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Municipio>();
			for (i = 0; i < rst.size(); i++) {
                            oMun = new Municipio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oMun.setClaveMun((String)vRowTemp.get(0));
                            oMun.setDescripcionMun((String)vRowTemp.get(1));
                            vObj.add(oMun);
			}
                    nTam = vObj.size();
                    arrRet = new Municipio[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        //***************************************************************
       public Municipio[] buscaMunicipioCP(String cp, String cveEdo) throws Exception{
	Municipio arrRet[]=null, oMun=null;
	ArrayList rst = null;
        ArrayList<Municipio> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaMunicipioCP('"+cp+"', '"+cveEdo+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Municipio>();
			for (i = 0; i < rst.size(); i++) {
                            oMun = new Municipio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oMun.setClaveMun((String)vRowTemp.get(0));
                            oMun.setDescripcionMun((String)vRowTemp.get(1));
                            vObj.add(oMun);
			}
                    nTam = vObj.size();
                    arrRet = new Municipio[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        //***************************************************************
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Municipio.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaMunicipio();"; 
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
			throw new Exception("Municipio.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaMunicipio();"; 
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
			throw new Exception("Municipio.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaMunicipio();"; 
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
	public Estado getEstado() {
	return oEstado;
	}

	public void setEstado(Estado valor) {
	oEstado=valor;
	}

	public String getClaveMun() {
	return sClaveMun;
	}

	public void setClaveMun(String valor) {
	sClaveMun=valor;
	}

	public String getDescripcionMun() {
	return sDescripcionMun;
	}

	public void setDescripcionMun(String valor) {
	sDescripcionMun=valor;
	}

} 
