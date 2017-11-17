package mx.gob.hrrb.jbs.core;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalGrupo;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Puesto;
import mx.gob.hrrb.utilerias.CustomDataExporter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author JMHG
 */
@ManagedBean( name = "oRepPersHosp" )
@ViewScoped
public class ReportesPersonalJB implements Serializable {

    private PersonalHospitalario oPersHosp;
    private List<PersonalHospitalario> lReporte;
    private List<Parametrizacion> lActivo;
    private List<String> lHeaderCols;
    private String sTipoReporte;
    private String sDisplayAntiguedad;
    private String sDisplayReportes;
    private String sTitulo;
    private Date dFechaMaxima;
    private CustomDataExporter oExporter;
    private StreamedContent dFile;
    private int nAntiguedadInicio;
    private int nAntiguedadFin;
    private List<PersonalHospitalario> lFiltro;
    private boolean[] arrRenderColumn;
    private String sEmptyMessage;
    private List<PersonalGrupo> lPersonalGrupo;
    private List<Puesto> lPuesto;
    private List<AreaServicioHRRB> lAreaServ;
    private List<Parametrizacion> lTipoEmpleado;
    private String sDisplayFiltro;

    private final ResourceBundle etiq = ResourceBundle.getBundle( "mx.gob.hrrb.modelo.core.hrrb_labels" );

    public ReportesPersonalJB() {
        limpiar();
        crearListaActivo();
        crearListaPersonalGrupo();
        crearListaPuesto();
        crearListaAreaServ();
        crearListaTipoEmpleado();
    }

    private void limpiar() {
        oPersHosp = new PersonalHospitalario();
        oPersHosp.setFechaCambioActivo( new Date() );
        lReporte = null;
        sTipoReporte = "";
        lHeaderCols = null;
        sDisplayAntiguedad = "none;";
        lActivo = null;
        dFechaMaxima = new Date();
        sDisplayReportes = "none;";
        oExporter = null;
        nAntiguedadInicio = 0;
        nAntiguedadFin = 0;
        lFiltro = null;
        sTitulo = "";
        arrRenderColumn = new boolean[]{true, true, true, true};
        sEmptyMessage = "No hay datos.";
        sDisplayFiltro = "none;";
    }

    public void seleccionarReporte() {
        switch( sTipoReporte ) {
            case "2":
                sDisplayAntiguedad = "table;";
                sDisplayReportes = "none;";
                sDisplayFiltro = "none;";
                break;
            case "3":
                sDisplayAntiguedad = "none;";
                sDisplayReportes = "none;";
                sDisplayFiltro = "table;";
                break;
            default:
                sDisplayAntiguedad = "none;";
                sDisplayReportes = "none;";
                sDisplayFiltro = "none;";
                generaReporte();
                break;
        }
    }

    public void generaReporte() {
        String sMensaje = "";
        FacesContext context = FacesContext.getCurrentInstance();
        if( sTipoReporte.compareTo( "2" ) == 0 && nAntiguedadInicio > nAntiguedadFin && nAntiguedadFin > 0 ) {
            sMensaje = "Los años de inicio no pueden ser mayores a los del final.";
            context.addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, "Error!", sMensaje ) );
            return;
        }
        lReporte = null;
        lFiltro = null;
        clearSortFilter();
        lHeaderCols = new ArrayList<>();
        lHeaderCols.add( etiq.getString( "noTarjeta" ) );
        lHeaderCols.add( etiq.getString( "nombre" ) );
        lHeaderCols.add( etiq.getString( "appat" ) );
        lHeaderCols.add( etiq.getString( "apmat" ) );

        PersonalHospitalario[] arrPersHosp = null;
        try {
            arrRenderColumn = new boolean[]{false, false, false, false};
            switch( sTipoReporte ) {
                case "0":
                    arrPersHosp = oPersHosp.buscarReporteVigenciaCredElector();
                    sTitulo = "Vigencia de Credencial de Elector";
                    lHeaderCols.add( etiq.getString( "anioRegCredElector" ) );
                    arrRenderColumn[0] = true;
                    sEmptyMessage = "No hay personal con credenciales vencidas o por vencerse";
                    break;
                case "1":
                    arrPersHosp = oPersHosp.buscarReporteDocumentosFaltantes();
                    sTitulo = "Documentos Faltantes";
                    lHeaderCols.add( etiq.getString( "noDocsFaltante" ) );
                    arrRenderColumn[1] = true;
                    sEmptyMessage = "No hay personal que falte registrar documentos";
                    break;
                case "2":
                    arrPersHosp = oPersHosp.buscarReporteAntiguedad( nAntiguedadInicio, nAntiguedadFin );
                    sTitulo = "Antigüedad de Empleados";
                    lHeaderCols.add( etiq.getString( "fechaRegistro" ) );
                    lHeaderCols.add( etiq.getString( "aniosServicio" ) );
                    arrRenderColumn[2] = true;
                    sEmptyMessage = "No hay personal con la antigüedad especificada.";
                    break;
                case "3":
                    if( oPersHosp.getTipoEmpleado().getTipoParametro() != null && !oPersHosp.getTipoEmpleado().getTipoParametro().isEmpty() ) {
                        oPersHosp.getTipoEmpleado().setClaveParametro( Parametrizacion.TABLA_TIPO_EMPLEADO );
                    }
                    arrPersHosp = oPersHosp.buscarTodosFiltro();
                    sTitulo = "Personal Hospitalario";
                    lHeaderCols.add( etiq.getString( "curp" ) );
                    lHeaderCols.add( etiq.getString( "rfc" ) );
                    lHeaderCols.add( etiq.getString( "cedulaProf" ) );
                    lHeaderCols.add( etiq.getString( "fechaRegistro" ) );
                    lHeaderCols.add( etiq.getString( "estadoActividad" ) );
                    lHeaderCols.add( etiq.getString( "fechaCambioActividad" ) );
                    lHeaderCols.add( etiq.getString( "tipoEmpleado" ) );
                    lHeaderCols.add( etiq.getString( "vistoSinergia" ) );
                    lHeaderCols.add( etiq.getString( "grupoPersonal" ) );
                    lHeaderCols.add( etiq.getString( "puesto" ) );
                    lHeaderCols.add( etiq.getString( "areaServicio" ) );
                    arrRenderColumn[3] = true;
                    sEmptyMessage = "No hay personal con el filtro especificado.";
                    break;
                default:
                    sDisplayReportes = "none;";
                    break;
            }
            if( arrPersHosp != null ) {
                lReporte = new ArrayList<>( Arrays.asList( arrPersHosp ) );
            }
            sDisplayReportes = "block;";
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error interno.";
        }

        if( !sMensaje.isEmpty() ) {
            arrRenderColumn = new boolean[]{false, false, false, false};
            sDisplayReportes = "none;";
            context.addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, "Error!", sMensaje ) );
        }
    }

    public void exportaReporte() {
        if( lFiltro != null && !lFiltro.isEmpty() ) {
            lReporte = lFiltro;
        }
        if( lReporte != null && !lReporte.isEmpty() ) {
            oExporter = new CustomDataExporter( false );
            estiloReporte();
            defaultReporte();
            for( PersonalHospitalario oPers : lReporte ) {
                oExporter.addCell( oPers.getNoTarjeta() );
                oExporter.addCell( oPers.getNombres() );
                oExporter.addCell( oPers.getApPaterno() );
                oExporter.addCell( oPers.getApMaterno() );
                switch( sTipoReporte ) {
                    case "0":
                        oExporter.addCell( oPers.getAnoRegCredElector() );
                        break;
                    case "1":
                        oExporter.addCell( oPers.getNoTarjetaBase() );
                        break;
                    case "2":
                        oExporter.addCell( oPers.getFechaRegistro(), oExporter.findStyle( "ARIAL_10_NORMAL_DATE" ) );
                        oExporter.addCell( oPers.getEdad() );
                        break;
                    case "3":
                        oExporter.addCell( oPers.getCurp() );
                        oExporter.addCell( oPers.getRFC() );
                        oExporter.addCell( oPers.getCedProf() );
                        oExporter.addCell( oPers.getFechaRegistro(), oExporter.findStyle( "ARIAL_10_NORMAL_DATE" ) );
                        oExporter.addCell( oPers.getActivo().getValor() );
                        oExporter.addCell( oPers.getFechaCambioActivo(), oExporter.findStyle( "ARIAL_10_NORMAL_DATE" ) );
                        oExporter.addCell( oPers.getTipoEmpleado().getValor() );
                        oExporter.addCell( oPers.getSinergia() );
                        oExporter.addCell( oPers.getPersonalAreaServ().getPersonalGrupo().getDescripcion() );
                        oExporter.addCell( oPers.getPersonalAreaServ().getPuesto().getDescripcion() );
                        oExporter.addCell( oPers.getPersonalAreaServ().getAreaServ().getDescripcion() );
                        break;
                }
                oExporter.addRowCount();
            }
            /*for( int i = 0; i <= lHeaderCols.size(); i++ ) {
                oExporter.setCoulmnSize( i, 0.0, false );
            }*/
            oExporter.addCell( "Total:", oExporter.findStyle( "ARIAL_12_BOLD_RIGHT" ) );
            oExporter.mergeCells( -1, -1, 0, lHeaderCols.size() - 2 );
            oExporter.addCell( (Object) lReporte.size(), oExporter.findStyle( "ARIAL_12_BOLD_CENTER" ) );
            try {
                oExporter.createExcel();
                dFile = new DefaultStreamedContent(
                        new FileInputStream( oExporter.getTempFile() ),
                        oExporter.getMIMEType(),
                        "Reporte_" + sTitulo.replaceAll( " ", "_" ) + oExporter.getExtension() );
            } catch( Exception ex ) {
                ex.printStackTrace();
            }
        }
    }

    private void estiloReporte() {
        oExporter.addFont( "ARIAL_12_BOLD", CustomDataExporter.FONT_ARIAL, (short) 12, true );
        oExporter.addFont( "ARIAL_10_NORMAL", CustomDataExporter.FONT_ARIAL, (short) 10 );
        oExporter.addStyle( "ARIAL_12_BOLD_CENTER", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true, oExporter.findFont( "ARIAL_12_BOLD" ) );
        oExporter.addStyle( "ARIAL_12_BOLD_RIGHT", CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, true, oExporter.findFont( "ARIAL_12_BOLD" ) );
        oExporter.addStyle( "ARIAL_10_NORMAL_DATE", CellStyle.ALIGN_GENERAL, CellStyle.VERTICAL_CENTER, true, oExporter.findFont( "ARIAL_10_NORMAL" ) );
        oExporter.addStyle( "ARIAL_12_BOLD_DATE", CellStyle.ALIGN_GENERAL, CellStyle.VERTICAL_CENTER, true, oExporter.findFont( "ARIAL_12_BOLD" ) );
        oExporter.addDataFormat( oExporter.findStyle( "ARIAL_12_BOLD_DATE" ),
                new Locale( "es", "MX" ),
                "dd/MM/yyyy" );
        oExporter.addDataFormat( oExporter.findStyle( "ARIAL_10_NORMAL_DATE" ),
                new Locale( "es", "MX" ),
                "dd/MM/yyyy" );
    }

    private void defaultReporte() {
        oExporter.addCell( 0, sTitulo, oExporter.findStyle( "ARIAL_12_BOLD_CENTER" ) );
        oExporter.mergeCells( 0, 0, 0, lHeaderCols.size() - 1 );
        oExporter.addCell( new Date(), oExporter.findStyle( "ARIAL_12_BOLD_DATE" ) );
        oExporter.addRowCount();
        for( String sHeader : lHeaderCols ) {
            oExporter.addCell( sHeader, oExporter.findStyle( "ARIAL_12_BOLD_CENTER" ) );
        }
        oExporter.addRowCount();
    }

    public String postDownload() {
        String sPag = "ReportesPersonalHospitalario.xhtml?faces-redirect=true";
        try {
            if( oExporter != null ) {
                if( dFile != null ) {
                    dFile.getStream().close();
                } else {
                    System.out.println( "dFile is null" );
                }
                System.out.println( "Temp file deleted: " + oExporter.deleteTempFile() );
                limpiar();
            } else {
                System.out.println( "Exporter is null" );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }

        return sPag;
    }

    private void crearListaActivo() {
        Parametrizacion[] arrParam = null;
        String sValor = "";
        try {
            arrParam = (new Parametrizacion()).buscaEstadoActivoPersonal();
            if( arrParam != null ) {
                for( Parametrizacion oParam : arrParam ) {
                    sValor = sp_ascii( oParam.getValor() ).toUpperCase();
                    oParam.setValor( sValor );
                }
                oPersHosp.getActivo().setClaveParametro( arrParam[0].getClaveParametro() );
                lActivo = new ArrayList<>( Arrays.asList( arrParam ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
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

    public String sp_ascii( String input ) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for( int i = 0; i < original.length(); i++ ) {
            // Reemplazamos los caracteres especiales.
            output = output.replace( original.charAt( i ), ascii.charAt( i ) );
        }//for i
        return output;
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

    public boolean filterByDate( Object value, Object filter, Locale locale ) {
        String filterText = (filter == null) ? null : filter.toString().trim().toUpperCase();
        String valueText = (value == null) ? null : value.toString().trim().toUpperCase();
        if( filterText == null || filterText.isEmpty() ) {
            return true;
        }

        if( value == null || valueText == null ) {
            return false;
        }
        String[] sFechaValor = valueText.split( "-" );
        valueText = sFechaValor[2] + "/" + sFechaValor[1] + "/" + sFechaValor[0];
        return valueText.contains( filterText );
    }

    public PersonalHospitalario getPersonalHospitalario() {
        return oPersHosp;
    }

    public void setPersonalHospitalario( PersonalHospitalario oPersHosp ) {
        this.oPersHosp = oPersHosp;
    }

    public List<PersonalHospitalario> getReporte() {
        return lReporte;
    }

    public String getTipoReporte() {
        return sTipoReporte;
    }

    public void setTipoReporte( String sTipoReporte ) {
        this.sTipoReporte = sTipoReporte;
    }

    public List<Parametrizacion> getListaActivo() {
        return lActivo;
    }

    public String getDisplayAntiguedad() {
        return sDisplayAntiguedad;
    }

    public Date getFechaMaxima() {
        return dFechaMaxima;
    }

    public String getDisplayReportes() {
        return sDisplayReportes;
    }

    public StreamedContent getDownloadFile() {
        return dFile;
    }

    public int getAntiguedadInicio() {
        return nAntiguedadInicio;
    }

    public void setAntiguedadInicio( int nAntiguedadInicio ) {
        this.nAntiguedadInicio = nAntiguedadInicio;
    }

    public int getAntiguedadFin() {
        return nAntiguedadFin;
    }

    public void setAntiguedadFin( int nAntiguedadFin ) {
        this.nAntiguedadFin = nAntiguedadFin;
    }

    public List<PersonalHospitalario> getFiltro() {
        return lFiltro;
    }

    public void setFiltro( List<PersonalHospitalario> lFiltro ) {
        this.lFiltro = lFiltro;
    }

    public String getTitulo() {
        return sTitulo;
    }

    public void setTitulo( String sTitulo ) {
        this.sTitulo = sTitulo;
    }

    public boolean[] getArrRenderColumn() {
        return arrRenderColumn;
    }

    public String getEmptyMessage() {
        return sEmptyMessage;
    }

    public void clearSortFilter() {
        DataTable dtReportes = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent( ":frmRepPersHosp:dtReportes" );
        if( dtReportes != null ) {
            dtReportes.setSortOrder( "ascending" );
            dtReportes.setFirst( 0 );
            dtReportes.setSortBy( null );
            dtReportes.setValueExpression( "sortBy", null );
            dtReportes.setFilteredValue( null );
            dtReportes.setFilters( null );
            dtReportes.resetValue();
            dtReportes.reset();
        }
    }

    public String getDisplayFiltro() {
        return sDisplayFiltro;
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
}
