package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Proveedor;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author GIL
 */
@ManagedBean(name="oConPro")
@ViewScoped
public class ConsultaProveedor implements Serializable {
private Proveedor[] arrPro ;
private boolean banderaBusqueda =false;
private Proveedor oProv=null;
private boolean botonGuarda=true;

    public ConsultaProveedor(){
    oProv = new Proveedor();
        try{
            arrPro= oProv.buscarTodos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
    public void eventoGuardar() throws Exception {
        registraP();
    }
    
    public String registraP() throws Exception {
    Proveedor proAux = new Proveedor();        
        proAux.setId(this.getProv().getId());
        if(this.getProv().getNombre()==null){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingrese un nombre ", " "));
            return "agregarProveedor";
        }
        if (!proAux.buscar()) {
            if (getProv().insertar() == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "PROVEEDOR", "INSERTADO : "+this.getProv().getId()));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "ID PROVEEDOR YA EXISTE  " + this.getProv().getId()));
               return "agregarProveedor";
            }
        
        }   
        getProv().setId(null);
        getProv().setNombre(null);
        getProv().setRFC(null);
        getProv().setCFISCAL(null);
        arrPro= oProv.buscarTodos();
        return "agregarProveedor";
    }
    
    public void onEdit(RowEditEvent event) throws Exception {
        setBanderaBusqueda(true);
        Proveedor oProvedor = (Proveedor) event.getObject();
        if (oProvedor.modificaDatosProvmat() == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Material  "+" "+oProvedor.getNombre()));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No Modificado", " Material"));
        }
        setBanderaBusqueda(false);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelado", ((Proveedor) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isBanderaBusqueda() {
        return banderaBusqueda;
    }

    public void setBanderaBusqueda(boolean banderaBusqueda) {
        this.banderaBusqueda = banderaBusqueda;
    }

    public Proveedor getProv() {
        return oProv;
    }

    public void setProv(Proveedor oProv) {
        this.oProv = oProv;
    }

    public Proveedor[] getArrPro() {
        return arrPro;
    }

    public void setArrPro(Proveedor[] arrPro) {
        this.arrPro = arrPro;
    }

    public boolean isBotonGuarda() {
        return botonGuarda;
    }

    public void setBotonGuarda(boolean botonGuarda) {
        this.botonGuarda = botonGuarda;
    }
}
