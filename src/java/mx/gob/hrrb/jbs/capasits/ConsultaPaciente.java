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
@ManagedBean(name="insPac")
@SessionScoped
public class ConsultaPaciente implements Serializable{
private PacienteCapasits pac=null;
private Seguro segu=null;
private Expediente exp=null;
private Etnicidad etn=null;
private int id=0;
private boolean ban=false;
private boolean vali=false;
private PacienteCapasits[] arrUsu = null;
    /**
     * Creates a new instance of ConsultaPaciente
     */
    
    public ConsultaPaciente() { 
        pac=new PacienteCapasits();
        segu= new Seguro();
        exp= new Expediente();
        etn= new Etnicidad();
        
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
        else {if(segu.buscarNumSegPopu()){ 
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de seguro popular ya se encuentra en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else {if(pac.buscarIdNacional()){ 
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de ID  ya se encuentra en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
        }
         else {if(exp.buscar()){ 
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","El numero de expediente  ya se encuentra en la base de datos");
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
         else {
         this.ban=true;}
        }
        }
        }
        }
        }
        }
        }
        }
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
    
    public String almacena() throws Exception{
        String sRet="";
        valida();
        int nAfec =0;
        System.out.println(this.ban);
        if(this.ban){    
            System.out.println(segu.getNumero());
            pac.setSeg(segu);
            pac.setEtnic(etn);
            pac.setExp(exp);
         try{ 
            nAfec=this.pac.insertar(); 
            if(nAfec==1){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Sea Insertado a",pac.getNombreCompleto()));  
            sRet = "/faces/sesiones/capasits/PacienteCapasits.xhtml";
            }
            else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.pac= new PacienteCapasits();
         this.segu= new Seguro();
         this.exp=new Expediente();
        }
         this.ban=false;
         id=0;
         return sRet;
    }
    
    public PacienteCapasits getPaci(){
     if (this.id!=0 && this.pac.getCodigoBarras()==0){
         try{
             pac.setCodigoBarras(id);
             pac.buscar();
         }catch(Exception e){
         e.printStackTrace();
         }
     }
     return pac;
    }
    
    public List<SelectItem> getSex(){
    List<SelectItem> nsex = new ArrayList<SelectItem>();
    Parametrizacion clvsexo[] = null;
        try{
            clvsexo = (new Parametrizacion()).buscarSexo("T09");
            for (Parametrizacion n:clvsexo){
                nsex.add(new SelectItem(n.getTipoParametro(),n.getValor()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return nsex;
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
         
    public Seguro getSeg(){
        return segu;
    }
    
    public Expediente getExpe(){
        return exp;
    }
    public Etnicidad getEtnic(){
        return etn;
    }
    
         private void cargaLista(){
        try{
            arrUsu = (PacienteCapasits[])pac.buscarTodos();           
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public PacienteCapasits[] getLista(){
        cargaLista();
        return arrUsu;
    }
}
