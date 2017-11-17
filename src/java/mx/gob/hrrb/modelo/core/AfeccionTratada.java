package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AfeccionTratada implements Serializable{
	private AccesoDatos oAD;
	private boolean bInfeccionIntraHospitalaria;
	private int nNumEnfermedadMuerte;
	private DiagnosticoCIE10 oCausaBasicaDefuncion;
	private Parametrizacion oOrden;
	private DiagnosticoCIE10 oReseleccion;
	private Parametrizacion oTiempoEnfermedadMuerte;
	private DiagnosticoCIE10 oCIE10;
	private char sTipo;
	private String sTiempoEnfermedadMuerteP;

	public AfeccionTratada(){
            oCIE10 = new DiagnosticoCIE10();
    }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AfeccionTratada.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAfeccionTratada();"; 
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
	public AfeccionTratada[] buscarTodos() throws Exception{
	AfeccionTratada arrRet[]=null, oAfeccionTratada=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAfeccionTratada();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AfeccionTratada[rst.size()];
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
			throw new Exception("AfeccionTratada.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAfeccionTratada();"; 
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
			throw new Exception("AfeccionTratada.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAfeccionTratada();"; 
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
			throw new Exception("AfeccionTratada.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAfeccionTratada();"; 
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
        
    ///////////////////////////////////////////////////////////////////////////
    // Órdenes de servicio
    public AfeccionTratada[] buscarAfeccionTratadaPac(long FolioPac, long CveEpiMed) throws Exception{
    AfeccionTratada arrRet[]=null, oAfecTrat=null;
    ArrayList rst = null;
    ArrayList<AfeccionTratada> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
             if( this==null){
                    throw new Exception("AfeccionTratada.buscar: error de programación, faltan datos");
            }else{
                        sQuery = "SELECT * FROM buscardiagnosticospacientes("+FolioPac+"::bigint,"+CveEpiMed+"::bigint);";
                        System.out.println(sQuery);
                    oAD=new AccesoDatos();

                    if (oAD.conectar()){
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                        vObj = new ArrayList<AfeccionTratada>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAfecTrat=new AfeccionTratada();
                            oAfecTrat.oCIE10.setClave((String) vRowTemp.get(0));
                            oAfecTrat.oCIE10.setDescripcionDiag((String) vRowTemp.get(1));
                            vObj.add(oAfecTrat);                              
                        }
                        nTam = vObj.size();
                        arrRet = new AfeccionTratada[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
    }


    //////////////////////////////////////////////////////////////////////////
        
	public boolean getInfeccionIntraHospitalaria() {
	return bInfeccionIntraHospitalaria;
	}

	public void setInfeccionIntraHospitalaria(boolean valor) {
	bInfeccionIntraHospitalaria=valor;
	}

	public int getNumEnfermedadMuerte() {
	return nNumEnfermedadMuerte;
	}

	public void setNumEnfermedadMuerte(int valor) {
	nNumEnfermedadMuerte=valor;
	}

	public DiagnosticoCIE10 getCausaBasicaDefuncion() {
	return oCausaBasicaDefuncion;
	}

	public void setCausaBasicaDefuncion(DiagnosticoCIE10 valor) {
	oCausaBasicaDefuncion=valor;
	}

	public Parametrizacion getOrden() {
	return oOrden;
	}

	public void setOrden(Parametrizacion valor) {
	oOrden=valor;
	}

	public DiagnosticoCIE10 getReseleccion() {
	return oReseleccion;
	}

	public void setReseleccion(DiagnosticoCIE10 valor) {
	oReseleccion=valor;
	}

	public Parametrizacion getTiempoEnfermedadMuerte() {
	return oTiempoEnfermedadMuerte;
	}

	public void setTiempoEnfermedadMuerte(Parametrizacion valor) {
	oTiempoEnfermedadMuerte=valor;
	}

	public char getTipo() {
	return sTipo;
	}

	public void setTipo(char valor) {
	sTipo=valor;
	}

	public DiagnosticoCIE10 getCIE10() {
        return oCIE10;
    }

    public void setCIE10(DiagnosticoCIE10 oCIE10) {
        this.oCIE10 = oCIE10;
    }

    public String getTiempoEnfermedadMuerteP() {
        return sTiempoEnfermedadMuerteP;
    }

    public void setTiempoEnfermedadMuerteP(String sTiempoEnfermedadMuerteP) {
        this.sTiempoEnfermedadMuerteP = sTiempoEnfermedadMuerteP;
    }
} 
