/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import mx.gob.hrrb.modelo.consultaexterna.Horario;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Consultorio;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Turno;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oProgConsul")
@SessionScoped
public class ProgConsultorios {

   private AsignaConsultorio oAsigcon;
   private Consultorio oConsul;
   private Medico oMedico;
   private AreaServicioHRRB oAreaServicio;
   private Turno oTurno;
   private Horario oHoraLun;
   private Horario oHoraMar;
   private Horario oHoraMie;
   private Horario oHoraJue;
   private Horario oHoraVie;
   private String sDialogo;
   private int nOp;
   private int primvezAux;
   private int subAux;
   private int modificacion;
    
    public ProgConsultorios() {
        oAsigcon=new AsignaConsultorio();
        oConsul=new Consultorio();
        oMedico=new Medico();
        oTurno=new Turno();
        oAreaServicio=new AreaServicioHRRB();
        oHoraLun=new Horario();
        oHoraMar=new Horario();
        oHoraMie=new Horario();
        oHoraJue=new Horario();
        oHoraVie=new Horario();
        sDialogo="dlg1";
        nOp=0;
        primvezAux=0;
        subAux=0;
        modificacion=0;
    }

    public AsignaConsultorio getAsigcon() {
        return oAsigcon;
    }

    public void setAsigcon(AsignaConsultorio oAsigcon) {
        this.oAsigcon = oAsigcon;
    }

    public Consultorio getConsul() {
        return oConsul;
    }

    public void setConsul(Consultorio oConsul) {
        this.oConsul = oConsul;
    }
    
    public Medico getMedico() {
        return oMedico;
    }

    public void setMedico(Medico oMedico) {
        this.oMedico = oMedico;
    }
    
    public AreaServicioHRRB getAreaServicio() {
        return oAreaServicio;
    }

    public void setAreaServicio(AreaServicioHRRB oAreaServicio) {
        this.oAreaServicio = oAreaServicio;
    }
    
    public Turno getTurno() {
        return oTurno;
    }

    public void setTurno(Turno oTurno) {
        this.oTurno = oTurno;
    }
    
    public String introduceURl(){
        setOp(0);
        reseteaValores();
        return "/sesiones/consultaexterna/ProgConsultorios.xhtml";
    }
    
    public String redirecModifica(){
        String pag="ProgConsultorios.xhtml";       
        if (oHoraLun.getHoras().compareTo("")!=0 && oHoraLun.getHoras().compareTo("00:00  -  00:00")!=0){oAsigcon.getDias().add("lun");}
        if (oHoraMar.getHoras().compareTo("")!=0 && oHoraMar.getHoras().compareTo("00:00  -  00:00")!=0){oAsigcon.getDias().add("mar");}
        if (oHoraMie.getHoras().compareTo("")!=0 && oHoraMie.getHoras().compareTo("00:00  -  00:00")!=0){oAsigcon.getDias().add("mie");}
        if (oHoraJue.getHoras().compareTo("")!=0 && oHoraJue.getHoras().compareTo("00:00  -  00:00")!=0){oAsigcon.getDias().add("jue");}
        if (oHoraVie.getHoras().compareTo("")!=0 && oHoraVie.getHoras().compareTo("00:00  -  00:00")!=0){oAsigcon.getDias().add("vie");}
        return pag;
    }
    
    public String devuelveCadena(String cad){
        String d="";
        if (cad.compareTo("")!=0)
            d=cad.substring(0, 5)+"  -  "+cad.substring(8, 13);
        else
            d="00:00  -  00:00";
        return d;
    }
     //************************************
     //Habilita o deshabilita los campos para horas
     public boolean habilitaHoras(String dia){
        int i=0;
        boolean hab=true;
        
        while (i<oAsigcon.getDias().size()){
            if (oAsigcon.getDias().get(i).compareTo(dia)==0){
                hab=false;
            }
            i++;
        }
        if(hab==true){
            if(dia.compareTo("lun")==0){getHoraLun().setHoras("00:00  -  00:00");}
            if(dia.compareTo("mar")==0){getHoraMar().setHoras("00:00  -  00:00");}
            if(dia.compareTo("mie")==0){getHoraMie().setHoras("00:00  -  00:00");}
            if(dia.compareTo("jue")==0){getHoraJue().setHoras("00:00  -  00:00");}
            if(dia.compareTo("vie")==0){getHoraVie().setHoras("00:00  -  00:00");}
        }
        //Si hab es true hay que comparar el día y dependiendo del día que sea se invoca al metodo de horario para que ponga la hora en ceros
        return hab;
    }

    //Suma las citas subs y de primera vez
    public int getSumaCitas(){
        oAsigcon.setCitasTotales(oAsigcon.getCitas1eraVez()+oAsigcon.getCitasSubs());
        return oAsigcon.getCitasTotales();
    }
    
    //Retorna Lista de Médicos
     public List<Medico> getListaMedicos(){
        List<Medico> lLista = null;
       try {
           lLista = new ArrayList<Medico>(Arrays.asList(
                   (new Medico()).buscarMedicosCE()));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
     public List<AreaServicioHRRB> getListaAreasCE(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasCE()));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
     public List<Turno> getListaTurnoCE(){
        List<Turno> lLista = null;
       try {
           lLista = new ArrayList<Turno>(Arrays.asList(
                   (new Turno()).buscarTurnosCE()));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public String activaCancel(){
        if (getOp()==1)
            return "";
        else
            return "mostrarbtn";
    }
    
    public String cancelar(){
        setOp(0);
        reseteaValores();
        return "ProgConsultorios.xhtml";
    }

     public void setDialogo() throws Exception{
         int ins;
         int[] v;
         ins=1;
         FacesMessage message=null;
         boolean enc=true;
         boolean rep=false;
         String d="";
         
         if (validaTurno()==false){
         if (getOp()==1){
             int aux1=oAsigcon.getCitas1eraVez();
             int aux2=oAsigcon.getCitasSubs();
             oAsigcon.setCitas1eraVez(getPrimVezAux());
             oAsigcon.setCitasSubs(getSubAux());
             oAsigcon.eliminar();
             oAsigcon.setCitas1eraVez(aux1);
             oAsigcon.setCitasSubs(aux2);
             setOp(0);
             modificacion=1;
         }
         
         if (getHoraLun().getHoras().compareTo("00:00  -  00:00")!=0 && rep==false){
         rep=oAsigcon.buscaRepetidos(oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), getTurno().getClave(), "LUNES");
         d="LUNES";}
         
         if (getHoraMar().getHoras().compareTo("00:00  -  00:00")!=0 && rep==false){
         rep=oAsigcon.buscaRepetidos(oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), getTurno().getClave(), "MARTES");
         d="MARTES";}
         
         if (getHoraMie().getHoras().compareTo("00:00  -  00:00")!=0 && rep==false){
         rep=oAsigcon.buscaRepetidos(oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), getTurno().getClave(), "MIÉRCOLES");
         d="MIÉRCOLES";}
         
         if (getHoraJue().getHoras().compareTo("00:00  -  00:00")!=0 && rep==false){
         rep=oAsigcon.buscaRepetidos(oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), getTurno().getClave(), "JUEVES");
         d="JUEVES";}
         
         if (getHoraVie().getHoras().compareTo("00:00  -  00:00")!=0 && rep==false){
         rep=oAsigcon.buscaRepetidos(oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), getTurno().getClave(), "VIERNES");
         d="VIERNES";}
         
         if (rep==false){
         enc=oConsul.buscar(oConsul.getNoConsultorio(), oConsul.getAreaServicio()); //Busca si existe un registro con el número y área enviados
         //Ve si fue seleccionado el día y luego busca en la BD  algún registro de ese día en esas horas
         if (enc==false){ //Si no existe un consultorio con ese número y servicio se ingresa a la BD
            ins=oConsul.insertar(oConsul.getNoConsultorio(),oAreaServicio.getClave(), null);}
         
         v=insertaHorarios();
         for (int i=0; i<v.length; i++){
             if (v[i]>1)
                 v[i]=0;
         }
         //Muestra Mensaje de Éxito o Error
         if (ins==0 || v[0]==0 || v[1]==0 || v[2]==0 || v[3]==0 || v[4]==0 || v[5]==0 || v[6]==0 || v[7]==0 || v[8]==0 || v[9]==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "Registro fallido :(");
         }else{
               message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "Registro Exitoso!!");
               reseteaValores();
         }
         }else{
             message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "Error: El médico seleccionado está repetido en el consultorio: "+oConsul.getNoConsultorio()+", turno: "+getTurno().getClave()+", día: "+d);
         }
         }else{
             message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Programación de Consultorios", "El Turno no corresponde con el Horario fijado");
         }
         RequestContext.getCurrentInstance().showMessageInDialog(message);
     }
     
     //*********************************************
     public boolean validaTurno(){
         boolean error=false;
         
         if (getTurno().getClave().substring(0, 3).compareTo("MAT")==0){
         if (getHoraLun().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraLun().getHoras().substring(0, 2).compareTo("14")>=0 || getHoraLun().getHoras().substring(10, 12).compareTo("14")>0)
                 error=true;
         }
         
         if (getHoraMar().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraMar().getHoras().substring(0, 2).compareTo("14")>=0 || getHoraMar().getHoras().substring(10, 12).compareTo("14")>0)
                 error=true;
         }
         if (getHoraMie().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraMie().getHoras().substring(0, 2).compareTo("14")>=0 || getHoraMie().getHoras().substring(10, 12).compareTo("14")>0)
                 error=true;
         }
         if (getHoraJue().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraJue().getHoras().substring(0, 2).compareTo("14")>=0 || getHoraJue().getHoras().substring(10, 12).compareTo("14")>0)
                 error=true;
         }
         if (getHoraVie().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraVie().getHoras().substring(0, 2).compareTo("14")>=0 || getHoraVie().getHoras().substring(10, 12).compareTo("14")>0)
                 error=true;
         }
         }else{
             if (getHoraLun().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraLun().getHoras().substring(0, 2).compareTo("14")<0)
                 error=true;
         }
         if (getHoraMar().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraMar().getHoras().substring(0, 2).compareTo("14")<0)
                 error=true;
         }
         if (getHoraMie().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraMie().getHoras().substring(0, 2).compareTo("14")<0)
                 error=true;
         }
         if (getHoraJue().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraJue().getHoras().substring(0, 2).compareTo("14")<0)
                 error=true;
         }
         if (getHoraVie().getHoras().compareTo("00:00  -  00:00")!=0){
             if(getHoraVie().getHoras().substring(0, 2).compareTo("14")<0)
                 error=true;
         }
         }
         return error;
     }
     //*********************************
     public void reseteaValores(){
         oAsigcon.setCitas1eraVez(0);
         oAsigcon.setCitasSubs(0); 
         oAsigcon.setCitasTotales(0);
         oConsul.setNoConsultorio(1);  
         oAsigcon.setDias(new ArrayList<String>());
         oMedico.setNoTarjeta(0); 
         oAreaServicio.setClave(0);
         oTurno.setClave("");
         habilitaHoras("lun");
         habilitaHoras("mar");
         habilitaHoras("mie");
         habilitaHoras("jue");
         habilitaHoras("vie");
     }
     //*********************************
     
     public int[] insertaHorarios() throws Exception{
         int v[]={1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
         boolean hl, hm, hx, hj, hv;
         hl=hm=hx=hj=hv=true;
         
         if (oHoraLun.getHoras().compareTo("00:00  -  00:00")!=0){
             hl=oHoraLun.buscarPorDia("LUNES", oHoraLun.getHoras().substring(0, 5), oHoraLun.getHoras().substring(10, 15));
                if (hl==false){v[0]=oHoraLun.insertarHoras("LUNES", oHoraLun.getHoras().substring(0, 5), oHoraLun.getHoras().substring(10, 15));
                                    hl=oHoraLun.buscarPorDia("LUNES", oHoraLun.getHoras().substring(0, 5), oHoraLun.getHoras().substring(10, 15));}}
                                    
         if (oHoraMar.getHoras().compareTo("00:00  -  00:00")!=0){
             hm=oHoraMar.buscarPorDia("MARTES",oHoraMar.getHoras().substring(0, 5), oHoraMar.getHoras().substring(10, 15));
                if (hm==false){v[1]=oHoraLun.insertarHoras("MARTES",oHoraMar.getHoras().substring(0, 5), oHoraMar.getHoras().substring(10, 15));
                                    hm=oHoraMar.buscarPorDia("MARTES",oHoraMar.getHoras().substring(0, 5), oHoraMar.getHoras().substring(10, 15));}}
                                    
         if (oHoraMie.getHoras().compareTo("00:00  -  00:00")!=0){
             hx=oHoraMie.buscarPorDia("MIÉRCOLES",oHoraMie.getHoras().substring(0, 5), oHoraMie.getHoras().substring(10, 15));
                if (hx==false){v[2]=oHoraMie.insertarHoras("MIÉRCOLES",oHoraMie.getHoras().substring(0, 5), oHoraMie.getHoras().substring(10, 15));
                                    hx=oHoraMie.buscarPorDia("MIÉRCOLES",oHoraMie.getHoras().substring(0, 5), oHoraMie.getHoras().substring(10, 15));}}
                                    
         if (oHoraJue.getHoras().compareTo("00:00  -  00:00")!=0){
             hj=oHoraJue.buscarPorDia("JUEVES",oHoraJue.getHoras().substring(0, 5), oHoraJue.getHoras().substring(10, 15));
                if (hj==false){v[3]=oHoraJue.insertarHoras("JUEVES",oHoraJue.getHoras().substring(0, 5), oHoraJue.getHoras().substring(10, 15));
                                    hj=oHoraJue.buscarPorDia("JUEVES",oHoraJue.getHoras().substring(0, 5), oHoraJue.getHoras().substring(10, 15));}}
                                    
         if (oHoraVie.getHoras().compareTo("00:00  -  00:00")!=0){
             hv=oHoraVie.buscarPorDia("VIERNES",oHoraVie.getHoras().substring(0, 5), oHoraVie.getHoras().substring(10, 15));
                if (hv==false){v[4]=oHoraLun.insertarHoras("VIERNES",oHoraVie.getHoras().substring(0, 5), oHoraVie.getHoras().substring(10, 15));
                                    hv=oHoraVie.buscarPorDia("VIERNES",oHoraVie.getHoras().substring(0, 5), oHoraVie.getHoras().substring(10, 15));}}
                                    
             v=insertaAsignacion(v);
           return v;
     }
     //*********************************
     public int[] insertaAsignacion(int[] v) throws Exception{
         boolean lleno=false;
         if (oHoraLun.getHoras().compareTo("00:00  -  00:00")!=0){v[5]=oAsigcon.insertaAsignaConsAux(oAreaServicio.getClave(), oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), oHoraLun.getClave(), oAsigcon.getCitas1eraVez(), oAsigcon.getCitasSubs(), oTurno.getClave()); lleno=true;}
         if (oHoraMar.getHoras().compareTo("00:00  -  00:00")!=0){v[6]=oAsigcon.insertaAsignaConsAux(oAreaServicio.getClave(), oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), oHoraMar.getClave(), oAsigcon.getCitas1eraVez(), oAsigcon.getCitasSubs(), oTurno.getClave()); lleno=true;}
         if (oHoraMie.getHoras().compareTo("00:00  -  00:00")!=0){v[7]=oAsigcon.insertaAsignaConsAux(oAreaServicio.getClave(), oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), oHoraMie.getClave(), oAsigcon.getCitas1eraVez(), oAsigcon.getCitasSubs(), oTurno.getClave()); lleno=true;}
         if (oHoraJue.getHoras().compareTo("00:00  -  00:00")!=0){v[8]=oAsigcon.insertaAsignaConsAux(oAreaServicio.getClave(), oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), oHoraJue.getClave(), oAsigcon.getCitas1eraVez(), oAsigcon.getCitasSubs(), oTurno.getClave()); lleno=true;}
         if (oHoraVie.getHoras().compareTo("00:00  -  00:00")!=0){v[9]=oAsigcon.insertaAsignaConsAux(oAreaServicio.getClave(), oConsul.getNoConsultorio(), oMedico.getNoTarjeta(), oHoraVie.getClave(), oAsigcon.getCitas1eraVez(), oAsigcon.getCitasSubs(), oTurno.getClave()); lleno=true;}
         if (lleno==false){v[5]=0;}
         
         if (modificacion==1){
             oAsigcon.modificarCitasAsignacion(getMedico().getNoTarjeta(), oAsigcon.getCons().getNoConsultorio(), oAsigcon.getAreaServicio().getClave(), getConsul().getNoConsultorio(), getAreaServicio().getClave(), oAsigcon.getTurno().getClave());
             modificacion=0;
         }
         return v;
     }
     //*********************************
     public void borraProgramacion(){
        int nAfec = 0;
        try{
            nAfec=oAsigcon.eliminar();
        }catch (Exception e){
            e.printStackTrace();
        };
        oAsigcon.getAreaServicio().setDescripcion("");
        oAsigcon.getPH().setNoTarjeta(0);
        oAsigcon.getCons().setNoConsultorio(0);
        oAsigcon=new AsignaConsultorio();
    }
     
    public Horario getHoraLun() {
        return oHoraLun;
    }

    public void setHoraLun(Horario oHoraLun) {
        this.oHoraLun = oHoraLun;
    }

    public Horario getHoraMar() {
        return oHoraMar;
    }

    public void setHoraMar(Horario oHoraMar) {
        this.oHoraMar = oHoraMar;
    }

    public Horario getHoraMie() {
        return oHoraMie;
    }

    public void setHoraMie(Horario oHoraMie) {
        this.oHoraMie = oHoraMie;
    }

    public Horario getHoraJue() {
        return oHoraJue;
    }

    public void setHoraJue(Horario oHoraJue) {
        this.oHoraJue = oHoraJue;
    }

    public Horario getHoraVie() {
        return oHoraVie;
    }

    public void setHoraVie(Horario oHoraVie) {
        this.oHoraVie = oHoraVie;
    }
    
    public void setOp(int x){
        nOp=x;
    }
    
    public int getOp(){
        return nOp;
    }
    
    public void setPrimVezAux(int x){
        primvezAux=x;
    }
    
    public int getPrimVezAux(){
        return primvezAux;
    }
    
    public void setSubAux(int x){
        subAux=x;
    }
    
    public int getSubAux(){
        return subAux;
    }
    
    public boolean habilitaMedico(){
       return getOp()==1;
    }
}
