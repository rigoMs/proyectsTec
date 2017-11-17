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
import jdk.nashorn.internal.runtime.Context;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author JDanny
 */
@ManagedBean(name = "oExpAper")
@ViewScoped
public class GenerarExpParaAperturarJB {
    private String sUsuarioFirmado;
    private Expediente oExp;
    private ArrayList<Expediente> arrExp;
    
    public GenerarExpParaAperturarJB(){
        oExp=new Expediente();
        arrExp=new ArrayList<Expediente>();
        oExp.getLugarApertura().setValor("");
    }
    
    public void agregaElemento(){
        try{
            if(oExp.getNumero()==0 ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","No se acepta 0"));
            }else{
                if(buscaRepetido(oExp.getNumero())){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","El expediente ya se encuentra agregado"));
                    oExp.setNumero(0);
                }else{
                    if(oExp.getLugarApertura().getValor().equals("")){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Debe seleccionar lugar de apertura"));
                    }
                    else{
                        arrExp.add(oExp);
                        oExp = new Expediente();
                        oExp.setNumero(0);
                        oExp.getLugarApertura().setValor("");
                        //RequestContext.getCurrentInstance().update("lugarAper");
                    }
                }
            }
        }catch(Exception e){
            e.getMessage();
            Logger.getLogger(GenerarExpParaAperturarJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public boolean buscaRepetido(int num){
        boolean bandera = false;
        for(Expediente i: arrExp){
            if(i.getNumero()==num){
                bandera = true;
                break;
            }
        }
        return bandera;
    }
    
    public Expediente getExp(){
        oExp.setInserta(true);
        return oExp;
    }
    
    public void setExp(Expediente oExp){
        this.oExp=oExp;
    }
    
    public ArrayList<Expediente> getArrExp(){
        return arrExp;
    }
    
    public void buscaLugarApertura(){
        try{
            oExp.getLugarApertura().setValor(oExp.getLugarApertura().buscaValorParametrizado(oExp.getLugarApertura().getTipoParametro()));
            
            
        }catch(Exception e){
            e.getMessage();
            Logger.getLogger(GenerarExpParaAperturarJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void registrar(){
        try{
            if((oExp.insertaVariosExp(arrExp))>0){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Inserta Expedientes","Guardados con éxitos"));
                //FacesContext.getCurrentInstance().getExternalContext().redirect("generarExpParaAperturar.xhtml");
                RequestContext.getCurrentInstance().execute("PF('dlgImpresion').show();");
                arrExp=new ArrayList<Expediente>();
                
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Inserción fallida"));
            }
        }catch(Exception e){
            e.getMessage();
            Logger.getLogger(GenerarExpParaAperturarJB.class.getName()).log(Level.SEVERE, null, e);
        }
    }            
    
    public Date getFecha(){
        return new Date();
    }
    
    public List<Parametrizacion> getListaLugarAperturaExp(){
        List<Parametrizacion> lLista = null;
       try {
           Parametrizacion[] arrLugarApertura = new Parametrizacion[2];
           arrLugarApertura[0]= new Parametrizacion();
           arrLugarApertura[0].setTipoParametro("TAJ");
           arrLugarApertura[0].setClaveParametro("2 ");
           arrLugarApertura[0].setValor("URGENCIAS");
           arrLugarApertura[1]= new Parametrizacion();
           arrLugarApertura[1].setTipoParametro("TAJ");
           arrLugarApertura[1].setClaveParametro("3 ");
           arrLugarApertura[1].setValor("CAPASITS");
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(arrLugarApertura));
       } catch (Exception ex) {
           ex.getMessage();
           Logger.getLogger(GenerarExpParaAperturarJB.class.getName()).log(Level.SEVERE, null, ex);
       }
       return lLista;
    }
}
