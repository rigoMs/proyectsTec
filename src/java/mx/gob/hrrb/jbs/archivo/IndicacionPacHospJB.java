package mx.gob.hrrb.jbs.archivo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.archivo.PrestamoExp;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.modelo.core.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author JDanny
 */
@ManagedBean (name = "oIndPacHosp")
@ViewScoped
public class IndicacionPacHospJB {
    private Usuario oUsu;
    private ProcedimientosRealizados oProReal;
    private Paciente oListaPac[], oPac;
    private boolean bNom, bExp;
    
    public IndicacionPacHospJB(){
        HttpServletRequest req;
        oUsu=new Usuario();
        req=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm") != null){
            oUsu.setIdUsuario(((Firmado) req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
        }
        oPac=new Paciente();
        bNom=true;
        bExp=false;
    }
    
    public void buscarPacientes(int valor){
        String msg="";
        oListaPac=null;
        try{
            oPac.setOpcionUrg(valor);
            oListaPac=oPac.buscarExp();
        }catch(Exception e){
            e.printStackTrace();
            FacesContext context= FacesContext.getCurrentInstance();
            msg="Error al buscar pacientes";
            context.addMessage(null, new FacesMessage("Indicaciones", msg));
        }
    }
    
    public void buscaIndicaciones(long folioPaciente){
            try{                
                oProReal= new ProcedimientosRealizados();
                oProReal.inicializar();
                oProReal.getPaciente().setFolioPaciente(folioPaciente);
                oProReal=oProReal.buscaProcedimientoHojaIndicaciones();
                if(oProReal!=null){
                    //RequestContext.getCurrentInstance().update(""); 
                    RequestContext.getCurrentInstance().execute("PF('dlgIngDatos').show();");
                }
                else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Aviso","El paciente seleccionado no tiene cirugía programada"));
                }
            }catch(Exception ex){Logger.getLogger(IndicacionPacHospJB.class.getName()).log(Level.SEVERE,null,ex);}
    }
    
    public ProcedimientosRealizados getProcedimiento(){
        return oProReal;
    }
    
    public void setProcedimiento(ProcedimientosRealizados oProReal){
        this.oProReal=oProReal;
    }
    
    public Paciente[] getListaPac(){
        return oListaPac;
    }
    
    public Paciente getPac(){
        return oPac;
    }
    
    public void setPac(Paciente oPac){
        this.oPac=oPac;
    }
    
     public boolean getNom() {
        return bNom;
    }

    public void setNom(boolean bNom) {
        this.bNom = bNom;
    }
    
    public boolean getExp() {
        return bExp;
    }

    public void setExp(boolean bExp) {
        this.bExp = bExp;
    }
    
    public void requerir(TabChangeEvent event){        
        if(event.getTab().getId().equals("tabE")){
            bNom=false;
            bExp=true;
        }else if(event.getTab().getId().equals("tabN")){
            bNom=true;
            bExp=false;
        }
        //if(event.getTab()==)
    }
    
}