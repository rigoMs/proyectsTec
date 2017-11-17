/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Temperaturas;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Font;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author sam
 */
@ManagedBean(name = "estadistica")
@SessionScoped
public class EstadisticaTemJB implements Serializable {

    private String mes = "";
    public int mesBusqueda = 0;
    private int turno = 1;
    private int anio, AnioActual = 0;
    private Temperaturas[] arrTemM = null;
    private Temperaturas[] arrTemV = null;
    private Temperaturas[] arrTemN = null;
    private LineChartModel graficaTemAmb, graficaTemRef, graficaHumAmb;
    private List<List<Double>> TablaCompleta;
    private Calendar cal = null;
    private boolean NoesMesFuturo = true;
    private boolean verBotonesGrafica = true;

    public EstadisticaTemJB() throws Exception {
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        setAnioActual(cal.get(Calendar.YEAR));
        cargaListasYDatos();
    }

    public void cargaListasYDatos() throws Exception {
        setVerBotonesGrafica(true);
        if (getNoesMesFuturo() && getAnio() <= getAnioActual()) {
            cargaListaM();
            graficaTemAmb();
            graficaTemRef();
            graficaHumAmb();
            combinaDatos();
        } else {
            setVerBotonesGrafica(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Verifique seleccion", " "));
        }
    }

    public List<List<Double>> getTablaCompleta() {
        return this.TablaCompleta;
    }

    public int getColumnNumber() {
        return TablaCompleta.get(0).size();
    }

    public void cargaListaM() { //carla la lista con los datos en horario Matutino
        arrTemM = null;
        if (getMesBusqueda() == 0) { // si no hay ningun mes indicado a buscar busca el mes actual
            cal = Calendar.getInstance();
            cal.setTime(new Date());
            setMesBusqueda(cal.get(Calendar.MONTH) + 1);
            nombreMes(cal.get(Calendar.MONTH) + 1);
            setAnio(cal.get(Calendar.YEAR));
        } else {
            nombreMes(getMesBusqueda());
        }
        Temperaturas oTem = new Temperaturas();
        try {
            if (getMesBusqueda() > 9) {
                arrTemM = (Temperaturas[]) oTem.buscarTodos(getAnio(), "" + getMesBusqueda(), 1);
            } else {
                arrTemM = (Temperaturas[]) oTem.buscarTodos(getAnio(), "0" + getMesBusqueda(), 1);
            }
            cargaListaV();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron datos", " "));
            this.TablaCompleta = null;
            return;
        }
    }

    public void cargaListaV() throws Exception {//Carga la lista con los datos en horario Vespertino
        arrTemV = null;
        Temperaturas oTem = new Temperaturas();
        if (getMesBusqueda() > 9) {
            arrTemV = (Temperaturas[]) oTem.buscarTodos(getAnio(), "" + getMesBusqueda(), 2);
        } else {
            arrTemV = (Temperaturas[]) oTem.buscarTodos(getAnio(), "0" + getMesBusqueda(), 2);
        }
        cargaListaN();
    }

    public void cargaListaN() throws Exception {//Carga la lista con los datos en horario Nocturno
        arrTemN = null;
        Temperaturas oTem = new Temperaturas();
        if (getMesBusqueda() > 9) {
            arrTemN = (Temperaturas[]) oTem.buscarTodos(getAnio(), "" + getMesBusqueda(), 3);
        } else {
            arrTemN = (Temperaturas[]) oTem.buscarTodos(getAnio(), "0" + getMesBusqueda(), 3);
        }
    }

    public void setMesBusqueda(int mesBusqueda) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int month = (cal.get(Calendar.MONTH)) + 1;

        if (getAnio() > getAnioActual()) {
        }
        if (getAnio() == getAnioActual()) {
            if (mesBusqueda > month) {
                setNoesMesFuturo(false);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocurrio un error, selecciono mes futuro", " "));
            } else {
                this.mesBusqueda = mesBusqueda;
                setNoesMesFuturo(true);
            }
        }
        if (getAnio() < getAnioActual()) {
            this.mesBusqueda = mesBusqueda;
            setNoesMesFuturo(true);
        }
    }

    public void nombreMes(int month) {
        String mesActual = "";
        switch (month) {
            case 1:
                mesActual = "Enero";
                break;
            case 2:
                mesActual = "Febrero";
                break;
            case 3:
                mesActual = "Marzo";
                break;
            case 4:
                mesActual = "Abril";
                break;
            case 5:
                mesActual = "Mayo";
                break;
            case 6:
                mesActual = "Junio";
                break;
            case 7:
                mesActual = "Julio";
                break;
            case 8:
                mesActual = "Agosto";
                break;
            case 9:
                mesActual = "Septiembre";
                break;
            case 10:
                mesActual = "Octubre";
                break;
            case 11:
                mesActual = "Noviembre";
                break;
            case 12:
                mesActual = "Diciembre";
                break;
            default:
                mesActual = "Mes Invalido";
                break;
        }
        this.mes = mesActual;
    }

    public Temperaturas[] getListaM() {
        return arrTemM;
    }

    public Temperaturas[] getListaV() {
        return arrTemV;
    }

    public Temperaturas[] getListaN() {
        return arrTemN;
    }

    public int getMesBusqueda() {
        return mesBusqueda;
    }

    private void graficaTemAmb() {
        graficaTemAmb = new LineChartModel();

        graficaTemAmb.setTitle("Temperatura Ambiental");
        graficaTemAmb.setZoom(true);
        graficaTemAmb.getAxis(AxisType.Y).setLabel("Grados");
        DateAxis axis = new DateAxis("Dias");
        axis.setTickAngle(-50);

        axis.setTickFormat("%b %#d, %y");
        graficaTemAmb.getAxes().put(AxisType.X, axis);
        graficaTemAmb.setLegendPosition("g");

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Turno Matutino");

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Turno Vespertino");

        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("Turno Nocturno");

        Calendar cal = Calendar.getInstance();
        cal.setTime(arrTemM[0].getRegistro());
        String mesAnio = "" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-";
        for (int i = 0; i < arrTemM.length; i++) {
            series1.set("" + mesAnio + (i + 1), arrTemM[i].getTemAmb());
            series2.set("" + mesAnio + (i + 1), arrTemV[i].getTemAmb());
            series3.set("" + mesAnio + (i + 1), arrTemN[i].getTemAmb());

        }
        graficaTemAmb.addSeries(series1);
        graficaTemAmb.addSeries(series2);
        graficaTemAmb.addSeries(series3);
    }

    private void graficaTemRef() {
        graficaTemRef = new LineChartModel();

        graficaTemRef.setTitle("Temperatura Refrigerador");
        graficaTemRef.setZoom(true);
        graficaTemRef.getAxis(AxisType.Y).setLabel("Grados");
        DateAxis axis = new DateAxis("Dias");
        axis.setTickAngle(-50);

        axis.setTickFormat("%b %#d, %y");
        graficaTemRef.getAxes().put(AxisType.X, axis);
        graficaTemRef.setLegendPosition("g");

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Turno Matutino");

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Turno Vespertino");

        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("Turno Nocturno");

        Calendar cal = Calendar.getInstance();
        cal.setTime(arrTemM[0].getRegistro());
        String mesAnio = "" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-";
        for (int i = 0; i < arrTemM.length; i++) {
            series1.set("" + mesAnio + (i + 1), arrTemM[i].getTemRef());
            series2.set("" + mesAnio + (i + 1), arrTemV[i].getTemRef());
            series3.set("" + mesAnio + (i + 1), arrTemN[i].getTemRef());
        }
        graficaTemRef.addSeries(series1);
        graficaTemRef.addSeries(series2);
        graficaTemRef.addSeries(series3);
    }

    private void graficaHumAmb() {
        graficaHumAmb = new LineChartModel();

        graficaHumAmb.setTitle("Humedad Ambiental");
        graficaHumAmb.setZoom(true);
        graficaHumAmb.getAxis(AxisType.Y).setLabel("Grados");
        DateAxis axis = new DateAxis("Dias");
        axis.setTickAngle(-50);

        axis.setTickFormat("%b %#d, %y");
        graficaHumAmb.getAxes().put(AxisType.X, axis);
        graficaHumAmb.setLegendPosition("g");

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Turno Matutino");

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Turno Vespertino");

        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("Turno Nocturno");

        Calendar cal = Calendar.getInstance();
        cal.setTime(arrTemM[0].getRegistro());
        String mesAnio = "" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-";
        for (int i = 0; i < arrTemM.length; i++) {
            series1.set("" + mesAnio + (i + 1), arrTemM[i].getHumAmb());
            series2.set("" + mesAnio + (i + 1), arrTemV[i].getHumAmb());
            series3.set("" + mesAnio + (i + 1), arrTemN[i].getHumAmb());
        }
        graficaHumAmb.addSeries(series1);
        graficaHumAmb.addSeries(series2);
        graficaHumAmb.addSeries(series3);
    }

    public LineChartModel getGraficaTemAmb() {
        return graficaTemAmb;
    }

    private void combinaDatos() {
        this.TablaCompleta = new ArrayList<List<Double>>();
        for (int i = 0; i < arrTemM.length; i++) {
            this.TablaCompleta.add(new ArrayList<Double>());

            this.TablaCompleta.get(i).add(arrTemM[i].getTemAmb());//temAmbientalMatutino
            this.TablaCompleta.get(i).add(arrTemM[i].getHumAmb());//humAmbientalMatutino
            this.TablaCompleta.get(i).add(arrTemM[i].getTemRef());//temRefriMatutino

            this.TablaCompleta.get(i).add(arrTemV[i].getTemAmb());//temAmbientalVespertino    
            this.TablaCompleta.get(i).add(arrTemV[i].getHumAmb());//humAmbientalVespertino
            this.TablaCompleta.get(i).add(arrTemV[i].getTemRef());//temRefriVespertino

            this.TablaCompleta.get(i).add(arrTemN[i].getTemAmb());//temAmbientalNocturno
            this.TablaCompleta.get(i).add(arrTemN[i].getHumAmb());//humAmbientalNocturno
            this.TablaCompleta.get(i).add(arrTemN[i].getTemRef());//temRefriNocturno
        }
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        if (anio > getAnioActual()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se puede Buscar en un año Futuro", " "));
            return;
        } else {
            this.anio = anio;
        }
    }

    public LineChartModel getGraficaTemRef() {
        return graficaTemRef;
    }

    public void setGraficaTemRef(LineChartModel graficaTemRef) {
        this.graficaTemRef = graficaTemRef;
    }

    public LineChartModel getGraficaHumAmb() {
        return graficaHumAmb;
    }

    public void setGraficaHumAmb(LineChartModel graficaHumAmb) {
        this.graficaHumAmb = graficaHumAmb;
    }

    public int getAnioActual() {
        return AnioActual;
    }

    public void setAnioActual(int AnioActual) {
        this.AnioActual = AnioActual;
    }

    public boolean getNoesMesFuturo() {
        return NoesMesFuturo;
    }

    public void setNoesMesFuturo(boolean NoesMesFuturo) {
        this.NoesMesFuturo = NoesMesFuturo;
    }

    public boolean isVerBotonesGrafica() {
        return verBotonesGrafica;
    }

    public void setVerBotonesGrafica(boolean verBotonesGrafica) {
        this.verBotonesGrafica = verBotonesGrafica;
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0, mes); //nombre de la hoja de excel
        sheet.shiftRows(0, 10, +7);//recorre el contenido que hay desde en la fila 0 hasta la 

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("INFORME DE TEMPERATURAS");
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 9));
        HSSFCellStyle cellStyle1 = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 300);
        cellStyle1.setFont(font);
        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellStyle(cellStyle1);

        HSSFRow row2 = sheet.createRow((short) 2);
        HSSFCell cell2 = row2.createCell((short) 1);
        cell2.setCellValue("ENTIDAD FEDERATIVA: VERACRUZ          CAPASITS: HOSPITAL REGIONAL RIO BLANCO");
        sheet.addMergedRegion(new Region(2, (short) 1, 2, (short) 5));

        HSSFRow row3 = sheet.createRow((short) 4);
        HSSFCell cell3 = row3.createCell((short) 1);
        cell3.setCellValue("Fecha: " + mes + " del " + anio);
        sheet.addMergedRegion(new Region(4, (short) 1, 4, (short) 2));

        row = sheet.createRow((short) 6);
        cell = row.createCell((short) 2);
        cell.setCellValue("Turno Matutino");
        sheet.addMergedRegion(new Region(6, (short) 2, 6, (short) 2));

        cell = row.createCell((short) 5);
        cell.setCellValue("Turno Vespertino");
        sheet.addMergedRegion(new Region(6, (short) 5, 6, (short) 5));

        cell = row.createCell((short) 8);
        cell.setCellValue("Turno Nocturno");
        sheet.addMergedRegion(new Region(6, (short) 8, 6, (short) 8));

        row = sheet.createRow((short) 7);
        cell = row.createCell((short) 0);
        cell.setCellValue("Dia");

        cell = row.createCell((short) 1);
        cell.setCellValue("Temperatura Hambiental");

        cell = row.createCell((short) 2);
        cell.setCellValue("Humedad Hambiental");

        cell = row.createCell((short) 3);
        cell.setCellValue("Temperatura Refrigerador");

        cell = row.createCell((short) 4);
        cell.setCellValue("Temperatura Hambiental");

        cell = row.createCell((short) 5);
        cell.setCellValue("Humedad Hambiental");

        cell = row.createCell((short) 6);
        cell.setCellValue("Temperatura Refrigerador");

        cell = row.createCell((short) 7);
        cell.setCellValue("Temperatura Hambiental");

        cell = row.createCell((short) 8);
        cell.setCellValue("Humedad Hambiental");

        cell = row.createCell((short) 9);
        cell.setCellValue("Temperatura Refrigerador");

        HSSFRow header = sheet.getRow(7);
        HSSFRow header2 = sheet.getRow(6);
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

        header2.getCell(2).setCellStyle(cellStyle);
        header2.getCell(5).setCellStyle(cellStyle);
        header2.getCell(8).setCellStyle(cellStyle);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
       int ultimoRow = 32;

        HSSFRow row4 = sheet.createRow((short) ultimoRow);
        HSSFCell cell4 = row4.createCell((short) 1);
        cell4.setCellValue("COORDINADOR CAPASITS:    DR.MAURICIO DE LA ROCA CHIAPAS");
        sheet.addMergedRegion(new Region(ultimoRow, (short) 1, ultimoRow, (short) 4));

        HSSFRow row5 = sheet.createRow((short) ultimoRow + 5);
        HSSFCell cell5 = row5.createCell((short) 1);
        cell5.setCellValue("ELABORO: ");
        sheet.addMergedRegion(new Region(ultimoRow + 5, (short) 1, ultimoRow + 5, (short) 4));
    }

}
