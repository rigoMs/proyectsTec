/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import java.util.Date;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 *
 * @author DavidH
 */
public class RecibirRequisicion implements Serializable {
    
    private Date dFechaSol;
    private RequisicionInterna oRequisicion;
    private PersonalHospitalario oRecibio;
    private PersonalHospitalario oSubdAdministrativa;

    
    
    
    
    public Date getdFechaSol() {
        return dFechaSol;
    }
    public void setdFechaSol(Date dFechaSol) {
        this.dFechaSol = dFechaSol;
    }
    public RequisicionInterna getoRequisicion() {
        return oRequisicion;
    }
    public void setoRequisicion(RequisicionInterna oRequisicion) {
        this.oRequisicion = oRequisicion;
    }
    public PersonalHospitalario getoRecibio() {
        return oRecibio;
    }
    public void setoRecibio(PersonalHospitalario oRecibio) {
        this.oRecibio = oRecibio;
    }
    public PersonalHospitalario getoSubdAdministrativa() {
        return oSubdAdministrativa;
    }
    public void setoSubdAdministrativa(PersonalHospitalario oSubdAdministrativa) {
        this.oSubdAdministrativa = oSubdAdministrativa;
    }
}
