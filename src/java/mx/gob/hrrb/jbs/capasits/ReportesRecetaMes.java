/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Receta;
import mx.gob.hrrb.modelo.core.ReporteMes;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author sam
 */
@ManagedBean(name = "oRepRecMes")
@SessionScoped
public class ReportesRecetaMes implements Serializable {

    private Date fechaDia1 = null;
    private int anio, anioActual = 0;
    private ReporteMes[] listafinal = null;
    private int mesBusqueda = 0;
    private int totalRecetas = 0;
    private int totalMedicSurtidos = 0;

    public ReportesRecetaMes() {
        establecerFechaActual();
    }

    public void establecerFechaActual() {
        Calendar diaini = Calendar.getInstance();
        diaini.setTime(new Date());
        setAnioActual(diaini.get(Calendar.YEAR));
        setAnio(diaini.get(Calendar.YEAR));
        int mesActual = diaini.get(Calendar.MONTH);
        if (mesActual == 0) {
            Calendar fechainicial = Calendar.getInstance();
            diaini.add(Calendar.MONTH, -1);
            fechainicial.set(diaini.get((Calendar.YEAR)), diaini.get((Calendar.MONTH)), 28);
            setFechaDia1(fechainicial.getTime());
        } else {
            Calendar fechainicial = Calendar.getInstance();
            fechainicial.set(diaini.get(Calendar.YEAR), (mesActual - 1), 26);
            setFechaDia1(fechainicial.getTime());
        }
    }

    public void cargaListaXMes() throws Exception {
        listafinal = null;
        Calendar fecha1Aux = Calendar.getInstance();
        fecha1Aux.setTime(getFechaDia1());

        ReporteMes oDetMet = new ReporteMes();
        Calendar FechaDia2 = Calendar.getInstance();
        FechaDia2.setTime(fechaDia1);
        FechaDia2.add(Calendar.MONTH, 1);
        FechaDia2.set(FechaDia2.get(Calendar.YEAR), FechaDia2.get(Calendar.MONTH), 25);
        totalRecetas = new Receta().buscaRecetasSurtidasXRango(getFechaDia1(), FechaDia2.getTime());
        listafinal = (ReporteMes[]) oDetMet.buscarDetalleRecetaXMes(fecha1Aux.getTime());

        totalMedicSurtidos = 0;
        if (listafinal != null) {
            for (int i = 0; i < listafinal.length; i++) {
                totalMedicSurtidos += listafinal[i].getSubTotal();
            }
        }
    }

    public Date getFechaDia1() {
        return fechaDia1;
    }

    public void setFechaDia1(Date fechaDia1) {
        this.fechaDia1 = fechaDia1;
    }

    public ReporteMes[] getListafinal() throws Exception {
        totalMedicSurtidos = 0;
        listafinal = null;
        cargaListaXMes();
        return listafinal;
    }

    public void setListafinal(ReporteMes[] listafinal) {
        this.listafinal = listafinal;
    }

    public int getMesBusqueda() {
        return mesBusqueda;
    }

    public void setMesBusqueda(int mesBusqueda) {
        Calendar fechainicial = Calendar.getInstance();
        fechainicial.set(getAnio(), (mesBusqueda - 1), 26);
        fechainicial.add(Calendar.MONTH, -1);
        fechaDia1 = fechainicial.getTime();
        this.mesBusqueda = mesBusqueda;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        Calendar fechainicial = Calendar.getInstance();
        fechainicial.set(anio, mesBusqueda, 28);
        fechaDia1 = fechainicial.getTime();
        this.anio = anio;
    }

    public int getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(int anioActual) {
        this.anioActual = anioActual;
    }

    public int getTotalRecetas() {
        return totalRecetas;
    }

    public void setTotalRecetas(int totalRecetas) {
        this.totalRecetas = totalRecetas;
    }

    public String getFechas() {
        String fechas = "";
        Calendar f1 = Calendar.getInstance();
        f1.setTime(fechaDia1);
        fechas = +f1.get(Calendar.DAY_OF_MONTH) + "/" + (f1.get((Calendar.MONTH)) + 1) + "/" + f1.get(Calendar.YEAR);
        f1.add(Calendar.MONTH, 1);
        f1.add(Calendar.DAY_OF_MONTH, -1);
        fechas += "  al  " + f1.get(Calendar.DAY_OF_MONTH) + "/" + (f1.get((Calendar.MONTH)) + 1) + "/" + f1.get(Calendar.YEAR);
        return fechas;
    }

    public int getTotalMedicSurtidos() {
        return totalMedicSurtidos;
    }

    public void setTotalMedicSurtidos(int totalMedicSurtidos) {
        this.totalMedicSurtidos = totalMedicSurtidos;
    }

    public void postProcessXLS(Object document) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getFechaDia1());
        String fecha = "" + cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        fecha += " al " + cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0, "Medicamentos Surtidos " + fecha); //nombre de la hoja de excel
        sheet.shiftRows(0, 10, +6);//inserta renglones vacios

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("INFORME MENSUAL DE CONSUMO MEDICAMENTO ANTIRRETROVIRAL POR CLAVES");
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 34));

        HSSFRow row2 = sheet.createRow((short) 2);
        HSSFCell cell2 = row2.createCell((short) 1);
        cell2.setCellValue("ENTIDAD FEDERATIVA: VERACRUZ          CAPASITS: HOSPITAL REGIONAL RIO BLANCO");
        sheet.addMergedRegion(new Region(2, (short) 1, 2, (short) 27));

        HSSFRow row3 = sheet.createRow((short) 4);
        HSSFCell cell3 = row3.createCell((short) 1);
        cell3.setCellValue("FECHA: " + fecha);
        sheet.addMergedRegion(new Region(4, (short) 1, 4, (short) 10));
        HSSFCell cell3b = row3.createCell((short) 28);
        cell3b.setCellValue("Total de Recetas: " + totalRecetas);
        sheet.addMergedRegion(new Region(4, (short) 28, 4, (short) 33));

        int rowPieTabla = listafinal.length + 7;

        HSSFRow rowPie = sheet.createRow((short) rowPieTabla);
        HSSFCell cellPie = rowPie.createCell((short) 0);
        cellPie.setCellValue("TOTAL DE MEDICAMENTOS SURTIDOS ");
        sheet.addMergedRegion(new Region(rowPieTabla, (short) 0, rowPieTabla, (short) 32));
        HSSFCell cellTotal = rowPie.createCell((short) 33);
        cellTotal.setCellValue("" + getTotalMedicSurtidos());
        sheet.addMergedRegion(new Region(rowPieTabla, (short) 33, rowPieTabla, (short) 33));

        HSSFCellStyle cellStylePie = wb.createCellStyle();
        cellStylePie.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellPie.setCellStyle(cellStylePie);

        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 300);

        HSSFCellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle1.setFont(font);
        cell.setCellStyle(cellStyle1);

        HSSFRow header = sheet.getRow(6);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
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
        sheet.autoSizeColumn(14);
        sheet.autoSizeColumn(15);
        sheet.autoSizeColumn(16);
        sheet.autoSizeColumn(17);
        sheet.autoSizeColumn(18);
        sheet.autoSizeColumn(19);
        sheet.autoSizeColumn(20);
        sheet.autoSizeColumn(21);
        sheet.autoSizeColumn(22);
        sheet.autoSizeColumn(23);
        sheet.autoSizeColumn(24);
        sheet.autoSizeColumn(25);
        sheet.autoSizeColumn(26);
        sheet.autoSizeColumn(27);
        sheet.autoSizeColumn(28);
        sheet.autoSizeColumn(29);
        sheet.autoSizeColumn(30);
        sheet.autoSizeColumn(31);
        sheet.autoSizeColumn(32);
        sheet.autoSizeColumn(33);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
        int base = 7;
        HSSFCellStyle styletbl = wb.createCellStyle();
        styletbl.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styletbl.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styletbl.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styletbl.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        for (int i = 0; i < listafinal.length; i++) {
            HSSFRow tbl = sheet.getRow(base + i);
            for (int j = 0; j < 34; j++) {//35 numero de columnas
                HSSFCell cell0tb = tbl.getCell(j);
                cell0tb.setCellStyle(styletbl);
            }
        }
        HSSFRow tbl = sheet.getRow(base + listafinal.length);

        HSSFCell cell33tb = tbl.getCell(33);
        cell33tb.setCellStyle(styletbl);

        int ultimoRow = sheet.getLastRowNum();

        HSSFRow row4 = sheet.createRow((short) ultimoRow);
        HSSFCell cell4 = row4.createCell((short) 1);
        cell4.setCellValue("COORDINADOR CAPASITS:    DR.MAURICIO DE LA ROCA CHIAPAS");
        sheet.addMergedRegion(new Region(ultimoRow, (short) 1, ultimoRow, (short) 20));

        HSSFRow row5 = sheet.createRow((short) ultimoRow + 5);
        HSSFCell cell5 = row5.createCell((short) 1);
        cell5.setCellValue("ELABORO: ");
        sheet.addMergedRegion(new Region(ultimoRow + 5, (short) 1, ultimoRow + 5, (short) 20));
    }
}
