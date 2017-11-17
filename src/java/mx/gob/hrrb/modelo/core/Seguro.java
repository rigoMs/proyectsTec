package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Seguro implements Serializable{
    private AccesoDatos oAD;
    private Date dVigencia;
    private Parametrizacion oDerechohabiente;
    private String sNumero;
    private String sNumero2;
    private String sNumero3;
    private String sNumero4;
    private String sNumero5;
    private String sNumero6;
    private String sNumero7;
    private String sNumero8;
    private String sNumeroG;
    private String sNumeroP;
    private String dVigenciaTexto;
    private String dVigenciaTexto2;
    private String dVigenciaTexto3;
    private String dVigenciaTexto4;
    private String dVigenciaTexto5;
    private String dVigenciaTexto6;
    private String dVigenciaTexto7;
    private String dVigenciaTexto8;
    private String dVigenciaTextoG;
    private String dVigenciaTextoP;
    private String sOpc;
    private String dFecha;
    private String oUnaDer;
    private String sDerechohabienteP;
    private String sTipoSeguro;
    private List<String> lDer;
    private int nidentiSegu;
    
    private String sRutaPoliza;
    public final static String RUTA_POLIZA="/resources/docPac";
        
        public Seguro(){
            dFecha="";
            oUnaDer="";
            sOpc="";
            sTipoSeguro ="08";
            lDer=new ArrayList<String>();
            oDerechohabiente= new Parametrizacion();
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Seguro.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSeguro();"; 
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
        //*******************************************************************
        public boolean buscarSeguroPop(String numero, long folio) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Seguro.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaSeguroPop('"+numero+"', 'T0108', "+folio+"::bigint);";
                        
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
	//********************MÉTODO DE CAPASITS*************************
	public boolean buscarNumSegPopu() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this.getNumero()!=null){   //completar llave
			sQuery = "SELECT * FROM buscaNumeroSegPopu('"+this.sNumero+"');"; 
                        System.out.println(sQuery);
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
        //*******************************************************************
	public Seguro[] buscarTodos() throws Exception{
	Seguro arrRet[]=null, oSeguro=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSeguro();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Seguro[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        //**********************************************************************************************
        public String buscaSegPopxPac(long folio) throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        int i=0;
        String seguro="";
             
		sQuery = "select * from buscaSegPopxPac("+folio+", 'T0108');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            seguro=((String)vRowTemp.get(0));

			}
		} 
		return seguro; 
	}
        //**********************************************************************************************
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Seguro.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSeguro();"; 
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
			throw new Exception("Seguro.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSeguro();"; 
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
			throw new Exception("Seguro.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSeguro();"; 
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

	 //*******************************************************************
        public String existeSeguro(String numero, String der, long folio) throws Exception{
	String bRet = "";
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Seguro.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM existeSeguro('"+numero+"', 'T01"+der+"', "+folio+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				bRet = (String)rst.get(0).toString();
                        //System.out.println("rst Existe seguro: "+bRet);
                        }
                        
                        
                        
		} 
		return bRet; 
	} 
        
    public void buscaSeguroReciente(long folipac) throws Exception{
   	ArrayList rst = null;
	String sQuery = "";  
        
        sQuery = "SELECT * FROM buscaseguropopularvigente("+folipac+" ::BIGINT);";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null && rst.size()==1) {
            ArrayList vRowTemp = (ArrayList)rst.get(0);
            this.setIdentiSegu(((Double) vRowTemp.get( 0 )).intValue());
            this.setNumero((String)vRowTemp.get( 1 ));
            this.setVigencia((Date)vRowTemp.get(2));
            this.setDerechohabienteP((String)vRowTemp.get(3));
            this.getDerechohabiente().setValor(this.getDerechohabiente().buscaValorParametrizado((String)vRowTemp.get(3)));        
        }else{
            this.setNumero("SIN SEGURO");
        }    
    }
       

	public Date getVigencia() {
	return dVigencia;
	}

	public void setVigencia(Date valor) {
	dVigencia=valor;
	}

	public Parametrizacion getDerechohabiente() {
	return oDerechohabiente;
	}

	public void setDerechohabiente(Parametrizacion valor) {
	oDerechohabiente=valor;
	}

	public String getNumero() {
	return sNumero;
	}

	public void setNumero(String valor) {
	sNumero=valor;
	}

    public List<String> getDer() {
        System.out.println("getDer: "+lDer.size());
        return lDer;
    }

    public void setDer(List<String> lDer) {
        System.out.println("setDer: "+lDer);
        this.lDer = lDer;
    }
    
    public String getFechas(){
         return dFecha;
        //return x;
        }
    
    public void setFechas(String fechas){
            //System.out.println("Entro a setFechas: "+fechas);
            dFecha=fechas;
            //System.out.println("setFechas: "+dFecha);
            
        }

    public String getdFecha() {
        return dFecha;
    }

    public void setdFecha(String dFecha) {
        this.dFecha = dFecha;
    }

    public String getUnaDer() {
        return oUnaDer;
    }

    public void setUnaDer(String oUnaDer) {
        this.oUnaDer = oUnaDer;
    }
    
    public String getDerechohabienteP() {
        return sDerechohabienteP;
    }

    public void setDerechohabienteP(String sDerechohabienteP) {
        this.sDerechohabienteP = sDerechohabienteP;
    }

    public String getTipoSeguro() {
        return sTipoSeguro;
    }

    public void setTipoSeguro(String sTipoSeguro) {
        this.sTipoSeguro = sTipoSeguro;
    }
    
    public String getVigenciaTexto() {
        return dVigenciaTexto;
    }
    
    public void setVigenciaTexto(String dVigenciaTexto) {
        this.dVigenciaTexto = dVigenciaTexto;
    }

    public String getVigenciaTexto2() {
        return dVigenciaTexto2;
    }

    public void setVigenciaTexto2(String dVigenciaTexto2) {
        this.dVigenciaTexto2 = dVigenciaTexto2;
    }

    public String getVigenciaTexto3() {
        return dVigenciaTexto3;
    }

    public void setVigenciaTexto3(String dVigenciaTexto3) {
        this.dVigenciaTexto3 = dVigenciaTexto3;
    }

    public String getVigenciaTexto4() {
        return dVigenciaTexto4;
    }

    public void setVigenciaTexto4(String dVigenciaTexto4) {
        this.dVigenciaTexto4 = dVigenciaTexto4;
    }

    public String getVigenciaTexto5() {
        return dVigenciaTexto5;
    }

    public void setVigenciaTexto5(String dVigenciaTexto5) {
        this.dVigenciaTexto5 = dVigenciaTexto5;
    }

    public String getVigenciaTexto6() {
        return dVigenciaTexto6;
    }

    public void setVigenciaTexto6(String dVigenciaTexto6) {
        this.dVigenciaTexto6 = dVigenciaTexto6;
    }

    public String getVigenciaTexto7() {
        return dVigenciaTexto7;
    }

    public void setVigenciaTexto7(String dVigenciaTexto7) {
        this.dVigenciaTexto7 = dVigenciaTexto7;
    }

    public String getVigenciaTexto8() {
        return dVigenciaTexto8;
    }

    public void setVigenciaTexto8(String dVigenciaTexto8) {
        this.dVigenciaTexto8 = dVigenciaTexto8;
    }

    public String getVigenciaTextoG() {
        return dVigenciaTextoG;
    }

    public void setVigenciaTextoG(String dVigenciaTextoG) {
        this.dVigenciaTextoG = dVigenciaTextoG;
    }

    public String getVigenciaTextoP() {
        return dVigenciaTextoP;
    }

    public void setVigenciaTextoP(String dVigenciaTextoP) {
        this.dVigenciaTextoP = dVigenciaTextoP;
    }

    public String getNumero2() {
        return sNumero2;
    }

    public void setNumero2(String sNumero2) {
        this.sNumero2 = sNumero2;
    }

    public String getNumero3() {
        return sNumero3;
    }

    public void setNumero3(String sNumero3) {
        this.sNumero3 = sNumero3;
    }

    public String getNumero4() {
        return sNumero4;
    }

    public void setNumero4(String sNumero4) {
        this.sNumero4 = sNumero4;
    }

    public String getNumero5() {
        return sNumero5;
    }

    public void setNumero5(String sNumero5) {
        this.sNumero5 = sNumero5;
    }

    public String getNumero6() {
        return sNumero6;
    }

    public void setNumero6(String sNumero6) {
        this.sNumero6 = sNumero6;
    }

    public String getNumero7() {
        return sNumero7;
    }

    public void setNumero7(String sNumero7) {
        this.sNumero7 = sNumero7;
    }

    public String getNumero8() {
        return sNumero8;
    }

    public void setNumero8(String sNumero8) {
        this.sNumero8 = sNumero8;
    }

    public String getNumeroG() {
        return sNumeroG;
    }

    public void setNumeroG(String sNumeroG) {
        this.sNumeroG = sNumeroG;
    }

    public String getNumeroP() {
        return sNumeroP;
    }

    public void setNumeroP(String sNumeroP) {
        this.sNumeroP = sNumeroP;
    }
    
    public String getNomArchPOLIZA(){return sRutaPoliza;}
    public String getRutaPOLIZA(){ return RUTA_POLIZA+"/"+sRutaPoliza;}
    public void setRutaPOLIZA(String sRutaPoliza){this.sRutaPoliza=sRutaPoliza;};
    
    public int getIdentiSegu() {
        return nidentiSegu;
    }

    public void setIdentiSegu(int nidentiSegu) {
        this.nidentiSegu = nidentiSegu;
    }
} 
