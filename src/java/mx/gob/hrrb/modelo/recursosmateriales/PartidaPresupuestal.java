/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.recursosmateriales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author DavidH
 */
public class PartidaPresupuestal implements Serializable {
    
    private int nCvePart;/*Clave de la partida*/
    private String sDescPart;/*descripcion de la partida*/
    
    /****************************************/
    private Usuario oIdUsuario;
    private HttpServletRequest httpServletRequest;
    private Firmado oFirm;
    private String sUsuario;
    private List<PartidaPresupuestal> listPartPres;
    private AccesoDatos oAD;
    
    
    public PartidaPresupuestal(){
        //oFirm = new Firmado();
        //oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            //sUsuario=oFirm.getUsu().getIdUsuario();
        
        sUsuario = "TSOCIAL153";
    }
    
    public void finalize() throws Throwable {
        
    }

    
    /*  ***********Busca todas las partidas presupuestales*********** */
    public PartidaPresupuestal[] buscaTodosPartidas() throws Exception{
        PartidaPresupuestal arrRet[]=null, oPartidaPresupuestal=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
            sQuery = "SELECT * FROM buscaTodasPartidas();";
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null) {
                arrRet = new PartidaPresupuestal[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                }
            }
            return arrRet;
    }
    
    
    
    /* ****************Busca todas partidas por area**************** */
    public PartidaPresupuestal[] buscaTodosPorArea() throws Exception{
        PartidaPresupuestal arrRet[]=null, oPartidaPresupuestal= null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
            sQuery = "SELECT * FROM buscaTodasPartidasPorArea;";
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null) {
                arrRet = new PartidaPresupuestal[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                }
            }
            return arrRet;
    }

    
    
    /* **************Busca todas las partidas por area funcional****** */
    public PartidaPresupuestal[] buscaTodas( ) throws Exception{
        PartidaPresupuestal arrRet[]=null, oPartida= null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        
        
        sQuery="select * from buscapartidasporarea('"+sUsuario+"');";
        oAD=new AccesoDatos();
        if (oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            System.out.println(rst);
            oAD.desconectar();
        }
        if (rst != null && rst.size() > 0) {
            
            arrRet = new PartidaPresupuestal[rst.size()];
            for (i=0; i < rst.size(); i++) {
                oPartida= new PartidaPresupuestal();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oPartida.setCvePart(((Double)vRowTemp.get(0)).intValue());
                oPartida.setDescPart((String)vRowTemp.get(1));
                arrRet[i]=oPartida;
            }
        } 
        return arrRet;
       
    }
    
    public int getCvePart() {
        return nCvePart;
    }
    public void setCvePart(int nCvePart) {
        this.nCvePart = nCvePart;
    }
    public String getDescPart() {
        return sDescPart;
    }
    public void setDescPart(String sDescPart) {
        this.sDescPart = sDescPart;
    }

    
}
