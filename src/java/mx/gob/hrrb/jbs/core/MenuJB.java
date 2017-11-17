package mx.gob.hrrb.jbs.core;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.core.Funcion;
import mx.gob.hrrb.modelo.core.Menu;
import mx.gob.hrrb.modelo.core.Perfil;
import mx.gob.hrrb.modelo.core.Usuario;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author BAOZ
 */
@ManagedBean(name = "oMenuJB")
@SessionScoped
public class MenuJB implements Serializable {

    private MenuModel oModeloMenu;

    /**
     * Creates a new instance of MenuJB
     */
    public MenuJB() {
        init();
    }

    //@PostConstruct
    public void init() {

        oModeloMenu = new DefaultMenuModel();
        int submen = 0;

        DefaultMenuItem menuItem = null;
        DefaultSubMenu menuArriba = null, subMenu = null;

        Menu oArriba = null, oSub = null;
        Usuario oUsu;
        ArrayList<Perfil> arrPerfil, completo = new ArrayList<Perfil>();

        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().
                getSession(false);
        Firmado oFirm = (Firmado) session.getAttribute("oFirm");

        if (oFirm != null) {
            if (oFirm.getUsu() != null) {
                oUsu = oFirm.getUsu();
                if (oUsu.getPerfil() != null) {
                    arrPerfil = oUsu.getPerfil();
                    for (Perfil p : arrPerfil) {
                        completo.add(p);
                    }
                    menuItem = new DefaultMenuItem("Inicio");
                    menuItem.setUrl("/faces/sesiones/Inicio.xhtml");
                    menuItem.setIcon("ui-icon-home");
                    oModeloMenu.addElement(menuItem);

                    for (Perfil p : completo) {
                        for (Funcion f : p.getPermisos()) {
                            if ((f.getLlamada() == null || f.getLlamada().compareToIgnoreCase("") == 0) && f.getMenu().getPadre() == null) {
                                oArriba = f.getMenu();
                                if (menuArriba != null) {
                                    oModeloMenu.addElement(menuArriba);
                                }
                                menuArriba = new DefaultSubMenu(oArriba.getDescripcion());
                                menuArriba.setIcon(f.getMenu().getIcono().getNombre());
                                subMenu = null;
                            } else if (f.getLlamada() == null || f.getLlamada().compareTo("") == 0) { //MODIFICACION 29/01/15
                                oSub = f.getMenu();
                                subMenu = new DefaultSubMenu(oSub.getDescripcion());
                                subMenu.setIcon( f.getMenu().getIcono().getNombre() );
                                menuArriba.addElement(subMenu);
                                submen = f.getMenu().getClaveMenu();
                            } else {
                                menuItem = new DefaultMenuItem(f.getDescripcion());
                                if (f.getLlamada().startsWith("urgencias/archivos/CAUSES_20")) {
                                    menuItem.setTarget("_blank");
                                }
                                if (f.getLlamada().startsWith("capasits/hojaDiariaExterna.xhtml")) {
                                    menuItem.setTarget("_blank");
                                }
                                if (f.getLlamada().startsWith("capasits/hojaDiariaNutricion.xhtml")) {
                                    menuItem.setTarget("_blank");
                                }
                                if (f.getLlamada().startsWith("capasits/hojaDiariaOdontologia.xhtml")) {
                                    menuItem.setTarget("_blank");
                                }
                                if (f.getLlamada().startsWith("capasits/hojaDiariaPsicologia.xhtml")) {
                                    menuItem.setTarget("_blank");
                                }
                                if (f.getLlamada().startsWith("capasits/hojaDiariaTrabajos.xhtml")) {
                                    menuItem.setTarget("_blank");
                                }
                                menuItem.setUrl("/faces/sesiones/" + f.getLlamada());
                                menuItem.setIcon( f.getIcono().getNombre() );
                                //System.out.println(menuItem.getUrl()+" <--URL");
                                if (f.getMenu().getPadre() == null && menuArriba != null) {
                                    menuArriba.addElement(menuItem);
                                } else {
                                    if (subMenu != null && submen == f.getMenu().getClaveMenu()) {
                                        if (f.getDescripcion().compareTo("Matutino") == 0 && 
                                                f.getMenu().getClaveMenu() == 9) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(19,1)}");
                                        }
                                        if (f.getDescripcion().compareTo("Vespertino") == 0 && 
                                                f.getMenu().getClaveMenu() == 9) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(19,2)}");
                                        }
                                        if (f.getDescripcion().compareTo("Matutino") == 0 && 
                                                f.getMenu().getClaveMenu() == 10) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(20,1)}");
                                        }
                                        if (f.getDescripcion().compareTo("Vespertino") == 0 && 
                                                f.getMenu().getClaveMenu() == 10) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(20,2)}");
                                        }
                                        if (f.getDescripcion().compareTo("Matutino") == 0 && 
                                                f.getMenu().getClaveMenu() == 11) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(22,1)}");
                                        }
                                        if (f.getDescripcion().compareTo("Vespertino") == 0 && 
                                                f.getMenu().getClaveMenu() == 11) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(22,2)}");
                                        }
                                        if (f.getDescripcion().compareTo("Matutino") == 0 && 
                                                f.getMenu().getClaveMenu() == 12) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(24,1)}");
                                        }
                                        if (f.getDescripcion().compareTo("Vespertino") == 0 && 
                                                f.getMenu().getClaveMenu() == 12) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(24,2)}");
                                        }
                                        if (f.getDescripcion().compareTo("Matutino") == 0 && 
                                                f.getMenu().getClaveMenu() == 13) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(23,1)}");
                                        }
                                        if (f.getDescripcion().compareTo("Vespertino") == 0 && 
                                                f.getMenu().getClaveMenu() == 13) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(23,2)}");
                                        }
                                        if (f.getDescripcion().compareTo("Matutino") == 0 && 
                                                f.getMenu().getClaveMenu() == 14) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(21,1)}");
                                        }
                                        if (f.getDescripcion().compareTo("Vespertino") == 0 && 
                                                f.getMenu().getClaveMenu() == 14) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{agendaAdmin.Datos(21,2)}");
                                        }
                                        subMenu.addElement(menuItem);
                                    } else {
                                        if (f.getDescripcion().compareTo("Registrar Paciente") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(1)}");
                                        }
                                        if (f.getDescripcion().compareTo("Hospitalizar") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(2)}");
                                        }
                                        if (f.getDescripcion().compareTo("Abrir Expediente") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(3)}");
                                        }
                                        if (f.getDescripcion().compareTo("Ver Ingresos del Paciente") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(4)}");
                                        }
                                        if (f.getDescripcion().compareTo("Llenar Hoja Lesiones") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(5)}");
                                        }
                                        if (f.getDescripcion().compareTo("Consultar/Modificar Pacientes") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(6)}");
                                        }
                                        if (f.getDescripcion().compareTo("Llenar Nota Médica") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(7)}");
                                        }
                                        if (f.getDescripcion().compareTo("Alta Paciente") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(8)}");
                                        }
                                        if (f.getDescripcion().compareTo("Modificar Paciente CE") == 0 || (f.getDescripcion().compareTo("Modificar Paciente") == 0 && f.getMenu().getPadre().getClaveMenu() == 1)) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(9)}");
                                        }
                                        if (f.getLlamada().compareToIgnoreCase("hospitalizacion/PacientesHospitalizados.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("CODES Pendientes") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oHospCODE.introduceTipo(1)}");
                                        }
                                        if (f.getLlamada().compareToIgnoreCase("hospitalizacion/PacientesHospitalizadosArea.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("CODES Pendientes") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oHospCODE.introduceTipo(4)}");
                                        }
                                        if (f.getLlamada().compareTo("hospitalizacion/PacientesHospitalizados.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("Modificar Edo Salud Paciente Hospitalizado") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oHospCODE.introduceTipo(2)}");
                                        }
                                        if (f.getLlamada().compareTo("hospitalizacion/PacientesHospitalizadosArea.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("Modificar Edo Salud Paciente Hospitalizado") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oHospCODE.introduceTipo(5)}");
                                        }
                                        if (f.getLlamada().compareTo("hospitalizacion/PacientesHospitalizados.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("Reservar Cita Hospitalización") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oHospCODE.introduceTipo(3)}");
                                        }
                                        if (f.getLlamada().compareTo("hospitalizacion/PacientesHospitalizadosArea.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("Reservar Cita Hospitalización") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oHospCODE.introduceTipo(6)}");
                                        }
                                        if (f.getLlamada().compareTo("hospitalizacion/PacientesHospitalizados.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("Llenar Hoja de Alta") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oHospCODE.introduceTipo(7)}");
                                        }
                                        if (f.getLlamada().compareTo("hospitalizacion/PacientesHospitalizadosArea.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("Llenar Hoja de Alta") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oHospCODE.introduceTipo(8)}");
                                        }
                                        if (f.getLlamada().compareTo("hospitalizacion/Reporte.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("Reporte de Hospitalización") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oReporteHosp.introduceTipo(1)}");
                                        }
                                        if (f.getLlamada().compareTo("hospitalizacion/ReporteArea.xhtml") == 0 && f.getDescripcion().compareToIgnoreCase("Reporte de Hospitalización") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oReporteHosp.introduceTipo(2)}");
                                        }
                                        if (f.getDescripcion().compareTo("Modificar Expediente") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oBuscarPac.introduceTipo(10)}");
                                        }
                                        if (f.getDescripcion().compareTo("Prog. de Consultorios") == 0) {
                                            menuItem.setUrl(null);
                                            menuItem.setAjax(false);
                                            menuItem.setCommand("#{oProgConsul.introduceURl()}");
                                        }
                                        menuArriba.addElement(menuItem);

                                    }
                                }
                            }
                        } //por función
                        //El último menú se completó al terminar el ciclo 
                        //y no se anexó al modelo
                        if (menuArriba != null) {
                            //menuArriba.setIcon(obtenIcono(p.getPermisos().get(p.getPermisos().size()-ultimomenu-1).getMenu().getDescripcion()));
                            oModeloMenu.addElement(menuArriba);
                        }
                    }// por perfil

                    menuItem = new DefaultMenuItem("Salir");
                    menuItem.setCommand("#{oLogin.logout}");
                    menuItem.setIcon("ui-icon-power");
                    oModeloMenu.addElement(menuItem);
                } //usuario con al menos un perfil
            }//usuario distinto de null
        }//firmado
    }

    /**
     * @return the oModeloMenu
     */
    public MenuModel getModeloMenu() {
        return oModeloMenu;
    }

    public int coincideSubmenu(int v[], int x) {
        int coin = -1;
        for (int i = 0; i < v.length; i++) {
            if (v[i] == x) {
                coin = i;
            }
        }
        return coin;
    }

    /**
     * @param oModeloMenu the oModeloMenu to set
     */
    public void setModeloMenu(MenuModel oModeloMenu) {
        this.oModeloMenu = oModeloMenu;
    }

}
