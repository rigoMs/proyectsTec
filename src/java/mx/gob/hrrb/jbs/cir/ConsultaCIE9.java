/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
/**
 *
 * @author Quintero
 */
@ManagedBean (name="consultaCIE9")
@ViewScoped
public class ConsultaCIE9 implements Serializable{
    private ProcedimientoCIE9 cie9=new ProcedimientoCIE9();
    private Firmado oFirm;
    private ProcedimientoCIE9[] arrCIE9 = null;
    //private boolean req=true;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    
    /*public ConsultaCIE9(){
        cie9 = new ProcedimientoCIE9();
        oFirm = new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
    }*/
    
    public ProcedimientoCIE9[] getListaProcedimiento() throws Exception{
        try{
            arrCIE9 = (ProcedimientoCIE9[])cie9.buscarTodos();
        }catch(Exception e){
            e.printStackTrace();
        }
        return arrCIE9;
    }
    
    //Lista de los Procedimientos de CIE9
   
}
