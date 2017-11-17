package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class ControlLiquidos implements Serializable{
	
        private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nDiuresis;	
	private int nSangrado;        
	private int nTotalEgresos;
        private long nIdEgresos;
	private Parametrizacion oTipoControl;
	private String sOtros;
	private HojaEnfermeriaQuirofano oHojaEnfermeriaQuirofano;
        private final String sTablaControl="TAK";
        
       

	public ControlLiquidos(){
            oTipoControl = new Parametrizacion();           
            
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
			throw new Exception("ControlLiquidos.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveControlLiquidos();"; 
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
	public ControlLiquidos[] buscarTodos() throws Exception{
	ControlLiquidos arrRet[]=null, oControlLiquidos=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosControlLiquidos();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ControlLiquidos[rst.size()];
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
			throw new Exception("ControlLiquidos.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaControlLiquidos('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ControlLiquidos.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaControlLiquidos('"+sUsuarioFirmado+"');"; 
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
	public int getDiuresis() {
            return nDiuresis;
	}

	public void setDiuresis(int valor) {
            this.nDiuresis=valor;
	}

	public long getIdEgresos() {
            return nIdEgresos;
	}

	public void setIdEgresos(long valor) {
            this.nIdEgresos=valor;
	}

	public int getSangrado() {
            return nSangrado;
	}

	public void setSangrado(int valor) {
            this.nSangrado=valor;
	}

	public int getTotalEgresos() {
            return nTotalEgresos;
	}

	public void setTotalEgresos(int valor) {
            this.nTotalEgresos=valor;
	}

	public Parametrizacion getTipoControl() {
            return oTipoControl;
	}

	public void setTipoControl(Parametrizacion valor) {
            this.oTipoControl=valor;
	}

	public String getOtros() {
            return sOtros;
	}

	public void setOtros(String valor) {
            this.sOtros=valor;
	}

	public HojaEnfermeriaQuirofano getHojaEnfermeriaQuirofano() {
            return oHojaEnfermeriaQuirofano;
	}

	public void setHojaEnfermeriaQuirofano(HojaEnfermeriaQuirofano valor) {
            this.oHojaEnfermeriaQuirofano=valor;
	}

    public List<Parametrizacion> getListaControl() {
           List<Parametrizacion> lListaControl=null;
            try {
               lListaControl=  new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion()).buscarTabla(sTablaControl)));
            } catch (Exception ex) {
                Logger.getLogger(ControlLiquidos.class.getName()).log(Level.SEVERE, null, ex);
            }
            return lListaControl;
    }
    
    public void getBuscaClave(String sValor){
        for(Parametrizacion oPa: this.getListaControl()){
            if(oPa.getValor().equals(sValor)){
               this.getTipoControl().setClaveParametro(oPa.getClaveParametro());
               this.getTipoControl().setTipoParametro(oPa.getTipoParametro());
               break;
            }
        }        
    }

   

} 
