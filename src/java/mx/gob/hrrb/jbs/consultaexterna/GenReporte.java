/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.consultaexterna;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.core.Cita;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oGenRepor")
@ViewScoped
public class GenReporte {
private Cita oCita;
private Cita[] arrCitas1, arrCitas2, arrCitas3, arrCitas4, arrCitas5, 
            arrCitas6, arrCitas7, arrCitas8, arrCitas9, arrCitas10,
            arrCitas11, arrCitas12, arrCitas13, arrCitas14, arrCitas15,
            arrCitas16, arrCitas17, arrCitas18;
//18 consultorios, colocar gets y actionlistener para el botón
    /**
     * Creates a new instance of GenReporte
     */
    public GenReporte() {
        oCita=new Cita();
    }
    
    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }
    
    public void buscaCitas(ActionEvent ae){
    Cita[] arrCita=null;
        try{
            arrCitas1 = oCita.buscarReporteCE(1);
            arrCitas2 = oCita.buscarReporteCE(2);
            arrCitas3 = oCita.buscarReporteCE(3);
            arrCitas4 = oCita.buscarReporteCE(4);
            arrCitas5 = oCita.buscarReporteCE(5);
            arrCitas6 = oCita.buscarReporteCE(6);
            arrCitas7 = oCita.buscarReporteCE(7);
            arrCitas8 = oCita.buscarReporteCE(8);
            arrCitas9 = oCita.buscarReporteCE(9);
            arrCitas10 = oCita.buscarReporteCE(10);
            arrCitas11 = oCita.buscarReporteCE(11);
            arrCitas12 = oCita.buscarReporteCE(12);
            arrCitas13 = oCita.buscarReporteCE(13);
            arrCitas14 = oCita.buscarReporteCE(14);
            arrCitas15 = oCita.buscarReporteCE(15);
            arrCitas16 = oCita.buscarReporteCE(16);
            arrCitas17 = oCita.buscarReporteCE(17);
            arrCitas18 = oCita.buscarReporteCE(18);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Cita[] getArrCitas1() {
        return arrCitas1;
    }

    public Cita[] getArrCitas2() {
        return arrCitas2;
    }

    public Cita[] getArrCitas3() {
        return arrCitas3;
    }

    public Cita[] getArrCitas4() {
        return arrCitas4;
    }

    public Cita[] getArrCitas5() {
        return arrCitas5;
    }

    public Cita[] getArrCitas6() {
        return arrCitas6;
    }

    public Cita[] getArrCitas7() {
        return arrCitas7;
    }

    public Cita[] getArrCitas8() {
        return arrCitas8;
    }

    public Cita[] getArrCitas9() {
        return arrCitas9;
    }

    public Cita[] getArrCitas10() {
        return arrCitas10;
    }

    public Cita[] getArrCitas11() {
        return arrCitas11;
    }

    public Cita[] getArrCitas12() {
        return arrCitas12;
    }

    public Cita[] getArrCitas13() {
        return arrCitas13;
    }

    public Cita[] getArrCitas14() {
        return arrCitas14;
    }

    public Cita[] getArrCitas15() {
        return arrCitas15;
    }

    public Cita[] getArrCitas16() {
        return arrCitas16;
    }

    public Cita[] getArrCitas17() {
        return arrCitas17;
    }

    public Cita[] getArrCitas18() {
        return arrCitas18;
    }
}
