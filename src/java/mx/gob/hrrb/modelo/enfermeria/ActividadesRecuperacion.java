package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.Turno;
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

public  class ActividadesRecuperacion implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
        
	private Date dHoraRecuperacion;
	private long nIdRecuperacion;
	private Turno oTurno;
	private String sRecuperacion;
        
	private HojaEnfermeriaQuirofano oHojaEnfermeriaQuirofano;

	public ActividadesRecuperacion(){
            oTurno= new Turno();
            
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
			throw new Exception("ActividadesRecuperacion.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveActividadesRecuperacion();"; 
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
	
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("ActividadesRecuperacion.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaActividadesRecuperacion('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ActividadesRecuperacion.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaActividadesRecuperacion('"+sUsuarioFirmado+"');"; 
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
	
	public Date getHoraRecuperacion() {
            return dHoraRecuperacion;
	}

	public void setHoraRecuperacion(Date valor) {
            this.dHoraRecuperacion=valor;
	}

	public long getIdRecuperacion() {
            return nIdRecuperacion;
	}

	public void setIdRecuperacion(long valor) {
            this.nIdRecuperacion=valor;
	}

	public Turno getTurno() {
            return oTurno;
	}

	public void setTurno(Turno valor) {
            this.oTurno=valor;
	}

	public String getRecuperacion() {
            return sRecuperacion;
	}

	public void setRecuperacion(String valor) {
            this.sRecuperacion=valor;
	}

	public HojaEnfermeriaQuirofano getHojaEnfermeriaQuirofano() {
            return oHojaEnfermeriaQuirofano;
	}

	public void setHojaEnfermeriaQuirofano(HojaEnfermeriaQuirofano valor) {
            this.oHojaEnfermeriaQuirofano=valor;
	}
        
        public String getHoraRecuString(){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(dHoraRecuperacion);
        }
        
       

} 
