package mx.gob.hrrb.modelo.caja.reportes;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Daniel
 */
public class InformeServMedSegPop {
    private String sCaja;
    private BigDecimal nTotal;
    private Date dFecha;
    private int arrRecCancelados[];

    public String getCaja() {
        return sCaja;
    }

    public void setCaja(String sCaja) {
        this.sCaja = sCaja;
    }

    public BigDecimal getTotal() {
        return nTotal;
    }

    public void setTotal(BigDecimal nTotal) {
        this.nTotal = nTotal;
    }

    public Date getFecha() {
        return dFecha;
    }

    public void setFecha(Date fFecha) {
        this.dFecha = fFecha;
    }

    public String getRecCancelados() {
        String sRecibos="";
        if(arrRecCancelados.length==0){
            sRecibos="NINGUNO";
        }
        else{
            if(arrRecCancelados.length==1){
                sRecibos=arrRecCancelados[0]+"";
            }
            else{
                for(int i=0;i<arrRecCancelados.length;i++){
                    sRecibos=sRecibos+arrRecCancelados;
                }
            }
        }
        return sRecibos;
    }

}
