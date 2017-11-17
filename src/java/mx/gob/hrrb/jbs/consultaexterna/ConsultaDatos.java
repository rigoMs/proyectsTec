/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Medico;
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
 * @author Javi
 */
@ManagedBean(name = "oConDat")
@SessionScoped
public class ConsultaDatos {
private Cita oCita;
private Cita oCita2;
private Medico oMed;
private Medico oMed2;
private Cita oCita3;
private Medico oMed3;
private Cita oCita4;
private AsignaConsultorio oAsigCon;
private int nBorrado;

    public ConsultaDatos() {
        oCita=new Cita();
        oCita2=new Cita();
        oMed=new Medico();
        oMed2=new Medico();
        oCita3=new Cita();
        oMed3=new Medico();
        oAsigCon=new AsignaConsultorio();
        oCita4=new Cita();
        nBorrado=0;
    }

    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }

    public Medico getMed() {
        return oMed;
    }

    public void setMed(Medico oMed) {
        this.oMed = oMed;
    }
    
    public List<Medico> getListaMedicos(){
       List<Medico> lLista = null;
       try {
           lLista = new ArrayList<Medico>(Arrays.asList(
                   (new Medico()).buscarMedicosParaCambios()));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
      //*************************************************************************
        public void postProcessXLS2(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"Citas"); //nombre de la hoja de excel
        sheet.shiftRows (0, 100, +5);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)6));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("PACIENTES CITADOS POR MÉDICO");
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)6));
        
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA: "+oCita2.obtenFechaAux()+"   -   CONSULTORIO: "+oCita2.getNoConsultorio()+"  -  SERVICIO: "+oCita2.getAreaServicio().getDescripcion());
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)6));
        
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
        
        row =sheet.createRow((short)3);
        cell=row.createCell((short)0);
        cell.setCellValue("MÉDICO: "+oCita2.getPH().getNoTarjeta()+" - "+oCita2.getPH().getApPaterno()+" "+oCita2.getPH().getApMaterno()+" "+oCita2.getPH().getNombres());
        sheet.addMergedRegion(new Region(3,(short)0, 3, (short)6));
        
        
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
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 3500);
        sheet.setColumnWidth(2, 3500);
        sheet.setColumnWidth(3, 4200);
        sheet.autoSizeColumn(4);
        sheet.setColumnWidth(5, 4300);
        sheet.setColumnWidth(6, 7600);
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
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(5);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
    }
        
    public int borraCampos() throws Exception{
        if (nBorrado==1){
            getCita4().getPaciente().setApPaterno("");
            getCita4().getPaciente().setApMaterno("");
            getCita4().getPaciente().setNombres("");
            getCita4().getPaciente().getExpediente().setNumero(0);
            nBorrado=0;
        }
        return nBorrado;
    }
    
    public void setCita2(Cita oCita2) {
        this.oCita2 = oCita2;
    }

        public Cita getCita2() {
        return oCita2;
    }

    public Medico getMed2() {
        return oMed2;
    }

    public void setMed2(Medico oMed2) {
        this.oMed2 = oMed2;
    }

    public Cita getCita3() {
        return oCita3;
    }

    public void setCita3(Cita oCita3) {
        this.oCita3 = oCita3;
    }

    public Medico getMed3() {
        return oMed3;
    }

    public void setMed3(Medico oMed3) {
        this.oMed3 = oMed3;
    }

    public AsignaConsultorio getAsigCon() {
        return oAsigCon;
    }

    public void setAsigCon(AsignaConsultorio oAsigCon) {
        this.oAsigCon = oAsigCon;
    }

    public Cita getCita4() {
        return oCita4;
    }

    public void setCita4(Cita oCita4) {
        this.oCita4 = oCita4;
    }
    
    public int getBorrado() {
        return nBorrado;
    }

    public void setBorrado(int nBorrado) {
        this.nBorrado = nBorrado;
    }
}
