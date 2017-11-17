package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
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
@ManagedBean (name="PacH")
@SessionScoped
public class ConsultaPacienteHrrb implements Serializable{
    private PacienteCapasits pac=null;
    private Seguro segu=null;
    private Expediente exp=null;
    private Etnicidad etn=null;
    private boolean vali=false;
    private boolean ban=false;
    private String cvlseg;
    
    
    public ConsultaPacienteHrrb() {
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
         System.out.println(exp.getNumero());
         if(exp.getNumero()<1){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","No. de expediente invalido"));
         }
         else{ pac.getExp().setNumero(exp.getNumero());
         if(pac.buscarExpe()){ 
         try{ this.segu=pac.getSeg(); this.exp=pac.getExp();  this.etn=pac.getEtnic(); 
         }catch(Exception e){
         e.printStackTrace();
         FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
         }
         }
         else{
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","no se encuentra El No. de expediente en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
             this.pac=new PacienteCapasits();
            }      
        } this.cvlseg=segu.getNumero();
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
    
    public void modifica() throws Exception{
       pac.setSeg(segu);
       valida();
       if(this.ban){
        try{ 
            pac.setSeg(segu); pac.setExp(exp); 
            this.pac.modificarPacH();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Sea modificado a",pac.getNombreCompleto()));
         } catch(Exception e){
            e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Al modificar"));
         }
        pac=new PacienteCapasits();
        segu= new Seguro();
        exp= new Expediente();
       }
    }
    
    public void cancela(){ 
        pac=new PacienteCapasits();
        segu= new Seguro();
        exp= new Expediente();
    }
    
    public void valida() throws Exception{
        Date fecha= new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
        String f=ff.format(fecha);
        
    if(fecha.before(pac.getPrimerFechaIngreso())){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Fecha de ingreso invalida"));
        }
        else {if(pac.getFechaNac().after(pac.getPrimerFechaIngreso())){ 
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Fecha de ingresomenos que la de nacimiento"));
        }
        else {if(segu.getVigencia()!=null && fecha.after(segu.getVigencia())){ 
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Fecha de vigencia invalida"));
        }
         else {if(pac.getIdNacional()<=0){ 
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de ID nacional invalido"));
        }
         else {if(pac.buscar()){ 
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de ID  ya se encuentra en la base de datos"));
        }
         else{
             this.ban=true;
             if(segu.getNumero().compareTo(cvlseg)!=0){
               if(segu.buscarNumSegPopu()){
                   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de seguro popular ya se encuentra en la base de datos"));
                   this.ban=false;
               }
            }
         }
        }
        }
        }
        }
    }
    
}
