package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author JMHG
 */
public class DocumentoDigital implements Serializable {

    private int nNoTarjeta;
    private Parametrizacion oTipo;
    private int nConsecutivo;
    private String sNombre;

    private Parametrizacion oTipoOld;
    private int nConsecutivoOld;

    public static final int ID_CLASE = 6;
    public static final String CVE_SELECCION_PERS = "16";

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    private boolean bSubido;
    private String sCedEsp;

    public DocumentoDigital() {
        nNoTarjeta = 0;
        oTipo = new Parametrizacion();
        oTipo.setTipoParametro( "" );
        oTipo.setClaveParametro( "" );
        nConsecutivo = -1;
        sNombre = "";
        oTipoOld = new Parametrizacion();
        nConsecutivoOld = 0;
        bSubido = false;
        sCedEsp = "";
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

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( getNoTarjeta() < 1
                || (getTipo().getClaveParametro()).compareTo( "" ) == 0
                || (getTipo().getTipoParametro()).compareTo( "" ) == 0
                || getConsecutivo() < 0 ) {
            throw new Exception( "DocumentoDigital.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveDocumentoDigital( "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getTipo().getClaveParametro() + getTipo().getTipoParametro() + "', "
                    + getConsecutivo() + "::INTEGER );";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                getTipo().asignaValorParametrizado( (String) vRowTemp.get( 1 ) );
                getTipoOld().asignaValorParametrizado( (String) vRowTemp.get( 1 ) );
                setConsecutivo( ((Double) vRowTemp.get( 2 )).intValue() );
                setNombre( (String) vRowTemp.get( 3 ) );
                setConsecutivoOld( getConsecutivo() );
                setSubido( !getNombre().isEmpty() );
                if( getConsecutivo() > 0 ) {
                    String sValor = getTipo().getValor() + " - ";
                    if( getConsecutivo() < 10 ) {
                        sValor += "0";
                    }
                    sValor += getConsecutivo();
                    getTipo().setValor( sValor );
                    getTipoOld().setValor( sValor );
                }
                bRet = true;
            }
        }
        return bRet;
    }

    public int buscarConsecutivoMax( int nNoTarjeta ) throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "SELECT * FROM buscaDocumentoConsecutivoMax( " + nNoTarjeta + " );";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() == 1 ) {
            ArrayList vRowTemp = (ArrayList) rst.get( 0 );
            nRet = ((Double) vRowTemp.get( 0 )).intValue();
        }
        return nRet;
    }

    public DocumentoDigital[] buscarTodos() throws Exception {
        DocumentoDigital arrRet[] = null, oDocDigi = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosDocumentoDigital();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new DocumentoDigital[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oDocDigi = new DocumentoDigital();
                oDocDigi.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oDocDigi.getTipo().asignaValorParametrizado( (String) vRowTemp.get( 1 ) );
                oDocDigi.getTipoOld().asignaValorParametrizado( (String) vRowTemp.get( 1 ) );
                oDocDigi.setConsecutivo( ((Double) vRowTemp.get( 2 )).intValue() );
                oDocDigi.setNombre( (String) vRowTemp.get( 3 ) );
                oDocDigi.setConsecutivoOld( oDocDigi.getConsecutivo() );
                oDocDigi.setSubido( !oDocDigi.getNombre().isEmpty() );
                if( oDocDigi.getConsecutivo() > 0 ) {
                    String sValor = oDocDigi.getTipo().getValor() + " - ";
                    if( oDocDigi.getConsecutivo() < 10 ) {
                        sValor += "0";
                    }
                    sValor += oDocDigi.getConsecutivo();
                    oDocDigi.getTipo().setValor( sValor );
                    oDocDigi.getTipoOld().setValor( sValor );
                }
                arrRet[i] = oDocDigi;
            }
        }
        return arrRet;
    }

    public DocumentoDigital[] buscarTodosFiltro() throws Exception {
        DocumentoDigital arrRet[] = null, oDocDigi = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaDocumentoDigitalFiltro( "
                + getNoTarjeta() + "::INTEGER, "
                + "'" + getTipo().getClaveParametro() + getTipo().getTipoParametro() + "', "
                + getConsecutivo() + "::INTEGER );";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new DocumentoDigital[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oDocDigi = new DocumentoDigital();
                oDocDigi.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oDocDigi.getTipo().asignaValorParametrizado( (String) vRowTemp.get( 1 ) );
                oDocDigi.getTipoOld().asignaValorParametrizado( (String) vRowTemp.get( 1 ) );
                oDocDigi.setConsecutivo( ((Double) vRowTemp.get( 2 )).intValue() );
                oDocDigi.setNombre( (String) vRowTemp.get( 3 ) );
                oDocDigi.setCedEsp( (String) vRowTemp.get( 4 ) );
                oDocDigi.setConsecutivoOld( oDocDigi.getConsecutivo() );
                oDocDigi.setSubido( !oDocDigi.getNombre().isEmpty() );
                if( !oDocDigi.getCedEsp().isEmpty() ) {
                    String sValor = oDocDigi.getTipo().getValor() + " (" + oDocDigi.getCedEsp() + ")";
                    oDocDigi.getTipo().setValor( sValor );
                    oDocDigi.getTipoOld().setValor( sValor );
                }
                arrRet[i] = oDocDigi;
            }
        }
        return arrRet;
    }

    public DocumentoDigital[] buscarDocumentosDisponibles() throws Exception {
        DocumentoDigital arrRet[] = null, oDocDigi = null;
        ArrayList rst = null;
        String sQuery = "";
        if( getNoTarjeta() < 1 ) {
            throw new Exception( "DocumentoDigital.buscarDocumentosDisponibles: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaDocumentoDigitalDisponible( " + getNoTarjeta() + "::INTEGER ); ";
            System.out.println( sQuery );
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
                arrRet = new DocumentoDigital[rst.size()];
                ArrayList vRowTemp = null;
                for( int i = 0; i < rst.size(); i++ ) {
                    vRowTemp = (ArrayList) rst.get( i );
                    oDocDigi = new DocumentoDigital();
                    oDocDigi.getTipo().asignaValorParametrizado( (String) vRowTemp.get( 0 ) );
                    oDocDigi.setConsecutivo( ((Double) vRowTemp.get( 1 )).intValue() );
                    arrRet[i] = oDocDigi;
                }
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
                || (getTipo().getClaveParametro()).compareTo( "" ) == 0
                || (getTipo().getTipoParametro()).compareTo( "" ) == 0
                || getConsecutivo() < 0 ) {
            throw new Exception( "DocumentoDigital.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaDocumentoDigital( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getTipo().getClaveParametro() + getTipo().getTipoParametro() + "', "
                    + getConsecutivo() + "::INTEGER, "
                    + ((getNombre().compareTo( "" ) == 0) ? "NULL" : "'" + getNombre() + "'") + " );";
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

    public int insertarDefault() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( !buscarUsuario() || getNoTarjeta() < 1 ) {
            throw new Exception( "DocumentoDigital.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaDocumentoDigitalDef( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER ); ";
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
        if( !buscarUsuario()
                || getNoTarjeta() < 1
                || (getTipoOld().getClaveParametro()).compareTo( "" ) == 0
                || (getTipoOld().getTipoParametro()).compareTo( "" ) == 0
                || getConsecutivoOld() < 0 ) {
            throw new Exception( "DocumentoDigital.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaDocumentoDigital( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getTipoOld().getClaveParametro() + getTipoOld().getTipoParametro() + "', "
                    + getConsecutivoOld() + "::INTEGER, "
                    + "'" + getTipo().getClaveParametro() + getTipo().getTipoParametro() + "', "
                    + getConsecutivo() + "::INTEGER, "
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
        if( !buscarUsuario()
                || getNoTarjeta() < 1
                || (getTipo().getClaveParametro()).compareTo( "" ) == 0
                || (getTipo().getTipoParametro()).compareTo( "" ) == 0
                || getConsecutivo() < 0 ) {
            throw new Exception( "DocumentoDigital.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaDocumentoDigital( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + "'" + getTipo().getClaveParametro() + getTipo().getTipoParametro() + "', "
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

    public String construyeQuery( String sIdUsuario, int nNoTarjeta, String sClaveTipo,
            int nConsecutivo, String sNombre ) {
        String sRet = "";
        sRet = "SELECT * FROM insertaDocumentoDigital( "
                + "'" + sIdUsuario + "', "
                + nNoTarjeta + "::INTEGER, "
                + "'" + Parametrizacion.TABLA_DOCUMENTO + sClaveTipo + "', "
                + nConsecutivo + "::INTEGER, "
                + (sNombre.isEmpty() ? "NULL" : "'" + sNombre + "'") + " );";
        return sRet;
    }

    public int getNoTarjeta() {
        return nNoTarjeta;
    }

    public void setNoTarjeta( int nNoTarjeta ) {
        this.nNoTarjeta = nNoTarjeta;
    }

    public Parametrizacion getTipo() {
        return oTipo;
    }

    public void setTipo( Parametrizacion oTipo ) {
        this.oTipo = oTipo;
    }

    public int getConsecutivo() {
        return nConsecutivo;
    }

    public void setConsecutivo( int nConsecutivo ) {
        this.nConsecutivo = nConsecutivo;
    }

    public String getNombre() {
        return sNombre;
    }

    public void setNombre( String sNombre ) {
        this.sNombre = sNombre;
    }

    public Parametrizacion getTipoOld() {
        return oTipoOld;
    }

    public void setTipoOld( Parametrizacion oTipoOld ) {
        this.oTipoOld = oTipoOld;
    }

    public int getConsecutivoOld() {
        return nConsecutivoOld;
    }

    public void setConsecutivoOld( int nConsecutivoOld ) {
        this.nConsecutivoOld = nConsecutivoOld;
    }

    public boolean isSubido() {
        return bSubido;
    }

    public void setSubido( boolean bSubido ) {
        this.bSubido = bSubido;
    }

    public String getCedEsp() {
        return sCedEsp;
    }

    public void setCedEsp( String sCedEsp ) {
        this.sCedEsp = sCedEsp;
    }
}
