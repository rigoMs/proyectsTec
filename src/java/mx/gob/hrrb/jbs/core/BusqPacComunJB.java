package mx.gob.hrrb.jbs.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Paciente;

/**
 * Bean Administrado para el manejo de búsquedas de pacientes
 * (componente reutilizable)
 * @author BAOZ
 */
@ManagedBean(name = "oBusqPacComunJB")
@ViewScoped
public class BusqPacComunJB implements Serializable{
private Paciente oPaciente = new Paciente();
private String sVisibilidadTabla="hidden";
private ArrayList<Paciente> arrPacs=null;
private String boton="hidden";
private String boton2="hidden";
private EpisodioMedico oEpiMed = new EpisodioMedico();
private ArrayList<EpisodioMedico> arrEpiMed;

    public BusqPacComunJB() {
    }
    
    public void inicializar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        session.setAttribute("oPacienteSeleccionado", new Paciente());
        oPaciente = new Paciente();
        oPaciente.setAdmUrg(0);//Para que incluya los que no tienen expediente
        arrPacs=null;
    }
    
    public void buscaPaciente(){
        sVisibilidadTabla="visible";
        try{
            arrPacs = new ArrayList<Paciente>(Arrays.asList(oPaciente.buscarPac()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void buscaPacienteAtenMed(){
        sVisibilidadTabla="visible";
        boton="visible";
        try{
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(oEpiMed.buscarDatosPacienteAtencionMedica()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void buscaPacientesinAltaFisica(){
        sVisibilidadTabla="visible";
        boton="visible";
        boton2="visible";
        try{
          arrPacs = new ArrayList<Paciente>(Arrays.asList(
                  oPaciente.buscaSinAltaFisica())); 
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void buscaTodoslosPacientes(){
        sVisibilidadTabla="visible";
        boton="visible";
        boton2="visible";
        try{
           arrPacs = new ArrayList<Paciente>(Arrays.asList(
                   oPaciente.buscaTodoslosPacientes())); 
        }catch (Exception e){
            e.printStackTrace();
        }
        
    } 
    
    public ArrayList<Paciente> getListaPac(){
        return arrPacs;
    }
    
    public Paciente getPaciente() {
        return oPaciente;
    }
    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }
    public String getVisibilidadTabla(){
        return this.sVisibilidadTabla;
    }
    
    public Paciente getSeleccionado(){
        return new Paciente();
    }
    public void setSeleccionado(Paciente valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().
        getSession(false);
        session.setAttribute("oPacienteSeleccionado", valor);
        
        //System.out.println("Seleccionado "+valor.getNombres());
    }

    public EpisodioMedico getEpiMed() {
        return oEpiMed;
    }
    public void setEpiMed(EpisodioMedico oEpiMed) {
        this.oEpiMed = oEpiMed;
    }
    
    public ArrayList<EpisodioMedico> getListaEpiMed(){
        return arrEpiMed;
    }
    
    public EpisodioMedico getSeleccionado2(){
        return new EpisodioMedico();
    }
        
    public void setSeleccionado2(EpisodioMedico valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteselecc", valor);
    }
    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton2) {
        this.boton = boton2;
    }
    public String getBoton2() {
        return boton2;
    }

    public void setBoton2(String boton2) {
        this.boton2 = boton2;
    }
}
