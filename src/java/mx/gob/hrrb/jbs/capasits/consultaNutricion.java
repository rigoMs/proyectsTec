package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.capasits.CitaNutricionCapasits;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.Cita;

/**
 *
 * @author pedro
 */
@ManagedBean (name="nutricion")
@SessionScoped
public class consultaNutricion implements Serializable{
    private CitaNutricionCapasits oNutCap;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private Cita[] arrAgen = null;
    private Cita agen;


    public consultaNutricion(){
       oNutCap=new CitaNutricionCapasits();
       agen=new Cita();
       oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
    }
    
    public CitaNutricionCapasits getCNutCpa(){
        return oNutCap;
    }
    
    public Cita[] getLista() throws Exception{
        agen.buscarUsuarioMedico(sUsuario);
        if(this.agen.getFechaCita()==null){
        Date fecha=new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String fec=ff.format(fecha);
            Date fech=ff.parse(fec);
        this.agen.setFechaCita(fech);
        }  
        try{
            arrAgen = (Cita[])agen.buscarCitasCapasits();
        } catch(Exception e){
            e.printStackTrace();
        }
        return arrAgen;
    }
    
    public String recuperaDato(int valor) throws Exception{
        oNutCap= new CitaNutricionCapasits();
        String link="";
        oNutCap.setFolioCita(valor); 
        if(oNutCap.buscar()) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta Realizada","Para modificar datos dar clic en el boton modificar"));
        }else{
        oNutCap.buscarPacienteEncita();
        link="/faces/sesiones/capasits/nutricion.xhtml";}
        return link;
    }
    
    public String recuperaDato2(int valor) throws Exception{
        String link="";
        oNutCap= new CitaNutricionCapasits();
        oNutCap.setFolioCita(valor);
        if(oNutCap.buscar()) {  
            oNutCap.buscarPacienteEncita();
            link="/faces/sesiones/capasits/modificaNutricion.xhtml";
        }else
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "No sea realizado la consulta","Por lo tanto no se puede modificar"));
        return link;
    }
    
    public String almacena() {
        String sRet="";
        int nAfec =0;
         try{ 
            nAfec=this.oNutCap.insertar(); 
            sRet="/faces/sesiones/capasits/atenderCitasNut.xhtml";
         } catch(Exception e){
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al insertar verificar datos"));
         }
         this.oNutCap=null;
         arrAgen=null;
         return sRet;
    }
    
    public String modifica() {
         String link="";
        try{ 
            this.oNutCap.modificar(); 
            link="/faces/sesiones/capasits/atenderCitasNut.xhtml";
         } catch(Exception e){
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al modificar"));
         }
        oNutCap=null;
        arrAgen=null;
        return link;
    }
    
    public String link(){
        String link="/faces/sesiones/capasits/atenderCitasNut.xhtml";
        return link;
    }
    
}
