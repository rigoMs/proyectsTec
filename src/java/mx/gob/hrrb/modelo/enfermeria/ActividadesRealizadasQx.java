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

public  class ActividadesRealizadasQx implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dHoraActividad;
	private long nIdAct;
	private String sActividad;
	private HojaEnfermeriaQuirofano oHojaEnfermeriaQuirofano;

	public ActividadesRealizadasQx(){
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
			throw new Exception("ActividadesRealizadasQx.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveActividadesRealizadasQx();"; 
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
			throw new Exception("ActividadesRealizadasQx.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaActividadesRealizadasQx('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ActividadesRealizadasQx.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaActividadesRealizadasQx('"+sUsuarioFirmado+"');"; 
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
        
	public Date getHoraActividad() {
            return dHoraActividad;
	}

	public void setHoraActividad(Date valor) {
            this.dHoraActividad=valor;
	}

	public long getIdAct() {
            return nIdAct;
	}

	public void setIdAct(long valor) {
            this.nIdAct=valor;
	}

	public String getActividad() {
            return sActividad;
	}

	public void setActividad(String valor) {
            this.sActividad=valor;
	}

	public HojaEnfermeriaQuirofano getHojaEnfermeriaQuirofano() {
            return oHojaEnfermeriaQuirofano;
	}

	public void setHojaEnfermeriaQuirofano(HojaEnfermeriaQuirofano valor) {
            this.oHojaEnfermeriaQuirofano=valor;
	}
        
        public String getHoraActividadString(){           
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");            
            return format.format(this.getHoraActividad());
        }

} 
