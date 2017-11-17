/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
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
 * @author KarDan91
 */
@ManagedBean(name="oReporteHosp")
@ViewScoped
public class ReporteHospitalizacion {
    private Hospitalizacion oHosp;
    private Date dFechaIni;
    private Date dFechaFin;
    private List<Hospitalizacion> lHospitalizacion;
    private List<Hospitalizacion> lFiltroHospitalizacion;
    private String sFechaI;
    private String sFechaF;
    private AreaServicioHRRB oASerHRRB; 
    private int nTipo;
    /**
     * Creates a new instance of ReporteHospitalizacion
     */
    public ReporteHospitalizacion() {
        oHosp = new Hospitalizacion();
        oASerHRRB = new AreaServicioHRRB();
        dFechaIni = null;
        dFechaFin = null;
    }

    public String introduceTipo(int n){
        nTipo=n;
        String pag="/faces/sesiones/hospitalizacion/Reporte.xhtml";
        if (nTipo==1)
            pag="/faces/sesiones/hospitalizacion/Reporte.xhtml";        
        if (nTipo==2)
            pag="/faces/sesiones/hospitalizacion/ReporteArea.xhtml";      

        return pag;
    }
    
    
   public List<AreaServicioHRRB> getListaAreasServicio(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasServicioUsuario()));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }    
   
   public List<AreaServicioHRRB> getListaAreasServicioHosp(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasHospReporte()));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }     
    
    public void generar(){
        //Date fAltaHosp=(oEpisodioMedico.getAltaHospitalaria());

        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fF=new SimpleDateFormat("yyyy-MM-dd");
        
        setFechaI(fI.format(getFechaIni()));
        setFechaF(fF.format(getFechaFin()));
        
        System.out.println("Fecha Inicio: "+getFechaI());
        System.out.println("Fecha Fin: "+getFechaF());
        try {
            lHospitalizacion=oHosp.reporteHospitalizacion(getFechaI(), getFechaF(), oASerHRRB.getClave());
        } catch (Exception ex) {
            Logger.getLogger(ReporteHospitalizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void postProcessXLS(Object document) throws Exception {                  
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
                          wb.setSheetName(0,"Egresos Hospitalarios"); //nombre de la hoja de excel
                  sheet.shiftRows(0, 63768, +4);
                  
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        
        HSSFRow row2 = sheet.createRow((short)1);
        HSSFCell cell2 = row2.createCell((short)0);
        
        HSSFRow row3 = sheet.createRow((short)2);
        HSSFCell cell3 = row3.createCell((short)0);
        
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0, (short)0, 0, (short)13));
        
        String areaHRRB=oASerHRRB.buscar();
        
        cell2.setCellValue("SERVICIO: "+areaHRRB);
        sheet.addMergedRegion(new Region(1, (short)0, 1, (short)13));
        
        cell3.setCellValue("REPORTE DE EGRESOS HOSPITALARIOS. FECHA DE EMISIÓN: "+getFechaI()+" - "+getFechaF());
        sheet.addMergedRegion(new Region(2, (short) 0, 2, (short)13));
        
        HSSFFont fontTit = wb.createFont();
        fontTit.setFontName(HSSFFont.FONT_ARIAL);
        fontTit.setFontHeightInPoints((short) 16);
        fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontTit.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle cellStyleTit = wb.createCellStyle();         
        cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleTit.setFont(fontTit);
        
        HSSFCellStyle style= wb.createCellStyle();                  
        HSSFFont font = wb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 5000);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        HSSFRow header = sheet.getRow(0);    
        HSSFRow header2 = sheet.getRow(1);
        HSSFRow header3 = sheet.getRow(2);        
        //header.setRowStyle(style);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleTit);
        } 
                
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
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
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);
                       
        
        header = sheet.getRow(3);
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(11);
        sheet.autoSizeColumn(12);
        sheet.autoSizeColumn(13);
        /*for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }*/
        
        HSSFCellStyle stilo = wb.createCellStyle();         

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
                if(c==5){
                    valor=fila.getCell(c).getStringCellValue();
                    //if(valor.length()>0){
                    System.out.println(valor);
                        valor=valor.substring(0, valor.length()-1);
                        fila.getCell(c).setCellValue(valor);         
                    //}
                }
                fila.getCell(c).setCellStyle(stilo);
            }            
        }
    }
    
    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }

    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
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

    /**
     * @return the lHospitalizacion
     */
    public List<Hospitalizacion> getHospitalizacion() {
        return lHospitalizacion;
    }

    /**
     * @param lHospitalizacion the lHospitalizacion to set
     */
    public void setHospitalizacion(List<Hospitalizacion> lHospitalizacion) {
        this.lHospitalizacion = lHospitalizacion;
    }

    /**
     * @return the lFiltroHospitalizacion
     */
    public List<Hospitalizacion> getFiltroHospitalizacion() {
        return lFiltroHospitalizacion;
    }

    /**
     * @param lFiltroHospitalizacion the lFiltroHospitalizacion to set
     */
    public void setFiltroHospitalizacion(List<Hospitalizacion> lFiltroHospitalizacion) {
        this.lFiltroHospitalizacion = lFiltroHospitalizacion;
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
     * @return the oASerHRRB
     */
    public AreaServicioHRRB getASerHRRB() {
        return oASerHRRB;
    }

    /**
     * @param oASerHRRB the oASerHRRB to set
     */
    public void setASerHRRB(AreaServicioHRRB oASerHRRB) {
        this.oASerHRRB = oASerHRRB;
    }

    /**
     * @return the nTipo
     */
    public int getTipo() {
        return nTipo;
    }

    /**
     * @param nTipo the nTipo to set
     */
    public void setTipo(int nTipo) {
        this.nTipo = nTipo;
    }
    
}
