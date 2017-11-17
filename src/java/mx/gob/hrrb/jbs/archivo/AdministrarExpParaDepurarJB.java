package mx.gob.hrrb.jbs.archivo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Expediente;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
//import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author JDanny
 */
@ManagedBean (name = "oExpParaDep")
@ViewScoped
public class AdministrarExpParaDepurarJB{
    private Expediente oExp;
    private List<EpisodioMedico> lFilteredExpDev;
    private EpisodioMedico[] lFilteredExpDev2;
    private List<EpisodioMedico> lFilteredExpDev3;
    private EpisodioMedico[] oListaExpDepuradosPorAño;
    private int nAño, nMaxAño;
    
    public AdministrarExpParaDepurarJB(){
        oExp= new Expediente();
        cargaLista();
        determinaAño();
        nAño=0;
    }
    
    public boolean filterByDate(Object value, Object filter, Locale locale) {
        if( filter == null ) return true;
        if( value == null )  return false;
        return DateUtils.truncatedEquals((Date) filter, (Date) value, Calendar.DATE);
    }
    
    public void filtraPorFecha(){
        RequestContext.getCurrentInstance().execute("PF('tableDep').filter()");
    }
    
    public void buscaExpDepuradosPorFecha(){
        String msg="";
        if(getAño()>0){
            try{
                validaAño();
                oListaExpDepuradosPorAño=oExp.buscaExpedientesDepuradosPorCriterio(getAño());
                lFilteredExpDev2=oListaExpDepuradosPorAño;
                //RequestContext.getCurrentInstance().update("dtExpDepurados");
                //nAño=0;
            }catch(Exception e){
                e.printStackTrace();
                FacesContext context= FacesContext.getCurrentInstance();
                msg="error al buscar expedientes depurados por año";
                context.addMessage(null, new FacesMessage("Expedientes depurados", msg));
            }    
        }else{
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Expedientes depurados","No se acepta el año"));
        }
    }
    
    public List<EpisodioMedico> getListaExpParaDepurar(){
        List<EpisodioMedico> lLista =null;
        try{
            lLista= new ArrayList<EpisodioMedico>(Arrays.asList((new Expediente().buscaDatosExpParaDepurar())));
        }catch(Exception e){
            e.getMessage();
            Logger.getLogger(AdministrarExpParaDepurarJB.class.getName()).log(Level.SEVERE, null, e);
        }
        return lLista;
    }
    
    public void registrarDepuracion(int num){
        try{
            oExp.setNumero2(num);
            if((oExp.insertaDepuracionExp())!=0){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Depuración expediente","Registro exitoso!"));
                lFilteredExpDev=null;
                lFilteredExpDev3=new ArrayList<EpisodioMedico>(Arrays.asList((new Expediente().buscaExpedientesDepurados())));
                RequestContext.getCurrentInstance().update("tabExpDep2");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Depuración expediente","Registro fallido"));
            }
        }catch(Exception e){
            e.getMessage();
            Logger.getLogger(AdministrarExpParaDepurarJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public List<EpisodioMedico> getListaExpDepurados(){
        List<EpisodioMedico> lLista =null;
        try{
            lLista= new ArrayList<EpisodioMedico>(Arrays.asList((new Expediente().buscaExpedientesDepurados())));
        }catch(Exception e){
            e.getMessage();
            Logger.getLogger(AdministrarExpParaDepurarJB.class.getName()).log(Level.SEVERE, null, e);
        }
        return lLista;
    }
    
    public void cargaLista(){
        try{
            lFilteredExpDev=new ArrayList<EpisodioMedico>(Arrays.asList((new Expediente().buscaDatosExpParaDepurar())));
            lFilteredExpDev3=new ArrayList<EpisodioMedico>(Arrays.asList((new Expediente().buscaExpedientesDepurados())));
        }catch(Exception e){
            Logger.getLogger(AdmPrestamoExpJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public List<EpisodioMedico> getListFiltered(){
        return lFilteredExpDev;
    }
    
    public void setListFiltered(List<EpisodioMedico> lFilteredExpDev){
        this.lFilteredExpDev=lFilteredExpDev;
    }
    
    public EpisodioMedico[] getListFiltered2(){
        return lFilteredExpDev2;
    }
    
    public void setListFiltered2(EpisodioMedico[] lFilteredExpDev2){
        this.lFilteredExpDev2=lFilteredExpDev2;
    }
    
    public List<EpisodioMedico> getListFiltered3(){
        return lFilteredExpDev3;
    }
    
    public void setListFiltered3(List<EpisodioMedico> lFilteredExpDev3){
        this.lFilteredExpDev3=lFilteredExpDev3;
    }
    
    public EpisodioMedico[] getListaExpDepuradosPorAño(){
        return oListaExpDepuradosPorAño;
    }
    
    private void determinaAño(){
        Date dF= new Date();
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        String v[]=f.format(dF).split("-");
        //this.setAño(Integer.parseInt(v[0]));
        nMaxAño=Integer.parseInt(v[0]);
    }
    
    public void validaAño(){
        String mess="";
        //System.out.println("año seleccionado: "+nAño);
        if (nAño !=0){
            if (nAño<=2010)
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","El año debe ser mayor a 2010"));    
            if (nAño>nMaxAño)
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","El año debe ser menor o igual a: " +nMaxAño));                
        }
    }    
    
    public void renderizar(TabChangeEvent event) throws Exception{        
        if(event.getTab().getId().equals("tabExpDep2")){
            if(getListFiltered3()==null)
                lFilteredExpDev3=new ArrayList<EpisodioMedico>(Arrays.asList((new Expediente().buscaExpedientesDepurados())));
        }
        //if(event.getTab()==)
    }
    
    //*************************************************************************
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"Expedientes_Depurados "+getAño()); //nombre de la hoja de excel
        sheet.shiftRows (0, 63768, +3);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)8));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("EXPEDIENTES DEPURADOS EN "+getAño() );
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)8));
        
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA: "+obtenFechaAux());
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)8));
        
        
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
        
        HSSFCellStyle cellStyleSubTit2 = wb.createCellStyle();         
        cellStyleSubTit2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyleSubTit2.setFont(font);
        
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
        
        //sheet.autoSizeColumn(0);
        sheet.setColumnWidth(0, 4200);
        sheet.autoSizeColumn(1);
        sheet.setColumnWidth(2, 4200);
        sheet.setColumnWidth(3, 3500);
        sheet.setColumnWidth(4, 4700);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 4700);
        sheet.setColumnWidth(7, 4700);
        sheet.setColumnWidth(8, 4700);
        
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleTit);
        }
        header = sheet.getRow(1);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(2);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit2);
        }
        
        header = sheet.getRow(3);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
    }
    
    /************************************************************************************************/
    public void postProcessXLS2(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"Rpt_Expedientes_Depurados"); //nombre de la hoja de excel
        sheet.shiftRows (0, 63768, +3);
        
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new Region(0,(short)0, 0, (short)8));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("REPORTE EXPEDIENTES DEPURADOS");
        sheet.addMergedRegion(new Region(1,(short)0, 1, (short)8));
        
        row = sheet.createRow((short)2);
        cell = row.createCell((short)0);
        cell.setCellValue("FECHA: "+obtenFechaAux());
        sheet.addMergedRegion(new Region(2,(short)0, 2, (short)8));
        
        
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
        
        HSSFCellStyle cellStyleSubTit2 = wb.createCellStyle();         
        cellStyleSubTit2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyleSubTit2.setFont(font);
        
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
        
        //sheet.autoSizeColumn(0);
        sheet.setColumnWidth(0, 4200);
        sheet.autoSizeColumn(1);
        sheet.setColumnWidth(2, 4200);
        sheet.setColumnWidth(3, 3500);
        sheet.setColumnWidth(4, 4700);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 4700);
        sheet.setColumnWidth(7, 4700);
        sheet.setColumnWidth(8, 4700);
        
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleTit);
        }
        header = sheet.getRow(1);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit);
        }
        header = sheet.getRow(2);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyleSubTit2);
        }
        
        header = sheet.getRow(3);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
    }
     
    /**
     * @return the nAño
     */
    public int getAño() {
        return nAño;
    }

    /**
     * @param dAño the nAño to set
     */
    public void setAño(int dAño) {
        this.nAño = dAño;
    }
    
    public int getMaxAño() {
        //System.out.println(nMaxAño);
        return nMaxAño;
    }
    
    public String obtenFechaAux(){
        String f="";
        SimpleDateFormat fechaHora=new SimpleDateFormat("dd/MM/yyyy HH:mm");      
        f = fechaHora.format(new Date());
        return f;
    }
}