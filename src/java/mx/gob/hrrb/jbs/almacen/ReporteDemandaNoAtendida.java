package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.almacen.ReporteDNA;
import mx.gob.hrrb.modelo.almacen.ValeColectivo;

/**
 *
 * @author GIL
 */
@ManagedBean(name = "oDNA")
@ViewScoped
public class ReporteDemandaNoAtendida implements Serializable {
private ReporteDNA[] arrReporteDNA=null;
private ReporteDNA reporte=null;
private ValeColectivo vale;
private Material material;   
private InventarioMateriales inventario;
private Date fechaIn=null;
private Date fechaFi=null;
    
    public ReporteDemandaNoAtendida() throws Exception{
       reporte=new ReporteDNA();
       vale=new ValeColectivo();
       material=new Material();
       inventario=new InventarioMateriales();
    }
    
    public void eventoDNA(ActionEvent even)throws Exception{
        listaDNA();
    }
      
    public void listaDNA() throws Exception {
        setArrReporteDNA((ReporteDNA[])  new ReporteDNA().buscarRangoDNA(
                getFechaIn(), getFechaFi()));
        setFechaIn(null);
        setFechaFi(null);
    }

    public Date getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(Date fechaIn) {
        this.fechaIn = fechaIn;
    }

    public Date getFechaFi() {
        return fechaFi;
    }

    public void setFechaFi(Date fechaFi) {
        this.fechaFi = fechaFi;
    }

    public ReporteDNA[] getArrReporteDNA() {
        return arrReporteDNA;
    }

    public void setArrReporteDNA(ReporteDNA[] arrReporteDNA) {
        this.arrReporteDNA = arrReporteDNA;
    }

    public ReporteDNA getReporte() {
        return reporte;
    }

    public void setReporte(ReporteDNA reporte) {
        this.reporte = reporte;
    }
    
    public ValeColectivo getVale() {
        return vale;
    }

    public void setVale(ValeColectivo vale) {
        this.vale = vale;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public InventarioMateriales getInventario() {
        return inventario;
    }

    public void setInventario(InventarioMateriales inventario) {
        this.inventario = inventario;
    }
}
