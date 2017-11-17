package mx.gob.hrrb.modelo.caja;

import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Parametrizacion;
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

public  class FoliosCaja implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaAsignacion;
	private int nFolioFinal;
	private int nFolioInicial;
	private PersonalHospitalario oRecibe;
	private Parametrizacion oTipoRecibo;
	private Caja oCaja;

	public FoliosCaja(){
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
			throw new Exception("FoliosCaja.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveFoliosCaja();"; 
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
	public FoliosCaja[] buscarTodos() throws Exception{
	FoliosCaja arrRet[]=null, oFoliosCaja=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosFoliosCaja();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new FoliosCaja[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
        public FoliosCaja[] buscarExistenciaRecibos() throws Exception{
	FoliosCaja arrRet[]=null, oFoliosCaja=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaExistenciaRecibos("+this.oCaja.getIdCaja()+"::smallint,'"+this.oTipoRecibo.getTipoParametro()+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new FoliosCaja[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFoliosCaja=new FoliosCaja();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oFoliosCaja.setFolioInicial(((Double) vRowTemp.get(0)).intValue());
                            oFoliosCaja.setFolioFinal(((Double) vRowTemp.get(1)).intValue());
                            arrRet[i]=oFoliosCaja;
			} 
		} 
		return arrRet; 
	} 
        
        public FoliosCaja[] buscarEntregaRecibos(Date dFechaI,Date dFechaF,String sTipoRec) throws Exception{
	FoliosCaja arrRet[]=null, oFoliosCaja=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sQuery = "SELECT * FROM buscaEntregaRecibos('"+format.format(dFechaI)+"'::date,'"+format.format(dFechaF)+"'::date,'"+sTipoRec+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new FoliosCaja[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFoliosCaja=new FoliosCaja();
                            oFoliosCaja.setRecibe(new PersonalHospitalario());
                            oFoliosCaja.setCaja(new Caja());
                            oFoliosCaja.getCaja().setTipoCaja(new Parametrizacion());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oFoliosCaja.setFechaAsignacion((Date)vRowTemp.get(0));
                            oFoliosCaja.setFolioInicial(((Double) vRowTemp.get(1)).intValue());
                            oFoliosCaja.setFolioFinal(((Double) vRowTemp.get(2)).intValue());
                            oFoliosCaja.getRecibe().setNombres((String)vRowTemp.get(3));
                            oFoliosCaja.getRecibe().setApPaterno((String)vRowTemp.get(4));
                            oFoliosCaja.getRecibe().setApMaterno((String)vRowTemp.get(5));
                            oFoliosCaja.getCaja().getTipoCaja().setValor((String)vRowTemp.get(6));
                            arrRet[i]=oFoliosCaja;
			} 
		} 
		return arrRet; 
	}
        
        public FoliosCaja[] buscaFoliosCajaPorTipoRecibo() throws Exception{
	FoliosCaja arrRet[]=null, oFoliosCaja=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaFoliosCajaPorTipoRecibo('"+this.getTipoRecibo().getTipoParametro()+"'::character(3));"; 
		oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new FoliosCaja[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFoliosCaja=new FoliosCaja();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oFoliosCaja.setFolioInicial(((Double) vRowTemp.get(0)).intValue());
                            oFoliosCaja.setFolioFinal(((Double) vRowTemp.get(1)).intValue());
                            arrRet[i]=oFoliosCaja;
			} 
		} 
		return arrRet; 
	}
        
    public FoliosCaja[] buscarFoliosUtilizadosDuranteTurno(Date dFecha,String sCveTurno) throws Exception{
        FoliosCaja arrRet[]=null, oFC=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	sQuery = "SELECT * FROM buscaFoliosCajaUtilizadosTurno('"+format.format(dFecha)+"'::date,"+this.getCaja().getIdCaja()+"::smallint,'"+sCveTurno+"'::character(3),'"+this.getTipoRecibo().getTipoParametro()+"'::character(3));"; 
        oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new FoliosCaja[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oFC=new FoliosCaja();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oFC.setFolioInicial(((Double) vRowTemp.get(0)).intValue());
                oFC.setFolioFinal(((Double) vRowTemp.get(1)).intValue());
                arrRet[i]=oFC;
            } 
	} 
	return arrRet; 
    }
    
    public FoliosCaja[] buscarFoliosExistentesDespuesTurno(Date dFecha,String sCveTurno) throws Exception{
        FoliosCaja arrRet[]=null, oFC=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	sQuery = "SELECT * FROM buscaFoliosCajaExistentesDespuesTurno('"+isNull(format.format(dFecha))+"'::date,"+this.getCaja().getIdCaja()+"::smallint,'"+isNull(sCveTurno)+"'::character(3),'"+this.getTipoRecibo().getTipoParametro()+"'::character(3));"; 
        oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new FoliosCaja[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oFC=new FoliosCaja();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oFC.setFolioInicial(((Double) vRowTemp.get(0)).intValue());
                oFC.setFolioFinal(((Double) vRowTemp.get(1)).intValue());
                arrRet[i]=oFC;
            } 
	} 
	return arrRet; 
    }
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("FoliosCaja.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaFoliosCaja('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("FoliosCaja.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaFoliosCaja('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("FoliosCaja.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaFoliosCaja('"+sUsuarioFirmado+"');"; 
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
        
        public String getInsertar() throws Exception{
            String sQuery="";
            if( this==null){   //completar llave
                throw new Exception("FoliosCaja.Insertar: error, faltan datos");}
            else{
                sQuery ="SELECT * FROM insertaFoliosCaja('"+
                    this.sUsuarioFirmado+ "'::character varying,"+
                    this.oCaja.getIdCaja()+"::smallint,'"+
                    this.oTipoRecibo.getClaveParametro()+"'::character(3),'"+
                    this.oTipoRecibo.getTipoParametro()+"'::character(3),"+
                    this.nFolioInicial+"::integer,"+
                    this.nFolioFinal+"::integer,"+
                    this.oRecibe.getNoTarjeta()+"::integer);";
            }
            return sQuery;
        }
    
    public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }
        
	public Date getFechaAsignacion() {
	return dFechaAsignacion;
	}

	public void setFechaAsignacion(Date valor) {
	dFechaAsignacion=valor;
	}

	public int getFolioFinal() {
	return nFolioFinal;
	}

	public void setFolioFinal(int valor) {
	nFolioFinal=valor;
	}

	public int getFolioInicial() {
	return nFolioInicial;
	}

	public void setFolioInicial(int valor) {
	nFolioInicial=valor;
	}

	public PersonalHospitalario getRecibe() {
	return oRecibe;
	}

	public void setRecibe(PersonalHospitalario valor) {
	oRecibe=valor;
	}

	public Parametrizacion getTipoRecibo() {
	return oTipoRecibo;
	}

	public void setTipoRecibo(Parametrizacion valor) {
	oTipoRecibo=valor;
	}

	public Caja getCaja() {
	return oCaja;
	}

	public void setCaja(Caja valor) {
	oCaja=valor;
	}

    public int getCantidad() {
        return (nFolioFinal-nFolioInicial)+1;
    }
} 
