/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import mx.gob.hrrb.modelo.consultaexterna.Permiso;
import mx.gob.hrrb.modelo.core.Medico;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 */
@ManagedBean(name="oCamCons")
@SessionScoped
public class CambiarConsultorio {
    private AsignaConsultorio oAsigCon;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private FacesMessage facesMessage;
    private Firmado oFirm;
    private String sUsuario;
    private String mostrar="none";
    private Medico oMedico;
    private Medico oMedico2;
    private AsignaConsultorio[] arrAsig = null;
    private AsignaConsultorio[] Asigna;
    private Permiso oPer;
    /**
     * Creates a new instance of CambiarConsultorio
     */
    public CambiarConsultorio() {
        oAsigCon=new AsignaConsultorio();
        oMedico=new Medico();
        oMedico2=new Medico();
        oPer=new Permiso();
    }
    
    public AsignaConsultorio[] getListaCitasPorMedico(){
        try{
            return this.getAsigCon().buscaCitasPorMedico();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public AsignaConsultorio getAsigCon() {
        return oAsigCon;
    }

    public void setAsigCon(AsignaConsultorio oAsigCon) {
        this.oAsigCon = oAsigCon;
    }
    
    public String getMostrar() {
        return mostrar;
    }
    
    public void mostrarTabla(int numero){
        int opc=1;
        if(opc ==numero)
            setMostrar("block");

        System.out.println(getMostrar());
    }
    
    public Date getFechaHoy(){
        Date x=new Date();
        return x;
    }
    
    public void setMostrar(String mostrar) {
        this.mostrar = mostrar;
    }
    
    public String renuevaFecha(){
        String pag="/faces/sesiones/consultaexterna/CambiarConsultorio.xhtml";
        return pag;
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
    
   /* public List<AsignaConsultorio> getListaAsignacion(){
       List<AsignaConsultorio> lLista = null;
       try {
           lLista = new ArrayList<AsignaConsultorio>(Arrays.asList(
                   (oAsigCon).buscarPacPorMedico()));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }*/
    
     public AsignaConsultorio[] getAsigna(){
        try{
            Asigna = (AsignaConsultorio[])oAsigCon.buscarPacPorMedico();
        } catch(Exception e){
            e.printStackTrace();
        }
        return Asigna;
    }
       
    public Medico getMedico() {
        return oMedico;
    }

    public void setMedico(Medico oMedico) {
        this.oMedico = oMedico;
    }

    public Medico getMedico2() {
        return oMedico2;
    }

    public void setMedico2(Medico oMedico2) {
        this.oMedico2 = oMedico2;
    }
    
    public String actualizaCambio() throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat form=new SimpleDateFormat("EEEE");
        FacesMessage message=null;
        String v[]=oMedico2.getNombres().split("-");
        int newMax=oMedico2.buscarMaximoPorDia(v[0], v[4], oAsigCon.getFechaConsulta(), v[2]);
        String pagina="CambiarConsultorio";
        int n=0;

        String vec[]=getMedico2().getNombres().split("-");
        if(oAsigCon.getCitados()<=newMax){
            if (oPer.buscar(oMedico2.getNombres().split("-"), oAsigCon.getFechaConsulta())==false){
            if(vec.length>0 && oAsigCon.getTurno().getClave().substring(0, 3).compareTo(vec[4].substring(0, 3))==0){
            n=oAsigCon.getCita().cambiarMedicoDeCitas(df.format(oAsigCon.getFechaConsulta()), oAsigCon.getAreaServicio().getClave(), oAsigCon.getCons().getNoConsultorio(),oAsigCon.getPH().getNoTarjeta(),v[1], v[2], v[0], oAsigCon.getTurno().getClave().substring(0, 3));
                    if (n==0){
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "Cambio fallido :(");
                    pagina="CambiaPacDeCons";
                        }else{
                            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "Cambio Exitoso!!");
                            oMedico=new Medico();
                            oMedico2=new Medico();
                            oAsigCon=new AsignaConsultorio();
         }
            }
            else{
              message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "El turno de los 2 médicos es distinto :(");
              pagina="CambiaPacDeCons";
            }
         }else{
               message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "El médico seleccionado tiene un permiso en esa fecha");
            pagina="CambiaPacDeCons"; 
            }
        }
        else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "El número de pacientes citados supera al máximo de pacientes que puede tener ese Médico");
            pagina="CambiaPacDeCons";
        }
         RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pagina;
    }
    
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
                         wb.setSheetName(0,"Pacientes"); //nombre de la hoja de excel
                     sheet.shiftRows (0, 50, +4);
                     
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)6)); 
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("BITÁCORA DEL: "+oAsigCon.getFechaConsulTexto()+"   -   CONSULTORIO: "+oAsigCon.getCons().getNoConsultorio()+"  -  SERVICIO: "+oAsigCon.getAreaServicio().getDescripcion());
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)6));
        
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font0 = wb.createFont();
        font0.setFontName(HSSFFont.FONT_ARIAL);
        font0.setFontHeightInPoints((short) 11);
        font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font0.setColor(HSSFColor.BLACK.index);
        style.setFont(font0);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellStyle(style); //*********************
        
        row =sheet.createRow((short)2);
        cell=row.createCell((short)0);
        cell.setCellValue("MÉDICO: "+oAsigCon.getPH().getNoTarjeta()+" - "+oAsigCon.getPH().getApPaterno()+" "+oAsigCon.getPH().getApMaterno()+" "+oAsigCon.getPH().getNombres()+" - TURNO: "+oAsigCon.getTurno().getClave());
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)6));
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellStyle(style);
        
        
        HSSFRow header = sheet.getRow(4);
        HSSFCellStyle cellStyle = wb.createCellStyle(); 
        //cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);        
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        cellStyle.setFont(font0);
            sheet.setColumnWidth(0, 3800);
            sheet.setColumnWidth(1, 3500);
            sheet.autoSizeColumn(2);
            sheet.setColumnWidth(3, 4200);
            sheet.setColumnWidth(4, 4300);
            sheet.autoSizeColumn(5);
            sheet.setColumnWidth(6, 7600);
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);} 
        
        HSSFCellStyle styleTit = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 16);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        styleTit.setFont(font);
        styleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        header = sheet.getRow(0);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(styleTit);} 
    }
}
