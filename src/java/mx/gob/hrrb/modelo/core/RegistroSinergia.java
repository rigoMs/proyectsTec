package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author JMHG
 */
public class RegistroSinergia implements Serializable {

    private Date dFechaRegistro;
    private String sIdUsuarioReg;
    private String sNombre;
    private Date dFechaRegistroOld;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    public static final int ID_CLASE = 7;

    private String sNoPersonal;
    private String sNoGrupo;
    private String sRFC;
    private String sFuncionNombre;
    private String sCategoria;
    private String sCodigo;
    private String sTipoPlaza;
    private String sOrigenPlaza;
    private String sTurno;
    private String sHorario;
    private String sDiasTrabajo;
    private char[] arrDiasDescanso;
    private int nNoTarjeta;
    private String sNombrePersonal;
    private String sActivo;
    private Date dFechaCambioActivo;

    public RegistroSinergia() {
        dFechaRegistro = null;
        sIdUsuarioReg = "";
        sNombre = "";
        dFechaRegistroOld = null;

        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if( httpServletRequest.getSession().getAttribute( "oFirm" ) != null ) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute( "oFirm" );
            sIdUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    private void init() {
        sNoPersonal = "";
        sNoGrupo = "";
        sRFC = "";
        sFuncionNombre = "";
        sCategoria = "";
        sCodigo = "";
        sTipoPlaza = "";
        sOrigenPlaza = "";
        sTurno = "";
        sHorario = "";
        sDiasTrabajo = "";
        arrDiasDescanso = null;
        nNoTarjeta = 0;
        sActivo = "";
        sNombrePersonal = "";
        dFechaCambioActivo = null;
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat oFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        if( getFechaRegistro() == null ) {
            throw new Exception( "RegistroSinergia.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveRegistroSinergia( '" + oFormat.format( getFechaRegistro() ) + "'::DATE );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setFechaRegistro( (Date) vRowTemp.get( 0 ) );
                setFechaRegistroOld( (Date) vRowTemp.get( 0 ) );
                setIdUsuarioReg( (String) vRowTemp.get( 1 ) );
                setNombre( (String) vRowTemp.get( 2 ) );
                bRet = true;
            }
        }
        return bRet;
    }

    public String buscarVariableDeClase( String sVariable ) throws Exception {
        String sRet = "";
        ArrayList rst = null;
        String sQuery = "SELECT * FROM buscaLlaveVariablesDeClase( " + ID_CLASE + "::SMALLINT, '" + sVariable + "' )";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() == 1 ) {
            ArrayList vRowTemp = (ArrayList) rst.get( 0 );
            sRet = (String) vRowTemp.get( 2 );
        }
        return sRet;
    }

    public boolean buscarUsuario( String sPwd ) throws Exception {
        Usuario oUsuario = new Usuario();
        oUsuario.setIdUsuario( sIdUsuario );
        oUsuario.setPassword( sPwd );
        return oUsuario.buscarUsuario();
    }

    public RegistroSinergia[] buscarTodos() throws Exception {
        RegistroSinergia[] arrRet = null;
        RegistroSinergia oRegSinergia = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosRegistroSinergia();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new RegistroSinergia[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oRegSinergia = new RegistroSinergia();
                oRegSinergia.setFechaRegistro( (Date) vRowTemp.get( 0 ) );
                oRegSinergia.setFechaRegistroOld( (Date) vRowTemp.get( 0 ) );
                oRegSinergia.setIdUsuarioReg( (String) vRowTemp.get( 1 ) );
                oRegSinergia.setNombre( (String) vRowTemp.get( 2 ) );
                arrRet[i] = oRegSinergia;
            }
        }
        return arrRet;
    }

    public RegistroSinergia[] buscarTodosFiltro() throws Exception {
        RegistroSinergia[] arrRet = null;
        RegistroSinergia oRegSinergia = null;
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat oFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        sQuery = "SELECT * FROM buscaRegistroSinergiaFiltro("
                + (getFechaRegistroOld() != null ? "'" + oFormat.format( getFechaRegistroOld() ) + "'::DATE" : "NULL") + ", "
                + (getFechaRegistro() != null ? "'" + oFormat.format( getFechaRegistro() ) + "'::DATE" : "NULL") + ", "
                + "'" + getIdUsuarioReg() + "', "
                + "'" + getNombre() + "' );";
        System.out.println( sQuery );
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new RegistroSinergia[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oRegSinergia = new RegistroSinergia();
                oRegSinergia.setFechaRegistro( (Date) vRowTemp.get( 0 ) );
                oRegSinergia.setFechaRegistroOld( (Date) vRowTemp.get( 0 ) );
                oRegSinergia.setIdUsuarioReg( (String) vRowTemp.get( 1 ) );
                oRegSinergia.setNombre( (String) vRowTemp.get( 2 ) );
                arrRet[i] = oRegSinergia;
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat oFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        if( sIdUsuario == null || sIdUsuario.isEmpty() || getNombre() == null || getNombre().isEmpty() || getFechaRegistro() == null ) {
            throw new Exception( "RegistroSinergia.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaRegistroSinergia( "
                    + "'" + sIdUsuario + "', "
                    + "'" + oFormat.format( getFechaRegistro() ) + "'::DATE, "
                    + "'" + getNombre() + "' );";
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

    public int modificar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat oFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        if( sIdUsuario == null || sIdUsuario.isEmpty() || getFechaRegistroOld() == null || getFechaRegistro() == null ) {
            throw new Exception( "RegistroSinergia.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaRegistroSinergia( "
                    + "'" + sIdUsuario + "', "
                    + "'" + oFormat.format( getFechaRegistroOld() ) + "'::DATE, "
                    + "'" + oFormat.format( getFechaRegistro() ) + "'::DATE, "
                    + "'" + getNombre() + "' );";
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

    public int eliminar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        SimpleDateFormat oFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        if( sIdUsuario == null || sIdUsuario.isEmpty() || getFechaRegistro() == null ) {
            throw new Exception( "RegistroSinergia.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaRegistroSinergia( "
                    + "'" + sIdUsuario + "', "
                    + "'" + oFormat.format( getFechaRegistro() ) + "' );";
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

    public RegistroSinergia[] buscarTodosSinergia() throws Exception {
        ArrayList rst = null;
        RegistroSinergia[] arrRet = null;
        RegistroSinergia oReg = null;
        String sQuery = "SELECT * FROM buscaTodosPersonalHospitalarioSinergia();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new RegistroSinergia[rst.size()];
            ArrayList vRowTemp;
            String sVal;
            int nPersonal;
            for( int i = 0; i < rst.size(); i++ ) {
                sVal = "";
                nPersonal = 0;
                vRowTemp = (ArrayList) rst.get( i );
                oReg = new RegistroSinergia();
                nPersonal = ((Double) vRowTemp.get( 0 )).intValue();
                oReg.setNoPersonal( nPersonal == 0 ? null : ("" + nPersonal) );
                sVal = ((String) vRowTemp.get( 1 )).trim();
                oReg.setNoGrupo( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 2 )).trim();
                oReg.setRFC( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 3 )).trim();
                oReg.setFuncionNombre( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 4 )).trim().replaceAll( "&quot;", "\"" );
                oReg.setCategoria( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 5 )).trim();
                oReg.setCodigo( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 6 )).trim();
                oReg.setTipoPlaza( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 7 )).trim();
                oReg.setOrigenPlaza( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 8 )).trim();
                oReg.setTurno( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 9 )).trim();
                oReg.setHorario( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 10 )).trim();
                oReg.setDiasTrabajo( !sVal.isEmpty() ? sVal : null );
                oReg.setNoTarjeta( ((Double) vRowTemp.get( 11 )).intValue() );
                sVal = ((String) vRowTemp.get( 12 )).trim();
                oReg.setNombrePersonal( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 13 )).trim();
                oReg.setActivo( !sVal.isEmpty() ? sVal : null );
                oReg.setFechaCambioActivo( (Date) vRowTemp.get( 14 ) );
                oReg.crearArrDiasDescanso();
                arrRet[i] = oReg;
            }
        }
        return arrRet;
    }

    public RegistroSinergia[] buscarTodosSinergiaResumen() throws Exception {
        ArrayList rst = null;
        RegistroSinergia[] arrRet = null;
        RegistroSinergia oPers = null;
        String sQuery = "SELECT * FROM buscaTodosPersonalHospitalarioSinergiaResumen();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new RegistroSinergia[rst.size()];
            ArrayList vRowTemp;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPers = new RegistroSinergia();
                int nTotal = ((Double) vRowTemp.get( 2 )).intValue();
                String sVal = ((String) vRowTemp.get( 0 )).trim();
                oPers.setCategoria( !sVal.isEmpty() ? sVal : null );
                sVal = ((String) vRowTemp.get( 1 )).trim();
                oPers.setFuncionNombre( !sVal.isEmpty() ? sVal : null );
                if( nTotal == 0 ) {
                    if( oPers.getFuncionNombre() == null ) {
                        sVal = null;
                    } else {
                        sVal = "-";
                    }
                } else {
                    sVal = "" + nTotal;
                }
                oPers.setNoPersonal( sVal );
                arrRet[i] = oPers;
            }
        }

        return arrRet;
    }

    private void crearArrDiasDescanso() {
        if( getDiasTrabajo() != null && !getDiasTrabajo().isEmpty() ) {
            setArrDiasDescanso( new char[]{'X', 'X', 'X', 'X', 'X', 'X', 'X'} );
            char[] arrDiasTrabajo = getDiasTrabajo().toCharArray();
            for( int i = 0; i < arrDiasTrabajo.length; i++ ) {
                int nPos = -1;
                switch( arrDiasTrabajo[i] ) {
                    case 'L':
                        nPos = 0;
                        break;
                    case 'M':
                        nPos = 1;
                        break;
                    case 'X':
                        nPos = 2;
                        break;
                    case 'J':
                        nPos = 3;
                        break;
                    case 'V':
                        nPos = 4;
                        break;
                    case 'S':
                        nPos = 5;
                        break;
                    case 'D':
                        nPos = 6;
                        break;
                }
                getArrDiasDescanso()[nPos] = ' ';
            }
        } else {
            setArrDiasDescanso( new char[7] );
        }
    }

    public Date getFechaRegistro() {
        return dFechaRegistro;
    }

    public void setFechaRegistro( Date dFechaRegistro ) {
        this.dFechaRegistro = dFechaRegistro;
    }

    public String getIdUsuarioReg() {
        return sIdUsuarioReg;
    }

    public void setIdUsuarioReg( String sIdUsuarioReg ) {
        this.sIdUsuarioReg = sIdUsuarioReg;
    }

    public String getNombre() {
        return sNombre;
    }

    public void setNombre( String sNombre ) {
        this.sNombre = sNombre;
    }

    public Date getFechaRegistroOld() {
        return dFechaRegistroOld;
    }

    public void setFechaRegistroOld( Date dFechaRegistroOld ) {
        this.dFechaRegistroOld = dFechaRegistroOld;
    }

    public String getNoPersonal() {
        return sNoPersonal;
    }

    public void setNoPersonal( String sNoPersonal ) {
        this.sNoPersonal = sNoPersonal;
    }

    public String getNoGrupo() {
        return sNoGrupo;
    }

    public void setNoGrupo( String sNoGrupo ) {
        this.sNoGrupo = sNoGrupo;
    }

    public String getRFC() {
        return sRFC;
    }

    public void setRFC( String sRFC ) {
        this.sRFC = sRFC;
    }

    public String getFuncionNombre() {
        return sFuncionNombre;
    }

    public void setFuncionNombre( String sFuncionNombre ) {
        this.sFuncionNombre = sFuncionNombre;
    }

    public String getCategoria() {
        return sCategoria;
    }

    public void setCategoria( String sCategoria ) {
        this.sCategoria = sCategoria;
    }

    public String getCodigo() {
        return sCodigo;
    }

    public void setCodigo( String sCodigo ) {
        this.sCodigo = sCodigo;
    }

    public String getTipoPlaza() {
        return sTipoPlaza;
    }

    public void setTipoPlaza( String sTipoPlaza ) {
        this.sTipoPlaza = sTipoPlaza;
    }

    public String getOrigenPlaza() {
        return sOrigenPlaza;
    }

    public void setOrigenPlaza( String sOrigenPlaza ) {
        this.sOrigenPlaza = sOrigenPlaza;
    }

    public String getTurno() {
        return sTurno;
    }

    public void setTurno( String sTurno ) {
        this.sTurno = sTurno;
    }

    public String getHorario() {
        return sHorario;
    }

    public void setHorario( String sHorario ) {
        this.sHorario = sHorario;
    }

    public char[] getArrDiasDescanso() {
        return arrDiasDescanso;
    }

    public void setArrDiasDescanso( char[] arrDiasDescanso ) {
        this.arrDiasDescanso = arrDiasDescanso;
    }

    public int getNoTarjeta() {
        return nNoTarjeta;
    }

    public void setNoTarjeta( int nNoTarjeta ) {
        this.nNoTarjeta = nNoTarjeta;
    }

    public String getNombrePersonal() {
        return sNombrePersonal;
    }

    public void setNombrePersonal( String sNombrePersonal ) {
        this.sNombrePersonal = sNombrePersonal;
    }

    public String getActivo() {
        return sActivo;
    }

    public void setActivo( String sActivo ) {
        this.sActivo = sActivo;
    }

    public Date getFechaCambioActivo() {
        return dFechaCambioActivo;
    }

    public void setFechaCambioActivo( Date dFechaCambioActivo ) {
        this.dFechaCambioActivo = dFechaCambioActivo;
    }

    public String getDiasTrabajo() {
        return sDiasTrabajo;
    }

    public void setDiasTrabajo( String sDiasTrabajo ) {
        this.sDiasTrabajo = sDiasTrabajo;
    }

    public String getIdUsuario() {
        return sIdUsuario;
    }
}
