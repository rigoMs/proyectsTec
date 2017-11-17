
package mx.gob.hrrb.jbs.core.notas;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author J2GOV
 */
@ManagedBean(name="oGrafica")
@ViewScoped
public class HistoriaClinicaPerinatalGrafica implements Serializable {
     
    private LineChartModel oPinta;
    public HistoriaClinicaPerinatalGrafica() {
        createLineModels();
    }
    
     private void createLineModels() {
        oPinta = initLinearModel();
        oPinta.setTitle("PESO AL NACER EN RELACION CON LA EDAD GESTACIONAL");
        oPinta.setLegendPosition("nw");
        oPinta.setShowPointLabels(true);
        oPinta.getAxes().put(AxisType.X, new CategoryAxis("SEMANAS"));
        Axis  yAxis = oPinta.getAxis(AxisType.Y);        
        yAxis.setLabel("PESO AL NACER, GRAMOS");
        yAxis.setMin(700);
        yAxis.setMax(4000);
    }
    
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries linea1 = new ChartSeries();
        linea1.setLabel("linea 1");
        linea1.set("23", null);
        linea1.set("24", 800);
        linea1.set("25", null);
        linea1.set("26", 900);
        linea1.set("28", 950);
        linea1.set("30", 1100);
        linea1.set("32", 1400);
        linea1.set("34", 1800);
        linea1.set("36", 2350);
        linea1.set("38", 2500);
        linea1.set("40", 2600);
        linea1.set("41", 2650);
        linea1.set("42", 2680);
        linea1.set("44", null);
        linea1.set("46", null);
 
        ChartSeries linea2 = new ChartSeries();
        linea2.setLabel("linea 2");
        linea2.set("23", null);
        linea2.set("24", 900);
        linea2.set("25", null);
        linea2.set("26", 1010);
        linea2.set("28", 1290);
        linea2.set("30", 1500);
        linea2.set("32", 1795);
        linea2.set("34", 2695);
        linea2.set("36", 3000);
        linea2.set("38", 3110);
        linea2.set("40", 3200);
        linea2.set("41", 3220);
        linea2.set("42", 3235);
        linea2.set("44", 3240);
        linea2.set("46", null);
        
        ChartSeries linea3 = new ChartSeries();
        linea3.setLabel("linea 3");
        linea3.set("23", null);
        linea3.set("24", 1200);
        linea3.set("25", null);
        linea3.set("26", 1350);
        linea3.set("28", 1600);
        linea3.set("30", 1800);
        linea3.set("32", 2100);
        linea3.set("34", 3000);
        linea3.set("36", 3500);
        linea3.set("38", 3700);
        linea3.set("40", 3710);
        linea3.set("41", 3720);
        linea3.set("42", 3720);
        linea3.set("44", 3730);
        linea3.set("46", null);
        
        ChartSeries punto = new ChartSeries();
        punto.setLabel("peso");
        punto.set("32", 1200);
        
        model.addSeries(linea1);
        model.addSeries(linea2);
        model.addSeries(linea3);
        model.addSeries(punto);
        return model;
    }

    public LineChartModel getPinta() {
        return oPinta;
    }
    
}
