/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.core;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oIdentificaUsu")
@RequestScoped
public class IdentificaUsu {

    /**
     * Creates a new instance of IdentificaUsu
     */
    private Usuario oUsuario;
    
    public IdentificaUsu() {
        oUsuario = new Usuario();
    }
    
    public void imprime(){
        System.out.println(oUsuario.getIdUsuario());
    }
    
}
