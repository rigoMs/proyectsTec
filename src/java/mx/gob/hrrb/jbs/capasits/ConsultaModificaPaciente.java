package mx.gob.hrrb.jbs.capasits;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.Etnicidad;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Seguro;

/**
 *
 * @author pedro
 */
@ManagedBean (name="modpac")
@SessionScoped
public class ConsultaModificaPaciente {
private PacienteCapasits pac=null;
private Seguro segu=null;
private Expediente exp=null;
private Etnicidad etn=null;
private boolean vali=false;
private boolean ban=false;
private long id=0;
private int ex=0;
private String cvlseg;

    public ConsultaModificaPaciente() {
        pac=new PacienteCapasits();
        segu= new Seguro();
        exp= new Expediente();
        etn= new Etnicidad();
    }
    
    public PacienteCapasits getPacic(){
     return pac;
    }
    
     public Seguro getSeg(){
        return segu;
    }
    
    public Expediente getExpe(){
        return exp;
    }
    
    public Etnicidad getEtnic(){
        return etn;
    }
    
    public void bus() throws Exception{
        FacesMessage msg = null;
         FacesContext context = FacesContext.getCurrentInstance();
         if(pac.getIdNacional()<1){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Id invalido"));
         }
         else{    
         if(pac.buscarIdNacional()){ 
         try{ this.segu=pac.getSeg(); this.exp=pac.getExp();  this.etn=pac.getEtnic(); 
         }catch(Exception e){
         e.printStackTrace();
         FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
         }
         }
         else{
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","no se encuentra El Id en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
             this.pac=new PacienteCapasits();
            }      
        } this.id=pac.getIdNacional(); this.ex=exp.getNumero(); this.cvlseg=segu.getNumero();
    }
    
    public boolean habilita(){
        if("08".equals(segu.getTipoSeguro()))
            vali=false;      
        else {
            vali=true;
            segu.setNumero(null);
            segu.setVigencia(null);
        }
        return vali;
    }
    
    public void modifica() throws Exception{
       valida();
       if(this.ban){
        try{ 
            this.pac.modificar();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Sea modificado a",pac.getNombreCompleto()));
         } catch(Exception e){
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al modificar"));
         }
        pac=new PacienteCapasits();
        segu= new Seguro();
        exp= new Expediente();
        etn= new Etnicidad();
       }
    }
    
    public void cancela(){ 
        pac=new PacienteCapasits();
        segu= new Seguro();
        exp= new Expediente();
        etn= new Etnicidad();
    }
    
    public List<SelectItem> getSeguros(){
    List<SelectItem> nseg = new ArrayList<SelectItem>();
    Parametrizacion clvseg[] = null;
        try{
            clvseg = (new Parametrizacion()).buscarSexo("T01");
            for (Parametrizacion n:clvseg){
                nseg.add(new SelectItem(n.getTipoParametro(),n.getValor()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
         
    return nseg;
    }
    
     public List<SelectItem> getEtni(){
    List<SelectItem> netni = new ArrayList<SelectItem>();
    Parametrizacion paraetnic[] = null;
        try{
            paraetnic = (new Parametrizacion()).buscarSexo("T02");
            for (Parametrizacion n:paraetnic){
                netni.add(new SelectItem(n.getTipoParametro(),n.getValor()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return netni;
    }
     
    public void valida() throws Exception{
      
         FacesMessage msg = null;
         FacesContext context = FacesContext.getCurrentInstance();
          Date fecha= new Date();
            SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String f=ff.format(fecha);
            String f2=ff.format(pac.getFechaNac());
        if(f.equals(f2) || fecha.before(pac.getFechaNac())){           
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA DE NACIMIENTO INVALIDA"));
            }
        else {
            if(fecha.before(pac.getPrimerFechaIngreso())){
             msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA DE INGRESO INVALIDA");
             FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else {if(pac.getFechaNac().after(pac.getPrimerFechaIngreso())){ 
             msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA DE INGRESO MENOR QUE LA DE NACIMIENTO");
             FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else {if(segu.getVigencia()!=null && fecha.after(segu.getVigencia())){ 
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA DE VIGENCIA INVALIDA");
             FacesContext.getCurrentInstance().addMessage(null, msg);
        }
         else {if(pac.getIdNacional()<=0){ 
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de ID nacional invalido");
             FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
          else {if(exp.getNumero()<=0){
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de expediente es invalido");
             FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
           else {this.ban=true; if(pac.getIdNacional()!=id){ 
               if(pac.buscarIdNacional()){
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de ID  ya se encuentra en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg); 
             pac.setIdNacional(id); pac.buscarIdNacional();
             this.ban=false;
             }       
        }
           else{ if(segu.getNumero().compareTo(cvlseg)!=0){
               if(segu.buscarNumSegPopu()){
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de seguro popular ya se encuentra en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg); this.ban=false;
        }
        }
           else{ if(exp.getNumero()!=ex){
               if(exp.buscar()){ 
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de expediente  ya se encuentra en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg); this.ban=false;
        }	
           }
           }
          }
        }
        }
        }
        }
        }
        }
        }
}
