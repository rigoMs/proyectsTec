package mx.gob.hrrb.jbs.archivo;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.enfermeria.reporte.RptCensoMensualEnfermeria;

/**
 *
 * @author JDanny
 */
@ManagedBean(name="oCMEarchJB")
@ViewScoped
public class CensoMensualEnferiaJB {
    private RptCensoMensualEnfermeria oRptMenEnfer;
    private RptCensoMensualEnfermeria[] oListaCensoMensual;
    
    private Hospitalizacion[] oListaDefunCirugia;
    private Hospitalizacion[] oListaDefunGO;
    private Hospitalizacion[] oListaDefunMI;
    private Hospitalizacion[] oListaDefunNEO;
    private Hospitalizacion[] oListaDefunPED;
    private boolean bRender;
    
    public CensoMensualEnferiaJB(){
        oRptMenEnfer=new RptCensoMensualEnfermeria();
        bRender=false;
    }
    
    public void buscaInformacion(){
        String msg="";
        if(oRptMenEnfer.getFechaIni()!=null && oRptMenEnfer.getFechaFin()!=null){
            try{
                oListaCensoMensual=oRptMenEnfer.buscaRptMensualEnfermeria();
                oListaDefunCirugia=oRptMenEnfer.buscaDefuncionesPorServicio(13);
                oListaDefunGO=oRptMenEnfer.buscaDefuncionesPorServicio(29);
                oListaDefunMI=oRptMenEnfer.buscaDefuncionesPorServicio(37);
                oListaDefunNEO=oRptMenEnfer.buscaDefuncionesPorServicio(39);
                oListaDefunPED=oRptMenEnfer.buscaDefuncionesPorServicio(56);
                bRender=true;
                
            }catch(Exception e){
                e.printStackTrace();
                bRender=false;
                FacesContext context= FacesContext.getCurrentInstance();
                msg="Error al buscar información";
                context.addMessage(null, new FacesMessage("Censo Mensual Enfermería", msg));
            }
        }else{
            bRender=false;
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Censo Mensual Enfermería "," No ha especificado fecha"));
        }
    }
    
    public void validaFecha(){
        String mess="";
        if (oRptMenEnfer.getFechaIni()!=null && oRptMenEnfer.getFechaFin()!=null)
            if ((oRptMenEnfer.getFechaIni()).compareTo((oRptMenEnfer.getFechaFin()))>0)
                mess="La fecha final del periodo debe ser posterior a la fecha de inicio";
        if (!mess.equals("")){
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Aviso",mess));
        }
    }
    
     //PARA EXPORTAR ARCHIVOS
    public void preProcessPDF(Object document) throws IOException, 
            BadElementException, DocumentException {
    Document pdf = (Document) document;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String sTit="CENSO MENSUAL DE ENFERMERIA";
       //pdf.setPageSize(PageSize.A4.rotate());
       pdf.open();
       pdf.add(new Phrase(sTit+"\nFecha: " + formato.format(new Date())+"\n"));
       //pdf.add(new Phrase("Total Pagos de Préstamos: $ " +"       "+"\n"));
       
       //pdf.add(new Phrase("\t \t \t \t Hora \t Folio \t\t\t\t\t\t\t Folio del Personal \t\t\t\t\t\t\t\t\t\t Nombre Empleado \t Total del Préstamo \t Fecha del Préstamo  \t Folio del Préstamo \t Pagos Realizados \t Condición \t Pago del Dia \t Saldo por Pagar \t Forma de Pago"));
       //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
       //String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
    }
    
    public RptCensoMensualEnfermeria[] getListaCENSO(){
        return oListaCensoMensual;
    }
    
    public Hospitalizacion[] getListaDefuncionesCIRUGIA(){
        return oListaDefunCirugia;
    }
    
    public Hospitalizacion[] getListaDefuncionesGO(){
        return oListaDefunGO;
    }
    
    public Hospitalizacion[] getListaDefuncionesMI(){
        return oListaDefunMI;
    }
    
    public Hospitalizacion[] getListaDefuncionesNEO(){
        return oListaDefunNEO;
    }
    
    public Hospitalizacion[] getListaDefuncionesPED(){
        return oListaDefunPED;
    }
    
    public RptCensoMensualEnfermeria getObjReporte(){
        return oRptMenEnfer;
    }
    
    public boolean getRender(){
        return bRender;
    }
}