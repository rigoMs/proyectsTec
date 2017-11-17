/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 *
 * @author DavidH
 */
public class Memorandum implements Serializable {
    
    private PersonalHospitalario oPersonalFinanciero;
    private int nNoDePedido;
    private PedidoOficial oPedidoOficial;
    private String sSituacDevoluc;
    private PersonalHospitalario oPersonalMateriales;

    
    
    
    
    
    public PersonalHospitalario getoPersonalFinanciero() {
        return oPersonalFinanciero;
    }
    public void setoPersonalFinanciero(PersonalHospitalario oPersonalFinanciero) {
        this.oPersonalFinanciero = oPersonalFinanciero;
    }
    public int getnNoDePedido() {
        return nNoDePedido;
    }
    public void setnNoDePedido(int nNoDePedido) {
        this.nNoDePedido = nNoDePedido;
    }
    public PedidoOficial getoPedidoOficial() {
        return oPedidoOficial;
    }
    public void setoPedidoOficial(PedidoOficial oPedidoOficial) {
        this.oPedidoOficial = oPedidoOficial;
    }
    public String getsSituacDevoluc() {
        return sSituacDevoluc;
    }
    public void setsSituacDevoluc(String sSituacDevoluc) {
        this.sSituacDevoluc = sSituacDevoluc;
    }
    public PersonalHospitalario getoPersonalMateriales() {
        return oPersonalMateriales;
    }
    public void setoPersonalMateriales(PersonalHospitalario oPersonalMateriales) {
        this.oPersonalMateriales = oPersonalMateriales;
    }
}
