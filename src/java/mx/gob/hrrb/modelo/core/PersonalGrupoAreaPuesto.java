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
public class PersonalGrupoAreaPuesto implements Serializable {

    private PersonalGrupo oPersonalGrupo;
    private AreaServicioHRRB oAreaServicio;
    private Puesto oPuesto;
    private int nNoPuestos;
    private String sSubAreas;
    private int nJerarquiaSinergia;

    private PersonalGrupo oPersonalGrupoOld;
    private AreaServicioHRRB oAreaServicioOld;
    private Puesto oPuestoOld;

    private String sDescripcion;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public PersonalGrupoAreaPuesto() {
        oPersonalGrupo = new PersonalGrupo();
        oAreaServicio = new AreaServicioHRRB();
        oPuesto = new Puesto();
        oPuesto.setClave( "" );
        nNoPuestos = -1;
        sSubAreas = "";
        oPersonalGrupoOld = new PersonalGrupo();
        oAreaServicioOld = new AreaServicioHRRB();
        oPuestoOld = new Puesto();
        oPuestoOld.setClave( "" );
        sDescripcion = "";
        nJerarquiaSinergia = 99;

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
        if( getPersonalGrupo().getClaveGrupo().isEmpty() || getAreaServicio().getClave() == 0 || getPuesto().getClave().isEmpty() ) {
            throw new Exception( "PersonalGrupoAreaPuesto.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalGrupoAreaPuesto( "
                    + "'" + getPersonalGrupo().getClaveGrupo() + "', "
                    + getAreaServicio().getClave() + "::SMALLINT, "
                    + "'" + getPuesto().getClave() + "' );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                getPersonalGrupo().setClaveGrupo( (String) vRowTemp.get( 0 ) );
                getAreaServicio().setClave( ((Double) vRowTemp.get( 1 )).intValue() );
                getPuesto().setClave( (String) vRowTemp.get( 2 ) );
                setNoPuestos( ((Double) vRowTemp.get( 3 )).intValue() );
                setSubAreas( (String) vRowTemp.get( 4 ) );
                setJerarquiaSinergia( ((Double) vRowTemp.get( 5 )).intValue() );

                getPersonalGrupoOld().setClaveGrupo( getPersonalGrupo().getClaveGrupo() );
                getAreaServicioOld().setClave( getAreaServicio().getClave() );
                getPuestoOld().setClave( getPuesto().getClave() );

                bRet = true;
            }
        }
        return bRet;
    }

    public PersonalGrupoAreaPuesto[] buscarTodos() throws Exception {
        PersonalGrupoAreaPuesto[] arrRet = null;
        PersonalGrupoAreaPuesto oPGAP = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosPersonalGrupoAreaPuesto();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalGrupoAreaPuesto[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPGAP = new PersonalGrupoAreaPuesto();
                oPGAP.getPersonalGrupo().setClaveGrupo( (String) vRowTemp.get( 0 ) );
                oPGAP.getAreaServicio().setClave( ((Double) vRowTemp.get( 1 )).intValue() );
                oPGAP.getPuesto().setClave( (String) vRowTemp.get( 2 ) );
                oPGAP.setNoPuestos( ((Double) vRowTemp.get( 3 )).intValue() );
                oPGAP.setSubAreas( (String) vRowTemp.get( 4 ) );
                oPGAP.setJerarquiaSinergia( ((Double) vRowTemp.get( 5 )).intValue() );

                oPGAP.getPersonalGrupoOld().setClaveGrupo( oPGAP.getPersonalGrupo().getClaveGrupo() );
                oPGAP.getAreaServicioOld().setClave( oPGAP.getAreaServicio().getClave() );
                oPGAP.getPuestoOld().setClave( oPGAP.getPuesto().getClave() );

                arrRet[i] = oPGAP;
            }
        }
        return arrRet;
    }

    public PersonalGrupoAreaPuesto[] buscarTodosFiltro() throws Exception {
        PersonalGrupoAreaPuesto[] arrRet = null;
        PersonalGrupoAreaPuesto oPGAP = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaPersonalGrupoAreaPuestoFiltro("
                + "'" + getPersonalGrupo().getClaveGrupo() + "', "
                + getAreaServicio().getClave() + "::SMALLINT, "
                + "'" + getPuesto().getClave() + "', "
                + getNoPuestos() + "::INTEGER, "
                + "'" + getSubAreas() + "' );";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalGrupoAreaPuesto[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPGAP = new PersonalGrupoAreaPuesto();
                oPGAP.getPersonalGrupo().setClaveGrupo( (String) vRowTemp.get( 0 ) );
                oPGAP.getAreaServicio().setClave( ((Double) vRowTemp.get( 1 )).intValue() );
                oPGAP.getPuesto().setClave( (String) vRowTemp.get( 2 ) );
                oPGAP.setNoPuestos( ((Double) vRowTemp.get( 3 )).intValue() );
                oPGAP.setSubAreas( (String) vRowTemp.get( 4 ) );
                oPGAP.setJerarquiaSinergia( ((Double) vRowTemp.get( 5 )).intValue() );

                oPGAP.getPersonalGrupoOld().setClaveGrupo( oPGAP.getPersonalGrupo().getClaveGrupo() );
                oPGAP.getAreaServicioOld().setClave( oPGAP.getAreaServicio().getClave() );
                oPGAP.getPuestoOld().setClave( oPGAP.getPuesto().getClave() );

                arrRet[i] = oPGAP;
            }
        }
        return arrRet;
    }

    public PersonalGrupo[] buscarTodosPersonalGrupoDisponible() throws Exception {
        PersonalGrupo arrRet[] = null, oPersonalGrupo = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosPersonalGrupoDisponible( "
                + "'" + getPersonalGrupo().getClaveGrupo() + "' );";
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

    public Puesto[] buscarTodosPuestoDisponible() throws Exception {
        Puesto arrRet[] = null, oPuesto = null;
        ArrayList rst = null;
        String sQuery = "";
        if( getPuesto().getClave() == null ) {
            getPuesto().setClave( "" );
        }
        if( getPersonalGrupo().getClaveGrupo().isEmpty() ) {
            throw new Exception( "PersonalGrupoAreaPuesto.buscarTodosPuestoDisponible: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaTodosPuestoDisponible("
                    + "'" + getPersonalGrupo().getClaveGrupo() + "'::CHARACTER(10),"
                    + "'" + getPuesto().getClave() + "'::CHARACTER(10) );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
                arrRet = new Puesto[rst.size()];
                ArrayList vRowTemp = null;

                for( int i = 0; i < rst.size(); i++ ) {
                    vRowTemp = (ArrayList) rst.get( i );
                    oPuesto = new Puesto();
                    oPuesto.setClave( (String) vRowTemp.get( 0 ) );
                    oPuesto.setDescripcion( (String) vRowTemp.get( 1 ) );
                    oPuesto.setSalario( ((Double) vRowTemp.get( 2 )).floatValue() );
                    oPuesto.setPuedeAutorizar( ((String) vRowTemp.get( 3 )).compareTo( "1" ) == 0 );
                    arrRet[i] = oPuesto;
                }
            }
        }
        return arrRet;
    }

    public AreaServicioHRRB[] buscarTodosAreaServicioDisponible() throws Exception {
        AreaServicioHRRB arrRet[] = null, oAreaServ = null;
        ArrayList rst = null;
        String sQuery = "";
        if( getPuesto().getClave() == null ) {
            getPuesto().setClave( "" );
        }
        if( getPersonalGrupo().getClaveGrupo().isEmpty() || getPuesto().getClave().isEmpty() ) {
            throw new Exception( "PersonalGrupoAreaPuesto.buscarTodosAreaServicioDisponible: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaTodosAreaServicioDisponible("
                    + "'" + getPersonalGrupo().getClaveGrupo() + "', "
                    + "'" + getPuesto().getClave() + "', "
                    + getAreaServicio().getClave() + "::SMALLINT );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() > 0 ) {
                arrRet = new AreaServicioHRRB[rst.size()];
                ArrayList vRowTemp = null;

                for( int i = 0; i < rst.size(); i++ ) {
                    vRowTemp = (ArrayList) rst.get( i );
                    oAreaServ = new AreaServicioHRRB();
                    oAreaServ.setClave( ((Double) vRowTemp.get( 0 )).intValue() );
                    oAreaServ.setDescripcion( (String) vRowTemp.get( 1 ) );
                    oAreaServ.setTipo( (String) vRowTemp.get( 2 ) );
                    arrRet[i] = oAreaServ;
                }
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( getPersonalGrupo().getClaveGrupo().isEmpty() || getAreaServicio().getClave() == 0 || getPuesto().getClave().isEmpty() ) {
            throw new Exception( "PersonalGrupoAreaPuesto.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaPersonalGrupoAreaPuesto( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getPersonalGrupo().getClaveGrupo() + "', "
                    + getAreaServicio().getClave() + "::SMALLINT, "
                    + "'" + getPuesto().getClave() + "', "
                    + getNoPuestos() + "::INTEGER, "
                    + "'" + getSubAreas() + "', "
                    + getJerarquiaSinergia() + "::SMALLINT );";
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
        if( getPersonalGrupo().getClaveGrupo().isEmpty() || getAreaServicio().getClave() == 0 || getPuesto().getClave().isEmpty()
                || getPersonalGrupoOld().getClaveGrupo().isEmpty() || getAreaServicioOld().getClave() == 0 || getPuestoOld().getClave().isEmpty() ) {
            throw new Exception( "PersonalGrupoAreaPuesto.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalGrupoAreaPuesto( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getPersonalGrupoOld().getClaveGrupo() + "', "
                    + getAreaServicioOld().getClave() + "::SMALLINT, "
                    + "'" + getPuestoOld().getClave() + "', "
                    + "'" + getPersonalGrupo().getClaveGrupo() + "', "
                    + getAreaServicio().getClave() + "::SMALLINT, "
                    + "'" + getPuesto().getClave() + "', "
                    + getNoPuestos() + "::INTEGER, "
                    + "'" + getSubAreas() + "', "
                    + getJerarquiaSinergia() + "::SMALLINT );";
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
        if( getPersonalGrupo().getClaveGrupo().isEmpty() || getAreaServicio().getClave() == 0 || getPuesto().getClave().isEmpty() ) {
            throw new Exception( "PersonalGrupoAreaPuesto.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaPersonalGrupoAreaPuesto( "
                    + "'" + sIdUsuario + "', "
                    + "'" + getPersonalGrupo().getClaveGrupo() + "', "
                    + getAreaServicio().getClave() + "::SMALLINT, "
                    + "'" + getPuesto().getClave() + "' );";
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

    public PersonalGrupo getPersonalGrupo() {
        return oPersonalGrupo;
    }

    public void setPersonalGrupo( PersonalGrupo oPersonalGrupo ) {
        this.oPersonalGrupo = oPersonalGrupo;
    }

    public AreaServicioHRRB getAreaServicio() {
        return oAreaServicio;
    }

    public void setAreaServicio( AreaServicioHRRB oAreaServicio ) {
        this.oAreaServicio = oAreaServicio;
    }

    public Puesto getPuesto() {
        return oPuesto;
    }

    public void setPuesto( Puesto oPuesto ) {
        this.oPuesto = oPuesto;
    }

    public int getNoPuestos() {
        return nNoPuestos;
    }

    public void setNoPuestos( int nNoPuestos ) {
        this.nNoPuestos = nNoPuestos;
    }

    public String getSubAreas() {
        return sSubAreas;
    }

    public void setSubAreas( String sSubAreas ) {
        this.sSubAreas = sSubAreas;
    }

    public int getJerarquiaSinergia() {
        return nJerarquiaSinergia;
    }

    public void setJerarquiaSinergia( int nJerarquiaSinergia ) {
        this.nJerarquiaSinergia = nJerarquiaSinergia;
    }

    public PersonalGrupo getPersonalGrupoOld() {
        return oPersonalGrupoOld;
    }

    public void setPersonalGrupoOld( PersonalGrupo oPersonalGrupoOld ) {
        this.oPersonalGrupoOld = oPersonalGrupoOld;
    }

    public AreaServicioHRRB getAreaServicioOld() {
        return oAreaServicioOld;
    }

    public void setAreaServicioOld( AreaServicioHRRB oAreaServicioOld ) {
        this.oAreaServicioOld = oAreaServicioOld;
    }

    public Puesto getPuestoOld() {
        return oPuestoOld;
    }

    public void setPuestoOld( Puesto oPuestoOld ) {
        this.oPuestoOld = oPuestoOld;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion( String sDescripcion ) {
        this.sDescripcion = sDescripcion;
    }
}
