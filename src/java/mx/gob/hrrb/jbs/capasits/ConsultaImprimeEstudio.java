package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.CitamedicinaGeneralCapasits;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.Estudios;

/**
 *
 * @author pedro
 */
@ManagedBean (name="ImpEst")
@SessionScoped
public class ConsultaImprimeEstudio implements Serializable{
    private Estudios oEstu;
    private PacienteCapasits oPCapa;
    public CitamedicinaGeneralCapasits oCitameGe;
    private String ed, hoy;
    private Estudios[] arrEstu;

    public ConsultaImprimeEstudio() {
        oEstu= new Estudios();
        oPCapa= new PacienteCapasits();
        oCitameGe=new CitamedicinaGeneralCapasits();
    }
    
    public Estudios getEstu(){
        return oEstu;
    }
    
    public PacienteCapasits getPac(){
        return oPCapa;
    }
    
    public CitamedicinaGeneralCapasits getCExterna(){
        return oCitameGe;        
    }
    
    public void bus() throws Exception{
        FacesMessage msg = null;
         FacesContext context = FacesContext.getCurrentInstance();
         if(oPCapa.getIdNacional()<1){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Id invalido"));
         }
         else{    
         if(oPCapa.buscarIdNacional()){
             Date fecha= new Date();
             oEstu.setFechaEstudio(fecha);
             oEstu.setPac(oPCapa);
            if (oEstu.buscarEstudiosDePaciente()){ 
         try{
             calculaEdad();
         }catch(Exception e){
         e.printStackTrace();
         FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
         }
          }
            else {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA","No se a realizado la solicitud de estudios de laboratorio de "+oPCapa.getNombreCompleto()));
            }
         }
         else{
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","no se encuentra El Id en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
             
            }      
        } 
    }
    
    public void calculaEdad(){
        Date fActual=new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechan=formato.format(oPCapa.getFechaNac());
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
        setHoy(fecAct);
    }
            
    public String  getEdad(){
        return ed;
    }
    public void setEdad(String valor){
      this.ed=valor;  
    }
    
    public String  getHoy(){
        return hoy;
    }
    public void setHoy(String valor){
      this.hoy=valor;  
    }
    
    public String getNombreMedico() throws Exception{
        String nombrec;
        oCitameGe.buscarMedicoDeHoja();
        nombrec=oCitameGe.getMed().getNombreCompleto();
        return nombrec;
    }
    
   public String imprime(){
        String link="/faces/sesiones/capasits/imprimeEstudioLab.xhtml";
        return link;
   }
   
   public void borrar(){
       oEstu= new Estudios();
       oPCapa= new PacienteCapasits();
       oCitameGe=new CitamedicinaGeneralCapasits();
   }
}
