/*
* MODIFICACION RELIAZADA POR ALBERTO
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

public  class Alumbramiento implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bRecCavidadUterina;
	private Parametrizacion oTipoAlumbramiento;
	private String sCausa;
	private String sMotivo;
	private String sObservaciones;
        private TerminacionEmbarazo oTerminacionEmbarazo;

	public Alumbramiento(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
            oTerminacionEmbarazo = new TerminacionEmbarazo();
            oTipoAlumbramiento = new Parametrizacion();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Alumbramiento.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAlumbramiento();"; 
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
	public Alumbramiento[] buscarTodos() throws Exception{
	Alumbramiento arrRet[]=null, oAlumbramiento=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAlumbramiento();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Alumbramiento[rst.size()];
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
			throw new Exception("Alumbramiento.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAlumbramiento('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Alumbramiento.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAlumbramiento('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Alumbramiento.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAlumbramiento('"+sUsuarioFirmado+"');"; 
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
        public void detalleAlumbramiento()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ESPOSIBLE PROCESAR LA INFORMACION:FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM buscadetallealumbramientopartograma(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
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
                this.getTipoAlumbramiento().setValor((String)vRowTemp.get(0).toString());
                this.setCausa((String)vRowTemp.get(1).toString());
                this.setRecCavidadUterina(vRowTemp.get(2).toString().compareTo("1") == 0);
                this.setMotivo((String)vRowTemp.get(3).toString());
                this.setObservaciones((String)vRowTemp.get(4).toString());
                String valor = (String)vRowTemp.get(5).toString() + "" + (String)vRowTemp.get(6).toString();
                this.getTipoAlumbramiento().setClaveParametro(valor);
            }else{
                this.getTipoAlumbramiento().setValor("");
                this.setCausa("");
                this.setRecCavidadUterina(false);
                this.setMotivo("");
                this.setObservaciones("");
                this.getTipoAlumbramiento().setClaveParametro("");
            }
        }
	public boolean getRecCavidadUterina() {
	return bRecCavidadUterina;
	}

	public void setRecCavidadUterina(boolean valor) {
	bRecCavidadUterina=valor;
	}

        public String getValorRecUterina(){
            return getRecCavidadUterina() ? "(X)" : "( )";
        }
        
	public Parametrizacion getTipoAlumbramiento() {
	return oTipoAlumbramiento;
	}

	public void setTipoAlumbramiento(Parametrizacion valor) {
	oTipoAlumbramiento=valor;
	}

	public String getCausa() {
	return sCausa;
	}

	public void setCausa(String valor) {
	sCausa=valor;
	}

	public String getMotivo() {
	return sMotivo;
	}

	public void setMotivo(String valor) {
	sMotivo=valor;
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
        public String getValorAlumbramiento(){
            if(this.getTipoAlumbramiento().getValor().compareTo("ESPONTÁNEO") == 0)
                return "ESPONTÁNEO(X) DIRIGIDO( ) MANUAL( )";
            if(this.getTipoAlumbramiento().getValor().compareTo("DIRIGIDO") == 0)
                return "ESPONTÁNEO( ) DIRIGIDO(X) MANUAL( )";
            if(this.getTipoAlumbramiento().getValor().compareTo("MANUAL") == 0)
                return "ESPONTÁNEO( ) DIRIGIDO( ) MANUAL(X)";
            return "ESPONTÁNEO( ) DIRIGIDO( ) MANUAL( )";
        }
} 
