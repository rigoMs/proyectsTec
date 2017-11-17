package mx.gob.hrrb.modelo.caja;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Turno;
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

public  class Caja implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
        private int nIdCaja;
	private Parametrizacion oMaxCajerosTipo;
	private Parametrizacion oTipoCaja;
	private Turno oTurno;
	private ArrayList<Recibo> arrRecibo;
        private ArrayList<CorteCaja> arrCorteCaja;
        
	public Caja(){
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
			throw new Exception("Caja.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCaja();"; 
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
        
    public boolean buscarCajaGeneral() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
	sQuery = "SELECT * FROM buscaCajaGeneral();"; 
	oAD=new AccesoDatos(); 
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null && rst.size() == 1) {
            this.setTipoCaja(new Parametrizacion());
            ArrayList vRowTemp = (ArrayList)rst.get(0);                            
            this.setIdCaja(((Double) vRowTemp.get(0)).intValue());
            this.getTipoCaja().setClaveParametro((String)vRowTemp.get(1));
            this.getTipoCaja().setTipoParametro((String)vRowTemp.get(2));
            this.getTipoCaja().setValor((String)vRowTemp.get(3));
            bRet = true;
	}
	return bRet; 
    } 
	
	public Caja[] buscarCajasAuxiliares() throws Exception{
	Caja arrRet[]=null, oCaja=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaCajasAuxiliares();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Caja[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oCaja=new Caja();
                            oCaja.setTipoCaja(new Parametrizacion());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);                            
                            oCaja.setIdCaja(((Double) vRowTemp.get(0)).intValue());
                            oCaja.getTipoCaja().setClaveParametro((String)vRowTemp.get(1));
                            oCaja.getTipoCaja().setTipoParametro((String)vRowTemp.get(2));
                            oCaja.getTipoCaja().setValor((String)vRowTemp.get(3));
                            arrRet[i]=oCaja;
			} 
		} 
		return arrRet; 
	} 
	
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Caja.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCaja('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Caja.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCaja('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Caja.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCaja('"+sUsuarioFirmado+"');"; 
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
        
        public void buscaIdCaja() throws Exception{
            boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Caja.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaIdCaja('"+this.getTipoCaja().getTipoParametro()+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setIdCaja(((Double)vRowTemp.get(0)).intValue());
				bRet = true;
			}
		} 
        }
        
        public void equalsCaja(Caja arrCaja[]){
            for(Caja caja:arrCaja){
                if(this.getIdCaja()==caja.getIdCaja()){
                    this.setIdCaja(caja.getIdCaja());
                    this.setTipoCaja(caja.getTipoCaja());
                }
            }
        }
	
	public Parametrizacion getMaxCajerosTipo() {
	return oMaxCajerosTipo;
	}

	public void setMaxCajerosTipo(Parametrizacion valor) {
	oMaxCajerosTipo=valor;
	}

	public Parametrizacion getTipoCaja() {
	return oTipoCaja;
	}

	public void setTipoCaja(Parametrizacion valor) {
	oTipoCaja=valor;
	}

	public Turno getTurno() {
	return oTurno;
	}

	public void setTurno(Turno valor) {
	oTurno=valor;
	}

    public int getIdCaja() {
        return nIdCaja;
    }

    public void setIdCaja(int nIdCaja) {
        this.nIdCaja = nIdCaja;
    }

    public ArrayList<CorteCaja> getCorteCaja() {
        return arrCorteCaja;
    }

    public void setCorteCaja(ArrayList<CorteCaja> oCorteCaja) {
        this.arrCorteCaja = oCorteCaja;
    }
} 
