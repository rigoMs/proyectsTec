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
public class ArchivarPedidos implements Serializable {
    
    private PedidoOficial oPedidos;
    private int nNoDePedido;
    private String sPedidoOficDig;
    private String sCotizacionDig;
    private String sCCSRDig;
    private String sCartaGarantiaDig;
    private String sSellosBienDig;
    private String sMinuta;
    private AccesoDatos oAD;

    
    
    
    
    public PedidoOficial getPedidos() {
        return oPedidos;
    }
    public void setPedidos(PedidoOficial oPedidos) {
        this.oPedidos = oPedidos;
    }
    public int getNoDePedido() {
        return nNoDePedido;
    }
    public void setNoDePedido(int nNoDePedido) {
        this.nNoDePedido = nNoDePedido;
    }
    public String getPedidoOficDig() {
        return sPedidoOficDig;
    }
    public void setPedidoOficDig(String sPedidoOficDig) {
        this.sPedidoOficDig = sPedidoOficDig;
    }
    public String getCotizacionDig() {
        return sCotizacionDig;
    }
    public void setCotizacionDig(String sCotizacionDig) {
        this.sCotizacionDig = sCotizacionDig;
    }
    public String getCCSRDig() {
        return sCCSRDig;
    }
    public void setCCSRDig(String sCCSRDig) {
        this.sCCSRDig = sCCSRDig;
    }
    public String getCartaGarantiaDig() {
        return sCartaGarantiaDig;
    }
    public void setCartaGarantiaDig(String sCartaGarantiaDig) {
        this.sCartaGarantiaDig = sCartaGarantiaDig;
    }
    public String getSellosBienDig() {
        return sSellosBienDig;
    }
    public void setSellosBienDig(String sSellosBienDig) {
        this.sSellosBienDig = sSellosBienDig;
    }
    public String getMinuta() {
        return sMinuta;
    }
    public void setMinuta(String sMinuta) {
        this.sMinuta = sMinuta;
    }
    public AccesoDatos getAD() {
        return oAD;
    }
    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }
}
