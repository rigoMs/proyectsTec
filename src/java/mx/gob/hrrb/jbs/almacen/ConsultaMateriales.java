package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.almacen.Material;

/**
 *
 * @author GIL
 */
@ManagedBean(name="oConMat")
@ViewScoped
public class ConsultaMateriales implements Serializable {
private Material[] arrMat = null; 
private Material oMaterial;
private boolean encontrado = false;
private String nombre = null;
List<String> arrRet;
private List<Material> oMatFiltrado;

    public ConsultaMateriales(){
    oMaterial=new Material();
    Material oMat = new Material();
        try{
            arrMat = (Material[])oMat.buscarTodos();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<String> complete(String sTxt) {
        arrRet = new ArrayList<String>();
        List<Material> lis = getListaMaterial(sTxt);
        for (Material li : lis) {
            if (sp_ascii(li.getNombre()).contains(sTxt)) {
                arrRet.add(li.getNombre());
            }
        }

        return arrRet;
    }

    public List<Material> getListaMaterial(String txt) {
        List<Material> lListaProce = null;
        try {
            lListaProce = new ArrayList<Material>(Arrays.asList(new Material().buscarNombreComplete(txt)));
        } catch (Exception ex) {
            Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaProce;
    }

    public String sp_ascii(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public void eventobusc(ActionEvent a)throws Exception{
        buscaNombreMaterial();
        
    }
    
    public String buscaNombreMaterial() throws Exception { 
    oMaterial=new Material();
    String sNom = "";
    
        if (getNombre() != null) {
            oMaterial.setNombre(getNombre());
            if (oMaterial.buscaMaterial()) {
                setEncontrado(true);
            } else {
                setEncontrado(false);              
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                "Material", "No existe"));
            }
        }
        this.setNombre(null);
        return sNom;
    } 
     
    public Material[] getLista(){
        return arrMat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Material getMaterial() {
        return oMaterial;
    }

    public void setMaterial(Material oMaterial) {
        this.oMaterial = oMaterial;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public List<Material> getMatFiltrado() {
        return oMatFiltrado;
    }

    public void setMatFiltrado(List<Material> oMatFiltrado) {
        this.oMatFiltrado = oMatFiltrado;
    }
}
