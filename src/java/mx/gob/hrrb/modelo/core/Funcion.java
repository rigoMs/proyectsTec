package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.1
 */
public class Funcion implements Serializable {

    private AccesoDatos oAD;
    private String sClave;
    private String sDescripcion;
    private String sLlamada;
    private Menu oMenu;
    private Icono oIcono;
    private List<Funcion> dependencias;
    private Funcion oInterna;
    
    
    private String sUsuarioFirm;

    public Funcion() {
        sClave = "";
        sDescripcion = "";
        sLlamada = "";
        oIcono = new Icono();
        oMenu = new Menu();
        dependencias = new ArrayList<>();

        Firmado oFirm = new Firmado();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuarioFirm = oFirm.getUsu().getIdUsuario();
        }

    }
    
    public Funcion(String val){
        this.sLlamada =val;
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this.sClave==null || this.sClave.equals("")) {   //completar llave
            throw new Exception("Funcion.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaLlaveFuncion('"+this.sClave+"');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                
                this.setClave((String) vRowTemp.get(0));
                this.setDescripcion((String) vRowTemp.get(1));
                this.setLlamada((String) vRowTemp.get(2));
                Menu oMenuTemp = new Menu();
                oMenuTemp.setClaveMenu(((Double) vRowTemp.get(3)).intValue());
                this.setMenu(oMenuTemp);
                Icono oIconoTemp = new Icono();
                oIconoTemp.setClave( ((Double) vRowTemp.get(4)).intValue()) ;
                oIconoTemp.setNombre( (String) vRowTemp.get(5) );
                this.setIcono(oIconoTemp);
                bRet = true;
            }
        }
        return bRet;
    }
    
    public boolean buscarPermisoInterna(String sCvePerfil) throws Exception{
    boolean bRet=false;
    ArrayList rst = null;
    String sQuery = "";
    int nCant=0;
        if (this.sLlamada == null || this.sLlamada.equals("")) {  
            throw new Exception("Funcion.buscarPermisoInterna: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaPermisoFunInterna('"+this.sLlamada+
                    "', '"+sCvePerfil+"');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                nCant = ((Double) vRowTemp.get(0)).intValue();
                if (nCant>=1)
                    bRet = true;
            }
        }
        return bRet;
    }

    public List <Funcion> buscarTodos() throws Exception {
        List<Funcion> arrRet = null;
        Funcion oFuncion = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosFuncion();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oFuncion = new Funcion();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oFuncion.setClave((String) vRowTemp.get(0));
                oFuncion.setDescripcion((String) vRowTemp.get(1));
                oFuncion.setLlamada((String) vRowTemp.get(2));
                oMenu = new Menu();
                oMenu.setDescripcion((String)vRowTemp.get(3));
                oMenu.setClaveMenu(((Double) vRowTemp.get(4)).intValue());
                oFuncion.setMenu(oMenu);
                Icono oIconoTemp= new Icono();
                oIconoTemp.setNombre((String) vRowTemp.get(5));
                oIconoTemp.setClave(((Double) vRowTemp.get(6)).intValue());
                oFuncion.setIcono(oIconoTemp);
                arrRet.add(oFuncion);
            }

        }else
            System.out.println("no se recibieron datos");
        return arrRet;
    }

    public List<Funcion> convertirEnLista(Funcion[] arrFun) throws Exception {
        List<Funcion> listFunc = new ArrayList<Funcion>();

        listFunc.addAll(Arrays.asList(arrFun));
        return listFunc;
    }
    
    public int removerDelMenu() throws Exception{
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Funcion.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaFuncionDelMenu('" + this.sClave + "' , '" + this.oMenu.getClaveMenu() + "' , '" + this.sUsuarioFirm + "');";
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
    
    public int asignarAMenu(int nClaveMenu) throws Exception{
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Funcion.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaFuncionEnMenu('" + this.sClave + "' , '" + nClaveMenu + "' , '" + this.sUsuarioFirm + "');";
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

    public List<Funcion> buscarTodasPorPerfil(String sIdPerfil) throws Exception {
        List<Funcion> arrRet = null;
        Funcion oFuncion = null;
        ArrayList rst = null;
        String sQuery;
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosFuncionPorPerfil('" + sIdPerfil + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oFuncion = new Funcion();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oFuncion.setClave((String) vRowTemp.get(0));
                oFuncion.setDescripcion((String) vRowTemp.get(1));
                oFuncion.setLlamada((String) vRowTemp.get(2));
                Menu oMenuTemp = new Menu();
                oMenuTemp.setClaveMenu(((Double) vRowTemp.get(3)).intValue());
                oMenuTemp.setDescripcion((String) vRowTemp.get(4));
                oFuncion.setMenu(oMenuTemp);
                Icono oIconoTemp= new Icono();
                oIconoTemp.setClave(((Double) vRowTemp.get(5)).intValue());
                oFuncion.setIcono(oIconoTemp);

                arrRet.add(oFuncion);
            }
        }
        return arrRet;
    }

    public List<Funcion> buscarTodasDispPorPerfil(String sIdPerfil) throws Exception {
        List<Funcion> arrRet = null;
        Funcion oFuncion = null;
        ArrayList rst = null;
        String sQuery;
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosFuncionDispParaElPerfil('" + sIdPerfil + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oFuncion = new Funcion();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oFuncion.setClave((String) vRowTemp.get(0));
                oFuncion.setDescripcion((String) vRowTemp.get(1));
                oFuncion.setLlamada((String) vRowTemp.get(2));
                Menu oMenuTemp = new Menu();
                oMenuTemp.setClaveMenu(((Double) vRowTemp.get(3)).intValue());
                oMenuTemp.setDescripcion((String) vRowTemp.get(4));
                oFuncion.setMenu(oMenuTemp);
                Icono oIconoTemp= new Icono();
                oIconoTemp.setClave(((Double)vRowTemp.get(5)).intValue());

                arrRet.add(oFuncion);
            }
        }
        return arrRet;
    }
    
    public List<Funcion> buscarTodosPorMenu(int nClaveMenu) throws Exception {
        List<Funcion> arrRet = null;
        Funcion oFuncion;
        ArrayList rst = null;
        String sQuery;
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosFuncionPorMenu(" + nClaveMenu + ");";
        
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oFuncion = new Funcion();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oFuncion.setClave((String) vRowTemp.get(0));
                oFuncion.setDescripcion((String) vRowTemp.get(1));
                oFuncion.setLlamada((String) vRowTemp.get(2));
                Menu oMenuTemp = new Menu();
                oMenuTemp.setClaveMenu(((Double) vRowTemp.get(3)).intValue());
                oMenuTemp.setDescripcion((String) vRowTemp.get(4));
                oFuncion.setMenu(oMenuTemp);
                Icono oIconoTemp= new Icono();
                oIconoTemp.setClave(((Double) vRowTemp.get(5)).intValue());
                oFuncion.setIcono(oIconoTemp);
                arrRet.add(oFuncion);
            }
        }
        return arrRet;
    
    }

    public List<Funcion> buscaTodosDispParaElMenu(int nClaveMenu) throws Exception {
        List<Funcion> arrRet = null;
        Funcion oFuncion;
        ArrayList rst = null;
        String sQuery;
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosFuncionDispParaElMenu(" + nClaveMenu + ");";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oFuncion = new Funcion();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oFuncion.setClave((String) vRowTemp.get(0));
                oFuncion.setDescripcion((String) vRowTemp.get(1));
                oFuncion.setLlamada((String) vRowTemp.get(2));
                Menu oMenuTemp = new Menu();
                oMenuTemp.setClaveMenu(((Double) vRowTemp.get(3)).intValue());
                oMenuTemp.setDescripcion((String) vRowTemp.get(4));
                oFuncion.setMenu(oMenuTemp);
                Icono oIconoTemp= new Icono();
                oIconoTemp.setClave(((Double) vRowTemp.get(5)).intValue());
                oFuncion.setIcono(oIconoTemp);
                arrRet.add(oFuncion);
            }
        }
        return arrRet;
    }
    
    public List<Funcion> buscaInternasDisponibles() throws Exception {
        List<Funcion> arrRet = null;
        Funcion oFuncion;
        ArrayList rst = null;
        String sQuery;
        int i = 0;
        sQuery = "SELECT * FROM buscaTodasInternasDisp('" + this.sClave + "');";
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oFuncion = new Funcion();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oFuncion.setClave((String) vRowTemp.get(0));
                oFuncion.setDescripcion((String) vRowTemp.get(1));
                oFuncion.setLlamada((String) vRowTemp.get(2));
                Menu oMenuTemp = new Menu();
                oMenuTemp.setClaveMenu(((Double) vRowTemp.get(3)).intValue());
                oMenuTemp.setDescripcion((String) vRowTemp.get(4));
                oFuncion.setMenu(oMenuTemp);
                Icono oIconoTemp= new Icono();
                oIconoTemp.setClave(((Double) vRowTemp.get(5)).intValue());
                oFuncion.setIcono(oIconoTemp);
                arrRet.add(oFuncion);
            }
        }
        return arrRet;
    }
    
    public List<Funcion> buscaInternasUsadas() throws Exception {
        List<Funcion> arrRet = null;
        Funcion oFuncion;
        ArrayList rst = null;
        String sQuery;
        int i = 0;
        sQuery = "SELECT * FROM buscaInternasDeFuncion('" + this.sClave + "');";
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oFuncion = new Funcion();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oFuncion.setClave((String) vRowTemp.get(0));
                oFuncion.setDescripcion((String) vRowTemp.get(1));
                oFuncion.setLlamada((String) vRowTemp.get(2));
                Menu oMenuTemp = new Menu();
                oMenuTemp.setClaveMenu(((Double) vRowTemp.get(3)).intValue());
                oMenuTemp.setDescripcion((String) vRowTemp.get(4));
                oFuncion.setMenu(oMenuTemp);
                Icono oIconoTemp= new Icono();
                oIconoTemp.setClave(((Double) vRowTemp.get(5)).intValue());
                oFuncion.setIcono(oIconoTemp);
                arrRet.add(oFuncion);
            }
        }
        return arrRet;
    }
    
    public int insertarInterna() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery;
        if (this == null) {   //completar llave
            throw new Exception("Funcion.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaDependenciaInterna('" 
                    + this.sClave + "' , '"
                    +this.oInterna.getClave()+ "' , '" 
                    + this.sUsuarioFirm + "');";
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
    
    public int eliminarInterna() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.sClave == null || this.sClave.equals("")) {   //completar llave
            throw new Exception("Funcion.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaDependenciaInterna('" 
                    + this.sClave + "' , '"
                    +this.oInterna.getClave()+ "' , '" 
                    + this.sUsuarioFirm + "');";
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

    public int insertar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery;
        if (this == null) {   //completar llave
            throw new Exception("Funcion.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaFuncion('" + this.sClave + "' , '"
                    + this.sDescripcion + "' , '"
                    + this.sLlamada + "' , "
                    + this.oMenu.getClaveMenu() + " ,'"
                    + this.sUsuarioFirm + "',"
                    + this.oIcono.getClave()+");";
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
        String sQuery;
        if (this.sClave == null || this.sClave.equals("") || this.sDescripcion==null || this.sDescripcion.equals("") ||
                this.sLlamada==null || this.sLlamada.equals("") || this.oMenu==null || this.sUsuarioFirm == null || 
                this.sUsuarioFirm.equals("") || this.oIcono==null) {   //completar llave
            throw new Exception("Funcion.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaFuncion('" + this.sClave + "' , '"
                    + this.sDescripcion + "' , '"
                    + this.sLlamada + "' , "
                    + this.oMenu.getClaveMenu() + " ,'"
                    + this.sUsuarioFirm + "',"
                    + this.oIcono.getClave()+");";

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
            throw new Exception("Funcion.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaFuncion('" + this.sClave + "' , '" + this.sUsuarioFirm + "');";

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

    public int asignarAPerfil(String sCvePerfil) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.sClave == null || this.sClave.equals("")) {   //completar llave
            throw new Exception("Funcion.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaFuncionEnPerfil('" + this.sClave + "' , '" + sCvePerfil + "' , '" + this.sUsuarioFirm + "');";
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

    public int removerDelPerfil(String sCvePerfil) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.sClave == null || this.sClave.equals("")) {   //completar llave
            throw new Exception("Funcion.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaFuncionDelPerfil('" + this.sClave + "' , '" + sCvePerfil + "' , '" + this.sUsuarioFirm + "');";
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
    
    public String getClave() {
        return sClave;
    }

    public void setClave(String valor) {
        sClave = valor;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion(String valor) {
        sDescripcion = valor;
    }

    public String getLlamada() {
        return sLlamada;
    }

    public void setLlamada(String valor) {
        sLlamada = valor;
    }

    /**
     * @return the oMenu
     */
    public Menu getMenu() {
        return oMenu;
    }

    /**
     * @param oMenu the oMenu to set
     */
    public void setMenu(Menu oMenu) {
        this.oMenu = oMenu;
    }

    /**
     * @return the oIcono
     */
    public Icono getIcono() {
        return oIcono;
    }

    /**
     * @param oIcono the oIcono to set
     */
    public void setIcono(Icono oIcono) {
        this.oIcono = oIcono;
    }

    /**
     * @return the oInterna
     */
    public Funcion getInterna() {
        return oInterna;
    }

    /**
     * @param oInterna the oInterna to set
     */
    public void setInterna(Funcion oInterna) {
        this.oInterna = oInterna;
    }


}
