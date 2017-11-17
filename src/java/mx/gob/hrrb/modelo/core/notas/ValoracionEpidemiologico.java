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

public  class ValoracionEpidemiologico implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private float nPuntageTotal;
	private float nValoracionApgar;
	private float nValoracionEdadGestacional;
	private float nValoracionEdadMaterna;
	private float nValoracionEmParto;
	private float nValoracionPesoNacer;
	private float nValoracionReanimacion;
	private String sUnidadCorresp;
	private AtencionNeonatalTococirugia oAtencionNeonatalTococirugia;

	public ValoracionEpidemiologico(){
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
			throw new Exception("ValoracionEpidemiologico.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveValoracionEpidemiologico();"; 
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
	public ValoracionEpidemiologico[] buscarTodos() throws Exception{
	ValoracionEpidemiologico arrRet[]=null, oValoracionEpidemiologico=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosValoracionEpidemiologico();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ValoracionEpidemiologico[rst.size()];
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
			throw new Exception("ValoracionEpidemiologico.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaValoracionEpidemiologico('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ValoracionEpidemiologico.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaValoracionEpidemiologico('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ValoracionEpidemiologico.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaValoracionEpidemiologico('"+sUsuarioFirmado+"');"; 
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
        public String getValoracion(){
            String ingreso = this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT,";
            String consecutivo = this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() + "::SMALLINT,";
            String peso = this.getValoracionPesoNacer() + "::NUMERIC(4,2),";
            String edadm = this.getValoracionEdadMaterna() + "::NUMERIC(4,2),";
            String edadg = this.getValoracionEdadGestacional() + "::NUMERIC(4,2),";
            String embarazoparto = this.getValoracionEmParto() + "::NUMERIC(4,2),";
            String apgar = this.getValoracionApgar() + "::NUMERIC(4,2),";
            String reanimacion = this.getValoracionReanimacion() + "::NUMERIC(4,2),";
            String uni = "'" + this.getUnidadCorresp() + "'::CHARACTER VARYING,";
            String evolucion = "'" + this.getAtencionNeonatalTococirugia().getEvoIndicaciones().toUpperCase() + "'::TEXT);";
            return "SELECT * FROM insertactualizavaloracionepidemiorn(" + ingreso + consecutivo + "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                    peso + edadm + edadg + embarazoparto + apgar + reanimacion + uni + evolucion;
        }
        public void buscaDetalleValoracionEpidemio()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() == 0 || this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() == 0)
                throw new Exception("BUSCADETALLEVALORACIONEPIDEMIO:NOPUDIMOSPORCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallevaloracionepidemiorn(" + this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT," +
                        this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() + "::SMALLINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setValoracionPesoNacer(((Double)vRowTemp.get(0)).floatValue());
                    this.setValoracionEdadMaterna(((Double)vRowTemp.get(1)).floatValue());
                    this.setValoracionEdadGestacional(((Double)vRowTemp.get(2)).floatValue());
                    this.setValoracionEmParto(((Double)vRowTemp.get(3)).floatValue());
                    this.setValoracionApgar(((Double)vRowTemp.get(4)).floatValue());
                    this.setValoracionReanimacion(((Double)vRowTemp.get(5)).floatValue());
                    this.setUnidadCorresp((String)vRowTemp.get(6).toString());
                    this.getAtencionNeonatalTococirugia().setEvoIndicaciones((String)vRowTemp.get(7).toString());
                }
            }
        }
        
        public void buscaDetalleValoracionEpidemioEXP()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() == 0 || this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() == 0)
                throw new Exception("BUSCADETALLEVALORACIONEPIDEMIOEXP:NOPUDIMOSPORCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallevaloracionepidemiornEXP(" + 
                        this.getAtencionNeonatalTococirugia().getProducto().getNumeroIngresoHospitalario() + "::BIGINT," +
                        this.getAtencionNeonatalTococirugia().getProducto().getConsecutivoProducto() + "::SMALLINT,"+
                        this.getAtencionNeonatalTococirugia().getClaveAtNeonatalToco() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setValoracionPesoNacer(((Double)vRowTemp.get(0)).floatValue());
                    this.setValoracionEdadMaterna(((Double)vRowTemp.get(1)).floatValue());
                    this.setValoracionEdadGestacional(((Double)vRowTemp.get(2)).floatValue());
                    this.setValoracionEmParto(((Double)vRowTemp.get(3)).floatValue());
                    this.setValoracionApgar(((Double)vRowTemp.get(4)).floatValue());
                    this.setValoracionReanimacion(((Double)vRowTemp.get(5)).floatValue());
                    this.setUnidadCorresp((String)vRowTemp.get(6).toString());
                    this.getAtencionNeonatalTococirugia().setEvoIndicaciones((String)vRowTemp.get(7).toString());
                }
            }
        }
        
	public float getPuntageTotal() {
	return nPuntageTotal;
	}

	public void setPuntageTotal(float valor) {
	nPuntageTotal=valor;
	}

	public float getValoracionApgar() {
	return nValoracionApgar;
	}

	public void setValoracionApgar(float valor) {
	nValoracionApgar=valor;
	}

	public float getValoracionEdadGestacional() {
	return nValoracionEdadGestacional;
	}

	public void setValoracionEdadGestacional(float valor) {
	nValoracionEdadGestacional=valor;
	}

	public float getValoracionEdadMaterna() {
	return nValoracionEdadMaterna;
	}

	public void setValoracionEdadMaterna(float valor) {
	nValoracionEdadMaterna=valor;
	}

	public float getValoracionEmParto() {
	return nValoracionEmParto;
	}

	public void setValoracionEmParto(float valor) {
	nValoracionEmParto=valor;
	}

	public float getValoracionPesoNacer() {
	return nValoracionPesoNacer;
	}

	public void setValoracionPesoNacer(float valor) {
	nValoracionPesoNacer=valor;
	}

	public float getValoracionReanimacion() {
	return nValoracionReanimacion;
	}

	public void setValoracionReanimacion(float valor) {
	nValoracionReanimacion=valor;
	}

	public String getUnidadCorresp() {
	return sUnidadCorresp;
	}

	public void setUnidadCorresp(String valor) {
	sUnidadCorresp=valor;
	}

	public AtencionNeonatalTococirugia getAtencionNeonatalTococirugia() {
	return oAtencionNeonatalTococirugia;
	}

	public void setAtencionNeonatalTococirugia(AtencionNeonatalTococirugia valor) {
	oAtencionNeonatalTococirugia=valor;
	}

} 
