/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.recursosmateriales;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author DavidH
 */
@ManagedBean (name="rptcarga")
@ViewScoped
public class CargareqJB {
    
    private int prueba;
    private boolean render;

    /**
     * Creates a new instance of Cargareq
     */
    public CargareqJB() {
        render=false;
    }

    /**
     * @return the prueba
     */
    public int getPrueba() {
        return prueba;
    }

    /**
     * @param prueba the prueba to set
     */
    public void setPrueba(int prueba) {
        render=true;
        this.prueba = prueba;
    }

    /**
     * @return the render
     */
    public boolean getRender() {
        return render;
    }

    /**
     * @param render the render to set
     */
    public void setRender(boolean render) {
        this.render = render;
    }
    
}
