package mx.gob.hrrb.jbs.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DocumentoDigital;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author JMHG
 */
@ManagedBean( name = "oFiltroDocJB" )
@ViewScoped
public class FiltroDocumentosJB implements Serializable {

    private DocumentoDigital oDoc;
    private UploadedFile uFile;
    private String sMensaje;
    private String sExtension;
    private static String RUTA = "";
    private static long LIMITE_TAMANO_ARCHIVO = 0;
    private final static String TIPO_FOTO = "20";
    private final static String MIME_JPG = "image/jpeg";
    private final static String MIME_PDF = "application/pdf";

    private List<DocumentoDigital> lDocumentoDigital;
    private List<DocumentoDigital> lDocDigitalDisponible;
    private DocumentoDigital oDocSelect;
    private boolean bCarnetSeleccion;
    private String sDisplayCarnet;

    private StreamedContent dFile;

    public FiltroDocumentosJB() {
        oDoc = new DocumentoDigital();
        uFile = null;
        sMensaje = "";
        lDocumentoDigital = null;
        lDocDigitalDisponible = null;
        oDocSelect = new DocumentoDigital();
        bCarnetSeleccion = false;
        sDisplayCarnet = "block;";
        buscarVariablesDeClase();
    }

    private void buscarVariablesDeClase() {
        try {
            RUTA = oDoc.buscarVariableDeClase( "RUTA" );
            LIMITE_TAMANO_ARCHIVO = Long.parseLong( oDoc.buscarVariableDeClase( "LIMITE_TAMANO_ARCHIVO" ) );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public void fileUploadHandle( FileUploadEvent event ) {
        sMensaje = "";
        uFile = event.getFile();
        String sNombreArch;
        File fDirectorio;
        File fArchivo;
        String sRuta = "";

        oDoc = (DocumentoDigital) UIComponent.getCurrentComponent( FacesContext.getCurrentInstance() ).getAttributes().get( "documento" );

        if( oDoc != null && oDoc.getNoTarjeta() > 0 ) {
            if( isValid() ) {
                try {
                    sNombreArch = crearArchivoNombre();
                    sRuta = RUTA + oDoc.getNoTarjeta() + File.separator;
                    fDirectorio = new File( sRuta );
                    if( !fDirectorio.exists() ) {
                        fDirectorio.mkdir();
                    }
                    oDoc.setNombre( sNombreArch );
                    fArchivo = new File( sRuta, sNombreArch );
                    Files.copy( uFile.getInputstream(), fArchivo.toPath(), StandardCopyOption.REPLACE_EXISTING );
                    if( fArchivo.exists() ) {
                        if( oDoc.modificar() == 1 ) {
                            sMensaje = "El archivo fue almacenado con éxito.";
                            crearListaDocumentos( oDoc.getNoTarjeta() );
                        } else {
                            sMensaje = "No se pudo almacenar el archivo";
                            fArchivo.delete();
                        }
                    }
                } catch( Exception e ) {
                    e.printStackTrace();
                    sMensaje = "Hubo un error al subir el archivo.";
                }
            }
        } else {
            sMensaje = "No se pudo obtener el documento a modificar";
        }

        FacesMessage message = new FacesMessage( "Modificar Documento", sMensaje );
        FacesContext.getCurrentInstance().addMessage( null, message );
    }

    private boolean isValid() {
        boolean bRet = false;
        String sType = "";

        if( uFile != null && uFile.getSize() > 0 ) {
            if( uFile.getSize() < LIMITE_TAMANO_ARCHIVO ) {
                sType = uFile.getContentType();

                if( oDoc.getTipo().getTipoParametro().trim().compareTo( TIPO_FOTO ) == 0 ) {
                    if( (sType.compareTo( MIME_JPG ) == 0) ) {
                        sExtension = ".jpg";
                        bRet = true;
                    } else {
                        sMensaje = "El tipo de archivo es incorrecto para la foto.";
                    }
                } else {
                    if( sType.compareTo( MIME_PDF ) == 0 ) {
                        sExtension = ".pdf";
                        bRet = true;
                    } else {
                        sMensaje = "El tipo de archivo no es válido";
                    }
                }
            } else {
                sMensaje = "El tamaño del archivo excede el límite";
            }
        } else {
            sMensaje = "No se ha subido ningún archivo";
        }

        return bRet;
    }

    private String crearArchivoNombre() {
        String sRet = "";

        if( oDoc != null && oDoc.getNoTarjeta() > 0 ) {
            sRet = oDoc.getNombre();
            if( sRet.isEmpty() ) {
                sRet += oDoc.getNoTarjeta() + "_";
                sRet += oDoc.getTipo().getTipoParametro().trim();
                if( oDoc.getConsecutivo() > 0 ) {
                    sRet += "-";
                    sRet += oDoc.getConsecutivo();
                }
                sRet += sExtension;
            }
        }

        return sRet;
    }

    public void crearListaDocumentos( int nNoTarjeta ) {
        lDocumentoDigital = null;
        DocumentoDigital[] arrDocs = null;
        oDoc = new DocumentoDigital();
        oDoc.setNoTarjeta( nNoTarjeta );
        sDisplayCarnet = "block;";
        try {
            arrDocs = oDoc.buscarTodosFiltro();
            if( arrDocs != null ) {
                int i = 0;
                while( i < arrDocs.length ) {
                    bCarnetSeleccion = (arrDocs[i].getTipo().getTipoParametro().trim().compareTo( DocumentoDigital.CVE_SELECCION_PERS ) == 0);
                    if( bCarnetSeleccion ) {
                        sDisplayCarnet = "none;";
                        i = arrDocs.length;
                    }
                    i++;
                }
                lDocumentoDigital = new ArrayList<>( Arrays.asList( arrDocs ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void crearListaDocumentosDisponibles() {
        System.out.println( "crearListaDocumentoDisponible()" );
        lDocDigitalDisponible = null;
        DocumentoDigital[] arrDoc = null;
        try {
            arrDoc = oDoc.buscarDocumentosDisponibles();
            if( arrDoc != null ) {
                lDocDigitalDisponible = new ArrayList( Arrays.asList( arrDoc ) );
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void registraDocumento() {
        try {
            oDocSelect.setNoTarjeta( oDoc.getNoTarjeta() );
            oDocSelect.getTipo().asignaValorParametrizado( "TBH" + DocumentoDigital.CVE_SELECCION_PERS );
            oDocSelect.setConsecutivo( 0 );
            int nRet = oDocSelect.insertar();
            if( nRet > 0 ) {
                crearListaDocumentos( oDocSelect.getNoTarjeta() );
                sMensaje = "Documento Agregado";
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error al intentar registrar el documento";
        }
        FacesMessage message = new FacesMessage( "Modificar Documento", sMensaje );
        FacesContext.getCurrentInstance().addMessage( null, message );
    }

    public void eliminarRegistroDocumento( DocumentoDigital oDocDigi ) {
        System.out.println( "eliminarRegistroDocumento" );
        try {
            oDocSelect = oDocDigi;
            System.out.println( oDocSelect.getNoTarjeta() );
            System.out.println( oDocSelect.getTipo().getClaveParametro() + oDocSelect.getTipo().getTipoParametro() );
            System.out.println( oDocSelect.getConsecutivo() );
            int nRet = oDocSelect.eliminar();
            if( nRet > 0 ) {
                sMensaje = "Documento eliminado";
                crearListaDocumentos( oDocSelect.getNoTarjeta() );
            } else {
                sMensaje = "El documento no fue eliminado";
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            sMensaje = "Hubo un error al intentar eliminar el registro del documento seleccionado";
        }
        FacesMessage message = new FacesMessage( "Modificar Documento", sMensaje );
        FacesContext.getCurrentInstance().addMessage( null, message );
    }

    public void descargaArchivo() {
        sMensaje = "";
        String sRuta = "";
        String sNombreDocumento = "";
        String sMIME = "";
        File fDirectorio;
        File fArchivo;
        InputStream stream = null;
        oDoc = (DocumentoDigital) UIComponent.getCurrentComponent( FacesContext.getCurrentInstance() ).getAttributes().get( "documento" );
        if( oDoc != null && oDoc.getNoTarjeta() > 0 ) {
            try {
                sRuta = RUTA + oDoc.getNoTarjeta() + File.separator;
                fDirectorio = new File( sRuta );
                if( fDirectorio.exists() ) {
                    fArchivo = new File( sRuta, oDoc.getNombre() );
                    if( fArchivo.exists() && oDoc.isSubido() ) {
                        sNombreDocumento = sp_ascii( oDoc.getTipo().getValor() ).toUpperCase().replaceAll( " ", "_" );
                        if( oDoc.getTipo().getTipoParametro().compareTo( TIPO_FOTO ) == 0 ) {
                            sNombreDocumento += ".jpg";
                            sMIME = MIME_JPG;
                        } else {
                            sNombreDocumento += ".pdf";
                            sMIME = MIME_PDF;
                        }
                        stream = new FileInputStream( fArchivo );
                        dFile = new DefaultStreamedContent( stream, sMIME, sNombreDocumento );
                    } else {
                        sMensaje = "No existe documento o no esta subido";
                    }
                } else {
                    sMensaje = "No existe directorio";
                }
            } catch( Exception ex ) {
                ex.printStackTrace();
            }
        } else {
            sMensaje = "No se selecciono documento";
        }
        if( !sMensaje.isEmpty() ) {
            FacesMessage message = new FacesMessage( "Modificar Documento", sMensaje );
            FacesContext.getCurrentInstance().addMessage( null, message );
            System.out.println( sMensaje );
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
    //====

    public UploadedFile getUploadedFile() {
        return uFile;
    }

    public void setUploadedFile( UploadedFile uFile ) {
        this.uFile = uFile;
    }

    public DocumentoDigital getDocumentoDigital() {
        return oDoc;
    }

    public void setDocumentoDigital( DocumentoDigital oDoc ) {
        this.oDoc = oDoc;
    }

    public long getLimite() {
        return LIMITE_TAMANO_ARCHIVO;
    }

    public List<DocumentoDigital> getListaDocumentosDisponibles() {
        return lDocDigitalDisponible;
    }

    public List<DocumentoDigital> getListaDocumentos() {
        return lDocumentoDigital;
    }

    public DocumentoDigital getDocumentoSelect() {
        return oDocSelect;
    }

    public void setDocumentoSelect( DocumentoDigital oDocSelect ) {
        this.oDocSelect = oDocSelect;
    }

    public boolean isCarnetSeleccion() {
        return bCarnetSeleccion;
    }

    public void setCarnetSeleccion( boolean bCarnetSeleccion ) {
        this.bCarnetSeleccion = bCarnetSeleccion;
    }

    public String getDisplayCarnet() {
        return sDisplayCarnet;
    }

    public StreamedContent getDownloadFile() {
        return dFile;
    }
}
