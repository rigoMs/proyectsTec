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
public class PersonalAreaServ implements Serializable {

    private int nNoTarjeta;
    private AreaServicioHRRB oAreaServ;
    private Turno oTurno;
    private Puesto oPuesto;
    private TurnoPersonal oTurnoPersonal;
    private PersonalGrupo oPersonalGrupo;
    private PersonalCategoria oPersonalCategoria;

    private int nClaveAreaServicioOld;
    private String sClaveTurnoOld;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public PersonalAreaServ() {
        nNoTarjeta = 0;
        oAreaServ = new AreaServicioHRRB();
        oTurno = new Turno();
        oPuesto = new Puesto();
        oTurnoPersonal = new TurnoPersonal();
        oPersonalGrupo = new PersonalGrupo();
        oPersonalCategoria = new PersonalCategoria();
        nClaveAreaServicioOld = 0;
        sClaveTurnoOld = "";
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
        if( getNoTarjeta() < 1 || getAreaServ().getClave() < 1 || getTurno().getClave().isEmpty() ) {
            throw new Exception( "PersonalAreaServ.buscar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM buscaLlavePersonalAreaServ( "
                    + getNoTarjeta() + "::INTEGER, "
                    + getAreaServ().getClave() + "::SMALLINT, "
                    + "'" + getTurno().getClave() + "' );";
            oAD = new AccesoDatos();
            if( oAD.conectar() ) {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
            }
            if( rst != null && rst.size() == 1 ) {
                ArrayList vRowTemp = (ArrayList) rst.get( 0 );
                setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                getAreaServ().setClave( ((Double) vRowTemp.get( 1 )).intValue() );
                getTurno().setClave( (String) vRowTemp.get( 2 ) );
                getPuesto().setClave( (String) vRowTemp.get( 3 ) );
                getTurnoPersonal().setClaveTurno( (String) vRowTemp.get( 4 ) );
                getTurnoPersonal().setHorarioDef( (String) vRowTemp.get( 5 ) );
                getTurnoPersonal().setDiasTrabajoDef( (String) vRowTemp.get( 6 ) );
                getPersonalGrupo().setClaveGrupo( (String) vRowTemp.get( 7 ) );
                getPersonalCategoria().setClaveCategoria( ((Double) vRowTemp.get( 8 )).intValue() );
                setClaveAreaServicioOld( getAreaServ().getClave() );
                setClaveTurnoOld( getTurno().getClave() );
                bRet = true;
            }
        }
        return bRet;
    }

    public PersonalAreaServ[] buscarTodos() throws Exception {
        PersonalAreaServ arrRet[] = null, oPersonalAreaServ = null;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaTodosPersonalAreaServ();";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalAreaServ[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPersonalAreaServ = new PersonalAreaServ();
                oPersonalAreaServ.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPersonalAreaServ.getAreaServ().setClave( ((Double) vRowTemp.get( 1 )).intValue() );
                oPersonalAreaServ.getTurno().setClave( (String) vRowTemp.get( 2 ) );
                oPersonalAreaServ.getPuesto().setClave( (String) vRowTemp.get( 3 ) );
                oPersonalAreaServ.getTurnoPersonal().setClaveTurno( (String) vRowTemp.get( 4 ) );
                oPersonalAreaServ.getTurnoPersonal().setHorarioDef( (String) vRowTemp.get( 5 ) );
                oPersonalAreaServ.getTurnoPersonal().setDiasTrabajoDef( (String) vRowTemp.get( 6 ) );
                oPersonalAreaServ.getPersonalGrupo().setClaveGrupo( (String) vRowTemp.get( 7 ) );
                oPersonalAreaServ.getPersonalCategoria().setClaveCategoria( ((Double) vRowTemp.get( 8 )).intValue() );
                oPersonalAreaServ.setClaveAreaServicioOld( getAreaServ().getClave() );
                oPersonalAreaServ.setClaveTurnoOld( getTurno().getClave() );
                arrRet[i] = oPersonalAreaServ;
            }
        }
        return arrRet;
    }

    public PersonalAreaServ[] buscarPorFiltro() throws Exception {
        PersonalAreaServ arrRet[] = null, oPersonalAreaServ = null;
        ArrayList rst = null;
        String sQuery = "SELECT * FROM buscaPersonalAreaServFiltro( "
                + getNoTarjeta() + "::INTEGER, "
                + getAreaServ().getClave() + "::SMALLINT, "
                + "'" + getTurno().getClave() + "' );";
        oAD = new AccesoDatos();
        if( oAD.conectar() ) {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null && rst.size() > 0 ) {
            arrRet = new PersonalAreaServ[rst.size()];
            ArrayList vRowTemp = null;
            for( int i = 0; i < rst.size(); i++ ) {
                vRowTemp = (ArrayList) rst.get( i );
                oPersonalAreaServ = new PersonalAreaServ();
                oPersonalAreaServ.setNoTarjeta( ((Double) vRowTemp.get( 0 )).intValue() );
                oPersonalAreaServ.getAreaServ().setClave( ((Double) vRowTemp.get( 1 )).intValue() );
                oPersonalAreaServ.getTurno().setClave( (String) vRowTemp.get( 2 ) );
                oPersonalAreaServ.getPuesto().setClave( (String) vRowTemp.get( 3 ) );
                oPersonalAreaServ.getTurnoPersonal().setClaveTurno( (String) vRowTemp.get( 4 ) );
                oPersonalAreaServ.getTurnoPersonal().setHorarioDef( (String) vRowTemp.get( 5 ) );
                oPersonalAreaServ.getTurnoPersonal().setArrDiasTrabajo( ((String) vRowTemp.get( 6 )).toCharArray() );
                oPersonalAreaServ.getPersonalGrupo().setClaveGrupo( (String) vRowTemp.get( 7 ) );
                oPersonalAreaServ.getPersonalCategoria().setClaveCategoria( ((Double) vRowTemp.get( 8 )).intValue() );
                oPersonalAreaServ.setClaveAreaServicioOld( getAreaServ().getClave() );
                oPersonalAreaServ.setClaveTurnoOld( getTurno().getClave() );
                arrRet[i] = oPersonalAreaServ;
            }
        }
        return arrRet;
    }

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if( !buscarUsuario() || getNoTarjeta() < 1 || getAreaServ().getClave() < 1 || getTurno().getClave().isEmpty() ) {
            throw new Exception( "PersonalAreaServ.insertar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM insertaPersonalAreaServ( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + getAreaServ().getClave() + "::SMALLINT, "
                    + "'" + getTurno().getClave() + "', "
                    + "'" + getPuesto().getClave() + "', "
                    + "'" + getTurnoPersonal().getClaveTurno() + "', "
                    + "'" + getTurnoPersonal().getHorarioDef() + "', "
                    + "'" + getTurnoPersonal().getDiasTrabajoDef() + "', "
                    + "'" + getPersonalGrupo().getClaveGrupo() + "', "
                    + getPersonalCategoria().getClaveCategoria() + "::SMALLINT );";
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
        if( !buscarUsuario() || getNoTarjeta() < 1 || getClaveAreaServicioOld() < 1 || getClaveTurnoOld().isEmpty() ) {
            throw new Exception( "PersonalAreaServ.modificar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM modificaPersonalAreaServ( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + getClaveAreaServicioOld() + "::SMALLINT, "
                    + "'" + getClaveTurnoOld() + "', "
                    + getAreaServ().getClave() + "::SMALLINT, "
                    + "'" + getTurno().getClave() + "', "
                    + "'" + getPuesto().getClave() + "', "
                    + "'" + getTurnoPersonal().getClaveTurno() + "', "
                    + "'" + getTurnoPersonal().getHorarioDef() + "', "
                    + "'" + getTurnoPersonal().getDiasTrabajoDef() + "', "
                    + "'" + getPersonalGrupo().getClaveGrupo() + "', "
                    + getPersonalCategoria().getClaveCategoria() + "::SMALLINT );";
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
        if( !buscarUsuario() || getNoTarjeta() < 1 || getAreaServ().getClave() < 1 || getTurno().getClave().isEmpty() ) {
            throw new Exception( "PersonalAreaServ.eliminar: error de programación, faltan datos" );
        } else {
            sQuery = "SELECT * FROM eliminaPersonalAreaServ( "
                    + "'" + sIdUsuario + "', "
                    + getNoTarjeta() + "::INTEGER, "
                    + getAreaServ().getClave() + "::SMALLINT, "
                    + "'" + getTurno().getClave() + "' );";
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

    public int getNoTarjeta() {
        return nNoTarjeta;
    }

    public void setNoTarjeta( int nNoTarjeta ) {
        this.nNoTarjeta = nNoTarjeta;
    }

    public AreaServicioHRRB getAreaServ() {
        return oAreaServ;
    }

    public void setAreaServ( AreaServicioHRRB oAreaServ ) {
        this.oAreaServ = oAreaServ;
    }

    public Turno getTurno() {
        return oTurno;
    }

    public void setTurno( Turno oTurno ) {
        this.oTurno = oTurno;
    }

    public Puesto getPuesto() {
        return oPuesto;
    }

    public void setPuesto( Puesto oPuesto ) {
        this.oPuesto = oPuesto;
    }

    public TurnoPersonal getTurnoPersonal() {
        return oTurnoPersonal;
    }

    public void setTurnoPersonal( TurnoPersonal oTurnoPersonal ) {
        this.oTurnoPersonal = oTurnoPersonal;
    }

    public PersonalGrupo getPersonalGrupo() {
        return oPersonalGrupo;
    }

    public void setPersonalGrupo( PersonalGrupo oPersonalGrupo ) {
        this.oPersonalGrupo = oPersonalGrupo;
    }

    public PersonalCategoria getPersonalCategoria() {
        return oPersonalCategoria;
    }

    public void setPersonalCategoria( PersonalCategoria oPersonalCategoria ) {
        this.oPersonalCategoria = oPersonalCategoria;
    }

    public int getClaveAreaServicioOld() {
        return nClaveAreaServicioOld;
    }

    public void setClaveAreaServicioOld( int nClaveAreaServicioOld ) {
        this.nClaveAreaServicioOld = nClaveAreaServicioOld;
    }

    public String getClaveTurnoOld() {
        return sClaveTurnoOld;
    }

    public void setClaveTurnoOld( String sClaveTurnoOld ) {
        this.sClaveTurnoOld = sClaveTurnoOld;
    }
}
