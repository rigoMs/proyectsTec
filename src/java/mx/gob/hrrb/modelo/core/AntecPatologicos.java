package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AntecPatologicos implements Serializable{
	private AccesoDatos oAD;
	private String sAlergias;
	private String sCardioPatias;
	private String sCardioVasculares;
	private String sDiabetico;
	private String sHTA;
	private String sQuirurgico;
	private String sTransfusion;
	private String sTraumat;
        private boolean bOncologico;
        private String sOtro;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AntecPatologicos.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAntecPatologicos();"; 
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
	public AntecPatologicos[] buscarTodos() throws Exception{
	AntecPatologicos arrRet[]=null, oAntecPatologicos=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAntecPatologicos();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AntecPatologicos[rst.size()];
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
			throw new Exception("AntecPatologicos.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAntecPatologicos();"; 
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
			throw new Exception("AntecPatologicos.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAntecPatologicos();"; 
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
			throw new Exception("AntecPatologicos.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAntecPatologicos();"; 
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
	public String getAlergias() {
	return sAlergias;
	}

	public void setAlergias(String valor) {
	sAlergias=valor;
	}

	public String getCardioPatias() {
	return sCardioPatias;
	}

	public void setCardioPatias(String valor) {
	sCardioPatias=valor;
	}

	public String getCardioVasculares() {
	return sCardioVasculares;
	}

	public void setCardioVasculares(String valor) {
	sCardioVasculares=valor;
	}

	public String getDiabetico() {
	return sDiabetico;
	}

	public void setDiabetico(String valor) {
	sDiabetico=valor;
	}

	public String getHTA() {
	return sHTA;
	}

	public void setHTA(String valor) {
	sHTA=valor;
	}

	public String getQuirurgico() {
	return sQuirurgico;
	}

	public void setQuirurgico(String valor) {
	sQuirurgico=valor;
	}

	public String getTransfusion() {
	return sTransfusion;
	}

	public void setTransfusion(String valor) {
	sTransfusion=valor;
	}

	public String getTraumat() {
	return sTraumat;
	}

	public void setTraumat(String valor) {
	sTraumat=valor;
	}
        
        public boolean getEsDiabetico(){
            return !(this.sDiabetico == null || this.sDiabetico.equals(""));
        }
        public String getValorDiabetico(){
            return this.getEsDiabetico() ? "SÍ" : "NO";
        }
        public boolean getEsHipertenso(){
            return !(this.sHTA == null || this.sHTA.equals(""));
        }
        public String getValorHipertenso(){
            return this.getEsHipertenso() ? "SÍ" : "NO";
} 
        public boolean getOncologico(){
            return bOncologico;
        }
        public void setOncologico(boolean bOncologico){
            this.bOncologico = bOncologico;
        }
        public String getOtro(){
            return sOtro;
        }
        public void setOtro(String sOtro){
            this.sOtro = sOtro;
        }
}
