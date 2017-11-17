/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.recursosmateriales;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author DavidH
 */
@ManagedBean
@RequestScoped
public class ColorViewJB {

    private String colorInline;
    private String colorPopup;
 
    public String getColorInline() {
        return colorInline;
    }
 
    public void setColorInline(String colorInline) {
        this.colorInline = colorInline;
    }
 
    public String getColorPopup() {
        return colorPopup;
    }
 
    public void setColorPopup(String colorPopup) {
        this.colorPopup = colorPopup;
    } 
}
