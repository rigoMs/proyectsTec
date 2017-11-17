/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.catalogos;

/**
 *
 * @author Aylin 1
 */

import java.text.ParseException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.CLUES;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "clues")
@SessionScoped

public class CluesJB {

    private CLUES clue;
    private CLUES[] lista;
  //  private String desc;
    private boolean visible1 = false;

    public CluesJB() {
        clue = new CLUES();
         cargalista();
    }

    private void cargalista() {
        try {
            lista = clue.buscarTodos();
            System.out.println("long "+lista.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CLUES[] getLista(){
        return lista;
    }

    public void onEdit(RowEditEvent event) throws Exception {
        CLUES es = (CLUES) event.getObject();
        int res = es.modificar();
        if (res == 1) {
            FacesMessage msg;
            //msg = new FacesMessage("Modificacion ", "Modificacion exitosa");
msg = new FacesMessage("Error", "Error al modificar");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg;
           // msg = new FacesMessage("Error", "Error al modificar");
msg = new FacesMessage("Modificacion ", "Modificacion exitosa");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCancel(RowEditEvent event) throws ParseException {
        FacesMessage msg;
        msg = new FacesMessage("Cancelado", "cancelación exitosa");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private List<CLUES> arrClueFiltrado;

    public List<CLUES> getPersFiltrado() {
        return arrClueFiltrado;
    }

    public void setPersFiltrado(List<CLUES> valor) {
        this.arrClueFiltrado = valor;
    }

    public boolean getVisible1() {
        return visible1;
    }

    public void setVisible1(boolean v) {
        visible1 = v;
    }

    public CLUES getClue() {
        return clue;
    }

    public CLUES setClue() {
        return clue;
    }

   /* public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
*/
  
            
    public String almacena() {

        int res = 0;
        try {
            res = clue.insertar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res == 1) {
            setVisible1(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "COMPLETADO", "Se inserto correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "al insertar"));
        }
       cargalista();
        return "CatalogoClues.xhtml";
    }

}
