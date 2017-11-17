package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import mx.gob.hrrb.modelo.core.Consultorio;
import mx.gob.hrrb.modelo.core.Especialidad;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.TipoMedico;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.core.Usuario;
/**
 *
 * @author pedro
 */
@ManagedBean(name = "oAddMedCapa")
@SessionScoped
public class ConsultaMedico implements Serializable{
    private Medico oMed;
    List<String> arrRet;
    private Turno oTur;
    private Consultorio oConsul;
    private Usuario oUsua;
    private boolean ban=false;
    private boolean ban2=false;
    private String ceduPro;
    private int consulvie;
    private String horavie;

    public ConsultaMedico() {
        oMed=new Medico();
        oTur=new Turno();
        oConsul=new Consultorio();
        oUsua= new Usuario();
    }
    
    public Medico getMed() {
        return oMed;
    }

    public void setMed(Medico oMed) {
        this.oMed = oMed;
    }
    
    public Turno getTur(){
        return oTur;
    }
    
    public void settur(Turno valor){
        this.oTur=valor;
    }
       
    public Consultorio getConsul(){
        return oConsul;
    }
    
    public void setConsultorio(Consultorio valor){
        this.oConsul=valor;
    }
    
    public Usuario getUsua() {
        return oUsua;
    }

    public void setUsua(Usuario valor) {
        this.oUsua= valor;
    }
    
    public List<SelectItem> getListaEspecialidad(){
    List<SelectItem> nmed = new ArrayList<SelectItem>();
    Especialidad clvEsp[] = null;
        try{
            clvEsp = (new Especialidad()).buscarTodos();
            for (Especialidad n:clvEsp){
                nmed.add(new SelectItem(n.getClaveEsp(),n.getDescripcion()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return nmed;
    }
     
    
    public List<String> completar(String sTxt){
    arrRet = new ArrayList<String>();
    List<TipoMedico> lis= getListaTipoMedico(sTxt);
        for (TipoMedico li : lis) {
            if (sp_ascii(li.getDescripcion()).contains(sTxt)) {
                arrRet.add(li.getDescripcion());
            }
        }
    return arrRet;
    }

    public List<TipoMedico> getListaTipoMedico(String txt){
        List<TipoMedico> lLista = null;
       try {
           lLista = new ArrayList<TipoMedico>(
                   Arrays.asList((new TipoMedico().buscarTipoMed(txt))));
       } catch (Exception e) {
           e.printStackTrace();
       }
        return lLista;
    }
    //*******************************************************************      
    public String sp_ascii(String input) {
    // Cadena de caracteres original a sustituir.
    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    // Cadena de caracteres ASCII que reemplazarán los originales.
    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    String output = input;
    for (int i=0; i<original.length(); i++) {
        // Reemplazamos los caracteres especiales.
        output = output.replace(original.charAt(i), ascii.charAt(i));
    }//for i
    return output;
    }
   //*******************************************************************    

    public String almacena() throws Exception{
        String sRet="";
        int nAfec =0;   
        valida();           
        if(this.ban){ 
         try{ 
             oMed.setCons(oConsul);
             oMed.setTurn(oTur);
             oMed.setUsuar(oUsua);
            nAfec=this.oMed.insertar(); 
            if(nAfec==1){
              FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Sea Insertado a",oMed.getNombreCompleto()));  
            sRet = "/faces/sesiones/capasits/altamedico.xhtml";
            }
            else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.oMed= new Medico();
         this.oTur=new Turno();
         this.oConsul=new Consultorio();
         this.oUsua=new Usuario();
        }
         return sRet;
    }
       
    public void valida() throws Exception{
        FacesMessage msg = null;
         FacesContext context = FacesContext.getCurrentInstance();
          Date fecha= new Date();
            SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String f=ff.format(fecha);
            String f2=ff.format(oMed.getFechaNac());
            
            if(f.equals(f2) || fecha.before(oMed.getFechaNac())){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA DE NACIMIENTO INVALIDA"));
            }
            else {if(oMed.getNoTarjeta()<=0){
                 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL NUMERO DE TARJERA ES INVALIDO"));
                }
            else{
                if(oMed.buscarFolio()){
                 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL NUMERO DE TARJERA YA EXISTE"));
            }   
                else{
                if(oMed.buscarCedulaProf()){
                 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","LA CÉDULA PROFECIONAL YA EXISTE"));
                } 
                else{
                if(oUsua.buscarNombreUsuario()){
                 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL NOMBRE DE USUARIO YA EXISTE"));
                } 
                else{
                if(!esEntero(oMed.getCedProf())){
                 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","DATOS PARA EL CAMPO DE CEDULA PROFESIONAL INVALIDOS"));   
                }
                else{
                  this.ban=true;
                }
                }  
                }
                }
                }
            }
    }
    
    public void valida2() throws Exception{
          Date fecha= new Date();
            SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String f=ff.format(fecha);
            String f2=ff.format(oMed.getFechaNac());
            
            if(f.equals(f2) || fecha.before(oMed.getFechaNac())){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","FECHA DE NACIMIENTO INVALIDA"));
            }   
            else{
                if(!esEntero(oMed.getCedProf())){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","DATOS PARA EL CAMPO DE CEDULA PROFESIONAL INVALIDOS"));   
                }
                else{
                if(this.ceduPro.compareTo(oMed.getCedProf())!=0){
                if(oMed.buscarCedulaProf()){
                 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","LA CÉDULA PROFECIONAL YA EXISTE"));
                }
                }
                else{
                if(oUsua.getIdUsuario2().compareTo(oUsua.getIdUsuario())!=0){ 
                if(oUsua.buscarNombreUsuario()){
                 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","EL NOMBRE DE USUARIO YA EXISTE"));
                } 
                else{
                  this.ban2=true;
                }  
                }
                else{
                  this.ban2=true;
                }
                }  
                }
            }
    }
    
    public void busca() throws Exception{
        if(oMed.getNoTarjeta()<1){
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Número de tarjeta invalido"));
        }
        else{
            if(oMed.buscarMedicoCapasits()){ 
                oMed.buscarUsuarioMedico();
                oUsua=oMed.getUsuar();
                oConsul=oMed.getCons();
                oTur=oMed.getTurn();
                oUsua.setIdUsuario2(oUsua.getIdUsuario());
                this.ceduPro=oMed.getCedProf();
                this.consulvie=oConsul.getNoConsultorio(); 
                this.horavie=oTur.getClave().trim(); 
                System.out.println(this.consulvie);
                System.out.println(this.horavie);
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","No se encuentra el número de tarjeta en la base de datos"));
            }
        }
    }
    
    public String borrar(){
        String link ="/faces/sesiones/capasits/modificaMedico.xhtml";
        oMed=new Medico();
        this.oTur=new Turno();
        this.oConsul=new Consultorio();
        this.oUsua=new Usuario();
        return link;
    }
    
    public void modifica() throws Exception{
        int n=0;
        valida2();        
        if(this.ban2){ 
         try{ 
             oMed.setCons(oConsul);
             oMed.setTurn(oTur);
             oMed.setUsuar(oUsua);
             if(oUsua.getIdUsuario2()!=null){oMed.eliminaUsuarioMedicoCapa();}
             System.out.println(oConsul.getNoConsultorio());
             System.out.println(oTur.getClave());   
             if(this.consulvie==oConsul.getNoConsultorio() && this.horavie.equals(oTur.getClave()))
             {n=1;}
             else {n=2;}
             if(oUsua.getIdUsuario2().equals(oUsua.getIdUsuario()))
                 this.oMed.modificarMedicoCapasits(1,n); 
             else
                 this.oMed.modificarMedicoCapasits(2,n);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Sea modificado a",oMed.getNombreCompleto()));
         } catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al Modificar"));
         }
         this.oMed= new Medico();
         this.oTur=new Turno();
         this.oConsul=new Consultorio();
         this.oUsua=new Usuario();
        }
    }   
    
    public boolean esEntero(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }  
}
