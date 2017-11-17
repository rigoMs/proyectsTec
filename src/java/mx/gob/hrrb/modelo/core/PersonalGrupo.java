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
public class PersonalGrupo implements Serializable {

    private String sClaveGrupo;
    private String sPadre;
    private String sDescripcion;
    private int nOrdenSinergia;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public PersonalGrupo() {
        sClaveGrupo = "";
        sPadre = "";
        sDescripcion = "";
        nOrdenSinergia = 0;
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
        if( getClaveGrupo().compareTo( "" ) == 0 ) {
            throw new Exception( "PersonalGrupo.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalGrupo( '" + getClaveGrupo() + "' );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setClaveGrupo( (String) vRowTemp.get( 0 ) );
                setPadre( (String) vRowTemp.get( 1 ) );
                setDescripcion( (String) vRowTemp.get( 2 ) );
                bRet = true;
            }
        }
        return bRet;
    }

    public PersonalGrupo[] buscarTodos() throws Exception {
        PersonalGrupo arrRet[] = null, oPersonalGrupo = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosPersonalGrupo();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalGrupo[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPersonalGrupo = new PersonalGrupo();
                oPersonalGrupo.setClaveGrupo( (String) vRowTemp.get( 0 ) );
                oPersonalGrupo.setPadre( (String) vRowTemp.get( 1 ) );
                oPersonalGrupo.setDescripcion( (String) vRowTemp.get( 2 ) );
                arrRet[i] = oPersonalGrupo;
            }
        }
        return arrRet;
    }

    public PersonalGrupo[] buscarPersonalGrupoFiltro() throws Exception {
        PersonalGrupo arrRet[] = null, oPersonalGrupo = null;
        ArrayList rst = null;
        String sQuery = "SELECT * FROM buscaPersonalGrupoFiltro( "
                + "'" + getClaveGrupo() + "', "
                + "'" + getPadre() + "', "
                + "'" + getDescripcion() + "' );";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalGrupo[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPersonalGrupo = new PersonalGrupo();
                oPersonalGrupo.setClaveGrupo( (String) vRowTemp.get( 0 ) );
                oPersonalGrupo.setPadre( (String) vRowTemp.get( 1 ) );
                oPersonalGrupo.setDescripcion( (String) vRowTemp.get( 2 ) );
                arrRet[i] = oPersonalGrupo;
            }
        }

        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( getClaveGrupo().compareTo( "" ) == 0 ) {
            throw new Exception( "PersonalGrupo.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaPersonalGrupo( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getClaveGrupo() + "', "
                    + "'" + getPadre() + "', "
                    + "'" + getDescripcion() + "' );";
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
        if( getClaveGrupo().compareTo( "" ) == 0 ) {
            throw new Exception( "PersonalGrupo.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalGrupo( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getClaveGrupo() + "', "
                    + "'" + getPadre() + "', "
                    + "'" + getDescripcion() + "' );";
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
        if( getClaveGrupo().compareTo( "" ) == 0 ) {
            throw new Exception( "PersonalGrupo.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaPersonalGrupo( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getClaveGrupo() + "' );";
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

    public String getClaveGrupo() {
        return sClaveGrupo;
    }

    public void setClaveGrupo( String sClaveGrupo ) {
        this.sClaveGrupo = sClaveGrupo;
    }

    public String getPadre() {
        return sPadre;
    }

    public void setPadre( String sPadre ) {
        this.sPadre = sPadre;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion( String sDescripcion ) {
        this.sDescripcion = sDescripcion;
    }

    public int getOrdenSinergia() {
        return nOrdenSinergia;
    }

    public void setOrdenSinergia( int nOrdenSinergia ) {
        this.nOrdenSinergia = nOrdenSinergia;
    }
}
