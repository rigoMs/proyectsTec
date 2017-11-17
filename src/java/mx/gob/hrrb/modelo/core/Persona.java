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

public class Persona implements Serializable{
	protected AccesoDatos oAD;
	protected Date dFechaNac;
	protected int nEdad;
	protected Parametrizacion oEstadoCivil;
	protected String oEdoCivil;
	protected String sApMaterno;
	protected String sApPaterno;
	protected String sCurp;
	protected String sNombres;
	protected Parametrizacion oSexo;
	protected String sTelefono;
    protected String strFecha;
    protected String sSexoP;
    protected String sSexo;
    protected String sCp;
    protected String edoCivilStr;
        
    public Persona(Date dFechaNac, int nEdad, Parametrizacion oEstadoCivil, String sApMaterno, String sApPaterno, 
                String sCurp, String sNombres, String sTelefono, String sSexo){
            this.dFechaNac=dFechaNac;
            this.nEdad=nEdad;
            this.oEstadoCivil=oEstadoCivil;
            this.sApMaterno=sApMaterno;
            this.sApPaterno=sApPaterno;
            this.sCurp=sCurp;
            this.sNombres=sNombres;
            this.sTelefono=sTelefono;
            this.sSexo=sSexo;
        }

    public Persona() {
     oEstadoCivil=new Parametrizacion();
     nEdad=0;
	 sApMaterno="";
	 sApPaterno="";
	 sCurp="";
	 sNombres="";
	 sTelefono="";
     strFecha="";
     sSexoP="";
    }

    	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Persona.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePersona();"; 
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
	public Persona[] buscarTodos() throws Exception{
	Persona arrRet[]=null, oPersona=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPersona();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Persona[rst.size()];
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
			throw new Exception("Persona.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPersona();"; 
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
			throw new Exception("Persona.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPersona();"; 
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
			throw new Exception("Persona.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPersona();"; 
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
        
        
        
	public Date getFechaNac() {
	return dFechaNac;
	}

	public void setFechaNac(Date valor) {
	dFechaNac=valor;
	}

	public int getEdad() {
	return nEdad;
	}

	public void setEdad(int valor) {
	nEdad=valor;
	}

	public Parametrizacion getEstadoCivil() {
	return oEstadoCivil;
	}

	public void setEstadoCivil(Parametrizacion valor) {
	oEstadoCivil=valor;
	}

	public String getApMaterno() {
	return sApMaterno;
	}

	public void setApMaterno(String valor) {
	sApMaterno=valor;
	}

	public String getApPaterno() {
	return sApPaterno;
	}

	public void setApPaterno(String valor) {
	sApPaterno=valor;
	}

	public String getCurp() {
	return sCurp;
	}

	public void setCurp(String valor) {
	sCurp=valor.toUpperCase();
	}

	public String getNombres() {
	return sNombres;
	}

	public void setNombres(String valor) {
	sNombres=valor;
	}

	public Parametrizacion getSexo() {
	return oSexo;
	}

	public void setSexo(Parametrizacion valor) {
	oSexo=valor;
	}

	public String getTelefono() {
	return sTelefono;
	}

	public void setTelefono(String valor) {
	sTelefono=valor;
	}

    public String getStrFecha() {
        System.out.println("Fecha: "+strFecha);
        return strFecha;
    }

    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
    }

    public String getSexoP() {
        return sSexoP;
    }

    public void setSexoP(String sSexoP) {
        this.sSexoP = sSexoP;
    }

    public String getSexos() {
        return sSexo;
    }

    public void setSexos(String sSexo) {
        this.sSexo = sSexo;
    }

    public String getCp() {
        return sCp;
    }

    public void setCp(String sCp) {
        this.sCp = sCp;
    }

    public String getNombreCompleto(){
        return this.sNombres+" " + this.sApPaterno + " " + this.sApMaterno;
    }

    public String getEdoCivil() {     
        return oEdoCivil.substring(3,4);
    }

    public void setEdoCivil(String oEdoCivil) {
        this.oEdoCivil = oEdoCivil;
    }

    public String getEdoCivilStr() {
        return edoCivilStr;
    }

    public void setEdoCivilStr(String edoCivilStr) {
        this.edoCivilStr = edoCivilStr;
    }
} 
