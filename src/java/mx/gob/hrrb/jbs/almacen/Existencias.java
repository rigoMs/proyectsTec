package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;

/**
 *
 * @author GIL
 */
@ManagedBean(name="oExistencia")
@ViewScoped
public class Existencias implements Serializable {
public InventarioMateriales[] arrInv=null;
private List<InventarioMateriales> oMatFiltrado;
    
    public Existencias(){
    InventarioMateriales oInv =new InventarioMateriales();
        try{
            arrInv = (InventarioMateriales[])oInv.buscarExistencia();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public InventarioMateriales[] getLista(){
        return arrInv;
    }

    public List<InventarioMateriales> getMatFiltrado() {
        return oMatFiltrado;
    }

    public void setMatFiltrado(List<InventarioMateriales> oMatFiltrado) {
        this.oMatFiltrado = oMatFiltrado;
    }
}
