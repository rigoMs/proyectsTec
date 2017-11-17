package mx.gob.hrrb.modelo.cxc;

import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class ServicioCobrable implements Serializable{
    protected AccesoDatos oAD;
    protected String sUsuarioFirmado;
    protected BigDecimal nCostoHospital;
    protected BigDecimal nPctIVA;
    protected ServicioRealizado oServicioRealizado;
    protected String sAbreviatura;
    protected String sClave;
    protected String sDescripcion;
    protected AgrupacionServ oAgrupacionServ;
	public ServicioCobrable(){
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
			throw new Exception("ServicioCobrable.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveServicioCobrable();"; 
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
	public ServicioCobrable[] buscarTodos() throws Exception{
	ServicioCobrable arrRet[]=null, oServicioCobrable=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosServicioCobrable();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ServicioCobrable[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
    
    
    public ServicioCobrable[] buscarServiciosCie10() throws Exception {
        ServicioCobrable arrRet[] = null;
        ServicioCobrable oServCob = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaServiciosParaCie10();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ServicioCobrable[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oServCob = new ServicioCobrable();
                oServCob.setClave(""+((Double)vRowTemp.get(2)).intValue());
                oServCob.setDescripcion((String)vRowTemp.get(1));
                
                arrRet[i]=oServCob;
            }
        }
        return arrRet;
    }
      
    
    public ServicioCobrable[] buscarServiciosCie9() throws Exception {
        ServicioCobrable arrRet[] = null;
        ServicioCobrable oServCob = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaServiciosParaCie9();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ServicioCobrable[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oServCob = new ServicioCobrable();
                oServCob.setClave(""+((Double)vRowTemp.get(0)).intValue());
                oServCob.setDescripcion((String)vRowTemp.get(3));
                
                arrRet[i]=oServCob;
            }
        }
        return arrRet;
    }

	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("ServicioCobrable.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaServicioCobrable('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ServicioCobrable.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaServicioCobrable('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ServicioCobrable.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaServicioCobrable('"+sUsuarioFirmado+"');"; 
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
	public BigDecimal getCostoHospital() {
	return nCostoHospital;
	}

	public void setCostoHospital(BigDecimal valor) {
	nCostoHospital=valor;
	}

	public BigDecimal getPctIVA() {
	return nPctIVA;
	}

	public void setPctIVA(BigDecimal valor) {
	nPctIVA=valor;
	}

	public ServicioRealizado getServicioRealizado() {
	return oServicioRealizado;
	}

	public void setServicioRealizado(ServicioRealizado valor) {
	oServicioRealizado=valor;
	}

    /**
     * @return the oAgrupacionServ
     */
    public AgrupacionServ getAgrupacionServ() {
        return oAgrupacionServ;
    }

    /**
     * @param oAgrupacionServ the oAgrupacionServ to set
     */
    public void setAgrupacionServ(AgrupacionServ oAgrupacionServ) {
        this.oAgrupacionServ = oAgrupacionServ;
    }

    public String getClave() {
        return sClave;
    }

    public void setClave(String sClave) {
        this.sClave = sClave;
    }

    public String getDescripcion() {
        return sDescripcion;
    }
    
    public void setDescripcion(String sDescripcion){
        this.sDescripcion = sDescripcion;
    }
    
    public String getAbreviatura() {
        return sAbreviatura;
    }
    public void setAbreviatura(String sAbreviatura) {
        this.sAbreviatura = sAbreviatura;
    }
} 
