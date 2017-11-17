/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Javi
 */
public class Icono implements Serializable {

    private AccesoDatos oAD;
    private int nClave;
    private String sNombre;

    public Icono() {
        nClave = 0;
        sNombre = "";

    }

    public List<Icono> buscarTodos() throws Exception {
        List<Icono> arrRet = null;
        Icono oIcono = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosIcono();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oIcono = new Icono();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oIcono.setClave(((Double) vRowTemp.get(0)).intValue());
                oIcono.setIcono((String) vRowTemp.get(1));
                arrRet.add(oIcono);
            }

        }
        return arrRet;
    }
    
    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this.nClave<1) {
            throw new Exception("Funcion.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaLlaveIcono("+this.nClave+");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                
                this.setClave(((Double)vRowTemp.get(0)).intValue());
                this.setIcono((String)vRowTemp.get(1));
                
                bRet = true;
            }
        }
        return bRet;
    }

    /**
     * @return the nClave
     */
    public int getClave() {
        return nClave;
    }

    /**
     * @param nClave the nClave to set
     */
    public void setClave(int nClaveMenu) {
        this.nClave = nClaveMenu;
    }

    /**
     * @return the sIcono
     */
    public String getIcono() {
        return "ui-icon-" + sNombre;
    }

    /**
     * @param sIcono the sIcono to set
     */
    public void setIcono(String sIcono) {
        if (sIcono.contains("ui-icon-")) {
            this.sNombre = sIcono.substring(8, sIcono.length() - 1);
        } else {
            this.sNombre = sIcono;
        }
    }

    /**
     * @return the sNombre
     */
    public String getNombre() {
        return "ui-icon-" + sNombre;
    }

    /**
     * @param sNombre the sNombre to set
     */
    public void setNombre(String sNombre) {
            this.sNombre = sNombre;
    }
}
