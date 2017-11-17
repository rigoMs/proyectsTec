/*
* MODIFICACION REALIZADA POR ALBERTO
* AGREGUE UN OBJETO TERMMINACION EMBARAZO
*/
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
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Producto;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class Legrado implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFecha;
	private Date dHora;
	private Parametrizacion oTipo;
	private String sObservaciones;
	private String sProductoAborto;
        private TerminacionEmbarazo oTerminacionEmbarazo;
        private PersonalHospitalario oPersonal;
	public Legrado(){
	HttpServletRequest req;        
            this.oPersonal = new PersonalHospitalario();
            this.oPersonal.setUsuar(new Usuario());
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
            oTerminacionEmbarazo = new TerminacionEmbarazo();
            oTipo = new Parametrizacion();
            this.oPersonal.getUsuar().setIdUsuario(this.sUsuarioFirmado);
	}
        public void buscaPersonal()throws Exception{
            this.oPersonal.buscaPersonalHospitalarioDatos();
        }
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Legrado.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveLegrado();"; 
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
	public Legrado[] buscarTodos() throws Exception{
	Legrado arrRet[]=null, oLegrado=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosLegrado();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Legrado[rst.size()];
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
			throw new Exception("Legrado.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaLegrado('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Legrado.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaLegrado('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("Legrado.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaLegrado('"+sUsuarioFirmado+"');"; 
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
        public int semanasAmenorrea(PartoGrama oPartoGrama)throws Exception{
            ArrayList rst = null;
            int nSemanas = 0;
            String sQuery = "";
            if(oPartoGrama != null){
                if(oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() == 0)
                    throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
                else{
                    sQuery = "SELECT * FROM consultasemanasamenorrea(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() +"::BIGINT,"
                            + oPartoGrama.getEpiMed().getClaveEpisodio() + "::BIGINT);";                
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){
                        rst = oAD.ejecutarConsulta(sQuery);
                        oAD.desconectar();
                    }
                    if(rst.isEmpty())
                        return 0;
                    else{
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        nSemanas = ((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }            
            return nSemanas;
        }
        public String armaQuery(PartoGrama oPartoGrama){
            String sQuery = "";
            sQuery = "SELECT * FROM insertActualizaTerminacionEmbarazo(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                    oPartoGrama.getEpiMed().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING);";
            return sQuery;
        }
        public String armaQuery(PartoGrama oPartoGrama, PartoEutocico oPartoEutocico){            
            String sQuery = "";
            if(oPartoEutocico.getVariedadPosicion() == null || oPartoEutocico.getVariedadPosicion().isEmpty())
                return sQuery;
            else{
                String vposicion = 
                        (oPartoEutocico.getVariedadPosicion() == null || 
                        oPartoEutocico.getVariedadPosicion().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oPartoEutocico.getVariedadPosicion().toUpperCase()+ "'::CHARACTER VARYING,";
                String episiotomia = 
                        (oPartoEutocico.getEpisiotomia() == null || 
                        oPartoEutocico.getEpisiotomia().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oPartoEutocico.getEpisiotomia().toUpperCase() + "'::CHARACTER VARYING,";
                String prolongacion = 
                        (oPartoEutocico.getProlongacion() == null || 
                        oPartoEutocico.getEpisiotomia().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oPartoEutocico.getProlongacion().toUpperCase() + "'::CHARACTER VARYING,";
                String desgarroPerine = 
                        (oPartoEutocico.getDesgarro().get(0).getClaveParametro() == null || 
                        oPartoEutocico.getDesgarro().get(0).getClaveParametro().isEmpty()) ? 
                         "null::CHARACTER(5)," : "'" + oPartoEutocico.getDesgarro().get(0).getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
                String desgarroVaginal = 
                        (oPartoEutocico.getDesgarro().get(1).getClaveParametro() == null || 
                        oPartoEutocico.getDesgarro().get(1).getClaveParametro().isEmpty()) ? 
                        "null::CHARACTER(5)," : "'" + oPartoEutocico.getDesgarro().get(1).getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
                String desgarroCervix = 
                        (oPartoEutocico.getDesgarro().get(2).getClaveParametro() == null || 
                        oPartoEutocico.getDesgarro().get(2).getClaveParametro().isEmpty()) ? 
                        "null::CHARACTER(5)," : "'" + oPartoEutocico.getDesgarro().get(2).getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
                String observaciones = 
                        (oPartoEutocico.getObservaciones() == null || 
                        oPartoEutocico.getObservaciones().isEmpty()) ? 
                        "null::TEXT);" : "'" + oPartoEutocico.getObservaciones().toUpperCase() + "'::TEXT);";
                sQuery = "SELECT * FROM insertActualizaPartoEutocico(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + vposicion + episiotomia + prolongacion + desgarroPerine +
                        desgarroVaginal + desgarroCervix + observaciones;                 
            }
            return sQuery;
        }
        public String armaQuery(PartoGrama oPartoGrama, Forceps oForceps){
            String sQuery = "";
            if(oForceps.getVariedadPosicion().getClaveParametro() != null || !oForceps.getVariedadPosicion().getClaveParametro().isEmpty() ||
                    oForceps.getIndicacionPrincipal() != null || !oForceps.getIndicacionPrincipal().isEmpty() ||
                    oForceps.getInstrumento() != null || !oForceps.getInstrumento().isEmpty() ||
                    oForceps.getObservaciones() != null || !oForceps.getObservaciones().isEmpty()){
                String vposicion = 
                        (oForceps.getVariedadPosicion().getClaveParametro() == null || 
                        oForceps.getVariedadPosicion().getClaveParametro().isEmpty()) ? 
                        "null::CHARACTER(5)," : "'" + oForceps.getVariedadPosicion().getClaveParametro().substring(0,5).toUpperCase() + "'::CHARACTER(5),";
                String indicacion = 
                        (oForceps.getIndicacionPrincipal() == null || 
                        oForceps.getIndicacionPrincipal().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oForceps.getIndicacionPrincipal().toUpperCase() + "'::CHARACTER VARYING,";
                String instrumento = 
                        (oForceps.getInstrumento() == null || 
                        oForceps.getInstrumento().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oForceps.getInstrumento().toUpperCase() + "'::CHARACTER VARYING,";
                String observaciones = 
                        (oForceps.getObservaciones() == null || 
                        oForceps.getObservaciones().isEmpty()) ? 
                        "null::TEXT);" : "'" + oForceps.getObservaciones().toUpperCase() + "'::TEXT);";
                sQuery = "SELECT * FROM insertActualizaForceps(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + vposicion + indicacion + instrumento + observaciones;
                
            }
            return sQuery;
        }
        public String armaQuery(PartoGrama oPartoGrama, Cesarea oCesarea){
            String sQuery = "";            
            if(oCesarea.getCesarea1().getClaveParametro().compareTo("") == 0)
                return sQuery;
            else{
                String tipocesarea = 
                        (oCesarea.getCesarea1().getClaveParametro() == null || 
                        oCesarea.getCesarea1().getClaveParametro().isEmpty()) ? 
                        "null::CHARACTER(5)," : "'" + oCesarea.getCesarea1().getClaveParametro().substring(0,5).toUpperCase() + "'::CHARACTER(5),";
                String otras = 
                        (oCesarea.getOtras() == null || 
                        oCesarea.getOtras().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oCesarea.getOtras().toUpperCase() + "'::CHARACTER VARYING,";
                String indicacion = 
                        (oCesarea.getIndicacionPrincipal() == null || 
                        oCesarea.getIndicacionPrincipal().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oCesarea.getIndicacionPrincipal().toUpperCase() + "'::CHARACTER VARYING,";
                String histerectomia = oCesarea.getPHisteroctomia() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
                String describir = 
                        (oCesarea.getDescribir() == null || 
                        oCesarea.getDescribir().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oCesarea.getDescribir().toUpperCase() + "'::CHARACTER VARYING,";
                String extraccion = 
                        (oCesarea.getExtraccionProducto() == null || 
                        oCesarea.getExtraccionProducto().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oCesarea.getExtraccionProducto().toUpperCase() + "'::CHARACTER VARYING,";
                String complicacion = 
                        (oCesarea.getComplicaciones() == null || 
                        oCesarea.getComplicaciones().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oCesarea.getComplicaciones().toUpperCase() + "'::CHARACTER VARYING,";
                String observaciones = 
                        (oCesarea.getOtrasObservaciones() == null || 
                        oCesarea.getOtrasObservaciones().isEmpty()) ? 
                        "null::TEXT);" : "'" + oCesarea.getOtrasObservaciones().toUpperCase() + "'::TEXT);";
                sQuery = "SELECT * FROM insertActualizaCesarea(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + tipocesarea + otras + indicacion + histerectomia +
                        describir + extraccion + complicacion + observaciones;
            }
            return sQuery;
            
        }
        public String armaQuery(PartoGrama oPartoGrama, Alumbramiento oAlumbramiento){
            String sQuery = "";
            if(oAlumbramiento.getTipoAlumbramiento().getClaveParametro() != null || !oAlumbramiento.getTipoAlumbramiento().getClaveParametro().isEmpty() || 
                    oAlumbramiento.getCausa() != null || !oAlumbramiento.getCausa().isEmpty() ||
                    oAlumbramiento.getMotivo() != null || !oAlumbramiento.getMotivo().isEmpty() ||
                    oAlumbramiento.getObservaciones() != null || !oAlumbramiento.getObservaciones().isEmpty()){
                String tipoalumbramiento = 
                        (oAlumbramiento.getTipoAlumbramiento().getClaveParametro() == null || 
                        oAlumbramiento.getTipoAlumbramiento().getClaveParametro().isEmpty()) ? 
                        "null::CHARACTER(5)," : "'" + oAlumbramiento.getTipoAlumbramiento().getClaveParametro().substring(0, 5).toUpperCase() + "'::CHARACTER(5),";
                String causa = 
                        (oAlumbramiento.getCausa() == null || 
                        oAlumbramiento.getCausa().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oAlumbramiento.getCausa().toUpperCase() + "'::CHARACTER VARYING,";
                String ruterina = oAlumbramiento.getRecCavidadUterina() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
                String motivo = 
                        (oAlumbramiento.getMotivo() == null || 
                        oAlumbramiento.getMotivo().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oAlumbramiento.getMotivo().toUpperCase() + "'::CHARACTER VARYING,";
                String observaciones = 
                        (oAlumbramiento.getObservaciones() == null || 
                        oAlumbramiento.getObservaciones().isEmpty()) ? 
                        "null::TEXT);" : "'" + oAlumbramiento.getObservaciones().toUpperCase() + "'::TEXT);";
                sQuery = "SELECT * FROM insertActualizAlumbramiento(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + tipoalumbramiento + causa + ruterina + motivo + observaciones;
                 return sQuery;             
            }           
            return sQuery;
        }
        public ArrayList<String> armaQuery(PartoGrama oPartoGrama, ArrayList<Producto> oProducto){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String sQuery = "";
            ArrayList<String> arrQuery = null;
            if(oProducto != null || !oProducto.isEmpty()){
                arrQuery = new ArrayList<String>();
                for (Producto oProducto1 : oProducto) {
                    String estadorn = 
                            (oProducto1.getEstadoNacimiento() == null || 
                            oProducto1.getEstadoNacimiento().isEmpty()) ? 
                            "null::CHARACTER(5)," : "'" + oProducto1.getEstadoNacimiento().substring(0, 5).toUpperCase() + "'::CHARACTER(5),";
                    String estado = oProducto1.getEstadoNacimiento().substring(0, 5).compareTo("TAU01") == 0 ? "'N'::CHARACTER," : "'M'::CHARACTER,";
                    String sexo = 
                            (oProducto1.getSexoProducto().getClaveParametro() == null || 
                            oProducto1.getSexoProducto().getClaveParametro().isEmpty()) ? 
                            "null::CHARACTER(5)," : "'" + oProducto1.getSexoProducto().getClaveParametro().substring(0, 5).toUpperCase() + "'::CHARACTER(5),";
                    String apgamin = oProducto1.getApgar1Min() == 0 ? 0 + "::SMALLINT," : oProducto1.getApgar1Min() + "::SMALLINT,";
                    String apgacincomin = oProducto1.getApgar5Min() == 0 ? 0 + "::SMALLINT," : oProducto1.getApgar5Min() + "::SMALLINT,";
                    String peso = oProducto1.getPesoAlNacer() == 0 ? 0 + "::SMALLINT," : oProducto1.getPesoAlNacer() + "::SMALLINT,";
                    String malformaciones = 
                            (oProducto1.getMalformaciones() == null || 
                            oProducto1.getMalformaciones().isEmpty()) ? 
                            "null::CHARACTER VARYING," : "'" + oProducto1.getMalformaciones().toUpperCase() + "'::CHARACTER VARYING,";
                    String kristeller = oProducto1.getKristeller() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
                    String traumatismo = oProducto1.getTraumatismObstetrico() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
                    String reanimacion = oProducto1.getReanimacion() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
                    String observaciones = 
                            (oProducto1.getObservaciones() == null || 
                            oProducto1.getObservaciones().isEmpty()) ? 
                            "null::TEXT," : "'" + oProducto1.getObservaciones().toUpperCase() + "'::TEXT,";
                    String fechanac = 
                            (oProducto1.getFechaNacimiento() == null ) ? 
                            "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + format.format(oProducto1.getFechaNacimiento()) + "'::TIMESTAMP WITHOUT TIME ZONE,";
                    String tarjeta = 
                            oProducto1.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() == 0 ? 
                            "null::INTEGER);" : oProducto1.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER);";
                    String consecutivo = oProducto1.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 ? "null::SMALLINT,'" : oProducto1.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT,'";
                    sQuery = "SELECT * FROM insertaProductoTparto(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                            oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + consecutivo + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                            estadorn + estado + sexo + apgamin + apgacincomin + peso +
                            malformaciones + kristeller + traumatismo + reanimacion + observaciones + fechanac + tarjeta;
                    arrQuery.add(sQuery);
                }                
            }
            return arrQuery;             
        }
        public String armaQuery(PartoGrama oPartoGrama, AnalgesiaTparto oAnalgesia, AnalgesiaTparto oComplemento){
            String sQuery = "";
            if(oAnalgesia.getTipo() != null || !oAnalgesia.getTipo().isEmpty() ||
                    oAnalgesia.getResultado() != null || !oAnalgesia.getResultado().isEmpty() ||
                    oAnalgesia.getComplicaciones() != null || !oAnalgesia.getComplicaciones().isEmpty() ||
                    oComplemento.getTipo() != null || !oComplemento.getTipo().isEmpty()){
                String analgesiaParto = oAnalgesia.getAnalgesiaTparto() ? "'1'::CHARACTER,":"'0'::CHARACTER,";
                String tipo = 
                        (oAnalgesia.getTipo() == null || 
                        oAnalgesia.getTipo().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oAnalgesia.getTipo().toUpperCase() + "'::CHARACTER VARYING,";
                String resultado = 
                        (oAnalgesia.getResultado() == null || 
                        oAnalgesia.getResultado().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oAnalgesia.getResultado().toUpperCase() + "'::CHARACTER VARYING,";
                String complicaciones = 
                        (oAnalgesia.getComplicaciones() == null || 
                        oAnalgesia.getComplicaciones().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oAnalgesia.getComplicaciones().toUpperCase() + "'::CHARACTER VARYING,";
                String analgesiaLegrado = oAnalgesia.getPartoLegrado() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
                String tipol = 
                        (oComplemento.getTipo() == null || 
                        oComplemento.getTipo().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oComplemento.getTipo().toUpperCase() + "'::CHARACTER VARYING,";
                String tarjeta = oAnalgesia.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() == 0 ? "null::INTEGER);" : oAnalgesia.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER);";
                sQuery = "SELECT * FROM insertActualizAnalgesiatParto(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + analgesiaParto + tipo + resultado + complicaciones +
                        analgesiaLegrado + tipol + tarjeta;
                 return sQuery;
            }
            return sQuery;
        }
        public String armaQuery(PartoGrama oPartoGrama, Placenta oPlacenta){
            String sQuery = "";
            if(oPlacenta.getPlacenta() != null || !oPlacenta.getPlacenta().isEmpty() ||
                    oPlacenta.getCordon().getClaveParametro() != null || !oPlacenta.getCordon().getClaveParametro().isEmpty() ||
                    oPlacenta.getDescribir() != null || !oPlacenta.getDescribir().isEmpty() ||
                    oPlacenta.getCordon().getTipoParametro() != null || !oPlacenta.getCordon().getTipoParametro().isEmpty() ||
                    oPlacenta.getDescribir1() != null || !oPlacenta.getDescribir1().isEmpty() ||
                    oPlacenta.getObservaciones() != null || !oPlacenta.getObservaciones().isEmpty() ||
                    oPlacenta.getSangrado() != null || !oPlacenta.getSangrado().isEmpty() ||
                    oPlacenta.getMetodoAnticonceptivo() != null || !oPlacenta.getMetodoAnticonceptivo().isEmpty()){
                String placenta = 
                        (oPlacenta.getPlacenta() == null || 
                        oPlacenta.getPlacenta().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oPlacenta.getPlacenta().toUpperCase() + "'::CHARACTER VARYING,";
                String cordon = 
                        (oPlacenta.getCordon().getClaveParametro() == null || 
                        oPlacenta.getCordon().getClaveParametro().isEmpty()) ? 
                        "null::CHARACTER(5)," : "'" + oPlacenta.getCordon().getClaveParametro().toUpperCase() + "'::CHARACTER(5),";
                String describir = 
                        (oPlacenta.getDescribir() == null || 
                        oPlacenta.getDescribir().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oPlacenta.getDescribir().toUpperCase() + "'::CHARACTER VARYING,";
                String cordon1 = 
                        (oPlacenta.getCordon().getTipoParametro() == null || 
                        oPlacenta.getCordon().getTipoParametro().isEmpty()) ? 
                        "null::CHARACTER(5)," : "'" + oPlacenta.getCordon().getTipoParametro().toUpperCase() + "'::CHARACTER(5),";
                String describir1 = 
                        (oPlacenta.getDescribir1() == null || 
                        oPlacenta.getDescribir1().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oPlacenta.getDescribir1().toUpperCase() + "'::CHARACTER VARYING,";
                String observaciones = 
                        (oPlacenta.getObservaciones() == null || 
                        oPlacenta.getObservaciones().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oPlacenta.getObservaciones().toUpperCase() + "'::CHARACTER VARYING,";
                String sangrado = 
                        (oPlacenta.getSangrado() == null || 
                        oPlacenta.getSangrado().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oPlacenta.getSangrado().toUpperCase() + "'::CHARACTER VARYING,";
                String metodo = 
                        (oPlacenta.getMetodoAnticonceptivo() == null || 
                        oPlacenta.getMetodoAnticonceptivo().isEmpty()) ? 
                        "null::CHARACTER(1));" : "'" + oPlacenta.getMetodoAnticonceptivo().toUpperCase() + "'::CHARACTER VARYING(1));";            
                sQuery = "SELECT * FROM insertActualizaPlacenta(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + placenta + cordon + describir + cordon1 + describir1 +
                        observaciones + sangrado + metodo;
                 return sQuery;
            }
            return sQuery;
        }
        
        public String armaQuery(PartoGrama oPartoGrama, Legrado oLegrado){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat format1 = new SimpleDateFormat("HH:mm:ss");
            String sQuery = "";
            if( oLegrado.getFecha() == null || oLegrado.getHora() == null )
                return sQuery;
            else{
                String fecha = oLegrado.getFecha() == null ? 
                        "null" : format.format(oLegrado.getFecha());
                String hora = oLegrado.getHora() == null ? 
                        "null" : format1.format(oLegrado.getHora());
                String fechaCompleta = 
                        (fecha == null || hora == null || 
                        fecha.isEmpty() || hora.isEmpty()) ? 
                        "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + fecha + " " + hora + "'::TIMESTAMP WITHOUT TIME ZONE,";
                String producto = 
                        (oLegrado.getProductoAborto() == null || 
                        oLegrado.getProductoAborto().isEmpty()) ? 
                        "null::CHARACTER VARYING," : "'" + oLegrado.getProductoAborto().toUpperCase() + "'::CHARACTER VARYING,";
                String tipo = 
                        (oLegrado.getTipo().getClaveParametro() == null || 
                        oLegrado.getTipo().getClaveParametro().isEmpty()) ? 
                        "null::CHARACTER(5)," : "'" + oLegrado.getTipo().getClaveParametro().substring(0, 5).toUpperCase() + "'::CHARACTER(5),";
                String observaciones = 
                        (oLegrado.getObservaciones() == null || 
                        oLegrado.getObservaciones().isEmpty()) ? 
                        "null::TEXT," : "'" + oLegrado.getObservaciones().toUpperCase() + "'::TEXT,";
                String tarjeta = oLegrado.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() == 0 ? 
                        "null::INTEGER);" : oLegrado.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() + "::INTEGER);";
                sQuery = "SELECT * FROM insertActualizaLegrado(" + oPartoGrama.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        oPartoGrama.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," + fechaCompleta + producto + tipo + observaciones + tarjeta;
            }
            return sQuery;
        }
        public int insertaDatosAnverso2(PartoGrama oPartoGrama, PartoEutocico oPartoEutocico,
                Forceps oForceps, Cesarea oCesarea, Alumbramiento oAlumbramiento,
                ArrayList<Producto> oProducto, AnalgesiaTparto oAnalgesiaTparto, AnalgesiaTparto oPartoLegrado,
                Placenta oPlacenta, Legrado oLegrado)throws Exception{
            ArrayList<String> rstQuery = null;
            ArrayList<String> rstTemp = null; 
            int nRet = -1;
            if(oPartoGrama == null)
                throw new Exception("TERMINACIONEMBARAZO.INSERTADATOS:ERROR,FALTAN DATOS");
            else{
                String query1 = armaQuery(oPartoGrama, oPartoEutocico);
                String query2 = armaQuery(oPartoGrama, oForceps);
                String query3 = armaQuery(oPartoGrama, oCesarea);
                String query4 = armaQuery(oPartoGrama, oAlumbramiento);
                String query5 = armaQuery(oPartoGrama, oAnalgesiaTparto, oPartoLegrado);
                String query6 = armaQuery(oPartoGrama, oPlacenta);
                String query7 = armaQuery(oPartoGrama, oLegrado);
                rstQuery = new ArrayList<String>();
                rstTemp = new ArrayList<String>();
                rstQuery.add(armaQuery(oPartoGrama));
                if(query1.compareTo("") != 0)
                    rstQuery.add(query1);
                if(query2.compareTo("") != 0)
                    rstQuery.add(query2);
                if(query3.compareTo("") != 0)
                    rstQuery.add(query3);
                if(query4.compareTo("") != 0)
                    rstQuery.add(query4);
                rstTemp = armaQuery(oPartoGrama, oProducto);
                if(rstTemp != null || !rstTemp.isEmpty())
                    for (String rstTemp1 : rstTemp) {
                        rstQuery.add(rstTemp1);
                    }
                if(query5.compareTo("") != 0)
                    rstQuery.add(query5);
                if(query6.compareTo("") != 0)
                    rstQuery.add(query6);
                if(query7.compareTo("") != 0)
                    rstQuery.add(query7);
                if(rstQuery.size() > 0){
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){
                        nRet = oAD.ejecutarConsultaComando(rstQuery);
                        oAD.desconectar();
                    }
                }
                /*for (int l = 0; l < rstQuery.size(); l++)
                    System.out.println(rstQuery.get(l));*/
            }
            return nRet;
        }
        public void detalleLegrado()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION:FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM buscadetallelegradopartograma("+ this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,"+
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() +"::SMALLINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() +"::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setFecha((Date)vRowTemp.get(0));
                this.setProductoAborto((String)vRowTemp.get(1).toString());
                this.getTipo().setValor((String)vRowTemp.get(2).toString());
                this.setObservaciones((String)vRowTemp.get(3).toString());
                String valor1 = (String)vRowTemp.get(5).toString() + " " + (String)vRowTemp.get(6).toString() + " " + (String)vRowTemp.get(7).toString();
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(4).toString());
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres(valor1);
                String valor = (String)vRowTemp.get(8).toString() + "" + (String)vRowTemp.get(9).toString();
                this.getTipo().setClaveParametro(valor);
            }
            else{
                this.setFecha(null);
                this.setProductoAborto("");
                this.getTipo().setValor("");
                this.setObservaciones("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApPaterno("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApMaterno("");
            }
        }
	public Date getFecha() {
	return dFecha;
	}

	public void setFecha(Date valor) {
	dFecha=valor;
	}

	public Date getHora() {
	return dHora;
	}

	public void setHora(Date valor) {
	dHora=valor;
	}

	public Parametrizacion getTipo() {
	return oTipo;
	}

	public void setTipo(Parametrizacion valor) {
	oTipo=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public String getProductoAborto() {
	return sProductoAborto;
	}

	public void setProductoAborto(String valor) {
	sProductoAborto=valor;
	}
        public TerminacionEmbarazo getTerminacionEmbarazo(){
            return oTerminacionEmbarazo;
        }
        public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
            this.oTerminacionEmbarazo = oTerminacionEmbarazo;
        }
        public String getValorTipo(){
            if(this.getTipo().getValor().isEmpty())
                return "CON DILATACIÓN( ) SIN DILATACIÓN( ) INSTRUMENTAL( ) DIGITAL( )";
            if(this.getTipo().getValor().compareTo("CON DILATACIÓN") == 0)
                return "CON DILATACIÓN(X) SIN DILATACIÓN( ) INSTRUMENTAL( ) DIGITAL( )";
            if(this.getTipo().getValor().compareTo("SIN DILATACIÓN") == 0)
                return "CON DILATACIÓN( ) SIN DILATACIÓN(X) INSTRUMENTAL( ) DIGITAL( )";
            if(this.getTipo().getValor().compareTo("INSTRUMENTAL") == 0)
                return "CON DILATACIÓN( ) SIN DILATACIÓN( ) INSTRUMENTAL(X) DIGITAL( )";
            if(this.getTipo().getValor().compareTo("DIGITAL") == 0)
                return "CON DILATACIÓN( ) SIN DILATACIÓN( ) INSTRUMENTAL( ) DIGITAL(X)";
            return null;
        }
} 
