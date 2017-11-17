package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.Parametrizacion;
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
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class SegundaMitadEmbarazo extends PrimeraMitadEmbarazo implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bLiquidoAmniotico;
	private boolean bMembranas;
	private Date dHora;
	private int nContracciones;
	private int nFCfetal;	
	private Parametrizacion oLongitud0;
        private Parametrizacion oLongitud1;
        private Parametrizacion oLongitud2;
        private Parametrizacion oLongitud3;
        private Parametrizacion oLongitud4;
        private Parametrizacion oCuelloPosterior;
        private Parametrizacion oCuelloCentral;
        private Parametrizacion oCuelloResistente;
        private Parametrizacion oCuelloBlando;
	private String sEdema;
	private String sIntencidad;
	private String sOtrasCondionesIngreso;
	private String sOtrosDatos;
	private String sRitmo;
	private String sTonoUterino;
        private PersonalHospitalario oPersonal;

	public SegundaMitadEmbarazo(){
            this.oPersonal = new PersonalHospitalario();
	HttpServletRequest req;                
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oPersonal.getUsuar().setIdUsuario(this.sUsuarioFirmado);
                oCuelloPosterior = new Parametrizacion();
                oCuelloCentral = new Parametrizacion();
                oCuelloResistente = new Parametrizacion();
                oCuelloBlando = new Parametrizacion();
                oLongitud0 = new Parametrizacion();
                oLongitud1 = new Parametrizacion();
                oLongitud2 = new Parametrizacion();
                oLongitud3 = new Parametrizacion();
                oLongitud4 = new Parametrizacion();
	}
        public void buscaPersonal()throws Exception{
            this.oPersonal.buscaPersonalHospitalarioDatos();
        }
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SegundaMitadEmbarazo.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSegundaMitadEmbarazo();"; 
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
	public SegundaMitadEmbarazo[] buscarTodos() throws Exception{
	SegundaMitadEmbarazo arrRet[]=null, oSegundaMitadEmbarazo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSegundaMitadEmbarazo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new SegundaMitadEmbarazo[rst.size()];
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
			throw new Exception("SegundaMitadEmbarazo.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSegundaMitadEmbarazo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("SegundaMitadEmbarazo.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSegundaMitadEmbarazo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("SegundaMitadEmbarazo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSegundaMitadEmbarazo('"+sUsuarioFirmado+"');"; 
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
        public String armaQueryPartoGrama(PartoGrama oPartoGrama){
            String sQuery = "";
            String fecha = oPartoGrama.getFechaHora() == null ? "null::TIMESTAMP WITHOUT TIME ZONE);" : "'" + oPartoGrama.getFechaHora() + "'::TIMESTAMP WITHOUT TIME ZONE);";
            String motivoConsulta = oPartoGrama.getMotivoConsulta().getMotivoAtencion() == null || oPartoGrama.getMotivoConsulta().getMotivoAtencion().isEmpty() ? "null::CHARACTER," : "'" + oPartoGrama.getMotivoConsulta().getMotivoAtencion().substring(0, 5).toUpperCase() +"'::CHARACTER(5),";
            sQuery = "SELECT * FROM insertaDatosPartoGrama(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT,"
                    + oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                    oPartoGrama.getConsecutivo() + "::SMALLINT," +
                    oPartoGrama.getNpartograma() + "::BIGINT," +
                    oPartoGrama.getMaxConsecutivo() + "::SMALLINT,'" +
                    this.sUsuarioFirmado + "'::CHARACTER VARYING," + motivoConsulta + fecha;
            return sQuery;
        }
        public String armaQuerySignosVitales(PartoGrama oPartoGrama){
            String sQuery = "";
            String pulso = oPartoGrama.getEpiMed().getSignosVitales().getPulso() == 0 ? 0 + "::SMALLINT," : oPartoGrama.getEpiMed().getSignosVitales().getPulso() + "::SMALLINT,";
            String temperatura = oPartoGrama.getEpiMed().getSignosVitales().getTemp() == null || oPartoGrama.getEpiMed().getSignosVitales().getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + oPartoGrama.getEpiMed().getSignosVitales().getTemp() + "'::CHARACTER VARYING,";
            String TA = oPartoGrama.getEpiMed().getSignosVitales().getTA() == null || oPartoGrama.getEpiMed().getSignosVitales().getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oPartoGrama.getEpiMed().getSignosVitales().getTA() + "'::CHARACTER VARYING,";
            String resp = oPartoGrama.getEpiMed().getSignosVitales().getFR() == null || oPartoGrama.getEpiMed().getSignosVitales().getFR().isEmpty() ? "null::CHARACTER VARYING,": "'" + oPartoGrama.getEpiMed().getSignosVitales().getFR() + "'::CHARACTER VARYING,";
            String consiente = oPartoGrama.getEpiMed().getSignosVitales().getConsiente() ? "'1'::CHARACTER(1));" : "'0'::CHARACTER(1));"; 
            sQuery = "SELECT * FROM insertaSignosVitalesPartoGrama(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + 
                    8 + "::SMALLINT,'" +
                    this.sUsuarioFirmado + "'::CHARACTER VARYING," + pulso + temperatura + TA + resp + consiente;
            return sQuery;
        }
        public String armaQueryPartoGrama(PartoGrama oPartoGrama, PrimeraMitadEmbarazo oPrimeraMitadEmbarazo){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String sQuery = "";
            String amenorrea = oPrimeraMitadEmbarazo.getSemanasAmerorrea() == 0 ? 0 + "::SMALLINT," : oPrimeraMitadEmbarazo.getSemanasAmerorrea() + "::SMALLINT,";
            String dolor = oPrimeraMitadEmbarazo.getDolorIntesidad() == 0 ? 0 + "::SMALLINT," : oPrimeraMitadEmbarazo.getDolorIntesidad() + "::SMALLINT,";            
            String sitio = oPrimeraMitadEmbarazo.getSitio().getTipoParametro() == null || oPrimeraMitadEmbarazo.getSitio().getTipoParametro().isEmpty() ? "null::CHARACTER," : "'" + oPrimeraMitadEmbarazo.getSitio().getTipoParametro().substring(0, 4).toUpperCase() + "'::CHARACTER(5),";
            String fecha = oPrimeraMitadEmbarazo.getFecha() == null ? "null::TIMESTAMP WITHOUT TIME ZONE,": "'" + format.format(oPrimeraMitadEmbarazo.getFecha()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String hemorragia = oPrimeraMitadEmbarazo.getHemorragia() ? "'1'::CHARACTER(1)," : "'0'::CHARACTER(1),";
            String observaciones = oPrimeraMitadEmbarazo.getObservaciones() == null || oPrimeraMitadEmbarazo.getObservaciones().isEmpty() ? "null::CHARACTER VARYING," : "'" + oPrimeraMitadEmbarazo.getObservaciones().toUpperCase() + "'::CHARACTER VARYING,";
            String sintomas = oPrimeraMitadEmbarazo.getOtroSintomas() == null || oPrimeraMitadEmbarazo.getOtroSintomas().isEmpty() ? "null::CHARACTER VARYING," : "'" + oPrimeraMitadEmbarazo.getOtroSintomas().toUpperCase() + "'::CHARACTER VARYING,";
            String utero = oPrimeraMitadEmbarazo.getUtero() == null || oPrimeraMitadEmbarazo.getUtero().isEmpty() ? "null::CHARACTER VARYING," : "'" + oPrimeraMitadEmbarazo.getUtero().toUpperCase() + "'::CHARACTER VARYING,";
            String cervix = oPrimeraMitadEmbarazo.getCervix() == null || oPrimeraMitadEmbarazo.getCervix().isEmpty() ? "null::CHARACTER VARYING," : "'" + oPrimeraMitadEmbarazo.getCervix().toUpperCase() + "'::CHARACTER VARYING,";
            String exploracion = oPrimeraMitadEmbarazo.getOtrosDatos() == null || oPrimeraMitadEmbarazo.getOtrosDatos().isEmpty() ? "null::CHARACTER VARYING," : "'" + oPrimeraMitadEmbarazo.getOtrosDatos().toUpperCase() + "'::CHARACTER VARYING,";
            String plan = oPrimeraMitadEmbarazo.getPlan() == null || oPrimeraMitadEmbarazo.getPlan().isEmpty() ? "null::CHARACTER VARYING," : "'" + oPrimeraMitadEmbarazo.getPlan().toUpperCase() + "'::CHARACTER VARYING,";
            String tarjeta = oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().getNoTarjeta() == 0 ? "null::INTEGER," : oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER,";            
            String diagnostico = oPrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().getClave() == null || oPrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().getClave().isEmpty() ? "null::CHARACTER(6));" : "'" + oPrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().getClave() + "'::CHARACTER(6));";
            sQuery = "SELECT * FROM insertaPrimeraMitadEmbarazo(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + amenorrea + dolor + sitio + fecha + hemorragia +
                    observaciones + sintomas + utero + cervix + exploracion + plan + tarjeta + diagnostico;
            return sQuery;
        }
        public String armaQueryPartoGrama(PartoGrama oPartoGrama, SegundaMitadEmbarazo oSegundaMitadEmbarazo){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String sQuery = "";
            String amenorrea = oSegundaMitadEmbarazo.getSemanasAmerorrea() == 0 ? 0 + "::SMALLINT," : oSegundaMitadEmbarazo.getSemanasAmerorrea() + "::SMALLINT,";
            String edema = oSegundaMitadEmbarazo.getEdema() == null || oSegundaMitadEmbarazo.getEdema().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSegundaMitadEmbarazo.getEdema().toUpperCase() + "'::CHARACTER VARYING,";
            String hemorragia = oSegundaMitadEmbarazo.getHemorragia() ? "'1'::CHARACTER(1)," : "'0'::CHARACTER(1),";
            String dolor = oSegundaMitadEmbarazo.getDolorIntesidad() == 0 ? 0 + "::SMALLINT," : oSegundaMitadEmbarazo.getDolorIntesidad() + "::SMALLINT,";
            String contracciones = oSegundaMitadEmbarazo.getContracciones() == 0 ? 0 + "::SMALLINT," : oSegundaMitadEmbarazo.getContracciones() + "::SMALLINT,";
            String tonoUterino = oSegundaMitadEmbarazo.getTonoUterino() == null || oSegundaMitadEmbarazo.getTonoUterino().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSegundaMitadEmbarazo.getTonoUterino().toUpperCase() + "'::CHARACTER VARYING,";
            String membranas = oSegundaMitadEmbarazo.getMembranas() ? "'1'::CHARACTER(1)," : "'0'::CHARACTER(1),";
            String fecha = oSegundaMitadEmbarazo.getFecha() == null ? "null::TIMESTAMP WITHOUT TIME ZONE,": "'" + format.format(oSegundaMitadEmbarazo.getFecha()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String liquidoAmnio = oSegundaMitadEmbarazo.getLiquidoAmniotico() ? "'1'::CHARACTER(1)," : "'0'::CHARACTER(1),";
            String fcf = oSegundaMitadEmbarazo.getFCfetal() == 0 ? 0 + "::SMALLINT," : oSegundaMitadEmbarazo.getFCfetal() + "::SMALLINT,";
            String intencidad = oSegundaMitadEmbarazo.getIntencidad() == null || oSegundaMitadEmbarazo.getIntencidad().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSegundaMitadEmbarazo.getIntencidad().toUpperCase() + "'::CHARACTER VARYING,";
            String ritmo = oSegundaMitadEmbarazo.getRitmo() == null || oSegundaMitadEmbarazo.getRitmo().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSegundaMitadEmbarazo.getRitmo().toUpperCase() + "'::CHARACTER VARYING,";
            String otrosDatos = oSegundaMitadEmbarazo.getOtrosDatos() == null || oSegundaMitadEmbarazo.getOtrosDatos().isEmpty() ? "null::TEXT," : "'" + oSegundaMitadEmbarazo.getOtrosDatos().toUpperCase() + "'::TEXT,";
            String posterior = oSegundaMitadEmbarazo.getCuelloPosterior().getClaveParametro() == null || oSegundaMitadEmbarazo.getCuelloPosterior().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getCuelloPosterior().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String central = oSegundaMitadEmbarazo.getCuelloCentral().getClaveParametro() == null || oSegundaMitadEmbarazo.getCuelloCentral().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getCuelloCentral().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String resistente = oSegundaMitadEmbarazo.getCuelloResistente().getClaveParametro() == null || oSegundaMitadEmbarazo.getCuelloResistente().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getCuelloResistente().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String blando = oSegundaMitadEmbarazo.getCuelloBlando().getClaveParametro() == null || oSegundaMitadEmbarazo.getCuelloBlando().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getCuelloBlando().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String tresCms = oSegundaMitadEmbarazo.getLongitud0().getClaveParametro() == null || oSegundaMitadEmbarazo.getLongitud0().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getLongitud0().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String dosCms = oSegundaMitadEmbarazo.getLongitud1().getClaveParametro() == null || oSegundaMitadEmbarazo.getLongitud1().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getLongitud1().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String cm = oSegundaMitadEmbarazo.getLongitud2().getClaveParametro() == null || oSegundaMitadEmbarazo.getLongitud2().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getLongitud2().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String cm05 = oSegundaMitadEmbarazo.getLongitud3().getClaveParametro() == null || oSegundaMitadEmbarazo.getLongitud3().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getLongitud3().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String desgarro = oSegundaMitadEmbarazo.getLongitud4().getClaveParametro()== null || oSegundaMitadEmbarazo.getLongitud4().getClaveParametro().isEmpty() ? "null::CHARACTER(5)," : "'" + oSegundaMitadEmbarazo.getLongitud4().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
            String observaciones = oSegundaMitadEmbarazo.getObservaciones() == null || oSegundaMitadEmbarazo.getObservaciones().isEmpty() ? "null::TEXT," : "'" + oSegundaMitadEmbarazo.getObservaciones().toUpperCase() + "'::TEXT,";
            String plan = oSegundaMitadEmbarazo.getPlan() == null || oSegundaMitadEmbarazo.getPlan().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSegundaMitadEmbarazo.getPlan().toUpperCase() + "'::CHARACTER VARYING,";
            String otrasCondiciones = oSegundaMitadEmbarazo.getOtrasCondionesIngreso() == null  || oSegundaMitadEmbarazo.getOtrasCondionesIngreso().isEmpty()? "null::TEXT," : "'" + oSegundaMitadEmbarazo.getOtrasCondionesIngreso().toUpperCase() + "'::TEXT,";
            String tarjeta = oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().getNoTarjeta() == 0 ? "null::INTEGER," : oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER,";
            String ingreso = oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().getNoTarjeta() == 0 ? "null::INTEGER," : oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().getNoTarjeta() + "::INTEGER,";
            String diagnostico = oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().getClave() == null || oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().getClave().isEmpty() ? "null::CHARACTER(6));" : "'" + oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().getClave() + "'::CHARACTER(6));";
            sQuery = "SELECT * FROM insertaSegundaMitadEmbarazo(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" +
                    this.sUsuarioFirmado + "'::CHARACTER VARYING," + amenorrea + edema + hemorragia + dolor +
                    contracciones + tonoUterino + membranas + fecha + liquidoAmnio + fcf + intencidad + ritmo + otrosDatos +
                    posterior +  central + resistente + blando + tresCms + dosCms + cm + cm05 + desgarro + observaciones + plan + otrasCondiciones +
                    tarjeta + ingreso + diagnostico;
            return sQuery;
        }
        public ArrayList armaQueryPartoGrama(long fpaciente, long clavepisodio, ArrayList<DiagnosticoCIE10> dx, int nTam)throws Exception{
            ArrayList rstQuery = null;
            String sQuery = "";
            Date fecha = new Date();
            DateFormat fechaDiag = new SimpleDateFormat("dd/MM/yyyy");
            String fechadx = fechaDiag.format(fecha);            
            if(dx == null)
                throw new Exception("PARTOGRAMA.INSERTADIAGNOSTICO:ERROR, FALTANDATOS");
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
                    sQuery = "SELECT * FROM insertaDiagnosticosTriage(" + fpaciente + "::BIGINT," + clavepisodio + "::BIGINT,";
                    sQuery += valoresDx.get(indice);
                    indice = indice + 1;
                    sQuery += "'" + ((String) dx1.getClave()) + "'::CHARACTER(6),'" + fechadx + "'::DATE,'C'::CHARACTER,'R'::CHARACTER);";
                    rstQuery.add(sQuery);
                } //SELECT * FROM insertaDiagnosticosTriage(" + fpaciente + "::BIGINT," + clavepisodio + "::BIGINT, ''::CHARACTER(6),'" + fechadx + "'::DATE,'C'::CHARACTER,'R'::CHARACTER);"
            }
            return rstQuery;
        }
        
        public int insertaAnverso1PartoGrama(PartoGrama oPartoGrama, 
                                            PrimeraMitadEmbarazo oPrimeraMitadEmbarazo, 
                                            SegundaMitadEmbarazo oSegundaMitadEmbarazo,                                            
                                            ArrayList<DiagnosticoCIE10> dx,
                                            ArrayList<DiagnosticoCIE10> dx1,
                                            int nTam)throws Exception{
            ArrayList<String> rstQuery = null;
            ArrayList<String> rstTemporal0 = null;
            ArrayList<String> rstTemporal1 = null;
            int nRet = -1;
            if (oPartoGrama == null 
                    || oPrimeraMitadEmbarazo == null 
                    || oSegundaMitadEmbarazo == null)
                throw new Exception("PARTOGRAMA.INSERTADATOS:ERROR, FALTAN DATOS");
            else{
                rstQuery = new ArrayList<String>();
                rstQuery.add(armaQueryPartoGrama(oPartoGrama));
                rstQuery.add(armaQueryPartoGrama(oPartoGrama, oPrimeraMitadEmbarazo));
                rstQuery.add(armaQueryPartoGrama(oPartoGrama, oSegundaMitadEmbarazo));
                rstQuery.add(armaQuerySignosVitales(oPartoGrama));
                if(!dx.isEmpty()){
                    rstTemporal0 = armaQueryPartoGrama(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), dx, nTam);
                    for (String rstTemporal01 : rstTemporal0) {
                        rstQuery.add((String) rstTemporal01);
                    }
                }
                if(!dx1.isEmpty()){
                    rstTemporal1 = armaQueryPartoGrama(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), dx1, nTam);
                    for (String rstTemporal11 : rstTemporal1) {
                        rstQuery.add((String) rstTemporal11);
                    }
                }
                if(rstQuery.size() > 0){
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){
                        nRet = oAD.ejecutarConsultaComando(rstQuery);
                        oAD.desconectar();
                    }
                }
            }
            return nRet;
        }
        
        public SegundaMitadEmbarazo cargaDetallePacienteSegundaMitadEmbarazo()throws Exception{
            SegundaMitadEmbarazo oSegundaMitadEmbarazo = null;
            ArrayList rst = null;
            String sQuery = "";
            int i = 0;
            if(this.getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getPartoGrama().getConsecutivo() == 0 || this.getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallepacientepartograma(" + this.getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getPartoGrama().getConsecutivo() + "::SMALLINT," + this.getPartoGrama().getNpartograma() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return null;
                else{
                    oSegundaMitadEmbarazo = new SegundaMitadEmbarazo();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oSegundaMitadEmbarazo.setSemanasAmerorrea(((Double)vRowTemp.get(22)).intValue());
                    oSegundaMitadEmbarazo.setEdema((String)vRowTemp.get(23).toString());
                    boolean hemorragia = vRowTemp.get(24).toString().compareTo("1") == 0;
                    oSegundaMitadEmbarazo.setHemorragia(hemorragia);
                    oSegundaMitadEmbarazo.setDolorIntesidad(((Double)vRowTemp.get(25)).intValue());
                    oSegundaMitadEmbarazo.setContracciones(((Double)vRowTemp.get(26)).intValue());
                    oSegundaMitadEmbarazo.setTonoUterino((String)vRowTemp.get(27).toString());
                    boolean membranas = vRowTemp.get(28).toString().compareTo("1") == 0;
                    oSegundaMitadEmbarazo.setMembranas(membranas);
                    oSegundaMitadEmbarazo.setFecha((Date)vRowTemp.get(29));
                    boolean liquidoAmnio = vRowTemp.get(30).toString().compareTo("1") == 0;
                    oSegundaMitadEmbarazo.setLiquidoAmniotico(liquidoAmnio);
                    oSegundaMitadEmbarazo.setFCfetal(((Double)vRowTemp.get(31)).intValue());
                    oSegundaMitadEmbarazo.setIntencidad((String)vRowTemp.get(32).toString());
                    oSegundaMitadEmbarazo.setRitmo((String)vRowTemp.get(33).toString());
                    oSegundaMitadEmbarazo.setOtrosDatos((String)vRowTemp.get(34).toString());
                    oSegundaMitadEmbarazo.getCuelloPosterior().setValor((String)vRowTemp.get(35).toString());
                    oSegundaMitadEmbarazo.getCuelloCentral().setValor((String)vRowTemp.get(36).toString());
                    oSegundaMitadEmbarazo.getCuelloResistente().setValor((String)vRowTemp.get(37).toString());
                    oSegundaMitadEmbarazo.getCuelloBlando().setValor((String)vRowTemp.get(38).toString());
                    oSegundaMitadEmbarazo.getLongitud0().setValor((String)vRowTemp.get(39).toString());
                    oSegundaMitadEmbarazo.getLongitud1().setValor((String)vRowTemp.get(40).toString());
                    oSegundaMitadEmbarazo.getLongitud2().setValor((String)vRowTemp.get(41).toString());
                    oSegundaMitadEmbarazo.getLongitud3().setValor((String)vRowTemp.get(42).toString());
                    oSegundaMitadEmbarazo.getLongitud4().setValor((String)vRowTemp.get(43).toString());
                    oSegundaMitadEmbarazo.setObservaciones((String)vRowTemp.get(44).toString());
                    oSegundaMitadEmbarazo.setPlan((String)vRowTemp.get(45).toString());
                    oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(46).toString());
                    String nombres1 = (String)vRowTemp.get(47).toString() + " " + (String)vRowTemp.get(48).toString() + " " + (String)vRowTemp.get(49).toString();
                    oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setNombres(nombres1);
                    oSegundaMitadEmbarazo.setOtrasCondionesIngreso((String)vRowTemp.get(50).toString());
                    String nombres2 = (String)vRowTemp.get(52).toString() + " " + (String)vRowTemp.get(53).toString() + " " + (String)vRowTemp.get(54).toString();
                    oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().setCedProf((String)vRowTemp.get(51).toString());
                    oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getMedicoTratante().setNombres(nombres2);
                    oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().setClave((String)vRowTemp.get(57).toString());
                    oSegundaMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(58).toString());
                    oSegundaMitadEmbarazo.getCuelloPosterior().setClaveParametro((String)vRowTemp.get(61).toString().trim());
                    oSegundaMitadEmbarazo.getCuelloPosterior().setTipoParametro((String)vRowTemp.get(62).toString());
                    oSegundaMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setNoTarjeta(((Double)vRowTemp.get(79)).intValue());
                }
            }
            return oSegundaMitadEmbarazo;
        }
        public String buscaMembranas(){
            String sResultado = "";
            return sResultado;
        }
	public boolean getLiquidoAmniotico() {
	return bLiquidoAmniotico;
	}

	public void setLiquidoAmniotico(boolean valor) {
	bLiquidoAmniotico=valor;
	}

	public boolean getMembranas() {
	return bMembranas;
	}

	public void setMembranas(boolean valor) {
	bMembranas=valor;
	}

	public Date getHora() {
	return dHora;
	}

	public void setHora(Date valor) {
	dHora=valor;
	}

	public int getContracciones() {
	return nContracciones;
	}

	public void setContracciones(int valor) {
	nContracciones=valor;
	}

	public int getFCfetal() {
	return nFCfetal;
	}

	public void setFCfetal(int valor) {
	nFCfetal=valor;
	}

	public Parametrizacion getLongitud0() {
	return oLongitud0;
	}

	public void setLongitud0(Parametrizacion valor) {
	oLongitud0=valor;
	}
        
        public Parametrizacion getLongitud1(){
            return oLongitud1;
        }
        public void setLongitud1(Parametrizacion oLongitud1){
            this.oLongitud1 = oLongitud1;
        }
        public Parametrizacion getLongitud2(){
            return oLongitud2;
        }
        public void setLongitud2(Parametrizacion oLongitud2){
            this.oLongitud2 = oLongitud2;
        }
        public Parametrizacion getLongitud3(){
            return oLongitud3;
        }
        public void setLongitud3(Parametrizacion oLongitud3){
            this.oLongitud3 = oLongitud3;
        }
        public Parametrizacion getLongitud4(){
            return oLongitud4;
        }
        public void setLongitud4(Parametrizacion oLongitud4){
            this.oLongitud4 = oLongitud4;
        }
	public String getEdema() {
	return sEdema;
	}

	public void setEdema(String valor) {
	sEdema=valor;
	}

	public String getIntencidad() {
	return sIntencidad;
	}

	public void setIntencidad(String valor) {
	sIntencidad=valor;
	}

	public String getOtrasCondionesIngreso() {
	return sOtrasCondionesIngreso;
	}

	public void setOtrasCondionesIngreso(String valor) {
	sOtrasCondionesIngreso=valor;
	}

	public String getRitmo() {
	return sRitmo;
	}

	public void setRitmo(String valor) {
	sRitmo=valor;
	}

	public String getTonoUterino() {
	return sTonoUterino;
	}

	public void setTonoUterino(String valor) {
	sTonoUterino=valor;
	}
        public Parametrizacion getCuelloPosterior(){
            return oCuelloPosterior;
        }
        public void setCuelloPosterior(Parametrizacion oCuelloPosterior){
            this.oCuelloPosterior = oCuelloPosterior;
        }
        public Parametrizacion getCuelloCentral(){
            return oCuelloCentral;
        }
        public void setCuelloCentral(Parametrizacion oCuelloCentral){
            this.oCuelloCentral = oCuelloCentral;
        }
        public Parametrizacion getCuelloResistente(){
            return oCuelloResistente;
        }
        public void setCuelloResistente(Parametrizacion oCuelloResistente){
            this.oCuelloResistente = oCuelloResistente;
        }
        public Parametrizacion getCuelloBlando(){
            return oCuelloBlando;
        }
        public void setCuelloBlando(Parametrizacion oCuelloBlando){
            this.oCuelloBlando = oCuelloBlando;
        }
        //METODOS QUE TRANSFORMA EXPRESIONES BOOLEANAS EN AFIRMACIONES SÍ O NO
        public String getValorMembranas(){
            return this.getMembranas() ? "INTEGRAS" : "ROTAS";
        }
        public String getValorLiquido(){
            return this.getLiquidoAmniotico() ? "NORMAL(X) ANORMAL( )" : "NORMAL( ) ANORMAL(X)";
        }
        public String getValorCuelloPosterior(){
            if(this.getCuelloPosterior() != null)
                return !this.getCuelloPosterior().getValor().isEmpty() ? "POSTERIOR(X)" : "POSTERIOR( )";
            return null;
        }
        public String getValorCuelloCentral(){
            if(this.getCuelloCentral() != null)
                return !this.getCuelloCentral().getValor().isEmpty() ? "CENTRAL(X)" : "CENTRAL( )";
            return null;
        }
        public String getValorCuelloResistente(){
            if(this.getCuelloResistente() != null)
                return !this.getCuelloResistente().getValor().isEmpty() ? "RESISTENTE(X)" : "RESISTENTE( )";
            return null;
        }
        public String getValorCuelloBlando(){
            if(this.getCuelloBlando() != null)
                return !this.getCuelloBlando().getValor().isEmpty() ? "BLANDO(X)" : "BLANDO( )";
            return null;
        }
        public String getValorLongitud0(){
            if( this.getLongitud0() != null)                
                return !this.getLongitud0().getValor().isEmpty() ? "3 CMS(X)" : "3 CMS( )";
            return null;
        }
        public String getValorLongitud1(){
            if( this.getLongitud1() != null)                
                return !this.getLongitud1().getValor().isEmpty() ? "2 CMS(X)" : "2 CMS( )";
            return null;
        }
        public String getValorLongitud2(){
            if( this.getLongitud2() != null)                
                return !this.getLongitud2().getValor().isEmpty() ? "1 CM(X)" : "1 CM( )";
            return null;
        }
        public String getValorLongitud3(){
            if( this.getLongitud3() != null)                
                return !this.getLongitud3().getValor().isEmpty() ? "1/2 CM(X)" : "1/2 CM( )";
            return null;
        }
        public String getValorLongitud4(){
            if( this.getLongitud4() != null)                
                return !this.getLongitud4().getValor().isEmpty() ? "CON DESGARROS (X)" : "CON DESGARROS CM( )";
            return null;
        }
}
