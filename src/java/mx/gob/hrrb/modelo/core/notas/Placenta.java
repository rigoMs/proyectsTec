/*
* MODIFICACION REALIZADA POR ALBERTO
* AGREGUE UN OBJETO TERMINACION EMBARAZO
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

public  class Placenta implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Parametrizacion oCordon;
	private String sDescribir1;
	private String sDescribir;
	private String sMetodoAnticonceptivo;
	private String sObservaciones;
	private String sPlacenta;
	private String sSangrado;
        private TerminacionEmbarazo oTerminacionEmbarazo;

	public Placenta(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
            oTerminacionEmbarazo = new TerminacionEmbarazo();
            oCordon = new Parametrizacion();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Placenta.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePlacenta();"; 
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
	public Placenta[] buscarTodos() throws Exception{
	Placenta arrRet[]=null, oPlacenta=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPlacenta();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Placenta[rst.size()];
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
			throw new Exception("Placenta.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPlacenta('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Placenta.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPlacenta('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Placenta.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPlacenta('"+sUsuarioFirmado+"');"; 
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
        public void detallePaciente(short opc)throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION:FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM buscadetalleplacentapartograma(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma()+ "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                if(opc == 0){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setPlacenta((String)vRowTemp.get(0).toString());                
                    this.getCordon().setValor((String)vRowTemp.get(1).toString());
                    this.setDescribir((String)vRowTemp.get(2).toString());                
                    this.setDescribir1((String)vRowTemp.get(4).toString());
                    this.setObservaciones((String)vRowTemp.get(5).toString());
                    this.setSangrado((String)vRowTemp.get(6).toString());
                    this.setMetodoAnticonceptivo((String)vRowTemp.get(7).toString());
                    String valor = (String)vRowTemp.get(8).toString() + "" + (String)vRowTemp.get(9).toString();
                    this.getCordon().setClaveParametro(valor);
                    String valor1 = (String)vRowTemp.get(10) + "" + (String)vRowTemp.get(11).toString();
                    this.getCordon().setTipoParametro(valor1);
                    this.setMetodoAnticonceptivo((String)vRowTemp.get(12).toString());
                }else if(opc == 1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setPlacenta((String)vRowTemp.get(0).toString());
                    this.getCordon().setClaveParametro((String)vRowTemp.get(1).toString());
                    this.setDescribir((String)vRowTemp.get(2).toString());
                    this.getCordon().setTipoParametro((String)vRowTemp.get(3).toString());
                    this.setDescribir1((String)vRowTemp.get(4).toString());
                    this.setObservaciones((String)vRowTemp.get(5).toString());
                    this.setSangrado((String)vRowTemp.get(6).toString());
                    this.setMetodoAnticonceptivo((String)vRowTemp.get(7).toString());
                }
                
            }else{
                this.setPlacenta("");
                this.getCordon().setClaveParametro("");
                this.setDescribir("");
                this.getCordon().setTipoParametro("");
                this.setDescribir1("");
                this.setObservaciones("");
                this.setSangrado("");
                this.setMetodoAnticonceptivo("");
            }
        }
	public Parametrizacion getCordon() {
	return oCordon;
	}

	public void setCordon(Parametrizacion valor) {
	oCordon=valor;
	}

	public String getDescribir1() {
	return sDescribir1;
	}

	public void setDescribir1(String valor) {
	sDescribir1=valor;
	}

	public String getDescribir() {
	return sDescribir;
	}

	public void setDescribir(String valor) {
	sDescribir=valor;
	}

	public String getMetodoAnticonceptivo() {
	return sMetodoAnticonceptivo;
	}

	public void setMetodoAnticonceptivo(String valor) {
	sMetodoAnticonceptivo=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public String getPlacenta() {
	return sPlacenta;
	}

	public void setPlacenta(String valor) {
	sPlacenta=valor;
	}

	public String getSangrado() {
	return sSangrado;
	}

	public void setSangrado(String valor) {
	sSangrado=valor;
	}
        public TerminacionEmbarazo getTerminacionEmbarazo(){
            return oTerminacionEmbarazo;
        }
        public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
            this.oTerminacionEmbarazo = oTerminacionEmbarazo;
        }
        public String getValorCordon(){
            return this.getCordon().getValor();
        }
        public String getValorCordon1(){
            return this.getCordon().getTipoParametro();
        }
        ////
        public String getCadenaCordon(){
            if(this.getCordon().getClaveParametro().isEmpty())
                return "NORMAL( ) ANORMAL( )";
            if(this.getCordon().getClaveParametro().compareTo("NORMAL") == 0)
                return "NORMAL(X) ANORMAL( )";
            else
                return "NORMAL( ) ANORMAL(X)";            
        }
        public String getCadenaCordon1(){
            if(this.getCordon().getTipoParametro().isEmpty())
                return "NUDOS( ) CIRCULARES( )";
            if(this.getCordon().getClaveParametro().compareTo("NUDOS") == 0)
                return "NUDOS(X) CIRCULARES( )";
            else
                return "NUDOS( ) CIRCULARES(X)";            
        }
} 
