package mx.gob.hrrb.jbs.archivo;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.archivo.PrestamoExp;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Paciente;
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
@ManagedBean(name="oExpDev")
@ViewScoped
public class ExpDevueltosJB {
    private Date dFechaIni;
    private Date dFechaFin;
    private int nNum;
    private PrestamoExp[] oListaExpDevPorFecha;
    private PrestamoExp[] oListaExpDevPorRangoFechas;
    private PrestamoExp[] oListaExpDevPorExp;
    private PrestamoExp oPresExp;
    
    public ExpDevueltosJB(){
       oListaExpDevPorFecha= null;
       oListaExpDevPorRangoFechas= null;
       oListaExpDevPorExp=null;
    }
    
    public void buscaExpDevPorFecha(){
        String msg="";
        if(dFechaIni!=null){
            try{
                oPresExp=new PrestamoExp();
                oListaExpDevPorFecha=oPresExp.buscaExpedientesDevueltosPorCriterio(dFechaIni, null,0);
            }catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msg="error al buscar expedientes devueltos por fecha";
                context.addMessage(null, new FacesMessage("Expedientes devueltos", msg));
            }
            
        }else{
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Expedientes devueltos"," No ha especificado la fecha"));
        }
    }
    
    public void buscaExpDevPorRangoFechas(){
        String msg="";
        if(dFechaIni!=null && dFechaFin!=null){
            try{
                oPresExp=new PrestamoExp();
                oListaExpDevPorRangoFechas=oPresExp.buscaExpedientesDevueltosPorCriterio(dFechaIni, dFechaFin,0);
            }catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msg="error al buscar expedientes devueltos por rango de fechas";
                context.addMessage(null, new FacesMessage("Expedientes devueltos", msg));
            }
        }else{
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Expedientes devueltos "," No ha especificado fecha"));
        }
    }
    
    public void buscaExpDevPorExp(){
        String msg="";
        if(getNum()!=0){
            try{
                oPresExp=new PrestamoExp();
                oListaExpDevPorExp=oPresExp.buscaExpedientesDevueltosPorCriterio(null, null,getNum());
            }catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msg="error al buscar expediente devuelto";
                context.addMessage(null, new FacesMessage("Expedientes devueltos", msg));
            }
        }else{
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Expedientes devueltos"," No ha especificado el número de expediente"));
        }
    }
    
    public PrestamoExp[] getListaDevExpPorFecha(){
        return oListaExpDevPorFecha;
    }
    
    public PrestamoExp[] getListaDevExpPorRangoFechas(){
        return oListaExpDevPorRangoFechas;
    }
    
    public PrestamoExp[] getListaDevExpPorExp(){
        return oListaExpDevPorExp;
    }
    
    public List<String> getListaDevocionServicioExp(){
        List<AreaServicioHRRB> lLista = null;
        List<String> lLista2=null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList((new PrestamoExp().getSerUbicacion().buscaServiciohrrbPrestamoExp())));
           lLista2=new ArrayList<String>();
           for(int i=0;i<lLista.size();i++){
               lLista2.add(lLista.get(i).getDescripcion());
           }
       } catch (Exception ex) {
           ex.getMessage();
           Logger.getLogger(ExpPrestadosJB.class.getName()).log(Level.SEVERE, null, ex);
       }
       return lLista2;
    }
    
    //*************************************************************************
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"ExpDevueltos "+getFechaArch1()); //nombre de la hoja de excel
        sheet.shiftRows (0, 63768, +3);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)8));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("DESGLOCE DE EXPEDIENTES DEVUELTOS "+getFechaArch1());
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)8));
                
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA IMPRESIÓN: "+obtenFechaAux());
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)8));
        
        
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
        sheet.autoSizeColumn(8);
        
        
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
        wb.setSheetName(0,"ExpDevueltos"+getFechaArch1()+" "+getFechaArch2()); //nombre de la hoja de excel
        sheet.shiftRows (0, 63768, +3);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)8));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("DESGLOCE DE EXPEDIENTES DEVUELTOS DEL "+getFechaArch1()+" AL "+getFechaArch2());
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)8));
                
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA IMPRESIÓN: "+obtenFechaAux());
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)8));
        
        
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
        sheet.autoSizeColumn(8);
        
        
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
    /**
     * @return the dFechaIni
     */
    public Date getFechaIni() {
        return dFechaIni;
    }

    /**
     * @param dFechaIni the dFechaIni to set
     */
    public void setFechaIni(Date dFechaIni) {
        this.dFechaIni = dFechaIni;
    }

    /**
     * @return the dFechaFin
     */
    public Date getFechaFin() {
        return dFechaFin;
    }

    /**
     * @param dFechaFin the dFechaFin to set
     */
    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }
    
    public PrestamoExp getPresExp(){
        return oPresExp;
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
    
    /*Para exportar archivos*/
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String sTit="DESGLOCE DE EXPEDIENTES DEVUELTOS";
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();
        pdf.add(new Phrase(sTit+"\nFecha: " + formato.format(new Date())+"\n"));
        //pdf.add(new Phrase("Total Pagos de Préstamos: $ " +getTotalPagosPrestamos()+"\n"));
        //pdf.add(new Phrase("\t \t \t \t Hora \t Folio \t\t\t\t\t\t\t Folio del Personal \t\t\t\t\t\t\t\t\t\t Nombre Empleado \t Total del Préstamo \t Fecha del Préstamo  \t Folio del Préstamo \t Pagos Realizados \t Condición \t Pago del Dia \t Saldo por Pagar \t Forma de Pago"));
        //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
    }

    /**
     * @return the nNum
     */
    public int getNum() {
        return nNum;
    }

    /**
     * @param nNum the nNum to set
     */
    public void setNum(int nNum) {
        this.nNum = nNum;
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