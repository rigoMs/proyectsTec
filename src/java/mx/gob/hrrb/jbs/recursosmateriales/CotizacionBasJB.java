/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.recursosmateriales;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
 
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author DavidH
 */
@ManagedBean (name="cotizac")
@ViewScoped
public class CotizacionBasJB {

    /**
     * Creates a new instance of CotizacionBasJB
     */
    
    List<String> productosSource = new ArrayList<String>();
    
    public CotizacionBasJB() {
    }
    
    private DualListModel<String> productos;
     
    @PostConstruct
    public void init() {
        //Cities
        //List<String> citiesSource = new ArrayList<String>();
        List<String> productosTarget = new ArrayList<String>();
         
        productosSource.add(" 1  Arroz  kg");
        productosSource.add(" 2  Frijol kg");
        productosSource.add(" 3  Aceite lt");
        productosSource.add(" 4  Azucar kg");
         
        productos = new DualListModel<String>(productosSource, productosTarget);
         
        
    }
 
    public DualListModel<String> getProductos() {
        return productos;
    }
 
    public void setProductos(DualListModel<String> productos) {
        this.productos = productos;
    }
    
     
 
 
    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }
     
    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }
     
    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    } 
}
