package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.Producto;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.PacienteNeonato;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class AtencionNeonatalTococirugia implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private float nEdadGestacionalCapu;
	private DiagnosticoCIE10 oDiagnosticos;
	private String sEvoIndicaciones;
	private String sEvolucion;
	private String sPlanPronostico;
	private String sReanimacionNeonatalExpFisica;
	private ValoracionEpidemiologico oValoracionEpidemiologico;
	private CalificacionSilvermann oCalificacionSilvermann;
	private CalificacionApgar oCalificacionApgar;
	private SignosVitalesNeoNatos oSignosVitalesNeoNatos;
	private Producto oProducto;
        private PacienteNeonato oPacienteNeonato;
        private Date dFechaElab;
        private int nedadgestacionalegfur;     
        private long nClaveAtNeonatalToco;    
	public AtencionNeonatalTococirugia(){
	HttpServletRequest req;        
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oProducto = new Producto();
                this.oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());        
                this.oPacienteNeonato = new PacienteNeonato();
                this.oPacienteNeonato.setProducto(new Producto());
                this.oPacienteNeonato.getProducto().setTerminacionEmbarazo(new TerminacionEmbarazo());
                this.oDiagnosticos = new DiagnosticoCIE10();
                this.oSignosVitalesNeoNatos = new SignosVitalesNeoNatos();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AtencionNeonatalTococirugia.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAtencionNeonatalTococirugia();"; 
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
	public AtencionNeonatalTococirugia[] buscarTodos() throws Exception{
	AtencionNeonatalTococirugia arrRet[]=null, oAtencionNeonatalTococirugia=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAtencionNeonatalTococirugia();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AtencionNeonatalTococirugia[rst.size()];
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
			throw new Exception("AtencionNeonatalTococirugia.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAtencionNeonatalTococirugia('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("AtencionNeonatalTococirugia.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAtencionNeonatalTococirugia('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("AtencionNeonatalTococirugia.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAtencionNeonatalTococirugia('"+sUsuarioFirmado+"');"; 
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
        //*******INCIAN LOS METODOS DE CONTROL DE DATOS*******
        public PersonalHospitalario informacionMedico()throws Exception{
            PersonalHospitalario oPersonal = new PersonalHospitalario();
            oPersonal.setUsuar(new Usuario());
            oPersonal.getUsuar().setIdUsuario(sUsuarioFirmado);
            if(oPersonal.buscaPersonalHospitalarioDatos())
                return oPersonal;
            else
                return null;
        }       
        public AtencionNeonatalTococirugia[] buscaDatosPacienteNproductos()throws Exception{
            AtencionNeonatalTococirugia arrRet[] = null;
            AtencionNeonatalTococirugia oAtencion = null;
            ArrayList rst = null;            
            String sQuery = "";
            String nombre = "";
            String apaterno = "";
            String amaterno = "";
            String numexp = "";
            int i = 0;            
            if(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getOpcionUrg() == 0){
                nombre = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getNombres();
                apaterno = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno();
                amaterno = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno();
                numexp = "null";
            }else{
                nombre = "";
                apaterno = "";
                amaterno = "";
                numexp = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getNumero() + "";
            }
            if(numexp.compareTo("null") == 0)
                sQuery = "SELECT * FROM buscadatospacientenproductos('" + nombre + "','" + apaterno +"','"+amaterno + "'," + numexp + ");";
            else
                sQuery = "SELECT * FROM buscadatospacientenproductos('" + nombre + "','" + apaterno +"','"+amaterno + "'," + Integer.parseInt(numexp) + ");";            
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() > 0){
                arrRet = new AtencionNeonatalTococirugia[rst.size()];
                for(i = 0 ; i < rst.size(); i++){
                    oAtencion = new AtencionNeonatalTococirugia();                                        
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(((Double)vRowTemp.get(3)).intValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(((Double)vRowTemp.get(4)).longValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(5));
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setNombres((String)vRowTemp.get(6).toString());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(7).toString());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(8).toString());
                    oAtencion.getProducto().setNumeroIngresoHospitalario(((Double)vRowTemp.get(9)).intValue());
                    oAtencion.getProducto().setConsecutivoProducto(((Double)vRowTemp.get(10)).intValue());
                    oAtencion.getProducto().setSexoProductoP((String)vRowTemp.get(11).toString());
                    arrRet[i] = oAtencion;
                }
            }
            return arrRet;
        }
                
        public void buscaDatosPacienteProductoEpecifico()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("BUSCADATOSPACIENTEATENCIONNEONATAL:NOESPOSIBLEPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadatospacienteantencionneonatal(" + this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," + 
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getNumero() + "::INTEGER," +
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," +
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT," +
                        this.getProducto().getNumeroIngresoHospitalario() + "::BIGINT," + this.getProducto().getConsecutivoProducto() + "::SMALLINT);";                                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setNombres((String)vRowTemp.get(0).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(1).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(2).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setLugarNacimiento((String)vRowTemp.get(4).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(5));
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setCalleNum((String)vRowTemp.get(6).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setColonia((String)vRowTemp.get(7).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setEdoCivilStr((String)vRowTemp.get(8).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setEscolaridad((String)vRowTemp.get(9).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setOcupacion((String)vRowTemp.get(10).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setGestaciones(((Double)vRowTemp.get(11)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setPartos(((Double)vRowTemp.get(12)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setAbortos(((Double)vRowTemp.get(13)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setCesareas(((Double)vRowTemp.get(14)).intValue());
                    this.getPacienteNeonato().setNombres((String)vRowTemp.get(15).toString());
                    this.getPacienteNeonato().getProducto().setSexoProductoP((String)vRowTemp.get(16).toString());
                    this.getPacienteNeonato().getProducto().setFechaNacimiento((Date)vRowTemp.get(17));
                    this.getPacienteNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(18)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setUltimaMenstruacion((Date)vRowTemp.get(19));
                    this.getProducto().setNumeroIngresoHospitalario(((Double)vRowTemp.get(20)).intValue());
                    this.getProducto().setConsecutivoProducto(((Double)vRowTemp.get(21)).intValue());
                    this.getPacienteNeonato().setFolioPaciente(((Double)vRowTemp.get(22)).longValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(23)).longValue());
                    this.getPacienteNeonato().setClaveEpisodio(((Double)vRowTemp.get(24)).longValue());
                    float peso = ((Double)vRowTemp.get(25)).floatValue()/1000;
                    this.getPacienteNeonato().setPeso(peso);
                    this.getPacienteNeonato().setApPaterno((String)vRowTemp.get(26).toString());
                    this.getPacienteNeonato().setApMaterno((String)vRowTemp.get(27).toString());
                }
                if(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno() != null || !this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno().isEmpty() &&
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno() != null || !this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno().isEmpty()
                        && this.getPacienteNeonato().getApPaterno() == null && this.getPacienteNeonato().getApMaterno() == null){
                    this.getPacienteNeonato().setApPaterno(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno());
                    this.getPacienteNeonato().setApMaterno(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno());
                }
            }
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
            }
            if(rst.isEmpty())
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
                arrRet = new Medico[vObj.size()];
                for(i = 0; i < vObj.size(); i++)
                    arrRet[i] = vObj.get(i);
            }
            return arrRet;
        }
        public boolean guardaAnverso(ArrayList<DiagnosticoCIE10> arrDiagnostico, int tam)throws Exception{            
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            ArrayList<String> rstTemporal;
            String sQuery = "";
            int nRet = -1;            
            if(this.getProducto().getNumeroIngresoHospitalario() == 0 || this.getProducto().getConsecutivoProducto() == 0 ||
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0)
                throw new Exception("ATENCIONNEONATALTOCOCIRUGIA.GUARDAANVERSO:NOPODEMOSPROCESARLAINFORMACIONFALTANDATOS");
            else{                
                String nombre = this.getPacienteNeonato().getNombres() == null||
                        this.getPacienteNeonato().getNombres().isEmpty() ? "null" + "::CHARACTER VARYING(50)," : "'" + this.getPacienteNeonato().getNombres().toUpperCase() + "'::CHARACTER VARYING(50),";
                String apaterno = this.getPacienteNeonato().getApPaterno() == null || this.getPacienteNeonato().getApPaterno().isEmpty() ? "null" + "::CHARECTER VARYING(50)," : "'" + this.getPacienteNeonato().getApPaterno().toUpperCase() + "'::CHARACTER VARYING(50),";
                String apmaterno = this.getPacienteNeonato().getApMaterno() == null || this.getPacienteNeonato().getApMaterno().isEmpty() ? "null" + "::CHARACTER VARYING(50)," : "'" +this.getPacienteNeonato().getApMaterno().toUpperCase() + "'::CHARACTER VARYING(50),";
                String sexo = this.getPacienteNeonato().getProducto().getSexoProductoP() == null || this.getPacienteNeonato().getProducto().getSexoProductoP().isEmpty() ? "null" : this.getPacienteNeonato().getProducto().getSexoProductoP();
                String sexoCaracter = "";
                if(sexo.compareTo("NO ESPECIFICADO") == 0)
                    sexoCaracter ="'N'::CHARACTER(1),";
                else                    
                    sexoCaracter = sexo.compareTo("MASCULINO") == 0 ? "'M'::CHARACTER(1)," :"'F'::CHARACTER(1),";
                String egfur = this.getEdadgestacionalEGFUR() == 0 ? 0 + "::SMALLINT," : this.getEdadgestacionalEGFUR() + "::SMALLINT,";
                String edadcapurro = this.getEdadGestacionalCapu() == 0 ? 0 + "::NUMERIC(4,2)," : this.getEdadGestacionalCapu() + "::NUMERIC(4,2),";
                String reanimacion = this.getReanimacionNeonatalExpFisica() == null || this.getReanimacionNeonatalExpFisica().isEmpty() ? "null" + "::TEXT," : "'" + this.getReanimacionNeonatalExpFisica().toUpperCase() + "'::TEXT,";
                String diagnostico = this.getDiagnosticos().getClave() == null || this.getDiagnosticos().getClave().isEmpty() ? "null" + "::CHARACTER(6)," : "'" + this.getDiagnosticos().getClave().toUpperCase() + "'::CHARACTER(6),";
                String plan = this.getPlanPronostico() == null || this.getPlanPronostico().isEmpty() ? "null" + "::TEXT," : "'" + this.getPlanPronostico().toUpperCase() + "'::TEXT,";
                String tarjeta = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() == 0 ? "null" + "::INTEGER," : this.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER,";
                String fechaelab = this.getFechaElaboracion() == null  ? "null" + "::TIMESTAMP WITHOUT TIME ZONE," : "'" + this.getFechaElaboracion() + "'::TIMESTAMP WITHOUT TIME ZONE,";
                String fechanac =  this.getPacienteNeonato().getProducto().getFechaNacimiento()==null ? "null" + "::DATE," : "'" + this.getPacienteNeonato().getProducto().getFechaNacimiento() + "'::DATE,";
                String peso = this.getPacienteNeonato().getPeso() == 0 ? 0 + "::NUMERIC(6,3)," : this.getPacienteNeonato().getPeso() + "::NUMERIC(6,3),";
                String talla = this.getPacienteNeonato().getTalla() == 0 ? 0 + "::NUMERIC(4,1)," : this.getPacienteNeonato().getTalla() + "::NUMERIC(4,1),";
                String pc = this.getSignosVitalesNeoNatos().getPc() == null || this.getSignosVitalesNeoNatos().getPc().isEmpty() ? "null" + "::CHARACTER VARYING," : "'" + this.getSignosVitalesNeoNatos().getPc() + "'::CHARACTER VARYING,";
                String pt = this.getSignosVitalesNeoNatos().getPt() == null || this.getSignosVitalesNeoNatos().getPt().isEmpty() ? "null" + "::CHARACTER VARYING," : "'" + this.getSignosVitalesNeoNatos().getPt() + "'::CHARACTER VARYING,";
                String pa = this.getSignosVitalesNeoNatos().getPa() == null || this.getSignosVitalesNeoNatos().getPa().isEmpty() ? "null" + "::CHARACTER VARYING," : "'" + this.getSignosVitalesNeoNatos().getPa() + "'::CHARACTER VARYING,";
                String pie = this.getSignosVitalesNeoNatos().getPie() == null || this.getSignosVitalesNeoNatos().getPie().isEmpty() ? "null" + "::CHARACTER VARYING," : "'" + this.getSignosVitalesNeoNatos().getPie() + "'::CHARACTER VARYING,";
                String pesoproducto = this.getPacienteNeonato().getPeso() == 0 ? 0 + "::SMALLINT);" : (this.getPacienteNeonato().getPeso()*1000) + "::SMALLINT);";
                sQuery = "SELECT * FROM insertactualizatencioneonatal(" + this.getProducto().getNumeroIngresoHospitalario() + "::BIGINT," +
                        this.getProducto().getConsecutivoProducto() + "::SMALLINT," +
                        this.getPacienteNeonato().getFolioPaciente() + "::BIGINT," + /*CHECAR*/
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING(10)," + nombre + apaterno + apmaterno + sexoCaracter + egfur +
                        edadcapurro + reanimacion + diagnostico + plan + tarjeta + fechaelab + fechanac + peso + talla + pc + pt + pa + pie + pesoproducto;                
                rstQuery = new ArrayList<String>();                
                rstQuery.add(sQuery);
                if(!arrDiagnostico.isEmpty() && this.getPacienteNeonato().getFolioPaciente() == 0 && this.getPacienteNeonato().getClaveEpisodio() == 0){
                    rstTemporal = queryDiagnosticos(arrDiagnostico, tam);
                    for (String rstTemporal1 : rstTemporal) {
                        rstQuery.add((String) rstTemporal1);
                    }
                }else{
                    if(!arrDiagnostico.isEmpty() && this.getPacienteNeonato().getFolioPaciente() != 0 && this.getPacienteNeonato().getClaveEpisodio() != 0){
                        SegundaMitadEmbarazo oConsulta = new SegundaMitadEmbarazo();
                        rstTemporal = oConsulta.armaQueryPartoGrama(this.getPacienteNeonato().getFolioPaciente(), this.getPacienteNeonato().getClaveEpisodio(), arrDiagnostico, tam);
                        for (String rstTemporal1 : rstTemporal) {
                            rstQuery.add((String) rstTemporal1);
                        }
                    }
                }
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(rstQuery);
                    oAD.desconectar();
                    bandera = nRet > 0;
                }
            }
            return bandera;
        }
        public ArrayList queryDiagnosticos(ArrayList<DiagnosticoCIE10> dx, int nTam)throws Exception{
            ArrayList rstQuery = null;
            String sQuery = "";
            if(dx == null)
                throw new Exception("ATENCIONNEONATAL.ARMAQUERYDIAGNOSTICO:NOPUDIMOSPROCESARLAINFORMACION");
            else{
                ArrayList<String> valoresDx = new ArrayList<String>();
                valoresDx.add("'T0600'::CHARACTER(5),");
                valoresDx.add("'T0601'::CHARACTER(5),");
                valoresDx.add("'T0602'::CHARACTER(5),");
                valoresDx.add("'T0603'::CHARACTER(5),");
                valoresDx.add("'T0604'::CHARACTER(5),");
                valoresDx.add("'T0605'::CHARACTER(5),");
                valoresDx.add("'T0606'::CHARACTER(5),");
                rstQuery = new ArrayList();
                int indice = nTam;
                for (DiagnosticoCIE10 dx1 : dx) {
                    sQuery = "SELECT * FROM insertadiagnosticosantencionneonatal(";
                    sQuery += valoresDx.get(indice);
                    indice = indice + 1;
                    sQuery += "'" + ((String) dx1.getClave()) + "'::CHARACTER(6),'C'::CHARACTER,'R'::CHARACTER);";
                    rstQuery.add(sQuery);
                }
            }
            return rstQuery;
        }
        public void consultaAnverso()throws Exception{
            ArrayList rst = null;
            String sQuery =  "";
            if(this.getProducto().getNumeroIngresoHospitalario() == 0 || this.getProducto().getConsecutivoProducto() == 0)
                throw new Exception("CONSULTAANVERSO.FALTANDATOS:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM consultanversoatencioneonatal("+ this.getProducto().getNumeroIngresoHospitalario() +"::BIGINT,"+ this.getProducto().getConsecutivoProducto() +"::SMALLINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);                    
                    this.setEdadGestacionalCapu(((Double)vRowTemp.get(0)).floatValue());
                    this.setReanimacionNeonatalExpFisica((String)vRowTemp.get(1).toString());
                    this.getDiagnosticos().setClave((String)vRowTemp.get(2).toString());
                    this.getDiagnosticos().setDescripcionDiag((String)vRowTemp.get(3).toString());
                    this.setPlanPronostico((String)vRowTemp.get(4).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(5).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres((String)vRowTemp.get(6).toString() + " "+ (String)vRowTemp.get(7).toString() + " " + (String)vRowTemp.get(8).toString());
                    this.setFechaElaboracion((Date)vRowTemp.get(9));
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(((Double)vRowTemp.get(10)).intValue());
                    this.getPacienteNeonato().setTalla(((Double)vRowTemp.get(11)).floatValue());
                    this.getSignosVitalesNeoNatos().setPc((String)vRowTemp.get(12).toString());
                    this.getSignosVitalesNeoNatos().setPt((String)vRowTemp.get(13).toString());
                    this.getSignosVitalesNeoNatos().setPa((String)vRowTemp.get(14).toString());
                    this.getSignosVitalesNeoNatos().setPie((String)vRowTemp.get(15).toString());
                }
            }
        }
        public boolean guardaReverso(String queryApgar, String querySilvermann, String queryValoracionEpidemio)throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            int nRet = -1;            
            if(this.getProducto().getNumeroIngresoHospitalario() == 0 || this.getProducto().getConsecutivoProducto() == 0)
                throw new Exception("GUARDAREVERSOATENCIONNEONATALTOCOCIRUGIA.FALTANDATOS:NOPODEMOSPORCESARLAINFORMACION");
            else{
                rstQuery = new ArrayList<String>();
                rstQuery.add(queryApgar);
                rstQuery.add(querySilvermann);
                rstQuery.add(queryValoracionEpidemio);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(rstQuery);
                    oAD.desconectar();
                    bandera = (nRet > 0);
                }
            }
            return bandera;
        }
        public AtencionNeonatalTococirugia[] buscaDatosPacienteAtencionNeonatal()throws Exception{
            AtencionNeonatalTococirugia arrRet[] = null;
            AtencionNeonatalTococirugia oAtencion = null;
            ArrayList rst = null;            
            String sQuery = "";
            String nombre = "";
            String apaterno = "";
            String amaterno = "";
            String numexp = "";
            int i = 0;
            if(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getOpcionUrg() == 0){
                nombre = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getNombres();
                apaterno = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno();
                amaterno = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno();
                numexp = "null";
            }else{
                nombre = "";
                apaterno = "";
                amaterno = "";
                numexp = this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getNumero() + "";
            }
            if(numexp.compareTo("null") == 0)
                sQuery = "SELECT * FROM  buscapacienteatencionneonataltc('"+ nombre + "','" + apaterno + "','" + amaterno + "'," + numexp + ");";
            else
                sQuery = "SELECT * FROM  buscapacienteatencionneonataltc('" + nombre + "','" + apaterno + "','" + amaterno + "'," + Integer.parseInt(numexp) + ");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() > 0){
                arrRet = new AtencionNeonatalTococirugia[rst.size()];
                for( i = 0 ; i < rst.size() ; i++){
                    oAtencion = new AtencionNeonatalTococirugia();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(((Double)vRowTemp.get(3)).intValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(((Double)vRowTemp.get(4)).longValue());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(5));
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setNombres((String)vRowTemp.get(6).toString());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(7).toString());
                    oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(8).toString());
                    oAtencion.getProducto().setNumeroIngresoHospitalario(((Double)vRowTemp.get(9)).intValue());
                    oAtencion.getProducto().setConsecutivoProducto(((Double)vRowTemp.get(10)).intValue());
                    oAtencion.getProducto().setSexoProductoP((String)vRowTemp.get(11).toString());
                    arrRet[i] = oAtencion;
                }
            }
            return arrRet;
        }
        public AtencionNeonatalTococirugia[] buscaHistorialHojaAtnNeonatal(long folioPac) throws Exception{
        AtencionNeonatalTococirugia arrRet[]=null, oAtnNeonatal=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("AtencionNeonatalTococirugia.buscaHistorialHojaAtnNeonatal: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialNotaAtencionNeonatal("+folioPac+");"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new AtencionNeonatalTococirugia[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oAtnNeonatal= new AtencionNeonatalTococirugia();
                    oAtnNeonatal.getPacienteNeonato().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oAtnNeonatal.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                    oAtnNeonatal.getProducto().setNumeroIngresoHospitalario(((Double)vRowTemp.get(2)).intValue());
                    oAtnNeonatal.getProducto().setConsecutivoProducto(((Double)vRowTemp.get(3)).intValue());
                    oAtnNeonatal.setClaveAtNeonatalToco(((Double)vRowTemp.get(4)).longValue());
                    oAtnNeonatal.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(5)).longValue());
                    oAtnNeonatal.getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(((Double)vRowTemp.get(6)).intValue());
                    oAtnNeonatal.getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(((Double)vRowTemp.get(7)).longValue());
                    oAtnNeonatal.getProducto().setFechaNacimiento((Date)vRowTemp.get(8));
                    oAtnNeonatal.getProducto().setSexoProductoP((String)vRowTemp.get(9).toString());
                    arrRet[i]=oAtnNeonatal;
                } 
            } 
        }
        return arrRet; 
    }
    
        public void buscaDatosPacienteProductoEpecificoEXP()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("buscaDatosPacienteProductoEpecificoEXP:NOESPOSIBLEPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallespacienteExpantencionneonatal(" + 
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," + 
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," +
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT," +
                        this.getProducto().getNumeroIngresoHospitalario() + "::BIGINT," +
                        this.getProducto().getConsecutivoProducto() + "::SMALLINT);";                                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setNombres((String)vRowTemp.get(0).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(1).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(2).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setLugarNacimiento((String)vRowTemp.get(4).toString());//de la madre
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(5));//de la madre
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setCalleNum((String)vRowTemp.get(6).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setColonia((String)vRowTemp.get(7).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setEdoCivilStr((String)vRowTemp.get(8).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setEscolaridad((String)vRowTemp.get(9).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setOcupacion((String)vRowTemp.get(10).toString());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setGestaciones(((Double)vRowTemp.get(11)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setPartos(((Double)vRowTemp.get(12)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setAbortos(((Double)vRowTemp.get(13)).intValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setCesareas(((Double)vRowTemp.get(14)).intValue());
                    this.getPacienteNeonato().setNombres((String)vRowTemp.get(15).toString());//producto
                    this.getPacienteNeonato().getProducto().setSexoProductoP((String)vRowTemp.get(16).toString());//producto
                    this.getPacienteNeonato().getProducto().setFechaNacimiento((Date)vRowTemp.get(17));//producto
                    
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setUltimaMenstruacion((Date)vRowTemp.get(18));
                    this.getProducto().setNumeroIngresoHospitalario(((Double)vRowTemp.get(19)).intValue());
                    this.getProducto().setConsecutivoProducto(((Double)vRowTemp.get(20)).intValue());
                    this.getPacienteNeonato().setFolioPaciente(((Double)vRowTemp.get(21)).longValue());
                    this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(22)).longValue());
                    this.getPacienteNeonato().setClaveEpisodio(((Double)vRowTemp.get(23)).longValue());
                    float peso = ((Double)vRowTemp.get(24)).floatValue()/1000;
                    this.getPacienteNeonato().setPeso(peso);
                    //nombre linea 25
                    this.getPacienteNeonato().setApPaterno((String)vRowTemp.get(26).toString());
                    this.getPacienteNeonato().setApMaterno((String)vRowTemp.get(27).toString());
                    this.getPacienteNeonato().getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(28)).intValue());//del producto
                }
                if(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno() != null || !this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno().isEmpty() &&
                        this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno() != null || !this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno().isEmpty()
                        && this.getPacienteNeonato().getApPaterno() == null && this.getPacienteNeonato().getApMaterno() == null){
                    this.getPacienteNeonato().setApPaterno(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno());
                    this.getPacienteNeonato().setApMaterno(this.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno());
                }
            }
        }
    
        //*******TERMINAN LOS METODOS DE CONTROL DE DATOS*******        
	public float getEdadGestacionalCapu() {
	return nEdadGestacionalCapu;
	}

	public void setEdadGestacionalCapu(float valor) {
	nEdadGestacionalCapu=valor;
	}

	public DiagnosticoCIE10 getDiagnosticos() {
	return oDiagnosticos;
	}

	public void setDiagnosticos(DiagnosticoCIE10 valor) {
	oDiagnosticos=valor;
	}

	public String getEvoIndicaciones() {
	return sEvoIndicaciones;
	}

	public void setEvoIndicaciones(String valor) {
	sEvoIndicaciones=valor;
	}

	public String getEvolucion() {
	return sEvolucion;
	}

	public void setEvolucion(String valor) {
	sEvolucion=valor;
	}

	public String getPlanPronostico() {
	return sPlanPronostico;
	}

	public void setPlanPronostico(String valor) {
	sPlanPronostico=valor;
	}

	public String getReanimacionNeonatalExpFisica() {
	return sReanimacionNeonatalExpFisica;
	}

	public void setReanimacionNeonatalExpFisica(String valor) {
	sReanimacionNeonatalExpFisica=valor;
	}

	public ValoracionEpidemiologico getValoracionEpidemiologico() {
	return oValoracionEpidemiologico;
	}

	public void setValoracionEpidemiologico(ValoracionEpidemiologico valor) {
	oValoracionEpidemiologico=valor;
	}

	public CalificacionSilvermann getCalificacionSilvermann() {
	return oCalificacionSilvermann;
	}

	public void setCalificacionSilvermann(CalificacionSilvermann valor) {
	oCalificacionSilvermann=valor;
	}

	public CalificacionApgar getCalificacionApgar() {
	return oCalificacionApgar;
	}

	public void setCalificacionApgar(CalificacionApgar valor) {
	oCalificacionApgar=valor;
	}

	public SignosVitalesNeoNatos getSignosVitalesNeoNatos() {
	return oSignosVitalesNeoNatos;
	}

	public void setSignosVitalesNeoNatos(SignosVitalesNeoNatos valor) {
	oSignosVitalesNeoNatos=valor;
	}

	public Producto getProducto() {
	return oProducto;
	}

	public void setProducto(Producto valor) {
	oProducto=valor;
	}
        public PacienteNeonato getPacienteNeonato(){
            return oPacienteNeonato;
        }
        public void setPacienteNeonato(PacienteNeonato oPacienteNeonato){
            this.oPacienteNeonato = oPacienteNeonato;
        }
        public Date getFechaElaboracion(){
            return dFechaElab;
        }
        public void setFechaElaboracion(Date dFechaElab){
            this.dFechaElab = dFechaElab;
        }
        public int getEdadgestacionalEGFUR(){
            return nedadgestacionalegfur;
        }
        public void setEdadgestacionalEGFUR(int nedadgestacionalegfur){
            this.nedadgestacionalegfur = nedadgestacionalegfur;
        }

    public long getClaveAtNeonatalToco() {
        return nClaveAtNeonatalToco;
    }

    public void setClaveAtNeonatalToco(long nClaveAtNeonatalToco) {
        this.nClaveAtNeonatalToco = nClaveAtNeonatalToco;
    }
} 