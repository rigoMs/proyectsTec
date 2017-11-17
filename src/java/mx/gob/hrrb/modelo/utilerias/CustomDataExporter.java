package mx.gob.hrrb.utilerias;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author JMHG
 */
public class CustomDataExporter implements Serializable {

    private Workbook workbook;
    private Sheet sheet;
    private String sFileNamePrefix;
    private Map<String, Font> mFonts;
    private Map<String, CellStyle> mStyles;
    public final static String FONT_ARIAL = "Arial";
    private int nCurrentRow;
    private File fTempFile;
    private FileOutputStream fileOut;
    private boolean bXSSF;
    private String sMIME;
    private String sExtension;
    private String sFileName;

    public static String MIME_XLS = "application/vnd.ms-excel";
    public static String MIME_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * Constructs a new Workbook for the data to be exported
     *
     * @param bXSSF If Workbook should be of type XSSF or else use HSSF
     */
    public CustomDataExporter( boolean bXSSF ) {
        this( bXSSF, null, null );
    }

    /**
     * Constructs a new Workbook for the data to be exported
     *
     * @param bXSSF If Workbook should be of type XSSF or else use HSSF
     * @param sSheetName Name of the Sheet to be created
     */
    public CustomDataExporter( boolean bXSSF, String sSheetName ) {
        this( bXSSF, sSheetName, null );
    }

    /**
     * Constructs a new Workbook for the data to be exported
     *
     * @param bXSSF If Workbook should be of type XSSF or else use HSSF
     * @param sSheetName Name of the Sheet to be created
     * @param sFileNamePrefix Name of the file to create
     */
    public CustomDataExporter( boolean bXSSF, String sSheetName, String sFileNamePrefix ) {
        if( sFileNamePrefix == null ) {
            sFileNamePrefix = "CustomDataExporter_";
        }
        if( bXSSF ) {
            sMIME = MIME_XLSX;
            sExtension = ".xlsx";
            workbook = new XSSFWorkbook();
        } else {
            sMIME = MIME_XLS;
            sExtension = ".xls";
            workbook = new HSSFWorkbook();
        }
        if( sSheetName != null ) {
            sheet = workbook.createSheet( WorkbookUtil.createSafeSheetName( sSheetName ) );
        } else {
            sheet = workbook.createSheet();
        }
        this.bXSSF = bXSSF;
        this.sFileNamePrefix = sFileNamePrefix;
        mFonts = new HashMap<>();
        mStyles = new HashMap<>();
        nCurrentRow = 0;
    }

    /**
     * Creates the Excel file as a temporary file
     *
     * @throws Exception
     */
    public void createExcel() throws Exception {
        fTempFile = File.createTempFile( sFileNamePrefix, sExtension );
        sFileName = fTempFile.getName();

        fileOut = new FileOutputStream( fTempFile );
        workbook.write( fileOut );
        fileOut.flush();
        fileOut.close();
    }

    /**
     * Calls to delete the temporary excel file
     *
     * @return True if it deleted the file
     * @throws Exception
     */
    public boolean deleteTempFile() throws Exception {
        if( fTempFile != null && fTempFile.exists() ) {
            fTempFile.deleteOnExit();
            return fTempFile.delete();
        }
        return false;
    }

    /**
     * Adds a cell after the last cell with data at the row specified by the
     * global row count and cell's default style
     *
     * @param oValue Value to add to assign the cell (default's to BLANK cell if
     * data type is not a primitive type or Date)
     */
    public void addCell( Object oValue ) {
        addCell( -1, -1, oValue, null );
    }

    /**
     * Adds a cell after the last cell with data at the row specified by the
     * global row count and with the specified style
     *
     * @param oValue Value to add to assign the cell (default's to BLANK cell if
     * data type is not a primitive type or Date)
     * @param style Style to use for the cell (can be null)
     */
    public void addCell( Object oValue, CellStyle style ) {
        addCell( -1, -1, oValue, style );
    }

    /**
     * Adds a cell at the column index using the current row index
     *
     * @param nColumn Column index where to add cell (can use -1 to indicate to
     * add it after the last cell with data)
     * @param oValue Value to add to assign the cell (default's to BLANK cell if
     * data type is not a primitive type or Date)
     */
    public void addCell( int nColumn, Object oValue ) {
        addCell( -1, nColumn, oValue, null );
    }

    /**
     * Adds a cell using either the current row index or the last cell with data
     *
     * @param nColumn Column index where to add cell (can use -1 to indicate to
     * add it after the last cell with data)
     * @param oValue Value to add to assign the cell (default's to BLANK cell if
     * data type is not a primitive type or Date)
     * @param style Style to use for the cell
     */
    public void addCell( int nColumn, Object oValue, CellStyle style ) {
        addCell( -1, nColumn, oValue, style );
    }

    /**
     * Adds a cell at the specified row and column indexes with the cell's
     * default style
     *
     * @param nRow Row index where to add cell (can use -1 to indicate to use
     * the current row index value)
     * @param nColumn Column index where to add cell (can use -1 to indicate to
     * use the index of the last cell with data)
     * @param oValue Value to add to assign the cell (default's to BLANK cell if
     * data type is not a primitive type or Date)
     */
    public void addCell( int nRow, int nColumn, Object oValue ) {
        addCell( nRow, nColumn, oValue, null );
    }

    /**
     * Adds a cell at the specified row and column
     *
     * @param nRow Row index where to add cell (can use -1 to indicate to use
     * the current row index value)
     * @param nColumn Column index where to add cell (can use -1 to indicate to
     * use the index of the last cell with data)
     * @param oValue Value to add to assign the cell (default's to BLANK cell if
     * data type is not a primitive type or Date)
     * @param style CellStyle to use for the cell (can be null to use cell's
     * default style)
     */
    public void addCell( int nRow, int nColumn, Object oValue, CellStyle style ) {
        if( nRow < 0 ) {
            nRow = nCurrentRow;
        }
        Row row = sheet.getRow( nRow );
        if( row == null ) {
            row = sheet.createRow( nRow );
        }
        if( nColumn < 0 ) {
            nColumn = (int) row.getLastCellNum();
            if( nColumn < 0 ) {
                nColumn = 0;
            }
        }
        Cell cell = row.createCell( nColumn );
        if( oValue != null ) {
            switch( oValue.getClass().getSimpleName() ) {
                case "Byte":
                    cell.setCellValue( (byte) oValue );
                    break;
                case "Short":
                    cell.setCellValue( (short) oValue );
                    break;
                case "Integer":
                    cell.setCellValue( (int) oValue );
                    break;
                case "Long":
                    cell.setCellValue( (long) oValue );
                    break;
                case "Float":
                    cell.setCellValue( (float) oValue );
                    break;
                case "Double":
                    cell.setCellValue( (double) oValue );
                    break;
                case "Character":
                    if( Character.toString( (char) oValue ).trim().length() == 0 ) {
                        cell.setCellType( Cell.CELL_TYPE_BLANK );
                    } else {
                        cell.setCellType( Cell.CELL_TYPE_STRING );
                        cell.setCellValue( Character.toString( (char) oValue ) );
                    }
                    break;
                case "String":
                    if( ((String) oValue).trim().length() == 0 ) {
                        cell.setCellType( Cell.CELL_TYPE_BLANK );
                    } else {
                        cell.setCellValue( (String) oValue );
                    }
                    break;
                case "Boolean":
                    cell.setCellValue( (boolean) oValue );
                    break;
                case "Date":
                    cell.setCellValue( (Date) oValue );
                    break;
                default:
                    cell.setCellType( Cell.CELL_TYPE_BLANK );
            }
        } else {
            cell.setCellType( Cell.CELL_TYPE_BLANK );
        }
        if( style != null ) {
            cell.setCellStyle( style );
        }
        nCurrentRow = nRow;
    }

    /**
     * Sets the column size to the specified width or to autosize. This should
     * be used after the data has been written.
     *
     * @param nColumn Column number
     * @param nWidth Width (see Excel width), if 0 then it will autosize column
     * depending on the data
     * @param bMergedCells If true it will use merged cells to calculate size
     */
    public void setCoulmnSize( int nColumn, double nWidth, boolean bMergedCells ) {
        if( nWidth <= 0.0 ) {
            sheet.autoSizeColumn( nColumn, bMergedCells );
        } else {
            sheet.setColumnWidth( nColumn, (int) ((nWidth * 256) + 200) );
        }
    }

    /**
     * Merges a range of cells
     *
     * @param nRowStart Starting row index (can use -1 to indicate to use the
     * current row index value)
     * @param nRowEnd Ending row index that must be greater than or equal to the
     * starting row index (can use -1 to indicate to use the current row index
     * value)
     * @param nColumnStart Starting column index
     * @param nColumnEnd Ending column index that must be greater than or equal
     * to the starting column index
     */
    public void mergeCells( int nRowStart, int nRowEnd, int nColumnStart, int nColumnEnd ) {
        mergeCells( nRowStart, nRowEnd, nColumnStart, nColumnEnd,
                CellStyle.BORDER_NONE, CellStyle.BORDER_NONE, CellStyle.BORDER_NONE, CellStyle.BORDER_NONE );
    }

    /**
     * Merges a range of cells and adds a border to it
     *
     * @param nRowStart Starting row index (can use -1 to indicate to use the
     * current row index value)
     * @param nRowEnd Ending row index that must be greater than or equal to the
     * starting row index (can use -1 to indicate to use the current row index
     * value)
     * @param nColumnStart Starting column index
     * @param nColumnEnd Ending column index that must be greater than or equal
     * to the starting column index
     * @param nBorderLeft Left border type (use CellStyle's built-in values)
     * @param nBorderTop Top border type (use CellStyle's built-in values)
     * @param nBorderRight Right border type (use CellStyle's built-in values)
     * @param nBorderBottom Bottom border type (use CellStyle's built-in values)
     */
    public void mergeCells( int nRowStart, int nRowEnd, int nColumnStart, int nColumnEnd,
            short nBorderLeft, short nBorderTop, short nBorderRight, short nBorderBottom ) {
        if( nRowStart < 0 ) {
            nRowStart = nCurrentRow;
        }
        if( nRowEnd < 0 ) {
            nRowEnd = nCurrentRow;
        }
        CellRangeAddress region = new CellRangeAddress( nRowStart, nRowEnd, nColumnStart, nColumnEnd );
        RegionUtil.setBorderLeft( nBorderLeft, region, sheet, workbook );
        RegionUtil.setBorderTop( nBorderTop, region, sheet, workbook );
        RegionUtil.setBorderRight( nBorderRight, region, sheet, workbook );
        RegionUtil.setBorderBottom( nBorderBottom, region, sheet, workbook );
        sheet.addMergedRegion( region );
    }

    /**
     * Adds a Font to the Map of Font using the workbook's default font
     *
     * @param sID ID of the font
     */
    public void addFont( String sID ) {
        addFont( sID, null, (short) 0, false, false, Font.U_NONE, false, Font.SS_NONE );
    }

    /**
     * Adds a Font to the Map of Font
     *
     * @param sID ID of the font
     * @param sFontName Name of the font (e.g. "Arial")
     * @param nHeight Font size (in points)
     */
    public void addFont( String sID, String sFontName, short nHeight ) {
        addFont( sID, sFontName, nHeight, false, false, Font.U_NONE, false, Font.SS_NONE );
    }

    /**
     * Adds a Font to the Map of Font
     *
     * @param sID ID of the font
     * @param sFontName Name of the font (e.g. "Arial")
     * @param nHeight Font size (in points)
     * @param bBold Sets bold
     */
    public void addFont( String sID, String sFontName, short nHeight, boolean bBold ) {
        addFont( sID, sFontName, nHeight, bBold, false, Font.U_NONE, false, Font.SS_NONE );
    }

    /**
     * Adds a Font to the Map of Font
     *
     * @param sID ID of the font
     * @param sFontName Name of the font (e.g. "Arial")
     * @param nHeight Font size (in points)
     * @param bBold Sets bold
     * @param bItalic Sets italics
     * @param nUnderline Sets number of lines in underlining (use Font built-in
     * value)
     * @param bStrikeout Sets strikeout
     * @param nTypeOffset Sets type of offset to use (use Font built-in value)
     */
    public void addFont( String sID, String sFontName, short nHeight, boolean bBold,
            boolean bItalic, byte nUnderline, boolean bStrikeout, short nTypeOffset ) {
        Font font = workbook.createFont();
        if( sFontName != null ) {
            font.setFontName( sFontName );
        }
        if( nHeight > 0 ) {
            font.setFontHeightInPoints( nHeight );
        }
        font.setBold( bBold );
        font.setItalic( bItalic );
        font.setUnderline( nUnderline );
        font.setStrikeout( bStrikeout );
        font.setTypeOffset( nTypeOffset );
        mFonts.put( sID, font );
    }

    /**
     * Adds a CellStyle to the Map of CellStyle with the workbook's default
     * style
     *
     * @param sID ID of the style
     * @param font Font to add to the style (can be null for workbook's default
     * font)
     */
    public void addStyle( String sID, Font font ) {
        addStyle( sID, (short) -1, (short) -1, false, font,
                CellStyle.BORDER_NONE, CellStyle.BORDER_NONE, CellStyle.BORDER_NONE, CellStyle.BORDER_NONE );
    }

    /**
     * Adds a CellStyle to the Map of CellStyle
     *
     * @param sID ID of the style
     * @param nhAlignment Horizontal alignment (use CellStyle built-in values)
     * @param nvAlignment Vertical alignment (use CellStyle built-in values)
     * @param bWrapText Sets text wrapping
     * @param font Font to add to the style (can be null for workbook's default
     * font)
     */
    public void addStyle( String sID, short nhAlignment, short nvAlignment, boolean bWrapText, Font font ) {
        addStyle( sID, nhAlignment, nvAlignment, bWrapText, font,
                CellStyle.BORDER_NONE, CellStyle.BORDER_NONE, CellStyle.BORDER_NONE, CellStyle.BORDER_NONE );
    }

    /**
     * Adds a CellStyle to the Map of CellStyle
     *
     * @param sID ID of the style
     * @param nhAlignment Horizontal alignment (use CellStyle built-in values)
     * @param nvAlignment Vertical alignment (use CellStyle built-in values)
     * @param bWrapText Sets text wrapping
     * @param font Font to add to the style (can be null for workbook's default
     * font)
     * @param nBorderLeft Sets left border type (use CellStyle built-in values)
     * @param nBorderTop Sets top border type (use CellStyle built-in values)
     * @param nBorderRight Sets right border type (use CellStyle built-in
     * values)
     * @param nBorderBottom Sets bottom border type (use CellStyle built-in
     * values)
     */
    public void addStyle( String sID, short nhAlignment, short nvAlignment, boolean bWrapText, Font font,
            short nBorderLeft, short nBorderTop, short nBorderRight, short nBorderBottom ) {
        CellStyle style = workbook.createCellStyle();
        if( nhAlignment > -1 ) {
            style.setAlignment( nhAlignment );
        }
        if( nhAlignment > -1 ) {
            style.setVerticalAlignment( nvAlignment );
        }
        style.setWrapText( bWrapText );
        if( font != null ) {
            style.setFont( font );
        }
        style.setBorderLeft( nBorderLeft );
        style.setBorderTop( nBorderTop );
        style.setBorderRight( nBorderRight );
        style.setBorderBottom( nBorderBottom );
        mStyles.put( sID, style );
    }

    /**
     * Adds a data format to select style
     *
     * @param style CellStyle to add the formatting
     * @param sFormat Format pattern
     */
    public void addDataFormat( CellStyle style, String sFormat ) {
        style.setDataFormat( (workbook.getCreationHelper()).createDataFormat().getFormat( sFormat ) );
    }

    /**
     * Adds a data format to select style
     *
     * @param style CellStyle to add the formatting
     * @param locale Locale to use for the Date format
     * @param sFormat Format pattern
     */
    public void addDataFormat( CellStyle style, Locale locale, String sFormat ) {
        String sExcelFormat = DateFormatConverter.convert( locale, sFormat );
        addDataFormat( style, sExcelFormat );
    }

    /**
     * Finds a font in the map of fonts by its ID
     *
     * @param sID Font ID
     * @return Font or null if not found
     */
    public Font findFont( String sID ) {
        return mFonts.get( sID );
    }

    /**
     * Finds a style in the map of styles by its ID
     *
     * @param sID Style ID
     * @return CellStyle or null if not found
     */
    public CellStyle findStyle( String sID ) {
        return mStyles.get( sID );
    }

    /**
     * Obtains the currently selected sheet
     *
     * @return Sheet
     */
    public Sheet getSheet() {
        return sheet;
    }

    /**
     * Obtains the currently selected workbook
     *
     * @return Workbook
     */
    public Workbook getWorkbook() {
        return workbook;
    }

    /**
     * Increments the global row index by 1
     */
    public void addRowCount() {
        nCurrentRow++;
    }

    /**
     * Obtains the global row index
     *
     * @return
     */
    public int getCurrentRow() {
        return nCurrentRow;
    }

    /**
     * Sets a new value to the global row index
     *
     * @param nCurrentRow Amount to set the global row index to
     */
    public void setCurrentRow( int nCurrentRow ) {
        this.nCurrentRow = nCurrentRow;
    }

    /**
     * Obtains the temporary Excel file
     *
     * @return Temporary File or null if it doesn't exist
     */
    public File getTempFile() {
        return fTempFile;
    }

    /**
     * Obtains the Excel file MIME Type which is dependant on whether it used
     * XSSF or HSSF
     *
     * @return String specifying the MIME Type
     */
    public String getMIMEType() {
        return sMIME;
    }

    /**
     * Obtains the Excel file's extension which is dependant on whether it used
     * XSSF or HSSF
     *
     * @return
     */
    public String getExtension() {
        return sExtension;
    }

    /**
     * Obtains the temporary file's name once created
     *
     * @return
     */
    public String getFileName() {
        return sFileName;
    }
}
