
package mx.gob.hrrb.jbs.archivo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Paciente;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Danny
 */

@ManagedBean (name = "oCedPac")
@ViewScoped
public class ConsultarCedulaPacienteJB {
    private Paciente oPaciente, listaPacientes[];
    
    private boolean bNom;
    private boolean bExp;
    
    public ConsultarCedulaPacienteJB() {
        oPaciente= new Paciente();
    }
    
    
    public void buscarPacientes(int valor){
        String msg="";
        listaPacientes=null;
        try{
            oPaciente.setOpcionUrg(valor);
            listaPacientes=oPaciente.buscarExp();
        }catch(Exception e){
            e.printStackTrace();
            FacesContext context= FacesContext.getCurrentInstance();
            msg="Error al buscar pacientes";
            context.addMessage(null, new FacesMessage("Consultar Cédula Paciente", msg));
        }
    }
    
    public void buscaDetalle(long folioPaciente, int numeroExp){
        try{
            oPaciente.setFolioPaciente(folioPaciente);
            oPaciente.getExpediente().setNumero(numeroExp);
            oPaciente.buscarPacARCHIVO();
            oPaciente.getExpediente().buscaEstatusExpedienteBD(folioPaciente,numeroExp);
            RequestContext.getCurrentInstance().execute("PF('dlgDetalles').show();");
            
        }catch(Exception ex){Logger.getLogger(ConsultarCedulaPacienteJB.class.getName()).log(Level.SEVERE,null,ex);}
    }
    
    
    public Paciente[] getListaInformacion(){
        return listaPacientes;
    }
    
    public boolean getNom() {
        return bNom;
    }

    public void setNom(boolean bNom) {
        this.bNom = bNom;
    }
    
    public boolean getExp() {
        return bExp;
    }

    public void setExp(boolean bExp) {
        this.bExp = bExp;
    }
    
    public void requerir(TabChangeEvent event){        
        if(event.getTab().getId().equals("tabE")){
            bNom=false;
            bExp=true;
        }else if(event.getTab().getId().equals("tabN")){
            bNom=true;
            bExp=false;
        }
        //if(event.getTab()==)
    }
    
    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }
    
}
