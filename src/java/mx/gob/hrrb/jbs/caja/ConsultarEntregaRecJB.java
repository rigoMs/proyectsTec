package mx.gob.hrrb.jbs.caja;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.FoliosCaja;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConsultarEntRec")
@RequestScoped
public class ConsultarEntregaRecJB {
    private String sActivaTabla;
    private Parametrizacion oRec;
    private Parametrizacion arrRecibo[];
    private Date dFechaI;
    private Date dFechaF;
    private FoliosCaja arrFoliosCaja[];
    private FoliosCaja oFoliosCaja;
    private String sTipoRec;

    public ConsultarEntregaRecJB() throws Exception {
        oRec=new Parametrizacion();
        sActivaTabla="display: none;";
        oFoliosCaja=new FoliosCaja();
        arrRecibo=oRec.buscarTabla(Parametrizacion.TABLA_RECIBO);
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscar();
        oRec.equalsParam(arrRecibo);
        sActivaTabla="";
    }
    
    
    public void buscar() throws Exception{
        arrFoliosCaja=oFoliosCaja.buscarEntregaRecibos(dFechaI, dFechaF, oRec.getTipoParametro());
    }

    public Date getFechai() {
        return dFechaI;
    }

    public void setFechai(Date dFechai) {
        this.dFechaI = dFechai;
    }

    public Date getFechaf() {
        return dFechaF;
    }

    public void setFechaf(Date dFechaf) {
        this.dFechaF = dFechaf;
    }

    public FoliosCaja[] getFoliosCaja() {
        return arrFoliosCaja;
    }

    public void setFoliosCaja(FoliosCaja[] arrFoliosCaja) {
        this.arrFoliosCaja = arrFoliosCaja;
    }
    
    public Parametrizacion[] getTiposRecibo() {
        return arrRecibo;
    }

    public String getTipoRec() {
        return sTipoRec;
    }

    public void setTipoRec(String sTipoRec) {
        this.sTipoRec = sTipoRec;
    }

    public String getActivaTabla() {
        return sActivaTabla;
    }

    public void setActivaTabla(String sActivaTabla) {
        this.sActivaTabla = sActivaTabla;
    }

    public Parametrizacion getRec() {
        return oRec;
    }
}
