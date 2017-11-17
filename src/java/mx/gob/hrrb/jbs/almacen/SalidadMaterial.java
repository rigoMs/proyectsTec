/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import mx.gob.hrrb.modelo.almacen.CabeceraControl;
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author GIL
 */
@ManagedBean(name = "oSalidad")
@ViewScoped
public class SalidadMaterial implements Serializable {
private String sCveMat = "";
private String lote = "";
private String nombre = null;
private CabeceraControl oDetInv = null;
private ArrayList<InventarioMateriales> listaDetalleInv = new ArrayList<InventarioMateriales>();
private InventarioMateriales oInventario = null;
private InventarioMateriales invAux;
private InventarioMateriales[] listaLotes, materiales, matBusNom = null;
private String nombreBusqueda = "";
private boolean matEncontrado = false;
private boolean encontrado = false;
private Parametrizacion parametros;
private boolean nombreEncontrado = false;
private Date fecha = null;
private Material oMat;
List<String> arrRet;

    public SalidadMaterial() {
        oInventario = new InventarioMateriales();
        oDetInv = new CabeceraControl();
        oMat = new Material();
        parametros=new Parametrizacion(); 
        fecha = new Date();
    }

    public String getBuscaNombreMaterial() throws Exception {
    oMat = new Material();
    String sNom = "";
        if (getNombre() != null) {
            oMat.setNombre(getNombre());
            if (oMat.buscarMaterialSalida()) {
                sNom += oMat.getNombre();
                setNombre(oMat.getNombre());
                encontrado = true;
            } else {
                encontrado = false;
                setNombre(null);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Material",
                                "No existe"));
            }
        }
        return sNom;
    }
    
    public void setListaSalida(ActionEvent ae) throws Exception {
        if(oInventario.getCant()==0){
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", 
                            "INGRESE CANTIDAD MAYOR A CERO "));
        }
        if (oInventario.getTipoLotes().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", 
                            "SIN LOTES....MATERIAL SIN EXISTENCIA "));
        }
        if(oInventario.notificaExistenciaNegativa(oMat.getClaveMaterial()))
            if(oInventario.getCant()>oInventario.getExistencia()){
                FacesContext.getCurrentInstance().addMessage(null, 
                 new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", 
                         "CANTIDAD EXCEDIDA INSUFICIENTE EN EL INVENTARIO "
                                +"TOTAL :"+oInventario.getExistencia()));
        }else{
            oInventario.getMaterial().setClaveMaterial(oMat.getClaveMaterial());
            oInventario.getMaterial().setNombre(oMat.getNombre());
            oInventario.getMaterial().setPresentacion(oMat.getPresentacion());
            listaDetalleInv.add(0,oInventario);
            oMat = new Material();
            forzarEncontrado(); 
        }
        this.setNombre(null);
    }
    
    public void forzarEncontrado() {
        oInventario = new InventarioMateriales();
        encontrado = false;
    }

    public String remueveInventario(int i) {
        if (i == 1) {
            listaDetalleInv.remove(invAux);
        } else {
            invAux = null;
        }
        return "";
    }
   
    public void limpiaSalidad() {
        listaDetalleInv.clear();
    }

    public boolean getOculta() {
        return listaDetalleInv.size()>=0;
    }

    public void verLista() {
        for (InventarioMateriales listaDetalleInv1 : listaDetalleInv) {
            System.out.println("...." + listaDetalleInv1.getMaterial().getClaveMaterial());
        }
    }

    public String borraTablaSalidad() throws Exception {
        if (listaDetalleInv==null || listaDetalleInv.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "NO HAY REGISTRADOS "));
            return "registrarMovSalida";
        } else {
            listaDetalleInv.clear();
            return "";
        }
    }

    public ArrayList<InventarioMateriales> getListaSalidad() {
        return listaDetalleInv;
    }

//metodo para que me muestre los lotes disponibles
    public List<InventarioMateriales> getLotesInventario() throws Exception {
        List<InventarioMateriales> sLot = new ArrayList<InventarioMateriales>();
        try {
            sLot = new ArrayList<InventarioMateriales>(Arrays.asList(
                    new Material().buscaLotes(oMat.getClaveMaterial())));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sLot;
    }

    public List<String> complete(String sTxt) {
        arrRet = new ArrayList<String>();
        List<InventarioMateriales> lis = getListaMaterial(sTxt);
        for (InventarioMateriales li : lis) {
            if (sp_ascii(li.getMaterial().getNombre()).contains(sTxt)) {
                arrRet.add(li.getMaterial().getNombre());
            }
        }
        return arrRet;
    }

    public List<InventarioMateriales> getListaMaterial(String txt) {
        List<InventarioMateriales> lListaProce = null;
        try {
            lListaProce = new ArrayList<InventarioMateriales>(Arrays.asList(
                    new InventarioMateriales().buscarNombreMaterial(txt)));
        } catch (Exception ex) {
            Logger.getLogger(Material.class.getName()).log(Level.SEVERE,null,ex);
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

    public void reset() {
        RequestContext.getCurrentInstance().reset("principal:nombreMat");
    }

    public boolean ValidacionEntero(String cadena) {
        int num;
        try {
            num = Integer.parseInt(cadena.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
//--------------------------------------------------------------------------------------------------
    public List<Parametrizacion> getTipoMotivo() throws Exception {
        List<Parametrizacion> parametrizacion = new ArrayList<Parametrizacion>();
        try {
            parametrizacion = new ArrayList<Parametrizacion>(Arrays.asList(
                    new Parametrizacion().buscarTipoMotivoMaterial()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return parametrizacion;
    }
    
    ////-----------------------------------------------------------------------------------------
         public List<SelectItem> getMotivo() throws Exception {
        List<SelectItem> sMot = new ArrayList<SelectItem>();
        Parametrizacion parametrizaciones[] = null;

        parametrizaciones = (new Parametrizacion()).buscarTipoMotivoMaterial();
        if (parametrizaciones != null) {
            for (Parametrizacion n : parametrizaciones) {
                sMot.add(new SelectItem(n.getClaveParametro(), n.getValor()));
            }
        } else {
            parametrizaciones = (new Parametrizacion()).buscarTodos();
            for (Parametrizacion n : parametrizaciones) {
                sMot.add(new SelectItem(n.getClaveParametro(), n.getValor()));
            }
        }
        return sMot;
    }
    
    public void onRowEditCant(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(" Editado", "Clave Material:" + 
                ((InventarioMateriales) 
                        event.getObject()).getMaterial().getClaveMaterial());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
         
    //----------------------------------------------------------------
     public void onRowEdit(RowEditEvent event)throws Exception{
        if(oInventario.notificaExistenciaNegativaOnEdit(((InventarioMateriales) 
                event.getObject()).getMaterial().getClaveMaterial(),
                ((InventarioMateriales)event.getObject()).getTipoLotes()))
            if(((InventarioMateriales) event.getObject()).getCant()>
                    oInventario.getExistencia()){                 
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", 
                               "CANTIDAD EXCEDIDA INSUFICIENTE EN EL INVENTARIO"
                                +"TOTAL :"+oInventario.getExistencia()));            
                 ((InventarioMateriales) event.getObject()).setCant(1);
            }else{    
                if(((InventarioMateriales) event.getObject()).getCant()==0){
                    FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR", 
                                    "DEBE SE MAYOR A CERO LA CANTIDAD :( "));
                    ((InventarioMateriales) event.getObject()).setCant(1);
                }
                else{
                    FacesMessage msg = new FacesMessage("Editado", 
                            "Clave Material:" + 
                           ((InventarioMateriales) event.getObject()
                                   ).getMaterial().getClaveMaterial());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }   
            }
    }
     
    public void onRowCancel(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Edicion Cancelada", 
                "Clave Material: " + 
                ((InventarioMateriales) event.getObject()
                        ).getMaterial().getClaveMaterial());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //***************************************************************************************/
    
    public void guardarSalida(ActionEvent ae){  
    InventarioMateriales oInv= new InventarioMateriales();
    int nVal=0;
        for (int i = 0; i < listaDetalleInv.size(); i++) {
            try{
                nVal = oInv.insertarSalidaMaterial( 
                        listaDetalleInv.get(i).getMaterial().getClaveMaterial(),
                        listaDetalleInv.get(i).getTipoLotes(), 
                        listaDetalleInv.get(i).getCant(),
                        listaDetalleInv.get(i).getFecha(),
                        listaDetalleInv.get(i).getTipoMovimiento() );
                if (nVal != 1){
                    FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Error", 
                                    listaDetalleInv.get(i).getMaterial().getClaveMaterial() + " No se insertó"));
                    break;
                }
                listaDetalleInv.clear();
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Correcto", "Material insertado"));
            }catch(Exception e){
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Error", "Error al almacenar el material"));
            }
        }
    }

    public void forzarEncontradoSalidad() {
        oMat = new Material();
        oDetInv = new CabeceraControl();
        oInventario = new InventarioMateriales();
        nombre = null;
        encontrado = false;
    }

    public String getCveMat() {
        return sCveMat;
    }

    public void setCveMat(String sCveMat) {
        this.sCveMat = sCveMat;
    }

    public CabeceraControl getDetInv() {
        return oDetInv;
    }

    public void setDetInv(CabeceraControl oDetInv) {
        this.oDetInv = oDetInv;
    }

    public ArrayList<InventarioMateriales> getListaDetalleInv() {
        return listaDetalleInv;
    }

    public void setListaDetalleInv(ArrayList<InventarioMateriales> listaDetalleInv) {
        this.listaDetalleInv = listaDetalleInv;
    }

    public InventarioMateriales[] getListaLotes() {
        return listaLotes;
    }

    public void setListaLotes(InventarioMateriales[] listaLotes) {
        this.listaLotes = listaLotes;
    }

    public InventarioMateriales[] getMateriales() {
        return materiales;
    }

    public void setMateriales(InventarioMateriales[] materiales) {
        this.materiales = materiales;
    }

    public InventarioMateriales[] getMatBusNom() {
        return matBusNom;
    }

    public void setMatBusNom(InventarioMateriales[] matBusNom) {
        this.matBusNom = matBusNom;
    }

    public String getNombreBusqueda() {
        return nombreBusqueda;
    }

    public void setNombreBusqueda(String nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }

    public boolean isMatEncontrado() {
        return matEncontrado;
    }

    public void setMatEncontrado(boolean matEncontrado) {
        this.matEncontrado = matEncontrado;
    }

    public boolean isNombreEncontrado() {
        return nombreEncontrado;
    }

    public void setNombreEncontrado(boolean nombreEncontrado) {
        this.nombreEncontrado = nombreEncontrado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean esEntero(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean getAlmenosUnoEnLista() {
        return listaDetalleInv.size() > 0;
    }

    
    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public Material getMat() {
        return oMat;
    }

    public void setMat(Material oMat) {
        this.oMat = oMat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public InventarioMateriales getInventario() {
        return oInventario;
    }

    public void setInventario(InventarioMateriales oInventario) {
        this.oInventario = oInventario;
    }
}
