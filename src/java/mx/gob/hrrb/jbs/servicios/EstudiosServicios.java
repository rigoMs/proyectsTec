package mx.gob.hrrb.jbs.servicios;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.*;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.jbs.core.Firmado;
import java.util.logging.Logger;
/**
 *
 * @author Pablo
 */
@ManagedBean (name="EstServ")
@ViewScoped
public class EstudiosServicios implements Serializable {

    private Firmado oFirmado;
    private HttpServletRequest httpServletRequest;
    private HttpSession session;
    private String sURL;
    private String sUsuario;
    private FacesContext  faceContext;
    private List<Estudios> lEstPorServ;
    public static final int CVE_AREA_IMAGEN = 33;
    public static final int CVE_AREA_LAB = 34;
    public static final int CVE_AREA_PAT= 84;
    public static final int CVE_AREA_ENDOS = 85;
    
    
    public EstudiosServicios() {
    }
    
    public void inicializar(){
        setFirmado(new Firmado());
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("oFirm") != null){
            oFirmado = (Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirmado.getUsu().getIdUsuario();
            sURL = httpServletRequest.getRequestURL().toString().toLowerCase();
        }
    }

    public List<Estudios> getEstudiosImagen(){
        try{
            lEstPorServ = new ArrayList<Estudios>(Arrays.asList(
                    new Estudios().buscarEstudiosPorServicio(CVE_AREA_IMAGEN)));
        }catch(Exception ex){
            Logger.getLogger(EstudiosServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lEstPorServ;
    }
    
    public List<Estudios> getEstudiosLab(){
        try{
            lEstPorServ = new ArrayList<Estudios>(Arrays.asList(
                    new Estudios().buscarEstudiosPorServicio(CVE_AREA_LAB)));
        }catch(Exception ex){
            Logger.getLogger(EstudiosServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lEstPorServ; 
    }
    
    public List<Estudios> getEstudiosPat(){
        try{
            lEstPorServ = new ArrayList<Estudios>(Arrays.asList(
                    new Estudios().buscarEstudiosPorServicio(CVE_AREA_PAT)));
        }catch(Exception ex){
            Logger.getLogger(EstudiosServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lEstPorServ;
    }
    
    public List<Estudios> getEstudiosEndos(){
        try{
            lEstPorServ = new ArrayList<Estudios>(Arrays.asList(
                    new Estudios().buscarEstudiosPorServicio(CVE_AREA_ENDOS)));
        }catch(Exception ex){
            Logger.getLogger(EstudiosServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lEstPorServ;
    }
    
    public Firmado getFirmado() {
        return oFirmado;
    }

    public void setFirmado(Firmado oFirmado) {
        this.oFirmado = oFirmado;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getURL() {
        return sURL;
    }

    public void setURL(String sURL) {
        this.sURL = sURL;
    }

    public String getUsuario() {
        return sUsuario;
    }

    public void setUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }

    public FacesContext getFaceContext() {
        return faceContext;
    }

    public void setFaceContext(FacesContext faceContext) {
        this.faceContext = faceContext;
    }
    
    
    
    
}
