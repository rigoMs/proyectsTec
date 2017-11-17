package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Ciudad implements Serializable{
	private AccesoDatos oAD;
	private Municipio oMunicipio;
	private String sClaveCiu;
	private String sDescripcionCiu;
	private Estado oEstado;

	public Ciudad(){
            oMunicipio= new Municipio();
            oEstado=new Estado();
        }

	public boolean buscar(String edo, String mun) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Ciudad.buscar: error de programación, faltan datos");
		}else{ 
                        sQuery = "SELECT * FROM buscaLlaveCiudad('"+edo+"', '"+mun+"', '"+sClaveCiu+"');";
                        
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setDescripcionCiu((String)vRowTemp.get(3));
				bRet = true;
			}
		} 
		return bRet; 
	} 
	public Ciudad[] buscarTodos() throws Exception{
	Ciudad arrRet[]=null, oCiudad=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCiudad();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Ciudad[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        //*********************************************************************
        public Ciudad[] buscarCiudad(String cveEdo, String cveMun) throws Exception{
	Ciudad arrRet[]=null, oCiu=null;
	ArrayList rst = null;
        ArrayList<Ciudad> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaCiudades('"+cveEdo+"','"+cveMun+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Ciudad>();
			for (i = 0; i < rst.size(); i++) {
                            oCiu = new Ciudad();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCiu.setClaveCiu((String)vRowTemp.get(0));
                            oCiu.setDescripcionCiu((String)vRowTemp.get(1));
                            vObj.add(oCiu);
			}
                    nTam = vObj.size();
                    arrRet = new Ciudad[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        //*********************************************************************
        public Ciudad[] buscaCiudadCP(String cveEdo, String cveMun,String cp) throws Exception{
	Ciudad arrRet[]=null, oCiu=null;
	ArrayList rst = null;
        ArrayList<Ciudad> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaCiudadesAux('"+cveEdo+"', '"+cveMun+"','"+cp+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Ciudad>();
			for (i = 0; i < rst.size(); i++) {
                            oCiu = new Ciudad();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCiu.setClaveCiu((String)vRowTemp.get(0));
                            oCiu.setDescripcionCiu(buscaDescripcionCD(oCiu.getClaveCiu(), cveEdo, cveMun));
                            vObj.add(oCiu);
			}
                    nTam = vObj.size();
                    arrRet = new Ciudad[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        //*********************************************************************
        public String buscaDescripcionCD(String cveciu, String edo, String mun) throws Exception{
	Ciudad arrRet[]=null, oCiu=null;
	ArrayList rst = null;
        ArrayList<Ciudad> vObj=null;
	String sQuery = "";
        String descripcion="";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaDescripcionCD('"+cveciu+"', '"+edo+"', '"+mun+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Ciudad>();
			for (i = 0; i < rst.size(); i++) {
                            oCiu = new Ciudad();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            descripcion=(String)vRowTemp.get(0);
                            vObj.add(oCiu);
			}
                    }
		 
		return descripcion; 
	} 
        //*********************************************************************
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Ciudad.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCiudad();"; 
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
			throw new Exception("Ciudad.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCiudad();"; 
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
			throw new Exception("Ciudad.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCiudad();"; 
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
	public Municipio getMunicipio() {
	return oMunicipio;
	}

	public void setMunicipio(Municipio valor) {
	oMunicipio=valor;
	}

	public String getClaveCiu() {
	return sClaveCiu;
	}

	public void setClaveCiu(String valor) {
	sClaveCiu=valor;
	}

	public String getDescripcionCiu() {
	return sDescripcionCiu;
	}

	public void setDescripcionCiu(String valor) {
	sDescripcionCiu=valor;
	}

	public Estado getEstado() {
        return oEstado;
    }

    public void setEstado(Estado oEstado) {
        this.oEstado = oEstado;
    }
} 
