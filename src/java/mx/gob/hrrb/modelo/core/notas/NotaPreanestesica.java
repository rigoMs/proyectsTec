package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.NotaMedicaHRRB;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
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

public  class NotaPreanestesica extends NotaMedicaHRRB implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private String sEvaluacionClinica;	
	private String sRiesgoAnestesico;
	private ProcedimientosRealizados oProcedimientosRealizados;
        private PersonalHospitalario oPersonal;

	public NotaPreanestesica(){                      
	HttpServletRequest req;
            oPersonal = new PersonalHospitalario(); 
            oPersonal.setUsuar(new Usuario());
            this.oProcedimientosRealizados = new ProcedimientosRealizados();  
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oPersonal.getUsuar().setIdUsuario(this.sUsuarioFirmado);
	}
        
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaPreanestesica.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveNotaPreanestesica();"; 
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
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaPreanestesica.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaNotaPreanestesica('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaPreanestesica.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaNotaPreanestesica('"+sUsuarioFirmado+"');"; 
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
        //*******************INICIAN METODOS DE CONTROL DE DATOS***************************
    public void buscaPersonal()throws Exception{
        this.oPersonal.buscaPersonalHospitalarioDatos();
    }
        
    public void buscaDatosCabecera()throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if(this.getEpiMed().getPaciente().getFolioPaciente() == 0)
            throw new Exception("BUSCADATOSCABECERA:NOPODEMOSPROCESARLAINFORMACION");
        else{
            sQuery = "SELECT * FROM buscadatoscabeceranotapreanestesica(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(0).toString());
                this.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(1).toString());
                this.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(2).toString());
                this.getEpiMed().getPaciente().setFechaNacTexto(format.format((Date)vRowTemp.get(3)));
                this.getEpiMed().getPaciente().calculaEdad();
                this.getEpiMed().getPaciente().setSexoP(vRowTemp.get(4).toString().compareTo("M") == 0 ? "MASCULINO" : "FEMENINO");
            }
        }
    }
    
    public String armaQueryNotaPreanestesica(){
        String sQuery = "";
        if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0)
            return sQuery;
        else{
            String fecha = this.getFechaRegistro() == null  ? 
                   "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + this.getFechaRegistro() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String tipoanestesia = 
                    (this.getProcedimientosRealizados().getAnestEspecifica().getClave() == null || 
                    this.getProcedimientosRealizados().getAnestEspecifica().getClave().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + this.getProcedimientosRealizados().getAnestEspecifica().getClave().trim() + "'::CHARACTER VARYING,";
            String temp = 
                    (this.getEpiMed().getSignosVitales().getTemp() == null || 
                    this.getEpiMed().getSignosVitales().getTemp().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getTemp() + "'::CHARACTER VARYING,";
            String pulso = this.getEpiMed().getSignosVitales().getPulso() == 0 ? 
                    "null::SMALLINT," : this.getEpiMed().getSignosVitales().getPulso() + "::SMALLINT,";
            String fr = 
                    (this.getEpiMed().getSignosVitales().getFR() == null || 
                    this.getEpiMed().getSignosVitales().getFR().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getFR() + "'::CHARACTER VARYING,";
            String ta = 
                    (this.getEpiMed().getSignosVitales().getTA() == null || 
                    this.getEpiMed().getSignosVitales().getTA().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getTA() + "'::CHARACTER VARYING,";
            String rinterrogatorio = 
                    (this.getResumenInter() == null || 
                    this.getResumenInter().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getResumenInter().toUpperCase() + "'::TEXT,";
            String exploracionfisica = 
                    (this.getExploracionFisica() == null || 
                    this.getExploracionFisica().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getExploracionFisica().toUpperCase() + "'::TEXT,";
            String resumenedomental = 
                    (this.getResumenEstadoMental() == null || 
                    this.getResumenEstadoMental().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getResumenEstadoMental().toUpperCase() + "'::TEXT,";
            String resultadoestudios = 
                    (this.getResultadosServAuxDiag() == null || 
                    this.getResultadosServAuxDiag().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getResultadosServAuxDiag().toUpperCase() + "'::TEXT,";
            String comentarios = 
                    (this.getComentarios() == null || 
                    this.getComentarios().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getComentarios().toUpperCase() + "'::TEXT,";
            String impresiondiagnostica = 
                    (this.getImpresionDiagnostica() == null || 
                    this.getImpresionDiagnostica().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getImpresionDiagnostica().toUpperCase() + "'::TEXT,";
            String plan = 
                    (this.getPlanDeTratamiento() == null || 
                    this.getPlanDeTratamiento().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getPlanDeTratamiento().toUpperCase() + "'::TEXT,";
            String evaluacionclinica = 
                    (this.getEvaluacionClinica() == null || 
                    this.getEvaluacionClinica().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getEvaluacionClinica().toUpperCase() + "'::TEXT,";
            String riesgoanestesico = 
                    (this.getRiesgoAnestesico() == null || 
                    this.getRiesgoAnestesico().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getRiesgoAnestesico().toUpperCase() + "'::TEXT,";
            String tarjetamed = this.getPersonal().getNoTarjeta() == 0 ? 
                    "null::INTEGER);" : this.getPersonal().getNoTarjeta() + "::INTEGER);";
            sQuery = "SELECT * FROM insertaNotaPreanestesica(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                    fecha + tipoanestesia + temp + pulso + fr + ta + rinterrogatorio + exploracionfisica + resumenedomental +
                    resultadoestudios + comentarios + impresiondiagnostica + plan + evaluacionclinica + riesgoanestesico + tarjetamed;
        }
        return sQuery;
    }
    
    public boolean inserta() throws Exception{
        boolean bandera = false;
        ArrayList<String> rstQuery = null;
        String sQuery = this.armaQueryNotaPreanestesica();
        int nRet = -1;
        if(sQuery == null)
            return bandera;
        else{
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
    public NotaPreanestesica[] buscarTodosNotasPorPaciente() throws Exception{
	NotaPreanestesica arrRet[] = null;
        NotaPreanestesica oNotaPreanestesica = null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0)   //completar llave
            throw new Exception("NotaPreanestesica.eliminar: error, faltan datos");
        else{
            sQuery = "SELECT * FROM  buscatodosnotapreanestesicaporpaciente(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT);"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);                
                oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new NotaPreanestesica[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    oNotaPreanestesica = new NotaPreanestesica();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNotaPreanestesica.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oNotaPreanestesica.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oNotaPreanestesica.setConsecutivo(((Double)vRowTemp.get(2)).intValue());
                    oNotaPreanestesica.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(3).toString());
                    oNotaPreanestesica.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(4).toString());
                    oNotaPreanestesica.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(5).toString());
                    oNotaPreanestesica.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(6)).intValue());                    
                    oNotaPreanestesica.getProcedimientosRealizados().getAnestEspecifica().setDescripcion((String)vRowTemp.get(7).toString());
                    if(i == 0){
                        this.getEpiMed().getPaciente().setNombres(oNotaPreanestesica.getEpiMed().getPaciente().getNombreCompleto());
                        this.getEpiMed().getPaciente().getExpediente().setNumero(oNotaPreanestesica.getEpiMed().getPaciente().getExpediente().getNumero());
                    }
                    arrRet[i] = oNotaPreanestesica;
                } 
            } 
        }	
        return arrRet; 
    }
    public void buscaDatosNotaEspecifica()throws Exception{
        ArrayList rst = null;
        String sQuery= "";
        if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getConsecutivo() == 0)
            throw new Exception("BUSCADATOSNOTAESPECIFICA:NOPODEMOSPROCESARLAINFORMACION");
        else{
            sQuery = "SELECT * FROM buscanotapreanestesicaespecifica(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + this.getConsecutivo() + "::SMALLINT);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setFechaRegistro((Date)vRowTemp.get(3));
                this.getProcedimientosRealizados().getAnestEspecifica().setClave((String)vRowTemp.get(4).toString());
                this.getProcedimientosRealizados().getAnestEspecifica().setDescripcion((String)vRowTemp.get(5).toString());
                this.getEpiMed().getSignosVitales().setTemp((String)vRowTemp.get(6).toString());
                this.getEpiMed().getSignosVitales().setPulso(((Double)vRowTemp.get(7)).intValue());
                this.getEpiMed().getSignosVitales().setFR((String)vRowTemp.get(8).toString());
                this.getEpiMed().getSignosVitales().setTA((String)vRowTemp.get(9).toString());
                this.setResumenInter((String)vRowTemp.get(10).toString());
                this.setExploracionFisica((String)vRowTemp.get(11).toString());
                this.setResumenEstadoMental((String)vRowTemp.get(12).toString());
                this.setResultadosServAuxDiag((String)vRowTemp.get(13).toString());
                this.setComentarios((String)vRowTemp.get(14).toString());
                this.setImpresionDiagnostica((String)vRowTemp.get(15).toString());
                this.setPlanDeTratamiento((String)vRowTemp.get(16).toString());
                this.setEvaluacionClinica((String)vRowTemp.get(17).toString());
                this.setRiesgoAnestesico((String)vRowTemp.get(18).toString());
                this.oPersonal.setNombres((String)vRowTemp.get(19).toString());
                this.oPersonal.setApPaterno((String)vRowTemp.get(20).toString());
                this.oPersonal.setApMaterno((String)vRowTemp.get(21).toString());
                this.oPersonal.setCedProf((String)vRowTemp.get(22).toString());
            }
        }
    }
    public String armaQueryNotaModificacion(){
        String sQuery = "";
        if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getConsecutivo() == 0)
            return sQuery;
        else{
            String fecha = this.getFechaRegistro() == null ? 
                    "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + this.getFechaRegistro() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String tipoanestesia = 
                    (this.getProcedimientosRealizados().getAnestEspecifica().getClave() == null || 
                    this.getProcedimientosRealizados().getAnestEspecifica().getClave().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + this.getProcedimientosRealizados().getAnestEspecifica().getClave() + "'::CHARACTER VARYING,";
            String temp = 
                    (this.getEpiMed().getSignosVitales().getTemp() == null || 
                    this.getEpiMed().getSignosVitales().getTemp().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getTemp() + "'::CHARACTER VARYING,";
            String pulso = this.getEpiMed().getSignosVitales().getPulso() == 0 ? "null::SMALLINT," : this.getEpiMed().getSignosVitales().getPulso() + "::SMALLINT,";
            String fr = 
                    (this.getEpiMed().getSignosVitales().getFR() == null || 
                    this.getEpiMed().getSignosVitales().getFR().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getFR() + "'::CHARACTER VARYING,";
            String ta = 
                    (this.getEpiMed().getSignosVitales().getTA() == null || 
                    this.getEpiMed().getSignosVitales().getTA().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getTA() + "'::CHARACTER VARYING,";
            String rinterrogatorio = 
                    (this.getResumenInter() == null || 
                    this.getResumenInter().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getResumenInter().toUpperCase() + "'::TEXT,";
            String exploracionfisica = 
                    (this.getExploracionFisica() == null || 
                    this.getExploracionFisica().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getExploracionFisica().toUpperCase() + "'::TEXT,";
            String resumenedomental = 
                    (this.getResumenEstadoMental() == null || 
                    this.getResumenEstadoMental().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getResumenEstadoMental().toUpperCase() + "'::TEXT,";
            String resultadoestudios = 
                    (this.getResultadosServAuxDiag() == null || 
                    this.getResultadosServAuxDiag().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getResultadosServAuxDiag().toUpperCase() + "'::TEXT,";
            String comentarios = 
                    (this.getComentarios() == null || 
                    this.getComentarios().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getComentarios().toUpperCase() + "'::TEXT,";
            String impresiondiagnostica = 
                    (this.getImpresionDiagnostica() == null || 
                    this.getImpresionDiagnostica().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getImpresionDiagnostica().toUpperCase() + "'::TEXT,";
            String plan = 
                    (this.getPlanDeTratamiento() == null || 
                    this.getPlanDeTratamiento().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getPlanDeTratamiento().toUpperCase() + "'::TEXT,";
            String evaluacionclinica = 
                    (this.getEvaluacionClinica() == null || 
                    this.getEvaluacionClinica().isEmpty()) ? 
                    "null::TEXT," : "'" + this.getEvaluacionClinica().toUpperCase() + "'::TEXT,";
            String riesgoanestesico = 
                    (this.getRiesgoAnestesico() == null || 
                    this.getRiesgoAnestesico().isEmpty()) ? 
                    "null::TEXT);" : "'" + this.getRiesgoAnestesico().toUpperCase() + "'::TEXT);";
             sQuery = "SELECT * FROM modificaNotaPreanestesica(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                    this.getConsecutivo()+ "::SMALLINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                    fecha + tipoanestesia + temp + pulso + fr + ta + rinterrogatorio + exploracionfisica + resumenedomental +
                    resultadoestudios + comentarios + impresiondiagnostica + plan + evaluacionclinica + riesgoanestesico;
        }        
        return sQuery;
    }
    public boolean modificaNota()throws Exception{
        boolean bandera = false;
        ArrayList<String> rstQuery = null;
        String sQuery = this.armaQueryNotaModificacion();
        int nRet = -1;
        if(sQuery == null)
            return bandera;
        else{
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
        
    public NotaPreanestesica[] buscaHistorialNotaPreanestesica(long folioPac) throws Exception{
        NotaPreanestesica arrRet[]=null, oNP=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("NotaPreanestesica.buscaHistorialNotaPreanestesica: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialnotapreanestesica("+folioPac+");"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new NotaPreanestesica[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNP= new NotaPreanestesica();
                    oNP.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oNP.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oNP.setConsecutivo(((Double)vRowTemp.get(2)).intValue());                   
                    oNP.getProcedimientosRealizados().getAnestEspecifica().setDescripcion((String)vRowTemp.get(3).toString());
                    oNP.setFechaRegistro((Date)vRowTemp.get(4));
                    arrRet[i]=oNP;
                } 
            } 
        }
        return arrRet; 
    }
    
//*******************TERMINAN METODOS DE CONTROL DE DATOS***************************
	public String getEvaluacionClinica() {
	return sEvaluacionClinica;
	}

	public void setEvaluacionClinica(String valor) {
	sEvaluacionClinica=valor;
	}
        public String getRiesgoAnestesico() {
	return sRiesgoAnestesico;
	}

	public void setRiesgoAnestesico(String valor) {
	sRiesgoAnestesico=valor;
	}

	public ProcedimientosRealizados getProcedimientosRealizados() {
	return oProcedimientosRealizados;
	}

	public void setProcedimientosRealizados(ProcedimientosRealizados valor) {
	oProcedimientosRealizados=valor;
	}
        public PersonalHospitalario getPersonal(){
            return oPersonal;
        }
        public void setPersonal(PersonalHospitalario oPersonal){
            this.oPersonal = oPersonal;
        }
} 
