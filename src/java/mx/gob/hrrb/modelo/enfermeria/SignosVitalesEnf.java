package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.Parametrizacion;
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

public  class SignosVitalesEnf implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;        
	private Parametrizacion oTipoSigno;// si se ocupa para las siguientes hojas de enfermeria Hosp, UCI;
        private String sSignoValor;
        private String sTA;
        private String sFR;
        private String sPulso;
        private String sTemperatura;
        private String sSatOxigeno;        
        private String sValor;		
	private HojaEnfermeriaQuirofano oHojaEnfermeriaQuirofano;	

	public SignosVitalesEnf(){
            oTipoSigno= new Parametrizacion();	  
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SignosVitalesEnf.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSignosVitalesEnf('"+sUsuarioFirmado+"');"; 
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
		

    public HojaEnfermeriaQuirofano getHojaEnfermeriaQuirofano() {
        return oHojaEnfermeriaQuirofano;
    }

    public void setHojaEnfermeriaQuirofano(HojaEnfermeriaQuirofano valor) {
        this.oHojaEnfermeriaQuirofano=valor;
    }

    public String getTa() {
        return sTA;
    }

    public void setTa(String sTA) {
        this.sTA = sTA;
    }

    public String getFr() {
        return sFR;
    }

    public void setFr(String sFR) {
        this.sFR = sFR;
    }

    public String getPulso() {
        return sPulso;
    }

    public void setPulso(String sPulso) {
        this.sPulso = sPulso;
    }

    public String getTemperatura() {
        return sTemperatura;
    }

    public void setTemperatura(String sTemperatura) {
        this.sTemperatura = sTemperatura;
    }

    public String getSatOxigeno() {
        return sSatOxigeno;
    }

    public void setSatOxigeno(String sSatOxigeno) {
        this.sSatOxigeno = sSatOxigeno;
    }

    public Parametrizacion getTipoSigno() {
        return oTipoSigno;
    }

    public void setTipoSigno(Parametrizacion oTipoSigno) {
        this.oTipoSigno = oTipoSigno;
    }

    public String getSignoValor() {
        return sSignoValor;
    }

    public void setSignoValor(String sSignoValor) {
        this.sSignoValor = sSignoValor;
    }

   
    public String getValor() {
        return sValor;
    }


    public void setValor(String sValor) {
        this.sValor = sValor;
    }

	

} 
