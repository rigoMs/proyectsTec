package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class Dietas implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nFolioDieta;	
	private String sNombre;
        private String sEpecificacion;
       
	public Dietas(){
            
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
			throw new Exception("Dietas.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDietas();"; 
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
        
	public Dietas[] buscarTodosLasDietas() throws Exception{            
            Dietas arrRet[]=null, oDietas=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
            sQuery = "SELECT * FROM BuscaDietas();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new Dietas[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oDietas = new Dietas();
                        oDietas.setFolioDieta(((Double)vRowTemp.get(0)).intValue());
                        oDietas.setNombre((String)vRowTemp.get(1));
                        oDietas.setEpecificacion((String)vRowTemp.get(2));
                     arrRet[i]=oDietas;    
                    } 
            } 
            return arrRet; 
	} 
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Dietas.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaDietas('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Dietas.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaDietas('"+sUsuarioFirmado+"');"; 
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
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Dietas.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaDietas('"+sUsuarioFirmado+"');"; 
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
	public int getFolioDieta() {
            return nFolioDieta;
	}

	public void setFolioDieta(int valor) {
            this.nFolioDieta=valor;
	}

	public String getEpecificacion() {
            return sEpecificacion;
	}

	public void setEpecificacion(String valor) {
            this.sEpecificacion=valor;
	}

	public String getNombre() {
            return sNombre;
	}

	public void setNombre(String valor) {
            this.sNombre=valor;
	}

} 
