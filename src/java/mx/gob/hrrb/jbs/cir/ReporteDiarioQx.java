package mx.gob.hrrb.jbs.cir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Quintero
 */
@ManagedBean (name="oReporteQx")
@ViewScoped
public class ReporteDiarioQx {
    private ProcedimientosRealizados oProcedimientos = new ProcedimientosRealizados();
    private ArrayList<ProcedimientosRealizados> arrProgramacion = null;
    private String sVisibilidad = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false;
    
    public List<Parametrizacion> getListaTiposQx() throws Exception{
        List<Parametrizacion> listaTipos = null;
        listaTipos = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscaTiposProceQx())));
        return listaTipos;
    }
    
    public void buscaProgramacion(){
        sVisibilidad = "Visible";
        try{
            arrProgramacion = new ArrayList<ProcedimientosRealizados>(
                    Arrays.asList(oProcedimientos.buscaReporteDiario()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
         
    public void editaCantidad(CellEditEvent event){
        String sMsg = "";
        sMsg = "Tipo de Operacion Modificado";
        FacesMessage msg = new FacesMessage("Editado", sMsg);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
   
    public void almacena(){
        int i = 0;
        FacesMessage msg;
        try{
            i = (oProcedimientos.modificarConjunto(arrProgramacion));
            if(i>0)
                msg = new FacesMessage("Almacenamiento","Se actualizo con éxito");
            else 
                msg = new FacesMessage("Almacenamiento","No se registraron los datos");
        }catch(Exception e){
            msg = new FacesMessage("Error", "Hubo un Error en la base de Datos");
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    public ArrayList<ProcedimientosRealizados> getListaProgramacion() { return arrProgramacion; }
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; }
    
    
    public String getVisibilidad() { return this.sVisibilidad; }
    public String getVisible(){
        if(this.bBuscado)
            Visible = "Visible";
        else 
            Visible = "hidden";
        return Visible;
    }
}
