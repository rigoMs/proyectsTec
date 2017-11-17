package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class PersonalHospitalario extends Persona implements Serializable {

    protected int nNoTarjeta;
    protected String sActividad;
    protected String sCedProf;
    protected String sCedEsp;
    protected String sCedIMSS;
    protected boolean bStatusPersonal;
    protected String sIdUsuario;
    protected Parametrizacion oTipoContrato;
    protected Parametrizacion oTipoPersonal;
    protected String sFoto;

    //JMHG
    protected int nAnoRegCredElector;
    protected Parametrizacion oActivo;
    protected Date dFechaRegistro;
    protected String sRFC;
    protected Parametrizacion oTipoEmpleado;
    protected String sCalleNum;
    protected String sColonia;
    protected Estado oEstado;
    protected Municipio oMunicipio;
    protected Ciudad oCiudad;
    protected CiudadCP oCiudadCP;
    protected String sOtroPais;
    protected int nNoTarjetaBase;
    protected Date dFechaCambioActivo;
    protected String sSinergia;

    //AGREGADOS: 10/04/17 (JMHG)
    protected String sNumExterior;
    protected String sNumInterior;
    protected TipoVialidad oTipoVialidad;
    protected TipoAsentamiento oTipoAsentamiento;
    //====

    //Emmanuel    
    protected String sClavpeus;
    protected String sPuesto;
    //====

    protected AccesoDatos oAD;
    protected PersonalAreaServ oPersAreaServ;
    protected String sStatus;
    protected Pais oPais;
    protected String sIdUsuarioFirmado;
    protected DocumentoDigital oDocumentoDigital;
    protected CedulaEspecialidad oCedulaEspecialidad;
    protected CapacitacionPersonal oCapacitacionPersonal;
    protected List<CedulaEspecialidad> lCedulaEspecialidad;
    protected List<CedulaEspecialidad> lCedulaEspecialidadOld;
    protected List<CapacitacionPersonal> lCapacitacionPersonal;
    protected List<CapacitacionPersonal> lCapacitacionPersonalOld;

    protected ArrayList arrAreaServicioHRRB;
    protected String sCvePerfil;
    protected Usuario oUsuar; //CAPASITS
    public static final String PUESTO_CAJERO = "CAJCAJ";

    public PersonalHospitalario( boolean bStatusPersonal, int nNoTarjeta, Parametrizacion oTipoContrato, Parametrizacion oTipoPersonal, String sActividad, String sCedEsp, String sCedIMSS, String sCedProf, String sFoto, ArrayList arrAreaServicioHRRB, Date dFechaNac, int nEdad, Parametrizacion oEstadoCivil, String sApMaterno, String sApPaterno, String sCurp, String sNombres, String sTelefono, String sSexo, String sStatus ) {
        super( dFechaNac, nEdad, oEstadoCivil, sApMaterno, sApPaterno, sCurp, sNombres, sTelefono, sSexo );
        this.bStatusPersonal = bStatusPersonal;
        this.nNoTarjeta = nNoTarjeta;
        this.oTipoContrato = oTipoContrato;
        this.oTipoPersonal = oTipoPersonal;
        this.sActividad = sActividad;
        this.sCedEsp = sCedEsp;
        this.sCedIMSS = sCedIMSS;
        this.sCedProf = sCedProf;
        this.sFoto = sFoto;
        this.arrAreaServicioHRRB = arrAreaServicioHRRB;
        this.sStatus = sStatus;
    }

    public PersonalHospitalario() {
        nNoTarjeta = 0;
        sActividad = "";
        sCedEsp = "";
        sCedIMSS = "";
        sCedProf = "";
        sIdUsuario = "";
        oTipoPersonal = new Parametrizacion();
        oTipoContrato = new Parametrizacion();
        sFoto = "";

        nAnoRegCredElector = 0;
        oActivo = new Parametrizacion();
        dFechaRegistro = new Date();
        sRFC = "";
        oTipoEmpleado = new Parametrizacion();
        sCalleNum = "";
        sColonia = "";
        oEstado = new Estado();
        oMunicipio = new Municipio();
        oCiudad = new Ciudad();
        oCiudadCP = new CiudadCP();
        sOtroPais = "";

        oPersAreaServ = new PersonalAreaServ();

        sStatus = "";
        oPais = new Pais();
        sIdUsuarioFirmado = "";
        oDocumentoDigital = new DocumentoDigital();
        oCedulaEspecialidad = new CedulaEspecialidad();
        nNoTarjetaBase = 0;
        lCedulaEspecialidad = null;
        lCedulaEspecialidadOld = new ArrayList<>();

        oCapacitacionPersonal = new CapacitacionPersonal();
        lCapacitacionPersonal = null;
        lCapacitacionPersonalOld = new ArrayList<>();
        dFechaCambioActivo = null;
        sSinergia = "";

        sNumExterior = "";
        sNumInterior = "";
        oTipoVialidad = new TipoVialidad();
        oTipoAsentamiento = new TipoAsentamiento();
    }

    @Override
    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( getNoTarjeta() < 1 ) {
            throw new Exception( "PersonalHospitalario.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalHospitalario( " + getNoTarjeta() + "::INTEGER );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                setActividad( (String) vRowTemp.get( 1 ) );
                setCedProf( (String) vRowTemp.get( 2 ) );
                setCedEsp( (String) vRowTemp.get( 3 ) );
                setCedIMSS( (String) vRowTemp.get( 4 ) );
                setNombres( (String) vRowTemp.get( 5 ) );
                setApPaterno( (String) vRowTemp.get( 6 ) );
                setApMaterno( (String) vRowTemp.get( 7 ) );
                setFechaNac( (Date) vRowTemp.get( 8 ) );
                setSexos( (String) vRowTemp.get( 9 ) );
                setCurp( (String) vRowTemp.get( 10 ) );
                setStatus( (String) vRowTemp.get( 11 ) );
                setIdUsuario( (String) vRowTemp.get( 12 ) );
                getTipoContrato().asignaValorParametrizado( (String) vRowTemp.get( 13 ) );
                getEstadoCivil().asignaValorParametrizado( (String) vRowTemp.get( 14 ) );
                setTelefono( (String) vRowTemp.get( 15 ) );
                getTipoPersonal().asignaValorParametrizado( (String) vRowTemp.get( 16 ) );
                setFoto( (String) vRowTemp.get( 17 ) );
                setAnoRegCredElector( ((Double) vRowTemp.get( 18 )).intValue() );
                setFechaRegistro( (Date) vRowTemp.get( 20 ) );
                setRFC( (String) vRowTemp.get( 21 ) );
                getTipoEmpleado().asignaValorParametrizado( (String) vRowTemp.get( 22 ) );
                setCalleNum( (String) vRowTemp.get( 23 ) );
                setColonia( (String) vRowTemp.get( 24 ) );
                getEstado().setClaveEdo( (String) vRowTemp.get( 25 ) );
                getMunicipio().setClaveMun( (String) vRowTemp.get( 26 ) );
                getCiudad().setClaveCiu( (String) vRowTemp.get( 27 ) );
                getCiudadCP().setCp( (String) vRowTemp.get( 28 ) );
                setOtroPais( (String) vRowTemp.get( 29 ) );
                setNoTarjetaBase( ((Double) vRowTemp.get( 30 )).intValue() );
                setFechaCambioActivo( (Date) vRowTemp.get( 31 ) );
                setSinergia( (String) vRowTemp.get( 32 ) );
                setNumExterior( (String) vRowTemp.get( 33 ) );
                setNumInterior( (String) vRowTemp.get( 34 ) );
                getTipoVialidad().setCveTipoVial( (String) vRowTemp.get( 35 ) );
                getTipoAsentamiento().setCveAsenta( ((Double) vRowTemp.get( 36 )).intValue() );
                bRet = true;
            }
        }
        return bRet;
    }

    public boolean buscaUsuarioFirmado() throws Exception {
        boolean bFlag = false;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        List<AdmisionUrgs> lAdmisionUrgs = null;

        sQuery = "SELECT * FROM buscaUsuarioUrgs('" + sIdUsuario + "');";
        System.out.println( sQuery );

        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        return bFlag;
    }

    public boolean buscarPersonalPorCurp() throws Exception {
        boolean bRet = false;
        String sQuery = "";
        ArrayList rst = null;
        if( getCurp().isEmpty() ) {
            throw new Exception( "PersonalHospitalario.buscarPersonalPorCurp: Error de programación, faltan datos" );
        } else {
            oAD = new AccesoDatos();
            sQuery = "SELECT * FROM buscarPorCurp( '" + getCurp() + "', 0::INTEGER );";
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNombres( (String) vRowTemp.get( 0 ) );
                setApPaterno( (String) vRowTemp.get( 1 ) );
                setApMaterno( (String) vRowTemp.get( 2 ) );
                setNoTarjeta( ((Double) vRowTemp.get( 3 )).intValue() );
                setFechaNac( (Date) vRowTemp.get( 4 ) );
                getPersonalAreaServ().getPuesto().setDescripcion( (String) vRowTemp.get( 5 ) );
                getPersonalAreaServ().getAreaServ().setDescripcion( (String) vRowTemp.get( 6 ) );
                setCurp( (String) vRowTemp.get( 7 ) );
                setActividad( (String) vRowTemp.get( 8 ) );
                getPersonalAreaServ().getTurno().setDescripcion( (String) vRowTemp.get( 9 ) );
                bRet = true;
            }
        }

        return bRet;
    }

    @Override
    public PersonalHospitalario[] buscarTodos() throws Exception {
        PersonalHospitalario arrRet[] = null, oPersonalHospitalario = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosPersonalHospitalario();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            arrRet = new PersonalHospitalario[rst.size()];
            for( i = 0; i < rst.size(); i++ ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                oPersonalHospitalario = new PersonalHospitalario();
                oPersonalHospitalario.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPersonalHospitalario.setActividad( (String) vRowTemp.get( 1 ) );
                oPersonalHospitalario.setCedProf( (String) vRowTemp.get( 2 ) );
                oPersonalHospitalario.setCedEsp( (String) vRowTemp.get( 3 ) );
                oPersonalHospitalario.setCedIMSS( (String) vRowTemp.get( 4 ) );
                oPersonalHospitalario.setNombres( (String) vRowTemp.get( 5 ) );
                oPersonalHospitalario.setApPaterno( (String) vRowTemp.get( 6 ) );
                oPersonalHospitalario.setApMaterno( (String) vRowTemp.get( 7 ) );
                oPersonalHospitalario.setFechaNac( (Date) vRowTemp.get( 8 ) );
                oPersonalHospitalario.setSexos( (String) vRowTemp.get( 9 ) );
                oPersonalHospitalario.setCurp( (String) vRowTemp.get( 10 ) );
                oPersonalHospitalario.setStatus( (String) vRowTemp.get( 11 ) );
                oPersonalHospitalario.setIdUsuario( (String) vRowTemp.get( 12 ) );
                oPersonalHospitalario.getTipoContrato().asignaValorParametrizado( (String) vRowTemp.get( 13 ) );
                oPersonalHospitalario.getEstadoCivil().asignaValorParametrizado( (String) vRowTemp.get( 14 ) );
                oPersonalHospitalario.setTelefono( (String) vRowTemp.get( 15 ) );
                oPersonalHospitalario.getTipoPersonal().asignaValorParametrizado( (String) vRowTemp.get( 16 ) );
                oPersonalHospitalario.setFoto( (String) vRowTemp.get( 17 ) );
                oPersonalHospitalario.setAnoRegCredElector( ((Double) vRowTemp.get( 18 )).intValue() );
                oPersonalHospitalario.setFechaRegistro( (Date) vRowTemp.get( 20 ) );
                oPersonalHospitalario.setRFC( (String) vRowTemp.get( 21 ) );
                oPersonalHospitalario.getTipoEmpleado().asignaValorParametrizado( (String) vRowTemp.get( 22 ) );
                oPersonalHospitalario.setCalleNum( (String) vRowTemp.get( 23 ) );
                oPersonalHospitalario.setColonia( (String) vRowTemp.get( 24 ) );
                oPersonalHospitalario.getEstado().setClaveEdo( (String) vRowTemp.get( 25 ) );
                oPersonalHospitalario.getMunicipio().setClaveMun( (String) vRowTemp.get( 26 ) );
                oPersonalHospitalario.getCiudad().setClaveCiu( (String) vRowTemp.get( 27 ) );
                oPersonalHospitalario.getCiudadCP().setCp( (String) vRowTemp.get( 28 ) );
                oPersonalHospitalario.setOtroPais( (String) vRowTemp.get( 29 ) );
                oPersonalHospitalario.setNoTarjetaBase( ((Double) vRowTemp.get( 30 )).intValue() );
                oPersonalHospitalario.setFechaCambioActivo( (Date) vRowTemp.get( 31 ) );
                oPersonalHospitalario.setSinergia( (String) vRowTemp.get( 32 ) );
                oPersonalHospitalario.setNumExterior( (String) vRowTemp.get( 33 ) );
                oPersonalHospitalario.setNumInterior( (String) vRowTemp.get( 34 ) );
                oPersonalHospitalario.getTipoVialidad().setCveTipoVial( (String) vRowTemp.get( 35 ) );
                oPersonalHospitalario.getTipoAsentamiento().setCveAsenta( ((Double) vRowTemp.get( 36 )).intValue() );
                arrRet[i] = oPersonalHospitalario;
            }
        }
        return arrRet;
    }

    public PersonalHospitalario[] buscarTodosFiltro() throws Exception {
        PersonalHospitalario arrRet[] = null, oPersHosp = null;
        ArrayList rst = null;
        String sQuery = "";
        if( getPersonalAreaServ() == null
                || getPersonalAreaServ().getPersonalGrupo() == null
                || getPersonalAreaServ().getAreaServ() == null
                || getPersonalAreaServ().getPuesto() == null ) {
            throw new Exception( "PersonalHospitalario.buscarTodosFiltro: Error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaTodosPersonalHospitalarioFiltro( "
                    + "'" + getPersonalAreaServ().getPersonalGrupo().getClaveGrupo() + "', "
                    + getPersonalAreaServ().getAreaServ().getClave() + "::SMALLINT, "
                    + "'" + getPersonalAreaServ().getPuesto().getClave() + "', "
                    + "'" + getTipoEmpleado().getClaveParametro() + getTipoEmpleado().getTipoParametro() + "', "
                    + "'" + (nNoTarjetaBase == -1 ? "1" : "") + "' );";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null ) {
                arrRet = new PersonalHospitalario[rst.size()];
                for( int i = 0; i < rst.size(); i++ ) {
                    ArrayList vRowTemp = (ArrayList) rst.get( i );
                    oPersHosp = new PersonalHospitalario();
                    oPersHosp.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                    oPersHosp.setCedProf( (String) vRowTemp.get( 1 ) );
                    oPersHosp.setNombres( (String) vRowTemp.get( 2 ) );
                    oPersHosp.setApPaterno( (String) vRowTemp.get( 3 ) );
                    oPersHosp.setApMaterno( (String) vRowTemp.get( 4 ) );
                    oPersHosp.setCurp( (String) vRowTemp.get( 5 ) );
                    oPersHosp.getActivo().setValor( (String) vRowTemp.get( 6 ) );
                    oPersHosp.setFechaRegistro( (Date) vRowTemp.get( 7 ) );
                    oPersHosp.setRFC( (String) vRowTemp.get( 8 ) );
                    oPersHosp.getTipoEmpleado().setValor( (String) vRowTemp.get( 9 ) );
                    oPersHosp.setFechaCambioActivo( (Date) vRowTemp.get( 10 ) );
                    oPersHosp.setSinergia( (String) vRowTemp.get( 11 ) );
                    oPersHosp.getPersonalAreaServ().getPersonalGrupo().setDescripcion( (String) vRowTemp.get( 12 ) );
                    oPersHosp.getPersonalAreaServ().getPuesto().setDescripcion( (String) vRowTemp.get( 13 ) );
                    oPersHosp.getPersonalAreaServ().getAreaServ().setDescripcion( (String) vRowTemp.get( 14 ) );
                    oPersHosp.getPersonalAreaServ().getAreaServ().setTipo( (String) vRowTemp.get( 15 ) );
                    arrRet[i] = oPersHosp;
                }
            }
        }
        return arrRet;
    }

    public ArrayList<PersonalHospitalario> buscarTodosSinUsuario() throws Exception {
        ArrayList<PersonalHospitalario> arrRet = null;
        PersonalHospitalario oPerfil = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;

        sQuery = "SELECT * FROM buscaTodosPersSinUsu();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            arrRet = new ArrayList<>();
            for( i = 0; i < rst.size(); i++ ) {
                oPerfil = new PersonalHospitalario();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oPerfil.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPerfil.setNombres( (String) vRowTemp.get( 1 ) );
                oPerfil.setApPaterno( (String) vRowTemp.get( 2 ) );
                oPerfil.setApMaterno( (String) vRowTemp.get( 3 ) );
                oPerfil.setFechaNac( (Date) vRowTemp.get( 4 ) );
                oPerfil.setStatus( (String) vRowTemp.get( 5 ) );
                arrRet.add( oPerfil );
            }
        }
        return arrRet;

    }

    public PersonalHospitalario[] buscarPorPuesto( String sCvePuesto ) throws Exception {
        PersonalHospitalario arrRet[] = null, oPersonalHospitalario = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaPorPuestoPersonalHospitalario('" + sCvePuesto + "'::character(10));";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            arrRet = new PersonalHospitalario[rst.size()];
            for( i = 0; i < rst.size(); i++ ) {
                oPersonalHospitalario = new PersonalHospitalario();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oPersonalHospitalario.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPersonalHospitalario.setNombres( (String) vRowTemp.get( 1 ) );
                oPersonalHospitalario.setApPaterno( (String) vRowTemp.get( 2 ) );
                oPersonalHospitalario.setApMaterno( (String) vRowTemp.get( 3 ) );
                arrRet[i] = oPersonalHospitalario;
            }
        }
        return arrRet;
    }

    public PersonalHospitalario[] buscarPorFiltro() throws Exception {
        PersonalHospitalario[] arrRet = null;
        ArrayList rst = null;
        String sQuery = "";
        oAD = new AccesoDatos();
        sQuery = "SELECT * FROM buscaPersonalHospitalarioFiltro("
                + getNoTarjeta() + "::INTEGER, "
                + "'" + getCedProf() + "', "
                + "'" + getRFC() + "', "
                + "'" + getNombres() + "', "
                + "'" + getApPaterno() + "', "
                + "'" + getApMaterno() + "', "
                + "'" + getCurp() + "', "
                + "'" + (nNoTarjetaBase == -1 ? "1" : "") + "' );";
        System.out.println( sQuery );
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            ArrayList vRowTemp = null;
            PersonalHospitalario oPersHosp = null;
            arrRet = new PersonalHospitalario[rst.size()];
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPersHosp = new PersonalHospitalario();
                oPersHosp.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPersHosp.setCedProf( (String) vRowTemp.get( 1 ) );
                oPersHosp.setRFC( (String) vRowTemp.get( 2 ) );
                oPersHosp.setNombres( (String) vRowTemp.get( 3 ) );
                oPersHosp.setApPaterno( (String) vRowTemp.get( 4 ) );
                oPersHosp.setApMaterno( (String) vRowTemp.get( 5 ) );
                oPersHosp.setCurp( (String) vRowTemp.get( 6 ) );
                arrRet[i] = oPersHosp;
            }
        }

        return arrRet;
    }

    public PersonalHospitalario[] buscarReporteVigenciaCredElector() throws Exception {
        ArrayList rst = null;
        PersonalHospitalario arrRet[] = null, oPers = null;
        String sQuery = "SELECT * FROM buscaPersonalHospitalarioVigenciaCredElector();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalHospitalario[rst.size()];
            ArrayList vRowTemp;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPers = new PersonalHospitalario();
                oPers.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPers.setNombres( (String) vRowTemp.get( 1 ) );
                oPers.setApPaterno( (String) vRowTemp.get( 2 ) );
                oPers.setApMaterno( (String) vRowTemp.get( 3 ) );
                oPers.setAnoRegCredElector( ((Double) vRowTemp.get( 4 )).intValue() );
                arrRet[i] = oPers;
            }
        }

        return arrRet;
    }

    public PersonalHospitalario[] buscarReporteDocumentosFaltantes() throws Exception {
        ArrayList rst = null;
        PersonalHospitalario arrRet[] = null, oPers = null;
        String sQuery = "SELECT * FROM buscaPersonalHospitalarioDocumentosFaltantes();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalHospitalario[rst.size()];
            ArrayList vRowTemp;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPers = new PersonalHospitalario();
                oPers.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPers.setNombres( (String) vRowTemp.get( 1 ) );
                oPers.setApPaterno( (String) vRowTemp.get( 2 ) );
                oPers.setApMaterno( (String) vRowTemp.get( 3 ) );
                oPers.setNoTarjetaBase( ((Double) vRowTemp.get( 4 )).intValue() ); //Usado para el numero de documentos faltantes
                arrRet[i] = oPers;
            }
        }

        return arrRet;
    }

    public PersonalHospitalario[] buscarReporteAntiguedad( int nInicio, int nFin ) throws Exception {
        ArrayList rst = null;
        PersonalHospitalario arrRet[] = null, oPers = null;
        String sQuery = "";
        if( getFechaRegistro() == null || getFechaCambioActivo() == null ) {
            throw new Exception( "PersonalHospitalario.buscarReporteAntiguedad" );
        } else {
            sQuery = "SELECT * FROM buscaPersonalHospitalarioAntiguedad( "
                    + nInicio + "::INTEGER, "
                    + nFin + "::INTEGER );";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
                arrRet = new PersonalHospitalario[rst.size()];
                ArrayList vRowTemp;
                for( int i = 0; i < rst.size(); i++ ) {
                    vRowTemp = (ArrayList) rst.get( i );
                    oPers = new PersonalHospitalario();
                    oPers.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                    oPers.setNombres( (String) vRowTemp.get( 1 ) );
                    oPers.setApPaterno( (String) vRowTemp.get( 2 ) );
                    oPers.setApMaterno( (String) vRowTemp.get( 3 ) );
                    oPers.setFechaRegistro( (Date) vRowTemp.get( 4 ) );
                    oPers.setEdad( ((Double) vRowTemp.get( 5 )).intValue() ); //Utilizado para la antiguedad
                    arrRet[i] = oPers;
                }
            }
        }
        return arrRet;
    }

    @Override
    public int insertar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PersonalHospitalario.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaPersonalHospitalario();";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                if( rst != null && rst.size() == 1 ) {
                    ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                    nRet = ((Double) rst.get( 0 )).intValue();
                }
            }
        }
        return nRet;
    }

    @Override
    public int modificar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PersonalHospitalario.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalHospitalario();";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                if( rst != null && rst.size() == 1 ) {
                    ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                    nRet = ((Double) rst.get( 0 )).intValue();
                }
            }
        }
        return nRet;
    }

    public int modificaPersonalHospitalarioUsuario( String usu, String idUsuOld, String idUsuNew, String pass, int tipo, String turno ) throws Exception {//Modifica PersonalHospitalario de CAPASITS
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PersonalHospitalario.modificar: error de programaciÃ³n, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaAdministrativoCapasits('" + usu + "','" + idUsuOld + "','" + idUsuNew + "','" + pass + "'," + getNoTarjeta() + ",'" + getActividad() + "','" + getCedProf() + "','" + getNombres() + "','" + getApPaterno() + "','" + getApMaterno() + "','" + getFechaNac() + "','" + getSexos() + "','" + getCurp() + "'," + tipo + ",'" + turno + "');";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                if( rst != null && rst.size() == 1 ) {
                    ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                    nRet = ((Double) vRowTemp.get( 0 )).intValue();
                }
            }
        }
        return nRet;
    }

    public boolean modificaStatus( String sUsuarioFirm ) throws Exception {
        boolean bFlag = false;
        ArrayList rst = null;
        String sQuery = "";
        if( sUsuarioFirm == null || sUsuarioFirm.equals( "" ) || this.sStatus == null || this.nNoTarjeta < 0 ) {
            throw new Exception( "Error de programacion, faltan datos, modificaStatus" );
        } else {
            sQuery = "SELECT * FROM modificaStatusPersonal('" + sUsuarioFirm + "'::character varying,"
                    + this.nNoTarjeta + ",'"
                    + this.sStatus + "'::character(5));";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.isEmpty() == false ) {
                bFlag = true;
            }
        }
        return bFlag;

    }

    @Override
    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PersonalHospitalario.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaPersonalHospitalario();";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                if( rst != null && rst.size() == 1 ) {
                    ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                    nRet = ((Double) rst.get( 0 )).intValue();
                }
            }
        }
        return nRet;
    }

    //*************************MÉTODO DE CAPASITS**************************
    public boolean buscarCapa() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( getNoTarjeta() < 1 ) {   //completar llave
            throw new Exception( "PersonalHospitalario.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalHospitalario(" + getNoTarjeta() + ");";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                if( vRowTemp.get( 1 ).equals( "FARMCAPASI" ) ) {
                    setActividad( "FARMACIA" );
                }
                if( vRowTemp.get( 1 ).equals( "CAPASSECRE" ) ) {
                    setActividad( "SECRETARIA" );
                }
                if( vRowTemp.get( 1 ).equals( "ADMCAPASIT" ) ) {
                    setActividad( "DIRECTIVO" );
                }
                setCedProf( (String) vRowTemp.get( 2 ) );
                setNombres( (String) vRowTemp.get( 5 ) );
                setApPaterno( (String) vRowTemp.get( 6 ) );
                setApMaterno( (String) vRowTemp.get( 7 ) );
                setFechaNac( (Date) vRowTemp.get( 8 ) );
                setSexos( (String) vRowTemp.get( 9 ) );
                setCurp( (String) vRowTemp.get( 10 ) );
                this.oUsuar = new Usuario();
                this.oUsuar.setIdUsuario( (String) vRowTemp.get( 12 ) );

                bRet = true;
            }
        }
        return bRet;
    }

    public boolean buscarCedulaProfes() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PersonalHospitalario.error de programación, faltan datos" );
        }
        if( this.getCedProf().equals( "" ) ) {
            return false;
        } else {
            sQuery = "SELECT * FROM buscaCedulaProf('" + getCedProf() + "');";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                bRet = true;
            }
        }
        if( this.getCedProf().equals( "CEDULA X" ) ) {
            this.setCedProf( "" );
        }
        return bRet;
    }

    //Inserta Personal Hospitalario de CAPASITS
    public int insertarCapa( String usu, String turno ) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PersonalHospitalario.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaPersonalHospitalarioCapa('" + usu + "'," + getNoTarjeta() + ",'" + getActividad() + "','" + getCedProf() + "','" + getNombres() + "','" + getApPaterno() + "','" + getApMaterno() + "','" + getFechaNac() + "','" + getSexos() + "','" + getCurp() + "','" + getStatus() + "','" + turno + "');";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                if( rst != null && rst.size() == 1 ) {
                    ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                    nRet = ((Double) vRowTemp.get( 0 )).intValue();
                }
            }
        }
        return nRet;
    }

    public int modificarCapa( String usu ) throws Exception {//Modifica PersonalHospitalario de CAPASITS
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PersonalHospitalario.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalHospitalarioCapa('" + usu + "'," + getNoTarjeta() + ",'" + getActividad() + "','" + getCedProf() + "','" + getNombres() + "','" + getApPaterno() + "','" + getApMaterno() + "','" + getFechaNac() + "','" + getSexos() + "','" + getCurp() + "');";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                if( rst != null && rst.size() == 1 ) {
                    ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                    nRet = ((Double) vRowTemp.get( 0 )).intValue();
                }
            }
        }
        return nRet;
    }

    ////////////////////////////////////// Fin CAPASITS
    public boolean buscaMedicoAnestesioFirmado() throws Exception {
        boolean bFlag = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaAnestesiologoFirmado('"
                + this.oUsuar.getIdUsuario() + "');";
        System.out.println( sQuery );

        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null || rst.isEmpty() != true ) {
            ArrayList vRowTemp = (ArrayList) rst.get( 0 );
            //this.setNoTarjeta(((Double) vRowTemp.get(1)).intValue());
            this.setApPaterno( (String) vRowTemp.get( 1 ) );
            this.setApMaterno( (String) vRowTemp.get( 2 ) );
            this.setNombres( (String) vRowTemp.get( 3 ) );
            bFlag = true;
        }
        return bFlag;
    }

    public boolean buscarIdPersonalHospitalario() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;

        String sQuery = "";

        sQuery = "SELECT * FROM buscaPersonaHospitalaria(" + this.nNoTarjeta + ");";

        System.out.println( sQuery );
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();

        }
        if( rst != null && rst.size() >= 1 ) {
            ArrayList vRowTemp = (ArrayList) rst.get( 0 );

            bRet = true;
        }
        return bRet;
    }

    /**
     * Busca nombre de la enfermera que capturó el vale colectivo de quirófano
     * en cierta fecha
     *
     * @author GIL
     */
    public boolean buscaPersonalQUIROFANO( Date dfecha ) throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        SimpleDateFormat oFec = new SimpleDateFormat( "yyyy-MM-dd" );
        String sQuery = "";
        sQuery = "SELECT * FROM buscaPersonalDetalleValeColectivoquirofano('" + oFec.format( dfecha ) + "');";

        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() >= 1 ) {
            ArrayList vRowTemp = (ArrayList) rst.get( 0 );
            this.setNombres( ((String) vRowTemp.get( 0 )) );
            this.setApPaterno( ((String) vRowTemp.get( 1 )) );
            this.setApMaterno( ((String) vRowTemp.get( 2 )) );
            System.out.println( "nombre" + this.getNombreCompleto() );
            bRet = true;
        }
        return bRet;
    }

    /**
     * Busca nombre de la enfermera que capturó el vale colectivo de CEYE en
     * cierta fecha
     *
     * @author GIL
     */
    public boolean buscaPersonalCEYE( Date dfecha ) throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        SimpleDateFormat oFec = new SimpleDateFormat( "yyyy-MM-dd" );
        String sQuery = "";

        sQuery = "SELECT * FROM buscaPersonalDetalleValeColectivoCEYE('"
                + oFec.format( dfecha ) + "');";

        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() >= 1 ) {
            ArrayList vRowTemp = (ArrayList) rst.get( 0 );
            this.setNombres( ((String) vRowTemp.get( 0 )) );
            this.setApPaterno( ((String) vRowTemp.get( 1 )) );
            this.setApMaterno( ((String) vRowTemp.get( 2 )) );
            // System.out.println("nombre"+this.getName());
            bRet = true;
        }
        return bRet;
    }

    //METODO AGREGADO POR JOSE JAVIER GONZALEZ VALLEJO
    //BUSCA EL NOMBRE DEL USUARIO FIRMADO Y SU NUMERO DE TRAJETA
    public boolean buscaPersonalHospitalarioDatos() throws Exception {
        
        boolean bRtn = false;
        ArrayList rst = null;
        String sQuery = "";
        AreaServicioHRRB oArea = null;
                                                                                /*aqui se modifico */ 
        if( this.getIdUsuario().equals( "" ) ) {
            
            throw new Exception( "PersonalHospitalario.BuscaPersonalHospitalarioDatos: error, falta datos" );
        } else {
            
            sQuery = "SELECT * FROM BuscaPersonalHospitalarioDatos('"
                    + this.getIdUsuario() + "'::character varying);";
            
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                setCedProf( (String) vRowTemp.get( 1 ) );
                setNombres( (String) vRowTemp.get( 2 ) );
                setApPaterno( (String) vRowTemp.get( 3 ) );
                setApMaterno( (String) vRowTemp.get( 4 ) );
                oArea = new AreaServicioHRRB();
                oArea.setClave( ((Double) vRowTemp.get( 5 )).intValue() );
                
                this.setAreaServicioHRRB( new ArrayList<AreaServicioHRRB>() );
                
                
                this.arrAreaServicioHRRB.add( oArea );
                bRtn = true;
            }
        }
        return bRtn;
    }

    //METODO CREADO POR DANIEL HERNANDEZ SANCHEZ TERCERA FASE
    //busca datos del personal hospitalario que se relacionan con el prestamo y la devolucion del expediente
    public PersonalHospitalario[] buscaPersonalExpPres( String valor ) throws Exception {
        PersonalHospitalario arrRet[] = null, oPersonalHospitalario = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaPersonalExpPres(" + Integer.parseInt( valor ) + ");";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            arrRet = new PersonalHospitalario[rst.size()];
            for( i = 0; i < rst.size(); i++ ) {
                oPersonalHospitalario = new PersonalHospitalario();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oPersonalHospitalario.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPersonalHospitalario.setNombres( (String) vRowTemp.get( 1 ) );
                oPersonalHospitalario.setApPaterno( (String) vRowTemp.get( 2 ) );
                oPersonalHospitalario.setApMaterno( (String) vRowTemp.get( 3 ) );
                arrRet[i] = oPersonalHospitalario;
            }
        }
        return arrRet;
    }

    public boolean buscaAreaPersonal() throws Exception {
        boolean bRtn = false;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        AreaServicioHRRB oArea = null;
        if( this.getNoTarjeta() == 0 ) {
            throw new Exception( "PersonalHospitalario.BuscaAreaPersonal" );
        } else {
            sQuery = "SELECT * FROM buscaAreaPersonal(" + this.getNoTarjeta() + ");";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
                this.setAreaServicioHRRB( new ArrayList<AreaServicioHRRB>() );
                for( i = 0; i < rst.size(); i++ ) {
                    oArea = new AreaServicioHRRB();
                    ArrayList vRowTemp = (ArrayList) rst.get( i );
                    oArea.setClave( ((Double) vRowTemp.get( 0 )).intValue() );
                    oArea.setDescripcion( (String) vRowTemp.get( 1 ) );
                    this.arrAreaServicioHRRB.add( oArea );
                }
            }
            bRtn = true;
        }
        return bRtn;
    }

    public PersonalHospitalario[] buscannotarjetados( String actividad ) throws Exception {
        PersonalHospitalario arrRet[] = null, oPerHos = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscannotarjetatrabajoSocialDos('" + actividad + "');";
        System.out.println( sQuery );
        oAD = new AccesoDatos();
        System.out.println( sQuery );
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            arrRet = new PersonalHospitalario[rst.size()];
            for( i = 0; i < rst.size(); i++ ) {
                oPerHos = new PersonalHospitalario();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oPerHos.setNombres( (String) vRowTemp.get( 0 ) );
                oPerHos.setApPaterno( (String) vRowTemp.get( 1 ) );
                oPerHos.setApMaterno( (String) vRowTemp.get( 2 ) );
                oPerHos.setNoTarjeta( ((Double) vRowTemp.get( 3 )).intValue() );
                arrRet[i] = oPerHos;
            }
        }
        return arrRet;
    }

    public PersonalHospitalario[] buscalistdesplAutori() throws Exception {
        ArrayList rst = null;
        PersonalHospitalario arrRet[] = null, oPh = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        List<PersonalHospitalario> vObj = null;
        sQuery = "SELECT * FROM buscapuestosautorizar();";
        System.out.println( sQuery );
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<PersonalHospitalario>();
            for( i = 0; i < rst.size(); i++ ) {
                oPh = new PersonalHospitalario();
                ArrayList<Object> vRowTemp = (ArrayList) rst.get( i );
                oPh.setPuesto( (String) vRowTemp.get( 0 ) );
                oPh.setClavpeus( (String) vRowTemp.get( 1 ) );
                vObj.add( oPh );
            }
            nTam = vObj.size();
            arrRet = new PersonalHospitalario[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }

    public int getNoTarjeta() {
        return nNoTarjeta;
    }

    public void setNoTarjeta( int valor ) {
        nNoTarjeta = valor;
    }

    public String getActividad() {
        return sActividad;
    }

    public void setActividad( String valor ) {
        sActividad = valor;
    }

    public String getCedProf() {
        return sCedProf;
    }

    public void setCedProf( String valor ) {
        sCedProf = valor;
    }

    public String getCedEsp() {
        return sCedEsp;
    }

    public void setCedEsp( String valor ) {
        sCedEsp = valor;
    }

    public String getCedIMSS() {
        return sCedIMSS;
    }

    public void setCedIMSS( String valor ) {
        sCedIMSS = valor;
    }

    public boolean getStatusPersonal() {
        return bStatusPersonal;
    }

    public void setStatusPersonal( boolean valor ) {
        bStatusPersonal = valor;
    }

    public String getIdUsuario() {
        return sIdUsuario;
    }

    public void setIdUsuario( String sIdUsuario ) {
        this.sIdUsuario = sIdUsuario;
    }

    public Parametrizacion getTipoContrato() {
        return oTipoContrato;
    }

    public void setTipoContrato( Parametrizacion valor ) {
        oTipoContrato = valor;
    }

    public Parametrizacion getTipoPersonal() {
        return oTipoPersonal;
    }

    public void setTipoPersonal( Parametrizacion valor ) {
        oTipoPersonal = valor;
    }

    public String getFoto() {
        return sFoto;
    }

    public void setFoto( String valor ) {
        sFoto = valor;
    }

    public int getAnoRegCredElector() {
        return nAnoRegCredElector;
    }

    public void setAnoRegCredElector( int nAnoRegCredElector ) {
        this.nAnoRegCredElector = nAnoRegCredElector;
    }

    public Parametrizacion getActivo() {
        return oActivo;
    }

    public void setActivo( Parametrizacion oActivo ) {
        this.oActivo = oActivo;
    }

    public Date getFechaRegistro() {
        return dFechaRegistro;
    }

    public void setFechaRegistro( Date dFechaRegistro ) {
        this.dFechaRegistro = dFechaRegistro;
    }

    public String getRFC() {
        return sRFC;
    }

    public void setRFC( String sRFC ) {
        this.sRFC = (sRFC != null ? sRFC.toUpperCase() : sRFC);
    }

    public Parametrizacion getTipoEmpleado() {

        return oTipoEmpleado;
    }

    public void setTipoEmpleado( Parametrizacion oTipoEmpleado ) {

        this.oTipoEmpleado = oTipoEmpleado;
    }

    public String getCalleNum() {
        return sCalleNum;
    }

    public void setCalleNum( String sCalleNum ) {
        this.sCalleNum = sCalleNum.toUpperCase();
    }

    public String getColonia() {
        return sColonia;
    }

    public void setColonia( String sColonia ) {
        this.sColonia = sColonia.toUpperCase();
    }

    public Estado getEstado() {
        return oEstado;
    }

    public void setEstado( Estado oEstado ) {
        this.oEstado = oEstado;
    }

    public Municipio getMunicipio() {
        return oMunicipio;
    }

    public void setMunicipio( Municipio oMunicipio ) {
        this.oMunicipio = oMunicipio;
    }

    public Ciudad getCiudad() {
        return oCiudad;
    }

    public void setCiudad( Ciudad oCiudad ) {
        this.oCiudad = oCiudad;
    }

    public CiudadCP getCiudadCP() {
        return oCiudadCP;
    }

    public void setCiudadCP( CiudadCP oCiudadCP ) {
        this.oCiudadCP = oCiudadCP;
    }

    public String getOtroPais() {
        return sOtroPais;
    }

    public void setOtroPais( String sOtroPais ) {
        this.sOtroPais = sOtroPais;
    }

    public String getIdUsuarioFirmado() {
        return sIdUsuarioFirmado;
    }

    public void setIdUsuarioFirmado( String sIdUsuarioFirmado ) {
        this.sIdUsuarioFirmado = sIdUsuarioFirmado;
    }

    public DocumentoDigital getDocumentoDigital() {
        return oDocumentoDigital;
    }

    public void setDocumentoDigital( DocumentoDigital oDocumentoDigital ) {
        this.oDocumentoDigital = oDocumentoDigital;
    }

    public ArrayList getAreaServicioHRRB() {
        return arrAreaServicioHRRB;
    }

    public void setAreaServicioHRRB( ArrayList valor ) {
        arrAreaServicioHRRB = valor;
    }

    public String getStatus() {
        return sStatus;
    }

    public void setStatus( String sStatus ) {
        this.sStatus = sStatus;
    }

    public String getStatusDesc() {
        Parametrizacion p = new Parametrizacion();
        try {
            return p.buscaValorParametrizado( sStatus );
        } catch( Exception e ) {
            e.printStackTrace();
            return "Error buscando status";
        }
    }

    public String getClavpeus() {
        return sClavpeus;
    }

    public void setClavpeus( String sClavpeus ) {
        this.sClavpeus = sClavpeus;
    }

    public String getPuesto() {
        return sPuesto;
    }

    public void setPuesto( String sPuesto ) {
        this.sPuesto = sPuesto;
    }

    public Pais getPais() {
        return oPais;
    }

    public void setPais( Pais oPais ) {
        this.oPais = oPais;
    }

    public PersonalAreaServ getPersonalAreaServ() {
        return oPersAreaServ;
    }

    public void setPersonalAreaServ( PersonalAreaServ oPersAreaServ ) {
        this.oPersAreaServ = oPersAreaServ;
    }

    public int getNoTarjetaBase() {
        return nNoTarjetaBase;
    }

    public void setNoTarjetaBase( int nNoTarjetaBase ) {
        this.nNoTarjetaBase = nNoTarjetaBase;
    }

    public CedulaEspecialidad getCedulaEspecialidad() {
        return oCedulaEspecialidad;
    }

    public void setCedulaEspecialidad( CedulaEspecialidad oCedulaEspecialidad ) {
        this.oCedulaEspecialidad = oCedulaEspecialidad;
    }

    public List<CedulaEspecialidad> getListaCedulaEspecialidad() {
        return lCedulaEspecialidad;
    }

    public void setListaCedulaEspecialidad( List<CedulaEspecialidad> lCedulaEspecialidad ) {
        this.lCedulaEspecialidad = lCedulaEspecialidad;
    }

    public List<CedulaEspecialidad> getListaCedulaEspecialidadOld() {
        return lCedulaEspecialidadOld;
    }

    public void setListaCedulaEspecialidadOld( List<CedulaEspecialidad> lCedulaEspecialidadOld ) {
        this.lCedulaEspecialidadOld = lCedulaEspecialidadOld;
    }

    public CapacitacionPersonal getCapacitacionPersonal() {
        return oCapacitacionPersonal;
    }

    public void setCapacitacionPersonal( CapacitacionPersonal oCapacitacionPersonal ) {
        this.oCapacitacionPersonal = oCapacitacionPersonal;
    }

    public List<CapacitacionPersonal> getListaCapacitacionPersonal() {
        return lCapacitacionPersonal;
    }

    public void setListaCapacitacionPersonal( List<CapacitacionPersonal> lCapacitacionPersonal ) {
        this.lCapacitacionPersonal = lCapacitacionPersonal;
    }

    public List<CapacitacionPersonal> getListaCapacitacionPersonalOld() {
        return lCapacitacionPersonalOld;
    }

    public void setListaCapacitacionPersonalOld( List<CapacitacionPersonal> lCapacitacionPersonalOld ) {
        this.lCapacitacionPersonalOld = lCapacitacionPersonalOld;
    }

    public Date getFechaCambioActivo() {
        return dFechaCambioActivo;
    }

    public void setFechaCambioActivo( Date dFechaCambioActivo ) {
        this.dFechaCambioActivo = dFechaCambioActivo;
    }

    public String getSinergia() {
        return sSinergia;
    }

    public void setSinergia( String sSinergia ) {
        this.sSinergia = sSinergia;
    }

    public Usuario getUsuar() {
        return oUsuar;
    }

    public void setUsuar( Usuario valor ) {
        this.oUsuar = valor;
    }

    public String getNumExterior() {
        return sNumExterior;
    }

    public void setNumExterior( String sNumExterior ) {
        this.sNumExterior = sNumExterior;
    }

    public String getNumInterior() {
        return sNumInterior;
    }

    public void setNumInterior( String sNumInterior ) {
        this.sNumInterior = sNumInterior;
    }

    public TipoVialidad getTipoVialidad() {
        return oTipoVialidad;
    }

    public void setTipoVialidad( TipoVialidad oTipoVialidad ) {
        this.oTipoVialidad = oTipoVialidad;
    }

    public TipoAsentamiento getTipoAsentamiento() {
        return oTipoAsentamiento;
    }

    public void setTipoAsentamiento( TipoAsentamiento oTipoAsentamiento ) {
        this.oTipoAsentamiento = oTipoAsentamiento;
    }
}
