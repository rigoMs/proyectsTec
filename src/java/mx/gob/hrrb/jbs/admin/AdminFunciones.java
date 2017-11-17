/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.primefaces.model.DualListModel;

/**
 *
 * @author juan
 */
@ManagedBean(name = "adminFunciones")
@ViewScoped
public class AdminFunciones implements Serializable {

    private Funcion oFuncion;
    private String nombre;

    private String tituloVentana;
    private DualListModel<Funcion> Internas;
    private boolean noModificar;
    private boolean nuevaFuncion;
    private boolean renderEdicion;
    private boolean renderEliminar;
    private List<Funcion> funcionesTarget;
    private List<Funcion> funcionesSource;

    /**
     * Creates a new instance of AdminFunciones
     *
     */
    public AdminFunciones() {
        oFuncion = new Funcion();

        nuevaFuncion = true;
        nombre = "";
        renderEdicion = false;
        renderEliminar = false;
        
        
        funcionesTarget = new ArrayList<>();
        funcionesSource = new ArrayList<>();

        Internas = new DualListModel<>(funcionesSource, funcionesTarget);

    }

    public List<String> completaFuncion(String s) {
        List<Funcion> funciones = new ArrayList<>();
        List<String> sFunciones = new ArrayList<>();
        try {
            funciones = oFuncion.buscarTodos();
            if (s.trim().equals("")) {
                for (Funcion p : funciones) {
                    sFunciones.add(p.getDescripcion());
                }
                return sFunciones;
            }

            for (Funcion m : funciones) {
                if (m.getDescripcion().contains(s) || m.getDescripcion().toUpperCase().contains(s.toUpperCase())) {
                    sFunciones.add(m.getDescripcion());
                }
            }
            return sFunciones;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Funcion[] getListaFunciones() {
        List<Funcion> todasLasFunciones, sFunciones = new ArrayList<>();
        Funcion func = new Funcion();

        try {
            todasLasFunciones = func.buscarTodos();

            sFunciones = new ArrayList<>();

            if (nombre.trim().equals("")) {
                Funcion[] retFunciones = new Funcion[todasLasFunciones.size()];
                return todasLasFunciones.toArray(retFunciones);
            }

            for (Funcion f : todasLasFunciones) {
                if (f.getDescripcion().contains(nombre) || f.getDescripcion().toUpperCase().contains(nombre.toUpperCase())) {
                    sFunciones.add(f);
                }
            }
            Funcion[] retFunciones = new Funcion[sFunciones.size()];
            return sFunciones.toArray(retFunciones);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Menu> getTodosMenus() {
        Menu oMenu = new Menu();
        List<Menu> todosMenus = new ArrayList<>();
        try {
            todosMenus = oMenu.buscarTodos();
        } catch (Exception e) {
            System.out.println(e);
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

    public void guardarFuncion() {
        boolean existeFuncion = false;
        int modificado = 0;
        String sValidacion = "";
        String estado="";
        //Funcion oFuncTemp=(Funcion) SerializationUtils.clone(oFuncion);
        Funcion oFuncTemp= new Funcion();
        oFuncTemp.setClave(oFuncion.getClave());
        oFuncTemp.setDescripcion(oFuncion.getDescripcion());
        oFuncTemp.setIcono(oFuncion.getIcono());
        oFuncTemp.setInterna(oFuncion.getInterna());
        oFuncTemp.setLlamada(oFuncion.getLlamada());
        oFuncTemp.setMenu(oFuncion.getMenu());
        
        if (oFuncion.getDescripcion() != null && !oFuncion.getDescripcion().equals("")) {
            if (oFuncion.getClave() != null && !oFuncion.getClave().equals("")) {
                if (oFuncion.getLlamada() != null && !oFuncion.getLlamada().equals("")) {
                    if (oFuncion.getMenu().getClaveMenu() != 0) {

                        String sErr = "";
                        
                        try {
                            existeFuncion = oFuncion.buscar();

                            //los vuelvo a setear porque .buscar() 
                            //reescribe los datos ya existentes en ese registro
                            oFuncion=oFuncTemp;

                        } catch (Exception e) {
                            sErr = "Error en la consulta";
                        }

                        if (existeFuncion && !nuevaFuncion) {

                            try {
                                modificado = oFuncion.modificar();
                            } catch (Exception e) {
                                sErr = "error enviando el update";
                            }

                            if (modificado == 0) {
                                sErr = "error modificando la funcion";
                            }else
                                estado="modificada exitosamente";

                        } else {

                            if (nuevaFuncion && !existeFuncion) {
                                try {
                                    int rst = oFuncion.insertar();
                                    if (rst==1) {
                                        estado="creada exitosamente";
                                    }
                                } catch (Exception e) {
                                    sErr = "Error insertando";
                                }
                            } else {
                                sErr = "Ya existe una función con esa clave o es interna";
                                oFuncion.setDescripcion("");
                            }

                        }
                        
                        
                        try {
                            if(!modificarInternas())
                               sErr="error insertando internas";
                        } catch (Exception e) {
                        }

                        if (sErr.equals("")) {
                            oFuncion.setDescripcion("");

                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("Operación exitosa", "Funcion " + oFuncion.getDescripcion() + " "+estado));
                        } else {
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("Operación fallida", sErr));
                        }
                    } else {
                        sValidacion = "Falta el menú padre de la función";
                    }
                } else {
                    sValidacion = "Falta la ruta de la función";
                }
            } else {
                sValidacion = "Falta la clave de la función";
            }
        } else {
            sValidacion = "Falta el nombre de la función";
        }

        if (!sValidacion.equals("")) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Faltan datos", sValidacion));
        }

    }
    
    public boolean modificarInternas() {

        boolean res = true;
        if (Internas.getTarget() != null) {
            List<Funcion> escogidas = Internas.getTarget();

            for (Funcion f : funcionesTarget) {
                if (!Internas.getTarget().contains(f)) {
                    oFuncion.setInterna(f);
                    try {
                        System.out.println("se va a eliminar");
                        if ( oFuncion.eliminarInterna() == 0 )
                            res = false;
                    } catch (Exception e) {
                    }
                }
            }

            for (Funcion f : escogidas) {
                if (!funcionesTarget.contains(f)) {
                    oFuncion.setInterna(f);
                    try {
                        System.out.println("se va a insertar");
                        if ( oFuncion.insertarInterna()== 0) 
                            res = false;
                    } catch (Exception e) {
                    }
                }
            }

        }
        return res;
    }
    
    public void nuevaFuncion(){
        oFuncion = new Funcion();
        setRenderEdicion(true);
        Internas=buscaInternas();
        nuevaFuncion=true;
        noModificar=false;
    }

    public void preparaModificar(Funcion oFuncTemp) {
        oFuncion = oFuncTemp;
        setRenderEdicion(true);
        Internas=buscaInternas();
        nuevaFuncion=false;
        noModificar=true;
    }

    public void preparaEliminar(Funcion oFuncTemp) {
        oFuncion = oFuncTemp;
        setRenderEliminar(true);
    }

    public void eliminarFuncion() {

        try {
            if (oFuncion.eliminar() != 0) {
                oFuncion.setDescripcion("");

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Operación exitosa", "Funcion " + oFuncion.getDescripcion() + " eliminada"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public DualListModel<Funcion> buscaInternas(){
        
        try {
            funcionesTarget = oFuncion.buscaInternasUsadas();
            funcionesSource = oFuncion.buscaInternasDisponibles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Internas = new DualListModel<>(funcionesSource, funcionesTarget);

        return Internas;
    }

    /**
     * @return the oFuncion
     */
    public Funcion getFuncion() {
        return oFuncion;
    }

    /**
     * @return the Internas
     */
    public DualListModel<Funcion> getInternas() {
        return Internas;
    }

    /**
     * @param Internas the Internas to set
     */
    public void setInternas(DualListModel<Funcion> Internas) {
        this.Internas = Internas;
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
        this.nombre = nombre;
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
     * @return the noModificar
     */
    public boolean isNoModificar() {
        return noModificar;
    }

    /**
     * @param noModificar the noModificar to set
     */
    public void setNoModificar(boolean noModificar) {
        this.noModificar = noModificar;
    }

    /**
     * @return the nuevaFuncion
     */
    public boolean isNuevaFuncion() {
        return nuevaFuncion;
    }

    /**
     * @param nuevaFuncion the nuevaFuncion to set
     */
    public void setNuevaFuncion(boolean nuevaFuncion) {
        this.nuevaFuncion = nuevaFuncion;
    }

    /**
     * @return the renderEdicion
     */
    public boolean isRenderEdicion() {
        return renderEdicion;
    }

    /**
     * @param renderEdicion the renderEdicion to set
     */
    public void setRenderEdicion(boolean renderEdicion) {
        this.renderEdicion = renderEdicion;
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
