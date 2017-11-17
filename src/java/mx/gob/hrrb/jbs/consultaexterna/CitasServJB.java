/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Cita;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oCitasServicio")
@SessionScoped
public class CitasServJB implements Serializable{
private Cita oCita;
    /**
     * Creates a new instance of CitasServJB
     */
    public CitasServJB() {
    oCita=new Cita();
    }
    
    public List<AreaServicioHRRB> getListaAreasCE(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasCE()));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
//JMHG
    public List<AreaServicioHRRB> getListaAreasServ( String sClave ) //getListaAreasServImagen
    {
        List<AreaServicioHRRB> lLista = null;
        try {
            //lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                //(new AreaServicioHRRB()).buscarSubImg()));
            lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                (new AreaServicioHRRB()).buscarAreasServ( sClave )));
        } catch (Exception ex) {
            Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lLista;
    }
    
    public List<AreaServicioHRRB> getListaAreasServImagen() //INECESARIO
    {
        List<AreaServicioHRRB> lLista = null;
        try {
            lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                (new AreaServicioHRRB()).buscarSubImg()));
        } catch (Exception ex) {
            Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lLista;
    }
    //----
    
    
    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }
}
