package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import mx.gob.hrrb.modelo.consultaexterna.Horario;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Consultorio;
import mx.gob.hrrb.modelo.core.Medico;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author pedro
 */
@ManagedBean (name="etiqueta")
@SessionScoped
public class ConsultaEt implements Serializable{
    private Cita oEtiq=new Cita();
    private Medico oMed;
    private PacienteCapasits oPac;
    private Consultorio oCons;
    private AsignaConsultorio oAsignacons;
    private Horario oHor;
    private Cita[] arrEtiq = null;
    private int id=0;
    private boolean vis=false, ban=true;
    private String ed;

    public ConsultaEt() {
        oPac= new PacienteCapasits(); 
        oCons= new Consultorio();
        oAsignacons=new AsignaConsultorio();
        oHor=new Horario();
        oMed=new Medico();
    }
    
    public Cita[] getListar() throws Exception{
        if(arrEtiq==null){ 
            oEtiq.getNoconsult().setNoConsultorio(25);
            oEtiq.getHorarios().setClave(1);
            oMed.getCons().setNoConsultorio(25);
            oMed.getHorarios().setClave(1);
        if(this.oEtiq.getFechaCita()==null){
        Date fecha=new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String fec=ff.format(fecha);
            Date fech=ff.parse(fec);
        this.oEtiq.setFechaCita(fech);
        }  
        try{ 
            oEtiq.setPacienteCapa(oPac);
            arrEtiq = (Cita[])oEtiq.buscarCitasCapasits();
        } catch(Exception e){
            e.printStackTrace();
        }
        }
        return arrEtiq;
    }
    
    public Cita getAgendEtiq(){
     return oEtiq;
    }
    
    public PacienteCapasits getPaci(){
        return oPac;
    }
    
    public String getNombrecom(){
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
         return nom;
    }
    
    public String almacenar() throws Exception{ 
        String sRet="imprimeetiqueta";
        int nAfec =0;
        if(valida()){ 
         try{ 
             oMed.setPacienteCapa(oPac);
             oMed.setCit(oEtiq);
             oMed.buscarMedicodenCita();
            nAfec=oMed.insertarCitaCapasitsAdmin(); 
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } 
        }
         arrEtiq = null;
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
          else ban=true;
        }
        }
         if(ban){
        try{ 
            sMsg =age.getFechaCita().toString(); 
            age.modificarCitaCapasits();
             this.arrEtiq=null;
        }catch(Exception e){
            e.printStackTrace();
            sMsg = "Error al actualizar bd";
        }
        FacesMessage msg = new FacesMessage("Editado", sMsg);
        FacesContext.getCurrentInstance().addMessage(null, msg); 
        } arrEtiq = null;
    }
    
    public void onCancel(RowEditEvent event) throws ParseException {
        FacesMessage msg;msg = new FacesMessage("Cancelado", ((Cita) event.getObject()).getFechaCita().toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }      
    
    public void inicializa(){
        this.arrEtiq=null;
    }
    
    public boolean valida() throws Exception{
        boolean ban=false;
        FacesMessage msg = null;
        FacesContext context = FacesContext.getCurrentInstance();
        Date fecha= new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String f=ff.format(fecha);
            String f2=ff.format(oEtiq.getFechaCita());
            Date ff1=ff.parse(f);
            Date ff2=ff.parse(f2);
        int h1=Integer.parseInt(oEtiq.getHora().substring(0, 2));
        if(ff1.after(ff2)){
              FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA PARA INGRESAR CITA INVALIDA"));                
         }
        else {if(oEtiq.buscarPacienteEnConsultorio()){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL PACIENTE YA SE ENCUENTA AGENDADO")); 
        }
        else{if(oEtiq.buscarPacienteEnConsultoriofechayhora()){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL PACIENTE YA SE ENCUENTRA AGENDADO A ESA HORA EN OTRO CONSULTORIO"));    
        }
        else{if(oEtiq.buscarCitaEnConsultoriofechayhora()){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","YA SE AGENDADO UN PACIENTE A ESA HORA"));    
        }
        
         else ban=true;
        }
        }
        }
        return ban;
    }
    
     public void bus(int id, String hora, Date fecha) throws Exception{
        oPac.setIdNacional(id);
        oEtiq.setHora(hora); 
        oEtiq.setFechaCita(fecha);
        FacesMessage msg = null;
         FacesContext context = FacesContext.getCurrentInstance();
         if(oPac.getIdNacional()<1){ 
             this.setVisible(false); 
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Id invalido"));
         }
         else{
         if(oPac.buscarIdNacional()){
         try{
             calculaEdad();
             this.setVisible(true);
         }catch(Exception e){
         e.printStackTrace();
         }
         }
         else{
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","no se encuentra El Id en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
             this.oPac=new PacienteCapasits();
        this.setVisible(false); 
            }   
         }
    }
    
    public String calculaEdad(){
        Date fActual=new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechan=formato.format(oPac.getFechaNac());
        String x[]=fechan.split("/");
        String fecAct=formato.format(fActual);
        int d1=Integer.parseInt(x[0]);
        int m1=Integer.parseInt(x[1]);
        int a1=Integer.parseInt(x[2]);
        String v[]=fecAct.split("/");
        int d2=Integer.parseInt(v[0]);
        int m2=Integer.parseInt(v[1]);
        int a2=Integer.parseInt(v[2]);
        int anofinal;
        String mensaje="";

        anofinal=a2-a1;
        if (anofinal>0){
            if (m2<m1){ anofinal--; }
            if (m2==m1 && d2<d1){anofinal--;}
            if (anofinal==1)
            mensaje=""+anofinal;
            else
                mensaje=""+anofinal;
        }
        setEdad(mensaje);
        return mensaje;
    }
    
    public boolean  getVisible(){
        return vis;
    }
    public void setVisible(boolean valor){
      this.vis=valor;  
    }
    
    public String  getEdad(){
        return ed;
    }
    public void setEdad(String valor){
      this.ed=valor;  
    }
    
    public boolean getBhora(){
        return ban;
    }
    
    public void setBhora(boolean valor){
        ban=valor;
    }
}
