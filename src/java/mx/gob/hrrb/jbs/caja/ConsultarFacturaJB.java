package mx.gob.hrrb.jbs.caja;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.caja.reportes.ReporteFacturas;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConsultarFac")
@ViewScoped
public class ConsultarFacturaJB {
    private ReporteFacturas oRpt;
    private ArrayList<ReporteFacturas> arrFacturas;
    private Date dFechaI;
    private Date dFechaF;
    private String sActivaTabla;
    private StreamedContent scFilePDF;
    private StreamedContent scFileXML;

    public ConsultarFacturaJB() {
        sActivaTabla="display: none;";
        oRpt=new ReporteFacturas();
        arrFacturas=new ArrayList();
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscar();
        sActivaTabla="";
    }
        
    public void buscar() throws Exception{
        arrFacturas.addAll(oRpt.buscaFacturas(dFechaI, dFechaF));
    }
    
    public void preDownloadPDF(ActionEvent ae,String sNombre)throws Exception{
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(CuotaRecuperacion.RUTA_PDF_DOWN+sNombre);
        scFilePDF = new DefaultStreamedContent(stream, "application/pdf", sNombre);
    }
    
    public void preDownloadXML(ActionEvent ae,String sNombre)throws Exception{       
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(CuotaRecuperacion.RUTA_XML_DOWN+sNombre);
        scFileXML = new DefaultStreamedContent(stream, "application/xml", sNombre);
    }
    
    public Date getFechaI() {
        return dFechaI;
    }

    public void setFechaI(Date dFechaI) {
        this.dFechaI = dFechaI;
    }

    public Date getFechaF() {
        return dFechaF;
    }

    public void setFechaF(Date dFechaF) {
        this.dFechaF = dFechaF;
    }

    public String getActivaTabla() {
        return sActivaTabla;
    }

    public ArrayList<ReporteFacturas> getFacturas() {
        return arrFacturas;
    }

    public StreamedContent getFilePDF() {
        return scFilePDF;
    }

    public StreamedContent getFileXML() {
        return scFileXML;
    }
    
}
