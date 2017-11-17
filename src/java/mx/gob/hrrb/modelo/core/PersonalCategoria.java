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
public class PersonalCategoria implements Serializable {

    private int nClaveCategoria;
    private String sDescripcion;
    private String sClaveXalapa;
    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public PersonalCategoria() {
        nClaveCategoria = 0;
        sDescripcion = "";
        sClaveXalapa = "";
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
        if( getClaveCategoria() < 1 ) {
            throw new Exception( "PersonalCategoria.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalCategoria( " + getClaveCategoria() + "::SMALLINT );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setClaveCategoria( ((Double) vRowTemp.get( 0 )).intValue() );
                setDescripcion( (String) vRowTemp.get( 1 ) );
                setClaveXalapa( (String) vRowTemp.get( 2 ) );
                bRet = true;
            }
        }
        return bRet;
    }

    public PersonalCategoria[] buscarTodos() throws Exception {
        PersonalCategoria[] arrRet = null;
        PersonalCategoria oPersonalCategoria = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosPersonalCategoria();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalCategoria[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPersonalCategoria = new PersonalCategoria();
                oPersonalCategoria.setClaveCategoria( ((Double) vRowTemp.get( 0 )).intValue() );
                oPersonalCategoria.setDescripcion( (String) vRowTemp.get( 1 ) );
                oPersonalCategoria.setClaveXalapa( (String) vRowTemp.get( 2 ) );
                arrRet[i] = oPersonalCategoria;
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "SELECT * FROM insertaPersonalCategoria( "
                + "'" + sIdUsuario + "', "
                + "'" + getDescripcion() + "', "
                + "'" + getClaveXalapa() + "' );";
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

    public int modificar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( getClaveCategoria() < 1 ) {
            throw new Exception( "PersonalCategoria.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalCategoria( "
                    + "'" + sIdUsuario + "', "
                    + getClaveCategoria() + "::SMALLINT, "
                    + "'" + getDescripcion() + "', "
                    + "'" + getClaveXalapa() + "' );";
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
        if( getClaveCategoria() < 1 ) {
            throw new Exception( "PersonalCategoria.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaPersonalCategoria( "
                    + "'" + sIdUsuario + "', "
                    + getClaveCategoria() + "::SMALLINT )";
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

    public int getClaveCategoria() {
        return nClaveCategoria;
    }

    public void setClaveCategoria( int nClaveCategoria ) {
        this.nClaveCategoria = nClaveCategoria;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion( String sDescripcion ) {
        this.sDescripcion = sDescripcion;
    }

    public String getClaveXalapa() {
        return sClaveXalapa;
    }

    public void setClaveXalapa( String sClaveXalapa ) {
        this.sClaveXalapa = sClaveXalapa;
    }
}
