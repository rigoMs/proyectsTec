package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.urgencias.HojaLesiones;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.almacen.ValeMaterial;
import mx.gob.hrrb.modelo.capasits.NotaMedica;
import mx.gob.hrrb.modelo.serv.EstudioEspLab;
import mx.gob.hrrb.modelo.serv.EstudioInterconsulta;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.modelo.serv.SolicitudOtroServicio;
import mx.gob.hrrb.modelo.serv.SolicitudSangre;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class EpisodioMedico implements Serializable {

    private AccesoDatos oAD;
    private String sAvisoMinisterioDefun;
    private char bDocEntregada;
    private boolean bPrimeraVezEsp;
    private boolean bPrimeraVezHRRB;
    private Date dAltaFisica;
    private Date dAltaHospitalaria;
    private Date dFechaIngreso;
    private int nDiasEstancia;
    private Cama oCama;
    private Parametrizacion oDestino;
    private DiagnosticoCIE10 oDiagIngreso;
    private Parametrizacion oEdoSalud;
    private Medico oMedicoRecibe;
    private Medico oMedicoTratante;
    private Parametrizacion oMotivoEgreso;
    private String sAreaServicioHRRB;
    private int sSubServicioHRRB;
    private String sRazonAltaVolunTrasl;
    private String sRazonDestino;
    private HojaLesiones oHojaLesiones;
    private FamiliarCercano oFamiliarCercano;
    private Paciente oPaciente;
    private ArrayList arrSignosVitales;
    private String sMotivoEgresoP;
    private String sEdoSaludStr;
    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private AreaServicioHRRB oArea;
    private Expediente oExpediente;
    private String dFechaIngresoP;
    private String diasEstanciaSTR;
    private int sAreaServicioHRRBSTR;
    private String sAreaServicioHRRBCad;
    private String sDestinoSTR;
    private String sSubServicioHRRBSTR;
    private long nClaveEpisodio;
    private AfeccionTratada oAfePrincipal;
    private AfeccionTratada oAfePrimera;
    private AfeccionTratada oAfeSegunda;
    private AfeccionTratada oAfeTercera;
    private AfeccionTratada oAfeCuarta;
    private AfeccionTratada oAfeQuinta;
    private AfeccionTratada oAfeSexta;
    private AfeccionTratada oAfeResAP;
    private AfeccionTratada oAfeResAPDef;
    private ProcedimientosRealizados oProceRe1;
    private ProcedimientosRealizados oProceRe2;
    private ProcedimientosRealizados oProceRe3;
    private ProcedimientosRealizados oProceRe4;
    private ProcedimientosRealizados oProceRe5;
    private ProcedimientosRealizados oProceRe6;
    private ProcedimientosRealizados oProceRe7;
    private ProcedimientosRealizados oProceRe8;
    private MedicamentoAplicado oMedicamento1;
    private MedicamentoAplicado oMedicamento2;
    private MedicamentoAplicado oMedicamento3;
    private int contador = 0;
    private int contadorDef = 0;
    private SignosVitales oSignosVitales;
    private String sFIngreso;
    private char sTipoAfePrinc;
    private char sInfeccionIntrahospitalaria;   
    private ServicioRealizado[] arrServiciosRealizados; 
    private AdmisionUrgs oAdmisionUrgs;    
    
    
    /*Agregados en fase II*/
    private Programa oProgActual;
    private ArrayList<EstudioRealizado> arrEstudiosRealizados;
    private ArrayList<SolicitudOtroServicio> arrSolicitudesOtrosServicios;
    private ArrayList<ProcedimientosRealizados> arrProcsRealizados;
    private ArrayList<ValeMaterial> arrValesMaterial;
    private ArrayList<DetalleValeColectivo> arrDetallesValesColectivos;
    private ArrayList<Receta> arrRecetas;
    private ArrayList<DetalleRecetarioColectivo> arrDetallesRecetariosColectivos;
    private ArrayList<NotaMedicaHRRB> arrNotasMedicasHRRB;
    private ArrayList<SolicitudSangre> arrSolicitudesSangre;
    private ArrayList<MedicamentoAplicado> arrMedicamentosAplicados;
    ////////////////////////////////////////////////////////////////////////////
    private NotaMedica oNota;
    private AdmisionUrgs oAdmUrgs;
    private Cita oCita; //Sólo para facilitar consultas, en realidad es un arreglo de citas
    private EstudioInterconsulta oEstInter; //mismo caso de citas
    private NotaMedicaHRRB oNotaMedHrrb;//mismo caso de citas
    //////////////////////////////////////////////////////////////////////////// 
    private Programa oPrograma;
    
    
    ////////////////Reportes
    private EstudioEspLab oEstudioEspLab;
    private ValeMaterial oValeMaterial;
    
    public EpisodioMedico() {
        bPrimeraVezHRRB = true;
        bPrimeraVezEsp = true;
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
        oDiagIngreso = new DiagnosticoCIE10();
        oArea = new AreaServicioHRRB();
        oExpediente = new Expediente();
        oPaciente = new Paciente();
        oCama = new Cama();
        oSignosVitales = new SignosVitales();
        oAfePrincipal = new AfeccionTratada();
        oAfePrimera = new AfeccionTratada();
        oAfeSegunda = new AfeccionTratada();
        oAfeTercera = new AfeccionTratada();
        oAfeCuarta = new AfeccionTratada();
        oAfeQuinta = new AfeccionTratada();
        oAfeSexta = new AfeccionTratada();
        oAfeResAP = new AfeccionTratada();
        oAfeResAPDef = new AfeccionTratada();
        oProceRe1 = new ProcedimientosRealizados();
        oProceRe2 = new ProcedimientosRealizados();
        oProceRe3 = new ProcedimientosRealizados();
        oProceRe4 = new ProcedimientosRealizados();
        oProceRe5 = new ProcedimientosRealizados();
        oProceRe6 = new ProcedimientosRealizados();
        oProceRe7 = new ProcedimientosRealizados();
        oProceRe8 = new ProcedimientosRealizados();
        oProceRe1.setCIE9(new ProcedimientoCIE9());
        oProceRe2.setCIE9(new ProcedimientoCIE9());
        oProceRe3.setCIE9(new ProcedimientoCIE9());
        oProceRe4.setCIE9(new ProcedimientoCIE9());
        oProceRe5.setCIE9(new ProcedimientoCIE9());
        oProceRe6.setCIE9(new ProcedimientoCIE9());
        oProceRe7.setCIE9(new ProcedimientoCIE9());
        oProceRe8.setCIE9(new ProcedimientoCIE9());
        oProceRe1.setCirujano(new Medico());
        oProceRe2.setCirujano(new Medico());
        oProceRe3.setCirujano(new Medico());
        oProceRe4.setCirujano(new Medico());
        oProceRe5.setCirujano(new Medico());
        oProceRe6.setCirujano(new Medico());
        oProceRe7.setCirujano(new Medico());
        oProceRe8.setCirujano(new Medico());
        oMedicamento1 = new MedicamentoAplicado();
        oMedicamento2 = new MedicamentoAplicado();
        oMedicamento3 = new MedicamentoAplicado();
        sEdoSaludStr = "";
        diasEstanciaSTR = "";
        oFamiliarCercano = new FamiliarCercano();
        oMedicoRecibe = new Medico();
        oMedicoTratante = new Medico();
        oHojaLesiones = new HojaLesiones();
        oNota = new NotaMedica();
        //oNotaMedHrrb = new NotaMedicaHRRB();
    }

    public void inicializaProcedimientos(){
        this.getProceRe1().inicializar();
        this.getProceRe2().inicializar();
        this.getProceRe3().inicializar();
        this.getProceRe4().inicializar();
        this.getProceRe5().inicializar();
        this.getProceRe6().inicializar();
        this.getProceRe7().inicializar();
        this.getProceRe8().inicializar();
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaLlaveEpisodioMedico();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }

    //*********************************************************************
    public int buscarTipoEpisodio(String[] datos, Date fCita, String tipo) throws Exception {
        int bRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscacanttipocita(" + datos[0] + ", " + datos[1] + "::smallint, '" + ft.format(fCita) + "%', '" + tipo + "', '" + datos[4].substring(0, 3) + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = ((Double) vRowTemp.get(0)).intValue();
            }
        }
        return bRet;
    }
        //*********************************************************************
    //Busca si un paciente es de primvez o subsecuente para cierta especialidad
    public int buscaTipoEPis(int datos, long folio, int opcion) throws Exception {
        int bRet = 0;
        ArrayList rst = null;
        String sQuery = "";

        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaTipoEP(" + datos + "::smallint, " + folio + ", " + opcion + "::smallint);";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = 5;
            }
        }
        return bRet;
    }
    //Busca episodio medico de un paciente de capasits mediante su folio
    public long buscaEpisodioPacienteCapa(long folio) throws Exception {
        long nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaEpisodioMedicoCapasits(" + folio + ");";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                nRet = ((Double) vRowTemp.get(0)).intValue();
            }
        }
        return nRet;
    }

    //*********************************************************************
    public EpisodioMedico[] buscarTodos() throws Exception {
        EpisodioMedico arrRet[] = null, oEpisodioMedico = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosEpisodioMedico();"; 
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new EpisodioMedico[rst.size()];
            for (i = 0; i < rst.size(); i++) {
            }
        }
        return arrRet;
    }

    
    
    public int insertar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaEpisodioMedico();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) rst.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    //***************************************************************
//**************************episodio medico consulta externa*********
    //***************************************************************
    public int insertarEpisodioCE(String ingreso, String[] tarjeta, String diag, int exp, long folio) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        String primesp = "", primhos = "";
        if (getPrimeraVezHRRB() == true) {
            primhos = "S";
        } else {
            primhos = "N";
        }
        if (getPrimeraVezEsp() == true) {
            primesp = "S";
        } else {
            primesp = "N";
        }
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaReg = null;
        try {
            fechaReg = formatoDelTexto.parse(ingreso);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaEpisodioMedicoCE('" + sUsuario + "', '" + fechaReg + "'::timestamp, " + tarjeta[0] + ", '" + diag + "', " + tarjeta[1] + "::smallint, '" + primhos + "', '" + primesp + "', " + exp + ", " + folio + "::bigint);";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    //*************************************************************************
    public int modificarEPReserva(String ingreso, String[] tarjeta, String diag, int exp, long folio, int episodio) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        String primesp = "", primhos = "";
        if (getPrimeraVezHRRB() == true) {
            primhos = "S";
        } else {
            primhos = "N";
        }
        if (getPrimeraVezEsp() == true) {
            primesp = "S";
        } else {
            primesp = "N";
        }
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaReg = null;
        try {
            fechaReg = formatoDelTexto.parse(ingreso);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM ModificaEpisodioMedicoCE('" + sUsuario + "', '" + fechaReg + "'::timestamp, " + tarjeta[0] + ", '" + diag + "', " + tarjeta[1] + "::smallint, '" + primhos + "', '" + primesp + "', " + exp + ", " + folio + "::bigint, " + episodio + ");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    //*****************************************************************************+
    
////////////////////////////////////////////////////////////////////////////////
    public EpisodioMedico[] buscarDatosPacienteAtencionMedica() throws Exception {
    EpisodioMedico arrRet[]=null, oEpiMed = null;
    ArrayList rst = null;
    ArrayList<EpisodioMedico> vObj = null;        
    String sQuery = "";
    String edad="";
    int i=0, nTam=0;
    
    if(this.getPaciente().getNombres().trim() != null && 
            this.getPaciente().getApPaterno().trim() != null && 
            getExpediente().getNumero() == 0 ){
        sQuery="SELECT * FROM buscadatospacienteatencionmedica('" + 
                this.getPaciente().getNombres().toUpperCase() + "'::character varying,'"
              + this.getPaciente().getApPaterno().toUpperCase()+ "'::character varying,'"
              + this.getPaciente().getApMaterno().toUpperCase()+ "'::character varying,0);";
    }else if(getExpediente().getNumero() != 0){
       sQuery="SELECT * FROM buscadatospacienteatencionmedica(''::character varying,''::character varying,''::character varying,"+getExpediente()+");";
    }
    System.out.println(sQuery);
    oAD = new AccesoDatos();
    if (oAD.conectar()) {
        rst = oAD.ejecutarConsulta(sQuery);
        oAD.desconectar();
    }
    if (rst != null) {
        vObj = new ArrayList<EpisodioMedico>();
        for(i=0; i < rst.size(); i++){
            ArrayList vRowTemp = (ArrayList) rst.get(i);
            oEpiMed = new EpisodioMedico();
            oEpiMed.setPaciente(new Paciente());
            oEpiMed.setAdmUrgs(new AdmisionUrgs());
            oEpiMed.setCita(new Cita());
            oEpiMed.setProceRe1(new ProcedimientosRealizados());
            oEpiMed.getProceRe1().setCIE9(new ProcedimientoCIE9());
            oEpiMed.getExpediente().setAntecGinecoObstetricos(new AntecGinecoObstetricos());
            oEpiMed.getExpediente().setAntecNoPatologicos(new AntecNoPatologicos());
            oEpiMed.getExpediente().setAntecPatologicos(new AntecPatologicos());
            oEpiMed.oPaciente.setNombres((String) vRowTemp.get(0));
            oEpiMed.oPaciente.setApPaterno((String) vRowTemp.get(1));
            oEpiMed.oPaciente.setApMaterno((String) vRowTemp.get(2));
            oEpiMed.oPaciente.setEdadNumero((String) vRowTemp.get(3));
            edad=(String)vRowTemp.get(3);
                if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                        if (edad.charAt(0)=='0')
                            oEpiMed.oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                            oEpiMed.oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                            oEpiMed.oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                            oEpiMed.oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                }
            String sSex = ((String)vRowTemp.get(4));
            oEpiMed.oPaciente.sSexoP = sSex.equals("M") ? "MASCULINO" : "FEMENINO";
            oEpiMed.oPaciente.setFechaNac((Date) vRowTemp.get(5));
            oEpiMed.oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(6)).intValue());
            oEpiMed.oPaciente.getSeg().setNumero((String) vRowTemp.get(7));
            oEpiMed.oPaciente.setFolioPaciente(((Double) vRowTemp.get(8)).intValue());
            oEpiMed.oPaciente.setCurp((String) vRowTemp.get(9));
            oEpiMed.setAreaServicioHRRB((String) vRowTemp.get(10));
            oEpiMed.oCama.setNumero((String) vRowTemp.get(11));
            
            oEpiMed.oPaciente.setPeso(((Double) vRowTemp.get(16)).intValue());
            oEpiMed.oPaciente.setTalla(((Double) vRowTemp.get(17)).intValue());
            oEpiMed.getExpediente().getAntecPatologicos().setAlergias((String) vRowTemp.get(18));
            oEpiMed.oDiagIngreso.setDescripcionDiag((String) vRowTemp.get(19));
            oEpiMed.oDiagIngreso.setClave(((String) vRowTemp.get(20)).trim());
            oEpiMed.oPaciente.setCalleNum((String) vRowTemp.get(21));
            oEpiMed.oPaciente.setCp((String) vRowTemp.get(22));
            oEpiMed.oPaciente.getCiudad().setClaveCiu((String) vRowTemp.get(23));
            oEpiMed.oPaciente.getEstado().setClaveEdo((String) vRowTemp.get(24));                        
            oEpiMed.oPaciente.getMunicipio().setClaveMun((String) vRowTemp.get(25));
            oEpiMed.oPaciente.setColonia((String) vRowTemp.get(26));
            oEpiMed.oPaciente.getMunicipio().buscar(oPaciente.getEstado().getClaveEdo());
            oEpiMed.oPaciente.getEstado().buscar();
            oEpiMed.oPaciente.getCiudad().buscar(oEpiMed.oPaciente.getEstado().getClaveEdo(), oEpiMed.oPaciente.getMunicipio().getClaveMun());
            oEpiMed.setFechaIngreso((Date) vRowTemp.get(27)); 
            oEpiMed.oPaciente.setTelefono((String) vRowTemp.get(28));
            oEpiMed.oPaciente.getSeg().setVigencia((Date) vRowTemp.get(29));
            oEpiMed.oPaciente.setOtroPais((String) vRowTemp.get(30));
            if(oEpiMed.oPaciente.getOtroPais().compareToIgnoreCase("")==0)
                oEpiMed.oPaciente.setOtroPais("MÉXICO");
            oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(31)).intValue());
            oEpiMed.oArea.setClave(((Double) vRowTemp.get(32)).intValue());
            oEpiMed.oArea.buscarNombreArea(oEpiMed.oArea.getClave());
            oEpiMed.oAdmUrgs.setFolioAdmision(((Double) vRowTemp.get(33)).intValue());
            String sTabaq = ((String)vRowTemp.get(34));
            oEpiMed.getExpediente().getAntecNoPatologicos().setTabaquismo(sTabaq.equals("S"));
            oEpiMed.getExpediente().getAntecPatologicos().setCardioPatias((String) vRowTemp.get(35));
            oEpiMed.getExpediente().getAntecPatologicos().setQuirurgico((String) vRowTemp.get(36));
            oEpiMed.getExpediente().getAntecPatologicos().setTransfusion((String) vRowTemp.get(37));
            oEpiMed.getExpediente().getAntecPatologicos().setDiabetico((String) vRowTemp.get(38));
            oEpiMed.getExpediente().getAntecPatologicos().setCardioVasculares((String) vRowTemp.get(39));
            oEpiMed.getExpediente().getAntecPatologicos().setTraumat((String) vRowTemp.get(40));
            oEpiMed.getExpediente().getAntecPatologicos().setHTA((String) vRowTemp.get(41));
            oEpiMed.getExpediente().getAntecNoPatologicos().setReligion((String) vRowTemp.get(42));
            oEpiMed.getExpediente().getAntecNoPatologicos().setEscolaridad((String) vRowTemp.get(43));
            oEpiMed.getExpediente().getAntecNoPatologicos().setOcupacion((String) vRowTemp.get(44));
            String sAlc = ((String)vRowTemp.get(45));
            oEpiMed.getExpediente().getAntecNoPatologicos().setAlcoholismo(sAlc.equals("S"));
            oEpiMed.getExpediente().getAntecNoPatologicos().setDrogas((String) vRowTemp.get(46));
            String sAguaPot = ((String)vRowTemp.get(47));
            oEpiMed.getExpediente().getAntecNoPatologicos().setAguaPotable(sAguaPot.equals("S"));
            String sElectr = ((String)vRowTemp.get(48));
            oEpiMed.getExpediente().getAntecNoPatologicos().setElectricidad(sElectr.equals("S"));
            String sDren = ((String)vRowTemp.get(49));
            oEpiMed.getExpediente().getAntecNoPatologicos().setDrenaje(sDren.equals("S"));
            oEpiMed.getExpediente().getAntecNoPatologicos().setAnimales((String) vRowTemp.get(50));
            String sCasaH = ((String)vRowTemp.get(51));
            System.out.println("sCasaH"+sCasaH);
            if (sCasaH != null && !sCasaH.equals(""))
                oEpiMed.getExpediente().getAntecNoPatologicos().setTipoCasaHab(sCasaH.charAt(0));
            String sServS = ((String)vRowTemp.get(52));
            oEpiMed.getExpediente().getAntecNoPatologicos().setServSanit(sServS.equals("S"));
            oEpiMed.getExpediente().getAntecNoPatologicos().setOtro((String) vRowTemp.get(53));
            oEpiMed.getExpediente().getAntecGinecoObstetricos().setIVSA(((Double) vRowTemp.get(54)).intValue());
            oEpiMed.getArea().setTipo((String) vRowTemp.get(55));
            System.out.println("FOLIO PACIENTE: "+oEpiMed.getPaciente().getFolioPaciente()); 
            System.out.println("EPISODIO MEDICO: "+oEpiMed.getClaveEpisodio());
            System.out.println(oEpiMed.getArea().getTipo());

            oEpiMed.oPaciente=oEpiMed.getPaciente();
            vObj.add(oEpiMed);
        }
        nTam = vObj.size();
        arrRet=new EpisodioMedico[nTam];
        for (i=0; i<nTam; i++){
            arrRet[i] = vObj.get(i);
        }
    }
    return arrRet;
    }

    public EpisodioMedico[] buscarPacientesConsulta(Date dFecAux, Medico noConsul, Medico noTar, Medico CveTur) throws Exception{
    EpisodioMedico arrRet[]=null, oEpiMed=null;
    ArrayList rst = null;
    ArrayList<EpisodioMedico> vObj=null;
    String sQuery = "";
    String edad="";
    int i=0, nTam=0, con=0;
    int noConsultorio = noConsul.getCons().getNoConsultorio();
    int noTarjeta = noTar.getNoTarjeta();
    String cveTurno = CveTur.getAsigCon().getTurno().getClave();
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
             if( this==null){
                    throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
            }else{  
                        sQuery = "SELECT * FROM buscadatospacienteatencionmedicaconsulta('"+df.format(dFecAux)+"%',"
                                +noConsultorio+"::smallint, "+noTarjeta+", '"+cveTurno+"');";
                        System.out.println(sQuery);
                    oAD=new AccesoDatos();

                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                        vObj = new ArrayList<EpisodioMedico>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEpiMed=new EpisodioMedico();
                            oEpiMed.setPaciente(new Paciente());
                            oEpiMed.setAdmUrgs(new AdmisionUrgs());
                            oEpiMed.setCita(new Cita());
                            oEpiMed.setProceRe1(new ProcedimientosRealizados());
                            oEpiMed.getProceRe1().setCIE9(new ProcedimientoCIE9());
                            oEpiMed.getExpediente().setAntecGinecoObstetricos(new AntecGinecoObstetricos());
                            oEpiMed.getPaciente().setNombres((String) vRowTemp.get(0));
                            oEpiMed.getPaciente().setApPaterno((String) vRowTemp.get(1));
                            oEpiMed.getPaciente().setApMaterno((String) vRowTemp.get(2));
                            oEpiMed.getPaciente().setEdadNumero((String) vRowTemp.get(3));
                            edad=(String)vRowTemp.get(3);
                                if (edad.compareTo("")!=0){
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0')
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0)
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                            String sSex = ((String)vRowTemp.get(4));
                            oEpiMed.oPaciente.sSexoP = sSex.equals("M") ? "MASCULINO" : "FEMENINO";
                            oEpiMed.getPaciente().setFechaNac((Date) vRowTemp.get(5));
                            oEpiMed.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(6)).intValue());
                            oEpiMed.getPaciente().getSeg().setNumero((String) vRowTemp.get(7));
                            oEpiMed.getPaciente().setFolioPaciente(((Double) vRowTemp.get(8)).intValue());
                            oEpiMed.getPaciente().setCurp((String) vRowTemp.get(9));
                            oEpiMed.setAreaServicioHRRB((String) vRowTemp.get(10));
                            oEpiMed.getCama().setNumero((String) vRowTemp.get(11));

                            oEpiMed.getPaciente().setPeso(((Double) vRowTemp.get(16)).intValue());
                            oEpiMed.getPaciente().setTalla(((Double) vRowTemp.get(17)).intValue());
                            oEpiMed.getExpediente().getAntecPatologicos().setAlergias((String) vRowTemp.get(18));
                            oEpiMed.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(19));
                            oEpiMed.getDiagIngreso().setClave(((String) vRowTemp.get(20)).trim());
                            oEpiMed.getPaciente().setCalleNum((String) vRowTemp.get(21));
                            oEpiMed.getPaciente().setCp((String) vRowTemp.get(22));
                            oEpiMed.getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get(23));
                            oEpiMed.getPaciente().getEstado().setClaveEdo((String) vRowTemp.get(24));                        
                            oEpiMed.getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get(25));
                            oEpiMed.getPaciente().setColonia((String) vRowTemp.get(26));
                            oEpiMed.getPaciente().getMunicipio().buscar(oEpiMed.getPaciente().getEstado().getClaveEdo());
                            oEpiMed.getPaciente().getEstado().buscar();
                            oEpiMed.getPaciente().getCiudad().buscar(oEpiMed.getPaciente().getEstado().getClaveEdo(), oEpiMed.getPaciente().getMunicipio().getClaveMun());
                            oEpiMed.setFechaIngreso((Date) vRowTemp.get(27)); 
                            oEpiMed.getPaciente().setTelefono((String) vRowTemp.get(28));
                            oEpiMed.getPaciente().getSeg().setVigencia((Date) vRowTemp.get(29));
                            oEpiMed.getPaciente().setOtroPais((String) vRowTemp.get(30));
                            if(oEpiMed.getPaciente().getOtroPais().compareToIgnoreCase("")==0)
                               oEpiMed.getPaciente().setOtroPais("MÉXICO");
                            oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(31)).intValue());
                            oEpiMed.getArea().setClave(((Double) vRowTemp.get(32)).intValue());
                            oEpiMed.oArea.buscarNombreArea(oEpiMed.oArea.getClave());
                            oEpiMed.getCita().setNoFicha(((Double) vRowTemp.get(33)).intValue());
                            oEpiMed.getCita().setFolioCita(((Double) vRowTemp.get(34)).intValue());
                            oEpiMed.getPaciente().getTipoSol().setValor(oEpiMed.getPaciente().getTipoSol().buscaValorParametrizado((String) vRowTemp.get(35)));
                            String sTabaq = ((String)vRowTemp.get(36));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setTabaquismo(sTabaq.equals("S"));
                            oEpiMed.getAdmUrgs().setFolioAdmision(((Double) vRowTemp.get(37)).intValue());
                            oEpiMed.getExpediente().getAntecPatologicos().setCardioPatias((String) vRowTemp.get(38));
                            oEpiMed.getExpediente().getAntecPatologicos().setQuirurgico((String) vRowTemp.get(39));
                            oEpiMed.getExpediente().getAntecPatologicos().setTransfusion((String) vRowTemp.get(40));
                            oEpiMed.getExpediente().getAntecPatologicos().setDiabetico((String) vRowTemp.get(41));
                            oEpiMed.getExpediente().getAntecPatologicos().setCardioVasculares((String) vRowTemp.get(42));
                            oEpiMed.getExpediente().getAntecPatologicos().setTraumat((String) vRowTemp.get(43));
                            oEpiMed.getExpediente().getAntecPatologicos().setHTA((String) vRowTemp.get(44));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setReligion((String) vRowTemp.get(45));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setEscolaridad((String) vRowTemp.get(46));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setOcupacion((String) vRowTemp.get(47));
                            String sAlc = ((String)vRowTemp.get(48));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setAlcoholismo(sAlc.equals("S"));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setDrogas((String) vRowTemp.get(49));
                            String sAguaPot = ((String)vRowTemp.get(50));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setAguaPotable(sAguaPot.equals("S"));
                            String sElectr = ((String)vRowTemp.get(51));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setElectricidad(sElectr.equals("S"));
                            String sDren = ((String)vRowTemp.get(52));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setDrenaje(sDren.equals("S"));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setAnimales((String) vRowTemp.get(53));
                            String sCasaH = ((String)vRowTemp.get(54));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setTipoCasaHab(sCasaH.length() == -1 ? 'N':'S');
                            String sServS = ((String)vRowTemp.get(55));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setServSanit(sServS.equals("S"));
                            oEpiMed.getExpediente().getAntecNoPatologicos().setOtro((String) vRowTemp.get(56));
                            oEpiMed.getExpediente().getAntecGinecoObstetricos().setIVSA(((Double) vRowTemp.get(57)).intValue());
                            oEpiMed.getCita().setFechaAux(dFecAux);
                            oEpiMed.getCita().getPaciente().setFolioPaciente(((Double) vRowTemp.get(8)).intValue());
                            oEpiMed.getCita().getAreaServicio().setClave(((Double) vRowTemp.get(32)).intValue());
                            oEpiMed.getArea().setTipo((String)vRowTemp.get(58));
                            oEpiMed.getCita().buscaCitaEP();
                            con++;
                            oEpiMed.getCita().setNoConsulta(con);
                            oEpiMed.setProceRe1(new ProcedimientosRealizados());
                            oEpiMed.getProceRe1().setCIE9(new ProcedimientoCIE9());
                            vObj.add(oEpiMed);                              
                        }
                        nTam = vObj.size();
                        arrRet = new EpisodioMedico[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
    }

    public EpisodioMedico[] buscarPacientesInterconsulta(Date dFecAux, int AreaServ) throws Exception{
    EpisodioMedico arrRet[]=null, oEpiMed=null;
    ArrayList rst = null;
    ArrayList<EpisodioMedico> vObj=null;
    String sQuery = "";
    String edad="";
    int i=0, nTam=0, con=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
             if( this==null){
                    throw new Exception("EpisodioMedico.buscar: error de programación, faltan datos");
            }else{  
                        sQuery = "SELECT * FROM buscadatospacienteinterconsulta('"+df.format(dFecAux)+"'::timestamp without time zone,"+AreaServ+"::smallint);";
                        System.out.println(sQuery);
                    oAD=new AccesoDatos();

                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                        vObj = new ArrayList<EpisodioMedico>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEpiMed=new EpisodioMedico();
                            oEpiMed.setPaciente(new Paciente());
                            oEpiMed.getPaciente().setNombres((String) vRowTemp.get(0));
                            oEpiMed.getPaciente().setApPaterno((String) vRowTemp.get(1));
                            oEpiMed.getPaciente().setApMaterno((String) vRowTemp.get(2));
                            oEpiMed.getPaciente().setEdadNumero((String) vRowTemp.get(3));
                            edad=(String)vRowTemp.get(3);
                                if (edad.compareTo("")!=0){
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0')
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0)
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                            String sSex = ((String)vRowTemp.get(4));
                            oEpiMed.oPaciente.sSexoP = sSex.equals("M") ? "MASCULINO" : "FEMENINO";
                            oEpiMed.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(5)).intValue());
                            oEpiMed.getPaciente().getSeg().setNumero((String) vRowTemp.get(6));
                            oEpiMed.getPaciente().setFolioPaciente(((Double) vRowTemp.get(7)).intValue());
                            oEpiMed.setAreaServicioHRRB((String) vRowTemp.get(8));
                            oEpiMed.getCama().setNumero((String) vRowTemp.get(9));
                            oEpiMed.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(10));
                            oEpiMed.setFechaIngreso((Date) vRowTemp.get(11));
                            oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(12)).intValue());
                            oEpiMed.getEstInter().setMotivo((String) vRowTemp.get(13));
                            oEpiMed.getMedicoTratante().setNombres((String) vRowTemp.get(14));
                            oEpiMed.getMedicoTratante().setApPaterno((String) vRowTemp.get(15));
                            oEpiMed.getMedicoTratante().setApMaterno((String) vRowTemp.get(16));
                            oEpiMed.getMedicoTratante().setCedProf((String) vRowTemp.get(17));
                            oEpiMed.getEstInter().setFechaSolicitud((Date) vRowTemp.get(18));
                            oEpiMed.getEstInter().getSituacion().setValor((String) vRowTemp.get(19));
                            oEpiMed.getEstInter().setIdentificador(((Double) vRowTemp.get(20)).intValue());
                            oEpiMed.oArea.setClave(((Double) vRowTemp.get(21)).intValue());
                            oEpiMed.oArea.buscarNombreArea(oEpiMed.oArea.getClave());

                            vObj.add(oEpiMed);                              
                        }
                        nTam = vObj.size();
                        arrRet = new EpisodioMedico[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
    }

    public EpisodioMedico[] buscarPacientesColectivosHosp(Date dFechaActual, int AreaServ) throws Exception{
    EpisodioMedico arrRet[]=null, oEpiMed=null;
    ArrayList rst = null;
    ArrayList<EpisodioMedico> vObj=null;
    String sQuery = "";
    String edad="";
    int i=0, nTam=0, con=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
             if( this==null){
                    throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
            }else{  
                        sQuery = "SELECT * FROM buscadatospacienteareahospitalizacion('"+df.format(dFechaActual)+"'::timestamp without time zone,"+AreaServ+"::smallint);";
                        System.out.println(sQuery);
                    oAD=new AccesoDatos();

                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                        vObj = new ArrayList<EpisodioMedico>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEpiMed=new EpisodioMedico();
                            oEpiMed.setPaciente(new Paciente());
                            oEpiMed.getPaciente().setNombres((String) vRowTemp.get(0));
                            oEpiMed.getPaciente().setApPaterno((String) vRowTemp.get(1));
                            oEpiMed.getPaciente().setApMaterno((String) vRowTemp.get(2));
                            oEpiMed.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                            oEpiMed.getPaciente().getSeg().setNumero((String) vRowTemp.get(4));
                            oEpiMed.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).intValue());
                            oEpiMed.setAreaServicioHRRB((String) vRowTemp.get(6));
                            oEpiMed.setFechaIngreso((Date) vRowTemp.get(7));
                            oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(8)).intValue());
                            oEpiMed.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                            edad=(String)vRowTemp.get(9);
                                if (edad.compareTo("")!=0){
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0')
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0)
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                            oEpiMed.getCama().setNumero((String) vRowTemp.get(10));
                            String sSex = ((String)vRowTemp.get(11));
                            oEpiMed.oPaciente.sSexoP = sSex.equals("M") ? "MASCULINO" : "FEMENINO";
                            oEpiMed.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(12));
                            oEpiMed.getPaciente().setPeso(((Double) vRowTemp.get(13)).intValue());
                            oEpiMed.getPaciente().setTalla(((Double) vRowTemp.get(14)).intValue());
                            oEpiMed.getArea().setClave(((Double) vRowTemp.get(15)).intValue());
                            oEpiMed.oArea.buscarNombreArea(oEpiMed.oArea.getClave());

                            vObj.add(oEpiMed);                              
                        }
                        nTam = vObj.size();
                        arrRet = new EpisodioMedico[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
    }

    public EpisodioMedico[] buscarPacientesColectivosSeguroHosp(Date dFechaActual, int AreaServ) throws Exception{
    EpisodioMedico arrRet[]=null, oEpiMed=null;
    ArrayList rst = null;
    ArrayList<EpisodioMedico> vObj=null;
    String sQuery = "";
    String edad="";
    int i=0, nTam=0, con=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
             if( this==null){
                    throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
            }else{  
                        sQuery = "SELECT * FROM buscadatospacienteseguroareahospitalizacion('"+df.format(dFechaActual)+"'::timestamp without time zone,"+AreaServ+"::smallint);";
                        System.out.println(sQuery);
                    oAD=new AccesoDatos();

                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                        vObj = new ArrayList<EpisodioMedico>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEpiMed=new EpisodioMedico();
                            oEpiMed.setPaciente(new Paciente());
                            oEpiMed.getPaciente().setNombres((String) vRowTemp.get(0));
                            oEpiMed.getPaciente().setApPaterno((String) vRowTemp.get(1));
                            oEpiMed.getPaciente().setApMaterno((String) vRowTemp.get(2));
                            oEpiMed.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                            oEpiMed.getPaciente().getSeg().setNumero((String) vRowTemp.get(4));
                            oEpiMed.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).intValue());
                            oEpiMed.setAreaServicioHRRB((String) vRowTemp.get(6));
                            oEpiMed.setFechaIngreso((Date) vRowTemp.get(7));
                            oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(8)).intValue());
                            oEpiMed.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                            edad=(String)vRowTemp.get(9);
                                if (edad.compareTo("")!=0){
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0')
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0)
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                            oEpiMed.getCama().setNumero((String) vRowTemp.get(10));
                            oEpiMed.getArea().setClave(((Double) vRowTemp.get(11)).intValue());
                            oEpiMed.oArea.buscarNombreArea(oEpiMed.oArea.getClave());

                            vObj.add(oEpiMed);                              
                        }
                        nTam = vObj.size();
                        arrRet = new EpisodioMedico[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
    }

    public EpisodioMedico[] buscarPacientesColectivosCE(Date dFechaAux, int AreaServ) throws Exception{
    EpisodioMedico arrRet[]=null, oEpiMed=null;
    ArrayList rst = null;
    ArrayList<EpisodioMedico> vObj=null;
    String sQuery = "";
    String edad="";
    int i=0, nTam=0, con=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
             if( this==null){
                    throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
            }else{  
                        sQuery = "SELECT * FROM buscadatospacienteareaconsultaexterna('"+df.format(dFechaAux)+"'::date,"+AreaServ+"::smallint);";
                        System.out.println(sQuery);
                    oAD=new AccesoDatos();

                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                        vObj = new ArrayList<EpisodioMedico>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEpiMed=new EpisodioMedico();
                            oEpiMed.setPaciente(new Paciente());
                            oEpiMed.getPaciente().setNombres((String) vRowTemp.get(0));
                            oEpiMed.getPaciente().setApPaterno((String) vRowTemp.get(1));
                            oEpiMed.getPaciente().setApMaterno((String) vRowTemp.get(2));
                            oEpiMed.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                            oEpiMed.getPaciente().getSeg().setNumero((String) vRowTemp.get(4));
                            oEpiMed.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).intValue());
                            oEpiMed.setAreaServicioHRRB((String) vRowTemp.get(6));
                            oEpiMed.setFechaIngreso((Date) vRowTemp.get(7));
                            oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(8)).intValue());
                            oEpiMed.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                            edad=(String)vRowTemp.get(9);
                                if (edad.compareTo("")!=0){
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0')
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0)
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                            oEpiMed.getArea().setClave(((Double) vRowTemp.get(10)).intValue());
                            oEpiMed.oArea.buscarNombreArea(oEpiMed.oArea.getClave());

                            vObj.add(oEpiMed);                              
                        }
                        nTam = vObj.size();
                        arrRet = new EpisodioMedico[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
    }

    public EpisodioMedico[] buscarPacientesColectivosSeguroCE(Date dFechaAux, int AreaServ) throws Exception{
    EpisodioMedico arrRet[]=null, oEpiMed=null;
    ArrayList rst = null;
    ArrayList<EpisodioMedico> vObj=null;
    String sQuery = "";
    String edad="";
    int i=0, nTam=0, con=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
             if( this==null){
                    throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
            }else{  
                        sQuery = "SELECT * FROM buscadatospacienteseguroareaconsultaexterna('"+df.format(dFechaAux)+"'::date,"+AreaServ+"::smallint);";
                        System.out.println(sQuery);
                    oAD=new AccesoDatos();

                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                        vObj = new ArrayList<EpisodioMedico>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEpiMed=new EpisodioMedico();
                            oEpiMed.setPaciente(new Paciente());
                            oEpiMed.getPaciente().setNombres((String) vRowTemp.get(0));
                            oEpiMed.getPaciente().setApPaterno((String) vRowTemp.get(1));
                            oEpiMed.getPaciente().setApMaterno((String) vRowTemp.get(2));
                            oEpiMed.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                            oEpiMed.getPaciente().getSeg().setNumero((String) vRowTemp.get(4));
                            oEpiMed.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).intValue());
                            oEpiMed.setAreaServicioHRRB((String) vRowTemp.get(6));
                            oEpiMed.setFechaIngreso((Date) vRowTemp.get(7));
                            oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(8)).intValue());
                            oEpiMed.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                            edad=(String)vRowTemp.get(9);
                                if (edad.compareTo("")!=0){
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0')
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0)
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                        else
                                            oEpiMed.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                            oEpiMed.getArea().setClave(((Double) vRowTemp.get(10)).intValue());
                            oEpiMed.oArea.buscarNombreArea(oEpiMed.oArea.getClave());

                            vObj.add(oEpiMed);                              
                        }
                        nTam = vObj.size();
                        arrRet = new EpisodioMedico[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
    }

    public EpisodioMedico[] buscarPacientesColectivosURG(Date dFechaAux, int AreaServ) throws Exception{
	EpisodioMedico arrRet[]=null, oEpiMed=null;
	ArrayList rst = null;
        ArrayList<EpisodioMedico> vObj=null;
	String sQuery = "";
        String edad="";
	int i=0, nTam=0, con=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{  
                            sQuery = "SELECT * FROM buscadatospacienteareaurgencias('"+df.format(dFechaAux)+"'::timestamp without time zone,"+AreaServ+"::smallint);";
                            System.out.println(sQuery);
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<EpisodioMedico>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oEpiMed=new EpisodioMedico();
                                oEpiMed.setPaciente(new Paciente());
                                oEpiMed.setMotivoEgreso(new Parametrizacion());
                                oEpiMed.getPaciente().setNombres((String) vRowTemp.get(0));
                                oEpiMed.getPaciente().setApPaterno((String) vRowTemp.get(1));
                                oEpiMed.getPaciente().setApMaterno((String) vRowTemp.get(2));
                                oEpiMed.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                                oEpiMed.getPaciente().getSeg().setNumero((String) vRowTemp.get(4));
                                oEpiMed.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).intValue());
                                oEpiMed.setAreaServicioHRRB((String) vRowTemp.get(6));
                                oEpiMed.setFechaIngreso((Date) vRowTemp.get(7));
                                oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(8)).intValue());
                                oEpiMed.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                                edad=(String)vRowTemp.get(9);
                                    if (edad.compareTo("")!=0){
                                        if(edad.substring(0, 3).compareTo("000")!=0){
                                            if (edad.charAt(0)=='0')
                                                oEpiMed.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                            else
                                                oEpiMed.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                        }else{
                                            if (edad.substring(4, 6).compareTo("00")!=0)
                                                oEpiMed.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                            else
                                                oEpiMed.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                        }
                                    }
                                
                                oEpiMed.getMotivoEgreso().setValor((String) vRowTemp.get(10));
                                
                                String sSex = ((String)vRowTemp.get(11));
                                oEpiMed.oPaciente.sSexoP = sSex.equals("M") ? "MASCULINO" : "FEMENINO";
                                oEpiMed.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(12));
                                oEpiMed.getPaciente().setPeso(((Double) vRowTemp.get(13)).intValue());
                                oEpiMed.getPaciente().setTalla(((Double) vRowTemp.get(14)).intValue());
                                    
				vObj.add(oEpiMed);                              
                            }
                            nTam = vObj.size();
                            arrRet = new EpisodioMedico[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
        
    public EpisodioMedico[] buscarDatosPacienteSignosVitales() throws Exception {
    EpisodioMedico arrRet[]=null, oEpiMed = null;
    ArrayList rst = null;
    ArrayList<EpisodioMedico> vObj = null;        
    String sQuery = "";
    String edad="", numExp=this.getExpediente()+"";
    int i=0, nTam=0;
    oEpiMed = new EpisodioMedico();
        if(this.getPaciente().getNombres().trim() != null && 
            this.getPaciente().getApPaterno().trim() != null && 
            this.getExpediente().getNumero() == 0 ){
            sQuery="SELECT * FROM buscadatospacientesignosvitales('" + 
                    this.getPaciente().getNombres().toUpperCase() + 
                    "'::character varying,'" + 
                    this.getPaciente().getApPaterno().toUpperCase()+ 
                    "'::character varying,'" + 
                    this.getPaciente().getApMaterno().toUpperCase()+ 
                    "'::character varying,0);";
        }else if(getExpediente().getNumero() != 0){
           sQuery="SELECT * FROM buscadatospacientesignosvitales(''::character varying,''::character varying,''::character varying,"+getExpediente().getNumero()+");";
        }
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null) {
                vObj = new ArrayList<EpisodioMedico>();
                for(i=0; i < rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oEpiMed = new EpisodioMedico();
                    oEpiMed.setPaciente(new Paciente());
                    oEpiMed.oPaciente.setNombres((String) vRowTemp.get(0));
                    oEpiMed.oPaciente.setApPaterno((String) vRowTemp.get(1));
                    oEpiMed.oPaciente.setApMaterno((String) vRowTemp.get(2));
                    oEpiMed.oPaciente.setEdadNumero((String) vRowTemp.get(3));
                    edad=(String)vRowTemp.get(3);
                        if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oEpiMed.oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oEpiMed.oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oEpiMed.oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oEpiMed.oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                        }
                    String sSex = ((String)vRowTemp.get(4));
                    oEpiMed.oPaciente.sSexoP = sSex.equals("M") ? "MASCULINO" : "FEMENINO";
                    oEpiMed.oPaciente.setFechaNac((Date) vRowTemp.get(5));
                    oEpiMed.oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(6)).intValue());
                    oEpiMed.oPaciente.getSeg().setNumero((String) vRowTemp.get(7));
                    oEpiMed.oPaciente.setFolioPaciente(((Double) vRowTemp.get(8)).intValue());
                    oEpiMed.setClaveEpisodio(((Double) vRowTemp.get(9)).intValue());

                    vObj.add(oEpiMed);
                }
                nTam = vObj.size();
                arrRet=new EpisodioMedico[nTam];
                for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
    }

    public int insertarSignosVitales() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        System.out.println("Dentro de modifsigvit");
                if( getPaciente().getFolioPaciente()==0 ){   //completar llave
			throw new Exception("SignosVitales.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertasignosvitales('"+sUsuario+"'::character varying,"
                                                                        + getPaciente().getFolioPaciente()+ "::bigint,"
                                                                        + nClaveEpisodio+ "::bigint,'"
                                                                        + getSignosVitales().getTA()+ "'::character varying,'"
                                                                        + getSignosVitales().getFR()+ "'::character varying,'"
                                                                        + getSignosVitales().getFC()+"'::character varying,'"
                                                                        + getSignosVitales().getTemp()+"'::character varying);";
                        System.out.println(sQuery);
			oAD=new AccesoDatos();
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	}
        
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
    public int modificar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaEpisodioMedico();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) rst.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("EpisodioMedico.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaEpisodioMedico();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) rst.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    ////////////////////////////////////////////////////////////////////////////
    public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }
    ////////////////////////////////////////////////////////////////////////////
    
    public String getAvisoMinisterioDefun() {
        return sAvisoMinisterioDefun;
    }

    public void setAvisoMinisterioDefun(String valor) {
        sAvisoMinisterioDefun = valor;
    }

    public char getDocEntregada() {
        return bDocEntregada;
    }

    public void setDocEntregada(char valor) {
        bDocEntregada = valor;
    }

    public boolean getPrimeraVezEsp() {
        if (bPrimeraVezHRRB == true) {
            bPrimeraVezEsp = true;
        }
        return bPrimeraVezEsp;
    }

    public void setPrimeraVezEsp(boolean valor) {
        bPrimeraVezEsp = valor;
    }

    public boolean getPrimeraVezHRRB() {
        if (bPrimeraVezEsp == false) {
            bPrimeraVezHRRB = false;
        }
        return bPrimeraVezHRRB;
    }

    public void setPrimeraVezHRRB(boolean valor) {
        bPrimeraVezHRRB = valor;
    }

    public Date getAltaFisica() {
        return dAltaFisica;
    }

    public void setAltaFisica(Date valor) {
        dAltaFisica = valor;
    }

    public Date getAltaHospitalaria() {
        return dAltaHospitalaria;
    }

    public void setAltaHospitalaria(Date valor) {
        dAltaHospitalaria = valor;
    }

    public Date getFechaIngreso() {
        return dFechaIngreso;
    }

    public void setFechaIngreso(Date valor) {
        dFechaIngreso = valor;
    }

    public int getDiasEstancia() {
        return nDiasEstancia;
    }

    public void setDiasEstancia(int valor) {
        nDiasEstancia = valor;
    }

    public Cama getCama() {
        return oCama;
    }

    public void setCama(Cama valor) {
        oCama = valor;
    }

    public Parametrizacion getDestino() {
        return oDestino;
    }

    public void setDestino(Parametrizacion valor) {
        oDestino = valor;
    }

    public DiagnosticoCIE10 getDiagIngreso() {
        return oDiagIngreso;
    }

    public void setDiagIngreso(DiagnosticoCIE10 valor) {
        oDiagIngreso = valor;
    }

    public Parametrizacion getEdoSalud() {
        return oEdoSalud;
    }

    public void setEdoSalud(Parametrizacion valor) {
        oEdoSalud = valor;
    }

    public Medico getMedicoRecibe() {
        return oMedicoRecibe;
    }

    public void setMedicoRecibe(Medico valor) {
        oMedicoRecibe = valor;
    }

    public Medico getMedicoTratante() {
        return oMedicoTratante;
    }

    public void setMedicoTratante(Medico valor) {
        oMedicoTratante = valor;
    }

    public Parametrizacion getMotivoEgreso() {
        return oMotivoEgreso;
    }

    public void setMotivoEgreso(Parametrizacion valor) {
        oMotivoEgreso = valor;
    }

    public String getAreaServicioHRRB() {
        return sAreaServicioHRRB;
    }

    public void setAreaServicioHRRB(String valor) {
        sAreaServicioHRRB = valor;
    }

    public String getRazonAltaVolunTrasl() {
        return sRazonAltaVolunTrasl;
    }

    public void setRazonAltaVolunTrasl(String valor) {
        sRazonAltaVolunTrasl = valor.toUpperCase();
    }

    public String getRazonDestino() {
        return sRazonDestino;
    }

    public void setRazonDestino(String valor) {
        sRazonDestino = valor;
    }

    public HojaLesiones getHojaLesiones() {
        return oHojaLesiones;
    }

    public void setHojaLesiones(HojaLesiones valor) {
        oHojaLesiones = valor;
    }

    public FamiliarCercano getFamiliarCercano() {
        return oFamiliarCercano;
    }

    public void setFamiliarCercano(FamiliarCercano valor) {
        oFamiliarCercano = valor;
    }

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente valor) {
        oPaciente = valor;
    }

    public ArrayList getrrSignosVitales() {
        return arrSignosVitales;
    }

    public void setrrSignosVitales(ArrayList valor) {
        arrSignosVitales = valor;
    }

    public String getMotivoEgresoP() {
        return sMotivoEgresoP;
    }

    public void setMotivoEgresoP(String sMotivoEgresoP) {
        this.sMotivoEgresoP = sMotivoEgresoP;
    }

    public AreaServicioHRRB getArea() {
        return oArea;
    }

    public void setArea(AreaServicioHRRB oArea) {
        this.oArea = oArea;
    }

    public Expediente getExpediente() {
        return oExpediente;
    }

    public void setExpediente(Expediente val) {
        this.oExpediente = val;
    }

    public int getSubServicioHRRB() {
        return sSubServicioHRRB;
    }

    public void setSubServicioHRRB(int sSubServicioHRRB) {
        this.sSubServicioHRRB = sSubServicioHRRB;
    }

    public String getEdoSaludStr() {
        return sEdoSaludStr;
    }

    public void setEdoSaludStr(String sEdoSaludStr) {
        this.sEdoSaludStr = sEdoSaludStr;
    }

    public String getFechaIngresoP() {
        return dFechaIngresoP;
    }

    public void setFechaIngresoP(String dFechaIngresoP) {
        this.dFechaIngresoP = dFechaIngresoP;
    }

    public String getDiasEstanciaSTR() {
        return diasEstanciaSTR;
    }

    public void setDiasEstanciaSTR(String diasEstanciaSTR) {
        this.diasEstanciaSTR = diasEstanciaSTR;
    }

    public int getAreaServicioHRRBSTR() {
        return sAreaServicioHRRBSTR;
    }

    public void setAreaServicioHRRBSTR(int sAreaServicioHRRBSTR) {
        this.sAreaServicioHRRBSTR = sAreaServicioHRRBSTR;
    }

    public String getAreaServicioHRRBCad() {
        return sAreaServicioHRRBCad;
    }

    public void setAreaServicioHRRBCad(String sAreaServicioHRRBCad) {
        this.sAreaServicioHRRBCad = sAreaServicioHRRBCad;
    }

    public String getDestinoSTR() {
        return sDestinoSTR;
    }

    public void setDestinoSTR(String sDestinoSTR) {
        this.sDestinoSTR = sDestinoSTR;
    }

    public String getSubServicioHRRBSTR() {
        return sSubServicioHRRBSTR;
    }

    public void setSubServicioHRRBSTR(String sSubServicioHRRBSTR) {
        this.sSubServicioHRRBSTR = sSubServicioHRRBSTR;
    }

    public SignosVitales getSignosVitales() {
        return oSignosVitales;
    }

    public void setSignosVitales(SignosVitales oSignosVitales) {
        this.oSignosVitales = oSignosVitales;
    }

    public AfeccionTratada getAfePrimera() {
        return oAfePrimera;
    }

    public void setAfePrimera(AfeccionTratada oAfePrimera) {
        this.oAfePrimera = oAfePrimera;
    }

    public AfeccionTratada getAfeSegunda() {
        return oAfeSegunda;
    }

    public void setAfeSegunda(AfeccionTratada oAfeSegunda) {
        this.oAfeSegunda = oAfeSegunda;
    }

    public AfeccionTratada getAfeTercera() {
        return oAfeTercera;
    }

    public void setAfeTercera(AfeccionTratada oAfeTercera) {
        this.oAfeTercera = oAfeTercera;
    }

    public AfeccionTratada getAfeCuarta() {
        return oAfeCuarta;
    }

    public void setAfeCuarta(AfeccionTratada oAfeCuarta) {
        this.oAfeCuarta = oAfeCuarta;
    }

    public AfeccionTratada getAfeQuinta() {
        return oAfeQuinta;
    }

    public void setAfeQuinta(AfeccionTratada oAfeQuinta) {
        this.oAfeQuinta = oAfeQuinta;
    }

    public AfeccionTratada getAfeSexta() {
        return oAfeSexta;
    }

    public void setAfeSexta(AfeccionTratada oAfeSexta) {
        this.oAfeSexta = oAfeSexta;
    }

    public AfeccionTratada getAfeResAP() throws Exception{
        //contador++;
        //System.out.println("Contador: "+contador);
        if (oAfePrincipal.getCIE10().getDescripcionDiag() != null) {
            if (oAfeResAP.getCIE10().getDescripcionDiag() == null || oAfeResAP.getCIE10().getDescripcionDiag().isEmpty()) {
                oAfeResAP.getCIE10().setDescripcionDiag(oAfePrincipal.getCIE10().getDescripcionDiag());
                //contador=0;
            }
        }
        return oAfeResAP;
    }

    public void setAfeResAP(AfeccionTratada oAfeResAP) {
        this.oAfeResAP = oAfeResAP;
    }

    public AfeccionTratada getAfeResAPDef() throws Exception{
        //contadorDef++;
        //System.out.println("ContadorDef: "+contadorDef+ " || "+oAfePrimera.getCIE10().getDescripcionDiag());
        if (oAfePrimera.getCIE10().getDescripcionDiag() != null) {
            if (oAfeResAPDef.getCIE10().getDescripcionDiag() == null || oAfeResAPDef.getCIE10().getDescripcionDiag().isEmpty()) {
                //System.out.println("Entre :D");
                oAfeResAPDef.getCIE10().setDescripcionDiag(oAfePrimera.getCIE10().getDescripcionDiag());
                //contadorDef=0;
            }
        }
        return oAfeResAPDef;
    }

    //***************************BUSCA HOJAS LESIONES LLENAS**************************************         
    public List<EpisodioMedico> buscaHojasLesionesLlenas(String sFechaI, String sFechaF) throws Exception {
        EpisodioMedico oHojaHL = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        String v[];

        List<EpisodioMedico> lHojaLesion = null;

        sQuery = "SELECT * FROM buscaHojasLesion('" + sFechaI + "','" + sFechaF + "');";
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null || rst.isEmpty() != true) {
            lHojaLesion = new ArrayList<EpisodioMedico>();
            //vObj = new ArrayList<Hospitalizacion>();
            for (i = 0; i < rst.size(); i++) {
                oHojaHL = new EpisodioMedico();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oHojaHL.getPaciente().setNombres((String) vRowTemp.get(0));
                oHojaHL.getPaciente().setApPaterno((String) vRowTemp.get(1));
                oHojaHL.getPaciente().setApMaterno((String) vRowTemp.get(2));
                v = vRowTemp.get(3).toString().split("-");
                oHojaHL.getPaciente().setFechaNacTexto(v[2] + "/" + v[1] + "/" + v[0]);
                oHojaHL.getPaciente().setFechaNac((Date) vRowTemp.get(3));

                oHojaHL.setFechaIngreso(((Date) vRowTemp.get(4)));
                SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                //System.out.println("fechaIngreso: "+fechaIng.format(getFechaIngreso()));
                oHojaHL.setFIngreso(fechaIng.format(oHojaHL.getFechaIngreso()));

                oHojaHL.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).longValue());
                oHojaHL.setClaveEpisodio(((Double) vRowTemp.get(6)).longValue());

                lHojaLesion.add(oHojaHL);
            }
        }

        return lHojaLesion;
    }

    public void buscarHojaLesionLlenaAfectado() throws Exception {

        ArrayList rst = null;
        String sQuery = "";
        String v[];
        FacesMessage message = null;
        if (this == null) {   //completar llave
            throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaDatosLesionDatosAfectado(" + getPaciente().getFolioPaciente() + "::BIGINT" + "," + getClaveEpisodio() + "::BIGINT);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst.isEmpty() == false) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                getPaciente().setNombres((String) vRowTemp.get(0));
                getPaciente().setApPaterno((String) vRowTemp.get(1));
                getPaciente().setApMaterno((String) vRowTemp.get(2));
                getPaciente().setSexoP((String) vRowTemp.get(3));
                getPaciente().setCurp((String) vRowTemp.get(4));
                v = vRowTemp.get(5).toString().split("-");
                getPaciente().setFechaNacTexto(v[2] + "/" + v[1] + "/" + v[0]);
                getPaciente().setFechaNac((Date) vRowTemp.get(5));
                getPaciente().getSeg().setDerechohabienteP((String) vRowTemp.get(7));
                getPaciente().getSeg().getDerechohabiente().setValor((String) vRowTemp.get(8));
                if (getPaciente().getSeg().getDerechohabienteP().compareTo("T0108") == 0 || getPaciente().getSeg().getDerechohabienteP().compareTo("T010G") == 0) {
                    getPaciente().getSeg().setNumero((String) vRowTemp.get(6));
                }

                getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(12)).intValue());
                getHojaLesiones().setEsEmbarazada((String) vRowTemp.get(13));
                if (((String) vRowTemp.get(14)).compareTo("") != 0) {
                    getHojaLesiones().setSabeLeerEscribir(((String) vRowTemp.get(14)).charAt(0));
                }
                getHojaLesiones().getEscolaridad().setClaveParametro(((String) vRowTemp.get(15)).substring(3, 4));
                getHojaLesiones().getEscolaridad().setValor(((String) vRowTemp.get(16)));
                getHojaLesiones().getLesion().setDomicilioOcurrencia((String) vRowTemp.get(17));
                getHojaLesiones().getLesion().getEstado().setClaveEdo((String) vRowTemp.get(18));
                getHojaLesiones().getLesion().getEstado().setDescripcionEdo((String) vRowTemp.get(19));
                getHojaLesiones().getLesion().getMunicipio().setClaveMun((String) vRowTemp.get(20));
                getHojaLesiones().getLesion().getMunicipio().setDescripcionMun((String) vRowTemp.get(21));
                getHojaLesiones().getLesion().getCiudad().setClaveCiu((String) vRowTemp.get(22));
                getHojaLesiones().getLesion().getCiudad().setDescripcionCiu((String) vRowTemp.get(23));
            }
        }
    }

    public void buscarHojaLesionLlenaEvento() throws Exception {

        ArrayList rst = null;
        String sQuery = "";
        String v[];
        FacesMessage message = null;
        if (this == null) {   //completar llave
            throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaDatosLesionEvento(" + getPaciente().getFolioPaciente() + "::BIGINT" + "," + getClaveEpisodio() + "::BIGINT);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst.isEmpty() == false) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                getHojaLesiones().getLesion().setFechaOcurrencia((Date) vRowTemp.get(0));
                if (((String) vRowTemp.get(1)).compareTo("") != 0) {
                    getHojaLesiones().getLesion().setDiaFestivo(((String) vRowTemp.get(1)).charAt(0));
                }

                if (((String) vRowTemp.get(2)).compareTo("N") == 0) {
                    getPaciente().setDiscapacitado(false);
                } else {
                    getPaciente().setDiscapacitado(true);
                }

                getHojaLesiones().getLesion().getIntencionalidad().setClave(((Double) vRowTemp.get(3)).intValue());
                getHojaLesiones().getLesion().getIntencionalidad().setDescripcion((String) vRowTemp.get(4));
                getHojaLesiones().getLesion().getTipoViolencia().setClaveParametro((String) vRowTemp.get(5));
                getHojaLesiones().getLesion().getTipoViolencia().setValor((String) vRowTemp.get(6));
                getHojaLesiones().getLesion().setCasoEventoAuto((String) vRowTemp.get(7));
                getHojaLesiones().getLesion().getPacBajoEfectos().setClaveParametro((String) vRowTemp.get(8));
                getHojaLesiones().getLesion().getPacBajoEfectos().setValor((String) vRowTemp.get(9));
                getHojaLesiones().getLesion().getSitioOcurrencia().setClave(((Double) vRowTemp.get(10)).intValue());
                getHojaLesiones().getLesion().getSitioOcurrencia().setDescripcion((String) vRowTemp.get(11));
                getHojaLesiones().getLesion().getAgenteLesion().setClaveParametro(String.valueOf(((Double) vRowTemp.get(12)).intValue()));
                getHojaLesiones().getLesion().getAgenteLesion().setValor((String) vRowTemp.get(13));
                getHojaLesiones().getLesion().getSiVehiculo().setClaveParametro((String) vRowTemp.get(14));
                getHojaLesiones().getLesion().getSiVehiculo().setValor((String) vRowTemp.get(15));
                getHojaLesiones().getLesion().getEquipoSeguridad().setClaveParametro((String) vRowTemp.get(16));
                getHojaLesiones().getLesion().getEquipoSeguridad().setValor((String) vRowTemp.get(17));
                getHojaLesiones().getLesion().getNomEqSeguridad().setClaveParametro((String) vRowTemp.get(18));
                getHojaLesiones().getLesion().getNomEqSeguridad().setValor((String) vRowTemp.get(19));
                getHojaLesiones().getLesion().getAreaAnatomicaGrave().setClaveParametro(String.valueOf(((Double) vRowTemp.get(20)).intValue()));
                getHojaLesiones().getLesion().getAreaAnatomicaGrave().setValor((String) vRowTemp.get(21));
                getHojaLesiones().getLesion().getConsecuenciaResultante().setClaveParametro(String.valueOf(((Double) vRowTemp.get(22)).intValue()));
                getHojaLesiones().getLesion().getConsecuenciaResultante().setValor((String) vRowTemp.get(23));

                if (((String) vRowTemp.get(24)).compareTo("N") == 0) {
                    getHojaLesiones().getLesion().setAtnPreHospitalaria(false);
                } else {
                    getHojaLesiones().getLesion().setAtnPreHospitalaria(true);
                }

                getHojaLesiones().getLesion().setTiempoTraslado(((Date) vRowTemp.get(25)).toLocaleString().substring(11, 16));

            }
        }
    }

    public void buscarHojaLesionLlenaAgresor() throws Exception {

        ArrayList rst = null;
        String sQuery = "";
        String v[];
        FacesMessage message = null;
        if (this == null) {   //completar llave
            throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaDatosLesionAgresor(" + getPaciente().getFolioPaciente() + "::BIGINT" + "," + getClaveEpisodio() + "::BIGINT);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst.isEmpty() == false) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                if (((String) vRowTemp.get(0)).compareTo("") != 0) {
                    getHojaLesiones().getAgresor().setTipoAgresor(((String) vRowTemp.get(0)).charAt(0));
                }
                getHojaLesiones().getAgresor().setEdadAgresor(((Double) vRowTemp.get(1)).intValue());
                if (((String) vRowTemp.get(2)).compareTo("") != 0) {
                    getHojaLesiones().getAgresor().setSexoAgresor(((String) vRowTemp.get(2)).charAt(0));
                }
                getHojaLesiones().getAgresor().getParentescoConAfectado().setClaveParametro((String) vRowTemp.get(3));
                getHojaLesiones().getAgresor().getParentescoConAfectado().setValor((String) vRowTemp.get(4));
                getHojaLesiones().getAgresor().getAgresorBajoEfecto().setClaveParametro((String) vRowTemp.get(5));
                getHojaLesiones().getAgresor().getAgresorBajoEfecto().setValor((String) vRowTemp.get(6));

            }
        }
    }

    public void buscarHojaLesionLlenaAtencion() throws Exception {

        ArrayList rst = null;
        String sQuery = "";
        String v[];
        FacesMessage message = null;
        if (this == null) {   //completar llave
            throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaDatosLesionAtencion(" + getPaciente().getFolioPaciente() + "::BIGINT" + "," + getClaveEpisodio() + "::BIGINT);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst.isEmpty() == false) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);

                getHojaLesiones().getAtencion().setFechaAtencion(((Date) vRowTemp.get(0)));
                getHojaLesiones().getAtencion().getServicioAtencion().setClaveParametro((String) vRowTemp.get(1));
                getHojaLesiones().getAtencion().getServicioAtencion().setValor((String) vRowTemp.get(2));
                if (((Date) vRowTemp.get(3)) != null) {
                    getHojaLesiones().getAtencion().setAtnUrgs(((Date) vRowTemp.get(3)));
                }
                getHojaLesiones().getAtencion().getTipoAtencion().setClaveParametro((String) vRowTemp.get(4));
                getHojaLesiones().getAtencion().getTipoAtencion().setValor((String) vRowTemp.get(5));
                getHojaLesiones().getAtencion().getReferidoPor().setClaveParametro((String) vRowTemp.get(6));
                getHojaLesiones().getAtencion().getReferidoPor().setValor((String) vRowTemp.get(7));
                getHojaLesiones().getAtencion().getDestinoDespAtencion().setClaveParametro((String) vRowTemp.get(8));
                getHojaLesiones().getAtencion().getDestinoDespAtencion().setValor((String) vRowTemp.get(9));
                getHojaLesiones().getAtencion().setMinisterioPublico((String) vRowTemp.get(10));
            }
        }
    }

    
    //****************************************************************************
    //**********************atencion diagnostico*********************************
    //***************************************************************************
    public void buscarHojaLesionLlenaAtencionDiag() throws Exception {

        // resetea2();
        ArrayList rst = null;
        String sQuery = "";
        String v[];
        FacesMessage message = null;
        if (this == null) {   //completar llave
            throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaDatosLesionAtencionDiag(" + getPaciente().getFolioPaciente() + "::BIGINT" + "," + getClaveEpisodio() + "::BIGINT);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst.isEmpty() == false && rst.size() >= 1) {
                for (Object rst1 : rst) {
                    ArrayList vRowTemp = (ArrayList) rst1;
                    switch ((String) vRowTemp.get(0)) {
                        case "T0615":
                            this.getAfePrincipal().getCIE10().setDescripcionDiag((String) vRowTemp.get(2));
                            this.getAfePrincipal().getCIE10().setClave((String) vRowTemp.get(1));
                            break;
                        case "T0616":
                            this.getAfeSegunda().getCIE10().setDescripcionDiag((String) vRowTemp.get(2));
                            this.getAfeSegunda().getCIE10().setClave((String) vRowTemp.get(1));
                            break;
                        case "T0617":
                            this.getAfeTercera().getCIE10().setDescripcionDiag((String) vRowTemp.get(2));
                            this.getAfeTercera().getCIE10().setClave((String) vRowTemp.get(1));
                            break;
                        case "T0618":
                            this.getAfeResAP().getCIE10().setDescripcionDiag((String) vRowTemp.get(2));
                            this.getAfeResAP().getCIE10().setClave((String) vRowTemp.get(1));
                            break;
                        case "T0607":
                            this.getAfeCuarta().getCIE10().setDescripcionDiag((String) vRowTemp.get(2));
                            this.getAfeCuarta().getCIE10().setClave((String) vRowTemp.get(1));
                            break;
                    }
                }
            }

        }
    }
/****************************************************************************************************************
    /* busqueda de Datos de Paciente Valoración Preanestesica*/
    public EpisodioMedico[] buscaPacientesValoracion()throws Exception{
            EpisodioMedico arrRet[]=null, oEpiMed = null;
            ArrayList rst = null;
            ArrayList<EpisodioMedico> vObj = null;
            String sQuery = "";
            String edad = "", numExp=this.getExpediente()+"";
            int i=0, nTam=0;
            
            if((!"null".equals(this.oPaciente.getNombres()) && (!"null".equals(this.oPaciente.getApPaterno()) || !"null".equals(this.oPaciente.getApMaterno()))) 
                    || !"null".equals(numExp)){
                sQuery = "SELECT * FROM buscaDatosPacienteValoracionPreanestesica('"+this.oPaciente.getNombres()+"','"
                        +this.oPaciente.getApPaterno()+"','"+this.oPaciente.getApMaterno()+"',"+numExp+");";
            }
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
        }
        if (rst != null) {
            vObj = new ArrayList<EpisodioMedico>();
            for (i = 0; i < rst.size(); i++) { 
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oEpiMed = new EpisodioMedico();
                oEpiMed.oProceRe1 = new ProcedimientosRealizados();
                oEpiMed.oProceRe1.setCIE9(new ProcedimientoCIE9());
                oEpiMed.oPaciente.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                oEpiMed.oPaciente.setNombres((String)vRowTemp.get(1));
                oEpiMed.oPaciente.setApPaterno((String)vRowTemp.get(2));
                oEpiMed.oPaciente.setApMaterno((String)vRowTemp.get(3));
                oEpiMed.oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(4)).intValue());
                oEpiMed.oPaciente.getSeg().setNumero((String) vRowTemp.get(5));
                oEpiMed.oPaciente.setFechaNac((Date) vRowTemp.get(6));
                oEpiMed.oPaciente.setEdadNumero((String)vRowTemp.get(7));
                edad=(String)vRowTemp.get(7);
                if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                        if (edad.charAt(0)=='0')
                            oEpiMed.oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                            oEpiMed.oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                            oEpiMed.oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                            oEpiMed.oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                }
                oEpiMed.oPaciente.setSexoP((String) vRowTemp.get(8));
                oEpiMed.oDiagIngreso.setClave((String) vRowTemp.get(9));
                oEpiMed.oDiagIngreso.setDescripcionDiag((String) vRowTemp.get(10));
                oEpiMed.oProceRe1.getCIE9().setClave((String) vRowTemp.get(11));
                oEpiMed.oProceRe1.getCIE9().setDescripcion((String) vRowTemp.get(12));
                vObj.add(oEpiMed);
            }
            nTam = vObj.size();
            arrRet = new EpisodioMedico[nTam];
            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
        }
    
    /***** Emmanuel****/
    public void buscaEpisodioAbierto(Paciente oPac) throws Exception{
    ArrayList rst = null;
    String sQuery = ""; 
    String sestaSalud; 

        sQuery = "SELECT * FROM buscaepisodiomedicopaciente ("+oPac.getFolioPaciente()+" ::BIGINT);";
        System.out.println(sQuery);
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null) {
            ArrayList vRowTemp = (ArrayList)rst.get(0);  
            this.setFechaIngreso((Date)vRowTemp.get(0)); 
            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy"); 
            this.setFIngreso(fechaIng.format(this.getFechaIngreso())); 
            this.setAreaServicioHRRBSTR(((Double) vRowTemp.get(1)).intValue());
             oArea.buscarNombreArea(getAreaServicioHRRBSTR());
            oDiagIngreso.setClave((String)vRowTemp.get(2));
            oDiagIngreso.setDescripcionDiag((String)vRowTemp.get(3));
            oDiagIngreso.getCauses().setClave((String)vRowTemp.get(4));
            this.setClaveEpisodio(((Double) vRowTemp.get(5)).longValue());    
            this.oCama.setNumero((String)vRowTemp.get(6));
            this.getMedicoRecibe().setNoTarjeta(((Double)vRowTemp.get(7)).intValue());
            this.getMedicoRecibe().buscarDatosMedico();
            this.getMedicoTratante().setNoTarjeta(((Double)vRowTemp.get(8)).intValue());
            this.getMedicoTratante().buscarDatosMedico();
             //String destino=(String)vRowTemp.get(9);
            //if(destino.compareTo("")!=0){ 
            ///this.getDestino().setValor(this.getDestino().buscaValorParametrizado(destino));
            /// }
            //else{
             ///this.getDestino().setValor("");
              ///}
            //this.setDocEntregada((char)vRowTemp.get(10));
             //String motivoegre=(String)vRowTemp.get(11);
            /* if(motivoegre.compareTo("")!=0){
               this.getMotivoEgreso().setValor(this.getMotivoEgreso().buscaValorParametrizado(motivoegre));
             }else{
                this.getMotivoEgreso().setValor("");} */
                this.setRazonAltaVolunTrasl((String)vRowTemp.get(12));
                this.setAvisoMinisterioDefun((String)vRowTemp.get(13));
                ///this.setPrimeraVezEsp((boolean)vRowTemp.get(14));
                ///this.setPrimeraVezEsp((boolean)vRowTemp.get(15));
                /// String estadsalud=(String)vRowTemp.get(16);
             ///if(estadsalud.compareTo("")!=0){ 
               /*this.getEdoSalud().setValor(this.getEdoSalud().buscaValorParametrizado(estadsalud));
             }else{
                this.getEdoSalud().setValor("");}*/  
               this.oArea.buscarNombreArea(getAreaServicioHRRBSTR());
              //this.setTipoAfePrinc((char)vRowTemp.get(18));
              //this.setInfeccionIntrahospitalaria((char)vRowTemp.get(19));
               this.setSubServicioHRRB(((Double) vRowTemp.get(20)).intValue());
               this.oArea.buscarSubServiciosHosp(getSubServicioHRRB());
               this.getPrograma().setClave(((Double) vRowTemp.get(21)).intValue());

        } 
    }
    
    public void buscaEpisodioMedicoReciente(Paciente oPac) throws Exception{
    ArrayList rst = null;
    String sQuery = "";  

        sQuery = "SELECT * FROM buscaEpisodioRecinte("+oPac.getFolioPaciente()+" ::BIGINT);";
        System.out.println(sQuery);
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null && rst.size()>0) {
            ArrayList vRowTemp = (ArrayList)rst.get(0);  
            this.setAltaFisica((Date)vRowTemp.get(0));
            this.setAltaHospitalaria((Date)vRowTemp.get(1));
            this.setFechaIngreso((Date)vRowTemp.get(2)); 
            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy"); 
            this.setFIngreso(fechaIng.format(this.getFechaIngreso())); 
            this.setAreaServicioHRRBSTR(((Double) vRowTemp.get(3)).intValue());
             oArea.buscarNombreArea(getAreaServicioHRRBSTR());
            oDiagIngreso.setClave((String)vRowTemp.get(4));
            oDiagIngreso.setDescripcionDiag((String)vRowTemp.get(5));
            oDiagIngreso.getCauses().setClave((String)vRowTemp.get(6));
            this.setClaveEpisodio(((Double) vRowTemp.get(7)).longValue());    
            this.oCama.setNumero((String)vRowTemp.get(8));
            this.getMedicoRecibe().setNoTarjeta(((Double)vRowTemp.get(9)).intValue());
            this.getMedicoRecibe().buscarDatosMedico();
            this.getMedicoTratante().setNoTarjeta(((Double)vRowTemp.get(10)).intValue());
            this.getMedicoTratante().buscarDatosMedico();
            this.setRazonAltaVolunTrasl((String)vRowTemp.get(14));
            this.setAvisoMinisterioDefun((String)vRowTemp.get(15));
            this.oArea.buscarNombreArea(getAreaServicioHRRBSTR());
            this.setSubServicioHRRB(((Double) vRowTemp.get(22)).intValue());
            this.oArea.buscarSubServiciosHosp(getSubServicioHRRB());
            this.getPrograma().setClave(((Double) vRowTemp.get(23)).intValue());                                
        } 
    }
 
 
    /******************/
    
    //Javier 
    //Busca la informacion de un paciente y el area de servicio en donde entra 
    public boolean buscaIdentificacionPac() throws Exception{
        boolean sRtn=false;
        ArrayList rst=null;
        
        String sQuery="" , edad="";
        if(oPaciente.getFolioPaciente()==0){             
            throw new Exception("EpisodioMedico.buscaIdentificacionPac: Error de programacion faltan datos");
        }else{
            sQuery="SELECT * FROM buscaidentificacionpaciente("+oPaciente.getFolioPaciente()+"::bigint)";
            if (oAD == null){
                oAD=new AccesoDatos();
                if (oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                oAD = null;
            }
            else{
                rst = oAD.ejecutarConsulta(sQuery);
            }
            if(rst!=null && rst.size()==1){
               
                ArrayList vRowTemp=(ArrayList)rst.get(0);                
                oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                oPaciente.setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                oPaciente.getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());                
                oPaciente.setNombres((String)vRowTemp.get(3));
                oPaciente.setApPaterno((String)vRowTemp.get(4));
                oPaciente.setApMaterno((String)vRowTemp.get(5));
                oPaciente.setFechaNac((Date)vRowTemp.get(6));
                edad=(String)vRowTemp.get(7);
                if (edad.compareTo("")!=0){
                if(edad.substring(0, 3).compareTo("000")!=0){
                    if (edad.charAt(0)=='0')
                        oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                    else
                        oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                }else{
                    if (edad.substring(4, 6).compareTo("00")!=0)
                        oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                    else
                        oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                }
                setFechaIngreso((Date)vRowTemp.get(8));                
                oArea.setDescripcion((String)vRowTemp.get(9));
                oPaciente.getExpediente().setAntecPatologicos(new AntecPatologicos());
                oPaciente.getExpediente().getAntecPatologicos().setAlergias((String)vRowTemp.get(10));
                sRtn=true;                
            }           
             
        }
        return sRtn;
    }
    
    public EpisodioMedico[] buscarPacientesEnServicio(int CveArea)throws Exception{
        EpisodioMedico ArrEpi[]=null, oEpi=null;
        ArrayList rst=null;
        String sQuery="", edad="";
        int i=0;
        if(CveArea==0){
            throw new Exception("EpisodioMedico.BuscarPacientesEnServicio: Error Faltan Datos");            
        }
        else{
            sQuery="SELECT * FROM buscarpacientesenservicio("+CveArea+"::bigint);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                ArrEpi = new EpisodioMedico[rst.size()];
                for(i=0;i<rst.size();i++){
                    oEpi= new EpisodioMedico();
                   ArrayList vRowTemp= (ArrayList)rst.get(i); 
                   
                   oEpi.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                   oEpi.getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                   oEpi.getPaciente().setNombres((String)vRowTemp.get(2));
                   oEpi.getPaciente().setApPaterno((String)vRowTemp.get(3));
                   oEpi.getPaciente().setApMaterno((String)vRowTemp.get(4));
                   oEpi.getPaciente().setSexoP((String)vRowTemp.get(5));
                   oEpi.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(6)).intValue());
                   oEpi.setFechaIngreso((Date)vRowTemp.get(7));
                   oEpi.getCama().setNumero((String)vRowTemp.get(9));
                   oEpi.getArea().setClave(((Double)vRowTemp.get(10)).intValue());
                   oEpi.getArea().setDescripcion((String)vRowTemp.get(11));
                   edad=(String)vRowTemp.get(12);
                   if (edad.compareTo("")!=0){
                   if(edad.substring(0, 3).compareTo("000")!=0){
                      if (edad.charAt(0)=='0')
                          oEpi.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                       else
                          oEpi.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                   }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                          oEpi.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                          oEpi.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                  }
                   oEpi.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(16));
                   
                   ArrEpi[i]= oEpi;
                }
                
            }
        }
        
        return ArrEpi;
    }
    
    public boolean buscarDatosPaciente()throws Exception{
        boolean bRtn=false;
        ArrayList rst=null;
        String sQuery="", sEdad="";
        
        if(oPaciente.getFolioPaciente()==0){
            throw new Exception("EpisodioMedico.BuscarDatosPaciente: Error Faltan Datos");
        }
        else{
            sQuery=" SELECT * FROM buscainformacionpaciente("+oPaciente.getFolioPaciente()+"::bigint,"+oPaciente.getClaveEpisodio()+"::bigint)";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()==1){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                oPaciente.setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                oPaciente.getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                oPaciente.setNombres((String)vRowTemp.get(3));
                oPaciente.setApPaterno((String)vRowTemp.get(4));
                oPaciente.setApMaterno((String)vRowTemp.get(5));
                oCama.setNumero((String)vRowTemp.get(6));
                if(((String)vRowTemp.get(7)).equals("M")){
                    oPaciente.setSexoP("MASCULINO");
                }else if(((String)vRowTemp.get(7)).equals("F")){
                    oPaciente.setSexoP("FEMENINO");
                }else{
                    oPaciente.setSexoP("NO ESPECIFICADO");
                }                      
                sEdad= (String)vRowTemp.get(8);
                if (sEdad.compareTo("")!=0){
                   if(sEdad.substring(0, 3).compareTo("000")!=0){
                      if (sEdad.charAt(0)=='0')
                          oPaciente.setEdadNumero(sEdad.substring(1, 3)+" AÑOS");
                       else
                          oPaciente.setEdadNumero(sEdad.substring(0, 3)+" AÑOS");
                   }else{
                        if (sEdad.substring(4, 6).compareTo("00")!=0)
                          oPaciente.setEdadNumero(sEdad.substring(4, 6)+" MESES");
                        else
                          oPaciente.setEdadNumero(sEdad.substring(7, 9)+" DÍAS");
                    }
                  }
                 oDiagIngreso.setDescripcionDiag((String)vRowTemp.get(9));
                 oArea.setDescripcion((String)vRowTemp.get(10));
                 setFechaIngreso((Date)vRowTemp.get(11));
                 bRtn=true;
            }  
            System.out.println(" pac "+ oPaciente.getNombreCompleto());
        }
        return bRtn;
    }
    
    /*buscar datos de un paciente mas especifico*/
    public boolean buscarPacienteParaHojaEnf()throws Exception{
        boolean bRtn=false;
        ArrayList rst=null;
        String sQuery="", edad="",sHabla="";
        if(oPaciente.getFolioPaciente()==0){
            throw new Exception("EpisodioMedico.BuscarPacienteParaHojaEnf");
        }else{
            sQuery="SELECT * FROM  buscapacienteparahojaenf("+oPaciente.getFolioPaciente()+"::bigint);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()==1){
                ArrayList vRowTemp= (ArrayList)rst.get(0);
                oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                oPaciente.setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                oPaciente.getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                oPaciente.setNombres((String)vRowTemp.get(3));
                oPaciente.setApPaterno((String)vRowTemp.get(4));
                oPaciente.setApMaterno((String)vRowTemp.get(5));
                oCama.setNumero((String)vRowTemp.get(6));
                oPaciente.setSexoP((String)vRowTemp.get(7));
                edad=(String)vRowTemp.get(8);
                   if (edad.compareTo("")!=0){
                   if(edad.substring(0, 3).compareTo("000")!=0){
                      if (edad.charAt(0)=='0')
                          oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                       else
                          oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                   }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                          oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                          oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");                        
                    }
                  }
                 setFechaIngreso((Date)vRowTemp.get(9));
                 oDiagIngreso.setDescripcionDiag((String)vRowTemp.get(10));
                 oArea.setDescripcion((String)vRowTemp.get(11));
                 setDiasEstancia(((Double)vRowTemp.get(12)).intValue());                 
                 oPaciente.setPeso(((Double)vRowTemp.get(13)).floatValue());
                 oPaciente.setTalla(((Double)vRowTemp.get(14)).floatValue());
                 oPaciente.setFechaNac((Date)vRowTemp.get(15));
                 sHabla=(String)vRowTemp.get(16);
                 if(sHabla.equals("T0201")){
                     oPaciente.setHablaLengua("ESPAÑOL");
                 }else{
                     oPaciente.setHablaLengua((String)vRowTemp.get(17));
                 }
                 oPaciente.getExpediente().setAntecNoPatologicos(new AntecNoPatologicos());
                 oPaciente.getExpediente().getAntecNoPatologicos().setReligion((String)vRowTemp.get(18));
                 oPaciente.getExpediente().getAntecNoPatologicos().setEscolaridad((String)vRowTemp.get(19));
                 oPaciente.getExpediente().getAntecPatologicos().setAlergias((String)vRowTemp.get(20));
            }
            //System.out.println(" pacient " + oPaciente.getNombreCompleto());
        }
        return bRtn;
    }
    /****INICIA METODOS CREADOS POR JOSE JAVIER GONZALEZ VALLEJO TERCERA FACE */
    public EpisodioMedico[] buscarPacientesPorServicioHopitalizacion(int nClaveArea) throws Exception{
        EpisodioMedico arrPacHosp[]=null, oEpi=null;
        ArrayList rst=null;
        String sQuery="", edad="";
        int i=0;
        if(nClaveArea==0){
            throw new Exception("EpisodioMedico.BuscarPacientesPorServicioHopitalizacion: ERROR, Faltan Datos");
        }else{
            sQuery="SELECT * FROM  buscarPacientePorServicioHosp("+nClaveArea+"::bigint);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                arrPacHosp= new EpisodioMedico[rst.size()];
                for(i=0; i<rst.size();i++){
                    ArrayList vRowTemp= (ArrayList)rst.get(i);
                    oEpi= new EpisodioMedico();
                    oEpi.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oEpi.getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oEpi.getPaciente().setNombres((String)vRowTemp.get(2));
                    oEpi.getPaciente().setApPaterno((String)vRowTemp.get(3));
                    oEpi.getPaciente().setApMaterno((String)vRowTemp.get(4));
                    oEpi.getPaciente().setSexoP((String)vRowTemp.get(5));
                    oEpi.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(6)).intValue());
                    oEpi.setFechaIngreso((Date)vRowTemp.get(7));
                    oEpi.getCama().setNumero((String)vRowTemp.get(9));
                    oEpi.getArea().setClave(((Double)vRowTemp.get(10)).intValue());
                    oEpi.getArea().setDescripcion((String)vRowTemp.get(11));
                    edad=(String)vRowTemp.get(12);
                    if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                       if (edad.charAt(0)=='0')
                           oEpi.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                           oEpi.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                         if (edad.substring(4, 6).compareTo("00")!=0)
                           oEpi.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                         else
                           oEpi.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                     }
                   }
                   oEpi.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(14));
                    arrPacHosp[i]= oEpi;
                }
            }
        }
        
        return arrPacHosp;
        
    }
    
    public EpisodioMedico[] buscarPacientesPorServicioHopitalizacion2(int nClaveArea) throws Exception{
        EpisodioMedico arrPacHosp[]=null, oEpi=null;
        ArrayList rst=null;
        String sQuery="", edad="";
        int i=0;
        if(nClaveArea==0){
            throw new Exception("EpisodioMedico.BuscarPacientesPorServicioHopitalizacion: ERROR, Faltan Datos");
        }else{
            sQuery="SELECT * FROM  buscarPacientePorServicioHospitalizacion("+nClaveArea+"::bigint);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                arrPacHosp= new EpisodioMedico[rst.size()];
                for(i=0; i<rst.size();i++){
                    ArrayList vRowTemp= (ArrayList)rst.get(i);
                    oEpi= new EpisodioMedico();
                    oEpi.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oEpi.getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oEpi.getPaciente().setNombres((String)vRowTemp.get(2));
                    oEpi.getPaciente().setApPaterno((String)vRowTemp.get(3));
                    oEpi.getPaciente().setApMaterno((String)vRowTemp.get(4));
                    oEpi.getPaciente().setSexoP((String)vRowTemp.get(5));
                    oEpi.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(6)).intValue());
                    oEpi.setFechaIngreso((Date)vRowTemp.get(7));
                    oEpi.getCama().setNumero((String)vRowTemp.get(8));
                    oEpi.getArea().setClave(((Double)vRowTemp.get(9)).intValue());
                    oEpi.getArea().setDescripcion((String)vRowTemp.get(10));
                    edad=(String)vRowTemp.get(11);
                    if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                       if (edad.charAt(0)=='0')
                           oEpi.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                           oEpi.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                         if (edad.substring(4, 6).compareTo("00")!=0)
                           oEpi.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                         else
                           oEpi.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                     }
                   }
                   oEpi.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(13));
                    arrPacHosp[i]= oEpi;
                }
            }
        }
        
        return arrPacHosp;
        
    }
/****TERMINAN METODOS CREADOS POR JOSE JAVIER GONZALEZ VALLEJO TERCERA FACE */
    
    public void buscaepisodioabierto(Paciente oPac) throws Exception{
	ArrayList rst = null;
	String sQuery = ""; 
        String sestaSalud; 

		sQuery = "SELECT * FROM buscaepisodiomedicopaciente ("+oPac.getFolioPaciente()+" ::BIGINT);";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);  
                            this.setFechaIngreso((Date)vRowTemp.get(0)); 
                            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy"); 
                            this.setFIngreso(fechaIng.format(this.getFechaIngreso())); 
                            this.setAreaServicioHRRBSTR(((Double) vRowTemp.get(1)).intValue());
                             oArea.buscarNombreArea(getAreaServicioHRRBSTR());
                            oDiagIngreso.setClave((String)vRowTemp.get(2));
                            oDiagIngreso.setDescripcionDiag((String)vRowTemp.get(3));
                            oDiagIngreso.getCauses().setClave((String)vRowTemp.get(4));
                            this.setClaveEpisodio(((Double) vRowTemp.get(5)).longValue());    
                            this.oCama.setNumero((String)vRowTemp.get(6));
                            this.getMedicoRecibe().setNoTarjeta(((Double)vRowTemp.get(7)).intValue());
                            this.getMedicoRecibe().buscarDatosMedico();
                            this.getMedicoTratante().setNoTarjeta(((Double)vRowTemp.get(8)).intValue());
                            this.getMedicoTratante().buscarDatosMedico();
                             //String destino=(String)vRowTemp.get(9);
                            //if(destino.compareTo("")!=0){ 
                            ///this.getDestino().setValor(this.getDestino().buscaValorParametrizado(destino));
                            /// }
                            //else{
                             ///this.getDestino().setValor("");
                              ///}
                            //this.setDocEntregada((char)vRowTemp.get(10));
                             //String motivoegre=(String)vRowTemp.get(11);
                            /* if(motivoegre.compareTo("")!=0){
                               this.getMotivoEgreso().setValor(this.getMotivoEgreso().buscaValorParametrizado(motivoegre));
                             }else{
                                this.getMotivoEgreso().setValor("");} */
                                this.setRazonAltaVolunTrasl((String)vRowTemp.get(12));
                                this.setAvisoMinisterioDefun((String)vRowTemp.get(13));
                                ///this.setPrimeraVezEsp((boolean)vRowTemp.get(14));
                                ///this.setPrimeraVezEsp((boolean)vRowTemp.get(15));
                                /// String estadsalud=(String)vRowTemp.get(16);
                             ///if(estadsalud.compareTo("")!=0){ 
                               /*this.getEdoSalud().setValor(this.getEdoSalud().buscaValorParametrizado(estadsalud));
                             }else{
                                this.getEdoSalud().setValor("");}*/  
                               this.oArea.buscarNombreArea(getAreaServicioHRRBSTR());
                              //this.setTipoAfePrinc((char)vRowTemp.get(18));
                              //this.setInfeccionIntrahospitalaria((char)vRowTemp.get(19));
                               this.setSubServicioHRRB(((Double) vRowTemp.get(20)).intValue());
                               this.oArea.buscarSubServiciosHosp(getSubServicioHRRB());
                               this.getPrograma().setClave(((Double) vRowTemp.get(21)).intValue());
	} 
 }
 
    public void buscaDatosaltapaciente() throws Exception{
    ArrayList rst = null;
    String sQuery = "";  

        sQuery = "SELECT * FROM buscaepisodiomedicopacientealtamedica("+
                this.oPaciente.getFolioPaciente()+" ::BIGINT);";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null) {
            ArrayList vRowTemp = (ArrayList)rst.get(0); 
            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy"); 
            this.setAltaHospitalaria((Date)vRowTemp.get(0));  
            this.setFechaIngreso((Date)vRowTemp.get(1));  
            this.setFIngreso(fechaIng.format(this.getFechaIngreso()));
            this.setAreaServicioHRRBSTR(((Double) vRowTemp.get(2)).intValue());
            oArea.buscarNombreArea(getAreaServicioHRRBSTR());
            oDiagIngreso.setClave((String)vRowTemp.get(3));
            oDiagIngreso.setDescripcionDiag((String)vRowTemp.get(4));
            oDiagIngreso.getCauses().setClave((String)vRowTemp.get(5));
            this.setClaveEpisodio(((Double) vRowTemp.get(6)).longValue());
            this.oCama.setNumero((String)vRowTemp.get(7));
            this.getMedicoRecibe().setNoTarjeta(((Double)vRowTemp.get(8)).intValue());
            this.getMedicoRecibe().buscarDatosMedico();
            this.getMedicoTratante().setNoTarjeta(((Double)vRowTemp.get(9)).intValue());
            this.getMedicoTratante().buscarDatosMedico();
            this.setSubServicioHRRB(((Double) vRowTemp.get(21)).intValue());
            this.oArea.buscarSubServiciosHosp(getSubServicioHRRB());
            this.getPrograma().setClave(((Double) vRowTemp.get(22)).intValue()); 
	} 
    }
    
    public EpisodioMedico[] reportEstudEsp(Date fechInici,Date fechFinal) 
            throws Exception{
    EpisodioMedico arrRet[]=null, oepi=null;
    SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
    String fechainicail =df.format(fechInici);
    String fechaFinal=df.format(fechFinal); 
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
        sQuery = "select * from buscaTodosEstRealEspLabRepDep("+
                this.isNull(this.getPaciente().getNombres().toUpperCase())+","+
                this.isNull(this.getPaciente().getApPaterno().toUpperCase())+","+
                this.isNull(this.getPaciente().getApMaterno().toUpperCase())+","+
                this.numnull(this.getPaciente().getExpediente().getNumero())+",'"+
                fechainicail+"','"+fechaFinal+"') order by poutdfecharesultadores desc;";  
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null) {
            arrRet = new EpisodioMedico[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oepi=new EpisodioMedico(); 
                oepi.setEstudioEspLab(new EstudioEspLab());
                ArrayList vRowTemp = (ArrayList)rst.get(i); 
                oepi.getPaciente().setNombres((String)vRowTemp.get(0));
                oepi.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oepi.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oepi.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                oepi.getMedicoTratante().setNoTarjeta(((Double) vRowTemp.get(4)).intValue());
                oepi.getMedicoTratante().buscarMedicoCapasits(); 
                oepi.getEstudioEspLab().setDescripcion((String)vRowTemp.get(5));
                oepi.getEstudioEspLab().setLabDestino((String)vRowTemp.get(6));
                oepi.getEstudioEspLab().setFechaEnvio((Date)vRowTemp.get(7));
                oepi.getEstudioEspLab().setFechaResult((Date)vRowTemp.get(8));
                oepi.getEstudioEspLab().getAutorizadoPor().setNoTarjeta(((Double) vRowTemp.get(9)).intValue());
                oepi.getEstudioEspLab().getAutorizadoPor().buscarCapa();
                oepi.getEstudioEspLab().setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(10)).intValue()));
                arrRet[i]=oepi;
            } 
        } 
        return arrRet; 
    }
    
    public EpisodioMedico[] reporteDeMaterialOsteo(Date fechInici,
            Date fechFinal) throws Exception{
    SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
    String fechainicail =df.format(fechInici);
    String fechaFinal=df.format(fechFinal);  
    ArrayList rst = null; 
    EpisodioMedico arrRet[]=null, epi=null;
    String sQuery = "";     
    int i=0,nTam=0;
    List<EpisodioMedico> vObj=null;
        sQuery = "select * from buscaTodosMaterialEspeciDep("+
                this.isNull(this.getPaciente().getNombres().toUpperCase())+","+
                this.isNull(this.getPaciente().getApPaterno().toUpperCase())+","+
                this.isNull(this.getPaciente().getApMaterno().toUpperCase())+","+
                this.numnull(this.getPaciente().getExpediente().getNumero())+",'"+
                fechainicail+"','"+fechaFinal+"')order by poutdfechaemisionres desc;"; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null) {
            vObj = new ArrayList<EpisodioMedico>();
            for (i=0; i<rst.size(); i++){
                epi=new  EpisodioMedico(); 
                epi.setValeMaterial(new ValeMaterial());
                epi.arrValesMaterial=new ArrayList();
                ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);  
                epi.getPaciente().setNombres((String)vRowTemp.get(0));
                epi.getPaciente().setApPaterno((String)vRowTemp.get(1));
                epi.getPaciente().setApMaterno((String)vRowTemp.get(2));
                epi.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                epi.getMedicoTratante().setNoTarjeta(((Double) vRowTemp.get(4)).intValue());
                epi.getMedicoTratante().buscarMedicoCapasits(); 
                epi.getArea().setDescripcion(epi.getArea().buscarNombreArea(((Double) vRowTemp.get(5)).intValue()));
                epi.getValeMaterial().getArrDetalle().get(0).getMaterial().setNombre((String)vRowTemp.get(6));
                epi.getValeMaterial().setFechaEmision((Date)vRowTemp.get(7));
                epi.getValeMaterial().setFechaSurtido((Date)vRowTemp.get(8));
                epi.getValeMaterial().getProcReal().setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(9)).intValue()));
                epi.getValeMaterial().getProcReal().getSitPago().setValor((String)vRowTemp.get(10));         
              vObj.add(epi); 
            } 
                    
            nTam = vObj.size();
            arrRet = new EpisodioMedico[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }                         
        }
        return arrRet;
    } 
    
    public EpisodioMedico[] buscaTabuladorNivelesPorEpisodio(Date fechInici,Date fechFinal) throws Exception{
    SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
    String fechainicail =df.format(fechInici);
    String fechaFinal=df.format(fechFinal);  
    ArrayList rst = null;
    EpisodioMedico arrRet[]=null, oPh=null;
    String sQuery = "";     
    int i=0,nTam=0; 
    List<EpisodioMedico> vObj=null;
        sQuery = "select * from buscaReporteNiveles('"+fechainicail+"','"+fechaFinal+"',"+ isNull(this.oPaciente.getApPaterno()) +","+ isNull(this.oPaciente.getApMaterno())+","+ isNull(this.oPaciente.getNombres() )+","+numnull(this.oPaciente.getExpediente().getNumero())+");"; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null) {
            vObj = new ArrayList<EpisodioMedico>();
            for (i=0; i<rst.size(); i++){  
                ArrayList<Object> vRowTemp = (ArrayList) rst.get(i);
                oPh=new  EpisodioMedico(); 
                oPh.setPaciente(new Paciente());
                oPh.oPaciente.setNombres((String)vRowTemp.get(0));
                oPh.oPaciente.setApPaterno((String)vRowTemp.get(1));
                oPh.oPaciente.setApMaterno((String)vRowTemp.get(2));
                oPh.oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                oPh.oPaciente.setNivelSocioEco(new Parametrizacion());
                oPh.oPaciente.getNivelSocioEco().setClaveParametro((String)vRowTemp.get(4));  
                oPh.getArea().setDescripcion((String)vRowTemp.get(5)); 
                oPh.setSubServicioHRRBSTR((String)vRowTemp.get(6));
                oPh.setFechaIngreso((Date)vRowTemp.get(7));
                oPh.setFIngreso(df.format(oPh.getFechaIngreso())); 
                vObj.add(oPh); 
            }
            nTam = vObj.size();
            arrRet = new EpisodioMedico[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }                         
        }
        return arrRet;
    } 
     
    public EpisodioMedico[] buscaEpisodiosAnteriores() throws Exception{
    ArrayList rst = null;
    String edad;
    EpisodioMedico arrRet[]=null, epi=null;
    String sQuery = "";     
    int i=0,nTam=0;
    List<EpisodioMedico> vObj=null;
        sQuery = "select * from buscaepisodiosAnterioresPaci("+this.getPaciente().getFolioPaciente()+" ::BIGINT);"; 
        
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        
        if (rst != null) {
            vObj = new ArrayList<EpisodioMedico>();
            for (i=0; i<rst.size(); i++){
                epi=new  EpisodioMedico();
                ArrayList<Object> vRowTemp = (ArrayList)rst.get(i); 
                epi.setClaveEpisodio(((Double) vRowTemp.get(0)).intValue());
                epi.setAltaHospitalaria((Date)vRowTemp.get(1));
                epi.setFechaIngreso((Date)vRowTemp.get(2));
                epi.setAreaServicioHRRB((String)vRowTemp.get(3));
                epi.getCama().setNumero((String)vRowTemp.get(4));
                edad=(String)vRowTemp.get(5);
                if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                        if (edad.charAt(0)=='0')
                            epi.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                            epi.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                            epi.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                            epi.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                }
                vObj.add(epi); 
            }
            nTam = vObj.size();
            arrRet = new EpisodioMedico[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }                         
        }
        return arrRet;
    } 
    
 
    public String numnull(int num){
        if(num==0){
            return null;
        }
        else{
            return ""+num;
        }
    } 
    /* ******************************************************* */
    public void setAfeResAPDef(AfeccionTratada oAfeResAPDef) {
        this.oAfeResAPDef = oAfeResAPDef;
    }

    public ProcedimientosRealizados getProceRe1() {
        return oProceRe1;
    }

    public void setProceRe1(ProcedimientosRealizados oProceRe1) {
        this.oProceRe1 = oProceRe1;
    }

    public ProcedimientosRealizados getProceRe2() {
        return oProceRe2;
    }

    public void setProceRe2(ProcedimientosRealizados oProceRe2) {
        this.oProceRe2 = oProceRe2;
    }

    public ProcedimientosRealizados getProceRe3() {
        return oProceRe3;
    }

    public void setProceRe3(ProcedimientosRealizados oProceRe3) {
        this.oProceRe3 = oProceRe3;
    }

    public ProcedimientosRealizados getProceRe4() {
        return oProceRe4;
    }

    public void setProceRe4(ProcedimientosRealizados oProceRe4) {
        this.oProceRe4 = oProceRe4;
    }

    public ProcedimientosRealizados getProceRe5() {
        return oProceRe5;
    }

    public void setProceRe5(ProcedimientosRealizados oProceRe5) {
        this.oProceRe5 = oProceRe5;
    }

    public ProcedimientosRealizados getProceRe6() {
        return oProceRe6;
    }

    public void setProceRe6(ProcedimientosRealizados oProceRe6) {
        this.oProceRe6 = oProceRe6;
    }

    public ProcedimientosRealizados getProceRe7() {
        return oProceRe7;
    }

    public void setProceRe7(ProcedimientosRealizados oProceRe7) {
        this.oProceRe7 = oProceRe7;
    }

    public ProcedimientosRealizados getProceRe8() {
        return oProceRe8;
    }

    public void setProceRe8(ProcedimientosRealizados oProceRe8) {
        this.oProceRe8 = oProceRe8;
    }

    public char getTipoAfePrinc() {
        return sTipoAfePrinc;
    }

    public void setTipoAfePrinc(char sTipoAfePrinc) {
        this.sTipoAfePrinc = sTipoAfePrinc;
    }

    public MedicamentoAplicado getMedicamento1() {
        return oMedicamento1;
    }

    public void setMedicamento1(MedicamentoAplicado oMedicamento1) {
        this.oMedicamento1 = oMedicamento1;
    }

    public MedicamentoAplicado getMedicamento2() {
        return oMedicamento2;
    }

    public void setMedicamento2(MedicamentoAplicado oMedicamento2) {
        this.oMedicamento2 = oMedicamento2;
    }

    public MedicamentoAplicado getMedicamento3() {
        return oMedicamento3;
    }

    public void setMedicamento3(MedicamentoAplicado oMedicamento3) {
        this.oMedicamento3 = oMedicamento3;
    }

    public char getInfeccionIntrahospitalaria() {
        return sInfeccionIntrahospitalaria;
    }

    public void setInfeccionIntrahospitalaria(char sInfeccionIntrahospitalaria) {
        this.sInfeccionIntrahospitalaria = sInfeccionIntrahospitalaria;
    }

    public String getFIngreso() {
        return sFIngreso;
    }

    public void setFIngreso(String sFIngreso) {
        this.sFIngreso = sFIngreso;
    }

    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    public AfeccionTratada getAfePrincipal() {
        return oAfePrincipal;
    }

    public void setAfePrincipal(AfeccionTratada oAfePrincipal) {
        this.oAfePrincipal = oAfePrincipal;
    }

    /**
     * @return the oProgActual
     */
    public Programa getoProgActual() {
        return oProgActual;
    }

    /**
     * @param oProgActual the oProgActual to set
     */
    public void setoProgActual(Programa oProgActual) {
        this.oProgActual = oProgActual;
    }

    /**
     * @return the arrEstudiosRealizados
     */
    public ArrayList<EstudioRealizado> getEstudiosRealizados() {
        return arrEstudiosRealizados;
    }   

    /**
     * @param arrEstudiosRealizados the arrEstudiosRealizados to set
     */
    public void setEstudiosRealizados(ArrayList<EstudioRealizado> valor) {
        this.arrEstudiosRealizados = valor;
    }

    /**
     * @return the arrSolicitudesOtrosServicios
     */
    public ArrayList<SolicitudOtroServicio> getSolicitudesOtrosServicios() {
        return arrSolicitudesOtrosServicios;
    }

    /**
     * @param arrSolicitudesOtrosServicios the arrSolicitudesOtrosServicios to set
     */
    public void setSolicitudesOtrosServicios(ArrayList<SolicitudOtroServicio> valor) {
        this.arrSolicitudesOtrosServicios = valor;
    }

    /**
     * @return the arrProcsRealizados
     */
    public ArrayList<ProcedimientosRealizados> getProcsRealizados() {
        return arrProcsRealizados;
    }

    /**
     * @param arrProcsRealizados the arrProcsRealizados to set
     */
    public void setProcsRealizados(ArrayList<ProcedimientosRealizados> valor) {
        this.arrProcsRealizados = valor;
    }

    /**
     * @return the arrValesMaterial
     */
    public ArrayList<ValeMaterial> getValesMaterial() {
        return arrValesMaterial;
    }

    /**
     * @param arrValesMaterial the arrValesMaterial to set
     */
    public void setValesMaterial(ArrayList<ValeMaterial> valor) {
        this.arrValesMaterial = valor;
    }

    /**
     * @return the arrDetallesValesColectivos
     */
    public ArrayList<DetalleValeColectivo> getDetallesValesColectivos() {
        return arrDetallesValesColectivos;
    }

    /**
     * @param arrDetallesValesColectivos the arrDetallesValesColectivos to set
     */
    public void setDetallesValesColectivos(ArrayList<DetalleValeColectivo> valor) {
        this.arrDetallesValesColectivos = valor;
    }

    /**
     * @return the arrRecetas
     */
    public ArrayList<Receta> getRecetas() {
        return arrRecetas;
    }

    /**
     * @param arrRecetas the arrRecetas to set
     */
    public void setRecetas(ArrayList<Receta> valor) {
        this.arrRecetas = valor;
    }

    /**
     * @return the arrDetallesRecetariosColectivos
     */
    public ArrayList<DetalleRecetarioColectivo> getDetallesRecetariosColectivos() {
        return arrDetallesRecetariosColectivos;
    }

    /**
     * @param arrDetallesRecetariosColectivos the arrDetallesRecetariosColectivos to set
     */
    public void setDetallesRecetariosColectivos(ArrayList<DetalleRecetarioColectivo> valor) {
        this.arrDetallesRecetariosColectivos = valor;
    }

    /**
     * @return the arrNotasMedicasHRRB
     */
    public ArrayList<NotaMedicaHRRB> getNotasMedicasHRRB() {
        return arrNotasMedicasHRRB;
    }

    /**
     * @param arrNotasMedicasHRRB the arrNotasMedicasHRRB to set
     */
    public void setNotasMedicasHRRB(ArrayList<NotaMedicaHRRB> valor) {
        this.arrNotasMedicasHRRB = valor;
    }

    /**
     * @return the arrSolicitudesSangre
     */
    public ArrayList<SolicitudSangre> getSolicitudesSangre() {
        return arrSolicitudesSangre;
    }

    /**
     * @param arrSolicitudesSangre the arrSolicitudesSangre to set
     */
    public void setSolicitudesSangre(ArrayList<SolicitudSangre> valor) {
        this.arrSolicitudesSangre = valor;
    }

    /**
     * @return the arrMedicamentosAplicados
     */
    public ArrayList<MedicamentoAplicado> getMedicamentosAplicados() {
        return arrMedicamentosAplicados;
    }

    /**
     * @param arrMedicamentosAplicados the arrMedicamentosAplicados to set
     */
    public void setMedicamentosAplicados(ArrayList<MedicamentoAplicado> valor) {
        this.arrMedicamentosAplicados = valor;
    }
////////////////////////////////////////////////////////////////////////////////    

////////////////////////////////////////////////////////////////////////////////    

    public NotaMedica getNota() {
        return oNota;
    }

    public void setNota(NotaMedica oNota) {
        this.oNota = oNota;
    }

    public AdmisionUrgs getAdmUrgs() {
        return oAdmUrgs;
    }

    public void setAdmUrgs(AdmisionUrgs oAdmUrgs) {
        this.oAdmUrgs = oAdmUrgs;
    }
    
    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }

    public EstudioInterconsulta getEstInter() {
        return oEstInter;
    }

    public void setEstInter(EstudioInterconsulta oEstInter) {
        this.oEstInter = oEstInter;
    }

    public NotaMedicaHRRB getNotaMedHrrb() {
        return oNotaMedHrrb;
    }

    public void setNotaMedHrrb(NotaMedicaHRRB oNotaMedHrrb) {
        this.oNotaMedHrrb = oNotaMedHrrb;
    }
    
    public Programa getPrograma() {
        return oPrograma;
    }

    public void setPrograma(Programa oProgramas) {
        this.oPrograma = oProgramas;
    }
    
    public AdmisionUrgs getAdmisionUrgs(){
        return oAdmisionUrgs;
    }
    
    public void setAdmisionUrgs (AdmisionUrgs val){
        this.oAdmisionUrgs = val;
    }
    
    public ValeMaterial getValeMaterial() {
        return oValeMaterial;
    }

    public void setValeMaterial(ValeMaterial oValeMaterial) {
        this.oValeMaterial = oValeMaterial;
    }
    
    public EstudioEspLab getEstudioEspLab() {
        return oEstudioEspLab;
    }

    public void setEstudioEspLab(EstudioEspLab oEstudioEspLab) {
        this.oEstudioEspLab = oEstudioEspLab;
    }
    
    public ServicioRealizado[] getServiciosRealizados() {
        return arrServiciosRealizados;
    }

    public void setServiciosRealizados(ServicioRealizado[] arrServiciosRealizados) {
        this.arrServiciosRealizados = arrServiciosRealizados;
    }
}
