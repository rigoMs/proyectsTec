package mx.gob.hrrb.jbs.catalogos;

/**
 *
 * @author Aylin
 */


import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.TipoMedico;
import org.primefaces.event.RowEditEvent;

@ManagedBean (name="tipomedico")
@SessionScoped
public class TipoMedicoJB {
     private TipoMedico tipo;
    private TipoMedico[] lista;
    private boolean visible1=false;
    
    public TipoMedicoJB() {
        tipo=new TipoMedico();
       cargalista();
    }
    
    private void cargalista(){
        try{
            lista=tipo.buscarTodos();
            System.out.println("long "+lista.length);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    public TipoMedico[] getLista(){
        return lista;
    }
    
   public void onEdit(RowEditEvent event) throws Exception {
       tipo = (TipoMedico) event.getObject();
        int res=tipo.modificar();
        
        if(res==1){
            FacesMessage msg;
            msg = new FacesMessage("Modificacion ","modificacion exitosa");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
                FacesMessage msg;
            msg = new FacesMessage("Error","Error al modificar");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
   /*  public void onEditRow(RowEditEvent event) {
    TipoMedico tipo = (TipoMedico) event.getObject();
    String sMsg = "";
        try{
            sMsg = tipo.getDescripcion();
            tipo.modificar();
            System.out.println("Modificado");
        }catch(Exception e){
            e.printStackTrace();
            sMsg = "Error al actualizar bd";
        }
        FacesMessage msg = new FacesMessage("Editado", sMsg);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void onCancel(RowEditEvent event) throws ParseException {
        FacesMessage msg;
        msg = new FacesMessage("Cancelado", "cancelación exitosa");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }*/
    
    /* public void onBuscar{
       TipoMedico tipo = (TipoMedico) event.getObject();
         boolean res=tipo.buscar();
        if(res==true){
            FacesMessage msg;
            msg = new FacesMessage("Buscar ","Busqueda exitosa");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
                FacesMessage msg;
            msg = new FacesMessage("Error","Error al buscar");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }*/
    
   
    
      private List<TipoMedico> arrTipoFiltrado;
    
    public List<TipoMedico> getPersFiltrado() {
        return arrTipoFiltrado;
    }
    public void setPersFiltrado(List<TipoMedico> valor) {
        this.arrTipoFiltrado = valor;
    }
    
    public boolean getVisible1(){
        return visible1;
    }
    public void setVisible1(boolean v){
        visible1=v;
    }
    
    public TipoMedico getTipo(){
        return tipo;
    }
    public TipoMedico setTipo(){
        return tipo;
    }
    public String almacena(){
        //tipo.setHabilitado(true);
        int res=0;
        
         try{
            res=tipo.insertar();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        if(res==1){
            setVisible1(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "COMPLETADO", "Se inserto correctamente"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "al insertar"));
        }
       
        
        cargalista();
        return "catalogoTipoMedico.xhtml";
    }
    
    
	
    
  
   
}



