/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.consultaexterna.ProgConsultorios;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Perfil;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Puesto;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.core.Turno;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oEdicionUsu")
@SessionScoped
public class EdicionUsuarios {

    private String sOpe = "";
    private String sIdUsu = "";
    private Usuario oUsuario = null;
    private boolean bBuscado = false;
    private boolean bNuevo = false;
    private Firmado oFirm;
    private String sUsuarioFirm;
    private ArrayList<Usuario> usuarios;
    private ArrayList<PersonalHospitalario> persSinUsuario;
    private ArrayList<Usuario> usuariosFiltro;
    private ArrayList<PersonalHospitalario> persSinUsuarioFiltro;

    private DualListModel<Perfil> perfiles;
    private List<Perfil> perfilesTarget;
    private List<Perfil> perfilesSource;

    /**
     * Creates a new instance of EdicionUsuarios
     */
    public EdicionUsuarios() {

        perfilesTarget = new ArrayList<>();
        perfilesSource = new ArrayList<>();

        perfiles = new DualListModel<>(perfilesSource, perfilesTarget);

        oUsuario = new Usuario();
        try {
            usuarios = oUsuario.buscarUsuarios();
            persSinUsuario = oUsuario.getPersonal().buscarTodosSinUsuario();
            usuariosFiltro = oUsuario.buscarUsuarios();
            persSinUsuarioFiltro = oUsuario.getPersonal().buscarTodosSinUsuario();
        } catch (Exception e) {
            e.printStackTrace();
        }

        oFirm = new Firmado();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuarioFirm = oFirm.getUsu().getIdUsuario();
        }
    }

    public void edicionPerSinUsu(RowEditEvent event) {
        PersonalHospitalario row = (PersonalHospitalario) event.getObject();
        boolean modCorrecta = false;
        try {
            modCorrecta = row.modificaStatus(sUsuarioFirm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (modCorrecta) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Exito", "Se ha actualizado el área"));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "No se ha actualizado el área"));
        }

    }

    public Usuario getUsu() {
        if (!bBuscado) {
            bBuscado = true;
            if (this.sOpe.equals("a")) {
                oUsuario = new Usuario();
                //oUsuario.setAreaServHRRB(new AreaServicioHRRB());
                oUsuario.setPersonal(new PersonalHospitalario());
                oUsuario.setPuesto(new Puesto());
                oUsuario.setDesPerfil("");
                oUsuario.setCvePerfil("");
                oUsuario.setTurno(new Turno());
            } else {

                if (oUsuario.getIdUsuario() != null && !oUsuario.getIdUsuario().equals("")
                        && oUsuario.getCvePerfil() != null && !oUsuario.getCvePerfil().equals("")) {
                    try {
                        oUsuario.setIdUsuario(sIdUsu);
                        oUsuario.buscarPorPerfil();
                    } catch (Exception e) {
                        e.printStackTrace();
                        bBuscado = false;
                    }
                } else {
                    bBuscado = false;
                }
            }
        }
        return oUsuario;
    }

    public List<Perfil> getListaPerfiles() {
        List<Perfil> lLista = null;
        try {
            lLista = new Usuario().buscarPerfilesDisp();
        } catch (Exception ex) {
            Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lLista;
    }

    public boolean getProtegido() {
        boolean bProtegido = false;
        if (this.sOpe.equals("b")) {
            bProtegido = true;
        }
        return bProtegido;
    }

    public boolean getExistente() {
        boolean bExistente = false;
        if (!this.sOpe.equals("a")) {
            bExistente = true;
        }
        return bExistente;
    }

    public String getDescOpe() {
        String sRet = "Crear";
        if (this.sOpe.equals("b")) {
            sRet = "Eliminar";
        } else if (this.sOpe.equals("m")) {
            sRet = "Modificar";
        }
        return sRet;
    }

    public String eliminaUsuario(Usuario usu, String usu2) {
        int nAfec = 0;
        String pag = "/sesiones/UsuariosEncontrados.xhtml";
        FacesContext context = FacesContext.getCurrentInstance();
        oUsuario = usu;
        oUsuario.setIdUsuario2(usu2);

        try {
            nAfec = oUsuario.eliminar(sUsuarioFirm);
            usuarios = oUsuario.buscarUsuarios();
            persSinUsuario = oUsuario.getPersonal().buscarTodosSinUsuario();
            usuariosFiltro = oUsuario.buscarUsuarios();
            persSinUsuarioFiltro = oUsuario.getPersonal().buscarTodosSinUsuario();
        } catch (Exception e) {
        }

        if (nAfec >= 1) {
            if (nAfec == 23503) {
                context.addMessage(null, new FacesMessage("Operación fallida", "Falló al eliminar " + oUsuario.getIdUsuario() + " el usuario tiene un historial y no se debe eliminar"));
            } else {
                context.addMessage(null, new FacesMessage("Operación exitosa", "Eliminación Correcta de " + oUsuario.getIdUsuario()));
            }

        } else {
            context.addMessage(null, new FacesMessage("Operación fallida", "Falló al eliminar " + oUsuario.getIdUsuario()));
        }
        oUsuario = new Usuario();

        return pag;
    }

    public String modifica() {
        String pag = "ModificarUsuario";

        try {
            perfilesTarget = oUsuario.buscarPerfilesUsados();
            perfilesSource = oUsuario.buscarPerfilesDisp();
            setPerfiles(new DualListModel<>(perfilesSource, perfilesTarget));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pag;
    }

    public String ingresa() {
        String pag = "ModificarUsuario";
        //boolean enc=oUsuario.buscar();
        oUsuario.getPersonal().setNoTarjeta(0);
        oUsuario.getPersonal().setCurp("");
        try {
            perfilesTarget = new ArrayList<>();
            perfilesSource = oUsuario.buscarPerfilesDisp();
            setPerfiles(new DualListModel<>(perfilesSource, perfilesTarget));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pag;
    }

    public boolean habilitaCuenta() {
        if (getOpe().compareTo("a") == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String realizaModificacion() {
        String pag = "ModificarUsuario";
        FacesContext context = FacesContext.getCurrentInstance();
        int mod = 0;
        boolean modStatus = false, existe = false, existePersonal = false;
        String pass = oUsuario.getPassword();
        String passEnc="";

        String sArea=oUsuario.getPersonal().getStatus();

        try {
            existe = oUsuario.buscarNombreUsuario();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            existePersonal = oUsuario.getPersonal().buscar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (existePersonal) {
            if (getOpe().compareTo("m") == 0) { //modificar

                if (existe) {

                    oUsuario.getPersonal().setStatus(sArea);
                    
                    try {
                        MessageDigest md;
                        md = MessageDigest.getInstance("MD5");
                        md.update(pass.getBytes());
                        byte[] digest = md.digest();
                        StringBuilder sb = new StringBuilder();
                        for (byte b : digest) {
                            sb.append(String.format("%02x", b & 0xff));
                        }
                        passEnc=sb.toString();
                    } catch (NoSuchAlgorithmException ex) {

                    }

                    if (!oUsuario.getPassword().equals(passEnc)) {
                        oUsuario.setPassword(pass);
                    }else{
                        oUsuario.setPassword("");
                    }
                    
                    oUsuario.setPerfil(new ArrayList(perfiles.getTarget()));
                    try {
                        mod = oUsuario.modificar(sUsuarioFirm);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {    //insertar

                if (!existe) {
                    oUsuario.setPerfil(new ArrayList(perfiles.getTarget()));
                    try {
                        mod = oUsuario.insertar(sUsuarioFirm);
                        
                        usuarios = oUsuario.buscarUsuarios();
                        persSinUsuario = oUsuario.getPersonal().buscarTodosSinUsuario();
                        usuariosFiltro = oUsuario.buscarUsuarios();
                        persSinUsuarioFiltro = oUsuario.getPersonal().buscarTodosSinUsuario();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    context.addMessage(null, new FacesMessage("Error", "El usuario " + oUsuario.getIdUsuario() + " ya existe"));
                }
            }
        } else {
            context.addMessage(null, new FacesMessage("Error", "El personal " + oUsuario.getPersonal().getNombreCompleto() + " con tarjeta\n "
                    + oUsuario.getPersonal().getNoTarjeta() + " no existe"));
        }
        if (mod > 0) {
            pag = "UsuariosEncontrados";
            if (getOpe().compareTo("m") == 0) {
                context.addMessage(null, new FacesMessage("Exito", "Modificación correcta"));
            } else {
                context.addMessage(null, new FacesMessage("Exito", "Registro correcto"));
            }
        } else {
            if (getOpe().compareTo("m") == 0) {
                context.addMessage(null, new FacesMessage("Error", "Modificación fallida"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro fallido"));
            }
        }
        return pag;
    }

    public boolean habilitaTarjeta() {
        /*if (oUsuario.getPersonal().getCurp().compareTo("") == 0) {
         oUsuario.getPersonal().setNoTarjeta(0);
         }*/
        if (oUsuario.getPersonal().getCurp().compareTo("") == 0 && getOpe().compareTo("a") == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean habilitaCurp() {
        if (oUsuario.getPersonal().getNoTarjeta() == 0) {
            oUsuario.getPersonal().setCurp("");
        }
        if (getOpe().compareTo("a") == 0 && oUsuario.getPersonal().getNoTarjeta() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<Parametrizacion> getListaAreas() {
        List<Parametrizacion> lLista = null;
        try {
            lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T46"))));
        } catch (Exception ex) {
            Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lLista;
    }

    public String recargaListas() {
        try {
            usuarios = oUsuario.buscarUsuarios();
            persSinUsuario = oUsuario.getPersonal().buscarTodosSinUsuario();
            usuariosFiltro = oUsuario.buscarUsuarios();
            persSinUsuarioFiltro = oUsuario.getPersonal().buscarTodosSinUsuario();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "UsuariosEncontrados.xhtml";
    }
    
    public boolean filterByUpperCase( Object value, Object filter, Locale locale ){
	String filterText = (filter == null) ? null : filter.toString().trim().toUpperCase();
	String valueText = (value == null) ? null : value.toString().trim().toUpperCase();
	if( filterText == null || filterText.isEmpty() ){
	    return true;
	}

	if( value == null || valueText == null ){
	    return false;
	}

	return valueText.contains( filterText );
    }

    /**
     * @return the sOpe
     */
    public String getOpe() {
        return sOpe;
    }

    /**
     * @param sOpe the sOpe to set
     */
    public void setOpe(String sOpe) {
        this.sOpe = sOpe;
    }

    public boolean getBuscado() {
        return bBuscado;
    }

    public void setBuscado(boolean valor) {
        this.bBuscado = valor;
    }

    /**
     * @return the sIdUsu
     */
    public String getIdUsu() {
        return sIdUsu;
    }

    /**
     * @param sIdUsu the sIdUsu to set
     */
    public void setIdUsu(String sIdUsu) {
        this.sIdUsu = sIdUsu;
    }

    /**
     * @return the perfiles
     */
    public DualListModel<Perfil> getPerfiles() {
        return perfiles;
    }

    /**
     * @param perfiles the perfiles to set
     */
    public void setPerfiles(DualListModel<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    /**
     * @return the usuariosFiltro
     */
    public ArrayList<Usuario> getUsuariosFiltro() {
        return usuariosFiltro;
    }

    /**
     * @param usuariosFiltro the usuariosFiltro to set
     */
    public void setUsuariosFiltro(ArrayList<Usuario> usuariosFiltro) {
        this.usuariosFiltro = usuariosFiltro;
    }

    /**
     * @return the persSinUsuarioFiltro
     */
    public ArrayList<PersonalHospitalario> getPersSinUsuarioFiltro() {
        return persSinUsuarioFiltro;
    }

    /**
     * @param persSinUsuarioFiltro the persSinUsuarioFiltro to set
     */
    public void setPersSinUsuarioFiltro(ArrayList<PersonalHospitalario> persSinUsuarioFiltro) {
        this.persSinUsuarioFiltro = persSinUsuarioFiltro;
    }

    /**
     * @return the bNuevo
     */
    public boolean isNuevo() {
        return bNuevo;
}

    /**
     * @param bNuevo the bNuevo to set
     */
    public void setNuevo(boolean bNuevo) {
        this.bNuevo = bNuevo;
    }

    /**
     * @return the usuarios
     */
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the persSinUsuario
     */
    public ArrayList<PersonalHospitalario> getPersSinUsuario() {
        return persSinUsuario;
    }

    /**
     * @param persSinUsuario the persSinUsuario to set
     */
    public void setPersSinUsuario(ArrayList<PersonalHospitalario> persSinUsuario) {
        this.persSinUsuario = persSinUsuario;
    }

}
