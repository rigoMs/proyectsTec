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

public  class AnalgesiaTparto implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bAnalgesiaTparto;
	private boolean bPartoLegrado;
	private String  sTipo;
	private String sComplicaciones;
	private String sResultado;
        private TerminacionEmbarazo oTerminacionEmbarazo;

	public AnalgesiaTparto(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oTerminacionEmbarazo = new TerminacionEmbarazo();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AnalgesiaTparto.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAnalgesiaTparto();"; 
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
	public AnalgesiaTparto[] buscarTodos() throws Exception{
	AnalgesiaTparto arrRet[]=null, oAnalgesiaTparto=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAnalgesiaTparto();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AnalgesiaTparto[rst.size()];
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
			throw new Exception("AnalgesiaTparto.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAnalgesiaTparto('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("AnalgesiaTparto.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAnalgesiaTparto('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("AnalgesiaTparto.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAnalgesiaTparto('"+sUsuarioFirmado+"');"; 
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
        public void detalleAnalgesia()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION:FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM buscadetalleanalgesiapartograma(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setAnalgesiaTparto(vRowTemp.get(0).toString().compareTo("1") == 0);
                this.setTipo((String)vRowTemp.get(1).toString());
                this.setResultado(vRowTemp.get(2).toString());
                this.setComplicaciones(vRowTemp.get(3).toString());
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(6).toString());
                String nombres = (String)vRowTemp.get(7).toString() + " " + (String)vRowTemp.get(8).toString() + " " + (String)vRowTemp.get(9).toString();
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres(nombres);
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(((Double)vRowTemp.get(10)).intValue());
            }else{
                this.setAnalgesiaTparto(false);
                this.setTipo("");
                this.setResultado("");
                this.setComplicaciones("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApPaterno("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApMaterno("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(0);
            }
        }
        
        public void detalleAnalgesiaLegrado()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION:FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM buscadetalleanalgesiapartograma(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setAnalgesiaTparto(vRowTemp.get(4).toString().compareTo("1") == 0);
                this.setTipo((String)vRowTemp.get(5).toString());
            }
        }
	public boolean getAnalgesiaTparto() {
	return bAnalgesiaTparto;
	}

	public void setAnalgesiaTparto(boolean valor) {
	bAnalgesiaTparto=valor;
	}

	public boolean getPartoLegrado() {
	return bPartoLegrado;
	}

	public void setPartoLegrado(boolean valor) {
	bPartoLegrado=valor;
	}

	public String getTipo() {
	return sTipo;
	}

	public void setTipo(String valor) {
	sTipo=valor;
	}

	public String getComplicaciones() {
	return sComplicaciones;
	}

	public void setComplicaciones(String valor) {
	sComplicaciones=valor;
	}

	public String getResultado() {
	return sResultado;
	}

	public void setResultado(String valor) {
	sResultado=valor;
	}
        public TerminacionEmbarazo getTerminacionEmbarazo(){
            return oTerminacionEmbarazo;
        }
        public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
            this.oTerminacionEmbarazo = oTerminacionEmbarazo;
        }
        public String getValorAnalgesiaTparto(){
            return this.getAnalgesiaTparto() ? "SÍ(X) NO( )" : "SI( ) NO(X)";
        }

} 
