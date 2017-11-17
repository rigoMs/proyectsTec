/*
* CLASE MODIFICADA POR ALBERTO
* AGREGE UN OBJETO TERMINACION EMBARAZO
* 
*/
package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class EvolucionPartoLegrado implements Serializable{
	private AccesoDatos oAD;
        
	private String sUsuarioFirmado;
	private String sEvolucion;
	private Date dFechaHora;
	private String sObservaciones;
        private TerminacionEmbarazo oTerminacionEmbarazo;
        private PersonalHospitalario oPersonal;
        
	public EvolucionPartoLegrado(){
	HttpServletRequest req;
            this.oPersonal = new PersonalHospitalario();
            this.oPersonal.setUsuar(new Usuario());
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oPersonal.setUsuar(new Usuario());
                this.oPersonal.getUsuar().setIdUsuario(this.sUsuarioFirmado);
                oTerminacionEmbarazo = new TerminacionEmbarazo();
	}
        public void buscaPersonal()throws Exception{
            this.oPersonal.buscaPersonalHospitalarioDatos();
        }
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("EvolucionPartoLegrado.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveEvolucionPartoLegrado();"; 
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
	public EvolucionPartoLegrado[] buscarTodos() throws Exception{
	EvolucionPartoLegrado arrRet[]=null, oEvolucionPartoLegrado=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosEvolucionPartoLegrado();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new EvolucionPartoLegrado[rst.size()];
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
			throw new Exception("EvolucionPartoLegrado.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaEvolucionPartoLegrado('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("EvolucionPartoLegrado.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaEvolucionPartoLegrado('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("EvolucionPartoLegrado.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaEvolucionPartoLegrado('"+sUsuarioFirmado+"');"; 
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
        public ArrayList<String> getQueryEvolucion(ArrayList<EvolucionPartoLegrado> arrEvolucion){            
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            ArrayList<String> arrQuery = null;
            if(arrEvolucion.isEmpty() || arrEvolucion == null)
                return null;
            else{
                String tarjeta = this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() != 0 ? this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER);" : null + "::INTEGER);";                
                System.out.println(tarjeta);
                String sQuery = "";
                arrQuery = new ArrayList<String>();
                for(EvolucionPartoLegrado i: arrEvolucion){                    
                    String fecha = i.getFechaHora() == null || i.getFechaHora().equals("") ? null + "::timestamp without time zone," : "'" + format.format(i.getFechaHora()) + "'::timestamp without time zone,";
                    String evolucion = i.getEvolucion() == null || i.getEvolucion().isEmpty() ? null + "::TEXT," : "'" + i.getEvolucion().toUpperCase() + "'::TEXT,";
                    String observaciones = i.getObservaciones() == null || i.getObservaciones().isEmpty() ? null + "::TEXT," : "'" + i.getObservaciones().toUpperCase() + "'::TEXT,";
                    sQuery ="SELECT * FROM insertaevolucionparto(" + i.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                            i.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                            i.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT,'" +
                            this.sUsuarioFirmado + "'::CHARACTER VARYING," +fecha + evolucion + observaciones + tarjeta;
                    arrQuery.add(sQuery);
                }
            }
            return arrQuery;
        }
        public ArrayList<EvolucionPartoLegrado> detalleEvolucionPartoLegrado()throws Exception{
            ArrayList rst = null;
            EvolucionPartoLegrado oEvolucion = null;
            ArrayList<EvolucionPartoLegrado> arrEvolucion = null;
            String sQuery = null;
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ESPOSIBLE PROCESAR LA INFORMACION:FALTANDATOS");
            else{
                sQuery = "SELECT * FROM buscadetalleevolucionpartolegrado(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," + this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null){
                    ArrayList vRowTemp = null;
                    arrEvolucion = new ArrayList<EvolucionPartoLegrado>();
                    for(int i = 0; i < rst.size(); i++){
                        oEvolucion = new EvolucionPartoLegrado();
                        vRowTemp = (ArrayList)rst.get(i);
                        oEvolucion.setFechaHora((Date)vRowTemp.get(0));
                        oEvolucion.setEvolucion((String)vRowTemp.get(1).toString());
                        oEvolucion.setObservaciones((String)vRowTemp.get(2).toString());
                        oEvolucion.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(3).toString());
                        String nombre = (String)vRowTemp.get(4).toString() + " " + (String)vRowTemp.get(5).toString() + " " + (String)vRowTemp.get(6).toString();
                        oEvolucion.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres(nombre);
                        oEvolucion.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(((Double)vRowTemp.get(7)).intValue());
                        oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(8)).longValue());
                        oEvolucion.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(9)).longValue());
                        arrEvolucion.add(oEvolucion);
                    }
                }
            }
            return arrEvolucion;
        }
	public String getEvolucion() {
	return sEvolucion;
	}

	public void setEvolucion(String valor) {
	sEvolucion=valor;
	}

	public Date getFechaHora() {
	return dFechaHora;
	}

	public void setFechaHora(Date valor) {
	dFechaHora=valor;
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
} 
