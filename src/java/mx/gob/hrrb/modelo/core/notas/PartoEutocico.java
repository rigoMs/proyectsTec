/*
*MODIFICACION REALIZADA POR ALBERTO
*AGREGE UN OBETO DE TERMINACION EMBARAZO
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

public  class PartoEutocico implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private ArrayList<Parametrizacion> oDesgarro;
	private String sEpisiotomia;
	private String sObservaciones;
	private String sProlongacion;
	private String sVariedadPosicion;
        private TerminacionEmbarazo oTerminacionEmbarazo;
	public PartoEutocico(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oTerminacionEmbarazo = new TerminacionEmbarazo();
                oDesgarro = new ArrayList<Parametrizacion>();
                oDesgarro.add(new Parametrizacion());
                oDesgarro.add(new Parametrizacion());
                oDesgarro.add(new Parametrizacion());
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PartoEutocico.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePartoEutocico();"; 
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
	public PartoEutocico[] buscarTodos() throws Exception{
	PartoEutocico arrRet[]=null, oPartoEutocico=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPartoEutocico();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new PartoEutocico[rst.size()];
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
			throw new Exception("PartoEutocico.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPartoEutocico('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("PartoEutocico.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPartoEutocico('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("PartoEutocico.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPartoEutocico('"+sUsuarioFirmado+"');"; 
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
        public void detallePartoEutocico()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION:FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM buscadetallepartoeutocicopartograma("+ this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() +"::BIGINT,"+
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," + this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setVariedadPosicion((String)vRowTemp.get(0).toString());
                this.setEpisiotomia((String)vRowTemp.get(1).toString());
                this.setProlongacion((String)vRowTemp.get(2).toString());
                this.getDesgarro().get(0).setValor((String)vRowTemp.get(3).toString());
                this.getDesgarro().get(1).setValor((String)vRowTemp.get(4).toString());
                this.getDesgarro().get(2).setValor((String)vRowTemp.get(5).toString());
                this.setObservaciones((String)vRowTemp.get(6).toString());
                this.getDesgarro().get(0).setClaveParametro((String)vRowTemp.get(7).toString());
                this.getDesgarro().get(0).setTipoParametro((String)vRowTemp.get(8).toString());
                this.getDesgarro().get(1).setClaveParametro((String)vRowTemp.get(9).toString());
                this.getDesgarro().get(1).setTipoParametro((String)vRowTemp.get(10).toString());
                this.getDesgarro().get(2).setClaveParametro((String)vRowTemp.get(11).toString());
                this.getDesgarro().get(2).setTipoParametro((String)vRowTemp.get(12).toString());
            }
        }
	public ArrayList<Parametrizacion> getDesgarro() {
	return oDesgarro;
	}

	public void setDesgarro(ArrayList<Parametrizacion> valor) {
	oDesgarro=valor;
	}

	public String getEpisiotomia() {
	return sEpisiotomia;
	}

	public void setEpisiotomia(String valor) {
	sEpisiotomia=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public String getProlongacion() {
	return sProlongacion;
	}

	public void setProlongacion(String valor) {
	sProlongacion=valor;
	}

	public String getVariedadPosicion() {
	return sVariedadPosicion;
	}

	public void setVariedadPosicion(String valor) {
	sVariedadPosicion=valor;
	}
        public TerminacionEmbarazo getTerminacionEmbarazo(){
            return oTerminacionEmbarazo;
        }
        public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
            this.oTerminacionEmbarazo = oTerminacionEmbarazo;
        }
} 
