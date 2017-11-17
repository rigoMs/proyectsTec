package mx.gob.hrrb.jbs.capasits;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.GruposCapasits;

/**
 *
 * @author pedro
 */
@ManagedBean (name="grupos")
@SessionScoped
public class ConsultaGruposCapa {
    private GruposCapasits oGruc;

    public ConsultaGruposCapa() {
        oGruc= new GruposCapasits();
    }
    
    public GruposCapasits getGrupc(){
        return oGruc;
    }
    
    public String almacena() throws Exception{
        String sRet="";
        int nAfec =0;
        Date fecha= new Date();
        oGruc.setFecha(fecha);
        oGruc.setTipogrupo(1);
         try{ 
            nAfec=this.oGruc.insertar(); 
            if(nAfec==1){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Grupo Insertado","Grupo Insertado"));
            sRet="/faces/sesiones/capasits/grupos.xhtml";}
            else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } catch(Exception e){
            e.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.oGruc=new GruposCapasits();
         return sRet;
    }
    
    public String almacena2() throws Exception{
        String sRet="";
        int nAfec =0;
        Date fecha= new Date();
        oGruc.setFecha(fecha);
        oGruc.setTipogrupo(2);
         try{ 
            nAfec=this.oGruc.insertar(); 
            if(nAfec==1){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Grupo Insertado","Grupo Insertado"));
            sRet="/faces/sesiones/capasits/grupos.xhtml";}
            else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } catch(Exception e){
            e.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.oGruc=new GruposCapasits();
         return sRet;
    }
    
    public String almacena3() throws Exception{
        String sRet="";
        int nAfec =0;
        Date fecha= new Date();
        oGruc.setFecha(fecha);
        oGruc.setTipogrupo(3);
         try{ 
            nAfec=this.oGruc.insertar(); 
            if(nAfec==1){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Grupo Insertado","Grupo Insertado"));
            sRet="/faces/sesiones/capasits/grupos.xhtml";}
            else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } catch(Exception e){
            e.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.oGruc=new GruposCapasits();
         return sRet;
    }
    
    public String almacena4() throws Exception{
        String sRet="";
        int nAfec =0;
        Date fecha= new Date();
        oGruc.setFecha(fecha);
        oGruc.setTipogrupo(4);
        oGruc.setTipo("GRUPOS DE AYUDA MUTUA");
         try{ 
            nAfec=this.oGruc.insertar(); 
            if(nAfec==1){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Grupo Insertado","Grupo Insertado"));
            sRet="/faces/sesiones/capasits/grupos.xhtml";}
            else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } catch(Exception e){
            e.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.oGruc=new GruposCapasits();
         return sRet;
    }
    
    public String almacena5() throws Exception{
        String sRet="";
        int nAfec =0;
        Date fecha= new Date();
        oGruc.setFecha(fecha);
        oGruc.setTipogrupo(5);
         try{ 
            nAfec=this.oGruc.insertar(); 
            if(nAfec==1){
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Grupo Insertado","Grupo Insertado"));
            sRet="/faces/sesiones/capasits/grupos.xhtml";}
            else
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         } catch(Exception e){
            e.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
         this.oGruc=new GruposCapasits();
         return sRet;
    }
    
}
