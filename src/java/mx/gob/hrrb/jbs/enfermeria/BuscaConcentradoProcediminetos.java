
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.enfermeria.CabeceraActividadesEnfermeria;
import mx.gob.hrrb.modelo.enfermeria.reporte.ConcentradoProcedimientos;

/**
 *
 * @author J2GOV
 */
@ManagedBean(name="oBConc")
@ViewScoped
public class BuscaConcentradoProcediminetos implements Serializable {
    
    private boolean dRendering;
    private String sMes;
    private String sAnho;
    private String sMaxAnho;
    private ConcentradoProcedimientos oCp;    
    private List<CabeceraActividadesEnfermeria> arrServicios;
    private ConcentradoProcedimientos[] arrProced=null; 

    public BuscaConcentradoProcediminetos() { 
        oCp= new ConcentradoProcedimientos();
        cargaDatos();
        determinaMesAnho();
        validaFecha();
    }
    
    private void cargaDatos(){
        try{
            arrServicios= new ArrayList<CabeceraActividadesEnfermeria>(Arrays.asList((new CabeceraActividadesEnfermeria()).buscaServiciosTodos()));            
        }catch(Exception e){
            e.printStackTrace();            
        }
    }
    
    public void buscaNombreServicio(){
        for(CabeceraActividadesEnfermeria oCb: arrServicios){
            if(oCb.getAreaServicio().getClave()==this.getCp().getServicio().getClave()){
                this.getCp().getServicio().setDescripcion(oCb.getAreaServicio().getDescripcion());
                break;
            }
        }
    }
    
    public void buscaProcedimientosEnfermeria(ActionEvent ev){
        if(this.getCp().getServicio().getClave()!=0){
            try {
                validaFecha();                 
                arrProced=oCp.buscarConcentradoProcediminetos();
                dRendering=true;
                if(arrProced!=null && arrProced.length>0){
                    dRendering=true;
                }
                buscaNombreServicio();
            } catch (Exception ex) {
                Logger.getLogger(BuscaConcentradoProcediminetos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void determinaMesAnho(){
        Date dF= new Date();
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        String v[]=f.format(dF).split("-");
        this.setMes(v[1]);
        this.setAnho(v[0]);
        sMaxAnho=v[0];
    }
    
    public void validaFecha(){   
        String sA="";
        String sM=""; 
        int nA= Integer.parseInt(this.getAnho());
        int nM=Integer.parseInt(this.getMes());        
        if(nM==12){
            sA=nA+1+"";
            sM="01";
        }else{
            sA=this.getAnho();
            sM=nM+1+"";
            if(sM.length()==1){
                sM="0"+sM;
            }
        }        
        String sFecha1=this.getAnho()+"-"+this.getMes()+"-"+"26";
        String sFecha2=sA+"-"+sM+"-"+"25";
        this.getCp().setFechaInicio(sFecha1);
        this.getCp().setFechaFin(sFecha2);
        this.getCp().setFechaInicio2("26/"+this.getMes()+"/"+this.getAnho());
        this.getCp().setFechaFin2("25/"+this.getMes()+"/"+this.getAnho());
    }    
    
    public boolean getRendering() {
        return dRendering;
    }

    public String getMes() {
        return sMes;
    }

    public void setMes(String sMes) {
        this.sMes = sMes;
    }

    public String getAnho() {
        return sAnho;
    }

    public void setAnho(String sAnho) {
        this.sAnho = sAnho;
    }

    public String getMaxAnho() {
        return sMaxAnho;
    }

    public ConcentradoProcedimientos getCp() {
        return oCp;
    }

    public List<CabeceraActividadesEnfermeria> getArrServicios() {
        return arrServicios;
    }

    public ConcentradoProcedimientos[] getArrProced() {
        return arrProced;
    }
}
