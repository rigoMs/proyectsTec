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
public class TurnoPersonal implements Serializable {

    private String sClaveTurno;
    private String sDescripcion;
    private String sHoraIni;
    private String sHoraFin;
    private String sHorarioDef;
    private String sDiasTrabajoDef;

    public static final String CVE_MATUTINO = "1         ";
    public static final String CVE_VESPERTINO = "2         ";
    public static final String CVE_NOCTURNO_A = "3         ";
    public static final String CVE_NOCTURNO_B = "4         ";
    public static final String CVE_CUBRE_DESCANSOS = "C/D       ";
    public static final String CVE_CUBRE_INCIDENCIAS = "C/I       ";
    public static final String CVE_CUBRE_VACACIONES = "C/V       ";
    public static final String CVE_COMISIONADO = "COMISION  ";
    public static final String CVE_JORNADA_ACUMULADA = "J/A       ";
    public static final String CVE_JORNADA_ESPECIAL = "J/E       ";
    public static final String CVE_LICENCIA_SINDICAL = "LITIGIO   ";
    public static final String CVE_LITIGIO_LABORAL = "SINDICATO ";
    public static final String CVE_TIEMPO_COMPLETO = "T/C       ";

    private Date dHoraIni;
    private String sHoraIniMin;
    private String sHoraIniMax;
    private Date dHoraFin;
    private String sHoraFinMin;
    private String sHoraFinMax;

    private char[] arrDiasTrabajo;
    private char[] arrDiasDescanso;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public TurnoPersonal() {
        sClaveTurno = "";
        sDescripcion = "";
        sHoraIni = "";
        sHoraFin = "";
        sHorarioDef = "";
        sDiasTrabajoDef = "";
        dHoraIni = null;
        dHoraFin = null;
        arrDiasTrabajo = new char[]{'L', 'M', 'X', 'J', 'V', 'S', 'D'};
        arrDiasDescanso = new char[]{'X', 'X', 'X', 'X', 'X', 'X', 'X'};
    }

    private boolean buscarUsuario() {
        boolean bRet = false;
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if( httpServletRequest.getSession().getAttribute( "oFirm" ) != null ) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute( "oFirm" );
            sIdUsuario = oFirm.getUsu().getIdUsuario();
            bRet = (sIdUsuario != null && !sIdUsuario.isEmpty());
        }
        return bRet;
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( getClaveTurno().compareTo( "" ) == 0 ) {
            throw new Exception( "TurnoPersonal.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveTurnoPersonal( '" + getClaveTurno() + "' );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                arrDiasTrabajo = null;
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setClaveTurno( (String) vRowTemp.get( 0 ) );
                setDescripcion( (String) vRowTemp.get( 1 ) );
                setHoraIni( (String) vRowTemp.get( 2 ) );
                setHoraFin( (String) vRowTemp.get( 3 ) );
                setHorarioDef( (String) vRowTemp.get( 4 ) );
                setDiasTrabajoDef( (String) vRowTemp.get( 5 ) );
                crearArrDiasTrabajo();
                crearHorarioDef();
                bRet = true;
            }
        }
        return bRet;
    }

    public TurnoPersonal[] buscarTodos() throws Exception {
        TurnoPersonal arrRet[] = null, oTurnoPersonal = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosTurnoPersonal();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new TurnoPersonal[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oTurnoPersonal = new TurnoPersonal();
                oTurnoPersonal.setClaveTurno( (String) vRowTemp.get( 0 ) );
                oTurnoPersonal.setDescripcion( (String) vRowTemp.get( 1 ) );
                oTurnoPersonal.setHoraIni( (String) vRowTemp.get( 2 ) );
                oTurnoPersonal.setHoraFin( (String) vRowTemp.get( 3 ) );
                oTurnoPersonal.setHorarioDef( (String) vRowTemp.get( 4 ) );
                oTurnoPersonal.setDiasTrabajoDef( (String) vRowTemp.get( 5 ) );
                arrRet[i] = oTurnoPersonal;
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( !buscarUsuario() || getClaveTurno().compareTo( "" ) == 0 ) {
            throw new Exception( "TurnoPersonal.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaTurnoPersonal( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getClaveTurno() + "', "
                    + "'" + getDescripcion() + "', "
                    + "'" + getHoraIni() + "', "
                    + "'" + getHoraFin() + "', "
                    + "'" + getHorarioDef() + "', "
                    + "'" + getDiasTrabajoDef() + "' );";
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
        if( !buscarUsuario() || getClaveTurno().compareTo( "" ) == 0 ) {
            throw new Exception( "TurnoPersonal.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaTurnoPersonal( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getClaveTurno() + "', "
                    + "'" + getDescripcion() + "', "
                    + "'" + getHoraIni() + "', "
                    + "'" + getHoraFin() + "', "
                    + "'" + getHorarioDef() + "', "
                    + "'" + getDiasTrabajoDef() + "' );";
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
        if( !buscarUsuario() || getClaveTurno().compareTo( "" ) == 0 ) {
            throw new Exception( "TurnoPersonal.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaTurnoPersonal( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getClaveTurno() + "' );";
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

    public void crearHorarioDef() {
        SimpleDateFormat format = new SimpleDateFormat( "HH:mm" );
        if( getHorarioDef() != null && !getHorarioDef().isEmpty() && getHorarioDef().compareTo( "SIN HORARIO" ) != 0 ) {
            String[] arrHoras = getHorarioDef().split( "-" );
            try {
                setHoraIniDate( format.parse( arrHoras[0] ) );
                setHoraFinDate( format.parse( arrHoras[1] ) );
            } catch( Exception ex ) {
                ex.printStackTrace();
            }
        } else {
            try {
                setHoraIniDate( format.parse( "00:00" ) );
                setHoraFinDate( format.parse( "00:00" ) );
            } catch( Exception ex ) {
                ex.printStackTrace();
            }
        }
    }

    public void crearArrDiasTrabajo() {
        if( getDiasTrabajoDef() != null && !getDiasTrabajoDef().isEmpty() ) {
            setArrDiasTrabajo( getDiasTrabajoDef().toCharArray() );
        }
    }

    public void crearArrDiasDescanso() {
        if( getDiasTrabajoDef() != null && !getDiasTrabajoDef().isEmpty() ) {
            setArrDiasTrabajo( getDiasTrabajoDef().toCharArray() );
            for( int i = 0; i < getArrDiasTrabajo().length; i++ ) {
                int nPos = -1;
                switch( getArrDiasTrabajo()[i] ) {
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

    public String getHorarioComp() {
        String sRet = "";
        SimpleDateFormat format = new SimpleDateFormat( "HH:mm" );
        if( getHoraIniDate() != null && getHoraFinDate() != null ) {
            if( getHoraIniDate().equals( getHoraFinDate() ) ) {
                sRet = "SIN HORARIO";
            } else {
                sRet = format.format( getHoraIniDate() ) + "-" + format.format( getHoraFinDate() );
            }
        }
        return sRet;
    }

    public String getDiasTrabajoComp() {
        String sRet = "";
        if( getArrDiasTrabajo() != null && getArrDiasTrabajo().length > 0 ) {
            for( int i = 0; i < getArrDiasTrabajo().length; i++ ) {
                if( getArrDiasTrabajo()[i] != ' ' ) {
                    sRet += "" + getArrDiasTrabajo()[i];
                }
            }
        }
        return sRet;
    }

    public String getClaveTurno() {
        return sClaveTurno;
    }

    public void setClaveTurno( String sClaveTurno ) {
        this.sClaveTurno = sClaveTurno;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion( String sDescripcion ) {
        this.sDescripcion = sDescripcion;
    }

    public String getHoraIni() {
        return sHoraIni;
    }

    public void setHoraIni( String sHoraIni ) {
        this.sHoraIni = sHoraIni;
    }

    public String getHoraFin() {
        return sHoraFin;
    }

    public void setHoraFin( String sHoraFin ) {
        this.sHoraFin = sHoraFin;
    }

    public String getHorarioDef() {
        return sHorarioDef;
    }

    public void setHorarioDef( String sHorarioDef ) {
        this.sHorarioDef = sHorarioDef;
    }

    public String getDiasTrabajoDef() {
        return sDiasTrabajoDef;
    }

    public void setDiasTrabajoDef( String sDiasTrabajoDef ) {
        this.sDiasTrabajoDef = sDiasTrabajoDef;
    }

    public Date getHoraIniDate() {
        return dHoraIni;
    }

    public void setHoraIniDate( Date dHoraIni ) {
        this.dHoraIni = dHoraIni;
    }

    public Date getHoraFinDate() {
        return dHoraFin;
    }

    public void setHoraFinDate( Date dHoraFin ) {
        this.dHoraFin = dHoraFin;
    }

    public char[] getArrDiasTrabajo() {
        return arrDiasTrabajo;
    }

    public void setArrDiasTrabajo( char[] arrDiasTrabajo ) {
        this.arrDiasTrabajo = arrDiasTrabajo;
    }

    public char[] getArrDiasDescanso() {
        return arrDiasDescanso;
    }

    public void setArrDiasDescanso( char[] arrDiasDescanso ) {
        this.arrDiasDescanso = arrDiasDescanso;
    }
}
