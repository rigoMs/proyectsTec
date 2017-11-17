package mx.gob.hrrb.jbs.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalGrupo;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Puesto;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author JMHG
 */
@ManagedBean( name = "oBuscarPersHospJB" )
@ViewScoped
public class BuscarPersonalHospitalarioJB implements Serializable {

    private PersonalHospitalario oPersHosp;
    private PersonalHospitalario oPersHospSelect;
    private List<PersonalHospitalario> listPersHosp;
    private PersonalHospitalario[] arrPersHosp;
    private boolean bEncontrado;
    private String sMostrarBusqueda;
    private String sDivStyle;
    private boolean bDialog;
    private List<PersonalGrupo> lPersonalGrupo;
    private List<Puesto> lPuesto;
    private List<AreaServicioHRRB> lAreaServ;
    private List<Parametrizacion> lTipoEmpleado;
    private String sTabId;
    private boolean bPersBase;

    public BuscarPersonalHospitalarioJB() {
        bDialog = false;
        crearListaPersonalGrupo();
        crearListaPuesto();
        crearListaAreaServ();
        crearListaTipoEmpleado();
        sTabId = "tab1";
        limpiar();
    }

    public void limpiar() {
        oPersHosp = new PersonalHospitalario();
        oPersHospSelect = new PersonalHospitalario();
        listPersHosp = null;
        arrPersHosp = null;
        bEncontrado = false;
        sMostrarBusqueda = "none;";
        if( !bDialog ) {
            sDivStyle = "margin-left: 10%; margin-right: 10%; width: 80%;";
        }
        oPersHosp.getPersonalAreaServ().getPuesto().setClave( "" );
        oPersHosp.getTipoEmpleado().setClaveParametro( "" );
        oPersHosp.getTipoEmpleado().setTipoParametro( "" );
    }

    public void buscar() {
        String sMensaje = "El personal no existe.";
        try {
            if( bPersBase ) {
                oPersHosp.setNoTarjetaBase( -1 );
            }
            if( sTabId.compareTo( "tab3" ) == 0 ) {
                if( !oPersHosp.getTipoEmpleado().getTipoParametro().trim().isEmpty() ) {
                    oPersHosp.getTipoEmpleado().setClaveParametro( Parametrizacion.TABLA_TIPO_EMPLEADO );
                }
                arrPersHosp = oPersHosp.buscarTodosFiltro();
            } else {
                arrPersHosp = oPersHosp.buscarPorFiltro();
            }
            if( arrPersHosp != null && arrPersHosp.length > 0 ) {
                listPersHosp = new ArrayList<>( Arrays.asList( arrPersHosp ) );
                bEncontrado = true;
                sMostrarBusqueda = "block;";
                sMensaje = "";
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error interno.";
        }
        if( !sMensaje.isEmpty() ) {
            RequestContext.getCurrentInstance().showMessageInDialog( new FacesMessage( "Buscar Personal Hospitalario", sMensaje ) );
            System.out.println( sMensaje );
        }
    }

    private void crearListaPersonalGrupo() {
        lPersonalGrupo = null;
        try {
            PersonalGrupo[] arrGrupo = (new PersonalGrupo()).buscarTodos();
            if( arrGrupo != null && arrGrupo.length > 0 ) {
                lPersonalGrupo = new ArrayList<>( Arrays.asList( arrGrupo ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void crearListaPuesto() {
        lPuesto = null;
        try {
            Puesto[] arrPuesto = (new Puesto()).buscarTodos();
            if( arrPuesto != null && arrPuesto.length > 0 ) {
                lPuesto = new ArrayList<>( Arrays.asList( arrPuesto ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void crearListaAreaServ() {
        lAreaServ = null;
        try {
            AreaServicioHRRB[] arrAreaServ = (new AreaServicioHRRB()).buscarTodos();
            if( arrAreaServ != null && arrAreaServ.length > 0 ) {
                lAreaServ = new ArrayList<>( Arrays.asList( arrAreaServ ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void crearListaTipoEmpleado() {
        lTipoEmpleado = null;
        try {
            Parametrizacion[] arrTipoEmpleado = (new Parametrizacion()).buscaTipoEmpleado();
            if( arrTipoEmpleado != null && arrTipoEmpleado.length > 0 ) {
                lTipoEmpleado = new ArrayList<>( Arrays.asList( arrTipoEmpleado ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public boolean filterByUpperCase( Object value, Object filter, Locale locale ) {
        String filterText = (filter == null) ? null : filter.toString().trim().toUpperCase();
        String valueText = (value == null) ? null : value.toString().trim().toUpperCase();
        if( filterText == null || filterText.isEmpty() ) {
            return true;
        }

        if( value == null || valueText == null ) {
            return false;
        }

        return valueText.contains( filterText );
    }

    public void tabChange( TabChangeEvent event ) {
        this.sTabId = event.getTab().getId();
        limpiar();
    }

    public PersonalHospitalario getPersHosp() {
        return oPersHosp;
    }

    public void setPersHosp( PersonalHospitalario oPersHosp ) {
        this.oPersHosp = oPersHosp;
    }

    public void setPersonalHospitalarioSelect( PersonalHospitalario oPersHospSelect ) {
        this.oPersHospSelect = oPersHospSelect;
    }

    public PersonalHospitalario getPersonalHospitalarioSelect() {
        return oPersHospSelect;
    }

    public List<PersonalHospitalario> getListaPersonal() {
        return listPersHosp;
    }

    public PersonalHospitalario[] getArrPersonalHosp() {
        return arrPersHosp;
    }

    public void setArrPersonalHosp( PersonalHospitalario[] arrPersHosp ) {
        this.arrPersHosp = arrPersHosp;
    }

    public boolean isEncontrado() {
        return bEncontrado;
    }

    public void setEncontrado( boolean bEncontrado ) {
        this.bEncontrado = bEncontrado;
    }

    public String getMostrarBusqueda() {
        return sMostrarBusqueda;
    }

    public void setMostrarBusqueda( String sMostrarBusqueda ) {
        this.sMostrarBusqueda = sMostrarBusqueda;
    }

    public String getDivStyle() {
        return sDivStyle;
    }

    public void setDivStyle( String sDivStyle ) {
        this.sDivStyle = sDivStyle;
    }

    public boolean isDialog() {
        return bDialog;
    }

    public void setDialog( boolean bDialog ) {
        this.bDialog = bDialog;
        sDivStyle = "";
    }

    public List<PersonalGrupo> getListaPersonalGrupo() {
        return lPersonalGrupo;
    }

    public List<Puesto> getListaPuesto() {
        return lPuesto;
    }

    public List<AreaServicioHRRB> getListaAreaServ() {
        return lAreaServ;
    }

    public List<Parametrizacion> getListaTipoEmpleado() {
        return lTipoEmpleado;
    }

    public boolean isPersBase() {
        return bPersBase;
    }

    public void setPersBase( boolean bPersBase ) {
        this.bPersBase = bPersBase;
    }
}
