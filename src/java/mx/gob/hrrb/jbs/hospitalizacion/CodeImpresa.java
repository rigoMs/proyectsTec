/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.hospitalizacion;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Lesion;
import mx.gob.hrrb.modelo.hospitalizacion.HospitalPsiquiatrico;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oCodeImp")
@RequestScoped
public class CodeImpresa {

    private long nFolioPaciente;
    private long nClaveEpisodio;
    private long nNumIngresoHosp;
    private Hospitalizacion oHosp;
    private Hospitalizacion oHosDef;
    private Lesion oLes;
    private HospitalPsiquiatrico oHosPsi;
    private boolean bVerDesdeExpediente;

    /**
     * Creates a new instance of CodeImpresa
     */
    public CodeImpresa() {
        oHosp = new Hospitalizacion();
        oHosDef = new Hospitalizacion();
        oLes = new Lesion();
        oHosPsi = new HospitalPsiquiatrico();
        bVerDesdeExpediente=false;
    }

    public String recuperaDatos() throws Exception {
        oHosp = new Hospitalizacion();
        oHosp.setNumIngresoHos(nNumIngresoHosp);
        oHosp.getEpisodioMedico().setClaveEpisodio(nClaveEpisodio);
        oHosp.getPaciente().setFolioPaciente(nFolioPaciente);
        oHosDef.setNumIngresoHos(nNumIngresoHosp);
        oHosDef.getEpisodioMedico().setClaveEpisodio(nClaveEpisodio);
        oHosDef.getPaciente().setFolioPaciente(nFolioPaciente);
        oLes.setFolioPaciente(nFolioPaciente);
        oLes.setClaveEpisodio(nClaveEpisodio);
        oLes.setNumIngresoHosp(nNumIngresoHosp);
        oHosPsi.getHospitalizacion().setNumIngresoHos(nNumIngresoHosp);
        oHosPsi.getPaciente().setFolioPaciente(nFolioPaciente);
        oHosp.buscarCodeFolio();
        oHosp.buscarDatosPacienteCODE();
        oHosp.buscarCodeEstancia();
        oHosp.buscarCodeAreasApoyo();
        oHosp.buscarCodeAreasServicio();
        oHosp.buscarCodeAfecciones();
        oHosp.buscarCodeProcedimientos();
        oHosp.buscarCodeDatosDefuncion();
        oHosDef.buscarCodeAfeccionesDefuncion();
        oHosp.buscarCodeAtencionObstetrica();
        oHosp.buscarCodeAntecedentesGinecoObstetricos();
        oHosp.buscarCodeProductos();
        oLes.buscarCode();
        oHosPsi.buscarCode();
        String nombreHTML = imprimeCODE();
        System.out.println(nombreHTML);
        return nombreHTML;
    }

    public String obtenCedulaProf(String sMedico) {
        String cedulaProf = "";
        if (sMedico.compareTo("") != 0) {
            int inicio = sMedico.indexOf("*");
            int fin = sMedico.indexOf("-");
            cedulaProf = sMedico.substring(inicio + 1, fin);
        }

        return cedulaProf;
    }

    public String imprimeCODE() throws FileNotFoundException, Exception {
        String nombreHTML = "";
        FileWriter fichero = null;
        PrintWriter pw = null;

        if (this == null) {
            throw new Exception("Funcion.CodeImpresa: error de programación, faltan datos");
        } else {
            nombreHTML = "/hospitalizacion/HojaCodeImpresa.xhtml";
            String HTML = "hospitalizacion/FormatoCODE.html";
            ExternalContext extCont = FacesContext.getCurrentInstance().getExternalContext();
            File folder = new File(extCont.getRealPath("//sesiones//"));
            //System.out.println("Aqui se guarda "+folder);
            try {

                fichero = new FileWriter(folder + nombreHTML);
                pw = new PrintWriter(fichero);
                String str = "<?xml version='1.0' encoding='UTF-8' ?>\n"
                        + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                        + "        <link href=\"#{facesContext.externalContext.requestContextPath}/css/estiloCODE.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
                        + "        <title>Hoja De Hospitalizacion " + oHosp.getPaciente().getNombres() + " " + oHosp.getPaciente().getApPaterno() + " " + oHosp.getPaciente().getApMaterno() + "</title>\n"
                        + "    </head>\n"
                        + "    <body>";
                File myhtml = new File(folder, HTML);
                FileInputStream fileinput = null;
                BufferedInputStream mybuffer = null;
                DataInputStream datainput = null;
                fileinput = new FileInputStream(myhtml);
                mybuffer = new BufferedInputStream(fileinput);
                datainput = new DataInputStream(mybuffer);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                //SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm:ss");
                int i = 0;
                String fAlta = sdf.format(oHosp.getEpisodioMedico().getAltaHospitalaria());
                    //System.out.append("Hola "+oHosp.getServicioIngreso().getAreaServicioSAEH().getClave());
                //System.out.append("Hola "+oHosp.getServicioIngreso().getAreaServicioSAEH().getClaveArea());
                while (datainput.available() != 0) {
                    i++;
                    String linea = datainput.readLine();
                    //System.out.println(linea);
                    if (linea.indexOf("12345678") > 0) {
                        linea = linea.replace("12345678", oHosp.getFolioCode());
                    }
                    if (linea.indexOf("sApPaterno") > 0) {
                        linea = linea.replace("sApPaterno", oHosp.getPaciente().getApPaterno());
                    }
                    if (linea.indexOf("sApMaterno") > 0) {
                        linea = linea.replace("sApMaterno", oHosp.getPaciente().getApMaterno());
                    }
                    if (linea.indexOf("sNombres") > 0) {
                        linea = linea.replace("sNombres", oHosp.getPaciente().getNombres());
                    }
                    if (linea.indexOf("Masc") > 0) {
                        if (oHosp.getPaciente().getSexoP().equals("Masculino")) {
                            linea = linea.replace("Masc", "X");
                        } else {
                            linea = linea.replace("Masc", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Feme") > 0) {
                        if (oHosp.getPaciente().getSexoP().equals("Femenino")) {
                            linea = linea.replace("Feme", "X");
                        } else {
                            linea = linea.replace("Feme", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CMS") > 0) {
                        int talla = (int) oHosp.getPaciente().getTalla();
                        String t = "" + talla;
                        if (t.length() == 1) {
                            linea = linea.replace("CMS", "&nbsp;&nbsp;" + talla);
                        } else if (t.length() == 2) {
                            linea = linea.replace("CMS", "&nbsp;" + talla);
                        } else if (t.length() == 3) {
                            linea = linea.replace("CMS", "" + talla);
                        }
                    }
                    if (linea.indexOf("KGS") > 0) {
                        int peso = (int) oHosp.getPaciente().getPeso();
                        String p = "" + peso;
                        if (p.length() == 1) {
                            linea = linea.replace("KGS", "&nbsp;&nbsp;" + peso);
                        } else if (p.length() == 2) {
                            linea = linea.replace("KGS", "&nbsp;" + peso);
                        } else if (p.length() == 3) {
                            linea = linea.replace("KGS", "" + peso);
                        }
                    }
                    if (linea.indexOf("GRMS") > 0) {
                        String peso = Float.toString( oHosp.getPaciente().getPeso() );
                        String[] num= peso.split("\\.");
                        String p = num[1];
                        if(p.length()>3)
                            p=p.substring(0,2);
                        
                        if (p.length() == 1) {
                            linea = linea.replace("GRMS", "&nbsp;&nbsp;" + p);
                        } else if (p.length() == 2) {
                            linea = linea.replace("GRMS", "&nbsp;" + p);
                        } else if (p.length() == 3) {
                            linea = linea.replace("GRMS", "" + p);
                        }
                    }
                    if (linea.indexOf("HORAS") > 0) {
                        linea = linea.replace("HORAS", "&nbsp;");
                    }
                    if (linea.indexOf("DIAS") > 0) {
                        if (oHosp.getPaciente().getClaveEdadP().equals("DÍAS")) {
                            String dias = "" + oHosp.getPaciente().getEdad();
                            if (dias.length() == 1) {
                                linea = linea.replace("DIAS", "&nbsp;" + oHosp.getPaciente().getEdad());
                            } else {
                                linea = linea.replace("DIAS", "" + oHosp.getPaciente().getEdad());
                            }
                        } else {
                            linea = linea.replace("DIAS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MESES") > 0) {
                        if (oHosp.getPaciente().getClaveEdadP().equals("MESES")) {
                            String meses = "" + oHosp.getPaciente().getEdad();
                            if (meses.length() == 1) {
                                linea = linea.replace("MESES", "&nbsp;" + oHosp.getPaciente().getEdad());
                            } else {
                                linea = linea.replace("MESES", "" + oHosp.getPaciente().getEdad());
                            }
                        } else {
                            linea = linea.replace("MESES", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AYOS") > 0) {
                        if (oHosp.getPaciente().getClaveEdadP().equals("AÑOS")) {
                            String aios = "" + oHosp.getPaciente().getEdad();
                            if (aios.length() == 1) {
                                linea = linea.replace("AYOS", "&nbsp;&nbsp;" + oHosp.getPaciente().getEdad());
                            } else if (aios.length() == 2) {
                                linea = linea.replace("AYOS", "&nbsp;" + oHosp.getPaciente().getEdad());
                            } else if (aios.length() == 3) {
                                linea = linea.replace("AYOS", "" + oHosp.getPaciente().getEdad());
                            }
                        } else {
                            linea = linea.replace("AYOS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NHS") > 0) {
                        if (oHosp.getPaciente().getNacidoEnHospitalP() == null) {
                            linea = linea.replace("NHS", "&nbsp;");
                        } else if (oHosp.getPaciente().getNacidoEnHospitalP().equals("SI")) {
                            linea = linea.replace("NHS", "X");
                        } else {
                            linea = linea.replace("NHS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NHN") > 0) {
                        if (oHosp.getPaciente().getNacidoEnHospitalP() == null) {
                            linea = linea.replace("NHN", "&nbsp;");
                        } else if (oHosp.getPaciente().getNacidoEnHospitalP().equals("NO")) {
                            linea = linea.replace("NHN", "X");
                        } else {
                            linea = linea.replace("NHN", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("IMSS") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("IMSS")) {
                            linea = linea.replace("IMSS", "X");
                        } else {
                            linea = linea.replace("IMSS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ISSSTE") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("ISSSTE")) {
                            linea = linea.replace("ISSSTE", "X");
                        } else {
                            linea = linea.replace("ISSSTE", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PEMEX") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("PEMEX")) {
                            linea = linea.replace("PEMEX", "X");
                        } else {
                            linea = linea.replace("PEMEX", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SEDENA") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("SEDENA")) {
                            linea = linea.replace("SEDENA", "X");
                        } else {
                            linea = linea.replace("SEDENA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SEMAR") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("SEMAR")) {
                            linea = linea.replace("SEMAR", "X");
                        } else {
                            linea = linea.replace("SEMAR", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROSPERA") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("PROSPERA")) {
                            linea = linea.replace("PROSPERA", "X");
                        } else {
                            linea = linea.replace("PROSPERA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("GOB. ESTATAL") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("GOB. ESTATAL")) {
                            linea = linea.replace("GOB. ESTATAL", "X");
                        } else {
                            linea = linea.replace("GOB. ESTATAL", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SEGURO PRIVADO") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("SEGURO PRIVADO")) {
                            linea = linea.replace("SEGURO PRIVADO", "X");
                        } else {
                            linea = linea.replace("SEGURO PRIVADO", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SEGURO POPULAR") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("SEGURO POPULAR")) {
                            linea = linea.replace("SEGURO POPULAR", "X");
                        } else {
                            linea = linea.replace("SEGURO POPULAR", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SE IGNORA") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("SE IGNORA")) {
                            linea = linea.replace("SE IRNORA", "X");
                        } else {
                            linea = linea.replace("SE IGNORA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NINGUNA") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("NINGUNA")) {
                            linea = linea.replace("NINGUNA", "X");
                        } else {
                            linea = linea.replace("NINGUNA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SEGURO GRATUIDAD") > 0) {
                        if (oHosp.getSeguro().getDerechohabienteP().equals("SEGURO GRATUIDAD")) {
                            linea = linea.replace("SEGURO GRATUIDAD", "X");
                        } else {
                            linea = linea.replace("SEGURO GRATUIDAD", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NNNNNNNNNN") > 0) {
                        if (!oHosp.getSeguro().getNumero().equals("")) {
                            String seguro = oHosp.getSeguro().getNumero().substring(0, 10);
                            linea = linea.replace("NNNNNNNNNN", seguro);
                        } else {
                            linea = linea.replace("NNNNNNNNNN", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                        }
                    }
                    if (linea.indexOf("NNSP") > 0) {
                        if (!oHosp.getSeguro().getNumero().equals("")) {
                            String seguro = oHosp.getSeguro().getNumero().substring(11, 13);
                            linea = linea.replace("NNSP", seguro);
                        } else {
                            linea = linea.replace("NNSP", "&nbsp;&nbsp;");
                        }
                    }
                    if (linea.indexOf("ENTIDAD") > 0) {
                        if (!oHosp.getEstado().getDescripcionEdo().equals("")) {
                            linea = linea.replace("ENTIDAD", oHosp.getEstado().getDescripcionEdo());
                        } else {
                            linea = linea.replace("ENTIDAD", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MUNICIPIO") > 0) {
                        if (!oHosp.getMunicipio().getDescripcionMun().equals("")) {
                            linea = linea.replace("MUNICIPIO", oHosp.getMunicipio().getDescripcionMun());
                        } else {
                            linea = linea.replace("MUNICIPIO", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("LOCALIDAD") > 0) {
                        if (!oHosp.getCiudad().getDescripcionCiu().equals("")) {
                            linea = linea.replace("LOCALIDAD", oHosp.getCiudad().getDescripcionCiu());
                        } else {
                            linea = linea.replace("LOCALIDAD", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NUMEXPEDIENTE") > 0) {
                        linea = linea.replace("NUMEXPEDIENTE", "" + oHosp.getExpediente().getNumero());
                    }
                    if (linea.indexOf("CURPCURPCURPCURPVZ") > 0) {
                        if (!oHosp.getPaciente().getCurp().equals("")) {
                            linea = linea.replace("CURPCURPCURPCURPVZ", oHosp.getPaciente().getCurp());
                        } else {
                            linea = linea.replace("CURPCURPCURPCURPVZ", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ClaEDO") > 0) {
                        if (!oHosp.getEstado().getClaveEdo().equals("")) {
                            linea = linea.replace("ClaEDO", oHosp.getEstado().getClaveEdo());
                        } else {
                            linea = linea.replace("ClaEDO", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ClaMUN") > 0) {
                        if (!oHosp.getMunicipio().getClaveMun().equals("")) {
                            linea = linea.replace("ClaMUN", oHosp.getMunicipio().getClaveMun());
                        } else {
                            linea = linea.replace("ClaMUN", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ClaCIU") > 0) {
                        if (!oHosp.getCiudad().getClaveCiu().equals("")) {
                            linea = linea.replace("ClaCIU", oHosp.getCiudad().getClaveCiu());
                        } else {
                            linea = linea.replace("ClaCIU", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("GRUPO INDIGENA SI") > 0) {
                        if (oHosp.getEtnicidad().getPertenenciaGpoIndP().equals("SI")) {
                            linea = linea.replace("GRUPO INDIGENA SI", "X");
                        } else {
                            linea = linea.replace("GRUPO INDIGENA SI", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("GRUPO INDIGENA NO") > 0) {
                        if (oHosp.getEtnicidad().getPertenenciaGpoIndP().equals("NO")) {
                            linea = linea.replace("GRUPO INDIGENA NO", "X");
                        } else {
                            linea = linea.replace("GRUPO INDIGENA NO", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("GRUPO INDIGENA NR") > 0) {
                        if (oHosp.getEtnicidad().getPertenenciaGpoIndP().equals("NO RESPONDE")) {
                            linea = linea.replace("GRUPO INDIGENA NR", "X");
                        } else {
                            linea = linea.replace("GRUPO INDIGENA NR", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("GRUPO INDIGENA NS") > 0) {
                        if (oHosp.getEtnicidad().getPertenenciaGpoIndP().equals("NO SABE")) {
                            linea = linea.replace("GRUPO INDIGENA NS", "X");
                        } else {
                            linea = linea.replace("GRUPO INDIGENA NS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("LENGUA INDIGENA SI") > 0) {
                        if (oHosp.getEtnicidad().getHablaLenguaIndP().equals("SI")) {
                            linea = linea.replace("LENGUA INDIGENA SI", "X");
                        } else {
                            linea = linea.replace("LENGUA INDIGENA SI", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("LENGUA INDIGENA NO") > 0) {
                        if (oHosp.getEtnicidad().getHablaLenguaIndP().equals("NO")) {
                            linea = linea.replace("LENGUA INDIGENA NO", "X");
                        } else {
                            linea = linea.replace("LENGUA INDIGENA NO", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("LENGUA INDIGENA NR") > 0) {
                        if (oHosp.getEtnicidad().getHablaLenguaIndP().equals("NO RESPONDE")) {
                            linea = linea.replace("LENGUA INDIGENA NR", "X");
                        } else {
                            linea = linea.replace("LENGUA INDIGENA NR", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("LENGUA INDIGENA NS") > 0) {
                        if (oHosp.getEtnicidad().getHablaLenguaIndP().equals("NO SABE")) {
                            linea = linea.replace("LENGUA INDIGENA NS", "X");
                        } else {
                            linea = linea.replace("LENGUA INDIGENA NS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Clave Lengua Indigena") > 0) {
                        if (!oHosp.getEtnicidad().getClaveLengua().equals("")) {
                            linea = linea.replace("Clave Lengua Indigena", oHosp.getEtnicidad().getClaveLengua());
                        } else {
                            linea = linea.replace("Clave Lengua Indigena", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("HABLA ESPANIOL SI") > 0) {
                        if (oHosp.getEtnicidad().getHablaEspaniolP().equals("SI")) {
                            linea = linea.replace("HABLA ESPANIOL SI", "X");
                        } else {
                            linea = linea.replace("HABLA ESPANIOL SI", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("HABLA ESPANIOL NO") > 0) {
                        if (oHosp.getEtnicidad().getHablaEspaniolP().equals("NO")) {
                            linea = linea.replace("HABLA ESPANIOL NO", "X");
                        } else {
                            linea = linea.replace("HABLA ESPANIOL NO", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("HABLA ESPANIOL NR") > 0) {
                        if (oHosp.getEtnicidad().getHablaEspaniolP().equals("NO RESPONDE")) {
                            linea = linea.replace("HABLA ESPANIOL NR", "X");
                        } else {
                            linea = linea.replace("HABLA ESPANIOL NR", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("HABLA ESPANIOL NS") > 0) {
                        if (oHosp.getEtnicidad().getHablaEspaniolP().equals("NO SABE")) {
                            linea = linea.replace("HABLA ESPANIOL NS", "X");
                        } else {
                            linea = linea.replace("HABLA ESPANIOL NS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("DiaIng") > 0) {
                        String dia = oHosp.getEpisodioMedico().getFIngreso().substring(0, 2);
                        if (!oHosp.getEpisodioMedico().getFIngreso().equals("")) {
                            linea = linea.replace("DiaIng", dia);
                        } else {
                            linea = linea.replace("DiaIng", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MesIng") > 0) {
                        String mes = oHosp.getEpisodioMedico().getFIngreso().substring(3, 5);
                        if (!oHosp.getEpisodioMedico().getFIngreso().equals("")) {
                            linea = linea.replace("MesIng", mes);
                        } else {
                            linea = linea.replace("MesIng", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AnioIng") > 0) {
                        String mes = oHosp.getEpisodioMedico().getFIngreso().substring(6, 10);
                        if (!oHosp.getEpisodioMedico().getFIngreso().equals("")) {
                            linea = linea.replace("AnioIng", mes);
                        } else {
                            linea = linea.replace("AnioIng", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("DiaEgre") > 0) {
                        String dia = fAlta.substring(0, 2);
                        if (oHosp.getEpisodioMedico().getAltaHospitalaria() != null) {
                            linea = linea.replace("DiaEgre", dia);
                        } else {
                            linea = linea.replace("DiaEgre", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MesEgre") > 0) {
                        String mes = fAlta.substring(3, 5);
                        if (oHosp.getEpisodioMedico().getAltaHospitalaria() != null) {
                            linea = linea.replace("MesEgre", mes);
                        } else {
                            linea = linea.replace("MesEgre", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AnioEgre") > 0) {
                        String mes = fAlta.substring(6, 10);
                        if (oHosp.getEpisodioMedico().getAltaHospitalaria() != null) {
                            linea = linea.replace("AnioEgre", mes);
                        } else {
                            linea = linea.replace("AnioEgre", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("EstNor") > 0) {
                        if (oHosp.getTipoEstancia() == '1') {
                            linea = linea.replace("EstNor", "X");
                        } else {
                            linea = linea.replace("EstNor", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("EstCor") > 0) {
                        if (oHosp.getTipoEstancia() == '2') {
                            linea = linea.replace("EstCor", "X");
                        } else {
                            linea = linea.replace("EstCor", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("DE INGRESO") > 0) {
                        System.out.println("Entre aqui **********");
                        //System.out.println(oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion());
                        if (oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion() != null) {
                            if (!oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion().equals("")) {
                                linea = linea.replace("DE INGRESO", oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("DE INGRESO", "&nbsp;");
                        }

                    }
                    if (linea.indexOf("SEGUNDO") > 0) {
                        if (oHosp.getServicioSegundo().getAreaServicioSAEH().getDescripcion() != null) {
                            if (!oHosp.getServicioSegundo().getAreaServicioSAEH().getDescripcion().equals("")) {
                                linea = linea.replace("SEGUNDO", oHosp.getServicioSegundo().getAreaServicioSAEH().getDescripcion());
                            }

                        } else {
                            linea = linea.replace("SEGUNDO", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TERCERO") > 0) {
                        if (oHosp.getServicioTercero().getAreaServicioSAEH().getDescripcion() != null) {
                            if (!oHosp.getServicioTercero().getAreaServicioSAEH().getDescripcion().equals("")) {
                                linea = linea.replace("TERCERO", oHosp.getServicioTercero().getAreaServicioSAEH().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("TERCERO", "&nbsp;");
                        }

                    }
                    if (linea.indexOf("DE EGRESO") > 0) {
                        if (oHosp.getServicioEgreso().getAreaServicioSAEH().getDescripcion() != null) {
                            if (!oHosp.getServicioEgreso().getAreaServicioSAEH().getDescripcion().equals("")) {
                                linea = linea.replace("DE EGRESO", oHosp.getServicioEgreso().getAreaServicioSAEH().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("DE EGRESO", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIngreso") > 0) {
                        if (!oHosp.getServicioIngreso().getAreaServicioSAEH().claveArea().equals("")) {
                            linea = linea.replace("CIngreso", oHosp.getServicioIngreso().getAreaServicioSAEH().claveArea());
                        } else {
                            linea = linea.replace("CIngreso", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CSegundo") > 0) {
                        if (!oHosp.getServicioSegundo().getAreaServicioSAEH().claveArea().equals("")) {
                            linea = linea.replace("CSegundo", oHosp.getServicioSegundo().getAreaServicioSAEH().claveArea());
                        } else {
                            linea = linea.replace("CSegundo", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CTercero") > 0) {
                        if (!oHosp.getServicioTercero().getAreaServicioSAEH().claveArea().equals("")) {
                            linea = linea.replace("CTercero", oHosp.getServicioTercero().getAreaServicioSAEH().claveArea());
                        } else {
                            linea = linea.replace("CTercero", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CEgreso") > 0) {
                        if (!oHosp.getServicioEgreso().getAreaServicioSAEH().claveArea().equals("")) {
                            linea = linea.replace("CEgreso", oHosp.getServicioEgreso().getAreaServicioSAEH().claveArea());
                        } else {
                            linea = linea.replace("CEgreso", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SLABOR") > 0) {
                        if (oHosp.getSalaLabor().getTiempoUso() > 0) {
                            String slabor = "" + oHosp.getSalaLabor().getTiempoUso();
                            if (slabor.length() == 1) {
                                linea = linea.replace("SLABOR", "&nbsp;" + oHosp.getSalaLabor().getTiempoUso());
                            } else if (slabor.length() == 2) {
                                linea = linea.replace("SLABOR", "" + oHosp.getSalaLabor().getTiempoUso());
                            }
                        } else {
                            linea = linea.replace("SLABOR", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SEXPULSION") > 0) {
                        if (oHosp.getSalaExpulsion().getTiempoUso() > 0) {
                            String sexpulsion = "" + oHosp.getSalaExpulsion().getTiempoUso();
                            if (sexpulsion.length() == 1) {
                                linea = linea.replace("SEXPULSION", "&nbsp;" + oHosp.getSalaExpulsion().getTiempoUso());
                            } else if (sexpulsion.length() == 2) {
                                linea = linea.replace("SEXPULSION", "" + oHosp.getSalaExpulsion().getTiempoUso());
                            }
                        } else {
                            linea = linea.replace("SEXPULSION", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SRECUPERACION") > 0) {
                        if (oHosp.getSalaRecuperacion().getTiempoUso() > 0) {
                            String srecuperacion = "" + oHosp.getSalaRecuperacion().getTiempoUso();
                            if (srecuperacion.length() == 1) {
                                linea = linea.replace("SRECUPERACION", "&nbsp;" + oHosp.getSalaRecuperacion().getTiempoUso());
                            } else if (srecuperacion.length() == 2) {
                                linea = linea.replace("SRECUPERACION", "" + oHosp.getSalaRecuperacion().getTiempoUso());
                            }
                        } else {
                            linea = linea.replace("SRECUPERACION", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TINTENSIVA") > 0) {
                        if (oHosp.getTerapiaIntensiva().getTiempoUso() > 0) {
                            String terapiaI = "" + oHosp.getTerapiaIntensiva().getTiempoUso();
                            if (terapiaI.length() == 1) {
                                linea = linea.replace("TINTENSIVA", "&nbsp;&nbsp;" + oHosp.getTerapiaIntensiva().getTiempoUso());
                            } else if (terapiaI.length() == 2) {
                                linea = linea.replace("TINTENSIVA", "&nbsp;" + oHosp.getTerapiaIntensiva().getTiempoUso());
                            } else if (terapiaI.length() == 3) {
                                linea = linea.replace("TINTENSIVA", "" + oHosp.getTerapiaIntensiva().getTiempoUso());
                            }
                        } else {
                            linea = linea.replace("TINTENSIVA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TINTERMEDIA") > 0) {
                        if (oHosp.getTerapiaIntermedia().getTiempoUso() > 0) {
                            String terapiaI = "" + oHosp.getTerapiaIntermedia().getTiempoUso();
                            if (terapiaI.length() == 1) {
                                linea = linea.replace("TINTERMEDIA", "&nbsp;&nbsp;" + oHosp.getTerapiaIntermedia().getTiempoUso());
                            } else if (terapiaI.length() == 2) {
                                linea = linea.replace("TINTERMEDIA", "&nbsp;" + oHosp.getTerapiaIntermedia().getTiempoUso());
                            } else if (terapiaI.length() == 3) {
                                linea = linea.replace("TINTERMEDIA", "" + oHosp.getTerapiaIntermedia().getTiempoUso());
                            }
                        } else {
                            linea = linea.replace("TINTERMEDIA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("InstitucionProcedencia") > 0) {
                        System.out.println("Referencia: " + oHosp.getPaciente().getReferencia().getClave());
                        if (!oHosp.getPaciente().getReferencia().getClave().equals("")) {
                            oHosp.getPaciente().getReferencia().buscar();
                            linea = linea.replace("InstitucionProcedencia", oHosp.getPaciente().getReferencia().getDescripcion());
                        } else {
                            linea = linea.replace("InstitucionProcedencia", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PConExt") > 0) {
                        if (!oHosp.getProcedenciaP().equals("") && oHosp.getProcedenciaP().equals("T0401")) {
                            linea = linea.replace("PConExt", "X");
                        } else {
                            linea = linea.replace("PConExt", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PUrg") > 0) {
                        if (!oHosp.getProcedenciaP().equals("") && oHosp.getProcedenciaP().equals("T0402")) {
                            linea = linea.replace("PUrg", "X");
                        } else {
                            linea = linea.replace("PUrg", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PRef") > 0) {
                        if (!oHosp.getProcedenciaP().equals("") && oHosp.getProcedenciaP().equals("T0403")) {
                            linea = linea.replace("PRef", "X");
                        } else {
                            linea = linea.replace("PRef", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("POtro") > 0) {
                        if (!oHosp.getProcedenciaP().equals("") && oHosp.getProcedenciaP().equals("T0404")) {
                            linea = linea.replace("POtro", "X");
                        } else {
                            linea = linea.replace("POtro", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("InstitucionMotEgre") > 0) {
                        if (!oHosp.getEpisodioMedico().getRazonAltaVolunTrasl().equals("") && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3804")) {
                            linea = linea.replace("InstitucionMotEgre", oHosp.getEpisodioMedico().getRazonAltaVolunTrasl());
                        } else {
                            linea = linea.replace("InstitucionMotEgre", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MECur") > 0) {
                        if (!oHosp.getEpisodioMedico().getMotivoEgresoP().equals("") && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3801")) {
                            linea = linea.replace("MECur", "X");
                        } else {
                            linea = linea.replace("MECur", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MEMej") > 0) {
                        if (!oHosp.getEpisodioMedico().getMotivoEgresoP().equals("") && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3802")) {
                            linea = linea.replace("MEMej", "X");
                        } else {
                            linea = linea.replace("MEMej", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MEVol") > 0) {
                        if (!oHosp.getEpisodioMedico().getMotivoEgresoP().equals("") && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3803")) {
                            linea = linea.replace("MEVol", "X");
                        } else {
                            linea = linea.replace("MEVol", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MEPase") > 0) {
                        if (!oHosp.getEpisodioMedico().getMotivoEgresoP().equals("") && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3804")) {
                            linea = linea.replace("MEPase", "X");
                        } else {
                            linea = linea.replace("MEPase", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MEDef") > 0) {
                        if (!oHosp.getEpisodioMedico().getMotivoEgresoP().equals("") && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3805")) {
                            linea = linea.replace("MEDef", "X");
                        } else {
                            linea = linea.replace("MEDef", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MEOtro") > 0) {
                        if (!oHosp.getEpisodioMedico().getMotivoEgresoP().equals("") && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3806")) {
                            linea = linea.replace("MEOtro", "X");
                        } else {
                            linea = linea.replace("MEOtro", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AFECCION PRINCIPAL") > 0) {
                        if (oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("AFECCION PRINCIPAL", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag().equals("")) {
                            String afeprincipal = "";
                            if (oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag().length() > 101) {
                                afeprincipal = oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag().substring(0, 102);
                                linea = linea.replace("AFECCION PRINCIPAL", afeprincipal);
                            } else {
                                linea = linea.replace("AFECCION PRINCIPAL", oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("AFECCION PRINCIPAL", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AFECCION PRIMERA") > 0) {
                        if (oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("AFECCION PRIMERA", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().equals("")) {
                            String afeprimera = "";
                            if (oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().length() > 112) {
                                afeprimera = oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().substring(0, 113);
                                linea = linea.replace("AFECCION PRIMERA", afeprimera);
                            } else {
                                linea = linea.replace("AFECCION PRIMERA", oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("AFECCION PRIMERA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AFECCION SEGUNDA") > 0) {
                        if (oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("AFECCION SEGUNDA", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().equals("")) {
                            String afesegunda = "";
                            if (oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().length() > 112) {
                                System.out.println(oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().length());
                                afesegunda = oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().substring(0, 113);
                                System.out.println(afesegunda);
                                linea = linea.replace("AFECCION SEGUNDA", afesegunda);
                            } else {
                                linea = linea.replace("AFECCION SEGUNDA", oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("AFECCION SEGUNDA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AFECCION TERCERA") > 0) {
                        if (oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("AFECCION TERCERA", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag().equals("")) {
                            String afetercera = "";
                            if (oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag().length() > 112) {
                                afetercera = oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag().substring(0, 113);
                                linea = linea.replace("AFECCION TERCERA", afetercera);
                            } else {
                                linea = linea.replace("AFECCION TERCERA", oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("AFECCION TERCERA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AFECCION CUARTA") > 0) {
                        if (oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("AFECCION CUARTA", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag().equals("")) {
                            String afecuarta = "";
                            if (oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag().length() > 112) {
                                afecuarta = oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag().substring(0, 113);
                                linea = linea.replace("AFECCION CUARTA", afecuarta);
                            } else {
                                linea = linea.replace("AFECCION CUARTA", oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("AFECCION CUARTA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AFECCION QUINTA") > 0) {
                        if (oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("AFECCION QUINTA", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag().equals("")) {
                            String afequinta = "";
                            if (oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag().length() > 112) {
                                afequinta = oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag().substring(0, 113);
                                linea = linea.replace("AFECCION QUINTA", afequinta);
                            } else {
                                linea = linea.replace("AFECCION QUINTA", oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("AFECCION QUINTA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AFECCION SEXTA") > 0) {
                        if (oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("AFECCION SEXTA", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag().equals("")) {
                            String afesexta = "";
                            if (oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag().length() > 112) {
                                afesexta = oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag().substring(0, 113);
                                linea = linea.replace("AFECCION SEXTA", afesexta);
                            } else {
                                linea = linea.replace("AFECCION SEXTA", oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("AFECCION SEXTA", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIE0") > 0) {
                        if (!oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIE0", oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIE0", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIE1") > 0) {
                        if (!oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIE1", oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIE1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIE2") > 0) {
                        if (!oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIE2", oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIE2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIE3") > 0) {
                        if (!oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIE3", oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIE3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIE4") > 0) {
                        if (!oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIE4", oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIE4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIE5") > 0) {
                        if (!oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIE5", oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIE5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIE6") > 0) {
                        if (!oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIE6", oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIE6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIER") > 0) {
                        if (!oHosp.getEpisodioMedico().getAfeResAP().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIER", oHosp.getEpisodioMedico().getAfeResAP().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIER", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PRIMERA VEZ") > 0) {
                        if (oHosp.getEpisodioMedico().getTipoAfePrinc() == '1') {
                            linea = linea.replace("PRIMERA VEZ", "X");
                        } else {
                            linea = linea.replace("PRIMERA VEZ", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SUBSECUENTE") > 0) {
                        if (oHosp.getEpisodioMedico().getTipoAfePrinc() == '2') {
                            linea = linea.replace("SUBSECUENTE", "X");
                        } else {
                            linea = linea.replace("SUBSECUENTE", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("InfIntS") > 0) {
                        if (oHosp.getEpisodioMedico().getInfeccionIntrahospitalaria() == 'S') {
                            linea = linea.replace("InfIntS", "X");
                        } else {
                            linea = linea.replace("InfIntS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("InfIntN") > 0) {
                        if (oHosp.getEpisodioMedico().getInfeccionIntrahospitalaria() == 'N') {
                            linea = linea.replace("InfIntN", "X");
                        } else {
                            linea = linea.replace("InfIntN", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROCEDIMIENTO 1") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe1().getCIE9().getDescripcion() == null) {
                            linea = linea.replace("PROCEDIMIENTO 1", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe1().getCIE9().getDescripcion().equals("")) {
                            String proce1 = "";
                            if (oHosp.getEpisodioMedico().getProceRe1().getCIE9().getDescripcion().length() > 75) {
                                proce1 = oHosp.getEpisodioMedico().getProceRe1().getCIE9().getDescripcion().substring(0, 76);
                                linea = linea.replace("PROCEDIMIENTO 1", proce1);
                            } else {
                                linea = linea.replace("PROCEDIMIENTO 1", oHosp.getEpisodioMedico().getProceRe1().getCIE9().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("PROCEDIMIENTO 1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROCEDIMIENTO 2") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe2().getCIE9().getDescripcion() == null) {
                            linea = linea.replace("PROCEDIMIENTO 2", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe2().getCIE9().getDescripcion().equals("")) {
                            String proce2 = "";
                            if (oHosp.getEpisodioMedico().getProceRe2().getCIE9().getDescripcion().length() > 75) {
                                proce2 = oHosp.getEpisodioMedico().getProceRe2().getCIE9().getDescripcion().substring(0, 76);
                                linea = linea.replace("PROCEDIMIENTO 2", proce2);
                            } else {
                                linea = linea.replace("PROCEDIMIENTO 2", oHosp.getEpisodioMedico().getProceRe2().getCIE9().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("PROCEDIMIENTO 2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROCEDIMIENTO 3") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe3().getCIE9().getDescripcion() == null) {
                            linea = linea.replace("PROCEDIMIENTO 3", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe3().getCIE9().getDescripcion().equals("")) {
                            String proce3 = "";
                            if (oHosp.getEpisodioMedico().getProceRe3().getCIE9().getDescripcion().length() > 75) {
                                proce3 = oHosp.getEpisodioMedico().getProceRe3().getCIE9().getDescripcion().substring(0, 76);
                                linea = linea.replace("PROCEDIMIENTO 3", proce3);
                            } else {
                                linea = linea.replace("PROCEDIMIENTO 3", oHosp.getEpisodioMedico().getProceRe3().getCIE9().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("PROCEDIMIENTO 3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROCEDIMIENTO 4") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe4().getCIE9().getDescripcion() == null) {
                            linea = linea.replace("PROCEDIMIENTO 4", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe4().getCIE9().getDescripcion().equals("")) {
                            String proce4 = "";
                            if (oHosp.getEpisodioMedico().getProceRe4().getCIE9().getDescripcion().length() > 75) {
                                proce4 = oHosp.getEpisodioMedico().getProceRe4().getCIE9().getDescripcion().substring(0, 76);
                                linea = linea.replace("PROCEDIMIENTO 4", proce4);
                            } else {
                                linea = linea.replace("PROCEDIMIENTO 4", oHosp.getEpisodioMedico().getProceRe4().getCIE9().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("PROCEDIMIENTO 4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROCEDIMIENTO 5") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe5().getCIE9().getDescripcion() == null) {
                            linea = linea.replace("PROCEDIMIENTO 5", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe5().getCIE9().getDescripcion().equals("")) {
                            String proce5 = "";
                            if (oHosp.getEpisodioMedico().getProceRe5().getCIE9().getDescripcion().length() > 75) {
                                proce5 = oHosp.getEpisodioMedico().getProceRe5().getCIE9().getDescripcion().substring(0, 76);
                                linea = linea.replace("PROCEDIMIENTO 5", proce5);
                            } else {
                                linea = linea.replace("PROCEDIMIENTO 5", oHosp.getEpisodioMedico().getProceRe5().getCIE9().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("PROCEDIMIENTO 5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROCEDIMIENTO 6") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe6().getCIE9().getDescripcion() == null) {
                            linea = linea.replace("PROCEDIMIENTO 6", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe6().getCIE9().getDescripcion().equals("")) {
                            String proce6 = "";
                            if (oHosp.getEpisodioMedico().getProceRe6().getCIE9().getDescripcion().length() > 75) {
                                proce6 = oHosp.getEpisodioMedico().getProceRe6().getCIE9().getDescripcion().substring(0, 76);
                                linea = linea.replace("PROCEDIMIENTO 6", proce6);
                            } else {
                                linea = linea.replace("PROCEDIMIENTO 6", oHosp.getEpisodioMedico().getProceRe6().getCIE9().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("PROCEDIMIENTO 6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROCEDIMIENTO 7") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe7().getCIE9().getDescripcion() == null) {
                            linea = linea.replace("PROCEDIMIENTO 7", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe7().getCIE9().getDescripcion().equals("")) {
                            String proce7 = "";
                            if (oHosp.getEpisodioMedico().getProceRe7().getCIE9().getDescripcion().length() > 75) {
                                proce7 = oHosp.getEpisodioMedico().getProceRe7().getCIE9().getDescripcion().substring(0, 76);
                                linea = linea.replace("PROCEDIMIENTO 7", proce7);
                            } else {
                                linea = linea.replace("PROCEDIMIENTO 7", oHosp.getEpisodioMedico().getProceRe7().getCIE9().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("PROCEDIMIENTO 7", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PROCEDIMIENTO 8") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe8().getCIE9().getDescripcion() == null) {
                            linea = linea.replace("PROCEDIMIENTO 8", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe8().getCIE9().getDescripcion().equals("")) {
                            String proce8 = "";
                            if (oHosp.getEpisodioMedico().getProceRe8().getCIE9().getDescripcion().length() > 75) {
                                proce8 = oHosp.getEpisodioMedico().getProceRe8().getCIE9().getDescripcion().substring(0, 76);
                                linea = linea.replace("PROCEDIMIENTO 8", proce8);
                            } else {
                                linea = linea.replace("PROCEDIMIENTO 8", oHosp.getEpisodioMedico().getProceRe8().getCIE9().getDescripcion());
                            }
                        } else {
                            linea = linea.replace("PROCEDIMIENTO 8", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Anes1") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP() == null || oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP().equals("     ")) {
                            linea = linea.replace("Anes1", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP().equals("")) {
                            if (oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP().equals("T0701")) {
                                linea = linea.replace("Anes1", "1");
                            } else if (oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP().equals("T0702")) {
                                linea = linea.replace("Anes1", "2");
                            } else if (oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP().equals("T0703")) {
                                linea = linea.replace("Anes1", "3");
                            } else if (oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP().equals("T0704")) {
                                linea = linea.replace("Anes1", "4");
                            } else if (oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP().equals("T0705")) {
                                linea = linea.replace("Anes1", "5");
                            } else if (oHosp.getEpisodioMedico().getProceRe1().getTipoAnestesiaP().equals("T0706")) {
                                linea = linea.replace("Anes1", "6");
                            }
                        } else {
                            linea = linea.replace("Anes1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Anes2") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP() == null || oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP().equals("     ")) {
                            linea = linea.replace("Anes2", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP().equals("")) {
                            if (oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP().equals("T0701")) {
                                linea = linea.replace("Anes2", "1");
                            } else if (oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP().equals("T0702")) {
                                linea = linea.replace("Anes2", "2");
                            } else if (oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP().equals("T0703")) {
                                linea = linea.replace("Anes2", "3");
                            } else if (oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP().equals("T0704")) {
                                linea = linea.replace("Anes2", "4");
                            } else if (oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP().equals("T0705")) {
                                linea = linea.replace("Anes2", "5");
                            } else if (oHosp.getEpisodioMedico().getProceRe2().getTipoAnestesiaP().equals("T0706")) {
                                linea = linea.replace("Anes2", "6");
                            }
                        } else {
                            linea = linea.replace("Anes2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Anes3") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP() == null || oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP().equals("     ")) {
                            linea = linea.replace("Anes3", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP().equals("")) {
                            if (oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP().equals("T0701")) {
                                linea = linea.replace("Anes3", "1");
                            } else if (oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP().equals("T0702")) {
                                linea = linea.replace("Anes3", "2");
                            } else if (oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP().equals("T0703")) {
                                linea = linea.replace("Anes3", "3");
                            } else if (oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP().equals("T0704")) {
                                linea = linea.replace("Anes3", "4");
                            } else if (oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP().equals("T0705")) {
                                linea = linea.replace("Anes3", "5");
                            } else if (oHosp.getEpisodioMedico().getProceRe3().getTipoAnestesiaP().equals("T0706")) {
                                linea = linea.replace("Anes3", "6");
                            }
                        } else {
                            linea = linea.replace("Anes3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Anes4") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP() == null || oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP().equals("     ")) {
                            linea = linea.replace("Anes4", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP().equals("")) {
                            if (oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP().equals("T0701")) {
                                linea = linea.replace("Anes4", "1");
                            } else if (oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP().equals("T0702")) {
                                linea = linea.replace("Anes4", "2");
                            } else if (oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP().equals("T0703")) {
                                linea = linea.replace("Anes4", "3");
                            } else if (oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP().equals("T0704")) {
                                linea = linea.replace("Anes4", "4");
                            } else if (oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP().equals("T0705")) {
                                linea = linea.replace("Anes4", "5");
                            } else if (oHosp.getEpisodioMedico().getProceRe4().getTipoAnestesiaP().equals("T0706")) {
                                linea = linea.replace("Anes4", "6");
                            }
                        } else {
                            linea = linea.replace("Anes4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Anes5") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP() == null || oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP().equals("     ")) {
                            linea = linea.replace("Anes5", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP().equals("")) {
                            if (oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP().equals("T0701")) {
                                linea = linea.replace("Anes5", "1");
                            } else if (oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP().equals("T0702")) {
                                linea = linea.replace("Anes5", "2");
                            } else if (oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP().equals("T0703")) {
                                linea = linea.replace("Anes5", "3");
                            } else if (oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP().equals("T0704")) {
                                linea = linea.replace("Anes5", "4");
                            } else if (oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP().equals("T0705")) {
                                linea = linea.replace("Anes5", "5");
                            } else if (oHosp.getEpisodioMedico().getProceRe5().getTipoAnestesiaP().equals("T0706")) {
                                linea = linea.replace("Anes5", "6");
                            }
                        } else {
                            linea = linea.replace("Anes5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Anes6") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP() == null || oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP().equals("     ")) {
                            linea = linea.replace("Anes6", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP().equals("")) {
                            if (oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP().equals("T0701")) {
                                linea = linea.replace("Anes6", "1");
                            } else if (oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP().equals("T0702")) {
                                linea = linea.replace("Anes6", "2");
                            } else if (oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP().equals("T0703")) {
                                linea = linea.replace("Anes6", "3");
                            } else if (oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP().equals("T0704")) {
                                linea = linea.replace("Anes6", "4");
                            } else if (oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP().equals("T0705")) {
                                linea = linea.replace("Anes6", "5");
                            } else if (oHosp.getEpisodioMedico().getProceRe6().getTipoAnestesiaP().equals("T0706")) {
                                linea = linea.replace("Anes6", "6");
                            }
                        } else {
                            linea = linea.replace("Anes6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Anes7") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP() == null || oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP().equals("     ")) {
                            linea = linea.replace("Anes7", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP().equals("")) {
                            if (oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP().equals("T0701")) {
                                linea = linea.replace("Anes7", "1");
                            } else if (oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP().equals("T0702")) {
                                linea = linea.replace("Anes7", "2");
                            } else if (oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP().equals("T0703")) {
                                linea = linea.replace("Anes7", "3");
                            } else if (oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP().equals("T0704")) {
                                linea = linea.replace("Anes7", "4");
                            } else if (oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP().equals("T0705")) {
                                linea = linea.replace("Anes7", "5");
                            } else if (oHosp.getEpisodioMedico().getProceRe7().getTipoAnestesiaP().equals("T0706")) {
                                linea = linea.replace("Anes7", "6");
                            }
                        } else {
                            linea = linea.replace("Anes7", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("Anes8") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP() == null || oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP().equals("     ")) {
                            linea = linea.replace("Anes8", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP().equals("")) {
                            if (oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP().equals("T0701")) {
                                linea = linea.replace("Anes8", "1");
                            } else if (oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP().equals("T0702")) {
                                linea = linea.replace("Anes8", "2");
                            } else if (oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP().equals("T0703")) {
                                linea = linea.replace("Anes8", "3");
                            } else if (oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP().equals("T0704")) {
                                linea = linea.replace("Anes8", "4");
                            } else if (oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP().equals("T0705")) {
                                linea = linea.replace("Anes8", "5");
                            } else if (oHosp.getEpisodioMedico().getProceRe8().getTipoAnestesiaP().equals("T0706")) {
                                linea = linea.replace("Anes8", "6");
                            }
                        } else {
                            linea = linea.replace("Anes8", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QEn1") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe1().getQuirofano() == null) {
                            linea = linea.replace("QEn1", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe1().getQuirofano().equals("E")) {
                            linea = linea.replace("QEn1", "X");
                        } else {
                            linea = linea.replace("QEn1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QEn2") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe2().getQuirofano() == null) {
                            linea = linea.replace("QEn2", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe2().getQuirofano().equals("E")) {
                            linea = linea.replace("QEn2", "X");
                        } else {
                            linea = linea.replace("QEn2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QEn3") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe3().getQuirofano() == null) {
                            linea = linea.replace("QEn3", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe3().getQuirofano().equals("E")) {
                            linea = linea.replace("QEn3", "X");
                        } else {
                            linea = linea.replace("QEn3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QEn4") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe4().getQuirofano() == null) {
                            linea = linea.replace("QEn4", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe4().getQuirofano().equals("E")) {
                            linea = linea.replace("QEn4", "X");
                        } else {
                            linea = linea.replace("QEn4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QEn5") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe5().getQuirofano() == null) {
                            linea = linea.replace("QEn5", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe5().getQuirofano().equals("E")) {
                            linea = linea.replace("QEn5", "X");
                        } else {
                            linea = linea.replace("QEn5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QEn6") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe6().getQuirofano() == null) {
                            linea = linea.replace("QEn6", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe6().getQuirofano().equals("E")) {
                            linea = linea.replace("QEn6", "X");
                        } else {
                            linea = linea.replace("QEn6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QEn7") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe7().getQuirofano() == null) {
                            linea = linea.replace("QEn7", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe7().getQuirofano().equals("E")) {
                            linea = linea.replace("QEn7", "X");
                        } else {
                            linea = linea.replace("QEn7", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QEn8") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe8().getQuirofano() == null) {
                            linea = linea.replace("QEn8", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe8().getQuirofano().equals("E")) {
                            linea = linea.replace("QEn8", "X");
                        } else {
                            linea = linea.replace("QEn8", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TiempoQ1") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe1().getDuracionP() == null) {
                            linea = linea.replace("TiempoQ1", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe1().getDuracionP().equals("")) {
                            linea = linea.replace("TiempoQ1", oHosp.getEpisodioMedico().getProceRe1().getDuracionP());
                        } else {
                            linea = linea.replace("TiempoQ1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TiempoQ2") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe2().getDuracionP() == null) {
                            linea = linea.replace("TiempoQ2", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe2().getDuracionP().equals("")) {
                            linea = linea.replace("TiempoQ2", oHosp.getEpisodioMedico().getProceRe2().getDuracionP());
                        } else {
                            linea = linea.replace("TiempoQ2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TiempoQ3") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe3().getDuracionP() == null) {
                            linea = linea.replace("TiempoQ3", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe3().getDuracionP().equals("")) {
                            linea = linea.replace("TiempoQ3", oHosp.getEpisodioMedico().getProceRe3().getDuracionP());
                        } else {
                            linea = linea.replace("TiempoQ3", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("TiempoQ4") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe4().getDuracionP() == null) {
                            linea = linea.replace("TiempoQ4", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe4().getDuracionP().equals("")) {
                            linea = linea.replace("TiempoQ4", oHosp.getEpisodioMedico().getProceRe4().getDuracionP());
                        } else {
                            linea = linea.replace("TiempoQ4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TiempoQ5") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe5().getDuracionP() == null) {
                            linea = linea.replace("TiempoQ5", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe5().getDuracionP().equals("")) {
                            linea = linea.replace("TiempoQ5", oHosp.getEpisodioMedico().getProceRe5().getDuracionP());
                        } else {
                            linea = linea.replace("TiempoQ5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TiempoQ6") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe6().getDuracionP() == null) {
                            linea = linea.replace("TiempoQ6", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe6().getDuracionP().equals("")) {
                            linea = linea.replace("TiempoQ6", oHosp.getEpisodioMedico().getProceRe6().getDuracionP());
                        } else {
                            linea = linea.replace("TiempoQ8", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TiempoQ7") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe7().getDuracionP() == null) {
                            linea = linea.replace("TiempoQ7", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe7().getDuracionP().equals("")) {
                            linea = linea.replace("TiempoQ7", oHosp.getEpisodioMedico().getProceRe7().getDuracionP());
                        } else {
                            linea = linea.replace("TiempoQ7", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TiempoQ8") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe8().getDuracionP() == null) {
                            linea = linea.replace("TiempoQ8", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe8().getDuracionP().equals("")) {
                            linea = linea.replace("TiempoQ8", oHosp.getEpisodioMedico().getProceRe8().getDuracionP());
                        } else {
                            linea = linea.replace("TiempoQ8", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QFu1") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe1().getQuirofano() == null) {
                            linea = linea.replace("QFu1", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe1().getQuirofano().equals("F")) {
                            linea = linea.replace("QFu1", "X");
                        } else {
                            linea = linea.replace("QFu1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QFu2") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe2().getQuirofano() == null) {
                            linea = linea.replace("QFu2", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe2().getQuirofano().equals("F")) {
                            linea = linea.replace("QFu2", "X");
                        } else {
                            linea = linea.replace("QFu2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QFu3") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe3().getQuirofano() == null) {
                            linea = linea.replace("QFu3", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe3().getQuirofano().equals("F")) {
                            linea = linea.replace("QFu3", "X");
                        } else {
                            linea = linea.replace("QFu3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QFu4") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe4().getQuirofano() == null) {
                            linea = linea.replace("QFu4", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe4().getQuirofano().equals("F")) {
                            linea = linea.replace("QFu4", "X");
                        } else {
                            linea = linea.replace("QFu4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QFu5") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe5().getQuirofano() == null) {
                            linea = linea.replace("QFu5", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe5().getQuirofano().equals("F")) {
                            linea = linea.replace("QFu5", "X");
                        } else {
                            linea = linea.replace("QFu5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QFu6") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe6().getQuirofano() == null) {
                            linea = linea.replace("QFu6", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe6().getQuirofano().equals("F")) {
                            linea = linea.replace("QFu6", "X");
                        } else {
                            linea = linea.replace("QFu6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QFu7") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe7().getQuirofano() == null) {
                            linea = linea.replace("QFu7", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe7().getQuirofano().equals("F")) {
                            linea = linea.replace("QFu7", "X");
                        } else {
                            linea = linea.replace("QFu7", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("QFu8") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe8().getQuirofano() == null) {
                            linea = linea.replace("QFu8", "&nbsp;");
                        } else if (oHosp.getEpisodioMedico().getProceRe8().getQuirofano().equals("F")) {
                            linea = linea.replace("QFu8", "X");
                        } else {
                            linea = linea.replace("QFu8", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("MEDICO C1") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe1().getCirujano().getNombres() == null) {
                            linea = linea.replace("MEDICO C1", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe1().getCirujano().getNombres().equals("")) {
                            linea = linea.replace("MEDICO C1", obtenCedulaProf(oHosp.getEpisodioMedico().getProceRe1().getCirujano().getNombres()));
                        } else {
                            linea = linea.replace("MEDICO C1", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("MEDICO C2") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe2().getCirujano().getNombres() == null) {
                            linea = linea.replace("MEDICO C2", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe2().getCirujano().getNombres().equals("")) {
                            linea = linea.replace("MEDICO C2", obtenCedulaProf(oHosp.getEpisodioMedico().getProceRe2().getCirujano().getNombres()));
                        } else {
                            linea = linea.replace("MEDICO C2", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("MEDICO C3") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe3().getCirujano().getNombres() == null) {
                            linea = linea.replace("MEDICO C3", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe3().getCirujano().getNombres().equals("")) {
                            linea = linea.replace("MEDICO C3", obtenCedulaProf(oHosp.getEpisodioMedico().getProceRe3().getCirujano().getNombres()));
                        } else {
                            linea = linea.replace("MEDICO C3", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("MEDICO C4") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe4().getCirujano().getNombres() == null) {
                            linea = linea.replace("MEDICO C4", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe4().getCirujano().getNombres().equals("")) {
                            linea = linea.replace("MEDICO C4", obtenCedulaProf(oHosp.getEpisodioMedico().getProceRe4().getCirujano().getNombres()));
                        } else {
                            linea = linea.replace("MEDICO C4", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("MEDICO C5") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe5().getCirujano().getNombres() == null) {
                            linea = linea.replace("MEDICO C5", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe5().getCirujano().getNombres().equals("")) {
                            linea = linea.replace("MEDICO C5", obtenCedulaProf(oHosp.getEpisodioMedico().getProceRe5().getCirujano().getNombres()));
                        } else {
                            linea = linea.replace("MEDICO C5", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("MEDICO C6") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe6().getCirujano().getNombres() == null) {
                            linea = linea.replace("MEDICO C6", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe6().getCirujano().getNombres().equals("")) {
                            linea = linea.replace("MEDICO C6", obtenCedulaProf(oHosp.getEpisodioMedico().getProceRe6().getCirujano().getNombres()));
                        } else {
                            linea = linea.replace("MEDICO C6", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("MEDICO C7") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe7().getCirujano().getNombres() == null) {
                            linea = linea.replace("MEDICO C7", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe7().getCirujano().getNombres().equals("")) {
                            linea = linea.replace("MEDICO C7", obtenCedulaProf(oHosp.getEpisodioMedico().getProceRe7().getCirujano().getNombres()));
                        } else {
                            linea = linea.replace("MEDICO C7", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("MEDICO C8") > 0) {
                        if (oHosp.getEpisodioMedico().getProceRe8().getCirujano().getNombres() == null) {
                            linea = linea.replace("MEDICO C8", "&nbsp;");
                        } else if (!oHosp.getEpisodioMedico().getProceRe8().getCirujano().getNombres().equals("")) {
                            linea = linea.replace("MEDICO C8", obtenCedulaProf(oHosp.getEpisodioMedico().getProceRe8().getCirujano().getNombres()));
                        } else {
                            linea = linea.replace("MEDICO C8", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("C9P1") > 0) {
                        if (!oHosp.getEpisodioMedico().getProceRe1().getCIE9().getClaveCIE9().equals("")) {
                            linea = linea.replace("C9P1", oHosp.getEpisodioMedico().getProceRe1().getCIE9().getClaveCIE9());
                        } else {
                            linea = linea.replace("C9P1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("C9P2") > 0) {
                        if (!oHosp.getEpisodioMedico().getProceRe2().getCIE9().getClaveCIE9().equals("")) {
                            linea = linea.replace("C9P2", oHosp.getEpisodioMedico().getProceRe2().getCIE9().getClaveCIE9());
                        } else {
                            linea = linea.replace("C9P2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("C9P3") > 0) {
                        if (!oHosp.getEpisodioMedico().getProceRe3().getCIE9().getClaveCIE9().equals("")) {
                            linea = linea.replace("C9P3", oHosp.getEpisodioMedico().getProceRe3().getCIE9().getClaveCIE9());
                        } else {
                            linea = linea.replace("C9P3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("C9P4") > 0) {
                        if (!oHosp.getEpisodioMedico().getProceRe4().getCIE9().getClaveCIE9().equals("")) {
                            linea = linea.replace("C9P4", oHosp.getEpisodioMedico().getProceRe4().getCIE9().getClaveCIE9());
                        } else {
                            linea = linea.replace("C9P4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("C9P5") > 0) {
                        if (!oHosp.getEpisodioMedico().getProceRe5().getCIE9().getClaveCIE9().equals("")) {
                            linea = linea.replace("C9P5", oHosp.getEpisodioMedico().getProceRe5().getCIE9().getClaveCIE9());
                        } else {
                            linea = linea.replace("C9P5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("C9P6") > 0) {
                        if (!oHosp.getEpisodioMedico().getProceRe6().getCIE9().getClaveCIE9().equals("")) {
                            linea = linea.replace("C9P6", oHosp.getEpisodioMedico().getProceRe6().getCIE9().getClaveCIE9());
                        } else {
                            linea = linea.replace("C9P6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("C9P7") > 0) {
                        if (!oHosp.getEpisodioMedico().getProceRe7().getCIE9().getClaveCIE9().equals("")) {
                            linea = linea.replace("C9P7", oHosp.getEpisodioMedico().getProceRe7().getCIE9().getClaveCIE9());
                        } else {
                            linea = linea.replace("C9P7", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("C9P8") > 0) {
                        if (!oHosp.getEpisodioMedico().getProceRe8().getCIE9().getClaveCIE9().equals("")) {
                            linea = linea.replace("C9P8", oHosp.getEpisodioMedico().getProceRe8().getCIE9().getClaveCIE9());
                        } else {
                            linea = linea.replace("C9P8", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MinisPubS") > 0) {
                        if (oHosp.getEpisodioMedico().getAvisoMinisterioDefun() != null && !oHosp.getEpisodioMedico().getAvisoMinisterioDefun().equals("")) {
                            if (oHosp.getEpisodioMedico().getAvisoMinisterioDefun().equals("S")) {
                                linea = linea.replace("MinisPubS", "X");
                            } else {
                                linea = linea.replace("MinisPubS", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("MinisPubS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MinisPubN") > 0) {
                        if (oHosp.getEpisodioMedico().getAvisoMinisterioDefun() != null && !oHosp.getEpisodioMedico().getAvisoMinisterioDefun().equals("")) {
                            if (oHosp.getEpisodioMedico().getAvisoMinisterioDefun().equals("N")) {
                                linea = linea.replace("MinisPubN", "X");
                            } else {
                                linea = linea.replace("MinisPubN", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("MinisPubN", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("FolioDefu") > 0) {
                        if (oHosp.getPaciente().getFolioDefuncion() == null) {
                            linea = linea.replace("FolioDefu", "&nbsp;");
                        }
                        if (!oHosp.getPaciente().getFolioDefuncion().equals("")) {
                            linea = linea.replace("FolioDefu", oHosp.getPaciente().getFolioDefuncion());
                        } else {
                            linea = linea.replace("FolioDefu", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CAUSA MUERTE A") > 0) {
                        if (oHosDef.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("CAUSA MUERTE A", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().equals("")) {
                            String causaA = "";
                            if (oHosDef.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().length() > 95) {
                                causaA = oHosDef.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().substring(0, 96);
                                linea = linea.replace("CAUSA MUERTE A", causaA);
                            } else {
                                linea = linea.replace("CAUSA MUERTE A", oHosDef.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("CAUSA MUERTE A", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CAUSA MUERTE B") > 0) {
                        if (oHosDef.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("CAUSA MUERTE B", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().equals("")) {
                            String causaB = "";
                            if (oHosDef.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().length() > 95) {
                                causaB = oHosDef.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().substring(0, 96);
                                linea = linea.replace("CAUSA MUERTE B", causaB);
                            } else {
                                linea = linea.replace("CAUSA MUERTE B", oHosDef.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("CAUSA MUERTE B", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CAUSA MUERTE C") > 0) {
                        if (oHosDef.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("CAUSA MUERTE C", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag().equals("")) {
                            String causaC = "";
                            if (oHosDef.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag().length() > 95) {
                                causaC = oHosDef.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag().substring(0, 96);
                                linea = linea.replace("CAUSA MUERTE C", causaC);
                            } else {
                                linea = linea.replace("CAUSA MUERTE C", oHosDef.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("CAUSA MUERTE C", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CAUSA MUERTE D") > 0) {
                        if (oHosDef.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("CAUSA MUERTE D", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag().equals("")) {
                            String causaD = "";
                            if (oHosDef.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag().length() > 95) {
                                causaD = oHosDef.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag().substring(0, 96);
                                linea = linea.replace("CAUSA MUERTE D", causaD);
                            } else {
                                linea = linea.replace("CAUSA MUERTE D", oHosDef.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("CAUSA MUERTE D", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CAUSA MUERTE E") > 0) {
                        if (oHosDef.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("CAUSA MUERTE E", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag().equals("")) {
                            String causaE = "";
                            if (oHosDef.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag().length() > 95) {
                                causaE = oHosDef.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag().substring(0, 96);
                                linea = linea.replace("CAUSA MUERTE E", causaE);
                            } else {
                                linea = linea.replace("CAUSA MUERTE E", oHosDef.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("CAUSA MUERTE E", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CAUSA MUERTE F") > 0) {
                        if (oHosDef.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("CAUSA MUERTE F", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag().equals("")) {
                            String causaF = "";
                            if (oHosDef.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag().length() > 95) {
                                causaF = oHosDef.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag().substring(0, 96);
                                linea = linea.replace("CAUSA MUERTE F", causaF);
                            } else {
                                linea = linea.replace("CAUSA MUERTE F", oHosDef.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("CAUSA MUERTE C", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIEMPO APROX A") > 0) {
                        int tiem = 0;
                        if (oHosDef.getEpisodioMedico().getAfePrimera().getNumEnfermedadMuerte() > 0) {
                            tiem = oHosDef.getEpisodioMedico().getAfePrimera().getNumEnfermedadMuerte();
                        }
                        if (oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP() == null || oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP().equals("     ")) {
                            linea = linea.replace("TIEMPO APROX A", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP().equals("")) {
                            if (oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP().equals("T5501")) {
                                linea = linea.replace("TIEMPO APROX A", "" + tiem + " HORAS");
                            } else if (oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP().equals("T5502")) {
                                linea = linea.replace("TIEMPO APROX A", "" + tiem + " MINUTOS");
                            } else if (oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP().equals("T5503")) {
                                linea = linea.replace("TIEMPO APROX A", "" + tiem + " D&Iacute;AS");
                            } else if (oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP().equals("T5504")) {
                                linea = linea.replace("TIEMPO APROX A", "" + tiem + " SEMANAS");
                            } else if (oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP().equals("T5505")) {
                                linea = linea.replace("TIEMPO APROX A", "" + tiem + " MESES");
                            } else if (oHosDef.getEpisodioMedico().getAfePrimera().getTiempoEnfermedadMuerteP().equals("T5506")) {
                                linea = linea.replace("TIEMPO APROX A", "" + tiem + " A&Ntilde;OS");
                            }
                        } else {
                            linea = linea.replace("TIEMPO APROX A", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIEMPO APROX B") > 0) {
                        int tiem = 0;
                        if (oHosDef.getEpisodioMedico().getAfeSegunda().getNumEnfermedadMuerte() > 0) {
                            tiem = oHosDef.getEpisodioMedico().getAfeSegunda().getNumEnfermedadMuerte();
                        }
                        if (oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP() == null || oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP().equals("     ")) {
                            linea = linea.replace("TIEMPO APROX B", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP().equals("")) {
                            if (oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP().equals("T5501")) {
                                linea = linea.replace("TIEMPO APROX B", "" + tiem + " HORAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP().equals("T5502")) {
                                linea = linea.replace("TIEMPO APROX B", "" + tiem + " MINUTOS");
                            } else if (oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP().equals("T5503")) {
                                linea = linea.replace("TIEMPO APROX B", "" + tiem + " D&Iacute;AS");
                            } else if (oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP().equals("T5504")) {
                                linea = linea.replace("TIEMPO APROX B", "" + tiem + " SEMANAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP().equals("T5505")) {
                                linea = linea.replace("TIEMPO APROX B", "" + tiem + " MESES");
                            } else if (oHosDef.getEpisodioMedico().getAfeSegunda().getTiempoEnfermedadMuerteP().equals("T5506")) {
                                linea = linea.replace("TIEMPO APROX B", "" + tiem + " A&Ntilde;OS");
                            }
                        } else {
                            linea = linea.replace("TIEMPO APROX B", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIEMPO APROX C") > 0) {
                        int tiem = 0;
                        if (oHosDef.getEpisodioMedico().getAfeTercera().getNumEnfermedadMuerte() > 0) {
                            tiem = oHosDef.getEpisodioMedico().getAfeTercera().getNumEnfermedadMuerte();
                        }
                        if (oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP() == null || oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP().equals("     ")) {
                            linea = linea.replace("TIEMPO APROX C", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP().equals("")) {
                            if (oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP().equals("T5501")) {
                                linea = linea.replace("TIEMPO APROX C", "" + tiem + " HORAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP().equals("T5502")) {
                                linea = linea.replace("TIEMPO APROX C", "" + tiem + " MINUTOS");
                            } else if (oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP().equals("T5503")) {
                                linea = linea.replace("TIEMPO APROX C", "" + tiem + " D&Iacute;AS");
                            } else if (oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP().equals("T5504")) {
                                linea = linea.replace("TIEMPO APROX C", "" + tiem + " SEMANAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP().equals("T5505")) {
                                linea = linea.replace("TIEMPO APROX C", "" + tiem + " MESES");
                            } else if (oHosDef.getEpisodioMedico().getAfeTercera().getTiempoEnfermedadMuerteP().equals("T5506")) {
                                linea = linea.replace("TIEMPO APROX C", "" + tiem + " A&Ntilde;OS");
                            }
                        } else {
                            linea = linea.replace("TIEMPO APROX C", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIEMPO APROX D") > 0) {
                        int tiem = 0;
                        if (oHosDef.getEpisodioMedico().getAfeCuarta().getNumEnfermedadMuerte() > 0) {
                            tiem = oHosDef.getEpisodioMedico().getAfeCuarta().getNumEnfermedadMuerte();
                        }
                        if (oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP() == null || oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP().equals("     ")) {
                            linea = linea.replace("TIEMPO APROX D", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP().equals("")) {
                            if (oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP().equals("T5501")) {
                                linea = linea.replace("TIEMPO APROX D", "" + tiem + " HORAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP().equals("T5502")) {
                                linea = linea.replace("TIEMPO APROX D", "" + tiem + " MINUTOS");
                            } else if (oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP().equals("T5503")) {
                                linea = linea.replace("TIEMPO APROX D", "" + tiem + " D&Iacute;AS");
                            } else if (oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP().equals("T5504")) {
                                linea = linea.replace("TIEMPO APROX D", "" + tiem + " SEMANAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP().equals("T5505")) {
                                linea = linea.replace("TIEMPO APROX D", "" + tiem + " MESES");
                            } else if (oHosDef.getEpisodioMedico().getAfeCuarta().getTiempoEnfermedadMuerteP().equals("T5506")) {
                                linea = linea.replace("TIEMPO APROX D", "" + tiem + " A&Ntilde;OS");
                            }
                        } else {
                            linea = linea.replace("TIEMPO APROX D", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIEMPO APROX E") > 0) {
                        int tiem = 0;
                        if (oHosDef.getEpisodioMedico().getAfeQuinta().getNumEnfermedadMuerte() > 0) {
                            tiem = oHosDef.getEpisodioMedico().getAfeQuinta().getNumEnfermedadMuerte();
                        }
                        if (oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP() == null || oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP().equals("     ")) {
                            linea = linea.replace("TIEMPO APROX E", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP().equals("")) {
                            if (oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP().equals("T5501")) {
                                linea = linea.replace("TIEMPO APROX E", "" + tiem + " HORAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP().equals("T5502")) {
                                linea = linea.replace("TIEMPO APROX E", "" + tiem + " MINUTOS");
                            } else if (oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP().equals("T5503")) {
                                linea = linea.replace("TIEMPO APROX E", "" + tiem + " D&Iacute;AS");
                            } else if (oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP().equals("T5504")) {
                                linea = linea.replace("TIEMPO APROX E", "" + tiem + " SEMANAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP().equals("T5505")) {
                                linea = linea.replace("TIEMPO APROX E", "" + tiem + " MESES");
                            } else if (oHosDef.getEpisodioMedico().getAfeQuinta().getTiempoEnfermedadMuerteP().equals("T5506")) {
                                linea = linea.replace("TIEMPO APROX E", "" + tiem + " A&Ntilde;OS");
                            }
                        } else {
                            linea = linea.replace("TIEMPO APROX E", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIEMPO APROX F") > 0) {
                        int tiem = 0;
                        if (oHosDef.getEpisodioMedico().getAfeSexta().getNumEnfermedadMuerte() > 0) {
                            tiem = oHosDef.getEpisodioMedico().getAfeSexta().getNumEnfermedadMuerte();
                        }
                        if (oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP() == null || oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP().equals("     ")) {
                            linea = linea.replace("TIEMPO APROX F", "&nbsp;");
                        } else if (!oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP().equals("")) {
                            if (oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP().equals("T5501")) {
                                linea = linea.replace("TIEMPO APROX F", "" + tiem + " HORAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP().equals("T5502")) {
                                linea = linea.replace("TIEMPO APROX F", "" + tiem + " MINUTOS");
                            } else if (oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP().equals("T5503")) {
                                linea = linea.replace("TIEMPO APROX F", "" + tiem + " D&Iacute;AS");
                            } else if (oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP().equals("T5504")) {
                                linea = linea.replace("TIEMPO APROX F", "" + tiem + " SEMANAS");
                            } else if (oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP().equals("T5505")) {
                                linea = linea.replace("TIEMPO APROX F", "" + tiem + " MESES");
                            } else if (oHosDef.getEpisodioMedico().getAfeSexta().getTiempoEnfermedadMuerteP().equals("T5506")) {
                                linea = linea.replace("TIEMPO APROX F", "" + tiem + " A&Ntilde;OS");
                            }
                        } else {
                            linea = linea.replace("TIEMPO APROX F", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CD1A") > 0) {
                        if (!oHosDef.getEpisodioMedico().getAfePrimera().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CD1A", oHosDef.getEpisodioMedico().getAfePrimera().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CD1A", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CD1B") > 0) {
                        if (!oHosDef.getEpisodioMedico().getAfeSegunda().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CD1B", oHosDef.getEpisodioMedico().getAfeSegunda().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CD1B", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CD1C") > 0) {
                        if (!oHosDef.getEpisodioMedico().getAfeTercera().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CD1C", oHosDef.getEpisodioMedico().getAfeTercera().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CD1C", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CD1D") > 0) {
                        if (!oHosDef.getEpisodioMedico().getAfeCuarta().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CD1D", oHosDef.getEpisodioMedico().getAfeCuarta().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CD1D", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CD1E") > 0) {
                        if (!oHosDef.getEpisodioMedico().getAfeQuinta().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CD1E", oHosDef.getEpisodioMedico().getAfeQuinta().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CD1E", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CD1F") > 0) {
                        if (!oHosDef.getEpisodioMedico().getAfeSexta().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CD1F", oHosDef.getEpisodioMedico().getAfeSexta().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CD1F", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CD1R") > 0) {
                        if (!oHosDef.getEpisodioMedico().getAfeResAPDef().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CD1R", oHosDef.getEpisodioMedico().getAfeResAPDef().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CD1R", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NumGes") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getGestaciones() > 0) {
                            String gestaciones = "" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getGestaciones();
                            if (gestaciones.length() == 1) {
                                linea = linea.replace("NumGes", "&nbsp;" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getGestaciones());
                            } else if (gestaciones.length() == 2) {
                                linea = linea.replace("NumGes", "" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getGestaciones());
                            }
                        } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getGestaciones() == 0 && (oHosp.getAtencionObstetrica().getTipoAtencion() == 'A' || oHosp.getAtencionObstetrica().getTipoAtencion() == 'P')) {
                            linea = linea.replace("NumGes", "&nbsp;0");
                        } else {
                            linea = linea.replace("NumGes", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NumPar") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getPartos() > 0) {
                            String partos = "" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getPartos();
                            if (partos.length() == 1) {
                                linea = linea.replace("NumPar", "&nbsp;" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getPartos());
                            } else if (partos.length() == 2) {
                                linea = linea.replace("NumPar", "" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getPartos());
                            }
                        } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getPartos() == 0 && (oHosp.getAtencionObstetrica().getTipoAtencion() == 'A' || oHosp.getAtencionObstetrica().getTipoAtencion() == 'P')) {
                            linea = linea.replace("NumPar", "&nbsp;0");
                        } else {
                            linea = linea.replace("NumPar", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NumAbor") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getAbortos() > 0) {
                            String abortos = "" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getAbortos();
                            if (abortos.length() == 1) {
                                linea = linea.replace("NumAbor", "&nbsp;" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getAbortos());
                            } else if (abortos.length() == 2) {
                                linea = linea.replace("NumAbor", "" + oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getAbortos());
                            }
                        } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getAbortos() == 0 && (oHosp.getAtencionObstetrica().getTipoAtencion() == 'A' || oHosp.getAtencionObstetrica().getTipoAtencion() == 'P')) {
                            linea = linea.replace("NumAbor", "&nbsp;0");
                        } else {
                            linea = linea.replace("NumAbor", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ExtExpS") > 0) {
                        if (oHosp.getPaciente().getSexoP().equals("Masculino")) {
                            linea = linea.replace("ExtExpS", "&nbsp;");
                        } else if (oHosp.getAtencionObstetrica().getExtraccion() != null && !oHosp.getAtencionObstetrica().getExtraccion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getExtraccion().equals("S")) {
                                linea = linea.replace("ExtExpS", "X");
                            } else {
                                linea = linea.replace("ExtExpS", "&nbsp;");
                            }
                        } else if (oHosp.getAtencionObstetrica().getExtraccion() == null || oHosp.getAtencionObstetrica().getExtraccion().equals("")) {
                            linea = linea.replace("ExtExpS", "&nbsp;");
                        } else {
                            linea = linea.replace("ExtExpS", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ExtExpN") > 0) {
                        if (oHosp.getPaciente().getSexoP().equals("Masculino")) {
                            linea = linea.replace("ExtExpN", "&nbsp;");
                        } else if (oHosp.getAtencionObstetrica().getExtraccion() != null && !oHosp.getAtencionObstetrica().getExtraccion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getExtraccion().equals("N")) {
                                linea = linea.replace("ExtExpN", "X");
                            } else {
                                linea = linea.replace("ExtExpN", "&nbsp;");
                            }
                        } else if (oHosp.getAtencionObstetrica().getExtraccion() == null || oHosp.getAtencionObstetrica().getExtraccion().equals("")) {
                            linea = linea.replace("ExtExpN", "&nbsp;");
                        } else {
                            linea = linea.replace("ExtExpN", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AB0RT0") > 0) {
                        if (oHosp.getAtencionObstetrica().getTipoAtencion() == 'A') {
                            linea = linea.replace("AB0RT0", "X");
                        } else {
                            linea = linea.replace("AB0RT0", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PART0") > 0) {
                        if (oHosp.getAtencionObstetrica().getTipoAtencion() == 'P') {
                            linea = linea.replace("PART0", "X");
                        } else {
                            linea = linea.replace("PART0", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SEMGES") > 0) {
                        if (oHosp.getAtencionObstetrica().getSemanasGestacion() > 0) {
                            String semanas = "" + oHosp.getAtencionObstetrica().getSemanasGestacion();
                            if (semanas.length() == 1) {
                                linea = linea.replace("SEMGES", "&nbsp;" + oHosp.getAtencionObstetrica().getSemanasGestacion());
                            } else if (semanas.length() == 2) {
                                linea = linea.replace("SEMGES", "" + oHosp.getAtencionObstetrica().getSemanasGestacion());
                            }
                        } else {
                            linea = linea.replace("SEMGES", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("UN1C0") > 0) {
                        if (oHosp.getAtencionObstetrica().getConProducto() == 'U') {
                            linea = linea.replace("UN1C0", "X");
                        } else {
                            linea = linea.replace("UN1C0", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MULT1PL3") > 0) {
                        if (oHosp.getAtencionObstetrica().getConProducto() == 'M') {
                            linea = linea.replace("MULT1PL3", "X");
                        } else {
                            linea = linea.replace("MULT1PL3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("EUTOC1C0") > 0) {
                        //System.out.println("atencion== "+oHosp.getAtencionObstetrica().getTipoNacimientoP());
                        if(oHosp.getAtencionObstetrica().getTipoNacimientoP()!=null){
                        if (!oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0801") && !oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0802") && !oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0803")) {
                            linea = linea.replace("EUTOC1C0", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0801")) {
                                linea = linea.replace("EUTOC1C0", "X");
                            } else if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0802")) {
                                linea = linea.replace("EUTOC1C0", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0803")) {
                                linea = linea.replace("EUTOC1C0", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("EUTOC1C0", "&nbsp;");
                        }
                        } else{
                            linea = linea.replace("EUTOC1C0", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("D1ST0C1C0") > 0) {
                        if(oHosp.getAtencionObstetrica().getTipoNacimientoP()!=null){
                        if (!oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0801") && !oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0802") && !oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0803")) {
                            linea = linea.replace("D1ST0C1C0", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0801")) {
                                linea = linea.replace("D1ST0C1C0", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0802")) {
                                linea = linea.replace("D1ST0C1C0", "X");
                            } else if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0803")) {
                                linea = linea.replace("D1ST0C1C0", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("D1ST0C1C0", "&nbsp;");
                        }
                        }
                        else {
                            linea = linea.replace("D1ST0C1C0", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("C3S4R34") > 0) {
                        if(oHosp.getAtencionObstetrica().getTipoNacimientoP()!=null){
                        if (!oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0801") && !oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0802") && !oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0803")) {
                            linea = linea.replace("C3S4R34", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0801")) {
                                linea = linea.replace("C3S4R34", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0802")) {
                                linea = linea.replace("C3S4R34", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getTipoNacimientoP().equals("T0803")) {
                                linea = linea.replace("C3S4R34", "X");
                            }
                        } else {
                            linea = linea.replace("C3S4R34", "&nbsp;");
                        }
                        } else {
                            linea = linea.replace("C3S4R34", "&nbsp;");
                        }
                        
                    }
                    if (linea.indexOf("METODO 1") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave() == null) {
                            linea = linea.replace("METODO 1", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("")) {
                            if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("1 ")) {
                                linea = linea.replace("METODO 1", "X");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("2 ")) {
                                linea = linea.replace("METODO 1", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("3 ")) {
                                linea = linea.replace("METODO 1", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("4 ")) {
                                linea = linea.replace("METODO 1", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("5 ")) {
                                linea = linea.replace("METODO 1", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("6 ")) {
                                linea = linea.replace("METODO 1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("METODO 1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("METODO 2") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave() == null) {
                            linea = linea.replace("METODO 2", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("")) {
                            if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("1 ")) {
                                linea = linea.replace("METODO 2", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("2 ")) {
                                linea = linea.replace("METODO 2", "X");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("3 ")) {
                                linea = linea.replace("METODO 2", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("4 ")) {
                                linea = linea.replace("METODO 2", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("5 ")) {
                                linea = linea.replace("METODO 2", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("6 ")) {
                                linea = linea.replace("METODO 2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("METODO 2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("METODO 3") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave() == null) {
                            linea = linea.replace("METODO 3", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("")) {
                            if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("1 ")) {
                                linea = linea.replace("METODO 3", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("2 ")) {
                                linea = linea.replace("METODO 3", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("3 ")) {
                                linea = linea.replace("METODO 3", "X");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("4 ")) {
                                linea = linea.replace("METODO 3", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("5 ")) {
                                linea = linea.replace("METODO 3", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("6 ")) {
                                linea = linea.replace("METODO 3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("METODO 3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("METODO 4") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave() == null) {
                            linea = linea.replace("METODO 4", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("")) {
                            if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("1 ")) {
                                linea = linea.replace("METODO 4", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("2 ")) {
                                linea = linea.replace("METODO 4", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("3 ")) {
                                linea = linea.replace("METODO 4", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("4 ")) {
                                linea = linea.replace("METODO 4", "X");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("5 ")) {
                                linea = linea.replace("METODO 4", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("6 ")) {
                                linea = linea.replace("METODO 4", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("METODO 4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("METODO 5") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave() == null) {
                            linea = linea.replace("METODO 5", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("")) {
                            if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("1 ")) {
                                linea = linea.replace("METODO 5", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("2 ")) {
                                linea = linea.replace("METODO 5", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("3 ")) {
                                linea = linea.replace("METODO 5", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("4 ")) {
                                linea = linea.replace("METODO 5", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("5 ")) {
                                linea = linea.replace("METODO 5", "X");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("6 ")) {
                                linea = linea.replace("METODO 5", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("METODO 5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("METODO 6") > 0) {
                        if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave() == null) {
                            linea = linea.replace("METODO 6", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("")) {
                            if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("1 ")) {
                                linea = linea.replace("METODO 6", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("2 ")) {
                                linea = linea.replace("METODO 6", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("3 ")) {
                                linea = linea.replace("METODO 6", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("4 ")) {
                                linea = linea.replace("METODO 6", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("5 ")) {
                                linea = linea.replace("METODO 6", "&nbsp;");
                            } else if (oHosp.getAtencionObstetrica().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave().equals("6 ")) {
                                linea = linea.replace("METODO 6", "X");
                            }
                        } else {
                            linea = linea.replace("METODO 6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PESONACPRO1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getPesoAlNacer() > 0) {
                            linea = linea.replace("PESONACPRO1", "" + oHosp.getAtencionObstetrica().getP1().getPesoAlNacer());
                        } else {
                            linea = linea.replace("PESONACPRO1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PESONACPRO2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getPesoAlNacer() > 0) {
                            linea = linea.replace("PESONACPRO2", "" + oHosp.getAtencionObstetrica().getP2().getPesoAlNacer());
                        } else {
                            linea = linea.replace("PESONACPRO2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("PESONACPRO3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getPesoAlNacer() > 0) {
                            linea = linea.replace("PESONACPRO3", "" + oHosp.getAtencionObstetrica().getP3().getPesoAlNacer());
                        } else {
                            linea = linea.replace("PESONACPRO3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SexoMasP1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getSexoProductoP() == null) {
                            linea = linea.replace("SexoMasP1", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP1().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP1().getSexoProductoP().equals("T0901")) {
                                linea = linea.replace("SexoMasP1", "X");
                            } else {
                                linea = linea.replace("SexoMasP1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoMasP1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SexoMasP2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getSexoProductoP() == null) {
                            linea = linea.replace("SexoMasP2", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP2().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP2().getSexoProductoP().equals("T0901")) {
                                linea = linea.replace("SexoMasP2", "X");
                            } else {
                                linea = linea.replace("SexoMasP2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoMasP2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SexoMasP3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getSexoProductoP() == null) {
                            linea = linea.replace("SexoMasP3", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP3().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP3().getSexoProductoP().equals("T0901")) {
                                linea = linea.replace("SexoMasP3", "X");
                            } else {
                                linea = linea.replace("SexoMasP3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoMasP3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SexoFemP1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getSexoProductoP() == null) {
                            linea = linea.replace("SexoFemP1", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP1().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP1().getSexoProductoP().equals("T0902")) {
                                linea = linea.replace("SexoFemP1", "X");
                            } else {
                                linea = linea.replace("SexoFemP1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoFemP1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SexoFemP2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getSexoProductoP() == null) {
                            linea = linea.replace("SexoFemP2", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP2().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP2().getSexoProductoP().equals("T0902")) {
                                linea = linea.replace("SexoFemP2", "X");
                            } else {
                                linea = linea.replace("SexoFemP2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoFemP2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SexoFemP3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getSexoProductoP() == null) {
                            linea = linea.replace("SexoFemP3", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP3().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP3().getSexoProductoP().equals("T0902")) {
                                linea = linea.replace("SexoFemP3", "X");
                            } else {
                                linea = linea.replace("SexoFemP3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoFemP3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SexoNEsP1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getSexoProductoP() == null) {
                            linea = linea.replace("SexoNEsP1", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP1().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP1().getSexoProductoP().equals("T0903")) {
                                linea = linea.replace("SexoNEsP1", "X");
                            } else {
                                linea = linea.replace("SexoNEsP1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoNEsP1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SexoNEsP2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getSexoProductoP() == null) {
                            linea = linea.replace("SexoNEsP2", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP2().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP2().getSexoProductoP().equals("T0903")) {
                                linea = linea.replace("SexoNEsP2", "X");
                            } else {
                                linea = linea.replace("SexoNEsP2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoNEsP2", "&nbsp;");
                        }
                    }

                    if (linea.indexOf("SexoNEsP3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getSexoProductoP() == null) {
                            linea = linea.replace("SexoNEsP3", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP3().getSexoProductoP().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP3().getSexoProductoP().equals("T0903")) {
                                linea = linea.replace("SexoNEsP3", "X");
                            } else {
                                linea = linea.replace("SexoNEsP3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("SexoNEsP3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MUERTEFETALP1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getCondicionNacimiento() == 'M') {
                            linea = linea.replace("MUERTEFETALP1", "X");
                        } else {
                            linea = linea.replace("MUERTEFETALP1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MUERTEFETALP2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getCondicionNacimiento() == 'M') {
                            linea = linea.replace("MUERTEFETALP2", "X");
                        } else {
                            linea = linea.replace("MUERTEFETALP2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MUERTEFETALP3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getCondicionNacimiento() == 'M') {
                            linea = linea.replace("MUERTEFETALP3", "X");
                        } else {
                            linea = linea.replace("MUERTEFETALP3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NACIDOVIVO1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getCondicionNacimiento() == 'N') {
                            linea = linea.replace("NACIDOVIVO1", "X");
                        } else {
                            linea = linea.replace("NACIDOVIVO1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NACIDOVIVO2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getCondicionNacimiento() == 'N') {
                            linea = linea.replace("NACIDOVIVO2", "X");
                        } else {
                            linea = linea.replace("NACIDOVIVO2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NACIDOVIVO3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getCondicionNacimiento() == 'N') {
                            linea = linea.replace("NACIDOVIVO3", "X");
                        } else {
                            linea = linea.replace("NACIDOVIVO3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("FolioNacimientoProd1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getFolioCertificadoNac() == null) {
                            linea = linea.replace("FolioNacimientoProd1", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP1().getFolioCertificadoNac().equals("")) {
                            linea = linea.replace("FolioNacimientoProd1", oHosp.getAtencionObstetrica().getP1().getFolioCertificadoNac());
                        } else {
                            linea = linea.replace("FolioNacimientoProd1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("FolioNacimientoProd2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getFolioCertificadoNac() == null) {
                            linea = linea.replace("FolioNacimientoProd2", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP2().getFolioCertificadoNac().equals("")) {
                            linea = linea.replace("FolioNacimientoProd2", oHosp.getAtencionObstetrica().getP2().getFolioCertificadoNac());
                        } else {
                            linea = linea.replace("FolioNacimientoProd2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("FolioNacimientoProd3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getFolioCertificadoNac() == null) {
                            linea = linea.replace("FolioNacimientoProd3", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP3().getFolioCertificadoNac().equals("")) {
                            linea = linea.replace("FolioNacimientoProd3", oHosp.getAtencionObstetrica().getP3().getFolioCertificadoNac());
                        } else {
                            linea = linea.replace("FolioNacimientoProd3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("APGARPRO1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getApgar5Min() > 0) {
                            linea = linea.replace("APGARPRO1", "" + oHosp.getAtencionObstetrica().getP1().getApgar5Min());
                        } else {
                            linea = linea.replace("APGARPRO1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("APGARPRO2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getApgar5Min() > 0) {
                            linea = linea.replace("APGARPRO2", "" + oHosp.getAtencionObstetrica().getP2().getApgar5Min());
                        } else {
                            linea = linea.replace("APGARPRO2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("APGARPRO3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getApgar5Min() > 0) {
                            linea = linea.replace("APGARPRO3", "" + oHosp.getAtencionObstetrica().getP3().getApgar5Min());
                        } else {
                            linea = linea.replace("APGARPRO3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("REANEOSPRO1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal() == null || oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal().equals("")) {
                            linea = linea.replace("REANEOSPRO1", "&nbsp;");
                        } else if (oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal() != null && !oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal().equals("S")) {
                                linea = linea.replace("REANEOSPRO1", "X");
                            } else {
                                linea = linea.replace("REANEOSPRO1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("REANEOSPRO1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("REANEOSPRO2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal() == null || oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal().equals("")) {
                            linea = linea.replace("REANEOSPRO2", "&nbsp;");
                        } else if (oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal() != null && !oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal().equals("S")) {
                                linea = linea.replace("REANEOSPRO2", "X");
                            } else {
                                linea = linea.replace("REANEOSPRO2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("REANEOSPRO2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("REANEOSPRO3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal() == null || oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal().equals("")) {
                            linea = linea.replace("REANEOSPRO3", "&nbsp;");
                        } else if (oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal() != null && !oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal().equals("S")) {
                                linea = linea.replace("REANEOSPRO3", "X");
                            } else {
                                linea = linea.replace("REANEOSPRO3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("REANEOSPRO3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("REANEONPRO1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal() == null || oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal().equals("")) {
                            linea = linea.replace("REANEONPRO1", "&nbsp;");
                        } else if (oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal() != null && !oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP1().getReanimacionNeonatal().equals("N")) {
                                linea = linea.replace("REANEONPRO1", "X");
                            } else {
                                linea = linea.replace("REANEONPRO1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("REANEONPRO1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("REANEONPRO2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal() == null || oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal().equals("")) {
                            linea = linea.replace("REANEONPRO2", "&nbsp;");
                        } else if (oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal() != null && !oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP2().getReanimacionNeonatal().equals("N")) {
                                linea = linea.replace("REANEONPRO2", "X");
                            } else {
                                linea = linea.replace("REANEONPRO2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("REANEONPRO2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("REANEONPRO3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal() == null || oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal().equals("")) {
                            linea = linea.replace("REANEONPRO3", "&nbsp;");
                        } else if (oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal() != null && !oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP3().getReanimacionNeonatal().equals("N")) {
                                linea = linea.replace("REANEONPRO3", "X");
                            } else {
                                linea = linea.replace("REANEONPRO3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("REANEONPRO3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ESTCUNPRO1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getEstanciaEnCunero() > 0) {
                            linea = linea.replace("ESTCUNPRO1", "" + oHosp.getAtencionObstetrica().getP1().getEstanciaEnCunero());
                        } else if (oHosp.getAtencionObstetrica().getP1().getEstanciaEnCunero() == 0 && oHosp.getAtencionObstetrica().getP1().getPesoAlNacer() > 0) {
                            linea = linea.replace("ESTCUNPRO1", "0");
                        } else {
                            linea = linea.replace("ESTCUNPRO1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ESTCUNPRO2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getEstanciaEnCunero() > 0) {
                            linea = linea.replace("ESTCUNPRO2", "" + oHosp.getAtencionObstetrica().getP2().getEstanciaEnCunero());
                        } else if (oHosp.getAtencionObstetrica().getP2().getEstanciaEnCunero() == 0 && oHosp.getAtencionObstetrica().getP2().getPesoAlNacer() > 0) {
                            linea = linea.replace("ESTCUNPRO2", "0");
                        } else {
                            linea = linea.replace("ESTCUNPRO2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ESTCUNPRO3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getEstanciaEnCunero() > 0) {
                            linea = linea.replace("ESTCUNPRO3", "" + oHosp.getAtencionObstetrica().getP3().getEstanciaEnCunero());
                        } else if (oHosp.getAtencionObstetrica().getP3().getEstanciaEnCunero() == 0 && oHosp.getAtencionObstetrica().getP3().getPesoAlNacer() > 0) {
                            linea = linea.replace("ESTCUNPRO3", "0");
                        } else {
                            linea = linea.replace("ESTCUNPRO3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ALTACONMADREPRO1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getCondicion() == null) {
                            linea = linea.replace("ALTACONMADREPRO1", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP1().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP1().getCondicion().equals("T1001")) {
                                linea = linea.replace("ALTACONMADREPRO1", "X");
                            } else {
                                linea = linea.replace("ALTACONMADREPRO1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("ALTACONMADREPRO1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ALTACONMADREPRO2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getCondicion() == null) {
                            linea = linea.replace("ALTACONMADREPRO2", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP2().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP2().getCondicion().equals("T1001")) {
                                linea = linea.replace("ALTACONMADREPRO2", "X");
                            } else {
                                linea = linea.replace("ALTACONMADREPRO2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("ALTACONMADREPRO2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("ALTACONMADREPRO3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getCondicion() == null) {
                            linea = linea.replace("ALTACONMADREPRO3", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP3().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP3().getCondicion().equals("T1001")) {
                                linea = linea.replace("ALTACONMADREPRO3", "X");
                            } else {
                                linea = linea.replace("ALTACONMADREPRO3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("ALTACONMADREPRO3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("VIVOHOSPPROD1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getCondicion() == null) {
                            linea = linea.replace("VIVOHOSPPROD1", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP1().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP1().getCondicion().equals("T1002")) {
                                linea = linea.replace("VIVOHOSPPROD1", "X");
                            } else {
                                linea = linea.replace("VIVOHOSPPROD1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("VIVOHOSPPROD1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("VIVOHOSPPROD2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getCondicion() == null) {
                            linea = linea.replace("VIVOHOSPPROD2", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP2().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP2().getCondicion().equals("T1002")) {
                                linea = linea.replace("VIVOHOSPPROD2", "X");
                            } else {
                                linea = linea.replace("VIVOHOSPPROD2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("VIVOHOSPPROD2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("VIVOHOSPPROD3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getCondicion() == null) {
                            linea = linea.replace("VIVOHOSPPROD3", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP3().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP3().getCondicion().equals("T1002")) {
                                linea = linea.replace("VIVOHOSPPROD3", "X");
                            } else {
                                linea = linea.replace("VIVOHOSPPROD3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("VIVOHOSPPROD3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NACIDOMUERTOPRO1") > 0) {
                        if (oHosp.getAtencionObstetrica().getP1().getCondicion() == null) {
                            linea = linea.replace("NACIDOMUERTOPRO1", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP1().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP1().getCondicion().equals("T1003")) {
                                linea = linea.replace("NACIDOMUERTOPRO1", "X");
                            } else {
                                linea = linea.replace("NACIDOMUERTOPRO1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("NACIDOMUERTOPRO1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NACIDOMUERTOPRO2") > 0) {
                        if (oHosp.getAtencionObstetrica().getP2().getCondicion() == null) {
                            linea = linea.replace("NACIDOMUERTOPRO2", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP2().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP2().getCondicion().equals("T1003")) {
                                linea = linea.replace("NACIDOMUERTOPRO2", "X");
                            } else {
                                linea = linea.replace("NACIDOMUERTOPRO2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("NACIDOMUERTOPRO2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("NACIDOMUERTOPRO3") > 0) {
                        if (oHosp.getAtencionObstetrica().getP3().getCondicion() == null) {
                            linea = linea.replace("NACIDOMUERTOPRO3", "&nbsp;");
                        } else if (!oHosp.getAtencionObstetrica().getP3().getCondicion().equals("")) {
                            if (oHosp.getAtencionObstetrica().getP3().getCondicion().equals("T1003")) {
                                linea = linea.replace("NACIDOMUERTOPRO3", "X");
                            } else {
                                linea = linea.replace("NACIDOMUERTOPRO3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("NACIDOMUERTOPRO3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("AFECCION LESION") > 0) {
                        if (oLes.getAfeccionTratada().getCIE10().getDescripcionDiag() == null) {
                            linea = linea.replace("AFECCION LESION", "&nbsp;");
                        } else if (!oLes.getAfeccionTratada().getCIE10().getDescripcionDiag().equals("")) {
                            String afelesion = "";
                            if (oLes.getAfeccionTratada().getCIE10().getDescripcionDiag().length() > 115) {
                                afelesion = oLes.getAfeccionTratada().getCIE10().getDescripcionDiag().substring(0, 116);
                                linea = linea.replace("AFECCION LESION", afelesion);
                            } else {
                                linea = linea.replace("AFECCION LESION", oLes.getAfeccionTratada().getCIE10().getDescripcionDiag());
                            }
                        } else {
                            linea = linea.replace("AFECCION LESION", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("CIEL") > 0) {
                        if (!oLes.getAfeccionTratada().getCIE10().getClaveCIE10().equals("")) {
                            linea = linea.replace("CIEL", oLes.getAfeccionTratada().getCIE10().getClaveCIE10());
                        } else {
                            linea = linea.replace("CIEL", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("INTEN1") > 0) {
                        if (oLes.getIntencionalidad().getClaveCode() == 1) {
                            linea = linea.replace("INTEN1", "X");
                        } else {
                            linea = linea.replace("INTEN1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("INTEN2") > 0) {
                        if (oLes.getIntencionalidad().getClaveCode() == 2) {
                            linea = linea.replace("INTEN2", "X");
                        } else {
                            linea = linea.replace("INTEN2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("INTEN3") > 0) {
                        if (oLes.getIntencionalidad().getClaveCode() == 3) {
                            linea = linea.replace("INTEN3", "X");
                        } else {
                            linea = linea.replace("INTEN3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("INTEN4") > 0) {
                        if (oLes.getIntencionalidad().getClaveCode() == 4) {
                            linea = linea.replace("INTEN4", "X");
                        } else {
                            linea = linea.replace("INTEN4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("INTEN5") > 0) {
                        if (oLes.getIntencionalidad().getClaveCode() == 9) {
                            linea = linea.replace("INTEN5", "X");
                        } else {
                            linea = linea.replace("INTEN5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU0") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 0) {
                            linea = linea.replace("SITOCU0", "X");
                        } else {
                            linea = linea.replace("SITOCU0", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU1") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 1) {
                            linea = linea.replace("SITOCU1", "X");
                        } else {
                            linea = linea.replace("SITOCU1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU2") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 2) {
                            linea = linea.replace("SITOCU2", "X");
                        } else {
                            linea = linea.replace("SITOCU2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU3") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 3) {
                            linea = linea.replace("SITOCU3", "X");
                        } else {
                            linea = linea.replace("SITOCU3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU4") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 4) {
                            linea = linea.replace("SITOCU4", "X");
                        } else {
                            linea = linea.replace("SITOCU4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU5") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 5) {
                            linea = linea.replace("SITOCU5", "X");
                        } else {
                            linea = linea.replace("SITOCU5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU6") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 6) {
                            linea = linea.replace("SITOCU6", "X");
                        } else {
                            linea = linea.replace("SITOCU6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU7") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 7) {
                            linea = linea.replace("SITOCU7", "X");
                        } else {
                            linea = linea.replace("SITOCU7", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU8") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 8) {
                            linea = linea.replace("SITOCU8", "X");
                        } else {
                            linea = linea.replace("SITOCU8", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("SITOCU9") > 0) {
                        if (oLes.getSitioOcurrencia().getClaveCode() == 9) {
                            linea = linea.replace("SITOCU9", "X");
                        } else {
                            linea = linea.replace("SITOCU9", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIPUNI1") > 0) {
                        if (oHosPsi.getTipoUnidadP() == null) {
                            linea = linea.replace("TIPUNI1", "&nbsp;");
                        } else if (!oHosPsi.getTipoUnidadP().equals("")) {
                            if (oHosPsi.getTipoUnidadP().equals("T1101")) {
                                linea = linea.replace("TIPUNI1", "X");
                            } else {
                                linea = linea.replace("TIPUNI1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIPUNI1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIPUNI2") > 0) {
                        if (oHosPsi.getTipoUnidadP() == null) {
                            linea = linea.replace("TIPUNI2", "&nbsp;");
                        } else if (!oHosPsi.getTipoUnidadP().equals("")) {
                            if (oHosPsi.getTipoUnidadP().equals("T1102")) {
                                linea = linea.replace("TIPUNI2", "X");
                            } else {
                                linea = linea.replace("TIPUNI2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIPUNI2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIPUNI3") > 0) {
                        if (oHosPsi.getTipoUnidadP() == null) {
                            linea = linea.replace("TIPUNI3", "&nbsp;");
                        } else if (!oHosPsi.getTipoUnidadP().equals("")) {
                            if (oHosPsi.getTipoUnidadP().equals("T1103")) {
                                linea = linea.replace("TIPUNI3", "X");
                            } else {
                                linea = linea.replace("TIPUNI3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIPUNI1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNC1") > 0) {
                        if (oHosPsi.getServiciosHCP() == null) {
                            linea = linea.replace("TIUNC1", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHCP().equals("")) {
                            if (oHosPsi.getServiciosHCP().equals("T1201")) {
                                linea = linea.replace("TIUNC1", "X");
                            } else {
                                linea = linea.replace("TIUNC1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNC1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNC2") > 0) {
                        if (oHosPsi.getServiciosHCP() == null) {
                            linea = linea.replace("TIUNC2", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHCP().equals("")) {
                            if (oHosPsi.getServiciosHCP().equals("T1202")) {
                                linea = linea.replace("TIUNC2", "X");
                            } else {
                                linea = linea.replace("TIUNC2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNC2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNC3") > 0) {
                        if (oHosPsi.getServiciosHCP() == null) {
                            linea = linea.replace("TIUNC3", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHCP().equals("")) {
                            if (oHosPsi.getServiciosHCP().equals("T1203")) {
                                linea = linea.replace("TIUNC3", "X");
                            } else {
                                linea = linea.replace("TIUNC3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNC3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNC4") > 0) {
                        if (oHosPsi.getServiciosHCP() == null) {
                            linea = linea.replace("TIUNC4", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHCP().equals("")) {
                            if (oHosPsi.getServiciosHCP().equals("T1204")) {
                                linea = linea.replace("TIUNC4", "X");
                            } else {
                                linea = linea.replace("TIUNC4", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNC4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNC5") > 0) {
                        if (oHosPsi.getServiciosHCP() == null) {
                            linea = linea.replace("TIUNC5", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHCP().equals("")) {
                            if (oHosPsi.getServiciosHCP().equals("T1205")) {
                                linea = linea.replace("TIUNC5", "X");
                            } else {
                                linea = linea.replace("TIUNC5", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNC5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNC6") > 0) {
                        if (oHosPsi.getServiciosHCP() == null) {
                            linea = linea.replace("TIUNC6", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHCP().equals("")) {
                            if (oHosPsi.getServiciosHCP().equals("T1206")) {
                                linea = linea.replace("TIUNC6", "X");
                            } else {
                                linea = linea.replace("TIUNC6", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNC6", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNC7") > 0) {
                        if (oHosPsi.getServiciosHCP() == null) {
                            linea = linea.replace("TIUNC7", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHCP().equals("")) {
                            if (oHosPsi.getServiciosHCP().equals("T1209")) {
                                linea = linea.replace("TIUNC7", "X");
                            } else {
                                linea = linea.replace("TIUNC7", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNC7", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNP1") > 0) {
                        if (oHosPsi.getServiciosHPP() == null) {
                            linea = linea.replace("TIUNP1", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHPP().equals("")) {
                            if (oHosPsi.getServiciosHPP().equals("T1301")) {
                                linea = linea.replace("TIUNP1", "X");
                            } else {
                                linea = linea.replace("TIUNP1", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNP1", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNP2") > 0) {
                        if (oHosPsi.getServiciosHPP() == null) {
                            linea = linea.replace("TIUNP2", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHPP().equals("")) {
                            if (oHosPsi.getServiciosHPP().equals("T1302")) {
                                linea = linea.replace("TIUNP2", "X");
                            } else {
                                linea = linea.replace("TIUNP2", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNP2", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNP3") > 0) {
                        if (oHosPsi.getServiciosHPP() == null) {
                            linea = linea.replace("TIUNP3", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHPP().equals("")) {
                            if (oHosPsi.getServiciosHPP().equals("T1303")) {
                                linea = linea.replace("TIUNP3", "X");
                            } else {
                                linea = linea.replace("TIUNP3", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNP3", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNP4") > 0) {
                        if (oHosPsi.getServiciosHPP() == null) {
                            linea = linea.replace("TIUNP4", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHPP().equals("")) {
                            if (oHosPsi.getServiciosHPP().equals("T1304")) {
                                linea = linea.replace("TIUNP4", "X");
                            } else {
                                linea = linea.replace("TIUNP4", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNP4", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("TIUNP5") > 0) {
                        if (oHosPsi.getServiciosHPP() == null) {
                            linea = linea.replace("TIUNP5", "&nbsp;");
                        } else if (!oHosPsi.getServiciosHPP().equals("")) {
                            if (oHosPsi.getServiciosHPP().equals("T1309")) {
                                linea = linea.replace("TIUNP5", "X");
                            } else {
                                linea = linea.replace("TIUNP5", "&nbsp;");
                            }
                        } else {
                            linea = linea.replace("TIUNP5", "&nbsp;");
                        }
                    }
                    if (linea.indexOf("MEDICO AUTORIZA") > 0) {
                        String nombreMedico = oHosp.buscaNombreMedicoAutoriza();
                        if (nombreMedico != null && !nombreMedico.equals("")) {
                            linea = linea.replace("MEDICO AUTORIZA", nombreMedico);
                        } else {
                            linea = linea.replace("MEDICO AUTORIZA", "&nbsp;");
                        }
                    }

                    str += linea + " ";

                }

                //System.out.println(str);
                pw.println(str);
                pw.println("<DIV id=\"page_1\">\n"
                        + "<DIV id=\"dimg1\">\n"
                        + "<IMG src=\"#{facesContext.externalContext.requestContextPath}/imgs/hoja1.png\" id=\"img1\">\n"
                        + "</IMG>"
                        + "</DIV>\n"
                        + "</DIV>\n"
                        + "\n"
                        + "<DIV id=\"page_2\">\n"
                        + "<DIV id=\"dimg1\">\n"
                        + "<IMG src=\"#{facesContext.externalContext.requestContextPath}/imgs/hoja2.png\" id=\"img1\">\n"
                        + "</IMG>"
                        + "</DIV>\n"
                        + "</DIV>"
                        + "</body>\n"
                        + "</html>");
                fichero.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(bVerDesdeExpediente==true)
            return "hospitalizacion/HojaCodeImpresa";
        else
            return "HojaCodeImpresa";
    }

    /**
     * @return the nFolioPaciente
     */
    public long getFolioPaciente() {
        return nFolioPaciente;
    }

    /**
     * @param nFolioPaciente the nFolioPaciente to set
     */
    public void setFolioPaciente(long nFolioPaciente) {
        this.nFolioPaciente = nFolioPaciente;
    }

    /**
     * @return the nClaveEpisodio
     */
    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    /**
     * @param nClaveEpisodio the nClaveEpisodio to set
     */
    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    /**
     * @return the nNumIngresoHosp
     */
    public long getNumIngresoHosp() {
        return nNumIngresoHosp;
    }

    /**
     * @param nNumIngresoHosp the nNumIngresoHosp to set
     */
    public void setNumIngresoHosp(long nNumIngresoHosp) {
        this.nNumIngresoHosp = nNumIngresoHosp;
    }

    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }

    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }

    /**
     * @return the oHosDef
     */
    public Hospitalizacion getHosDef() {
        return oHosDef;
    }

    /**
     * @param oHosDef the oHosDef to set
     */
    public void setHosDef(Hospitalizacion oHosDef) {
        this.oHosDef = oHosDef;
    }

    /**
     * @return the oLes
     */
    public Lesion getLes() {
        return oLes;
    }

    /**
     * @param oLes the oLes to set
     */
    public void setLes(Lesion oLes) {
        this.oLes = oLes;
    }

    /**
     * @return the oHosPsi
     */
    public HospitalPsiquiatrico getHosPsi() {
        return oHosPsi;
    }

    /**
     * @param oHosPsi the oHosPsi to set
     */
    public void setHosPsi(HospitalPsiquiatrico oHosPsi) {
        this.oHosPsi = oHosPsi;
    }
    
    public boolean getVerDesdeExpediente() {
        return bVerDesdeExpediente;
    }

    public void setVerDesdeExpediente(boolean bVerDesdeExpediente) {
        this.bVerDesdeExpediente = bVerDesdeExpediente;
    }
}