 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
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
 * @author Betia Ochoa
 */

@ManagedBean(name = "oRepAdmHosp")
@SessionScoped
//@ViewScoped

public class ReporteAdmisionHosp{
    private AdmisionUrgs oAdmisionUrgs;
    
    private List<AdmisionUrgs> lAdmision;
    private String activaTabla;
    private Date dFechaInicial;
    private Date dFechaFinal;
    private String sFechaI;
    private String sFechaF;
    private String fechaHoy;
    
    public ReporteAdmisionHosp() throws Exception{
        activaTabla="display: none;";
        lAdmision=null;
        dFechaInicial=null;
        dFechaFinal=null;
        oAdmisionUrgs= new AdmisionUrgs();
        setFechaHoy(oAdmisionUrgs.fechaActual());
    }
     
    /**
     * @return the oAdmisionUrgs
     */
    public AdmisionUrgs getAdmisionUrgs() {
        return oAdmisionUrgs;
    }

    /**
     * @param oAdmisionUrgs the oAdmisionUrgs to set
     */
    public void setAdmisionUrgs(AdmisionUrgs oAdmisionUrgs) {
        this.oAdmisionUrgs = oAdmisionUrgs;
    }

    public void generar(){
        setActivaTabla("");
        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fF=new SimpleDateFormat("yyyy-MM-dd");
        
        setFechaI(fI.format(getFechaInicial()));
        setFechaF(fF.format(getFechaFinal()));
        
        try {
            setAdmision(oAdmisionUrgs.BuscaReporteAdmHosp(getFechaI(), getFechaF()));
        } catch (Exception ex) {
            Logger.getLogger(ReporteAdmisionHosp.class.getName()).log(Level.SEVERE, null, ex);
        }
        dFechaInicial=null;
        dFechaFinal=null;
    }

    
    public void postProcessXLS(Object document) {                  
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
                          wb.setSheetName(0,"Admision Hosp."); //nombre de la hoja de excel
                  sheet.shiftRows(0, 63768, +3);
                  
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        
        HSSFRow row2 = sheet.createRow((short)1);
        HSSFCell cell2 = row2.createCell((short)0);
       
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0, (short)0, 0, (short)14));
       
        cell2.setCellValue("ADMISIÓN HOSPITALARIA                                 EMISIÓN: "+getFechaI()+" AL "+getFechaF());
        sheet.addMergedRegion(new Region(1, (short) 0, 1, (short)14));
        
        HSSFCellStyle style= wb.createCellStyle();                  
        HSSFFont font = wb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 200);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index); 
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        HSSFRow header = sheet.getRow(0);
        HSSFRow header2 = sheet.getRow(1);
                
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(style);
            header2.getCell(i).setCellStyle(style);
        }          
        
        HSSFCellStyle cellStyle = wb.createCellStyle(); 
        //cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);        
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
                       
        
        header = sheet.getRow(3);
        
        //sheet.setColumnWidth(0, 3500);
        //sheet.setColumnWidth(1, 3500);
        //sheet.setColumnWidth(2, 3500);
        //sheet.setColumnWidth(3, 4200);
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.setColumnWidth(2, 7100);
        sheet.autoSizeColumn(3);
        sheet.setColumnWidth(4, 8000);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.setColumnWidth(8,3750);
        sheet.setColumnWidth(9,7100);
        sheet.autoSizeColumn(10);
        sheet.setColumnWidth(11, 8000);
        sheet.setColumnWidth(12, 3650);
        sheet.setColumnWidth(13,3750);
        sheet.setColumnWidth(14,4000);
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
        
        HSSFCellStyle stilo = wb.createCellStyle();         
        HSSFFont font2 = wb.createFont();
        font2.setFontName(HSSFFont.FONT_ARIAL);
        font2.setFontHeightInPoints((short) 1000);
        font2.setColor(HSSFColor.BLACK.index);
        
        font2.setFontHeightInPoints((short) 8);

        stilo.setFont(font2);
        
        stilo.setBorderBottom((short)1);
        stilo.setBorderTop((short)1);
        stilo.setBorderLeft((short)1);
        stilo.setBorderRight((short)1);        
        
        String valor="";
        HSSFRow fila = null;         
        for(int i=4; i < 63771; i++) {
            //header.getCell(i).setCellStyle(cellStyle);
            fila=sheet.getRow(i);
            for(int c=0; c < fila.getPhysicalNumberOfCells();c++) {
                fila.getCell(c).setCellStyle(stilo);
            }            
        }
       
    }
    
    /**
     * @return the lAdmision
     */
    public List<AdmisionUrgs> getAdmision() {
        return lAdmision;
    }

    /**
     * @param lAdmision the lAdmision to set
     */
    public void setAdmision(List<AdmisionUrgs> lAdmision) {
        this.lAdmision = lAdmision;
    }
    
    /**
     * @return the activaTabla
     */
    public String getActivaTabla() {
        return activaTabla;
    }

    /**
     * @param activaTabla the activaTabla to set
     */
    public void setActivaTabla(String activaTabla) {
        this.activaTabla = activaTabla;
    }
   
    /**
     * @return the dFechaInicial
     */
    public Date getFechaInicial() {
        return dFechaInicial;
    }

    /**
     * @param dFechaInicial the dFechaInicial to set
     */
    public void setFechaInicial(Date dFechaInicial) {
        this.dFechaInicial = dFechaInicial;
    }

    /**
     * @return the dFechaFinal
     */
    public Date getFechaFinal() {
        return dFechaFinal;
    }

    /**
     * @param dFechaFinal the dFechaFinal to set
     */
    public void setFechaFinal(Date dFechaFinal) {
        this.dFechaFinal = dFechaFinal;
    }

    /**
     * @return the sFechaI
     */
    public String getFechaI() {
        return sFechaI;
    }

    /**
     * @param sFechaI the sFechaI to set
     */
    public void setFechaI(String sFechaI) {
        this.sFechaI = sFechaI;
    }

    /**
     * @return the sFechaF
     */
    public String getFechaF() {
        return sFechaF;
    }

    /**
     * @param sFechaF the sFechaF to set
     */
    public void setFechaF(String sFechaF) {
        this.sFechaF = sFechaF;
    }

    /**
     * @return the fechaHoy
     */
    public String getFechaHoy() {
        setActivaTabla("display: none;");
        return fechaHoy;
    }

    /**
     * @param fechaHoy the fechaHoy to set
     */
    public void setFechaHoy(String fechaHoy) {
        this.fechaHoy = fechaHoy;
    }
}