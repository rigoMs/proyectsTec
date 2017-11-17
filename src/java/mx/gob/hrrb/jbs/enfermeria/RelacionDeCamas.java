
package mx.gob.hrrb.jbs.enfermeria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.enfermeria.reporte.RelacionDeCamasCensables;

/**
 *
 * @author J2GOV
 */
@ManagedBean(name="oRCama")
@ViewScoped
public class RelacionDeCamas implements Serializable {
    
    private boolean bRender;
    private RelacionDeCamasCensables oCama;   
    private Map<String,Integer> mapServicio;
    private ArrayList<RelacionDeCamasCensables> arrCamasCensables;
    
    public RelacionDeCamas() {
        mapServicio= new HashMap<String,Integer>();
        oCama= new RelacionDeCamasCensables();
        cargaServicio();
    }

    
    private void cargaServicio(){
        getMapServicio().put("TOCO CIRUGIA", 69);
        getMapServicio().put("GINECO-OBSTETRICIA", 29);
        getMapServicio().put("NEONATOLOGÍA", 39);
        getMapServicio().put("PEDIATRIA", 56);
    }
    
    public void BuscaNombreServicio(){
        for (Map.Entry<String,Integer> oSer : mapServicio.entrySet()){
            if(oSer.getValue()==oCama.getServicio().getClave()){
               oCama.getServicio().setDescripcion(oSer.getKey());
               break;
            }
        }
    }
    
    public void buscaRelacuionCamasServicios(){
        if(oCama.getServicio().getClave()!=0){
            try {
                BuscaNombreServicio();
                bRender=true;
                arrCamasCensables= oCama.buscaRelacionDeCamasCensablesServicio();
            } catch (Exception ex) {
                Logger.getLogger(RelacionDeCamas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
    public Map<String,Integer> getMapServicio() {
        return mapServicio;
    }

    public RelacionDeCamasCensables getCama() {
        return oCama;
    }
    
    public boolean getRender(){
        return bRender;
    }

    public ArrayList<RelacionDeCamasCensables> getArrCamasCensables() {
        return arrCamasCensables;
    }
    
}
