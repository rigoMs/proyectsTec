package mx.gob.hrrb.jbs.core.notas;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.notas.NotaPreanestesica;
import org.primefaces.context.RequestContext;
@ManagedBean(name = "notapa")
@ViewScoped
public class notaPreanestesicaJB implements Serializable {
    private Paciente oPaciente;
    private Paciente[] arrPaciente;
    private boolean bMuestraPantalla;
    private NotaPreanestesica oNotapreanestesica;
    private NotaPreanestesica[] arrNotas;    
    private Date dFecha;
    private Date dHora;
    private boolean bHabilitaHora;
    private boolean bGuaMod;
    public notaPreanestesicaJB() {        
        this.oPaciente = new Paciente();
        this.oNotapreanestesica = new NotaPreanestesica();        
    }
//******************INICIAN METODOS DE CONTROL DE DATOS******************
    public void cargaDatosPaciente(short opcion){
        try{
            this.setMuestraPantalla(false);
            this.oPaciente.setOpcionUrg(opcion);
            this.arrPaciente = this.oPaciente.buscarPacientesConExpediente();
            if(this.arrPaciente == null)
                this.setMuestraPantalla(false);
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public void cargaDatosGenerales(long foliopaciente, long clave, int exp, int consecutivo, short opc){
        try{
            this.dFecha = null;
            this.dHora = null;
            this.bHabilitaHora = true;
            if(opc == 0){
                this.oNotapreanestesica = new NotaPreanestesica();
                this.oNotapreanestesica.getEpiMed().getPaciente().setFolioPaciente(foliopaciente);
                this.oNotapreanestesica.getEpiMed().getPaciente().setClaveEpisodio(clave);
                this.oNotapreanestesica.buscaDatosCabecera();
                this.oNotapreanestesica.buscaPersonal();
                this.setGuardaModifica(true);
                this.setMuestraPantalla(true);
            }else if(opc == 1){
                this.oNotapreanestesica = new NotaPreanestesica();
                this.oNotapreanestesica.getEpiMed().getPaciente().setFolioPaciente(foliopaciente);
                this.oNotapreanestesica.getEpiMed().getPaciente().setClaveEpisodio(clave);
                this.oNotapreanestesica.setConsecutivo(consecutivo);
                this.oNotapreanestesica.buscaDatosCabecera();
                this.oNotapreanestesica.buscaDatosNotaEspecifica();
                this.dFecha = this.oNotapreanestesica.getFechaRegistro();
                this.dHora = this.oNotapreanestesica.getFechaRegistro();
                this.setGuardaModifica(false);
                this.setMuestraPantalla(true);                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void buscaNotasporPaciente(long fpaciente, long clave){
        try{
            this.oNotapreanestesica = new NotaPreanestesica();
            this.oNotapreanestesica.getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oNotapreanestesica.getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.arrNotas = this.oNotapreanestesica.buscarTodosNotasPorPaciente();
            this.setMuestraPantalla(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void habilita(){
        this.dHora = null;
        this.setHabilitahora(false);
    }
    public void asignaFecha(){
        long suma = (this.getFecha().getTime() + this.getHora().getTime())-21600000; //EQUIVALENTE A UNA 6 HORAS;
        this.oNotapreanestesica.setFechaRegistro(new Date());
        this.oNotapreanestesica.getFechaRegistro().setTime(suma);
    }
    public void guardaDatos(){
        try{
            if(this.getGuardaModifica()){
                if(this.oNotapreanestesica.inserta()){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }
            }else{
                if(this.oNotapreanestesica.modificaNota()){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "MODIFICACIÓN EXITOSA"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL MODIFICAR LOS DATOS"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
//******************TERMINAN METODOS DE CONTROL DE DATOS******************    
//******************INICIAN METODOS SETS Y GETS******************
    public Paciente getPaciente(){
        return oPaciente;
    }
    public void setPaciente(Paciente oPaciente){
        this.oPaciente = oPaciente;
    }
    public Paciente[] getArrPaciente(){
        return arrPaciente;
    }
    public void setArrPaciente(Paciente[] arrPaciente){
        this.arrPaciente = arrPaciente;
    }
    public boolean getMuestraPantalla(){
        return bMuestraPantalla;
    }
    public void setMuestraPantalla(boolean bMuestraPantalla){
        this.bMuestraPantalla = bMuestraPantalla;
    }
    public NotaPreanestesica getNotaPreanestesica(){
        return oNotapreanestesica;
    }
    public void setNotaPreanestesica(NotaPreanestesica oNotapreanestesica){
        this.oNotapreanestesica = oNotapreanestesica;
    }
    public Date getFechaActual(){
        return new Date();
    }
    public Date getFecha(){
        return dFecha;
    }
    public void setFecha(Date dFecha){
        this.dFecha = dFecha;
    }
    public Date getHora(){
        return dHora;
    }
    public void setHora(Date dHora){
        this.dHora = dHora;
    }
    public boolean getHabilitahora(){
        return bHabilitaHora;
    }
    public void setHabilitahora(boolean bHabilitaHora){
        this.bHabilitaHora = bHabilitaHora;
    }
    public NotaPreanestesica[] getNotas(){
        return arrNotas;
    }
    public void setNotas(NotaPreanestesica[] arrNotas){
        this.arrNotas = arrNotas;
    }
    public boolean getGuardaModifica(){
        return bGuaMod;
    }
    public void setGuardaModifica(boolean bGuaMod){
        this.bGuaMod = bGuaMod;
    }
//******************TERMINAN METODOS SETS Y GETS******************
}
