package mx.gob.hrrb.jbs.archivo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.archivo.PrestamoExp;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author JDanny
 */
@ManagedBean(name="oAdmPresExp")
@ViewScoped
public class AdmPrestamoExpJB {
    private Paciente oPac;
    private PrestamoExp oPresExp;
    FacesContext faceContext;
    private String sClaveBusca;
    private boolean bDisable;
    private AreaServicioHRRB oAreaSer;
    private List<PrestamoExp> lFilteredPresExp;
    private boolean bNom;
    private boolean bExp;
    
    public AdmPrestamoExpJB(){
        oAreaSer= new AreaServicioHRRB();
        oPresExp= new PrestamoExp();
        bDisable=true;
        cargaLista();
    }
    
    public void actualizaPac(long folioPaciente, int nnum){
            try{
                oPac=oPresExp.buscaPac(folioPaciente, nnum);
                oPresExp.getExp().setNumero(oPac.getExpediente().getNumero());
                if(oPac==null)
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"INFO","NO SE CARGARON LOS DATOS"));
            }catch(Exception ex){Logger.getLogger(PrestamoExp.class.getName()).log(Level.SEVERE,null,ex);}
    }

    public void insertarPrestamo(){
        try{
            if((oPresExp.insertarPrestamoExp())==0){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Prestamo Expediente"," Préstamo fallido"));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Prestamo de Expediente","Préstamo exitoso!"));
                RequestContext.getCurrentInstance().execute("PF('dlgModal').hide()");
                cargaLista();
            }
        }catch(Exception e){
            java.util.logging.Logger.getLogger(PrestamoExp.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void regDevolucion(long nfoliopres, int num){
        try{
            if((oPresExp.RegistraDevolucionExp(nfoliopres,num))==0){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Devolución Expediente"," Registro Fallido"));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Devolución Expediente","Registro Exitoso!"));
                lFilteredPresExp=null;
            }
        }catch(Exception e){
            Logger.getLogger(AdmPrestamoExpJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public List<AreaServicioHRRB> getListaPrestamoServicioExp(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList((new PrestamoExp().getSerUbicacion().buscaServiciohrrbPrestamoExp())));
       } catch (Exception ex) {
           ex.getMessage();
           Logger.getLogger(AdmPrestamoExpJB.class.getName()).log(Level.SEVERE, null, ex);
       }
       return lLista;
    }
    
    public List<PersonalHospitalario> getListaPersonalExpPres(){
        List<PersonalHospitalario> lLista = null;
        String sVal="";
        if(this.sClaveBusca==null) 
            sVal="4";
        else
            sVal=this.sClaveBusca;
       try {
           lLista = new ArrayList<PersonalHospitalario>(Arrays.asList((new PrestamoExp().getPersonalRecibe().buscaPersonalExpPres(sVal))));
       } catch (Exception ex) {
           ex.getMessage();
           Logger.getLogger(AdmPrestamoExpJB.class.getName()).log(Level.SEVERE, null, ex);
       }
       return lLista;
    }
    
    public List<PrestamoExp> getListaExpPrestados(){
        List<PrestamoExp> lLista= null;
        String sVal="";
        try{
            lLista= new ArrayList<PrestamoExp>(Arrays.asList((new PrestamoExp().buscaExpedientesPrestados())));
        }catch(Exception e){
            e.getMessage();
            Logger.getLogger(AdmPrestamoExpJB.class.getName()).log(Level.SEVERE, null, e);
        }
        return lLista;
    }
    
    public void cargaLista(){
        try{
            lFilteredPresExp=new ArrayList<PrestamoExp>(Arrays.asList((new PrestamoExp().buscaExpedientesPrestados())));
        }catch(Exception e){
            Logger.getLogger(AdmPrestamoExpJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    public void onPersonalChange(){
       if(!this.sClaveBusca.equals("")){
           bDisable=false;
       }else{
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","Seleccciona tipo de personal"));
       }
    }
    
    public Date getFecha(){
        return new Date();
    }
    
    public Paciente getPac(){
        return oPac;
    }

    /**
     * @return the oAreaSer
     */
    public AreaServicioHRRB getAreaSer() {
        return oAreaSer;
    }

    /**
     * @param oAreaSer the oAreaSer to set
     */
    public void setAreaSer(AreaServicioHRRB oAreaSer) {
        this.oAreaSer = oAreaSer;
    }

    /**
     * @return the oPresExp
     */
    public PrestamoExp getPresExp() {
        return oPresExp;
    }

    /**
     * @param oPresExp the oPresExp to set
     */
    public void setPresExp(PrestamoExp oPresExp) {
        this.oPresExp = oPresExp;
    }

    /**
     * @return the sClaveBusca
     */
    public String getClaveBusca() {
        return sClaveBusca;
    }

    /**
     * @param sClaveBusca the sClaveBusca to set
     */
    public void setClaveBusca(String sClaveBusca) {
            this.sClaveBusca = sClaveBusca;
    }

    public boolean getDisable(){
        return bDisable;
    }
    
    public List<PrestamoExp> getFilteredPresExp(){
        return lFilteredPresExp;
    }
    
    public void setFilteredPresExp(List<PrestamoExp> lFilteredPresExp){
        this.lFilteredPresExp=lFilteredPresExp;
    }
}