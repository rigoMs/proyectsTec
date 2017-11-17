package mx.gob.hrrb.jbs.core.notas;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.notas.NotaPostoperatoria;
import mx.gob.hrrb.modelo.core.notas.NotaPreoperatoria;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

@ManagedBean(name = "notapoo")
@ViewScoped
public class notaPostOperatoriaJB implements Serializable {    
    private NotaPostoperatoria oNotaPost;
    private NotaPostoperatoria oTempNotaPost;
    private ArrayList<NotaPostoperatoria> arrNotapostoperatoria;
    private boolean bMuestraPantalla;
    private NotaPreoperatoria[] arrDxAgregados;
    private Parametrizacion[] arrTipocirugia;
    private ArrayList sValorCirugia;
    private boolean bLimpia;
    private boolean bLimpiaContaminada;
    private boolean bContaminada;
    private boolean bSucia;
    private boolean bBanderaMedico;
    private boolean bTab1;
    private boolean bTab2;
    private boolean bMotivo;
    private boolean bBoton;
    private boolean bModificar;    
    private NotaPreoperatoria oNotaPreoperatoria;
    private NotaPreoperatoria oTemp;
    private NotaPreoperatoria[] arrNotaPreoperatoria;
    public notaPostOperatoriaJB() {
        this.oNotaPreoperatoria = new NotaPreoperatoria();        
        this.bTab1 = true;
        this.bTab2 = false;        
    }
//***********************INICIAN METODOS DE CONTROL DE DATOS***********************
    public void requerir(TabChangeEvent event){
        if(event.getTab().getId().compareTo("tab1") != 0){
           this.bTab1 = !bTab1;
           this.bTab2 = !bTab2;
        }else{
            this.bTab1 = !bTab1;
            this.bTab2 = !bTab2;
        }   
    }
    public void cargaDatosPaciente(short opcion){
        try{
            this.oNotaPreoperatoria.getEpiMed().getPaciente().setOpcionUrg(opcion); 
            this.arrNotaPreoperatoria = this.oNotaPreoperatoria.buscaNotasPaciente();
            if(this.arrNotaPreoperatoria == null)
                this.setMuestraPantalla(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void buscaNotasPorPaciente(NotaPreoperatoria opaciente, NotaPostoperatoria oNotapost, short opc){
        try{
            if(opc == 0){
                this.bMuestraPantalla = false;
                this.oNotaPost = new NotaPostoperatoria();                
                this.oTempNotaPost = new NotaPostoperatoria();
                this.setTemp(opaciente);
                this.oNotaPost.getEpiMed().getPaciente().setFolioPaciente(opaciente.getEpiMed().getPaciente().getFolioPaciente());
                this.oNotaPost.getEpiMed().getPaciente().setClaveEpisodio(opaciente.getEpiMed().getPaciente().getClaveEpisodio());
                this.oNotaPost.getCIE9().setClave(opaciente.getCIE9().getClave());                
                this.arrNotapostoperatoria = this.oNotaPost.buscaNotasAgregadas();                
                if(this.arrNotapostoperatoria != null){
                    if(this.arrNotapostoperatoria.size() > 0)
                        this.oTempNotaPost = this.arrNotapostoperatoria.get(0);                
                    if(this.arrNotapostoperatoria.size() == 1 && this.arrNotapostoperatoria.get(0).getConsecutivo() == 0 && this.arrNotapostoperatoria.get(0).getMaxConsecutivo() != 0)
                        this.bBoton = true;                
                    else                    
                        this.bBoton = false;
                }else{
                    this.oTempNotaPost.getEpiMed().getPaciente().setFolioPaciente(opaciente.getEpiMed().getPaciente().getFolioPaciente());
                    this.oTempNotaPost.getEpiMed().getPaciente().setClaveEpisodio(opaciente.getEpiMed().getPaciente().getClaveEpisodio());
                    this.oTempNotaPost.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().setClave(opaciente.getCIE9().getClave());                    
                    this.oTempNotaPost.setMaxConsecutivo(opaciente.getMaxConsecutivo());
                }
            }else if(opc == 1){
                this.setModificar(true);
                this.oNotaPost = new NotaPostoperatoria();
                this.oNotaPost.getEpiMed().getPaciente().setFolioPaciente(oNotapost.getEpiMed().getPaciente().getFolioPaciente());
                this.oNotaPost.getEpiMed().getPaciente().setClaveEpisodio(oNotapost.getEpiMed().getPaciente().getClaveEpisodio());
                this.oNotaPost.getProcedimientosRealizados().getCIE9().setClave(oNotapost.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave());
                this.oNotaPost.setConsecutivo(oNotapost.getConsecutivo());
                this.oNotaPost.getNotaPreoperatoria().getEpiMed().getPaciente().setFolioPaciente(oNotapost.getEpiMed().getPaciente().getFolioPaciente());
                this.oNotaPost.getNotaPreoperatoria().getEpiMed().getPaciente().setClaveEpisodio(oNotapost.getEpiMed().getPaciente().getClaveEpisodio());
                this.oNotaPost.buscaDatosCabecera();
                this.buscaDiagnosticoAgregados();
                if(this.oNotaPost.getConsecutivo() != 0)
                    this.oNotaPost.buscaDetalleAnverso();
                this.oNotaPost.buscaPersonal();
                this.setLimpia(false);
                this.setLimpiaContaminada(false);
                this.setContaminda(false);
                this.setSucia(false);                
                this.oNotaPost.buscaDetalleReverso();
                this.habilita();
                this.habilitaMotivo();
                this.valorCirugiaLimpia();
                this.valorCirugiaLimpiaContaminada();
                this.valorCirugiaContaminada();
                this.valorCirugiaSucia();                
                this.setMuestraPantalla(true);
            }else if(opc == 2){
                this.setModificar(false);
                this.oNotaPost = new NotaPostoperatoria();
                this.oNotaPost.getEpiMed().getPaciente().setFolioPaciente(oTempNotaPost.getEpiMed().getPaciente().getFolioPaciente());
                this.oNotaPost.getEpiMed().getPaciente().setClaveEpisodio(oTempNotaPost.getEpiMed().getPaciente().getClaveEpisodio());
                this.oNotaPost.getProcedimientosRealizados().getCIE9().setClave(oTempNotaPost.getNotaPreoperatoria().getProcedimientosRealizados().getCIE9().getClave());                                                
                this.oNotaPost.setConsecutivo(this.oTempNotaPost.getMaxConsecutivo() + 1);                
                this.oNotaPost.buscaDatosCabecera();
                this.oNotaPost.getNotaPreoperatoria().getEpiMed().getPaciente().setFolioPaciente(oTempNotaPost.getEpiMed().getPaciente().getFolioPaciente());
                this.oNotaPost.getNotaPreoperatoria().getEpiMed().getPaciente().setClaveEpisodio(oTempNotaPost.getEpiMed().getPaciente().getClaveEpisodio());
                this.buscaDiagnosticoAgregados();
                this.oNotaPost.buscaPersonal();  
                habilitaMotivo();
                this.habilita();
                this.setLimpia(false);
                this.setLimpiaContaminada(false);
                this.setContaminda(false);
                this.setSucia(false);
                this.setMuestraPantalla(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void buscaDiagnosticoAgregados(){
        try{
            arrDxAgregados = this.getNotaPostoperatoria().getNotaPreoperatoria().buscaDianosticosAgregados();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void guardAnverso(){        
        try{
            if(this.getModificar()){
                if(this.oNotaPost.modificaAnverdoNotaPostoperatoria()){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }
            }else{
                if(this.oNotaPost.insertAnversoNotaPostoperatoria()){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                    RequestContext.getCurrentInstance().update("busqueda:msgs");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void asignaCirugiaLimpia(){        
        this.oNotaPost.getArregloCirugias().get(0).setTipoParametro(this.getLimpia() ? "1" : "");
    }
    public void valorCirugiaLimpia(){
        this.setLimpia(this.oNotaPost.getArregloCirugias().get(0).getTipoParametro().compareTo("1") == 0 ? true : false);
    }
    public void asignaCirugiaLimpiaContaminda(){
        this.oNotaPost.getArregloCirugias().get(1).setTipoParametro(this.getLimpiaContaminada() ? "2" : "");
    }
    public void valorCirugiaLimpiaContaminada(){
        this.setLimpiaContaminada(this.oNotaPost.getArregloCirugias().get(1).getTipoParametro().compareTo("2") == 0 ? true : false);
    }
    public void asignaCirugiaContaminada(){
        this.oNotaPost.getArregloCirugias().get(2).setTipoParametro(this.getContaminda() ? "3" : "");
    }
    public void valorCirugiaContaminada(){
        this.setContaminda(this.oNotaPost.getArregloCirugias().get(2).getTipoParametro().compareTo("3") == 0 ? true : false);
    }
    public void asignaCirugiaSucia(){
        this.oNotaPost.getArregloCirugias().get(3).setTipoParametro(this.getSucia() ? "4" : "");
    }
    public void valorCirugiaSucia(){
        this.setSucia(this.oNotaPost.getArregloCirugias().get(3).getTipoParametro().compareTo("4") == 0 ? true : false);
    }
    public void guardaReverso(){
        try{
            if(this.oNotaPost.inserReversoNotaPostoperatoria()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");                
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void habilita(){        
        this.bBanderaMedico = this.oNotaPost.getEnvioPiezasBiopcia() ? false : true;
        if(this.bBanderaMedico == true)
            this.oNotaPost.getMedicoLlevoPieza().setNoTarjeta(0);        
    }
    public void habilitaMotivo(){
        this.bMotivo = this.oNotaPost.getAmeritaUCI() ? false : true;
        if(this.bMotivo)
            this.oNotaPost.setMotivo("");
    }
    public String getBuscaMedico(){
        try{
            ArrayList<Medico> arrMedico = arrMedico = this.oNotaPost.getProcedimientosRealizados().getCAyudanteDos().buscaAyudanteInstrumentistaCirculante((short) 1);
            for(Medico i: arrMedico)
                if(i.getNoTarjeta() == this.oNotaPost.getProcedimientosRealizados().getCAyudanteDos().getNoTarjeta())
                   return i.getNombreCompleto();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getBuscaCirujanoAyudenteDos(){
        try{
            ArrayList<Medico> arrMedico = arrMedico = this.oNotaPost.getProcedimientosRealizados().getCAyudanteDos().buscaAyudanteInstrumentistaCirculante((short) 1);
            for(Medico i: arrMedico)
                if(i.getNoTarjeta() == this.oNotaPost.getMedicoLlevoPieza().getNoTarjeta())
                    return i.getNombreCompleto();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
//***********************TERMINAN METODOS DE CONTROL DE DATOS***********************
//***********************INICIAN METODOS SET'S Y GET'S***********************
    public NotaPreoperatoria getNotaPreoperatoria(){
        return this.oNotaPreoperatoria;
    }
    public void setNotaPreoperatoria(NotaPreoperatoria oNotaPreoperatoria){
        this.oNotaPreoperatoria = oNotaPreoperatoria;
    }
    public NotaPreoperatoria[] getNotasPreoperatorias(){
        return this.arrNotaPreoperatoria;
    }
    public void setNotasPreoperatorias(NotaPreoperatoria[] arrNotaPreoperatoria){
        this.arrNotaPreoperatoria = arrNotaPreoperatoria;
    }
    
    public boolean getMuestraPantalla(){
        return bMuestraPantalla;
    }
    public void setMuestraPantalla(boolean bMuestraPantalla){
        this.bMuestraPantalla = bMuestraPantalla;
    }
    public NotaPostoperatoria getNotaPostoperatoria(){
        return oNotaPost;
    }
    public void setNotaPostoperatoria(NotaPostoperatoria oNotaPost){
        this.oNotaPost = oNotaPost;
    }
    public ArrayList<NotaPostoperatoria> getNotasPostoperatorias(){
        return arrNotapostoperatoria;
    }
    public void setNotasPostoperatorias(ArrayList<NotaPostoperatoria> arrNotapostoperatoria){
        this.arrNotapostoperatoria = arrNotapostoperatoria;
    }
    public NotaPreoperatoria[] getDiagnosticosAgregados(){
        return arrDxAgregados;
    }
    public void setDiagnosticosAgregados(NotaPreoperatoria[] arrDxAgregados){
        this.arrDxAgregados = arrDxAgregados;
    }
    public Parametrizacion[] getArrTipocirugia(){
        return arrTipocirugia;
    }
    public void setArrTipocirugia(Parametrizacion[] arrTipocirugia){
        this.arrTipocirugia = arrTipocirugia;
    }
    public ArrayList getValorCirugia(){
        return sValorCirugia;
    }
    public void setValorCirugia(ArrayList sValorCirugia){
        this.sValorCirugia = sValorCirugia;
    }
    public boolean getLimpia(){
        return bLimpia;
    }
    public void setLimpia(boolean bLimpia){
        this.bLimpia = bLimpia;
    }
    public boolean getLimpiaContaminada(){
        return bLimpiaContaminada;
    }
    public void setLimpiaContaminada(boolean bLimpiaContaminada){
        this.bLimpiaContaminada = bLimpiaContaminada;
    }
    public boolean getContaminda(){
        return bContaminada;
    }
    public void setContaminda(boolean bContaminada){
        this.bContaminada = bContaminada;
    }
    public boolean getSucia(){
        return bSucia;
    }
    public void setSucia(boolean bSucia){
        this.bSucia = bSucia;
    }
    public boolean getBanderaMedico(){
        return bBanderaMedico;
    }
    public void setBanderaMedico(boolean bBanderaMedico){
        this.bBanderaMedico = bBanderaMedico;
    }
    public boolean getMotivo(){
        return bMotivo;
    }
    public void setMotivo(boolean bMotivo){
        this.bMotivo = bMotivo;
    }
    public boolean getTab1(){
        return bTab1;
    }
    public void setTab1(boolean bTab1){
        this.bTab1 = bTab1;
    }
    public boolean getTab2(){
        return bTab2;
    }
    public void setTab2(boolean bTab2){
        this.bTab2 = bTab2;
    }
    public NotaPostoperatoria getTempNotaPostoperatoria(){
        return oTempNotaPost;
    }
    public void setTempNotaPostoperatoria(NotaPostoperatoria oTempNotaPost){
        this.oTempNotaPost = oTempNotaPost;
    }
    public boolean getBanderaBoton(){
        return bBoton;
    }
    public void setBanderaBoton(boolean bBoton){
        this.bBoton = bBoton;
    }
    public boolean getModificar(){
        return bModificar;
    }
    public void setModificar(boolean bModificar){
        this.bModificar = bModificar;
    }    
    public NotaPreoperatoria getTemp(){
        return this.oTemp;
    }
    public void setTemp(NotaPreoperatoria oTemp){
        this.oTemp = oTemp;
    }
//***********************TERMINAN METODOS SET'S Y GET'S***********************
}
