package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Juan
 */
public class CedulaEspecialidad implements Serializable {

    private int nNoTarjeta;
    private String sCedEsp;
    private Especialidad oEspecialidad;
    private int nConsecutivo;

    private String sCedEspOld;
    private String sTipoComando;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public CedulaEspecialidad() {
        nNoTarjeta = 0;
        sCedEsp = "";
        oEspecialidad = new Especialidad();
        sCedEspOld = "";
        sTipoComando = "";
        nConsecutivo = 0;
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
        if( getNoTarjeta() < 1 && (getCedEsp().compareTo( "" ) == 0) ) {
            throw new Exception( "CedulaEspecialidad.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveCedulaEspecialidad( " + getNoTarjeta() + "::INTEGER, '" + getCedEsp() + "' );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                setCedEsp( (String) vRowTemp.get( 1 ) );
                getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 2 )).intValue() );
                setConsecutivo( ((Double) vRowTemp.get( 3 )).intValue() );
                setCedEspOld( getCedEsp() );
                bRet = true;
            }
        }
        return bRet;
    }

    public CedulaEspecialidad[] buscarTodos() throws Exception {
        CedulaEspecialidad arrRet[] = null, oCedEsp = null;
        ArrayList rst = null;
        String sQuery = "SELECT * FROM buscaTodosCedulaEspecialidad();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            ArrayList vRowTemp = null;
            arrRet = new CedulaEspecialidad[rst.size()];
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oCedEsp = new CedulaEspecialidad();
                oCedEsp.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oCedEsp.setCedEsp( (String) vRowTemp.get( 1 ) );
                oCedEsp.getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 2 )).intValue() );
                oCedEsp.setConsecutivo( ((Double) vRowTemp.get( 3 )).intValue() );
                oCedEsp.setCedEspOld( oCedEsp.getCedEsp() );
                arrRet[i] = oCedEsp;
            }
        }
        return arrRet;
    }

    public CedulaEspecialidad[] buscarTodosFiltro() throws Exception {
        CedulaEspecialidad arrRet[] = null, oCedEsp = null;
        ArrayList rst = null;
        String sQuery = "SELECT * FROM buscaCedulaEspecialidadFiltro( "
                + getNoTarjeta() + "::INTEGER, "
                + "'" + getCedEsp() + "', "
                + getEspecialidad().getClaveEsp() + "::SMALLINT );";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            ArrayList vRowTemp = null;
            arrRet = new CedulaEspecialidad[rst.size()];
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oCedEsp = new CedulaEspecialidad();
                oCedEsp.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oCedEsp.setCedEsp( (String) vRowTemp.get( 1 ) );
                oCedEsp.getEspecialidad().setClaveEsp( ((Double) vRowTemp.get( 2 )).intValue() );
                oCedEsp.setConsecutivo( ((Double) vRowTemp.get( 3 )).intValue() );
                oCedEsp.setCedEspOld( oCedEsp.getCedEsp() );
                arrRet[i] = oCedEsp;
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( !buscarUsuario()
                || getNoTarjeta() < 1
                || getCedEsp().compareTo( "" ) == 0
                || getEspecialidad().getClaveEsp() < 1 ) {
            throw new Exception( "CedulaEspecialidad.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaCedulaEspecialidad( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getCedEsp() + "', "
                    + getEspecialidad().getClaveEsp() + "::SMALLINT, "
                    + getConsecutivo() + "::INTEGER );";
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

    public int insertarMultiples( ArrayList<CedulaEspecialidad> arrEsp ) throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( !buscarUsuario()
                || getNoTarjeta() < 1
                || (getCedEsp().compareTo( "" ) == 0)
                || getEspecialidad().getClaveEsp() < 1
                || arrEsp == null
                || arrEsp.size() == 0 ) {
            throw new Exception( "CedulaEspecialidad.insertarMultiples: error de programación, faltan datos" );
        } else {
            int[] arrNoTarjeta;

            sQuery = "SELECT * FROM insertaCedulaEspecialidadMulti( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getCedEsp() + "', "
                    + getEspecialidad().getClaveEsp() + "::SMALLINT, "
                    + getConsecutivo() + "::INTEGER );";
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
        if( !buscarUsuario() || getNoTarjeta() < 1 || getCedEspOld().compareTo( "" ) == 0 ) {
            throw new Exception( "CedulaEspecialidad.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaCedulaEspecialidad( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getCedEspOld() + "', "
                    + "'" + getCedEsp() + "', "
                    + getEspecialidad().getClaveEsp() + "::SMALLINT, "
                    + getConsecutivo() + "::INTEGER );";
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
        if( !buscarUsuario() || getNoTarjeta() < 1 || getCedEsp().compareTo( "" ) == 0 ) {
            throw new Exception( "CedulaEspecialidad.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaCedulaEspecialidad( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getCedEsp() + "' );";
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

    public String crearQueryArreglos( List<CedulaEspecialidad> lCedEspOld, List<CedulaEspecialidad> lCedEsp, boolean bModificar ) {
        String sRet = "NULL, NULL";
        int nConsecutivoMax = 0;
        if( bModificar ) {
            sRet = "NULL, NULL, NULL, NULL, NULL";
        }
        if( (lCedEsp != null && !lCedEsp.isEmpty())
                || (lCedEspOld != null && !lCedEspOld.isEmpty()) ) {
            List<CedulaEspecialidad> lTemp = new ArrayList<>();
            List<CedulaEspecialidad> lTempUpdate = new ArrayList<>();
            List<CedulaEspecialidad> lTempInsert = new ArrayList<>();

            if( lCedEspOld != null && !lCedEspOld.isEmpty() ) {
                for( CedulaEspecialidad oCedEsp : lCedEspOld ) {
                    if( oCedEsp.getTipoComando().compareTo( "DELETE" ) == 0 ) {
                        lTemp.add( oCedEsp );
                    }
                }
            }
            if( lCedEsp != null && !lCedEsp.isEmpty() ) {
                for( CedulaEspecialidad oCedEsp : lCedEsp ) {
                    if( oCedEsp.getTipoComando().compareTo( "INSERT" ) == 0 ) {
                        lTempInsert.add( oCedEsp );
                    } else {
                        oCedEsp.setTipoComando( "UPDATE" );
                        lTempUpdate.add( oCedEsp );
                    }
                }
            }
            lTemp.addAll( lTempUpdate );
            lTemp.addAll( lTempInsert );
            if( bModificar ) {
                try {
                    nConsecutivoMax = (new DocumentoDigital()).buscarConsecutivoMax( getNoTarjeta() );
                } catch( Exception ex ) {
                    ex.printStackTrace();
                    nConsecutivoMax = 0;
                }
            }
            StringBuilder sbArrsComandos = new StringBuilder( "ARRAY[ " );
            StringBuilder sbArrsCedEspOld = new StringBuilder( "ARRAY[ " );
            StringBuilder sbArrsCedEsp = new StringBuilder( "ARRAY[ " );
            StringBuilder sbArrnClaveEsp = new StringBuilder( "ARRAY[ " );
            StringBuilder sbArrnConsecutivo = new StringBuilder( "ARRAY[ " );
            int i = 1, nSize = lTemp.size();
            for( CedulaEspecialidad oCedEsp : lTemp ) {
                sbArrsComandos.append( "'" ).append( oCedEsp.getTipoComando() ).append( "'" );
                sbArrsCedEspOld.append( "'" ).append( oCedEsp.getCedEspOld() ).append( "'" );
                sbArrsCedEsp.append( "'" ).append( oCedEsp.getCedEsp() ).append( "'" );
                sbArrnClaveEsp.append( oCedEsp.getEspecialidad().getClaveEsp() ).append( "::SMALLINT" );
                if( oCedEsp.getTipoComando().compareTo( "INSERT" ) == 0 ) {
                    sbArrnConsecutivo.append( ++nConsecutivoMax ).append( "::INTEGER" );
                } else {
                    sbArrnConsecutivo.append( oCedEsp.getConsecutivo() ).append( "::INTEGER" );
                }
                if( i < nSize ) {
                    sbArrsComandos.append( ", " );
                    sbArrsCedEspOld.append( ", " );
                    sbArrsCedEsp.append( ", " );
                    sbArrnClaveEsp.append( ", " );
                    sbArrnConsecutivo.append( ", " );
                } else {
                    sbArrsComandos.append( " ]" );
                    sbArrsCedEspOld.append( " ]" );
                    sbArrsCedEsp.append( " ]" );
                    sbArrnClaveEsp.append( " ]" );
                    sbArrnConsecutivo.append( " ]" );
                }
                i++;
            }
            sRet = sbArrsCedEsp.toString() + ", " + sbArrnClaveEsp.toString();
            if( bModificar ) {
                sRet = sbArrsComandos.toString() + ", " + sbArrsCedEspOld.toString() + ", " + sRet + ", " + sbArrnConsecutivo.toString();
            }
        }
        return sRet;
    }

    public int getNoTarjeta() {
        return nNoTarjeta;
    }

    public void setNoTarjeta( int nNoTarjeta ) {
        this.nNoTarjeta = nNoTarjeta;
    }

    public String getCedEsp() {
        return sCedEsp;
    }

    public void setCedEsp( String sCedEsp ) {
        this.sCedEsp = sCedEsp;
    }

    public Especialidad getEspecialidad() {
        return oEspecialidad;
    }

    public void setEspecialidad( Especialidad oEspecialidad ) {
        this.oEspecialidad = oEspecialidad;
    }

    public String getCedEspOld() {
        return sCedEspOld;
    }

    public void setCedEspOld( String sCedEspOld ) {
        this.sCedEspOld = sCedEspOld;
    }

    public String getTipoComando() {
        return sTipoComando;
    }

    public void setTipoComando( String sTipoComando ) {
        this.sTipoComando = sTipoComando;
    }

    public int getConsecutivo() {
        return nConsecutivo;
    }

    public void setConsecutivo( int nConsecutivo ) {
        this.nConsecutivo = nConsecutivo;
    }
}
