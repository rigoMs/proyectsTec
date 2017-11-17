package mx.gob.hrrb.jbs.core;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.RegistroSinergia;
import mx.gob.hrrb.utilerias.CustomDataExporter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.DateFormatConverter;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author JMHG
 */
@ManagedBean( name = "oRepSinergia" )
@ViewScoped
public class ReporteSinergiaJB implements Serializable {

    private List<RegistroSinergia> lSinergia;
    private List<RegistroSinergia> lSinergiaResumen;
    private List<RegistroSinergia> lFiltrado;
    private CustomDataExporter oExporter;
    private StreamedContent dFile;
    private Gson oGson;
    private RegistroSinergia oRegSinergia;
    private RegistroSinergia oRegSinergiaSelect;
    private List<RegistroSinergia> lReportes;
    private static String RUTA = "";
    private Date dFechaHoy;
    private String sDisplaySinergia;
    private boolean bCerrar;
    private String sPassword;
    private final String[] arrHeaderTextSinergia = {"DESCANSO", "Nº", "Nº DE GRUPO", "RFC", "FUNCION / NOMBRE", "CATEGORIA", "CODIGO", "TIPO DE PLAZA",
        "ORIGEN DE LA PLAZA", "TURNO", "", "HORARIO", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB", "DOM", "Nº TARJETA", "NOMBRE PERSONAL", "VIGENCIA",
        "FECHA CAMBIO ACTIVIDAD"};

    /**
     * Creates a new instance of ReporteSinergia
     */
    public ReporteSinergiaJB() {
        oRegSinergia = new RegistroSinergia();
        lSinergia = null;
        lSinergiaResumen = null;
        oExporter = null;
        oRegSinergiaSelect = null;
        dFechaHoy = new Date();
        lReportes = null;
        sDisplaySinergia = "none;";
        bCerrar = true;
        sPassword = "";
        lFiltrado = null;
        buscarVariablesDeClase();
    }

    private void buscarVariablesDeClase() {
        try {
            RUTA = oRegSinergia.buscarVariableDeClase( "RUTA" );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public void limpiar() {
        oRegSinergia = new RegistroSinergia();
        lSinergia = null;
        lSinergiaResumen = null;
        oExporter = null;
        oRegSinergiaSelect = null;
        dFechaHoy = new Date();
        lReportes = null;
        sDisplaySinergia = "none;";
        dFile = null;
        bCerrar = true;
        sPassword = "";
        lFiltrado = null;
    }

    private boolean verificarFechas() throws Exception {
        if( oRegSinergia.getFechaRegistroOld() != null && oRegSinergia.getFechaRegistro() != null ) {
            return oRegSinergia.getFechaRegistro().after( oRegSinergia.getFechaRegistroOld() );
        }
        return true;
    }

    public void crearListaReportes() {
        String sMensaje = "";
        lReportes = null;
        RegistroSinergia[] arrReps = null;
        try {
            if( verificarFechas() ) {
                arrReps = oRegSinergia.buscarTodosFiltro();
                if( arrReps != null && arrReps.length > 0 ) {
                    lReportes = new ArrayList<>( Arrays.asList( arrReps ) );
                    sDisplaySinergia = "block;";
                } else {
                    sDisplaySinergia = "none;";
                    sMensaje = "No hay reportes en la(s) fecha(s) especificada(s)";
                }
            } else {
                sMensaje = "La fecha final no puede ser menor a la fecha inicial";
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sDisplaySinergia = "none;";
            sMensaje = "Hubo un error interno";
        }

        if( !sMensaje.isEmpty() ) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, "Buscar Reporte Sinergia", sMensaje ) );
            System.out.println( sMensaje );
        }
    }

    public void generaReporteSinergia() {
        oRegSinergia.setFechaRegistro( dFechaHoy );
        try {
            lSinergia = null;
            lSinergiaResumen = null;
            RegistroSinergia[] arrRegSinergia = null;
            arrRegSinergia = oRegSinergia.buscarTodosSinergia();
            if( arrRegSinergia != null && arrRegSinergia.length > 0 ) {
                lSinergia = new ArrayList<>( Arrays.asList( arrRegSinergia ) );
            }
            arrRegSinergia = null;
            arrRegSinergia = oRegSinergia.buscarTodosSinergiaResumen();
            if( arrRegSinergia != null && arrRegSinergia.length > 0 ) {
                lSinergiaResumen = new ArrayList<>( Arrays.asList( arrRegSinergia ) );
            }
            sDisplaySinergia = "block;";
            bCerrar = !oRegSinergia.buscar();
        } catch( Exception ex ) {
            ex.printStackTrace();
            sDisplaySinergia = "none;";
        }
    }

    public void exportaSinergia() {
        String sMensaje = "";
        if( lSinergia != null && lSinergiaResumen != null ) {
            oExporter = new CustomDataExporter( false );
            estiloSinergia();
            defaultSinergia();
            for( RegistroSinergia oReg : lSinergia ) {
                if( oReg.getNoPersonal() != null && oReg.getNoPersonal().trim().length() > 0 ) {
                    oExporter.addCell( 0, Integer.valueOf( oReg.getNoPersonal() ) );
                } else {
                    oExporter.addCell( 0, oReg.getNoPersonal() );
                }

                if( oReg.getNoGrupo() != null
                        && oReg.getNoGrupo().trim().length() > 0
                        && oReg.getNoGrupo().trim().length() < 4 ) {
                    oExporter.addCell( 1, Integer.valueOf( oReg.getNoGrupo() ) );
                } else {
                    oExporter.addCell( 1, oReg.getNoGrupo() );
                }

                if( oReg.getRFC() != null
                        && oReg.getRFC().trim().length() > 0
                        && oReg.getRFC().trim().length() < 5 ) {
                    oExporter.addCell( 2, Integer.valueOf( oReg.getRFC() ) );
                } else {
                    oExporter.addCell( 2, oReg.getRFC() );
                }

                oExporter.addCell( 3, oReg.getFuncionNombre() );
                oExporter.addCell( 4, oReg.getCategoria() );
                oExporter.addCell( 5, oReg.getCodigo() );
                oExporter.addCell( 6, oReg.getTipoPlaza() );
                oExporter.addCell( 7, oReg.getOrigenPlaza() );
                oExporter.addCell( 8, oReg.getTurno() );
                oExporter.addCell( 10, oReg.getHorario() );
                if( oReg.getArrDiasDescanso() != null ) {
                    oExporter.addCell( 11, oReg.getArrDiasDescanso()[0] );
                    oExporter.addCell( 12, oReg.getArrDiasDescanso()[1] );
                    oExporter.addCell( 13, oReg.getArrDiasDescanso()[2] );
                    oExporter.addCell( 14, oReg.getArrDiasDescanso()[3] );
                    oExporter.addCell( 15, oReg.getArrDiasDescanso()[4] );
                    oExporter.addCell( 16, oReg.getArrDiasDescanso()[5] );
                    oExporter.addCell( 17, oReg.getArrDiasDescanso()[6] );
                }
                oExporter.addCell( 18, (oReg.getNoTarjeta() == 0 ? "" : oReg.getNoTarjeta()) );
                oExporter.addCell( 19, oReg.getNombrePersonal() );
                oExporter.addCell( 20, oReg.getActivo() );
                oExporter.addCell( 21, oReg.getFechaCambioActivo(), oExporter.findStyle( "ARIAL_10_NORMAL_DATE" ) );

                oExporter.addRowCount();
            }

            oExporter.setCurrentRow( oExporter.getCurrentRow() + 10 );
            for( RegistroSinergia oReg : lSinergiaResumen ) {
                if( oReg.getCategoria() != null
                        && oReg.getCategoria().trim().length() > 0 ) {
                    oExporter.addCell( 3, oReg.getCategoria() );
                }
                if( oReg.getFuncionNombre() != null
                        && oReg.getFuncionNombre().trim().length() > 0 ) {
                    oExporter.addCell( 4, oReg.getFuncionNombre() );
                }
                if( oReg.getNoPersonal() != null
                        && oReg.getNoPersonal().trim().compareTo( "-" ) != 0 ) {
                    oExporter.addCell( 5, Integer.valueOf( oReg.getNoPersonal() ) );
                } else {
                    if( oReg.getFuncionNombre() != null
                            && oReg.getFuncionNombre().trim().length() > 0 ) {
                        oExporter.addCell( -1, 5, oReg.getNoPersonal(), null );
                    }
                }

                oExporter.addRowCount();
            }
            /*for( int i = 0; i < arrHeaderTextSinergia.length; i++ ) {
                oExporter.setCoulmnSize( i, 0.0, true );
            }*/

            try {
                oExporter.createExcel();
                dFile = new DefaultStreamedContent(
                        new FileInputStream( oExporter.getTempFile() ),
                        oExporter.getMIMEType(),
                        "Reporte_Sinergia" + oExporter.getExtension() );
            } catch( Exception ex ) {
                ex.printStackTrace();
                sMensaje = "Hubo un error al exportar el reporte";
            }
        } else {
            sMensaje = "No hay datos del reporte de Sinergia";
        }

        if( !sMensaje.isEmpty() ) {
            System.out.println( sMensaje );
            RequestContext.getCurrentInstance().showMessageInDialog( new FacesMessage( "Reporte Sinergia", sMensaje ) );
        }
    }

    public String postDownload() {
        String sPag = "ReporteSinergia.xhtml?faces-redirect=true";
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

    private void estiloSinergia() {
        oExporter.addFont( "ARIAL_10_BOLD", CustomDataExporter.FONT_ARIAL, (short) 10, true );
        oExporter.addFont( "ARIAL_10_NORMAL", CustomDataExporter.FONT_ARIAL, (short) 10 );
        oExporter.addFont( "ARIAL_8_BOLD", CustomDataExporter.FONT_ARIAL, (short) 8, true );
        oExporter.addStyle( "ARIAL_10_BOLD_CENTER", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true, oExporter.findFont( "ARIAL_10_BOLD" ) );
        oExporter.addStyle( "ARIAL_10_NORMAL_CENTER", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true, oExporter.findFont( "ARIAL_10_NORMAL" ) );
        oExporter.addStyle( "ARIAL_8_BOLD_CENTER", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true, oExporter.findFont( "ARIAL_8_BOLD" ) );
        oExporter.addStyle( "ARIAL_10_NORMAL_DATE", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true, oExporter.findFont( "ARIAL_10_NORMAL" ) );
        oExporter.addDataFormat( oExporter.findStyle( "ARIAL_10_NORMAL_DATE" ),
                DateFormatConverter.convert( new Locale( "es", "MX" ), "dd/MM/yyyy" ) );
    }

    private void defaultSinergia() {
        oExporter.addCell( 5, "FECHA:", oExporter.findStyle( "ARIAL_10_BOLD_CENTER" ) );
        oExporter.addCell( 6, new Date(), oExporter.findStyle( "ARIAL_10_NORMAL_DATE" ) );
        oExporter.mergeCells( -1, -1, 6, 8 );
        oExporter.addCell( 11, arrHeaderTextSinergia[0], oExporter.findStyle( "ARIAL_10_BOLD_CENTER" ) );
        oExporter.mergeCells( -1, -1, 11, 17 );
        oExporter.addRowCount();
        int j = 1;
        for( int i = 0; i < arrHeaderTextSinergia.length - 1; i++ ) {
            String sValue = arrHeaderTextSinergia[j++];
            if( i != 9 ) {
                if( i < 11 ) {
                    oExporter.addCell( i, sValue, oExporter.findStyle( "ARIAL_10_BOLD_CENTER" ) );
                } else {
                    oExporter.addCell( i, sValue, oExporter.findStyle( "ARIAL_8_BOLD_CENTER" ) );
                }
            }
        }
        oExporter.setCurrentRow( oExporter.getCurrentRow() + 2 );
    }

    public void cerrarSinergia() {
        String sMensaje = "Sinergia Archivado";
        oRegSinergia.setFechaRegistro( dFechaHoy );
        try {
            if( oRegSinergia.buscarUsuario( sPassword ) ) {
                if( lSinergia != null && !lSinergia.isEmpty() && !oRegSinergia.buscar() ) {
                    SimpleDateFormat oFormat = new SimpleDateFormat( "yyMMdd" );
                    oRegSinergia.setNombre( "sinergia_" + oFormat.format( oRegSinergia.getFechaRegistro() ) + ".json" );

                    GsonBuilder oGsonBuilder = new GsonBuilder();
                    oGsonBuilder.setExclusionStrategies( new CustomExclusionStrategyPersHosp() );
                    oGsonBuilder.setDateFormat( "yyyy-MM-dd" );
                    oGson = oGsonBuilder.create();
                    OutputStream out = new FileOutputStream( RUTA + oRegSinergia.getNombre() );
                    JsonWriter writer = new JsonWriter( new OutputStreamWriter( out, "UTF-8" ) );
                    writer.setIndent( "    " );
                    writer.beginObject();
                    writer.name( "lSinergia" );
                    writer.beginArray();
                    for( RegistroSinergia oReg : lSinergia ) {
                        if( oReg.getArrDiasDescanso()[0] == '\u0000' ) {
                            oReg.setArrDiasDescanso( null );
                        }
                        oGson.toJson( oReg, RegistroSinergia.class, writer );
                    }
                    writer.endArray();
                    if( lSinergiaResumen != null && !lSinergiaResumen.isEmpty() ) {
                        writer.flush();
                        writer.name( "lSinergiaResumen" );
                        writer.beginArray();
                        for( RegistroSinergia oReg : lSinergiaResumen ) {
                            oGson.toJson( oReg, RegistroSinergia.class, writer );
                        }
                        writer.endArray();
                    }
                    writer.endObject();
                    writer.flush();
                    writer.close();
                    int nRes = oRegSinergia.insertar();
                    if( nRes > 0 ) {
                        bCerrar = false;
                        RequestContext.getCurrentInstance().execute( "PF('varDlgCerrarSinergia').hide();" );
                    } else {
                        sMensaje = "No se pudo registrar el reporte de Sinergia";
                    }
                } else {
                    sMensaje = "No se pudo almacenar el reporte de Sinergia";
                }
            } else {
                sMensaje = "Contraseña Incorrecta!";
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error al almacenar el reporte de Sinergi";
        }
        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( "Reporte Sinergia", sMensaje ) );
    }

    public void abrirSinergia() {
        String sMensaje = "";
        if( oRegSinergiaSelect != null && !oRegSinergiaSelect.getNombre().isEmpty() ) {
            lSinergia = new ArrayList<>();
            lSinergiaResumen = new ArrayList<>();
            oRegSinergia = oRegSinergiaSelect;
            oGson = new Gson();
            try {
                JsonReader reader = new JsonReader( new FileReader( RUTA + oRegSinergiaSelect.getNombre() ) );
                JsonParser parser = new JsonParser();
                JsonElement jsonTree = parser.parse( reader );
                JsonObject jsonObject = jsonTree.getAsJsonObject();

                JsonArray jsonArray = jsonObject.get( "lSinergia" ).getAsJsonArray();
                for( JsonElement oJson : jsonArray ) {
                    JsonObject oJsonRegSinergia = oJson.getAsJsonObject();
                    JsonElement oElement;
                    RegistroSinergia oReg = new RegistroSinergia();

                    oElement = oJsonRegSinergia.get( "sNoPersonal" );
                    if( oElement != null ) {
                        oReg.setNoPersonal( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sNoGrupo" );
                    if( oElement != null ) {
                        oReg.setNoGrupo( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sRFC" );
                    if( oElement != null ) {
                        oReg.setRFC( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sFuncionNombre" );
                    if( oElement != null ) {
                        oReg.setFuncionNombre( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sCategoria" );
                    if( oElement != null ) {
                        oReg.setCategoria( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sCodigo" );
                    if( oElement != null ) {
                        oReg.setCodigo( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sTipoPlaza" );
                    if( oElement != null ) {
                        oReg.setTipoPlaza( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sOrigenPlaza" );
                    if( oElement != null ) {
                        oReg.setOrigenPlaza( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sTurno" );
                    if( oElement != null ) {
                        oReg.setTurno( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sHorario" );
                    if( oElement != null ) {
                        oReg.setHorario( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "arrDiasDescanso" );
                    if( oElement != null ) {
                        JsonArray oJsonArray = oElement.getAsJsonArray();
                        oReg.setArrDiasDescanso( new char[]{
                            oJsonArray.get( 0 ).getAsCharacter(),
                            oJsonArray.get( 1 ).getAsCharacter(),
                            oJsonArray.get( 2 ).getAsCharacter(),
                            oJsonArray.get( 3 ).getAsCharacter(),
                            oJsonArray.get( 4 ).getAsCharacter(),
                            oJsonArray.get( 5 ).getAsCharacter(),
                            oJsonArray.get( 6 ).getAsCharacter()
                        } );
                    }
                    oElement = oJsonRegSinergia.get( "nNoTarjeta" );
                    if( oElement != null ) {
                        oReg.setNoTarjeta( oElement.getAsInt() );
                    }
                    oElement = oJsonRegSinergia.get( "sNombrePersonal" );
                    if( oElement != null ) {
                        oReg.setNombrePersonal( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "sActivo" );
                    if( oElement != null ) {
                        oReg.setActivo( oElement.getAsString() );
                    }
                    oElement = oJsonRegSinergia.get( "dFechaCambioActivo" );
                    if( oElement != null ) {
                        oReg.setFechaCambioActivo( new SimpleDateFormat( "yyyy-MM-dd" ).parse( oElement.getAsString() ) );
                    }

                    lSinergia.add( oReg );
                }

                jsonArray = jsonObject.get( "lSinergiaResumen" ).getAsJsonArray();
                for( JsonElement oJson : jsonArray ) {
                    JsonObject oJsonPersHosp = oJson.getAsJsonObject();
                    JsonElement oElement;
                    RegistroSinergia oReg = new RegistroSinergia();

                    oElement = oJsonPersHosp.get( "sCategoria" );
                    if( oElement != null ) {
                        oReg.setCategoria( oElement.getAsString() );
                    }
                    oElement = oJsonPersHosp.get( "sNoPersonal" );
                    if( oElement != null ) {
                        oReg.setNoPersonal( oElement.getAsString() );
                    }
                    oElement = oJsonPersHosp.get( "sFuncionNombre" );
                    if( oElement != null ) {
                        oReg.setFuncionNombre( oElement.getAsString() );
                    }

                    lSinergiaResumen.add( oReg );
                }
                sDisplaySinergia = "block;";
                bCerrar = false;
            } catch( Exception ex ) {
                ex.printStackTrace();
                sDisplaySinergia = "none;";
                bCerrar = false;
                sMensaje = "Hubo un error al abrir el reporte seleccionado.";
            }
        } else {
            sMensaje = "No se pudo abrir el reporte de sinergia seleccionado.";
            bCerrar = false;
        }

        if( !sMensaje.isEmpty() ) {
            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( "Reporte Sinergia", sMensaje ) );
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

    public List<RegistroSinergia> getSinergia() {
        return lSinergia;
    }

    public List<RegistroSinergia> getSinergiaResumen() {
        return lSinergiaResumen;
    }

    public String[] getHeaderTextSinergia() {
        return arrHeaderTextSinergia;
    }

    public StreamedContent getDownloadFile() {
        return dFile;
    }

    public RegistroSinergia getRegistroSinergia() {
        return oRegSinergia;
    }

    public void setRegistroSinergia( RegistroSinergia oRegSinergia ) {
        this.oRegSinergia = oRegSinergia;
    }

    public Date getFechaHoy() {
        return dFechaHoy;
    }

    public List<RegistroSinergia> getListaReportes() {
        return lReportes;
    }

    public RegistroSinergia getRegSinergiaSelect() {
        return oRegSinergiaSelect;
    }

    public void setRegSinergiaSelect( RegistroSinergia oRegSinergiaSelect ) {
        this.oRegSinergiaSelect = oRegSinergiaSelect;
    }

    public String getDisplaySinergia() {
        return sDisplaySinergia;
    }

    public boolean isCerrar() {
        return bCerrar;
    }

    public void setCerrar( boolean bCerrar ) {
        this.bCerrar = bCerrar;
    }

    public String getPassword() {
        return sPassword;
    }

    public void setPassword( String sPassword ) {
        this.sPassword = sPassword;
    }

    public List<RegistroSinergia> getFiltrado() {
        return lFiltrado;
    }

    public void setFiltrado( List<RegistroSinergia> lFiltrado ) {
        this.lFiltrado = lFiltrado;
    }

// *****************************************************************************
// *****************************************************************************
    public class CustomExclusionStrategyPersHosp implements ExclusionStrategy, Serializable {

        @Override
        public boolean shouldSkipField( FieldAttributes f ) {
            boolean bRet = true;
            switch( f.getName() ) {
                case "sNoPersonal":
                case "sNoGrupo":
                case "sRFC":
                case "sFuncionNombre":
                case "sCategoria":
                case "sCodigo":
                case "sTipoPlaza":
                case "sOrigenPlaza":
                case "sTurno":
                case "sHorario":
                case "arrDiasDescanso":
                case "nNoTarjeta":
                case "sActivo":
                case "sNombrePersonal":
                case "dFechaCambioActivo":
                    bRet = false;
                    break;
            }

            return bRet;
        }

        @Override
        public boolean shouldSkipClass( Class<?> clazz ) {
            return false;
        }
    }
}
