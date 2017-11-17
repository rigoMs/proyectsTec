package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Parametrizacion;
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
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.enfermeria.HojaEnfermeriaQuirofano;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class NotaPostoperatoria extends NotaMedicaHRRB implements Serializable{
    private AccesoDatos oAD;
    private String sUsuarioFirmado;
    private boolean bAmeritaUCI;
    private boolean bEnvioPiezasBiopcia;
    private DiagnosticoCIE10 oDiagnosticoPostoperatorio;
    private Parametrizacion oTipoCirugia;
    private ArrayList<Parametrizacion> arrTipoCirugia;
    private String sHTransOpe;
    private String sMotivo;
    private String sOtrosHayasgos;
    private String sProblemasPostOpe;
    private String sSaturacionArterial;
    private ProcedimientosRealizados oProcedimientosRealizados;
    private PersonalHospitalario oPersonal;
    private NotaPreoperatoria oNotaPreoperatoria;
    private Medico oMedicoLlevoPieza;
    private String sEstudiosTransOperatorios;
    private HojaEnfermeriaQuirofano oHojaQuirofano;
        
	public NotaPostoperatoria(){            
	HttpServletRequest req;
            oPersonal = new PersonalHospitalario();
            oPersonal.setUsuar(new Usuario());
            this.oProcedimientosRealizados = new ProcedimientosRealizados();
            this.oProcedimientosRealizados.inicializar();            
            this.oNotaPreoperatoria = new NotaPreoperatoria();
            this.oDiagnosticoPostoperatorio = new DiagnosticoCIE10();
            this.oTipoCirugia = new Parametrizacion();            
            this.arrTipoCirugia = new ArrayList<Parametrizacion>();
            this.arrTipoCirugia.add(new Parametrizacion());
            this.arrTipoCirugia.add(new Parametrizacion());
            this.arrTipoCirugia.add(new Parametrizacion());
            this.arrTipoCirugia.add(new Parametrizacion());
            this.oMedicoLlevoPieza = new Medico();
            this.oHojaQuirofano = new HojaEnfermeriaQuirofano();
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
			throw new Exception("NotaPostoperatoria.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveNotaPostoperatoria();"; 
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
	public NotaPostoperatoria[] buscarTodos() throws Exception{
	NotaPostoperatoria arrRet[]=null, oNotaPostoperatoria=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosNotaPostoperatoria();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new NotaPostoperatoria[rst.size()];
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
			throw new Exception("NotaPostoperatoria.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaNotaPostoperatoria('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaPostoperatoria.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaNotaPostoperatoria('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaPostoperatoria.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaNotaPostoperatoria('"+sUsuarioFirmado+"');"; 
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
//*******************INICIAN METODOS DE CONTROL DE DATOS*******************
        public void buscaPersonal()throws Exception{
            this.oPersonal.buscaPersonalHospitalarioDatos();
        }
        
        public ArrayList<NotaPostoperatoria> buscaNotasAgregadas()throws Exception{
            ArrayList<NotaPostoperatoria> arrRet = null;
            NotaPostoperatoria oNota = null;
            ArrayList rst = null;
            int i = 0;
            String sQuery = "";
            if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getCIE9().getClave() == null || this.getCIE9().getClave().isEmpty())
                throw new Exception("NOTAPOSTOPERATORIA.BUSCANOTASAGREGADAS:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscanotaspostoperatorias(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                            this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" +
                        this.getCIE9().getClave() + "'::CHARACTER(6));";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    arrRet = new ArrayList<NotaPostoperatoria>();
                    for(i = 0; i < rst.size(); i++){
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oNota = new NotaPostoperatoria();
                        oNota.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                        oNota.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                        oNota.setConsecutivo(((Double)vRowTemp.get(2)).intValue());
                        oNota.getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(3));
                        oNota.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                        oNota.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(5).toString());
                        oNota.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(6).toString());
                        oNota.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(7).toString());
                        oNota.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(8).toString());
                        oNota.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(9).toString());
                        oNota.setMaxConsecutivo(((Double)vRowTemp.get(10)).intValue());
                        arrRet.add(oNota);                        
                    }
                }
            }
            return arrRet;
        }
        
        public void buscaDatosCabecera()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("BUSCADATOSCABECERA:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadatoscabeceranotapostoperatoria(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getConsecutivo() + "::SMALLINT,'" +
                        this.getProcedimientosRealizados().getCIE9().getClave() +"'::CHARACTER(6));";                                
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);                    
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(2).toString());
                    this.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(3).toString());
                    this.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(4).toString());
                    this.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(5).toString());
                    this.getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(6));
                    this.getEpiMed().getPaciente().setFechaNacTexto(format.format((Date)vRowTemp.get(6)));
                    this.getEpiMed().getPaciente().calculaEdad();
                    this.getEpiMed().getPaciente().setSexoP(vRowTemp.get(7).toString().compareTo("M") == 0 ? "MASCULINO" : "FEMENINO");
                    this.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(8)).intValue());
                    this.getProcedimientosRealizados().setFechaEntrada((Date)vRowTemp.get(9));
                    this.getProcedimientosRealizados().setFechaSalida((Date)vRowTemp.get(10));
                    this.getNotaPreoperatoria().getDiagnosticoPreoperatorio().setClave((String)vRowTemp.get(11).toString());
                    this.getNotaPreoperatoria().getDiagnosticoPreoperatorio().setDescripcionDiag((String)vRowTemp.get(12).toString());
                    this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(13).toString());
                    this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(14).toString());
                    this.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(15).toString());
                    this.getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(16).toString());                    
                    this.getProcedimientosRealizados().getCirujano().setNoTarjeta(((Double)vRowTemp.get(17)).intValue());
                    this.getProcedimientosRealizados().getCirujano().setNombres((String)vRowTemp.get(18).toString());
                    this.getProcedimientosRealizados().getCirujano().setApPaterno((String)vRowTemp.get(19).toString());
                    this.getProcedimientosRealizados().getCirujano().setApMaterno((String)vRowTemp.get(20).toString());                    
                    this.getProcedimientosRealizados().getCirujano().setCedProf((String)vRowTemp.get(21).toString());                    
                    this.getProcedimientosRealizados().getCirculante().setNoTarjeta(((Double)vRowTemp.get(22)).intValue());
                    this.getProcedimientosRealizados().getCirculante().setNombres((String)vRowTemp.get(23).toString());
                    this.getProcedimientosRealizados().getCirculante().setApPaterno((String)vRowTemp.get(24).toString());
                    this.getProcedimientosRealizados().getCirculante().setApMaterno((String)vRowTemp.get(25).toString());
                    this.getProcedimientosRealizados().getCAyudanteUno().setNoTarjeta(((Double)vRowTemp.get(27)).intValue());
                    this.getProcedimientosRealizados().getCAyudanteUno().setNombres((String)vRowTemp.get(28).toString());
                    this.getProcedimientosRealizados().getCAyudanteUno().setApPaterno((String)vRowTemp.get(29).toString());
                    this.getProcedimientosRealizados().getCAyudanteUno().setApMaterno((String)vRowTemp.get(30).toString());
                    this.getProcedimientosRealizados().getInstrumentista().setNoTarjeta(((Double)vRowTemp.get(32)).intValue());
                    this.getProcedimientosRealizados().getInstrumentista().setNombres((String)vRowTemp.get(33).toString());
                    this.getProcedimientosRealizados().getInstrumentista().setApPaterno((String)vRowTemp.get(34).toString());
                    this.getProcedimientosRealizados().getInstrumentista().setApMaterno((String)vRowTemp.get(35).toString());
                    this.getProcedimientosRealizados().getAnestesio().setNoTarjeta(((Double)vRowTemp.get(37)).intValue());
                    this.getProcedimientosRealizados().getAnestesio().setNombres((String)vRowTemp.get(38).toString());
                    this.getProcedimientosRealizados().getAnestesio().setApPaterno((String)vRowTemp.get(39).toString());
                    this.getProcedimientosRealizados().getAnestesio().setApMaterno((String)vRowTemp.get(40).toString());
                    this.getProcedimientosRealizados().getAnestesio().setCedProf((String)vRowTemp.get(41).toString());
                    this.getProcedimientosRealizados().setResultado((String)vRowTemp.get(42).toString());
                }
            }
        }
        public boolean insertAnversoNotaPostoperatoria()throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = new ArrayList<String>();
            int nRet = -1;            
            rstQuery.add(this.armaqueryNotaPostoperatoria());
            if(rstQuery.isEmpty())
                bandera = false;
            else{
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(rstQuery);
                    oAD.desconectar();
                    bandera = nRet > 0;
                }
            }
            return bandera;
        }
        
        public String armaqueryNotaPostoperatoria(){
            String sQuery = "";
            if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().trim() == null || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().trim().isEmpty())
                return sQuery;
            else{
                String dxpostoperatorio = this.getDiagnosticoPostoperatorio().getClave() == null || this.getDiagnosticoPostoperatorio().getClave().isEmpty() ?  "null::CHARACTER(6)," : "'" + this.getDiagnosticoPostoperatorio().getClave().trim() + "'::CHARACTER(6),";
                String cirujiarealizada = this.getProcedimientosRealizados().getCIE9().getClave() == null || this.getProcedimientosRealizados().getCIE9().getClave().isEmpty() ?  "null::CHARACTER(6)," : "'" + this.getProcedimientosRealizados().getCIE9().getClave().trim() + "'::CHARACTER(6),";
                String ntarmed = this.getProcedimientosRealizados().getCAyudanteDos().getNoTarjeta() == 0 ?  "null::INTEGER," : this.getProcedimientosRealizados().getCAyudanteDos().getNoTarjeta() + "::INTEGER,";
                String desccirugia = this.getProcedimientosRealizados().getResultado() == null || this.getProcedimientosRealizados().getResultado().isEmpty() ?  "null::TEXT);" : "'" + this.getProcedimientosRealizados().getResultado().toUpperCase() + "'::TEXT);";
                sQuery = "SELECT * FROM insertAnversoNotaPostoperatoria(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                                                                            this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,"+
                                                                            this.getConsecutivo() + "::SMALLINT," +
                                                                            this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().trim() + "::CHARACTER(6),'"+
                                                                            this.sUsuarioFirmado + "'::CHARACTER VARYING," + dxpostoperatorio + cirujiarealizada +
                                                                            ntarmed + desccirugia;

            }            
            return sQuery;
        }        
        
        public void buscaDetalleAnverso()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getConsecutivo() == 0 || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave() == null || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().isEmpty())
                throw new Exception("BUSCADETALLEANVERSO:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery ="SELECT * FROM buscadetalleanversonotapostoperatoria(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," + this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +  this.getConsecutivo() + "::SMALLINT,'" + this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().trim() + "'::CHARACTER(6));";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);                    
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.getDiagnosticoPostoperatorio().setClave((String)vRowTemp.get(0).toString());
                    this.getDiagnosticoPostoperatorio().setDescripcionDiag((String)vRowTemp.get(1).toString());
                    this.getProcedimientosRealizados().getCAyudanteDos().setNoTarjeta(((Double)vRowTemp.get(2)).intValue());
                    this.getProcedimientosRealizados().getCAyudanteDos().setNombres((String)vRowTemp.get(3).toString());
                    this.getProcedimientosRealizados().getCAyudanteDos().setApPaterno((String)vRowTemp.get(4).toString());
                    this.getProcedimientosRealizados().getCAyudanteDos().setApMaterno((String)vRowTemp.get(5).toString());
                }
            }
        }
        
        public boolean inserReversoNotaPostoperatoria()throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = new ArrayList<String>();
            int nRet = -1;            
            rstQuery.add(this.armaQueryReversoPostoperatoria());
            if(rstQuery.isEmpty())
                bandera = false;
            else{
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(rstQuery);
                    oAD.desconectar();
                    bandera = nRet > 0;
                }
            }
            return bandera;
        }
        
        public String armaQueryReversoPostoperatoria()throws Exception{
            String sQuery = "";            
            if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getConsecutivo() == 0 || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave() == null || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().isEmpty())
                return sQuery;
            else{                
                String htransope = this.getHTransOpe() == null || this.getHTransOpe().isEmpty() ? "null::TEXT," : "'" + this.getHTransOpe().toUpperCase() + "'::TEXT,";
                String incidentes = this.getIncidentesAccidentes() == null || this.getIncidentesAccidentes().isEmpty() ? "null::TEXT," : "'" + this.getIncidentesAccidentes().toUpperCase() + "'::TEXT,";                
                String estdtransope = this.getEstudiosTransoperatorios() == null || this.getEstudiosTransoperatorios().isEmpty() ? "null::TEXT," : "'" + this.getEstudiosTransoperatorios().toUpperCase() + "'::TEXT,";
                String ta = this.getEpiMed().getSignosVitales().getTA() == null || this.getEpiMed().getSignosVitales().getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getTA() + "'::CHARACTER VARYING,";
                String fc = this.getEpiMed().getSignosVitales().getFC() == null || this.getEpiMed().getSignosVitales().getFC().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getFC() + "'::CHARACTER VARYING,";
                String fr = this.getEpiMed().getSignosVitales().getFR() == null || this.getEpiMed().getSignosVitales().getFR().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getFR() + "'::CHARACTER VARYING,";
                String temp = this.getEpiMed().getSignosVitales().getTemp() == null || this.getEpiMed().getSignosVitales().getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getEpiMed().getSignosVitales().getTemp() + "'::CHARACTER VARYING,";
                String saturacionarterial = this.getSaturacionArterial() == null || this.getSaturacionArterial().isEmpty() ? "null::CHARACTER VARYING," : "'" + this.getSaturacionArterial() + "'::CHARACTER VARYING,";
                String cuidadosintensivos = this.getAmeritaUCI() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
                String motivo = this.getMotivo() == null || this.getMotivo().isEmpty() ? "null::TEXT," : "'" + this.getMotivo().toUpperCase() + "'::TEXT,";
                String planmanejo = this.getPlanDeTratamiento() == null || this.getPlanDeTratamiento().isEmpty() ? "null::TEXT," : "'" + this.getPlanDeTratamiento().toUpperCase() + "'::TEXT,";
                String pronostico = this.getPronostico() == null || this.getPronostico().isEmpty() ? "null::TEXT," : "'" + this.getPronostico().toUpperCase() + "'::TEXT,";
                String probcomplicaciones = this.getProblemasPostOpe() == null || this.getProblemasPostOpe().isEmpty() ? "null::TEXT," : "'" + this.getProblemasPostOpe().toUpperCase() + "'::TEXT,";
                String enviopiezas = this.getEnvioPiezasBiopcia() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
                String nmedllevopieza = this.getMedicoLlevoPieza().getNoTarjeta() == 0 ? "null::INTEGER," : this.getMedicoLlevoPieza().getNoTarjeta() + "::INTEGER,";
                String climpia = this.getArregloCirugias().get(0).getTipoParametro() == null || this.getArregloCirugias().get(0).getTipoParametro().isEmpty() ? "null::CHARACTER," : "'" + "TBJ" + this.getArregloCirugias().get(0).getTipoParametro() +  "'::CHARACTER(4),";
                String climpiacontaminada = this.getArregloCirugias().get(1).getTipoParametro() == null || this.getArregloCirugias().get(1).getTipoParametro().isEmpty() ? "null::CHARACTER," : "'" + "TBJ" + this.getArregloCirugias().get(1).getTipoParametro() + "'::CHARACTER(4),";
                String ccontaminada = this.getArregloCirugias().get(2).getTipoParametro() == null || this.getArregloCirugias().get(2).getTipoParametro().isEmpty() ? "null::CHARACTER," : "'" + "TBJ" + this.getArregloCirugias().get(2).getTipoParametro() + "'::CHARACTER(4),";
                String csucia = this.getArregloCirugias().get(3).getTipoParametro() == null || this.getArregloCirugias().get(3).getTipoParametro().isEmpty() ? "null::CHARACTER," : "'" + "TBJ" + this.getArregloCirugias().get(3).getTipoParametro() + "'::CHARACTER(4),";
                String otroshayasgos = this.getOtrosHayasgos() == null || this.getOtrosHayasgos().isEmpty() ? "null::TEXT," : "'" + this.getOtrosHayasgos().toUpperCase() + "'::TEXT,";
                String medrealcir = this.getProcedimientosRealizados().getCirujano().getNoTarjeta() == 0 ?  "null::INTEGER," : this.getProcedimientosRealizados().getCirujano().getNoTarjeta() + "::INTEGER,";
                String medrealnota = this.getPersonalHospitalario().getNoTarjeta() == 0 ? "null::INTEGER);" : this.getPersonalHospitalario().getNoTarjeta() + "::INTEGER);";
                sQuery = "SELECT * FROM insertareversonotapostoperatoria(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                                                                        this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                                                                        this.getConsecutivo() + "::SMALLINT,'" +
                                                                        this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().trim() + "'::CHARACTER(6),'" +
                                                                        this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                                                        htransope + incidentes + estdtransope + ta + fc + fr +temp + saturacionarterial +
                                                                        cuidadosintensivos + motivo + planmanejo + pronostico + probcomplicaciones + enviopiezas + nmedllevopieza +
                                                                        climpia + climpiacontaminada + ccontaminada + csucia + otroshayasgos + medrealcir + medrealnota;                
            }
            return sQuery;
        }
        
        public void buscaDetalleReverso()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getConsecutivo() ==0 || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave() == null || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().isEmpty())
                throw new Exception("BUSCADETALLEREVERSO:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallereversonotapostoperatoria(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getEpiMed().getPaciente().getClaveEpisodio() +"::BIGINT," +
                        this.getConsecutivo() + "::SMALLINT,'" + 
                        this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave() + "'::CHARACTER(6));";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);                    
                    oAD.desconectar();                    
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setHTransOpe((String)vRowTemp.get(0).toString());
                    this.getHojaQuirofano().setConteoGasas(((Double)vRowTemp.get(1)).intValue());                    
                    this.getHojaQuirofano().setCuentaGasas((vRowTemp.get(2).toString().compareTo("1") == 0));
                    this.getHojaQuirofano().setConteoCompresas(((Double)vRowTemp.get(3)).intValue());
                    this.getHojaQuirofano().setCuentaGasas((vRowTemp.get(4).toString().compareTo("1") == 0));
                    this.setIncidentesAccidentes((String)vRowTemp.get(5).toString());
                    this.getProcedimientosRealizados().setCantSangreQx(((Double)vRowTemp.get(6)).intValue());
                    this.setEstudiosTransoperatorios((String)vRowTemp.get(7).toString());
                    this.getEpiMed().getSignosVitales().setTA((String)vRowTemp.get(8).toString());
                    this.getEpiMed().getSignosVitales().setFC((String)vRowTemp.get(9).toString());
                    this.getEpiMed().getSignosVitales().setFR((String)vRowTemp.get(10).toString());
                    this.getEpiMed().getSignosVitales().setTemp((String)vRowTemp.get(11).toString());
                    this.setSaturacionArterial((String)vRowTemp.get(12).toString());
                    this.setAmeritaUCI((vRowTemp.get(13).toString().compareTo("1") == 0));
                    this.setMotivo((String)vRowTemp.get(14).toString());
                    this.setPlanDeTratamiento((String)vRowTemp.get(15).toString());
                    this.setPronostico((String)vRowTemp.get(16).toString());
                    this.setProblemasPostOpe((String)vRowTemp.get(17).toString());
                    this.setEnvioPiezasBiopcia((vRowTemp.get(18).toString().compareTo("1") == 0));
                    this.getMedicoLlevoPieza().setNoTarjeta(((Double)vRowTemp.get(19)).intValue());
                    this.getMedicoLlevoPieza().setCedProf((String)vRowTemp.get(20).toString());
                    this.getMedicoLlevoPieza().setNombres((String)vRowTemp.get(21).toString());
                    this.getMedicoLlevoPieza().setApPaterno((String)vRowTemp.get(22).toString());
                    this.getMedicoLlevoPieza().setApMaterno((String)vRowTemp.get(23).toString());
                    this.getArregloCirugias().get(0).setTipoParametro(!vRowTemp.get(24).toString().isEmpty() ? "1" : "");
                    this.getArregloCirugias().get(1).setTipoParametro(!vRowTemp.get(25).toString().isEmpty() ? "2" : "");
                    this.getArregloCirugias().get(2).setTipoParametro(!vRowTemp.get(26).toString().isEmpty() ? "3" : "");
                    this.getArregloCirugias().get(3).setTipoParametro(!vRowTemp.get(27).toString().isEmpty() ? "4" : "");
                    this.setOtrosHayasgos((String)vRowTemp.get(28).toString());
                    this.getProcedimientosRealizados().getCirujano().setNoTarjeta(((Double)vRowTemp.get(29)).intValue());
                    this.getProcedimientosRealizados().getCirujano().setCedProf((String)vRowTemp.get(30).toString());
                    this.getProcedimientosRealizados().getCirujano().setNombres((String)vRowTemp.get(31).toString());
                    this.getProcedimientosRealizados().getCirujano().setApPaterno((String)vRowTemp.get(32).toString());
                    this.getProcedimientosRealizados().getCirujano().setApMaterno((String)vRowTemp.get(33).toString());
                    this.getPersonalHospitalario().setNoTarjeta(((Double)vRowTemp.get(34)).intValue());
                    this.getPersonalHospitalario().setCedProf((String)vRowTemp.get(35).toString());
                    this.getPersonalHospitalario().setNombres((String)vRowTemp.get(36).toString());
                    this.getPersonalHospitalario().setApPaterno((String)vRowTemp.get(37).toString());
                    this.getPersonalHospitalario().setApMaterno((String)vRowTemp.get(38).toString());
                }
            }
        }
        public String armaqueryModificaAnversoNotaPostoperatoria(){
            String sQuery = "";
            if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getConsecutivo() == 0 || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().trim() == null  || this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave().trim().isEmpty())
                return sQuery;
            else{
                String ntarmed = this.getProcedimientosRealizados().getCAyudanteDos().getNoTarjeta() == 0 ?  "null::INTEGER," : this.getProcedimientosRealizados().getCAyudanteDos().getNoTarjeta() + "::INTEGER,";
                String desccirugia = this.getProcedimientosRealizados().getResultado() == null || this.getProcedimientosRealizados().getResultado().isEmpty() ?  "null::TEXT);" : "'" + this.getProcedimientosRealizados().getResultado().toUpperCase() + "'::TEXT);";
                sQuery = "SELECT * FROM modificanversonotapostoperatoria(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                                                                            this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                                                                            this.getConsecutivo() + "::SMALLINT,'" +
                                                                            this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave() + "'::CHARACTER(6),'" +
                                                                            this.sUsuarioFirmado + "'::CHARACTER VARYING," + ntarmed + desccirugia;
            }
            return sQuery;
        }
        
        public boolean modificaAnverdoNotaPostoperatoria()throws Exception{
            boolean bandera = false;            
            ArrayList<String> rstQuery = new ArrayList<String>();
            int nRet = -1;
            rstQuery.add(this.armaqueryModificaAnversoNotaPostoperatoria());
            if(rstQuery.isEmpty())
                bandera = false;
            else{
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRet = oAD.ejecutarConsultaComando(rstQuery);
                    oAD.desconectar();
                    bandera = nRet > 0;
                }
            }
            return bandera;
        }
        
    
    public NotaPostoperatoria[] buscaHistorialNotaPostoperatoria(long folioPac) throws Exception{
        NotaPostoperatoria arrRet[]=null, oNP=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("NotaPostoperatoria.buscaHistorialNotaPostoperatoria: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialnotaspostoperatorias("+folioPac+");"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new NotaPostoperatoria[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNP= new NotaPostoperatoria();
                    oNP.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oNP.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oNP.setConsecutivo((((Double)vRowTemp.get(2)).intValue()));
                    oNP.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(3));
                    oNP.getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(4));
                    oNP.setFechaRegistro((Date)vRowTemp.get(5));
                    arrRet[i]=oNP;
                } 
            } 
        }
        return arrRet; 
    }
    
        public void buscaDatosCabeceraCPEXP()throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0)
            throw new Exception("buscaDatosCabeceraCPEXP:NOPODEMOSPROCESARLAINFORMACION");
        else{
            sQuery = "SELECT * FROM buscadatoscabeceranotapostoperatoriaEXP(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                    this.getConsecutivo() + "::SMALLINT,'" +
                    this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave() +"'::CHARACTER(6));";                                
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);                    
                oAD.desconectar();
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(2).toString());
                this.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(3).toString());
                this.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(4).toString());
                this.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(5).toString());
                this.getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(6));
                this.getEpiMed().getPaciente().setFechaNacTexto(format.format((Date)vRowTemp.get(6)));
                this.getEpiMed().getPaciente().calculaEdad();
                this.getEpiMed().getPaciente().setSexoP(vRowTemp.get(7).toString().compareTo("M") == 0 ? "MASCULINO" : "FEMENINO");
                this.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(8)).intValue());
                this.getProcedimientosRealizados().setFechaEntrada((Date)vRowTemp.get(9));
                this.getProcedimientosRealizados().setFechaSalida((Date)vRowTemp.get(10));
                this.getNotaPreoperatoria().getDiagnosticoPreoperatorio().setClave((String)vRowTemp.get(11).toString());
                this.getNotaPreoperatoria().getDiagnosticoPreoperatorio().setDescripcionDiag((String)vRowTemp.get(12).toString());
                this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(13).toString());
                this.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(14).toString());
                this.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(15).toString());
                this.getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(16).toString());                    
                this.getProcedimientosRealizados().getCirujano().setNoTarjeta(((Double)vRowTemp.get(17)).intValue());
                this.getProcedimientosRealizados().getCirujano().setNombres((String)vRowTemp.get(18).toString());
                this.getProcedimientosRealizados().getCirujano().setApPaterno((String)vRowTemp.get(19).toString());
                this.getProcedimientosRealizados().getCirujano().setApMaterno((String)vRowTemp.get(20).toString());                    
                this.getProcedimientosRealizados().getCirujano().setCedProf((String)vRowTemp.get(21).toString());                    
                this.getProcedimientosRealizados().getCirculante().setNoTarjeta(((Double)vRowTemp.get(22)).intValue());
                this.getProcedimientosRealizados().getCirculante().setNombres((String)vRowTemp.get(23).toString());
                this.getProcedimientosRealizados().getCirculante().setApPaterno((String)vRowTemp.get(24).toString());
                this.getProcedimientosRealizados().getCirculante().setApMaterno((String)vRowTemp.get(25).toString());
                this.getProcedimientosRealizados().getCAyudanteUno().setNoTarjeta(((Double)vRowTemp.get(27)).intValue());
                this.getProcedimientosRealizados().getCAyudanteUno().setNombres((String)vRowTemp.get(28).toString());
                this.getProcedimientosRealizados().getCAyudanteUno().setApPaterno((String)vRowTemp.get(29).toString());
                this.getProcedimientosRealizados().getCAyudanteUno().setApMaterno((String)vRowTemp.get(30).toString());
                this.getProcedimientosRealizados().getInstrumentista().setNoTarjeta(((Double)vRowTemp.get(32)).intValue());
                this.getProcedimientosRealizados().getInstrumentista().setNombres((String)vRowTemp.get(33).toString());
                this.getProcedimientosRealizados().getInstrumentista().setApPaterno((String)vRowTemp.get(34).toString());
                this.getProcedimientosRealizados().getInstrumentista().setApMaterno((String)vRowTemp.get(35).toString());
                this.getProcedimientosRealizados().getAnestesio().setNoTarjeta(((Double)vRowTemp.get(37)).intValue());
                this.getProcedimientosRealizados().getAnestesio().setNombres((String)vRowTemp.get(38).toString());
                this.getProcedimientosRealizados().getAnestesio().setApPaterno((String)vRowTemp.get(39).toString());
                this.getProcedimientosRealizados().getAnestesio().setApMaterno((String)vRowTemp.get(40).toString());
                this.getProcedimientosRealizados().getAnestesio().setCedProf((String)vRowTemp.get(41).toString());
                this.getProcedimientosRealizados().setResultado((String)vRowTemp.get(42).toString());
            }
        }
    }
    
//*******************TERMINAN METODOS DE CONTROL DE DATOS*******************        
	public boolean getAmeritaUCI() {
	return bAmeritaUCI;
	}

	public void setAmeritaUCI(boolean valor) {
	bAmeritaUCI=valor;
	}

	public boolean getEnvioPiezasBiopcia() {
	return bEnvioPiezasBiopcia;
	}

	public void setEnvioPiezasBiopcia(boolean valor) {
	bEnvioPiezasBiopcia=valor;
	}

	public DiagnosticoCIE10 getDiagnosticoPostoperatorio() {
	return oDiagnosticoPostoperatorio;
	}

	public void setDiagnosticoPostoperatorio(DiagnosticoCIE10 valor) {
	oDiagnosticoPostoperatorio=valor;
	}

	public Parametrizacion getTipoCirugia() {
	return oTipoCirugia;
	}

	public void setTipoCirugia(Parametrizacion valor) {
	oTipoCirugia=valor;
	}

	public String getHTransOpe() {
	return sHTransOpe;
	}

	public void setHTransOpe(String valor) {            
	sHTransOpe=valor;       
	}

	public String getMotivo() {
	return sMotivo;
	}

	public void setMotivo(String valor) {
	sMotivo=valor;
	}

	public String getOtrosHayasgos() {
	return sOtrosHayasgos;
	}

	public void setOtrosHayasgos(String valor) {
	sOtrosHayasgos=valor;
	}

	public String getProblemasPostOpe() {
	return sProblemasPostOpe;
	}

	public void setProblemasPostOpe(String valor) {
	sProblemasPostOpe=valor;
	}

	public String getSaturacionArterial() {
	return sSaturacionArterial;
	}

	public void setSaturacionArterial(String valor) {
	sSaturacionArterial=valor;
	}

	public ProcedimientosRealizados getProcedimientosRealizados() {
	return oProcedimientosRealizados;
	}

	public void setProcedimientosRealizados(ProcedimientosRealizados valor) {
	oProcedimientosRealizados=valor;
	}
        public NotaPreoperatoria getNotaPreoperatoria(){
            return oNotaPreoperatoria;
        }
        public void setNotaPreoperatoria(NotaPreoperatoria oNotaPreoperatoria){
            this.oNotaPreoperatoria = oNotaPreoperatoria;
        }
        public ArrayList<Parametrizacion> getArregloCirugias(){
            return arrTipoCirugia;
        }
        public void setArregloCirugias(ArrayList<Parametrizacion> arrTipoCirugia){
            this.arrTipoCirugia = arrTipoCirugia;            
        }
        public PersonalHospitalario getPersonalHospitalario(){
            return this.oPersonal;
        }
        public void setPersonalHospitalario(PersonalHospitalario oPersonal){
            this.oPersonal = oPersonal;
        }
        public Medico getMedicoLlevoPieza(){
            return oMedicoLlevoPieza;
        }
        public void setMedicoLlevoPieza(Medico oMedicoLlevoPieza){
            this.oMedicoLlevoPieza = oMedicoLlevoPieza;
        }
        public String getEstudiosTransoperatorios(){
            return sEstudiosTransOperatorios;
        }
        public void setEstudiosTransoperatorios(String sEstudiosTransOperatorios){
            this.sEstudiosTransOperatorios = sEstudiosTransOperatorios;
        }
        public HojaEnfermeriaQuirofano getHojaQuirofano(){
            return oHojaQuirofano;
        }
        public void setHojaQuirofano(HojaEnfermeriaQuirofano oHojaQuirofano){
            this.oHojaQuirofano = oHojaQuirofano;
        }
        public String getValorCuidadosIntensivos(){
            return this.getAmeritaUCI() ? "SI ( X ) NO (  )" : "SI (  ) NO ( X )";
        }
        public String getValorEnvioBiopcia(){
            return this.getEnvioPiezasBiopcia() ? "SI ( X ) NO (  )" : "SI (  ) NO ( X )";
        }
        public String getValorCirugiaLimpia(){
            return this.arrTipoCirugia.get(0).getTipoParametro().compareTo("1") == 0 ? "LIMPIA ( X )" : "LIMPIA (   )";
        }
        public String getValorCirugiaLimpiaContaminada(){
            return this.arrTipoCirugia.get(1).getTipoParametro().compareTo("2") == 0 ? "LIMPIA CONTAMINADA ( X )" : "LIMPIA CONTAMINADA (   )"; 
        }
        public String getValorCirugiaContaminada(){
            return this.arrTipoCirugia.get(2).getTipoParametro().compareTo("3") == 0 ? "CONTAMINADA ( X )" : "CONTAMINADA (   )";
        }
        public String getValorCirugiaSucia(){
            return this.arrTipoCirugia.get(3).getTipoParametro().compareTo("4") == 0 ? "SUCIA( X )" : "SUCIA(   )";
        }
} 