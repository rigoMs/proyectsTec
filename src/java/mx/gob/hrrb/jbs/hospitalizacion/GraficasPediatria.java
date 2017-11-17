/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

 
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
 
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
 
/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oGraPed")
@SessionScoped

public class GraficasPediatria implements Serializable {
 
    private BarChartModel barModel;
    private LineChartModel lineModel;
    private Hospitalizacion oHosp;
    private List<Hospitalizacion> lHospitalizacion;
    private Date dFechaIni;
    private Date dFechaFin;
    private String sFechaI;
    private String sFechaF;
    private AreaServicioHRRB oASerHRRB;
    private int nTipoGra;
    private String sEstilo;
 
    public GraficasPediatria(){
        oHosp = new Hospitalizacion();
        oASerHRRB = new AreaServicioHRRB();
        dFechaIni = null;
        dFechaFin = null;
        sEstilo="display:none";
    }
    @PostConstruct
    public void init() {
        createBarModels();
    }    
 
    public BarChartModel getBarModel() {
        return barModel;
    }
    
    public LineChartModel getLineModel(){
        return lineModel;
    }
      
    private void createBarModels() {
        generar();
    }
    
    public void generar(){
        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fF=new SimpleDateFormat("yyyy-MM-dd");
        
        if(getFechaIni()!=null && getFechaFin()!=null){
            setFechaI(fI.format(getFechaIni()));
            setFechaF(fF.format(getFechaFin()));
        }else{
            setFechaI("");
            setFechaF("");
        }
        graficaEdad();
        switch(getTipoGra()){
            case 0:
                graficaEdad();
                break;
            case 1:
                graficaEdad();
                break;                
            case 2:
                graficaSexo();
                break;
            case 3:
                graficaEgreso();
                break;
            case 4:
                graficaEgresoEdad();
                break;
            case 5:
                graficaEgresoSexo();
                break;                
            case 6:
                graficaEgresoDiagSexo();
                break;
        }
    }
     
    public void graficaEdad(){
        int tam = 0;
        String sTituloB="";
        String sTituloL="";
        try {
            barModel = oHosp.initBarModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),1);
            lineModel = oHosp.initLineModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),1);  
            tam=oHosp.tamGra;
        } catch (Exception ex) {
            Logger.getLogger(GraficasPediatria.class.getName()).log(Level.SEVERE, null, ex);
        }

        sTituloB="Gráfica Por Rango De Edades";
        barModel.setTitle(sTituloB);
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(getFechaI()+" - "+getFechaF());
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        

        sTituloL="Gráfica Por Rango de Edades";
        lineModel.setTitle(sTituloL);      
        lineModel.setLegendPosition("e");
        lineModel.setAnimate(true);
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis(getFechaI()+" - "+getFechaF()));
        yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        sEstilo="display:none";
    }     
    
    public void graficaSexo(){
        int tam = 0;
        String sTituloB="";
        String sTituloL="";
        try {
            barModel = oHosp.initBarModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),2);
            lineModel = oHosp.initLineModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),2);  
            tam=oHosp.tamGra;
        } catch (Exception ex) {
            Logger.getLogger(GraficasPediatria.class.getName()).log(Level.SEVERE, null, ex);
        }

        sTituloB="Gráfica Por Sexo";
        barModel.setTitle(sTituloB);
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(getFechaI()+" - "+getFechaF());
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        
        sTituloL="Gráfica Por Sexo";
        lineModel.setTitle(sTituloL);      
        lineModel.setLegendPosition("e");
        lineModel.setAnimate(true);
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis(getFechaI()+" - "+getFechaF()));
        yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        sEstilo="display:none";
    }     
    
    public void graficaEgreso(){
        int tam = 0;
        String sTituloB="";
        String sTituloL="";
        try {
            barModel = oHosp.initBarModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),3);
            lineModel = oHosp.initLineModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),3);  
            tam=oHosp.tamGra;
        } catch (Exception ex) {
            Logger.getLogger(GraficasPediatria.class.getName()).log(Level.SEVERE, null, ex);
        }

        sTituloB="Gráfica Por Motivo Egreso";
        barModel.setTitle(sTituloB);
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(getFechaI()+" - "+getFechaF());
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        
        sTituloL="Gráfica Por Motivo Egreso";
        lineModel.setTitle(sTituloL);      
        lineModel.setLegendPosition("e");
        lineModel.setAnimate(true);
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis(getFechaI()+" - "+getFechaF()));
        yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        sEstilo="display:none";
    }        
    
    public void graficaEgresoEdad(){
        int tam = 0;
        String sTituloB="";
        String sTituloL="";
        try {
            barModel = oHosp.initBarModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),4);
            lineModel = oHosp.initLineModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),4);  
            tam=oHosp.tamGra;
        } catch (Exception ex) {
            Logger.getLogger(GraficasPediatria.class.getName()).log(Level.SEVERE, null, ex);
        }

        sTituloB="Gráfica De Motivos De Egresos Por Rango De Edades";
        barModel.setTitle(sTituloB);
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(getFechaI()+" - "+getFechaF());
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        

        sTituloL="Gráfica De Motivos De Egresos Por Rango De Edades";
        lineModel.setTitle(sTituloL);      
        lineModel.setLegendPosition("e");
        lineModel.setAnimate(true);
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis(getFechaI()+" - "+getFechaF()));
        yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        sEstilo="display:none";
    }      
    
    public void graficaEgresoSexo(){
        int tam = 0;
        String sTituloB="";
        String sTituloL="";
        try {
            barModel = oHosp.initBarModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),5);
            lineModel = oHosp.initLineModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),5);  
            tam=oHosp.tamGra;
        } catch (Exception ex) {
            Logger.getLogger(GraficasPediatria.class.getName()).log(Level.SEVERE, null, ex);
        }

        sTituloB="Gráfica De Motivos De Egresos Por Sexo";
        barModel.setTitle(sTituloB);
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(getFechaI()+" - "+getFechaF());
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        

        sTituloL="Gráfica De Motivos De Egresos Por Sexo";
        lineModel.setTitle(sTituloL);      
        lineModel.setLegendPosition("e");
        lineModel.setAnimate(true);
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis(getFechaI()+" - "+getFechaF()));
        yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        sEstilo="display:none";
    }       
    
    public void graficaEgresoDiagSexo(){
        int tam = 0;
        String sTituloB="";
        String sTituloL="";
        try {
            barModel = oHosp.initBarModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),6);
            lineModel = oHosp.initLineModel(getFechaI(), getFechaF(), oASerHRRB.getClave(),6);  
            tam=oHosp.tamGra;
        } catch (Exception ex) {
            Logger.getLogger(GraficasPediatria.class.getName()).log(Level.SEVERE, null, ex);
        }

        sTituloB="Gráfica De Diagnosticos Más Frecuentes por Sexo";
        barModel.setTitle(sTituloB);
        barModel.setLegendPosition("e");
        barModel.setAnimate(true);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(getFechaI()+" - "+getFechaF());
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        

        sTituloL="Gráfica De Diagnosticos Más Frecuentes por Sexo";
        lineModel.setTitle(sTituloL);      
        lineModel.setLegendPosition("e");
        lineModel.setAnimate(true);
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis(getFechaI()+" - "+getFechaF()));
        yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Egresos");
        yAxis.setMin(0);
        yAxis.setMax(tam+1);
        sEstilo="";
        lHospitalizacion=oHosp.getHospitalizacion();
    }      
    
   public List<AreaServicioHRRB> getListaAreasServicio(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasServicioUsuario()));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }      
    
    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }

    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }

    /**
     * @return the dFechaIni
     */
    public Date getFechaIni() {
        return dFechaIni;
    }

    /**
     * @param dFechaIni the dFechaIni to set
     */
    public void setFechaIni(Date dFechaIni) {
        this.dFechaIni = dFechaIni;
    }

    /**
     * @return the dFechaFin
     */
    public Date getFechaFin() {
        return dFechaFin;
    }

    /**
     * @param dFechaFin the dFechaFin to set
     */
    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

    /**
     * @return the sFechaI
     */
    public String getFechaI() {
        return sFechaI;
    }

    /**
     * @param sFechaI the sFechaI to set
     */
    public void setFechaI(String sFechaI) {
        this.sFechaI = sFechaI;
    }

    /**
     * @return the sFechaF
     */
    public String getFechaF() {
        return sFechaF;
    }

    /**
     * @param sFechaF the sFechaF to set
     */
    public void setFechaF(String sFechaF) {
        this.sFechaF = sFechaF;
    }

    /**
     * @return the oASerHRRB
     */
    public AreaServicioHRRB getASerHRRB() {
        return oASerHRRB;
    }

    /**
     * @param oASerHRRB the oASerHRRB to set
     */
    public void setASerHRRB(AreaServicioHRRB oASerHRRB) {
        this.oASerHRRB = oASerHRRB;
    }

    /**
     * @return the nTipoGra
     */
    public int getTipoGra() {
        return nTipoGra;
    }

    /**
     * @param nTipoGra the nTipoGra to set
     */
    public void setTipoGra(int nTipoGra) {
        this.nTipoGra = nTipoGra;
    }

    /**
     * @return the lHospitalizacion
     */
    public List<Hospitalizacion> getHospitalizacion() {
        return lHospitalizacion;
    }

    /**
     * @param lHospitalizacion the lHospitalizacion to set
     */
    public void setHospitalizacion(List<Hospitalizacion> lHospitalizacion) {
        this.lHospitalizacion = lHospitalizacion;
    }

    /**
     * @return the sEstilo
     */
    public String getEstilo() {
        return sEstilo;
    }

    /**
     * @param sEstilo the sEstilo to set
     */
    public void setEstilo(String sEstilo) {
        this.sEstilo = sEstilo;
    }
 
}