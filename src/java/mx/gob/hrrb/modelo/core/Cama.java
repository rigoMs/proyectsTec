package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Cama implements Serializable{
	private AccesoDatos oAD;
	private boolean bStatusCama;
	private String sAreaServicioHRRB;
	private String sNumero;
	private String sCensable;
    private String sNumeroOcupada;
    private String sNumeroDesocupada;

    public Cama(){
            sNumero="";
            sCensable="";
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cama.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCama();"; 
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
	public Cama[] buscarTodos() throws Exception{
	Cama arrRet[]=null, oCama=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCama();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Cama[rst.size()];
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
			throw new Exception("Cama.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCama();"; 
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
			throw new Exception("Cama.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCama();"; 
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
			throw new Exception("Cama.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCama();"; 
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

	//Retorna Lista de camas disponibles
     public List<Cama> getListaCamas() throws Exception{
        List<Cama> lLista = null;
       
           lLista = new ArrayList<Cama>(Arrays.asList(
                   (new Cama()).buscarCamasDisponibles()));
       
        return lLista;
    }
 
//***************************************************************
        public Cama[] buscarCamasDisponibles() throws Exception{
	Cama arrRet[]=null, oCama=null;
	ArrayList rst = null;
        ArrayList<Cama> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "select * from buscacamasdispobibles();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Cama>();
			for (i = 0; i < rst.size(); i++) {
                            oCama = new Cama();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCama.setNumero((String)vRowTemp.get(0));
                            oCama.setAreaServicioHRRB((String)vRowTemp.get(2));
                            vObj.add(oCama);
			}
                    nTam = vObj.size();
                    arrRet = new Cama[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}
        
        
    //Retorna Lista de camas Ocupadas
     public List<Cama> getListaCamasOcupadas() throws Exception{
        List<Cama> lLista = null;
       
           lLista = new ArrayList<Cama>(Arrays.asList(
                   (new Cama()).buscarCamasOcupadas()));
       
        return lLista;
    }
 
//***************************************************************
        public Cama[] buscarCamasOcupadas() throws Exception{
	Cama arrRet[]=null, oCama=null;
	ArrayList rst = null;
        ArrayList<Cama> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "select * from buscaCamasOcupadas();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Cama>();
			for (i = 0; i < rst.size(); i++) {
                            oCama = new Cama();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCama.setNumero((String)vRowTemp.get(0));
                            oCama.setAreaServicioHRRB((String)vRowTemp.get(2));
                            vObj.add(oCama);
			}
                    nTam = vObj.size();
                    arrRet = new Cama[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}

	public boolean getStatusCama() {
	return bStatusCama;
	}

	public void setStatusCama(boolean valor) {
	bStatusCama=valor;
	}

	public String getAreaServicioHRRB() {
	return sAreaServicioHRRB;
	}

	public void setAreaServicioHRRB(String valor) {
	sAreaServicioHRRB=valor;
	}

	public String getNumero() {
	return sNumero;
	}

	public void setNumero(String valor) {
	sNumero=valor;
	}

	public String getCensable() {
        return sCensable;
    }

    public void setCensable(String sCensable) {
        this.sCensable = sCensable;
    }

     public String getNumeroOcupada() {
        return sNumeroOcupada;
    }


    public void setNumeroOcupada(String sNumeroOcupada) {
        this.sNumeroOcupada = sNumeroOcupada;
    }

    public String getNumeroDesocupada() {
        return sNumeroDesocupada;
    }

    public void setNumeroDesocupada(String sNumeroDesocupada) {
        this.sNumeroDesocupada = sNumeroDesocupada;
    }

} 
