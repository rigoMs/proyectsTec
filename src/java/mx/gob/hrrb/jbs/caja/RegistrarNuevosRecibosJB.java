package mx.gob.hrrb.jbs.caja;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.caja.FoliosCaja;
import mx.gob.hrrb.modelo.caja.FoliosDisponibles;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oRegRecibos")
@ViewScoped
public class RegistrarNuevosRecibosJB implements Serializable {
    private Parametrizacion oParam;
    private Parametrizacion arrRecibo[];
    private FoliosDisponibles arrFolios[];
    private FoliosDisponibles oFoliosUrg;
    private FoliosDisponibles oFoliosCE;
    private Parametrizacion oRecibo;
    private FoliosDisponibles oFD;
    private FoliosCaja oFoliosCaja;
    private FoliosCaja arrFoliosCaja[];
    private int nFolioi;
    private int nFoliof;
    private int nCantRecCE;
    private int nCantRecUrg;
    private int nTotalRecibos;
    private boolean bDisDatos=true;
    
    public RegistrarNuevosRecibosJB() throws Exception {
        oRecibo=new Parametrizacion();
        oParam=new Parametrizacion();
        arrRecibo=oParam.buscarTabla(Parametrizacion.TABLA_RECIBO);
    }
    
    public void preGuarda(ActionEvent ae)throws Exception{
        guarda();
    }
    
    public void guarda() throws Exception{
        int nRet=0;
        oFoliosUrg=new FoliosDisponibles();
        oFoliosCE=new FoliosDisponibles();
        
                
        oFoliosUrg.setTipoCaja(new Parametrizacion());
        oFoliosUrg.setTipoRecibo(oRecibo);
        oFoliosUrg.getTipoCaja().setClaveParametro(Parametrizacion.TABLA_CAJA);
        oFoliosUrg.getTipoCaja().setTipoParametro(Parametrizacion.CAJA_AUX_URG);
        oFoliosUrg.setFolioInicial(nFolioi);
        oFoliosUrg.setFolioFinal((nFolioi+nCantRecUrg)-1);
        nRet=oFoliosUrg.insertar();
        
        oFoliosCE.setTipoCaja(new Parametrizacion());
        oFoliosCE.setTipoRecibo(oRecibo);
        oFoliosCE.getTipoCaja().setClaveParametro(Parametrizacion.TABLA_CAJA);
        oFoliosCE.getTipoCaja().setTipoParametro(Parametrizacion.CAJA_AUX_CE);
        oFoliosCE.setFolioInicial(nFolioi+nCantRecUrg);
        oFoliosCE.setFolioFinal(nFoliof);
        nRet+=oFoliosCE.insertar();
        
        if(nRet==2){
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"","Se ha insertado el registro exitosamente."));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        else{
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error en la base de datos.","Contacte al administrador."));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        
        hideDialog();
        inicializar();
    }
    
    public void preDialogo(ActionEvent ae)throws Exception{
        oFoliosCaja=new FoliosCaja();
        oFoliosCaja.setTipoRecibo(oRecibo);
        oFD=new FoliosDisponibles();
        oFD.setTipoRecibo(oRecibo);
        oFD.setTipoCaja(new Parametrizacion());
        arrFolios=oFD.buscarFoliosDisponiblesCajaRecibo();
        arrFoliosCaja=oFoliosCaja.buscaFoliosCajaPorTipoRecibo();
        boolean bFlag=false;
        for(FoliosDisponibles fd:arrFolios){
            if((nFolioi>=fd.getFolioInicial() && nFolioi<=fd.getFolioFinal())||
                (nFoliof>=fd.getFolioInicial() && nFoliof<=fd.getFolioFinal())){
                bFlag=true;
            }
        }
        for(FoliosCaja fc:arrFoliosCaja){
            if((nFolioi>=fc.getFolioInicial() && nFolioi<=fc.getFolioFinal())||
                (nFoliof>=fc.getFolioInicial() && nFoliof<=fc.getFolioFinal())){
                bFlag=true;
            }
        }
        if(bFlag){
            FacesContext context= FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Los folios ya fueron registrados, verifique que los números sean correctos."));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        else{
            oRecibo.equalsParam(arrRecibo);
            showDialog();
        }  
    }
    
    public void inicializar(){
        oRecibo=new Parametrizacion();
        nFolioi=0;
        nFoliof=0;
        nCantRecCE=0;
        nCantRecUrg=0;
        nTotalRecibos=0;
        bDisDatos=true;
    }
    
    public void showDialog(){
        RequestContext.getCurrentInstance().execute("PF('dlgC').show()");
    }
    
    public void hideDialog(){
        RequestContext.getCurrentInstance().execute("PF('dlgC').hide()");
    }
    
    public void habilitaCampo(){
        nCantRecCE=0;
        nCantRecUrg=0;
        if(nFoliof!=0 && nFolioi!=0 && nFoliof>nFolioi){
            bDisDatos=false;
            nTotalRecibos=(nFoliof-nFolioi)+1;
        }
        else{
            nTotalRecibos=0;
            bDisDatos=true;
        }
    }
    
    public void calculaCantRec(){
        nCantRecCE=nTotalRecibos-nCantRecUrg;
    }
    
    public int getFolioi() {
        return nFolioi;
    }

    public void setFolioi(int nFolioi) {
        this.nFolioi = nFolioi;
    }

    public int getFoliof() {
        return nFoliof;
    }

    public void setFoliof(int nFoliof) {
        this.nFoliof = nFoliof;
    }

    public int getCantRecCE() {
        return nCantRecCE;
    }

    public void setCantRecCE(int nCantRecCE) {
        this.nCantRecCE = nCantRecCE;
    }

    public int getCantRecUrg() {
        return nCantRecUrg;
    }

    public void setCantRecUrg(int nCantRecUrg) {
        this.nCantRecUrg = nCantRecUrg;
    }
    
    public Parametrizacion[] getTiposRecibo() {
        return arrRecibo;
    }
    
    public int getTotalRecibos(){
        return nTotalRecibos;
    }
    
    public int getFolioFinalMin(){
        return nFolioi+1;
    }
    
    public int getMaxCantRecCajaUrg(){
        return ((nFoliof-nFolioi));
    }
    
    public String getRangoRecibosUrg(){
        return nFolioi+" a "+((nFolioi+nCantRecUrg)-1);
    }
    
    public String getRangoRecibosCE(){
        return (nFolioi+nCantRecUrg)+" a "+nFoliof;
    }


    public Parametrizacion getRecibo() {
        return oRecibo;
    }

    public boolean isDisDatos() {
        return bDisDatos;
    }

}