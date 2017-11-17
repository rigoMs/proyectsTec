package mx.gob.hrrb.jbs.servicios;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.*;
import mx.gob.hrrb.modelo.serv.*;
import java.util.List;

/**
 *
 * @author Pablo
 */

@ManagedBean(name = "OtServ")
@SessionScoped
public abstract class OtorgarServicio implements Serializable{

    private AreaServicioHRRB oArea;
    private EpisodioMedico oEpisodio;
    private Paciente oPaciente;
    private ServicioRealizado oServReal;
    
    public OtorgarServicio(){
        oArea = new AreaServicioHRRB();
        oEpisodio = new EpisodioMedico();
        oPaciente = new Paciente();
   
    }
    
    //public abstract List buscarTodos();
    
    public abstract List buscarDetalleSolicitud();
    
    public abstract void listaOrdenes();
    
    

    public AreaServicioHRRB getArea() {
        return oArea;
    }

    public void setArea(AreaServicioHRRB oArea) {
        this.oArea = oArea;
    }

    public EpisodioMedico getEpisodio() {
        return oEpisodio;
    }

    public void setEpisodio(EpisodioMedico oEpisodio) {
        this.oEpisodio = oEpisodio;
    }

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public ServicioRealizado getServReal() {
        return oServReal;
    }

    public void setServReal(ServicioRealizado oServReal) {
        this.oServReal = oServReal;
    }
    
}
