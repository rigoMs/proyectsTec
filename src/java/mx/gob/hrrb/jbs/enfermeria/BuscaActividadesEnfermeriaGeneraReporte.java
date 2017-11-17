
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.enfermeria.CabeceraActividadesEnfermeria;
import mx.gob.hrrb.modelo.enfermeria.DetalleActividadesEnfermeria;
import mx.gob.hrrb.modelo.enfermeria.reporte.ReporteActividadesEnfermeria;

/**
 *
 * @author Javier
 * @objetivo: busca el reporte diario y reporte mensual de las actividades de enfermeria en los diferentes servicios
 */
@ManagedBean(name="oBActEnf")
@ViewScoped
public class BuscaActividadesEnfermeriaGeneraReporte implements Serializable{
    
    private DetalleActividadesEnfermeria oBusca;
    private List<CabeceraActividadesEnfermeria> arrServicios;
    private ArrayList<DetalleActividadesEnfermeria> arrActividades;
    
    /*para el reporte mensual de actividades enfermeria*/
    private boolean bRendering;
    private String sNombreServicio;
    private ReporteActividadesEnfermeria oReporteMensual;
    private ArrayList<ReporteActividadesEnfermeria> arrMensual;  
    private String sMes;
    private String sAnho;
    private String sMaxAnho;
    
    public BuscaActividadesEnfermeriaGeneraReporte() {
        oBusca= new DetalleActividadesEnfermeria();
        arrActividades=null;
        oReporteMensual = new ReporteActividadesEnfermeria();
        arrMensual=null;
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
    
    public void buscaActividadesEnfermeria(){
        try{
            arrActividades= oBusca.buscaActividadesEnfermeriaReporteDiario();
            buscaNombreServicio();
            if(arrActividades==null || arrActividades.isEmpty()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ALERTA","No se encuenta actividades para el reporte"));
            }else{
               buscaNombreResponsableTurno(); 
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void buscaNombreServicio(){
        for(CabeceraActividadesEnfermeria oCb: arrServicios){
            if(oCb.getAreaServicio().getClave()==this.getBuscaActividades().getCabeceraAct().getAreaServicio().getClave()){
                this.getBuscaActividades().getCabeceraAct().getAreaServicio().setDescripcion(oCb.getAreaServicio().getDescripcion());
                break;
            }
        }
    }
    
    public void buscaNombreResponsableTurno(){
        this.getBuscaActividades().getCabeceraAct().setEnfermeraMAT(new PersonalHospitalario());
        this.getBuscaActividades().getCabeceraAct().getEnfermeraMAT().setNombres(arrActividades.get(1).getArrActividades().get(1).getCabeceraAct().getEnfermeraMAT().getNombreCompleto());
        this.getBuscaActividades().getCabeceraAct().setEnfermeraVES(new PersonalHospitalario());
        this.getBuscaActividades().getCabeceraAct().getEnfermeraVES().setNombres(arrActividades.get(1).getArrActividades().get(1).getCabeceraAct().getEnfermeraVES().getNombreCompleto());
        this.getBuscaActividades().getCabeceraAct().setEnfermeraNOC(new PersonalHospitalario());
        this.getBuscaActividades().getCabeceraAct().getEnfermeraNOC().setNombres(arrActividades.get(1).getArrActividades().get(1).getCabeceraAct().getEnfermeraNOC().getNombreCompleto());
    }
    
    /*Reporte Mensual*/
    public void buscaNombreServicioReporte(){
        for(CabeceraActividadesEnfermeria oCb: arrServicios){
            if(oCb.getAreaServicio().getClave()==this.getReporteMensual().getServicio().getClave()){
                this.sNombreServicio=oCb.getAreaServicio().getDescripcion();
                break;
            }
        }
    }
    
    public void buscaReporteMensualActividadesEnfermeria(){              
        if(this.oReporteMensual.getServicio().getClave()!=0){
            validaFecha();            
            try {
                arrMensual= oReporteMensual.buscaReporteMensualActividadesEnfermeria();
                if(arrMensual!=null && !arrMensual.isEmpty()){
                    this.bRendering=true;
                    buscaNombreServicioReporte();
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","la busqueda no retorna ninguna información"));
                }
                buscaNombreServicioReporte();
            } catch (Exception ex) {
                Logger.getLogger(BuscaActividadesEnfermeriaGeneraReporte.class.getName()).log(Level.SEVERE, null, ex);
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
        this.getReporteMensual().setFechaInicioStr(sFecha1);
        this.getReporteMensual().setFechaFinStr(sFecha2);    
        this.getReporteMensual().setFechaInicioStr2("26/"+this.getMes()+"/"+this.getAnho());
        this.getReporteMensual().setFechaFinStr2("25/"+sM+"/"+this.getAnho());
    }    
    
    
    /*termina*/
    
    public DetalleActividadesEnfermeria getBuscaActividades() {
        return oBusca;
    }
    
    public List<CabeceraActividadesEnfermeria> getArrServicios() {
        return arrServicios;
    }

    public ArrayList<DetalleActividadesEnfermeria> getArrActividades() {
        return arrActividades;
    }
    public boolean getRendering() {
        return bRendering;
    }
    public ReporteActividadesEnfermeria getReporteMensual() {
        return oReporteMensual;
    }
    public ArrayList<ReporteActividadesEnfermeria> getArrMensual() {
        return arrMensual;
    }

    public String getNombreServicio() {
        return sNombreServicio;
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
    
}
