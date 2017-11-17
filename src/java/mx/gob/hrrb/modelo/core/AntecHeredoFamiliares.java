package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AntecHeredoFamiliares implements Serializable{
	private AccesoDatos oAD;
	private String sAlcoholismo;
	private String sAlergias;
	private String sAltMentales;
	private String sAsma;
	private String sCancer;
	private String sCongenitos;
	private String sConvulsiones;
	private String sDiabetes;
	private String sDrogadiccion;
	private String sEnfisema;
	private String sEpilepsia;
	private int sFamCardioPatias;
	private String sHipertension;
	private String sIAM;
	private String sLitiasis;
	private String sTabaquismo;
	private String sTuberculosis;
        
        /*INICIAN ATRIBUTOS AGREGADOS*/
        private String sPadre;
        private String sMadre;
        private String sAbueloMaterno;
        private String sAbuelaMaterna;
        private String sAbueloPaterno;
        private String sAbuelaPaterna;
        private String sOtros;
        /*TERMINAN ATRIBUTOS AGREGADOS*/

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AntecHeredoFamiliares.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAntecHeredoFamiliares();"; 
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
	public AntecHeredoFamiliares[] buscarTodos() throws Exception{
	AntecHeredoFamiliares arrRet[]=null, oAntecHeredoFamiliares=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAntecHeredoFamiliares();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AntecHeredoFamiliares[rst.size()];
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
			throw new Exception("AntecHeredoFamiliares.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAntecHeredoFamiliares();"; 
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
			throw new Exception("AntecHeredoFamiliares.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAntecHeredoFamiliares();"; 
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
			throw new Exception("AntecHeredoFamiliares.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAntecHeredoFamiliares();"; 
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
	public String getAlcoholismo() {
	return sAlcoholismo;
	}

	public void setAlcoholismo(String valor) {
	sAlcoholismo=valor;
	}

	public String getAlergias() {
	return sAlergias;
	}

	public void setAlergias(String valor) {
	sAlergias=valor;
	}

	public String getAltMentales() {
	return sAltMentales;
	}

	public void setAltMentales(String valor) {
	sAltMentales=valor;
	}

	public String getAsma() {
	return sAsma;
	}

	public void setAsma(String valor) {
	sAsma=valor;
	}

	public String getCancer() {
	return sCancer;
	}

	public void setCancer(String valor) {
	sCancer=valor;
	}

	public String getCongenitos() {
	return sCongenitos;
	}

	public void setCongenitos(String valor) {
	sCongenitos=valor;
	}

	public String getConvulsiones() {
	return sConvulsiones;
	}

	public void setConvulsiones(String valor) {
	sConvulsiones=valor;
	}

	public String getDiabetes() {
	return sDiabetes;
	}

	public void setDiabetes(String valor) {
	sDiabetes=valor;
	}

	public String getDrogadiccion() {
	return sDrogadiccion;
	}

	public void setDrogadiccion(String valor) {
	sDrogadiccion=valor;
	}

	public String getEnfisema() {
	return sEnfisema;
	}

	public void setEnfisema(String valor) {
	sEnfisema=valor;
	}

	public String getEpilepsia() {
	return sEpilepsia;
	}

	public void setEpilepsia(String valor) {
	sEpilepsia=valor;
	}

	public int getFamCardioPatias() {
	return sFamCardioPatias;
	}

	public void setFamCardioPatias(int valor) {
	sFamCardioPatias=valor;
	}

	public String getHipertension() {
	return sHipertension;
	}

	public void setHipertension(String valor) {
	sHipertension=valor;
	}

	public String getIAM() {
	return sIAM;
	}

	public void setIAM(String valor) {
	sIAM=valor;
	}

	public String getLitiasis() {
	return sLitiasis;
	}

	public void setLitiasis(String valor) {
	sLitiasis=valor;
	}

	public String getTabaquismo() {
	return sTabaquismo;
	}

	public void setTabaquismo(String valor) {
	sTabaquismo=valor;
	}

	public String getTuberculosis() {
	return sTuberculosis;
	}

	public void setTuberculosis(String valor) {
	sTuberculosis=valor;
	}
        public String getPadre(){
            return sPadre;
        }
        public void setPadre(String sPadre){
            this.sPadre = sPadre;
        }
        public String getMadre(){
            return sMadre;
        }
        public void setMadre(String sMadre){
            this.sMadre = sMadre;
        }
        public String getAbueloMaterno(){
            return sAbueloMaterno;
        }
        public void setAbueloMaterno(String sAbueloMaterno){
            this.sAbueloMaterno = sAbueloMaterno;
        }
        public String getAbuelaMaterna(){
            return sAbuelaMaterna;
        }
        public void setAbuelaMaterna(String sAbuelaMaterna){
            this.sAbuelaMaterna = sAbuelaMaterna;
        }
        public String getAbueloPaterno(){
            return sAbueloPaterno;
        }
        public void setAbueloPaterno(String sAbueloPaterno){
            this.sAbueloPaterno = sAbueloPaterno;
        }
        public String getAbuelaPaterna(){
            return sAbuelaPaterna;
        }
        public void setAbuelaPaterna(String sAbuelaPaterna){
            this.sAbuelaPaterna = sAbuelaPaterna;
        }
        public String getOtros(){
            return sOtros;
        }
        public void setOtros(String sOtros){
            this.sOtros = sOtros;
        }
} 
