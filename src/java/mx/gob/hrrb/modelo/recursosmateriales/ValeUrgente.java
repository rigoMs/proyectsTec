/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 *
 * @author DavidH
 */
public class ValeUrgente implements Serializable {
    
    private String sServicio;
    private String sProducto;
    private String sEspecificaciones;
    private double nCostoAproximado;
    private AreaServicioHRRB oDepartamento;
    private String sLugarCompra;
    private PersonalHospitalario oSolicita;
    private PersonalHospitalario oAutoriza;
    private PersonalHospitalario oRecibe;

    
    
    
    
    public String getsServicio() {
        return sServicio;
    }
    public void setsServicio(String sServicio) {
        this.sServicio = sServicio;
    }
    public String getsProducto() {
        return sProducto;
    }
    public void setsProducto(String sProducto) {
        this.sProducto = sProducto;
    }
    public String getsEspecificaciones() {
        return sEspecificaciones;
    }
    public void setsEspecificaciones(String sEspecificaciones) {
        this.sEspecificaciones = sEspecificaciones;
    }
    public double getnCostoAproximado() {
        return nCostoAproximado;
    }
    public void setnCostoAproximado(double nCostoAproximado) {
        this.nCostoAproximado = nCostoAproximado;
    }
    public AreaServicioHRRB getoDepartamento() {
        return oDepartamento;
    }
    public void setoDepartamento(AreaServicioHRRB oDepartamento) {
        this.oDepartamento = oDepartamento;
    }
    public String getsLugarCompra() {
        return sLugarCompra;
    }
    public void setsLugarCompra(String sLugarCompra) {
        this.sLugarCompra = sLugarCompra;
    }
    public PersonalHospitalario getoSolicita() {
        return oSolicita;
    }
    public void setoSolicita(PersonalHospitalario oSolicita) {
        this.oSolicita = oSolicita;
    }
    public PersonalHospitalario getoAutoriza() {
        return oAutoriza;
    }
    public void setoAutoriza(PersonalHospitalario oAutoriza) {
        this.oAutoriza = oAutoriza;
    }
    public PersonalHospitalario getoRecibe() {
        return oRecibe;
    }
    public void setoRecibe(PersonalHospitalario oRecibe) {
        this.oRecibe = oRecibe;
    }
}
