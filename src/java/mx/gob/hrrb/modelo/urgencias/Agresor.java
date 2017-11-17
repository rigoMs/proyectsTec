package mx.gob.hrrb.modelo.urgencias;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Agresor implements Serializable{
	private AccesoDatos oAD;
	private int nEdadAgresor;
	private Parametrizacion oAgresorBajoEfecto;
	private Parametrizacion oParentescoConAfectado;
	private char sSexoAgresor;
	private char sTipoAgresor;
        
        public Agresor(){
            oAgresorBajoEfecto=new Parametrizacion();
            oParentescoConAfectado=new Parametrizacion();
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Agresor.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAgresor();"; 
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
	public Agresor[] buscarTodos() throws Exception{
	Agresor arrRet[]=null, oAgresor=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAgresor();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Agresor[rst.size()];
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
			throw new Exception("Agresor.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAgresor();"; 
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
			throw new Exception("Agresor.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAgresor();"; 
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
			throw new Exception("Agresor.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAgresor();"; 
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
	public int getEdadAgresor() {
	return nEdadAgresor;
	}

	public void setEdadAgresor(int valor) {
	nEdadAgresor=valor;
	}

	public Parametrizacion getAgresorBajoEfecto() {
	return oAgresorBajoEfecto;
	}

	public void setAgresorBajoEfecto(Parametrizacion valor) {
	oAgresorBajoEfecto=valor;
	}

	public Parametrizacion getParentescoConAfectado() {
	return oParentescoConAfectado;
	}

	public void setParentescoConAfectado(Parametrizacion valor) {
	oParentescoConAfectado=valor;
	}

	public char getSexoAgresor() {
	return sSexoAgresor;
	}

	public void setSexoAgresor(char valor) {
	sSexoAgresor=valor;
	}

	public char getTipoAgresor() {
	return sTipoAgresor;
	}

	public void setTipoAgresor(char valor) {
	sTipoAgresor=valor;
	}

} 
