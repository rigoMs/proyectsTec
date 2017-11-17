package mx.gob.hrrb.jbs.caja;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.caja.Caja;
import mx.gob.hrrb.modelo.caja.FoliosCaja;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Perfil;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConsultarExistRec")
@RequestScoped
public class ConsultarExistRecJB {
    private String sActivaTabla;
    private Parametrizacion oParam;
    private Caja arrCaja[];
    private Parametrizacion arrRecibo[];
    private Caja oCaja;
    private FoliosCaja oFoliosC;
    private FoliosCaja arrFoliosC[];
    private int nTotalRec;
    private boolean bRenDatos;
    Firmado oFirm;
    HttpServletRequest req;

    
    public ConsultarExistRecJB() throws Exception {
        oParam=new Parametrizacion();
        sActivaTabla="display: none;";
	req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                oFirm=(Firmado) req.getSession().getAttribute("oFirm");
            }
        oFoliosC=new FoliosCaja();
        oFoliosC.setCaja(new Caja());
        oFoliosC.setTipoRecibo(new Parametrizacion());
        oCaja=new Caja();
        arrCaja=oCaja.buscarCajasAuxiliares();
        arrRecibo=oParam.buscarTabla(Parametrizacion.TABLA_RECIBO);
        bRenDatos = oFirm.getUsu().getPerfil().get(0).getClave().trim().equals(Perfil.CLAVE_JEFE_CAJA_GRAL);
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscaExistenciaRecibos();
        sActivaTabla="";
    }
    
    public void buscaExistenciaRecibos() throws Exception{
        if(!bRenDatos){
            if (req.getSession().getAttribute("oCaja") != null) {
                oCaja=(Caja) req.getSession().getAttribute("oCaja");
                oFoliosC.setCaja(oCaja);
            }
        }
        else{
            oFoliosC.getCaja().equalsCaja(arrCaja);
        }
        
        nTotalRec=0;
        arrFoliosC=oFoliosC.buscarExistenciaRecibos();
        oFoliosC.getTipoRecibo().equalsParam(arrRecibo);
        for(FoliosCaja fc:arrFoliosC){
               nTotalRec+=(fc.getFolioFinal()-fc.getFolioInicial())+1;
            }
    }

    public FoliosCaja[] getFoliosCaja() {
        return arrFoliosC;
    }

    public int getTotalRec() {
        return nTotalRec;
    }

    public void setTotalRec(int nTotalRec) {
        this.nTotalRec = nTotalRec;
    }
    
    public Caja[] getTiposCaja() {
        return arrCaja;
    }

    public Parametrizacion[] getTiposRecibo() {
        return arrRecibo;
    }

    public FoliosCaja getFoliosC() {
        return oFoliosC;
    }

    public void setFoliosC(FoliosCaja oFoliosC) {
        this.oFoliosC = oFoliosC;
    }

    public boolean isRenDatos() {
        return bRenDatos;
    }

    public String getActivaTabla() {
        return sActivaTabla;
    }

    public void setActivaTabla(String sActivaTabla) {
        this.sActivaTabla = sActivaTabla;
    }
}