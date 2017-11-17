package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.almacen.CabeceraControl;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.core.Destino;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import org.primefaces.event.RowEditEvent;
import mx.gob.hrrb.modelo.core.Proveedor;

/**
 *
 * @author GIL
 */
@ManagedBean(name = "oRegCabecera")
@ViewScoped
public class RegistraCabecera implements Serializable {

    private String nCveMat = null;
    private String nombre = null;

    private Material oMat;
    List<String> arrRet;
    private boolean encontrado = false;
    private ArrayList<CabeceraControl> lisInventario = new ArrayList<CabeceraControl>();
    private CabeceraControl oDetInv, matAux;
    private CabeceraControl[] arrDetMaterial = null;
    private float total = 0;
    private CabeceraControl oDetCabe = null;
    private CabeceraControl matAuxiliar;
    private CabeceraControl[] arrCabecera = null;
    private Material material;
    private CabeceraControl oCabecera;
    private int idPersonal;
    private ArrayList<CabeceraControl> lisCabecera = new ArrayList<CabeceraControl>();
    private String tipoAdquisicion;
    private Proveedor provedor;
    private Destino destino;
    private Parametrizacion parametros;
    private Parametrizacion arrLista[];
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private Boolean customExporter;

    public RegistraCabecera() {
        customExporter = false;
        oDetInv = new CabeceraControl();
        oDetCabe = new CabeceraControl();
        parametros = new Parametrizacion();
        oCabecera = new CabeceraControl();
        oFirm = new Firmado();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
        }
    }

    public String guardaCabecera() throws Exception {        
        CabeceraControl cabAux = new CabeceraControl();
        cabAux.setIdEntrada(oDetCabe.getIdEntrada());
        if (!cabAux.buscarLlaveEntrada()) {
            if (oDetCabe.insertarCabecera(sUsuario) == 1) {
                this.oDetCabe = new CabeceraControl();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ID ENRADA", "INSERTADO"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ID ENTRADA", "NO INSERTADO"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "ID DE ENTRADA YA EXISTE"));
        }
        cabAux = new CabeceraControl();
        return "registrarMovEntrada";

    }

    public Boolean busca() throws Exception {
        oDetCabe = new CabeceraControl();
        if (getIdPersonal() >= 0) {
            oDetCabe.getQuienRegistra().setNoTarjeta(idPersonal);
            if (oDetCabe.buscarQuienCaptura()) {
                encontrado = true;
            } else {
                encontrado = false;
                setIdPersonal(0);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "numero de tarjeta", "No existe"));
            }
        }
        return encontrado;
    }

    public void forzarEncontrado() {
        oDetCabe = new CabeceraControl();
        idPersonal = 0;
        encontrado = false;
    }

    public String setListaCabecera() throws Exception {
        if (oDetCabe.buscarLlaveEntrada()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "id Entrada Existente", ""));
            return "registrarMovEntrada";
        } else {
            lisCabecera.add(0, oDetCabe);
            oDetCabe = new CabeceraControl();
            encontrado = false;
            forzarEncontrado();
        }
        return null;
    }

    public boolean getOcultaCabecera() {
        return lisCabecera.size() > 0;
    }

    public String getSoloUnaVez() {
        int cd = 0;
        if (cd > 1) {
        }
        return "registrarMovEntrada";
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(" Editado", "Id Entrada:" + ((CabeceraControl) event.getObject()).getIdEntrada());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "Id Entrada: " + ((CabeceraControl) event.getObject()).getIdEntrada());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void identificadorSeleccionado(CabeceraControl m) {
        matAuxiliar = m;
    }

    public void limpia() {
        lisCabecera.clear();
    }

    public void limpiaControl() {
        lisInventario.clear();
    }

    public String remueveCabecera(int i) {
        if (i == 1) {
            lisCabecera.remove(matAux);
        } else {
            matAux = null;
        }
        return "";
    }

    public List<Proveedor> getProveedores() throws Exception {
        List<Proveedor> sPro = new ArrayList<Proveedor>();
        try {
            sPro = new ArrayList<Proveedor>(Arrays.asList(new Proveedor().buscarProveedor()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sPro;
    }

    public List<Destino> getDestinos() throws Exception {
        List<Destino> sDes = new ArrayList<Destino>();
        try {
            sDes = new ArrayList<Destino>(Arrays.asList(new Destino().buscarDestinos()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sDes;
    }

    public List<Parametrizacion> getAdquisicion() throws Exception {
        List<Parametrizacion> parametrizacion = new ArrayList<Parametrizacion>();
        try {
            parametrizacion = new ArrayList<Parametrizacion>(Arrays.asList(new Parametrizacion().buscarTipoAdquisicion()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return parametrizacion;
    }

    public String getBuscaClave() throws Exception {
        oMat = new Material();
        String sNom = "";
        if (getCveMat() != null) {
            oMat.setClaveMaterial(getCveMat());
            if (oMat.buscar()) {
                sNom += oMat.getClaveMaterial() + "-";
                sNom += oMat.getNombre();
                setCveMat(oMat.getClaveMaterial());
                encontrado = true;
                System.out.println(encontrado);
            } else {
                encontrado = false;
                System.out.println(encontrado);
                setCveMat(null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Codigo", "No existe"));
            }
        }
        return sNom;
    }

    public String setListaControl() throws Exception {
        if (oDetInv.getArrControlMaterial().get(0).getInv().getPrecio() < 0) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, 
                            "El precio no puede ser menor a 0", ""));
            return "registrarMovEntrada";
        }
        if (oDetInv.getArrControlMaterial().get(0).getInv().getExistencia()==0||
            oDetInv.getArrControlMaterial().get(0).getInv().getExistencia() < 0) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, 
                         "Cantidad no puede ser 0 o menor", " "));
            return "registrarMovEntrada";
        } else {
            if (oDetInv.getArrControlMaterial().get(0).getInv().getLote().isEmpty()) {
                oDetInv.getArrControlMaterial().get(0).getInv().setLote("S/L");
            }
            Material m = new Material();
            oDetInv.getArrControlMaterial().get(0).getInv().getMaterial().setClaveMaterial(oMat.getClaveMaterial());
            oDetInv.getArrControlMaterial().get(0).getInv().getMaterial().setNombre(oMat.getNombre());
            oDetInv.getArrControlMaterial().get(0).getInv().getMaterial().setPresentacion(oMat.getPresentacion());
            oDetInv.getArrControlMaterial().get(0).getInv().getMaterial().setConcentrado(oMat.getConcentrado());
            lisInventario.add(0, oDetInv);
            oMat = new Material();
            oDetInv = new CabeceraControl();
            encontrado = false;
            forzarEncontradoControl();
        }
       nombre=null;
        return null;
    }

    public boolean getOcultaTabla() {
        return lisInventario.size() > 0;
    }

    public String borraTablaProductos() throws Exception {
        lisInventario.clear();
        return "";
    }

    public void forzarEncontradoControl() {
        oMat = new Material();
        oDetInv = new CabeceraControl();
        nombre = null;
        encontrado = false;
    }
    public void valoresNulos(){
        nombre=null;
    }

    public void materialSeleccionado(CabeceraControl m) {
        matAux = m;
    }

    public String remueveMaterial(int i) {
        if (i == 1) {
            lisInventario.remove(matAux);
        } else {
            matAux = null;
        }
        return "";
    }

    public String guarda() {
        try {
            for (CabeceraControl lisInventario1 : lisInventario) {
                if (almacenaEntrada(lisInventario1) != 1) {
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        "No se inserto", "" + 
                        lisInventario1.getArrControlMaterial().get(0).getInv().getMaterial().getClaveMaterial() + ""));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistraCabecera.class.getName()).log(Level.SEVERE, null, ex);
        }
        oDetInv = new CabeceraControl();
        encontrado = false;
        lisInventario = new ArrayList<CabeceraControl>();
        return "registrarMovEntrada";

    }
    
    public int almacenaEntrada(CabeceraControl entrada) throws Exception {
        oDetInv = new CabeceraControl();
        int nAfec = 0;
        nAfec = entrada.insertarMaterialACabecera();
        entrada = null;
        return nAfec;
    }

    public void eventoGuardaCabecera(ActionEvent a) throws Exception {
        guardaPrincipal();
        guarda();
    }

    public String guardaPrincipal() throws Exception {
        for (CabeceraControl lisCabecera1 : lisCabecera) {
            if (almacena(lisCabecera1) != 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", " Lista no insertada :("));
            } else {
                oDetCabe = new CabeceraControl();
                encontrado = false;
                lisCabecera = new ArrayList<CabeceraControl>();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lista insertada ", ""));
            }
        }
        return "registrarMovEntrada";

    }

    public int almacena(CabeceraControl mat) throws Exception {
        oDetCabe = new CabeceraControl();
        int nAfec = 0;

        nAfec = mat.insertarCabecera(sUsuario);
        mat = null;
        return nAfec;
    }

    public void calculaTotal() {
        float total1 = 0;
        for (CabeceraControl lisInventario1 : lisInventario) {
            total1 += lisInventario1.getArrControlMaterial().get(0).getInv().getSubTotal();
        }
        setTotal(total1);
    }

    public float getTotal() {
        calculaTotal();
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void editarMaterial(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Material Editado", "Clave;" + 
                ((CabeceraControl) 
                event.getObject()).getArrControlMaterial().get(0).getInv().getMaterial().getClaveMaterial());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cancelarMaterial(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "Clave : " + 
            ((CabeceraControl) event.getObject()
            ).getArrControlMaterial().get(0).getInv().getMaterial().getClaveMaterial());
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
            lListaProce = new ArrayList<Material>(Arrays.asList(new Material().buscarNombreMaterial(txt)));
        } catch (Exception ex) {
            Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaProce;
    }

    public String sp_ascii(String input) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }

    public String fechaActual() {
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String a = anio + "";
        String hoy = dia + "/" + (mes + 1) + "/" + a.substring(2, 4);       
        return hoy;
    }

    public String fechaActualMasUno() {
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String a = anio + "";
        String hoy = dia + "/" + (mes + 1) + "/" + a.substring(2, 4);        
        return hoy;
    }
    
    public String getBuscaNombreMaterial() throws Exception { //busca el nombre  del material 

        oMat = new Material();
        String sNom = "";
        if (getNombre() != null) {

            oMat.setNombre(getNombre());
            if (oMat.buscarMaterialSalida()) {

                sNom += oMat.getNombre();
                setNombre(oMat.getNombre());
                encontrado = true;

                System.out.println(encontrado);

            } else {
                encontrado = false;
                System.out.println(encontrado);
                setNombre(null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Material", "No existe"));
            }
        }
       return sNom;
    }

    public CabeceraControl getMatAuxiliar() {
        return matAuxiliar;
    }

    public void setMatAuxiliar(CabeceraControl matAuxiliar) {
        this.matAuxiliar = matAuxiliar;
    }

    public Proveedor getProvedor() {
        return provedor;
    }

    public void setProvedor(Proveedor provedor) {
        this.provedor = provedor;
    }

    public String getTipoAdquisicion() {
        return tipoAdquisicion;
    }

    public void setTipoAdquisicion(String tipoAdquisicion) {
        this.tipoAdquisicion = tipoAdquisicion;
    }

    public Parametrizacion getParametros() {
        return parametros;
    }

    public void setParametros(Parametrizacion parametros) {
        this.parametros = parametros;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public String getCveMat() {
        return nCveMat;
    }

    public void setCveMat(String nCveMat) {
        this.nCveMat = nCveMat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Material getMat() {
        return oMat;
    }

    public void setMat(Material oMat) {
        this.oMat = oMat;
    }

    public ArrayList<CabeceraControl> getLisInventario() {
        return lisInventario;
    }

    public void setLisInventario(ArrayList<CabeceraControl> lisInventario) {
        this.lisInventario = lisInventario;
    }

    public CabeceraControl getDetInv() {
        return oDetInv;
    }

    public void setDetInv(CabeceraControl oDetInv) {
        this.oDetInv = oDetInv;
    }

    public CabeceraControl getMatAux() {
        return matAux;
    }

    public void setMatAux(CabeceraControl matAux) {
        this.matAux = matAux;
    }

    public CabeceraControl[] getArrDetMaterial() {
        return arrDetMaterial;
    }

    public void setArrDetMaterial(CabeceraControl[] arrDetMaterial) {
        this.arrDetMaterial = arrDetMaterial;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int valor) {
        this.idPersonal = valor;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public CabeceraControl getCabecera() {
        return oCabecera;
    }

    public void setCabecera(CabeceraControl oCabecera) {
        this.oCabecera = oCabecera;
    }

    public Parametrizacion[] getArrLista() {
        return arrLista;
    }

    public void setArrLista(Parametrizacion[] arrLista) {
        this.arrLista = arrLista;
    }

    public ArrayList<CabeceraControl> getListaCabecera() {
        return lisCabecera;
    }

    public ArrayList<CabeceraControl> getLisCabecera() {
        return lisCabecera;
    }

    public CabeceraControl getDetCabe() {
        return oDetCabe;

    }

    public void setDetCabe(CabeceraControl oDetCabe) {
        this.oDetCabe = oDetCabe;
    }

    public void setLisCabecera(ArrayList<CabeceraControl> lisCabecera) {
        this.lisCabecera = lisCabecera;
    }

    public Boolean getCustomExporter() {
        return customExporter;
    }

    public void setCustomExporter(Boolean customExporter) {
        this.customExporter = customExporter;
    }

}
