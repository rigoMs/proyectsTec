package mx.gob.hrrb.jbs.caja;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConsultarReint")
@RequestScoped
public class ConsultarReintegrosJB {
    private Date dFechaI;
    private Date dFechaF;
    private String sActivaTabla;
    private CuotaRecuperacion arrReintegrosReal[];
    private CuotaRecuperacion oCuotaRecup;
    
    
    public ConsultarReintegrosJB() {
        sActivaTabla="display: none;";
        oCuotaRecup=new CuotaRecuperacion();
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
            buscaReintegros();
            sActivaTabla="";
    }
    
    public void buscaReintegros() throws Exception{
        arrReintegrosReal=oCuotaRecup.buscarReintegrosRealizados(dFechaI, dFechaF);
    }

    public Date getFechaI() {
        return dFechaI;
    }

    public void setFechaI(Date dFechaI) {
        this.dFechaI = dFechaI;
    }

    public Date getFechaF() {
        return dFechaF;
    }

    public void setFechaF(Date dFechaF) {
        this.dFechaF = dFechaF;
    }
    
    public String getActivaTabla() {
        return sActivaTabla;
    }   

    public CuotaRecuperacion[] getReintegrosReal() {
        return arrReintegrosReal;
    }

    public void setReintegrosReal(CuotaRecuperacion arrReintegrosReal[]) {
        this.arrReintegrosReal = arrReintegrosReal;
    }
}
