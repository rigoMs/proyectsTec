package mx.gob.hrrb.jbs.caja;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.caja.Caja;
import mx.gob.hrrb.modelo.caja.ComprobanteExencion;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.caja.Recibo;
import mx.gob.hrrb.modelo.caja.ReciboSeguroPopular;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Perfil;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oConHistTurno")
@RequestScoped
public class ConsultarHistorialTurnoJB {
    private Parametrizacion oRec;
    private Parametrizacion arrRecibo[];
    private Recibo arrRecibosEmitidos[];
    private CuotaRecuperacion oRecCR;
    private ReciboSeguroPopular oRecSP;
    private ComprobanteExencion oCompEx;
    private FacesContext facesContext;
    private Caja oCajaActual;
    private Caja oCaja;
    private Caja arrCajas[];
    private Firmado oFirm;
    private HttpServletRequest req;
    private boolean bVisDlg;
    private boolean bDisDatos;
    private String sActivaTabla;

    public ConsultarHistorialTurnoJB() throws Exception {
        oRec=new Parametrizacion();
        sActivaTabla="display: none;";
        oCaja=new Caja();
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                oFirm=(Firmado) req.getSession().getAttribute("oFirm");
            }
        if(req.getSession().getAttribute("oCaja") == null){
            bVisDlg=oFirm.getUsu().getPerfil().get(0).getClave().trim().equals(Perfil.CLAVE_CAJERO);
        }
        bDisDatos = oFirm.getUsu().getPerfil().get(0).getClave().trim().equals(Perfil.CLAVE_JEFE_CAJA_GRAL);
        
        arrRecibo=oRec.buscarTabla(Parametrizacion.TABLA_RECIBO);
        facesContext = FacesContext.getCurrentInstance();
        oCajaActual=new Caja();
        arrCajas=oCajaActual.buscarCajasAuxiliares();
    }
    
    public void preGuardaCajaSession(ActionEvent ae)throws Exception{
        guardaCajaSession();
    }
    
    public void guardaCajaSession(){
        oCajaActual.equalsCaja(arrCajas);
        try{
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("oCaja", oCajaActual);
            hideDialog();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void preBusqueda(ActionEvent ae) throws Exception{
        if(bDisDatos){
            buscarRecibos(oCaja.getIdCaja());
        }
        else{
            if (req.getSession().getAttribute("oCaja") != null) {
                oCaja=(Caja) req.getSession().getAttribute("oCaja");
                buscarRecibos(oCaja.getIdCaja());
            }
        }
        oCaja.equalsCaja(arrCajas);
        oRec.equalsParam(arrRecibo);
        sActivaTabla="";
    }
    
    public void buscarRecibos(int nIdCaja) throws Exception{
        if(oRec.getTipoParametro().trim().equals(Parametrizacion.RECIBO_CUOTAS))
            {
                oRecCR=new CuotaRecuperacion();
                oRecCR.setCajaEmite(new Caja());
                oRecCR.getCajaEmite().setIdCaja(nIdCaja);
                arrRecibosEmitidos=oRecCR.buscarHistorialTurno();
            }
        if(oRec.getTipoParametro().trim().equals(Parametrizacion.RECIBO_SEGPOP))
            {
                oRecSP=new ReciboSeguroPopular();
                oRecSP.setCajaEmite(new Caja());
                oRecSP.getCajaEmite().setIdCaja(nIdCaja);
                arrRecibosEmitidos=oRecSP.buscarHistorialTurno();
            }
        if(oRec.getTipoParametro().trim().equals(Parametrizacion.RECIBO_EXENTO))
            {
                oCompEx=new ComprobanteExencion();
                oCompEx.setCajaEmite(new Caja());
                oCompEx.getCajaEmite().setIdCaja(nIdCaja);
                arrRecibosEmitidos=oCompEx.buscarHistorialTurno();
            }
    }
    
    public void hideDialog(){
        RequestContext.getCurrentInstance().execute("PF('dlgCaja').hide()");
    }
    
    public Recibo[] getRecibos() {
        return arrRecibosEmitidos;
    }

    public void setRecibos(Recibo[] arrRecibos) {
        this.arrRecibosEmitidos = arrRecibos;
    }

    public Parametrizacion[] getTiposRecibo() {
        return arrRecibo;
    }

    public Caja[] getCajas() {
        return arrCajas;
    }

    public void setCajas(Caja[] arrCajas) {
        this.arrCajas = arrCajas;
    }
    
    public boolean isVisDlg() {
        return bVisDlg;
    }
    
    public boolean isDisDatos() {
        return bDisDatos;
    }

    public Caja getCaja() {
        return oCaja;
    }

    public void setCaja(Caja oCaja) {
        this.oCaja = oCaja;
    }

    public Caja getCajaActual() {
        return oCajaActual;
    }

    public void setCajaActual(Caja oCajaActual) {
        this.oCajaActual = oCajaActual;
    }

    public Parametrizacion getRec() {
        return oRec;
    }
    
    public String getActivaTabla() {
        return sActivaTabla;
    }

}