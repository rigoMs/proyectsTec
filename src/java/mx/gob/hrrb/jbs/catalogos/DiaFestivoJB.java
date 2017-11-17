package mx.gob.hrrb.jbs.catalogos;

import mx.gob.hrrb.modelo.core.DiaFestivo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;

/**
 * @author ivan__000
 */

@ManagedBean(name = "oDfAni")
@ViewScoped

public class DiaFestivoJB implements Serializable {
    private short nCveAni;
    private int nAnio;
    private DiaFestivo[] arrAnio = null;
    private ArrayList<DiaFestivo> arrAni=null;
    private String visible="hidden";
    
    private ArrayList<DiaFestivo> arrDias = null;
    private DiaFestivo oAnioSeleccionado = new DiaFestivo();
    
    private Date dDia, dDiaEli;
    private String locale;
    
    public DiaFestivoJB(){
        int i=0, j=0;
        try{
            arrAnio = (new DiaFestivo()).buscarAnio();
            locale="es";
        } catch(Exception e){
            e.printStackTrace();
        }
    }
  
    public void onAnioChan() {
    DiaFestivo oAnio;
        if(nCveAni > 0){
            oAnio = new DiaFestivo(nCveAni, nAnio);
            oAnio.setAnio(nAnio);
            oAnio.getAnio();
            try{
                arrAni = new ArrayList<DiaFestivo>(
                    Arrays.asList((DiaFestivo[])oAnio.buscarTodosPorAnio(nAnio)));
            }catch(Exception e){
                e.printStackTrace();
            }
        }else {
            arrAni = null;
        }
        if(nCveAni>0){
            visible="visible";
        }else{
            visible="hidden";
        }
    }
    
    public void agregarTabla(){
        DiaFestivo oDiaFestivo = new DiaFestivo();
        try{
            arrDias = new ArrayList<DiaFestivo>(
            Arrays.asList((DiaFestivo[])oDiaFestivo.buscarTodosPorAnio(nAnio)));
        }catch(Exception e){
            e.printStackTrace();
        }
        visible="visible";
    }
    
    /*----- Agregar Fecha -----*/
    public void almacenarFecha() throws Exception{
        int i = 0;
        Date fcha = new Date();
        FacesMessage msg;
        int j;
        j = (new DiaFestivo()).buscarFech(dDia);
        if(j==1)
        {
            msg = new FacesMessage("¡Advertencia!","La Fecha ya Existe");
        } else if (dDia.after(fcha)){
                try{
                    i = (new DiaFestivo()).insertar(dDia);
                    if (i>0){
                        msg = new FacesMessage("Almacenamiento", "Fecha Agregada en la BD");
                    }else{
                        msg = new FacesMessage("Almacenamiento", "Error en la BD");
                    }
                }catch(Exception e){
                    msg = new FacesMessage("Almacenamiento", "Error al Afectar la Base de Datos");
                    e.printStackTrace();
                }
            }else{
                msg = new FacesMessage("¡Advertencia!","Solo se Pueden Agregar Fechas Futuras");
            }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /*----- Eliminar Fecha -----*/
    public void eliminarFecha() throws Exception{
        int i = 0;
        FacesMessage msg;
        try{
            i = (new DiaFestivo()).eliminar(dDiaEli);
            if (i>0){
                msg = new FacesMessage("Borrado", "Fecha Eliminada de la BD");
            } else {
                msg = new FacesMessage("Borrado", "La Fecha No Existe");
            }
        }catch(Exception e){
            msg = new FacesMessage ("Borrado", "Error al Afectar la Base de Datos");
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /*----- Para Calendario -----*/
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "La fecha es", format.format(event.getObject())));
    }
    
    
    public void cambiaEspaniol(ActionEvent ae){
        this.locale = "es";
    }
   
    public ArrayList<DiaFestivo> getLista(){
        return arrDias;
    }
    
    public String getVisible(){
        return visible;
    }
    
    public void setVisible(String v){
        visible=v;
    }
    
    public DiaFestivo[] getArrAnio() {
        return arrAnio;
    }

    public void setArrAnio(DiaFestivo[] arrAni) {
        this.arrAnio = arrAni;
    }
    
    public short getCve(){
        return nCveAni;
    }
    
    public void setCve(short nCveAni){
        nCveAni = nCveAni;
    }
    
    public int getAnio(){
        return nAnio;
    }
    
    public void setAnio(int nAni){
        nAnio = nAni;
    }
    
    public DiaFestivo getSeleccion(){
        return oAnioSeleccionado;
    }
    
    public void setSeleccion(DiaFestivo oAnioSeleccionado){
        this.oAnioSeleccionado = oAnioSeleccionado;
    }
    
    public Date getDia() {
        return dDia;
    }
    public void setDia(Date value) {
        dDia = value;
    }
    
    public Date getDiaEli(){
        return dDiaEli;
    }
    
    public void setDiaEli(Date value){
        dDiaEli = value;
    }
    
    public Date getActual(){
        return new Date();
    }
    
    public String getLocale(){
        return this.locale;
    }
    public void setLocale(String val){
        this.locale = val;
    }
}

