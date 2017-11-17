package mx.gob.hrrb.jbs.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Funcion;
import mx.gob.hrrb.modelo.core.Icono;
import mx.gob.hrrb.modelo.core.Menu;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.model.DualListModel;

/**
 *
 * @author juan
 */
@ManagedBean(name = "adminMenu")
@ViewScoped
public class AdminMenu implements Serializable {

    private Funcion oFuncion;
    private Menu oMenu;

    private String nombre;

    private DualListModel<Funcion> listaFunciones;
    private List<Funcion> funcionesSource;
    private List<Funcion> funcionesTarget;

    private String tituloVentana;
    private boolean nuevoMenu;
    private boolean mostrarVentanaEdicion;
    private boolean renderEliminar;

    /**
     * Creates a new instance of AdminMenues
     */
    public void guardarMenu() {
        boolean existeElMenu = false;
        int menuModificado = 0;
        //Menu oMenuResp = (Menu) SerializationUtils.clone(oMenu);
        Menu oMenuResp= new Menu();
        oMenuResp.setClaveMenu(oMenu.getClaveMenu());
        oMenuResp.setDescripcion(oMenu.getDescripcion());
        oMenuResp.setIcono(oMenu.getIcono());
        oMenuResp.setPadre(oMenu.getPadre());
        
        if (oMenu.getDescripcion() != null && !oMenu.getDescripcion().equals("")) {

            String sErr = "";

            try {
                existeElMenu = getMenu().buscar();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (existeElMenu) {
                //los vuelvo a setear porque .buscar() 
                //reescribe los datos ya existentes en ese registro
                oMenu = oMenuResp;

                try {
                    menuModificado = getMenu().modificar();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (menuModificado == 0) {
                    sErr = "error modificando el menu ";
                }

            } else {
                try {
                    getMenu().insertar();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (modificarMenu() != 0) {
                sErr = "error con la actualización de las funciones del menu ";
            }

            if (sErr.equals("")) {
                nombre = "";

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Operación exitosa", "Menu " + getMenu().getDescripcion() + " guardado correctamente"));
            } else {
                System.out.println(sErr);
            }

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Faltan datos", "Falta el nombre del menu"));
        }
    }

    public int modificarMenu() {

        int res = 0;

        if (listaFunciones.getTarget() != null) {
            if (oMenu.getClaveMenu() != 0) {
                List<Funcion> escogidas = listaFunciones.getTarget();

                for (Funcion f : funcionesTarget) {
                    //si la nueva lista de funcion no contiene la funcion de la lista original f, esta se borra
                    if (!listaFunciones.getTarget().contains(f)) {
                        //System.out.println("borrar funcion " + f.getDescripcion());
                        try {
                            f.setMenu(oMenu);
                            if (f.removerDelMenu() == 0) {
                                res = 1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                for (Funcion f : escogidas) {
                    //si el original no contenia la funcion f de las escogidas, esta se guarda
                    if (!funcionesTarget.contains(f)) {
                        //System.out.println("guardar funcion " + f.getDescripcion());
                        try {
                            if (f.asignarAMenu(oMenu.getClaveMenu()) == 0) {
                                res = 1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return res;
    }

    public DualListModel<Funcion> obtenerFunciones() {

        try {
            funcionesTarget = oFuncion.buscarTodosPorMenu(oMenu.getClaveMenu());
            funcionesSource = oFuncion.buscaTodosDispParaElMenu(oMenu.getClaveMenu());
        } catch (Exception e) {
            System.out.println("error en la llamada a base de datos");
        }
        listaFunciones = new DualListModel<>(funcionesSource, funcionesTarget);

        return listaFunciones;
    }

    public List<String> completaMenu(String s) {
        List<Menu> menues;
        List<String> sMenues = new ArrayList<>();
        try {
            menues = getMenu().buscarTodos();
            if (s.trim().equals("")) {
                for (Menu p : menues) {
                    sMenues.add(p.getDescripcion());
                }
                return sMenues;
            }

            for (Menu m : menues) {
                if (m.getDescripcion().contains(s) || m.getDescripcion().toUpperCase().contains(s.toUpperCase())) {
                    sMenues.add(m.getDescripcion());
                }
            }
            return sMenues;
        } catch (Exception e) {
            return null;
        }
    }

    public Menu[] getListaMenus() {
        List<Menu> menus, sMenues = new ArrayList<>();

        try {
            menus = getMenu().buscarTodos();

            if (nombre.trim().equals("")) {
                Menu[] retMenues = new Menu[menus.size()];
                return menus.toArray(retMenues);
            }

            for (Menu m : menus) {
                if (m.getDescripcion().contains(nombre)
                        || m.getDescripcion().toUpperCase().contains(nombre.toUpperCase())) {
                    sMenues.add(m);
                }
            }

        } catch (Exception e) {
            System.err.printf("error pinta");
        }

        Menu[] retMenues = new Menu[sMenues.size()];
        return sMenues.toArray(retMenues);

    }

    public List<Menu> getTodosMenus() {
        Menu oMenu = new Menu();
        List<Menu> todosMenus = new ArrayList<>();
        try {
            todosMenus = oMenu.buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return todosMenus;
    }

    public List<Icono> getTodosIconos() {
        Icono oIcono = new Icono();
        List<Icono> todosIconos = new ArrayList<>();
        try {
            todosIconos = oIcono.buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todosIconos;
    }

    public void eliminarMenu() throws Exception {
        getMenu().setDescripcion(nombre);
        getMenu().setClaveMenu(oMenu.getClaveMenu());

        int res = oMenu.eliminar();

        if (res != 0) {
            nombre = "";
            oMenu.setClaveMenu(0);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Operación exitosa", "Menú "
                    + oMenu.getDescripcion() + " eliminado"));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Eliminación fallida", "El Menú "
                    + oMenu.getDescripcion() + " tiene submenus, elimínelos o cámbielos de lugar"));
        }
    }

    public AdminMenu() {
        oMenu = new Menu();
        oFuncion = new Funcion();
        nombre = "";

        mostrarVentanaEdicion = false;
        renderEliminar = false;
        tituloVentana = "";

        funcionesTarget = new ArrayList<>();
        funcionesSource = new ArrayList<>();

        listaFunciones = new DualListModel<>(funcionesSource, funcionesTarget);
    }

    public void modificarMenu(Menu oMen) {
        oMenu = oMen;
        preparaDialogoEdicion();
        nuevoMenu = false;
    }
    
    public void preparaEliminar(Menu oMenuTemp){
        oMenu=oMenuTemp;
        renderEliminar=true;
    }

    public void nuevoMenu() {
        oMenu = new Menu();
        oMenu.setPadre(new Menu());
        preparaDialogoEdicion();
        nuevoMenu = true;
    }

    public void preparaDialogoEdicion() {
        nombre = "";
        mostrarVentanaEdicion = true;
        listaFunciones = obtenerFunciones();
    }

    /**
     * @param menu the funcion to set
     */
    public void setListaFunciones(DualListModel<Funcion> menu) {
        this.listaFunciones = menu;
    }

    /**
     * @return the funcion
     */
    public DualListModel<Funcion> getListaFunciones() {
        return listaFunciones;
    }

    /**
     * @return the tituloVentana
     */
    public String getTituloVentana() {
        return tituloVentana;
    }

    /**
     * @param tituloVentana the tituloVentana to set
     */
    public void setTituloVentana(String tituloVentana) {
        this.tituloVentana = tituloVentana;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        System.out.println("set nombre " + nombre);
        this.nombre = nombre;
    }

    /**
     * @return the funcion
     */
    public Menu getMenu() {
        return oMenu;
    }

    /**
     * @param oMenu the oMenu to set
     */
    public void setMenu(Menu oMenu) {
        System.out.println("set Menu " + oMenu.getDescripcion());
        this.oMenu = oMenu;
    }

    /**
     * @return the mostrarVentanaEdicion
     */
    public boolean getMostrarVentanaEdicion() {
        return mostrarVentanaEdicion;
    }

    /**
     * @param mostrarVentanaEdicion the mostrarVentanaEdicion to set
     */
    public void setMostrarVentanaEdicion(boolean mostrarVentanaEdicion) {
        this.mostrarVentanaEdicion = mostrarVentanaEdicion;
    }

    /**
     * @return the renderEliminar
     */
    public boolean isRenderEliminar() {
        return renderEliminar;
    }

    /**
     * @param renderEliminar the renderEliminar to set
     */
    public void setRenderEliminar(boolean renderEliminar) {
        this.renderEliminar = renderEliminar;
    }

}
