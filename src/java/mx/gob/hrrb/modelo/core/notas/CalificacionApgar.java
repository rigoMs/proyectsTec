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

public  class CalificacionApgar implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nColor5Min;
	private int nColorMin;
	private int nEsfuerzoRespiratorio5Min;
	private int nEsfuerzoRespiratorioMin;
	private int nIrritabilidad5Min;
	private int nIrritabilidadMin;
	private int nTonoMuscular5Min;
	private int nTonoMuscularMin;
	private int nTotal5Min;
	private int nTotalMin;
	private int sFc5Min;
	private int sFcMin;
	private AtencionNeonatalTococirugia oAtencionNeonatalTococirugia;
                
	public CalificacionApgar(){
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
			throw new Exception("CalificacionApgar.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCalificacionApgar();"; 
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
	public CalificacionApgar[] buscarTodos() throws Exception{
	CalificacionApgar arrRet[]=null, oCalificacionApgar=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCalificacionApgar();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CalificacionApgar[rst.size()];
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
			throw new Exception("CalificacionApgar.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCalificacionApgar('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("CalificacionApgar.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCalificacionApgar('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("CalificacionApgar.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCalificacionApgar('"+sUsuarioFirmado+"');"; 
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
        public String getQueryCalficacionApgar(){            
            String ingreso = this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT,";
            String consecutivo = this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() + "::SMALLINT,";            
            String apgar1 = this.getFcMin() + "::SMALLINT,";
            String apgar5 = this.getFc5Min() + "::SMALLINT,";
            String esfuerzo1 = this.getEsfuerzoRespiratorioMin() + "::SMALLINT,";
            String esfuerzo5 = this.getEsfuerzoRespiratorio5Min() + "::SMALLINT,";
            String tonomuscular1 = this.getTonoMuscularMin() + "::SMALLINT,";
            String tonomuscular5 = this.getTonoMuscular5Min() + "::SMALLINT,";
            String irritabilidad1 = this.getIrritabilidadMin() + "::SMALLINT,";
            String irritabilidad5 = this.getIrritabilidad5Min() + "::SMALLINT,";
            String color1 = this.getColorMin() + "::SMALLINT,";
            String color5 = this.getColor5Min() + "::SMALLINT);";
            return "SELECT * FROM insertactualizapgarn(" + ingreso + consecutivo + "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + apgar1 + apgar5 
                    + esfuerzo1 +esfuerzo5 + tonomuscular1 + tonomuscular5 + irritabilidad1 + irritabilidad5 + color1 + color5;
        }
        public void buscaDetalleApgar()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() == 0 || this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() == 0)
                throw new Exception("BUSCADETALLEAPGAR:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetalleapgarn(" + this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT,"+
                        this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() +"::SMALLINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setFcMin(((Double)vRowTemp.get(0)).intValue());
                    this.setFc5Min(((Double)vRowTemp.get(1)).intValue());
                    this.setEsfuerzoRespiratorioMin(((Double)vRowTemp.get(2)).intValue());
                    this.setEsfuerzoRespiratorio5Min(((Double)vRowTemp.get(3)).intValue());
                    this.setTonoMuscularMin(((Double)vRowTemp.get(4)).intValue());
                    this.setTonoMuscular5Min(((Double)vRowTemp.get(5)).intValue());
                    this.setIrritabilidadMin(((Double)vRowTemp.get(6)).intValue());
                    this.setIrritabilidad5Min(((Double)vRowTemp.get(7)).intValue());
                    this.setColorMin(((Double)vRowTemp.get(8)).intValue());
                    this.setColor5Min(((Double)vRowTemp.get(9)).intValue());
                }
            }
        }
        
        
        public void buscaDetalleApgarEXP()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() == 0 || this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() == 0 || this.getAtencionNeonatalTococirugia().getClaveAtNeonatalToco() == 0)
                throw new Exception("BUSCADETALLEAPGAREXP:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetalleapgarnExp(" + 
                        this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT,"+
                        this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() +"::SMALLINT,"+
                        this.getAtencionNeonatalTococirugia().getClaveAtNeonatalToco()+"::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setFcMin(((Double)vRowTemp.get(0)).intValue());
                    this.setFc5Min(((Double)vRowTemp.get(1)).intValue());
                    this.setEsfuerzoRespiratorioMin(((Double)vRowTemp.get(2)).intValue());
                    this.setEsfuerzoRespiratorio5Min(((Double)vRowTemp.get(3)).intValue());
                    this.setTonoMuscularMin(((Double)vRowTemp.get(4)).intValue());
                    this.setTonoMuscular5Min(((Double)vRowTemp.get(5)).intValue());
                    this.setIrritabilidadMin(((Double)vRowTemp.get(6)).intValue());
                    this.setIrritabilidad5Min(((Double)vRowTemp.get(7)).intValue());
                    this.setColorMin(((Double)vRowTemp.get(8)).intValue());
                    this.setColor5Min(((Double)vRowTemp.get(9)).intValue());
                }
            }
        }
        
	public int getColor5Min() {
	return nColor5Min;
	}

	public void setColor5Min(int valor) {
	nColor5Min=valor;
	}

	public int getColorMin() {
	return nColorMin;
	}

	public void setColorMin(int valor) {
	nColorMin=valor;
	}

	public int getEsfuerzoRespiratorio5Min() {
	return nEsfuerzoRespiratorio5Min;
	}

	public void setEsfuerzoRespiratorio5Min(int valor) {
	nEsfuerzoRespiratorio5Min=valor;
	}

	public int getEsfuerzoRespiratorioMin() {
	return nEsfuerzoRespiratorioMin;
	}

	public void setEsfuerzoRespiratorioMin(int valor) {
	nEsfuerzoRespiratorioMin=valor;
	}

	public int getIrritabilidad5Min() {
	return nIrritabilidad5Min;
	}

	public void setIrritabilidad5Min(int valor) {
	nIrritabilidad5Min=valor;
	}

	public int getIrritabilidadMin() {
	return nIrritabilidadMin;
	}

	public void setIrritabilidadMin(int valor) {
	nIrritabilidadMin=valor;
	}

	public int getTonoMuscular5Min() {
	return nTonoMuscular5Min;
	}

	public void setTonoMuscular5Min(int valor) {
	nTonoMuscular5Min=valor;
	}

	public int getTonoMuscularMin() {
	return nTonoMuscularMin;
	}

	public void setTonoMuscularMin(int valor) {
	nTonoMuscularMin=valor;
	}

	public int getTotal5Min() {
	return nTotal5Min;
	}

	public void setTotal5Min(int valor) {
	nTotal5Min=valor;
	}

	public int getTotalMin() {
	return nTotalMin;
	}

	public void setTotalMin(int valor) {
	nTotalMin=valor;
	}

	public int getFc5Min() {
	return sFc5Min;        
	}

	public void setFc5Min(int valor) {
	sFc5Min=valor;
        System.out.println(sFc5Min);
	}

	public int getFcMin() {            
	return sFcMin;
	}

	public void setFcMin(int valor) {
	sFcMin=valor;
        System.out.println(sFcMin);
	}

	public AtencionNeonatalTococirugia getAtencionNeonatalTococirugia() {
	return oAtencionNeonatalTococirugia;
	}

	public void setAtencionNeonatalTococirugia(AtencionNeonatalTococirugia valor) {
	oAtencionNeonatalTococirugia=valor;
	}

} 
