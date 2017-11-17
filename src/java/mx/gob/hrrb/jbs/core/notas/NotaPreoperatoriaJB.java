
package mx.gob.hrrb.jbs.core.notas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.notas.NotaPreoperatoria;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name="oNoPrepe")
@ViewScoped
public class NotaPreoperatoriaJB implements Serializable {
    
    private boolean bDisable=true;
    private boolean bRender;
    private boolean bReadOnly;
    private boolean bRequired=true;
    private NotaPreoperatoria oNota;
    private ArrayList<NotaPreoperatoria> arrNotas=null;
    private NotaPreoperatoria[] arrNotasAgregadas=null;
    
    private String sNombreBoton;
    private String sIcon;
    private short nOpe;
    private List<Medico> arrMedico;
    
    public NotaPreoperatoriaJB() {
        oNota= new NotaPreoperatoria();
        arrNotas= new ArrayList<NotaPreoperatoria>();
    }
    
    public List<Medico> getListMedico(){
        try{
            List<Medico> listaMedico = null;
            listaMedico = new ArrayList<Medico>(Arrays.asList((new NotaPreoperatoria().buscaMedicos((short) 1))));
            arrMedico= listaMedico;
            return listaMedico;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void buscaNotasPreoperatoriasAgregadas(Paciente oPa){
        try {
            oNota.getEpiMed().setPaciente(oPa);
            arrNotas=oNota.buscarNotasAgregadas();            
        } catch (Exception ex) {
            Logger.getLogger(NotaPreoperatoriaJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscaCedulaProfecional(){
        for(Medico oM:arrMedico){
            if(oM.getNoTarjeta()==this.getNota().getMedicoSupervisor().getNoTarjeta()){
                this.getNota().getMedicoSupervisor().setCedProf(oM.getCedProf());
                break;
            }
        }
    }
    
    public void BuscaInfomacionNota(NotaPreoperatoria oNt){
        try {
            nOpe=1;
            oNota.getEpiMed().getPaciente().setFolioPaciente(oNt.getEpiMed().getPaciente().getFolioPaciente());
            oNota.getEpiMed().getPaciente().setClaveEpisodio(oNt.getEpiMed().getPaciente().getClaveEpisodio());
            oNota.getProcedimientosRealizados().getCIE9().setClave(oNt.getProcedimientosRealizados().getCIE9().getClave());
            oNota.setConsecutivo(oNt.getConsecutivo());
            if(oNota.buscarInfomacionNota()){
                bRender=true;
                bReadOnly=true;
                sNombreBoton="Modificar";
                sIcon="ui-icon-script";
                buscaDignosticoAgregados(oNota);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(NotaPreoperatoriaJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void buscaDignosticoAgregados(NotaPreoperatoria oNt){
        try {
            arrNotasAgregadas=oNt.buscaDianosticosAgregados();
        } catch (Exception ex) {
            Logger.getLogger(NotaPreoperatoriaJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void nuevaNotaPreoperatoria(){
        NotaPreoperatoria oN=oNota;
        oNota= new NotaPreoperatoria();
        oNota.setEpiMed(oN.getEpiMed());
        buscaDignosticoAgregados(oNota);
        bRender=true;
        bReadOnly=false;
        sNombreBoton="Guardar";
        sIcon="ui-icon-disk";
        nOpe=2;
    }
    
    public void nuevaNotaPreoperatoria2(Paciente oP){
        NotaPreoperatoria oN=oNota;
        oNota= new NotaPreoperatoria();
        oNota.getEpiMed().setPaciente(oP);
        buscaDignosticoAgregados(oNota);
        bRender=true;
        bReadOnly=false;
        sNombreBoton="Guardar";
        sIcon="ui-icon-disk";
        nOpe=2;
    }
    
    public boolean verificaDiagnosticoRepetido(){
        boolean bRet=false;
        if(this.getArrNotasAgregadas()!=null && this.getArrNotasAgregadas().length>0){
           for(NotaPreoperatoria oN: this.getArrNotasAgregadas()){
                if(oN.getDiagnosticoPreoperatorio().getDescripcionDiag().equals(this.getNota().getDiagnosticoPreoperatorio().getDescripcionDiag())){
                    bRet=true;
                    break;
                }
            } 
        }
        
        return bRet;
    }
    
    public void buscaDiagnosticoRepetido(){
        if(verificaDiagnosticoRepetido()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se puede agregar diagnóstico ya esta agregado"));
        }
    }
    public void metodoGuardarOModificar(){
        if(nOpe==1){
            try {
                if(oNota.modificaNotaPreoperatoriaGeneral()>0){
                    bRender=false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","MODIFICACIÓN DE LA NOTA CORRECTA"));
                }
            } catch (Exception ex) {
                Logger.getLogger(NotaPreoperatoriaJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(nOpe==2){
            if(verificaDiagnosticoRepetido()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se puede agregar diagnóstico ya esta agregado"));
            }else{
                try {
                    if(oNota.insertarNotaPreoperatoriaGeneral()>0){
                        bRender=false;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFORMACION","NOTA GUARDADA CORRECTAMENTE"));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(NotaPreoperatoriaJB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    public void habilitaHora(SelectEvent dateSelect){
        bDisable = false;
    }
   
    public boolean getDisable() {
        return bDisable;
    }
    
    public boolean getRender(){
        return bRender;
    }
    
    public void setRender(boolean b){
        this.bRender=b;
    }
    
    public NotaPreoperatoria getNota() {
        return oNota;
    }

    public ArrayList<NotaPreoperatoria> getArrNotas() {
        return arrNotas;
    }

    public boolean isReadOnly() {
        return bReadOnly;
    }

    public NotaPreoperatoria[] getArrNotasAgregadas() {
        return arrNotasAgregadas;
    }

    public String getNombreBoton() {
        return sNombreBoton;
    }

    public String getIcon() {
        return sIcon;
    }

    public boolean getRequired() {
        return bRequired;
    }
    
}
