/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.cir.Residente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import org.primefaces.context.RequestContext;
/**
 *
 * @author Quintero
 */
@ManagedBean (name="oInfoResidentes")
@ViewScoped
public class ConsultaInfoResidentes implements Serializable{
    private Residente oResidentes = new Residente();;
    private ArrayList<Residente> arrResidentes = null;
    private boolean bBuscado = false;
    private String sVisibilidad = "hidden";
    private String Visible = "hidden";
    private int nSex = 0;
    private String sEdoCivil = "";
    private Parametrizacion arrEdosCivil[];
    private String sFechaNac = "";
    
    public ConsultaInfoResidentes(){
        oResidentes = new Residente();
    }
    
    public void inicializar() throws Exception{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("oRecidenteSeleccionado", new Residente());
        arrResidentes = null;
    }
    
    public void  consultaResidentes(){
        sVisibilidad ="visible";
        try{
            arrResidentes = (new ArrayList<Residente>(Arrays.asList(oResidentes.buscaResidentesVigentes())));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void setSeleccionado(Residente valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("residenteSelec", valor);
    }
    
    public Residente getSeleccionado(){
         return new Residente();
    }
    
    public void llenaDatosResidente(){
         Residente oRes = null;
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
         oRes = (Residente)session.getAttribute("residenteSelec");
         if(oRes == null){
            FacesMessage msj2 = new FacesMessage("¡Alerta!","Selecciona Un Residente");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bBuscado = false;
        }else{
            System.out.println("Estamos en la impresion de Datos "+oRes.getNombreCompleto());
            oResidentes = oRes;
            bBuscado = true;
        }
    }
    
    public void limpia(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("residSelec",null);
    }
    
    public void registraNuevoResidente(){
        FacesMessage message = null;
        try{
            System.out.println(getFechaNac());
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fNac = fecha.parse(sFechaNac);
            oResidentes.setFechaNac(fNac);
            if(oResidentes.regitraResidente() == 1)
                //message = new FacesMessage(FacesMessage.SEVERITY_INFO,"","");  
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Residente","Nuevo Residente  :) !!"));
        }catch (Exception ex){
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Residentes","Ocurrio un Error al Guardar :(");
        }
       // RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    
    public ArrayList<Residente> getListaResidentes(){ return arrResidentes; }
    public Residente getResidentes() { return oResidentes; }
    public void setResidentes(Residente oResidentes) { this.oResidentes = oResidentes; }
    public String getVisibilidad() { return this.sVisibilidad; } 
    public String getVisible(){
         if(this.bBuscado)
             Visible = "visible";
         else
             Visible = "hidden";
         return Visible;
     }
    public String getFechaNac() {
        return sFechaNac;
    }
    public void setFechaNac(String sFechaNac) {
        this.sFechaNac = sFechaNac;
    }
}
