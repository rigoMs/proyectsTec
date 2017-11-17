package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Atencion implements Serializable{
	private AccesoDatos oAD;
	private String bMinisterioPublico;
	private Date dFechaAtencion;
        private Date dAtnUrgs;
        private String dHoraAtencion;
	private String dHoraLesionUrgs;
	private Parametrizacion oDestinoDespAtencion;
	private Parametrizacion oReferidoPor;
	private Parametrizacion oServicioAtencion;
	private Parametrizacion oTipoAtencion;

        public Atencion(){
            dFechaAtencion=null;
            oDestinoDespAtencion= new Parametrizacion();
            oReferidoPor= new Parametrizacion();
            oServicioAtencion= new Parametrizacion();
            oTipoAtencion= new Parametrizacion();
        }
        
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Atencion.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAtencion();"; 
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
	public Atencion[] buscarTodos() throws Exception{
	Atencion arrRet[]=null, oAtencion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAtencion();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Atencion[rst.size()];
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
			throw new Exception("Atencion.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAtencion();"; 
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
			throw new Exception("Atencion.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAtencion();"; 
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
			throw new Exception("Atencion.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAtencion();"; 
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
	public String getMinisterioPublico() {
	return bMinisterioPublico;
	}

	public void setMinisterioPublico(String valor) {
	bMinisterioPublico=valor;
	}

	public Date getFechaAtencion() {
	return dFechaAtencion;
	}

	public void setFechaAtencion(Date valor) {
	dFechaAtencion=valor;
	}

	public String getHoraLesionUrgs() {
	return dHoraLesionUrgs;
	}

	public void setHoraLesionUrgs(String valor) {
	dHoraLesionUrgs=valor;
	}

	public Parametrizacion getDestinoDespAtencion() {
	return oDestinoDespAtencion;
	}

	public void setDestinoDespAtencion(Parametrizacion valor) {
	oDestinoDespAtencion=valor;
	}

	public Parametrizacion getReferidoPor() {
	return oReferidoPor;
	}

	public void setReferidoPor(Parametrizacion valor) {
	oReferidoPor=valor;
	}

	public Parametrizacion getServicioAtencion() {
	return oServicioAtencion;
	}

	public void setServicioAtencion(Parametrizacion valor) {
	oServicioAtencion=valor;
	}

	public Parametrizacion getTipoAtencion() {
	return oTipoAtencion;
	}

	public void setTipoAtencion(Parametrizacion valor) {
	oTipoAtencion=valor;
	}

    /**
     * @return the dHoraAtencion
     */
    public String getHoraAtencion() {
        return dHoraAtencion;
    }

    /**
     * @param dHoraAtencion the dHoraAtencion to set
     */
    public void setHoraAtencion(String dHoraAtencion) {
        this.dHoraAtencion = dHoraAtencion;
    }

    /**
     * @return the dAtnUrgs
     */
    public Date getAtnUrgs() {
        return dAtnUrgs;
    }

    /**
     * @param dAtnUrgs the dAtnUrgs to set
     */
    public void setAtnUrgs(Date dAtnUrgs) {
        this.dAtnUrgs = dAtnUrgs;
    }

} 
