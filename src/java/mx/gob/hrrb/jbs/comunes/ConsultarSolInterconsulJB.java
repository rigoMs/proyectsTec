package mx.gob.hrrb.jbs.comunes;

import java.util.ArrayList;
import java.util.Arrays;
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
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.serv.EstudioInterconsulta;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael
 */
@ManagedBean (name="oConInterconsulta")
@ViewScoped
public class ConsultarSolInterconsulJB {
    private AccesoDatos oAD;
    private Medico oMedFirm;
    private EpisodioMedico oEpisodioMedico;
    private EpisodioMedico oSeleccionado;
    private EstudioInterconsulta oEstInter;
    private AreaServicioHRRB oAreaServ;
    private String sVisibilidadTabla="hidden";
    private Date dFechaAux;
    private Date dFechaActual;
    private ArrayList<EpisodioMedico> arrConsulta;
    private boolean bBuscado = false;
    
    public ConsultarSolInterconsulJB() throws Exception{
        HttpServletRequest req;
        oMedFirm=new Medico();
        oMedFirm.setUsuar(new Usuario());
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm")!=null){
            oMedFirm.getUsuar().setIdUsuario(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            oMedFirm.buscaUsuarioFirmado();
        }
        oEstInter = new EstudioInterconsulta();
        oEpisodioMedico = new EpisodioMedico();
    }
    
            public Medico getMedFirm() {
                return oMedFirm;
            }

            public void setMedFirm(Medico oMedFirm) {
                this.oMedFirm = oMedFirm;
            }
            
    public void buscaPacienteFechaConsulta(){
        sVisibilidadTabla="visible";
        try{
            arrConsulta = new ArrayList<EpisodioMedico>(Arrays.asList(oEpisodioMedico.buscarPacientesInterconsulta(dFechaAux, oMedFirm.getArea().getClave())));
            oSeleccionado = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public EpisodioMedico getSeleccionado(){
        return oSeleccionado;
    }

    public void setSeleccionado(EpisodioMedico oSeleccionado){
                this.oSeleccionado = oSeleccionado;
    }

            public ArrayList<EpisodioMedico> getListaCitado(){
                return arrConsulta;
            }
            
    public void llenadoDatosPersonales() throws Exception{
                    if(oSeleccionado==null){
                FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡Seleccione un paciente!");
                RequestContext.getCurrentInstance().showMessageInDialog(msj2);
                bBuscado = false;
            }else{
                        oEpisodioMedico = oSeleccionado;
                bBuscado=true;
                        sVisibilidadTabla="hidden";
            }
    }  

            public String getVisible(){
                if(this.bBuscado)
                    return "visible";
                  else
                    return "hidden";                
    }
    
    public void cambiarRealizado() throws Exception {
                    if(oSeleccionado==null){
                        FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡Seleccione un paciente!");
                        RequestContext.getCurrentInstance().showMessageInDialog(msj2);
                        bBuscado = false;
                    }else{
                        oEpisodioMedico = oSeleccionado;
                        bBuscado=true;
                        sVisibilidadTabla="hidden";
                        
                        oEstInter.setEpisodio(oEpisodioMedico);
                        System.out.println("IDENTIFICADOR: "+this.getEstInter().getEpisodio().getEstInter().getIdentificador());
                        try {
                            if (oEstInter.modificarSituacionInter()== 1) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Interconsulta", "Interconsulta realizada"));
                            }
                        } catch (Exception e) {
                            Logger.getLogger(ConsultarSolInterconsulJB.class.getName()).log(Level.SEVERE, null, e);
                        }
                        
                    }
            }
            
    public AccesoDatos getAD() {
        return oAD;
    }

    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }


    public String getVisibilidadTabla(){
        return this.sVisibilidadTabla;
    }


    public Date getFechaAux() {
        return dFechaAux;
    }

    public void setFechaAux(Date dFechaAux) {
        this.dFechaAux = dFechaAux;
    }


    public EstudioInterconsulta getEstInter() {
        return oEstInter;
    }

    public void setEstInter(EstudioInterconsulta oEstInter) {
        this.oEstInter = oEstInter;
    }


    public Date getFechaActual() {
        return dFechaActual;
    }

    public void setFechaActual(Date dFechaActual) {
        this.dFechaActual = dFechaActual;
    }

    public AreaServicioHRRB getAreaServ() {
        return oAreaServ;
    }

    public void setAreaServ(AreaServicioHRRB oAreaServ) {
        this.oAreaServ = oAreaServ;
    }

    public EpisodioMedico getEpisodioMedico() {
        return oEpisodioMedico;
}

    public void setEpisodioMedico(EpisodioMedico oEpisodioMedico) {
        this.oEpisodioMedico = oEpisodioMedico;
    }
}
