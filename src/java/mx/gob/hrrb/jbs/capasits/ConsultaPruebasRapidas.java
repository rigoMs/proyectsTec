package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.PruebasRapidas;

/**
 *
 * @author pedro
 */
@ManagedBean (name="pruebasr")
@SessionScoped
public class ConsultaPruebasRapidas implements Serializable{
private PruebasRapidas oPruer;

    public ConsultaPruebasRapidas() {
        oPruer=new PruebasRapidas();
    }
    
    public PruebasRapidas getPruebr(){
        return oPruer;
    }
    
     public String almacena() throws Exception{
        String sRet="";
        int nAfec =0;
        Date fecha= new Date();
        oPruer.setFecha(fecha);
         try{ 
            nAfec=this.oPruer.insertar(); 
            if(nAfec==1){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Prueba rapida guardada","Prueba rapida guardada"));
            sRet="Pruebasrapidas.xhtml";}
            else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } catch(Exception e){
            e.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.oPruer=new PruebasRapidas();
         return sRet;
    }
}
