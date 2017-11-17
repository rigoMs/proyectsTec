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
public class TipoAsentamiento implements Serializable {

    private int nCveAsenta;
    private String sDescripcion;
    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public TipoAsentamiento() {
        nCveAsenta = 0;
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
        if( getCveAsenta() < 1 ) {
            throw new Exception( "TipoAsentamiento.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveTipoAsentamiento( " + getCveAsenta() + "::SMALLINT )";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setCveAsenta( ((Double) vRowTemp.get( 0 )).intValue() );
                setDescripcion( (String) vRowTemp.get( 1 ) );
                bRet = true;
            }
        }
        return bRet;
    }

    public TipoAsentamiento[] buscarTodos() throws Exception {
        TipoAsentamiento arrRet[] = null, oTipoAsentamiento = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosTipoAsentamiento();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new TipoAsentamiento[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oTipoAsentamiento = new TipoAsentamiento();
                oTipoAsentamiento.setCveAsenta( ((Double) vRowTemp.get( 0 )).intValue() );
                oTipoAsentamiento.setDescripcion( (String) vRowTemp.get( 1 ) );
                arrRet[i] = oTipoAsentamiento;
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
            throw new Exception( "TipoAsentamiento.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaTipoAsentamiento( "
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
            throw new Exception( "TipoAsentamiento.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaTipoAsentamiento( "
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
            throw new Exception( "TipoAsentamiento.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaTipoAsentamiento( "
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

    public int getCveAsenta() {
        return nCveAsenta;
    }

    public void setCveAsenta( int nCveAsenta ) {
        this.nCveAsenta = nCveAsenta;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion( String sDescripcion ) {
        this.sDescripcion = sDescripcion;
    }
}
