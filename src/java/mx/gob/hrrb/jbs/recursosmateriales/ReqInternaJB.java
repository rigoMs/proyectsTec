package mx.gob.hrrb.jbs.recursosmateriales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.recursosmateriales.PartidaPresupuestal;
import mx.gob.hrrb.modelo.recursosmateriales.RequisicionInterna;


/**
 *
 * @author DavidH
 */
@ManagedBean(name="oRequiInt")
@SessionScoped
public class ReqInternaJB implements Serializable {
    
    private PersonalHospitalario oPersonal;
    private PartidaPresupuestal oPartida;
    private Firmado oFirm;
    private String sUsuario;
    private AccesoDatos oAD;
    private String url;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private PartidaPresupuestal[] lListaPartidas;
    //private RequisicionInterna oRequisicion;
    

    /**
     * Creates a new instance of ReqInternaJB
     */
    public ReqInternaJB() throws Exception {
       oPartida = new PartidaPresupuestal();
       //oPersonal = new PersonalHospitalario(); 
       cargaDatos();
       /*oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            oPersonal.buscaPersonalHospitalarioDatos();
            //oPersonal.
            url=httpServletRequest.getRequestURL().toString().toLowerCase();
            }
        try{
        //bper=oPerfil.buscaPerfilCE();
        }catch(Exception e){}*/
    }
    
    public PartidaPresupuestal[] cargaDatos(){
        try{
            lListaPartidas = oPartida.buscaTodas();
        }catch (Exception e) {
            Logger.getLogger(PartidaPresupuestal.class.getName()).log(Level.SEVERE,null,e);
        }
        return lListaPartidas;
    }

    public PartidaPresupuestal getPartida() {
        return oPartida;
    }

    public void setPartida(PartidaPresupuestal oPartida) {
        this.oPartida = oPartida;
    }

    public PartidaPresupuestal[] getListaPartidas() {
        return lListaPartidas;
    }

    public PersonalHospitalario getPersonal() {
        return oPersonal;
    }

    public void setPersonal(PersonalHospitalario oPersonal) {
        this.oPersonal = oPersonal;
    }

    
    
    
    
}
