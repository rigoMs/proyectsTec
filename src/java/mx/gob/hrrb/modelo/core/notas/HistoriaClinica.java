package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.PacienteNeonato;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Seguro;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class HistoriaClinica implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private DiagnosticoCIE10 oDiagnostico;
	private String sAbdomen;
	private String sCabezaCue;
	private String sExtremidades;
	private String sGenitales;
	private String sInspeccionGeneral;
	private String sInterrogatorio;
	private String sNerviosoSentidos;
	private String sPadecimientoAct;
	private String sPielAnexos;
	private String sResultadoEstudios;
	private String sTorax;
	private EpisodioMedico oEpisodioMedico;
        private String sMotivoConsulta;
        private String sManejoRecibido;
        private String sAntecgineco;
        private Hospitalizacion oHospitalizacion;
        private PacienteNeonato oPacienteNeonato;
        private int nHistoriaClinica;
        /*public HistoriaClinica(String usuario){
            this.sUsuarioFirmado = usuario;
        }*/
	public HistoriaClinica(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oEpisodioMedico = new EpisodioMedico();
                oHospitalizacion = new Hospitalizacion();
                oDiagnostico = new DiagnosticoCIE10();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HistoriaClinica.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveHistoriaClinica();"; 
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
	public HistoriaClinica[] buscarTodos() throws Exception{
	HistoriaClinica arrRet[]=null, oHistoriaClinica=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosHistoriaClinica();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new HistoriaClinica[rst.size()];
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
			throw new Exception("HistoriaClinica.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaHistoriaClinica('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("HistoriaClinica.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHistoriaClinica('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("HistoriaClinica.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaHistoriaClinica('"+sUsuarioFirmado+"');"; 
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
        //****************INICIAN METODOS DE CONTROL DE DATOS****************
        public PersonalHospitalario informacionMedico()throws Exception{
            PersonalHospitalario oPersonal = new PersonalHospitalario();
            oPersonal.setUsuar(new Usuario());
            oPersonal.getUsuar().setIdUsuario(sUsuarioFirmado);
            if(oPersonal.buscaPersonalHospitalarioDatos())
                return oPersonal;
            else
                return null;
        }        
        public void buscaDatosPacienteHistoriaClinica()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0 || this.getEpisodioMedico().getPaciente().getExpediente().getNumero() == 0)
                throw new Exception("buscaDatosPacienteHistoriaClinica:Noesposibleprocesarlainformacion");
            else{
                sQuery = "SELECT * FROM buscadatospacientehistoriaclinica(" + this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," + this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," + this.getEpisodioMedico().getPaciente().getExpediente().getNumero() + "::INTEGER);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getEpisodioMedico().setAreaServicioHRRB((String)vRowTemp.get(0).toString());
                    this.getEpisodioMedico().setFechaIngreso((Date)vRowTemp.get(1));
                    this.getEpisodioMedico().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    this.getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(3).toString());
                    this.getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(4).toString());
                    this.getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(5).toString());
                    this.getEpisodioMedico().getPaciente().setSeguro(new Seguro());
                    this.getEpisodioMedico().getPaciente().getSeguro().setNumero((String)vRowTemp.get(6).toString());
                    this.getEpisodioMedico().getPaciente().setFechaNac((Date)vRowTemp.get(7));
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");                    
                    this.getEpisodioMedico().getPaciente().setFechaNacTexto(format.format((Date)vRowTemp.get(7)));
                    this.getEpisodioMedico().getPaciente().calculaEdad();                    
                    this.getEpisodioMedico().getPaciente().setSexoP((String)vRowTemp.get(8).toString());
                    this.getEpisodioMedico().getPaciente().setEdoCivilStr((String)vRowTemp.get(9).toString());
                    this.getEpisodioMedico().getPaciente().getCiudadCP().getCiudad().getMunicipio().setDescripcionMun((String)vRowTemp.get(10).toString());
                    this.getEpisodioMedico().getPaciente().setCalleNum((String)vRowTemp.get(11).toString());
                    this.getEpisodioMedico().getPaciente().setColonia((String)vRowTemp.get(12).toString());
                    this.getEpisodioMedico().getPaciente().getEtnicidad().setHablaEspaniolP((String)vRowTemp.get(13).toString());
                    this.getEpisodioMedico().getPaciente().getExpediente().getAntecNoPatologicos().setOcupacion((String)vRowTemp.get(14).toString());
                    this.getEpisodioMedico().getFamiliarCercano().setNombresFam((String)vRowTemp.get(15).toString() + " "+ (String)vRowTemp.get(16).toString() + " " + (String)vRowTemp.get(17).toString() + " " + (String)vRowTemp.get(18).toString());
                }                
            }
        }
        public boolean insertaAnverso01()throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            String sQuery = "";
            int nRet = -1;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("HISTORIACLINICAINSERTAANVERSO01:NOPODEMOSPROCESARLAINFORMACIONFALTANDATOS");
            else{
                String motivoconsulta = this.getMotivoConsulta() == null || this.getMotivoConsulta().isEmpty() ? "null" + "::TEXT," : "'" + this.getMotivoConsulta() + "'::TEXT,";
                String padecimiento = this.getPadecimientoAct() == null || this.getPadecimientoAct().isEmpty() ? "null" + "::TEXT," : "'" + this.getPadecimientoAct() + "'::TEXT,";
                String manejo = this.getManejoRecibido() == null || this.getManejoRecibido().isEmpty() ?  "null" + "::TEXT," : "'" + this.getManejoRecibido() + "'::TEXT,";
                String interrogatorio = this.getInterrogatorio() == null || this.getInterrogatorio().isEmpty() ? "null" + "::TEXT);" : "'" + this.getInterrogatorio() + "'::TEXT);";
                sQuery = "SELECT * FROM insertactualizaanverso1historiaclinica("+ this.getEpisodioMedico().getPaciente().getFolioPaciente() +"::BIGINT," +
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT,"+ "'" + sUsuarioFirmado + "'::CHARACTER VARYING(10)," +
                        motivoconsulta + padecimiento + manejo + interrogatorio;                
                rstQuery = new ArrayList<String>();
                rstQuery.add(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                   nRet = oAD.ejecutarConsultaComando(rstQuery);
                   oAD.desconectar();
                   bandera = nRet > 0;
                }
            }
            return bandera;
        }
        public void buscaDatosAnverso01()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("BUSCADATOSANVERSO01:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultaanverso1historiaclinica(" + this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," + this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setMotivoConsulta((String)vRowTemp.get(0).toString());
                    this.setPadecimientoAct((String)vRowTemp.get(1).toString());
                    this.setManejoRecibido((String)vRowTemp.get(2).toString());
                    this.setInterrogatorio((String)vRowTemp.get(3).toString());
                }
            }
        }
        public void buscaDatosAnverso02()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("BUSCADATOSANVERSO02:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultaanverso2historiaclinica(" + this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," + this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);                    
                    this.getEpisodioMedico().getPaciente().setPeso(((Double)vRowTemp.get(0)).floatValue());
                    this.getEpisodioMedico().getPaciente().setTalla(((Double)vRowTemp.get(1)).floatValue());
                    this.getEpisodioMedico().getSignosVitales().setFC((String)vRowTemp.get(2).toString());
                    this.getEpisodioMedico().getSignosVitales().setFR((String)vRowTemp.get(3).toString());
                    this.getEpisodioMedico().getSignosVitales().setTA((String)vRowTemp.get(4).toString());
                    this.getEpisodioMedico().getSignosVitales().setTemp((String)vRowTemp.get(5).toString());
                    this.setInspeccionGeneral((String)vRowTemp.get(6).toString());
                    this.setCabezaCue((String)vRowTemp.get(7).toString());
                    this.setTorax((String)vRowTemp.get(8).toString());
                    this.setAbdomen((String)vRowTemp.get(9).toString());
                    this.setGenitales((String)vRowTemp.get(10).toString());
                }
            }
        }
        public boolean insertaReverso01()throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            String sQuery = "";
            int nRet = -1;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("HISOTORIACLINICAINSERTAREVERSO01:NOPODEMOSPORCESARLAINFORMACIONFALTANDATOS");
            else{
                String antecheredo = this.getEpisodioMedico().getPaciente().getExpediente().getAntecHeredoFamiliares().getOtros() == null ||
                        this.getEpisodioMedico().getPaciente().getExpediente().getAntecHeredoFamiliares().getOtros().isEmpty() ? "null" + "::TEXT," : "'" + this.getEpisodioMedico().getPaciente().getExpediente().getAntecHeredoFamiliares().getOtros() + "'::TEXT,";
                String antecnopato = this.getEpisodioMedico().getPaciente().getExpediente().getAntecNoPatologicos().getOtro() == null ||
                        this.getEpisodioMedico().getPaciente().getExpediente().getAntecNoPatologicos().getOtro().isEmpty() ? "null" + "::TEXT," : "'" + this.getEpisodioMedico().getPaciente().getExpediente().getAntecNoPatologicos().getOtro() + "'::TEXT,";
                String antecgineco = this.getAntecgineco() == null || this.getAntecgineco().isEmpty() ? "null" + "::TEXT," : "'" + this.getAntecgineco() + "'::TEXT,";
                String antecpato = this.getEpisodioMedico().getPaciente().getExpediente().getAntecPatologicos().getOtro() == null || 
                        this.getEpisodioMedico().getPaciente().getExpediente().getAntecPatologicos().getOtro().isEmpty() ? "null" + "::TEXT);" :"'" + this.getEpisodioMedico().getPaciente().getExpediente().getAntecPatologicos().getOtro() + "'::TEXT);";
                sQuery = "SELECT * FROM insertactualizareverso1historiaclinica(" + this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," + "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING(10)," +
                        antecheredo + antecnopato + antecgineco + antecpato;
                rstQuery = new ArrayList<String>();
                rstQuery.add(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(rstQuery);
                    oAD.desconectar();
                    bandera = nRet > 0;
                }
            }
            return bandera;
        }        
        public void buscaDatosReverso01()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("BUSCADATOSREVERSO01:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultareverso1historiaclinica(" + this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," + this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){                    
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getEpisodioMedico().getPaciente().getExpediente().getAntecHeredoFamiliares().setOtros((String)vRowTemp.get(0).toString());
                    this.getEpisodioMedico().getPaciente().getExpediente().getAntecNoPatologicos().setOtro((String)vRowTemp.get(1).toString());
                    this.setAntecgineco((String)vRowTemp.get(2).toString());
                    this.getEpisodioMedico().getPaciente().getExpediente().getAntecPatologicos().setOtro((String)vRowTemp.get(3).toString());
                }
            }
        }        
        public float getCalculaIMC(){
            if(this.getEpisodioMedico().getPaciente().getTalla() == 0 && this.getEpisodioMedico().getPaciente().getPeso() == 0)
                return 0;
            float altura = this.getEpisodioMedico().getPaciente().getTalla() / 100;
            float altura1 = altura * altura;
            float resultado = this.getEpisodioMedico().getPaciente().getPeso() / altura1;
            return resultado;
        }        
        public Medico[] buscaMedicos(short nOpcion)throws Exception{
            if(nOpcion == 0)
                return buscaMedico((String) "SELECT * FROM buscamedicohistoriaclinica();");            
            return null;
        }
        public Medico[] buscaMedico(String sQuery)throws Exception{
            Medico arrRet[] = null, oMedico = null;
            ArrayList rst = null;
            ArrayList <Medico> vObj = null;            
            int i = 0, nTam = 0;            
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }if(rst.isEmpty())
                return null;
            else{
                vObj = new ArrayList<Medico>();
                for(i = 0; i < rst.size(); i++){
                    oMedico = new Medico();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oMedico.setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                    oMedico.setNombres((String)vRowTemp.get(1).toString());
                    oMedico.setApPaterno((String)vRowTemp.get(2).toString());
                    oMedico.setApMaterno((String)vRowTemp.get(3).toString());
                    oMedico.setCedProf((String)vRowTemp.get(4).toString());
                    vObj.add(oMedico);
                }
                nTam = vObj.size();
                arrRet = new Medico[nTam];
                for(i = 0; i < nTam; i++)
                    arrRet[i] = vObj.get(i);
            }
            return arrRet;
        }
        public boolean insertaDatosReverso02(ArrayList<DiagnosticoCIE10> arrDiagnostico, int tam)throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            ArrayList<String> rstTemporal = null;
            String sQuery = "";
            int nRet = -1;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("HISTORIACLINICAINSERTAREVERSO02:NOPODEMOSPROCESARLAINFORMACIONFALTANDATOS");
            else{
                rstQuery = new ArrayList<String>();
                String extremidades = this.getExtremidades() == null || this.getExtremidades().isEmpty() ? "null" + "::TEXT," : "'" + this.getExtremidades() + "'::TEXT,";
                String piel = this.getPielAnexos() == null || this.getPielAnexos().isEmpty() ? "null" + "::TEXT," : "'" + this.getPielAnexos() + "'::TEXT,";
                String snervioso = this.getNerviosoSentidos() == null || this.getNerviosoSentidos().isEmpty() ? "null" + "::TEXT," : "'" + this.getNerviosoSentidos() + "'::TEXT,";
                String estudios = this.getResultadoEstudios() == null || this.getResultadoEstudios().isEmpty() ? "null" + "::TEXT," : "'" + this.getResultadoEstudios() + "'::TEXT,";
                String clavedx = this.getDiagnostico().getClave() == null || this.getDiagnostico().getClave().isEmpty() ? "null" + "::CHARACTER(6)," : "'" + this.getDiagnostico().getClave() + "'::CHARACTER(6),";
                String plan = this.getHosptilatazacion().getPlan() == null || this.getHosptilatazacion().getPlan().isEmpty() ? "null" + "::TEXT," : "'" + this.getHosptilatazacion().getPlan() + "'::TEXT,";
                String tarjeta = this.getEpisodioMedico().getMedicoTratante().getNoTarjeta() == 0 ? "null" + "::INTEGER);" : this.getEpisodioMedico().getMedicoTratante().getNoTarjeta() + "::INTEGER);";
                sQuery = "SELECT * FROM insertactualizareverso2historiaclinica(" + this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING(10)," +
                        extremidades + piel + snervioso + estudios + clavedx + plan + tarjeta;                
                rstQuery.add(sQuery);
                if(!arrDiagnostico.isEmpty()){
                    SegundaMitadEmbarazo oSegundaMitad = new SegundaMitadEmbarazo();
                    rstTemporal = oSegundaMitad.armaQueryPartoGrama(this.getEpisodioMedico().getPaciente().getFolioPaciente(), this.getEpisodioMedico().getPaciente().getClaveEpisodio(), arrDiagnostico, tam);
                    for (String rstTemporal1 : rstTemporal) {
                        rstQuery.add((String) rstTemporal1);
                    }
                }
                if(rstQuery.size() > 0){
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){                        
                        nRet = oAD.ejecutarConsultaComando(rstQuery);
                        oAD.desconectar();
                        bandera = nRet >= 0;
                    }
                }
            }
            return bandera;
        }
        public boolean insertaAnverso02()throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            String sQuery = "";
            int nRet = -1;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("HISTORIACLINICAINSERTANAVERSO02:NOPODEMOSPORCESARLAINFORMACIONFALTANDATOS");
            else{
                String peso = this.getEpisodioMedico().getPaciente().getPeso() == 0.0 ? "null" + "::NUMERIC(6,3)," : this.getEpisodioMedico().getPaciente().getPeso() + "::NUMERIC(6,3),";
                String  talla = this.getEpisodioMedico().getPaciente().getTalla() == 0.0 ? "null" + "::NUMERIC(4,1)," : this.getEpisodioMedico().getPaciente().getTalla() + "::NUMERIC(4,1),";
                String fc = this.getEpisodioMedico().getSignosVitales().getFC() == null || this.getEpisodioMedico().getSignosVitales().getFC().isEmpty() ? "null" + "::CHARACTER VARYING(15)," : "'" + this.getEpisodioMedico().getSignosVitales().getFC() + "'::CHARACTER VARYING(15),";
                String fr = this.getEpisodioMedico().getSignosVitales().getFR() == null || this.getEpisodioMedico().getSignosVitales().getFR().isEmpty() ? "null" + "::CHARACTER VARYING(15)," : "'" + this.getEpisodioMedico().getSignosVitales().getFR() + "'::CHARACTER VARYING(15),";                
                String ta = this.getEpisodioMedico().getSignosVitales().getTA() == null || this.getEpisodioMedico().getSignosVitales().getTA().isEmpty() ? "null" + "::CHARACTER VARYING(15)," : "'" + this.getEpisodioMedico().getSignosVitales().getTA() + "'::CHARACTER VARYING(15),";
                String temp = this.getEpisodioMedico().getSignosVitales().getTemp() == null || this.getEpisodioMedico().getSignosVitales().getTemp().isEmpty() ? "null" + "::CHARACTER VARYING(5)," : "'" + this.getEpisodioMedico().getSignosVitales().getTemp() + "'::CHARACTER VARYING(15),";
                String inspeccion = this.getInspeccionGeneral() == null || this.getInspeccionGeneral().isEmpty() ? "null" + "::TEXT," : "'" + this.getInspeccionGeneral() + "'::TEXT,";
                String cabeza = this.getCabezaCue() == null || this.getCabezaCue().isEmpty() ? "null" + "::TEXT," : "'" + this.getCabezaCue() + "'::TEXT,";
                String torax = this.getTorax() == null || this.getTorax().isEmpty() ? "null" + "::TEXT," : "'" +  this.getTorax() + "'::TEXT,";
                String abdomen = this.getAbdomen() == null || this.getAbdomen().isEmpty() ? "null" + "::TEXT," : "'" + this.getAbdomen() + "'::TEXT,";
                String genitales = this.getGenitales() == null || this.getGenitales().isEmpty() ? "null" + "::TEXT);" : "'" + this.getGenitales() + "'::TEXT);";
                sQuery = "SELECT * FROM insertactualizaanverso2historiaclinica("+ this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio()+ "::BIGINT," + "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING(10)," + peso +
                        talla + fc + fr + ta + temp + inspeccion + cabeza + torax + abdomen + genitales;
                rstQuery = new ArrayList<String>();
                rstQuery.add(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(rstQuery);
                    oAD.desconectar();
                    bandera = nRet > 0;
                }
            }
            return bandera;            
        }
        public void buscaDatosReverso02()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("BUSCADATOSREVESO02:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM  consultareverso2historiaclinica(" + this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," + this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);                    
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setExtremidades((String)vRowTemp.get(0).toString());
                    this.setPielAnexos((String)vRowTemp.get(1).toString());
                    this.setNerviosoSentidos((String)vRowTemp.get(2).toString());
                    this.setResultadoEstudios((String)vRowTemp.get(3).toString());
                    this.getDiagnostico().setClave((String)vRowTemp.get(4).toString());
                    this.getDiagnostico().setDescripcionDiag((String)vRowTemp.get(5).toString());
                    this.getHosptilatazacion().setPlan((String)vRowTemp.get(6).toString());
                    this.getEpisodioMedico().getMedicoTratante().setCedProf((String)vRowTemp.get(7).toString());
                    this.getEpisodioMedico().getMedicoTratante().setNombres((String)vRowTemp.get(8).toString());
                    this.getEpisodioMedico().getMedicoTratante().setApPaterno((String)vRowTemp.get(9).toString());
                    this.getEpisodioMedico().getMedicoTratante().setApMaterno((String)vRowTemp.get(10).toString());                    
                }
            }
        }
        public HistoriaClinica[] buscaPacienteHistoriaClinica()throws Exception{
            HistoriaClinica[] arrRet = null;
            HistoriaClinica oHistoriaClinica = null;
            ArrayList rst = null;
            ArrayList<HistoriaClinica> vObj = null;
            String sQuery = "";
            String nombre = "";
            String appaterno = "";
            String apmaterno = "";
            String numexp = "";
            int i = 0;
            int nTam = 0;
            if(this.getEpisodioMedico().getPaciente().getOpcionUrg() == 0){
                nombre = this.getEpisodioMedico().getPaciente().getNombres();
                appaterno = this.getEpisodioMedico().getPaciente().getApPaterno();
                apmaterno = this.getEpisodioMedico().getPaciente().getApMaterno();
                numexp = "null";                
            }else{
                nombre = "";
                appaterno = "";
                apmaterno = "";
                numexp = this.getEpisodioMedico().getPaciente().getExpediente().getNumero() + "";
            }
            if(numexp.compareTo("null") == 0)
                sQuery = "SELECT * FROM buscahistoriasclinicaspaciente('" + nombre + "','" + appaterno + "','" + apmaterno + "'," + numexp +");";
            else
                sQuery = "SELECT * FROM buscahistoriasclinicaspaciente('" + nombre + "','" + appaterno + "','" + apmaterno + "'," + Integer.parseInt(numexp) +");";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() > 0){
                arrRet = new HistoriaClinica[rst.size()];
                for(i = 0; i < rst.size(); i++){
                    oHistoriaClinica = new HistoriaClinica();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).intValue());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setFechaNac((Date)vRowTemp.get(3));
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(4).toString());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(5).toString());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(6).toString());
                    arrRet[i] = oHistoriaClinica;
                }
            }
            return arrRet;
        } 
    
        public HistoriaClinica[] buscaHistorialHClinica(long folioPac) throws Exception{
        HistoriaClinica arrRet[]=null, oHC=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("HistoriaClinica.buscaHistorialHClinica: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialHClinica("+folioPac+");"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new HistoriaClinica[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oHC= new HistoriaClinica();
                    oHC.getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oHC.getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).intValue());
                    oHC.setNumeroHistoriaClinca(((Double)vRowTemp.get(2)).intValue());
                    oHC.getEpisodioMedico().setFechaIngreso((Date)vRowTemp.get(3));
                    arrRet[i]=oHC;
                } 
            } 
        }
        return arrRet; 
    }
        
    public void buscaDatosAnverso01EXP()throws Exception{
        ArrayList rst = null;
        String sQuery = null;
        if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || 
           this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0 ||
           this.getNumeroHistoriaClinica() == 0)
            throw new Exception("BUSCADATOSANVERSO01EXP:NOPODEMOSPROCESARLAINFORMACION");
        else{
            sQuery = "SELECT * FROM consultaanverso1historiaclinicaEXP(" + 
                    this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," + 
                    this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," +
                    this.getNumeroHistoriaClinica() + "::BIGINT);"; 
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                
                oAD.desconectar();
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setMotivoConsulta((String)vRowTemp.get(0).toString());
                this.setPadecimientoAct((String)vRowTemp.get(1).toString());
                this.setManejoRecibido((String)vRowTemp.get(2).toString());
                this.setInterrogatorio((String)vRowTemp.get(3).toString());
            }
        }
    }
    
    public void buscaDatosReverso01EXP()throws Exception{
        ArrayList rst = null;
        String sQuery = null;
        if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || 
                this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0 ||
                this.getNumeroHistoriaClinica() == 0)
            throw new Exception("BUSCADATOSREVERSO01EXP:NOPODEMOSPROCESARLAINFORMACION");
        else{
            sQuery = "SELECT * FROM consultareverso1historiaclinicaEXP(" + 
                    this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," + 
                    this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," +
                    this.getNumeroHistoriaClinica() + "::BIGINT);";                 
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(!rst.isEmpty()){                    
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.getEpisodioMedico().getPaciente().getExpediente().getAntecHeredoFamiliares().setOtros((String)vRowTemp.get(0).toString());
                this.getEpisodioMedico().getPaciente().getExpediente().getAntecNoPatologicos().setOtro((String)vRowTemp.get(1).toString());
                this.setAntecgineco((String)vRowTemp.get(2).toString());
                this.getEpisodioMedico().getPaciente().getExpediente().getAntecPatologicos().setOtro((String)vRowTemp.get(3).toString());
            }
        }
    }
    
    public void buscaDatosAnverso02EXP()throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || 
                this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0 ||
                this.getNumeroHistoriaClinica() == 0)
            throw new Exception("BUSCADATOSANVERSO02EXP:NOPODEMOSPROCESARLAINFORMACION");
        else{
            sQuery = "SELECT * FROM consultaanverso2historiaclinicaEXP(" +
                this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," + 
                this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," +
                this.getNumeroHistoriaClinica() + "::BIGINT);"; 
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);                    
                this.getEpisodioMedico().getPaciente().setPeso(((Double)vRowTemp.get(0)).floatValue());
                this.getEpisodioMedico().getPaciente().setTalla(((Double)vRowTemp.get(1)).floatValue());
                this.getEpisodioMedico().getSignosVitales().setFC((String)vRowTemp.get(2).toString());
                this.getEpisodioMedico().getSignosVitales().setFR((String)vRowTemp.get(3).toString());
                this.getEpisodioMedico().getSignosVitales().setTA((String)vRowTemp.get(4).toString());
                this.getEpisodioMedico().getSignosVitales().setTemp((String)vRowTemp.get(5).toString());
                this.setInspeccionGeneral((String)vRowTemp.get(6).toString());
                this.setCabezaCue((String)vRowTemp.get(7).toString());
                this.setTorax((String)vRowTemp.get(8).toString());
                this.setAbdomen((String)vRowTemp.get(9).toString());
                this.setGenitales((String)vRowTemp.get(10).toString());
            }
        }
    }
    
    public void buscaDatosReverso02EXP()throws Exception{
        ArrayList rst = null;
        String sQuery = null;
        if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 ||
                this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0 ||
                this.getNumeroHistoriaClinica() == 0)
            throw new Exception("BUSCADATOSREVESO02EXP:NOPODEMOSPROCESARLAINFORMACION");
        else{
            sQuery = "SELECT * FROM  consultareverso2historiaclinicaEXP(" + 
                    this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," +
                    this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," +
                    this.getNumeroHistoriaClinica() + "::BIGINT);";                
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);                    
                oAD.desconectar();
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setExtremidades((String)vRowTemp.get(0).toString());
                this.setPielAnexos((String)vRowTemp.get(1).toString());
                this.setNerviosoSentidos((String)vRowTemp.get(2).toString());
                this.setResultadoEstudios((String)vRowTemp.get(3).toString());
                this.getDiagnostico().setClave((String)vRowTemp.get(4).toString());
                this.getDiagnostico().setDescripcionDiag((String)vRowTemp.get(5).toString());
                this.getHosptilatazacion().setPlan((String)vRowTemp.get(6).toString());
                this.getEpisodioMedico().getMedicoTratante().setCedProf((String)vRowTemp.get(7).toString());
                this.getEpisodioMedico().getMedicoTratante().setNombres((String)vRowTemp.get(8).toString());
                this.getEpisodioMedico().getMedicoTratante().setApPaterno((String)vRowTemp.get(9).toString());
                this.getEpisodioMedico().getMedicoTratante().setApMaterno((String)vRowTemp.get(10).toString());                    
            }
        }
    }
    
    
        //****************TERMINAN METODOS DE CONTROL DE DATOS****************
        
	public DiagnosticoCIE10 getDiagnostico() {
	return oDiagnostico;
	}

	public void setDiagnostico(DiagnosticoCIE10 valor) {
	oDiagnostico=valor;
	}

	public String getAbdomen() {
	return sAbdomen;
	}

	public void setAbdomen(String valor) {
	sAbdomen=valor;
	}

	public String getCabezaCue() {
	return sCabezaCue;
	}

	public void setCabezaCue(String valor) {
	sCabezaCue=valor;
	}

	public String getExtremidades() {
	return sExtremidades;
	}

	public void setExtremidades(String valor) {
	sExtremidades=valor;
	}

	public String getGenitales() {
	return sGenitales;
	}

	public void setGenitales(String valor) {
	sGenitales=valor;
	}

	public String getInspeccionGeneral() {
	return sInspeccionGeneral;
	}

	public void setInspeccionGeneral(String valor) {
	sInspeccionGeneral=valor;
	}

	public String getInterrogatorio() {
	return sInterrogatorio;
	}

	public void setInterrogatorio(String valor) {
	sInterrogatorio=valor;
	}

	public String getNerviosoSentidos() {
	return sNerviosoSentidos;
	}

	public void setNerviosoSentidos(String valor) {
	sNerviosoSentidos=valor;
	}

	public String getPadecimientoAct() {
	return sPadecimientoAct;
	}

	public void setPadecimientoAct(String valor) {
	sPadecimientoAct=valor;
	}

	public String getPielAnexos() {
	return sPielAnexos;
	}

	public void setPielAnexos(String valor) {
	sPielAnexos=valor;
	}

	public String getResultadoEstudios() {
	return sResultadoEstudios;
	}

	public void setResultadoEstudios(String valor) {
	sResultadoEstudios=valor;
	}

	public String getTorax() {
	return sTorax;
	}

	public void setTorax(String valor) {
	sTorax=valor;
	}

	public EpisodioMedico getEpisodioMedico() {
	return oEpisodioMedico;
	}

	public void setEpisodioMedico(EpisodioMedico valor) {            
	oEpisodioMedico=valor;
	}
        public String getMotivoConsulta(){            
            return sMotivoConsulta;
        }
        public void setMotivoConsulta(String sMotivoConsulta){
            this.sMotivoConsulta = sMotivoConsulta;              
        }
        public String getManejoRecibido(){
            return sManejoRecibido;
        }
        public void setManejoRecibido(String sManejoRecibido){
            this.sManejoRecibido = sManejoRecibido;
        }
        public String getAntecgineco(){
            return sAntecgineco;
        }
        public void setAntecgineco(String sAntecgineco){
            this.sAntecgineco = sAntecgineco;
        }
        public Hospitalizacion getHosptilatazacion(){
            return oHospitalizacion;
        }
        public void setHospitalizacion(Hospitalizacion oHospitalizacion){
            this.oHospitalizacion = oHospitalizacion;
        }
        public PacienteNeonato getPacientenNeonato(){
            return oPacienteNeonato;
        }
        public void setPacienteNeonato(PacienteNeonato oPacienteNeonato){
            this.oPacienteNeonato = oPacienteNeonato;
        }
        public int getNumeroHistoriaClinica(){
            return nHistoriaClinica;
        }
        public void setNumeroHistoriaClinca(int nHistoriaClinica){
            this.nHistoriaClinica = nHistoriaClinica;
        }
} 
