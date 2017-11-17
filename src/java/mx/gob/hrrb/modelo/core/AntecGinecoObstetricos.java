/*
    ALBERTO
    AGREGUE LOS ATRIBUTOS 
    sCiclos
    bControlPrenatal
    Y FUERON AGREGADOS COMO CAMPOS EN LA BASE DE DATOS
    agregue la fecha del ultimi parto dFechaUltimoparto
*/
package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AntecGinecoObstetricos implements Serializable{
	private AccesoDatos oAD;
	private ArrayList arrMetodoAnticonceptivo;
	private Date dFechaPosibleParto;
	private Date dUltimaMenstruacion;
	private Date dUltPapanicolau;
        private Date dFechaUltimoParto;
	private int nAbortos;
	private int nCesareas;
	private int nGestaciones;
	private int nIVSA;
	private int nMenarca;
	private int nParejasSexuales;
	private int nPartos;
	private String sDoc;
	private String sETS;
	private String sPF;
	private ArrayList arrAtencionObstetrica;
        private MetodoAnticonceptivo oMetodoAnticonceptivo;
        private String sCiclos;
        private boolean bControlPrenatal;
        
        public AntecGinecoObstetricos(){
            oMetodoAnticonceptivo = new MetodoAnticonceptivo();
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AntecGinecoObstetricos.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAntecGinecoObstetricos();"; 
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
	public AntecGinecoObstetricos[] buscarTodos() throws Exception{
	AntecGinecoObstetricos arrRet[]=null, oAntecGinecoObstetricos=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAntecGinecoObstetricos();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AntecGinecoObstetricos[rst.size()];
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
			throw new Exception("AntecGinecoObstetricos.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAntecGinecoObstetricos();"; 
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
			throw new Exception("AntecGinecoObstetricos.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAntecGinecoObstetricos();"; 
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
			throw new Exception("AntecGinecoObstetricos.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAntecGinecoObstetricos();"; 
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
	public ArrayList getrrMetodoAnticonceptivo() {
	return arrMetodoAnticonceptivo;
	}

	public void setrrMetodoAnticonceptivo(ArrayList valor) {
	arrMetodoAnticonceptivo=valor;
	}

	public Date getFechaPosibleParto() {
	return dFechaPosibleParto;
	}

	public void setFechaPosibleParto(Date valor) {
	dFechaPosibleParto=valor;
	}

	public Date getUltimaMenstruacion() {
	return dUltimaMenstruacion;
	}

	public void setUltimaMenstruacion(Date valor) {
	dUltimaMenstruacion=valor;
	}

	public Date getUltPapanicolau() {
	return dUltPapanicolau;
	}

	public void setUltPapanicolau(Date valor) {
	dUltPapanicolau=valor;
	}

	public int getAbortos() {
	return nAbortos;
	}

	public void setAbortos(int valor) {
	nAbortos=valor;
	}

	public int getCesareas() {
	return nCesareas;
	}

	public void setCesareas(int valor) {
	nCesareas=valor;
	}

	public int getGestaciones() {
	return nGestaciones;
	}

	public void setGestaciones(int valor) {
	nGestaciones=valor;
	}

	public int getIVSA() {
	return nIVSA;
	}

	public void setIVSA(int valor) {
	nIVSA=valor;
	}

	public int getMenarca() {
	return nMenarca;
	}

	public void setMenarca(int valor) {
	nMenarca=valor;
	}

	public int getParejasSexuales() {
	return nParejasSexuales;
	}

	public void setParejasSexuales(int valor) {
	nParejasSexuales=valor;
	}

	public int getPartos() {
	return nPartos;
	}

	public void setPartos(int valor) {
	nPartos=valor;
	}

	public String getDoc() {
	return sDoc;
	}

	public void setDoc(String valor) {
	sDoc=valor;
	}

	public String getETS() {
	return sETS;
	}

	public void setETS(String valor) {
	sETS=valor;
	}

	public String getPF() {
	return sPF;
	}

	public void setPF(String valor) {
	sPF=valor;
	}

	public ArrayList getrrAtencionObstetrica() {
	return arrAtencionObstetrica;
	}

	public void setrrAtencionObstetrica(ArrayList valor) {
	arrAtencionObstetrica=valor;
	}

    /**
     * @return the oMetodoAnticonceptivo
     */
    public MetodoAnticonceptivo getMetodoAnticonceptivo() {
        return oMetodoAnticonceptivo;
    }

    /**
     * @param oMetodoAnticonceptivo the oMetodoAnticonceptivo to set
     */
    public void setMetodoAnticonceptivo(MetodoAnticonceptivo oMetodoAnticonceptivo) {
        this.oMetodoAnticonceptivo = oMetodoAnticonceptivo;
    }
    public String getCiclo(){
        return sCiclos;
    }
    public void setCiclo(String sCiclos){
        this.sCiclos = sCiclos;
    }
    public boolean getControlPrenatal(){
        return bControlPrenatal;
    }
    public void setControlPrenatal(boolean bControlPrenatal){
        this.bControlPrenatal = bControlPrenatal;
    }
    public Date getFechaUltimoParto(){
        return dFechaUltimoParto;
    }
    public void setFechaUltimoParto(Date dFechaUltimoParto){
        this.dFechaUltimoParto = dFechaUltimoParto;
    }
} 
