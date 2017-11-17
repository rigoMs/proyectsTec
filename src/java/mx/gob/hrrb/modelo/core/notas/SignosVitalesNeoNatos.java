package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.SignosVitales;
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

public  class SignosVitalesNeoNatos extends SignosVitales implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private float nPeso;
	private String sObservaciones;
	private String sPa;
	private String sPc;
	private String sPie;
	private String sPlan;
	private String sPt;
	private AtencionNeonatalTococirugia oAtencionNeonatalTococirugia;
	private HistoriaClinicaPerinatal oHistoriaClinicaPerinatal;

	public SignosVitalesNeoNatos(){
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
			throw new Exception("SignosVitalesNeoNatos.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSignosVitalesNeoNatos();"; 
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
	public SignosVitalesNeoNatos[] buscarTodos() throws Exception{
	SignosVitalesNeoNatos arrRet[]=null, oSignosVitalesNeoNatos=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSignosVitalesNeoNatos();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new SignosVitalesNeoNatos[rst.size()];
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
			throw new Exception("SignosVitalesNeoNatos.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSignosVitalesNeoNatos('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("SignosVitalesNeoNatos.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSignosVitalesNeoNatos('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("SignosVitalesNeoNatos.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSignosVitalesNeoNatos('"+sUsuarioFirmado+"');"; 
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
        //**************INCIAN METODOS DE CONTROL DE DATOS**************
        public String getQuerySingnosViatalesNeonatos(){
            String sQuery = "";
            
            return sQuery;
        }
        //**************TERMINAN METODOS DE CONTROL DE DATOS METODOS DE CONTROL DE DATOS**************
	public float getPeso() {
	return nPeso;
	}

	public void setPeso(float valor) {
	nPeso=valor;        
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public String getPa() {
	return sPa;
	}

	public void setPa(String valor) {
	sPa=valor;
	}

	public String getPc() {
	return sPc;
	}

	public void setPc(String valor) {
	sPc=valor;
	}

	public String getPie() {
	return sPie;
	}

	public void setPie(String valor) {
	sPie=valor;
	}

	public String getPlan() {
	return sPlan;
	}

	public void setPlan(String valor) {
	sPlan=valor;
	}

	public String getPt() {
	return sPt;
	}

	public void setPt(String valor) {
	sPt=valor;
	}

	public AtencionNeonatalTococirugia getAtencionNeonatalTococirugia() {
	return oAtencionNeonatalTococirugia;
	}

	public void setAtencionNeonatalTococirugia(AtencionNeonatalTococirugia valor) {
	oAtencionNeonatalTococirugia=valor;
	}

	public HistoriaClinicaPerinatal getHistoriaClinicaPerinatal() {
	return oHistoriaClinicaPerinatal;
	}

	public void setHistoriaClinicaPerinatal(HistoriaClinicaPerinatal valor) {
	oHistoriaClinicaPerinatal=valor;
	}

} 
