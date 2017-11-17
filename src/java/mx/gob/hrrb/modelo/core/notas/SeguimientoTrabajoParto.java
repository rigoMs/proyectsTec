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
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.SignosVitales;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class SeguimientoTrabajoParto implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bSolicitaOTB;
	private Date dFecRegistro;
	private int nHoras;
	private int nContracciones;
	private int nDilatacion;
	private int nFrecuenciaCardiaca;
	private int nHoraEstanciaParto;
	private int nMlOxi;
	private String sAnalgesia;
	private String sFactoresRiesgo;
	private String sIndicaciones;
	private String sSalaTrabajoParto;
	private String sTrabajoPartoIngreso;
	private String sVariedadPosicion;        
	private ArrayList<Pelvis> oPelvis;
        //****************INICIAN ATRIBUTOS PARA EL CONTROL DE HORAS***************************//
        private boolean bMembranas;
        private Date hora0;
        private Date hora1;
        private Date hora2;
        private Date hora3;
        private Date hora4;
        private Date hora5;
        private Date hora6;
        private Date hora7;
        private Date hora8;
        private Date hora9;
        private Date hora10;
        private Date hora11;
        private Date hora12;
        private Date hora13;
        private Date hora14;
        private int frec0;
        private int frec1;
        private int frec2;
        private int frec3;
        private int frec4;
        private int frec5;
        private int frec6;
        private int frec7;
        private int frec8;
        private int frec9;
        private int frec10;
        private int frec11;
        private int frec12;
        private int frec13;
        private int frec14;
        private int nDilatacion0;
        private int nDilatacion1;
        private int nDilatacion2;
        private int nDilatacion3;
        private int nDilatacion4;
        private int nDilatacion5;
        private int nDilatacion6;
        private int nDilatacion7;
        private int nDilatacion8;
        private int nDilatacion9;
        private int nDilatacion10;
        private int nDilatacion11;
        private int nDilatacion12;
        private int nDilatacion13;
        private int nDilatacion14;
        private String sPosicion0;
        private String sPosicion1;
        private String sPosicion2;
        private String sPosicion3;
        private String sPosicion4;
        private String sPosicion5;
        private String sPosicion6;
        private String sPosicion7;
        private String sPosicion8;
        private String sPosicion9;
        private String sPosicion10;
        private String sPosicion11;
        private String sPosicion12;
        private String sPosicion13;
        private String sPosicion14;
        private int nContraccion0;
        private int nContraccion1;
        private int nContraccion2;
        private int nContraccion3;
        private int nContraccion4;
        private int nContraccion5;
        private int nContraccion6;
        private int nContraccion7;
        private int nContraccion8;
        private int nContraccion9;
        private int nContraccion10;
        private int nContraccion11;
        private int nContraccion12;
        private int nContraccion13;
        private int nContraccion14;
        private int nMloxi0;
        private int nMloxi1;
        private int nMloxi2;
        private int nMloxi3;
        private int nMloxi4;
        private int nMloxi5;
        private int nMloxi6;
        private int nMloxi7;
        private int nMloxi8;
        private int nMloxi9;
        private int nMloxi10;
        private int nMloxi11;
        private int nMloxi12;
        private int nMloxi13;
        private int nMloxi14;
        private boolean bBloqueo0;
        private boolean bBloqueo1;
        private boolean bBloqueo2;
        private boolean bBloqueo3;
        private boolean bBloqueo4;
        private boolean bBloqueo5;
        private boolean bBloqueo6;
        private boolean bSedacion0;
        private boolean bSedacion1;
        private boolean bSedacion2;
        private boolean bSedacion3;
        private boolean bSedacion4;
        private boolean bSedacion5;
        private boolean bSedacion6;
        private PartoGrama oPartoGrama;
        private PersonalHospitalario oPersonal;
        //****************TERMINAN ATRIBUTOS PARA EL CONTROL DE HORAS***************************//
	public SeguimientoTrabajoParto(){
            this.oPersonal = new PersonalHospitalario();
            this.oPersonal.setUsuar(new Usuario());
	HttpServletRequest req;        
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oPersonal.getUsuar().setIdUsuario(this.sUsuarioFirmado);
                oPartoGrama = new PartoGrama();
                oPelvis = new ArrayList<Pelvis>();
	}
        public void buscaPersonal()throws Exception{
            this.oPersonal.buscaPersonalHospitalarioDatos();
        }
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SeguimientoTrabajoParto.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSeguimientoTrabajoParto();"; 
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
	public SeguimientoTrabajoParto[] buscarTodos() throws Exception{
	SeguimientoTrabajoParto arrRet[]=null, oSeguimientoTrabajoParto=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSeguimientoTrabajoParto();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new SeguimientoTrabajoParto[rst.size()];
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
			throw new Exception("SeguimientoTrabajoParto.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSeguimientoTrabajoParto('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("SeguimientoTrabajoParto.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSeguimientoTrabajoParto('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("SeguimientoTrabajoParto.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSeguimientoTrabajoParto('"+sUsuarioFirmado+"');"; 
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
        public String armaQuery(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            String sQuery = "";
            String salaTparto = 
                    (oSeguimiento.getSalaTrabajoParto() == null || 
                    oSeguimiento.getSalaTrabajoParto().isEmpty()) ? 
                    "null::CHARACTER VARYING," :"'" + oSeguimiento.getSalaTrabajoParto().toUpperCase() + "'::CHARACTER VARYING,";
            String otb = oSeguimiento.getSolicitaOTB() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String riesgo = 
                    (oSeguimiento.getFactoresRiesgo() == null || 
                    oSeguimiento.getFactoresRiesgo().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + oSeguimiento.getFactoresRiesgo().toUpperCase() + "'::CHARACTER VARYING,";
            String membranas = oSeguimiento.getMembranas() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String tparto = 
                    (oSeguimiento.getTrabajoPartoIngreso() == null || 
                    oSeguimiento.getTrabajoPartoIngreso().isEmpty()) ? 
                    "null::CHARACTER VARYING," : "'" + oSeguimiento.getTrabajoPartoIngreso().toUpperCase() + "'::CHARACTER VARYING,";
            String indicaciones = 
                    (oSeguimiento.getIndicaciones() == null || 
                    oSeguimiento.getIndicaciones().isEmpty()) ? 
                    "null::TEXT," : "'" + oSeguimiento.getIndicaciones().toUpperCase() + "'::TEXT,";
            String tarjeta = 
                    oSeguimiento.getPartoGrama().getMedicoSupervisor().getNoTarjeta() == 0 ? 
                    "null::INTEGER);" : oSeguimiento.getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER);";
            sQuery = "SELECT * FROM insertaSeguimientoTrabajoParto(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT,'" +
                    this.sUsuarioFirmado + "'::CHARACTER VARYING," +salaTparto + otb + riesgo + membranas + tparto + indicaciones + tarjeta;
            return sQuery;
        }
        public String armaQuery0(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");            
            String fechaFormato = format.format(oSeguimiento.getFecRegistro());
            String sQuery = "";
            String fecha = fechaFormato == null || fechaFormato.isEmpty() ? "null::DATE," : "'" + fechaFormato + "'::DATE,";
            String hora = oSeguimiento.getHora0() == null ? "null::TIMESTAMP WITHOUT TIME ZONE,": "'" + oSeguimiento.getHora0() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec0() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec0() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion0() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion0() + "::SMALLINT,";
            String vPosicion = oSeguimiento.getPosicion0() == null || oSeguimiento.getPosicion0().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion0().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion0() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion0() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi0() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getMloxi0() + "::SMALLINT,";
            String horaEstancia = oSeguimiento.getHoraEstanciaParto() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getHoraEstanciaParto() + "::SMALLINT);";            
            sQuery = "SELECT * FROM insertaSeguimiento0(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                    fecha + hora + frec + dilatacion + vPosicion + contraccion + oxi + horaEstancia;
            return sQuery;
        }
        public String armaQuery1(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora1() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora1()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec1() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec1() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion1() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion1() + "::SMALLINT,";
            String vPosicion = oSeguimiento.getPosicion1() == null || oSeguimiento.getPosicion1().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion1().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion1() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion1() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi1() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi1() + "::SMALLINT);";            
            sQuery = "SELECT * FROM insertaSeguimiento1(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vPosicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery2(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora2() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora2()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec2() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec2() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion2() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion2() + "::SMALLINT,";
            String vPosicion = oSeguimiento.getPosicion2() == null || oSeguimiento.getPosicion2().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion2().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion2() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion2() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi2() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi2() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento2(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vPosicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery3(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora3() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora3()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec3() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec3() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion3() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion3() + "::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion3() == null || oSeguimiento.getPosicion3().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion3().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion3() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion3() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi3() == 0 ? 0 + "::SMALLINT);": oSeguimiento.getMloxi3() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento3(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery4(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora4() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora4()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec4() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec4() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion4() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion4() + "::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion4() == null || oSeguimiento.getPosicion4().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion4().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion4() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion4() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi4() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi4() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento4(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery5(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora5() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora5()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec5() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec5() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion5() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion5() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion5() == null || oSeguimiento.getPosicion5().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion5().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion5() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion5() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi5() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi5() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento5(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery6(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora6() == null  ?  "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora6()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec6() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec6() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion6() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion6() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion6() == null || oSeguimiento.getPosicion6().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion6().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion6() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion6() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi6() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi6() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento6(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery7(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora7() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora7()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec7() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec7() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion7() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion7() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion7() == null || oSeguimiento.getPosicion7().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion7().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion7() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion7() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi7() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi7() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento7(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery8(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora8() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora8()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec8() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec8() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion8() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion8() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion8() == null || oSeguimiento.getPosicion8().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion8().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion8() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion8() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi8() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi8() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento8(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery9(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora9() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora9()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec9() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec9() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion9() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion9() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion9() == null || oSeguimiento.getPosicion9().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion9().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion9() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion9() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi9() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi9() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento9(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery10(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora10() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora10()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec10() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec10() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion10() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion10() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion10() == null || oSeguimiento.getPosicion10().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion10().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion10() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion10() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi10() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi10() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento10(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery11(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora11() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora11()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec11() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec11() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion11() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion11() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion11() == null || oSeguimiento.getPosicion11().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion11().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion11() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion11() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi11() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi11() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento11(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery12(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora12() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora12()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec12() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec12() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion12() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion12() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion12() == null || oSeguimiento.getPosicion12().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion12().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion12() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion12() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi12() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi12() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento12(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery13(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora13() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora13()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec13() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec13() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion13() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion13() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion13() == null || oSeguimiento.getPosicion13().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion13().toLowerCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion13() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion13() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi13() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi13() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento13(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery14(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            String hora = oSeguimiento.getHora14() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oSeguimiento.getHora14()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String frec = oSeguimiento.getFrec14() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getFrec14() + "::SMALLINT,";
            String dilatacion = oSeguimiento.getDilatacion14() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getDilatacion14() +"::SMALLINT,";
            String vposicion = oSeguimiento.getPosicion14() == null || oSeguimiento.getPosicion14().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPosicion14().toUpperCase() + "'::CHARACTER VARYING,";
            String contraccion = oSeguimiento.getContraccion14() == 0 ? 0 + "::SMALLINT," : oSeguimiento.getContraccion14() + "::SMALLINT,";
            String oxi = oSeguimiento.getMloxi14() == 0 ? 0 + "::SMALLINT);" : oSeguimiento.getMloxi14() + "::SMALLINT);";
            sQuery = "SELECT * FROM insertaSeguimiento14(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING,"+
                    hora + frec + dilatacion + vposicion + contraccion + oxi;
            return sQuery;
        }
        public String armaQuery15(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento, SignosVitales oSigno){
            String sQuery ="";
            String bloqueo = oSeguimiento.getBloqueo0() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String sedacion = oSeguimiento.getSedacion0() ? "'1'::CHARACTER," : "'0'::CHARACTER,";            
            String ta = oSigno.getTA() == null || oSigno.getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTA() + "'::CHARACTER VARYING,";
            String pulso = oSigno.getPulso() == 0 ? 0 + "::SMALLINT," : oSigno.getPulso() + "::SMALLINT,";
            String temp = oSigno.getTemp() == null || oSigno.getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTemp() + "'::CHARACTER VARYING,";
            sQuery = "SELECT * FROM insertaSeguimiento15(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                    bloqueo + sedacion + ta + pulso + temp + 1 + "::SMALLINT);";            
            return sQuery;
        }
        public String armaQuery16(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento, SignosVitales oSigno){
            String sQuery ="";
            String bloqueo = oSeguimiento.getBloqueo1() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String sedacion = oSeguimiento.getSedacion1() ? "'1'::CHARACTER," : "'0'::CHARACTER,";            
            String ta = oSigno.getTA() == null || oSigno.getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTA() + "'::CHARACTER VARYING,";
            String pulso = oSigno.getPulso() == 0 ? 0 + "::SMALLINT," : oSigno.getPulso() + "::SMALLINT,";
            String temp = oSigno.getTemp() == null || oSigno.getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTemp() + "'::CHARACTER VARYING,";
            sQuery = "SELECT * FROM insertaSeguimiento16(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    bloqueo + sedacion + ta + pulso + temp + 2 + "::SMALLINT);";
            return sQuery;
        }
        public String armaQuery17(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento, SignosVitales oSigno){
            String sQuery ="";
            String bloqueo = oSeguimiento.getBloqueo2() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String sedacion = oSeguimiento.getSedacion2() ? "'1'::CHARACTER," : "'0'::CHARACTER,";            
            String ta = oSigno.getTA() == null || oSigno.getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTA() + "'::CHARACTER VARYING,";
            String pulso = oSigno.getPulso() == 0 ? 0 + "::SMALLINT," : oSigno.getPulso() + "::SMALLINT,";
            String temp = oSigno.getTemp() == null || oSigno.getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTemp() + "'::CHARACTER VARYING,";
            sQuery = "SELECT * FROM insertaSeguimiento17(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    bloqueo + sedacion + ta + pulso + temp + 3 + "::SMALLINT);";
            return sQuery;
        }
        public String armaQuery18(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento, SignosVitales oSigno){
            String sQuery ="";
            String bloqueo = oSeguimiento.getBloqueo3() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String sedacion = oSeguimiento.getSedacion3() ? "'1'::CHARACTER," : "'0'::CHARACTER,";            
            String ta = oSigno.getTA() == null || oSigno.getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTA() + "'::CHARACTER VARYING,";
            String pulso = oSigno.getPulso() == 0 ? 0 + "::SMALLINT," : oSigno.getPulso() + "::SMALLINT,";
            String temp = oSigno.getTemp() == null || oSigno.getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTemp() + "'::CHARACTER VARYING,";
            sQuery = "SELECT * FROM insertaSeguimiento18(" + foliopaciente + 
                    "::BIGINT," + clavepisodio + "::BIGINT," +
                    bloqueo + sedacion + ta + pulso + temp + 4 + "::SMALLINT);";;
            return sQuery;
        }
        public String armaQuery19(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento, SignosVitales oSigno){
            String sQuery ="";
            String bloqueo = oSeguimiento.getBloqueo4() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String sedacion = oSeguimiento.getSedacion4() ? "'1'::CHARACTER," : "'0'::CHARACTER,";            
            String ta = oSigno.getTA() == null || oSigno.getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTA() + "'::CHARACTER VARYING,";
            String pulso = oSigno.getPulso() == 0 ? 0 + "::SMALLINT," : oSigno.getPulso() + "::SMALLINT,";
            String temp = oSigno.getTemp() == null || oSigno.getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTemp() + "'::CHARACTER VARYING,";
            sQuery = "SELECT * FROM insertaSeguimiento19(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    bloqueo + sedacion + ta + pulso + temp + 5 + "::SMALLINT);";;
            return sQuery;
        }
        public String armaQuery20(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento, SignosVitales oSigno){
            String sQuery ="";
            String bloqueo = oSeguimiento.getBloqueo5() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String sedacion = oSeguimiento.getSedacion5() ? "'1'::CHARACTER," : "'0'::CHARACTER,";            
            String ta = oSigno.getTA() == null || oSigno.getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTA() + "'::CHARACTER VARYING,";
            String pulso = oSigno.getPulso() == 0 ? 0 + "::SMALLINT," : oSigno.getPulso() + "::SMALLINT,";
            String temp = oSigno.getTemp() == null || oSigno.getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTemp() + "'::CHARACTER VARYING,";
            sQuery = "SELECT * FROM insertaSeguimiento20(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT," +
                    bloqueo + sedacion + ta + pulso + temp + 6 + "::SMALLINT);";;
            return sQuery;
        }
        public String armaQuery21(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento, SignosVitales oSigno){
            String sQuery ="";
            String bloqueo = oSeguimiento.getBloqueo6() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String sedacion = oSeguimiento.getSedacion6() ? "'1'::CHARACTER," : "'0'::CHARACTER,";            
            String ta = oSigno.getTA() == null || oSigno.getTA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTA() + "'::CHARACTER VARYING,";
            String pulso = oSigno.getPulso() == 0 ? 0 + "::SMALLINT," : oSigno.getPulso() + "::SMALLINT,";
            String temp = oSigno.getTemp() == null || oSigno.getTemp().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSigno.getTemp() + "'::CHARACTER VARYING,";
            sQuery = "SELECT * FROM insertaSeguimiento21(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                    bloqueo + sedacion + ta + pulso + temp + 7 + "::SMALLINT);";
            return sQuery;
        }
        public String armaQuery22(long foliopaciente, long clavepisodio, SeguimientoTrabajoParto oSeguimiento){
            String sQuery = "";
            String superior = oSeguimiento.getPelvis() == null || oSeguimiento.getPelvis().get(0).getSuperior() == null || oSeguimiento.getPelvis().get(0).getSuperior().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPelvis().get(0).getSuperior().substring(0, 5) + "'::CHARACTER VARYING,";
            String medio = oSeguimiento.getPelvis() == null || oSeguimiento.getPelvis().get(0).getMedio() == null || oSeguimiento.getPelvis().get(0).getMedio().isEmpty() ? "null::CHARACTER VARYING," : "'" + oSeguimiento.getPelvis().get(0).getMedio().substring(0, 5) + "'::CHARACTER VARYING,";
            String inferior = oSeguimiento.getPelvis() == null || oSeguimiento.getPelvis().get(0).getInferior() == null || oSeguimiento.getPelvis().get(0).getInferior().isEmpty() ? "null::CHARACTER VARYING);" : "'" + oSeguimiento.getPelvis().get(0).getInferior().substring(0, 5) + "'::CHARACTER VARYING);";
            sQuery = "SELECT * FROM insertaSeguimiento22(" + foliopaciente + "::BIGINT," + clavepisodio + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                    superior + medio + inferior;
            return sQuery;
        }
        public int insertaDatosReverso(PartoGrama oPartoGrama, SeguimientoTrabajoParto oSeguimiento,
                SignosVitales oSigno1, SignosVitales oSigno2, SignosVitales oSigno3, SignosVitales oSigno4,
                SignosVitales oSigno5, SignosVitales oSigno6, SignosVitales oSigno7 )throws Exception{
            ArrayList<String> rstQuery = null;
            int nRet = -1;
            if(oPartoGrama == null)
                throw new Exception("SEGUIMIENTOTRABAJOPARTO.INSERTADATOS:ERROR,FALTAN DATOS");
            else{
                rstQuery = new ArrayList<String>();
                rstQuery.add(armaQuery(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery0(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery1(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery2(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery3(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery4(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery5(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery6(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery7(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery8(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery9(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery10(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery11(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery12(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery13(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery14(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
                rstQuery.add(armaQuery15(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento, oSigno1));
                rstQuery.add(armaQuery16(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento, oSigno2));
                rstQuery.add(armaQuery17(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento, oSigno3));
                rstQuery.add(armaQuery18(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento, oSigno4));
                rstQuery.add(armaQuery19(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento, oSigno5));
                rstQuery.add(armaQuery20(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento, oSigno6));
                rstQuery.add(armaQuery21(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento, oSigno7));
                rstQuery.add(armaQuery22(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente(), oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio(), oSeguimiento));
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
        
        public SeguimientoTrabajoParto cargaDetallePacienteSeguimientoParto()throws Exception{
            SeguimientoTrabajoParto oSeguimiento = null;
            ArrayList rst = null;
            String sQuery = "";
            int i = 0;
            if(this.getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getPartoGrama().getConsecutivo() == 0  || this.getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetalleseguimientoparto(" + this.getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getPartoGrama().getConsecutivo() + "::SMALLINT," + this.getPartoGrama().getNpartograma() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return new SeguimientoTrabajoParto();
                else{
                    oSeguimiento = new SeguimientoTrabajoParto();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oSeguimiento.setSalaTrabajoParto((String)vRowTemp.get(0).toString());
                    oSeguimiento.getPartoGrama().getEpiMed().getCama().setNumero((String)vRowTemp.get(1).toString());
                    boolean otb = vRowTemp.get(2).toString().compareTo("1") == 0;
                    oSeguimiento.setSolicitaOTB(otb);
                    oSeguimiento.setFactoresRiesgo((String)vRowTemp.get(3).toString());
                    boolean membranas = vRowTemp.get(4).toString().compareTo("1") == 0;
                    oSeguimiento.setMembranas(membranas);
                    oSeguimiento.setTrabajoPartoIngreso((String)vRowTemp.get(5).toString());
                    oSeguimiento.setFecRegistro((Date)vRowTemp.get(6));
                    oSeguimiento.setHora0((Date)vRowTemp.get(7));
                    oSeguimiento.setHora1((Date)vRowTemp.get(8));
                    oSeguimiento.setHora2((Date)vRowTemp.get(9));
                    oSeguimiento.setHora3((Date)vRowTemp.get(10));
                    oSeguimiento.setHora4((Date)vRowTemp.get(11));
                    oSeguimiento.setHora5((Date)vRowTemp.get(12));
                    oSeguimiento.setHora6((Date)vRowTemp.get(13));
                    oSeguimiento.setHora7((Date)vRowTemp.get(14));
                    oSeguimiento.setHora8((Date)vRowTemp.get(15));
                    oSeguimiento.setHora9((Date)vRowTemp.get(16));
                    oSeguimiento.setHora10((Date)vRowTemp.get(17));
                    oSeguimiento.setHora11((Date)vRowTemp.get(18));
                    oSeguimiento.setHora12((Date)vRowTemp.get(19));
                    oSeguimiento.setHora13((Date)vRowTemp.get(20));
                    oSeguimiento.setHora14((Date)vRowTemp.get(21));
                    oSeguimiento.setFrec0(((Double)vRowTemp.get(22)).intValue());
                    oSeguimiento.setFrec1(((Double)vRowTemp.get(23)).intValue());
                    oSeguimiento.setFrec2(((Double)vRowTemp.get(24)).intValue());
                    oSeguimiento.setFrec3(((Double)vRowTemp.get(25)).intValue());
                    oSeguimiento.setFrec4(((Double)vRowTemp.get(26)).intValue());
                    oSeguimiento.setFrec5(((Double)vRowTemp.get(27)).intValue());
                    oSeguimiento.setFrec6(((Double)vRowTemp.get(28)).intValue());
                    oSeguimiento.setFrec7(((Double)vRowTemp.get(29)).intValue());
                    oSeguimiento.setFrec8(((Double)vRowTemp.get(30)).intValue());
                    oSeguimiento.setFrec9(((Double)vRowTemp.get(31)).intValue());
                    oSeguimiento.setFrec10(((Double)vRowTemp.get(32)).intValue());
                    oSeguimiento.setFrec11(((Double)vRowTemp.get(33)).intValue());
                    oSeguimiento.setFrec12(((Double)vRowTemp.get(34)).intValue());
                    oSeguimiento.setFrec13(((Double)vRowTemp.get(35)).intValue());
                    oSeguimiento.setFrec14(((Double)vRowTemp.get(36)).intValue());
                    oSeguimiento.setDilatacion0(((Double)vRowTemp.get(37)).intValue());
                    oSeguimiento.setDilatacion1(((Double)vRowTemp.get(38)).intValue());
                    oSeguimiento.setDilatacion2(((Double)vRowTemp.get(39)).intValue());
                    oSeguimiento.setDilatacion3(((Double)vRowTemp.get(40)).intValue());
                    oSeguimiento.setDilatacion4(((Double)vRowTemp.get(41)).intValue());
                    oSeguimiento.setDilatacion5(((Double)vRowTemp.get(42)).intValue());
                    oSeguimiento.setDilatacion6(((Double)vRowTemp.get(43)).intValue());
                    oSeguimiento.setDilatacion7(((Double)vRowTemp.get(44)).intValue());
                    oSeguimiento.setDilatacion8(((Double)vRowTemp.get(45)).intValue());
                    oSeguimiento.setDilatacion9(((Double)vRowTemp.get(46)).intValue());
                    oSeguimiento.setDilatacion10(((Double)vRowTemp.get(47)).intValue());
                    oSeguimiento.setDilatacion11(((Double)vRowTemp.get(48)).intValue());
                    oSeguimiento.setDilatacion12(((Double)vRowTemp.get(49)).intValue());
                    oSeguimiento.setDilatacion13(((Double)vRowTemp.get(50)).intValue());
                    oSeguimiento.setDilatacion14(((Double)vRowTemp.get(51)).intValue());
                    oSeguimiento.setPosicion0((String)vRowTemp.get(52).toString());
                    oSeguimiento.setPosicion1((String)vRowTemp.get(53).toString());
                    oSeguimiento.setPosicion2((String)vRowTemp.get(54).toString());
                    oSeguimiento.setPosicion3((String)vRowTemp.get(55).toString());
                    oSeguimiento.setPosicion4((String)vRowTemp.get(56).toString());
                    oSeguimiento.setPosicion5((String)vRowTemp.get(57).toString());
                    oSeguimiento.setPosicion6((String)vRowTemp.get(58).toString());
                    oSeguimiento.setPosicion7((String)vRowTemp.get(59).toString());
                    oSeguimiento.setPosicion8((String)vRowTemp.get(60).toString());
                    oSeguimiento.setPosicion9((String)vRowTemp.get(61).toString());
                    oSeguimiento.setPosicion10((String)vRowTemp.get(62).toString());
                    oSeguimiento.setPosicion11((String)vRowTemp.get(63).toString());
                    oSeguimiento.setPosicion12((String)vRowTemp.get(64).toString());
                    oSeguimiento.setPosicion13((String)vRowTemp.get(65).toString());
                    oSeguimiento.setPosicion14((String)vRowTemp.get(66).toString());
                    oSeguimiento.setContraccion0(((Double)vRowTemp.get(67)).intValue());
                    oSeguimiento.setContraccion1(((Double)vRowTemp.get(68)).intValue());
                    oSeguimiento.setContraccion2(((Double)vRowTemp.get(69)).intValue());
                    oSeguimiento.setContraccion3(((Double)vRowTemp.get(70)).intValue());
                    oSeguimiento.setContraccion4(((Double)vRowTemp.get(71)).intValue());
                    oSeguimiento.setContraccion5(((Double)vRowTemp.get(72)).intValue());
                    oSeguimiento.setContraccion6(((Double)vRowTemp.get(73)).intValue());
                    oSeguimiento.setContraccion7(((Double)vRowTemp.get(74)).intValue());
                    oSeguimiento.setContraccion8(((Double)vRowTemp.get(75)).intValue());
                    oSeguimiento.setContraccion9(((Double)vRowTemp.get(76)).intValue());
                    oSeguimiento.setContraccion10(((Double)vRowTemp.get(77)).intValue());
                    oSeguimiento.setContraccion11(((Double)vRowTemp.get(78)).intValue());
                    oSeguimiento.setContraccion12(((Double)vRowTemp.get(79)).intValue());
                    oSeguimiento.setContraccion13(((Double)vRowTemp.get(80)).intValue());
                    oSeguimiento.setContraccion14(((Double)vRowTemp.get(81)).intValue());
                    oSeguimiento.setMloxi0(((Double)vRowTemp.get(82)).intValue());
                    oSeguimiento.setMloxi1(((Double)vRowTemp.get(83)).intValue());
                    oSeguimiento.setMloxi2(((Double)vRowTemp.get(84)).intValue());
                    oSeguimiento.setMloxi3(((Double)vRowTemp.get(85)).intValue());
                    oSeguimiento.setMloxi4(((Double)vRowTemp.get(86)).intValue());
                    oSeguimiento.setMloxi5(((Double)vRowTemp.get(87)).intValue());
                    oSeguimiento.setMloxi6(((Double)vRowTemp.get(88)).intValue());
                    oSeguimiento.setMloxi7(((Double)vRowTemp.get(89)).intValue());
                    oSeguimiento.setMloxi8(((Double)vRowTemp.get(90)).intValue());
                    oSeguimiento.setMloxi9(((Double)vRowTemp.get(91)).intValue());
                    oSeguimiento.setMloxi10(((Double)vRowTemp.get(92)).intValue());
                    oSeguimiento.setMloxi11(((Double)vRowTemp.get(93)).intValue());
                    oSeguimiento.setMloxi12(((Double)vRowTemp.get(94)).intValue());
                    oSeguimiento.setMloxi13(((Double)vRowTemp.get(95)).intValue());
                    oSeguimiento.setMloxi14(((Double)vRowTemp.get(96)).intValue());
                    boolean bloqueo0 = vRowTemp.get(97).toString().compareTo("1") == 0;
                    boolean bloqueo1 = vRowTemp.get(98).toString().compareTo("1") == 0;
                    boolean bloqueo2 = vRowTemp.get(99).toString().compareTo("1") == 0;
                    boolean bloqueo3 = vRowTemp.get(100).toString().compareTo("1") == 0;
                    boolean bloqueo4 = vRowTemp.get(101).toString().compareTo("1") == 0;
                    boolean bloqueo5 = vRowTemp.get(102).toString().compareTo("1") == 0;
                    boolean bloqueo6 = vRowTemp.get(103).toString().compareTo("1") == 0;
                    oSeguimiento.setBloqueo0(bloqueo0);
                    oSeguimiento.setBloqueo1(bloqueo1);
                    oSeguimiento.setBloqueo2(bloqueo2);
                    oSeguimiento.setBloqueo3(bloqueo3);
                    oSeguimiento.setBloqueo4(bloqueo4);
                    oSeguimiento.setBloqueo5(bloqueo5);
                    oSeguimiento.setBloqueo6(bloqueo6);
                    boolean sedacion0 = vRowTemp.get(104).toString().compareTo("1") == 0;
                    boolean sedacion1 = vRowTemp.get(105).toString().compareTo("1") == 0;
                    boolean sedacion2 = vRowTemp.get(106).toString().compareTo("1") == 0;
                    boolean sedacion3 = vRowTemp.get(107).toString().compareTo("1") == 0;
                    boolean sedacion4 = vRowTemp.get(108).toString().compareTo("1") == 0;
                    boolean sedacion5 = vRowTemp.get(109).toString().compareTo("1") == 0;
                    boolean sedacion6 = vRowTemp.get(110).toString().compareTo("1") == 0;
                    oSeguimiento.setSedacion0(sedacion0);
                    oSeguimiento.setSedacion1(sedacion1);
                    oSeguimiento.setSedacion2(sedacion2);
                    oSeguimiento.setSedacion3(sedacion3);
                    oSeguimiento.setSedacion4(sedacion4);
                    oSeguimiento.setSedacion5(sedacion5);
                    oSeguimiento.setSedacion6(sedacion6);
                    oSeguimiento.setHoraEstanciaParto(((Double)vRowTemp.get(111)).intValue());                    
                    oSeguimiento.getPelvis().add(new Pelvis());
                    oSeguimiento.getPelvis().get(0).setSuperior((String)vRowTemp.get(112).toString());
                    oSeguimiento.getPelvis().get(0).setMedio((String)vRowTemp.get(113).toString());
                    oSeguimiento.getPelvis().get(0).setInferior((String)vRowTemp.get(114).toString());
                    oSeguimiento.setIndicaciones((String)vRowTemp.get(115).toString());
                    oSeguimiento.getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(116).toString());                    
                    String nombres = (String)vRowTemp.get(117).toString() + " " + (String)vRowTemp.get(118).toString() + " " + (String)vRowTemp.get(119).toString();
                    oSeguimiento.getPartoGrama().getMedicoSupervisor().setNombres(nombres);
                    oSeguimiento.getPelvis().add(new Pelvis());
                    oSeguimiento.getPelvis().get(1).setValores(new Parametrizacion());
                    oSeguimiento.getPelvis().get(1).getValores().setClaveParametro((String)vRowTemp.get(120).toString());
                    oSeguimiento.getPelvis().get(1).getValores().setTipoParametro((String)vRowTemp.get(121).toString());
                    oSeguimiento.getPelvis().add(new Pelvis());
                    oSeguimiento.getPelvis().get(2).setValores(new Parametrizacion());
                    oSeguimiento.getPelvis().get(2).getValores().setClaveParametro((String)vRowTemp.get(122).toString());
                    oSeguimiento.getPelvis().get(2).getValores().setTipoParametro((String)vRowTemp.get(123).toString());
                    oSeguimiento.getPelvis().add(new Pelvis());
                    oSeguimiento.getPelvis().get(3).setValores(new Parametrizacion());
                    oSeguimiento.getPelvis().get(3).getValores().setClaveParametro((String)vRowTemp.get(124).toString());
                    oSeguimiento.getPelvis().get(3).getValores().setTipoParametro((String)vRowTemp.get(125).toString());                    
                }
            }
            return oSeguimiento;
        }
        
        public SeguimientoTrabajoParto[] cargaSignosVitales() throws Exception{
            SeguimientoTrabajoParto[] arrSeguimiento = null;
            SeguimientoTrabajoParto oSeguimiento = null;
            ArrayList rst = null;
            String sQuery = "";            
            if(this.getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getPartoGrama().getConsecutivo() == 0 || this.getPartoGrama().getNpartograma() == 0)                
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallesignosvitalespartograma(" + this.getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + this.getPartoGrama().getConsecutivo() + "::SMALLINT," +
                        this.getPartoGrama().getNpartograma() + "::SMALLINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return null;
                else{
                    arrSeguimiento = new SeguimientoTrabajoParto[rst.size()];
                    ArrayList vRowTemp = null;
                    for(int j = 0; j < arrSeguimiento.length; j++){
                        oSeguimiento = new SeguimientoTrabajoParto();
                        vRowTemp = (ArrayList)rst.get(j);
                        oSeguimiento.getPartoGrama().getEpiMed().getSignosVitales().setTA((String)vRowTemp.get(0).toString());
                        oSeguimiento.getPartoGrama().getEpiMed().getSignosVitales().setPulso(((Double)vRowTemp.get(1)).intValue());
                        oSeguimiento.getPartoGrama().getEpiMed().getSignosVitales().setTemp((String)vRowTemp.get(2).toString());
                        arrSeguimiento[j] = oSeguimiento;
                    }
                }
            }
            return arrSeguimiento;
        }
        
	public boolean getSolicitaOTB() {
	return bSolicitaOTB;
	}

	public void setSolicitaOTB(boolean valor) {
	bSolicitaOTB=valor;
	}

	public Date getFecRegistro() {
	return dFecRegistro;
	}

	public void setFecRegistro(Date valor) {
	dFecRegistro=valor;
	}
        public boolean getFechaLectura(){
            return this.dFecRegistro == null ? !true : !false;
        }

	public int getHoras() {
	return nHoras;
	}

	public void setHoras(int valor) {
	nHoras=valor;
	}

	public int getContracciones() {
	return nContracciones;
	}

	public void setContracciones(int valor) {
	nContracciones=valor;
	}

	public int getDilatacion() {
	return nDilatacion;
	}

	public void setDilatacion(int valor) {
	nDilatacion=valor;
	}

	public int getFrecuenciaCardiaca() {
	return nFrecuenciaCardiaca;
	}

	public void setFrecuenciaCardiaca(int valor) {
	nFrecuenciaCardiaca=valor;
	}

	public int getHoraEstanciaParto() {
	return nHoraEstanciaParto;
	}

	public void setHoraEstanciaParto(int valor) {
	nHoraEstanciaParto=valor;
	}

	public int getMlOxi() {
	return nMlOxi;
	}

	public void setMlOxi(int valor) {
	nMlOxi=valor;
	}

	public String getAnalgesia() {
	return sAnalgesia;
	}

	public void setAnalgesia(String valor) {
	sAnalgesia=valor;
	}

	public String getFactoresRiesgo() {
	return sFactoresRiesgo;
	}

	public void setFactoresRiesgo(String valor) {
	sFactoresRiesgo=valor;
	}

	public String getIndicaciones() {
	return sIndicaciones;
	}

	public void setIndicaciones(String valor) {
	sIndicaciones=valor;
	}

	public String getSalaTrabajoParto() {
	return sSalaTrabajoParto;
	}

	public void setSalaTrabajoParto(String valor) {
	sSalaTrabajoParto=valor;
	}

	public String getTrabajoPartoIngreso() {
	return sTrabajoPartoIngreso;
	}

	public void setTrabajoPartoIngreso(String valor) {
	sTrabajoPartoIngreso=valor;
	}

	public String getVariedadPosicion() {
	return sVariedadPosicion;
	}

	public void setVariedadPosicion(String valor) {
	sVariedadPosicion=valor;
	}

	public ArrayList<Pelvis> getPelvis() {
	return oPelvis;
	}

	public void setPelvis(ArrayList<Pelvis> valor) {
	oPelvis=valor;
	}
        public Date getHora0(){
            return hora0;
        }
        public void setHora0(Date hora0){
            this.hora0 = hora0;            
        }
        public Date getHora1(){
            return hora1;
        }
        public void setHora1(Date hora1){
            this.hora1 = hora1;           
        }
        public Date getHora2(){
            return hora2;
        } 
        public void setHora2(Date hora2){
            this.hora2 = hora2;            
        }
        public Date getHora3(){
            return hora3;
        }
        public void setHora3(Date hora3){
            this.hora3 = hora3;            
        }
        public Date getHora4(){
            return hora4;
        }
        public void setHora4(Date hora4){
            this.hora4 = hora4;            
        }
        public Date getHora5(){
            return hora5;
        }
        public void setHora5(Date hora5){
            this.hora5 = hora5;            
        }
        public Date getHora6(){
            return hora6;
        }
        public void setHora6(Date hora6){
            this.hora6 = hora6;            
        }
        public Date getHora7(){
            return hora7;
        }
        public void setHora7(Date hora7){
            this.hora7 = hora7;            
        }
        public Date getHora8(){
            return hora8;
        }
        public void setHora8(Date hora8){
            this.hora8 = hora8;            
        }
        public Date getHora9(){
            return hora9;
        }
        public void setHora9(Date hora9){
            this.hora9 = hora9;            
        }
        public Date getHora10(){
            return hora10;
        }
        public void setHora10(Date hora10){
            this.hora10 = hora10;            
        }
        public Date getHora11(){
            return hora11;
        }
        public void setHora11(Date hora11){
            this.hora11 = hora11;            
        }
        public Date getHora12(){
            return hora12;
        }
        public void setHora12(Date hora12){
            this.hora12 = hora12;            
        }
        public Date getHora13(){
            return hora13;
        }
        public void setHora13(Date hora13){
            this.hora13 = hora13;            
        }
        public Date getHora14(){
            return hora14;
        }
        public void setHora14(Date hora14){
            this.hora14 = hora14;            
        }
        public int getFrec0(){
            return frec0;
        }
        public void setFrec0(int frec0){
            this.frec0 = frec0;            
        }
        public int getFrec1(){
            return frec1;
        }
        public void setFrec1(int frec1){
            this.frec1 = frec1;            
        }
        public int getFrec2(){
            return frec2;
        }
        public void setFrec2(int frec2){
            this.frec2 = frec2;
        }
        public int getFrec3(){
            return frec3;
        }
        public void setFrec3(int frec3){
            this.frec3 = frec3;
        }
        public int getFrec4(){
            return frec4;
        }
        public void setFrec4(int frec4){
            this.frec4 = frec4;
        }
        public int getFrec5(){
            return frec5;
        }
        public void setFrec5(int frec5){
            this.frec5 = frec5;
        }
        public int getFrec6(){
            return frec6;
        }
        public void setFrec6(int frec6){
            this.frec6 = frec6;
        }
        public int getFrec7(){
            return frec7;
        }
        public void setFrec7(int frec7){
            this.frec7 = frec7;
        }
        public int getFrec8(){
            return frec8;
        }
        public void setFrec8(int frec8){
            this.frec8 = frec8;
        }
        public int getFrec9(){
            return frec9;
        }
        public void setFrec9(int frec9){
            this.frec9 = frec9;
        }
        public int getFrec10(){
            return frec10;
        }
        public void setFrec10(int frec10){
            this.frec10 = frec10;
        }
        public int getFrec11(){
            return frec11;
        }
        public void setFrec11(int frec11){
            this.frec11 = frec11;
        }
        public int getFrec12(){
            return frec12;
        }
        public void setFrec12(int frec12){
            this.frec12 = frec12;
        }
        public int getFrec13(){
            return frec13;
        }
        public void setFrec13(int frec13){
            this.frec13 = frec13;
        }
        public int getFrec14(){
            return frec14;
        }
        public void setFrec14(int frec14){
            this.frec14 = frec14;
        }
        public int getDilatacion0(){
            return nDilatacion0;
        }
        public void setDilatacion0(int nDilatacion0){
            this.nDilatacion0 = nDilatacion0;
        }
        public int getDilatacion1(){
            return nDilatacion1;
        }
        public void setDilatacion1(int nDilatacion1){
            this.nDilatacion1 = nDilatacion1;
        }
        public int getDilatacion2(){
            return nDilatacion2;
        }
        public void setDilatacion2(int nDiltacion2){
            this.nDilatacion2 = nDiltacion2;
        }
        public int getDilatacion3(){
            return nDilatacion3;
        }
        public void setDilatacion3(int nDiltacion3){
            this.nDilatacion3 = nDiltacion3;
        }
        public int getDilatacion4(){
            return nDilatacion4;
        }
        public void setDilatacion4(int nDiltacion4){
            this.nDilatacion4 = nDiltacion4;
        }
        public int getDilatacion5(){
            return nDilatacion5;
        }
        public void setDilatacion5(int nDilatacion5){
            this.nDilatacion5 = nDilatacion5;
        }
        public int getDilatacion6(){
            return nDilatacion6;
        }
        public void setDilatacion6(int nDiltacion6){
            this.nDilatacion6 = nDiltacion6;
        }
        public int getDilatacion7(){
            return nDilatacion7;
        }
        public void setDilatacion7(int nDiltacion7){
            this.nDilatacion7 = nDiltacion7;
        }
        public int getDilatacion8(){
            return nDilatacion8;
        }
        public void setDilatacion8(int nDilatacion8){
            this.nDilatacion8 = nDilatacion8;
        }
        public int getDilatacion9(){
            return nDilatacion9;
        }
        public void setDilatacion9(int nDilatacion9){
            this.nDilatacion9 = nDilatacion9;
        }
        public int getDilatacion10(){
            return nDilatacion10;
        }
        public void setDilatacion10(int nDilatacion10){
            this.nDilatacion10 = nDilatacion10;
        }
        public int getDilatacion11(){
            return nDilatacion11;
        }
        public void setDilatacion11(int nDiltacion11){
            this.nDilatacion11 = nDiltacion11;
        }
        public int getDilatacion12(){
            return nDilatacion12;
        }
        public void setDilatacion12(int nDiltacion12){
            this.nDilatacion12 = nDilatacion10;
        }
        public int getDilatacion13(){
            return nDilatacion13;
        }
        public void setDilatacion13(int nDilatacion13){
            this.nDilatacion13 = nDilatacion13;
        }
        public int getDilatacion14(){
            return nDilatacion14;
        }
        public void setDilatacion14(int nDilatacion14){
            this.nDilatacion14 = nDilatacion14;
        }
        public PartoGrama getPartoGrama(){
            return oPartoGrama;
        }
        public void setPartoGrama(PartoGrama oPartoGrama){
            this.oPartoGrama = oPartoGrama;
        }
        public String getPosicion0(){
            return sPosicion0;
        }
        public void setPosicion0(String sPosicion0){
            this.sPosicion0 = sPosicion0;
        }
        public String getPosicion1(){
            return sPosicion1;
        }
        public void setPosicion1(String sPosicion1){
            this.sPosicion1 = sPosicion1;
        }
        public String getPosicion2(){
            return sPosicion2;
        }
        public void setPosicion2(String sPosicion2){
            this.sPosicion2 = sPosicion2;
        }
        public String getPosicion3(){
            return sPosicion3;
        }
        public void setPosicion3(String sPosicion3){
            this.sPosicion3 = sPosicion3;
        }
        public String getPosicion4(){
            return sPosicion4;
        }
        public void setPosicion4(String sPosicion4){
            this.sPosicion4 = sPosicion4;
        }
        public String getPosicion5(){
            return sPosicion5;
        }
        public void setPosicion5(String sPosicion5){
            this.sPosicion5 = sPosicion5;
        }
        public String getPosicion6(){
            return sPosicion6;
        }
        public void setPosicion6(String sPosicion6){
            this.sPosicion6 = sPosicion6;
        }
        public String getPosicion7(){
            return sPosicion7;
        }
        public void setPosicion7(String sPosicion7){
            this.sPosicion7 = sPosicion7;
        }
        public String getPosicion8(){
            return sPosicion8;
        }
        public void setPosicion8(String sPosicion8){
            this.sPosicion8 = sPosicion8;
        }
        public String getPosicion9(){            
            return sPosicion9;
        }
        public void setPosicion9(String sPosicion9){
            this.sPosicion9 = sPosicion9;
        }
        public String getPosicion10(){
            return sPosicion10;
        }
        public void setPosicion10(String sPosicion10){
            this.sPosicion10 = sPosicion10;
        }
        public String getPosicion11(){
            return sPosicion11;
        }
        public void setPosicion11(String sPosicion11){
            this.sPosicion11 = sPosicion11;
        }
        public String getPosicion12(){
            return sPosicion12;
        }
        public void setPosicion12(String sPosicion12){
            this.sPosicion12 = sPosicion12;
        }
        public String getPosicion13(){
            return sPosicion13;
        }
        public void setPosicion13(String sPosicion13){
            this.sPosicion13 = sPosicion13;
        }
        public String getPosicion14(){
            return sPosicion14;
        }
        public void setPosicion14(String sPosicion14){
            this.sPosicion14 = sPosicion14;
        }
        public boolean getMembranas(){
            return bMembranas;
        }
        public void setMembranas(boolean bMembranas){
            this.bMembranas = bMembranas;
        }
        public int getContraccion0(){
            return nContraccion0;
        }
        public void setContraccion0(int nContraccion0){
            this.nContraccion0 = nContraccion0;
        }
        public int getContraccion1(){
            return nContraccion1;
        }
        public void setContraccion1(int nContraccion1){
            this.nContraccion1 = nContraccion1;
        }
        public int getContraccion2(){
            return nContraccion2;
        }
        public void setContraccion2(int nContraccion2){
            this.nContraccion2 = nContraccion2;
        }
        public int getContraccion3(){
            return nContraccion3;
        }
        public void setContraccion3(int nContraccion3){
            this.nContraccion3 = nContraccion3;
        }
        public int getContraccion4(){
            return nContraccion4;
        }
        public void setContraccion4(int nContraccion4){
            this.nContraccion4 = nContraccion4;
        }
        public int getContraccion5(){
            return nContraccion5;
        }
        public void setContraccion5(int nContraccion5){
            this.nContraccion5 = nContraccion5;
        }
        public int getContraccion6(){
            return nContraccion6;
        }
        public void setContraccion6(int nContraccion6){
            this.nContraccion6 = nContraccion6;
        }
        public int getContraccion7(){
            return nContraccion7;
        }
        public void setContraccion7(int nContraccion7){
            this.nContraccion7 = nContraccion7;
        }
        public int getContraccion8(){
            return nContraccion8;
        }
        public void setContraccion8(int nContraccion8){
            this.nContraccion8 = nContraccion8;
        }
        public int getContraccion9(){
            return nContraccion9;
        }
        public void setContraccion9(int nContraccion9){
            this.nContraccion9 = nContraccion9;
        }
        public int getContraccion10(){
            return nContraccion10;
        }
        public void setContraccion10(int nContraccion10){
            this.nContraccion10 = nContraccion10;
        }
        public int getContraccion11(){
            return nContraccion11;
        }
        public void setContraccion11(int nContraccion11){
            this.nContraccion11 = nContraccion11;
        }
        public int getContraccion12(){
            return nContraccion12;
        }
        public void setContraccion12(int nContraccion12){
            this.nContraccion12 = nContraccion12;
        }
        public int getContraccion13(){
            return nContraccion13;
        }
        public void setContraccion13(int nContraccion13){
            this.nContraccion13 = nContraccion13;
        }
        public int getContraccion14(){
            return nContraccion14;
        }
        public void setContraccion14(int nContraccion14){
            this.nContraccion14 = nContraccion14;
        }
        public int getMloxi0(){
            return nMloxi0;
        }
        public void setMloxi0(int nMloxi0){
            this.nMloxi0 = nMloxi0;
        }
        public int getMloxi1(){
            return nMloxi1;
        }
        public void setMloxi1(int nMloxi1){
            this.nMloxi1 = nMloxi1;
        }
        public int getMloxi2(){
            return nMloxi2;
        }
        public void setMloxi2(int nMloxi2){
            this.nMloxi2 = nMloxi2;
        }
        public int getMloxi3(){
            return nMloxi3;
        }
        public void setMloxi3(int nMloxi3){
            this.nMloxi3 = nMloxi3;
        }
        public int getMloxi4(){
            return nMloxi4;
        }
        public void setMloxi4(int nMloxi4){
            this.nMloxi4 = nMloxi4;
        }
        public int getMloxi5(){
            return nMloxi5;
        }
        public void setMloxi5(int nMloxi5){
            this.nMloxi5 = nMloxi5;
        }
        public int getMloxi6(){
            return nMloxi6;
        }
        public void setMloxi6(int nMloxi6){
            this.nMloxi6 = nMloxi6;
        }
        public int getMloxi7(){
            return nMloxi7;
        }
        public void setMloxi7(int nMloxi7){
            this.nMloxi7 = nMloxi7;
        }
        public int getMloxi8(){
            return nMloxi8;
        }
        public void setMloxi8(int nMloxi8){
            this.nMloxi8 = nMloxi8;
        }
        public int getMloxi9(){
            return nMloxi9;
        }
        public void setMloxi9(int nMloxi9){
            this.nMloxi9 = nMloxi9;
        }
        public int getMloxi10(){
            return nMloxi10;
        }
        public void setMloxi10(int nMloxi10){
            this.nMloxi10 = nMloxi10;
        }
        public int getMloxi11(){
            return nMloxi11;
        }
        public void setMloxi11(int nMloxi11){
            this.nMloxi11 = nMloxi11;
        }
        public int getMloxi12(){
            return nMloxi12;
        }
        public void setMloxi12(int nMloxi12){
            this.nMloxi12 = nMloxi12;
        }
        public int getMloxi13(){
            return nMloxi13;
        }
        public void setMloxi13(int nMloxi13){
            this.nMloxi13 = nMloxi13;
        }
        public int getMloxi14(){
            return nMloxi14;
        }
        public void setMloxi14(int nMloxi14){
            this.nMloxi14 = nMloxi14;
        }
        public boolean getBloqueo0(){
            return this.bBloqueo0;
        }
        public void setBloqueo0(boolean valor){
            this.bBloqueo0 = valor;
        }
        public boolean getBloqueo1(){
            return bBloqueo1;
        }
        public void setBloqueo1(boolean bBloqueo1){
            this.bBloqueo1 = bBloqueo1;
        }
        public boolean getBloqueo2(){
            return bBloqueo2;
        }
        public void setBloqueo2(boolean bBloqueo2){
            this.bBloqueo2 = bBloqueo2;
        }
        public boolean getBloqueo3(){
            return bBloqueo3;
        }
        public void setBloqueo3(boolean bBloqueo3){
            this.bBloqueo3 = bBloqueo3;
        }
        public boolean getBloqueo4(){
            return bBloqueo4;
        }
        public void setBloqueo4(boolean bBloqueo4){
            this.bBloqueo4 = bBloqueo4;
        }
        public boolean getBloqueo5(){
            return bBloqueo5;
        }
        public void setBloqueo5(boolean bBloqueo5){
            this.bBloqueo5 = bBloqueo5;
        }
        public boolean getBloqueo6(){
            return bBloqueo6;
        }
        public void setBloqueo6(boolean bBloqueo6){
            this.bBloqueo6 = bBloqueo6;
        }
        public boolean getSedacion0(){
            return bSedacion0;
        }
        public void setSedacion0(boolean bSedacion0){
            this.bSedacion0 = bSedacion0;
        }
        public boolean getSedacion1(){
            return bSedacion1;
        }
        public void setSedacion1(boolean bSedacion1){
            this.bSedacion1 = bSedacion1;            
        }
        public boolean getSedacion2(){
            return bSedacion2;
        }
        public void setSedacion2(boolean bSedacion2){
            this.bSedacion2 = bSedacion2;
        }
        public boolean getSedacion3(){
            return bSedacion3;
        }
        public void setSedacion3(boolean bSedacion3){
            this.bSedacion3 = bSedacion3;
        }
        public boolean getSedacion4(){
            return bSedacion4;
        }
        public void setSedacion4(boolean bSedacion4){
            this.bSedacion4 = bSedacion4;
        }
        public boolean getSedacion5(){
            return bSedacion5;
        }
        public void setSedacion5(boolean bSedacion5){
            this.bSedacion5 = bSedacion5;
        }
        public boolean getSedacion6(){
            return bSedacion6;
        }
        public void setSedacion6(boolean bSedacion6){
            this.bSedacion6 = bSedacion6;
        }
        
        //METODOS CREADOS POR ALBERTO
        //COMBIERTEN UNA EXPRESION BOOLEAN A EXPRESIONES AFIRMATIVAS
        public String getValorOtb(){
            return this.getSolicitaOTB() ? "SÍ" : "NO";
        }
        public String getValorMembranas(){
            return this.getMembranas() ? "SÍ" : "NO";
        }
        public String getValorPelvisSuperior(){
            if(this.getPelvis().get(0).getSuperior() != null || !this.getPelvis().get(0).getSuperior().isEmpty()){
                if(this.getPelvis().get(0).getSuperior().compareTo("SUFICIENTE") == 0)
                    return "S";
                else
                    if(this.getPelvis().get(0).getSuperior().compareTo("LIMITE") == 0)
                        return "L";
                    else
                        if(this.getPelvis().get(0).getSuperior().compareTo("REDUCIDA") == 0)
                            return "R";
            }
            return null;
        }
        public String getValorPelvisMedio(){
            if(this.getPelvis().get(0).getMedio() != null || !this.getPelvis().get(0).getMedio().isEmpty()){
                if(this.getPelvis().get(0).getMedio().compareTo("SUFICIENTE") == 0)
                    return "S";
                else
                    if(this.getPelvis().get(0).getMedio().compareTo("LIMITE") == 0)
                        return "L";
                    else
                        if(this.getPelvis().get(0).getMedio().compareTo("REDUCIDA") == 0)
                            return "R";
            }
            return null;
        }
        public String getValorPelvisInferior(){
            if(this.getPelvis().get(0).getInferior() != null || !this.getPelvis().get(0).getInferior().isEmpty()){
                if(this.getPelvis().get(0).getInferior().compareTo("SUFICIENTE") == 0)
                    return "S";
                else
                    if(this.getPelvis().get(0).getInferior().compareTo("LIMITE") == 0)
                        return "L";
                    else
                        if(this.getPelvis().get(0).getInferior().compareTo("REDUCIDA") == 0)
                            return "R";
            }
            return null;
        }
} 