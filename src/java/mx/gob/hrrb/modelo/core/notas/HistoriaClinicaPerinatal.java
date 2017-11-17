package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.PacienteNeonato;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class HistoriaClinicaPerinatal extends HistoriaClinica implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nEgFur;
	private String sObservaciones;
	private ValoracionNeurologica oValoracionNeurologica;
	private SignosVitalesNeoNatos oSignosVitalesNeoNatos;
        private AtencionNeonatalTococirugia oAtencion;
        private PersonalHospitalario oPersonal;
        private Parametrizacion oNivelEducacion;
        private Parametrizacion oEstadoCivil;
        private float npeso;        
	public HistoriaClinicaPerinatal(){   
	HttpServletRequest req;
                oPersonal = new PersonalHospitalario();
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.setPacienteNeonato(new PacienteNeonato());
                this.oPersonal.setUsuar(new Usuario());
                this.oPersonal.getUsuar().setIdUsuario(this.sUsuarioFirmado);
                oSignosVitalesNeoNatos = new SignosVitalesNeoNatos();
                oAtencion = new AtencionNeonatalTococirugia();
                oNivelEducacion = new Parametrizacion();
                oEstadoCivil = new Parametrizacion();                
	}
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HistoriaClinicaPerinatal.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveHistoriaClinicaPerinatal();"; 
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
        @Override
	public HistoriaClinicaPerinatal[] buscarTodos() throws Exception{
	HistoriaClinicaPerinatal arrRet[]=null, oHistoriaClinicaPerinatal=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosHistoriaClinicaPerinatal();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new HistoriaClinicaPerinatal[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HistoriaClinicaPerinatal.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaHistoriaClinicaPerinatal('"+sUsuarioFirmado+"');"; 
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
        @Override
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HistoriaClinicaPerinatal.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHistoriaClinicaPerinatal('"+sUsuarioFirmado+"');"; 
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
        @Override
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HistoriaClinicaPerinatal.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaHistoriaClinicaPerinatal('"+sUsuarioFirmado+"');"; 
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
        //************INICIAN METODOS DE CONTROL DE DATOS*********************
        public void buscaPersonal()throws Exception{
            this.oPersonal.buscaPersonalHospitalarioDatos();
        }
        public void cargaDatosPaciente()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getPacientenNeonato().getFolioPaciente() == 0 || this.getPacientenNeonato().getClaveEpisodio() == 0)
                throw new Exception("CARAGADATOSPACIENTE.HISTORIACLINICAPERINATAL:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadatospacienteneonato(" + this.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," +
                        this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," +
                        this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getPacientenNeonato().setNombres((String)vRowTemp.get(0).toString());
                    this.getPacientenNeonato().setApPaterno((String)vRowTemp.get(1).toString());
                    this.getPacientenNeonato().setApMaterno((String)vRowTemp.get(2).toString());
                    this.getPacientenNeonato().setSexoP((String)vRowTemp.get(3).toString());
                    this.getPacientenNeonato().setFechaNac((Date)vRowTemp.get(4));                                                    
                    this.getPacientenNeonato().getExpediente().setNumero(((Double)vRowTemp.get(5)).intValue());
                    this.getPacientenNeonato().setPeso(((Double)vRowTemp.get(6)).floatValue());
                    this.getPacientenNeonato().setLugarNacimiento((String)vRowTemp.get(7).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setNombres((String)vRowTemp.get(8).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(9).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(10).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNac( (Date)vRowTemp.get(11));                    
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setEdoCivilStr((String)vRowTemp.get(12).toString());                                                
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setEscolaridad((String)vRowTemp.get(13).toString());                    
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setOcupacion((String)vRowTemp.get(14).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setGestaciones(((Double)vRowTemp.get(15)).intValue());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setPartos(((Double)vRowTemp.get(16)).intValue());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setAbortos(((Double)vRowTemp.get(17)).intValue());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setCesareas(((Double)vRowTemp.get(18)).intValue());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setUltimaMenstruacion((Date)vRowTemp.get(19));
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setFechaUltimoParto((Date)vRowTemp.get(20));
                }                
            }            
        }        
        public boolean  insertaAnverso001()throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            String sQuery = "";
            int nRet = -1;
            if(this.getPacientenNeonato().getFolioPaciente() == 0  || this.getPacientenNeonato().getClaveEpisodio() == 0)
                throw new Exception("HISTORIACLINICAPERINATALANVERSO1:NOPODEMOS PROCESARLAINFROMACION");
            else{
                String fechanacpac = this.getPacientenNeonato().getFechaNac() == null ? "null::DATE," : "'" + this.getPacientenNeonato().getFechaNac() + "'::DATE,";
                String peso = this.getPacientenNeonato().getPeso() == 0 ? 0 + "::NUMERIC(6,3)," : this.getPacientenNeonato().getPeso() + "::NUMERIC(6,3),";
                String lnacimiento = this.getPacientenNeonato().getLugarNacimiento() == null || this.getPacientenNeonato().getLugarNacimiento().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getPacientenNeonato().getLugarNacimiento().toUpperCase() + "'::CHARACTER VARYING,";
                String nmama = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getNombres() == null || this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getNombres().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getNombres() + "'::CHARACTER VARYING,";
                String apmama = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno() == null || this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno() + "'::CHARACTER VARYING,";
                String ammama = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno() == null || this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno() + "'::CHARACTER VARYING,";
                String fnamama = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac() == null ? "null::DATE," : "'" +this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac() + "'::DATE,";
                String edoCivil = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getEdoCivilStr() == null || this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getEdoCivilStr().isEmpty() ? "null::CHARACTER(5)," : "'" + this.getEstadoCivil().getClaveParametro() + this.getEstadoCivil().getTipoParametro() + "'::CHARACTER(5),";
                String educacion = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getEscolaridad() == null || this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getEscolaridad().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getEscolaridad().toUpperCase() + "'::CHARACTER VARYING,";
                String ocupacion = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOcupacion() == null || this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOcupacion().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOcupacion().toUpperCase() + "'::CHARACTER VARYING,";
                String padecimientoact = this.getPadecimientoAct() == null || this.getPadecimientoAct().isEmpty() ? "null::TEXT,": "'" + this.getPadecimientoAct().toUpperCase() + "'::TEXT,";
                String gestas = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getGestaciones() == 0 ? 0 + "::INTEGER," : this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getGestaciones() + "::INTEGER,";
                String partos = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getPartos() == 0 ? 0 + "::INTEGER," : this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getPartos() + "::INTEGER,";
                String abortos = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getAbortos() == 0 ? 0 + "::INTEGER," : this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getAbortos() + "::INTEGER,";
                String cesareas = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getCesareas() == 0 ? 0 + "::SMALLINT," : this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getCesareas() + "::SMALLINT,";
                String fup = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getFechaUltimoParto() == null ? "null::DATE," : "'" + this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getFechaUltimoParto() + "'::DATE,";
                String fur = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion() == null ? "null::DATE," : "'" + this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion() + "'::DATE,"; 
                String egfur = this.getAtencion().getEdadgestacionalEGFUR() == 0 ? 0 + "::SMALLINT," : this.getAtencion().getEdadgestacionalEGFUR() + "::SMALLINT,";
                String observaciones = this.getObservaciones() == null || this.getObservaciones().isEmpty() ? "null::TEXT," : "'" + this.getObservaciones().toUpperCase() + "'::TEXT,";
                String evolucion = this.getHosptilatazacion().getResumenEvolucion() == null || this.getHosptilatazacion().getResumenEvolucion().isEmpty() ? "null::TEXT," : "'" + this.getHosptilatazacion().getResumenEvolucion().toUpperCase() + "'::TEXT,";
                String medicoelab = this.getPersonalHospitalario().getNoTarjeta() == 0 ? "null::INTEGER," : this.getPersonalHospitalario().getNoTarjeta() + "::INTEGER,";
                String medicosuper = this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() == 0 ? "null::INTEGER," : this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER,";
                String fechaelab = this.getAtencion().getFechaElaboracion() == null ? "null::DATE);" : "'" + this.getAtencion().getFechaElaboracion() + "'::DATE);";
                sQuery = "SELECT * FROM insertactualizahistoriaclinicaperinatal(" + this.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," + 
                        this.getEpisodioMedico().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + fechanacpac + peso + lnacimiento + nmama + apmama + ammama + fnamama +
                        edoCivil + educacion + ocupacion + padecimientoact + gestas + partos + abortos + cesareas + fup + fur + egfur + observaciones+
                        evolucion + medicoelab + medicosuper + fechaelab;                
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
        public void consultaAnveso001()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getPacientenNeonato().getFolioPaciente() == 0 || this.getPacientenNeonato().getClaveEpisodio() == 0 )
                throw new Exception("BUSCADATOSANVERSO001:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultanverso1historiaperinatal(" + this.getPacientenNeonato().getFolioPaciente() + "::BIGINT," + this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT);";               
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setPadecimientoAct((String)vRowTemp.get(0).toString());
                    this.getAtencion().setEdadgestacionalEGFUR(((Double)vRowTemp.get(1)).intValue());
                    this.setObservaciones((String)vRowTemp.get(2).toString());
                    this.getHosptilatazacion().setResumenEvolucion((String)vRowTemp.get(3).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(4).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres((String)vRowTemp.get(5).toString() + " " + (String)vRowTemp.get(6).toString() + " " + (String)vRowTemp.get(7).toString());
                    this.getAtencion().setFechaElaboracion((Date)vRowTemp.get(8));
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(((Double)vRowTemp.get(9)).intValue());                    
                }
            }
        }
        public void consulatAnverso1Especifico()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.getPacientenNeonato().getFolioPaciente() == 0 || this.getPacientenNeonato().getClaveEpisodio() == 0 || this.getNumeroHistoriaClinica() == 0)
                throw new Exception("CONSULTANVERSO1ESPECIFICO:NOPODEMOSPROCESARLAINFROMACION");
            else{
                sQuery = "SELECT * FROM consultaespecificohistoriaclinicaperinatal(" + this.getPacientenNeonato().getFolioPaciente() +"::BIGINT," +
                        this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," +
                        this.getNumeroHistoriaClinica() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setPadecimientoAct((String)vRowTemp.get(0).toString());
                    this.getAtencion().setEdadgestacionalEGFUR(((Double)vRowTemp.get(1)).intValue());
                    this.setObservaciones((String)vRowTemp.get(2).toString());
                    this.getHosptilatazacion().setResumenEvolucion((String)vRowTemp.get(3).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(4).toString());
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres((String)vRowTemp.get(5).toString() + " " + (String)vRowTemp.get(6).toString() + " " + (String)vRowTemp.get(7).toString());
                    this.getAtencion().setFechaElaboracion((Date)vRowTemp.get(8));
                    this.getPacientenNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(((Double)vRowTemp.get(9)).intValue());
                    this.getPersonalHospitalario().setCedProf((String)vRowTemp.get(10).toString());
                    this.getPersonalHospitalario().setNombres((String)vRowTemp.get(11).toString());
                    this.getPersonalHospitalario().setApPaterno((String)vRowTemp.get(12).toString());
                    this.getPersonalHospitalario().setApMaterno((String)vRowTemp.get(13).toString());
                }
            }
        }
        public boolean insertaReverso001(ArrayList<HistoriaClinicaPerinatal> dxAgregados, int ntam)throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            ArrayList<String> rstTemporal;
            String sQuery = "";
            int nRet = -1;
            if(this.getPacientenNeonato().getFolioPaciente() == 0 || this.getPacientenNeonato().getClaveEpisodio() == 0)
                throw new Exception("HISTORIACLINICAPERINATAL.INSERTAREVERSO001.NOPODEMOSPROCESARLAINFORMACION");
            else{
                String inspeccion = this.getInspeccionGeneral() == null || this.getInspeccionGeneral().isEmpty() ? "null::TEXT," : "'" + this.getInspeccionGeneral().toUpperCase() + "'::TEXT,";
                String clave = this.getDiagnostico().getClave() == null || this.getDiagnostico().getClave().isEmpty() ? "null::CHARACTER(6)," : "'" + this.getDiagnostico().getClave().toUpperCase() + "'::CHARACTER(6),";
                String plan = this.getSignosVitalesNeoNatos().getPlan() == null || this.getSignosVitalesNeoNatos().getPlan().isEmpty() ? "null::TEXT," : "'" + this.getSignosVitalesNeoNatos().getPlan().toUpperCase() + "'::TEXT,";
                String pronostico = this.getAtencion().getPlanPronostico() == null || this.getAtencion().getPlanPronostico().isEmpty() ? "null::TEXT);" : "'" + this.getAtencion().getPlanPronostico().toUpperCase() + "'::TEXT);";
                sQuery = "SELECT * FROM insertactualizareversohperinatal(" + this.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," + "'" +this.sUsuarioFirmado + "'::CHARACTER VARYING,"  + inspeccion + clave + plan + pronostico;                
                rstQuery = new ArrayList<String>();
                rstQuery.add(this.armaQuerySignosVitalesNeonatos());
                rstQuery.add(sQuery);
                if(!dxAgregados.isEmpty()){
                    rstTemporal = queryDiagnosticos(this.getPacientenNeonato().getFolioPaciente(), this.getPacientenNeonato().getClaveEpisodio(), dxAgregados, ntam);
                    for (String rstTemporal1 : rstTemporal) {
                        rstQuery.add((String) rstTemporal1);
                    }
                }
                /*for(int i = 0; i < rstQuery.size(); i++)
                    System.out.println(rstQuery.get(i));*/
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(rstQuery);
                    oAD.desconectar();
                    bandera = nRet > 0;
                }
            }                      
            return bandera;
        }
        public String armaQuerySignosVitalesNeonatos(){
            String sQuery = "";
            String peso = this.getSignosVitalesNeoNatos().getPeso() == 0 ? 0 + "::NUMERIC(6,3)," : this.getSignosVitalesNeoNatos().getPeso() + "::NUMERIC(6,3),";
            String talla = this.getPacientenNeonato().getTalla() == 0 ? 0 + "::NUMERIC(4,1)," : this.getPacientenNeonato().getTalla() + "::NUMERIC(4,1),";
            String pc = this.getSignosVitalesNeoNatos().getPc() == null || this.getSignosVitalesNeoNatos().getPc().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getSignosVitalesNeoNatos().getPc() + "'::CHARACTER VARYING,";
            String fr = this.getSignosVitalesNeoNatos().getFR() == null || this.getSignosVitalesNeoNatos().getFR().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getSignosVitalesNeoNatos().getFR() + "'::CHARACTER VARYING,";
            String fc = this.getSignosVitalesNeoNatos().getFC() == null || this.getSignosVitalesNeoNatos().getFC().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getSignosVitalesNeoNatos().getFC() + "'::CHARACTER VARYING,";
            String temp = this.getSignosVitalesNeoNatos().getTemp() == null || this.getSignosVitalesNeoNatos().getTemp().isEmpty() ? "null::CHARACTER VARYING);" : "'" + this.getSignosVitalesNeoNatos().getTemp() + "'::CHARACTER VARYING);";
            sQuery = "SELECT * FROM insertactualizasignosneonato(" + this.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                    this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT,'"+ this.sUsuarioFirmado + "'::CHARACTER VARYING," + peso + talla + pc + fr + fc + temp;
            return sQuery;
        }
        public ArrayList<String> queryDiagnosticos(long fpaciente, long clavepisodio, ArrayList<HistoriaClinicaPerinatal> dx, int ntam){
            ArrayList<String> rstQuery = null;
            String sQuery = "";
            Date fecha = new Date();
            ArrayList<String> valoresDx = new ArrayList<String>();
            valoresDx.add("'T0600'::CHARACTER(5),");
            valoresDx.add("'T0601'::CHARACTER(5),");
            valoresDx.add("'T0602'::CHARACTER(5),");
            valoresDx.add("'T0603'::CHARACTER(5),");
            valoresDx.add("'T0604'::CHARACTER(5),");
            valoresDx.add("'T0605'::CHARACTER(5),");
            valoresDx.add("'T0606'::CHARACTER(5),");
            rstQuery = new ArrayList<String>();
            int indice = ntam;
            for (HistoriaClinicaPerinatal dx1 : dx) {
                sQuery = "SELECT * FROM insertadiagnosticostriage(" + fpaciente + "::BIGINT," + clavepisodio + "::BIGINT,";
                sQuery += valoresDx.get(indice);
                indice = indice + 1;
                sQuery += "'" + ((String) dx1.getDiagnostico().getClave()) + "'::CHARACTER(6),'" + fecha + "'::DATE,'C'::CHARACTER,'R'::CHARACTER);";
                rstQuery.add(sQuery);
            }
            return rstQuery;
        }
        public void consultaSignosvitalesNeonatoEsepecifico()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getPacientenNeonato().getFolioPaciente() == 0 || this.getPacientenNeonato().getClaveEpisodio() == 0 || this.getNumeroHistoriaClinica() == 0)
                throw new Exception("CONSULTASIGNOSVITALESESPECIFICOS:NOESPOSIBLEPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultasignovitalneonatoespecifico(" + this.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," +
                        this.getNumeroHistoriaClinica() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getSignosVitalesNeoNatos().setPeso(((Double)vRowTemp.get(0)).floatValue());
                    this.getPacientenNeonato().setTalla(((Double)vRowTemp.get(1)).floatValue());
                    this.getSignosVitalesNeoNatos().setPc((String)vRowTemp.get(2).toString());
                    this.getSignosVitalesNeoNatos().setFR((String)vRowTemp.get(3).toString());
                    this.getSignosVitalesNeoNatos().setFC((String)vRowTemp.get(4).toString());
                    this.getSignosVitalesNeoNatos().setTemp((String)vRowTemp.get(5).toString());    
                }
            }
        }
        public void consultaSignosvitalesNeonato()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getPacientenNeonato().getFolioPaciente() == 0 || this.getPacientenNeonato().getClaveEpisodio() == 0)
                throw new Exception("BUSCADATOSPACIENTE.CONSULTASIGNOVITALES:NOESPOSIBLEPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultadetallesignovitalneonato(" + this.getPacientenNeonato().getFolioPaciente()+ "::BIGINT," + this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);                    
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getSignosVitalesNeoNatos().setPeso(((Double)vRowTemp.get(0)).floatValue());
                    this.getPacientenNeonato().setTalla(((Double)vRowTemp.get(1)).floatValue());
                    this.getSignosVitalesNeoNatos().setPc((String)vRowTemp.get(2).toString());
                    this.getSignosVitalesNeoNatos().setFR((String)vRowTemp.get(3).toString());
                    this.getSignosVitalesNeoNatos().setFC((String)vRowTemp.get(4).toString());
                    this.getSignosVitalesNeoNatos().setTemp((String)vRowTemp.get(5).toString());                    
                }
            }
        }
        public void consultaReverso001Detalle()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getPacientenNeonato().getFolioPaciente() == 0 || this.getPacientenNeonato().getClaveEpisodio() == 0 || this.getNumeroHistoriaClinica() == 0 )
                throw new Exception("CONSULTAREVERSO001DETALLE:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultadetalleanversohistoriaclinicaperinatalespecifico("+ this.getPacientenNeonato().getFolioPaciente() +"::BIGINT," +
                        this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," + 
                        this.getNumeroHistoriaClinica() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);                    
                    oAD.desconectar();
                }if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setInspeccionGeneral((String)vRowTemp.get(0).toString());
                    this.getDiagnostico().setClave((String)vRowTemp.get(1).toString());
                    this.getDiagnostico().setDescripcionDiag((String)vRowTemp.get(2).toString());
                    this.getSignosVitalesNeoNatos().setPlan((String)vRowTemp.get(3).toString());
                    this.getAtencion().setPlanPronostico((String)vRowTemp.get(4).toString());
                }
            }
        }
        public void consultaAnverso001()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getPacientenNeonato().getFolioPaciente() == 0 || this.getPacientenNeonato().getClaveEpisodio() == 0)
                throw new Exception("BUSCADATOSPACIENTE.CONSULATANVERSO:NOESPOSIBLEPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultadetalleanversohistoriaclinicaperinatal(" + this.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +this.getPacientenNeonato().getClaveEpisodio() + "::BIGINT);";                
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setInspeccionGeneral((String)vRowTemp.get(0).toString());
                    this.getDiagnostico().setClave((String)vRowTemp.get(1).toString());
                    this.getDiagnostico().setDescripcionDiag((String)vRowTemp.get(2).toString());
                    this.getSignosVitalesNeoNatos().setPlan((String)vRowTemp.get(3).toString());
                    this.getAtencion().setPlanPronostico((String)vRowTemp.get(4).toString());
                }
            }
        }
        public HistoriaClinicaPerinatal[] buscaPacientesNeonatos()throws Exception{
            HistoriaClinicaPerinatal[] arrRet = null;
            HistoriaClinicaPerinatal oHistoriaClinica = null;
            ArrayList rst = null;
            ArrayList<HistoriaClinicaPerinatal> vObj = null;
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
                sQuery = "SELECT * FROM buscahistoriaclinicaspacienteneonato('" + nombre +"','" + appaterno + "','" +apmaterno + "'," + numexp +");";
            else
                sQuery = "SELECT * FROM buscahistoriaclinicaspacienteneonato('" + nombre +"','" + appaterno + "','" +apmaterno + "'," + Integer.parseInt(numexp) +");";            
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);                
                oAD.desconectar();
            }
            if(rst != null && rst.size() > 0){
                arrRet = new HistoriaClinicaPerinatal[rst.size()];
                for(i = 0; i < rst.size(); i++){
                    oHistoriaClinica = new HistoriaClinicaPerinatal();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setFechaNac((Date)vRowTemp.get(3));
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(4).toString());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(5).toString());
                    oHistoriaClinica.getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(6).toString());
                    oHistoriaClinica.getAtencion().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(7)).longValue());
                    oHistoriaClinica.getAtencion().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(8)).longValue());
                    oHistoriaClinica.getAtencion().getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(((Double)vRowTemp.get(9)).intValue());
                    oHistoriaClinica.getAtencion().getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(((Double)vRowTemp.get(10)).intValue());
                    oHistoriaClinica.setNumeroHistoriaClinca(((Double)vRowTemp.get(11)).intValue());
                    arrRet[i] = oHistoriaClinica;
                }
            }
            return arrRet;
        }
        public HistoriaClinicaPerinatal[] buscaHistorialHClinicaPerinatal(long folioPac) throws Exception{
        HistoriaClinicaPerinatal arrRet[]=null, oHCP=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("HistoriaClinicaPerinatal.buscaHistorialHClinicaPerinatal: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialHClinicaPerinatal("+folioPac+");"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new HistoriaClinicaPerinatal[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oHCP= new HistoriaClinicaPerinatal();
                    oHCP.setNumeroHistoriaClinca(((Double)vRowTemp.get(0)).intValue());
                    oHCP.getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                    oHCP.getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
                    oHCP.getAtencion().getProducto().setNumeroIngresoHospitalario(((Double)vRowTemp.get(3)).intValue());
                    oHCP.getAtencion().getProducto().setConsecutivoProducto(((Double)vRowTemp.get(4)).intValue());
                    oHCP.getAtencion().getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(((Double)vRowTemp.get(5)).intValue());
                    oHCP.getAtencion().getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(((Double)vRowTemp.get(6)).intValue());
                    oHCP.getAtencion().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(7)).longValue());
                    oHCP.getAtencion().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(8)).longValue());
                    oHCP.getAtencion().setFechaElaboracion((Date)vRowTemp.get(9));
                    oHCP.setValoracionNeurologica(new ValoracionNeurologica());
                    oHCP.getValoracionNeurologica().setFolioValoracion(((Double)vRowTemp.get(10)).longValue());
                    oHCP.setEgFur(((Double)vRowTemp.get(11)).intValue());
                    arrRet[i]=oHCP;
                } 
            } 
        }
        return arrRet; 
    }
    
        //************TERMINAN METODOS DE CONTROL DE DATOS*********************
	public int getEgFur() {
	return nEgFur;
	}

	public void setEgFur(int valor) {
	nEgFur=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public ValoracionNeurologica getValoracionNeurologica() {
	return oValoracionNeurologica;
	}

	public void setValoracionNeurologica(ValoracionNeurologica valor) {
	oValoracionNeurologica=valor;
	}

	public SignosVitalesNeoNatos getSignosVitalesNeoNatos() {
	return oSignosVitalesNeoNatos;
	}

	public void setSignosVitalesNeoNatos(SignosVitalesNeoNatos valor) {
	oSignosVitalesNeoNatos=valor;
	}
        public AtencionNeonatalTococirugia getAtencion(){
            return oAtencion;
        }
        public void setAtencion(AtencionNeonatalTococirugia oAtencion){
            this.oAtencion = oAtencion;
        }
        public PersonalHospitalario getPersonalHospitalario(){
            return oPersonal;
        }
        public void setPersonalHospitalrio(PersonalHospitalario oPersonal){
            this.oPersonal = oPersonal;
        }
        public Parametrizacion getNivelEducacion(){
            return oNivelEducacion;
        }
        public void setNivelEducacion(Parametrizacion oParametrizacion){
            this.oNivelEducacion = oParametrizacion;
        }
        public Parametrizacion getEstadoCivil(){
            return oEstadoCivil;
        }
        public void setEstadoCivil(Parametrizacion oEstadoCivil){
            this.oEstadoCivil = oEstadoCivil;
        }        
} 
