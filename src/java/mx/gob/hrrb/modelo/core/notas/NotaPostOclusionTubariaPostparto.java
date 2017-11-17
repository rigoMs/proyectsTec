/*
* MODIFICACIONES REALIZADAS POR ALBERTO
* AGREGUE EL OBJETO TERMINACION EMBARAZO
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
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class NotaPostOclusionTubariaPostparto implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nCuantificacionSangrado;
	private String sCompInsiAcci;
	private String sDiagPostoperatorio;
	private Date dFecha;
	private String sHayasgos;
	private Date dHora;
	private String sMotivoDeNoEfectuo;
	private String sPlanManejoPostoperatorio;
	private String sRepGasasCompresas;
	private String sTecnica;
        private TerminacionEmbarazo oTerminacionEmbarazo;
        private DiagnosticoCIE10 oDiagnosticopost;

	public NotaPostOclusionTubariaPostparto(){            
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oTerminacionEmbarazo = new TerminacionEmbarazo();
                this.oDiagnosticopost = new DiagnosticoCIE10();
                
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaPostOclusionTubariaPostparto.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveNotaPostOclusionTubariaPostparto();"; 
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
	public NotaPostOclusionTubariaPostparto[] buscarTodos() throws Exception{
	NotaPostOclusionTubariaPostparto arrRet[]=null, oNotaPostOclusionTubariaPostparto=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosNotaPostOclusionTubariaPostparto();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new NotaPostOclusionTubariaPostparto[rst.size()];
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
			throw new Exception("NotaPostOclusionTubariaPostparto.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaNotaPostOclusionTubariaPostparto('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaPostOclusionTubariaPostparto.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaNotaPostOclusionTubariaPostparto('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaPostOclusionTubariaPostparto.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaNotaPostOclusionTubariaPostparto('"+sUsuarioFirmado+"');"; 
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
        public String getQueryPostoperatorio(){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String sQuery = "";
            if(this.getFecha() == null || this.getFecha().equals(""))
                return sQuery;
            else{
                String fecha = this.getFecha() == null || this.getFecha().equals("") ? null + "::timestamp without time zone," : "'" + format.format(this.getFecha()) + "'::timestamp without time zone,";
                String diagnostico = this.getDiagnosticoPostoclusion().getClaveCIE10() == null || this.getDiagnosticoPostoclusion().getClaveCIE10().isEmpty() ? null + "::CHARACTER(6)," : "'" + this.getDiagnosticoPostoclusion().getClaveCIE10().toUpperCase() + "'::CHARACTER(6),";                
                String tecnica = this.getTecnica() == null || this.getTecnica().isEmpty() ? null + "::CHARACTER VARYING," : "'" + this.getTecnica().toUpperCase() + "'::CHARACTER VARYING,";
                String complicaciones = this.getCompInsiAcci() == null || this.getCompInsiAcci().isEmpty() ? null + "::TEXT," : "'" + this.getCompInsiAcci().toUpperCase() + "'::TEXT,";
                String hallazgos = this.getHayasgos() == null || this.getHayasgos().isEmpty() ? null + "::TEXT," : "'" + this.getHayasgos().toUpperCase() + "'::TEXT,";
                String repgazas = this.getRepGasasCompresas() == null || this.getRepGasasCompresas().isEmpty() ? null + "::CHARACTER VARYING," : "'" + this.getRepGasasCompresas().toUpperCase() + "'::CHARACTER VARYING,";
                String cuantificacionsangrado = this.getCuantificacionSangrado() != 0 ? this.getCuantificacionSangrado() + "::INTEGER," : 0 + "::INTEGER,";
                String planmanejo = this.getPlanManejoPostoperatorio() == null || this.getPlanManejoPostoperatorio().isEmpty() ? null + "::TEXT," : "'" + this.getPlanManejoPostoperatorio().toUpperCase() + "'::TEXT,";
                String motivo = this.getMotivoDeNoEfectuo() == null || this.getMotivoDeNoEfectuo().isEmpty() ? null + "::TEXT," : "'" + this.getMotivoDeNoEfectuo().toUpperCase() + "'::TEXT,";
                String ntarjeta = this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() != 0 ? this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER);" : null + "::INTEGER);";
                sQuery = "SELECT * FROM insertactualizanotapostoclusion(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT,"+
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT,'" +
                        this.sUsuarioFirmado + "'::CHARACTER VARYING,"+
                        fecha + diagnostico + tecnica + complicaciones +
                        hallazgos + repgazas + cuantificacionsangrado + planmanejo + motivo + ntarjeta;
            }
            return sQuery;
        }
        public int insertaReverso(ArrayList<String> arrQuery, String querypreoperatorio, String sQueryPosoperatorio)throws Exception{
            ArrayList<String> listQuery = new ArrayList<String>();            
            int nRet = -1;
            if(sQueryPosoperatorio.compareTo("") ==0 && querypreoperatorio.compareTo("") != 0){
                if(arrQuery == null || arrQuery.isEmpty())
                    listQuery.add(querypreoperatorio);                    
                else{
                    for(int j = 0; j < arrQuery.size(); j++)
                        listQuery.add(arrQuery.get(j));
                    listQuery.add(querypreoperatorio);                    
                }    
            }else if(querypreoperatorio.compareTo("") == 0 && sQueryPosoperatorio.compareTo("") != 0){
                if(arrQuery == null || arrQuery.isEmpty())
                    listQuery.add(sQueryPosoperatorio);
                else{
                    for(int j = 0; j < arrQuery.size(); j++)
                        listQuery.add(arrQuery.get(j));
                    listQuery.add(sQueryPosoperatorio);
                }
            }else if(querypreoperatorio.compareTo("") != 0 && sQueryPosoperatorio.compareTo("") != 0){
                if(arrQuery == null || arrQuery.isEmpty()){
                    listQuery.add(querypreoperatorio);
                    listQuery.add(sQueryPosoperatorio);
                }else{
                    for(int j = 0; j < arrQuery.size(); j++)
                        listQuery.add(arrQuery.get(j));
                    listQuery.add(querypreoperatorio);
                    listQuery.add(sQueryPosoperatorio);
                }
            }            
            if(listQuery.size() > 0){
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(listQuery);
                    oAD.desconectar();
                }
            }                          
            return nRet;
        }
        public void detalleNotaPostOclusionTubariaPostParto(short opc)throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION:FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM  buscadetallenotapostoclusiontubaria(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() +"::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo()+"::SMALLINT," + this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::SMALLINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                if(opc == 0){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setFecha((Date)vRowTemp.get(0));
                    this.setDiagPostoperatorio((String)vRowTemp.get(1).toString() + " " + (String)vRowTemp.get(2).toString());
                    this.setTecnica((String)vRowTemp.get(3).toString());
                    this.setCompInsiAcci((String)vRowTemp.get(4).toString());
                    this.setHayasgos((String)vRowTemp.get(5).toString());
                    this.setRepGasasCompresas((String)vRowTemp.get(6).toString());
                    this.setCuantificacionSangrado(((Double)vRowTemp.get(7)).intValue());
                    this.setPlanManejoPostoperatorio((String)vRowTemp.get(8).toString());
                    this.setMotivoDeNoEfectuo((String)vRowTemp.get(9).toString());
                    this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(10).toString());
                    this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres((String)vRowTemp.get(11).toString());
                    this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApPaterno((String)vRowTemp.get(12).toString());
                    this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApMaterno((String)vRowTemp.get(13).toString());
                }else if(opc == 1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setFecha((Date)vRowTemp.get(0));                    
                    this.getDiagnosticoPostoclusion().setClave((String)vRowTemp.get(1).toString());
                    this.getDiagnosticoPostoclusion().setDescripcionDiag((String)vRowTemp.get(2).toString());
                    this.setTecnica((String)vRowTemp.get(3).toString());
                    this.setCompInsiAcci((String)vRowTemp.get(4).toString());
                    this.setHayasgos((String)vRowTemp.get(5).toString());
                    this.setRepGasasCompresas((String)vRowTemp.get(6).toString());
                    this.setCuantificacionSangrado(((Double)vRowTemp.get(7)).intValue());
                    this.setPlanManejoPostoperatorio((String)vRowTemp.get(8).toString());
                    this.setMotivoDeNoEfectuo((String)vRowTemp.get(9).toString());
                    this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(10).toString());
                    this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres((String)vRowTemp.get(11).toString() + " " + (String)vRowTemp.get(12).toString() + " " + (String)vRowTemp.get(13).toString());
                }
                
            }else{
                this.setFecha(null);
                this.setDiagPostoperatorio("");
                this.setTecnica("");
                this.setCompInsiAcci("");
                this.setHayasgos("");
                this.setRepGasasCompresas("");
                this.setCuantificacionSangrado(0);
                this.setPlanManejoPostoperatorio("");
                this.setMotivoDeNoEfectuo("");                                
                this.getDiagnosticoPostoclusion().setClave("");
                this.getDiagnosticoPostoclusion().setDescripcionDiag("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApPaterno("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApMaterno("");
                
            }
        }
	public int getCuantificacionSangrado() {
	return nCuantificacionSangrado;
	}

	public void setCuantificacionSangrado(int valor) {
	nCuantificacionSangrado=valor;
	}

	public String getCompInsiAcci() {
	return sCompInsiAcci;
	}

	public void setCompInsiAcci(String valor) {
	sCompInsiAcci=valor;
	}

	public String getDiagPostoperatorio() {
	return sDiagPostoperatorio;
	}

	public void setDiagPostoperatorio(String valor) {
	sDiagPostoperatorio=valor;
	}

	public Date getFecha() {
	return dFecha;
	}

	public void setFecha(Date valor) {
	dFecha=valor;
	}

	public String getHayasgos() {
	return sHayasgos;
	}

	public void setHayasgos(String valor) {
	sHayasgos=valor;
	}

	public Date getHora() {
	return dHora;
	}

	public void setHora(Date valor) {
	dHora=valor;
	}

	public String getMotivoDeNoEfectuo() {
	return sMotivoDeNoEfectuo;
	}

	public void setMotivoDeNoEfectuo(String valor) {
	sMotivoDeNoEfectuo=valor;
	}

	public String getPlanManejoPostoperatorio() {
	return sPlanManejoPostoperatorio;
	}

	public void setPlanManejoPostoperatorio(String valor) {
	sPlanManejoPostoperatorio=valor;
	}

	public String getRepGasasCompresas() {
	return sRepGasasCompresas;
	}

	public void setRepGasasCompresas(String valor) {
	sRepGasasCompresas=valor;
	}

	public String getTecnica() {
	return sTecnica;
	}

	public void setTecnica(String valor) {
	sTecnica=valor;
	}
        public TerminacionEmbarazo getTerminacionEmbarazo(){
            return oTerminacionEmbarazo;
        }
        public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
            this.oTerminacionEmbarazo = oTerminacionEmbarazo;
        }
        public DiagnosticoCIE10 getDiagnosticoPostoclusion(){
            return oDiagnosticopost;
        }
        public void setDiagnosticoPostoclusion(DiagnosticoCIE10 oDiagnosticopost){
            this.oDiagnosticopost = oDiagnosticopost;
        }
        public boolean getHabilitaMedico(){            
            return this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() != 0;
        }
        public boolean getHabilitaCompletePostoclusion(){            
            return this.getDiagnosticoPostoclusion().getClave().compareTo("") != 0;            
        }
} 
