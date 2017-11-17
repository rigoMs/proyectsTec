/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import mx.gob.hrrb.modelo.core.Causes;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.jbs.consultaexterna.ProgConsultorios;
import mx.gob.hrrb.modelo.admin.ExtractorCAUSES;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author juan
 */
@ManagedBean(name = "gesCauses")
@ViewScoped
public class GestionCauses implements Serializable {

    private final static String RUTA_CAUSES = "/resources/CAUSES";
    private final static String NOMBRE_ARCHIVO_CAUSES = "CAUSES.pdf";

    private Causes oCauses;     //aqui se guarda todo lo relacionado a una intervención de causes
    private Causes oCausesB;    //sirve para buscar en el catálogo de causes
    private ArrayList<Causes> lCausesEncontrados; //todos los causes de un anio
    private ArrayList<Causes> lCausesEncontradosFiltro; //copia para el filtro
    private Medicamento oNuevoMedicamento;
    private Material oNuevoMaterial;
    private ArrayList<ServicioCobrable> lServiciosCobrablesCie9;
    private ArrayList<ServicioCobrable> lServiciosCobrablesCie10;
    private boolean bMostrarNuevo;
    private ServicioCobrable oServCob;
    private UploadedFile fArchivoPdf;
    
    private ExtractorCAUSES oExtractor;

    private LazyDataModel<DiagnosticoCIE10> lListaCie10;    //para filtrar y modificar los cie10
    private LazyDataModel<ProcedimientoCIE9> lListaCie9;        //para filtrar y  modificar los cie9
    /**
     * constructor
     */
    public GestionCauses() {
        bMostrarNuevo = true;
        oCauses = new Causes();
        oCauses.iniciar();
        oCausesB = new Causes();
        oCausesB.iniciar();
        oNuevoMedicamento = new Medicamento();
        oNuevoMaterial = new Material();
        oServCob = new ServicioCobrable();
        lServiciosCobrablesCie9 = new ArrayList<>();
        lServiciosCobrablesCie10= new ArrayList<>();
        lCausesEncontrados = new ArrayList<>();
        lCausesEncontradosFiltro = new ArrayList<>();
        oExtractor= new ExtractorCAUSES();
        
    }
    @PostConstruct
    public void init(){
        cargarListasCie10();
        cargarListasCie9();
        cargaServiciosCobrables();
    }

    private void cargarListasCie10() {
        try {
            lListaCie10 = new LazyCIE10DataModel(oCauses.getDiagnosticoCIE10().buscarTodos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarListasCie9() {
        try {
            lListaCie9 = new LazyCIE9DataModel(oCauses.getProcedimientoCIE9().buscarTodosCie9());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargaServiciosCobrables() {
        try {
            lServiciosCobrablesCie9.addAll(Arrays.asList(oServCob.buscarServiciosCie9()));
            lServiciosCobrablesCie10.addAll(Arrays.asList(oServCob.buscarServiciosCie10()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void nuevoCauses() {
        int res = 0;
        boolean existe = false;
        String datosFaltantes = datosFaltantesCauses();
        oCausesB.setClave(oCauses.getClave());
        
        if (datosFaltantes.equals("")) {
            try {
                existe = this.oCausesB.buscarCause();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!existe) {
                try {
                    res = this.oCauses.insertar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (res > 0) {
                    mensajeGrowl("Éxito", "Registro exitoso");
                }
            } else {
                mensajeGrowl("Error", "Ya existe la intervención");
            }
        } else {

            String[] err = datosFaltantes.split("\n");
            for (String e : err) {
                mensajeGrowl("Faltan datos", e);
            }
        }
    }

    /**
     *
     */
    public void modificaCauses() {
        int res = 0;
        boolean existe = false;
        String datosFaltantes = datosFaltantesCauses();
        if (datosFaltantes.equals("")) {
            try {
                existe = this.oCausesB.buscarCause();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (existe) {
                try {
                    res = this.oCauses.modificar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (res > 0) {
                    mensajeGrowl("Éxito", "Modificación exitosa");
                }
            } else {
                mensajeGrowl("Error", "Ya existe la intervención");
            }
        } else {

            String[] err = datosFaltantes.split("\n");
            for (String e : err) {
                mensajeGrowl("Faltan datos", e);
            }
        }
    }

    private String datosFaltantesCauses() {
        String datosFaltantes = "";

        if (this.oCauses.getClave() == null || this.oCauses.getClave().equals("")) {
            datosFaltantes += "Falta la clave de la intervención\n";
        } else {
            this.oCauses.setClave(oCauses.getClave().toUpperCase());
        }

        if (this.oCauses.getAnio() < 1) {
            datosFaltantes += "Falta el año\n";
        }

        if (this.oCauses.getDescripcion() == null || this.oCauses.getDescripcion().equals("")) {
            datosFaltantes += "Falta el nombre de la intervención\n";
        } else {
            this.oCauses.setDescripcion(oCauses.getDescripcion().toUpperCase());
        }

        if (this.oCauses.getDiagnosticoCIE10() == null
                || this.oCauses.getDiagnosticoCIE10().getListaDiagcve() == null) {
            datosFaltantes += "Error en cargar los Cie10\n";
        }

        if (this.oCauses.getProcedimientoCIE9() == null
                || this.oCauses.getProcedimientoCIE9().getListaProcecve() == null) {
            datosFaltantes += "Error en cargar los cie9\n";
        }

        if (this.oCauses.getProcedimientoCIE9().getListaProcecve().isEmpty()
                && this.oCauses.getDiagnosticoCIE10().getListaDiagcve().isEmpty()) {
            datosFaltantes += "Falta agregar al menos un procedimiento CIE-9-MC o un diagnóstico CIE-10";
        }

        return datosFaltantes;
    }

    private boolean datosCompletosCie9(ProcedimientoCIE9 cie9) {
        boolean datosCompletos = false;

        if (cie9.getClaveCie() != null
                || !cie9.getClaveCie().equals("")) {
            if (cie9.getDescripcion() != null
                    || !cie9.getDescripcion().equals("")) {
                datosCompletos = true;
                cie9.setClaveCie(cie9.getClaveCie().toUpperCase());
                cie9.setDescripcion(cie9.getDescripcion().toUpperCase());
            } else {
                mensajeGrowl("Faltan datos", "Falta la descripción");
            }
        } else {
            mensajeGrowl("Faltan datos", "Falta la clave");
        }

        return datosCompletos;
    }

    private boolean datosCompletosCie10(DiagnosticoCIE10 cie10) {
        boolean datosCompletos = false;

        if (cie10.getClave() != null
                || !cie10.getClave().equals("")) {
            if (cie10.getDescripcionDiag() != null
                    || !cie10.getDescripcionDiag().equals("")) {
                datosCompletos = true;
                cie10.setClave(cie10.getClave().toUpperCase());
                try{
                cie10.setDescripcionDiag(cie10.getDescripcionDiag().toUpperCase());
                }catch(Exception e){
                    e.printStackTrace();
                    mensajeGrowl("Error en bd", "Error al leer CIE10");
                }
            } else {
                mensajeGrowl("Faltan datos", "Falta descripción");
            }
        } else {
            mensajeGrowl("Faltan datos", "Falta clave");
        }
        return datosCompletos;
    }

    /**
     * mensajeGrowl("cabecera de mensaje","mensaje")
     */
    private void mensajeGrowl(String tipo, String sMensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(tipo, sMensaje));
    }

    /**
     * inserta el nuevo cie9
     *
     * @param cie9 El objeto con clave y descripción llenos
     */
    public void nuevoCie9(ProcedimientoCIE9 cie9) {

        int res = 0;
        boolean existe = false;

        if (datosCompletosCie9(cie9)) {
            try {
                existe = cie9.buscar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!existe) {
                try {
                    res = cie9.insertar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (res == 1) {
                    cargarListasCie9();
                    cie9.setClaveCie("");
                    cie9.setDescripcion("");
                    cie9.setRequiereQx(false);
                    mensajeGrowl("Éxito", "Registro exitoso");
                }
            } else {
                mensajeGrowl("Error", "Ya existe el Cie9");
            }
        }

    }

    /**
     * modifica un cie-9-mc
     *
     * @param evento
     */
    public void modificaCie9(RowEditEvent evento) {
        int res = 0;
        ProcedimientoCIE9 cie9 = (ProcedimientoCIE9) evento.getObject();
        //System.out.println(cie9.getRequiereQx() + cie9.getClaveCie() + cie9.getClaveAnterior() + cie9.getDescripcionSimple());

        if (datosCompletosCie9(cie9)) {

            try {
                res = cie9.modificar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (res == 1) {
                mensajeGrowl("Éxito", "Modificación exitosa");
            }

        }
    }

    public void visualizaCie9(RowEditEvent evento) {
        int res = 0;
        ProcedimientoCIE9 cie9 = ((ProcedimientoCIE9) evento.getObject());
        //System.out.println(cie9.getRequiereQx() + cie9.getClaveCie() + cie9.getDescripcionSimple());

    }

    /**
     * inserta el nuevo cie10
     */
    public void nuevoCie10(DiagnosticoCIE10 cie10) {
        int res = 0;
        boolean existe = true;
        if (datosCompletosCie10(cie10)) {
            try {
                existe = cie10.buscar();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!existe) {
                try {
                    res = cie10.insertar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (res == 1) {
                    mensajeGrowl("Éxito", "Registro exitoso de " + cie10.getDescripcionDiag());
                    cargarListasCie10();
                    cie10.setClave("");
                    try{
                    cie10.setDescripcionDiag("");
                    }catch(Exception e){
                        e.printStackTrace();
                        mensajeGrowl("Error en bd", "Error al leer CIE10");
                    }
                    cie10.setEsCapasits(false);
                }
                cargarListasCie10();
            } else {
                mensajeGrowl("Error", "Ya existe el Cie10 " + cie10.getDescripcionDiag());
            }
        }

    }

    /**
     * modifica un cie-10 por su clave
     *
     * @param event
     */
    public void modificaCie10(RowEditEvent event) {
        int res = 0;
        DiagnosticoCIE10 cie10 = ((DiagnosticoCIE10) event.getObject());

        if (datosCompletosCie10(cie10)) {

            try {
                res = cie10.modificar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (res == 1) {
                mensajeGrowl("Éxito", "Modificación exitosa");
            }
            cargarListasCie10();

        }
    }

    /**
     * agrega un cie-9 a la intervención de CAUSES actual a partir de su clave
     */
    public void agregaCie9() {
        boolean existeCie = false;
        boolean agregado = false;

        if (this.oCauses.getProcedimientoCIE9().getClaveCie() != null
                && !this.oCauses.getProcedimientoCIE9().getClaveCie().equals("")) {
            this.oCauses.getProcedimientoCIE9().setClaveCie(oCauses.getProcedimientoCIE9().getClaveCie().toUpperCase());
            ProcedimientoCIE9 oCie9 = new ProcedimientoCIE9();
            oCie9.setClaveCie(this.oCauses.getProcedimientoCIE9().getClaveCie());
            try {
                existeCie = oCie9.buscar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (ProcedimientoCIE9 proc : oCauses.getProcedimientoCIE9().getListaProcecve()) {
                if (proc.getClaveCie().trim().equals(oCauses.getProcedimientoCIE9().getClaveCie())) {
                    agregado = true;
                    break;
                }
            }

            if (existeCie) {
                if (!agregado) {
                    this.oCauses.getProcedimientoCIE9()
                            .getListaProcecve().add(oCie9);
                    this.oCauses.getProcedimientoCIE9().setClaveCie("");
                } else {
                    mensajeGrowl("Error", "Ya se insertó esa clave");
                }
            } else {
                mensajeGrowl("Error", "El numero de cie9 no existe, por favor registre el cie9 completo antes de agregarlo");
            }
        } else {
            mensajeGrowl("Faltan datos", "No insertó una clave");
        }

    }

    public boolean removerCie9(ProcedimientoCIE9 cie9) {
        return this.oCauses.getProcedimientoCIE9().getListaProcecve().remove(cie9);
    }

    /**
     * agrega un cie-10 a la intervención de CAUSES actual a partir de su clave
     */
    public void agregaCie10() {
        boolean existeCie = false;
        boolean agregado = false;
        if (this.oCauses.getDiagnosticoCIE10().getClave() != null
                && !this.oCauses.getDiagnosticoCIE10().getClave().equals("")) {
            this.oCauses.getDiagnosticoCIE10().setClave(oCauses.getDiagnosticoCIE10().getClave().toUpperCase());
            DiagnosticoCIE10 oCie10 = new DiagnosticoCIE10();
            oCie10.setClave(this.getCauses().getDiagnosticoCIE10().getClave());

            try {
                existeCie = oCie10.buscar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (DiagnosticoCIE10 diag : oCauses.getDiagnosticoCIE10().getListaDiagcve()) {
                if (diag.getClave().trim().equals(oCauses.getDiagnosticoCIE10().getClave())) {
                    agregado = true;
                    break;
                }
            }

            if (existeCie) {
                if (!agregado) {
                    this.oCauses.getDiagnosticoCIE10()
                            .getListaDiagcve().add(oCie10);
                    this.oCauses.getDiagnosticoCIE10().setClave("");
                } else {
                    mensajeGrowl("Error", "Ya se insertó esa clave");
                }
            } else {
                mensajeGrowl("Error", "El numero de cie10 no existe, por favor registre el cie10 completo antes de agregarlo");
            }

        } else {
            mensajeGrowl("Faltan datos", "No insertó una clave");
        }

    }

    public boolean removerCie10(DiagnosticoCIE10 cie10) {
        return this.oCauses.getDiagnosticoCIE10().getListaDiagcve().remove(cie10);
    }

    /**
     * agrega un medicamento a la intervención de CAUSES actual a partir de su
     * clave
     */
    public void agregaMedicamento() {
        boolean existeMed = false;
        boolean agregado = false;
        if (this.oNuevoMedicamento.getClaveMedicamento() != null
                && !this.oNuevoMedicamento.getClaveMedicamento().equals("")) {
            this.oNuevoMedicamento.setClave(oNuevoMedicamento.getClaveMedicamento().toUpperCase());

            try {
                existeMed = oNuevoMedicamento.buscarPorClave();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Medicamento med : oCauses.getMedicamentos()) {
                if (med.getClaveMedicamento().equals(oNuevoMedicamento.getClaveMedicamento())) {
                    agregado = true;
                    break;
                }
            }

            if (existeMed) {
                if (!agregado) {
                    this.oCauses.getMedicamentos().add(oNuevoMedicamento);
                    oNuevoMedicamento = new Medicamento();
                } else {
                    mensajeGrowl("Error", "Ya se insertó ese medicamento");
                }
            } else {
                mensajeGrowl("Error", "El medicamento no está registrado, por favor registrelo antes de agregarlo");
            }

        } else {
            mensajeGrowl("Faltan datos", "No insertó una clave");
        }

    }

    public boolean removerMedicamento(Medicamento med) {
        return this.oCauses.getMedicamentos().remove(med);
    }

    /**
     * agrega un material a la intervención de CAUSES actual a partir de su
     * clave
     */
    public void agregaMaterial() {
        boolean existeMat = false;
        boolean agregado = false;
        if (this.oNuevoMaterial != null
                && !this.oNuevoMaterial.getClaveMaterial().equals("")) {
            this.oNuevoMaterial.setClave(oNuevoMaterial.getClaveMaterial().toUpperCase());

            try {
                existeMat = oNuevoMaterial.buscar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Material med : oCauses.getMateriales()) {
                if (med.getClaveMaterial().equals(oNuevoMaterial.getClaveMaterial())) {
                    agregado = true;
                    break;
                }
            }

            if (existeMat) {
                if (!agregado) {
                    this.oCauses.getMateriales().add(oNuevoMaterial);
                    oNuevoMaterial = new Material();
                } else {
                    mensajeGrowl("Error", "Ya se insertó ese material");
                }
            } else {
                mensajeGrowl("Error", "El material no está registrado, por favor registrelo antes de agregarlo");
            }

        } else {
            mensajeGrowl("Faltan datos", "No insertó una clave");
        }

    }

    public boolean removerMaterial(Material med) {
        return this.oCauses.getMateriales().remove(med);
    }

    public List<Parametrizacion> getListaAreas() {
        List<Parametrizacion> lLista = null;
        try {
            lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion().buscarTabla("T46"))));
        } catch (Exception ex) {
            Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lLista;
    }

    public void editarCause(String clave) {
        //System.out.println("editando "+clave);
        bMostrarNuevo = false;
        setCauses(new Causes());
        getCauses().iniciar();
        
        oCausesB.setClave(clave); //para cuando valide que ya existe no se pierdan los datos del causes ya editado
        
        oCauses.setAnio(oCausesB.getAnio());
        oCauses.setClave(clave);

        try {
            oCauses.buscarCause();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void editarNuevo(){
        this.oCauses.iniciar();
        this.oCausesB.iniciar();
        this.bMostrarNuevo=true;
    }

    public void buscarCauses() {

        oCauses.setAnio(oCausesB.getAnio());

        try {
            this.lCausesEncontrados = new ArrayList(oCauses.buscarTodos());
            this.lCausesEncontradosFiltro = (ArrayList<Causes>) this.lCausesEncontrados.clone();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cargaArchivo(FileUploadEvent event) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String sRuta = facesContext.getExternalContext().getRealPath("") + GestionCauses.RUTA_CAUSES,
                sTxtMsj;
        OutputStream out = null;
        InputStream filecontent = null;
        FacesMessage msg;
        //System.out.println(sRuta);
        try {
            uploadFile(event, sRuta, NOMBRE_ARCHIVO_CAUSES);
            sTxtMsj = NOMBRE_ARCHIVO_CAUSES + " almacenado.";
            msg = new FacesMessage("Resultado", sTxtMsj);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            e.printStackTrace();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", event.getFile().getFileName() + " no almacenado."));
            //System.out.println("Error no almacenado" + event.getFile().getFileName());
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }

    private void uploadFile(FileUploadEvent event, String sRuta, String sNomArch) throws IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        byte[] bytes = new byte[1024];
        int nLeidos = 0;
        try {
            out = new FileOutputStream(new File(sRuta + File.separator
                    + sNomArch));
            filecontent = event.getFile().getInputstream();
            while ((nLeidos = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, nLeidos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }

    /**
     *
     */
    public void cargaDatos() {
        ExtractorCAUSES extractor = new ExtractorCAUSES();
        String direccion, ruta;
        File archivo;

        ExternalContext extCont = FacesContext.getCurrentInstance().getExternalContext();
        ruta = extCont.getRealPath("")+GestionCauses.RUTA_CAUSES;
        //System.out.println(ruta);
        direccion = ruta +"/"+ GestionCauses.NOMBRE_ARCHIVO_CAUSES;
        //System.out.println(direccion);

        archivo = new File(direccion);

        if (archivo.exists()) {

            extractor.setDireccionArchivo(direccion);

            if (extractor.extraerTodosCauses()) {
                
                //extractor.guardarTodosCauses();
                for(Causes cause: extractor.getListaCauses()){
                    this.setCauses(cause);
                    this.nuevoCauses();
                }
                setCauses(new Causes());
                mensajeGrowl("Exito", "Extracción correcta");
                oCauses.iniciar();
            } else {
                mensajeGrowl("Error", "Hubo un error al extraer el archivo");
            }
        } else {
            mensajeGrowl("Error", "Se necesita cargar un archivo");
        }
    }
    
    public void guardarCambiosAProduccion(){
        int totIns=0;
        try {
            totIns=oCauses.insertarTodosCausesAProduccion();
        } catch (Exception e) {
        }
        //System.out.println("guardado "+totIns);
        if(totIns>0){
            mensajeGrowl("Exito","Causes Guardados Correctamente");
        }else if(totIns==21000){
            mensajeGrowl("Exito","Algún cause se encuentra repetido");
        }else{
            mensajeGrowl("Error","No se copiaron CAUSES");
        }
        this.lCausesEncontrados.clear();
        this.lCausesEncontradosFiltro.clear();
        
    }
    
    public void guardaConfiguracion(){
        boolean exito=false;
        try {
            exito=oExtractor.guardarConfiguracion();
        } catch (Exception e) {
        }
        if (exito) {
            mensajeGrowl("Exito","Configuración guardada correctamente");
        }else{
            mensajeGrowl("Error","Hubo un error al guardar la configuración");
        }
    }
    
    public void reestableceConfiguracion(){
        boolean exito=false;
        try {
            exito=oExtractor.cargarConfiguracion();
        } catch (Exception e) {
        }
        if (exito) {
            mensajeGrowl("Exito","Configuración restablecida Correctamente");
        }else{
            mensajeGrowl("Error","Hubo un error al restablecer las configuraciónes");
        }
    }

    /**
     * @return the oCauses
     */
    public Causes getCauses() {
        return oCauses;
    }

    /**
     * @param oCauses the oCauses to set
     */
    public void setCauses(Causes oCauses) {
        this.oCauses = oCauses;
    }

    /**
     * @return the lListaCie10
     */
    public LazyDataModel<DiagnosticoCIE10> getListaCie10() {
        return lListaCie10;
    }

    /**
     * @param listaCie10 the lListaCie10 to set
     */
    public void setListaCie10(LazyDataModel<DiagnosticoCIE10> listaCie10) {
        this.lListaCie10 = listaCie10;
    }

    /**
     * @return the lListaCie9
     */
    public LazyDataModel<ProcedimientoCIE9> getListaCie9() {
        return lListaCie9;
    }

    /**
     * @param lListaCie9 the lListaCie9 to set
     */
    public void setListaCie9(LazyDataModel<ProcedimientoCIE9> lListaCie9) {
        this.lListaCie9 = lListaCie9;
    }

    /**
     * @return the oNuevoMedicamento
     */
    public Medicamento getNuevoMedicamento() {
        return oNuevoMedicamento;
    }

    /**
     * @param oNuevoMedicamento the oNuevoMedicamento to set
     */
    public void setNuevoMedicamento(Medicamento oNuevoMedicamento) {
        this.oNuevoMedicamento = oNuevoMedicamento;
    }

    /**
     * @return the oCausesB
     */
    public Causes getCausesB() {
        return oCausesB;
    }

    /**
     * @param oCausesB the oCausesB to set
     */
    public void setCausesB(Causes oCausesB) {
        this.oCausesB = oCausesB;
    }

    /**
     * @return the lCausesEncontrados
     */
    public ArrayList<Causes> getListaCauses() {
        return lCausesEncontrados;
    }

    /**
     * @param lListaCauses the lCausesEncontrados to set
     */
    public void setListaCauses(ArrayList<Causes> lListaCauses) {
        this.lCausesEncontrados = lListaCauses;
    }

    /**
     * @return the lCausesEncontradosFiltro
     */
    public ArrayList<Causes> getListaCausesFiltro() {
        return lCausesEncontradosFiltro;
    }

    /**
     * @param lCausesEncontradosFiltro the lCausesEncontradosFiltro to set
     */
    public void setListaCausesFiltro(ArrayList<Causes> lCausesEncontradosFiltro) {
        this.lCausesEncontradosFiltro = lCausesEncontradosFiltro;
    }

    /**
     * @return the bMostrarNuevo
     */
    public boolean isMostrarNuevo() {
        return bMostrarNuevo;
    }

    /**
     * @param bMostrarNuevo the bMostrarNuevo to set
     */
    public void setMostrarNuevo(boolean bMostrarNuevo) {
        this.bMostrarNuevo = bMostrarNuevo;
    }

    /**
     * @return the oNuevoMaterial
     */
    public Material getNuevoMaterial() {
        return oNuevoMaterial;
    }

    /**
     * @param oNuevoMaterial the oNuevoMaterial to set
     */
    public void setNuevoMaterial(Material oNuevoMaterial) {
        this.oNuevoMaterial = oNuevoMaterial;
    }

    /**
     * @return the lServiciosCobrablesCie9
     */
    public ArrayList<ServicioCobrable> getServiciosCobrablesCie9() {
        return lServiciosCobrablesCie9;
    }

    /**
     * @param lServiciosCobrables the lServiciosCobrablesCie9 to set
     */
    public void setServiciosCobrablesCie9(ArrayList<ServicioCobrable> lServiciosCobrables) {
        this.lServiciosCobrablesCie9 = lServiciosCobrables;
    }

    /**
     * @return the fArchivoPdf
     */
    public UploadedFile getArchivoPdf() {
        return fArchivoPdf;
    }

    /**
     * @param archivoPdf the fArchivoPdf to set
     */
    public void setArchivoPdf(UploadedFile archivoPdf) {
        this.fArchivoPdf = archivoPdf;
    }

    /**
     * @return the lServiciosCobrablesCie10
     */
    public ArrayList<ServicioCobrable> getServiciosCobrablesCie10() {
        return lServiciosCobrablesCie10;
    }

    /**
     * @param lServiciosCobrablesCie10 the lServiciosCobrablesCie10 to set
     */
    public void setServiciosCobrablesCie10(ArrayList<ServicioCobrable> lServiciosCobrablesCie10) {
        this.lServiciosCobrablesCie10 = lServiciosCobrablesCie10;
    }

    /**
     * @return the oExtractor
     */
    public ExtractorCAUSES getExtractor() {
        return oExtractor;
    }

    /**
     * @param oExtractor the oExtractor to set
     */
    public void setExtractor(ExtractorCAUSES oExtractor) {
        this.oExtractor = oExtractor;
    }

}
