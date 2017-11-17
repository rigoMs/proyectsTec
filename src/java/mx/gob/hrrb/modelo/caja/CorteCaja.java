package mx.gob.hrrb.modelo.caja;

import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Turno;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class CorteCaja implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaCorte;
	private PersonalHospitalario oCajero;
	private Caja oCaja;
        private BigDecimal nMontoCuotaRecup;
        private Turno oTurno;

	public CorteCaja(){
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
			throw new Exception("CorteCaja.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCorteCaja();"; 
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
	public CorteCaja[] buscarTodos() throws Exception{
	CorteCaja arrRet[]=null, oCorteCaja=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCorteCaja();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CorteCaja[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
    public CorteCaja[] buscarInformeDiario(Date dFecha) throws Exception{
	CorteCaja arrRet[]=null, oCorteCaja=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sQuery = "SELECT * FROM buscaInformeDiarioIngresosCuotasRecup('"+format.format(dFecha)+"'::date);"; 
	System.out.println(sQuery);
        oAD=new AccesoDatos(); 
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new CorteCaja[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oCorteCaja=new CorteCaja();
                oCorteCaja.setCaja(new Caja());
                oCorteCaja.getCaja().setTipoCaja(new Parametrizacion());
                oCorteCaja.setCajero(new PersonalHospitalario());
                oCorteCaja.setTurno(new Turno());
                
                oCorteCaja.setMontoCuotaRecup((BigDecimal.valueOf((Double)vRowTemp.get(0))));
                oCorteCaja.getCajero().setNombres((String)vRowTemp.get(1));
                oCorteCaja.getCajero().setApPaterno((String)vRowTemp.get(2));
                oCorteCaja.getCajero().setApMaterno((String)vRowTemp.get(3));
                oCorteCaja.getTurno().setClave((String)vRowTemp.get(4));
                oCorteCaja.getCaja().getTipoCaja().setTipoParametro((String)vRowTemp.get(5));
                arrRet[i]=oCorteCaja;
            } 
	} 
	return arrRet; 
    } 
    
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CorteCaja.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCorteCaja('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("CorteCaja.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCorteCaja('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("CorteCaja.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCorteCaja('"+sUsuarioFirmado+"');"; 
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
	public Date getFechaCorte() {
	return dFechaCorte;
	}

	public void setFechaCorte(Date valor) {
	dFechaCorte=valor;
	}
        
	public PersonalHospitalario getCajero() {
	return oCajero;
	}

	public void setCajero(PersonalHospitalario valor) {
	oCajero=valor;
	}

	public Caja getCaja() {
	return oCaja;
	}

	public void setCaja(Caja valor) {
	oCaja=valor;
	}

    public BigDecimal getMontoCuotaRecup() {
        return nMontoCuotaRecup;
    }

    public void setMontoCuotaRecup(BigDecimal nMontoCuotaRecup) {
        this.nMontoCuotaRecup = nMontoCuotaRecup;
    }

    public Turno getTurno() {
        return oTurno;
    }

    public void setTurno(Turno oTurno) {
        this.oTurno = oTurno;
    }
} 
