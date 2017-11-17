/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.jbs.admin.GestionCauses;
import mx.gob.hrrb.modelo.almacen.Material;

import mx.gob.hrrb.modelo.core.Causes;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author juan
 */
public class ExtractorCAUSES implements Serializable {

    private List<String> archivoCompleto;               //el archivo distribuido linea por linea
    private String direccionArchivo;
    private int nApuntador;

    private String archivoConfiguraciones;

    private String pTituloIntervencion;
    private String pAnio;
    private String pEsPagina;
    private String pFinDePagina;
    private String pCIE9;
    private String pCIE10;
    private String pMedicamento;
    private String pMaterial;
    private ArrayList<Causes> listaCauses;

    public static final int TITULO_INTERVENCION = 1;
    public static final int FIN_DE_PAGINA = 2;
    public static final int CIE9 = 3;
    public static final int CIE10 = 4;
    public static final int MEDICAMENTO = 5;
    public static final int MED_E_INS_TXT = 6;
    public static final int ANIO = 7;
    public static final int MATERIAL = 8;

    public ExtractorCAUSES() {

        listaCauses = new ArrayList<>();
        archivoCompleto = new ArrayList<>();
        archivoConfiguraciones="/regexConfig.properties";
       
        if (!cargarConfiguracion()) {
            configuracionesDefault();
        }

        nApuntador = 0;
    }

    public boolean cargarConfiguracion() {
        boolean res = false;
        Properties propiedades = new Properties();
        InputStream entrada = null;
        
        ExternalContext extCont = FacesContext.getCurrentInstance().getExternalContext();
        String ruta = extCont.getRealPath("/resources/CAUSES");
        
        try {
            
            
            entrada = new FileInputStream(ruta+archivoConfiguraciones);

            // carga el archivo de propiedades
            propiedades.load(entrada);

            setTituloIntervencion(propiedades.getProperty("tituloIntervencion"));
            setFinDePagina(propiedades.getProperty("finDePagina"));
            setCIE9(propiedades.getProperty("CIE9"));
            setCIE10(propiedades.getProperty("CIE10"));
            setMedicamento(propiedades.getProperty("medicamento"));
            setMaterial(propiedades.getProperty("material"));
            setEsPagina(propiedades.getProperty("esPag"));
            setAnio(propiedades.getProperty("anio"));

            res = true;

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    public boolean guardarConfiguracion() {
        boolean res = false;

        Properties propiedades = new Properties();
        OutputStream salida = null;
        
        ExternalContext extCont = FacesContext.getCurrentInstance().getExternalContext();
        String ruta = extCont.getRealPath("/resources/CAUSES");

        try {
            salida = new FileOutputStream(ruta+archivoConfiguraciones);

            // asignamos los valores a las propiedades
            propiedades.setProperty("tituloIntervencion", this.pTituloIntervencion);
            propiedades.setProperty("finDePagina", this.pFinDePagina);
            propiedades.setProperty("CIE9", this.pCIE9);
            propiedades.setProperty("CIE10", this.pCIE10);
            propiedades.setProperty("medicamento", this.pMedicamento);
            propiedades.setProperty("material", this.pMaterial);
            propiedades.setProperty("esPag", this.pEsPagina);
            propiedades.setProperty("anio",  this.pAnio);
            // guardamos el archivo de propiedades en la carpeta de aplicación
            propiedades.store(salida, null);
            res=true;

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (salida != null) {
                try {
                    salida.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return res;
    }

    public boolean configuracionesDefault() {
        setTituloIntervencion("^([0-9]{1,3}) ([A-ZÁÉÍÓÚÑ]+(\\\\.|,| |[A-ZÁÉÍÓÚÑ]|[0-9]|\\\\(|\\\\))+)$");
        setFinDePagina("^([0-9]{1,3})$");
        setCIE9("^(([^A-Z]{1}[.0-9A-Z]+))CIE-9-MC$");
        setCIE10("^(([A-Z]{1}[.0-9A-Z]+))CIE-10$");
        setMedicamento("(([0-9]{3}\\.){2}([0-9]{4})(\\.[0-9]{2}){0,1})");
        setMaterial("(([0-9]{3}\\.){2}([0-9]{4}))");
        setEsPagina("^Medicamentos e Insumos$");
        setAnio("^CATÁLOGO UNIVERSAL DE SERVICIOS DE SALUD ([0-9]{4})$");
        
        return true;
    }

    /**
     * extrae un archivo y lo convierte en un ArrayList para su uso linea por
     * linea
     *
     * @param archivo
     * @return ArrayList<String>. cada posición contiene una linea del documento
     * escaneado
     * @throws FileNotFoundException
     * @throws IOException
     * @throws org.apache.tika.exception.TikaException
     * @throws org.xml.sax.SAXException
     */
    public ArrayList<String> extraerArchivo(String archivo) throws FileNotFoundException, IOException, TikaException, SAXException {

        StringBuilder plainText = new StringBuilder();
        InputStream input;

        input = new FileInputStream(archivo);
        BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
        Metadata metadata = new Metadata();
        new PDFParser().parse(input, handler, metadata, new ParseContext());

        plainText.append(handler.toString());

        String textoCompleto = plainText.toString();

        StringBuilder doc = new StringBuilder();
        
        ArrayList<String> archivoExtraido = new ArrayList<>();

        String[] lineas = textoCompleto.split("\n");

        for (int i = 0; i < lineas.length; i++) {
            archivoExtraido.add(lineas[i]);
            doc.append(lineas[i]).append("\n");
        }
        nApuntador = 0;
        
        //System.out.println(doc);

        return archivoExtraido;
    }

    /*
     * recibe un ArrayList con la información de un procedimiento
     */
    public Causes extraerInformacionDeCause(ArrayList<String> infoCause) {
        boolean esPaginaCauses;

        Causes cause = new Causes();
        cause.iniciar();
        cause.getProcedimientoCIE9().setListaProcecve(new ArrayList<ProcedimientoCIE9>());
        cause.getDiagnosticoCIE10().setListaDiagcve(new ArrayList<DiagnosticoCIE10>());

        //datos extraidos
        String sNumInter;
        String sTitulo;
        String sAnio;
        Entry<Integer, String> coincidencia = new SimpleEntry<>(0, "");

        StringBuilder imp = new StringBuilder();

        esPaginaCauses = paginaCauses(infoCause);

        if (esPaginaCauses) {
            for (String linea : infoCause) {
                coincidencia = buscaCoincidencia(linea);
                boolean existe = false;
                String clave;

                switch (coincidencia.getKey()) {

                    case ExtractorCAUSES.CIE10:
                        existe = false;
                        clave = coincidencia.getValue().replace(".", "");
                        DiagnosticoCIE10 diag = new DiagnosticoCIE10();
                        diag.getCauses().iniciar();
                        diag.setClave(clave);

                        try {
                            existe = diag.buscar();
                        } catch (Exception e) {
                        }

                        if (existe) {
                            diag.getCauses().setTipo("T4600");
                            if (diag.getClave().contains("Z0") || diag.getClave().contains("Z1")) {
                                ServicioCobrable serv = new ServicioCobrable();
                                serv.setClave("1");
                                diag.setServCob(serv);
                            }

                            cause.getDiagnosticoCIE10().getListaDiagcve().add(diag);

                            imp.append("cie10 " + clave);
                        }

                        break;
                    case ExtractorCAUSES.CIE9:
                        existe = false;
                        clave = coincidencia.getValue().replace(".", "");
                        ProcedimientoCIE9 proc = new ProcedimientoCIE9();
                        proc.getCauses().iniciar();
                        proc.setClaveCie(clave);

                        try {
                            existe = proc.buscar();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (existe) {
                            proc.getCauses().setTipo("T4600");
                            cause.getProcedimientoCIE9().getListaProcecve().add(proc);

                            imp.append("cie9 " + clave);
                        }
                        break;
                    case ExtractorCAUSES.MATERIAL:
                        existe = false;
                        clave = coincidencia.getValue();
                        Material mat = new Material();
                        mat.setClaveMaterial(clave);

                        try {
                            existe = mat.buscar();
                        } catch (Exception e) {
                        }

                        if (existe) {
                            cause.getMateriales().add(mat);

                            imp.append("med " + clave);
                        }
                        break;
                    case ExtractorCAUSES.MEDICAMENTO:
                        existe = false;
                        clave = coincidencia.getValue();
                        Medicamento med = new Medicamento();
                        med.setClaveMedicamento(clave);
                        try {
                            existe = med.buscarPorClave();
                        } catch (Exception e) {
                        }

                        if (existe) {
                            cause.getMedicamentos().add(med);

                            imp.append("med " + clave);
                        }
                        break;
                    case ExtractorCAUSES.ANIO:
                        sAnio = coincidencia.getValue();
                        cause.setAnio(Integer.parseInt(sAnio));

                        imp.append("anio " + sAnio);
                        break;
                    case ExtractorCAUSES.TITULO_INTERVENCION:
                        //el numero regresa en la 0 y el titulo en la 1
                        String[] numYTitu = coincidencia.getValue().split(",");

                        sNumInter = numYTitu[0];
                        cause.setClave(sNumInter);

                        sTitulo = numYTitu[1];
                        cause.setDescripcion(sTitulo);

                        imp.append("clave " + sNumInter);
                        imp.append("desc " + sTitulo);
                        break;
                    default:
                        break;
                }
            }

            //System.out.println(imp);
        }

        return cause;
    }

    public Entry<Integer, String> buscaCoincidencia(String linea) {

        SimpleEntry<Integer, String> res = new SimpleEntry<Integer, String>(-1, "");
        Pattern expresion;
        Matcher comparador;

        expresion = Pattern.compile(getTituloIntervencion());
        comparador = expresion.matcher(linea);

        if (comparador.find()) {  //numero y titulo
            String val = comparador.group(1) + "," + comparador.group(2);
            res = new SimpleEntry<>(ExtractorCAUSES.TITULO_INTERVENCION, val);
        } else {

            expresion = Pattern.compile(getAnio());
            comparador = expresion.matcher(linea);
            if (comparador.find()) {
                res = new SimpleEntry<>(ExtractorCAUSES.ANIO, comparador.group(1));
            } else {

                expresion = Pattern.compile(getCIE9());
                comparador = expresion.matcher(linea);
                if (comparador.find()) {//un CIE-9
                    //cie9.add(comparador.group(1).replace(".", ""));
                    res = new SimpleEntry<>(ExtractorCAUSES.CIE9, comparador.group(1));

                } else {
                    expresion = Pattern.compile(getCIE10());
                    comparador = expresion.matcher(linea);
                    if (comparador.find()) {//un CIE-10-MC
                        //cie10.add(comparador.group(1).replace(".", ""));
                        res = new SimpleEntry<>(ExtractorCAUSES.CIE10, comparador.group(1));
                    } else {
                        expresion = Pattern.compile(getMedicamento());
                        comparador = expresion.matcher(linea);
                        if (comparador.find()) {
                            //medicamentos.add(comparador.group());
                            res = new SimpleEntry<>(ExtractorCAUSES.MEDICAMENTO, comparador.group());
                        } else {
                            expresion = Pattern.compile(getMaterial());
                            comparador = expresion.matcher(linea);
                            if (comparador.find()) {
                                //medicamentos.add(comparador.group());
                                res = new SimpleEntry<>(ExtractorCAUSES.MATERIAL, comparador.group());
                            }
                        }
                    }
                }
            }
        }

        return res;
    }

    /**
     * Extrae la información de todas las intervenciones encontradas en un
     * archivo y las prepara para guardarse en base de datos
     *
     * @return true si encuentra al menos una intervencion
     */
    public boolean extraerTodosCauses() {
        boolean exito = false;

        Causes cause;

        try {
            archivoCompleto = extraerArchivo(direccionArchivo);
        } catch (IOException | TikaException | SAXException e) {
            //e.printStackTrace();
        }

        //recorre el documento linea por linea
        for (int i = nApuntador; i < archivoCompleto.size(); i++) {

            String linea = archivoCompleto.get(i);
            Pattern patTituo = Pattern.compile(getTituloIntervencion());
            Matcher matTitulo = patTituo.matcher(linea);

            if (matTitulo.find()) {     //si se encontró el titulo empieza a extraer la pagina
                //System.out.println("titulo encontrado");
                //listaCauses.add(linea); //test
                ArrayList<String> pagina = extraePagina(i);
                cause = extraerInformacionDeCause(pagina);
                getListaCauses().add(cause);
                exito = true;
            }
        }

        return exito;
    }

    public void guardarTodosCauses() {
        for (Causes c : getListaCauses()) {
            try {
                c.insertar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Verifica que la pagina pasada contenga una intervencion, esto se
     * determina por una expresion regular
     *
     * @param infoCause la pagina a analizar
     * @return true si la página contienen una intervencion
     */
    private boolean paginaCauses(ArrayList<String> infoCause) {
        boolean esProcedimiento = false;
        Pattern patMedeIns;
        Matcher mediceIns;

        for (String lineaIntervencion : infoCause) {
            patMedeIns = Pattern.compile(getEsPagina());
            mediceIns = patMedeIns.matcher(lineaIntervencion);
            if (mediceIns.find()) {
                esProcedimiento = true;
            }
        }
        return esProcedimiento;
    }

    /**
     * extrae la pagina a partir de donde se le indique por el numero de
     * apuntador depende de que esté bien marcado el fin de pagina en
     * finDePagina al terminar si se llegó a un final de pagina válido devuelve
     * un ArrayList que contiene la información de ésta y deja nApuntador en la
     * linea siguiente a donde acabó la pagina
     *
     * @param apuntadorInterno
     * @return informacion de un procedimiento por linea
     */
    public ArrayList<String> extraePagina(int apuntadorInterno) {

        ArrayList<String> intervencion = new ArrayList<>();
        String lineaIntervencion = "";
        boolean terminaPagina = false;

        StringBuilder pag = new StringBuilder();

        //extrae el texto en la pagina de intervención encontrada
        while (!terminaPagina && apuntadorInterno < archivoCompleto.size()) {

            lineaIntervencion = archivoCompleto.get(apuntadorInterno);
            intervencion.add(lineaIntervencion);
            pag.append(lineaIntervencion).append("\n ").append(apuntadorInterno);

            Pattern patFinPagina = Pattern.compile(getFinDePagina());
            Matcher finPag = patFinPagina.matcher(lineaIntervencion);

            if (finPag.find()) {
                terminaPagina = true;
                nApuntador = apuntadorInterno + 1;
            }
            //System.out.println(pag.toString());
            apuntadorInterno++;
        }

        //System.out.println("pagina:\n"+pag);
        return intervencion;
    }

    /**
     * @return the direccionArchivo
     */
    public String getDireccionArchivo() {
        return direccionArchivo;
    }

    /**
     * @param direccionArchivo la dirección del archivo a extraer
     */
    public void setDireccionArchivo(String direccionArchivo) {
        this.direccionArchivo = direccionArchivo;
    }

    /**
     * @return the listaCauses
     */
    public ArrayList<Causes> getListaCauses() {
        return listaCauses;
    }

    /**
     * @param listaCauses the listaCauses to set
     */
    public void setListaCauses(ArrayList<Causes> listaCauses) {
        this.listaCauses = listaCauses;
    }

    /**
     * @return the pTituloIntervencion
     */
    public String getTituloIntervencion() {
        return pTituloIntervencion;
    }

    /**
     * @param pTituloIntervencion the pTituloIntervencion to set
     */
    public void setTituloIntervencion(String pTituloIntervencion) {
        this.pTituloIntervencion = pTituloIntervencion;
    }

    /**
     * @return the pAnio
     */
    public String getAnio() {
        return pAnio;
    }

    /**
     * @param pAnio the pAnio to set
     */
    public void setAnio(String pAnio) {
        this.pAnio = pAnio;
    }

    /**
     * @return the pEsPagina
     */
    public String getEsPagina() {
        return pEsPagina;
    }

    /**
     * @param pEsPagina the pEsPagina to set
     */
    public void setEsPagina(String pEsPagina) {
        this.pEsPagina = pEsPagina;
    }

    /**
     * @return the pFinDePagina
     */
    public String getFinDePagina() {
        return pFinDePagina;
    }

    /**
     * @param pFinDePagina the pFinDePagina to set
     */
    public void setFinDePagina(String pFinDePagina) {
        this.pFinDePagina = pFinDePagina;
    }

    /**
     * @return the pCIE9
     */
    public String getCIE9() {
        return pCIE9;
    }

    /**
     * @param pCIE9 the pCIE9 to set
     */
    public void setCIE9(String pCIE9) {
        this.pCIE9 = pCIE9;
    }

    /**
     * @return the pCIE10
     */
    public String getCIE10() {
        return pCIE10;
    }

    /**
     * @param pCIE10 the pCIE10 to set
     */
    public void setCIE10(String pCIE10) {
        this.pCIE10 = pCIE10;
    }

    /**
     * @return the pMedicamento
     */
    public String getMedicamento() {
        return pMedicamento;
    }

    /**
     * @param pMedicamento the pMedicamento to set
     */
    public void setMedicamento(String pMedicamento) {
        this.pMedicamento = pMedicamento;
    }

    /**
     * @return the pMaterial
     */
    public String getMaterial() {
        return pMaterial;
    }

    /**
     * @param pMaterial the pMaterial to set
     */
    public void setMaterial(String pMaterial) {
        this.pMaterial = pMaterial;
    }

}
