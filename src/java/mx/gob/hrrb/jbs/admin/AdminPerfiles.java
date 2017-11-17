package mx.gob.hrrb.jbs.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Funcion;
import mx.gob.hrrb.modelo.core.Perfil;
import org.primefaces.model.DualListModel;

/**
 *
 * @author juan
 */
@ManagedBean(name = "adminPerfiles")
@ViewScoped
public class AdminPerfiles implements Serializable {

    private final Funcion funcion;
    private DualListModel<Funcion> funciones;
    private List<Funcion> funcionesSource;
    private List<Funcion> funcionesTarget;

    private Perfil oPerfil;
    private String nombre;

    private String tituloVentana;
    private boolean noEditarClave;
    private boolean nuevoPerfil;
    private boolean renderEliminar;
    private boolean renderEdicion;

    /**
     * Creates a new instance of AdminPerfiles
     */
    public void guardarPerfil() {

        String estado = "";
        boolean existePerfil = true;
        int perfilModificado = 0;
        //Perfil oPerfilResp = (Perfil) SerializationUtils.clone(oPerfil);
        Perfil oPerfilResp = new Perfil();
        oPerfilResp.setClave(oPerfil.getClave());
        oPerfilResp.setDescripcion(oPerfil.getDescripcion());

        if (oPerfil.getDescripcion() != null && !oPerfil.getDescripcion().equals("")) {
            if (oPerfil.getClave() != null && !oPerfil.getClave().equals("")) {

                String sErr = "";

                try {
                    existePerfil = oPerfil.buscar();

                    //los vuelvo a setear porque .buscar() 
                    //reescribe los datos ya existentes en ese registro
                    oPerfil = oPerfilResp;

                } catch (Exception e) {
                    sErr = "Error en la consulta";
                }

                if (existePerfil && !isNuevoPerfil()) {

                    int modificado = 1;

                    try {
                        modificado = oPerfil.modificar();
                    } catch (Exception e) {
                        sErr = "error enviando el update";
                    }

                    if (modificado == 0) {
                        sErr = "error modificando el perfil ";
                    } else {
                        estado = "modificado exitosamente";
                    }

                } else {

                    if (isNuevoPerfil() && !existePerfil) {
                        try {
                            int rst = oPerfil.insertar();
                            if (rst == 1) {
                                estado = "insertado exitosamente";
                            }
                        } catch (Exception e) {
                            sErr = "Error insertando";
                        }
                    } else {
                        sErr = "Ya existe una perfil con esa oPerfil.getClave()";
                        nombre = "";
                    }

                }

                try {
                    perfilModificado = modificarFunciones();
                } catch (Exception e) {
                }

                if (perfilModificado != 0) {
                    sErr = "error con la actualización de funciones, ";
                }

                if (sErr.equals("")) {
                    nombre = "";
                    oPerfil.setClave("");

                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Operación exitosa", "Perfil " + oPerfil.getDescripcion() + " " + estado));
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Operación fallida",
                            sErr));
                }

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Faltan datos", "Falta la clave del perfil"));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Faltan datos", "Falta el nombre del perfil"));
        }
    }

    public int modificarFunciones() throws Exception {

        int res = 0;
        if (funciones.getTarget() != null) {
            List<Funcion> escogidas = funciones.getTarget();

            for (Funcion f : funcionesTarget) {
                //si la nueva lista de funciones no contiene la funcion de la lista original f, esta se borra
                if (!funciones.getTarget().contains(f)) {
                    //System.out.println("borrar funcion " + f.getDescripcion());
                    if (f.removerDelPerfil(oPerfil.getClave()) == 0) {
                        res = 1;
                    }
                }
            }

            for (Funcion f : escogidas) {
                //si el original no contenia la funcion f de las escogidas, esta se guarda
                if (!funcionesTarget.contains(f)) {
                    //System.out.println("guardar funcion " + f.getDescripcion());
                    if (f.asignarAPerfil(oPerfil.getClave()) == 0) {
                        res = 1;
                    }
                }
            }

        }
        return res;
    }

    public DualListModel<Funcion> obtenerFunciones() {

        try {
            funcionesTarget = funcion.buscarTodasPorPerfil(oPerfil.getClave());
            funcionesSource = funcion.buscarTodasDispPorPerfil(oPerfil.getClave());
        } catch (Exception e) {
            e.printStackTrace();
        }

        funciones = new DualListModel<>(funcionesSource, funcionesTarget);

        return funciones;
    }

    public List<String> completaPerfil(String s) {
        List<Perfil> perfiles = new ArrayList<>();
        List<String> sPerfiles = new ArrayList<>();
        try {
            perfiles = oPerfil.buscarTodosLosPerfiles();
            if (s.trim().equals("")) {
                for (Perfil p : perfiles) {
                    sPerfiles.add(p.getDescripcion());
                }
                return sPerfiles;
            }

            for (Perfil m : perfiles) {
                if (m.getDescripcion().contains(s) || m.getDescripcion().toUpperCase().contains(s.toUpperCase())) {
                    sPerfiles.add(m.getDescripcion());
                }
            }
            return sPerfiles;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Perfil[] getPerfiles() {
        List<Perfil> perfiles, sPerfiles = new ArrayList<>();

        try {
            perfiles = oPerfil.buscarTodosLosPerfiles();

            if (nombre.trim().equals("")) {
                Perfil[] retPerfiles = new Perfil[perfiles.size()];
                return perfiles.toArray(retPerfiles);
            }

            for (Perfil m : perfiles) {
                if (m.getDescripcion().contains(nombre) || m.getDescripcion().toUpperCase().contains(nombre.toUpperCase())) {
                    sPerfiles.add(m);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Perfil[] retPerfiles = new Perfil[sPerfiles.size()];
        return sPerfiles.toArray(retPerfiles);

    }

    public List<Perfil> convertirEnLista(Perfil[] arrPerf) throws Exception {
        List<Perfil> listPerf = new ArrayList<>();
        for (Perfil f : arrPerf) {
            listPerf.add(f);
        }
        return listPerf;
    }

    public void crearPerfil() {
        oPerfil = new Perfil();
        this.funciones = obtenerFunciones();
        setNuevoPerfil(true);
        renderEdicion = true;
        noEditarClave = false;
    }

    public void modificarPerfil(Perfil oPerfil) {
        setNuevoPerfil(false);
        this.oPerfil = oPerfil;
        this.funciones = obtenerFunciones();
        renderEdicion = true;
        noEditarClave = true;
    }

    public void preparaEliminar(Perfil oPerfTemp) {
        oPerfil = oPerfTemp;
        renderEliminar= true;
    }

    public void eliminarPerfil() {

        try {
            if (oPerfil.eliminar() != 0) {
                nombre = "";

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Operación exitosa", "Perfil " + oPerfil.getDescripcion() + " eliminado"));
            }
        } catch (Exception e) {
        }
    }

    public AdminPerfiles() {
        oPerfil = new Perfil();
        funcion = new Funcion();
        nombre = "";

        noEditarClave = false;
        renderEliminar = false;
        renderEdicion = false;
        tituloVentana = "";

        funcionesTarget = new ArrayList<>();
        funcionesSource = new ArrayList<>();

        funciones = new DualListModel<>(funcionesSource, funcionesTarget);

    }

    /**
     * @return the oPerfil
     */
    public Perfil getPerfil() {
        return oPerfil;
    }

    /**
     * @return the funcion
     */
    public Funcion getFuncion() {
        return funcion;
    }

    /**
     * @return the funciones
     */
    public DualListModel<Funcion> getFunciones() {
        return funciones;
    }

    /**
     * @param funciones the funciones to set
     */
    public void setFunciones(DualListModel<Funcion> funciones) {
        this.funciones = funciones;
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
        this.nombre = nombre.toUpperCase();
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
     * @return the editarClave
     */
    public boolean isNoEditarClave() {
        return noEditarClave;
    }

    /**
     * @param editarClave the editarClave to set
     */
    public void setNoEditarClave(boolean editarClave) {
        this.noEditarClave = editarClave;
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
     * @return the nuevoPerfil
     */
    public boolean isNuevoPerfil() {
        return nuevoPerfil;
    }

    /**
     * @param nuevoPerfil the nuevoPerfil to set
     */
    public void setNuevoPerfil(boolean nuevoPerfil) {
        this.nuevoPerfil = nuevoPerfil;
    }

}
