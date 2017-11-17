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

@ManagedBean(name = "oRepAltas")
@SessionScoped
//@ViewScoped

public class ReporteAltasDiarias{
    private AdmisionUrgs oAdmisionUrgs;
    
    private List<AdmisionUrgs> lAdmision;
    private String activaTabla;
    private Date dFechaInicial;
    private String sFechaI;
    private String fechaHoy;
    
    public ReporteAltasDiarias() throws Exception{
        activaTabla="display: none;";
        lAdmision=null;
        dFechaInicial=null;
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
        
        setFechaI(fI.format(getFechaInicial()));
        
        try {
            setAdmision(oAdmisionUrgs.BuscaReporteAltasDiarias(getFechaI()));
        } catch (Exception ex) {
            Logger.getLogger(ReporteAltasDiarias.class.getName()).log(Level.SEVERE, null, ex);
        }
        dFechaInicial=null;
    }

    
    public void postProcessXLS(Object document) {                  
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
                          wb.setSheetName(0,"Altas."); //nombre de la hoja de excel
                  sheet.shiftRows(0, 63768, +3);
                  
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        
        HSSFRow row2 = sheet.createRow((short)1);
        HSSFCell cell2 = row2.createCell((short)0);
       
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0, (short)0, 0, (short)12));
       
        cell2.setCellValue("REPORTE DIARIO DE ALTAS DE PACIENTES.            FECHA DE EMISIÓN: "+getFechaI());
        sheet.addMergedRegion(new Region(1, (short) 0, 1, (short)12));
        
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
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.setColumnWidth(5, 7100);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.setColumnWidth(8,9000);
        sheet.autoSizeColumn(9);
        sheet.setColumnWidth(10,3750);
        sheet.autoSizeColumn(11);
        sheet.autoSizeColumn(12);
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
        
        HSSFCellStyle stilo = wb.createCellStyle();         
        HSSFFont font2 = wb.createFont();
        font2.setFontName(HSSFFont.FONT_ARIAL);
        font2.setFontHeightInPoints((short) 1000);
        font2.setColor(HSSFColor.BLACK.index);
        
        font2.setFontHeightInPoints((short) 10);

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