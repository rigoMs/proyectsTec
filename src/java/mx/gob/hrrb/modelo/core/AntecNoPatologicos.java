package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AntecNoPatologicos implements Serializable{
	private AccesoDatos oAD;
	private boolean bAguaPotable;
	private boolean bAlcoholismo;
	private boolean bDrenaje;
	private boolean bElectricidad;
	private boolean bServSanit;
	private boolean bTabaquismo;
        private String sAguaPotableCad;
        private String sAlcoholismoCad;
        private String sDrenajeCad;
        private String sElectricidadCad;
        private String sServSanitCad;
        private String sTabaquismoCad;
	private String sAnimales;
	private String sDrogas;
	private String sEscolaridad;
	private String sOcupacion;
	private String sOtro;
	private String sReligion;
	private char sTipoCasaHab;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AntecNoPatologicos.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAntecNoPatologicos();"; 
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
	public AntecNoPatologicos[] buscarTodos() throws Exception{
	AntecNoPatologicos arrRet[]=null, oAntecNoPatologicos=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAntecNoPatologicos();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AntecNoPatologicos[rst.size()];
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
			throw new Exception("AntecNoPatologicos.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAntecNoPatologicos();"; 
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
			throw new Exception("AntecNoPatologicos.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAntecNoPatologicos();"; 
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
			throw new Exception("AntecNoPatologicos.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAntecNoPatologicos();"; 
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
        
        public void buscaAntecedenteNoPatologico(long foliopac) throws Exception{
        ArrayList rst = null;
	String sQuery = ""; 
         int n1=0;
         String cos;

		sQuery = "SELECT * FROM buscaAntecedentesNoPatologicosPac("+foliopac+");";
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()==1) { 
                 ArrayList vRowTemp = (ArrayList)rst.get(0); 
                 this.setReligion((String)vRowTemp.get(0)); 
                 cos=(String) vRowTemp.get( 1 );
                 this.setAlcoholismo(this.getBoolean2(cos));
                 this.setEscolaridad((String)vRowTemp.get(2)); 
                 this.setOcupacion((String)vRowTemp.get(3));
                 this.setAlcoholismo(this.getBoolean2((String)vRowTemp.get(4)));
                 this.setDrogas((String)vRowTemp.get(5));
                 this.setAguaPotable(this.getBoolean2((String)vRowTemp.get(6)));
                 this.setElectricidad(this.getBoolean2((String)vRowTemp.get(7) ));
                 this.setDrenaje(this.getBoolean2((String)vRowTemp.get(8)));
                 this.setAnimales((String)vRowTemp.get(9));
                 ///this.setTipoCasaHab((char) vRowTemp.get( 10 ));
                 this.setServSanit(this.getBoolean2((String)vRowTemp.get(11)));
                 this.setOtro((String)vRowTemp.get(12));                                 
	}   
       
  } 
    
        
	public boolean getAguaPotable() {
	return bAguaPotable;
	}

	public void setAguaPotable(boolean valor) {
	bAguaPotable=valor;
	}

	public boolean getAlcoholismo() {
	return bAlcoholismo;
	}

	public void setAlcoholismo(boolean valor) {
	bAlcoholismo=valor;
	}
        public String getValorAlcoholismo(){
            return this.getAlcoholismo() ? "SÍ" : "NO"; 
        }

	public boolean getDrenaje() {
	return bDrenaje;
	}

	public void setDrenaje(boolean valor) {
	bDrenaje=valor;
	}

	public boolean getElectricidad() {
	return bElectricidad;
	}

	public void setElectricidad(boolean valor) {
	bElectricidad=valor;
	}

	public boolean getServSanit() {
	return bServSanit;
	}

	public void setServSanit(boolean valor) {
	bServSanit=valor;
	}

	public boolean getTabaquismo() {
	return bTabaquismo;
	}

	public void setTabaquismo(boolean valor) {
	bTabaquismo=valor;
	}
        public String getValorTabaquismo(){
            return this.getTabaquismo() ? "SÍ" : "NO";
        }

	public String getAnimales() {
	return sAnimales;
	}

	public void setAnimales(String valor) {
	sAnimales=valor;
	}

	public String getDrogas() {
	return sDrogas;
	}

	public void setDrogas(String valor) {
	sDrogas=valor;
	}

	public String getEscolaridad() {
	return sEscolaridad;
	}

	public void setEscolaridad(String valor) {
	sEscolaridad=valor;
	}

	public String getOcupacion() {
	return sOcupacion;
	}

	public void setOcupacion(String valor) {
	sOcupacion=valor;
	}

	public String getOtro() {
	return sOtro;
	}

	public void setOtro(String valor) {
	sOtro=valor;
	}

	public String getReligion() {
	return sReligion;
	}

	public void setReligion(String valor) {
	sReligion=valor;
	}

	public char getTipoCasaHab() {
	return sTipoCasaHab;
	}

	public void setTipoCasaHab(char valor) {
	sTipoCasaHab=valor;
	}
        
        public boolean getConsumeDrogas(){
            return !(this.sDrogas == null || this.sDrogas.equals(""));
        }
    public String getValorConsumeDrogas(){
            return this.getConsumeDrogas() ? "SÍ" : "NO";
        }
    public String getAguaPotableCad() {
        return sAguaPotableCad;
    }

    public void setAguaPotableCad(String sAguaPotableCad) {
        this.sAguaPotableCad = sAguaPotableCad;
    }

    public String getAlcoholismoCad() {
        return sAlcoholismoCad;
    }

    public void setAlcoholismoCad(String sAlcoholismoCad) {
        this.sAlcoholismoCad = sAlcoholismoCad;
    }

    public String getDrenajeCad() {
        return sDrenajeCad;
    }

    public void setDrenajeCad(String sDrenajeCad) {
        this.sDrenajeCad = sDrenajeCad;
    }

    public String getElectricidadCad() {
        return sElectricidadCad;
    }

    public void setElectricidadCad(String sElectricidadCad) {
        this.sElectricidadCad = sElectricidadCad;
    }

    public String getServSanitCad() {
        return sServSanitCad;
    }

    public void setServSanitCad(String sServSanitCad) {
        this.sServSanitCad = sServSanitCad;
    }

    public String getTabaquismoCad() {
        return sTabaquismoCad;
    }

    public void setTabaquismoCad(String sTabaquismoCad) {
        this.sTabaquismoCad = sTabaquismoCad;
    }
    
    public boolean getBoolean2(String nume){
            return nume.compareTo("1")==0;
    }
} 
