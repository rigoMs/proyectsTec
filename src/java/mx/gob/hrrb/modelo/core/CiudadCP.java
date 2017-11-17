package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class CiudadCP implements Serializable{
	private AccesoDatos oAD;
	private Ciudad oCiudad;
	private String sCp;
    private Estado oEstado;
    private Municipio oMunicipio;

        public CiudadCP(){
            oCiudad=new Ciudad();
            oEstado=new Estado();
            oMunicipio=new Municipio();
        }
        
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CiudadCP.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCiudadCP();"; 
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
        //***********************************************************************
        //***********************************************************************
	public CiudadCP[] buscarTodos() throws Exception{
	CiudadCP arrRet[]=null, oCiudadCP=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCiudadCP();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CiudadCP[rst.size()];
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
			throw new Exception("CiudadCP.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCiudadCP();"; 
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
			throw new Exception("CiudadCP.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCiudadCP();"; 
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
			throw new Exception("CiudadCP.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCiudadCP();"; 
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
	public Ciudad getCiudad() {
	return oCiudad;
	}

	public void setCiudad(Ciudad valor) {
	oCiudad=valor;
	}

	public String getCp() {
	return sCp;
	}

	public void setCp(String valor) {
	sCp=valor;
	}
        
        //Busca coincidencias con codigo postal
        public CiudadCP[] buscarCP(String ciudad) throws Exception{
	CiudadCP arrRet[]=null, oCP=null;
	ArrayList rst = null;
        ArrayList<CiudadCP> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaCP('"+ciudad+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<CiudadCP>();
			for (i = 0; i < rst.size(); i++) {
                            oCP = new CiudadCP();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCP.setCp((String)vRowTemp.get(0));
                            vObj.add(oCP);
			}
                    nTam = vObj.size();
                    arrRet = new CiudadCP[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        
        //Busca coincidencias con codigo postal
        public CiudadCP[] buscarCP(String ciudad, String municipio, String estado) throws Exception{
	CiudadCP arrRet[]=null, oCP=null;
	ArrayList rst = null;
        ArrayList<CiudadCP> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaCP('"+ciudad+"','"+municipio+"','"+estado+"');"; 
		System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<CiudadCP>();
			for (i = 0; i < rst.size(); i++) {
                            oCP = new CiudadCP();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCP.setCp((String)vRowTemp.get(0));
                            vObj.add(oCP);
			}
                    nTam = vObj.size();
                    arrRet = new CiudadCP[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 

    public Estado getEstado() {
        return oEstado;
    }

    public void setEstado(Estado oEstado) {
        this.oEstado = oEstado;
    }

    public Municipio getMunicipio() {
        return oMunicipio;
    }

    public void setMunicipio(Municipio oMunicipio) {
        this.oMunicipio = oMunicipio;
    }
       

} 
