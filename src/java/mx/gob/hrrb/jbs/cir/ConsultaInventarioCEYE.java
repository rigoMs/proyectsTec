package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.almacen.InventarioMaterial;

/**
 *
 * @author Quintero
 */
@ManagedBean (name="oInvCEYE")
@ViewScoped
public class ConsultaInventarioCEYE implements Serializable{
    private InventarioMaterial oInvCEYE;
    private ArrayList<InventarioMaterial> arrInventario = null;
    private String sVisibilidad = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false;
    
    
    public ConsultaInventarioCEYE(){
        oInvCEYE = new InventarioMaterial();
    }
    
    public void inicializar() throws Exception{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("oMaterialSeleccionado", new InventarioMaterial());
        arrInventario = null;
    }
    
    public void consultaInventario(){
        sVisibilidad = "Visible";
        try{
            arrInventario = (new ArrayList<InventarioMaterial>(Arrays.asList(oInvCEYE.buscarMaterialCEYE())));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public InventarioMaterial getInvCEYE() { return oInvCEYE; }
    public ArrayList<InventarioMaterial> getListaInventario() { return arrInventario; }
    public void setInvCEYE(InventarioMaterial oInvCEYE) { this.oInvCEYE = oInvCEYE; }
    public String getVisibilidad() { return this.sVisibilidad; }
    public String getVisible(){
        if(this.bBuscado)
            Visible = "Visible";
        else 
            Visible = "hidden";
        return Visible;
    }
    
    
}
