package mx.gob.hrrb.jbs.capasits;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import mx.gob.hrrb.modelo.consultaexterna.Horario;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Consultorio;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author pedro
 */
@ManagedBean(name="agendar")
@SessionScoped
public class ConsultaAgenda implements Serializable{
private Cita agen;
private PacienteCapasits oPac;
private Consultorio oCons;
private AsignaConsultorio oAsignacons;
private Horario oHor;
private Cita[] arrAgen = null;
private int id=0;
private boolean hr=true;
private Firmado oFirm;
private HttpServletRequest httpServletRequest;
private FacesContext faceContext;
private String sUsuario;
    
    public ConsultaAgenda() {
         agen= new Cita();
         oPac= new PacienteCapasits(); 
         oCons= new Consultorio();
         oAsignacons=new AsignaConsultorio();
         oHor=new Horario();
         oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
            
    }
    
    
    public Cita[] getLista() throws Exception{
        agen.buscarUsuarioMedico(sUsuario);
        if(arrAgen==null){
        if(this.agen.getFechaCita()==null){
        Date fecha=new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String fec=ff.format(fecha);
            Date fech=ff.parse(fec);
        this.agen.setFechaCita(fech);
        }  
        try{
            agen.setPacienteCapa(oPac);
            arrAgen = (Cita[])agen.buscarCitasCapasits();           
        } catch(Exception e){
            e.printStackTrace();
        }
        }
        return arrAgen;
    }
    
    public Cita getAgend(){
     return agen;
    }
    
    public PacienteCapasits getPaci(){
        return oPac;
    }
    
     public String getNombrec(){
         String nom="";
         if (oPac.getIdNacional() > 0) {
            try {
                if (oPac.buscarIdNacional()) {
                    nom = oPac.getNombreCompleto();           
                } else {
                    
                    nom = "No existe";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         this.id=0;
         return nom;
     }
     
     public String almacena() throws Exception{
        String sRet="agenda";
        int nAfec =0;
        if(valida()){
         try{ 
             agen.setPacienteCapa(oPac);
            nAfec=this.agen.insertarCitaCapasits(); 
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } 
        }
         arrAgen = null;
         oPac=new PacienteCapasits();
         return sRet;
    }
     
    public void onEdit(RowEditEvent event) throws Exception{
        Cita age = (Cita) event.getObject();
        String sMsg = "";
        boolean ban=false;
        Date fecha= new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String f=ff.format(fecha);
            String f2=ff.format(age.getFechaCita());
            Date ff1=ff.parse(f);
            Date ff2=ff.parse(f2);
            int h1=Integer.parseInt(age.getHora().substring(0, 2));
         if(ff1.after(ff2)){ 
              FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA PARA INGRESAR INVALIDA"));                
             }
          else {
            if(age.buscarPacienteEnConsultoriofechayhora()){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL PACIENTE YA SE ENCUENTRA AGENDADO EN ESA HORA EN OTRO CONSULTORIO"));
         }
         else{if(age.buscarCitaEnConsultoriofechayhora()){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","YA SE ENCUENTRA AGENDADO UN PACIENTE A ESA HORA"));    
        }
         else{if(agen.getHorarios().getClave()==1){ 
            if(h1>13){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","HORA PARA INGRESAR CITA INVALIDA"));
            }
            else ban=true;
        }
        else{if(h1<14){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","HORA PARA INGRESAR CITA INVALIDA"));
        }
          else ban=true;
        }
        }
        }
        }
         if(ban){
        try{ 
            sMsg =age.getPacienteCapa().getNombreCompleto();
            age.modificarCitaCapasits();
             this.arrAgen=null;
        }catch(Exception e){
            e.printStackTrace();
            sMsg = "Error al actualizar bd";
        }
        FacesMessage msg = new FacesMessage("Editado", sMsg);
        FacesContext.getCurrentInstance().addMessage(null, msg); 
        } arrAgen = null;
    }
    
    public void onCancel(RowEditEvent event) throws ParseException {
        FacesMessage msg;msg = new FacesMessage("Cancelado", ((Cita) event.getObject()).getFechaCita().toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }      
    
    public boolean estadohr(){
     if(oPac.getIdNacional()==0)
     hr=true;
     else
     hr=false;
     return hr;
}
    public void inicializa(){
        this.arrAgen=null;
    }
    
    public boolean valida() throws Exception{
        boolean ban=false;
        FacesMessage msg = null;
        FacesContext context = FacesContext.getCurrentInstance();
        Date fecha= new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String f=ff.format(fecha);
            String f2=ff.format(agen.getFechaCita());
            Date ff1=ff.parse(f);
            Date ff2=ff.parse(f2);
        int h1=Integer.parseInt(agen.getHora().substring(0, 2));
        if(ff1.after(ff2)){
              FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA PARA INGRESAR CITA INVALIDA"));                
         }
        else {if(agen.buscarPacienteEnConsultorio()){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL PACIENTE YA SE ENCUENTA AGENDADO")); 
        }
        else{if(agen.buscarPacienteEnConsultoriofechayhora()){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL PACIENTE YA SE ENCUENTRA AGENDADO A ESA HORA EN OTRO CONSULTORIO"));    
        }
        else{if(agen.buscarCitaEnConsultoriofechayhora()){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","YA SE AGENDADO UN PACIENTE A ESA HORA"));    
        }
        else{if(agen.getHorarios().getClave()==1){ 
            if(h1>13){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","HORA PARA INGRESAR CITA INVALIDA"));
            }
            else ban=true;
        }
        else{if(h1<14){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","HORA PARA INGRESAR CITA INVALIDA"));
        }
         else ban=true;
        }
        }
        }
        }
        }
        return ban;
    }
    
}
