package mx.gob.hrrb.jbs.archivo;
     
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;


/**
 *
 * @author JDanny
 */
@ManagedBean (name="oRptProg")
@ViewScoped
public class RptProgQuirurgicaJB {
    private Date dFechaIni;
    private Date dFechaFin;
    private ProcedimientosRealizados oProReal;
    private ProcedimientosRealizados[] oListaProRealPorFecha;
    private ProcedimientosRealizados[] oListaProRealPorRangoFecha;
    
    
    public RptProgQuirurgicaJB(){
        oListaProRealPorFecha=null;
        oListaProRealPorRangoFecha=null;
    }
    
    public void buscaCirugiasProgramadasPorFecha(){
        String msgs="";
        if(dFechaIni!=null){
            try{
                oProReal= new ProcedimientosRealizados();
                oProReal.inicializar();
                oListaProRealPorFecha=
                        oProReal.buscaProcedimientosProgramadosPorFecha(
                                dFechaIni, null);
            }
            catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msgs="error al buscar cirugías programadas por fecha";
                context.addMessage(null, new FacesMessage("Procedimientos programados",msgs));
            }
        }
        else{
            FacesContext context=FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Procedimientos programados"," No ha especificado la fecha"));
        }
    }
    
    public void buscaCirugiasProgramadasPorRangoFecha(){
        String msgs="";
        if(dFechaIni!=null && dFechaFin!=null){
            try{
                oProReal= new ProcedimientosRealizados();
                oProReal.inicializar();
                oListaProRealPorRangoFecha=oProReal.buscaProcedimientosProgramadosPorFecha(dFechaIni, dFechaFin);
            }
            catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msgs="error al buscar cirugías programadas por rango de fechas";
                context.addMessage(null, new FacesMessage("Procedimientos programados",msgs));
            }
        }
        else{
            FacesContext context=FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Procedimientos programados"," No ha especificado fecha"));
        }
    }
    
    public Date getFechaIni() {
        return dFechaIni;
    }
    public void setFechaIni(Date dFechaIni) {
        this.dFechaIni = dFechaIni;
    }

    public Date getFechaFin() {
        return dFechaFin;
    }
    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

    public ProcedimientosRealizados getProReal() {
        return oProReal;
    }
    public void setProReal(ProcedimientosRealizados oProReal) {
        this.oProReal = oProReal;
    }

    public ProcedimientosRealizados[] getListaProRealPorFecha() {
        return oListaProRealPorFecha;
    }

    public ProcedimientosRealizados[] getListaProReaPorRangoFecha() {
        return oListaProRealPorRangoFecha;
    }
    
    public void validaFecha(){
        String mess="";
        if (dFechaIni !=null && dFechaFin!=null)
            if (dFechaIni.compareTo(dFechaFin)>0)
                mess="La fecha final del periodo debe ser posterior a la fecha de inicio";
        if (!mess.equals("")){
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Aviso",mess));
        }
    }
    
    //*************************************************************************
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"Programaciones Quirúrgicas "); //nombre de la hoja de excel
        sheet.shiftRows (0, 63768, +3);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)7));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("DESGLOCE DE PROGRAMACIONES QUIRÚRGICAS "+getFechaArch1());
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)7));
                
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA IMPRESIÓN: "+obtenFechaAux());
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)7));
        
        
        HSSFFont font = wb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        
        HSSFFont fontTit = wb.createFont();
        fontTit.setFontName(HSSFFont.FONT_ARIAL);
        fontTit.setFontHeightInPoints((short) 16);
        fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontTit.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle cellStyleTit = wb.createCellStyle();         
        cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleTit.setFont(fontTit);
        
        HSSFCellStyle cellStyleSubTit = wb.createCellStyle();         
        cellStyleSubTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleSubTit.setFont(font);
        
        HSSFCellStyle cellStyleSubTit2 = wb.createCellStyle();         
        cellStyleSubTit2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyleSubTit2.setFont(font);
        
        HSSFRow header;
        HSSFCellStyle cellStyle = wb.createCellStyle(); 
        //cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);        
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);
        
        header = sheet.getRow(0);
        
        //sheet.autoSizeColumn(0);setColumnWidth(0,4200)
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);       
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleTit);
        }
        header = sheet.getRow(1);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(2);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit2);
        }
        
        header = sheet.getRow(3);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
    }
    
    //*************************************************************************
    public void postProcessXLS2(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"Rpt_Programaciones_quirúrgicas"); //nombre de la hoja de excel
        sheet.shiftRows (0, 63768, +3);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)7));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("DESGLOCE DE PROGRAMACIONES QUIRÚRGICAS DEL "+getFechaArch1()+" AL "+getFechaArch2());
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)7));
                
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA IMPRESIÓN: "+obtenFechaAux());
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)7));
        
        
        HSSFFont font = wb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        
        HSSFFont fontTit = wb.createFont();
        fontTit.setFontName(HSSFFont.FONT_ARIAL);
        fontTit.setFontHeightInPoints((short) 16);
        fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontTit.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle cellStyleTit = wb.createCellStyle();         
        cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleTit.setFont(fontTit);
        
        HSSFCellStyle cellStyleSubTit = wb.createCellStyle();         
        cellStyleSubTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleSubTit.setFont(font);
        
        HSSFCellStyle cellStyleSubTit2 = wb.createCellStyle();         
        cellStyleSubTit2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyleSubTit2.setFont(font);
        
        HSSFRow header;
        HSSFCellStyle cellStyle = wb.createCellStyle(); 
        //cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);        
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);
        
        header = sheet.getRow(0);
        
        //sheet.autoSizeColumn(0);setColumnWidth(0,4200)
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);       
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleTit);
        }
        header = sheet.getRow(1);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(2);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit2);
        }
        
        header = sheet.getRow(3);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
    }
    
    public String obtenFechaAux(){
        String f="";
        SimpleDateFormat fechaHora=new SimpleDateFormat("dd/MM/yyyy HH:mm");      
        f = fechaHora.format(new Date());
        return f;
    }
    
    public String getFechaArch1() {
        String f="";
        SimpleDateFormat fechaHora=new SimpleDateFormat("dd-MM-yyyy");      
        f = fechaHora.format(getFechaIni());
        return f;
    }
    
    public String getFechaArch2() {
        String f="";
        SimpleDateFormat fechaHora=new SimpleDateFormat("dd-MM-yyyy");      
        f = fechaHora.format(getFechaFin());
        return f;
    }
}