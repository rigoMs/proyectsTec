package mx.gob.hrrb.jbs.archivo;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.primefaces.context.RequestContext;


/**
 *
 * @author JDanny
 */
@ManagedBean (name="oRptHosp")
@ViewScoped
public class RptEgreHosExpPresJB {
    private Hospitalizacion oHosp;
    private Date dFechaIni;
    private Date dFechaFin;
    private Hospitalizacion[] oListaHospitalizacionesXfecha;
    private Hospitalizacion[] oListaHospitalizacionesXRango;
    
    public RptEgreHosExpPresJB(){
        oListaHospitalizacionesXfecha=null;
        oListaHospitalizacionesXRango=null;
    }
    
    public void buscaEgresosPorFecha(){
        String msgs="";
        if(dFechaIni!=null){
            try{
                oHosp= new Hospitalizacion();
                oListaHospitalizacionesXfecha=
                        oHosp.buscaEgresosHospitalariosPorFecha(dFechaIni, null);
            }
            catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msgs="Error al buscar egresos hospitalarios por fecha";
                context.addMessage(null, new FacesMessage("Egresos hospitalarios",msgs));
            }
        }
        else{
            FacesContext context=FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Egresos hospitalarios"," No ha especificado la fecha"));
        }
    }
    
    public void buscaEgresosPorRango(){
        String msgs="";
        if(dFechaIni!=null && dFechaFin!=null){
            try{
                oHosp= new Hospitalizacion();
                oListaHospitalizacionesXRango=oHosp.buscaEgresosHospitalariosPorFecha(dFechaIni, dFechaFin);
            }
            catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msgs="Error al buscar egresos hospitalarios por rango de fechas";
                context.addMessage(null, new FacesMessage("Egresos hospitalarios",msgs));
            }
        }
        else{
            FacesContext context=FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Egresos hospitalarios"," No ha especificado fecha"));
        }
    }
    
    public void registrarEntregaCODE(long folpac, long claveepi, int numingresoHosp, int numexp, boolean b){
        String msgs;
        oHosp= new Hospitalizacion();
        //System.out.println( folpac+ " "+claveepi+ " "+ numingresoHosp+ " "+numexp);
        if(folpac!=0 && claveepi!=0 && numingresoHosp!=0 && numexp!=0){
            try{
               oHosp.registraEntregaCODE(folpac, claveepi, numingresoHosp, numexp);
            }catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msgs="Error al registrar devolución de CODE";
                context.addMessage(null, new FacesMessage("Egresos hospitalarios",msgs));
            }
        }else{
            FacesContext context=FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Egresos hospitalarios"," faltan datos para realizar el registro"));
        }
        try{
        if(b==true)
            oListaHospitalizacionesXfecha=oHosp.buscaEgresosHospitalariosPorFecha(getFechaIni(), null);
        else
            oListaHospitalizacionesXRango=oHosp.buscaEgresosHospitalariosPorFecha(getFechaIni(), getFechaFin());
        }catch(Exception e){    e.toString();    }
        
        //RequestContext.getCurrentInstance().update("frmInicio");
        //RequestContext.getCurrentInstance().update("frmImpresion");
    }
        
    public void validaFecha(){
        String mess="";
        if(getFechaIni()!=null && getFechaFin()!=null)
            if(getFechaIni().compareTo(getFechaFin())>0)
                mess="La fecha final del periodo debe ser posterior a la fecha de inicio";
        if(!mess.equals("")){
            FacesContext context=FacesContext.getCurrentInstance();
            context.addMessage(null,new FacesMessage("Aviso",mess));
        }
    }
    
    //*************************************************************************
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"Rpt_CodesFirmadas "+getFechaArch1()); //nombre de la hoja de excel
        sheet.shiftRows (0, 63768, +4);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)10));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("EGRESOS HOSPITALARIOS "+getFechaArch1());
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)10));
        
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("(Administración de CODES)");
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)10));
                
        row = sheet.createRow((short)3);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA IMPRESIÓN: "+obtenFechaAux());
        sheet.addMergedRegion(new Region(3,(short)0, 3, (short)10));
        
        
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
        
        //sheet.autoSizeColumn(0);
        sheet.setColumnWidth(0, 4200);
        sheet.autoSizeColumn(1);
        sheet.setColumnWidth(2, 4200);
        sheet.setColumnWidth(3, 3500);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.setColumnWidth(7, 4700);
        sheet.setColumnWidth(8, 4700);
        sheet.autoSizeColumn(9);
        sheet.setColumnWidth(10, 4700);
        
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleTit);
        }
        header = sheet.getRow(1);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(2);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(3);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit2);
        }
        
        header = sheet.getRow(4);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
    }
    //*************************************************************************
    public void postProcessXLS2(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"CODES "+getFechaArch1()+" al "+getFechaArch2()); //nombre de la hoja de excel
        sheet.shiftRows (0, 63768, +4);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)10));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("EGRESOS HOSPITALARIOS DEL "+getFechaArch1()+" AL "+getFechaArch2());
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)10));
        
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("(Administración de CODES)");
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)10));
                
        row = sheet.createRow((short)3);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA IMPRESIÓN: "+obtenFechaAux());
        sheet.addMergedRegion(new Region(3,(short)0, 3, (short)10));
        
        
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
        
        //sheet.autoSizeColumn(0);
        sheet.setColumnWidth(0, 4200);
        sheet.autoSizeColumn(1);
        sheet.setColumnWidth(2, 4200);
        sheet.setColumnWidth(3, 3500);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.setColumnWidth(7, 4700);
        sheet.setColumnWidth(8, 4700);
        sheet.autoSizeColumn(9);
        sheet.setColumnWidth(10, 4700);
        
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleTit);
        }
        header = sheet.getRow(1);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(2);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(3);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit2);
        }
        
        header = sheet.getRow(4);
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

    public Hospitalizacion[] getListaHospitalizacionesXfecha() {
        return oListaHospitalizacionesXfecha;
    }

    public Hospitalizacion[] getListaHospitalizacionesXRango() {
        return oListaHospitalizacionesXRango;
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
