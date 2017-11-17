package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.jbs.urgencias.registrarPaciente;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import mx.gob.hrrb.modelo.consultaexterna.Horario;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class Medico extends PersonalHospitalario implements Serializable {

    private Especialidad oEspecialidad;
    private ActividadMedico oActividadMedico;
    private TipoMedico oTipoMedico;

    private ArrayList arrConsultorio;
    private AsignaConsultorio oAsigCon;
    private Consultorio oCons;
    private AreaServicioHRRB oArea;
    private String sFechaNacTexto;

    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    private String sCturno;
    private String sCpuesto;
    private Turno oTurn; //CAPASITS
    private Usuario oUsuar; //CAPASITS
    private Horario oHora; ///CAPASITS
    private PacienteCapasits oPacCapa; ////CAPASITS
    private Cita oCit;
    private String medicoResponsableStr;//URG
    private Paciente oPaciente;

    public Medico( Especialidad oEspecialidad, TipoMedico oTipoMedico, ActividadMedico oActividadMedico, ArrayList arrConsultorio, Consultorio oCons, AreaServicioHRRB oArea, boolean bStatusPersonal, int nNoTarjeta, Parametrizacion oTipoContrato, Parametrizacion oTipoPersonal, String sActividad, String sCedEsp, String sCedIMSS, String sCedProf, String sFoto, ArrayList arrAreaServicioHRRB, Date dFechaNac, int nEdad, Parametrizacion oEstadoCivil, String sApMaterno, String sApPaterno, String sCurp, String sNombres, Parametrizacion oSexo, String sTelefono, String sSexo, String sStatus ) {
        super( bStatusPersonal, nNoTarjeta, oTipoContrato, oTipoPersonal, sActividad, sCedEsp, sCedIMSS, sCedProf, sFoto, arrAreaServicioHRRB, dFechaNac, nEdad, oEstadoCivil, sApMaterno, sApPaterno, sCurp, sNombres, sTelefono, sSexo, sStatus );
        this.oEspecialidad = oEspecialidad;
        this.oTipoMedico = oTipoMedico;
        this.oActividadMedico = oActividadMedico;
        this.arrConsultorio = arrConsultorio;
        this.oCons = oCons;
        this.oArea = oArea;
        this.sCturno = "";
        this.sCpuesto = "";
    }

    public Medico() {
        oCons = new Consultorio();
        oHora = new Horario(); //CAPASITS
        oCit = new Cita();
        oPaciente = new Paciente();
        oArea = new AreaServicioHRRB();
        oAsigCon = new AsignaConsultorio();
        medicoResponsableStr = "";
        oTurn = new Turno(); //CAPASITS
        oUsuar = new Usuario(); //CAPASITS
        oEspecialidad = new Especialidad();
        oTipoMedico = new TipoMedico();
        oActividadMedico = new ActividadMedico();
        sFechaNacTexto = "";
        setSexoP( "M" );
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if( httpServletRequest.getSession().getAttribute( "oFirm" ) != null ) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute( "oFirm" );
            sUsuario = oFirm.getUsu().getIdUsuario();
            setIdUsuarioFirmado( sUsuario );
        }
        sCturno = "";
        sCpuesto = "";
    }

    public void buscaNombreUsuario() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if( httpServletRequest.getSession().getAttribute( "oFirm" ) != null ) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute( "oFirm" );
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    @Override
    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( getNoTarjeta() < 1 ) {   //completar llave
            throw new Exception( "Medico.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveMedico(" + getNoTarjeta() + ");";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 1 )).intValue() );
                getTipoMedico().setClaveTipo( ((Double) vRowTemp.get( 2 )).intValue() );
                getActividadMedico().setClaveActividad( ((Double) vRowTemp.get( 3 )).intValue() );
                bRet = true;
            }
        }
        return bRet;
    }

    public boolean buscarPersonalHosp() throws Exception {
        return super.buscar();
    }

    public boolean buscarFolio() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PersonalHospitalario.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalHospitalario(" + getNoTarjeta() + ");";
            oAD = new AccesoDatos();
            System.out.println( sQuery );
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                bRet = true;
            }
        }
        return bRet;
    }

    public boolean buscarPersonalHospitalarioBase() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( getNoTarjeta() < 1 ) {
            throw new Exception( "Medico.buscarPersonalHospitalarioBase: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalHospitalarioBase( " + getNoTarjeta() + " );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                setNombres( (String) vRowTemp.get( 1 ) );
                setApPaterno( (String) vRowTemp.get( 2 ) );
                setApMaterno( (String) vRowTemp.get( 3 ) );
                getTipoPersonal().asignaValorParametrizado( (String) vRowTemp.get( 4 ) );
                getTipoEmpleado().asignaValorParametrizado( (String) vRowTemp.get( 5 ) );
                getPersonalAreaServ().getAreaServ().setClave( ((Double) vRowTemp.get( 6 )).intValue() );
                getPersonalAreaServ().getTurno().setClave( (String) vRowTemp.get( 7 ) );
                getPersonalAreaServ().getPuesto().setClave( (String) vRowTemp.get( 8 ) );
                getPersonalAreaServ().getTurnoPersonal().setClaveTurno( (String) vRowTemp.get( 9 ) );
                getPersonalAreaServ().getTurnoPersonal().setHorarioDef( (String) vRowTemp.get( 10 ) );
                getPersonalAreaServ().getTurnoPersonal().setDiasTrabajoDef( (String) vRowTemp.get( 11 ) );
                getPersonalAreaServ().getPersonalGrupo().setClaveGrupo( (String) vRowTemp.get( 12 ) );
                getPersonalAreaServ().getPersonalCategoria().setClaveCategoria( ((Double) vRowTemp.get( 13 )).intValue() ); //EDITADO: 22/03/17 (JMHG)
                getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 14 )).intValue() );
                getTipoMedico().setClaveTipo( ((Double) vRowTemp.get( 15 )).intValue() );
                getActividadMedico().setClaveActividad( ((Double) vRowTemp.get( 16 )).intValue() );
                bRet = true;
            }
        }

        return bRet;
    }

    public boolean buscarPersonalHospitalarioComp() throws Exception { //EDITADO: 10/04/17 (JMHG)
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( getNoTarjeta() < 1 ) {
            throw new Exception( "Medico.buscarPersonalHospitalarioComp: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalHospitalarioComp( " + getNoTarjeta() + "::INTEGER );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
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
                getActivo().asignaValorParametrizado( (String) vRowTemp.get( 19 ) );
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

                getPersonalAreaServ().getAreaServ().setClave( ((Double) vRowTemp.get( 37 )).intValue() );
                getPersonalAreaServ().getTurno().setClave( (String) vRowTemp.get( 38 ) );
                getPersonalAreaServ().getPuesto().setClave( (String) vRowTemp.get( 39 ) );
                getPersonalAreaServ().getTurnoPersonal().setClaveTurno( (String) vRowTemp.get( 40 ) );
                getPersonalAreaServ().getTurnoPersonal().setHorarioDef( (String) vRowTemp.get( 41 ) );
                getPersonalAreaServ().getTurnoPersonal().setDiasTrabajoDef( (String) vRowTemp.get( 42 ) );
                getPersonalAreaServ().getPersonalGrupo().setClaveGrupo( (String) vRowTemp.get( 43 ) );
                getPersonalAreaServ().getPersonalCategoria().setClaveCategoria( ((Double) vRowTemp.get( 44 )).intValue() );
                getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 45 )).intValue() );
                getTipoMedico().setClaveTipo( ((Double) vRowTemp.get( 46 )).intValue() );
                getActividadMedico().setClaveActividad( ((Double) vRowTemp.get( 47 )).intValue() );

                getCedulaEspecialidad().setNoTarjeta( getNoTarjeta() );
                CedulaEspecialidad[] arrCedEsp = getCedulaEspecialidad().buscarTodosFiltro();
                if( arrCedEsp != null ) {
                    setListaCedulaEspecialidad( new ArrayList<>( Arrays.asList( arrCedEsp ) ) );
                    setListaCedulaEspecialidadOld( new ArrayList<>( Arrays.asList( arrCedEsp ) ) );
                }

                getPersonalAreaServ().setClaveAreaServicioOld( getPersonalAreaServ().getAreaServ().getClave() );
                getPersonalAreaServ().setClaveTurnoOld( getPersonalAreaServ().getTurno().getClave() );
                getPersonalAreaServ().getTurnoPersonal().crearHorarioDef();
                getPersonalAreaServ().getTurnoPersonal().crearArrDiasTrabajo();
                bRet = true;
            }
        }
        return bRet;
    }

    /*Método de busqueda de Cirujanos*/
//////////////////////////////////////////////////////////////////////////////        
    public Medico[] buscarMedicoCir() throws Exception {
        Medico arrRet[] = null, oMed = null;
        ArrayList rst = null;
        ArrayList<Medico> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarMedicoCirujanos()";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<Medico>();
            for( i = 0; i < rst.size(); i++ ) {
                oMed = new Medico();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oMed.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oMed.setNombres( (String) vRowTemp.get( 1 ) );
                oMed.setApPaterno( (String) vRowTemp.get( 2 ) );
                oMed.setApMaterno( (String) vRowTemp.get( 3 ) );
                vObj.add( oMed );
            }
            nTam = vObj.size();
            arrRet = new Medico[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }

    public Medico[] buscarMedicoAnestesio() throws Exception {
        Medico arrRet[] = null, oMedico = null;
        ArrayList rst = null;
        ArrayList<Medico> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaMedicoAnestesiologo();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<Medico>();
            for( i = 0; i < rst.size(); i++ ) {
                oMedico = new Medico();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oMedico.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oMedico.setNombres( (String) vRowTemp.get( 1 ) );
                oMedico.setApPaterno( (String) vRowTemp.get( 2 ) );
                oMedico.setApMaterno( (String) vRowTemp.get( 3 ) );
                vObj.add( oMedico );
            }
            nTam = vObj.size();
            arrRet = new Medico[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////       
    //*************************MÉTODO DE CAPASITS**************************

    public boolean buscarMedicoCapasits() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaMedicoCapasits(" + getNoTarjeta() + ");";
            oAD = new AccesoDatos();
            System.out.println( sQuery );
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
                getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 11 )).intValue() );
                getTipoMedico().setClaveTipo( ((Double) vRowTemp.get( 12 )).intValue() );
                getActividadMedico().setClaveActividad( ((Double) vRowTemp.get( 13 )).intValue() );
                oCons.setNoConsultorio( ((Double) vRowTemp.get( 14 )).intValue() );
                oTurn.setClave( (String) vRowTemp.get( 15 ) );
                getEspecialidad().setDescripcion( (String) vRowTemp.get( 16 ) );
                getTipoMedico().setDescripcion( (String) vRowTemp.get( 17 ) );
                getActividadMedico().setDescripcion( (String) vRowTemp.get( 18 ) );
                oCons.setNomConsultorio( (String) vRowTemp.get( 19 ) );
                bRet = true;
            }
        }
        return bRet;
    }

    public boolean buscarUsuarioMedico() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaUsuarioMedico(" + getNoTarjeta() + ");";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                oUsuar.setIdUsuario( (String) vRowTemp.get( 0 ) );
                bRet = true;
            }
        }
        return bRet;
    }

    //*************************MÉTODO DE CAPASITS**************************
    public boolean buscarCedulaProf() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( this.getCedProf().equals( "" ) ) {   //completar llave
            throw new Exception( "Medico.Cedula profecional: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaCedulaProf('" + getCedProf() + "');";

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
        return bRet;
    }

    //**********************************************************************
    public boolean buscarDatosMedico() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalHospitalarioMed(" + getNoTarjeta() + ");";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                setCurp( (String) vRowTemp.get( 1 ) );
                setNombres( (String) vRowTemp.get( 2 ) );
                setApPaterno( (String) vRowTemp.get( 3 ) );
                setApMaterno( (String) vRowTemp.get( 4 ) );
                setSexoP( (String) vRowTemp.get( 5 ) );
                setFechaNac( (Date) vRowTemp.get( 6 ) );
                if( getFechaNac() != null ) {
                    setFechaNacTexto( df.format( getFechaNac() ) );
                } else {
                    setFechaNacTexto( "" );
                }
                setTelefono( (String) vRowTemp.get( 7 ) );

                getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 8 )).intValue() );
                getTipoPersonal().setTipoParametro( (String) vRowTemp.get( 10 ) );
                getTipoContrato().setTipoParametro( (String) vRowTemp.get( 11 ) );
                getEstadoCivil().setTipoParametro( (String) vRowTemp.get( 12 ) );
                getTipoMedico().setClaveTipo( ((Double) vRowTemp.get( 13 )).intValue() );
                getTipoMedico().setDescripcion( (String) vRowTemp.get( 14 ) );
                setStatus( (String) vRowTemp.get( 15 ) );
                getActividadMedico().setClaveActividad( ((Double) vRowTemp.get( 16 )).intValue() );
                setActividad( (String) vRowTemp.get( 17 ) );
                setCedProf( (String) vRowTemp.get( 18 ) );
                setCedEsp( (String) vRowTemp.get( 19 ) );
                setCedIMSS( (String) vRowTemp.get( 20 ) );
                sCturno = ((String) vRowTemp.get( 21 ));
                sCpuesto = ((String) vRowTemp.get( 22 ));
                getArea().setClave( ((Double) vRowTemp.get( 23 )).intValue() );
                bRet = true;
            }
        }
        return bRet;
    }

    //**********************************************************************
    public Medico[] buscarMedicosCE() throws Exception {
        Medico arrRet[] = null, oMedico = null;
        ArrayList rst = null;
        ArrayList<Medico> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaMedicosCE();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<Medico>();
            for( i = 0; i < rst.size(); i++ ) {
                oMedico = new Medico();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oMedico.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oMedico.setNombres( (String) vRowTemp.get( 1 ) );
                oMedico.setApPaterno( (String) vRowTemp.get( 2 ) );
                oMedico.setApMaterno( (String) vRowTemp.get( 3 ) );
                vObj.add( oMedico );
            }
            nTam = vObj.size();
            arrRet = new Medico[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }

//***************************************************************
    public Medico[] buscarMedicosUrg() throws Exception {
        Medico arrRet[] = null, oMedico = null;
        ArrayList rst = null;
        ArrayList<Medico> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaMedicosUrg();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<Medico>();
            for( i = 0; i < rst.size(); i++ ) {
                oMedico = new Medico();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oMedico.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oMedico.setNombres( (String) vRowTemp.get( 1 ) );
                oMedico.setApPaterno( (String) vRowTemp.get( 2 ) );
                oMedico.setApMaterno( (String) vRowTemp.get( 3 ) );
                vObj.add( oMedico );
            }
            nTam = vObj.size();
            arrRet = new Medico[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }

//***************************************************************
    //***************************************************************
    public Medico[] buscarMedicosHosp() throws Exception {
        Medico arrRet[] = null, oMedico = null;
        ArrayList rst = null;
        ArrayList<Medico> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaMedicosHosp();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<Medico>();
            for( i = 0; i < rst.size(); i++ ) {
                oMedico = new Medico();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oMedico.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oMedico.setNombres( (String) vRowTemp.get( 1 ) );
                oMedico.setApPaterno( (String) vRowTemp.get( 2 ) );
                oMedico.setApMaterno( (String) vRowTemp.get( 3 ) );
                vObj.add( oMedico );
                //System.out.println("busca medicos"+oMedico.getNoTarjeta()+" nom: "+oMedico.getNombres()+" appat: "+oMedico.getApPaterno()+" apmat: "+oMedico.getApMaterno());
            }
            nTam = vObj.size();
            arrRet = new Medico[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }
//***************************************************************

    @Override
    public Medico[] buscarTodos() throws Exception {
        Medico arrRet[] = null, oMedico = null;
        ArrayList rst = null;
        ArrayList<Medico> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaTodosMedico();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<Medico>();
            for( i = 0; i < rst.size(); i++ ) {
                oMedico = new Medico();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oMedico.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oMedico.getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 1 )).intValue() );
                oMedico.getTipoMedico().setClaveTipo( ((Double) vRowTemp.get( 2 )).intValue() );
                oMedico.getActividadMedico().setClaveActividad( ((Double) vRowTemp.get( 3 )).intValue() );
                vObj.add( oMedico );
            }
            nTam = vObj.size();
            arrRet = new Medico[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }
//***************************************************************

    public Medico[] buscarMedicosParaCambios() throws Exception {
        Medico arrRet[] = null, oMedico = null;
        ArrayList rst = null;
        ArrayList<Medico> vObj = null;
        String sQuery = "";
        String turno = "";
        int tarjaux = 0;
        int consAux = 0;
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarMedicosParaCambios();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<Medico>();
            for( i = 0; i < rst.size(); i++ ) {
                oMedico = new Medico();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oMedico.getCons().setNoConsultorio( ((Double) vRowTemp.get( 0 )).intValue() );
                oMedico.setNoTarjeta( ((Double) vRowTemp.get( 1 )).intValue() );
                oMedico.setApPaterno( (String) vRowTemp.get( 2 ) );
                oMedico.setApMaterno( (String) vRowTemp.get( 3 ) );
                oMedico.setNombres( (String) vRowTemp.get( 4 ) );
                oMedico.getArea().setClave( ((Double) vRowTemp.get( 5 )).intValue() );
                oMedico.getAsigCon().setMaximo( ((Double) vRowTemp.get( 6 )).intValue() );
                oMedico.getAsigCon().getTurno().setClave( (String) vRowTemp.get( 7 ) );
                oMedico.getAsigCon().getAreaServicio().setDescripcion( (String) vRowTemp.get( 8 ) );
                if( oMedico.getNoTarjeta() != tarjaux ) {
                    vObj.add( oMedico );
                } else {
                    if( oMedico.getAsigCon().getTurno().getClave().compareTo( turno ) != 0 ) {
                        vObj.add( oMedico );
                    } else {
                        if( oMedico.getNoTarjeta() == tarjaux && oMedico.getCons().getNoConsultorio() != consAux ) {
                            vObj.add( oMedico );
                        }
                    }
                }
                tarjaux = oMedico.getNoTarjeta();
                turno = oMedico.getAsigCon().getTurno().getClave();
                consAux = oMedico.getCons().getNoConsultorio();
            }
            nTam = vObj.size();
            arrRet = new Medico[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }
//*************************************************************** 

    public int buscarMaximoPorDia( String tarjeta, String turno, Date fecha, String cons ) throws Exception {
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat df = new SimpleDateFormat( "EEEE" );
        Calendar Cal = Calendar.getInstance();
        Cal.setTime( fecha );
        String diaTexto = "";
        int numDia = Cal.get( Calendar.DAY_OF_WEEK );
        switch( numDia ) {
            case 1:
                diaTexto = "DOMINGO";
                break;
            case 2:
                diaTexto = "LUNES";
                break;
            case 3:
                diaTexto = "MARTES";
                break;
            case 4:
                diaTexto = "MIÉRCOLES";
                break;
            case 5:
                diaTexto = "JUEVES";
                break;
            case 6:
                diaTexto = "VIERNES";
                break;
            case 7:
                diaTexto = "SÁBADO";
                break;
        }
        int max = 0;

        sQuery = "SELECT * FROM buscarMaximoPorDia(" + tarjeta + ", '" + diaTexto + "','" + turno.substring( 0, 3 ) + "', " + cons + "::smallint);";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            ArrayList vRowTemp = (ArrayList) rst.get( 0 );
            max = ((Double) vRowTemp.get( 0 )).intValue();
        }
        return max;
    }

    public boolean buscarMedicodenCita() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "PacienteCapasits.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaMedicodeCita(" + this.oCons.getNoConsultorio() + "::smallint," + this.oHora.getClave() + "::smallint);";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                this.setNoTarjeta( ((Double) vRowTemp.get( 2 )).intValue() );

                bRet = true;
            }
        }
        return bRet;
    }

    //*********************************MÉTODO DE CAPASITS*****************************************
    public int insertarCitaCapasitsAdmin() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "Cita.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaCitaCapasitsAdmin('" + sUsuario + "'," + oPacCapa.getFolioPaciente() + ",'" + oCit.getFechaHora() + "'," + oCons.getNoConsultorio() + "::smallint," + oHora.getClave() + "::smallint," + getNoTarjeta() + ");";
            oAD = new AccesoDatos();
            System.out.println( sQuery );
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
//******************************MÉTODO DE CAPASITS************************************************

    @Override
    public int insertar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        int horario = 0;
        String sPerfil = "";
        if( oTurn.getClave().equals( "MAT" ) ) {
            horario = 1;
        } else {
            horario = 2;
        }
        if( oCons.getNoConsultorio() == 19 || oCons.getNoConsultorio() == 20 ) {
            sPerfil = "CAPASICEXT";
        } else {
            if( oCons.getNoConsultorio() == 21 ) {
                sPerfil = "CAPASTSOC";
            } else {
                if( oCons.getNoConsultorio() == 22 ) {
                    sPerfil = "CAPASINUTR";
                } else {
                    if( oCons.getNoConsultorio() == 23 ) {
                        sPerfil = "CAPASPSICO";
                    } else {
                        sPerfil = "CAPASODONT";
                    }
                }
            }
        }
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaMedicoCapasits('" + sUsuario + "'," + this.getNoTarjeta() + ",'" + this.getCedProf() + "','" + this.getCedEsp() + "','" + this.getCedIMSS() + "','" + this.getNombres() + "','"
                    + this.getApPaterno() + "','" + this.getApMaterno() + "','" + this.getFechaNac() + "','" + this.getSexos() + "','" + this.getCurp() + "'," + this.getEspecialidad().getClaveEsp() + "::smallint,'" + this.getTipoMedico().getDescripcion()
                    + "'," + this.getActividadMedico().getClaveActividad() + "::smallint,10::smallint," + oCons.getNoConsultorio() + "::smallint," + horario + "::smallint,'" + oTurn.getClave() + "','" + oUsuar.getIdUsuario() + "','" + oUsuar.getPassword() + "','" + sPerfil + "');";
            oAD = new AccesoDatos();
            System.out.println( sQuery );
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
//*******************************************************************************        

    public int insertarMedico( String act, String tipo, String turno, String puesto ) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        String nac = "";
        String vTur[] = turno.split( " " );
        String vPue[] = puesto.split( " " );
        if( getFechaNacTexto().compareTo( "" ) == 0 || getFechaNacTexto().compareTo( "00/00/0000" ) == 0 ) {
            nac = "null";
        } else {
            nac = "'" + getFechaNacTexto() + "'::date";
        }
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaPersonalHospitalarioCE('" + sUsuario + "', " + getNoTarjeta() + ", '" + act + "', '" + getCedProf() + "', '" + getCedEsp() + "', '" + getCedIMSS() + "', '" + getNombres() + "', '" + getApPaterno() + "', '" + getApMaterno() + "', " + nac + ", '" + getSexoP() + "', '" + getCurp().toUpperCase() + "', '" + getTipoContrato().getTipoParametro() + "', '" + getEstadoCivil().getTipoParametro() + "','" + getTelefono() + "', '" + getTipoPersonal().getTipoParametro() + "', '" + getNoTarjeta() + ".jpg', " + getEspecialidad().getClaveEsp() + "::smallint, '" + tipo + "', '" + vTur[0] + "', '" + vPue[0] + "', " + getArea().getClave() + "::smallint);";
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
//*******************************************************************************

    public int insertarMedicoRoot( String act, String tipo, String turno, String puesto ) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        String nac = "";
        String vTur[] = turno.split( " " );
        String vPue[] = puesto.split( " " );
        if( getFechaNacTexto().compareTo( "" ) == 0 || getFechaNacTexto().compareTo( "00/00/0000" ) == 0 ) {
            nac = "null";
        } else {
            nac = "'" + getFechaNacTexto() + "'::date";
        }
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaPersonalHospitalarioRoot('" + sUsuario + "', " + getNoTarjeta() + ", '" + act + "', '" + getCedProf() + "', '" + getCedEsp() + "', '" + getCedIMSS() + "', '" + getNombres() + "', '" + getApPaterno() + "', '" + getApMaterno() + "', " + nac + ", '" + getSexoP() + "', '" + getCurp().toUpperCase() + "', '" + getTipoContrato().getTipoParametro() + "', '" + getEstadoCivil().getTipoParametro() + "','" + getTelefono() + "', '" + getTipoPersonal().getTipoParametro() + "', '" + getNoTarjeta() + ".jpg', " + getEspecialidad().getClaveEsp() + "::smallint, '" + tipo + "', '" + vTur[0] + "', '" + vPue[0] + "', " + getArea().getClave() + "::smallint, '" + getStatus() + "');";
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
//*******************************************************************************

    public int insertaPersonalHospitalarioComp() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        ArrayList<String> arrQuery = new ArrayList<>();
        String sQuery = "";
        if( sUsuario.isEmpty() || getNoTarjeta() < 1 ) {
            throw new Exception( "Medico.insertarPersonalHospitalarioRH: error de programación, faltan datos" );
        } else {
            SimpleDateFormat oFormat = new SimpleDateFormat( "yyyy-MM-dd" );
            getCedulaEspecialidad().setNoTarjeta( getNoTarjeta() );
            sQuery = "SELECT * FROM insertaPersonalHospitalarioComp( "
                    + "'" + sUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getActividad() + "', "
                    + "'" + getCedProf() + "', "
                    + "'" + getCedEsp() + "', "
                    + "'" + getCedIMSS() + "', "
                    + "'" + getNombres() + "', "
                    + "'" + getApPaterno() + "', "
                    + "'" + getApMaterno() + "', "
                    + "'" + oFormat.format( getFechaNac() ) + "'::DATE, "
                    + "'" + getSexos() + "', "
                    + "'" + getCurp() + "', "
                    + "'" + getStatus() + "', "
                    + "'" + getIdUsuario() + "', "
                    + "'" + getTipoContrato().getClaveParametro() + getTipoContrato().getTipoParametro() + "', "
                    + "'" + getEstadoCivil().getClaveParametro() + getEstadoCivil().getTipoParametro() + "', "
                    + "'" + getTelefono() + "', "
                    + "'" + getTipoPersonal().getClaveParametro() + getTipoPersonal().getTipoParametro() + "', "
                    + "'" + getFoto() + "', "
                    + getAnoRegCredElector() + "::SMALLINT, "
                    + "'" + "TBI01" + "', "
                    + "'" + oFormat.format( getFechaRegistro() ) + "'::DATE, "
                    + "'" + getRFC() + "', "
                    + "'" + getTipoEmpleado().getClaveParametro() + getTipoEmpleado().getTipoParametro() + "', "
                    + "'" + getCalleNum() + "', "
                    + "'" + getColonia() + "', "
                    + "'" + getEstado().getClaveEdo() + "', "
                    + "'" + getMunicipio().getClaveMun() + "', "
                    + "'" + getCiudad().getClaveCiu() + "', "
                    + "'" + getCiudadCP().getCp() + "', "
                    + "'" + getOtroPais() + "', "
                    + getNoTarjetaBase() + "::INTEGER, "
                    + "'" + getSinergia() + "', "
                    + "'" + getNumExterior() + "', "
                    + "'" + getNumInterior() + "', "
                    + "'" + getTipoVialidad().getCveTipoVial() + "', "
                    + getTipoAsentamiento().getCveAsenta() + "::SMALLINT, "
                    + getPersonalAreaServ().getAreaServ().getClave() + "::SMALLINT, "
                    + "'" + getPersonalAreaServ().getTurno().getClave() + "', "
                    + "'" + getPersonalAreaServ().getPuesto().getClave() + "', "
                    + "'" + getPersonalAreaServ().getTurnoPersonal().getClaveTurno() + "', "
                    + "'" + getPersonalAreaServ().getTurnoPersonal().getHorarioComp() + "', "
                    + "'" + getPersonalAreaServ().getTurnoPersonal().getDiasTrabajoComp() + "', "
                    + "'" + getPersonalAreaServ().getPersonalGrupo().getClaveGrupo() + "', "
                    + getPersonalAreaServ().getPersonalCategoria().getClaveCategoria() + "::SMALLINT, "
                    + getEspecialidad().getClaveEsp() + "::SMALLINT, "
                    + getTipoMedico().getClaveTipo() + "::SMALLINT, "
                    + getActividadMedico().getClaveActividad() + "::SMALLINT, "
                    + getCedulaEspecialidad().crearQueryArreglos( getListaCedulaEspecialidadOld(), getListaCedulaEspecialidad(), false ) + " );";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                nRet = ((Double) vRowTemp.get( 0 )).intValue();
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
            throw new Exception( "Medico.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaMedico();";
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

    //*******************************************************************************        
    public int ModificarMedicoCE( String act, String tipo, String turno, String puesto ) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        String nac = "";
        if( getFechaNacTexto().compareTo( "" ) == 0 || getFechaNacTexto().compareTo( "00/00/0000" ) == 0 ) {
            nac = "null";
        } else {
            nac = "'" + getFechaNacTexto() + "'::date";
        }
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalHospitalarioCE('" + sUsuario + "', " + getNoTarjeta() + ", '" + act + "', '" + getCedProf() + "', '" + getCedEsp() + "', '" + getCedIMSS() + "', '" + getNombres() + "', '" + getApPaterno() + "', '" + getApMaterno() + "', " + nac + ", '" + getSexoP() + "', '" + getCurp().toUpperCase() + "', '" + getStatus() + "', '" + getTipoContrato().getTipoParametro() + "', '" + getEstadoCivil().getTipoParametro() + "','" + getTelefono() + "', '" + getTipoPersonal().getTipoParametro() + "', '" + getNoTarjeta() + ".jpg', " + getEspecialidad().getClaveEsp() + "::smallint, '" + tipo + "', '" + turno + "', '" + puesto + "', " + getArea().getClave() + "::smallint);";
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
//*******************************************************************************     

    public int ModificarMedicoROOT( String act, String tipo, String turno, String puesto ) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        String nac = "";
        if( getFechaNacTexto().compareTo( "" ) == 0 || getFechaNacTexto().compareTo( "00/00/0000" ) == 0 ) {
            nac = "null";
        } else {
            nac = "'" + getFechaNacTexto() + "'::date";
        }
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalHospitalarioCE('" + sUsuario + "', " + getNoTarjeta() + ", '" + act + "', '" + getCedProf() + "', '" + getCedEsp() + "', '" + getCedIMSS() + "', '" + getNombres() + "', '" + getApPaterno() + "', '" + getApMaterno() + "', " + nac + ", '" + getSexoP() + "', '" + getCurp().toUpperCase() + "', '" + getStatus() + "', '" + getTipoContrato().getTipoParametro() + "', '" + getEstadoCivil().getTipoParametro() + "','" + getTelefono() + "', '" + getTipoPersonal().getTipoParametro() + "', '" + getNoTarjeta() + ".jpg', " + getEspecialidad().getClaveEsp() + "::smallint, '" + tipo + "', '" + turno + "', '" + puesto + "', " + getArea().getClave() + "::smallint);";
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

    //*******************************CAPASITS********************************************
    public int modificarMedicoCapasits( int num, int nu ) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        int horario = 0;
        String sPerfil = "";
        if( oTurn.getClave().equals( "MAT" ) ) {
            horario = 1;
        } else {
            horario = 2;
        }
        if( oCons.getNoConsultorio() == 19 || oCons.getNoConsultorio() == 20 ) {
            sPerfil = "CAPASICEXT";
        } else {
            if( oCons.getNoConsultorio() == 21 ) {
                sPerfil = "CAPASTSOC";
            } else {
                if( oCons.getNoConsultorio() == 22 ) {
                    sPerfil = "CAPASINUTR";
                } else {
                    if( oCons.getNoConsultorio() == 23 ) {
                        sPerfil = "CAPASPSICO";
                    } else {
                        sPerfil = "CAPASODONT";
                    }
                }
            }
        }
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaMedicoCapasits('" + sUsuario + "'," + this.getNoTarjeta() + ",'" + this.getCedProf() + "','" + this.getCedEsp() + "','" + this.getCedIMSS() + "','" + this.getNombres() + "','"
                    + this.getApPaterno() + "','" + this.getApMaterno() + "','" + this.getFechaNac() + "','" + this.getSexos() + "','" + this.getCurp() + "'," + this.getEspecialidad().getClaveEsp() + "::smallint,'" + this.getTipoMedico().getDescripcion()
                    + "'," + this.getActividadMedico().getClaveActividad() + "::smallint,10::smallint," + oCons.getNoConsultorio() + "::smallint," + horario + "::smallint,'" + oTurn.getClave() + "','" + oUsuar.getIdUsuario() + "','" + oUsuar.getPassword() + "','" + sPerfil + "','" + oUsuar.getIdUsuario2() + "'," + num + "," + nu + ");";
            oAD = new AccesoDatos();
            System.out.println( sQuery );
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

    public int modificarPersonalHospitalarioComp() throws Exception { //EDITADO: 10/04/17 (JMHG)
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( sUsuario.isEmpty() || getNoTarjeta() < 1 ) {
            throw new Exception( "Medico.modificarPersonalHospitalarioComp: error de programación, faltan datos" );
        } else {
            SimpleDateFormat oFormat = new SimpleDateFormat( "yyyy-MM-dd" );
            getCedulaEspecialidad().setNoTarjeta( getNoTarjeta() );
            sQuery = "SELECT * FROM modificaPersonalHospitalarioComp( "
                    + "'" + sUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getActividad() + "', "
                    + "'" + getCedProf() + "', "
                    + "'" + getCedEsp() + "', "
                    + "'" + getCedIMSS() + "', "
                    + "'" + getNombres() + "', "
                    + "'" + getApPaterno() + "', "
                    + "'" + getApMaterno() + "', "
                    + "'" + oFormat.format( getFechaNac() ) + "'::DATE, "
                    + "'" + getSexos() + "', "
                    + "'" + getCurp() + "', "
                    + "'" + getStatus() + "', "
                    + "'" + getIdUsuario() + "', "
                    + "'" + getTipoContrato().getClaveParametro() + getTipoContrato().getTipoParametro() + "', "
                    + "'" + getEstadoCivil().getClaveParametro() + getEstadoCivil().getTipoParametro() + "', "
                    + "'" + getTelefono() + "', "
                    + "'" + getTipoPersonal().getClaveParametro() + getTipoPersonal().getTipoParametro() + "', "
                    + "'" + getFoto() + "', "
                    + getAnoRegCredElector() + "::SMALLINT, "
                    + "'" + getActivo().getClaveParametro() + getActivo().getTipoParametro() + "', "
                    + "'" + oFormat.format( getFechaRegistro() ) + "'::DATE, "
                    + "'" + getRFC() + "', "
                    + "'" + getTipoEmpleado().getClaveParametro() + getTipoEmpleado().getTipoParametro() + "', "
                    + "'" + getCalleNum() + "', "
                    + "'" + getColonia() + "', "
                    + "'" + getEstado().getClaveEdo() + "', "
                    + "'" + getMunicipio().getClaveMun() + "', "
                    + "'" + getCiudad().getClaveCiu() + "', "
                    + "'" + getCiudadCP().getCp() + "', "
                    + "'" + getOtroPais() + "', "
                    + getNoTarjetaBase() + "::INTEGER, "
                    + "'" + getSinergia() + "', "
                    + "'" + getNumExterior() + "', "
                    + "'" + getNumInterior() + "', "
                    + "'" + getTipoVialidad().getCveTipoVial() + "', "
                    + getTipoAsentamiento().getCveAsenta() + "::SMALLINT, "
                    + getPersonalAreaServ().getClaveAreaServicioOld() + "::SMALLINT, "
                    + "'" + getPersonalAreaServ().getClaveTurnoOld() + "', "
                    + getPersonalAreaServ().getAreaServ().getClave() + "::SMALLINT, "
                    + "'" + getPersonalAreaServ().getTurno().getClave() + "', "
                    + "'" + getPersonalAreaServ().getPuesto().getClave() + "', "
                    + "'" + getPersonalAreaServ().getTurnoPersonal().getClaveTurno() + "', "
                    + "'" + getPersonalAreaServ().getTurnoPersonal().getHorarioComp() + "', "
                    + "'" + getPersonalAreaServ().getTurnoPersonal().getDiasTrabajoComp() + "', "
                    + "'" + getPersonalAreaServ().getPersonalGrupo().getClaveGrupo() + "', "
                    + getPersonalAreaServ().getPersonalCategoria().getClaveCategoria() + "::SMALLINT, "
                    + getEspecialidad().getClaveEsp() + "::SMALLINT, "
                    + getTipoMedico().getClaveTipo() + "::SMALLINT, "
                    + getActividadMedico().getClaveActividad() + "::SMALLINT, "
                    + getCedulaEspecialidad().crearQueryArreglos( getListaCedulaEspecialidadOld(), getListaCedulaEspecialidad(), true ) + " );";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                nRet = ((Double) vRowTemp.get( 0 )).intValue();
            }
        }
        return nRet;
    }

    public int modificarActivoPersonal() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( sUsuario.isEmpty() || getNoTarjeta() < 1 || !getActivo().buscar() ) {
            throw new Exception( "Medico.modificaPersonalHospitalarioActivo: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalHospitalarioActivo( "
                    + "'" + sUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getActivo().getClaveParametro() + getActivo().getTipoParametro() + "' );";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                nRet = ((Double) vRowTemp.get( 0 )).intValue();
            }
        }
        return nRet;
    }

    public ArrayList<Medico> buscaAyudanteInstrumentistaCirculante( short opc ) throws Exception {
        Medico oMedico = null;
        ArrayList<Medico> arrRet = null;
        ArrayList rst = null;
        int i = 0;
        String sQuery = "";
        if( opc == 0 ) {
            throw new Exception( "MEDICO.buscaAyudanteInstrumentistaCirculante:NOPODEMOSPROCESARLAINFORMACION" );
        } else {
            sQuery = "SELECT * FROM buscaAyudanteInstrumentistaCirculante(" + opc + "::SMALLINT);";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
                arrRet = new ArrayList<Medico>();
                for( i = 0; i < rst.size(); i++ ) {
                    oMedico = new Medico();
                    ArrayList vRowTemp = (ArrayList) rst.get( i );
                    oMedico.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                    oMedico.setNombres( (String) vRowTemp.get( 1 ).toString() );
                    oMedico.setApPaterno( (String) vRowTemp.get( 2 ).toString() );
                    oMedico.setApMaterno( (String) vRowTemp.get( 3 ).toString() );
                    oMedico.setCedProf( (String) vRowTemp.get( 4 ).toString() );
                    arrRet.add( oMedico );
                }
            }
        }
        return arrRet;
    }

    //*****************************************************************
    @Override
    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaMedico();";
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

    //***************************************METODO CAPASITS**************************************
    public int eliminaUsuarioMedicoCapa() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if( this == null ) {   //completar llave
            throw new Exception( "Medico.eliminausuariomedicoCapa: error de programaciÃ³n, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaPersonalHospitalarioCapa('" + sUsuario + "', " + getNoTarjeta() + ",'" + oUsuar.getIdUsuario2() + "');";
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

//***************************************************************
    //Retorna Lista de Médicos de Urgencias
    public List<Medico> getListaMedicosUrgs() {
        List<Medico> lLista = null;
        try {
            lLista = new ArrayList<Medico>( Arrays.asList(
                    (new Medico()).buscarMedicosUrg() ) );
        } catch( Exception ex ) {
            Logger.getLogger( registrarPaciente.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return lLista;
    }

    //Retorna Lista de Médicos de Urgencias y Hospitalizacion
    public List<Medico> getListaMedicosHosp() {
        List<Medico> lLista = null;
        try {
            lLista = new ArrayList<Medico>( Arrays.asList(
                    (new Medico()).buscarMedicosHosp() ) );
        } catch( Exception ex ) {
            Logger.getLogger( registrarPaciente.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return lLista;
    }

    public Medico[] buscarMedicosProcedimientos( String nom ) throws Exception {
        Medico arrRet[] = null, oMed = null;
        ArrayList rst = null;
        ArrayList<Medico> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaMedicoProcedimiento('" + nom + "');";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null ) {
            vObj = new ArrayList<Medico>();
            for( i = 0; i < rst.size(); i++ ) {
                oMed = new Medico();
                ArrayList vRowTemp = (ArrayList) rst.get( i );
                oMed.setNombres( (String) vRowTemp.get( 0 ) );
                //o.setDescripcionDiag((String)vRowTemp.get(0));
                vObj.add( oMed );
                //System.out.println("buscarCIE10: Diag-->"+oDiag.getDescripcionDiag().toString());
            }
            nTam = vObj.size();
            arrRet = new Medico[nTam];

            for( i = 0; i < nTam; i++ ) {
                arrRet[i] = vObj.get( i );
            }
        }
        return arrRet;
    }

    //Retorna lista Diagnosticos
    public List<Medico> getListaMedicosProcedimientos( String txt ) {
        List<Medico> lListaDiag = null;

        try {
            lListaDiag = new ArrayList<Medico>( Arrays.asList(
                    (new Medico()).buscarMedicosProcedimientos( txt ) ) );
        } catch( Exception ex ) {
            Logger.getLogger( Medico.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return lListaDiag;
    }

    public List<String> completar( String sTxt ) {
        ArrayList<String> arrRet = new ArrayList<String>();
        List<Medico> lis = getListaMedicosProcedimientos( sTxt );
        for( Medico li : lis ) {
            if( sp_ascii( li.getNombres() ).contains( sTxt ) ) {
                arrRet.add( li.getNombres() );
            }
        }
        return arrRet;
    }

    public String sp_ascii( String input ) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for( int i = 0; i < original.length(); i++ ) {
            // Reemplazamos los caracteres especiales.
            output = output.replace( original.charAt( i ), ascii.charAt( i ) );
        }//for i
        return output;
    }

    public ArrayList getrrConsultorio() {
        return arrConsultorio;
    }

    public void setrrConsultorio( ArrayList valor ) {
        arrConsultorio = valor;
    }

    public Consultorio getCons() {
        return oCons;
    }

    public void setCons( Consultorio oCons ) {
        this.oCons = oCons;
    }

    public AreaServicioHRRB getArea() {
        return oArea;
    }

    public void setArea( AreaServicioHRRB oArea ) {
        this.oArea = oArea;
    }

    public AsignaConsultorio getAsigCon() {
        return oAsigCon;
    }

    public void setAsigCon( AsignaConsultorio oAsigCon ) {
        this.oAsigCon = oAsigCon;
    }

    public String getFechaNacTexto() {
        return sFechaNacTexto;
    }

    public void setFechaNacTexto( String sFechaNacTexto ) {
        this.sFechaNacTexto = sFechaNacTexto;
    }

    public String getCpuesto() {
        return sCpuesto;
    }

    public String getCturno() {
        return sCturno;
    }

    @Override
    public Usuario getUsuar() {
        return oUsuar;
    }

    @Override
    public void setUsuar( Usuario valor ) {
        this.oUsuar = valor;
    }

    public Turno getTurn() {
        return oTurn;
    }

    public void setTurn( Turno valor ) {
        this.oTurn = valor;
    }

    public String getMedicoResponsableStr() {
        return medicoResponsableStr;
    }

    public void setMedicoResponsableStr( String medicoResponsableStr ) {
        this.medicoResponsableStr = medicoResponsableStr;
    }

//*********************************MÉTODO DE CAPASITS*****************************************
    public Horario getHorarios() {
        return oHora;
    }
//*********************************MÉTODO DE CAPASITS*****************************************

    public void setHorarios( Horario valor ) {
        oHora = valor;
    }

    public Cita getCit() {
        return oCit;
    }

    public void setCit( Cita valor ) {
        oCit = valor;
    }

//*********************************MÉTODO DE CAPASITS*****************************************
    public PacienteCapasits getPacienteCapa() {
        return oPacCapa;
    }

    //*********************************MÉTODO DE CAPASITS*****************************************
    public void setPacienteCapa( PacienteCapasits valor ) {
        oPacCapa = valor;
    }

    public Especialidad getEspecialidad() {
        return oEspecialidad;
    }

    public void setEspecialidad( Especialidad oEspecialidad ) {
        this.oEspecialidad = oEspecialidad;
    }

    public ActividadMedico getActividadMedico() {
        return oActividadMedico;
    }

    public void setActividadMedico( ActividadMedico oActividadMedico ) {
        this.oActividadMedico = oActividadMedico;
    }

    public TipoMedico getTipoMedico() {
        return oTipoMedico;
    }

    public void setTipoMedico( TipoMedico oTipoMedico ) {
        this.oTipoMedico = oTipoMedico;
    }
}
