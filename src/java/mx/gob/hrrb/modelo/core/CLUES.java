/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
aylin 1
 */
package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

public class CLUES implements Serializable 
{

    private String sCveClues;
    private String sDescripcion;

    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public CLUES() {
        sCveClues = "";
        sDescripcion = "";
    }

    private boolean buscarUsuario() //EDITADO: 16/12/16 (JMHG)
    {
        boolean bRet = false;
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sIdUsuario = oFirm.getUsu().getIdUsuario();
            bRet = (sIdUsuario != null && !sIdUsuario.isEmpty());
        }
        return bRet;
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";

        sQuery = "SELECT * FROM buscaLlaveClue( " + "'" + getCveClues() + "'::character );";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() == 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            setCveClues(((String) vRowTemp.get(0)));
            setDescripcion((String) vRowTemp.get(1));

        }

        return bRet;
    }

    public CLUES[] buscarTodos() throws Exception 
    {
        CLUES arrRet[] = null, oClues = null;
        ArrayList rst = null;
        String sQuery = "";
       sQuery = "SELECT * FROM buscaTodosClues();";
      // sQuery = "SELECT * FROM buscaTodosTipoMedico();";
        oAD = new AccesoDatos();
        System.out.println(sQuery);
        if (oAD.conectar()) 
        {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
             System.out.println("Esto es rst" + rst);
        }
      if (rst != null && rst.size() > 0) 
        {
            arrRet = new CLUES[rst.size()];
            ArrayList vRowTemp = null;
           System.out.println(rst.size());
            for (int i = 0; i < rst.size(); i++) 
            {
             vRowTemp = (ArrayList) rst.get(i);
                oClues = new CLUES();
                oClues.setCveClues((String) vRowTemp.get(0));
                oClues.setDescripcion((String) vRowTemp.get(1));
              arrRet[i] = oClues;
            }        }
        System.out.println(rst.size());
        return arrRet;    }
    
    
    
    
    

    public int insertar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";
        if (getDescripcion().isEmpty()) {
            throw new Exception("Clues.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaClues( " + "'ROOT'::character varying, " + "'"
                    + getCveClues() + "'::character varying(11), " + "'" + getDescripcion() + "'::character varying);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                nRet = ((Double) vRowTemp.get(0)).intValue();
            }
        }
        return nRet;
    }

    public int modificar() throws Exception {
        int nRet = 0;
        ArrayList rst = null;
        String sQuery = "";

        sQuery = "SELECT * FROM modificaClues( " + "'" + getCveClues() + "'::character varying(11), " + "'" + getDescripcion() + "'::character varying);";

        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            System.out.println(sQuery);
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() == 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            nRet = ((Double) vRowTemp.get(0)).intValue();
        }

        return nRet;
    }

    public String getCveClues() {
        return sCveClues;
    }

    public void setCveClues(String sCveClues) {
        this.sCveClues = sCveClues;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion(String sDescripcion) {
        sDescripcion = sDescripcion.toUpperCase();
        this.sDescripcion = sDescripcion;
    }

}
