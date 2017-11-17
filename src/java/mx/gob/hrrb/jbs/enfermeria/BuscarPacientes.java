
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.Paciente;

/**
 *
 * @author Javier
 */
@ManagedBean(name="oBuscarPaciente")
@ViewScoped
public class BuscarPacientes implements Serializable{

    private Paciente oPaciente;
    
    public BuscarPacientes() {
        oPaciente= new Paciente();        
    }
    
    public Paciente getPaciente(){
        return oPaciente;
    }
    
    public void setPaciente(Paciente oPac){
        this.oPaciente=oPac;
    }
    
    public void limpiarPacientesEncontrados(){
        oPaciente = new Paciente();
    }           
}
