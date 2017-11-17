/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.Temperaturas;
import mx.gob.hrrb.modelo.core.Usuario;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

/**
 *
 * @author sam
 */
@ManagedBean(name = "oFarm")
@SessionScoped
public class Farmacia implements Serializable {

    private Temperaturas oTem = null;
    private boolean bRegistrarTem = false;
    private DetalleMedicamentos oDetMed = null;
    private DetalleMedicamentos[] arrMed = null;
    private boolean verEdicion = true;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;    
    private Usuario oUsua;

    public Farmacia() throws Exception {
        oUsua = new Usuario();
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
        oTem = new Temperaturas();
        verificaTem();
        cargaLista();
    }

    //Verifica que se hayan registrado las temperaturas del dia en el turno que se ejecuta  
    //si no se han registrado pone  bRegistrarTem=true para que se muestre el p:dialog 
 public void verificaTem() throws Exception {
        oUsua.setIdUsuario(oFirm.getUsu().getIdUsuario());
        if (oUsua.buscarUsuarioPerfilCapa()) {
            if (oUsua.getCvePerfil().equals("FARMCAPASI")) {
                try {
                    int rtem = oTem.buscaTemperaturaTurno();
                    if (rtem == -1) {
                        bRegistrarTem = true;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Farmacia.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            bRegistrarTem = false;
        }
    }

    private void cargaLista() throws Exception {
        oDetMed = new DetalleMedicamentos();
        arrMed = (DetalleMedicamentos[]) oDetMed.buscaproximoscaducados();
    }

    public void almacena() throws Exception {
        if (getTem().getTemAmb() < -1 || getTem().getTemAmb() > 40) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Temperatura Ambiental Fuera de Rango 0-40", " "));
            return;
        }
        if (getTem().getHumAmb() < 29 || getTem().getHumAmb() > 101) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Humedad Ambiental Fuera de Rango 30-100", " "));
            return;
        }
        if (getTem().getTemRef() < -1 || getTem().getTemRef() > 5.1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Temperatura del Refrigerador Fuera de Rango 0-5.1", " "));
            return;
        } else {
            bRegistrarTem = false;
            if (getTem().insertar(sUsuario) == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos Guardados", " "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se Guardaron los Datos", " "));
            }
        }
        this.setTem(new Temperaturas());
    }

    public void postProcessXLS(Object document) {
        String fecha = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        fecha = "" + cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0, "" + fecha); //nombre de la hoja de excel
        sheet.shiftRows(0, 10, +4);

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("INFORME DE MEDICAMENTOS PROXIMOS A CADUCAR");
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));

        HSSFRow row2 = sheet.createRow((short) 2);
        HSSFCell cellA = row2.createCell((short) 0);
        cellA.setCellValue("Fecha: " + fecha);
        sheet.addMergedRegion(new Region(2, (short) 0, 2, (short) 1));
        HSSFCell cellB = row2.createCell((short) 2);
        cellB.setCellValue("ENTIDAD FEDERATIVA: VERACRUZ    CAPASITS: HOSPITAL REGIONAL RIO BLANCO");
        sheet.addMergedRegion(new Region(2, (short) 2, 2, (short) 5));

        HSSFCellStyle cellStyleLabel = wb.createCellStyle();
        cellStyleLabel.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellB.setCellStyle(cellStyleLabel);

        HSSFCellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellStyle(cellStyle1);

        HSSFRow header = sheet.getRow(4);
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

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
        int base = 5;
        HSSFCellStyle styletbl = wb.createCellStyle();
        styletbl.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styletbl.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styletbl.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styletbl.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        for (int i = 0; i < arrMed.length; i++) {
            HSSFRow tbl = sheet.getRow(base + i);
            HSSFCell cell0tb = tbl.getCell(0);
            cell0tb.setCellStyle(styletbl);
            HSSFCell cell1tb = tbl.getCell(1);
            cell1tb.setCellStyle(styletbl);
            HSSFCell cell2tb = tbl.getCell(2);
            cell2tb.setCellStyle(styletbl);
            HSSFCell cell3tb = tbl.getCell(3);
            cell3tb.setCellStyle(styletbl);
            HSSFCell cell4tb = tbl.getCell(4);
            cell4tb.setCellStyle(styletbl);
            HSSFCell cell5tb = tbl.getCell(5);
            cell5tb.setCellStyle(styletbl);
        }

        int ultimoRow = sheet.getLastRowNum();

        HSSFRow row4 = sheet.createRow((short) ultimoRow);
        HSSFCell cell4 = row4.createCell((short) 1);
        cell4.setCellValue("COORDINADOR CAPASITS:    DR.MAURICIO DE LA ROCA CHIAPAS");
        sheet.addMergedRegion(new Region(ultimoRow, (short) 1, ultimoRow, (short) 2));

        HSSFRow row5 = sheet.createRow((short) ultimoRow + 5);
        HSSFCell cell5 = row5.createCell((short) 1);
        cell5.setCellValue("ELABORO: ");
        sheet.addMergedRegion(new Region(ultimoRow + 5, (short) 1, ultimoRow + 5, (short) 2));

        setVerEdicion(true);
    }

    public DetalleMedicamentos[] getLista() throws Exception {
        cargaLista();
        return arrMed;
    }

    public boolean getRegistrarTem() {
        return bRegistrarTem;
    }

    public void setRegistrarTem(boolean bRegistrarTem) {
        this.bRegistrarTem = bRegistrarTem;
    }

    public Temperaturas getTem() {
        return oTem;
    }

    public void setTem(Temperaturas oTem) {
        this.oTem = oTem;
    }

    public boolean getVerEdicion() {
        return verEdicion;
    }

    public void setVerEdicion(boolean verEdicion) {
        this.verEdicion = verEdicion;
    }

    public void paraImpresion() {
        setVerEdicion(false);
    }
}
