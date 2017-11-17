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
public class TipoVialidad implements Serializable {

    private String sCveTipoVial;
    private String sDescripcion;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public TipoVialidad() {
        sCveTipoVial = "";
        sDescripcion = "";
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if( httpServletRequest.getSession().getAttribute( "oFirm" ) != null ) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute( "oFirm" );
            sIdUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( getCveTipoVial() == null || getCveTipoVial().trim().isEmpty() ) {
            throw new Exception( "TipoVialidad.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveTipoVialidad( '" + getCveTipoVial() + "' );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setCveTipoVial( (String) vRowTemp.get( 0 ) );
                setDescripcion( (String) vRowTemp.get( 1 ) );
                bRet = true;
            }
        }
        return bRet;
    }

    public TipoVialidad[] buscarTodos() throws Exception {
        TipoVialidad arrRet[] = null, oTipoVialidad = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosTipoVialidad();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new TipoVialidad[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oTipoVialidad = new TipoVialidad();
                oTipoVialidad.setCveTipoVial( (String) vRowTemp.get( 0 ) );
                oTipoVialidad.setDescripcion( (String) vRowTemp.get( 1 ) );
                arrRet[i] = oTipoVialidad;
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( this == null ) //completar llave
        {
            throw new Exception( "TipoVialidad.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaTipoVialidad( "
                    + "'" + sIdUsuario + "', ";
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
        if( this == null ) //completar llave
        {
            throw new Exception( "TipoVialidad.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaTipoVialidad( "
                    + "'" + sIdUsuario + "', ";
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
        if( this == null ) //completar llave
        {
            throw new Exception( "TipoVialidad.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaTipoVialidad( "
                    + "'" + sIdUsuario + "', ";
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

    public String getCveTipoVial() {
        return sCveTipoVial;
    }

    public void setCveTipoVial( String sCveTipoVial ) {
        this.sCveTipoVial = sCveTipoVial;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion( String sDescripcion ) {
        this.sDescripcion = sDescripcion;
    }
}
