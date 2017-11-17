/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;

/**
 *
 * @author Quintero
 */
@ManagedBean (name = "oProgramacionQx")
@ViewScoped
public class ConsultaProgramacionQx implements Serializable{
    private ProcedimientosRealizados oProcedimientos = new ProcedimientosRealizados();
    private String sVisibilidad="hidden";
    private ArrayList<ProcedimientosRealizados> arrProgramacion = null;

    public void buscaProgramacion(){
        sVisibilidad = "Visible";
        try{
            arrProgramacion = new ArrayList<ProcedimientosRealizados>(Arrays.asList(oProcedimientos.buscaProgramacion()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; }
    public String getVisibilidad() { return sVisibilidad; }
    public void setVisibilidad(String sVisibilidad) { this.sVisibilidad = sVisibilidad; }
    public ArrayList<ProcedimientosRealizados> getListaProgramacion() { return arrProgramacion; }
}
