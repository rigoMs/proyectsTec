package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 * Representa la entidad Menu (armado de información para el menú visual)
 * @author Javi
 * Última Modificación: Nava
 */
public class Menu implements Serializable{
    private AccesoDatos oAD;
    private int nClaveMenu;
    private String sDescripcion;
    private Menu oPadre;
    private Icono oIcono;
    private String sUsuarioFirm;

    public Menu() {
        nClaveMenu = 0;
        sDescripcion = "";
        oIcono= new Icono();

        Firmado oFirm = new Firmado();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuarioFirm = oFirm.getUsu().getIdUsuario();
        }
    }

    /**
     * @return the nClaveMenu
     */
    public int getClaveMenu() {
        return nClaveMenu;
    }

    /**
     * @param nClaveMenu the nClaveMenu to set
     */
    public void setClaveMenu(int nClaveMenu) {
        this.nClaveMenu = nClaveMenu;
    }

    /**
     * @return the sDescripcion
     */
    public String getDescripcion() {
        return sDescripcion;
    }

    /**
     * @param sDescripcion the sDescripcion to set
     */
    public void setDescripcion(String sDescripcion) {
        this.sDescripcion = sDescripcion;
    }

    /**
     * @return the oPadre
     */
    public Menu getPadre() {
        return oPadre;
    }

    /**
     * @param oPadre the oPadre to set
     */
    public void setPadre(Menu oPadre) {
        this.oPadre = oPadre;
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
    
    @Override
    public boolean equals(Object obj){
    boolean bRet=false;
        if (obj != null){
            if (this == obj)
                bRet = true;
            else{
                if (Objects.equals(this.getClass(), obj.getClass())){
                    Menu otro = (Menu)obj;
                    if (this.nClaveMenu==otro.nClaveMenu)
                        bRet = true;
                }
            }
        }
        return bRet;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.nClaveMenu;
        return hash;
    }
    
    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this.nClaveMenu == 0) {   //completar llave
            return bRet;
        } else {
            sQuery = "SELECT * FROM buscaLlaveMenu('"+this.nClaveMenu+"');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                this.nClaveMenu=((Double) vRowTemp.get(0)).intValue();
                this.sDescripcion=(String)vRowTemp.get(1);
                Menu oMenu = new Menu();
                oMenu.setClaveMenu(((Double) vRowTemp.get(2)).intValue());
                this.setPadre(oMenu);
                Icono oIconoTemp = new Icono();
                oIconoTemp.setClave(((Double) vRowTemp.get(3)).intValue());
                oIconoTemp.setNombre((String)vRowTemp.get(4));
                bRet = true;
            }
        }
        return bRet;
    }
    
    public List<Menu> buscarTodos() throws Exception {
        List<Menu> arrRet = null;
        Menu oMenu = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosMenu();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                oMenu = new Menu();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMenu.setClaveMenu(((Double)vRowTemp.get(0)).intValue());
                oMenu.setDescripcion((String)vRowTemp.get(1));
                Menu oMenuPadre= new Menu();
                if (((Double)vRowTemp.get(2)).intValue()>0){
                    oMenuPadre.setClaveMenu(((Double)vRowTemp.get(2)).intValue());
                    oMenuPadre.setDescripcion((String)vRowTemp.get(3));
                }
                oMenu.setPadre(oMenuPadre);
                Icono oIcono1=new Icono();
                oIcono1.setClave(((Double)vRowTemp.get(4)).intValue());
                oIcono1.setNombre((String)vRowTemp.get(5));
                oMenu.setIcono(oIcono1);
                arrRet.add(oMenu);
            }

        }
        return arrRet;
    }
    
    public int insertar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery;
        if (this.sDescripcion==null || this.sDescripcion.equals("")||
            this.oPadre == null || this.oIcono==null) {   //completar llave
            throw new Exception("Menu.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaMenu('"
                    + this.sDescripcion + "' , "
                    + this.oPadre.getClaveMenu() + " , "
                    + this.oIcono.getClave()+" ,'"
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
    
    public int modificar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery;
        if (this.nClaveMenu == 0) {   //completar llave
            throw new Exception("Menu.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaMenu('" 
                    + this.nClaveMenu + "' , '"
                    + this.sDescripcion + "' , "
                    + this.oPadre.getClaveMenu() + " , "
                    + this.oIcono.getClave()+" ,'"
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

    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.nClaveMenu == 0 ) {  
            throw new Exception("Menu.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaMenu('" + this.nClaveMenu + "' , '" + 
                    this.sUsuarioFirm + "');";
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
    
}

