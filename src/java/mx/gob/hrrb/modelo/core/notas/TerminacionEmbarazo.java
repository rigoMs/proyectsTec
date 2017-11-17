/*
*MODIFICACION: ALBERTO
*AGREGUE UN OBJETO DE TIPO PARTOGRAMA oPartoGrama
*/
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

public  class TerminacionEmbarazo implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private NotaPostOclusionTubariaPostparto oNotaPostOclusionTubariaPostparto;
	private NotaPreoperatoria oNotaPreoperatoria;
	private ArrayList<EvolucionPartoLegrado> oEvolucionPartoLegrado;
	private Legrado oLegrado;
	private Placenta oPlacenta;
	private AnalgesiaTparto oAnalgesiaTparto;
	private Alumbramiento oAlumbramiento;
	private Cesarea oCesarea;
	private Forceps oForceps;
	private PartoEutocico oPartoEutocico;
        private PartoGrama oPartoGrama;

	public TerminacionEmbarazo(){
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
            oPartoGrama = new PartoGrama();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("TerminacionEmbarazo.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveTerminacionEmbarazo();"; 
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
	public TerminacionEmbarazo[] buscarTodos() throws Exception{
	TerminacionEmbarazo arrRet[]=null, oTerminacionEmbarazo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosTerminacionEmbarazo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new TerminacionEmbarazo[rst.size()];
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
			throw new Exception("TerminacionEmbarazo.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaTerminacionEmbarazo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("TerminacionEmbarazo.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaTerminacionEmbarazo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("TerminacionEmbarazo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaTerminacionEmbarazo('"+sUsuarioFirmado+"');"; 
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
        
	public NotaPostOclusionTubariaPostparto getNotaPostOclusionTubariaPostparto() {
	return oNotaPostOclusionTubariaPostparto;
	}

	public void setNotaPostOclusionTubariaPostparto(NotaPostOclusionTubariaPostparto valor) {
	oNotaPostOclusionTubariaPostparto=valor;
	}

	public NotaPreoperatoria getNotaPreoperatoria() {
	return oNotaPreoperatoria;
	}

	public void setNotaPreoperatoria(NotaPreoperatoria valor) {
	oNotaPreoperatoria=valor;
	}

	public ArrayList<EvolucionPartoLegrado> getEvolucionPartoLegrado() {
	return oEvolucionPartoLegrado;
	}

	public void setEvolucionPartoLegrado(ArrayList<EvolucionPartoLegrado> valor) {
	oEvolucionPartoLegrado=valor;
	}

	public Legrado getLegrado() {
	return oLegrado;
	}

	public void setLegrado(Legrado valor) {
	oLegrado=valor;
	}

	public Placenta getPlacenta() {
	return oPlacenta;
	}

	public void setPlacenta(Placenta valor) {
	oPlacenta=valor;
	}

	public AnalgesiaTparto getAnalgesiaTparto() {
	return oAnalgesiaTparto;
	}

	public void setAnalgesiaTparto(AnalgesiaTparto valor) {
	oAnalgesiaTparto=valor;
	}

	public Alumbramiento getAlumbramiento() {
	return oAlumbramiento;
	}

	public void setAlumbramiento(Alumbramiento valor) {
	oAlumbramiento=valor;
	}

	public Cesarea getCesarea() {
	return oCesarea;
	}

	public void setCesarea(Cesarea valor) {
	oCesarea=valor;
	}

	public Forceps getForceps() {
	return oForceps;
	}

	public void setForceps(Forceps valor) {
	oForceps=valor;
	}

	public PartoEutocico getPartoEutocico() {
	return oPartoEutocico;
	}

	public void setPartoEutocico(PartoEutocico valor) {
	oPartoEutocico=valor;
	}
        public PartoGrama getPartoGrama(){
            return oPartoGrama;
        }
        public void setPartoGrama(PartoGrama oPartoGrama){
            this.oPartoGrama = oPartoGrama;
        }
} 
