
package mx.gob.hrrb.jbs.archivo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.archivo.PrestamoExp;
import mx.gob.hrrb.modelo.core.Paciente;
import org.primefaces.context.RequestContext;

/**
 *
 * @author JDanny
 */
@ManagedBean (name = "oOrdenApertura")
@ViewScoped

public class OrdenAperturaExpJB {
    private Medico oMedFirm;
    private EpisodioMedico oEpMed;
    private PrestamoExp oPresExp;
    private DiagnosticoCIE10 oCIE10;
    private AreaServicioHRRB oSerIni;
    private AreaServicioHRRB oEspSolicitada;
    private boolean bVisualiza=false;
    private boolean bVisualiza2=false;
    private String sugiere;
    Paciente oPac, oListaPac[];
    
    
    
    public OrdenAperturaExpJB() throws Exception{
        HttpServletRequest req;
        oMedFirm = new Medico();
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm") != null){
            oMedFirm.setIdUsuario(((Firmado) req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            //oMedFirm.buscaUsuarioFirmado();
            oMedFirm.buscaPersonalHospitalarioDatos();
        }
        //oEpMed=new EpisodioMedico();
        oPac= new Paciente();
        oPresExp= new PrestamoExp();
        oCIE10= new DiagnosticoCIE10();
        oSerIni= new AreaServicioHRRB();
        oEspSolicitada= new AreaServicioHRRB();
    }
    
    public void buscaPacientes(){
        String msg="";
        oListaPac=null;
        try{
            oListaPac=oPac.buscarPac();
        }catch(Exception e){
            e.printStackTrace();
            FacesContext context= FacesContext.getCurrentInstance();
            msg="error al buscar pacientes";
            context.addMessage(null, new FacesMessage("Orden apertura", msg));
        }
    }

    public void buscaPac(long fPac){
        try{
            oEpMed= new EpisodioMedico();
            oEpMed=oPresExp.buscaPaciente(fPac);
            if(oEpMed == null){
                bVisualiza=false;
                bVisualiza2=true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","NO SE CARGARON LOS DATOS"));
            }else{
                bVisualiza=true;
                bVisualiza2=false;
        }
        }catch(Exception e){
            Logger.getLogger(EpisodioMedico.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void imprime(){
        /*
        if(oSerIni.getDescripcion().equals(""))
            RequestContext.getCurrentInstance().update(":frmBusqueda :frmBusqueda:msgs");
        if(oCIE10.getDescripcionDiag().equals(""))
            RequestContext.getCurrentInstance().update(":frmBusqueda :frmBusqueda:msgs");
        if(getSugiere().equals(""))
            RequestContext.getCurrentInstance().update(":frmBusqueda :frmBusqueda:msgs"); 
        if(oEspSolicitada.getDescripcion().equals(""))
            RequestContext.getCurrentInstance().update(":frmBusqueda :frmBusqueda:msgs"); 
        */
        if(oSerIni.getDescripcion()!="" && oCIE10.getDescripcionDiag()!="" && getSugiere()!="" && oEspSolicitada.getDescripcion()!=""){
            //RequestContext.getCurrentInstance().update(""); 
            RequestContext.getCurrentInstance().execute("PF('dlgImp').show();");
        }
    }
    
    
    public boolean muestraBottom(long n){
        if(n>0) return true; else return false;
    }
    
    public void invierteBanderas(){
        bVisualiza=false;
        bVisualiza2=true;
    }
    
    public void invierteBanderas2(){
        bVisualiza=false;
        bVisualiza2=false;
    }
    
    
    public Medico getMedicoFirmado(){return oMedFirm;}
    
    public EpisodioMedico getEpisodioMed(){return oEpMed;}
    public void setEpisodioMed(EpisodioMedico oEpMed){this.oEpMed=oEpMed;}
 
    public DiagnosticoCIE10 getCIE10(){return oCIE10;}
    public void setCIE10(DiagnosticoCIE10 oCIE10){this.oCIE10=oCIE10;}
    
    public AreaServicioHRRB getSerIni(){return oSerIni;}
    
    public AreaServicioHRRB getEspSolicitada(){return oEspSolicitada;}
    
    public String getSugiere(){
        return sugiere;
    }
    public void setSugiere(String value){
        sugiere=value.toUpperCase();
    }
    
    public boolean getVisualiza(){return bVisualiza;}
    public void setVisualiza(boolean b){bVisualiza=b;}
    
    public boolean getVisualiza2(){return bVisualiza2;}
    public void setVisualiza2(boolean b){bVisualiza2=b;}
    
    public String getFecha(){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(new Date());
    }
    
    public Paciente[] getListaPac(){
        return oListaPac;
    }
    
    public Paciente getPaciente(){
        return oPac;
    }
    
    public void setPaciente(){
        this.oPac=new Paciente();
    }
}