package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class Perfil implements Serializable {

    private AccesoDatos oAD;
    private ArrayList<Funcion> arrPermisos;
    private String sClave;
    private String sDescripcion;
    private Firmado oFirm;
    private String sUsuarioFirm;
    public static final String CLAVE_CAJERO = "CAJERO";
    public static final String CLAVE_JEFE_CAJA_GRAL = "CAJJEFE";

    //JMHG
    public static final String CLAVE_RECEP_LABORATORIO = "LABRECEPP";
    //----

    public Perfil() {
        Firmado oFirm = new Firmado();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuarioFirm = oFirm.getUsu().getIdUsuario();
        }
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (getClave() == null || getClave().equals("")) {   //completar llave
            throw new Exception("Perfil.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaLlavePerfil('" + this.sClave + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                setClave((String) vRowTemp.get(0));
                setDescripcion((String) vRowTemp.get(1));
                bRet = true;
            }
        }
        return bRet;
    }

    //************************************************************************
    public boolean buscaPerfilPorUsuario() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Perfil.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaPerfilPorUsuario('" + sUsuarioFirm + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }

    //**********************************************************************
    public boolean buscaPerfilHosp() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Perfil.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaperfilhosp('" + sUsuarioFirm + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }

    //**********************************************************************
    public boolean buscaPerfilHospEst() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Perfil.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaperfilhospest('" + sUsuarioFirm + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }

    //**********************************************************************        
    public boolean buscaPerfilUrgs() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Perfil.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaPerfilUrgs('" + sUsuarioFirm + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }

    //**********************************************************************
    public boolean buscaPerfilCE() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Perfil.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaPerfilCE('" + sUsuarioFirm + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }

    //**********************************************************************
    public List<Perfil> buscarTodosLosPerfiles() throws Exception {
        Perfil oPer = null;
        ArrayList rst = null;
        ArrayList<Perfil> arrRet = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        int citaux = 0;

        sQuery = "SELECT * FROM buscarTodosPerfiles();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oPer = new Perfil();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oPer.setClave((String) vRowTemp.get(0));
                oPer.setDescripcion((String) vRowTemp.get(1));
                arrRet.add(oPer);
            }
        }

        return arrRet;
    }

    //**********************************************************************
    public boolean buscaPerfilRoot() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Perfil.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaPerfilRoot('" + sUsuarioFirm + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }

    //**********************************************************************
    public Perfil[] buscarTodos() throws Exception {
        Perfil arrRet[] = null, oPerfil = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        if (this == null) {
            throw new Exception("Perfil.buscarTodos: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaTodosPerfil();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null) {
                arrRet = new Perfil[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                }
            }
            return arrRet;
        }
    }

    public int insertar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.sClave == null || this.sClave.equals("")
                || this.sDescripcion == null || this.sDescripcion.equals("")) {
            throw new Exception("Perfil.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaPerfil('" + sClave + "' , '" + sDescripcion + "' , '" + sUsuarioFirm + "');";  //agregar susuario
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    public int modificar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.sClave == null || this.sClave.equals("")
                || this.sDescripcion == null || this.sDescripcion.equals("")) {
            throw new Exception("Perfil.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaPerfil('" + sClave + "' , '" + sDescripcion + "' , '" + sUsuarioFirm + "');";
            System.out.println("query " + sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.sClave == null || this.sClave.equals("")) {   //completar llave
            throw new Exception("Perfil.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaPerfil( '" + this.sClave + "' , '" + this.sUsuarioFirm + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    public ArrayList<Funcion> getPermisos() {
        return arrPermisos;
    }

    public void setPermisos(ArrayList<Funcion> valor) {
        arrPermisos = valor;
    }

    public String getClave() {
        return sClave;
    }

    public void setClave(String valor) {
        sClave = valor.toUpperCase();
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion(String valor) {
        sDescripcion = valor.toUpperCase();
    }

}
