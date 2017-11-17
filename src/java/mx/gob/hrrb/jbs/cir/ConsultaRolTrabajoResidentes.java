/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.cir.Residente;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;

/**
 *
 * @author Quintero
 */
@ManagedBean (name="oTrabajoResid")
@ViewScoped
public class ConsultaRolTrabajoResidentes implements Serializable {
    private ProcedimientosRealizados oProcedimientos = new ProcedimientosRealizados();
    private Medico oResidentes = new Medico();
    private ArrayList<ProcedimientosRealizados> arrProcedimientos;
    private String sVisibilidad = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false; 
    private Date dFechaInicio;
    private Date dFechaFin;
    
    public ConsultaRolTrabajoResidentes(){}
    
    public void inicializar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("oProcedimientoSeleccionado", new ProcedimientosRealizados());
        arrProcedimientos = null;
    }
    
    public List<Residente> getListaResidentes() throws Exception{
        List<Residente> listaResidente = null;
        listaResidente = new ArrayList<Residente>(Arrays.asList((new Residente().buscarResidentes())));
        return listaResidente;
    }
    
    public void buscaSolicitudes(){
        sVisibilidad = "visible";
        try{
            arrProcedimientos = new ArrayList<ProcedimientosRealizados>(Arrays.asList(oProcedimientos.buscaProcedimientosxResidente(oResidentes, dFechaInicio, dFechaFin)));
        }catch(Exception ex){            
            ex.printStackTrace();
        }
    }
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; } 
    public String getVisibilidad() { return this.sVisibilidad; } 
    public String getVisible(){
         if(this.bBuscado)
             Visible = "visible";
         else
             Visible = "hidden";
         return Visible;
    }
    public ArrayList<ProcedimientosRealizados> getListaProcedimientos(){ return arrProcedimientos; }
    public Date getFechaInicio() { return dFechaInicio; }
    public void setFechaInicio(Date dFechaInicio) { this.dFechaInicio = dFechaInicio; }
    public Date getFechaFin() { return dFechaFin; }
    public void setFechaFin(Date dFechaFin) { this.dFechaFin = dFechaFin; }
    public Medico getResidentes() { return oResidentes; }
    public void setResidentes(Medico oResidentes) { this.oResidentes = oResidentes; }
}
