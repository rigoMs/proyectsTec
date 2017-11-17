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

public class SignosVitales implements Serializable{
    private AccesoDatos oAD;
    private int nNgMotora;
    private int nNgOcular;
    private int nNgTotal;
    private int nNgVerbal;
    private String sAspecto;
    private String sFC;
    private String sFR;
    private String sTA;
    private String sTemp;
    private char sTipo;
    //agregado por la segunda fase
    private int nPulso;
    private Date dFechaReg;
    //////////////////////
    //Agregado por la tercera fase
    private boolean bConsiente;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SignosVitales.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSignosVitales();"; 
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
	public SignosVitales[] buscarTodos() throws Exception{
	SignosVitales arrRet[]=null, oSignosVitales=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSignosVitales();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new SignosVitales[rst.size()];
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
			throw new Exception("SignosVitales.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSignosVitales();"; 
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
			throw new Exception("SignosVitales.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSignosVitales();"; 
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
			throw new Exception("SignosVitales.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSignosVitales();"; 
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
////////////////////////////////////////////////////////////////////////////////
        
public SignosVitales[] buscarSignosVitalesActuales(long FolioPac, long CveEpiMed) throws Exception{
SignosVitales arrRet[]=null, oSigVit=null;
ArrayList rst = null;
ArrayList<SignosVitales> vObj=null;
String sQuery = "";
int i=0, nTam=0;
    if( this==null){
        throw new Exception("SignosVitales.buscar: error de programación, faltan datos");
    }else{
        sQuery = "SELECT * FROM buscarsignosvitalesactuales("+FolioPac+"::bigint,"+CveEpiMed+"::bigint);";
        
        oAD=new AccesoDatos();

        if (oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar(); 
        }
        if (rst != null) {
            vObj = new ArrayList<SignosVitales>();
            for (i=0; i<rst.size(); i++){
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oSigVit=new SignosVitales();
                this.setTA((String) vRowTemp.get(0));
                this.setFC((String) vRowTemp.get(1));
                this.setFR((String) vRowTemp.get(2));
                this.setTemp((String) vRowTemp.get(3));
                this.setFechaReg((Date) vRowTemp.get(4));

                vObj.add(oSigVit);                              
            }
            nTam = vObj.size();
            arrRet = new SignosVitales[nTam];
            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        }
    }
    return arrRet;
}

        
////////////////////////////////////////////////////////////////////////////////
        
	public int getNgMotora() {
	return nNgMotora;
	}

	public void setNgMotora(int valor) {
	nNgMotora=valor;
	}

	public int getNgOcular() {
	return nNgOcular;
	}

	public void setNgOcular(int valor) {
	nNgOcular=valor;
	}

	public int getNgTotal() {
	return nNgTotal=nNgOcular+nNgVerbal+nNgMotora;
	}

	public void setNgTotal(int valor) {
	nNgTotal=valor;
	}

	public int getNgVerbal() {
	return nNgVerbal;
	}

	public void setNgVerbal(int valor) {
	nNgVerbal=valor;
	}

	public String getAspecto() {
	return sAspecto;
	}

	public void setAspecto(String valor) {
	sAspecto=valor;
	}

	public String getFC() {
	return sFC;
	}

	public void setFC(String valor) {
	sFC=valor;
	}

	public String getFR() {
	return sFR;
	}

	public void setFR(String valor) {
	sFR=valor;
	}

	public String getTA() {
	return sTA;
	}

	public void setTA(String valor) {
	sTA=valor;
	}

	public String getTemp() {
	return sTemp;
	}

	public void setTemp(String valor) {
	sTemp=valor;
	}

	public char getTipo() {
	return sTipo;
	}

	public void setTipo(char valor) {
	sTipo=valor;
	}

    public int getPulso() {
        return nPulso;
    }

    public void setPulso(int nPulso) {
        this.nPulso = nPulso;
    }

    public Date getFechaReg() {
        return dFechaReg;
    }

    public void setFechaReg(Date dFechaReg) {
        this.dFechaReg = dFechaReg;
    }
    
    public boolean getConsiente(){
        return bConsiente;
    }
    public void setConsiente(boolean bConsiente){
        this.bConsiente = bConsiente;
    }
    
    //metodo agregado por alberto
    //traduce un booleano a una afirmacion
    public String getEstaConsciente(){
        return this.getConsiente() ? "SÍ(X) NO( )" : "SÍ( ) NO(X)";
    }
} 
