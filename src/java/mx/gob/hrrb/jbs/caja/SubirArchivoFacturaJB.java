package mx.gob.hrrb.jbs.caja;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.core.Paciente;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
/**
 *
 * @author Daniel
 */
@ManagedBean(name="oSubirFactura")
@ViewScoped
public class SubirArchivoFacturaJB implements Serializable{
    private Paciente oPaciente;
    private boolean bEncontrado = false;
    private CuotaRecuperacion oCR;
    private ArrayList<CuotaRecuperacion> arrRecibos;
    private ArrayList<CuotaRecuperacion> arrRecibosFactura;
    private String sNombreArch;
    private InputStream fFacturaXML;
    private InputStream fFacturaPDF;
    private boolean bDisDatosPdf;
    private boolean bDisDatosXml;
    private BigDecimal nTotal;
    
    public SubirArchivoFacturaJB() {
        bDisDatosPdf=true;
        bDisDatosXml=false;
        nTotal=new BigDecimal((Double)(0.0));
        oPaciente = new Paciente();
        oCR=new CuotaRecuperacion();
        arrRecibosFactura=new ArrayList();
        arrRecibos=new ArrayList();
    }
    
    public void preDialog(ActionEvent ae)throws Exception{
        if(arrRecibosFactura.isEmpty()){
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Seleccione al menos un recibo al cual vincular los archivos."));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        else{
            fFacturaXML=null;
            fFacturaPDF=null;
            sNombreArch=null;
            bDisDatosPdf=true;
            bDisDatosXml=false;
            for(CuotaRecuperacion cr:arrRecibosFactura){
                nTotal.add(cr.getMonto());
            }
            showDialog();
        }
    }
    
    public void showDialog(){
        RequestContext.getCurrentInstance().execute("PF('dlgArch').show()");
    }
    
    public void llenar() throws Exception{
    Paciente oPac=null;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        
        oPac=(Paciente)session.getAttribute("oPacienteSeleccionado");
        if(oPac == null || oPac.getFolioPaciente()==0){
            FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡No ha seleccionado un paciente!");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bEncontrado = false;
        }
        else{
            System.out.println(oPac.getNombreCompleto());
            oPaciente = oPac;
            bEncontrado = true;
            arrRecibos.clear();
            arrRecibos.addAll(Arrays.asList(oCR.buscarRecibosPaciente(oPaciente.getFolioPaciente())));
        }
    }
        
    public void limpiar(){
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        session.setAttribute("oPacienteSeleccionado", null);
        bEncontrado = false;
    }

    public Paciente getPaciente() {
        return oPaciente;
    }
    
    public void handleFileUploadXML(FileUploadEvent event) throws IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            fFacturaXML=event.getFile().getInputstream();
            sNombreArch=arrRecibosFactura.get(0).getFolio()+"";
            bDisDatosPdf=false;
            bDisDatosXml=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void handleFileUploadPDF(FileUploadEvent event) throws IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            fFacturaPDF=event.getFile().getInputstream();
            guardaArchivo(fFacturaXML,CuotaRecuperacion.RUTA_XML_UP,sNombreArch+".xml");
            guardaArchivo(fFacturaPDF,CuotaRecuperacion.RUTA_PDF_UP,sNombreArch+".pdf");
            oCR.asociarFacturaCuotaRecuperacion(arrRecibosFactura, sNombreArch);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Atención","Se han almacenado los archivos exitosamente."));
            arrRecibos.clear();
            arrRecibos.addAll(Arrays.asList(oCR.buscarRecibosPaciente(oPaciente.getFolioPaciente())));
            RequestContext.getCurrentInstance().execute("PF('dlgArch').hide()");
        }catch (Exception e){
            e.printStackTrace();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error","Error al almacenar los archivos."));
        }
    }
    
    public void guardaArchivo(InputStream Archivo,String Ruta,String sNomArch) throws IOException{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    String sRuta=facesContext.getExternalContext().getRealPath("");
    OutputStream out = null;
    InputStream filecontent = null;
    byte[] bytes = new byte[1024];
    int nLeidos = 0;
        try {
            out = new FileOutputStream(new File(sRuta+Ruta+sNomArch));
            System.out.println(sRuta+Ruta+sNomArch);
            filecontent =Archivo;
            while ((nLeidos = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, nLeidos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    } 
    
    
    public String getVisible(){
        if (this.bEncontrado)
            return "visible;";
        else
            return "hidden;";
    }
    
    public void setReciboFactura(CuotaRecuperacion oCR){
        arrRecibosFactura.add(oCR);
    }

    public CuotaRecuperacion getCR() {
        return oCR;
    }

    public void setCR(CuotaRecuperacion oCR) {
        this.oCR = oCR;
    }

    public ArrayList<CuotaRecuperacion> getRecibos() {
        return arrRecibos;
    }

    public void setRecibos(ArrayList<CuotaRecuperacion> arrRecibos) {
        this.arrRecibos = arrRecibos;
    }

    public ArrayList<CuotaRecuperacion> getRecibosFactura() {
        return arrRecibosFactura;
    }

    public void setRecibosFactura(ArrayList<CuotaRecuperacion> arrRecibosFactura) {
        this.arrRecibosFactura = arrRecibosFactura;
    }

    public boolean isDisDatosPdf() {
        return bDisDatosPdf;
    }

    public boolean isDisDatosXml() {
        return bDisDatosXml;
    }

    public BigDecimal getTotal() {
        return nTotal;
    }
    
}
