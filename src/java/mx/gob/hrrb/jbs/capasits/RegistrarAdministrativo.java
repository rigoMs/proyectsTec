package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.consultaexterna.ProgConsultorios;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 *
 * @author sam
 */
@ManagedBean(name = "regAdminis")
@SessionScoped
public class RegistrarAdministrativo implements Serializable {

    private PersonalHospitalario oPerHosp = null;
    private Usuario oUsua;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario, cedProAux, idUsuAux;
    private Turno oTurno;

    /**
     * Creates a new instance of RegistrarAdministrativo
     */
    public RegistrarAdministrativo() {
        oPerHosp = new PersonalHospitalario();
        oUsua = new Usuario();
        oTurno = new Turno();

        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public String almacena() throws Exception {
        if (!oPerHosp.getCedProf().equals("")) {
            if (!esEntero(oPerHosp.getCedProf())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cedula Profesional", "Solo ingresar números"));
                return "altaAdministrativo";
            }
        }
        if (valida()) {
            if (oUsua.insertarCapa(sUsuario) == 1) {
                if (oUsua.getDesPerfil().equals("FARMACIA")) {
                    oUsua.setDesPerfil("FARMCAPASI");
                }
                if (oUsua.getDesPerfil().equals("SECRETARIA")) {
                    oUsua.setDesPerfil("CAPASSECRE");
                }
                if (oUsua.getDesPerfil().equals("DIRECTIVO")) {
                    oUsua.setDesPerfil("ADMCAPASIT");
                }
                if (oUsua.insertarUsuPerf(sUsuario) == 1) {
                    oPerHosp.setStatus(oUsua.getIdUsuario());
                    oPerHosp.setActividad(oUsua.getDesPerfil());
                    if (oPerHosp.insertarCapa(sUsuario, oTurno.getClave()) == 1) {
                        oPerHosp = new PersonalHospitalario();
                        oUsua = new Usuario();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Administrativo", "Guardado"));
                        return "altaAdministrativo";
                    }
                }
            }

        } else {
            return "altaAdministrativo";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Verifique Datos"));
        return "altaAdministrativo";
    }

    public boolean valida() throws Exception {
        Date fecha = new Date();
        SimpleDateFormat ff = new SimpleDateFormat("yyyy/MM/dd");
        String f = ff.format(fecha);
        String f2 = ff.format(oPerHosp.getFechaNac());

        if (f.equals(f2) || fecha.before(oPerHosp.getFechaNac())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "FECHA DE NACIMIENTO INVALIDA"));
            return false;
        }
        if (oPerHosp.getNoTarjeta() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL NUMERO DE TARJERA ES INVALIDO"));
            return false;
        }
        if (oPerHosp.buscarCapa()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL NUMERO DE TARJERA YA EXISTE"));
            return false;
        }
        if (oPerHosp.buscarCedulaProfes()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA CÉDULA PROFECIONAL YA EXISTE"));
            return false;
        }
        if (oUsua.buscarNombreUsuario()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL NOMBRE DE USUARIO YA EXISTE"));
            return false;
        }
        return true;
    }

    public void buscarAdministrativo() throws Exception {
        if (oPerHosp.getNoTarjeta() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NUMERO DE TARJETA INVALIDO"));
        } else {
            if (oPerHosp.buscarCapa()) {
                oUsua.setIdUsuario(oPerHosp.getUsuar().getIdUsuario());
                oUsua.setIdUsuario2(oPerHosp.getUsuar().getIdUsuario());
                cedProAux = oPerHosp.getCedProf();
                idUsuAux = oPerHosp.getUsuar().getIdUsuario();
                oTurno.buscarTurnoPersonal(oPerHosp.getNoTarjeta());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "" + oPerHosp.getNoTarjeta(), "ENCONTRADO"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "" + oPerHosp.getNoTarjeta(), "NO ENCONTRADO"));
            }
        }
    }

    public String modificar() throws Exception {
        int tipo = 1;
        Date fecha = new Date();
        SimpleDateFormat ff = new SimpleDateFormat("yyyy/MM/dd");
        String f = ff.format(fecha);
        String f2 = ff.format(oPerHosp.getFechaNac());

        if (f.equals(f2) || fecha.before(oPerHosp.getFechaNac())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "FECHA DE NACIMIENTO INVALIDA"));
            return "modificaAdministrativo";
        }
        if (oPerHosp.getNoTarjeta() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL NUMERO DE TARJERA ES INVALIDO"));
            return "modificaAdministrativo";
        }
        if (!oPerHosp.getCedProf().equals(cedProAux)) {
            if (!esEntero(oPerHosp.getCedProf())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cedula Profesional", "Solo ingresar números"));
                return "modificaAdministrativo";
            }
            if (oPerHosp.buscarCedulaProfes()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL NUMERO DE CEDULA PROFESIONAL YA EXISTE"));
                return "modificaAdministrativo";
            }
        }

        if (!oUsua.getIdUsuario2().equals(oPerHosp.getUsuar().getIdUsuario())) {
            tipo = 2;
            oUsua.setIdUsuario(oUsua.getIdUsuario2());
            if (oUsua.buscarNombreUsuario()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL NOMBRE DE USUARIO YA EXISTE"));
                return "modificaAdministrativo";
            }
        }
        if (idUsuAux.length() != 0) {
            oUsua.setIdUsuario(idUsuAux);
            if (oUsua.elimUsuPerHospCapa(sUsuario, oPerHosp.getNoTarjeta()) != 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL USUARIO NO PUDO SER ACTUALIZADO"));
                return "modificaAdministrativo";
            }
        }

        if (oPerHosp.getActividad().equals("FARMACIA")) {
            oPerHosp.setActividad("FARMCAPASI");
        }
        if (oPerHosp.getActividad().equals("SECRETARIA")) {
            oPerHosp.setActividad("CAPASSECRE");
        }
        if (oPerHosp.getActividad().equals("DIRECTIVO")) {
            oPerHosp.setActividad("ADMCAPASIT");
        }
        oPerHosp.getUsuar().setIdUsuario(oUsua.getIdUsuario2());//nuvo usuario
        if (oPerHosp.modificaPersonalHospitalarioUsuario(sUsuario, idUsuAux, oUsua.getIdUsuario2(), oUsua.getPassword(), tipo, oTurno.getClave()) == 1) {
            oPerHosp = new PersonalHospitalario();
            oUsua = new Usuario();
            idUsuAux = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "PERSONAL MODIFICADO"));
            return "modificaAdministrativo";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "VERIFIQUE DATOS"));
            return "modificaAdministrativo";
        }
    }

    public List<Turno> getListaTurno() {
        List<Turno> lLista = null;
        try {
            lLista = new ArrayList<Turno>(Arrays.asList((new Turno().buscarTurnosCE())));
        } catch (Exception ex) {
            Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lLista;
    }

    public boolean esEntero(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public PersonalHospitalario getPerHosp() {
        return oPerHosp;
    }

    public void setPerHosp(PersonalHospitalario oPerHosp) {
        this.oPerHosp = oPerHosp;
    }

    public Usuario getUsua() {
        return oUsua;
    }

    public void setUsua(Usuario valor) {
        this.oUsua = valor;
    }

    public Turno getTurno() {
        return oTurno;
    }

    public void setTurno(Turno oTurno) {
        this.oTurno = oTurno;
    }

}
