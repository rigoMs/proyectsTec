package mx.gob.hrrb.jbs.caja;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.AutorizacionPago;
import mx.gob.hrrb.modelo.caja.Recibo;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConRelAnt")
@RequestScoped
public class ConsultarRelAnticiposJB {
    private AutorizacionPago arrAnticipos[];
    private AutorizacionPago oAut;
    private Date dFechaI;
    private Date dFechaF;
    private int nTipoAnt;
    private String sActivaTabla;
    
    public ConsultarRelAnticiposJB() {
        oAut=new AutorizacionPago();
        sActivaTabla="display: none;";
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
            buscaAnticipos();
            sActivaTabla="";
    }
    
    public void buscaAnticipos() throws Exception{
        arrAnticipos=oAut.buscarAnticipos(nTipoAnt, dFechaI, dFechaF);
    }
    
    public Recibo getRecibo(Recibo arrRec[]){
        return arrRec[0];
    }
    
    public ServicioRealizado getServicioRealziado(ServicioRealizado arrServReal[]){
        return arrServReal[0];
    }
    
    public AutorizacionPago[] getAnticipos() {
        return arrAnticipos;
    }

    public void setAnticipos(AutorizacionPago[] arrAutAnticipos) {
        this.arrAnticipos = arrAutAnticipos;
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

    public int getTipoAnt() {
        return nTipoAnt;
    }

    public void setTipoAnt(int nTipoAnt) {
        this.nTipoAnt = nTipoAnt;
    }
    
    public String getActivaTabla() {
        return sActivaTabla;
    }
    
}
