package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/
public class Consultorio implements Serializable{
	private AccesoDatos oAD;
	private int nNoConsultorio;
        private int nAreaServicio;
	private String sNomConsultorio;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;

        public Consultorio(){
        nNoConsultorio=1;
        oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
        }
        
	public boolean buscar(int numero, int area) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Consultorio.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveConsultorio("+numero+"::smallint, "+area+"::smallint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setNoConsultorio(((Double)vRowTemp.get(0)).intValue());
                                setAreaServicio(((Double)vRowTemp.get(1)).intValue());
                                setNomConsultorio((String)vRowTemp.get(2));
				bRet = true;
			}
		} 
		return bRet; 
	}
        
	public Consultorio[] buscarTodos() throws Exception{
	Consultorio arrRet[]=null, oConsultorio=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosConsultorio();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Consultorio[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar(int nNumero, int nArea, String sNombre) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
        String sLlave=nNumero+", "+nArea;
	String sQuery = "";
		 if( this==null){   //ESTE PROCEDIMIENTO NO TIENE USUARIO PORQUE FALTA LOGIN
			throw new Exception("Consultorio.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaConsultorioCE('"+sUsuario+"', "+nNumero+"::smallint, "+nArea+"::smallint, '"+sNombre+"', '"+sLlave+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = Integer.parseInt(rst.get(0).toString().substring(1, 2));
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
			throw new Exception("Consultorio.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaConsultorio();"; 
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
			throw new Exception("Consultorio.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaConsultorio();"; 
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
	public int getNoConsultorio() {
	return nNoConsultorio;
	}

	public void setNoConsultorio(int valor) {
	nNoConsultorio=valor;
	}

	public String getNomConsultorio() {
	return sNomConsultorio;
	}

	public void setNomConsultorio(String valor) {
	sNomConsultorio=valor;
	}

    public int getAreaServicio() {
        return nAreaServicio;
    }

    public void setAreaServicio(int nAreaServicio) {
        this.nAreaServicio = nAreaServicio;
    }

} 
