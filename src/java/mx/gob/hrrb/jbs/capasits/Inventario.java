package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.SessionScoped;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.Usuario;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Font;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author sam
 */
@ManagedBean(name = "oInvent")
@SessionScoped
public class Inventario implements Serializable {

    private DetalleMedicamentos[] arrDetMed = null;
    private List<DetalleMedicamentos> oMedFiltrado;
    private DetalleMedicamentos oMed = null;
    private boolean verEdicion = true;
    private boolean muestraInfo = false;
    private boolean permiso = true;
    private String usuario = "";
    private String contrasena = "";
    private DetalleMedicamentos DetalleDetalleMedicamentos = null;
    private boolean banderaBusqueda = false;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;

    public Inventario() throws Exception {
        cargaLista();
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public void cargaLista() throws Exception {
        if (banderaBusqueda == false && arrDetMed == null) {
            setVerEdicion(true);
            DetalleMedicamentos oMe = new DetalleMedicamentos();
            setLista((DetalleMedicamentos[]) oMe.buscarTodos());
        }
    }

    public void onEdit(RowEditEvent event) throws Exception {
        banderaBusqueda = true;
        DetalleMedicamentos oMedEdic = (DetalleMedicamentos) event.getObject();
        if (oMedEdic.modificar(sUsuario) == 1) {
            setPermiso(true);
            setUsuario("");
            setContrasena("");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", " "));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No Modificado", " "));
        }
        banderaBusqueda = false;
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelado", ((DetalleMedicamentos) event.getObject()).getMedicamento().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void postProcessXLS(Object document) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String fecha = "" + cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0, "inventario al " + fecha); //nombre de la hoja de excel
        sheet.shiftRows(0, 10, +4);//inserta renglones vacios

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("INFORME DE EXISTENCIAS DE MEDICAMENTO ANTIRRETROVIRAL");
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));

        HSSFRow row2 = sheet.createRow((short) 2);
        HSSFCell cell2 = row2.createCell((short) 1);
        cell2.setCellValue("ENTIDAD FEDERATIVA: VERACRUZ");
        sheet.addMergedRegion(new Region(2, (short) 1, 2, (short) 2));
        HSSFCell cell3 = row2.createCell((short) 3);
        cell3.setCellValue("CAPASITS: HOSPITAL REGIONAL RIO BLANCO ");
        sheet.addMergedRegion(new Region(2, (short) 3, 2, (short) 7));

        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) 300);

        HSSFCellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle1.setFont(font);
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

        for (int i = 0; i < arrDetMed.length; i++) {
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

    public String validaPermiso() throws Exception {
        Usuario usu = new Usuario();
        usu.setIdUsuario(usuario);
        usu.setPassword(contrasena);
        if (usu.buscarCvePwd()) {
            oFirm = new Firmado();
            oFirm.setUsu(usu);
            if (oFirm.getUsu().getPerfil().get(0).getClave().equals("ADMCAPASIT")) {
                setPermiso(false);
                return "ajusteInventario.xhtml";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Verifica Datos", " "));
            return "";
        }
        setPermiso(true);
        return "";

    }

    public String cancelar() {
        return "caducacionProxima";
    }

    public List<DetalleMedicamentos> getMedFiltrado() {
        return oMedFiltrado;
    }

    public void setMedFiltrado(List<DetalleMedicamentos> oDetMedFiltrado) {
        this.oMedFiltrado = oDetMedFiltrado;
    }

    public void paraImpresion() {
        setVerEdicion(false);
    }

    public boolean getVerEdicion() {
        return verEdicion;
    }

    public void setVerEdicion(boolean verEdicion) {
        this.verEdicion = verEdicion;
    }

    public void muestraInformacion(DetalleMedicamentos m) {
        setDetalleMedicamento(m);
        setMuestraInfo(true);
    }

    public void cierraInfo() {
        muestraInfo = false;
    }

    public boolean getMuestraInfo() {
        return muestraInfo;
    }

    public void setMuestraInfo(boolean muestraInfo) {
        this.muestraInfo = muestraInfo;
    }

    public DetalleMedicamentos getDetalleMedicamento() {
        return DetalleDetalleMedicamentos;
    }

    public void setDetalleMedicamento(DetalleMedicamentos Detalle) {
        this.DetalleDetalleMedicamentos = Detalle;
    }

    public DetalleMedicamentos[] getLista() throws Exception {
        arrDetMed = null;
        cargaLista();
        return arrDetMed;
    }

    public DetalleMedicamentos[] getListaEdic() {
        return arrDetMed;
    }

    public void setLista(DetalleMedicamentos[] arrDetMed) {
        this.arrDetMed = arrDetMed;
    }

    public boolean isPermiso() {
        return permiso;
    }

    public void setPermiso(boolean permiso) {
        this.permiso = permiso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
