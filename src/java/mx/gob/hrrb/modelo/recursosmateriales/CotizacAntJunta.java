/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author DavidH
 */
public class CotizacAntJunta implements Serializable {
    
    private FondoCajaCompras oFondoCajaComp;
    private RequisicionBasica oRequisicionBasica;
    private Recuperacion oRecuperacion;
    private RequisicionInterna oRequisicionInt;
    private String sPrioridad;
    private AccesoDatos oAD;

    
    
    
    
    public FondoCajaCompras getFondoCajaComp() {
        return oFondoCajaComp;
    }
    public void setFondoCajaComp(FondoCajaCompras oFondoCajaComp) {
        this.oFondoCajaComp = oFondoCajaComp;
    }
    public RequisicionBasica getRequisicionBasica() {
        return oRequisicionBasica;
    }
    public void setRequisicionBasica(RequisicionBasica oRequisicionBasica) {
        this.oRequisicionBasica = oRequisicionBasica;
    }
    public Recuperacion getRecuperacion() {
        return oRecuperacion;
    }
    public void setRecuperacion(Recuperacion oRecuperacion) {
        this.oRecuperacion = oRecuperacion;
    }
    public RequisicionInterna getRequisicionInt() {
        return oRequisicionInt;
    }
    public void setRequisicionInt(RequisicionInterna oRequisicionInt) {
        this.oRequisicionInt = oRequisicionInt;
    }
    public String getPrioridad() {
        return sPrioridad;
    }
    public void setPrioridad(String sPrioridad) {
        this.sPrioridad = sPrioridad;
    }
}
