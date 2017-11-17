/*
* MODIFICACION REALIZADA POR ALBERTO
* AGREGUE UN OBJETO TERMINACION EMBARAZO
* AGREGUE LA PROPIEAD sIndicacionPrincipal
*/
package mx.gob.hrrb.modelo.core.notas;

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

public  class Cesarea implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bPHisteroctomia;
	private Parametrizacion oCesarea;
	private String sComplicaciones;
	private String sDescribir;
	private String sExtraccionProducto;
	private String sOtras;
	private String sOtrasObservaciones;
        private String sIndicacionPrincipal;
        private TerminacionEmbarazo oTerminacionEmbarazo;

	public Cesarea(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oTerminacionEmbarazo = new TerminacionEmbarazo();
                oCesarea = new Parametrizacion();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cesarea.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCesarea();"; 
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
	public Cesarea[] buscarTodos() throws Exception{
	Cesarea arrRet[]=null, oCesarea=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCesarea();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Cesarea[rst.size()];
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
			throw new Exception("Cesarea.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCesarea('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Cesarea.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCesarea('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Cesarea.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCesarea('"+sUsuarioFirmado+"');"; 
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
        public void detalleCesarea()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION:FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM buscadetallecesareapartograma(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() +"::SMALLINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.getCesarea1().setValor((String)vRowTemp.get(0).toString());
                this.setOtras((String)vRowTemp.get(1).toString());
                this.setIndicacionPrincipal((String)vRowTemp.get(2).toString());
                this.setPHisteroctomia(vRowTemp.get(3).toString().compareTo("1") == 0);
                this.setDescribir((String)vRowTemp.get(4).toString());
                this.setExtraccionProducto((String)vRowTemp.get(5).toString());
                this.setComplicaciones((String)vRowTemp.get(6).toString());
                this.setOtrasObservaciones((String)vRowTemp.get(7).toString());
                String tipocesarea = (String)vRowTemp.get(8).toString() + "" + (String)vRowTemp.get(9).toString();
                this.getCesarea1().setClaveParametro(tipocesarea);
            }else{
                this.getCesarea1().setValor("");
                this.setOtras("");
                this.setIndicacionPrincipal("");
                this.setPHisteroctomia(false);
                this.setDescribir("");
                this.setExtraccionProducto("");
                this.setComplicaciones("");
                this.setOtrasObservaciones("");
                this.getCesarea1().setClaveParametro("");
            }
        }
	public boolean getPHisteroctomia() {
	return bPHisteroctomia;
	}

	public void setPHisteroctomia(boolean valor) {
	bPHisteroctomia=valor;
	}

	public Parametrizacion getCesarea1() {
	return oCesarea;
	}

	public void setCesarea1(Parametrizacion valor) {
	oCesarea=valor;
	}

	public String getComplicaciones() {
	return sComplicaciones;
	}

	public void setComplicaciones(String valor) {
	sComplicaciones=valor;
	}

	public String getDescribir() {
	return sDescribir;
	}

	public void setDescribir(String valor) {
	sDescribir=valor;
	}

	public String getExtraccionProducto() {
	return sExtraccionProducto;
	}

	public void setExtraccionProducto(String valor) {
	sExtraccionProducto=valor;
	}

	public String getOtras() {
	return sOtras;
	}

	public void setOtras(String valor) {
	sOtras=valor;
	}

	public String getOtrasObservaciones() {
	return sOtrasObservaciones;
	}

	public void setOtrasObservaciones(String valor) {
	sOtrasObservaciones=valor;
	}
        public TerminacionEmbarazo getTerminacionEmbarazo(){
            return oTerminacionEmbarazo;
        }
        public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
            this.oTerminacionEmbarazo = oTerminacionEmbarazo;
        }
        public String getIndicacionPrincipal(){
            return sIndicacionPrincipal;
        }
        public void setIndicacionPrincipal(String sIndicacionPrincipal){
            this.sIndicacionPrincipal = sIndicacionPrincipal;
        }
        public String getValorHisterectomia(){
            return this.getPHisteroctomia() ? "NO( ) SÍ(X)" : "NO(X) SI( )";
        }
        public String getValorCesarea(){
            if(this.getCesarea1().getValor().compareTo("KERR") == 0)
                return "KERR(X) BECK( ) CORPORAL( )";
            if(this.getCesarea1().getValor().compareTo("BECK") == 0)
                return "KERR( ) BECK(X) CORPORAL( )";
            if(this.getCesarea1().getValor().compareTo("CORPORAL") == 0)
                return "KERR( ) BECK( ) CORPORAL(X)";
            return "KERR( ) BECK( ) CORPORAL()";
        }
} 
