/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DavidH
 */
public class RequisicionAceptada implements Serializable {
    
    private Date dFechaSolic;
    private RequisicionInterna oRequisicionInterna;
    private RequisicionBasica oRequisicionBasica;
    private CotizacionDNAAlmacen oCotizacionDNAA;
    private CotizacionDNAFarmacia oCotizacionDNAF;

    
    
    
    
    public Date getdFechaSolic() {
        return dFechaSolic;
    }
    public void setdFechaSolic(Date dFechaSolic) {
        this.dFechaSolic = dFechaSolic;
    }
    public RequisicionInterna getoRequisicionInterna() {
        return oRequisicionInterna;
    }
    public void setoRequisicionInterna(RequisicionInterna oRequisicionInterna) {
        this.oRequisicionInterna = oRequisicionInterna;
    }
    public RequisicionBasica getoRequisicionBasica() {
        return oRequisicionBasica;
    }
    public void setoRequisicionBasica(RequisicionBasica oRequisicionBasica) {
        this.oRequisicionBasica = oRequisicionBasica;
    }
    public CotizacionDNAAlmacen getoCotizacionDNAA() {
        return oCotizacionDNAA;
    }
    public void setoCotizacionDNAA(CotizacionDNAAlmacen oCotizacionDNAA) {
        this.oCotizacionDNAA = oCotizacionDNAA;
    }
    public CotizacionDNAFarmacia getoCotizacionDNAF() {
        return oCotizacionDNAF;
    }
    public void setoCotizacionDNAF(CotizacionDNAFarmacia oCotizacionDNAF) {
        this.oCotizacionDNAF = oCotizacionDNAF;
    }
}
