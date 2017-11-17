package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class Soluciones implements Serializable{
    
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dHoraAplicacion;
	private int nCantidad;
	private long nClaveSolucion;
	private String sSolucion;
	private HojaEnfermeriaQuirofano oHojaEnfermeriaQuirofano;

	public Soluciones(){
            //dHoraAplicacion= new Date();            
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Soluciones.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSoluciones();"; 
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
	public Soluciones[] buscarTodos() throws Exception{
	Soluciones arrRet[]=null, oSoluciones=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSoluciones();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Soluciones[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Soluciones.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSoluciones('"+sUsuarioFirmado+"');"; 
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
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Soluciones.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSoluciones('"+sUsuarioFirmado+"');"; 
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
	
	public Date getHoraAplicacion() {
            return dHoraAplicacion;
	}

	public void setHoraAplicacion(Date valor) {
            this.dHoraAplicacion=valor;
	}

	public int getCantidad() {
            return nCantidad;
	}

	public void setCantidad(int valor) {
            this.nCantidad=valor;
	}

	public long getClaveSolucion() {
            return nClaveSolucion;
	}

	public void setClaveSolucion(long valor) {
            this.nClaveSolucion=valor;
	}

	public String getSolucion() {
            return sSolucion;
	}

	public void setSolucion(String valor) {
            this.sSolucion=valor;
	}

	public HojaEnfermeriaQuirofano getHojaEnfermeriaQuirofano() {
            return oHojaEnfermeriaQuirofano;
	}

	public void setHojaEnfermeriaQuirofano(HojaEnfermeriaQuirofano valor) {
            this.oHojaEnfermeriaQuirofano=valor;
	}
        
        public String getHoraAplicacionString(){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(this.getHoraAplicacion());            
        }

} 
