/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.Receta;
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
@ManagedBean(name = "oRepRecSemana")
@SessionScoped
public class ReportesRecetaSemana implements Serializable {

    private Date fechaDia1 = null;
    private Date fechaDia2 = null;
    private Date sfecha1 = null;
    private Date sfecha2 = null;
    private DetalleMedicamentos[] listaXRango = null;
    private Calendar cal = null;
    private int totalRecetas = 0;
    private int totalMedicSurtidos = 0;

    public ReportesRecetaSemana() throws Exception {
        buscarXSemana();
    }

    public void buscarXSemana() throws Exception {
        if (getFechaDia1() == null && getFechaDia2() == null) {
            establecerSemanaActual();
        }
        if (validaQueSeaSemana()) {
            if (getFechaDia1().after(new Date()) || getFechaDia2().after(new Date())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La fecha de inicio o de fin no pueden ser mayores al dia de Hoy", " "));
            } else {
                cargaListaXRangoSemana();
            }
        }
    }

    public boolean validaQueSeaSemana() {
        Calendar diaini = Calendar.getInstance();
        diaini.setTime(getFechaDia1());
        int diainicial = diaini.get(Calendar.DAY_OF_MONTH);

        Calendar diafin = Calendar.getInstance();
        diafin.setTime(getFechaDia2());
        int diafinal = diafin.get(Calendar.DAY_OF_MONTH);

        int diasSeleccionados = diafinal - diainicial;

        if (diasSeleccionados > 7) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La fecha de seleccion sobrepasa una semana", " "));
            return false;
        }
        return true;
    }

    public void establecerSemanaActual() {
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            default:
                cal.setTime(new Date());
            case 1:
                cal.add(Calendar.DAY_OF_YEAR, 1); //1=domingo 2=lunes, etc si es domingo suma un dia para que sea lunes el inicio
                setFechaDia1(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 4);//como ya se le sumo un dia solo se suman 4 para que sea viernes
                setFechaDia2(cal.getTime());
                break;
            case 2:
                setFechaDia1(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 4);
                setFechaDia2(cal.getTime());
                break;
            case 3:
                cal.add(Calendar.DAY_OF_YEAR, -1);
                setFechaDia1(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 4);
                setFechaDia2(cal.getTime());
                break;
            case 4:
                cal.add(Calendar.DAY_OF_YEAR, -2);
                setFechaDia1(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 4);
                setFechaDia2(cal.getTime());
                break;
            case 5:
                cal.add(Calendar.DAY_OF_YEAR, -3);
                setFechaDia1(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 4);
                setFechaDia2(cal.getTime());
                break;
            case 6:
                cal.add(Calendar.DAY_OF_YEAR, -4);
                setFechaDia1(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 4);
                setFechaDia2(cal.getTime());
                break;
            case 7:
                cal.add(Calendar.DAY_OF_YEAR, -1);
                setFechaDia1(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 4);
                setFechaDia2(cal.getTime());
                break;
        }
    }

    public void cargaListaXRangoSemana() throws Exception {
        DetalleMedicamentos oDetMet = new DetalleMedicamentos();
        listaXRango = (DetalleMedicamentos[]) oDetMet.buscarDetalleRecetaXRango(getFechaDia1(), getFechaDia2());
        if (listaXRango.length == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se Encontraron Datos", " "));
        }
    }

    public Date getFechaDia1() {
        return fechaDia1;
    }

    public void setFechaDia1(Date fechaDia1) {
        this.fechaDia1 = fechaDia1;
    }

    public DetalleMedicamentos[] getListaXRangoSemana() throws Exception {
        listaXRango = null;
        cargaListaXRangoSemana();
        return listaXRango;
    }

    public void setListaXRangoSemana(DetalleMedicamentos[] listaXRango) {
        this.listaXRango = listaXRango;
    }

    public Date getFechaDia2() {
        return fechaDia2;
    }

    public void setFechaDia2(Date fechaDia2) {
        this.fechaDia2 = fechaDia2;
    }

    public String getF1() {
        String sf1 = "";
        Calendar f1 = Calendar.getInstance();
        f1.setTime(getFechaDia1());
        sf1 = "" + f1.get(Calendar.DAY_OF_MONTH);
        sf1 += "-" + (f1.get(Calendar.MONTH) + 1);
        sf1 += "-" + f1.get(Calendar.YEAR);
        return sf1;
    }

    public String getF2() {
        String sf2 = "";
        Calendar f2 = Calendar.getInstance();
        f2.setTime(getFechaDia2());
        sf2 = "" + f2.get(Calendar.DAY_OF_MONTH);
        sf2 += "-" + (f2.get(Calendar.MONTH) + 1);
        sf2 += "-" + f2.get(Calendar.YEAR);
        return sf2;
    }

    public int getTotalRecetas() throws Exception {
        totalRecetas = new Receta().buscaRecetasSurtidasXRango(getFechaDia1(), getFechaDia2());
        return totalRecetas;
    }

    public void setTotalRecetas(int totalRecetas) {
        this.totalRecetas = totalRecetas;
    }

    public int getTotalMedicSurtidos() {
        totalMedicSurtidos = 0;
        if (listaXRango != null) {
            for (int i = 0; i < listaXRango.length; i++) {
                totalMedicSurtidos += listaXRango[i].getExistencia();
            }
        }
        return totalMedicSurtidos;
    }

    public void setTotalMedicSurtidos(int totalMedicSurtidos) {
        this.totalMedicSurtidos = totalMedicSurtidos;
    }

    public void postProcessXLS(Object document) throws Exception {
        if (listaXRango != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getFechaDia1());
            String fecha = " Semana  del " + cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
            cal.setTime(getFechaDia2());
            fecha += " al " + cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);
            wb.setSheetName(0, "Medicamentos Surtidos " + fecha); //nombre de la hoja de excel
            sheet.shiftRows(0, 10, +6);//inserta renglones vacios

            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);
            cell.setCellValue("INFORME SEMANAL DE CONSUMO MEDICAMENTO ANTIRRETROVIRAL POR CLAVES");
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 6));

            HSSFRow row2 = sheet.createRow((short) 2);
            HSSFCell cell2 = row2.createCell((short) 1);
            cell2.setCellValue("ENTIDAD FEDERATIVA: VERACRUZ          CAPASITS: HOSPITAL REGIONAL RIO BLANCO");
            sheet.addMergedRegion(new Region(2, (short) 1, 2, (short) 5));

            HSSFRow row3 = sheet.createRow((short) 4);
            HSSFCell cell3 = row3.createCell((short) 1);
            cell3.setCellValue("FECHA: " + fecha);
            sheet.addMergedRegion(new Region(4, (short) 1, 4, (short) 2));
            HSSFCell cell3b = row3.createCell((short) 3);
            cell3b.setCellValue("Total de Recetas: " + totalRecetas);
            sheet.addMergedRegion(new Region(4, (short) 3, 4, (short) 5));

            int rowPieTabla = listaXRango.length + 7;

            HSSFRow rowPie = sheet.createRow((short) rowPieTabla);
            HSSFCell cellPie = rowPie.createCell((short) 0);
            cellPie.setCellValue("TOTAL DE MEDICAMENTOS SURTIDOS ");
            sheet.addMergedRegion(new Region(rowPieTabla, (short) 0, rowPieTabla, (short) 2));
            HSSFCell cellTotal = rowPie.createCell((short) 3);
            cellTotal.setCellValue("" + totalMedicSurtidos);
            sheet.addMergedRegion(new Region(rowPieTabla, (short) 3, rowPieTabla, (short) 3));

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

            for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
                header.getCell(i).setCellStyle(cellStyle);
            }
            int base = 7;
            HSSFCellStyle styletbl = wb.createCellStyle();
            styletbl.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styletbl.setBorderTop(HSSFCellStyle.BORDER_THIN);
            styletbl.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styletbl.setBorderLeft(HSSFCellStyle.BORDER_THIN);

            for (int i = 0; i < listaXRango.length; i++) {
                HSSFRow tbl = sheet.getRow(base + i);
                HSSFCell cell0tb = tbl.getCell(0);
                cell0tb.setCellStyle(styletbl);
                HSSFCell cell1tb = tbl.getCell(1);
                cell1tb.setCellStyle(styletbl);
                HSSFCell cell2tb = tbl.getCell(2);
                cell2tb.setCellStyle(styletbl);
                HSSFCell cell3tb = tbl.getCell(3);
                cell3tb.setCellStyle(styletbl);
            }
            HSSFRow tbl = sheet.getRow(base + listaXRango.length);

            HSSFCell cell3tb = tbl.getCell(3);
            cell3tb.setCellStyle(styletbl);

            int ultimoRow = sheet.getLastRowNum();

            HSSFRow row4 = sheet.createRow((short) ultimoRow);
            HSSFCell cell4 = row4.createCell((short) 1);
            cell4.setCellValue("COORDINADOR CAPASITS:    DR.MAURICIO DE LA ROCA CHIAPAS");
            sheet.addMergedRegion(new Region(ultimoRow, (short) 1, ultimoRow, (short) 2));

            HSSFRow row5 = sheet.createRow((short) ultimoRow + 5);
            HSSFCell cell5 = row5.createCell((short) 1);
            cell5.setCellValue("ELABORO: ");
            sheet.addMergedRegion(new Region(ultimoRow + 5, (short) 1, ultimoRow + 5, (short) 2));

        }
    }

}
