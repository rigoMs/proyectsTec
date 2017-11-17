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
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
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
@ManagedBean(name = "oRepRec")
@SessionScoped
public class ReportesRecetaDIa implements Serializable {

    private Date fechaDia1 = null;
    private DetalleMedicamentos[] listaXDia = null;
    private int totalRecetas = 0;
    private int totalMedicSurtidos = 0;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;

    public ReportesRecetaDIa() throws Exception {
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
        buscar();
    }

    public void buscar() throws Exception {
        totalRecetas = 0;
        if (getFechaDia1() == null) {
            setFechaDia1(new Date());
        }
        if (getFechaDia1().after(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La fecha no puede ser futura", " "));
        } else {
            cargaListaXDia();
        }
    }

    public void cargaListaXDia() throws Exception {
        DetalleMedicamentos oDetMet = new DetalleMedicamentos();
        listaXDia = (DetalleMedicamentos[]) oDetMet.buscarDetalleRecetaXDia(getFechaDia1());

        if (listaXDia.length == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se Encontraron Datos", " "));
        }
    }

    public Date getFechaDia1() {
        return fechaDia1;
    }

    public void setFechaDia1(Date fechaDia1) {
        this.fechaDia1 = fechaDia1;
    }

    public DetalleMedicamentos[] getListaXDia() throws Exception {
        listaXDia = null;
        cargaListaXDia();
        return listaXDia;
    }

    public void setListaXDia(DetalleMedicamentos[] listaXDia) {
        this.listaXDia = listaXDia;
    }

    public int getDianumero() {
        Calendar dia = Calendar.getInstance();
        dia.setTime(fechaDia1);
        return dia.get(Calendar.DAY_OF_MONTH);
    }

    public String getMes() {
        String mesNombre = "";
        Calendar mes = Calendar.getInstance();
        mes.setTime(fechaDia1);
        int m = mes.get(Calendar.MONTH);
        switch (m) {
            case 0:
                mesNombre = "Enero";
                break;
            case 1:
                mesNombre = "Febrero";
                break;
            case 2:
                mesNombre = "Marzo";
                break;
            case 3:
                mesNombre = "Abril";
                break;
            case 4:
                mesNombre = "Mayo";
                break;
            case 5:
                mesNombre = "Junio";
                break;
            case 6:
                mesNombre = "Julio";
                break;
            case 7:
                mesNombre = "Agosto";
                break;
            case 8:
                mesNombre = "Septiembre";
                break;
            case 9:
                mesNombre = "Octubre";
                break;
            case 10:
                mesNombre = "Noviembre";
                break;
            case 11:
                mesNombre = "Diciembre";
                break;
        }
        return mesNombre;
    }

    public void postProcessXLS(Object document) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getFechaDia1());
        String fecha = "" + cal.get(Calendar.DAY_OF_MONTH) + "-" + ((cal.get(Calendar.MONTH)) + 1) + "-" + cal.get(Calendar.YEAR);

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0, "Medicamentos Surtidos " + fecha); //nombre de la hoja de excel
        sheet.shiftRows(0, 10, +6);//inserta renglones vacios

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("INFORME DIARIO DE CONSUMO MEDICAMENTO ANTIRRETROVIRAL POR CLAVES");
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 6));

        HSSFRow row2 = sheet.createRow((short) 2);
        HSSFCell cell2 = row2.createCell((short) 1);
        cell2.setCellValue("ENTIDAD FEDERATIVA: VERACRUZ          CAPASITS: HOSPITAL REGIONAL RIO BLANCO");
        sheet.addMergedRegion(new Region(2, (short) 1, 2, (short) 5));

        HSSFRow row3 = sheet.createRow((short) 4);
        HSSFCell cell3 = row3.createCell((short) 1);
        cell3.setCellValue("FECHA: " + getDianumero() + " de " + getMes() + " del " + getAnio());
        sheet.addMergedRegion(new Region(4, (short) 1, 4, (short) 2));
        HSSFCell cell3b = row3.createCell((short) 3);
        cell3b.setCellValue("Total de Recetas: " + totalRecetas);
        sheet.addMergedRegion(new Region(4, (short) 3, 4, (short) 5));

        int rowPieTabla = listaXDia.length + 7;
        HSSFCellStyle styletblabajo = wb.createCellStyle();
        styletblabajo.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFRow rowPie = sheet.createRow((short) rowPieTabla);
        HSSFCell cellPie = rowPie.createCell((short) 0);
        cellPie.setCellValue("TOTAL DE MEDICAMENTOS SURTIDOS ");
        sheet.addMergedRegion(new Region(rowPieTabla, (short) 0, rowPieTabla, (short) 2));
        cellPie.setCellStyle(styletblabajo);
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

        for (int i = 0; i < listaXDia.length; i++) {
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
        HSSFRow tbl = sheet.getRow(base + listaXDia.length);

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

    public int getAnio() {
        Calendar dia = Calendar.getInstance();
        dia.setTime(fechaDia1);
        return dia.get(Calendar.YEAR);
    }

    public int getTotalRecetas() throws Exception {
        totalRecetas = new Receta().buscaRecetasSurtidasXdia(getFechaDia1());
        return totalRecetas;
    }

    public void setTotalRecetas(int totalRecetas) {
        this.totalRecetas = totalRecetas;
    }

    public int getTotalMedicSurtidos() {
        totalMedicSurtidos = 0;
        for (int i = 0; i < listaXDia.length; i++) {
            totalMedicSurtidos += listaXDia[i].getExistencia();
        }
        return totalMedicSurtidos;
    }

    public void setTotalMedicSurtidos(int totalMedicSurtidos) {
        this.totalMedicSurtidos = totalMedicSurtidos;
    }

}
