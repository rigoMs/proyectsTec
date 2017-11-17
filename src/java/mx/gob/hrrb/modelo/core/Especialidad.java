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
public class Especialidad implements Serializable {

    private int nClaveEsp;
    private String sDescripcion;
    private boolean bHabilitado;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public Especialidad() {
        nClaveEsp = 0;
        sDescripcion = "";
        bHabilitado = true;
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
        if( getClaveEsp() < 1 ) {
            throw new Exception( "Especialidad.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlaveEspecialidad( " + getClaveEsp() + "::SMALLINT );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setClaveEsp( ((Double) vRowTemp.get( 0 )).intValue() );
                setDescripcion( (String) vRowTemp.get( 1 ) );
                setHabilitado( ((String) vRowTemp.get( 2 )).compareTo( "1" ) == 0 );
                bRet = true;
            }
        }
        return bRet;
    }

    public Especialidad[] buscarTodos() throws Exception {
        Especialidad arrRet[] = null, oEspecialidad = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosEspecialidad();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new Especialidad[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oEspecialidad = new Especialidad();
                oEspecialidad.setClaveEsp( ((Double) vRowTemp.get( 0 )).intValue() );
                oEspecialidad.setDescripcion( (String) vRowTemp.get( 1 ) );
                oEspecialidad.setHabilitado( ((String) vRowTemp.get( 2 )).compareTo( "1" ) == 0 );
                arrRet[i] = oEspecialidad;
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( !buscarUsuario() || getDescripcion().isEmpty() ) {
            throw new Exception( "Especialidad.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaEspecialidad( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getDescripcion() + "'"
                    + "'" + (isHabilitado() ? "1" : "0") + " );";
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
        if( !buscarUsuario() || getClaveEsp() < 1 ) {
            throw new Exception( "Especialidad.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaEspecialidad( "
                    + "'" + sIdUsuario + "', "
                    + getClaveEsp() + "::SMALLINT, "
                    + "'" + getDescripcion() + "', "
                    + "'" + (isHabilitado() ? "1" : "0") + "' );";
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
        if( !buscarUsuario() || getClaveEsp() < 1 ) {
            throw new Exception( "Especialidad.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaEspecialidad( "
                    + "'" + sIdUsuario + "', "
                    + getClaveEsp() + "::SMALLINT );";
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

    public int getClaveEsp() {
        return nClaveEsp;
    }

    public void setClaveEsp( int nClaveEsp ) {
        this.nClaveEsp = nClaveEsp;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion( String sDescripcion ) {
        this.sDescripcion = sDescripcion;
    }

    public boolean isHabilitado() {
        return bHabilitado;
    }

    public void setHabilitado( boolean bHabilitado ) {
        this.bHabilitado = bHabilitado;
    }
}
