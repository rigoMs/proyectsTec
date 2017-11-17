/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.capasits;

import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Temperaturas;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author sam
 */
@ManagedBean(name = "oTemps")
@SessionScoped
public class EdicionTemJB implements Serializable {

    private String mes = "";
    public int mesBusqueda = 0;
    private int turno = 1;
    private int anio, anioActual = 0;
    private Temperaturas[] arrTem = null;
    private ArrayList<Temperaturas> tempsArray = new ArrayList<Temperaturas>();
    private Calendar cal = null;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;

    public EdicionTemJB() throws Exception {
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
        cargaLista();
    }

    public void cargaLista() throws Exception {
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        setAnioActual(cal.get(Calendar.YEAR));//
        if (getAnio() > anioActual) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se puede Buscar en un año Futuro", " "));
            return;
        }
        arrTem = null;
        if (getMesBusqueda() == 0) {
            cal = Calendar.getInstance();
            cal.setTime(new Date());
            anio = cal.get(Calendar.YEAR);//solo para asignar por primera vez el año a la variable
            setMesBusqueda(cal.get(Calendar.MONTH) + 1);
            nombreMes(cal.get(Calendar.MONTH) + 1);
        }
        Temperaturas oTem = new Temperaturas();

        tempsArray.clear();
        nombreMes(getMesBusqueda());
        if (getMesBusqueda() > 9) {
            arrTem = (Temperaturas[]) oTem.buscarTodos(getAnio(), "" + getMesBusqueda(), turno);
        } else {
            arrTem = (Temperaturas[]) oTem.buscarTodos(getAnio(), "0" + getMesBusqueda(), turno);
        }
        llenaArray();

    }

    public void llenaArray() {
        for (int i = 0; i < arrTem.length; i++) {
            tempsArray.add(i, arrTem[i]);
        }
    }

    public Temperaturas[] getListaM() {
        return arrTem;
    }

    public void nombreMes(int month) {
        String mesActual = "";
        switch (month) {
            case 1:
                mesActual = "Enero";
                break;
            case 2:
                mesActual = "Febrero";
                break;
            case 3:
                mesActual = "Marzo";
                break;
            case 4:
                mesActual = "Abril";
                break;
            case 5:
                mesActual = "Mayo";
                break;
            case 6:
                mesActual = "Junio";
                break;
            case 7:
                mesActual = "Julio";
                break;
            case 8:
                mesActual = "Agosto";
                break;
            case 9:
                mesActual = "Septiembre";
                break;
            case 10:
                mesActual = "Octubre";
                break;
            case 11:
                mesActual = "Noviembre";
                break;
            case 12:
                mesActual = "Diciembre";
                break;
            default:
                mesActual = "Mes Invalido";
                break;
        }
        this.mes = mesActual;
    }

    public void onEdit(RowEditEvent event) throws Exception {
        Temperaturas oTem = (Temperaturas) event.getObject();
        int diaSeleccionado = tempsArray.indexOf(oTem) + 1;
        if (verificaRangos(oTem)) {

            if (oTem.getRegistro().getYear() == -1899) {//los dias que no estaban en la base de datos tienen año 1899
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date fecha = null;
                if (getTurno() == 1) {
                    fecha = formato.parse("" + getAnio() + "-" + getMesBusqueda() + "-" + diaSeleccionado + " 9:00");
                }
                if (getTurno() == 2) {
                    fecha = formato.parse("" + getAnio() + "-" + getMesBusqueda() + "-" + diaSeleccionado + " 15:00");
                }
                if (getTurno() == 3) {
                    fecha = formato.parse("" + getAnio() + "-" + getMesBusqueda() + "-" + diaSeleccionado + " 20:00");
                }
                oTem.setRegistro(fecha);
                oTem.insertarEditado(sUsuario);
            } else {//si tiene un año diferernte a 1899 entonces ya trae uno desde la BD y ya solo se manda a modificar con los nuevos datos
                oTem.modificar(sUsuario);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "" + diaSeleccionado + " de " + getMes(), "Editado"));
        }
    }

    public boolean verificaRangos(Temperaturas oTem) {
        boolean bandera = true;
        if (oTem.getTemAmb() < -1 || oTem.getTemAmb() > 40) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Temperatura Ambiental Fuera de Rango 0-40", " "));
            bandera = false;
        }
        if (oTem.getHumAmb() < 29 || oTem.getHumAmb() > 101) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Humedad Ambiental Fuera de Rango 30-100", " "));
            bandera = false;
        }
        if (oTem.getTemRef() < -1 || oTem.getTemRef() > 5.1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Temperatura del Refrigerador Fuera de Rango 0-5.1", " "));
            bandera = false;
        }
        return bandera;
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion", "Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getMesBusqueda() {
        return mesBusqueda;
    }

    public void setMesBusqueda(int mesBusqueda) {
        int month = (cal.get(Calendar.MONTH)) + 1;
        if (getAnio() <= getAnioActual()) {
            if (mesBusqueda > month) {
                if (getAnio() == getAnioActual()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No Puede editar un Mes futuro", " "));
                } else {
                    this.mesBusqueda = mesBusqueda;
                }
            } else {
                this.mesBusqueda = mesBusqueda;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No Puede editar un Año futuro", " "));
        }
    }

    public ArrayList<Temperaturas> getLista() {
        return tempsArray;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getNomTurno() {
        String msg = "";
        if (turno == 1) {
            msg = "Matutino";
        }
        if (turno == 2) {
            msg = "Vespertino";
        }
        if (turno == 3) {
            msg = "Nocturno";
        }
        return msg;
    }

    public void Limpia() {
        setTurno(1);
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(int anioActual) {
        this.anioActual = anioActual;
    }

}
