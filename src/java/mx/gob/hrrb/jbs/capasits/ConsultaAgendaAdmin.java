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
@ManagedBean (name="agendaAdmin")
@SessionScoped
public class ConsultaAgendaAdmin implements Serializable{
    private Medico oMed;
    private Cita agen;
    private PacienteCapasits oPac;
    private AsignaConsultorio oAsignacons;
    private Cita[] arrAgen = null;
    private int id=0;
    private boolean hr=true;
    private String sNomConsul;

    public ConsultaAgendaAdmin() {
        agen= new Cita();
        oMed=new Medico();
        oPac= new PacienteCapasits(); 
        oAsignacons=new AsignaConsultorio();
    }
    
    public Cita[] getListar() throws Exception{System.out.println("lista");
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
    
    public Cita getAgenda(){
     return agen;
    }
    
    public PacienteCapasits getPacic(){
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
         this.id=0;
         return nom;
    }
     
     public String almacena() throws Exception{ 
        String sRet="agendasecretaria";
        int nAfec =0;
        if(valida()){
         try{
             oMed.setPacienteCapa(oPac);
             oMed.setCit(agen);
             oMed.buscarMedicodenCita();
            nAfec=this.oMed.insertarCitaCapasitsAdmin(); 
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
         else{ if(age.buscarCitaEnConsultoriofechayhora()){
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
    
    public String Datos(int valor,int valor2) throws Exception{
        String link="";
        System.out.println(valor+" "+ valor2);
        agen.getNoconsult().setNoConsultorio(valor);
        agen.getHorarios().setClave(valor2);
        oMed.getCons().setNoConsultorio(valor);
        oMed.getHorarios().setClave(valor2);
        arrAgen=null;
        if(valor==19) setNomCons(" de Consulta Externa 1");
        else{ if(valor==20)setNomCons(" de Consulta Externa 2");
        else {if(valor==21)setNomCons(" de Trabajo Social");
        else {if(valor==22)setNomCons(" de Nutrición");
        if(valor==23)setNomCons(" de Psicología");
        else{if(valor==24)setNomCons(" de Odontología");}
        }
        }
        }
        link="/faces/sesiones/capasits/agendasecretaria.xhtml";
        return link;
    }
    
    public String getNomCons(){
        return sNomConsul;
    }
    
    public void setNomCons(String Valor){
        sNomConsul=Valor;
    }
    
}
