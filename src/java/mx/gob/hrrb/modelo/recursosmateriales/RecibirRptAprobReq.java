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
public class RecibirRptAprobReq implements Serializable {
    
    private Date dFechaSolic;
    private RequisicionInterna oRequisicionInt;
    private RequisicionBasica oRequisicBasic;
    private CotizacionDNAAlmacen oCotizacionDNAA;
    private CotizacionDNAFarmacia oCotizacDNAF;

    
    
    
    
    public Date getdFechaSolic() {
        return dFechaSolic;
    }
    public void setdFechaSolic(Date dFechaSolic) {
        this.dFechaSolic = dFechaSolic;
    }
    public RequisicionInterna getoRequisicionInt() {
        return oRequisicionInt;
    }
    public void setoRequisicionInt(RequisicionInterna oRequisicionInt) {
        this.oRequisicionInt = oRequisicionInt;
    }
    public RequisicionBasica getoRequisicBasic() {
        return oRequisicBasic;
    }
    public void setoRequisicBasic(RequisicionBasica oRequisicBasic) {
        this.oRequisicBasic = oRequisicBasic;
    }
    public CotizacionDNAAlmacen getoCotizacionDNAA() {
        return oCotizacionDNAA;
    }
    public void setoCotizacionDNAA(CotizacionDNAAlmacen oCotizacionDNAA) {
        this.oCotizacionDNAA = oCotizacionDNAA;
    }
    public CotizacionDNAFarmacia getoCotizacDNAF() {
        return oCotizacDNAF;
    }
    public void setoCotizacDNAF(CotizacionDNAFarmacia oCotizacDNAF) {
        this.oCotizacDNAF = oCotizacDNAF;
    }
    
}
