package mx.gob.hrrb.jbs.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.ActividadMedico;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.CapacitacionPersonal;
import mx.gob.hrrb.modelo.core.CedulaEspecialidad;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.Especialidad;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Pais;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalGrupo;
import mx.gob.hrrb.modelo.core.PersonalGrupoAreaPuesto;
import mx.gob.hrrb.modelo.core.Puesto;
import mx.gob.hrrb.modelo.core.TipoMedico;
import mx.gob.hrrb.modelo.core.PersonalCategoria;
import mx.gob.hrrb.modelo.core.TipoAsentamiento;
import mx.gob.hrrb.modelo.core.TipoVialidad;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.core.TurnoPersonal;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 * @editor JMHG
 */
@ManagedBean( name = "oAddPer" )
@ViewScoped
public class addPersonal implements Serializable {

    private Medico oMed;
    private Medico oMedBase;
    private String sTitulo;
    private String sMostrarCompBusq;
    private String sMostrarCompReg;
    private boolean bInterino;
    private boolean bMedico;
    private boolean bOtroPais;
    private boolean bModificar;
    private boolean bCarnetPersonal;
    private FacesMessage message;

    private String sIdFormCompReg;
    private boolean bEncontrado;
    private List<Pais> lPais;
    private List<Estado> lEstado;
    private List<Municipio> lMunicipio;
    private List<Ciudad> lCiudad;
    private List<CiudadCP> lCiudadCP;
    private List<PersonalGrupo> lPersonalGrupo;
    private List<AreaServicioHRRB> lAreaServicio;
    private List<Puesto> lPuesto;
    private List<ActividadMedico> lActividadMedico;
    private List<PersonalCategoria> lPersonalCategoria;
    private List<Especialidad> lEspecialidad;
    private List<Parametrizacion> lEstadoCivil;
    private List<Parametrizacion> lTipoContrato;
    private List<TipoMedico> lTipoMedico;
    private List<Parametrizacion> lTipoPersonal;
    private List<TurnoPersonal> lTurnoPersonal;
    private List<Parametrizacion> lStatusActividad;
    private List<Parametrizacion> lTipoEmpleado;
    private boolean bPersonalActivo;
    private String sActivoTipo;

    private static String CVE_EDO_CIVIL;
    private static String CVE_TIPO_CONTRATO;
    private static String CVE_TIPO_PERSONAL;
    private static String CVE_TIPO_EMPLEADO;

    private PersonalGrupoAreaPuesto oPGAP;
    private List<String> lIdsComps;

    //AGREGADOS: 10/04/17 (JMHG)
    private List<TipoVialidad> lTipoVialidad;
    private List<TipoAsentamiento> lTipoAsentamiento;
    private String sCedulaProfOld;
    private String sCurpOld;
    //====

    public addPersonal() {
        init();
    }

    private void init() {
        limpiar();
        lActividadMedico = null;
        lPersonalCategoria = null;
        lEspecialidad = null;
        lEstadoCivil = null;
        lTipoContrato = null;
        lTipoMedico = null;
        lTipoPersonal = null;
        lTurnoPersonal = null;
        lStatusActividad = null;
        lTipoEmpleado = null;
        lIdsComps = null;
        crearListaCiudad();
        crearListaCiudadCP();
        crearListaPersonalGrupo();
        crearListaPuesto();
        crearListaAreaServicioHRRB();
        crearListaPersonalCategoria();
        crearListaEspecialidad();
        crearListaEstadoCivil();
        crearListaTipoContrato();
        crearListaTipoMedico();
        crearListaTipoPersonal();
        crearListaTurnoPersonal();
        crearListaStatusActividad();
        crearListaTipoEmpleado();
        crearListaTipoVialidad();
        crearListaTipoAsentamiento();
    }

    public void limpiar() {
        oMed = new Medico();
        oMedBase = new Medico();
        sTitulo = "Registrar Personal Hospitalario";
        sMostrarCompBusq = "block;";
        sMostrarCompReg = "none;";
        bInterino = false;
        bMedico = false;
        bModificar = false;
        bOtroPais = false;
        bCarnetPersonal = false;
        message = null;

        sIdFormCompReg = "";
        bEncontrado = false;
        lPais = null;
        lEstado = null;
        lMunicipio = null;
        lCiudad = null;
        lCiudadCP = null;
        lAreaServicio = null;
        lPuesto = null;
        lPuesto = null;
        lAreaServicio = null;

        crearListaPais();
        crearListaEstado();
        crearListaMunicipio();
        bPersonalActivo = true;
        sActivoTipo = "";
        oPGAP = new PersonalGrupoAreaPuesto();
        sCedulaProfOld = "";
        sCurpOld = "";
    }

    public void verificarNoTarjeta() {
        try {
            if( oMed.buscarPersonalHosp() ) {
                FacesContext.getCurrentInstance().addMessage( null,
                        new FacesMessage( FacesMessage.SEVERITY_ERROR, "No. Tarjeta", "El número de tarjeta no esta disponible" ) );
                oMed.setNoTarjeta( 0 );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage( null,
                    new FacesMessage( FacesMessage.SEVERITY_ERROR, "No. Tarjeta", "Hubo un error interno" ) );
        }
    }

    public void verificarCedulaProf() {
        if( !oMed.getCedProf().trim().isEmpty() && oMed.getCedProf().trim().compareTo( sCedulaProfOld ) != 0 ) {
            try {
                if( oMed.buscarCedulaProf() ) {
                    FacesContext.getCurrentInstance().addMessage( null,
                            new FacesMessage( FacesMessage.SEVERITY_ERROR, "Cedula Prof.", "La cedula profesional ya esta asignada" ) );
                    oMed.setCedProf( "" );
                }
            } catch( Exception ex ) {
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage( null,
                        new FacesMessage( FacesMessage.SEVERITY_ERROR, "Cedula Prof.", "Hubo un error interno" ) );
            }
        }
    }

    public void verificaCurp() {
        message = null;
        if( !oMed.getCurp().trim().isEmpty() && oMed.getCurp().trim().compareTo( sCurpOld ) != 0 ) {
            Medico oMedCurp = new Medico();
            oMedCurp.setCurp( oMed.getCurp() );
            try {
                if( oMedCurp.buscarPersonalPorCurp() ) {
                    message = new FacesMessage( FacesMessage.SEVERITY_ERROR, "CURP", "La CURP ya esta registrada" );
                    oMed.setCurp( sCurpOld );
                }
            } catch( Exception ex ) {
                message = new FacesMessage( FacesMessage.SEVERITY_ERROR, "CURP", "Hubo un error interno" );
            }
        }
        if( message != null ) {
            FacesContext.getCurrentInstance().addMessage( null, message );
        }
    }

    public void buscarPersonalComp( int nNoTarjeta ) {
        oMed.setNoTarjeta( nNoTarjeta );
        bEncontrado = false;
        String sMensaje = "";
        try {
            bEncontrado = oMed.buscarPersonalHospitalarioComp();
            if( !bEncontrado ) {
                sMensaje = "No se encontró la información del personal seleccionado";
                sMostrarCompReg = "none;";
            } else {
                sMostrarCompReg = "block;";
                sMostrarCompBusq = "none;";
                if( oMed.getNoTarjetaBase() > 0 ) {
                    oMedBase = new Medico();
                    oMedBase.setNoTarjeta( oMed.getNoTarjeta() );
                    oMedBase.buscarPersonalHosp();
                    bInterino = true;
                }
                if( oMed.getActivo().getTipoParametro() == null ) {
                    oMed.getActivo().setTipoParametro( "99" );
                    bPersonalActivo = true;
                } else {
                    bPersonalActivo = (oMed.getActivo().getTipoParametro().trim().compareTo( "01" ) == 0);
                }
                sActivoTipo = oMed.getActivo().getTipoParametro();
                sCedulaProfOld = oMed.getCedProf();
                sCurpOld = oMed.getCurp();
                crearListaPais();
                crearListaEstado();
                crearListaMunicipio();
                crearListaCiudad();
                crearListaCiudadCP();
                crearListaPersonalGrupo();
                crearListaPuesto();
                crearListaAreaServicioHRRB();
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            if( nNoTarjeta < 1 ) {
                sMensaje = "El personal hospitalario seleccionado no es válido";
            } else {
                sMensaje = "Hubo un error interno.";
            }
            sMostrarCompReg = "none;";
            sMostrarCompBusq = "block;";
            bEncontrado = false;
        }

        if( !sMensaje.isEmpty() ) {
            RequestContext.getCurrentInstance().showMessageInDialog( new FacesMessage( "Buscar Personal Hospitalario", sMensaje ) );
        }
    }

    public void buscarPersonalBase( int nNoTarjeta ) {
        oMedBase = new Medico();
        oMedBase.setNoTarjeta( nNoTarjeta );
        boolean bEncontradoMedBase = false;
        String sMensaje = "";
        try {
            bEncontradoMedBase = oMedBase.buscarPersonalHospitalarioBase();
            if( bEncontradoMedBase ) {
                oMed.setNoTarjetaBase( oMedBase.getNoTarjeta() );
                oMed.getTipoPersonal().setTipoParametro( oMedBase.getTipoPersonal().getTipoParametro() );
                //oMed.setTipoEmpleado( oMedBase.getTipoEmpleado() );
                oMed.getPersonalAreaServ().getAreaServ().setClave( oMedBase.getPersonalAreaServ().getAreaServ().getClave() );
                oMed.getPersonalAreaServ().getTurno().setClave( oMedBase.getPersonalAreaServ().getTurno().getClave() );
                oMed.getPersonalAreaServ().getPuesto().setClave( oMedBase.getPersonalAreaServ().getPuesto().getClave() );
                oMed.getPersonalAreaServ().getTurnoPersonal().setClaveTurno( oMedBase.getPersonalAreaServ().getTurnoPersonal().getClaveTurno() );
                oMed.getPersonalAreaServ().getTurnoPersonal().setHorarioDef( oMedBase.getPersonalAreaServ().getTurnoPersonal().getHorarioDef() );
                oMed.getPersonalAreaServ().getTurnoPersonal().setDiasTrabajoDef(
                        oMedBase.getPersonalAreaServ().getTurnoPersonal().getDiasTrabajoDef() );
                oMed.getPersonalAreaServ().getPersonalGrupo().setClaveGrupo( oMedBase.getPersonalAreaServ().getPersonalGrupo().getClaveGrupo() );
                oMed.getPersonalAreaServ().getPersonalCategoria().setClaveCategoria(
                        oMedBase.getPersonalAreaServ().getPersonalCategoria().getClaveCategoria() );
                //oMed.getEspecialidad().setClaveEsp( oMedBase.getEspecialidad().getClaveEsp() );
                //oMed.getTipoMedico().setClaveTipo( oMedBase.getTipoMedico().getClaveTipo() );
                //oMed.getActividadMedico().setClaveActividad( oMedBase.getActividadMedico().getClaveActividad() );
                oMed.getPersonalAreaServ().getTurnoPersonal().crearHorarioDef();
                oMed.getPersonalAreaServ().getTurnoPersonal().crearArrDiasTrabajo();
                crearListaPersonalGrupo();
                crearListaPuesto();
                crearListaAreaServicioHRRB();
                RequestContext.getCurrentInstance().update( lIdsComps );
            } else {
                sMensaje = "No se encontró la información del personal seleccionado";
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error interno.";
        }

        if( !sMensaje.isEmpty() ) {
            RequestContext.getCurrentInstance().showMessageInDialog( new FacesMessage( "Buscar Personal Hospitalario", sMensaje ) );
            System.out.println( sMensaje );
        }
    }

    public void ingresaPersonal() {
        String sMensaje = "Registro Fallido";
        int nIns = 0;
        try {
            oMed.getEstadoCivil().setClaveParametro( CVE_EDO_CIVIL );
            oMed.getTipoContrato().setClaveParametro( CVE_TIPO_CONTRATO );
            oMed.getTipoPersonal().setClaveParametro( CVE_TIPO_PERSONAL );
            oMed.getTipoEmpleado().setClaveParametro( CVE_TIPO_EMPLEADO );
            nIns = oMed.insertaPersonalHospitalarioComp();
            if( nIns > 0 ) {
                sMensaje = "Registro Exitoso";
                limpiar();
            } else {
                if( nIns == -1 ) {
                    sMensaje = "El personal ya esta registrado.";
                }
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }

        message = new FacesMessage( FacesMessage.SEVERITY_INFO, "Registro de Personal Hospitalario", sMensaje );
        RequestContext.getCurrentInstance().showMessageInDialog( message );
    }

    public String modificaPersonal() {
        String sMensaje = "Modificación Fallida";
        String sPagina = null;
        try {
            oMed.getActivo().setTipoParametro( sActivoTipo );
            int nRet = oMed.modificarPersonalHospitalarioComp();
            if( nRet > 0 ) {
                sMensaje = "Modificación Exitosa";
                sPagina = "ModificarRegistroPersonal";
                limpiar();
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error al intentar modificar el registro del personal";
        }

        message = new FacesMessage( FacesMessage.SEVERITY_INFO, "Modificar Registro Personal Hospitalario", sMensaje );
        RequestContext.getCurrentInstance().showMessageInDialog( message );
        return sPagina;
    }

    public String modificarActivacionPersonal() {
        String sPagina = null;
        String sMensaje = "Cambio de activacion fallido";
        try {
            int nRet = oMed.modificarActivoPersonal();
            if( nRet > 0 ) {
                sPagina = "ModificarRegistroPersonal?faces-redirect=true";
                sMensaje = "Cambio de activación exitoso";
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error al intentar cambiar la activación del personal";
        }
        message = new FacesMessage( FacesMessage.SEVERITY_INFO, "Modificar Registro Personal Hospitalario", sMensaje );
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage( null, message );
        context.getExternalContext().getFlash().setKeepMessages( true );
        return sPagina;
    }

    public void registrarCapacitacion() {
        String sMensaje = "Se registraron las capacitaciones con éxito";
        CapacitacionPersonal oCapasitacionPer = new CapacitacionPersonal();
        oCapasitacionPer.setNoTarjeta( oMed.getNoTarjeta() );
        boolean bDuplicado = false;
        try {
            if( oMed.getListaCapacitacionPersonal() != null && oMed.getListaCapacitacionPersonal().size() > 1 ) {
                List<CapacitacionPersonal> lista = new ArrayList<>();
                loop:
                for( CapacitacionPersonal oCapPers : oMed.getListaCapacitacionPersonal() ) {
                    if( !lista.isEmpty() ) {
                        for( CapacitacionPersonal oCP : lista ) {
                            if( oCP.getFechaReal().compareTo( oCapPers.getFechaReal() ) == 0 ) {
                                bDuplicado = true;
                                break loop;
                            }
                        }
                    }
                    lista.add( oCapPers );
                }
            }
            if( !bDuplicado ) {
                int nRet = oCapasitacionPer.modificarComp( oMed.getListaCapacitacionPersonalOld(), oMed.getListaCapacitacionPersonal() );
                if( nRet < 1 ) {
                    sMensaje = "No se registraron las capacitaciones";
                } else {
                    RequestContext.getCurrentInstance().execute( "PF('varDlgCapacitacionPers').hide();" );
                }
                message = new FacesMessage( FacesMessage.SEVERITY_INFO, "Modificar Capacitación Personal", sMensaje );
            } else {
                sMensaje = "Las fechas no pueden ser duplicadas";
                message = new FacesMessage( FacesMessage.SEVERITY_ERROR, "Modificar Capacitación Personal", sMensaje );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error al tratar de registrar los cambios";
            message = new FacesMessage( FacesMessage.SEVERITY_ERROR, "Modificar Capacitación Personal", sMensaje );
        }
        FacesContext.getCurrentInstance().addMessage( null, message );
        System.out.println( sMensaje );
    }

    // *************************************************************************
    // Varias Funciones
    // *************************************************************************
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

    public void revisaInterino() {
        if( oMed != null && oMed.getTipoContrato().getTipoParametro().trim().compareTo( "06" ) == 0 ) { //INTERINO
            bInterino = true;
        } else {
            bInterino = false;
            oMedBase = new Medico();
            oMed.setNoTarjetaBase( 0 );
        }
        if( lIdsComps != null && lIdsComps.size() > 0 ) {
            RequestContext.getCurrentInstance().update( lIdsComps );
        }
    }

    public void togglePaisDiferente() {
        if( oMed.getPais().getClavePais().trim().compareTo( "99" ) == 0 ) {
            bOtroPais = true;
            oMed.setOtroPais( "" );
        } else if( oMed.getPais().getClavePais().trim().compareTo( "MX" ) != 0 ) {
            bOtroPais = true;
            oMed.setOtroPais( oMed.getPais().getDescripcionPais() );
        } else {
            bOtroPais = false;
            oMed.setOtroPais( "" );
        }
    }

    public void crearTurnoPersonal() {
        try {
            oMed.getPersonalAreaServ().getTurnoPersonal().buscar();
            switch( oMed.getPersonalAreaServ().getTurnoPersonal().getClaveTurno() ) {
                case TurnoPersonal.CVE_MATUTINO:
                    oMed.getPersonalAreaServ().getTurno().setClave( Turno.CVE_MATUTINO );
                    break;
                case TurnoPersonal.CVE_VESPERTINO:
                    oMed.getPersonalAreaServ().getTurno().setClave( Turno.CVE_VESPERTINO );
                    break;
                case TurnoPersonal.CVE_NOCTURNO_A:
                case TurnoPersonal.CVE_NOCTURNO_B:
                    oMed.getPersonalAreaServ().getTurno().setClave( Turno.CVE_NOCTURNO );
                    break;
                case TurnoPersonal.CVE_JORNADA_ACUMULADA:
                    oMed.getPersonalAreaServ().getTurno().setClave( Turno.CVE_JORNADA );
                    break;
                default:
                    oMed.getPersonalAreaServ().getTurno().setClave( Turno.CVE_OTRO );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void addRowCedulaEspecialidad() {
        CedulaEspecialidad oCedulaEsp = new CedulaEspecialidad();
        if( oMed.getListaCedulaEspecialidad() == null ) {
            oMed.setListaCedulaEspecialidad( new ArrayList<CedulaEspecialidad>() );
        }
        oCedulaEsp.setTipoComando( "INSERT" );
        oMed.getListaCedulaEspecialidad().add( oCedulaEsp );
    }

    public void delRowCedulaEspecialidad( CedulaEspecialidad oCedEsp ) {
        if( oMed.getListaCedulaEspecialidad() != null && !oMed.getListaCedulaEspecialidad().isEmpty() ) {
            if( bModificar ) {
                int nIndex = oMed.getListaCedulaEspecialidad().indexOf( oCedEsp );
                if( nIndex > -1 ) {
                    CedulaEspecialidad oCedula = oMed.getListaCedulaEspecialidad().remove( nIndex );
                    if( oMed.getListaCedulaEspecialidadOld() != null && !oMed.getListaCedulaEspecialidadOld().isEmpty() ) {
                        nIndex = oMed.getListaCedulaEspecialidadOld().indexOf( oCedula );
                        if( nIndex > -1 ) {
                            oMed.getListaCedulaEspecialidadOld().get( nIndex ).setTipoComando( "DELETE" );
                        }
                    }
                }
            } else {
                oMed.getListaCedulaEspecialidad().remove( oCedEsp );
            }
        } else {
            oMed.setListaCedulaEspecialidad( null );
        }
    }

    public void addRowCapacitacionPersonal() {
        CapacitacionPersonal oCapaPers = new CapacitacionPersonal();
        if( oMed.getListaCapacitacionPersonal() == null ) {
            oMed.setListaCapacitacionPersonal( new ArrayList<CapacitacionPersonal>() );
        }
        oCapaPers.setTipoComando( "INSERT" );
        oMed.getListaCapacitacionPersonal().add( oCapaPers );
    }

    public void delRowCapacitacionPersonal( CapacitacionPersonal oCapacitacionPers ) {
        if( oMed.getListaCapacitacionPersonal() != null && !oMed.getListaCapacitacionPersonal().isEmpty() ) {
            if( bModificar ) {
                int nIndex = oMed.getListaCapacitacionPersonal().indexOf( oCapacitacionPers );
                if( nIndex > -1 ) {
                    CapacitacionPersonal oCapaPers = oMed.getListaCapacitacionPersonal().remove( nIndex );
                    if( oMed.getListaCapacitacionPersonalOld() != null && !oMed.getListaCapacitacionPersonalOld().isEmpty() ) {
                        nIndex = oMed.getListaCapacitacionPersonalOld().indexOf( oCapaPers );
                        if( nIndex > -1 ) {
                            oMed.getListaCapacitacionPersonalOld().get( nIndex ).setTipoComando( "DELETE" );
                        }
                    }
                }
            } else {
                oMed.getListaCapacitacionPersonal().remove( oCapacitacionPers );
            }
        } else {
            oMed.setListaCapacitacionPersonal( null );
        }
    }

    // *************************************************************************
    // Creacion de Listas
    // *************************************************************************
    public void crearListaPais() {
        if( !bEncontrado ) {
            oMed.setPais( new Pais() );
        }
        lPais = null;
        Pais[] arrPais = null;
        String sDescripcion = "";
        try {
            arrPais = (new Pais()).buscarTodos();
            if( arrPais != null ) {
                for( Pais oPais : arrPais ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oPais.getDescripcionPais() );
                    oPais.setDescripcionPais( sDescripcion );
                }
                lPais = new ArrayList<>( Arrays.asList( arrPais ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaEstado() {
        if( !bEncontrado ) {
            oMed.setEstado( new Estado() );
        }
        lEstado = null;
        Estado[] arrEstado = null;
        String sDescripcion = "";
        try {
            arrEstado = (new Estado()).buscarEstadosPorPais( oMed.getPais().getClavePais() );
            if( arrEstado != null ) {
                for( Estado oEstado : arrEstado ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oEstado.getDescripcionEdo() );
                    oEstado.setDescripcionEdo( sDescripcion );
                }
                lEstado = new ArrayList<>( Arrays.asList( arrEstado ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaMunicipio() {
        if( !bEncontrado ) {
            oMed.setMunicipio( new Municipio() );
        }
        lMunicipio = null;
        Municipio[] arrMunicipio = null;
        String sDescripcion = "";
        try {
            arrMunicipio = (new Municipio()).buscarMunicipio( oMed.getEstado().getClaveEdo() );
            if( arrMunicipio != null ) {
                for( Municipio oMunicipio : arrMunicipio ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oMunicipio.getDescripcionMun() );
                    oMunicipio.setDescripcionMun( sDescripcion );
                }
                lMunicipio = new ArrayList<>( Arrays.asList( arrMunicipio ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaCiudad() {
        if( !bEncontrado ) {
            oMed.setCiudad( new Ciudad() );
        }
        lCiudad = null;
        Ciudad[] arrCiudad = null;
        String sDescripcion = "";
        try {
            arrCiudad = (new Ciudad()).buscarCiudad( oMed.getEstado().getClaveEdo(), oMed.getMunicipio().getClaveMun() );
            if( arrCiudad != null ) {
                for( Ciudad oCiudad : arrCiudad ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oCiudad.getDescripcionCiu() );
                    oCiudad.setDescripcionCiu( sDescripcion );
                }
                lCiudad = new ArrayList<>( Arrays.asList( arrCiudad ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaCiudadCP() {
        if( !bEncontrado ) {
            oMed.setCiudadCP( new CiudadCP() );
        }
        lCiudadCP = null;
        CiudadCP[] arrCiudadCP = null;
        try {
            arrCiudadCP = (new CiudadCP()).buscarCP(
                    oMed.getCiudad().getClaveCiu(), oMed.getMunicipio().getClaveMun(), oMed.getEstado().getClaveEdo() );
            if( arrCiudadCP != null ) {
                lCiudadCP = new ArrayList<>( Arrays.asList( arrCiudadCP ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaPersonalGrupo() {
        if( !bEncontrado && oMedBase.getNoTarjeta() < 1 ) {
            oMed.getPersonalAreaServ().setPersonalGrupo( new PersonalGrupo() );
            oPGAP = new PersonalGrupoAreaPuesto();
        }
        lPersonalGrupo = null;
        oPGAP.getPersonalGrupo().setClaveGrupo( (oMed.getPersonalAreaServ().getPersonalGrupo().getClaveGrupo()) );
        PersonalGrupo arrPersonalGrupo[] = null;
        String sDescripcion = "";
        try {
            arrPersonalGrupo = oPGAP.buscarTodosPersonalGrupoDisponible();
            if( arrPersonalGrupo != null ) {
                for( PersonalGrupo oPersonalGrupo : arrPersonalGrupo ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oPersonalGrupo.getDescripcion() );
                    oPersonalGrupo.setDescripcion( sDescripcion );
                }
                lPersonalGrupo = new ArrayList<>( Arrays.asList( arrPersonalGrupo ) );
                //crearListaPuesto();
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaAreaServicioHRRB() {
        if( !bEncontrado && oMedBase.getNoTarjeta() < 1 ) {
            oMed.getPersonalAreaServ().setAreaServ( new AreaServicioHRRB() );
        }
        oPGAP.getPersonalGrupo().setClaveGrupo( (oMed.getPersonalAreaServ().getPersonalGrupo().getClaveGrupo()) );
        oPGAP.getPuesto().setClave( oMed.getPersonalAreaServ().getPuesto().getClave() );
        oPGAP.getAreaServicio().setClave( oMed.getPersonalAreaServ().getAreaServ().getClave() );
        lAreaServicio = null;
        AreaServicioHRRB[] arrAreaServ = null;
        String sDescripcion = "";
        try {
            if( !oMed.getPersonalAreaServ().getPersonalGrupo().getClaveGrupo().isEmpty() ) {
                arrAreaServ = oPGAP.buscarTodosAreaServicioDisponible();
                if( arrAreaServ != null ) {
                    for( AreaServicioHRRB oArea : arrAreaServ ) {
                        sDescripcion = "";
                        sDescripcion = sp_ascii( oArea.getDescripcion() );
                        oArea.setDescripcion( sDescripcion );
                    }
                    lAreaServicio = new ArrayList<>( Arrays.asList( arrAreaServ ) );
                }
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaPuesto() {
        if( !bEncontrado && oMedBase.getNoTarjeta() < 1 ) {
            oMed.getPersonalAreaServ().setPuesto( new Puesto() );
        }
        oPGAP.getPersonalGrupo().setClaveGrupo( (oMed.getPersonalAreaServ().getPersonalGrupo().getClaveGrupo()) );
        oPGAP.getPuesto().setClave( oMed.getPersonalAreaServ().getPuesto().getClave() );
        lPuesto = null;
        lAreaServicio = null;
        Puesto[] arrPuesto = null;
        String sDescripcion = "";
        try {
            if( !oMed.getPersonalAreaServ().getPersonalGrupo().getClaveGrupo().isEmpty() ) {
                arrPuesto = oPGAP.buscarTodosPuestoDisponible();
                if( arrPuesto != null ) {
                    for( Puesto oPuesto : arrPuesto ) {
                        sDescripcion = "";
                        sDescripcion = sp_ascii( oPuesto.getDescripcion() );
                        oPuesto.setDescripcion( sDescripcion );
                    }
                    lPuesto = new ArrayList<>( Arrays.asList( arrPuesto ) );
                }
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaActividadMedico() {
        ActividadMedico[] arrActividadMedico = null;
        String sDescripcion = "";
        try {
            arrActividadMedico = (new ActividadMedico()).buscarTodos(); //INCOMPLETO
            if( arrActividadMedico != null ) {
                for( ActividadMedico oActividadMedico : arrActividadMedico ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oActividadMedico.getDescripcion() );
                    oActividadMedico.setDescripcion( sDescripcion );
                }
                lActividadMedico = new ArrayList<>( Arrays.asList( arrActividadMedico ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaPersonalCategoria() {
        PersonalCategoria[] arrCategoria = null;
        String sDescripcion = "";
        try {
            arrCategoria = (new PersonalCategoria()).buscarTodos();
            if( arrCategoria != null ) {
                for( PersonalCategoria oCategoria : arrCategoria ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oCategoria.getDescripcion() );
                    sDescripcion = sDescripcion.replaceAll( "&quot;", "\"" );
                    oCategoria.setDescripcion( sDescripcion );
                }
                lPersonalCategoria = new ArrayList<>( Arrays.asList( arrCategoria ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaEspecialidad() {
        Especialidad[] arrEsp = null;
        String sDescripcion = "";
        try {
            arrEsp = (new Especialidad()).buscarTodos();
            if( arrEsp != null ) {
                for( Especialidad oEsp : arrEsp ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oEsp.getDescripcion() );
                    oEsp.setDescripcion( sDescripcion );
                }
                lEspecialidad = new ArrayList<>( Arrays.asList( arrEsp ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaEstadoCivil() {
        Parametrizacion[] arrParam = null;
        String sValor = "";
        try {
            arrParam = (new Parametrizacion()).buscarEdosCiviles();
            if( arrParam != null ) {
                for( Parametrizacion oParam : arrParam ) {
                    sValor = "";
                    sValor = sp_ascii( oParam.getValor() );
                    oParam.setValor( sValor );
                }
                CVE_EDO_CIVIL = arrParam[0].getClaveParametro(); //TABLA
                oMed.getEstadoCivil().setClaveParametro( arrParam[0].getClaveParametro() );
                lEstadoCivil = new ArrayList<>( Arrays.asList( arrParam ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaTipoContrato() {
        Parametrizacion[] arrParam = null;
        String sValor = "";
        try {
            arrParam = (new Parametrizacion()).buscarTipoContrato();
            if( arrParam != null ) {
                for( Parametrizacion oParam : arrParam ) {
                    sValor = "";
                    sValor = sp_ascii( oParam.getValor() );
                    oParam.setValor( sValor );
                }
                CVE_TIPO_CONTRATO = arrParam[0].getTipoParametro(); //TABLA
                oMed.getTipoContrato().setClaveParametro( arrParam[0].getTipoParametro() );
                lTipoContrato = new ArrayList<>( Arrays.asList( arrParam ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaTipoMedico() {
        TipoMedico[] arrTipoMedico = null;
        String sDescripcion = "";
        try {
            arrTipoMedico = (new TipoMedico()).buscarTodos();
            if( arrTipoMedico != null ) {
                for( TipoMedico oTipoMed : arrTipoMedico ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oTipoMed.getDescripcion() );
                    oTipoMed.setDescripcion( sDescripcion );
                }
                lTipoMedico = new ArrayList<>( Arrays.asList( arrTipoMedico ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaTipoPersonal() {
        Parametrizacion[] arrParam = null;
        String sValor = "";
        try {
            arrParam = (new Parametrizacion()).buscarTipoPersonal();
            if( arrParam != null ) {
                for( Parametrizacion oParam : arrParam ) {
                    sValor = "";
                    sValor = sp_ascii( oParam.getValor() );
                    oParam.setValor( sValor );
                }
                CVE_TIPO_PERSONAL = arrParam[0].getTipoParametro(); //TABLA
                oMed.getTipoPersonal().setClaveParametro( arrParam[0].getTipoParametro() );
                lTipoPersonal = new ArrayList<>( Arrays.asList( arrParam ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaTurnoPersonal() {
        TurnoPersonal[] arrTurnoPersonal = null;
        String sDescripcion = "";
        try {
            arrTurnoPersonal = (new TurnoPersonal()).buscarTodos();
            if( arrTurnoPersonal != null ) {
                for( TurnoPersonal oTurnoPersonal : arrTurnoPersonal ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oTurnoPersonal.getDescripcion() );
                    oTurnoPersonal.setDescripcion( sDescripcion );
                }
                lTurnoPersonal = new ArrayList<>( Arrays.asList( arrTurnoPersonal ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaCapacitacionPersonal() {
        oMed.getCapacitacionPersonal().setNoTarjeta( oMed.getNoTarjeta() );
        CapacitacionPersonal[] arrCapacitacion = null;
        oMed.setListaCapacitacionPersonal( null );
        oMed.setListaCapacitacionPersonalOld( null );
        try {
            arrCapacitacion = oMed.getCapacitacionPersonal().buscarTodosFiltro();
            if( arrCapacitacion != null ) {
                oMed.setListaCapacitacionPersonal( new ArrayList<>( Arrays.asList( arrCapacitacion ) ) );
                oMed.setListaCapacitacionPersonalOld( new ArrayList<>( Arrays.asList( arrCapacitacion ) ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaStatusActividad() {
        Parametrizacion[] arrParam = null;
        String sValor = "";
        try {
            arrParam = (new Parametrizacion()).buscaEstadoActivoPersonal();
            if( arrParam != null ) {
                for( Parametrizacion oParam : arrParam ) {
                    sValor = sp_ascii( oParam.getValor() ).toUpperCase();
                    oParam.setValor( sValor );
                }
                oMed.getActivo().setClaveParametro( arrParam[0].getClaveParametro() );
                lStatusActividad = new ArrayList<>( Arrays.asList( arrParam ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaTipoEmpleado() {
        Parametrizacion[] arrParam = null;
        try {
            arrParam = (new Parametrizacion()).buscaTipoEmpleado();
            if( arrParam != null ) {
                oMed.getTipoEmpleado().setClaveParametro( arrParam[0].getClaveParametro() );
                CVE_TIPO_EMPLEADO = arrParam[0].getClaveParametro(); //TABLA
                lTipoEmpleado = new ArrayList<>( Arrays.asList( arrParam ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaTipoVialidad() {
        TipoVialidad[] arrTipoVialidad = null;
        String sDescripcion = "";
        try {
            arrTipoVialidad = (new TipoVialidad()).buscarTodos();
            if( arrTipoVialidad != null ) {
                for( TipoVialidad oTipoVial : arrTipoVialidad ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oTipoVial.getDescripcion() );
                    oTipoVial.setDescripcion( sDescripcion );
                }
                lTipoVialidad = new ArrayList<>( Arrays.asList( arrTipoVialidad ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaTipoAsentamiento() {
        TipoAsentamiento[] arrTipoAsentamiento = null;
        String sDescripcion = "";
        try {
            arrTipoAsentamiento = (new TipoAsentamiento()).buscarTodos();
            if( arrTipoAsentamiento != null ) {
                for( TipoAsentamiento oTipoAsenta : arrTipoAsentamiento ) {
                    sDescripcion = "";
                    sDescripcion = sp_ascii( oTipoAsenta.getDescripcion() );
                    oTipoAsenta.setDescripcion( sDescripcion );
                }
                lTipoAsentamiento = new ArrayList<>( Arrays.asList( arrTipoAsentamiento ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void crearListaIdsComps() {
        lIdsComps = new ArrayList<>();
        lIdsComps.add( sIdFormCompReg + ":tTipoPersonal" );
        lIdsComps.add( sIdFormCompReg + ":tCategoria" );
        lIdsComps.add( sIdFormCompReg + ":tGrupo" );
        lIdsComps.add( sIdFormCompReg + ":tPuesto" );
        lIdsComps.add( sIdFormCompReg + ":tServicio" );
        lIdsComps.add( sIdFormCompReg + ":tTurno" );
        lIdsComps.add( sIdFormCompReg + ":panelHorario" );
        lIdsComps.add( sIdFormCompReg + ":pnlPersBaseInterino" );
    }

    // *************************************************************************
    // GET / SET
    // *************************************************************************
    public Medico getMed() {
        return oMed;
    }

    public void setMed( Medico oMed ) {
        this.oMed = oMed;
    }

    public Medico getMedicoBase() {
        return oMedBase;
    }

    public void setMedicoBase( Medico oMedBase ) {
        this.oMedBase = oMedBase;
    }

    public String getTitulo() {
        if( bModificar ) {
            sTitulo = "Modificar Registro Personal Hospitalario";
        }

        return sTitulo;
    }

    public void setTitulo( String sTitulo ) {
        this.sTitulo = sTitulo;
    }

    public String getMostrarCompBusq() {
        return sMostrarCompBusq;
    }

    public void setMostrarCompBusq( String sMostrarCompBusq ) {
        this.sMostrarCompBusq = sMostrarCompBusq;
    }

    public String getMostrarCompReg() {
        return sMostrarCompReg;
    }

    public void setMostrarCompReg( String sMostrarCompReg ) {
        this.sMostrarCompReg = sMostrarCompReg;
    }

    public boolean isInterino() {
        return bInterino;
    }

    public void setInterino( boolean bInterino ) {
        this.bInterino = bInterino;
    }

    public boolean isMedico() {
        if( oMed != null && oMed.getTipoEmpleado().getTipoParametro() != null ) {
            bMedico = (oMed.getTipoEmpleado().getTipoParametro().trim().compareTo( "01" ) == 0);
        }
        return bMedico;
    }

    public boolean isModificar() {
        return bModificar;
    }

    public void setModificar( boolean bModificar ) {
        this.bModificar = bModificar;
    }

    public boolean isOtroPais() {
        return bOtroPais;
    }

    public void setOtroPais( boolean bOtroPais ) {
        this.bOtroPais = bOtroPais;
    }

    public boolean isCarnetPersonal() {
        return bCarnetPersonal;
    }

    public void setCarnetPersonal( boolean bCarnetPersonal ) {
        this.bCarnetPersonal = bCarnetPersonal;
    }

    public String getIdFormCompReg() {
        return sIdFormCompReg;
    }

    public void setIdFormCompReg( String sIdFormCompReg ) {
        this.sIdFormCompReg = sIdFormCompReg;
        crearListaIdsComps();
    }

    public boolean isEncontrado() {
        return bEncontrado;
    }

    public void setEncontrado( boolean bEncontrado ) {
        this.bEncontrado = bEncontrado;
    }

    public List<Pais> getListaPais() {
        return lPais;
    }

    public List<Estado> getListaEstado() {
        return lEstado;
    }

    public List<Municipio> getListaMunicipio() {
        return lMunicipio;
    }

    public List<Ciudad> getListaCiudad() {
        return lCiudad;
    }

    public List<CiudadCP> getListaCiudadCP() {
        return lCiudadCP;
    }

    public List<PersonalGrupo> getListaPersonalGrupo() {
        return lPersonalGrupo;
    }

    public List<AreaServicioHRRB> getListaAreaServicio() {
        return lAreaServicio;
    }

    public List<Puesto> getListaPuesto() {
        return lPuesto;
    }

    public List<ActividadMedico> getListaActividadMedico() {
        return lActividadMedico;
    }

    public List<PersonalCategoria> getListaPersonalCategoria() {
        return lPersonalCategoria;
    }

    public List<Especialidad> getListaEspecialidad() {
        return lEspecialidad;
    }

    public List<Parametrizacion> getListaEstadoCivil() {
        return lEstadoCivil;
    }

    public List<Parametrizacion> getListaTipoContrato() {
        return lTipoContrato;
    }

    public List<TipoMedico> getListaTipoMedico() {
        return lTipoMedico;
    }

    public List<Parametrizacion> getListaTipoPersonal() {
        return lTipoPersonal;
    }

    public List<TurnoPersonal> getListaTurnoPersonal() {
        return lTurnoPersonal;
    }

    public List<Parametrizacion> getListaStatusActividad() {
        return lStatusActividad;
    }

    public List<Parametrizacion> getListaTipoEmpleado() {
        return lTipoEmpleado;
    }

    public List<TipoVialidad> getListaTipoVialidad() {
        return lTipoVialidad;
    }

    public List<TipoAsentamiento> getListaTipoAsentamiento() {
        return lTipoAsentamiento;
    }

    public Date getFechaNacMaxRange() {
        Calendar cFechaMin = Calendar.getInstance( TimeZone.getTimeZone( "Mexico/General" ) );
        cFechaMin.add( Calendar.YEAR, -18 );
        return cFechaMin.getTime();
    }

    public int[] getListaAnoRegistroCredElector() {
        int[] arrnRet = null;
        Date dFechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( dFechaActual );
        int nYearNow = calendar.get( Calendar.YEAR );
        int nYear = 1992;
        int nTotal = (nYearNow - nYear) + 1;
        arrnRet = new int[nTotal];

        for( int i = 0; i < nTotal; i++ ) {
            arrnRet[i] = nYearNow;
            nYearNow--;
        }
        return arrnRet;
    }

    public Date getFechaHoy() {
        return new Date();
    }

    public boolean isPersonalActivo() {
        return bPersonalActivo;
    }

    public void setPersonalActivo( boolean bPersonalActivo ) {
        this.bPersonalActivo = bPersonalActivo;
    }

    public boolean getCedulaObligatoria() {
        boolean bRet = false;
        if( oMed != null && oMed.getTipoEmpleado().getTipoParametro() != null ) {
            switch( oMed.getTipoEmpleado().getTipoParametro().trim() ) {
                case "01":
                case "02":
                    bRet = true;
                    break;
                default:
                    bRet = false;
            }
        }
        return bRet;
    }

    public String getMostrarCedulaObligatoria() {
        String sDisplayCedula = "display: none;";
        if( oMed != null && oMed.getTipoEmpleado().getTipoParametro() != null ) {
            switch( oMed.getTipoEmpleado().getTipoParametro().trim() ) {
                case "01":
                case "02":
                    sDisplayCedula = "display: inline;";
                    break;
                default:
                    sDisplayCedula = "display: none;";
            }
        }
        return sDisplayCedula;
    }
}
