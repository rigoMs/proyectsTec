/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;

/**
 *
 * @author DavidH
 */
public class ProductosABasicos implements Serializable {
    
    
    private Requisicion oRequisicion;
    private RequisicionInterna arrInterna[];
    private RequisicionBasica arrBasica[];

    
    
    public Requisicion getRequisicion() {
        return oRequisicion;
    }
    public void setRequisicion(Requisicion oRequisicion) {
        this.oRequisicion = oRequisicion;
    }
}
