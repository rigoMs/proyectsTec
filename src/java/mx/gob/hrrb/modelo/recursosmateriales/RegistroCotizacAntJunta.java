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
public class RegistroCotizacAntJunta implements Serializable {
    
    private double nCajaOperacion;
    private double nCajaRecuperacion;
    private double nCajaCapasits;
    private double nCajaCatastrofico;
    private double nCajaMantenimientos;
    private FondoCajaCompras oFondoCajaComp;
    private double nReposicion;
    private double nViaticos;
    private Recuperacion oRecuperacion;

    
    
    
    public double getnCajaOperacion() {
        return nCajaOperacion;
    }
    public void setnCajaOperacion(double nCajaOperacion) {
        this.nCajaOperacion = nCajaOperacion;
    }
    public double getnCajaRecuperacion() {
        return nCajaRecuperacion;
    }
    public void setnCajaRecuperacion(double nCajaRecuperacion) {
        this.nCajaRecuperacion = nCajaRecuperacion;
    }
    public double getnCajaCapasits() {
        return nCajaCapasits;
    }
    public void setnCajaCapasits(double nCajaCapasits) {
        this.nCajaCapasits = nCajaCapasits;
    }
    public double getnCajaCatastrofico() {
        return nCajaCatastrofico;
    }
    public void setnCajaCatastrofico(double nCajaCatastrofico) {
        this.nCajaCatastrofico = nCajaCatastrofico;
    }
    public double getnCajaMantenimientos() {
        return nCajaMantenimientos;
    }
    public void setnCajaMantenimientos(double nCajaMantenimientos) {
        this.nCajaMantenimientos = nCajaMantenimientos;
    }
    public FondoCajaCompras getoFondoCajaComp() {
        return oFondoCajaComp;
    }
    public void setoFondoCajaComp(FondoCajaCompras oFondoCajaComp) {
        this.oFondoCajaComp = oFondoCajaComp;
    }
    public double getnReposicion() {
        return nReposicion;
    }
    public void setnReposicion(double nReposicion) {
        this.nReposicion = nReposicion;
    }
    public double getnViaticos() {
        return nViaticos;
    }
    public void setnViaticos(double nViaticos) {
        this.nViaticos = nViaticos;
    }
    public Recuperacion getoRecuperacion() {
        return oRecuperacion;
    }
    public void setoRecuperacion(Recuperacion oRecuperacion) {
        this.oRecuperacion = oRecuperacion;
    }
}
