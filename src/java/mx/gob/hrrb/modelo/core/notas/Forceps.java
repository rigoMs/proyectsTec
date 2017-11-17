/*
* MODIFICACION REALIZADA POR ALBERTO
*AGREGUE UN OBJETO TERMINACION EMBARAZO
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

public  class Forceps implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Parametrizacion oVariedadPosicion;
	private String sInidcacionPrincipal;
	private String sInstrumento;
	private String sObservaciones;
        private TerminacionEmbarazo oTerminacionEmbarazo;
	public Forceps(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
            oTerminacionEmbarazo = new TerminacionEmbarazo();
            oVariedadPosicion = new Parametrizacion();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Forceps.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveForceps();"; 
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
	public Forceps[] buscarTodos() throws Exception{
	Forceps arrRet[]=null, oForceps=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosForceps();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Forceps[rst.size()];
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
			throw new Exception("Forceps.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaForceps('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Forceps.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaForceps('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Forceps.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaForceps('"+sUsuarioFirmado+"');"; 
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
        public void buscaDetalleForceps()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION:FALTANDATOS");
            else{
                sQuery = "SELECT * FROM buscadetalleforcepspartograma(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() +"::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() +"::SMALLINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() +"::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.getVariedadPosicion().setValor((String)vRowTemp.get(0).toString());
                this.setIndicacionPrincipal((String)vRowTemp.get(1).toString());
                this.setInstrumento((String)vRowTemp.get(2).toString());
                this.setObservaciones((String)vRowTemp.get(3).toString());
                String vposicion = (String)vRowTemp.get(4).toString() + "" + (String)vRowTemp.get(5).toString();
                this.getVariedadPosicion().setClaveParametro(vposicion);                
            }else{
                this.getVariedadPosicion().setValor("");
                this.setIndicacionPrincipal("");
                this.setInstrumento("");
                this.setObservaciones("");
                this.getVariedadPosicion().setClaveParametro("");
                this.getVariedadPosicion().setTipoParametro("");
            }
        }
	public Parametrizacion getVariedadPosicion() {
	return oVariedadPosicion;
	}

	public void setVariedadPosicion(Parametrizacion valor) {
	oVariedadPosicion=valor;
	}

	public String getIndicacionPrincipal() {
	return sInidcacionPrincipal;
	}

	public void setIndicacionPrincipal(String valor) {
	sInidcacionPrincipal=valor;
	}

	public String getInstrumento() {
	return sInstrumento;
	}

	public void setInstrumento(String valor) {
	sInstrumento=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}
        public TerminacionEmbarazo getTerminacionEmbarazo(){
            return oTerminacionEmbarazo;
        }
        public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
            this.oTerminacionEmbarazo = oTerminacionEmbarazo;
        }
        public String getValorPosicion(){
            if(getVariedadPosicion().getValor().compareTo("MEDIO BAJO") == 0)
                return "MEDIO BAJO(X) PROFILÁCTICO( ) BAJO( ) ELECTIVOS( ) OTROS( )";
            if(getVariedadPosicion().getValor().compareTo("PROFILÁCTICO") == 0)
                return "MEDIO BAJO( ) PROFILÁCTICO(X) BAJO( ) ELECTIVOS( ) OTROS( )";
            if(getVariedadPosicion().getValor().compareTo("BAJO") == 0)
                return "MEDIO BAJO( ) PROFILÁCTICO( ) BAJO(X) ELECTIVOS( ) OTROS( )";
            if(getVariedadPosicion().getValor().compareTo("ELECTIVOS") == 0)
                return "MEDIO BAJO( ) PROFILÁCTICO( ) BAJO( ) ELECTIVOS(X) OTROS( )";
            if(getVariedadPosicion().getValor().compareTo("OTROS") == 0)
                return "MEDIO BAJO( ) PROFILÁCTICO( ) BAJO( ) ELECTIVOS( ) OTROS(X)";
            return "MEDIO BAJO( ) PROFILÁCTICO( ) BAJO( ) ELECTIVOS( ) OTROS( )";
        }
} 
