package mx.gob.hrrb.jbs.caja;

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.Caja;
import mx.gob.hrrb.modelo.caja.FoliosCaja;
import mx.gob.hrrb.modelo.caja.FoliosDisponibles;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oEntregarRec")
@ViewScoped
public class EntregarRecCajaAuxJB {
    private String sActivaTabla;
    private Parametrizacion oParam;
    private PersonalHospitalario oPH;
    private Parametrizacion arrCaja[];
    private Parametrizacion arrRecibo[];
    private PersonalHospitalario[] arrCajeros;
    private FoliosCaja oFoliosCaja;
    private int nCantEntrega;
    private FoliosDisponibles oFD1;
    private FoliosDisponibles oFD2;
    private FoliosDisponibles[] arrFolios1;
    private FoliosDisponibles[] arrFolios2;
    private int nTotalRecCaja1;
    private int nTotalRecCaja2;
    private int nTotalRecibos;
    private boolean bDisDatos=true;
    private ArrayList<String> vMsj;

    public EntregarRecCajaAuxJB() throws Exception {
        oParam=new Parametrizacion();
        sActivaTabla="display: none;";
        oPH=new PersonalHospitalario();
        oFoliosCaja=new FoliosCaja();
        oFoliosCaja.setCaja(new Caja());
        oFoliosCaja.getCaja().setTipoCaja(new Parametrizacion());
        oFoliosCaja.setRecibe(new PersonalHospitalario());
        oFoliosCaja.setTipoRecibo(new Parametrizacion());
        oFoliosCaja.getTipoRecibo().setClaveParametro(Parametrizacion.TABLA_RECIBO);
        arrCaja=oParam.buscarCajasAuxiliares();
        arrRecibo=oParam.buscarTabla(Parametrizacion.TABLA_RECIBO);
        oFD1=new FoliosDisponibles();
        oFD1.setTipoCaja(new Parametrizacion());
        oFD1.setTipoRecibo(new Parametrizacion());
        arrCajeros=oPH.buscarPorPuesto(PersonalHospitalario.PUESTO_CAJERO);
    }
    
    public void preBusqueda(ActionEvent ae)throws Exception{
        buscarFolios();
    }
    
    public void buscarFolios() throws Exception{
        nTotalRecibos=0;
        nTotalRecCaja1=0;
        nTotalRecCaja2=0;
        arrFolios1=oFD1.buscarFoliosDisponiblesCajaRecibo();
        oFD2=oFD1;
        
        
        oFD1.getTipoCaja().equalsParam(arrCaja);
        oFD1.getTipoRecibo().equalsParam(arrRecibo);        
        
        if(oFD1.getTipoCaja().getTipoParametro().equals(Parametrizacion.CAJA_AUX_CE)){
            oFD2.getTipoCaja().setTipoParametro(Parametrizacion.CAJA_AUX_URG);
        }
        else{
             oFD2.getTipoCaja().setTipoParametro(Parametrizacion.CAJA_AUX_CE);
        }
        arrFolios2=oFD2.buscarFoliosDisponiblesCajaRecibo();
        
        if(arrFolios1!=null){
            for(FoliosDisponibles f:arrFolios1){
               nTotalRecCaja1+=(f.getFolioFinal()-f.getFolioInicial())+1;
            }
        }
        
        if(arrFolios2!=null){
            for(FoliosDisponibles f:arrFolios2){
               nTotalRecCaja2+=(f.getFolioFinal()-f.getFolioInicial())+1;
            }
        }
        nTotalRecibos=nTotalRecCaja1+nTotalRecCaja2;
        if(nTotalRecibos>0){
            bDisDatos=false;
            sActivaTabla=""; 
        }
        else{
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se cuenta con folios disponibles."));
            context.getExternalContext().getFlash().setKeepMessages(true);
            bDisDatos=true;
            sActivaTabla="display: none;";
        }
    }

    public void guarda() throws Exception{
        
        oFoliosCaja.getCaja().getTipoCaja().setTipoParametro(oFD1.getTipoCaja().getTipoParametro());
        oFoliosCaja.getCaja().buscaIdCaja();
        oFoliosCaja.setTipoRecibo(oFD1.getTipoRecibo());
        oFoliosCaja.getTipoRecibo().setClaveParametro(Parametrizacion.TABLA_RECIBO);
        vMsj=oFD1.entregaFoliosDisponiblesFoliosCaja(arrFolios1, arrFolios2,oFoliosCaja,nCantEntrega);
        
        hideDialog();
        showDialog();
        buscarFolios();
    }
    
    public void hideDialog(){
        RequestContext.getCurrentInstance().execute("PF('dlgEntrega').hide()");
    }
    
    public void showDialog(){
        RequestContext.getCurrentInstance().execute("PF('dlgInfo').show()");
    }
    
    public Parametrizacion[] getTiposCaja() {
        return arrCaja;
    }

    public Parametrizacion[] getTiposRecibo() {
        return arrRecibo;
    }

    public FoliosDisponibles getFD() {
        return oFD1;
    }

    public void setFD(FoliosDisponibles oFD) {
        this.oFD1 = oFD;
    }

    public FoliosDisponibles[] getFolios() {
        return arrFolios1;
    }

    public void setFolios(FoliosDisponibles[] arrFolios) {
        this.arrFolios1 = arrFolios;
    }
    
    public int getTotal(){
        return nTotalRecCaja1;
    }

    public PersonalHospitalario[] getCajeros() {
        return arrCajeros;
    }

    public void setCajeros(PersonalHospitalario[] Cajeros) {
        this.arrCajeros = Cajeros;
    }

    public boolean isDisDatos() {
        return bDisDatos;
    }   

    public int getTotalRecibos() {
        return nTotalRecibos;
    }

    public FoliosCaja getFoliosCaja() {
        return oFoliosCaja;
    }

    public int getCantEntrega() {
        return nCantEntrega;
    }

    public void setCantEntrega(int nCantEntrega) {
        this.nCantEntrega = nCantEntrega;
    }

    public String getActivaTabla() {
        return sActivaTabla;
    }

    public void setActivaTabla(String sActivaTabla) {
        this.sActivaTabla = sActivaTabla;
    }

    public ArrayList<String> getMsj() {
        return vMsj;
    }
}
