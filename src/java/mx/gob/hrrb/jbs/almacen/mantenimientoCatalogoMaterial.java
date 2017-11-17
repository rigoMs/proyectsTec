/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@ManagedBean(name="oMantenimiento")
@ViewScoped
public class mantenimientoCatalogoMaterial implements Serializable  {
    public InventarioMateriales[] arrInv=null;
    private List<InventarioMateriales> oMatFiltrado;
    
    public mantenimientoCatalogoMaterial(){
    InventarioMateriales oInv =new InventarioMateriales();
    
     

        try{
            arrInv = (InventarioMateriales[])oInv.buscarTodosInventario();
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