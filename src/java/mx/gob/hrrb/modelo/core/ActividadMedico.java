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
public class ActividadMedico implements Serializable {

    private int nClaveActividad;
    private String sDescripcion = "";
    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public ActividadMedico() {
        nClaveActividad = 34;
        sDescripcion = "";
    }

    public boolean buscarUsuario() {
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
        if( this == null ) //completar llave
        {
            throw new Exception( "ActividadMedico.buscar: error de programaci�n, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveActividadMedico( ";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                //fields = DATA
                bRet = true;
            }
        }
        return bRet;
    }

    public ActividadMedico[] buscarTodos() throws Exception {
        ActividadMedico arrRet[] = null, oActividadMedico = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosActividadMedico();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new ActividadMedico[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oActividadMedico = new ActividadMedico();
                //fields = DATA
                arrRet[i] = oActividadMedico;
            }
        }
        return arrRet;
    }

    public ActividadMedico[] buscarActividad( String sTxt ) throws Exception {
        ActividadMedico arrRet[] = null, oActividadMedico = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaActividad( '" + sTxt + "' );";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new ActividadMedico[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oActividadMedico = new ActividadMedico();
                oActividadMedico.setClaveActividad( ((Double) vRowTemp.get( 0 )).intValue() );
                oActividadMedico.setDescripcion( (String) vRowTemp.get( 1 ) );
                arrRet[i] = oActividadMedico;
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( !buscarUsuario() ) //completar llave
        {
            throw new Exception( "ActividadMedico.insertar: error de programaci�n, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaActividadMedico( "
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
        if( !buscarUsuario() ) //completar llave
        {
            throw new Exception( "ActividadMedico.modificar: error de programaci�n, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaActividadMedico( "
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
        if( !buscarUsuario() ) //completar llave
        {
            throw new Exception( "ActividadMedico.eliminar: error de programaci�n, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaActividadMedico( "
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

    public int getClaveActividad() {
        return nClaveActividad;
    }

    public void setClaveActividad( int nClaveActividad ) {
        this.nClaveActividad = nClaveActividad;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion( String sDescripcion ) {
        this.sDescripcion = sDescripcion;
    }
}
