package mx.gob.hrrb.modelo.core.notas;

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

public  class CalificacionSilvermann implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nAleteoNasal;
	private int nDisociacion;
	private int nQResp;
	private int nRetraccion;
	private int nTInter;
	private int nTotal;
	private AtencionNeonatalTococirugia oAtencionNeonatalTococirugia;

	public CalificacionSilvermann(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oAtencionNeonatalTococirugia = new AtencionNeonatalTococirugia();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CalificacionSilvermann.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCalificacionSilvermann();"; 
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
	public CalificacionSilvermann[] buscarTodos() throws Exception{
	CalificacionSilvermann arrRet[]=null, oCalificacionSilvermann=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCalificacionSilvermann();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CalificacionSilvermann[rst.size()];
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
			throw new Exception("CalificacionSilvermann.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCalificacionSilvermann('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("CalificacionSilvermann.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCalificacionSilvermann('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("CalificacionSilvermann.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCalificacionSilvermann('"+sUsuarioFirmado+"');"; 
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
        public String getCalificacionSilvermann(){
            String ingreso = this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT,";
            String consecutivo = this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() + "::SMALLINT,";
            String aleteonasal = this.getAleteoNasal() + "::SMALLINT,";
            String quejidoresp = this.getQResp() + "::SMALLINT,";
            String intercostal = this.getTInter() + "::SMALLINT,";
            String retraccion = this.getRetraccion() + "::SMALLINT,";
            String disociacion = this.getDisociacion() + "::SMALLINT);";
            return "SELECT * FROM insertactualizasilvermannrn(" + ingreso + consecutivo + "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                    aleteonasal + quejidoresp + intercostal + retraccion + disociacion;            
        }
        public void buscaDetalleSilvermann()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() == 0 || this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() == 0)
                throw new Exception("BUSCADETALLESILVERMANN:NOPUDIMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallesilvermannrn("+ this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT," +
                        this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() + "::SMALLINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setAleteoNasal(((Double)vRowTemp.get(0)).intValue());
                    this.setQResp(((Double)vRowTemp.get(1)).intValue());
                    this.setTInter(((Double)vRowTemp.get(2)).intValue());
                    this.setRetraccion(((Double)vRowTemp.get(3)).intValue());
                    this.setDisociacion(((Double)vRowTemp.get(4)).intValue());
                }
            }
        }
        public void buscaDetalleSilvermannEXP()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() == 0 || this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() == 0 || this.getAtencionNeonatalTococirugia().getClaveAtNeonatalToco() == 0)
                throw new Exception("BUSCADETALLESILVERMANNEXP:NOPUDIMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallesilvermannrnEXP("+ 
                        this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT," +
                        this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() +"::SMALLINT,"+
                        this.getAtencionNeonatalTococirugia().getClaveAtNeonatalToco() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setAleteoNasal(((Double)vRowTemp.get(0)).intValue());
                    this.setQResp(((Double)vRowTemp.get(1)).intValue());
                    this.setTInter(((Double)vRowTemp.get(2)).intValue());
                    this.setRetraccion(((Double)vRowTemp.get(3)).intValue());
                    this.setDisociacion(((Double)vRowTemp.get(4)).intValue());
                }
            }
        }
        
	public int getAleteoNasal() {
	return nAleteoNasal;
	}

	public void setAleteoNasal(int valor) {
	nAleteoNasal=valor;
	}

	public int getDisociacion() {
	return nDisociacion;
	}

	public void setDisociacion(int valor) {
	nDisociacion=valor;
	}

	public int getQResp() {
	return nQResp;
	}

	public void setQResp(int valor) {
	nQResp=valor;
	}

	public int getRetraccion() {
	return nRetraccion;
	}

	public void setRetraccion(int valor) {
	nRetraccion=valor;
	}

	public int getTInter() {
	return nTInter;
	}

	public void setTInter(int valor) {
	nTInter=valor;
	}

	public int getTotal() {
	return nTotal;
	}

	public void setTotal(int valor) {
	nTotal=valor;
	}

	public AtencionNeonatalTococirugia getAtencionNeonatalTococirugia() {
	return oAtencionNeonatalTococirugia;
	}

	public void setAtencionNeonatalTococirugia(AtencionNeonatalTococirugia valor) {
	oAtencionNeonatalTococirugia=valor;
	}

} 
