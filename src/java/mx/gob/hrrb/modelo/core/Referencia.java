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

public class Referencia implements Serializable{
	private AccesoDatos oAD;
	private String sClave;
	private String sDescripcion;
	private String sTipo;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Referencia.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveReferencia('"+getClave()+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setDescripcion((String)vRowTemp.get(2));
				bRet = true;
			}
		} 
		return bRet; 
	}
        
	public Referencia[] buscarTodos() throws Exception{
	Referencia arrRet[]=null, oReferencia=null;
	ArrayList rst = null;
        ArrayList<Referencia> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaTodosReferencia();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Referencia>();
			for (i = 0; i < rst.size(); i++) {
                            oReferencia=new Referencia();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oReferencia.setClave((String)vRowTemp.get(0));
                            oReferencia.setDescripcion((String)vRowTemp.get(1));
                            oReferencia.setTipo((String)vRowTemp.get(2));
                            vObj.add(oReferencia);
			}
                    nTam = vObj.size();
                    arrRet = new Referencia[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Referencia.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaReferencia();"; 
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
			throw new Exception("Referencia.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaReferencia();"; 
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
			throw new Exception("Referencia.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaReferencia();"; 
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
        
        //Obtener Referencias
        public Referencia[] buscarReferencias() throws Exception{
	Referencia arrRet[]=null, oReferencia=null;
	ArrayList rst = null;
        ArrayList<Referencia> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaReferencia();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Referencia>();
			for (i = 0; i < rst.size(); i++) {
                            oReferencia = new Referencia();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oReferencia.setClave((String)vRowTemp.get(0));
                            oReferencia.setTipo((String)vRowTemp.get(1));
                            oReferencia.setDescripcion((String)vRowTemp.get(2));
                            vObj.add(oReferencia);
			}
                    nTam = vObj.size();
                    arrRet = new Referencia[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                        //System.out.println(arrRet[i].getClave().toString()+arrRet[i].getTipo().toString()+arrRet[i].getDescripcion().toString());
                    }
		} 
		return arrRet; 
	}
        
        //Retorna Lista de Referencias
     public List<Referencia> getListaReferencias() throws Exception{
        List<Referencia> lLista = null;
       
           lLista = new ArrayList<Referencia>(Arrays.asList(
                   (new Referencia()).buscarReferencias()));
       
        return lLista;
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

	public String getTipo() {
	return sTipo;
	}

	public void setTipo(String valor) {
	sTipo=valor;
	}

} 
