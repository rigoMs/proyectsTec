/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Hospitalizacion;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oHospCODE")
@SessionScoped
public class PacientesHospitalizadosCODE {
    private Hospitalizacion oHosp;
    private AreaServicioHRRB oASerHRRB;
    private List<Hospitalizacion> lHospitalizacion;
    private int nTipo;
    private boolean bEstado;
    private String sNombrePag;
    /**
     * Creates a new instance of PacientesHospitalizadosCODE
     */
    public PacientesHospitalizadosCODE() {
        oHosp = new Hospitalizacion();
        oASerHRRB = new AreaServicioHRRB();
    }
    
    public void buscar(){
        try {
            System.out.println("ENTRE A BUSCAR");
            System.out.println(oASerHRRB.getClave());
            //lHospitalizacion=oHosp.buscarPacientesHospitalizados();
        } catch (Exception ex) {
            Logger.getLogger(PacientesHospitalizadosCODE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }

    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }
    
   public List<AreaServicioHRRB> getListaAreasServicio(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasServicioUsuario()));
       } catch (Exception ex) {
           Logger.getLogger(hojaCODE.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }           

    /**
     * @return the oASerHRRB
     */
    public AreaServicioHRRB getASerHRRB() {
        return oASerHRRB;
    }

    /**
     * @param oASerHRRB the oASerHRRB to set
     */
    public void setASerHRRB(AreaServicioHRRB oASerHRRB) {
        this.oASerHRRB = oASerHRRB;
    }

    /**
     * @return the lHospitalizacion
     */
    public List<Hospitalizacion> getHospitalizacion() {
        return lHospitalizacion;
    }

    /**
     * @param lHospitalizacion the lHospitalizacion to set
     */
    public void setHospitalizacion(List<Hospitalizacion> lHospitalizacion) {
        this.lHospitalizacion = lHospitalizacion;
    }

    public int getTipo() {
        return nTipo;
    }

    public void setTipo(int nTipo) {
        this.nTipo = nTipo;
    }
       
    public String introduceTipo(int n){
        nTipo=n;
        String pag="/faces/sesiones/hospitalizacion/PacientesHospitalizados.xhtml";
        if (nTipo==1){
            sNombrePag="Hojas De Hospitalización Pendientes";
            oHosp.setTipo(2);
            pag="/faces/sesiones/hospitalizacion/PacientesHospitalizados.xhtml";
        }
        if (nTipo==2){
            sNombrePag="Modificar Estados De Salud De Pacientes Hospitalizados";
            oHosp.setTipo(4);
            pag="/faces/sesiones/hospitalizacion/PacientesHospitalizados.xhtml";
        }
        if (nTipo==3){
            sNombrePag="Reservar Cita A Pacientes Hospitalizados";
            oHosp.setTipo(4);
            pag="/faces/sesiones/hospitalizacion/PacientesHospitalizados.xhtml";
        }
        if (nTipo==4){
            sNombrePag="Hojas De Hospitalización Pendientes";
            oHosp.setTipo(1);
            pag="/faces/sesiones/hospitalizacion/PacientesHospitalizadosArea.xhtml";
        }
        if (nTipo==5){
            sNombrePag="Modificar Estados De Salud De Pacientes Hospitalizados";
            oHosp.setTipo(3);
            pag="/faces/sesiones/hospitalizacion/PacientesHospitalizadosArea.xhtml";
        }
        if (nTipo==6){
            sNombrePag="Reservar Cita A Pacientes Hospitalizados";
            oHosp.setTipo(3);
            pag="/faces/sesiones/hospitalizacion/PacientesHospitalizadosArea.xhtml";
        }
        if (nTipo==7){
            sNombrePag="Llenar Hoja Alta De Pacientes Hospitalizados";
            oHosp.setTipo(6);
            pag="/faces/sesiones/hospitalizacion/PacientesHospitalizados.xhtml";
        }
        if (nTipo==8){
            sNombrePag="Llenar Hoja Alta De Pacientes Hospitalizados";
            oHosp.setTipo(5);
            pag="/faces/sesiones/hospitalizacion/PacientesHospitalizadosArea.xhtml";
        }        
        return pag;
    }
    
    public String mostrarBotonCODES(){
        if (getTipo()==1 || getTipo()==4)
            return "";
        else
            return "display:none";
    }
    
    public String mostrarBotonEdoSalud(){
        System.out.println("nTipo==== "+nTipo);
        if (getTipo()==2 || getTipo()==5)
            return "";
        else
            return "display:none";
    }
    
    public String mostrarBotonReservaCita(){
        System.out.println("nTipo····· "+nTipo);
        if (getTipo()==3 || getTipo()==6)
            return "";
        else
            return "display:none";
    }
    
    public String mostrarBotonHojaAlta(){
        System.out.println("nTipo····· "+nTipo);
        if (getTipo()==7 || getTipo()==8)
            return "";
        else
            return "display:none";
    }    

    /**
     * @return the bEstado
     */
    public boolean getEstado() {
        return bEstado;
    }

    /**
     * @param bEstado the bEstado to set
     */
    public void setEstado(boolean bEstado) {
        this.bEstado = bEstado;
       
    }

    /**
     * @return the sNombrePag
     */
    public String getNombrePag() {
        return sNombrePag;
    }

    /**
     * @param sNombrePag the sNombrePag to set
     */
    public void setNombrePag(String sNombrePag) {
        this.sNombrePag = sNombrePag;
    }
}
