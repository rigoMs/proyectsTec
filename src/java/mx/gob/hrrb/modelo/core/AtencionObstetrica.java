package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AtencionObstetrica implements Serializable{
	private AccesoDatos oAD;
	private String sExtraccion;
	private int nSemanasGestacion;
	private Parametrizacion oTipoNacimiento;
	private char sConProducto;
	private char sTipoAtencion;
	private String sTipoNacimientoP;
    private ArrayList Producto;
    private AntecGinecoObstetricos oAntecGinecoObstetricos;
    private Producto oP1;
    private Producto oP2;
    private Producto oP3;

    public AtencionObstetrica(){
            oAntecGinecoObstetricos = new AntecGinecoObstetricos();
            oP1 = new Producto();
            oP2 = new Producto();
            oP3 = new Producto();
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AtencionObstetrica.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAtencionObstetrica();"; 
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
	public AtencionObstetrica[] buscarTodos() throws Exception{
	AtencionObstetrica arrRet[]=null, oAtencionObstetrica=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAtencionObstetrica();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AtencionObstetrica[rst.size()];
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
			throw new Exception("AtencionObstetrica.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAtencionObstetrica();"; 
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
			throw new Exception("AtencionObstetrica.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAtencionObstetrica();"; 
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
			throw new Exception("AtencionObstetrica.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAtencionObstetrica();"; 
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
	public String getExtraccion() {
	return sExtraccion;
	}

	public void setExtraccion(String valor) {
	sExtraccion=valor;
	}

	public int getSemanasGestacion() {
	return nSemanasGestacion;
	}

	public void setSemanasGestacion(int valor) {
	nSemanasGestacion=valor;
	}

	public Parametrizacion getTipoNacimiento() {
	return oTipoNacimiento;
	}

	public void setTipoNacimiento(Parametrizacion valor) {
	oTipoNacimiento=valor;
	}

	public char getConProducto() {
	return sConProducto;
	}

	public void setConProducto(char valor) {
	sConProducto=valor;
	}

	public char getTipoAtencion() {
	return sTipoAtencion;
	}

	public void setTipoAtencion(char valor) {
	sTipoAtencion=valor;
	}

	 public String getTipoNacimientoP() {
        return sTipoNacimientoP;
    }

    public void setTipoNacimientoP(String sTipoNacimientoP) {
        this.sTipoNacimientoP = sTipoNacimientoP;
    }

    public AntecGinecoObstetricos getAntecGinecoObstetricos() {
        return oAntecGinecoObstetricos;
    }

    public void setAntecGinecoObstetricos(AntecGinecoObstetricos oAntecGinecoObstetricos) {
        this.oAntecGinecoObstetricos = oAntecGinecoObstetricos;
    }

    public Producto getP1() {
        return oP1;
    }

    public void setP1(Producto oP1) {
        this.oP1 = oP1;
    }

    public Producto getP2() {
        return oP2;
    }

    public void setP2(Producto oP2) {
        this.oP2 = oP2;
    }

    public Producto getP3() {
        return oP3;
    }

    public void setP3(Producto oP3) {
        this.oP3 = oP3;
    }
} 
