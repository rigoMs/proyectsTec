/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author DavidH
 */
public class RequisicionInterna extends Requisicion implements Serializable {
    
    private String sPrioridad;
    private RequisicionInterna arrRequisicion[];
    
    
    /***************Atributos para la partida presupuestal*******/
    private Usuario oIdUsuario;
    private HttpServletRequest httpServletRequest;
    private Firmado oFirm;
    private String sUsuario;
    private List<PartidaPresupuestal> listPartPres;
    private PartidaPresupuestal oPartidap;
    private AccesoDatos oAD;

    
    public RequisicionInterna(){
        sUsuario = "TSOCIAL153";
    }
    
    public void finalize() throws Throwable {
        
    }
    
    /*********Metodo de busqueda de partidas por area*****/
    /*public RequisicionInterna[] buscapartidasporarea() throws Exception{
        RequisicionInterna arrRet[]=null;
        PartidaPresupuestal oPartidap = null;
        
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        
        sQuery = "select * from buscapartidasporarea('"+sUsuario+"')";
        oAD=new AccesoDatos();
        if (oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() > 0) {
            arrRet = new RequisicionInterna[rst.size()];
            for (i=0; i < rst.size(); i++) {
                oPartidap = new PartidaPresupuestal();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oPartidap.getCvePart();
                oPartidap.getDescPart();
                arrRet[i]=oPartidap;
                
            }
        }
        return arrRet;
    }*/
    
   
    
    
    /***********Metodos de set y get **************/
    public String getPrioridad() {
        return sPrioridad;
    }
    public void setPrioridad(String sPrioridad) {
        this.sPrioridad = sPrioridad;
    }
}
