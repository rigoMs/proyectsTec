/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.caja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mx.gob.hrrb.modelo.caja.AutorizacionPago;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oCobrarAnt")
@RequestScoped
public class CobrarAnticipoJB {
    private AutorizacionPago oAutPag=new AutorizacionPago();
    private AutorizacionPago selectedAutPag;
    private AutorizacionPago arrAutorizacionesPago[];
    /**
     * Creates a new instance of CobrarAnticipoJB
     */
    public CobrarAnticipoJB() {
        //llenaLista();
    }
    
    public void metodo(){
        System.out.println("Se invocó");
    }
    
    private void llenaLista(){
        try {
            arrAutorizacionesPago=oAutPag.buscarTodos();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    private void cobrar(){
        
    }

    public AutorizacionPago getSelectedAutPag() {
        return selectedAutPag;
    }

    public void setSelectedAutPag(AutorizacionPago selectedAutPag) {
        this.selectedAutPag = selectedAutPag;
    }

    /**
     * @return the arrAutorizacionesPago
     */
    public AutorizacionPago[] getAutorizacionesPago() {
        return arrAutorizacionesPago;
    }

    /**
     * @param arrAutorizacionesPago the arrAutorizacionesPago to set
     */
    public void setAutorizacionesPago(AutorizacionPago[] arrAutorizacionesPago) {
        this.arrAutorizacionesPago = arrAutorizacionesPago;
    }
    
}
