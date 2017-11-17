/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Paciente;

/**
 *
 * @author Javi
 */
@ManagedBean(name="oPacUrg")
@SessionScoped
public class PacUrgencias {
private Paciente oPaciente;
    /**
     * Creates a new instance of PacUrgencias
     */
    public PacUrgencias() {
    oPaciente = new Paciente();
    }

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }
    
}
