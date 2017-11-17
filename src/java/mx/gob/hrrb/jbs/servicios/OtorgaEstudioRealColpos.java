/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.servicios;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Pablo
 */
@ManagedBean
@ViewScoped
public class OtorgaEstudioRealColpos extends OtorgarServicio implements Serializable {

    /**
     * Creates a new instance of OtorgaEstudioRealColpos
     */
    public OtorgaEstudioRealColpos() {
    }

    @Override
    public List buscarDetalleSolicitud() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void listaOrdenes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
