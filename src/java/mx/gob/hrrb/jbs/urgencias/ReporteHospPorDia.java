 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
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

@ManagedBean(name = "oRepHospiDia") 
@SessionScoped
//@ViewScoped

public class ReporteHospPorDia{
    private AdmisionUrgs oAdmisionUrgs;
    
    private List<AdmisionUrgs> lAdmision;
    private String activaTabla;
    private Date dFechaInicial;
    private Date dFechaFinal;
    private String sFechaI;
    private String fechaHoy;
    private int nArea;
    private String dFechaRep;
    private String sAreaEmision;
    private AreaServicioHRRB oAreaServicio;
    
    public ReporteHospPorDia() throws Exception{
        activaTabla="display: none;";
        nArea=0;
        lAdmision=null;
        oAreaServicio= new AreaServicioHRRB();
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
        
        setFechaI(fI.format(getFechaInicial()));
        
        try {
            setAdmision(oAdmisionUrgs.buscareportehospipordia(getArea(),getFechaI()));
           setAreaEmision(oAreaServicio.buscarNombreArea(getArea()));
            
        } catch (Exception ex) {
            Logger.getLogger(ReporteHospPorDia.class.getName()).log(Level.SEVERE, null, ex);
        }
        dFechaInicial=null;
        dFechaFinal=null;
    }
    
    public void postProcessXLS(Object document) {                  
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
                          wb.setSheetName(0,getAreaEmision()); //nombre de la hoja de excel
                  sheet.shiftRows(0, 63768, +4);
                  
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        
        HSSFRow row2 = sheet.createRow((short)1);
        HSSFCell cell2 = row2.createCell((short)0);
       
        HSSFRow row3 = sheet.createRow((short)2);
        HSSFCell cell3 = row3.createCell((short)0);
        
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0, (short)0, 0, (short)12));
       
        SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy");
                            dFechaRep=(fechaIng.format(getFechaInicial()));
                            setFechaRep(dFechaRep);
        
        cell2.setCellValue("HOSPITALIZACIÓN POR DÍA");
        sheet.addMergedRegion(new Region(1, (short) 0, 1, (short)12));
        
        cell3.setCellValue("SERVICIO: "+getAreaEmision()+"                   FECHA DE EMISIÓN: "+getFechaRep());
        sheet.addMergedRegion(new Region(2, (short) 0, 2, (short)12));
        
        
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
        HSSFRow header3 = sheet.getRow(2);
                
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(style);
            header2.getCell(i).setCellStyle(style);
            header3.getCell(i).setCellStyle(style);
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
                       
        
        header = sheet.getRow(4);
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.setColumnWidth(3, 8000);
        sheet.autoSizeColumn(4);
        sheet.setColumnWidth(5, 9500);
        sheet.setColumnWidth(6, 5000);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.setColumnWidth(9, 8000);
        sheet.autoSizeColumn(10);
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
        
        font2.setFontHeightInPoints((short) 8);

        stilo.setFont(font2);
        
        stilo.setBorderBottom((short)1);
        stilo.setBorderTop((short)1);
        stilo.setBorderLeft((short)1);
        stilo.setBorderRight((short)1);        
        
        String valor="";
        HSSFRow fila = null;         
        for(int i=5; i < 63771; i++) {
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
    
    //Retorna lista de areas para el reporte de emision de estados de salud
     public List<AreaServicioHRRB> getListaAreasHospi(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasRepHospDia()));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }

    /**
     * @return the nArea
     */
    public int getArea() {
        return nArea;
    }

    /**
     * @param nArea the nArea to set
     */
    public void setArea(int nArea) {
        this.nArea = nArea;
    }

    /**
     * @return the dFechaRep
     */
    public String getFechaRep() {
        return dFechaRep;
    }

    /**
     * @param dFechaRep the dFechaRep to set
     */
    public void setFechaRep(String dFechaRep) {
        this.dFechaRep = dFechaRep;
    }

    /**
     * @return the sAreaEmision
     */
    public String getAreaEmision() {
        return sAreaEmision;
    }

    /**
     * @param sAreaEmision the sAreaEmision to set
     */
    public void setAreaEmision(String sAreaEmision) {
        this.sAreaEmision = sAreaEmision;
    }

    /**
     * @return the oAreaServicio
     */
    public AreaServicioHRRB getAreaServicio() {
        return oAreaServicio;
    }

    /**
     * @param oAreaServicio the oAreaServicio to set
     */
    public void setAreaServicio(AreaServicioHRRB oAreaServicio) {
        this.oAreaServicio = oAreaServicio;
    }
}